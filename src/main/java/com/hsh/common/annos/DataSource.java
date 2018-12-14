package com.hsh.common.annos;

import java.lang.annotation.*;

/**
 * @author hushihai
 * @version V1.0, 2018/12/14
 */

@Target({ ElementType.TYPE,ElementType.METHOD})
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface DataSource {
    String name() default "";
}
