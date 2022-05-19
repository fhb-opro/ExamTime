#Example Exam OPRO
###13. Mai 2021

Duration 2:50h

Reading Time: 5 min

Question Time: 10 min 

Open book Prüfung

Bei Fragen während der Prüfung: bitte den Chat benutzen! 

Achten Sie auf
* korrekte Formatierung
* Klammernsetzung
* Java-konforme Namensgebung (!) 
* passende Packages.



##Zu entwickeln ist folgende Anwendung 

###Ziel
Die Anwendung hat zum Ziel, für Prüfungen in verschiedenen Fächern den Fragenkatalog zu verwalten und abzufragen, sowie die Ergebnisse zu evaluieren.

Die Hauptuser sind die Studierenden.

Die Menüführung obliegt Ihren eigenen Entscheidungen.

###Anforderungen
Verwenden Sie die vorgegebenen Files. Dort befinden sich einerseits je 4 Prüfungsfragen für gewisse Fächer inkl der richtigen Antwort (ja/nein) und andererseits Lehrperson/Fächer Kombinationen.
Es müssen immer genau 4 Fragen sein.

Das Fach wird durch den:die Student:in, welche:r sich vorher mit Vor- und Nachnamen anmeldet, eingegeben. Die entsprechende Lehrperson wird in das Prüfungszimmer gebeten. Hernach werden dem Prüfling die Fragen aus dem Fach gestellt. Jede Frage wird mit einem Punkt bewertet. Am Ende wird ausgewertet, ob der:die Student:in bestanden hat. Bestehensgrenze: 50 % der Punkte. Die Prüfung kann pro Programmlauf und Student:in nur einmal abgelegt werden. Der:Die Student:in kann eine weitere Prüfung aus einem anderen Fach ablegen.
Nach max. 2 Prüfungen pro Student:in wird der Durchlauf beendet. 
Nach der Auswertung und Ausgabe des Ergebnisses wird die Applikation beendet.

###Annahmen: 
* Sie können, wenn Sie möchten, davon ausgehen, dass ein:e Student:in die Liste an möglichen Fächern kennt.



###Verpflichtend:
* Lauffähiges Programm! DH, keine Abstürze zu Beginn des Programms. Andernfalls negativ. 
* Pfade müssen relativ sein (dh, resource-package!)
* Exception Handling: Keine unerwarteten Exception (IOException, die auf Dateisystemfehlern (!) beruhen, InterruptedException dürfen weitergeworfen werden, da Sie dagegen nichts machen können). -5 Punkte pro ungehandelter Exception!
* passende Menüführung für die Applikation
* Anwendung der gelernten CC-Rules
* Verwenden von Interfaces, wo möglich und sinnvoll!
* Design Patterns (wo anwendbar)
* Methoden Javadoc (1-2 kurze Sätze als Beschreibung, Übergabeparameter und return wert). - 5 Punkte wenn nicht gegeben

Angabe der evtl verwendeten Quellen


### Extra Punkte (max insgesamt 100 Punkte auf die Prüfung)
* Unit tests für die Codebasis - max 10 Punkte
* Logging mit Unterscheidung der Loglevels - 2 Punkte 
* Es gibt ein File, welches Bestehensgrenze, guten Erfolg und Auszeichnung (nicht jedes Fach hat jede Stufe!) für das jeweilige Fach festhält. Sollten Sie dies einbauen - 7 Punkte
