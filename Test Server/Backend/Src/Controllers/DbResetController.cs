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
        private IUnitOfWork _unitOfWork;

        public DbResetController(IUnitOfWork uow)
        {
            _unitOfWork = uow;
        }

        [HttpGet]
        public void DeleteDbRequest()
        {

            _unitOfWork.DeleteDatabase();


        }
    }
}
