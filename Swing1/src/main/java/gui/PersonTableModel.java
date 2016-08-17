package gui;

import model.EmploymentCategory;
import model.Person;

import javax.swing.table.AbstractTableModel;
import java.util.List;

public class PersonTableModel extends AbstractTableModel{

    private List<Person> personList;
    private String[] columnNames={"ID","Name","Occupation","Age Category","Employment Category","US Citizen","Tax ID"};

    public PersonTableModel() {

    }

    public void setData(List<Person> personList) {
        this.personList = personList;
    }

    @Override
    public String getColumnName(int column) {
        return columnNames[column];
    }

    @Override
    public int getRowCount() {
        return personList.size();
    }

    @Override
    public int getColumnCount() {
        return 7;
    }

    @Override
    public boolean isCellEditable(int rowIndex, int columnIndex) {

        switch (columnIndex){
            case 1:
                return true;
            case 4:
                return true;
            case 5:
                return true;
            default:
                return false;
        }
    }

    @Override
    public Class<?> getColumnClass(int columnIndex) {

        switch (columnIndex){
            case 0:
                return Integer.class;
            case 1:
                return String.class;
            case 2:
                return String.class;
            case 3:
                return String.class;
            case 4:
                return EmploymentCategory.class;
            case 5:
                return Boolean.class;
            case 6:
                return String.class;
            default:
                return null;
        }

    }

    @Override
    public void setValueAt(Object aValue, int rowIndex, int columnIndex) {

        if(personList == null)
            return;

        Person person = personList.get(rowIndex);

        switch (columnIndex){
            case 1:
                person.setName((String) aValue);
            case 4:
                person.setEmployeeCategory((EmploymentCategory) aValue);
                break;
            case 5:
                person.setUsCitizen((Boolean) aValue);
                break;
            default:
        }


    }

    @Override
    public Object getValueAt(int rowIndex, int columnIndex) {
        Person person =  personList.get(rowIndex);

        switch (columnIndex){
            case 0:
                return person.getId();
            case 1:
                return person.getName();
            case 2:
                return person.getOccupation();
            case 3:
                return person.getAgeCategory();
            case 4:
                return person.getEmployeeCategory();
            case 5:
                return person.isUsCitizen();
            case 6:
                return person.getTaxId() ;
        }
        return null;
    }

}
