package dev.hw.app.streaming.model;

/**
 * Created by huyuit on 5/19/2015.
 */
public class User {

    private String name;
    private String imageUrl;
    private int countView;
    private int room;
    private String linkStream;
    private String groupId;

    public User() {
    }

    public User(String name, String imageUrl, int countView, int room, String linkStream, String groupId) {
        this.name = name;
        this.imageUrl = imageUrl;
        this.countView = countView;
        this.room = room;
        this.linkStream = linkStream;
        this.groupId = groupId;
    }

    public String getLinkStream() {
        return linkStream;
    }

    public void setLinkStream(String linkStream) {
        this.linkStream = linkStream;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    public int getCountView() {
        return countView;
    }

    public void setCountView(int countView) {
        this.countView = countView;
    }

    public int getRoom() {
        return room;
    }

    public void setRoom(int room) {
        this.room = room;
    }

    public String getGroupId() {
        return groupId;
    }

    public void setGroupId(String groupId) {
        this.groupId = groupId;
    }
}
