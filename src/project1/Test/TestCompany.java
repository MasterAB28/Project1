package project1.Test;

import project1.Exception.MyException;
import project1.beans.Category;
import project1.beans.Company;
import project1.beans.Coupon;
import project1.bl.AdminFacade;
import project1.bl.CompanyFacade;
import project1.login.ClientType;
import project1.login.LoginManager;

import java.sql.Date;
import java.sql.SQLException;
import java.util.List;

public class TestCompany {
    private CompanyFacade companyFacade;
    public void runAllCompanyFacadeTest() {
        login();
        if (companyFacade != null) {
//           addCoupon();
//            updateCoupon();
//            deleteCoupon();
//            getCompanyCoupons();
//            getCompanyCouponsByCategory();
//            getCompanyCouponsByMaxPrice();
//            getCompanyDetails();
        }
    }
        


    private void getCompanyDetails() {
        System.out.println("📢get company details📢");
        try {
            System.out.println(companyFacade.getCompanyDetails());
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }

    private void getCompanyCouponsByMaxPrice() {
        System.out.println("📢get company coupons by maximum price📢");
        try {
            System.out.println(companyFacade.getCompanyCoupons(7.90));
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        
    }

    private void getCompanyCouponsByCategory() {
        System.out.println("📢get company coupons by category📢");
        try {
            System.out.println(companyFacade.getCompanyCoupons(Category.SPORT));
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }


    }

    private void getCompanyCoupons() {
        System.out.println("📢get company coupons📢");
        try {
            System.out.println(companyFacade.getCompanyCoupons());
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }

    private void deleteCoupon() {
        System.out.println("📢delete coupon📢");
        try {
            List<Coupon>coupons=companyFacade.getCompanyCoupons();
            companyFacade.deleteCoupon(coupons.get(0).getId());
            companyFacade.deleteCoupon(coupons.get(1).getId());
        } catch (MyException | SQLException| IndexOutOfBoundsException e) {
            System.out.println(e.getMessage());
        }


    }

    private void updateCoupon() {
        System.out.println("📢update coupon📢");
        try {
        List<Coupon>coupons=companyFacade.getCompanyCoupons();
        Coupon coupon1=coupons.get(0);
        coupon1.setDescription("Margareta as smart as 'menta'");
        Coupon coupon2=coupons.get(1);
        coupon2.setDescription("Shraga as clever as 'ga ga'");
        companyFacade.updateCoupon(coupon1);
        companyFacade.updateCoupon(coupon2);
    } catch (SQLException | MyException|IndexOutOfBoundsException e) {
        System.out.println(e.getMessage());
    }
}
        


    private void addCoupon() {
        System.out.println("📢add coupon📢");
        try {
            Coupon coupon1=new Coupon(companyFacade.getCompanyDetails().getId(),Category.FOOD,"Margareta","Big bos to sail",
                    Date.valueOf("2023-02-08"),Date.valueOf("2023-09-02"),5,1.90,"🤬");
            Coupon coupon2=new Coupon(companyFacade.getCompanyDetails().getId(),Category.SPA,"Shraga","Shraga is recruiting John Bryce graduates ",
                    Date.valueOf("2023-02-08"),Date.valueOf("2023-07-02"),7,8.90,"🤠");
            Coupon coupon3=new Coupon(companyFacade.getCompanyDetails().getId(),Category.SPORT,"Stam","Achla shem shebaholam",
                    Date.valueOf("2023-02-08"),Date.valueOf("2023-06-02"),8,9.90,"😎");
            Coupon coupon4=new Coupon(companyFacade.getCompanyDetails().getId(),Category.BEVERAGES,"Yada","yada yada",
                    Date.valueOf("2023-02-08"),Date.valueOf("2023-05-02"),2,10.90,"😴");
            Coupon coupon5=new Coupon(companyFacade.getCompanyDetails().getId(),Category.TRAVEL,"Bla","bla bla",
                    Date.valueOf("2023-02-07"),Date.valueOf("2023-02-18"),4,6.90,"👽");
            companyFacade.addCoupon(coupon1);
            companyFacade.addCoupon(coupon2);
            companyFacade.addCoupon(coupon3);
            companyFacade.addCoupon(coupon4);
            companyFacade.addCoupon(coupon5);
        } catch (SQLException | MyException e) {
            System.out.println(e.getMessage());
        }
    }

    private void login() {
        try {
            companyFacade= (CompanyFacade) LoginManager.getInstance().login("Nirteck@gmail.com","Nirteck1234", ClientType.Company);
        } catch (MyException | SQLException e) {
            System.out.println(e.getMessage());
        }
    }
}
