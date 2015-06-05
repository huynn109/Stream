package dev.hw.app.streaming.model;

/**
 * Created by huyuit on 5/26/2015.
 */
public class JoinRoom {

    private String groupid;
    private String uname;
    private String content;
    private String color;

    public JoinRoom() {
    }

    public JoinRoom(String groupid, String uname, String content, String color) {
        this.groupid = groupid;
        this.uname = uname;
        this.content = content;
        this.color = color;
    }

    public String getGroupid() {
        return groupid;
    }

    public void setGroupid(String groupid) {
        this.groupid = groupid;
    }

    public String getUname() {
        return uname;
    }

    public void setUname(String uname) {
        this.uname = uname;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getColor() {
        return color;
    }

    public void setColor(String color) {
        this.color = color;
    }
}
