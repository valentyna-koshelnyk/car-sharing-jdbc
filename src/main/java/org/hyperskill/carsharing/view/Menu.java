package main.java.org.hyperskill.carsharing.view;


import main.java.org.hyperskill.carsharing.car.Car;
import main.java.org.hyperskill.carsharing.car.CarDAOImpl;
import main.java.org.hyperskill.carsharing.company.Company;
import main.java.org.hyperskill.carsharing.company.CompanyDAOImpl;
import main.java.org.hyperskill.carsharing.customer.Customer;
import main.java.org.hyperskill.carsharing.customer.CustomerDAOImpl;

import java.util.List;
import java.util.Scanner;

public class Menu {
    private final Scanner scanner;
    private final Scanner stringScanner;

    private final CompanyDAOImpl companyDAO;
    private final CarDAOImpl carDAO;
    private final Car car;
    private final CustomerDAOImpl customerDAO;

//the project without Spring Boot, so we follow bad practices for initialization
    public Menu() {
        this.customerDAO = new CustomerDAOImpl();
        this.scanner = new Scanner(System.in);
        this.stringScanner = new Scanner(System.in);
        this.companyDAO = new CompanyDAOImpl();
        this.carDAO = new CarDAOImpl();
        this.car = new Car();

    }

    public void loginMenu() {
        System.out.println("1. Log in as a manager\n" +
                "2. Log in as a customer\n" +
                "3. Create a customer\n" +
                "0. Exit");
        int response = scanner.nextInt();
        switch (response) {
            case 0:
                System.exit(0);
            case 1:
                managerMenu();
                break;
            case 2:
                customerListMenu();
                break;
            case 3:
                createCustomer();
            default:
                throw new IllegalStateException("Unexpected value: " + response);
        }
    }

    public void createCustomer(){
        System.out.println("Enter the customer name: ");
        String name = stringScanner.nextLine();
        Customer customer = new Customer();
        customer.setName(name);
        boolean isAdded = customerDAO.addCustomer(customer);

        if(isAdded) {
            System.out.println("The customer was added!");
        } else {
            System.out.println("There was a problem with adding the customer");
        }
        loginMenu();
    }

    public void customerListMenu(){
        List<Customer> customerDAOList = customerDAO.getCustomerList();
        if(!customerDAOList.isEmpty()) {
            System.out.println("Choose a customer: ");
            for (Customer customer : customerDAOList) {
                System.out.println(customer);
            }
            System.out.println("0. Back");
            int customerId = scanner.nextInt();
            if (customerId == 0) {
                managerMenu();
            } else {
                customerCarMenu(customerId);
            }
        } else {
            System.out.println("The customer list is empty!");
            loginMenu();
        }
    }

    public void customerCarMenu(int customerId){
        System.out.println("1. Rent a car\n" +
                "2. Return a rented car\n" +
                "3. My rented car\n" +
                "0. Back");
        int choice = scanner.nextInt();
        switch (choice){
            case 1: rentCar(customerId);
            case 2:
                returnCar(customerId);
            case 3:
                customerCarList(customerId);
            case 0:
                loginMenu();
        }
    }

    public void customerCarList(int customerId) {
        Customer customer = customerDAO.getCustomer(customerId);
        if (customer.getRentedCarId() != 0) {
            int carId = customer.getRentedCarId();
            Car car = carDAO.getCar(carId);
            String carName = car.getName();
            int companyId = car.getCompany_ID();
            Company company = companyDAO.getCompany(companyId);
            String companyName = company.getName();
            System.out.println("Your rented car: " + carName);
            System.out.println("Company: " + companyName);
            customerCarMenu(customerId);
        } else {
            System.out.println("You didn't rent a car!");
            customerCarMenu(customerId);
        }
    }
    public void returnCar(int customerId) {
        Customer customer = customerDAO.getCustomer(customerId);
        System.out.println(customer.getRentedCarId());
        if (customer.getRentedCarId() != 0) {
            customerDAO.deleteCustomer(customerId);
            System.out.println("You've returned" +
                    " a rented car!");
        } else {
            System.out.println("You didn't rent a car!\n");
        }
        customerCarMenu(customerId);
    }

