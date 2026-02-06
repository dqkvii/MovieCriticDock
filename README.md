# Docker project — MovieCritic

Projekt webowej aplikacji do zarządzania i przeglądania informacji o filmach. Aplikacja została zrealizowana w architekturze klient–serwer z dostępem do bazy danych i uruchamiana w całości przy pomocy Docker Compose.

---

## Temat projektu
**MovieCritic** — aplikacja webowa z interfejsem użytkownika umożliwiająca pracę z danymi filmów oraz funkcjami użytkownika (np. watchlista), oparta o backend REST API i bazę danych **MySQL**.

---

## Autorzy
- **Volodymyr Hryhoriak** — **55651**
- **Maksym Fisiak** — **58703**

---

## Wykorzystane technologie
- **Frontend:** React + TypeScript (Vite), Axios, MUI / TailwindCSS
- **Backend:** Java (Spring Boot), Spring MVC, Spring Data JPA, Spring Security
- **Baza danych:** MySQL 8.0
- **Konteneryzacja:** Docker, Docker Compose, Nginx (serwowanie zbudowanego frontendu)

---

## Funkcjonalności projektu
Aplikacja umożliwia pracę z danymi w bazie danych poprzez REST API oraz interfejs w przeglądarce.

Zrealizowane funkcjonalności :
1) **Watchlista (CRUD wpisów watchlisty użytkownika)**  
   - dodawanie filmu do watchlisty użytkownika  
   - pobieranie watchlisty użytkownika  
   - aktualizacja statusu (np. planned/watching/completed)  
   - usuwanie pozycji z watchlisty  

2) **Użytkownicy (operacje na użytkownikach)**  
   - tworzenie użytkownika (np. przez endpoint / logikę backendu)  
   - pobieranie danych użytkownika  

3) **Filmy (odczyt danych filmów)**  
   - pobieranie listy / szczegółów filmów (w zależności od endpointów API)

---

## Architektura Docker
Projekt składa się z trzech kontenerów:
- **mysql** — baza danych MySQL 8.0 (inicjalizacja schematu z `database/database.sql`)
- **backend** — aplikacja Spring Boot (REST API)
- **frontend** — Nginx serwujący zbudowaną aplikację React

---

## Uruchomienie aplikacji (Docker Compose)
- Docker Desktop (Docker Engine + Docker Compose)

### 1) Konfiguracja środowiska
Projekt używa pliku `.env`. W repozytorium znajduje się plik przykładowy `.env.example`.

1. Skopiuj plik:
   - **Windows (PowerShell):**
     ```powershell
     Copy-Item .env.example .env
     ```
   - **Linux/macOS:**
     ```bash
     cp .env.example .env
     ```

2.Uzupełnij wartości `<CHANGE_ME>` w `.env`.

PORT=8080
```
SPRING.DATASOURCE.URL=jdbc:mysql://b0j1ffermfvmtrwyhcti-mysql.services.clever-cloud.com:3306/beiuugh9j96baskmwgfk?useSSL=false&serverTimezone=UTC
SPRING.DATASOURCE.USERNAME=uwur7cu0drdtirmk
SPRING.DATASOURCE.PASSWORD=rYVdesz9MyPNDvCB5hBh
SPRING.DATASOURCE.DRIVER-CLASS-NAME=com.mysql.cj.jdbc.Driver

SPRING_SECURITY_USER_NAME=aeribmm@gmail.com
SPRING_SECURITY_USER_PASSWORD=55555

JWT_SECRET=c7cb75cfb15d4484ff7d823e2800e919081adc904347c90f74855823db20656b5879829ff0364d331ebc43a4576fd65da30773ba847378cebadc216094f87ae9120ae9946706fc2ea7052a831d726350ab6bd47bb1554a7a4df74eadd72088ac4f49c4fa236b436778ca3aa129bfa19b135b2f8a07341e7080b17a7c71c0f35daa6d7367d043a6d46c0d202e03b00e0f4c76c05bb415047545e8ec8fb1c1303faf3ed1275d1457b4dde698618cc61005097419922a85d8e4c06f25a0467fd275f3e9e49c50008f5aff1ccc595ce7597c67fe77eb531a215339c8f448a6a44b3009ee20f8c9b0abbb80074f959cc71442876b93f29d75dd1a7f2ff78561ea4d48
DEEPSEEK_KEY=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
DEEPSEEK_API_URL=https://api.deepseek.com/v1/chat/completions
OMDB_API_KEY=a3da22e4

VITE_API_BASE_URL=http://localhost:8080


### 2) Start kontenerów
W katalogu głównym projektu uruchom:
  ```bash
  docker compose up --build
  ```
  ```
Aby uruchomić w tle:
  ```bash
  docker compose up --build -d
  ```
Żeby uruchomić aplikację należy skorzystać z komedny:

```bash
docker compose up
```


### 2b) Start kontenerów (alternatywnie: obrazy z Docker Hub)
Jeśli nie chcesz budować obrazów lokalnie, możesz uruchomić projekt używając gotowych obrazów z Docker Hub:
  ```bash
  docker compose -f docker-compose.hub.yml up -d
  ```

### 3) Dostęp do aplikacji
- **Frontend:** http://localhost:3000
- **Backend:** http://localhost:8080
- **Healthcheck backendu:** http://localhost:8080/actuator/health

---

## Dodatkowe informacje / rozwiązywanie problemów
- Jeśli zmieniałeś `database/database.sql` i chcesz zainicjalizować bazę od nowa:
  ```bash
  docker compose down -v
  docker compose up --build
  ```
- Logi usług:
  ```bash
  docker compose logs -f mysql
  docker compose logs -f backend
  docker compose logs -f frontend
  ```

---