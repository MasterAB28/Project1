package project1.dao;

import project1.beans.Coupon;

import java.sql.SQLException;
import java.util.List;

public interface CouponsDao {
    void addCoupon(Coupon coupon) throws SQLException;

    void updateCoupon(Coupon coupon) throws SQLException;

    void deleteCoupon(int couponId) throws SQLException;

    Coupon getOneCoupon(int couponId) throws SQLException;

    List<Coupon> getAllCoupons() throws SQLException;

    void addCouponPurchase(int customerId, int couponId) throws SQLException;

    void deleteCouponPurchase(int customerId, int couponI) throws SQLException;

    void deleteCouponByCompanyId(int companyId) throws SQLException;

    boolean isCompanyGotTheTitle(int companyId, String title);

    void deleteCouponPurchaseByCompany(int couponId);
}

