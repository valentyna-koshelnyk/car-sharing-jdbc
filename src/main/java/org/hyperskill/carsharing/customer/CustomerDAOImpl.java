package main.java.org.hyperskill.carsharing.customer;

import utils.ConnectionDB;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class CustomerDAOImpl implements CustomerDAO {
    private static final String SELECT_ALL = "SELECT * FROM CUSTOMER";
    private static final String SELECT_CUSTOMER = "SELECT * FROM CUSTOMER WHERE ID = ?";
    private static final String DELETE_CAR_FROM_CUSTOMER =
            "UPDATE CUSTOMER SET RENTED_CAR_ID = NULL WHERE ID = ?";
    private static final String ADD_CUSTOMER = "INSERT INTO Customer(Name, Rented_Car_ID) VALUES (?, ?)";
    private static final String UPDATE_CUSTOMER_CAR_ID = "UPDATE CUSTOMER SET RENTED_CAR_ID = ? WHERE ID = ?";
    private static final String SELECT_CAR_WITH_EMPTY_RENTCARID = "SELECT * FROM CUSTOMER WHERE RENTED_CAR_ID IS NOT NULL";

    //private static final String SELECT_CAR_CUSTOMER = "SELECT * FROM CUSTOMER WHERE RENTED_CAR_ID = ?";

    public List<Customer> getCustomerList() {
        List<Customer> customers = new ArrayList<>();
        try (Connection connection = ConnectionDB.getConnection()) {
            Statement stmt = connection.createStatement();
            ResultSet rs = stmt.executeQuery(SELECT_ALL);
            while (rs.next()) {
                Customer customer = new Customer(
                        rs.getInt("Id"),
                        rs.getString("NAME"),
                        rs.getInt("RENTED_CAR_ID")
                );
                customers.add(customer);
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return customers;
    }

    public List<Integer> getNotEmptyCarList() {
        List<Integer> carsEmpty = new ArrayList<>();
        try (Connection connection = ConnectionDB.getConnection()) {
            Statement stmt = connection.createStatement();
            ResultSet rs = stmt.executeQuery(SELECT_CAR_WITH_EMPTY_RENTCARID);
            while (rs.next()) {
                Customer customer = new Customer(
                        rs.getInt("Id"),
                        rs.getString("Name"),
                        rs.getInt("RENTED_CAR_ID"));
                carsEmpty.add(customer.getRentedCarId());
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return carsEmpty;
    }

//
//    }

    @Override
    public Customer getCustomer(int id) {
        try (Connection connection = ConnectionDB.getConnection()) {
            PreparedStatement ps = connection.prepareStatement(SELECT_CUSTOMER);
            ps.setInt(1, id);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                return new Customer(
                        rs.getInt("ID"),
                        rs.getString("NAME"),
                        rs.getInt("RENTED_CAR_ID")
                );
            }
            return null;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void deleteCustomer(int customerId) {
        try (Connection connection = ConnectionDB.getConnection()) {
            PreparedStatement ps = connection.prepareStatement(DELETE_CAR_FROM_CUSTOMER);
            ps.setInt(1, customerId);
            ps.executeUpdate();
        } catch (SQLException e) {
            System.out.println("Invalid request. Please try again with another query.");
        }
    }

    @Override
    public boolean addCustomer(Customer customer) {
        try (Connection connection = ConnectionDB.getConnection()) {
            PreparedStatement ps = connection.prepareStatement(ADD_CUSTOMER);
            ps.setString(1, customer.getName());
            ps.setNull(2, Types.INTEGER);
            ps.executeUpdate();
            return true;
        } catch (SQLException e) {
            System.out.println("Error adding customer: " + e.getMessage());
            return false;
        }
    }



    @Override
    public void updateCustomer(int rentCarId, Customer customer) {
        try (Connection connection = ConnectionDB.getConnection()) {
            PreparedStatement ps = connection.prepareStatement(UPDATE_CUSTOMER_CAR_ID);
            ps.setInt(1, rentCarId);
            ps.setInt(2, customer.getId());
            ps.executeUpdate();
        } catch (SQLException e) {
            System.out.println("Something went wrong. Please check your query");
        }
    }
}

//    @Override
//    public Car getCarListCustomer(int id) {
//        try (Connection connection = ConnectionDB.getConnection()) {
//            PreparedStatement ps = connection.prepareStatement(SELECT_CAR_CUSTOMER);
//            ps.setInt(1, "customer.getRentedCarId()");
//            ResultSet rs = ps.executeQuery();
//            if (rs.next()){
//                return new Customer(
//                        rs.getInt("ID"),
//                        rs.getString("NAME"),
//                        rs.getInt("RENTED_CAR_ID")
//                );
//            }
//            return null;
//        } catch (SQLException e) {
//            throw new RuntimeException(e);
//        }



