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

    @Override
    public boolean login(String email, String password) throws MyException, SQLException {
        companyId = companiesDao.isCompanyExists(email, password);
        if (companyId != -1)
            return true;
        throw new MyException("login failed");
    }

    public void addCoupon(Coupon coupon) throws MyException, SQLException {
        if (couponsDao.isCompanyGotTheTitle(coupon.getCompanyId(), coupon.getTitle())) {
            throw new MyException("This title is exists for your company");
        }
        couponsDao.addCoupon(coupon);
    }

    public void updateCoupon(Coupon coupon) throws MyException, SQLException {
        if (couponsDao.isCouponExistById(coupon.getId()))
            couponsDao.updateCoupon(coupon);
        throw new MyException("coupon is not exists");
    }

    public void deleteCoupon(int couponId) throws MyException, SQLException {
        if (couponsDao.isCouponExistById(couponId)) {
            couponsDao.deleteCouponPurchaseByCouponId(couponId);
            couponsDao.deleteCoupon(couponId);
        }
        throw new MyException("coupon is not exists");
    }

    public List<Coupon> getCompanyCoupons() throws SQLException {
        return companiesDao.getAllCouponByCompanyId(companyId);
    }

    public List<Coupon> getCompanyCoupons(Category category) throws SQLException {
        ArrayList<Coupon> couponList = (ArrayList<Coupon>) getCompanyCoupons();
        couponList.removeIf(cou -> !cou.getCategory().equals(category));
        return couponList;
    }

    public List<Coupon> getCompanyCoupons(Double maxPrice) throws SQLException {
        ArrayList<Coupon> couponList = (ArrayList<Coupon>) getCompanyCoupons();
        couponList.removeIf(cou -> cou.getPrice() > maxPrice);
        return couponList;
    }

    public Company getCompanyDetails() throws SQLException {
            return companiesDao.getOneCompany(companyId);
    }
}
