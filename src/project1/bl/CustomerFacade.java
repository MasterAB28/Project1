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
     * The method receives customer's email and password and checks if the email and password are correct,
     * by method 'iscustomerExists' from the dao
     * @param email customer's email
     * @param password customer's password
     * @return true or false
     * @throws MyException if the login failed
     * @throws SQLException if the sql method is not working properly
     */
    @Override
    public boolean login(String email, String password) throws MyException, SQLException {
        customerID = customersDao.isCustomerExists(email, password);
        if (customerID != -1) {
            System.out.println("login success");
            return true;
        }
        throw new MyException("login failed");
    }

    /**
     * The method receives a coupon object and checks if this customer has the same coupon,
     * if not,checks if this amount of coupons bigger of 0,
     * if yes checks if this end date of coupons is not over yet
     * if not the method adds it to 'coupons' db by the method 'addCouponPurchase' from the dao
     * @param coupon a coupon object
     * @throws MyException if the purchase is  exists for this customer
     * @throws MyException if the amount of coupons is smaller than 1
     * @throws MyException if the end date of coupons is over
     * @throws SQLException if the sql method is not working properly
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
     * The method receives a coupon object and checks if the customer have the purchase
     * if not, throw exception
     * if yes delete the purchase from DB and add 1 to the coupon amount and update it in DB
     * @param coupon coupon to delete purchase
     * @throws SQLException if the sql method is not working properly
     * @throws MyException if the customer don't have the coupon purchase
     */
    public void deletePurchaseCoupon(Coupon coupon) throws SQLException, MyException {
        if (couponsDao.isPurchaseExist(customerID,coupon.getId())){
            couponsDao.deleteCouponPurchase(customerID,coupon.getId());
            coupon.setAmount(coupon.getAmount()+1);
            couponsDao.updateCoupon(coupon);
            return;
        }
        throw new MyException("this purchase is not exists");
    }

    /**
     *The method returns all the coupons from the db of the customer that performed the login
     * @return a list of coupons object of this customer`
     * @throws SQLException if the sql method is not working properly
     */
    public List<Coupon> getCustomerCoupons() throws SQLException {
        return customersDao.getAllCouponsById(customerID);
    }

    /**
     * The method receives category of coupon and returns a list of coupons object from this category
     * of the customer that performed the login
     * @param category coupon's category
     * @return coupons object from this category of this customer
     * @throws SQLException if the sql method is not working properly
     */
    public List<Coupon> getCustomerCoupons(Category category) throws SQLException {
        ArrayList<Coupon> couponList = (ArrayList<Coupon>) getCustomerCoupons();
        couponList.removeIf(cou -> !cou.getCategory().equals(category));
        return couponList;
    }

    /**
     *The method receives  maximum price of coupons and returns a list of coupons object up to the maximum price
     *  of the customer that performed the login
     * @param maxPrice maximum price of coupons
     * @return a list of coupons object up to the maximum price  of this customer
     * @throws SQLException if the sql method is not working properly
     */
    public List<Coupon> getCustomerCoupons(double maxPrice) throws SQLException {
        ArrayList<Coupon> couponList = (ArrayList<Coupon>) getCustomerCoupons();
        couponList.removeIf(cou -> cou.getPrice() > maxPrice);
        return couponList;
    }

    /**
     *The method return all the coupons in the DB
     * @return List of all coupons in DB
     * @throws SQLException if the sql method is not working properly
     */
    public List<Coupon> getAllCoupons() throws SQLException {
        return couponsDao.getAllCoupons();
    }
    /**
     *The method returns the details of the customer that performed the login
     * @return the details of the customer that performed the login
     * @throws SQLException if the sql method is not working properly
     */
    public Customer getCustomerDetails() throws SQLException {
        return customersDao.getOneCustomer(customerID);
    }
}
