package main.java.org.hyperskill.carsharing;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;
import main.java.org.hyperskill.carsharing.view.Menu;

public class Main {
    static final String DB_URL = "jdbc:h2:./src/carsharing/db/carsharing";
    static final String USER = "";
    static final String PASS = "";

    public static void main(String[] args) {
            try (Connection conn = DriverManager.getConnection(DB_URL, USER, PASS);
                 Statement stmt = conn.createStatement()) {
                 conn.setAutoCommit(true);

                 String  createCompanyTable = "CREATE TABLE IF NOT EXISTS COMPANY (" +
                        "id INT PRIMARY KEY AUTO_INCREMENT," +
                        "name VARCHAR(255) UNIQUE NOT NULL" +
                        ")";
                 String createCarTable = "CREATE TABLE IF NOT EXISTS CAR (" +
                         "ID INT PRIMARY KEY AUTO_INCREMENT ," +
                         "NAME VARCHAR(255) NOT NULL," +
                         "COMPANY_ID INT NOT NULL,"+
                         "FOREIGN KEY (COMPANY_ID) REFERENCES COMPANY(ID)" +
                         ")";
                 String createCustomerTable = "CREATE TABLE IF NOT EXISTS CUSTOMER (" +
                         "ID INT PRIMARY KEY AUTO_INCREMENT," +
                         "NAME VARCHAR(255) NOT NULL," +
                         " RENTED_CAR_ID INT," +
                         " FOREIGN KEY (RENTED_CAR_ID) REFERENCES CAR(ID)" +
                         ");";

               String dropCustomerTable = "DROP TABLE CUSTOMER";
               String dropCarTable = "DROP TABLE CAR";
               String dropCompanyTable = "DROP TABLE COMPANY";
               stmt.executeUpdate(dropCustomerTable);
               stmt.executeUpdate(dropCarTable);

              stmt.executeUpdate(dropCompanyTable);
              stmt.executeUpdate(createCarTable);
              stmt.executeUpdate(createCustomerTable);
              stmt.executeUpdate(createCustomerTable);

              Menu menu = new Menu();
              menu.loginMenu();

            } catch (SQLException e) {
                e.printStackTrace();
            }

        }
    }



