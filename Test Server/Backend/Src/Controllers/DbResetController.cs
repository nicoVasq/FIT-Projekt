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
namespace Backend.Src.Controllers
{
    [Route("api/[resetdb]")]
    public class DbResetController
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
