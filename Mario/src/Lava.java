
public class Lava {

	
private int level;

public Lava(){
	level=0;
}

public int getLevel(){
	return level;
	
}

public void increaseLevel(){
	level++;
}

public void setLevel(int newLevel){
	level=newLevel;
}

public int getHeight(){
	return level*20;
}

}
