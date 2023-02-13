package project1.Test;

import project1.Exception.MyException;
import project1.beans.Category;
import project1.beans.Coupon;
import project1.bl.CustomerFacade;
import project1.dao.CouponsDao;
import project1.login.ClientType;
import project1.login.LoginManager;

import java.sql.SQLException;
import java.util.List;

public class TestCustomer {
    CustomerFacade customerFacade;
    private CouponsDao couponsDao;

    public void runAllCustomerFacadeTest() {
        login();
        if (customerFacade != null) {
//            purchaseCoupon();
//            getCustomerCoupons();
//           getCustomerCouponsByCategory();
//          getCustomerCouponsByMaxPrice();
//        getAllCoupons();
//      getCustomerDetails();
        }

    }

    private void getCustomerDetails() {
        System.out.println("📢get customer details ");
        try {
            System.out.println(customerFacade.getCustomerDetails());
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }

    }

    private void getAllCoupons() {
        System.out.println("📢get all coupons ");
        try {
            System.out.println(customerFacade.getAllCoupons());
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }

    }

    private void getCustomerCouponsByMaxPrice() {
        System.out.println("📢get customer coupons by maximum price");
        try {
            System.out.println(customerFacade.getCustomerCoupons(10.9));
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }

    private void getCustomerCouponsByCategory() {
        System.out.println("📢get customer coupons by category📢");
        try {
            System.out.println(customerFacade.getCustomerCoupons(Category.SPORT));
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }


    }

    private void getCustomerCoupons() {
        System.out.println("📢get customer coupons📢");
        try {
            System.out.println(customerFacade.getCustomerCoupons());
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }

    public void deletePurchaseCoupon() {
        System.out.println("📢delete purchase coupon📢");
        try {
            customerFacade.deletePurchaseCoupon(couponsDao.getOneCoupon(42));
            System.out.println("delete purchase success");
        } catch (MyException | SQLException e) {
            System.out.println(e.getMessage());
        }
    }

    private void purchaseCoupon() {
        System.out.println("📢purchase coupon📢");
        try {
            List<Coupon> coupons = customerFacade.getAllCoupons();
            customerFacade.purchaseCoupon(coupons.get(0));
            System.out.println("purchase success");
        } catch (SQLException | MyException | IndexOutOfBoundsException e) {
            System.out.println(e.getMessage());
        }
    }

    private void login() {
        try {
            customerFacade = (CustomerFacade) LoginManager.getInstance().login("malki@gmail.com", "malki1234", ClientType.Customer);
        } catch (MyException | SQLException e) {
            System.out.println(e.getMessage());
        }

    }
}
