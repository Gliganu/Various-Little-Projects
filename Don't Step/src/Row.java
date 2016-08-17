import java.awt.Color;
import java.util.ArrayList;
import java.util.Random;

public class Row {

	private ArrayList<Block> blockList;
	private int width=100;
	private int height=100;
	
	public Row(int newHeight) {
		blockList = new ArrayList<>();
		for (int i = 0; i < 4; i++) {
			blockList.add(new Block(i*width,newHeight*height,height,width,Color.RED));
		}
		setBlack();
	}

	public void moveDown(int distance){
		for(Block block:blockList){
			block.setLocation((int)block.getX(),(int)block.getY()+distance);
		}
	}
	
	private void setBlack() {
		Random generator = new Random();
		blockList.get(generator.nextInt(4)).setBlack();
	}

	public ArrayList<Block> getBlocks() {
		return blockList;
	}
	
	public boolean validRow(){
		if(blockList.get(0).getY()>=1000){
			return false;
		}
		return true;
	}

}
