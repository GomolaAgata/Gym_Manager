# System obsługi sieci siłowni

System obsługi sieci siłowni wspiera klientów oraz pracowników w sprawnym korzystaniu z usług dostępnych w placówkach. System pozwala przeglądać i rezerwować zajęcia, odwoływać wcześniej zapisane treningi, rezerwować salę na potrzeby zaplanowanych zajęć, a także zgłaszać zapotrzebowanie na spersonalizowane plany treningowe. Celem systemu jest uproszczenie procesu obsługi klienta oraz usprawnienie organizacji pracy całej sieci siłowni.

## Funkcjonalności

- Przeglądanie dostępnych zajęć oraz ich harmonogramów  
- Rezerwacja zajęć oraz anulowanie wcześniej dokonanych rezerwacji za pomocą intuicyjnego GUI dostępnego w przeglądarce  
- Rezerwacja sal treningowych na zaplanowane zajęcia  
- Sprawdzanie aktywnych rezerwacji klienta  
- Obsługa różnych typów użytkowników (klienci, trenerzy, menedżerowie, recepcjoniści)  
- Automatyczne usuwanie danych o pracownikach z zakończonym okresem zatrudnienia  

## Interfejs użytkownika (GUI)

Projekt zawiera prosty interfejs graficzny dostępny przez przeglądarkę, umożliwiający:  

- Przeglądanie dostępnych zajęć w formie tabeli  
- Sprawdzanie szczegółów wybranego treningu  
- Rezerwację zajęć poprzez wprowadzenie danych osobowych (np. PESEL)  
- Anulowanie rezerwacji bezpośrednio z poziomu GUI  
- Wyświetlanie listy aktywnych rezerwacji użytkownika  

GUI komunikuje się z backendem za pomocą REST API, zapewniając szybkie i wygodne zarządzanie rezerwacjami.
---

## Technologie użyte w projekcie

- Java 17
- Spring Boot (Web, Data JPA)
- H2 Database (baza danych w pliku)
- Lombok
- Maven
- REST API
- HTML, CSS, JavaScript (frontend interfejsu)

---
