package cn.lwtAR.entity;

import java.io.Serializable;

/**
 * <p>Describe:
 * <p>Author:王春龙
 * <p>CreateTime:2016/10/11
 */
public class HelpEntity implements Serializable {
    private boolean isExpande;
    private String title;
    private String text;
    private int image;

    public int getImage() {
        return image;
    }

    public void setImage(int image) {
        this.image = image;
    }

    public boolean isExpande() {
        return isExpande;
    }

    public void setExpande(boolean expande) {
        isExpande = expande;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }
}
