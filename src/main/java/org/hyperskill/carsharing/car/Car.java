package main.java.org.hyperskill.carsharing.car;

public class Car {

    private int ID;
    public int getID() {
        return ID;
    }

    public void setID(int ID) {
        this.ID = ID;
    }

    public String getName() {
        return Name;
    }
    public void setName(String name) {
        Name = name;
    }

    public int getCompany_ID() {
        return Company_ID;
    }

    public void setCompany_ID(int company_ID) {
        Company_ID = company_ID;
    }

    private String Name;
    private int Company_ID;

    public Car() {
    }

    public Car(int ID, String name, int company_ID) {
        this.ID = ID;
        Name = name;
        Company_ID = company_ID;
    }
    @Override
    public String toString(){
        return ID + ". " + Name;
    }
}



