package project1;

import project1.Exception.MyException;
import project1.beans.Company;
import project1.beans.Customer;
import project1.bl.AdminFacade;
import project1.bl.CustomerFacade;
import project1.login.ClientType;
import project1.login.LoginManager;

import java.sql.SQLException;

public class Program {
    public static void main(String[] args) throws SQLException, MyException {
        AdminFacade adminFacade = (AdminFacade) LoginManager.getInstance().login("admin@admin.com",
                "admin", ClientType.Administrator);


    }
}
