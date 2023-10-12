# Planning

### Aufgaben

> Entwicklung mit **arc42-Template** dokumentieren -> Zur Abbleitung des Vortrags

**TODOs**

- [x] Konfigurationsobject Format definieren (.json)
- [x] Schnittstellendefinitionen
- [x] Extrahiere wiederverwendbare Komponenten aus urspruenglichem Projekt (Thomas)
- [x] Implementierung der Analyser Komponenten - Java, Notfall Python (Thomas)
- [x] Kafka (Florian)
- [x] UI + Griechisches Olivenoel(Florian)
- [x] Anforderungen durchgehen
- [x] Alle Technologien verstehen. (Austauschen)
- [x] Diskutieren: Crnkovic Komponentenmodell Framework/ MS Taxonomie anwenden?
- [x] Arch Smell und Anti Pattern: 5 priorisierte, davon 3 umgesetzte Loesungen aufzeigen
  - Erste Sammlung:
    - Independent deployability
    - Too many standards
    - Isolation of failures
    - Decentralization of business logic
    - Wrong Cut: Design Anti Pattern
    - Nano Services
    - Shared libraries (something we have with our 3 split, good for discussion)
    - Health check endpoint (UI regulary checks if services are up ? Is it necessary when using kafka?)
- [x] Praesentation erstellen
- [x] **5: Umsetzung Microservices**
- [x] **6.1: Erweiterung**
    
    Message/Queue (Kafka), Containerization (Docker), Circuit Breaker (Resilijence4j), FaaS (Knative)
- [x] **Persistierung**

    Ergebnisse (JSON/XML) in Datenbank (PostgreSQL) ablegen.

**Praesentation: (20 min)**

- [x] Fachliche Anforderung (Use Cases)
- [x] Software-Architektur - 4-Sichtenmodell
  - [x] UML
  - [x] Entwurfsentscheidungen kommentieren (arc42-template als Orientierung)
- [x] Code Walkthrough (relevante Passagen vorbereiten)
  - [x] Thomas Teil
- [x] Demonstration des Prototypen
- [x] Fazit: Lessons Learned, Ausblick, aktuelle Restriktionen

**ORGA**

- [x] Initialles Planning
- [x] Einteilung in Unteraufgaben & TODOs formulieren. 
  - (Potentiel ein wenig asynchron arbeiten moeglich)
  - Orientierung an **Umsetzung der Aufgabenblaetter**
- [x] Erstelle [arc42](https://arc42.org/overview) template [docs](./docs/arc42/)
- [x] Extraktion relevanter Kriterien aus Papern.
