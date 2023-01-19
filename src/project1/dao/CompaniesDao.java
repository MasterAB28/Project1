package project1.dao;

import project1.Exception.MyException;
import project1.beans.Company;
import project1.beans.Coupon;

import java.sql.SQLException;
import java.util.List;
import java.util.function.BinaryOperator;

public interface CompaniesDao {
    int isCompanyExists(String email, String password) throws SQLException, MyException;

    void addCompany(Company company) throws SQLException;

    void updateCompany(Company company) throws SQLException, MyException;

    void deleteCompany(int companyId) throws SQLException;

    Company getOneCompany(int companyId) throws SQLException, MyException;

    List<Company> getAllCompanies() throws SQLException;

    List<Coupon> getAllCouponByCompanyId(int companyId) throws SQLException;

    boolean isNameAndEmailExist(String email, String name) throws SQLException, MyException;

    void deletePurchasesCouponsWhenDeleteCompany(int companyId) throws SQLException;

    boolean isCompanyIdExist(int companyId) throws SQLException, MyException;



}
