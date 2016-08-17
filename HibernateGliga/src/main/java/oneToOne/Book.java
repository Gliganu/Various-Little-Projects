package oneToOne;

import javax.persistence.GeneratedValue;
import javax.persistence.*;

@Entity
@Table(name="Book")
public class Book {

	@Id
	@GeneratedValue
	@Column(name="Book_ID")
	private int id;
	
	@Column(name="Book_Name")
	private String name;
	
	@OneToOne(fetch = FetchType.LAZY, mappedBy = "book", cascade = CascadeType.ALL)
	private Author author;
	
	public Book() {
	}

	
	public Book(String name, Author author) {
		super();
		this.name = name;
		this.author = author;
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

	public Author getAuthor() {
		return author;
	}

	public void setAuthor(Author author) {
		this.author = author;
	}

	
}
