package com.leyunone.dbshop.annotate;

import com.leyunone.dbshop.enums.RuleTypeEnum;
import com.leyunone.dbshop.enums.SqlModelEnum;
import org.springframework.core.annotation.AliasFor;
import org.springframework.stereotype.Component;

import java.lang.annotation.*;

/**
 * :)
 *
 * @Author leyunone
 * @Date 2023/9/12 14:36
 */
@Component
@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.TYPE})
@Documented
public @interface SqlHandler {

    SqlModelEnum value();
}
