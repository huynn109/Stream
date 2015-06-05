package dev.hw.app.streaming.model;

/**
 * Created by huyuit on 6/2/2015.
 */
public class ClipItemData {

    private String youtubeId;
    private String name;
    private String countView;
    private String cate;
    private String image;

    public ClipItemData() {
    }

    public ClipItemData(String youtubeId, String name, String countView, String cate, String image) {
        this.youtubeId = youtubeId;
        this.name = name;
        this.countView = countView;
        this.cate = cate;
        this.image = image;
    }

    public String getYoutubeId() {
        return youtubeId;
    }

    public void setYoutubeId(String youtubeId) {
        this.youtubeId = youtubeId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getCountView() {
        return countView;
    }

    public void setCountView(String countView) {
        this.countView = countView;
    }

    public String getCate() {
        return cate;
    }

    public void setCate(String cate) {
        this.cate = cate;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }
}
