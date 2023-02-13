package project1.Test;

import project1.Exception.MyException;
import project1.beans.Company;
import project1.beans.Customer;
import project1.bl.AdminFacade;
import project1.dao.CompaniesDaoImpl;
import project1.login.ClientType;
import project1.login.LoginManager;

import java.sql.SQLException;
import java.util.List;

public class TestAdmin {
    private AdminFacade adminFacade;
    public void runAllAdminFacadeTest(){
        login();
        if (adminFacade!=null){
//            addCompany();
//            updateCompany();
//            deleteCompany();
//            getAllCompanies();
//            getOneCompany();
//            addCustomer();
//           updateCustomer();
//           deleteCustomer();
//            getAllCustomers();
//            getOneCustomer();

        }
    }

    private void getOneCustomer() {
        System.out.println("📢getOneCustomer📢");
        try {
            List<Customer>customers=adminFacade.getAllCustomers();
            System.out.println(adminFacade.getOneCustomer(customers.get(0).getId()));
        } catch (SQLException | MyException|IndexOutOfBoundsException e) {
            System.out.println(e.getMessage());
        }
    }

    private void getAllCustomers() {
        System.out.println("📢getAllCustomers📢");
        try {
            System.out.println(adminFacade.getAllCustomers());
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }

    private void deleteCustomer() {
        System.out.println("📢delete customer📢");
        try {
            List<Customer>customers=adminFacade.getAllCustomers();
            adminFacade.deleteCustomer(customers.get(0).getId());
            adminFacade.deleteCustomer(customers.get(1).getId());
        } catch (MyException | SQLException|IndexOutOfBoundsException e) {
            System.out.println(e.getMessage());
        }
    }

    private void updateCustomer() {
        System.out.println("📢updateCustomer📢");
        try {
            List<Customer>customers=adminFacade.getAllCustomers();
            Customer customer1=customers.get(0);
            customer1.setEmail("9876@gmail.com");
            Customer customer2=customers.get(1);
            customer2.setFirstName("Aviadush");
            Customer customer3=customers.get(2);
            customer3.setLastName("Danino");
            adminFacade.updateCustomer(customer1);
            adminFacade.updateCustomer(customer2);
            adminFacade.updateCustomer(customer3);
        } catch (SQLException| MyException |IndexOutOfBoundsException e) {
            System.out.println(e.getMessage());
        }
    }

    private void addCustomer() {
        System.out.println("📢addCustomer📢");
        Customer customer1=new Customer("Malki","Gefner","malki@gmail.com","malki1234");
        Customer customer2=new Customer("Aviad","Barel","Aviad@gmail.com","Aviad1234");
        Customer customer3=new Customer("Esti","Rayniz","Esti@gmail.com","Esti1234");
        Customer customer4=new Customer("Nir","Gal","Nir@gmail.com","Nir1234");
        Customer customer5=new Customer("Buchanan","Gershoni","Buchanan@gmail.com","malki1234");
        try {
            adminFacade.addCustomer(customer1);
            adminFacade.addCustomer(customer2);
            adminFacade.addCustomer(customer3);
            adminFacade.addCustomer(customer4);
            adminFacade.addCustomer(customer5);
        } catch (MyException | SQLException e) {
            System.out.println(e.getMessage());
        }

    }

    private void getOneCompany() {
        System.out.println("📢getOneCompany📢");
        try {
            List<Company>companies=adminFacade.getAllCompanies();
            System.out.println(adminFacade.getOneCompany(companies.get(0).getId()));
        } catch (SQLException | MyException |IndexOutOfBoundsException e) {
            System.out.println(e.getMessage());
        }
    }

    private void getAllCompanies() {
        System.out.println("📢getAllCompanies📢");
        try {
            System.out.println(adminFacade.getAllCompanies());
        } catch (SQLException | MyException e) {
            System.out.println(e.getMessage());
        }
    }

    private void deleteCompany() {
        System.out.println("📢delete company📢");
        try {
            List<Company>companies=adminFacade.getAllCompanies();
            adminFacade.deleteCompany(companies.get(0).getId());
            adminFacade.deleteCompany(companies.get(1).getId());
        } catch (MyException | SQLException|IndexOutOfBoundsException e) {
            System.out.println(e.getMessage());
        }

    }

    private void updateCompany() {
        System.out.println("📢update company📢");
        try {
            List<Company>companies=adminFacade.getAllCompanies();
            Company company1=adminFacade.getOneCompany(companies.get(0).getId());
            company1.setPassword("Baby6789");
            Company company2=adminFacade.getOneCompany(companies.get(1).getId());
            company2.setPassword("Ariel6789");
            Company company3=adminFacade.getOneCompany(companies.get(2).getId());
            company3.setPassword("Elite6789");
            adminFacade.updateCompany(company1);
            adminFacade.updateCompany(company2);
            adminFacade.updateCompany(company3);
        } catch (SQLException| MyException|IndexOutOfBoundsException e) {
            System.out.println(e.getMessage());
        }
    }

    private void addCompany() {
        System.out.println("📢add company📢");
        Company company1=new Company("Baby Star","Baby@gmail.com","Baby1234");
        Company company2=new Company("Ariel","Ariel@gmail.com","Ariel1234");
        Company company3=new Company("Elite","Elite@gmail.com","Elite1234");
        Company company4=new Company("Tnuva","Tnuva@gmail.com","Tnuva1234");
        Company company5=new Company("Nirteck","Nirteck@gmail.com","Nirteck1234");
        try {
            adminFacade.addCompany(company1);
            adminFacade.addCompany(company2);
            adminFacade.addCompany(company3);
            adminFacade.addCompany(company4);
            adminFacade.addCompany(company5);
        } catch (MyException | SQLException e) {
            System.out.println(e.getMessage());
        }

    }

    private void login() {
        try {
            adminFacade= (AdminFacade) LoginManager.getInstance().login("admin@admin.com","admin", ClientType.Administrator);
        } catch (MyException | SQLException e) {
            System.out.println(e.getMessage());
        }
    }
}
