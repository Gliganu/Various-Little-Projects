package model;

/**
 * 
 * Interface used for implementing Observer patern between the Controller and the Display
 *
 */
public interface Observer {
	
	/**
	 * Update the map 
	 */
	public void updateMap();
	/**
	 * End game
	 */
	public void endGame();
	/**
	 * Reset the game, and update the score if necessary
	 * @param shouldUpdateScore
	 */
	public void reset(boolean shouldUpdateScore);
	
}
