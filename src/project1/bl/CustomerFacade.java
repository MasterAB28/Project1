package project1.bl;

import project1.Exception.MyException;
import project1.beans.Coupon;

import java.sql.SQLException;

public class CustomerFacade extends ClientFacade {
    private int customerID;

    public CustomerFacade(String email, String password) throws SQLException, MyException {
        if (login(email, password))
        this.customerID = customersDao.getCustomerIdByEmailAndPassword(email, password);

    }

    @Override
    public boolean login(String email, String password) throws MyException, SQLException {
        if (customersDao.isCustomerExists(email, password))
            return true;
        throw new MyException("login failed");

    }
    public void purchaseCoupon(Coupon coupon){

    }
}
