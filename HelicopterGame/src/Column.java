import java.awt.Rectangle;
import java.util.ArrayList;
import java.util.Random;


public class Column {


private ArrayList<Rectangle> list;

private int topX;
private int topY;
private int radius=10;
private int diff;
private int upperBound=0;
private int lowerBound=800;
public Column(int newX, int newY,int difficulty) {
	
	this.topX=newX;
	this.topY=newY;
	this.diff=difficulty%5+1;
	list=new ArrayList<Rectangle>();
	initializeList();
	
}

private void initializeList(){
	
	Random generator=new Random();
	
	int topBound=generator.nextInt(diff*3);
	for(int i=0;i<topBound;i++){
		Rectangle upperRectangle=new Rectangle(topX,i*10,radius,radius);
		list.add(upperRectangle);
		
		Rectangle lowerRectangle=new Rectangle(topX,topY-i*10,radius,radius);
		list.add(lowerRectangle);
		
		if( upperRectangle.y>upperBound)
			upperBound=upperRectangle.y;
		
		if( lowerRectangle.y<lowerBound)
			lowerBound=lowerRectangle.y;
		
		
	}
}

public int getUpperBound(){
	return upperBound;
}

public int getLowerBound(){
	return lowerBound;
}

public int getX(){
	return topX;
}

public void move(){
	topX-=radius;
	for(Rectangle rect:list){
		rect.setBounds(rect.x-radius, rect.y, rect.width, rect.height);
	}
}

public boolean isValid(){
	if(list.get(0).x<0)
		return false;
	
	return true;
}
public ArrayList<Rectangle> getList(){
	return list;
}


}
