package controller;

import model.Message;

import java.util.*;

public class MessageServer implements Iterable<Message> {

    private Map<Integer, List<Message>> messageMap;

    private List<Message> selected;

    public MessageServer() {
        this.messageMap = new TreeMap<Integer, List<Message>>();
         this.selected = new ArrayList<Message>();

        initializeMessages();

    }

    private void initializeMessages() {
        List<Message> list = new ArrayList<Message>();

        list.add(new Message("New York - Weather","It will be hot and sunny"));
        list.add(new Message("New York - Sports","The Yankees have won the cup"));

        messageMap.put(0,list);

        List<Message> list2 = new ArrayList<Message>();

        list2.add(new Message("Boston - Politics","It appears that the mayor will change in the next elections"));
        list2.add(new Message("Boston - Entertainment","Cat sings at piano"));

        messageMap.put(1,list2);

        List<Message> list3 = new ArrayList<Message>();

        list3.add(new Message("Los Angeles - News","Policeman shots thief and manages to save the merchandise"));
        list3.add(new Message("Los Angeles - Sports","The Lakers lost last night\'s game 97-93"));

        messageMap.put(2, list3);

        List<Message> list4 = new ArrayList<Message>();

        list4.add(new Message("London - Stock","The DAW Index is plummeting following last night's events"));
        list4.add(new Message("London - Education", "It appears that the universities will reduce the fees"));

        messageMap.put(3,list4);

        List<Message> list5 = new ArrayList<Message>();

        list5.add(new Message("Edinburgh - News","Scotland has decided not to split from UK"));
        list5.add(new Message("Edinburgh - Technology","HP to open big data center in our city"));

        messageMap.put(4,list5);


    }


    public void setSelectedServers(Set<Integer> serverSet){

        selected.clear();
        for(Integer id: serverSet){

            if (messageMap.containsKey(id)){
                selected.addAll(messageMap.get(id));
            }

        }
    }

    public int getMessageCount(){
        return selected.size();
    }


    @Override
    public Iterator<Message> iterator() {

        return new MessageIterator(selected);
    }
}