    public void rentCar(int customerId){
        Customer customer = customerDAO.getCustomer(customerId);
        if (customer.getRentedCarId() != 0) {
            System.out.println("You've already rented a car!");
            customerCarMenu(customerId);
        }
        else {
            System.out.println("Choose a company: ");
            List<Company> companyList = companyDAO.getCompanyList();
            for(Company company: companyList){
                System.out.println(company);
            }
            System.out.println("0. Back");
            int companyId = scanner.nextInt();
            if (companyId != 0){
                System.out.println("Choose a car: ");
            }else {
                customerCarMenu(customerId);
            }

            List<Car> carDAOCarList =  carDAO.getCarList(companyId);

            if (!carDAOCarList.isEmpty()) {
                int num = 1;
                List<Integer> carRented = customerDAO.getNotEmptyCarList();
                for (Car car : carDAOCarList) {
                    if (!carRented.contains(car.getID())) {
                        System.out.println(num + ". " + car.getName());
                        num++;
                    }

                }
            } else {
                System.out.println("The car list is empty!");
            }
            int carId = scanner.nextInt();
            if(carId != 0){
            customer.setRentedCarId(carId);
            customerDAO.updateCustomer(carId, customer);
            System.out.println(customer.getRentedCarId());
            Car carSelected = carDAO.getCar(carId);
            String carName = carSelected.getName();
            System.out.println("You rented '" + carName+ "'");
            } else {
                customerCarMenu(customerId);
            }
            customerCarMenu(customerId);
        }

        }



    public void managerMenu() {
        System.out.println("1. Company list\n2. Create a company\n0. Back");
        int response = scanner.nextInt();
        switch (response) {
            case 1:
                getCompanyList();
                carMenu();
                break;
            case 2:
                createCompany();
                managerMenu();
                break;
            case 0:
                loginMenu();
                break;
            default:
                throw new IllegalStateException("Unexpected value: " + response);
        }
    }


    public void managerMenu(int response) {
        System.out.println("1. Company list\n" +
                "2. Create a company\n" +
                "0. Back");
        switch (response) {
            case 1 -> {
                getCompanyList();
                carMenu();
            }
            case 2 -> {
                createCompany();
                managerMenu();
            }
            case 0 -> loginMenu();

            default -> throw new IllegalStateException("Unexpected value: " + response);
        }
    }

    public void carMenu() {
        int company = scanner.nextInt();
        System.out.println("1.Car List" +
                "\n 2. Create a car" +
                "\n 0. Back");
        int menu = scanner.nextInt();
        switch (menu) {
            case 1 -> {
                getCarList(company);
                carMenu();
            }
            case 2 -> {
                createCar(company);
                carMenu();
            }
            case 0 -> {
                managerMenu();
            }
        }
    }

    public void carMenu(int company) {
        System.out.println(" 1. Car list" +
                "\n 2. Create a car" +
                "\n 0. Back");
        int menu = scanner.nextInt();
        switch (menu) {
            case 1 -> {
                getCarList(company);
                carMenu();
            }
            case 2 -> {
                createCar(company);
                carMenu();
            }
            case 0 -> {
                managerMenu();
            }
        }
    }
    public List<Company> getCompanyList(){
         List<Company>companyDAOList =  companyDAO.getCompanyList();
        if (!companyDAOList.isEmpty()){
            System.out.println("Choose the company: ");
                for (Company company: companyDAOList) {
                    System.out.println(company);
                }
            System.out.println("0. Back");
            int answer = scanner.nextInt();
            switch (answer){
                default -> carMenu(answer);
                case 0 -> managerMenu();
            }
        } else {
                System.out.println("The company list is empty");
                managerMenu();
            }
        carMenu();
            return (List<Company>) companyDAOList;
        }


    public void createCompany(){
        System.out.println("Enter the company name:");
        String name = stringScanner.nextLine();
        Company company = new Company( );
        company.setName(name);
        companyDAO.addCompany(company);
        System.out.println("The company was created");
        managerMenu();
    }

    public void createCar(int id) {
        System.out.println("Enter the car name: ");
        String name = stringScanner.nextLine();
        car.setName(name);
        car.setCompany_ID(id);
        carDAO.addCar(car);
        System.out.println("The car was added!");
        showCarMenu(id);
    }

    private void showCarMenu(int company) {
        System.out.println("1. Car list\n2. Create a car\n0. Back");
        int menu = scanner.nextInt();
        switch (menu) {
            case 1:
                getCarList(company);
                showCarMenu(company);
                break;
            case 2:
                createCar(company);
                showCarMenu(company);
                break;
            case 0:
                managerMenu();
                break;
            default:
                throw new IllegalStateException("Unexpected value: " + menu);
        }
    }
    public List<Car> getCarList(int id){
        List<Car> carDAOCarList =  carDAO.getCarList(id);
        if (!carDAOCarList.isEmpty()){
            int num = 1;
            for (Car car : carDAOCarList) {
                System.out.println(num + ". " + car.getName());
                num++;
        } }else {
            System.out.println("The car list is empty!");
        }
        int company = id;
        System.out.println(" 1. Car list" +
                "\n 2. Create a car" +
                "\n 0. Back");
        int menu = scanner.nextInt();
        switch (menu) {
            case 1 -> {
                getCarList(company);
            }
            case 2 -> {
                createCar(company);
            }
            case 0 -> managerMenu();
        }
        return (List<Car>) carDAOCarList;
    }

    }






