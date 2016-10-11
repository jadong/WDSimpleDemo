package com.dong.entity;

/**
 * Created by zengwendong on 16/8/24.
 */
public class ActivityName {

    private String name;

    private Class clazz;

    public ActivityName() {
    }

    public ActivityName(String name, Class clazz) {
        this.name = name;
        this.clazz = clazz;
    }

    public Class getClazz() {
        return clazz;
    }

    public void setClazz(Class clazz) {
        this.clazz = clazz;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
