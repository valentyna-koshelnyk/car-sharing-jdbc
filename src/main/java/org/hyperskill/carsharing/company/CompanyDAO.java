package main.java.org.hyperskill.carsharing.company;

import java.util.List;

public interface CompanyDAO {
    List<Company> getCompanyList();
    Company getCompany(int id);
    void deleteCompany(String name);
    void addCompany(Company company);
    void updateCompany(Company company);
}
