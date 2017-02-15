package cn.lwtAR.entity;

import java.io.Serializable;

public class AREntity implements Serializable {
    private int image;
    private String time;
    private String title;
    private String body;
    private int imageBody;

    public int getImageBody() {
        return imageBody;
    }

    public void setImageBody(int imageBody) {
        this.imageBody = imageBody;
    }

    public int getImage() {
        return image;
    }

    public void setImage(int image) {
        this.image = image;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getBody() {
        return body;
    }

    public void setBody(String body) {
        this.body = body;
    }
}
