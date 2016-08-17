package daoLayer;

import java.util.List;

import domainLayer.Message;

public interface MessagesDAO {

	public abstract List<Message> getAllMessages();

	public abstract void addMessage(Message message);

	public abstract List<Message> getMessagesByUser(String username);

	public abstract void update(Message message);

	public abstract void delete(int id);

}