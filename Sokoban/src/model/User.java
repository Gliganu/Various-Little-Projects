package model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;


/**
 * This class represents the users which play the game and it also serves as support for the mysql table in which the results are stored
 */
@Entity
@Table(name="User")
public class User implements Comparable<User>{

	@Id
	@Column(name="name")
	private String name;
	@Column(name="score")
	private int score;
	@Column(name="level_reached")
	private int levelReached;
	
	/**
	 * Creates a blank user
	 */
	public User() {

	}
	
	/**
	 * Creates a user with the specified name
	 * @param name Name for the user
	 */
	public User(String name) {
		this.name = name;
		this.score = 0;
		this.levelReached = 0;
	}
	
	/**
	 * Creates a user with the specified name,score and level reached
	 * @param name Name for the user
	 * @param score Score for the user
	 * @param levelReached Level reached for the user
	 */
	public User(String name, int score, int levelReached) {
		this.name = name;
		this.score = score;
		this.levelReached = levelReached;
	}

	
	/**
	 * Returns the last level reached 
	 */
	
	public int getLevelReached() {
		return levelReached;
	}
	
	/**
	 * Sets the level reached by the user
	 * @param levelReached what level the player has reached
	 */
	public void setLevelReached(int levelReached) {
		this.levelReached = levelReached;
	}
	
	
	/**
	 * Gets the user's name
	 * @return
	 */
	public String getName() {
		return name;
	}

	/**
	 * Sets the user's name
	 * @param name name which the user will have
	 */
	public void setName(String name) {
		this.name = name;
	}
	
	/**
	 * Add an ammount to the total score of the user
	 * @param ammount the amount which will be added to the total score
	 */
	public void addToScore(int ammount){
		score+=ammount;
	}

	/**
	 * Get user's score
	 * @return
	 */
	public int getScore() {
		return score;
	}

	/**
	 * Set user's score
	 * @param score waht score to be set to the user
	 */
	public void setScore(int score) {
		this.score = score;
	}

	/**
	 * A string representation of the user object
	 */
	@Override
	public String toString() {
		return "User [name=" + name + ", score=" + score + ", levelReached="
				+ levelReached + "]";
	}

	/**
	 * A comparison method to define a natural order for user objects
	 * @param the user with which the current user will be compared
	 */
	@Override
	public int compareTo(User otherUser){
		
		return otherUser.getScore()-score;
	}
	
	
	
}
