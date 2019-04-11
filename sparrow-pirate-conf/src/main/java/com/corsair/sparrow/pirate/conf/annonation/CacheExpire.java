package com.corsair.sparrow.pirate.conf.annonation;

import org.springframework.core.annotation.AliasFor;

import java.lang.annotation.*;

/**
 * CacheExpire用于业务方法拓展缓存定时
 */
@Target({ElementType.METHOD,ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
@Inherited
@Documented
public @interface CacheExpire {

    @AliasFor("expire")
    long value() default 60L;

    @AliasFor("value")
    long expire() default 60L;
}
