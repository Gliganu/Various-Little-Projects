package inheritence;

import javax.persistence.*;

@Entity
@DiscriminatorValue("Manager")
public class Manager extends Person {

	@Column(name="Title")
	public String title;
	
	public Manager() {
		super();
	}

	public Manager(String name, String title) {
		super(name);
		this.title = title;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}
	
	
	
	

}
