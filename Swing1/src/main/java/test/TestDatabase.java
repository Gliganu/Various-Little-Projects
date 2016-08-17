package test;

import model.*;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class TestDatabase {


    public static void main(String[] args) {

        Database database = new Database();

        try {
            database.connect();

            database.addPerson(new Person("Joe","lion tamer",  EmploymentCategory.Employed, AgeCategory.Adult,true,"777",Gender.Male));
            database.addPerson(new Person("Mary","artist",  EmploymentCategory.SelfEmployed, AgeCategory.Senior,false,null,Gender.Female));
            database.save();

            database.load();


        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } catch (SQLException e) {
            e.printStackTrace();
        }

    }

}
