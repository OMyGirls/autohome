package cn.lwtAR.entity;

import java.io.Serializable;

/**
 * <p>Describe:AR扫描数据实体
 * <p>Author:王春龙
 * <p>CreateTime:2016/7/7
 */
public class UnityScanEntity implements Serializable {
    /**自由AR**/
    public final static String SCAN_FREE = "1";
    /**非自由AR**/
    public final static String SCAN_FREE_NOT = "2";
    /**停止扫描**/
    public final static String SCAN_PRE = "3";

    private String scanType;
    private String showIndex;

    public UnityScanEntity() {
    }

    public UnityScanEntity(String scanType, String showIndex) {
        this.scanType = scanType;
        this.showIndex = showIndex;
    }

    public String getScanType() {
        return scanType;
    }

    public void setScanType(String scanType) {
        this.scanType = scanType;
    }

    public String getShowIndex() {
        return showIndex;
    }

    public void setShowIndex(String showIndex) {
        this.showIndex = showIndex;
    }
}
