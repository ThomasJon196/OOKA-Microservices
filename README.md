# Migration einer monolithischen Architektur auf Microservices

- [Migration einer monolithischen Architektur auf Microservices](#migration-einer-monolithischen-architektur-auf-microservices)
  - [Semesterprojekt](#semesterprojekt)
    - [Aufgaben](#aufgaben)
    - [weitere Infos](#weitere-infos)
  - [Projekt Struktur](#projekt-struktur)
  - [Projekt Setup](#projekt-setup)
  - [Deployment](#deployment)
  - [Lessons learned](#lessons-learned)
  - [Aussicht](#aussicht)
  - [Verbesserungsmoeglichkeiten](#verbesserungsmoeglichkeiten)

## Semesterprojekt

Florian Weber & Thomas Jonas


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
- [ ] Alle Technologien verstehen. (Austauschen)
- [ ] Diskutieren: Crnkovic Komponentenmodell Framework/ MS Taxonomie anwenden?
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
- [ ] Praesentation erstellen
- [ ] Handout erstellen HOCHLADEN!

**HIGH-LEVEL**

- [x] **4: Modellierung MS Architektur**
- [x] **5: Umsetzung Microservices**
- [x] **6.1: Erweiterung**
    
    Message/Queue (Kafka), Containerization (Docker), Circuit Breaker (Resilijence4j), FaaS (Knative)
- [ ] **Persistierung**

    Ergebnisse (JSON/XML) in Datenbank (PostgreSQL) ablegen.

**Praesentation: (20 min)**

- [x] Fachliche Anforderung (Use Cases)
- [ ] Software-Architektur - 4-Sichtenmodell
  - [ ] UML
  - [x] Entwurfsentscheidungen kommentieren (arc42-template als Orientierung)
- [x] Code Walkthrough (relevante Passagen vorbereiten)
  - [x] Thomas Teil
- [ ] Demonstration des Prototypen
- [ ] Fazit: Lessons Learned, Ausblick, aktuelle Restriktionen

**ORGA**

- [x] Initialles Planning
- [x] Einteilung in Unteraufgaben & TODOs formulieren. 
  - (Potentiel ein wenig asynchron arbeiten moeglich)
  - Orientierung an **Umsetzung der Aufgabenblaetter**
- [x] Erstelle [arc42](https://arc42.org/overview) template [docs](./docs/arc42/)
- [x] Extraktion relevanter Kriterien aus Papern.



### weitere Infos

- **Bis 10.09** Thema und Gruppe mitteilen -> Alda
- **Sprechstunde** Do 12-14 Uhr (außer: 01.08. bis 25.8.23) 


## Projekt Struktur

Beispielhaft:

- kafka_stream_visualizer: Kafka Visualisierung aus Florians Bachelorarbeit
- microservice_architecture: Microservice-Migration der WirSchaffenDas Ausgangsarchitektur
- kafka
- (docker)

*Um den Ueberblick zu behalten*

## Projekt Setup

- Requirements zum Ausfuehren. zb Java Version
- Installationsanleitung. zb. JDK oder Docker

## Deployment

0. Connect to HBRS-VPN to enable access to the kafka-cluster.
1. Execute services in seperate shells/processes.
2. Start kafka-consumer in each algorithm component.
3. Navigate to GUI and start the analysis.


1. Start services

```bash
# In each alg_comp directory run:
./gradlew bootRun

# Deploy analysis GUI
bash ./mvnw

```

2. 

To start the kafka-consumer in each algorithm component,\
their `/startConsuming`-endpoint has to be called explicitly.

```bash
# Check if server is up
curl 'http://localhost:8081/healthCheck'

# Start consuming messages from kafka topic
curl 'http://localhost:8081/startConsumingKafka'
```


## Lessons learned

- Kotlin-Gradle
  - REST Server
  - Data Classes and DB support


## Aussicht

## Verbesserungsmoeglichkeiten

- Persistierung der Konfiguration