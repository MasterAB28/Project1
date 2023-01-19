package project1.bl;

import project1.Exception.MyException;
import project1.beans.Category;
import project1.beans.Coupon;
import project1.beans.Customer;

import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

public class CustomerFacade extends ClientFacade {
    private int customerID;

    public CustomerFacade() {
    }

    @Override
    public boolean login(String email, String password) throws MyException, SQLException {
        customerID = customersDao.isCustomerExists(email, password);
        if (customerID != 0)
            return true;
        throw new MyException("login failed");

    }

    public void purchaseCoupon(Coupon coupon) throws SQLException, MyException {
        Date date = new Date();
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        dateFormat.format(date);
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
        } else {
            throw new MyException("you already bought the coupon");
        }
    }
    public List<Coupon> getCustomerCoupons(){
        try {
            return customersDao.getAllCouponsById(customerID);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
    public List<Coupon> getCustomerCoupons(Category category){
        ArrayList<Coupon> couponList = (ArrayList<Coupon>) getCustomerCoupons();
        couponList.removeIf(cou -> !cou.getCategory().equals(category));
        return couponList;
    }
    public List<Coupon> getCustomerCoupons(double maxPrice){
        ArrayList<Coupon> couponList = (ArrayList<Coupon>) getCustomerCoupons();
        couponList.removeIf(cou -> cou.getPrice()>maxPrice);
        return couponList;
    }
    public Customer getCustomerDetails(){
        try {
            return customersDao.getOneCustomer(customerID);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}
