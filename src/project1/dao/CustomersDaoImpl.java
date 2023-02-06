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
     * The method checks if the customer exists in the db by email and password
     * @param email customer's email
     * @param password customer's password
     * @return the id of the customer
     * @throws SQLException if the sql method is not working properly
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
     * The method adds customer to the db
     * @param customer customer, the details of customer are: first name, last name, email, password
     * @throws SQLException if the sql method is not working properly
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
     * The method updates customer in the db
     * @param customer customer, the details of customer are: first name, last name, email, password and id
     * @throws SQLException if the sql method is not working properly
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
     * The method deletes customer from the db
     * @param customerId customer id
     * @throws SQLException if the sql method is not working properly
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
     * The method returns one customer from the db by customer id
     * @param customerId customer id
     * @return a customer, the details are:id, first name, last name, email,password and the customer's coupons
     * @throws SQLException if the sql method is not working properly
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
     * The method returns list of all the customers from the db
     * @return list of all the customers, the details are:id, first name, last name, email,password and the customer's coupons
     * @throws SQLException if the sql method is not working properly
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
     * The method returns list of coupons by customer id
     * @param customerId customer id
     * @return list of coupons, the details are: coupon id, company id, category, name,
     * title, start date, end date,amount, price,image
     * @throws SQLException if the sql method is not working properly
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
     * The method checks if the customer exists in the db by email
     * @param email customer email
     * @return true or false
     * @throws SQLException if the sql method is not working properly
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
     * The method checks if the customer exists in the db by customer id
     * @param customerId customer id
     * @return true or false
     * @throws SQLException if the sql method is not working properly
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
     * The method checks if the customer exists in the db by customer id
     * @param customerId customer id
     * @return true or false
     * @throws SQLException if the sql method is not working properly
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
