package main.java.org.hyperskill.carsharing.company;

public class Company {
    private int id;
    private String Name;

    public Company(int id, String name) {
        this.id = id;
        Name = name;
    }

    public Company() {
    }

    public Company(String name) {
        Name = name;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return Name;
    }

    public void setName(String name) {
        Name = name;
    }
    @Override
    public String toString(){
        return id + ". " + Name;
    }
}
