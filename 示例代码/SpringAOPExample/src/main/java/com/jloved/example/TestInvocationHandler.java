package com.jloved.example;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.text.SimpleDateFormat;

public class TestInvocationHandler implements InvocationHandler {

    private Object obj;
    public TestInvocationHandler(Object obj){
        this.obj = obj;
    }

    @Override
    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
        //方法执行前
        long startTime = System.currentTimeMillis();

        Object result = method.invoke(obj, args);//执行方法的调用

        //方法执行后
        long endTime = System.currentTimeMillis();
        SimpleDateFormat sdf = new SimpleDateFormat();
        System.out.printf(String.format("%s方法执行结束时间：%%s ；方法执行耗时：%%d%%n"
                , method.getName()), sdf.format(endTime), endTime - startTime);
        return result;
    }
}
