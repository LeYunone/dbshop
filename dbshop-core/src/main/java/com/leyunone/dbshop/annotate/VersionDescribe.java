package com.leyunone.dbshop.annotate;

import java.lang.annotation.*;

/**
 * :)
 *
 * @Author pengli
 * @Date 2025/2/7 11:34
 */
@Documented
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.METHOD)
public @interface VersionDescribe {

    String describe();
    
    String version();
}
