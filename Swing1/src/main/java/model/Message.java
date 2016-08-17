package model;

public class Message {

    private String content;
    private String title;

    public Message(String title, String content) {
        this.content = content;
        this.title = title;
    }


    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    @Override
    public String toString() {
        return content;
    }
}
