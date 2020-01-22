package com.springcloud.servicezuul.configuration.helper.entity;

import com.alibaba.fastjson.JSON;

// 状态
public   class Status {
    final public String name;
    final public boolean success;
    final public String error;


    public String toString(Status s) {
        return JSON.toJSONString( s);
    }

    public Status(String name, boolean success, String error) {
        this.name = name;
        this.success = success;
        this.error = error;

    }
    public static Status success(String name) {
        return new Status(name, true, null);
    }
    public  static Status error(String name, String error) {
        return new Status(name, false, error);
    }
}