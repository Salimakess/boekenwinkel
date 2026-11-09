# Boekenwinkel – Application Architecture PRG2

## 1. Projectbeschrijving

Dit project is ontwikkeld voor het vak **Application Architecture PRG2** aan de Erasmushogeschool Brussel.

De applicatie stelt een online boekenwinkel voor. Gebruikers kunnen boeken bekijken, een account aanmaken, inloggen, boeken reserveren en reviews plaatsen.

Het project bestaat uit een **Spring Boot-backend** en een **HTML/JavaScript-frontend**.

## 2. Gebruikte technologieën

- Java en Spring Boot
- Spring Data JPA en Hibernate
- Spring Security
- H2-databank
- REST API
- Maven
- HTML, CSS en JavaScript
- Swagger / OpenAPI
- JUnit 5

## 3. Functionaliteiten

### Boekencatalogus
Boeken worden bijgehouden met ISBN, titel, auteur(s), omschrijving, prijs en voorraad. De backend biedt API-endpoints om boeken op te vragen en toe te voegen.

### Gebruikers
Gebruikers kunnen zich registreren en inloggen. Wachtwoorden worden gehasht met BCrypt.

### Reservaties en aankopen
Een gebruiker kan een boek reserveren. Bij de bevestiging van de reservatie wordt een aankoop geregistreerd en de voorraad verminderd.

### Aankoopgeschiedenis
De backend biedt een endpoint om de aankoopgeschiedenis van een gebruiker op te vragen.

### Reviews
Gebruikers kunnen een beoordeling van 1 tot 5 sterren en een tekstuele review toevoegen. Reviews kunnen per boek worden opgevraagd.

## 4. Projectstructuur

```text
boekenwinkel/
├── boekenwinkel-backend/
│   ├── src/
│   │   ├── main/
│   │   │   ├── java/be/ehb/boekenwinkel/
│   │   │   │   ├── config/
│   │   │   │   ├── controller/
│   │   │   │   ├── model/
│   │   │   │   └── repository/
│   │   │   └── resources/
│   │   └── test/
│   ├── pom.xml
│   └── mvnw.cmd
├── boekenwinkel-frontend/
│   └── index.html
└── README.md
```

## 5. Applicatie starten

### Backend

1. Open de map `boekenwinkel-backend` in IntelliJ IDEA.
2. Controleer of Java en Maven beschikbaar zijn.
3. Start `BoekenwinkelBackendApplication.java`.
4. De backend is bereikbaar via `http://localhost:8080`.
5. Swagger UI is bereikbaar via `http://localhost:8080/swagger-ui/index.html`.

### Frontend

Open `boekenwinkel-frontend/index.html` in een webbrowser.

De backend moet actief zijn om API-aanvragen vanuit de frontend te verwerken.

## 6. REST API

| Methode | Endpoint | Beschrijving |
|---|---|---|
| GET | `/api/books` | Boeken opvragen |
| POST | `/api/books` | Boek toevoegen |
| POST | `/api/users/register` | Registreren |
| POST | `/api/users/login` | Inloggen |
| GET | `/api/users/{id}/purchases` | Aankoopgeschiedenis |
| POST | `/api/reservations` | Boek reserveren |
| POST | `/api/reservations/{id}/confirm` | Reservatie bevestigen |
| POST | `/api/reviews` | Review toevoegen |
| GET | `/api/reviews/book/{isbn}` | Reviews per boek opvragen |

## 7. Testen

De backend bevat een JUnit-testklasse `BoekenwinkelBackendApplicationTests`.

Verschillende REST-endpoints zijn handmatig getest via Swagger UI, waaronder registreren, boeken reserveren, reservaties bevestigen en reviews toevoegen en opvragen.

Aanvullende geautomatiseerde tests zijn een mogelijk verbeterpunt.

## 8. Planning en werkwijze

Het project werd stapsgewijs ontwikkeld:

1. Analyse van de opdracht en bepaling van de functionaliteiten.
2. Aanmaken van het Spring Boot-project.
3. Ontwikkeling van de JPA-entiteiten en repositories.
4. Implementatie van REST-controllers.
5. Configuratie van Spring Security en H2.
6. Ontwikkeling van de frontend.
7. Testen van API-endpoints met Swagger.
8. Documentatie en publicatie op GitHub.

## 9. Architectuur

De applicatie gebruikt een gelaagde structuur:

- **Frontend:** gebruikersinterface in HTML en JavaScript.
- **Controllers:** verwerking van HTTP-verzoeken.
- **Models:** JPA-entiteiten voor boeken, auteurs, gebruikers, reservaties, aankopen en reviews.
- **Repositories:** databanktoegang via Spring Data JPA.
- **Database:** H2 voor ontwikkeling en testen.

De backend biedt REST-endpoints waarmee de frontend kan communiceren.

## 10. Gebruik van AI-tools

Tijdens de ontwikkeling werd **ChatGPT (OpenAI)** gebruikt als ondersteunend hulpmiddel voor uitleg over Spring Boot, het oplossen van programmeerfouten, het opstellen van voorbeeldcode, het testen van REST-endpoints en het verbeteren van documentatie.

De gegenereerde voorstellen werden tijdens de ontwikkeling toegepast en handmatig gecontroleerd.

## 11. Mogelijke verbeteringen

- Meer geautomatiseerde tests toevoegen.
- Alle backendfunctionaliteiten integreren in de frontend.
- De beveiliging verder uitwerken voor productiegebruik.
- Een permanente databank gebruiken in plaats van een in-memory H2-databank.
- Extra validatie en foutafhandeling toevoegen.

## 12. Opleiding

**Erasmushogeschool Brussel**  
Graduaat Programmeren – Avondtraject  
Vak: Application Architecture PRG2  
Academiejaar: 2026–2027
