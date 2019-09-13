package com.lin.utils;

import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;

/**
 * Spring 工具类
 *
 * ApplicationContextAware：实现该接口之后，当前类将具有获取 Spring 上下文的能力
 * @author lkmc2
 * @date 2019/9/13 20:32
 */
public class SpringUtils implements ApplicationContextAware {

    /** Spring 上下文，可用于获取 Spring 容器中的 bean 实例 **/
    private static ApplicationContext applicationContext;

    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        SpringUtils.applicationContext = applicationContext;
    }

    /**
     * 获取 Spring 上下文对象
     * @return Spring 上下文对象
     */
    public static ApplicationContext getApplicationContext() {
        return applicationContext;
    }

    /**
     * 根据名字获取 Spring 容器中的 bean 实例
     * @param name bean 实例名字
     * @return Spring 容器中的 bean 实例
     */
    public static Object getBean(String name) {
        return applicationContext.getBean(name);
    }

    /**
     * 根据类类型获取 Spring 容器中的 bean 实例
     * @param clazz bean 的类类型
     * @return Spring 容器中的 bean 实例
     */
    public static <T> T getBean(Class<T> clazz) {
        return applicationContext.getBean(clazz);
    }

    /**
     * 根据名字和类类型获取 Spring 容器中的 bean 实例
     * @param name bean 实例名字
     * @param clazz bean 的类类型
     * @return Spring 容器中的 bean 实例
     */
    public static <T> T getBean(String name, Class<T> clazz) {
        return applicationContext.getBean(name, clazz);
    }

}
