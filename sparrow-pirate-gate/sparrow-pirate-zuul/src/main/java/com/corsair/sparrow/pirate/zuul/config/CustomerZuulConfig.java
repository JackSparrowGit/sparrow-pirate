package com.corsair.sparrow.pirate.zuul.config;

import com.netflix.zuul.http.ZuulServlet;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.boot.autoconfigure.web.ServerProperties;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.boot.web.servlet.error.ErrorController;
import org.springframework.cloud.client.actuator.HasFeatures;
import org.springframework.cloud.client.discovery.event.HeartbeatEvent;
import org.springframework.cloud.client.discovery.event.HeartbeatMonitor;
import org.springframework.cloud.client.discovery.event.InstanceRegisteredEvent;
import org.springframework.cloud.client.discovery.event.ParentHeartbeatEvent;
import org.springframework.cloud.context.scope.refresh.RefreshScopeRefreshedEvent;
import org.springframework.cloud.netflix.zuul.RoutesRefreshedEvent;
import org.springframework.cloud.netflix.zuul.filters.ZuulProperties;
import org.springframework.cloud.netflix.zuul.web.ZuulController;
import org.springframework.cloud.netflix.zuul.web.ZuulHandlerMapping;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationEvent;
import org.springframework.context.ApplicationListener;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.event.ContextRefreshedEvent;

/**
 * @author jack
 */
@Slf4j
@Configuration
@EnableConfigurationProperties({ZuulProperties.class})
@ConditionalOnClass(ZuulServlet.class)
public class CustomerZuulConfig {

    /**
     * 根据springboot版本不同，EVENT_CLASS_PATH 包路径也不同，该配置为springboot 2.x
     * springboot 1.x路径: "org.springframework.context.annotation.AnnotationConfigApplicationContext"
     */
    private static final String EVENT_CLASS_PATH = "org.springframework.boot.web.servlet.context.AnnotationConfigServletWebServerApplicationContext";

    @Autowired
    private ZuulProperties zuulProperties;
    @Autowired
    private ServerProperties serverProperties;
    @Autowired(required = false)
    private ErrorController errorController;

    @Bean
    public CustomerRouteLocator routeLocator(){
        CustomerRouteLocator customerRouteLocator = new CustomerRouteLocator(this.serverProperties.getServlet().getPath(),this.zuulProperties);
        return customerRouteLocator;
    }

    @Bean
    public HasFeatures zuulFeatures(){
        return HasFeatures.namedFeature("Zuul (Discovery)",CustomerZuulConfig.class);
    }

    @Bean
    public ZuulController zuulController(){
        return new ZuulController();
    }

    @Bean
    public ZuulHandlerMapping zuulHandlerMapping(CustomerRouteLocator customerRouteLocator){
        ZuulHandlerMapping zuulHandlerMapping = new ZuulHandlerMapping(customerRouteLocator,zuulController());
        zuulHandlerMapping.setErrorController(this.errorController);
        return zuulHandlerMapping;
    }

    @Bean
    public ApplicationListener zuulRefreshListener(){
        return new ZuulRefreshListener();
    }

    private class ZuulRefreshListener implements ApplicationListener<ApplicationEvent> {

        @Autowired
        private ZuulHandlerMapping zuulHandlerMapping;

        private HeartbeatMonitor heartbeatMonitor;

        public ZuulRefreshListener() {
            this.heartbeatMonitor = new HeartbeatMonitor();
        }

        @Override
        public void onApplicationEvent(ApplicationEvent applicationEvent) {
            boolean isContextRefreshedEvent = applicationEvent instanceof ContextRefreshedEvent;
            boolean isRefreshScopeRefreshedEvent = applicationEvent instanceof RefreshScopeRefreshedEvent;
            boolean isRoutesRefreshedEvent = applicationEvent instanceof RoutesRefreshedEvent;
            boolean isInstanceRegisteredEvent = applicationEvent instanceof InstanceRegisteredEvent;
            if(!isContextRefreshedEvent
                && !isRefreshScopeRefreshedEvent
                && !isRoutesRefreshedEvent
                && !isInstanceRegisteredEvent){

                if(applicationEvent instanceof ParentHeartbeatEvent){
                    ParentHeartbeatEvent e = (ParentHeartbeatEvent) applicationEvent;
                    this.resetIfNeeded(e.getValue());
                } else if (applicationEvent instanceof HeartbeatEvent){
                    HeartbeatEvent e = (HeartbeatEvent) applicationEvent;
                    // 尝试心跳检测刷新路由
                    this.resetIfNeeded(e.getValue());
                }
            } else {
                if((applicationEvent instanceof ContextRefreshedEvent)
                    || (applicationEvent instanceof RefreshScopeRefreshedEvent)
                    || (applicationEvent instanceof RoutesRefreshedEvent)){

                    if(applicationEvent instanceof ContextRefreshedEvent){
                        ContextRefreshedEvent contextRefreshedEvent = (ContextRefreshedEvent) applicationEvent;
                        ApplicationContext context = contextRefreshedEvent.getApplicationContext();

                        String eventClassName = context.getClass().getName();

                        // 启动时从数据库或者缓存中初始化路由信息，判断是否为首次加载
                        if(eventClassName.equals(EVENT_CLASS_PATH)){
                            this.reset();
                        }
                    }
                }
            }
        }

        private void reset() {
            this.zuulHandlerMapping.setDirty(true);
        }

        private void resetIfNeeded(Object value) {
            boolean flag = this.heartbeatMonitor.update(value);
            if(!flag){
                return;
            }
        }
    }
}
