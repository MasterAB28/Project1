package project1.bl;

import project1.Exception.MyException;
import project1.dao.*;

import java.sql.SQLException;

public abstract class ClientFacade {
    protected CompaniesDao companiesDao = new CompaniesDaoImpl();
    protected CustomersDao customersDao = new CustomersDaoImpl();
    protected CouponsDao couponsDao = new CouponsDaoImpl();

    public abstract boolean login (String email,String password) throws MyException, SQLException;
}
