package com.kiv.kiveiruapi.xshell.ecsmanager.core.annotation;

import java.lang.annotation.*;

@Target({ElementType.METHOD})
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface SystemLog {

    String description() default "";

}
