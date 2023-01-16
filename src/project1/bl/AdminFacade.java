package project1.bl;

import project1.Exception.MyException;
import project1.beans.Company;
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
        if (companiesDao.isCompanyIdExist(company.getId())) {
            Company companyToUpdate = companiesDao.getOneCompany(company.getId());
            companyToUpdate.setEmail(company.getEmail());
            companyToUpdate.setPassword(company.getPassword());
            companiesDao.updateCompany(companyToUpdate);
            return;
        }
        throw new MyException("this company is not exist");
    }

    public void deleteCompany(int companyId) throws MyException, SQLException {
        if (companiesDao.isCompanyIdExist(companyId)) {
            companiesDao.deletePurchasesCouponsWhenDeleteCompany(companyId);
            couponsDao.deleteCouponByCompanyId(companyId);
            companiesDao.deleteCompany(companyId);
            return;
        }
        throw new MyException("this company is not exist");
    }

    public List<Company> getAllCompanies() throws SQLException {
        return companiesDao.getAllCompanies();
    }

    public Company getOneCompany(int companyId) throws SQLException, MyException {
        return companiesDao.getOneCompany(companyId);
    }

    public void addCustomer(Customer customer) throws SQLException, MyException {
        if (customersDao.isEmailExists(customer.getEmail()))
            throw new MyException("this email exists");
        customersDao.addCustomer(customer);
    }

    public void updateCustomer(Customer customer) throws SQLException, MyException {
        if (customersDao.isCustomerIdExists(customer.getId())) {
            customersDao.updateCustomer(customer);
        }
        throw new MyException("The customer not exist");
    }

    public void deleteCustomer(int customerId) throws SQLException, MyException {
        if (customersDao.isCustomerIdExists(customerId)) {
            customersDao.deleteCouponPurchasesByCustomerId(customerId);
            customersDao.deleteCustomer(customerId);
        }
        throw new MyException("this customer is not exists");
    }

    public List<Customer> getAllCustomers() throws SQLException {
        return customersDao.getAllCustomers();
    }

    public Customer getOneCustomer(int customerId) throws SQLException {
        return customersDao.getOneCustomer(customerId);
    }

}
