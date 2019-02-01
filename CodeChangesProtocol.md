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
        private IUnitOfWork _uow;

        public DbResetController(IUnitOfWork uow)
        {
            _uow = uow;
        }

        [HttpGet]
        public void DeleteDbRequest()
        {
            Booking booking = _uow.BookingRepository.Get().Last();
            Company company = _uow.CompanyRepository.Get().Last();

            var bookingBranches = _uow.BookingBranchesRepository.Get().Where(br => br.fk_Booking == booking.Id);
            foreach (var item in bookingBranches)
            {
                _uow.BookingBranchesRepository.Delete(item);
            }

            var companyBranch = _uow.CompanyBranchRepository.Get().Where(cb => cb.fk_Company == company.Id);
            foreach (var item in companyBranch)
            {
                _uow.CompanyBranchRepository.Delete(item);
            }

            var resourceBooking = _uow.ResourceBookingRepository.Get().Where(rb => rb.fk_Booking == booking.Id);
            foreach (var item in resourceBooking)
            {
                _uow.ResourceBookingRepository.Delete(item);
            }

            foreach (var item in booking.Representatives)
            {
                _uow.RepresentativeRepository.Delete(item);
            }


            var contacts = _uow.ContactRepository.Get().Where(c => c.Id == company.fk_Contact);
            foreach (var item in contacts)
            {
                _uow.ContactRepository.Delete(item);
            }

            _uow.AddressRepository.Delete(company.fk_Address);

            _uow.BookingRepository.Delete(booking.Id);
            _uow.CompanyRepository.Delete(company.Id);


            // TODO
            // Delete Presentation (+ PresentationBranch*)
            // [Delete ChangeProtocol*]

            _uow.Save();

        }
    }
}

```
