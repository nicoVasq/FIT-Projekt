# FIT-Projekt

In unserem Schulprojekt werden wir die Firmen-Informations-Tags Seite Testen um Fehler bei den Kunden zu vermeiden.
Hierbei wird automatisiert getestet (mit Selenium - Regressionstest), genauso wie manuell,da es sich bei manchen Use-Cases nicht lohnt sie zu automatisieren (Beispielsweise Admintool) 


### Wir testen das Projekt mit einem ausenstehenden Java Selenium Programm

<img src="Images/FitWebsite_Simple.png" />

## :gear: Setup 
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

## Anderungsprotokoll


### Client 
 - app.component.html
```html
<button (click)="SendDbMigrateRequest()">Reset DB</button>
```

- app.component.ts
```ts
...
import {HttpClient} from '@angular/common/http';
import {AppConfig} from './core/app-config/app-config.service';
.
.

public constructor(private applicationStateService: ApplicationStateService,
                    .
                    .                    
                    private http: HttpClient,
                    private appConfig: AppConfig) {
  }
.
.

public SendDbMigrateRequest(): void {
    this.http.get('http://localhost:8181/api/resetdb').subscribe(data => {
        console.log(data); });
    console.log('Delete Requested');
}
```

- app.module.ts
```ts
...
import { HttpClientModule } from '@angular/common/http';

.
.
@NgModule({
  imports: [
    ...,
    HttpClientModule
    ...
```

### Server

`Src/Controllers/DbResetController.cs` hinzugefügt  
~~Reset.cs~~


```c#
namespace Backend.Src.Controllers
{
    [Route("api/resetdb")]
    [Controller]
    public class DbResetController : Controller
    {
        private IUnitOfWork _unitOfWork;

        public DbResetController(IUnitOfWork uow)
        {
            _unitOfWork = uow;
        }

        [HttpGet]
        public void DeleteDbRequest()
        {
            _uow.ContactRepository.Delete(_uow.ContactRepository.Count());
            _uow.AddressRepository.Delete(_uow.AddressRepository.Count());
            _uow.CompanyBranchRepository.Delete(_uow.CompanyBranchRepository.Count());
            _uow.CompanyRepository.Delete(_uow.CompanyRepository.Count());

            _uow.Save();
        }
    }
}
```


## Wir programmieren mit ...
<img src="Images/protractor.png" /> <img src="Images/Selenium-Logo.png" />

## :art: Plakat

<img src="Images/Plakat.png" />
