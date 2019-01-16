# FIT-Projekt

In unserem Schulprojekt werden wir die Firmen-Informations-Tags Seite Testen um Fehler bei den Kunden zu vermeiden.


### Wir testen das Projekt mit einem ausenstehenden Java Selenium Programm

<img src="Images/FitWebsite_Simple.png" />

## Setup 
Für die Installationen wird der **[Node Package Manager](https://nodejs.org/en/download/)** benötigt 
### Client (Angular Applikation)
Die benötigten Dependencies bekommen:\
`npm install`  
Client starten:\
`ng serve`

### Server  (Visual Studio Solution)
Wenn Datenbank nicht aktuell, `Update-Database` in der NuGet Package Manager Console ausführen
- Im Startup.cs muss **InitDb(provider);** auskommentiert sein damit das Update-Command
funktioniert. Danach wieder entkommentieren damit Testdaten erstellt werden.
- wenn Model nicht up-to-date zuvor noch eine Migration erstellen: `Add-Migration`

In Visual Studio oben **Backend** auswählen und ausführen.

### [Protractor](https://www.protractortest.org/#/) 
Protractor is ein end-to-end testing Framework für Angular/Angular JS Applikationen.\
Wir benützt die Syntax von dem [Jasmine](https://jasmine.github.io/) Framework.
- **Protractor-Setup:**
    
    Mit **[Npm (+Node JS)](https://nodejs.org/en/download/)** Protractor + Jasmine installieren:
    ```
    # Local installation:
    npm install --save-dev protractor

    #Global installation:
    npm install -g protractor
    ```
    ```
    npm install --save @types/jasminewd2
    ```
    **[Selenium](https://www.seleniumhq.org/)** standalone server (wird von Protractor benötigt):
    ```
    webdriver-manager update
    ```
    Benötigte Library:
    ```
    npm install --save @types/core-js
    ```

Tests werden ausgeführt mit `protractor protractor.conf.js`

### Selenium Client

### Wir programmieren mit ...
<img src="Images/protractor.png" /> <img src="Images/Selenium-Logo.png" />

### Plakat

<img src="Images/Plakat.png" />
