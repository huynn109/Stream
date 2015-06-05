package dev.hw.app.streaming.model;

/**
 * Model message for adapter
 * <p/>
 * Created by huyuit on 5/16/2015.
 */
public class Message {

    private String name;
    private String message;
    private String color;


    public Message(String name, String message, String color) {
        this.name = name;
        this.message = message;
        this.color = color;
    }

    public Message(){

    }

    public String getColor() {
        return color;
    }

    public void setColor(String color) {
        this.color = color;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
