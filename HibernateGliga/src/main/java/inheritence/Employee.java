package inheritence;

import javax.persistence.*;

@Entity
@DiscriminatorValue("Employee")
public class Employee extends Person {

	@Column(name="Department")
	public String department;
	
	public Employee() {
		super();
	}

	public Employee(String name, String department) {
		super(name);
		this.department = department;
	}

	public String getDepartment() {
		return department;
	}

	public void setDepartment(String department) {
		this.department = department;
	}
	
	
	
	

}
