package com.itheima.controller;

import com.itheima.annotation.Controller;
import com.itheima.annotation.RequestMapping;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Controller
public class registController {
    @RequestMapping("/regist")
    public void doRegist(HttpServletRequest request, HttpServletResponse response) throws IOException {
        response.getWriter().write("registMethodInvoke");
    }
}
