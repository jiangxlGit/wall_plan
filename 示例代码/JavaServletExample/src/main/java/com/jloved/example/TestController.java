package com.jloved.example;


import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.io.PrintWriter;

/**
 * @author jiangxl
 * @version V1.0
 * @ClassName TestController
 * @Description Servlet简单示例
 * @Date 2023/12/26 12:07
 */
@WebServlet(urlPatterns = "/test")
public class TestController extends HttpServlet {

    @Override
    public void init() {
        System.out.println("===== TestController init");
    }

    @Override
    public void doGet(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        System.out.println("===== TestController doPost");
        // 设置响应类型:
        resp.setContentType("text/html");
        // 解决中文乱码
        resp.setContentType("text/html;charset=utf-8");
        // 获取输出流:
        PrintWriter pw = resp.getWriter();
        // 写入响应:
        pw.write("<h1>hello servlet，当前时间: " + System.currentTimeMillis() + "</h1>");
        // 最后不要忘记flush强制输出:
        pw.flush();
    }

    @Override
    public void destroy() {
        System.out.println("===== TestController destroy");
    }

}
