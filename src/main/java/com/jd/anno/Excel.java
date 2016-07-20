package com.jd.anno;

import java.lang.annotation.*;

/**
 * Created by caozhifei on 2016/7/20.
 */
@Documented
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.FIELD)
public @interface Excel {
    /**
     * excel 列名
     * @return
     */
    String columnName();

    /**
     * 日期格式
     * @return
     */
    String dateFormat() default "";
}
