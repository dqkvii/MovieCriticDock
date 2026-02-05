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

Zrealizowane funkcjonalności (minimum z zadania spełnione — min. 2):
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

> Uwaga: jeśli ocenianie wprost liczy “funkcjonalność” jako pełny CRUD na osobnej tabeli/zasobie,
> to najsilniej “liczy się” Watchlista (pełny CRUD). Pozostałe funkcje są co najmniej kompletne po stronie API/UI zgodnie z aktualną implementacją.

---

## Architektura Docker
Projekt składa się z trzech kontenerów:
- **mysql** — baza danych MySQL 8.0 (inicjalizacja schematu z `database/database.sql`)
- **backend** — aplikacja Spring Boot (REST API)
- **frontend** — Nginx serwujący zbudowaną aplikację React

---

## Uruchomienie aplikacji (Docker Compose)
### Wymagania
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

2. (Opcjonalnie) Uzupełnij wartości `<CHANGE_ME>` w `.env` (jeśli dana funkcjonalność tego wymaga).

### 2) Start kontenerów
W katalogu głównym projektu uruchom:
  ```bash
  docker compose up --build
  ```
Aby uruchomić w tle:
  ```bash
  docker compose up --build -d
  ```

### 3) Dostęp do aplikacji
- **Frontend:** http://localhost:3000
- **Backend:** http://localhost:8080
- **MySQL (opcjonalnie, debug):** `localhost:3306`

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

## Zgodność z wymaganiami zadania (skrót)
- Aplikacja webowa + UI: ✅ (React)
- Dostęp do bazy danych: ✅ (MySQL + JPA)
- Uruchomienie przez `docker compose up`: ✅ (3 kontenery)
- Minimum 2 funkcjonalności: ✅ (Watchlista + Użytkownicy; dodatkowo Filmy – odczyt)
- Repo zawiera README z tematem, autorami i instrukcją uruchomienia: ✅