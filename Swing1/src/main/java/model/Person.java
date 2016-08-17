package model;

import java.io.Serializable;

public class Person implements Serializable{

    private static int count = 1;


    private int id;
    private String name;
    private String occupation;
    private EmploymentCategory employeeCategory;
    private AgeCategory ageCategory;
    private boolean usCitizen;
    private String taxId;
    private Gender gender;

    public Person(String name, String occupation, EmploymentCategory employeeCategory, AgeCategory ageCategory, boolean usCitizen, String taxId, Gender gender) {
        this.name = name;
        this.occupation = occupation;
        this.employeeCategory = employeeCategory;
        this.ageCategory = ageCategory;
        this.usCitizen = usCitizen;
        this.taxId = taxId;
        this.gender = gender;

        this.id= count;
        count++;
    }

    public Person(int id ,String name, String occupation, EmploymentCategory employeeCategory, AgeCategory ageCategory, boolean usCitizen, String taxId, Gender gender) {
        this(name,occupation,employeeCategory,ageCategory,usCitizen,taxId,gender);
        this.id = id;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getOccupation() {
        return occupation;
    }

    public void setOccupation(String occupation) {
        this.occupation = occupation;
    }

    public EmploymentCategory getEmployeeCategory() {
        return employeeCategory;
    }

    public void setEmployeeCategory(EmploymentCategory employeeCategory) {
        this.employeeCategory = employeeCategory;
    }

    public AgeCategory getAgeCategory() {
        return ageCategory;
    }

    public void setAgeCategory(AgeCategory ageCategory) {
        this.ageCategory = ageCategory;
    }

    public boolean isUsCitizen() {
        return usCitizen;
    }

    public void setUsCitizen(boolean usCitizen) {
        this.usCitizen = usCitizen;
    }

    public String getTaxId() {
        return taxId;
    }

    public void setTaxId(String taxId) {
        this.taxId = taxId;
    }

    public Gender getGender() {
        return gender;
    }

    public void setGender(Gender gender) {
        this.gender = gender;
    }

    @Override
    public String toString() {
        return "Person{" +
                "ageCategory=" + ageCategory +
                ", id=" + id +
                ", name='" + name + '\'' +
                ", occupation='" + occupation + '\'' +
                ", employeeCategory=" + employeeCategory +
                ", usCitizen=" + usCitizen +
                ", taxId='" + taxId + '\'' +
                ", gender=" + gender +
                '}';
    }
}
