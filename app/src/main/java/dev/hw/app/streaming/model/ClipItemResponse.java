package dev.hw.app.streaming.model;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by huyuit on 6/2/2015.
 */
public class ClipItemResponse {

    private String tag;
    private boolean status;
    private ArrayList<ClipItemDataResponse> data;

    public ClipItemResponse() {
    }

    public ClipItemResponse(String tag, boolean status, ArrayList<ClipItemDataResponse> data) {
        this.tag = tag;
        this.status = status;
        this.data = data;
    }

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

    public List<ClipItemDataResponse> getClipItemDataResponses() {
        return data;
    }

    public void setClipItemDataResponses(ArrayList<ClipItemDataResponse> data) {
        this.data = data;
    }
}
