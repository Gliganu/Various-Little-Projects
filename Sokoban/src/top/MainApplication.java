package top;

import view.GameDisplay;
import view.MenuDisplay;
import controller.GameController;

/**
 *
 * Main class for the application where all the components are coupled together
 */
public class MainApplication {

	
	public static void main(String[] args) {
	
		GameController controller= new GameController();
		GameDisplay gameDisplay = new GameDisplay(controller,null);
		controller.setObserver(gameDisplay);
		MenuDisplay menuDisplay = new MenuDisplay(gameDisplay);
		gameDisplay.setUpdateObserver(menuDisplay);
		
	}
	



	
}
