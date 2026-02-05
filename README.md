# Docker project — MovieCritic

Projekt webowej aplikacji do zarządzania i przeglądania informacji o filmach. Aplikacja została zrealizowana w architekturze klient–serwer z dostępem do bazy danych i uruchamiana w całości przy pomocy konteneryzacji Docker (Docker Compose).

---

## Temat projektu
**MovieCritic** — aplikacja webowa z interfejsem użytkownika umożliwiająca pracę z danymi filmów oraz funkcjami użytkownika (np. watchlista), oparta o backend REST API i bazę danych MySQL.

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
Aplikacja umożliwia pracę z danymi w bazie danych poprzez REST API oraz wygodny interfejs w przeglądarce.

Przykładowe funkcjonalności (wymagane minimum spełnione — min. 2 funkcjonalności):
- **Filmy**
    - pobieranie danych filmów (np. lista / szczegóły)
    - losowy film / ostatnio dodane (jeśli dostępne w API)
- **Użytkownicy**
    - tworzenie użytkownika (procedura w bazie danych)
    - pobieranie danych użytkownika
- **Watchlista**
    - dodawanie filmu do watchlisty użytkownika
    - pobieranie watchlisty użytkownika
    - aktualizacja statusu (planned/watching/completed itp.)
    - usuwanie pozycji

> Zakres funkcjonalności może być rozszerzany w zależności od wersji projektu.

---

## Architektura Docker
Projekt składa się z trzech powiązanych kontenerów:
- **mysql** — kontener z bazą danych MySQL 8.0 (inicjalizacja schematu z `database/database.sql`)
- **backend** — kontener z aplikacją Spring Boot (REST API)
- **frontend** — kontener z Nginx serwującym zbudowaną aplikację React

---

## Uruchomienie aplikacji
Wymagania:
- Docker Desktop (Docker Engine + Docker Compose)

### 1) Konfiguracja środowiska
Projekt używa pliku `.env`. W repozytorium znajduje się plik przykładowy:

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

### 3) Dostęp do aplikacji
- **Frontend:** http://localhost:3000
- **Backend:** http://localhost:8080
- **MySQL:** `localhost:3306` (opcjonalnie, do debugowania)

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