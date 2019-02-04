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
            Booking booking = _uow.BookingRepository.Get().OrderBy(b => b.Id).Last();
            Company company = _uow.CompanyRepository.Get().OrderBy(c => c.Id).Last();

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

            _uow.AddressRepository.Delete(company.fk_Address);  

            _uow.BookingRepository.Delete(booking.Id);
            _uow.CompanyRepository.Delete(company.Id);

            var contacts = _uow.ContactRepository.Get().Where(c => c.Id == company.fk_Contact || c.Id == booking.fk_Contact);
            foreach (var item in contacts)
            {
                _uow.ContactRepository.Delete(item);
            }


            // TODO
            // Delete Presentation (+ PresentationBranch*)
            // [Delete ChangeProtocol*]

            _uow.Save();

        }
    }
}
