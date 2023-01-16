package project1.dao;

import project1.beans.Category;
import project1.beans.Coupon;
import project1.db.ConnectionPool;

import java.lang.reflect.Array;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class CouponsDaoImpl implements CouponsDao{
    private ConnectionPool pool = ConnectionPool.getInstance();

    @Override
    public void addCoupon(Coupon coupon) throws SQLException {
        Connection con = pool.getConnection();
        try {
            PreparedStatement statement = con.prepareStatement("INSERT INTO coupons " +
                    "(company_id, category_id, title, description, start_date, end_date, amount, price,image) VALUES(?,?,?,?,?,?,?,?,?)");
            statement.setInt(1,coupon.getCompanyId());
            statement.setInt(2, coupon.getCategory().ordinal()+1);
            statement.setString(3,coupon.getTitle());
            statement.setString(4,coupon.getDescription());
            statement.setDate(5,coupon.getStartDate());
            statement.setDate(6,coupon.getEndDate());
            statement.setInt(7,coupon.getAmount());
            statement.setDouble(8,coupon.getPrice());
            statement.setString(9,coupon.getImage());
            statement.execute();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }finally {
            pool.restoreConnection(con);
        }
    }

    @Override
    public void updateCoupon(Coupon coupon) throws SQLException {
        Connection con = pool.getConnection();
        try {
            PreparedStatement statement = con.prepareStatement("UPDATE coupons SET " +
                    " category_id=?, title=?, description=?, start_date=?, end_date=?, amount=?, price=?, image=? WHERE id` =?");
            statement.setInt(1,coupon.getCategory().ordinal());
            statement.setString(2,coupon.getTitle());
            statement.setString(3,coupon.getDescription());
            statement.setDate(4,coupon.getStartDate());
            statement.setDate(5,coupon.getEndDate());
            statement.setInt(6,coupon.getAmount());
            statement.setDouble(7,coupon.getPrice());
            statement.setString(8,coupon.getImage());
            statement.setInt(9,coupon.getId());
            statement.execute();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }finally {
            pool.restoreConnection(con);
        }
    }

    @Override
    public void deleteCoupon(int couponId) throws SQLException {
        Connection con = pool.getConnection();
        try {
            PreparedStatement statement = con.prepareStatement("delete from coupons where id=?");
            statement.setInt(1,couponId);
            statement.execute();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }finally {
            pool.restoreConnection(con);
        }
    }

    @Override
    public Coupon getOneCoupon(int couponId) throws SQLException {
        Connection con = pool.getConnection();
        try {
            PreparedStatement statement = con.prepareStatement("select * from coupons where id=?");
            statement.setInt(1,couponId);
            ResultSet resultSet = statement.executeQuery();
            if (resultSet.next()){
                pool.restoreConnection(con);
                return new Coupon(resultSet.getInt(1),resultSet.getInt(2), Category.values()[resultSet.getInt(3)],
                        resultSet.getString(4), resultSet.getString(5),resultSet.getDate(6),
                        resultSet.getDate(7),resultSet.getInt(8),resultSet.getDouble(9),
                        resultSet.getString(10));
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }finally {
            pool.restoreConnection(con);
        }
        return null;
    }

    @Override
    public List<Coupon> getAllCoupons() throws SQLException {
        Connection con = pool.getConnection();
        try {
            PreparedStatement statement = con.prepareStatement("select * from coupons");
            ResultSet resultSet = statement.executeQuery();
            List<Coupon> coupons = new ArrayList<>();
            while (resultSet.next()){
                coupons.add(new Coupon(resultSet.getInt(1),resultSet.getInt(2), Category.values()[resultSet.getInt(3)],
                        resultSet.getString(4), resultSet.getString(5),resultSet.getDate(6),
                        resultSet.getDate(7),resultSet.getInt(8),resultSet.getDouble(9),
                        resultSet.getString(10)));
            }
            return coupons;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }finally {
            pool.restoreConnection(con);
        }
    }

    @Override
    public void addCouponPurchase(int customerId, int couponId) throws SQLException {
        Connection con = pool.getConnection();
        try {
            PreparedStatement statement = con.prepareStatement("insert into customers_vs_coupons values (?,?)");
            statement.setInt(1,customerId);
            statement.setInt(2,couponId);
            statement.execute();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }finally {
            pool.restoreConnection(con);
        }
    }

    @Override
    public void deleteCouponPurchase(int customerId, int couponId) throws SQLException {
        Connection con = pool.getConnection();
        try {
            PreparedStatement statement = con.prepareStatement("delete from customers_vs_coupons where customer_id=? and coupon_id=?");
            statement.setInt(1,customerId);
            statement.setInt(2,couponId);
            statement.execute();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }finally {
            pool.restoreConnection(con);
        }
    }

    @Override
    public void deleteCouponByCompanyId(int companyId) throws SQLException {
        Connection con = pool.getConnection();
        try {
            PreparedStatement statement = con.prepareStatement("delete from coupons where company_id=?");
            statement.setInt(1,companyId);
            statement.execute();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }finally {
            pool.restoreConnection(con);
        }
    }

    @Override
    public boolean isCompanyGotTheTitle(int companyId, String title) {
        Connection con = pool.getConnection();
        try {
            PreparedStatement statement = con.prepareStatement("select from coupons where company_id=? and title=?");
            statement.setInt(1,companyId);
            statement.setString(2,title);
            ResultSet resultSet = statement.executeQuery();
            return resultSet.next();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }finally {
            pool.restoreConnection(con);
        }
    }
    public void deleteCouponPurchaseByCompany(int couponId){
        Connection con = pool.getConnection();
        try {
            PreparedStatement statement = con.prepareStatement("delete from Customers_vs_coupons where coupon_id=?");
            statement.setInt(1,couponId);
            statement.execute();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }finally {
            pool.restoreConnection(con);
        }
    }
}
