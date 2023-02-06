package project1.dao;

import project1.beans.Category;
import project1.beans.Company;
import project1.beans.Coupon;
import project1.db.ConnectionPool;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class CompaniesDaoImpl implements CompaniesDao {
    private ConnectionPool pool = ConnectionPool.getInstance();

    /**
     * The method receives company's email and password and checks if the company exists in the 'companies' db
     * @param email -company email
     * @param password-company password
     * @return company id
     * @throws SQLException if the sql method is not working properly
     */
    @Override
    public int isCompanyExists(String email, String password) throws SQLException {
        Connection con = pool.getConnection();
        try {
            PreparedStatement statement = con.prepareStatement("select id from companies where email=? and password=?");
            statement.setString(1, email);
            statement.setString(2, password);
            ResultSet resultSet = statement.executeQuery();
            if (resultSet.next())
                return resultSet.getInt(1);
            return -1;
        }  finally {
            pool.restoreConnection(con);
        }
    }

    /**
     *The method adds company to the db
     * @param company- name, email and password of the company
     * @throws SQLException if the sql method is not working properly
     */
    @Override
    public void addCompany(Company company) throws SQLException {
        Connection con = pool.getConnection();
        try {
            PreparedStatement statement = con.prepareStatement("insert into companies (`name`, `email`, `password`) VALUES (?,?,?)",
                    Statement.RETURN_GENERATED_KEYS);
            statement.setString(1, company.getName());
            statement.setString(2, company.getEmail());
            statement.setString(3, company.getPassword());
            statement.execute();
        } finally {
            pool.restoreConnection(con);

        }
    }

    /**
     * The method updates company in the db
     * @param company -email,password and id of the company
     * @throws SQLException if the sql method is not working properly
     */
    @Override
    public void updateCompany(Company company) throws SQLException {
        Connection con = pool.getConnection();
        try {
            PreparedStatement statement = con.prepareStatement("update companies set email=?, password=? where id=?");
            statement.setString(1, company.getEmail());
            statement.setString(2, company.getPassword());
            statement.setInt(3, company.getId());
            statement.execute();
        } finally {
            pool.restoreConnection(con);
        }
    }

    /**
     * The method deletes company from the db by id
     * @param companyId- company id
     * @throws SQLException if the sql method is not working properly
     */
    @Override
    public void deleteCompany(int companyId) throws SQLException {
        Connection con = pool.getConnection();
        try {
            PreparedStatement statement = con.prepareStatement("delete from companies where id=?");
            statement.setInt(1, companyId);
            statement.execute();
        } finally {
            pool.restoreConnection(con);
        }
    }

    /**
     * The method returns one company from the db by id
     * @param companyId- company id
     * @return one company, the details are:id,name, email, password and list of the company coupons
     * @throws SQLException if the sql method is not working properly
     */
    @Override
    public Company getOneCompany(int companyId) throws SQLException {
        Connection con = pool.getConnection();
        try {
            PreparedStatement statement = con.prepareStatement("select * from companies where id=?");
            statement.setInt(1, companyId);
            ResultSet resultSet = statement.executeQuery();
            if (resultSet.next()) {
                return new Company(resultSet.getInt(1), resultSet.getString(2), resultSet.getString(3),
                        resultSet.getString(4), getAllCouponByCompanyId(resultSet.getInt(1)));
            }
        } finally {
            pool.restoreConnection(con);

        }
        return null;
    }

    /**
     * The method returns all the companies from the db
     * @return list of all the companies, the details are:id,name, email, password and list of the company coupons for which company
     * @throws SQLException if the sql method is not working properly
     */
    @Override
    public List<Company> getAllCompanies() throws SQLException {
        Connection con = pool.getConnection();
        try {
            PreparedStatement statement = con.prepareStatement("select * from companies");
            ResultSet resultSet = statement.executeQuery();
            List<Company> companies = new ArrayList<>();
            while (resultSet.next()) {
                companies.add(new Company(resultSet.getInt(1), resultSet.getString(2), resultSet.getString(3),
                        resultSet.getString(4), getAllCouponByCompanyId(resultSet.getInt(1))));
            }
            return companies;
        } finally {
            pool.restoreConnection(con);
        }
    }

    /**
     * The method returns all the coupons of company from the db by company id
     * @param companyId company id
     * @return a list of company coupons
     * @throws SQLException if the sql method is not working properly
     */
    public List<Coupon> getAllCouponByCompanyId(int companyId) throws SQLException {
        Connection con = pool.getConnection();
        try {
            PreparedStatement statement = con.prepareStatement("select * from coupons where company_id=?");
            statement.setInt(1, companyId);
            ResultSet resultSet = statement.executeQuery();
            List<Coupon> coupons = new ArrayList<>();
            while (resultSet.next()) {
                coupons.add(new Coupon(resultSet.getInt(1), resultSet.getInt(2), Category.values()[resultSet.getInt(3) - 1],
                        resultSet.getString(4), resultSet.getString(5), resultSet.getDate(6), resultSet.getDate(7),
                        resultSet.getInt(8), resultSet.getDouble(9), resultSet.getString(10)));
            }
            return coupons;
        } finally {
            pool.restoreConnection(con);
        }
    }

    /**
     *The method checks if the company exists in the db by name or email
     * @param name company name
     * @param email company email
     * @return true or false
     * @throws SQLException if the sql method is not working properly
     */
    @Override
    public boolean isNameOrEmailExist(String email, String name) throws SQLException {
        Connection con = pool.getConnection();
        try {
            PreparedStatement statement = con.prepareStatement("select email,password from companies where email=? or name=?");
            statement.setString(1, email);
            statement.setString(2, name);
            ResultSet resultSet = statement.executeQuery();
            return resultSet.next();
        } finally {
            pool.restoreConnection(con);
        }
    }


    /**
     * The method checks if the company exists in the db by id
     * @param companyId the id of the company
     * @return true or false -if the company id exists or no
     * @throws SQLException if the sql method is not working properly
     */
    @Override
    public boolean isCompanyExistById(int companyId) throws SQLException {
        Connection con = pool.getConnection();
        try {
            PreparedStatement statement = con.prepareStatement("select * from companies where id=?");
            statement.setInt(1, companyId);
            ResultSet resultSet = statement.executeQuery();
            return resultSet.next();
        } finally {
            pool.restoreConnection(con);
        }
    }


}
