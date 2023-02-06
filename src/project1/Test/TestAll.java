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
    static Scanner scanner = new Scanner(System.in);
    private AdminFacade adminFacade;
    private CompanyFacade companyFacade;
    private CustomerFacade customerFacade;

    public void TestAll() {
        try {
            Thread thread = new Thread(new CouponExpirationDailyJob());
            thread.start();
            login();
            if (adminFacade != null)
                System.out.println(adminFacade.getAllCompanies());
            thread.stop();
            ConnectionPool.getInstance().closeAllConnections();
        } catch (MyException | SQLException e) {
            System.out.println(e.getMessage());
        }
    }

    public void login() throws MyException, SQLException {
        System.out.print("1-Administrator\n2-Company\n3-Customer\n");
        System.out.print("please choose which client type you want to sign: ");
        int choose = scanner.nextInt();
        scanner.nextLine();
        System.out.print("please enter your email: ");
        String email = scanner.nextLine();
        System.out.print("please enter your password: ");
        String password = scanner.nextLine();
        switch (choose){
            case 1:
                adminFacade = (AdminFacade) LoginManager.getInstance().login(email,password,ClientType.Administrator);
                break;
            case 2:
                companyFacade = (CompanyFacade) LoginManager.getInstance().login(email,password,ClientType.Company);
                break;
            case 3:
                customerFacade = (CustomerFacade) LoginManager.getInstance().login(email,password,ClientType.Customer);
                break;
        }
    }
    public void allAdminFacade(){

    }
}

