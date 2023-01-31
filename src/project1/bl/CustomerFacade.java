package project1.bl;

import project1.Exception.MyException;
import project1.beans.Category;
import project1.beans.Coupon;
import project1.beans.Customer;

import java.sql.Date;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class CustomerFacade extends ClientFacade {
    private int customerID;

    public CustomerFacade() {
    }

    /**
     *Check if the customer details who logged in are correct and set the customer id by the email and password
     */
    @Override
    public boolean login(String email, String password) throws MyException, SQLException {
        customerID = customersDao.isCustomerExists(email, password);
        if (customerID != -1)
            return true;
        throw new MyException("login failed");

    }

    /**
     *Logic of purchase coupon by customer
     */
    public void purchaseCoupon(Coupon coupon) throws SQLException, MyException {
        long millis = System.currentTimeMillis();
        java.sql.Date date = new Date(millis);
        if (couponsDao.isPurchaseExist(customerID, coupon.getId())) {
            if (coupon.getAmount() > 0) {
                if (coupon.getEndDate().after(date)) {
                    couponsDao.addCouponPurchase(customerID, coupon.getId());
                    coupon.setAmount(coupon.getAmount() - 1);
                    couponsDao.updateCoupon(coupon);
                } else
                    throw new MyException("The coupon is expired");
            } else
                throw new MyException("The coupon is out of stock");
        } else
            throw new MyException("you already bought the coupon");
    }

    /**
     *Get all customer coupons
     */
    public List<Coupon> getCustomerCoupons() throws SQLException {
        return customersDao.getAllCouponsById(customerID);
    }

    /**
     *Get all customer coupons by category
     */
    public List<Coupon> getCustomerCoupons(Category category) throws SQLException {
        ArrayList<Coupon> couponList = (ArrayList<Coupon>) getCustomerCoupons();
        couponList.removeIf(cou -> !cou.getCategory().equals(category));
        return couponList;
    }

    /**
     *Get all customer coupons by maximum price
     */
    public List<Coupon> getCustomerCoupons(double maxPrice) throws SQLException {
        ArrayList<Coupon> couponList = (ArrayList<Coupon>) getCustomerCoupons();
        couponList.removeIf(cou -> cou.getPrice() > maxPrice);
        return couponList;
    }

    /**
     * Get all customer details
     */
    public Customer getCustomerDetails() throws SQLException {
        return customersDao.getOneCustomer(customerID);
    }
}
