package main.java.org.hyperskill.carsharing.car;

import java.util.List;

public interface CarDAO{
    List<Car> getCarList(int id);
    Car getCar(int id);
    void deleteCar(String name);
    void addCar(Car car);
    void updateCar(Car car);
}
