package project1.Test;

import project1.Exception.MyException;
import project1.beans.Company;
import project1.bl.AdminFacade;
import project1.bl.CompanyFacade;
import project1.bl.CustomerFacade;
import project1.login.ClientType;
import project1.login.LoginManager;
import project1.threads.CouponExpirationDailyJob;

import java.sql.SQLException;
import java.util.Scanner;


public class TestAll {
    static Scanner scanner = new Scanner(System.in);
    public TestAll(){
        try {
            Thread thread = new Thread(new CouponExpirationDailyJob());
            thread.start();
            AdminFacade adminFacade = (AdminFacade) LoginManager.getInstance().login("admin@admin.com", "admin", ClientType.Administrator);
//          adminFacade.addCompany();
//            adminFacade.updateCompany();
//            adminFacade.deleteCompany();
//            adminFacade.getAllCompanies();
//            adminFacade.getOneCompany();
//            adminFacade.addCustomer();
//            adminFacade.updateCustomer();
//            adminFacade.deleteCustomer();
//            adminFacade.getAllCustomers();
//            adminFacade.getOneCustomer();
            CompanyFacade companyFacade = (CompanyFacade) LoginManager.getInstance().login("ddd","ddd",ClientType.Company);
//            companyFacade.addCoupon();
//            companyFacade.updateCoupon();

            CustomerFacade customerFacade = (CustomerFacade) LoginManager.getInstance().login("dd","dd",ClientType.Customer);
        } catch (MyException | SQLException e) {
            throw new RuntimeException(e);
        }
    }
}
