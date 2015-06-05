package dev.hw.app.streaming.model;

/**
 * Created by huyuit on 6/2/2015.
 */
public class Clip {

    private String imageUrl;
    private String name;
    private String cate;
    private String countView;

    public Clip() {
    }

    public Clip(String imageUrl, String name, String cate, String countView) {
        this.imageUrl = imageUrl;
        this.name = name;
        this.cate = cate;
        this.countView = countView;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getCate() {
        return cate;
    }

    public void setCate(String cate) {
        this.cate = cate;
    }

    public String getCountView() {
        return countView;
    }

    public void setCountView(String countView) {
        this.countView = countView;
    }
}
