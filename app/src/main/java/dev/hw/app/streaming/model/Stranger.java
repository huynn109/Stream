package dev.hw.app.streaming.model;

/**
 * Created by huyuit on 5/25/2015.
 */
public class Stranger {

    private String partnerid;
    private String status;
    private String content;

    public Stranger() {
    }

    public Stranger(String partnerid, String status, String content) {
        this.partnerid = partnerid;
        this.status = status;
        this.content = content;
    }

    public String getPartnerid() {
        return partnerid;
    }

    public void setPartnerid(String partnerid) {
        this.partnerid = partnerid;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }
}
