package model;

import java.io.*;
import java.sql.*;
import java.util.*;

public class Database {

    private List<Person> personList;
    private Connection con;

    public Database() {
        personList = new LinkedList<Person>();
    }

    public void addPerson(Person person) {
        personList.add(person);
    }

    public List<Person> getPersonList() {
        return Collections.unmodifiableList(personList);
    }

    public void saveToFile(File file) throws IOException {

        FileOutputStream fileOutputStream = new FileOutputStream(file);
        ObjectOutputStream objectOutputStream = new ObjectOutputStream(fileOutputStream);

        Person[] personArray = personList.toArray(new Person[personList.size()]);

        objectOutputStream.writeObject(personArray);

        objectOutputStream.close();
        fileOutputStream.close();
    }

    public void loadFromFIle(File file) throws IOException {

        FileInputStream fileInputStream = new FileInputStream(file);
        ObjectInputStream objectInputStream = new ObjectInputStream(fileInputStream);

        try {
            Person[] personArray = (Person[]) objectInputStream.readObject();

            personList.clear();
            personList.addAll(Arrays.asList(personArray));

        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }

        objectInputStream.close();
        fileInputStream.close();
    }

    public void removePerson(int index) {
        personList.remove(index);
    }

    public void connect() throws ClassNotFoundException, SQLException {

        if (con != null) {
            return;
        }

        Class.forName("com.mysql.jdbc.Driver");
        String url = "jdbc:mysql://localhost:3306/swingtest";
        con = DriverManager.getConnection(url, "root", "root");

        System.out.println("Connected: " + con);
    }

    public void disconnect() {
        if (con != null) {
            try {
                con.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }

    public void load() throws SQLException {
        personList.clear();

        String sql = "SELECT id,name,age,employment_status,tax_id,us_citizen,gender,occupation FROM people ORDER BY id";
        Statement selectStatement = con.createStatement();


        ResultSet resultSet = selectStatement.executeQuery(sql);

        while (resultSet.next()){
            int id = resultSet.getInt("id");
            String name = resultSet.getString("name");
            String age = resultSet.getString("age");
            String employment_status = resultSet.getString("employment_status");
            String tax_id = resultSet.getString("tax_id");
            boolean us_citizen = resultSet.getBoolean("us_citizen");
            String gender = resultSet.getString("gender");
            String occupation = resultSet.getString("occupation");


            Person person = new Person(id,name,occupation,EmploymentCategory.valueOf(employment_status),AgeCategory.valueOf(age),us_citizen,tax_id,Gender.valueOf(gender));

            personList.add(person);

        }

        resultSet.close();
        selectStatement.close();

    }


    public void save() throws SQLException {

        String checkSql = "SELECT COUNT(*) AS count FROM people WHERE id=?";
        PreparedStatement checkStatement = con.prepareStatement(checkSql);

        String insertSql = "INSERT INTO people (id,name,age,employment_status,tax_id,us_citizen,gender,occupation) VALUES (?,?,?,?,?,?,?,?)";
        String updateSql = "UPDATE people SET name=? ,age=? ,employment_status=? ,tax_id=? ,us_citizen=? ,gender=? ,occupation=? WHERE id=?";

        PreparedStatement insertStatement = con.prepareStatement(insertSql);
        PreparedStatement updateStatement = con.prepareStatement(updateSql);

        for (Person person : personList) {
            int id = person.getId();
            String name = person.getName();
            String occupation = person.getOccupation();
            AgeCategory ageCategory = person.getAgeCategory();
            EmploymentCategory employeeCategory = person.getEmployeeCategory();
            String taxId = person.getTaxId();
            boolean usCitizen = person.isUsCitizen();
            Gender gender = person.getGender();


            checkStatement.setInt(1, id);
            ResultSet checkResult = checkStatement.executeQuery();

            checkResult.next();

            int count = checkResult.getInt(1);

            if (count == 0) {

                insertStatement.setInt(1,id);
                insertStatement.setString(2, name);
                insertStatement.setString(3, ageCategory.toString());
                insertStatement.setString(4, employeeCategory.toString());
                insertStatement.setString(5, taxId);
                insertStatement.setBoolean(6, usCitizen);
                insertStatement.setString(7, gender.toString());
                insertStatement.setString(8, occupation);

                insertStatement.executeUpdate();

            } else {

                updateStatement.setString(1, name);
                updateStatement.setString(2, ageCategory.toString());
                updateStatement.setString(3, employeeCategory.toString());
                updateStatement.setString(4, taxId);
                updateStatement.setBoolean(5, usCitizen);
                updateStatement.setString(6, gender.toString());
                updateStatement.setString(7, occupation);
                updateStatement.setInt(8, id);

                updateStatement.executeUpdate();
            }

        }

        checkStatement.close();
        updateStatement.close();
        insertStatement.close();
    }
}
