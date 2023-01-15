package project1.bl;

import project1.Exception.MyException;
import project1.beans.Coupon;

import java.sql.SQLException;

public class CompanyFacade extends ClientFacade{
    private int companyId;

    public CompanyFacade(int companyId) {
        this.companyId = companyId;
    }

    @Override
    public boolean login(String email, String password) throws MyException, SQLException {
        if (companiesDao.isCompanyExists(email,password))
        return true;
        throw new MyException("login failed");
    }
    public void addCoupon(Coupon coupon) throws MyException, SQLException {
        if (couponsDao.isCompanyGotTheTitle(coupon.getCompanyId(),coupon.getTitle())){
            throw new MyException("This title is exist for your company");
        }
        couponsDao.addCoupon(coupon);
    }
    public void updateCoupon(Coupon coupon) throws MyException {
        try {
            couponsDao.updateCoupon(coupon);
        } catch (SQLException e) {
            throw new MyException("coupon update failed");
        }
    }
    public void deleteCoupon(int couponId) throws MyException {
        try {
            couponsDao.deleteCouponPurchaseByCompany(couponId);
            couponsDao.deleteCoupon(couponId);
        } catch (SQLException e) {
            throw new MyException("coupon delete failed");
        }
    }

}
