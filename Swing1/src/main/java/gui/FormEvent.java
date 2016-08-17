package gui;

import model.AgeCategory;
import model.EmploymentCategory;
import model.Gender;

import java.util.EventObject;

public class FormEvent extends EventObject {

    private String name;
    private String occupation;
    private EmploymentCategory employeeCaterory;
    private AgeCategory ageCategory;
    private boolean usCitizen;
    private String taxId;
    private String gender;

    public FormEvent(Object source) {
        super(source);
    }

    public FormEvent(Object source, String name, AgeCategory ageCategory, EmploymentCategory employeeCategory, String gender, String occupation, String taxId, boolean usCitizen) {
        super(source);
        this.name = name;
        this.ageCategory = ageCategory;
        this.employeeCaterory = employeeCategory;
        this.gender = gender;
        this.occupation = occupation;
        this.taxId = taxId;
        this.usCitizen = usCitizen;
    }

    public AgeCategory getAgeCategory() {
        return ageCategory;
    }

    public EmploymentCategory getEmployeeCategory() {
        return employeeCaterory;
    }

    public String getGender() {
        return gender;
    }

    public String getName() {
        return name;
    }

    public String getOccupation() {
        return occupation;
    }

    public String getTaxId() {
        return taxId;
    }

    public boolean isUsCitizen() {
        return usCitizen;
    }
}