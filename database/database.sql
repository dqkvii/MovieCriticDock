CREATE TABLE movies
(
    title            VARCHAR(256) NULL,
    year             INT          NULL,
    released         DATE         NULL,
    runtime          VARCHAR(255) NULL,
    director         VARCHAR(256) NULL,
    plot             TEXT         NULL,
    country          VARCHAR(256) NULL,
    poster           VARCHAR(512) NULL,
    rating_metascore VARCHAR(255) NULL,
    rating_rot_tom   VARCHAR(4)   NULL,
    rating_imdb      VARCHAR(255) NULL,
    type             VARCHAR(7)   NULL,
    imdb_id          VARCHAR(255) NOT NULL
        PRIMARY KEY,
    genre            VARCHAR(255) NULL,
    CHECK (`type` IN ('movie','series','episode')),
    CHECK (`imdb_id` LIKE 'tt%')
)
ENGINE=InnoDB
DEFAULT CHARSET=utf8mb4
COLLATE=utf8mb4_unicode_ci;

CREATE TABLE users
(
    id       INT AUTO_INCREMENT
        PRIMARY KEY,
    username VARCHAR(255) NULL,
    email    VARCHAR(255) NULL,
    password VARCHAR(255) NULL,
    CONSTRAINT email
        UNIQUE (email),
    CONSTRAINT username
        UNIQUE (username),
    CHECK (`email` LIKE '%@%.%')
)
ENGINE=InnoDB
DEFAULT CHARSET=utf8mb4
COLLATE=utf8mb4_unicode_ci;

CREATE TABLE watchlist
(
    id         INT AUTO_INCREMENT
        PRIMARY KEY,
    user_id    INT                                                                                 NOT NULL,
    movie_id   VARCHAR(255)                                                                        NOT NULL,
    status     ENUM ('favourite', 'planned', 'watching', 'completed', 'dropped') DEFAULT 'planned'  NULL,
    created_at DATE                                                                                NOT NULL,
    CONSTRAINT fk_movie_id
        FOREIGN KEY (movie_id) REFERENCES movies (imdb_id)
            ON UPDATE CASCADE ON DELETE CASCADE,
    CONSTRAINT watchlist_ibfk_1
        FOREIGN KEY (user_id) REFERENCES users (id)
            ON UPDATE CASCADE ON DELETE CASCADE
)
ENGINE=InnoDB
DEFAULT CHARSET=utf8mb4
COLLATE=utf8mb4_unicode_ci;

CREATE INDEX user_id
    ON watchlist (user_id);

DELIMITER $$

CREATE PROCEDURE NullChecker()
BEGIN
    SELECT *
    FROM movies
    WHERE
        title IS NULL OR
        year IS NULL OR
        released IS NULL OR
        runtime IS NULL OR
        director IS NULL OR
        plot IS NULL OR
        country IS NULL OR
        poster IS NULL OR
        rating_metascore IS NULL OR
        rating_rot_tom IS NULL OR
        rating_imdb IS NULL OR
        type IS NULL OR
        genre IS NULL;
END$$

CREATE PROCEDURE create_user(IN p_user VARCHAR(255), IN p_email VARCHAR(255), IN p_password VARCHAR(255))
BEGIN
    INSERT INTO users (username, email, password) VALUES (p_user, p_email, p_password);
END$$

CREATE PROCEDURE getFiveLastMovies()
BEGIN
    SELECT *
    FROM movies
    ORDER BY imdb_id DESC
    LIMIT 5;
END$$

CREATE PROCEDURE getMovieByImdbId(IN p_imdb_id VARCHAR(9))
BEGIN
    SELECT *
    FROM movies
    WHERE imdb_id = p_imdb_id;
END$$

CREATE PROCEDURE getRandomMovie()
BEGIN
    SELECT *
    FROM movies
    ORDER BY RAND()
    LIMIT 1;
END$$

CREATE PROCEDURE getUserById(IN p_id INT)
BEGIN
    SELECT *
    FROM users
    WHERE id = p_id;
END$$

CREATE PROCEDURE getWatchlistByUserId(IN p_user_id INT)
BEGIN
    SELECT *
    FROM watchlist
    WHERE user_id = p_user_id;
END$$

CREATE PROCEDURE insertMovie(
    IN p_imdb_id VARCHAR(11),
    IN p_type VARCHAR(10),
    IN p_title VARCHAR(256),
    IN p_year INT,
    IN p_released VARCHAR(20),
    IN p_runtime VARCHAR(256),
    IN p_director VARCHAR(256),
    IN p_plot MEDIUMTEXT,
    IN p_country VARCHAR(256),
    IN p_poster VARCHAR(512),
    IN p_r_meta VARCHAR(4),
    IN p_r_rot VARCHAR(4),
    IN p_r_imdb VARCHAR(4),
    IN p_genre VARCHAR(256)
)
BEGIN
    DECLARE release_date DATE;

    IF p_released = 'N/A' THEN
        SET release_date = NULL;
    ELSE
        SET release_date = STR_TO_DATE(p_released, '%d %b %Y');
    END IF;

    IF p_r_imdb = 'N/A' THEN SET p_r_imdb = NULL; END IF;
    IF p_r_meta = 'N/A' THEN SET p_r_meta = NULL; END IF;
    IF p_runtime = 'N/A' THEN SET p_runtime = NULL; END IF;
    IF p_released = 'N/A' THEN SET p_released = NULL; END IF;
    IF p_country = 'N/A' THEN SET p_country = NULL; END IF;
    IF p_director = 'N/A' THEN SET p_director = NULL; END IF;
    IF p_plot = 'N/A' THEN SET p_plot = NULL; END IF;
    IF p_r_rot = 'N/A' THEN SET p_r_rot = NULL; END IF;
    IF p_type = 'N/A' THEN SET p_type = NULL; END IF;
    IF p_poster = 'N/A' THEN SET p_poster = NULL; END IF;
    IF p_genre = 'N/A' THEN SET p_genre = NULL; END IF;

    INSERT INTO movies (imdb_id, type, title, year,
                        released, runtime, director, plot,
                        country, poster, rating_metascore,
                        rating_rot_tom, rating_imdb, genre)
    VALUES (p_imdb_id, p_type, p_title, p_year, release_date, p_runtime, p_director, p_plot,
            p_country, p_poster, p_r_meta, p_r_rot, p_r_imdb, p_genre);
END$$

CREATE PROCEDURE updateGenre(IN p_genre VARCHAR(256), IN p_title VARCHAR(256))
BEGIN
    UPDATE movies SET genre = p_genre
    WHERE title = p_title;
END$$

DELIMITER ;


