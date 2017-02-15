package cn.lwtAR.entity;

import org.xutils.db.annotation.Column;
import org.xutils.db.annotation.Table;

import java.io.Serializable;

/**
 * <p>Describe:
 * <p>Author:王春龙
 * <p>CreateTime:2016/9/19
 */
@Table(name = "ph")
public class PhotoEntity implements Serializable {
    public static String TYPE_PIC = "1";
    public static String TYPE_VIDEO = "2";
    public static String TYPE_AR_INFO = "3";

    @Column(name = "id", isId = true)
    private int id;
    @Column(name = "type")
    private String type;
    @Column(name = "picPath")
    private String picPath;
    @Column(name = "videoPath")
    private String videoPath;
    @Column(name = "text")
    private String text;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getPicPath() {
        return picPath;
    }

    public void setPicPath(String picPath) {
        this.picPath = picPath;
    }

    public String getVideoPath() {
        return videoPath;
    }

    public void setVideoPath(String videoPath) {
        this.videoPath = videoPath;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }
}
