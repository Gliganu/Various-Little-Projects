package inheritence;

import javax.persistence.*;

@Entity
@Table(name = "Person")
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
@DiscriminatorColumn(name = "discriminator", discriminatorType = DiscriminatorType.STRING)
@DiscriminatorValue(value = "Person")
public class Person {

	@Id
	@GeneratedValue
	@Column(name = "Person_ID")
	private int id;

	@Column(name = "Firstname")
	private String name;

	public Person() {

	}

	public Person(String name) {
		super();
		this.name = name;
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

}