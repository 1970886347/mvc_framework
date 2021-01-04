package com.itheima.bean;

import java.io.Serializable;
import java.lang.reflect.Method;

public class MethodBean implements Serializable {
    public Object obj;
    public Method method;

    public MethodBean(Object obj, Method method) {
        this.obj = obj;
        this.method = method;
    }

    public MethodBean() {
    }

    public Object getObj() {
        return obj;
    }

    public void setObj(Object obj) {
        this.obj = obj;
    }

    public Method getMethod() {
        return method;
    }

    public void setMethod(Method method) {
        this.method = method;
    }
}
