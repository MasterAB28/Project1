package project1.bl;

import project1.Exception.MyException;
import project1.beans.Company;
import project1.beans.Coupon;
import project1.beans.Customer;

import java.sql.SQLException;
import java.util.List;

public class AdminFacade extends ClientFacade {

    public AdminFacade() {
    }

    @Override
    public boolean login(String email, String password) {
        return email.equals("admin@admin.com") && password.equals("admin");
    }

    public void addCompany(Company company) throws MyException, SQLException {
        if (companiesDao.isNameAndEmailExist(company.getEmail(), company.getName())) {
            throw new MyException("Company email or name exists");
        }
        companiesDao.addCompany(company);
    }

    public void updateCompany(Company company) throws SQLException, MyException {
        if (companiesDao.isCompanyExistById(company.getId())) {
            companiesDao.updateCompany(company);
            return;
        }
        throw new MyException("The company update failed");
    }

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

    public List<Company> getAllCompanies() throws SQLException, MyException {
        return companiesDao.getAllCompanies();
    }

    public Company getOneCompany(int companyId) throws SQLException, MyException {
        if (companiesDao.isCompanyExistById(companyId))
        return companiesDao.getOneCompany(companyId);
        throw new MyException("This company is not exists");
    }

    public void addCustomer(Customer customer) throws SQLException, MyException {
        if (customersDao.isEmailExists(customer.getEmail()))
            throw new MyException("This email exists");
        customersDao.addCustomer(customer);
    }

    public void updateCustomer(Customer customer) throws SQLException, MyException {
        if (customersDao.isCustomerExistsById(customer.getId())) {
            customersDao.updateCustomer(customer);
        }
        throw new MyException("The customer update is failed");
    }

    public void deleteCustomer(int customerId) throws SQLException, MyException {
        if (customersDao.isCustomerExistsById(customerId)) {
            customersDao.deleteCouponPurchasesByCustomerId(customerId);
            customersDao.deleteCustomer(customerId);
        }
        throw new MyException("The customer delete is failed");
    }

    public List<Customer> getAllCustomers() throws SQLException, MyException {
        return customersDao.getAllCustomers();
    }

    public Customer getOneCustomer(int customerId) throws SQLException, MyException {
        if (customersDao.isCustomerExistsById(customerId))
        return customersDao.getOneCustomer(customerId);
        throw new MyException("This customer is not exists");
    }

}
