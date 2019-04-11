package com.corsair.sparrow.pirate.conf.annonation;

import java.lang.annotation.*;

/**
 * 缓存移除
 */
@Target({ElementType.METHOD,ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
@Inherited
@Documented
public @interface CacheRemove {

    /**
     * 需要清楚的缓存类
     * @return
     */
    String value() default "";

    /**
     * 需要缓存的具体类型
     * @return
     */
    String[] key() default "";
}
