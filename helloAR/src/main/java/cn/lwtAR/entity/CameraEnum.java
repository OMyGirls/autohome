package cn.lwtAR.entity;

/**
 * <p>Describe:
 * <p>Author:王春龙
 * <p>CreateTime:2016/6/21
 */
public enum CameraEnum {
    FRONT("1"),

    BACK("0");

    private String type;
    CameraEnum(String type) {
        this.type = type;
    }

    public String getType() {
        return type;
    }
}
