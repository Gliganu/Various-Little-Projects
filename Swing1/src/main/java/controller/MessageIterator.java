package controller;

import model.Message;

import java.util.Iterator;
import java.util.List;

public class MessageIterator implements Iterator  {

    private Iterator<Message> iterator;

    public MessageIterator(List<Message> messageList) {
        this.iterator = messageList.iterator();
    }

    @Override
    public boolean hasNext() {
      return iterator.hasNext();
    }

    @Override
    public Object next() {
        try {
            Thread.sleep(200);
        } catch (InterruptedException e) {
        }
        return iterator.next();
    }

    @Override
    public void remove() {
        iterator.remove();
    }
}
