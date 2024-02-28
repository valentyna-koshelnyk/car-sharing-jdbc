package main.java.org.hyperskill.carsharing.car;

import utils.ConnectionDB;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class CarDAOImpl implements CarDAO {
    private static final String SELECT_ALL = "SELECT * FROM car WHERE Company_ID = ? ORDER BY ID";
    private static final String SELECT_CAR = "SELECT * FROM car WHERE Company_ID = ?";
    private static final String DELETE_CAR = "DELETE FROM car WHERE Name = ?";
    private static final String ADD_CAR = "INSERT INTO CAR(Name, Company_ID) VALUES (?,?)";
    private static final String UPDATE_CAR = "Update CAR SET Name = ? WHERE ID = ?";

    public List<Car> getCarList(int id){
        List<Car> cars = new ArrayList<>();
        try(Connection connection = ConnectionDB.getConnection()){
            PreparedStatement ps  = connection.prepareStatement(SELECT_ALL);
            ps.setInt(1, id);
            ResultSet rs = ps.executeQuery();
            while (rs.next()){
               Car car = new Car(
                        rs.getInt("ID"),
                        rs.getString("Name"),
                        rs.getInt("Company_ID")
                );
                cars.add(car);
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return cars;
    }



    @Override
    public Car getCar(int id) {
        try(Connection connection = ConnectionDB.getConnection()){
            PreparedStatement ps = connection.prepareStatement(SELECT_CAR );
            ps.setInt(1, id);
            ResultSet rs = ps.executeQuery();
            if (rs.next()){
                return new Car(
                        rs.getInt("ID"),
                        rs.getString("Name"),
                        rs.getInt("Company_ID"));
        }
            return null;
    } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

        @Override
    public void deleteCar(String name) {
            try (Connection connection = ConnectionDB.getConnection()) {
                PreparedStatement ps = connection.prepareStatement(DELETE_CAR);
                ps.setString(1, "Name");
                ps.executeUpdate();
            } catch (SQLException e) {
                System.out.println("Invalid request. Please try again with another query.");;
            }
        }

    @Override
    public void addCar(Car car) {
        try (Connection connection = ConnectionDB.getConnection()) {
            PreparedStatement ps = connection.prepareStatement(ADD_CAR);
            ps.setString(1, car.getName());
            ps.setInt(2, car.getCompany_ID());
            ps.executeUpdate();
        } catch (SQLException e) {
            System.out.println("404: Bad Request. Check the query");;
        }
    }
    @Override
    public void updateCar(Car car) {
        try (Connection connection = ConnectionDB.getConnection()) {
            PreparedStatement ps = connection.prepareStatement(UPDATE_CAR);
            ps.setString(1, car.getName());
            ps.setInt(2, car.getID());
            ps.executeUpdate();
        } catch (SQLException e) {
            System.out.println("Something went wrong. Please check your query");;
        }
    }


    }







