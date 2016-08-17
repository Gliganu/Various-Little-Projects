import java.awt.Container;

import javax.swing.JFrame;


public class Manager {

private MyPanel myPanel;

public Manager() {

	setUpGui();

}	


private void setUpGui() {
	JFrame frame=new JFrame("Illusion");
	Container container=frame.getContentPane();
	MyPanel myPanel=new MyPanel();
	
	container.add(myPanel);

	frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	frame.setLocation(350,225);
	frame.setSize(600,600);
	frame.setVisible(true);
	
	
}



}
