package project1;

import project1.Exception.MyException;
import project1.beans.Category;
import project1.beans.Company;
import project1.beans.Coupon;
import project1.beans.Customer;
import project1.bl.AdminFacade;
import project1.dao.CompaniesDaoImpl;
import project1.dao.CouponsDaoImpl;
import project1.dao.CustomersDaoImpl;

import java.sql.Date;
import java.sql.SQLException;
import java.util.ArrayList;

public class Program {
    public static void main(String[] args) throws SQLException, MyException {
        CompaniesDaoImpl companiesDao = new CompaniesDaoImpl();
        AdminFacade adminFacade = new AdminFacade();
        CouponsDaoImpl couponsDao = new CouponsDaoImpl();
        adminFacade.deleteCompany(1);


    }
}
