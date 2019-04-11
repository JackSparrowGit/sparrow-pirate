package com.corsair.sparrow.pirate.core.base;

import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.Map;

/**
 * @author jack
 */
public abstract class BaseController {

    /**
     * 获取请求中的参数集合
     * @param request
     * @return
     */
    protected HashMap<String, Object> getRequestMapSingle(HttpServletRequest request) {
        HashMap<String, Object> conditions = new HashMap<String, Object>();
        Map map = request.getParameterMap();
        for (Object o : map.keySet()) {
            String key = (String) o;
            conditions.put(key, ((String[]) map.get(key))[0]);
        }
        return conditions;
    }
}
