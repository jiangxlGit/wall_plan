package com.jloved.example;

import javax.servlet.ServletContext;
import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;

/**
 * 现在来说说Servlet的监听器Listener，它是实现了javax.servlet.ServletContextListener 接口的
 * 服务器端程序，它也是随web应用的启动而启动，只初始化一次，随web应用的停止而销毁。主要作用是：做一些初始化
 * 的内容添加工作、设置一些基本的内容、比如一些参数或者是一些固定的对象等等。
 * <p>
 * 示例代码：使用监听器对数据库连接池DataSource进行初始化
 */
public class TestListener implements ServletContextListener {

    private static final String SERVLET_CONTEXT_ATTRIBUTE = "jloved";

    // 应用监听器的销毁方法
    public void contextDestroyed(ServletContextEvent servletContextEvent) {
        ServletContext servletContext = servletContextEvent.getServletContext();
        // 在整个web应用销毁之前调用，将所有应用空间所设置的内容清空  
        servletContext.removeAttribute(SERVLET_CONTEXT_ATTRIBUTE);
        System.out.println("===== TestListener销毁工作完成...");
    }

    // 应用监听器的初始化方法     
    public void contextInitialized(ServletContextEvent servletContextEvent) {
        // 通过这个事件可以获取整个应用的空间     
        // 在整个web应用下面启动的时候做一些初始化的内容添加工作     
        ServletContext servletContext = servletContextEvent.getServletContext();
        // 把 DataSource 放入ServletContext空间中，供整个web应用的使用
        servletContext.setAttribute(SERVLET_CONTEXT_ATTRIBUTE, 520);
        System.out.println("===== TestListener应用监听器初始化工作完成...");
    }
}