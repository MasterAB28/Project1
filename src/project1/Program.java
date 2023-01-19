package project1;

import com.mysql.cj.x.protobuf.MysqlxNotice;
import project1.Exception.MyException;
import project1.beans.Category;
import project1.beans.Company;
import project1.beans.Coupon;
import project1.beans.Customer;
import project1.bl.AdminFacade;
import project1.bl.CompanyFacade;
import project1.bl.CustomerFacade;
import project1.dao.CompaniesDaoImpl;
import project1.dao.CouponsDaoImpl;
import project1.dao.CustomersDaoImpl;

import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Date;

public class Program {
    public static void main(String[] args) throws SQLException, MyException {
        CompaniesDaoImpl companiesDao = new CompaniesDaoImpl();
        AdminFacade adminFacade = new AdminFacade();
        CouponsDaoImpl couponsDao = new CouponsDaoImpl();
        try{
//            couponsDao.deleteCouponPurchase(3,12);
            CustomerFacade customerFacade = new CustomerFacade();
            customerFacade.login("gg","1234");
            customerFacade.purchaseCoupon(couponsDao.getOneCoupon(10));
        } catch (MyException e) {
            System.out.println(e.getMessage());
        }


    }
}
