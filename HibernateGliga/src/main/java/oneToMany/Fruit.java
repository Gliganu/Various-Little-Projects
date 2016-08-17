package oneToMany;

import javax.persistence.*;

@Entity
@Table(name="Fruit")
public class Fruit {

	@Id
	@GeneratedValue
	@Column(name="Fruit_ID")
	private int id;
	
	@Column(name="Fruit_Name")
	private String name;
	
	@Column(name="Color_Name")
	private String color;
	
	@ManyToOne
	@JoinTable(name = "Basket_Fruit", 
			joinColumns = { @JoinColumn(name = "Basket_ID" ) }, 
			inverseJoinColumns = { @JoinColumn(name = "Fruit_ID") })
	
	private Basket basket;
	
	public Fruit() {
	}

	public Fruit(String name, String color) {
		super();
		this.name = name;
		this.color = color;
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

	public String getColor() {
		return color;
	}

	public void setColor(String color) {
		this.color = color;
	}

	public Basket getBasket() {
		return basket;
	}

	public void setBasket(Basket basket) {
		this.basket = basket;
	}
	
	
	
	
}
