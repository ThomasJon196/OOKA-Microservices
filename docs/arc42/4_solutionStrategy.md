| Goal | Scenario |	Solution approach | Link to Details|
| -- | -- | -- | -- |
| Eigentstaendige  Algorithmus-Komponente fuer jedes optional Equipment | | Implementierung als einzelne MS mittels Springboot. |
| Insgesamt 4 verschiedene Alg. Komp. | Clustern der zustaendigkeiten. Jedes Kategorie bildet ein Topic in Kafka|  |
| Choreographie der Alg. Komponenten | Alg. laufen nebenlaeufig in einer Choreographie. Wenn die aktuelle Alg. Komp. fertig ist, ruft diese die naechste auf. | Durch Eventstreaming mittels Kafka nehmen sich MS die bereit sind eine neue Aufgabe. Parallele Abarbeitung. |
| Retry einer Alg.Komp moeglich || Die Konfiguration in das ausgewaehlte Topic geschoben. |
| Konfiguration des opt. Equipment ueber Eingabe Maske (optional separater MicroService) || UI wird in Vaddin implementiert. |
| Persistierung der aktuellen Konfiguration im MS | Im Frontend Service selbst ? Wie funktioniert das wenn der browser neu geladen wird?| Client speichert die aktuelle Konfiguration selbst. |
| Button zum starten der Analyse || UI enthaelt Submit Button. Dieser schiebt die Konfiguration in alle Kafka Topics. |
| Alg. Komp. ueber REST-Endpoint aufrufbar | Lediglich simulation zb 5 sec sleep. | Alg.Komp. sind Kafka Consumer und holen sich selber ihre Aufgaben. REST Endpoint nur zur abfrage des Status.|
| Input der Algorithmen: gesamtes Konfigurationsobject | AnalKomp nehmen sich ihren Teil selbst raus.| Format spezifizieren |
| Resultat der Analyse speichern & anzeigen.  ok/failed | Zb als Eintrag in das Konfigurations-Objekt. Alternativen denkbar: "Resupt" Topic in Kafka, ueber welche die AlgKomp die Resultate liefern. Woher weiß das Frontend dass das Resultat fuer dieses gedacht ist? Sessions? Ist das eine Anforderung?| Mit Status kombiniert. Konfigurationsobjekt enthaelt eine Anfrage-ID um bezug zu Client herzustellen.|
| Status der Alg.Komp. einsehbar | Endpunkt zur Abfrage des Status.| Nutzen eine Kafka Topic um Status & Resultat zusammenzufassen. |
| Status aller AnalKomp zu jedem Zeitpunkt entnehmbar | „running“, „failed“, „ready“, „not started“ |
| Circuit Breaker Pattern fuer jeden MS | Einzelnen MS sollen zufaelligen Ausfall simulieren. Die Ansprache dieser Komponenten soll ueber den Circuit Breaker (CircBreak) erfolgen. Dies muss nicht Teil des UI sein. Lediglich ansprache ueber http ermoeglichen.| Circuit breaker wird vor den Kafka Service geschlatet|
| Benutzeroberflaeche fuer Konfiguration und Start-Button | Technologien: Vaadin| Fast fertig. (Florian) Muss noch angepasst werden damit der Kafka Service (bzw Circ.Breaker) angesprochen wird.|
| Monitoring | Integration eines Monitoring Frameworks. zb Prometheus. Hier nutzen wir Florians Bachelorarbeit.| Florians Bachelorarbeit |
| Aktuelle Modellierung der Software Architektur 4Sichten Modell | ggf. Aenderunge zu Aufgabe 5 hervorheben.|
| Einheitliche Schnittstellendefinition der MS ||
| 5 Loesungen von AntiPatterns & Arch.Smells benennen & 3 in das Projekt einbauen. | |