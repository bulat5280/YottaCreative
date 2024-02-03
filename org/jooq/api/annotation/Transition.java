package org.jooq.api.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/** @deprecated */
@Retention(RetentionPolicy.SOURCE)
@Target({ElementType.METHOD})
@Deprecated
public @interface Transition {
   String name() default "";

   String[] args() default {""};

   String from() default "";

   String to() default "";
}
