package project1.dao;


import project1.beans.Category;
import project1.beans.Coupon;
import project1.db.ConnectionPool;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class CouponsDaoImpl implements CouponsDao {
    private ConnectionPool pool = ConnectionPool.getInstance();

    /**
     * Add coupon to the DB
     */
    @Override
    public void addCoupon(Coupon coupon) throws SQLException {
        Connection con = pool.getConnection();
        try {
            PreparedStatement statement = con.prepareStatement("INSERT INTO coupons " +
                            "(company_id, category_id, title, description, start_date, end_date, amount, price,image) VALUES(?,?,?,?,?,?,?,?,?)",
                    Statement.RETURN_GENERATED_KEYS);
            statement.setInt(1, coupon.getCompanyId());
            statement.setInt(2, coupon.getCategory().ordinal() + 1);
            statement.setString(3, coupon.getTitle());
            statement.setString(4, coupon.getDescription());
            statement.setDate(5, coupon.getStartDate());
            statement.setDate(6, coupon.getEndDate());
            statement.setInt(7, coupon.getAmount());
            statement.setDouble(8, coupon.getPrice());
            statement.setString(9, coupon.getImage());
            statement.execute();
            ResultSet resultSet = statement.getGeneratedKeys();
            if (resultSet.next())
                coupon.setId(resultSet.getInt(1));
        } finally {
            pool.restoreConnection(con);
        }
    }

    /**
     *Update coupon in the DB
     */
    @Override
    public void updateCoupon(Coupon coupon) throws SQLException {
        Connection con = pool.getConnection();
        try {
            PreparedStatement statement = con.prepareStatement("UPDATE coupons SET " +
                    " category_id=?, title=?, description=?, start_date=?, end_date=?, amount=?, price=?, image=? WHERE id=?");
            statement.setInt(1, coupon.getCategory().ordinal() + 1);
            statement.setString(2, coupon.getTitle());
            statement.setString(3, coupon.getDescription());
            statement.setDate(4, coupon.getStartDate());
            statement.setDate(5, coupon.getEndDate());
            statement.setInt(6, coupon.getAmount());
            statement.setDouble(7, coupon.getPrice());
            statement.setString(8, coupon.getImage());
            statement.setInt(9, coupon.getId());
            statement.execute();
        } finally {
            pool.restoreConnection(con);
        }
    }

    /**
     *Delete coupon from the DB
     */
    @Override
    public void deleteCoupon(int couponId) throws SQLException {
        Connection con = pool.getConnection();
        try {
            PreparedStatement statement = con.prepareStatement("delete from coupons where id=?");
            statement.setInt(1, couponId);
            statement.execute();
        } finally {
            pool.restoreConnection(con);
        }
    }

    /**
     *Get one coupon from DB
     */
    @Override
    public Coupon getOneCoupon(int couponId) throws SQLException {
        Connection con = pool.getConnection();
        try {
            PreparedStatement statement = con.prepareStatement("select * from coupons where id=?");
            statement.setInt(1, couponId);
            ResultSet resultSet = statement.executeQuery();
            if (resultSet.next()) {
                return new Coupon(resultSet.getInt(1), resultSet.getInt(2), Category.values()[resultSet.getInt(3) - 1],
                        resultSet.getString(4), resultSet.getString(5), resultSet.getDate(6),
                        resultSet.getDate(7), resultSet.getInt(8), resultSet.getDouble(9),
                        resultSet.getString(10));
            }
        } finally {
            pool.restoreConnection(con);
        }
        return null;
    }

    /**
     *Get all the coupons from the DB
     */
    @Override
    public List<Coupon> getAllCoupons() throws SQLException {
        Connection con = pool.getConnection();
        try {
            PreparedStatement statement = con.prepareStatement("select * from coupons");
            ResultSet resultSet = statement.executeQuery();
            List<Coupon> coupons = new ArrayList<>();
            while (resultSet.next()) {
                coupons.add(new Coupon(resultSet.getInt(1), resultSet.getInt(2), Category.values()[resultSet.getInt(3)],
                        resultSet.getString(4), resultSet.getString(5), resultSet.getDate(6),
                        resultSet.getDate(7), resultSet.getInt(8), resultSet.getDouble(9),
                        resultSet.getString(10)));
            }
            return coupons;
        } finally {
            pool.restoreConnection(con);
        }
    }

    /**
     *Add purchase of coupon to the DB
     */
    @Override
    public void addCouponPurchase(int customerId, int couponId) throws SQLException {
        Connection con = pool.getConnection();
        try {
            PreparedStatement statement = con.prepareStatement("insert into customers_vs_coupons values (?,?)");
            statement.setInt(1, customerId);
            statement.setInt(2, couponId);
            statement.execute();
        } finally {
            pool.restoreConnection(con);
        }
    }

    /**
     *Delete purchase of coupon from DB
     */
    @Override
    public void deleteCouponPurchase(int customerId, int couponId) throws SQLException {
        Connection con = pool.getConnection();
        try {
            PreparedStatement statement = con.prepareStatement("delete from customers_vs_coupons where customer_id=? and coupon_id=?");
            statement.setInt(1, customerId);
            statement.setInt(2, couponId);
            statement.execute();
        } finally {
            pool.restoreConnection(con);
        }
    }

    /**
     *Check if the company have the coupon title
     */
    @Override
    public boolean isCompanyGotTheTitle(int companyId, String title) throws SQLException {
        Connection con = pool.getConnection();
        try {
            PreparedStatement statement = con.prepareStatement("select * from coupons where company_id=? and title=?");
            statement.setInt(1, companyId);
            statement.setString(2, title);
            ResultSet resultSet = statement.executeQuery();
            return resultSet.next();
        } finally {
            pool.restoreConnection(con);
        }
    }

    /**
     *Delete coupon purchase from DB by coupon id
     */
    public void deleteCouponPurchaseByCouponId(int couponId) throws SQLException {
        Connection con = pool.getConnection();
        try {
            PreparedStatement statement = con.prepareStatement("delete from Customers_vs_coupons where coupon_id=?");
            statement.setInt(1, couponId);
            statement.execute();
        } finally {
            pool.restoreConnection(con);
        }
    }

    /**
     *Check if customer purchase this coupon or not
     */
    public boolean isPurchaseExist(int customerId, int couponId) throws SQLException {
        Connection con = pool.getConnection();
        try {
            PreparedStatement statement = con.prepareStatement("select * from customers_vs_coupons where customer_id=? and coupon_id=?");
            statement.setInt(1, customerId);
            statement.setInt(2, couponId);
            ResultSet resultSet = statement.executeQuery();
            return !resultSet.next();
        }finally {
            pool.restoreConnection(con);
        }
    }

    /**
     *Check if the coupon exists in the DB by coupon id
     * @param couponId id of the coupon
     * @return true or false if the coupon exists by id
     * @throws SQLException
     */
    @Override
    public boolean isCouponExistById(int couponId) throws SQLException {
        Connection con = pool.getConnection();
        try {
            PreparedStatement statement = con.prepareStatement("select * from coupons where id=?");
            statement.setInt(1, couponId);
            ResultSet resultSet = statement.executeQuery();
            return resultSet.next();
        } finally {
            pool.restoreConnection(con);
        }
    }
}


