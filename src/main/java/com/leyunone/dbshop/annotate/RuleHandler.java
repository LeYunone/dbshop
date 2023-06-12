
package com.leyunone.dbshop.annotate;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import com.leyunone.dbshop.enums.RuleTypeEnum;
import org.springframework.core.annotation.AliasFor;
import org.springframework.stereotype.Component;

/**
 * 转换规则
 * @author leyuna
 */
@Component
@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.TYPE})
@Documented
public @interface RuleHandler {
    @AliasFor("identif")
    String[] value() default {""};

    //标识
    @AliasFor("value")
    String[] identifs() default {""};
    
    //结果集返回
    RuleTypeEnum type() default RuleTypeEnum.NONE;
}
