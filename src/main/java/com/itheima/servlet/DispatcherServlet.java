package com.itheima.servlet;

import com.itheima.annotation.Controller;
import com.itheima.annotation.RequestMapping;
import com.itheima.bean.MethodBean;
import com.itheima.utils.ClassScanUtils;

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

public class DispatcherServlet extends HttpServlet {
    Map<String,MethodBean>methodBeanMap=new HashMap<>();

    @Override
    public void init(ServletConfig config) throws ServletException {
        try {
            String scanPackage = config.getInitParameter("scanPackage");
            List<Class> classes = ClassScanUtils.scanClasses(scanPackage);
            Iterator<Class> iterator = classes.iterator();
            while (iterator.hasNext()){
                Class next = iterator.next();
                if(!next.isAnnotationPresent(Controller.class)){
                    iterator.remove();
                }
            }
            for (Class aClass : classes) {
                Method[] methods = aClass.getMethods();
                for (Method method : methods) {
                    if(method.isAnnotationPresent(RequestMapping.class)){
                        RequestMapping annotation = method.getAnnotation(RequestMapping.class);
                        String value = annotation.value();
                        methodBeanMap.put(value,new MethodBean(aClass.newInstance(),method));
                    }
                }
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        doGet(request, response);
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        try {
            String requestURI = request.getRequestURI();
            String path;
            if(requestURI.contains(".")) {
                path = requestURI.substring(request.getContextPath().length(), requestURI.lastIndexOf("."));
            }else {
                path=requestURI.substring(request.getContextPath().length());
            }
            MethodBean methodBean = methodBeanMap.get(path);
            if(methodBean!=null){
                Method method = methodBean.getMethod();
                Object obj = methodBean.getObj();
                method.invoke(obj,request,response);
                return;
            }
        } catch (Exception e) {
           throw new RuntimeException(e);
        }
        throw new RuntimeException("请求路径不存在");
    }
}
