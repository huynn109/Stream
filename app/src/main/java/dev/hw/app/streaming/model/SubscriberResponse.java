package dev.hw.app.streaming.model;

import java.util.ArrayList;
import java.util.List;

/**
 * Subscriber model response
 *
 * Created by huyuit on 5/12/2015.
 */
public class SubscriberResponse {
    private String tag;
    private boolean status;
    private List<Subscriber> data = new ArrayList<>();

    public String getTag() {
        return tag;
    }

    public void setTag(String tag) {
        this.tag = tag;
    }

    public boolean isStatus() {
        return status;
    }

    public void setStatus(boolean status) {
        this.status = status;
    }

    public List<Subscriber> getDataResponseList() {
        return data;
    }

    public void setDataResponseList(List<Subscriber> data) {
        this.data = data;
    }
}
