package project1.dao;

import project1.beans.Category;
import project1.beans.Company;
import project1.beans.Coupon;
import project1.beans.Customer;
import project1.db.ConnectionPool;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class CustomersDaoImpl implements CustomersDao {
    private ConnectionPool pool = ConnectionPool.getInstance();

    @Override
    public boolean isCustomerExists(String email, String password) throws SQLException {
        Connection con = pool.getConnection();
        try {
            PreparedStatement statement = con.prepareStatement("select email,password from customers where email=? and password=?");
            statement.setString(1, email);
            statement.setString(2,password);
            ResultSet resultSet = statement.executeQuery();
            return resultSet.next();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }finally {
            pool.restoreConnection(con);
        }
    }

    @Override
    public void addCustomer(Customer customer) throws SQLException {
        Connection con = pool.getConnection();
        try {
            PreparedStatement statement = con.prepareStatement("INSERT INTO `coupons`.`customers` (`first_name`, `last_name`, `email`, `password`) VALUES (?,?,?,?);");
            statement.setString(1, customer.getFirstName());
            statement.setString(2, customer.getLastName());
            statement.setString(3, customer.getEmail());
            statement.setString(4, customer.getPassword());
            statement.execute();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }finally {
            pool.restoreConnection(con);
        }
    }

    @Override
    public void updateCustomer(Customer customer) throws SQLException {
        Connection con = pool.getConnection();
        try {
            PreparedStatement statement = con.prepareStatement("update customers set first_name=?, last_name=?, email=?, password=? where id=?");
            statement.setString(1,customer.getFirstName());
            statement.setString(2,customer.getLastName());
            statement.setString(3, customer.getEmail());
            statement.setString(4, customer.getPassword());
            statement.setInt(5, customer.getId());
            statement.execute();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }finally {
            pool.restoreConnection(con);
        }
    }

    @Override
    public void deleteCustomer(int customerId) throws SQLException {
        Connection con = pool.getConnection();
        try {
            PreparedStatement statement = con.prepareStatement("delete from customers where id=?");
            statement.setInt(1,customerId);
            statement.execute();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }finally {
            pool.restoreConnection(con);
        }
    }

    @Override
    public Customer getOneCustomer(int customerId) throws SQLException {
        Connection con = pool.getConnection();
        try {
            PreparedStatement statement = con.prepareStatement("select * from customers where id=?");
            statement.setInt(1, customerId);
            ResultSet resultSet = statement.executeQuery();
            if (resultSet.next()) {
                pool.restoreConnection(con);
                return new Customer(resultSet.getInt(1), resultSet.getString(2), resultSet.getString(3),
                        resultSet.getString(4),resultSet.getString(5),getAllCouponsById(resultSet.getInt(1)));
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }finally {
            pool.restoreConnection(con);
        }
        return null;
    }

    @Override
    public List<Customer> getAllCustomers() throws SQLException {
        Connection con = pool.getConnection();
        try {
            PreparedStatement statement = con.prepareStatement("select * from customers");
            ResultSet resultSet = statement.executeQuery();
            List<Customer> customers = new ArrayList<>();
            while (resultSet.next()){
                customers.add(new Customer(resultSet.getInt(1), resultSet.getString(2), resultSet.getString(3),
                        resultSet.getString(4),resultSet.getString(5),getAllCouponsById(resultSet.getInt(1))));
            }
            return customers;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }finally {
            pool.restoreConnection(con);
        }
    }

    public List<Coupon> getAllCouponsById(int customerId) throws SQLException {
        Connection con = pool.getConnection();
        try {
            PreparedStatement statement = con.prepareStatement("SELECT id,company_id, " +
                    "category_id, title, description, start_date, end_date, amount, price,image " +
                    "FROM coupons join customers_vs_coupons on customer_id=? and coupon_id=id");
            statement.setInt(1,customerId);
            ResultSet resultSet = statement.executeQuery();
            List<Coupon> coupons = new ArrayList<>();
            while (resultSet.next()){
                coupons.add(new Coupon(resultSet.getInt(1),resultSet.getInt(2), Category.values()[resultSet.getInt(3)-1],
                        resultSet.getString(4),resultSet.getString(5),resultSet.getDate(6),resultSet.getDate(7),
                        resultSet.getInt(8),resultSet.getDouble(9),resultSet.getString(10)));
            }
            return coupons;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }finally {
            pool.restoreConnection(con);
        }
    }
    public boolean isEmailExists(String email) throws SQLException {
        Connection con = pool.getConnection();
        try {
            PreparedStatement statement = con.prepareStatement("select email,password from customers where email=?");
            statement.setString(1, email);
            ResultSet resultSet = statement.executeQuery();
            return resultSet.next();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }finally {
            pool.restoreConnection(con);
        }
    }

    public boolean isCustomerIdExists(int customerId) throws SQLException {
        Connection con = pool.getConnection();
        try {
            PreparedStatement statement = con.prepareStatement("select * from customers where id=?");
            statement.setInt(1, customerId);
            ResultSet resultSet = statement.executeQuery();
            return resultSet.next();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }finally {
            pool.restoreConnection(con);
        }
    }

    @Override
    public void deleteCouponPurchasesByCustomerId(int customerId) throws SQLException {
        Connection con = pool.getConnection();
            try {
                PreparedStatement statement = con.prepareStatement("delete from customers_vs_coupons where customer_id=?");
                statement.setInt(1,customerId);
                statement.execute();
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }finally {
                pool.restoreConnection(con);
            }
    }
    public int getCustomerIdByEmailAndPassword(String email, String password) throws SQLException {
        Connection con = pool.getConnection();
        try {
            PreparedStatement statement = con.prepareStatement("select id from customers where email=? and password=?");
            statement.setString(1, email);
            statement.setString(2,password);
            ResultSet resultSet = statement.executeQuery();
            resultSet.next();
            return resultSet.getInt(1);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }finally {
            pool.restoreConnection(con);
        }
    }
}
