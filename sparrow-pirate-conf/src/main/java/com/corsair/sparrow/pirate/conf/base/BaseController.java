package com.corsair.sparrow.pirate.conf.base;


import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.Map;

/**
 * BaseController常用rest 处理方法
 * @author jack
 */
public abstract class BaseController {

    /**
     * 获取请求中的参数集合
     * @param httpServletRequest
     * @return
     */
    protected HashMap<String,Object> getRequestMapSingle(HttpServletRequest httpServletRequest){
        HashMap<String,Object> conditions = new HashMap<>();
        Map map = httpServletRequest.getParameterMap();
        for (Object obj : map.entrySet()){
            String key = (String) obj;
            conditions.put(key,((String[])map.get(key))[0]);
        }
        return conditions;
    }
}
