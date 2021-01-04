package com.itheima.controller.web;

import com.itheima.annotation.Controller;
import com.itheima.annotation.RequestMapping;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Controller
public class CrudController {
    @RequestMapping("/crud")
    public void doCrud(HttpServletRequest request, HttpServletResponse response) throws IOException {
        response.getWriter().write("crudMethodInvoke");
    }
}
