# Code änderungen (bei produktiven Einsatz wieder entfernen)
## Client 
 - app.component.html
``` html
<div class="fixed-bottom ml-2" style="width: 700px">
  <span class="bg-danger" style="color: #caff9c">
    very secret dev area:
    <a [routerLink]="['/admin-tool/login']">ADMIN TOOL</a> |
    <a [routerLink]="['/']">FIT</a> |
    <a [routerLink]="['/konto/login']">ACCOUNT</a> |
    <form (submit)="SendDbMigrateRequest()">
     <input [(ngModel)]="companyName" [ngModelOptions]="{standalone: true}" placeholder="Company">
      <input type="submit" value="Delete" class="btn-danger">
    </form>
  </span>
</div>
```

- app.component.ts
```ts
...
import {HttpClient} from '@angular/common/http';
import {AppConfig} from './core/app-config/app-config.service';
.
.
companyName: string = '';

public constructor(private applicationStateService: ApplicationStateService,
                    .
                    .                    
                    private http: HttpClient,
                    private appConfig: AppConfig) {
  }
.
.

// Temporal function for deleting a company
public SendDbMigrateRequest(): void {
    this.http.get('http://localhost:8181/api/deleteCompany/' + this.companyName).subscribe(data => {console.log(data); });
    console.log('Delete Requested');
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

`Src/Controllers/DeleteCompanyController` hinzugefügt  

```c#
namespace Backend.Src.Controllers
{
    [Route("api/deleteCompany")]
    [Controller]
    public class DeleteCompanyController : Controller
    {
        private IUnitOfWork _uow;

        public DeleteCompanyController(IUnitOfWork uow)
        {
            _uow = uow;
        }

        // To be removed
        // => Companies should only be deleted by name (and not the last element)
        [HttpGet]
        [ProducesResponseType(typeof(Company), StatusCodes.Status200OK)]
        public IActionResult DeleteLastCompany()
        {
            Booking booking = _uow.BookingRepository.Get().OrderBy(b => b.Id).Last();
            Company company = _uow.CompanyRepository.Get().OrderBy(c => c.Id).Last();

            //Deleting entities 
            if(booking.fk_Company == company.Id)
            {
                DeleteEntities(company, booking);
                _uow.Save();
            }

            return new OkObjectResult(company);
        }

        [HttpGet("{name}")]
        [ProducesResponseType(typeof(Company), StatusCodes.Status200OK)]
        public IActionResult DeleteCompanyByName(string name)
        {
            Company company = _uow.CompanyRepository.Get().SingleOrDefault(c => c.Name == name);
            if (company != null)
            {
                Booking booking = _uow.BookingRepository.Get().SingleOrDefault(b => b.fk_Company == company.Id);

                DeleteEntities(company, booking);
                _uow.Save();

                return new OkObjectResult(company);
            }

            return new BadRequestObjectResult(new
                {
                    errorMessage = $"Es wurde keine Firma mit dem Namen '{name}' gefunden.",
                });
        }

        /// <summary>
        /// Deletes database entries
        /// </summary>
        /// <param name="company">Company to delete</param>
        /// <param name="booking">Booking to delete</param>
        private void DeleteEntities(Company company, Booking booking)
        {
            Console.WriteLine("\nDeleting\n#------------------------------#");
            Stopwatch stopWatch = new Stopwatch();
            stopWatch.Start();

            var bookingBranches = _uow.BookingBranchesRepository.Get().Where(br => br.fk_Booking == booking.Id);
            Console.WriteLine($"BookingBranches: {bookingBranches.Count()}");
            foreach (var item in bookingBranches)
            {
                Console.WriteLine($"Id:{item.Id} BookingId:{item.Booking.Id} Branch:{item.Branch.Name}");
                _uow.BookingBranchesRepository.Delete(item);
            }

            var companyBranches = _uow.CompanyBranchRepository.Get().Where(cb => cb.fk_Company == company.Id);
            Console.WriteLine($"\nCompanyBranches: {companyBranches.Count()}");
            foreach (var item in companyBranches)
            {
                Console.WriteLine($"Id:{item.Id} Company:{item.Comapny.Name} Branch:{item.Branch.Name}");
                _uow.CompanyBranchRepository.Delete(item);
            }

            var resourceBooking = _uow.ResourceBookingRepository.Get().Where(rb => rb.fk_Booking == booking.Id);
            Console.WriteLine($"\nResourceBooking: {resourceBooking.Count()}");
            foreach (var item in resourceBooking)
            {
                Console.WriteLine($"Id:{item.Id} BookingId:{item.Booking.Id} Resource:{item.Resource.Name}");
                _uow.ResourceBookingRepository.Delete(item);
            }

            Console.WriteLine($"\nRepresentatives: {booking.Representatives.Count()}");
            foreach (var item in booking.Representatives)
            {
                Console.WriteLine($"Id:{item.Id} Name:{item.Name}");
                _uow.RepresentativeRepository.Delete(item);
            }

            Console.WriteLine($"\nAddress\nId:{company.Address.Id} - {company.Address.Street} {company.Address.ZipCode}\n");
            _uow.AddressRepository.Delete(company.fk_Address);

            Console.WriteLine($"Booking\nId:{booking.Id} Company:{booking.Company.Name}");
            _uow.BookingRepository.Delete(booking.Id);
            Console.WriteLine($"Company\nId:{company.Id} Name:{company.Name}");
            _uow.CompanyRepository.Delete(company.Id);


            var contacts = _uow.ContactRepository.Get().Where(c => c.Id == company.fk_Contact || c.Id == booking.fk_Contact);
            Console.WriteLine($"\nContacts: {contacts.Count()}");
            foreach (var item in contacts)
            {
                Console.WriteLine($"Id:{item.Id} Name:{item.FirstName} {item.LastName}");
                _uow.ContactRepository.Delete(item);
            }


            // TODO
            // Delete Presentation (+ PresentationBranch*)
            // [Delete ChangeProtocol*]

            stopWatch.Stop();
            TimeSpan ts = stopWatch.Elapsed;
            string elapsedTime = String.Format("{0:00}:{1:00}.{2:00}", ts.Minutes, ts.Seconds,
               ts.Milliseconds / 10);
            Console.WriteLine("...\nFinished\nTime elapsed: " + elapsedTime);

        }
    }
}

```
