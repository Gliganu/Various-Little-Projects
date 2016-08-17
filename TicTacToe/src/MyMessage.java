import java.io.Serializable;


public class MyMessage implements Serializable{

private String message;
private String sender;

public MyMessage(String message, String sender) {
	super();
	this.message = message;
	this.sender = sender;
}

public String getMessage() {
	return message;
}

public String getSender() {
	return sender;
}


	
}
