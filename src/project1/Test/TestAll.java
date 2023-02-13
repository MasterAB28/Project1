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
        try {
            CouponExpirationDailyJob job = new CouponExpirationDailyJob();
            job.start();
            new TestAdmin().runAllAdminFacadeTest();
            new TestCompany().runAllCompanyFacadeTest();
            new TestCustomer().runAllCustomerFacadeTest();
            job.setQuit(true);
            job.stop();
            ConnectionPool.getInstance().closeAllConnections();
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }


    }
}

