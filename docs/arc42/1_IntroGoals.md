## Requirements

> Focus: Manufacturing Products

| Goal | Describtion |
| -- | -- |
| Eigentstaendige  Algorithmus-Komponente fuer jedes optional Equipment | |
| Choreographie der Alg. Komponenten | Alg. laufen nebenlaeufig in einer Choreographie. Wenn die aktuelle Alg. Komp. fertig ist, ruft diese die naechste auf. |
| Status der Alg.Komp. einsehbar ||
| Retry einer Alg.Komp moeglich ||
| Konfiguration des opt. Equipment ueber Eingabe Maske (optional separater MicroService) ||
| Persistierung der aktuellen Konfiguration im MS ||
| Button zum starten der Analyse ||
| Alg. Komp. ueber REST-Endpoint aufrufbar | Lediglich simulation zb 5 sec sleep|
| Insgesamt 4 verschiedene Alg. Komp. | Clustern der zustaendigkeiten|
| Input der Algorithmen: gesamtes Konfigurationsobject | AnalKomp nehmen sich ihren Teil selbst raus.|
| Resultat der Analyse speichern & anzeigen.  ok/failed | Zb als Eintrag in das Konfigurations-Objekt. Alternativen denkbar |
| Status aller AnalKomp zu jedem Zeitpunkt entnehmbar | „running“, „failed“, „ready“, „not started“ |
| Circuit Breaker Pattern fuer jeden MS | Einzelnen MS sollen zufaelligen Ausfall simulieren. Die Ansprache dieser Komponenten soll ueber den Circuit Breaker (CircBreak) erfolgen. |
| Einheitliche Schnittstellendefinition der MS ||
| Benutzeroberflaeche fuer Konfiguration und Start-Button | Technologien: Vaadin|
| Monitoring | Integration eines Monitoring Frameworks. zb Prometheus. Hier nutzen wir Florians Bachelorarbeit.|
| Aktuelle Modellierung der Software Architektur 4Sichten Modell | ggf. Aenderunge zu Aufgabe 5 hervorheben.|
| 5 Loesungen von AntiPatterns & Arch.Smells benennen & 3 in das Projekt einbauen. | |


__mindestens_1__

| Goal | Describtion |
| -- | -- |
| Containerisierung der MS durch Docker | |
| Kafka Event Streaming| MS rufen sich gegenseitig ueber EventStreaming auf. Uebermittlung der Bearbeitungsstatus Nachrichten.|
| Serverless Functions Knative | |
| Spring-Netflix-Stack | Integration von **Service Registry** & **Circuit Breaker**


## Empfehlungen

- Spring Boot fuer MS Entwicklung
- Resilience4J fuer CircBreak (optional selber)



## Quality goals

- Individualle Skallierung
- Ausfallsicherheit
- Monitoring