package project1.login;

import project1.Exception.MyException;
import project1.bl.AdminFacade;
import project1.bl.ClientFacade;
import project1.bl.CompanyFacade;
import project1.bl.CustomerFacade;

import java.sql.SQLException;

public class LoginManager {

    private static LoginManager instance;

    private LoginManager() {
    }

    public synchronized static LoginManager getInstance() {
        if (instance==null)
            instance = new LoginManager();
        return instance;
    }
    /**
     * The method receives client's email and password and type and checks if the email and password are correct,
     * by method 'facade.login' from the bl
     * @param email client's email
     * @param password client's password
     * @param password client's type
     * @return facade
     * @throws MyException if the login failed
     * @throws SQLException if the sql method is not working properly
     */
    public ClientFacade login(String email, String password, ClientType clientType) throws MyException, SQLException {
        switch (clientType){
            case Administrator:
                AdminFacade adminFacade = new AdminFacade();
                if (adminFacade.login(email,password)) {
                    return adminFacade;
                }
            case Company:
                CompanyFacade companyFacade = new CompanyFacade();
                if (companyFacade.login(email,password))
                    return companyFacade;
            case Customer:
                CustomerFacade customerFacade = new CustomerFacade();
                if (customerFacade.login(email,password))
                    return customerFacade;
        }
        return null;
    }
}
