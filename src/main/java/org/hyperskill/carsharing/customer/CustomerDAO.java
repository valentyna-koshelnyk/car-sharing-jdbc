package main.java.org.hyperskill.carsharing.customer;

import java.util.List;

public interface CustomerDAO {
    Customer getCustomer(int id);
    List<Customer> getCustomerList();
    void deleteCustomer(int customerId);
    boolean addCustomer(Customer customer);
    void updateCustomer(int rentCarId, Customer customer);
  // Car getCarListCustomer(int id);


}
