package project1.dao;

import project1.beans.Customer;

import java.sql.SQLException;
import java.util.List;

public interface CustomersDao {
    boolean isCustomerExists(String email,String password) throws SQLException;
    void addCustomer (Customer customer) throws SQLException;
    void updateCustomer (Customer customer) throws SQLException;
    void deleteCustomer (int customerId) throws SQLException;
    Customer getOneCustomer (int customerId) throws SQLException;
    List<Customer> getAllCustomers() throws SQLException;
}
