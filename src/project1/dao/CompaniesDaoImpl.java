package project1.dao;

import project1.Exception.MyException;
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

    /**
     * check if the company exist by email and password
     */
    @Override
    public int isCompanyExists(String email, String password) throws SQLException, MyException {
        Connection con = pool.getConnection();
        try {
            PreparedStatement statement = con.prepareStatement("select id from companies where email=? and password=?");
            statement.setString(1, email);
            statement.setString(2, password);
            ResultSet resultSet = statement.executeQuery();
            if (resultSet.next())
                return resultSet.getInt(1);
            return 0;
        } catch (SQLException e) {
            throw new MyException("System failed");
        } finally {
            pool.restoreConnection(con);
        }
    }

    /**
     * add company to the DB
     */
    @Override
    public void addCompany(Company company) throws SQLException {
        Connection con = pool.getConnection();
        try {
            PreparedStatement statement = con.prepareStatement("insert into companies (`name`, `email`, `password`) VALUES (?,?,?)");
            statement.setString(1, company.getName());
            statement.setString(2, company.getEmail());
            statement.setString(3, company.getPassword());
            statement.execute();
        } catch (Exception e) {
            throw new RuntimeException(e);
        } finally {
            pool.restoreConnection(con);

        }
    }

    /**
     * update company in the DB
     */
    @Override
    public void updateCompany(Company company) throws SQLException, MyException {
        Connection con = pool.getConnection();
        try {
            PreparedStatement statement = con.prepareStatement("update companies set email=?, password=? where id=?");
            statement.setString(1, company.getEmail());
            statement.setString(2, company.getPassword());
            statement.setInt(3, company.getId());
            statement.execute();
        } catch (SQLException e) {
            throw new MyException("THE UPDATE FAILED");
        } finally {
            pool.restoreConnection(con);
        }
    }

    /**
     * delete company from DB by company ID
     */
    @Override
    public void deleteCompany(int companyId) throws SQLException {
        Connection con = pool.getConnection();
        try {
            PreparedStatement statement = con.prepareStatement("delete from companies where id=?");
            statement.setInt(1, companyId);
            statement.execute();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        } finally {
            pool.restoreConnection(con);
        }
    }

    /**
     * get one company from DB by company ID
     */
    @Override
    public Company getOneCompany(int companyId) throws SQLException, MyException {
        Connection con = pool.getConnection();
        try {
            PreparedStatement statement = con.prepareStatement("select * from companies where id=?");
            statement.setInt(1, companyId);
            ResultSet resultSet = statement.executeQuery();
            if (resultSet.next()) {
                return new Company(resultSet.getInt(1), resultSet.getString(2), resultSet.getString(3),
                        resultSet.getString(4), getAllCouponByCompanyId(resultSet.getInt(1)));
            }
        } catch (SQLException e) {
            throw new MyException("the company not exist");
        } finally {
            pool.restoreConnection(con);

        }
        return null;
    }

    /**
     * get all the companies in the DB
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
        } catch (SQLException e) {
            throw new RuntimeException(e);
        } finally {
            pool.restoreConnection(con);
        }
    }

    /**
     * get all the coupons by company ID
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
        } catch (SQLException e) {
            throw new RuntimeException(e);
        } finally {
            pool.restoreConnection(con);
        }
    }

    /**
     * check if the email or the name is exists in the DB
     */
    @Override
    public boolean isNameAndEmailExist(String email, String name) throws SQLException, MyException {
        Connection con = pool.getConnection();
        try {
            PreparedStatement statement = con.prepareStatement("select email,password from companies where email=? or name=?");
            statement.setString(1, email);
            statement.setString(2, name);
            ResultSet resultSet = statement.executeQuery();
            return resultSet.next();
        } catch (SQLException e) {
            throw new MyException("Check if company exist by email and password failed");
        } finally {
            pool.restoreConnection(con);
        }
    }

    /**
     * delete purchase of coupon by coupon ID(use when delete company)*
     */
    public void deletePurchasesCouponsWhenDeleteCompany(int companyId) throws SQLException {
        Connection con = pool.getConnection();
        try {
            List<Coupon> coupons = getAllCouponByCompanyId(companyId);
            PreparedStatement statement = con.prepareStatement("delete from Customers_vs_coupons where coupon_id=?");
            for (Coupon coupon : coupons) {
                statement.setInt(1, coupon.getId());
                statement.execute();
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        } finally {
            pool.restoreConnection(con);
        }
    }

    /**
     * check if the company exist by ID
     */
    @Override
    public boolean isCompanyIdExist(int companyId) throws SQLException, MyException {
        Connection con = pool.getConnection();
        try {
            PreparedStatement statement = con.prepareStatement("select * from companies where id=?");
            statement.setInt(1, companyId);
            ResultSet resultSet = statement.executeQuery();
            return resultSet.next();
        } catch (SQLException e) {
            throw new MyException("This company not exist");
        } finally {
            pool.restoreConnection(con);
        }
    }


}
