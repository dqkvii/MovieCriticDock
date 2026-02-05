import mysql.connector
import requests
import logging
import ssl
import urllib3
from time import sleep
from mysql.connector import errorcode
from typing import List, Tuple, Optional, Dict, Any

urllib3.disable_warnings(urllib3.exceptions.InsecureRequestWarning)

try:
    _create_unverified_https_context = ssl._create_unverified_context
except AttributeError:
    pass
else:
    ssl._create_default_https_context = _create_unverified_https_context

logging.basicConfig(
    level=logging.INFO,
    format='%(asctime)s - %(levelname)s - %(message)s',
    handlers=[
        logging.FileHandler('imdb_scraper.log'),
        logging.StreamHandler()
    ]
)
logger = logging.getLogger(__name__)

class DatabaseManager:
    def __init__(self):
        self.db_config = {
            'host': "beiuugh9j96baskmwgfk-mysql.services.clever-cloud.com",
            'user': "uwur7cu0drdtirmk",
            'password': "rYVdesz9MyPNDvCB5hBh",
            'database': "beiuugh9j96baskmwgfk",
            'connection_timeout': 180,
            'autocommit': False
        }
        self.conn = None
        self.cursor = None
    
    def connect(self) -> Tuple[mysql.connector.MySQLConnection, mysql.connector.cursor.MySQLCursor]:
        """Establish database connection with retry logic"""
        max_retries = 3
        retry_count = 0
        
        while retry_count < max_retries:
            try:
                ssl_config = {
                    'ssl_disabled': True,  
                }
                
                db_config_with_ssl = {**self.db_config, **ssl_config}
                
                self.conn = mysql.connector.connect(**db_config_with_ssl)
                self.cursor = self.conn.cursor()
                logger.info("Database connection established successfully")
                return self.conn, self.cursor
                
            except mysql.connector.Error as err:
                retry_count += 1
                if err.errno == errorcode.CR_SERVER_GONE_ERROR or err.errno == errorcode.CR_SERVER_LOST:
                    logger.warning(f"Database connection lost. Retrying in 5 seconds... (attempt {retry_count})")
                    sleep(5)
                elif retry_count < max_retries:
                    logger.warning(f"Database connection error: {err}. Retrying... (attempt {retry_count})")
                    sleep(3)
                else:
                    logger.error(f"Database connection failed after {max_retries} attempts: {err}")
                    raise
            except Exception as e:
                retry_count += 1
                if retry_count < max_retries:
                    logger.warning(f"Unexpected database error: {e}. Retrying... (attempt {retry_count})")
                    sleep(3)
                else:
                    logger.error(f"Database connection failed after {max_retries} attempts: {e}")
                    raise
        
        raise Exception("Failed to establish database connection")
    
    def insert_movie(self, values: List) -> bool:
        """Insert movie data with retry logic"""
        max_retries = 3
        retries = 0
        
        while retries < max_retries:
            try:
                if not self.conn.is_connected():
                    logger.warning("Connection lost. Reconnecting...")
                    self.connect()
                    
                self.cursor.execute("CALL insertMovie(%s, %s, %s, %s, %s, %s, %s, %s, %s, %s, %s, %s, %s, %s)", values)
                return True
                
            except mysql.connector.Error as err:
                retries += 1
                if err.errno == errorcode.CR_SERVER_GONE_ERROR or err.errno == errorcode.CR_SERVER_LOST:
                    logger.warning(f"Connection error: {err}. Attempt {retries} of {max_retries}")
                    self.connect()
                else:
                    logger.error(f"Insert error: {err}")
                    logger.error(f"Data: {values}")
                    if retries == max_retries:
                        logger.error("Maximum retry attempts reached")
                        return False
                    sleep(2)
        
        return False
    
    def commit(self):
        """Commit transaction"""
        if self.conn and self.conn.is_connected():
            self.conn.commit()
            logger.info("Transaction committed")
    
    def close(self):
        """Close database connection"""
        if self.cursor:
            self.cursor.close()
        if self.conn and self.conn.is_connected():
            self.conn.close()
            logger.info("Database connection closed successfully")

