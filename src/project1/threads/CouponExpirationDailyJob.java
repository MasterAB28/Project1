package project1.threads;

import project1.beans.Coupon;
import project1.dao.CouponsDao;
import project1.dao.CouponsDaoImpl;

import java.sql.Date;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.List;

public class CouponExpirationDailyJob implements Runnable {
    private CouponsDao couponsDao;
    private boolean quit;

    public void setQuit(boolean quit) {
        this.quit = quit;
    }

    public CouponExpirationDailyJob() {
        this.quit = false;
        this.couponsDao = new CouponsDaoImpl();
    }

    @Override
    public void run() {
        try {
            while (!this.quit){
                long millis = System.currentTimeMillis();
                java.sql.Date date = new Date(millis);
                List<Coupon> coupons = couponsDao.getAllCoupons();
                for (Coupon coupon : coupons){
                    if (coupon.getEndDate().before(date)) {
                        couponsDao.deleteCouponPurchaseByCouponId(coupon.getId());
                        couponsDao.deleteCoupon(coupon.getId());
                        System.out.println("delete success");
                    }
                }
                try {
                    Thread.sleep(1000*60*60*24);
                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                }
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
    public void stop(){
        this.quit = true;
        System.out.println("the program end");
    }

}
