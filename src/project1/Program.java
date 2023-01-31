package project1;

import project1.Exception.MyException;
import project1.beans.Category;
import project1.beans.Company;
import project1.beans.Coupon;
import project1.beans.Customer;
import project1.bl.AdminFacade;
import project1.bl.CompanyFacade;
import project1.bl.CustomerFacade;
import project1.login.ClientType;
import project1.login.LoginManager;
import project1.threads.CouponExpirationDailyJob;

import java.sql.Date;
import java.sql.SQLException;

public class Program {
    public static void main(String[] args) throws SQLException, MyException {
        try {
            CouponExpirationDailyJob couponExpirationDailyJob = new CouponExpirationDailyJob();
            Thread thread = new Thread(couponExpirationDailyJob);
            thread.start();
            CompanyFacade companyFacade = (CompanyFacade) LoginManager.getInstance().login("ad",
                    "ad", ClientType.Company);
            if (companyFacade != null) {
                System.out.println("success to login");
//                companyFacade.addCoupon(new Coupon(5, Category.FOOD,"dd",
//                        "ddd", Date.valueOf("2023-01-20"),Date.valueOf("2023-02-01"),12,12.5,null));
//                companyFacade.addCoupon(new Coupon(5, Category.FOOD,"ddddd",
//                        "ddd", Date.valueOf("2023-01-20"),Date.valueOf("2023-01-30"),12,12.5,null));
                System.out.println(companyFacade.getCompanyCoupons());
                System.out.println(companyFacade.getCompanyCoupons());
            }

        } catch (MyException | SQLException e) {
            System.out.println(e.getMessage());

        }
    }
}
