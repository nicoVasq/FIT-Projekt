using Backend.Core.Contracts;
using Backend.Core.Entities;
using Microsoft.AspNetCore.Authorization;
using Microsoft.AspNetCore.Http;
using Microsoft.AspNetCore.Mvc;
using System;
using System.Collections.Generic;
using System.Linq;
using System.Net.Http;
using System.Reflection;
using System.Threading.Tasks;
using Microsoft.AspNetCore;
using System.Diagnostics;

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
