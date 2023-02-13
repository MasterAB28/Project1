package project1.threads;

import project1.beans.Coupon;
import project1.dao.CouponsDao;
import project1.dao.CouponsDaoImpl;

import java.sql.Date;
import java.sql.SQLException;
import java.util.List;

public class CouponExpirationDailyJob implements Runnable {
    private CouponsDao couponsDao;
    private boolean quit;
    private Thread thread = new Thread(this, "dailyJob");

    public void setQuit(boolean quit) {
        this.quit = quit;
    }

    public CouponExpirationDailyJob() {
        this.quit = false;
        this.couponsDao = new CouponsDaoImpl();
    }

    /**
     * The method is Override to method 'run' and checks if this thread is not quit
     *if not, the check expired have called
     */
    @Override
    public void run() {
        while (!this.quit) {
            try {
                checkExpired();
                Thread.sleep(1000 * 60 * 60 * 24);
            } catch (InterruptedException ignored) {
            }

        }
    }

    /**
     * the method start the thread
     */
    public void start() {
        this.thread.start();
        quit = false;
    }

    /**
     * the method changes the quit to true and wake up the thread to close the thread
     */
    public void stop() {
        quit = true;
        thread.interrupt();
        System.out.println("the program end");

    }

    /**
     * checks for all coupons if their dates are before today
     * if yes the method delete the purchase of coupon from 'coupons' db
     * by the method 'deleteCouponPurchaseByCouponId' from the dao
     * and delete coupon from 'coupons' db by the method 'deleteCoupon' from the dao
     */
    public void checkExpired() {
        try {
            long millis = System.currentTimeMillis();
            java.sql.Date date = new Date(millis);
            List<Coupon> coupons = couponsDao.getAllCoupons();
            for (Coupon coupon : coupons) {
                if (coupon.getEndDate().before(date)) {
                    couponsDao.deleteCouponPurchaseByCouponId(coupon.getId());
                    couponsDao.deleteCoupon(coupon.getId());
                    System.out.println("delete success");
                }
            }
        } catch (SQLException e) {
            System.out.println("sql error");
        }
    }

}