class OMDBClient:
    def __init__(self, api_keys: List[str]):
        self.api_keys = api_keys
        self.current_key_index = 0
        self.request_count = 0
        self.requests_per_key = 1000
    
    def get_current_api_key(self) -> str:
        """Get current API key"""
        return self.api_keys[self.current_key_index]
    
    def rotate_api_key(self) -> bool:
        """Rotate to next API key"""
        self.current_key_index += 1
        self.request_count = 0
        
        if self.current_key_index >= len(self.api_keys):
            logger.error("All API keys exhausted")
            return False
        
        logger.info(f"Switched to API key #{self.current_key_index + 1}")
        return True
    
    def get_movie_data(self, imdb_id: str) -> Optional[Dict[str, Any]]:
        """Fetch movie data from OMDB API"""
        max_retries = 3
        retry_count = 0
        
        while retry_count < max_retries:
            try:
                api_key = self.get_current_api_key()
                
                session = requests.Session()
                session.verify = False 
                
                headers = {
                    'User-Agent': 'Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/91.0.4472.124 Safari/537.36'
                }
                
                response = session.get(
                    f"http://www.omdbapi.com/?i={imdb_id}&apikey={api_key}",
                    headers=headers,
                    timeout=30
                )
                response.raise_for_status()
                
                self.request_count += 1
                
                if self.request_count >= self.requests_per_key:
                    if not self.rotate_api_key():
                        return None
                
                return response.json()
                
            except requests.exceptions.SSLError as ssl_err:
                retry_count += 1
                logger.warning(f"SSL error for {imdb_id} (attempt {retry_count}): {ssl_err}")
                if retry_count < max_retries:
                    sleep(2)
                    continue
                else:
                    logger.error(f"SSL error persists after {max_retries} attempts for {imdb_id}")
                    return None
                    
            except requests.exceptions.RequestException as e:
                retry_count += 1
                logger.warning(f"Request error for {imdb_id} (attempt {retry_count}): {e}")
                if retry_count < max_retries:
                    sleep(2)
                    continue
                else:
                    logger.error(f"Request failed after {max_retries} attempts for {imdb_id}")
                    return None
                    
            except Exception as e:
                logger.error(f"Unexpected error for {imdb_id}: {e}")
                return None
        
        return None

class MovieDataProcessor:
    @staticmethod
    def clean_numeric_value(value: str) -> Optional[float]:
        """Convert string to numeric value, handling N/A cases"""
        if value in ('N/A', None, ''):
            return None
        try:
            return float(value) if '.' in str(value) else int(value)
        except (ValueError, TypeError):
            return None
    
    @staticmethod
    def get_rotten_tomatoes_rating(response: Dict[str, Any]) -> Optional[str]:
        """Extract Rotten Tomatoes rating from response"""
        ratings = response.get('Ratings', [])
        rt_rating = next((r.get('Value') for r in ratings if r.get('Source') == 'Rotten Tomatoes'), None)
        return rt_rating if rt_rating != 'N/A' else None
    
    @staticmethod
    def clean_string_value(value: str) -> Optional[str]:
        """Clean string value, returning None for N/A"""
        return value if value and value != 'N/A' else None
    
    def extract_movie_data(self, response: Dict[str, Any]) -> List:
        """Extract and clean movie data from API response"""
        return [
            response.get('imdbID'),
            self.clean_string_value(response.get('Type')),
            self.clean_string_value(response.get('Title')),
            self.clean_numeric_value(response.get('Year')),
            self.clean_string_value(response.get('Released')),
            self.clean_string_value(response.get('Runtime')),
            self.clean_string_value(response.get('Director')),
            self.clean_string_value(response.get('Plot')),
            self.clean_string_value(response.get('Country')),
            self.clean_string_value(response.get('Poster')),
            self.clean_numeric_value(response.get('Metascore')),
            self.get_rotten_tomatoes_rating(response),
            self.clean_numeric_value(response.get('imdbRating')),
            self.clean_string_value(response.get('Genre')),
            self.clean_string_value(response.get('BoxOffice'))
        ]
    
    def validate_movie_data(self, movie_data: List) -> bool:
        """Check if all required fields (excluding box office) are not None"""
        required_fields = movie_data[:-1]
        
        if any(field is None for field in required_fields):
            missing_fields = [i for i, field in enumerate(required_fields) if field is None]
            logger.debug(f"Missing required fields at indices: {missing_fields}")
            return False
        
        return True

