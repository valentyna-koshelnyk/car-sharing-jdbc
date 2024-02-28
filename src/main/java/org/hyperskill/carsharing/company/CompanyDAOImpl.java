package main.java.org.hyperskill.carsharing.company;

import utils.ConnectionDB;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class CompanyDAOImpl implements CompanyDAO {

    private static final String SELECT_ALL = "SELECT * FROM company";
    private static final String SELECT_COMPANY = "SELECT * FROM company WHERE Id = ?";
    private static final String DELETE_COMPANY = "DELETE FROM company WHERE Name = ?";
    private static final String ADD_COMPANY= "INSERT INTO Company(Name) VALUES (?)";
    private static final String UPDATE_COMPANY = "Update Company SET Name = ? WHERE ID = ?";

    public List<Company> getCompanyList(){
        List<Company> companies = new ArrayList<>();
        try(Connection connection = ConnectionDB.getConnection()){
            Statement stmt = connection.createStatement();
            ResultSet rs = stmt.executeQuery(SELECT_ALL);
            while (rs.next()){
                Company company = new Company(
                        rs.getInt("ID"),
                        rs.getString("Name")
                );
                companies.add(company);

            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return companies;
    }

    @Override
    public Company getCompany(int id) {
        try(Connection connection = ConnectionDB.getConnection()){
            PreparedStatement ps = connection.prepareStatement(SELECT_COMPANY );
            ps.setInt(1,id);
            ResultSet rs = ps.executeQuery();
            if (rs.next()){
                return new Company(
                        rs.getInt("ID"),
                        rs.getString("Name"));
            }
            return null;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }


    @Override
    public void deleteCompany(String name) {
        try (Connection connection = ConnectionDB.getConnection()) {
            PreparedStatement ps = connection.prepareStatement(DELETE_COMPANY);
            ps.setString(1, "Name");
            ps.executeUpdate();
        } catch (SQLException e) {
            System.out.println("Invalid request. Please try again with another query.");;
        }
    }

    @Override
    public void addCompany(Company company) {
        try (Connection connection = ConnectionDB.getConnection()) {
            PreparedStatement ps = connection.prepareStatement(ADD_COMPANY);
            ps.setString(1, company.getName());
            ps.executeUpdate();
        } catch (SQLException e) {
            System.out.println("404: Bad Request. Check the query");;
        }
    }

    @Override
    public void updateCompany(Company company) {
        try (Connection connection = ConnectionDB.getConnection()) {
            PreparedStatement ps = connection.prepareStatement(UPDATE_COMPANY);
            ps.setString(1, company.getName());
            ps.setInt(2, company.getId());
            ps.executeUpdate();
        } catch (SQLException e) {
            System.out.println("Something went wrong. Please check your query");;
        }
    }

    }


