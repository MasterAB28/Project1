package project1;

import project1.Exception.MyException;
import project1.beans.Company;
import project1.beans.Customer;
import project1.bl.AdminFacade;
import project1.bl.CompanyFacade;
import project1.bl.CustomerFacade;
import project1.login.ClientType;
import project1.login.LoginManager;

import java.sql.SQLException;

public class Program {
    public static void main(String[] args) throws SQLException, MyException {
        try {
            CompanyFacade companyFacade = (CompanyFacade) LoginManager.getInstance().login("ad",
                    "a", ClientType.Company);
            if (companyFacade != null)
                System.out.println("success");
            else
                System.out.println("failed");
        } catch (MyException | SQLException e) {
            System.out.println(e.getMessage());
        }
    }
}
