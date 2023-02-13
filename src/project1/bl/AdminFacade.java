package project1.bl;

import project1.Exception.MyException;
import project1.beans.Company;
import project1.beans.Coupon;
import project1.beans.Customer;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class AdminFacade extends ClientFacade {

    public AdminFacade() {
    }
    /**
     * The method receives admin's email and password and checks if they are correct
     * @param email admin's email
     * @param password admin's password
     * @return true or false
     */
    @Override
    public boolean login(String email, String password) throws MyException {
        if (email.equals("admin@admin.com") && password.equals("admin")){
            System.out.println("login success");
            return true;
        }
        throw new MyException("login failed");
    }
    /**
     * The method receives object company and checks if the mail or email exist in the 'companies' db,
     * if not the method adds the company to the 'companies' db, by method 'add company; from the
     * @param company a company object
     * @throws MyException if Company email or name exists
     * @throws SQLException if the sql method is not working properly
     */
    public void addCompany(Company company) throws MyException, SQLException {
        if (companiesDao.isNameOrEmailExist(company.getEmail(), company.getName())) {
            throw new MyException("Company email or name exists");
        }
        companiesDao.addCompany(company);
    }
    /**
     * The method receives a company object checks if the company exists by id in the 'companies' db,
     * and then updates it in the 'companies' db,by method 'updateCompany' from the db
     * @param company a company object
     * @throws SQLException if The company is not exists
     * @throws MyException if the sql method is not working properly
     */
    public void updateCompany(Company company) throws SQLException, MyException {
        if (companiesDao.isCompanyExistById(company.getId())) {
            companiesDao.updateCompany(company);
            return;
        }
        throw new MyException("The company was not found");
    }

    /**
     *The method receives  company's id and checks if the company is exists in the 'companies' db, if it exists
     * the method deletes all coupons' purchase by coupon id of this company,
     * and all the company's coupons, and then it deletes the company
     * @param companyId-company's id
     * @throws MyException if the company is not exists in the db
     * @throws SQLException if the sql method is not working properly
     */
    public void deleteCompany(int companyId) throws MyException, SQLException {
        if (companiesDao.isCompanyExistById(companyId)) {
            List<Coupon> coupons = companiesDao.getAllCouponByCompanyId(companyId);
            for (Coupon coupon : coupons) {
                couponsDao.deleteCouponPurchaseByCouponId(coupon.getId());
                couponsDao.deleteCoupon(coupon.getId());
            }
            companiesDao.deleteCompany(companyId);
            return;
        }
        throw new MyException("The company delete is failed");
    }

    /**
     * The method returns all companies from the db by method 'getAllCompanies' from the dao
     * @return list of all the companies object
     * @throws SQLException if the sql method is not working properly
     * @throws MyException
     */
    public List<Company> getAllCompanies() throws SQLException, MyException {
        return companiesDao.getAllCompanies();
    }

    /**
     * The method receives company's id and checks if the company exists in the db
     * and returns one company object
     * @param companyId company's id
     * @return a company object
     * @throws SQLException if the sql method is not working properly
     * @throws MyException if the company is not exists
     */
    public Company getOneCompany(int companyId) throws SQLException, MyException {
        if (companiesDao.isCompanyExistById(companyId))
        return companiesDao.getOneCompany(companyId);
        throw new MyException("This company is not exists");
    }

    /**
     * The method receives a customer object and checks if the customer's email exists in the 'customers'  db,
     * if not, the method add it to the 'customers' db by method 'addCustomer' from the dao
     * @param customer- a customer object
     * @throws SQLException if the sql method is not working properly
     * @throws MyException if the email exists
     */
    public void addCustomer(Customer customer) throws SQLException, MyException {
        if (customersDao.isEmailExists(customer.getEmail()))
            throw new MyException("This email exists");
        customersDao.addCustomer(customer);
    }

    /**
     * The method receives a customer object and checks if the customer exist in the 'customers' db by id
     * and then updates the customer by the details it receives.
     * @param customer a customer object
     * @throws SQLException if the sql method is not working properly
     * @throws MyException if the customers' id is not exists
     */
    public void updateCustomer(Customer customer) throws SQLException, MyException {
        if (customersDao.isCustomerExistsById(customer.getId())) {
            customersDao.updateCustomer(customer);
            return;
        }
        throw new MyException("The customer was not found");
    }

    /**
     * The method receives customer's id and checks if the customer exists in the 'customers' db
     * and then deletes coupons purchase of customers by the method 'deleteCouponPurchasesByCustomerId' from the dao
     * deletes the customer by the method 'deleteCustomer' from the dao
     * @param customerId customer's id
     * @throws SQLException if the sql method is not working properly
     * @throws MyException if the customer is not exists
     */
    public void deleteCustomer(int customerId) throws SQLException, MyException {
        if (customersDao.isCustomerExistsById(customerId)) {
            customersDao.deleteCouponPurchasesByCustomerId(customerId);
            customersDao.deleteCustomer(customerId);
            return;
        }
        throw new MyException("The customer delete is failed");
    }

    /**
     *The method returns all the customers from the 'customers' db
     * @return a list of customer object
     * @throws SQLException if the sql method is not working properly
     */
    public List<Customer> getAllCustomers() throws SQLException {
        return customersDao.getAllCustomers();
    }

    /**
     *The method receives customer's id, checks if the customer exists in the 'customers' db
     * and then returns a customer object
     * @param customerId customer's id
     * @return a customer object
     * @throws SQLException if the sql method is not working properly
     * @throws MyException if the customer is not exists
     */
    public Customer getOneCustomer(int customerId) throws SQLException, MyException {
        if (customersDao.isCustomerExistsById(customerId))
        return customersDao.getOneCustomer(customerId);
        throw new MyException("This customer is not exists");
    }

}
