package controller;

import gui.FormEvent;
import model.*;

import java.io.File;
import java.io.IOException;
import java.sql.SQLException;
import java.util.List;

public class Controller {

    private Database database = new Database() ;

    public List<Person> getPersonList(){
        return database.getPersonList();
    }

    public void addPerson(FormEvent event){
        String name = event.getName();
        String occupation = event.getOccupation();
        AgeCategory ageCategory = event.getAgeCategory();
        EmploymentCategory employeeCategory = event.getEmployeeCategory();
        boolean isUs = event.isUsCitizen();
        String taxID = event.getTaxId();
        String gender = event.getGender();
        Gender realGender;
        if("Male".equals(gender)){
            realGender = Gender.Male;
        }else{
            realGender = Gender.Female;
        }
        Person person = new Person(name,occupation,employeeCategory,ageCategory,isUs,taxID,realGender);


        database.addPerson(person);
    }

    public void save() throws SQLException {
        database.save();
    }

    public void connect() throws SQLException, ClassNotFoundException {
        database.connect();
    }

    public void load() throws SQLException {
        database.load();
    }

    public void disconnect(){
        database.disconnect();
    }


    public void saveToFile(File file) throws IOException {
        database.saveToFile(file);
    }

    public void loadFromFile(File file) throws IOException {
        database.loadFromFIle(file);
    }

    public void removePerson(int index) {
        database.removePerson(index);
    }
}
