package model;

/**
 * 
 *  Interface used for implementing Observer patern
 *
 */
public interface UpdateObserver {

	/**
	 * Add the user to the database
	 * @param user the user to be saved to the database
	 */
	public void saveToDatabase(User user);
	
}
