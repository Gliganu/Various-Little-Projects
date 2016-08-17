import java.io.Serializable;


public class MyAction implements Serializable{

private String type;
private int index;
private String player;



public MyAction(String name,String type, int index) {
	this.type = type;
	this.index = index;
	this.player=name;
}

public MyAction(String name, int index){
	this.player=name;
	this.index=index;
	
}


public String getPlayerName(){
	return player;
}
public String getType() {
	return type;
}

public int getIndex() {
	return index;
}



}
