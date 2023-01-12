package project1.dao;

import project1.beans.Category;
import project1.beans.Company;
import project1.beans.Coupon;
import project1.db.ConnectionPool;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class CompaniesDaoImpl implements CompaniesDao {
    private ConnectionPool pool = ConnectionPool.getInstance();

    @Override
    public boolean isCompanyExists(String email, String password) throws SQLException {
        Connection con = pool.getConnection();
        PreparedStatement statement = con.prepareStatement("select email,password from companies");
        ResultSet resultSet = statement.executeQuery();
        while (resultSet.next()) {
            if (resultSet.getString(1).equals(email) && resultSet.getString(2).equals(password)) {
                pool.restoreConnection(con);
                return true;
            }
        }
        pool.restoreConnection(con);
        return false;
    }

    @Override
    public void addCompany(Company company) throws SQLException {
        Connection con = pool.getConnection();
        PreparedStatement statement = con.prepareStatement("insert into companies (`name`, `email`, `password`) VALUES (?,?,?)");
        statement.setString(1, company.getName());
        statement.setString(2, company.getEmail());
        statement.setString(3, company.getPassword());
        statement.execute();
        pool.restoreConnection(con);
    }

    @Override
    public void updateCompany(Company company) throws SQLException {
        Connection con = pool.getConnection();
        PreparedStatement statement = con.prepareStatement("update companies set name=?, email=?, password=? where id=?");
        statement.setString(1,company.getName());
        statement.setString(2, company.getEmail());
        statement.setString(3, company.getPassword());
        statement.setInt(4, company.getId());
        statement.execute();
        pool.restoreConnection(con);
    }

    @Override
    public void deleteCompany(int companyId) throws SQLException {
        Connection con = pool.getConnection();
        PreparedStatement statement = con.prepareStatement("delete from companies where id=?");
        statement.setInt(1, companyId);
        statement.execute();
        pool.restoreConnection(con);
    }

    @Override
    public Company getOneCompany(int companyId) throws SQLException {
        Connection con = pool.getConnection();
        PreparedStatement statement = con.prepareStatement("select * from companies where id=?");
        statement.setInt(1, companyId);
        ResultSet resultSet = statement.executeQuery();
        if (resultSet.next()) {
            pool.restoreConnection(con);
            return new Company(resultSet.getInt(1), resultSet.getString(2), resultSet.getString(3),
                    resultSet.getString(4),getAllCouponByCompanyId(resultSet.getInt(1)));
        }
        pool.restoreConnection(con);
        return null;
    }

    @Override
    public List<Company> getAllCompanies() throws SQLException {
        Connection con = pool.getConnection();
        PreparedStatement statement = con.prepareStatement("select * from companies");
        ResultSet resultSet = statement.executeQuery();
        List<Company> companies = new ArrayList<>();
        while (resultSet.next()) {
            companies.add(new Company(resultSet.getInt(1), resultSet.getString(2), resultSet.getString(3),
                    resultSet.getString(4),getAllCouponByCompanyId(resultSet.getInt(1))));
        }
        pool.restoreConnection(con);
        return companies;
    }
    public List<Coupon> getAllCouponByCompanyId(int companyId) throws SQLException {
        Connection con = pool.getConnection();
        PreparedStatement statement = con.prepareStatement("select * from coupons where company_id=?");
        statement.setInt(1,companyId);
        ResultSet resultSet = statement.executeQuery();
        List<Coupon> coupons = new ArrayList<>();
        while (resultSet.next()){
            coupons.add(new Coupon(resultSet.getInt(1),resultSet.getInt(2), Category.values()[resultSet.getInt(3)-1],
                    resultSet.getString(4),resultSet.getString(5),resultSet.getDate(6),resultSet.getDate(7),
                    resultSet.getInt(8),resultSet.getDouble(9),resultSet.getString(10)));
        }
        pool.restoreConnection(con);
        return coupons;
    }

}
