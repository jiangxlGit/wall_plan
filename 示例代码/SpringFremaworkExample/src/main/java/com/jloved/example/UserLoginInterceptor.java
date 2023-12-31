package com.jloved.example;

import java.util.HashMap;
import java.util.Map;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

/**
 * @description 利用spring框架提供的HandlerInterceptorAdapter，实现自定义拦截器
 */
public class UserLoginInterceptor extends HandlerInterceptorAdapter {
    // 在业务处理器处理请求之前被调用
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        System.out.println("===== UserLoginInterceptor preHandle...");
        String requestUri = request.getRequestURI();
        String contextPath = request.getContextPath();
        String url = requestUri.substring(contextPath.length());
        System.out.println("requestUri" + requestUri);
        System.out.println("contextPath" + contextPath);
        System.out.println("url" + url);
        String username = (String) request.getSession().getAttribute("username");
        if (null == username) {
            // 跳转到登录页面
            request.getRequestDispatcher("/WEB-INF/login.html").forward(request, response);
            return false;
        } else {
            return true;
        }
    }

    // 在业务处理器处理请求完成之后，生成视图之前执行
    public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, ModelAndView modelAndView) throws Exception {
        System.out.println("===== UserLoginInterceptor postHandle...");
        if (modelAndView != null) {
            Map<String, String> map = new HashMap<>();
            modelAndView.addAllObjects(map);
        }
    }

    // 在DispatcherServlet完全处理完请求之后被调用，可用于清理资源
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) throws Exception {
        System.out.println("===== UserLoginInterceptor afterCompletion...");
    }
}