class IMDBScraper:
    def __init__(self, api_keys_file: str, start_id: int, end_id: int):
        self.api_keys = self.load_api_keys(api_keys_file)
        self.start_id = start_id
        self.end_id = end_id
        self.current_id = start_id
        self.db_manager = DatabaseManager()
        self.omdb_client = OMDBClient(self.api_keys)
        self.data_processor = MovieDataProcessor()
        self.commit_frequency = 10
        self.successful_inserts = 0
    
    def load_api_keys(self, file_path: str) -> List[str]:
        """Load API keys from file"""
        try:
            with open(file_path, 'r', encoding='utf-8') as file:
                api_keys = [line.strip() for line in file if line.strip()]
            logger.info(f"Loaded {len(api_keys)} API keys")
            return api_keys
        except FileNotFoundError:
            logger.error(f"API keys file not found: {file_path}")
            raise
    
    def format_imdb_id(self, numeric_id: int) -> str:
        """Convert numeric ID to IMDB format (tt0000000)"""
        return f"tt{numeric_id:07d}"
    
    def process_movie(self, imdb_id: str) -> bool:
        """Process a single movie"""
        try:
            response = self.omdb_client.get_movie_data(imdb_id)
            if not response:
                logger.error(f"Failed to get API response for {imdb_id}")
                return False
            
            if response.get("Response") == "False":
                logger.warning(f"Movie not found: {imdb_id}")
                return False
            
            movie_data = self.data_processor.extract_movie_data(response)
            
            if not self.data_processor.validate_movie_data(movie_data):
                logger.info(f"Skipping {imdb_id}: missing required data")
                return False
            
            success = self.db_manager.insert_movie(movie_data[:-1])
            
            if success:
                self.successful_inserts += 1
                logger.info(f"Successfully added movie: {imdb_id} (Total: {self.successful_inserts})")
                
                if self.successful_inserts % self.commit_frequency == 0:
                    self.db_manager.commit()
                    logger.info(f"Committed after {self.successful_inserts} inserts")
                
                return True
            else:
                logger.error(f"Failed to insert movie: {imdb_id}")
                return False
                
        except Exception as e:
            logger.error(f"Unexpected error processing {imdb_id}: {e}")
            return False
    
    def run(self):
        """Main execution method"""
        logger.info(f"Starting IMDB scraper from ID {self.start_id} to {self.end_id}")
        logger.info(f"Processing range: {self.format_imdb_id(self.start_id)} to {self.format_imdb_id(self.end_id)}")
        
        try:
            self.db_manager.connect()
            
            processed_count = 0
            for numeric_id in range(self.start_id, self.end_id + 1):
                try:
                    self.current_id = numeric_id
                    imdb_id = self.format_imdb_id(numeric_id)
                    
                    self.process_movie(imdb_id)
                    processed_count += 1
                    
                    if processed_count % 100 == 0:
                        logger.info(f"Processed {processed_count} IDs - Current: {imdb_id}")
                    
                    sleep(0.1)
                    
                except KeyboardInterrupt:
                    logger.warning(f"Process interrupted by user at ID: {self.format_imdb_id(self.current_id)}")
                    break
                    
                except Exception as e:
                    logger.error(f"Error processing {self.format_imdb_id(numeric_id)}: {e}")
                    continue
            
            self.db_manager.commit()
            logger.info(f"Scraping completed. Successfully processed {self.successful_inserts} movies")
            logger.info(f"Stopped at ID: {self.format_imdb_id(self.current_id)}")
            
        except Exception as e:
            logger.error(f"Critical error: {e}")
            logger.info(f"Stopped at ID: {self.format_imdb_id(self.current_id)}")
        
        finally:
            self.db_manager.close()
            logger.info(f"Scraper finished. Last processed ID: {self.format_imdb_id(self.current_id)}")
            print(f"\n=== FINAL STATUS ===")
            print(f"Last processed ID: {self.format_imdb_id(self.current_id)}")
            print(f"Successfully inserted movies: {self.successful_inserts}")
            print(f"Total IDs processed: {self.current_id - self.start_id + 1}")

def main():
    """Main function"""
    try:
        print("=== IMDB Movie Scraper ===")
        print("Initializing SSL and network settings...")
        
        try:
            test_response = requests.get("http://httpbin.org/ip", timeout=10, verify=False)
            logger.info("Network connectivity test passed")
        except Exception as e:
            logger.warning(f"Network test failed: {e}")
            print("Warning: Network connectivity issues detected")
        
        start_id = int(input("Enter starting ID (numeric, e.g., 1095000): "))
        end_id = int(input("Enter ending ID (numeric, e.g., 2000000): "))
        
        print(f"Will process IDs from tt{start_id:07d} to tt{end_id:07d}")
        confirm = input("Continue? (y/n): ").lower()
        
        if confirm != 'y':
            print("Operation cancelled")
            return
        
        scraper = IMDBScraper(
            api_keys_file='./apikeys.txt',
            start_id=start_id,
            end_id=end_id
        )
        scraper.run()
        
    except ValueError:
        logger.error("Invalid input. Please enter numeric values only.")
    except Exception as e:
        logger.error(f"Failed to initialize scraper: {e}")

if __name__ == "__main__":
    main()