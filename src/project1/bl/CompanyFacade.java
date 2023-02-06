package project1.bl;

import project1.Exception.MyException;
import project1.beans.Category;
import project1.beans.Company;
import project1.beans.Coupon;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class CompanyFacade extends ClientFacade {
    private int companyId;

    public CompanyFacade() {
    }

    /**
     * The method receives company's email and password and checks if the email and password are correct,
     * by method 'isCompanyExists' from the dao
     * @param email company's email
     * @param password company's password
     * @return true or false
     * @throws MyException if the login failed
     * @throws SQLException if the sql method is not working properly
     */
    @Override
    public boolean login(String email, String password) throws MyException, SQLException {
        companyId = companiesDao.isCompanyExists(email, password);
        if (companyId != -1) {
            System.out.println("login success");
            return true;
        }
        throw new MyException("login failed");
    }

    /**
     * The method receives a coupon object and checks if this company has the same title to another coupon,
     * if not, the method adds it to 'coupons' db by the method 'addCoupon' from the dao
     * @param coupon a coupon object
     * @throws MyException if the title is  exists for this company
     * @throws SQLException if the sql method is not working properly
     */
    public void addCoupon(Coupon coupon) throws MyException, SQLException {
        if (couponsDao.isCompanyGotTheTitle(coupon.getCompanyId(), coupon.getTitle())) {
            throw new MyException("This title is exists for your company");
        }
        couponsDao.addCoupon(coupon);
    }

    /**
     * The method receives a coupon object, checks if the coupon is exists in 'coupons' db,
     * and then updates it in the db
     * @param coupon a coupon object
     * @throws MyException if the coupon is not exist
     * @throws SQLException if the sql method is not working properly
     */
    public void updateCoupon(Coupon coupon) throws MyException, SQLException {
        if (couponsDao.isCouponExistById(coupon.getId()))
            couponsDao.updateCoupon(coupon);
        throw new MyException("coupon is not exists");
    }

    /**
     *The method receives coupon's id and checks f the coupon is exists in the 'coupons' db
     * and then deletes the coupon purchase history by method deleteCouponPurchaseByCouponId from the dao,
     * and finally deletes the coupon
     * @param couponId coupon's id
     * @throws MyException if the coupon is not exists
     * @throws SQLException if the sql method is not working properly
     */
    public void deleteCoupon(int couponId) throws MyException, SQLException {
        if (couponsDao.isCouponExistById(couponId)) {
            couponsDao.deleteCouponPurchaseByCouponId(couponId);
            couponsDao.deleteCoupon(couponId);
        }
        throw new MyException("coupon is not exists");
    }

    /**
     *The method returns all the coupons from the db of the company that performed the login
     * @return a list of coupons object of this company
     * @throws SQLException if the sql method is not working properly
     */
    public List<Coupon> getCompanyCoupons() throws SQLException {
        return companiesDao.getAllCouponByCompanyId(companyId);
    }

    /**
     * The method receives category of coupon and returns a list of coupons object from this category
     * of the company that performed the login
     * @param category coupon's category
     * @return coupons object from this category of this company
     * @throws SQLException if the sql method is not working properly
     */
    public List<Coupon> getCompanyCoupons(Category category) throws SQLException {
        ArrayList<Coupon> couponList = (ArrayList<Coupon>) getCompanyCoupons();
        couponList.removeIf(cou -> !cou.getCategory().equals(category));
        return couponList;
    }

    /**
     *The method receives  maximum price of coupons and returns a list of coupons object up to the maximum price
     *  of the company that performed the login
     * @param maxPrice maximum price of coupons
     * @return a list of coupons object up to the maximum price  of this company
     * @throws SQLException if the sql method is not working properly
     */
    public List<Coupon> getCompanyCoupons(Double maxPrice) throws SQLException {
        ArrayList<Coupon> couponList = (ArrayList<Coupon>) getCompanyCoupons();
        couponList.removeIf(cou -> cou.getPrice() > maxPrice);
        return couponList;
    }

    /**
     *The method returns the details of the company that performed the login
     * @return the details of the company that performed the login
     * @throws SQLException if the sql method is not working properly
     */
    public Company getCompanyDetails() throws SQLException {
            return companiesDao.getOneCompany(companyId);
    }
}
