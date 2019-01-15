# Code Änderungen um die DB über REST löschen zu können
## Client 
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

## Server

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
