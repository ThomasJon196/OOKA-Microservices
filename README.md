# Migration einer monolithischen Architektur auf Microservices

- [Migration einer monolithischen Architektur auf Microservices](#migration-einer-monolithischen-architektur-auf-microservices)
  - [Semesterprojekt](#semesterprojekt)
    - [Aufgaben](#aufgaben)
    - [weitere Infos](#weitere-infos)
  - [Projekt Struktur](#projekt-struktur)
  - [Projekt Setup](#projekt-setup)
  - [Deployment](#deployment)

## Semesterprojekt

Florian Weber & Thomas Jonas


### Aufgaben

> Entwicklung mit **arc42-Template** dokumentieren -> Zur Abbleitung des Vortrags

**ORGA**

- [x] Initialles Planning
- [ ] Einteilung in Unteraufgaben & TODOs formulieren. 
  - (Potentiel ein wenig asynchron arbeiten moeglich)
  - Orientierung an **Umsetzung der Aufgabenblaetter**
- [ ] Erstelle [arc42](https://arc42.org/overview) template [docs](./docs/arc42/)
- [ ] Extraktion relevanter Kriterien aus Papern.


**TODOs**

- [ ] Extrahiere wiederverwendbare Komponenten aus urspruenglichem Projekt
- [ ] ...


**HIGH-LEVEL**

- [ ] **4: Modellierung MS Architektur**
- [ ] **5: Umsetzung Microservices**
- [ ] **6.1: Erweiterung**
    
    Message/Queue (Kafka), Containerization (Docker), Circuit Breaker (Resilijence4j), FaaS (Knative)
- [ ] **Persistierung**

    Ergebnisse (JSON/XML) in Datenbank (PostgreSQL) ablegen.

**Praesentation: (20 min)**

- [ ] Fachliche Anforderung (Use Cases)
- [ ] Software-Architektur - 4-Sichtenmodell
  - [ ] UML
  - [ ] Entwurfsentscheidungen kommentieren (arc42-template als Orientierung)
- [ ] Code Walkthrough (relevante Passagen vorbereiten)
- [ ] Demonstration des Prototypen
- [ ] Fazit: Lessons Learned, Ausblick, aktuelle Restriktionen


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

- Wie startet man das Projekt? ggf. Scripte schreiben.