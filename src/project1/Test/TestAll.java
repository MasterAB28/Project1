package project1.Test;

import project1.Exception.MyException;
import project1.bl.AdminFacade;
import project1.bl.CompanyFacade;
import project1.bl.CustomerFacade;
import project1.db.ConnectionPool;
import project1.login.ClientType;
import project1.login.LoginManager;
import project1.threads.CouponExpirationDailyJob;

import java.sql.SQLException;
import java.util.Scanner;


public class TestAll {


    public void testAll() {
            CouponExpirationDailyJob job=new CouponExpirationDailyJob();
            Thread thread = new Thread(job);
            thread.start();
            new TestAdmin().runAllAdminFacadeTest();
            new TestCompany().runAllCompanyFacadeTest();
            new TestCustomer().runAllCustomerFacadeTest();
            job.stop();
            ConnectionPool.getInstance().closeAllConnections();
    }
}

