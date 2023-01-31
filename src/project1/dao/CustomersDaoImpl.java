package project1.dao;

import project1.beans.Category;
import project1.beans.Coupon;
import project1.beans.Customer;
import project1.db.ConnectionPool;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class CustomersDaoImpl implements CustomersDao {
    private ConnectionPool pool = ConnectionPool.getInstance();

    /**
     *Check if customer exist by email and password and return the customer id
     */
    @Override
    public int isCustomerExists(String email, String password) throws SQLException {
        Connection con = pool.getConnection();
        try {
            PreparedStatement statement = con.prepareStatement("select id from customers where email=? and password=?");
            statement.setString(1, email);
            statement.setString(2,password);
            ResultSet resultSet = statement.executeQuery();
            if (resultSet.next())
                return resultSet.getInt(1);
            return -1;
        }finally {
            pool.restoreConnection(con);
        }
    }

    /**
     *Add customer to the DB
     */
    @Override
    public void addCustomer(Customer customer) throws SQLException {
        Connection con = pool.getConnection();
        try {
            PreparedStatement statement = con.prepareStatement("INSERT INTO `coupons`.`customers` (`first_name`," +
                    " `last_name`, `email`, `password`) VALUES (?,?,?,?);", Statement.RETURN_GENERATED_KEYS);
            statement.setString(1, customer.getFirstName());
            statement.setString(2, customer.getLastName());
            statement.setString(3, customer.getEmail());
            statement.setString(4, customer.getPassword());
            statement.execute();
            ResultSet resultSet = statement.getGeneratedKeys();
            if (resultSet.next())
                customer.setId(resultSet.getInt(1));
        } finally {
            pool.restoreConnection(con);
        }
    }

    /**
     *Update customer in the DB
     */
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
        } finally {
            pool.restoreConnection(con);
        }
    }

    /**
     *Delete customer from the DB
     */
    @Override
    public void deleteCustomer(int customerId) throws SQLException {
        Connection con = pool.getConnection();
        try {
            PreparedStatement statement = con.prepareStatement("delete from customers where id=?");
            statement.setInt(1,customerId);
            statement.execute();
        } finally {
            pool.restoreConnection(con);
        }
    }

    /**
     *Get one customer from DB by customer id
     */
    @Override
    public Customer getOneCustomer(int customerId) throws SQLException {
        Connection con = pool.getConnection();
        try {
            PreparedStatement statement = con.prepareStatement("select * from customers where id=?");
            statement.setInt(1, customerId);
            ResultSet resultSet = statement.executeQuery();
            if (resultSet.next()) {
                return new Customer(resultSet.getInt(1), resultSet.getString(2), resultSet.getString(3),
                        resultSet.getString(4),resultSet.getString(5),getAllCouponsById(resultSet.getInt(1)));
            }
        } finally {
            pool.restoreConnection(con);
        }
        return null;
    }

    /**
     *Get all the customers from DB
     */
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
        } finally {
            pool.restoreConnection(con);
        }
    }

    /**
     *Get all customer coupons by Customer ID
     */
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
        } finally {
            pool.restoreConnection(con);
        }
    }

    /**
     *Check if the email exists in the DB
     */
    public boolean isEmailExists(String email) throws SQLException {
        Connection con = pool.getConnection();
        try {
            PreparedStatement statement = con.prepareStatement("select email,password from customers where email=?");
            statement.setString(1, email);
            ResultSet resultSet = statement.executeQuery();
            return resultSet.next();
        } finally {
            pool.restoreConnection(con);
        }
    }

    /**
     *Check if the customer exist in the DB by id
     */
    public boolean isCustomerExistsById(int customerId) throws SQLException {
        Connection con = pool.getConnection();
        try {
            PreparedStatement statement = con.prepareStatement("select * from customers where id=?");
            statement.setInt(1, customerId);
            ResultSet resultSet = statement.executeQuery();
            return resultSet.next();
        } finally {
            pool.restoreConnection(con);
        }
    }

    /**
     *Delete the customer purchase from DB
     */
    @Override
    public void deleteCouponPurchasesByCustomerId(int customerId) throws SQLException {
        Connection con = pool.getConnection();
            try {
                PreparedStatement statement = con.prepareStatement("delete from customers_vs_coupons where customer_id=?");
                statement.setInt(1,customerId);
                statement.execute();
            } finally {
                pool.restoreConnection(con);
            }
    }

}
