package com.jloved.example;


import com.sun.javafx.logging.PulseLogger;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.beans.factory.support.RootBeanDefinition;

import java.lang.reflect.Field;
import java.util.Collections;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;

/***
 * @Author 徐庶   QQ:1092002729
 * @Slogan 致敬大师，致敬未来的你
 */
public class MainStart {

    private static Map<String, BeanDefinition> beanDefinitionMap = new ConcurrentHashMap<>(256);

    /**
     * 读取bean定义，当然在spring中肯定是根据配置 动态扫描注册
     */
    public static void loadBeanDefinitions() {
        RootBeanDefinition aBeanDefinition = new RootBeanDefinition(InstanceA.class);
        RootBeanDefinition bBeanDefinition = new RootBeanDefinition(InstanceB.class);
        beanDefinitionMap.put("instanceA", aBeanDefinition);
        beanDefinitionMap.put("instanceB", bBeanDefinition);
    }

    public static void main(String[] args) throws Exception {
        // 加载了BeanDefinition
        loadBeanDefinitions();
        // 注册Bean的后置处理器

        // 循环创建Bean
        for (String key : beanDefinitionMap.keySet()) {

            getBean(key);
        }
        InstanceA instanceA = (InstanceA) getBean("instanceA");
        instanceA.say();
    }

    // 一级缓存
    public static Map<String, Object> singletonObjects = new ConcurrentHashMap<>();


    // 二级缓存： 为了将成熟Bean和纯净Bean分离，避免读取到不完整得Bean
    public static Map<String, Object> earlySingletonObjects = new ConcurrentHashMap<>();

    // 三级缓存
    public static Map<String, ObjectFactory<Object>> singletonFactories = new ConcurrentHashMap<>();

    // 循环依赖标识，存放beanName
    public static Set<String> singletonsCurrennlyInCreation = new HashSet<>();


    // 假设A 使用了Aop @PointCut("execution(* *..InstanceA.*(..))")   要给A创建动态代理
    // 获取Bean
    public static Object getBean(String beanName) throws Exception {
        Object singleton = getSingleton(beanName);
        if (singleton != null) {
            return singleton;
        }

        // 标识bean正在创建中
        if (!singletonsCurrennlyInCreation.contains(beanName)) {
            singletonsCurrennlyInCreation.add(beanName);
        }

        // 实例化
        RootBeanDefinition beanDefinition = (RootBeanDefinition) beanDefinitionMap.get(beanName);
        Class<?> beanClass = beanDefinition.getBeanClass();
        Object instanceBean = beanClass.newInstance();

        // 从二级缓存中获取，并使用jdk动态代理生成ObjectFactory，再添加到三级缓存
        // 只在循环依赖的情况下在实例化后创建proxy   判断当前是不是循环依赖
        singletonFactories.put(beanName, () -> new JdkProxyBeanPostProcessor()
                .getEarlyBeanReference(earlySingletonObjects.get(beanName), beanName));

        // 属性赋值
        Field[] declaredFields = beanClass.getDeclaredFields();
        for (Field declaredField : declaredFields) {
            Autowired annotation = declaredField.getAnnotation(Autowired.class);
            // 说明属性上面有Autowired
            if (annotation != null) {
                declaredField.setAccessible(true);
                String name = declaredField.getName();
                Object fileObject = getBean(name);   //拿到B的Bean
                declaredField.set(instanceBean, fileObject);
            }
        }

        // 从二级缓存中拿到proxy 。
        if (earlySingletonObjects.containsKey(beanName)) {
            instanceBean = earlySingletonObjects.get(beanName);
        }

        // 添加到一级缓存
        singletonObjects.put(beanName, instanceBean);

        return instanceBean;
    }


    public static Object getSingleton(String beanName) {
        // 先从一级缓存中拿
        Object bean = singletonObjects.get(beanName);

        // bean正在创建中，则从二级缓存中拿，二级缓存拿不到就从三级缓存中拿，如果三级缓存中也没有就返回null
        if (bean == null && singletonsCurrennlyInCreation.contains(beanName)) {
            bean = earlySingletonObjects.get(beanName);
            // 如果二级缓存没有就从三级缓存中拿
            if (bean == null) {
                // 从三级缓存中拿
                ObjectFactory<Object> factory = singletonFactories.get(beanName);
                if (factory != null) {
                    // 三级缓存中有，就获取ObjectFactory中的bean并添加到二级缓存中
                    bean = factory.getObject(); // 拿到动态代理
                    earlySingletonObjects.put(beanName, bean);
                }
            }
        }
        return bean;
    }

}
