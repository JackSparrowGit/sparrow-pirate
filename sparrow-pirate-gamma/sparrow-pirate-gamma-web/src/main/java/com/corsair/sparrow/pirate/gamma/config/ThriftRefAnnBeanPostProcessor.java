package com.corsair.sparrow.pirate.gamma.config;

import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.config.BeanPostProcessor;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;

/**
 * @author jack
 */
@Slf4j
public class ThriftRefAnnBeanPostProcessor implements BeanPostProcessor {

    private ThriftServerDiscovery discovery;
    private ThriftConsumerProxy proxy;

    @Override
    public Object postProcessBeforeInitialization(Object bean, String beanName) throws BeansException {
        Method[] methods = bean.getClass().getMethods();
        for (Method method : methods) {
            String name = method.getName();
            if (name.length() > 3 && name.startsWith("set")
                    && method.getParameterTypes().length == 1
                    && Modifier.isPublic(method.getModifiers())
                    && !Modifier.isStatic(method.getModifiers())) {
                try {
                    ThriftReference reference = method.getAnnotation(ThriftReference.class);
                    if (reference != null) {
                        Object value = refer(reference, method.getParameterTypes()[0]);
                        if (value != null) {
                            method.invoke(bean, new Object[] {});
                        }
                    }
                } catch (Throwable e) {
                    log.error("Failed to init remote service reference at method " + name + " in class " + bean.getClass().getName() + ", cause: " + e.getMessage(), e);
                }
            }
        }
        Field[] fields = bean.getClass().getDeclaredFields();
        for (Field field : fields) {
            try {
                if (!field.isAccessible()) {
                    field.setAccessible(true);
                }
                ThriftReference reference = field.getAnnotation(ThriftReference.class);
                if (reference != null) {
                    Object value = refer(reference, field.getType());
                    if (value != null) {
                        field.set(bean, value);
                    }
                }
            } catch (Throwable e) {
                log.error("Failed to init remote service reference at filed " + field.getName() + " in class " + bean.getClass().getName() + ", cause: " + e.getMessage(), e);
            }
        }
        return bean;
    }

    @Override
    public Object postProcessAfterInitialization(Object bean, String beanName) throws BeansException {
        return bean;
    }

    private Object refer(ThriftReference reference, Class<?> referenceClass) {
        String version = reference.version();
        Class<?> serviceClass = referenceClass.getDeclaringClass();
        String address = discovery.getAddress(serviceClass.getName(), version);
        if(StringUtils.isEmpty(address) || address.split(":").length <2) {
            if(log.isErrorEnabled()) {
                log.error(String.format("No provider found for service: %s, version: %s", serviceClass.getName(),version));
            }
            return null;
        }
        String[] str = address.split(":");
        String host = str[0];
        int port = Integer.valueOf(str[1]);
        return proxy.proxy(referenceClass, host, port);
    }

    public ThriftServerDiscovery getDiscovery() {
        return discovery;
    }

    public void setDiscovery(ThriftServerDiscovery discovery) {
        this.discovery = discovery;
    }

    public ThriftConsumerProxy getProxy() {
        return proxy;
    }

    public void setProxy(ThriftConsumerProxy proxy) {
        this.proxy = proxy;
    }

}

