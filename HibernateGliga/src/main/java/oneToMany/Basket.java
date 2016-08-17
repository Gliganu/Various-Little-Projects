package oneToMany;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;




@Entity
@Table(name="Basket")
public class Basket {

	@Id
	@GeneratedValue
	@Column(name="Basket_ID")
	private int id;
	
	@Column(name="Basket_Name")
	private String name;
	
	@OneToMany(fetch = FetchType.LAZY, mappedBy = "basket",cascade = CascadeType.ALL)
	private List<Fruit> fruitList=new ArrayList<>();
	
	public Basket() {
		// TODO Auto-generated constructor stub
	}

	public Basket(int id, String name, List<Fruit> fruitList) {
		super();
		this.id = id;
		this.name = name;
		this.fruitList = fruitList;
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

	public List<Fruit> getFruitList() {
		return fruitList;
	}

	public void setFruitList(List<Fruit> fruitList) {
		this.fruitList = fruitList;
	}

	
	
	
	
	
	

}
