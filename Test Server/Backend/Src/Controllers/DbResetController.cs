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
            _uow.ContactRepository.Delete(_uow.ContactRepository.Count());
            _uow.AddressRepository.Delete(_uow.AddressRepository.Count());
            _uow.CompanyBranchRepository.Delete(_uow.CompanyBranchRepository.Count());
            _uow.CompanyRepository.Delete(_uow.CompanyRepository.Count());

            _uow.Save();
            //_uow.DeleteDatabase();


        }
    }
}
