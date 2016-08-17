package oneToOne;

import javax.persistence.*;

@Entity
@Table(name="Author")
public class Author {

	@Id
	@GeneratedValue
	@Column(name="Author_ID")
	private int id;
	
	@Column(name="Author_Name")
	private String name;
	
	@OneToOne
	@JoinColumn(name="Book_ID")
	private Book book;
	
	public Author() {
		// TODO Auto-generated constructor stub
	}

	public Author(int id, String name, Book book) {
		super();
		this.name = name;
		this.book = book;
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

	public Book getBook() {
		return book;
	}

	public void setBook(Book book) {
		this.book = book;
	}
	
	

}
