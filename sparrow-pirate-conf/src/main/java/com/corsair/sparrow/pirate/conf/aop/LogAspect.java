package com.corsair.sparrow.pirate.conf.aop;

import com.corsair.sparrow.pirate.conf.constant.ServerAttributesConstant;
import com.corsair.sparrow.pirate.conf.domain.dto.UserDTO;
import com.corsair.sparrow.pirate.conf.log.UserLog;
import com.corsair.sparrow.pirate.conf.thread.UserContext;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.exception.ExceptionUtils;
import org.apache.commons.lang3.time.StopWatch;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.util.Assert;
import org.springframework.util.IdGenerator;
import org.springframework.web.context.request.RequestAttributes;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.Servlet;
import javax.servlet.http.HttpServletRequest;
import java.util.Arrays;
import java.util.Date;
import java.util.UUID;

/**
 * @author jack
 */
@Slf4j
@Aspect
@Component
public class LogAspect {

    /**
     * 系统请求日志索引
     */
    private static final String REQUEST_LOG_INDEX = "sys-request-log";

    @Value("${spring.application.name}")
    private String serviceId;

    @Around("(execution(public * com.corsair.*.*.web.controller.*.*(..)) || execution(public * com.corsair.*.*.web.feign.*.*(..)))")
    public Object doFilter(ProceedingJoinPoint joinPoint) throws Throwable {
        StopWatch stopWatch = new StopWatch();
        stopWatch.start();
        UserLog userAccessLog = new UserLog().setRequestTime(new Date());
//        userAccessLog.setServerIp()
        userAccessLog.setServiceId(serviceId);
        userAccessLog.setIndex(REQUEST_LOG_INDEX).setParams(Arrays.toString(joinPoint.getArgs()));

        ServletRequestAttributes attributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
        Assert.notNull(attributes,"请求内容不能为空");
        HttpServletRequest httpServletRequest = attributes.getRequest();

        String token = StringUtils.isBlank(httpServletRequest.getHeader(ServerAttributesConstant.TOKEN))
                ? httpServletRequest.getParameter(ServerAttributesConstant.TOKEN)
                : httpServletRequest.getHeader(ServerAttributesConstant.TOKEN);

        if(StringUtils.isNotEmpty(token)){
            // todo 获取用户信息
            userAccessLog.setToken(token);
            getUserInfo(token);
        }

        // todo 匿名登录相关操作
//        else if(){
//
//        }

        Object obj = null;
        try {
            obj = joinPoint.proceed();
        } catch (Exception e) {
            userAccessLog.setExceptions(ExceptionUtils.getStackTrace(e));
            throw e;
        } finally {
            // 记录日志
            recordAccessLog(userAccessLog,httpServletRequest,obj);
            // 清空线程变量
            clearThreadVar();
        }

        return obj;
    }

    private void getUserInfo(String token) {
        UserDTO userDTO = new UserDTO();
        userDTO.setToken(token);
        // ... todo 通过网络调用获取用户token信息

        UserContext.setUserDTO(userDTO);
    }


    private void recordAccessLog(UserLog userAccessLog, HttpServletRequest httpServletRequest, Object obj) {
        userAccessLog.setAgent(httpServletRequest.getHeader("User-Agent"));
        userAccessLog.setClientIp(httpServletRequest.getRemoteAddr());
        // todo 修改成id生成器
        userAccessLog.setId(UUID.randomUUID().toString());
        userAccessLog.setRequestMethod(httpServletRequest.getMethod());
        StringBuffer requestUriBuf = httpServletRequest.getRequestURL();
        if(StringUtils.isNotEmpty(httpServletRequest.getQueryString())){
            requestUriBuf.append("?").append(httpServletRequest.getQueryString());
        }
        userAccessLog.setRequestUri(requestUriBuf.toString());
        userAccessLog.setUserId(UserContext.getUserDTO().getUserId());


    }

    /**
     * 清空子线程变量
     */
    private void clearThreadVar(){
        UserContext.USER_THREAD.remove();
    }
}
