package cn.lwtAR.entity;

import java.io.Serializable;

/**
 * <p>Describe:
 * <p>Author:王春龙
 * <p>CreateTime:2016/9/18
 */
public class UserEntity implements Serializable {
    private String name;
    private String password;

    public UserEntity() {
    }

    public UserEntity(String name, String password) {
        this.name = name;
        this.password = password;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
