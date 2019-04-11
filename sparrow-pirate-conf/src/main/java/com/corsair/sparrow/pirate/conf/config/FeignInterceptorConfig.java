package com.corsair.sparrow.pirate.conf.config;

import com.corsair.sparrow.pirate.conf.strategy.FeignHystrixConcurrencyStrategy;
import com.corsair.sparrow.pirate.core.constant.GlobalConstant;
import feign.RequestInterceptor;
import feign.RequestTemplate;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;

/**
 * @author jack
 */
@Slf4j
@Configuration
public class FeignInterceptorConfig implements RequestInterceptor {

    /**
     * 使SEMAPHORE feign隔离策略生效
     * @return
     */
    @Bean
    public FeignHystrixConcurrencyStrategy feignHystrixConcurrencyStrategy() {
        return new FeignHystrixConcurrencyStrategy();
    }

    @Override
    public void apply(RequestTemplate requestTemplate) {
        ServletRequestAttributes requestAttributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
        if (requestAttributes == null) {
            return;
        }
        HttpServletRequest httpServletRequest = requestAttributes.getRequest();

        // ie9 等低版本浏览器会将token携带在url中
        String token = httpServletRequest.getParameter(GlobalConstant.TOKEN_NAME);
        if(StringUtils.isEmpty(token)){
            token = httpServletRequest.getHeader(GlobalConstant.TOKEN_NAME);
        }
        log.info("feign请求拦截器携带token:{}",token);
        requestTemplate.header(GlobalConstant.TOKEN_NAME,token);
    }
}
