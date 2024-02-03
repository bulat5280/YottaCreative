package org.jooq.api.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/** @deprecated */
@Retention(RetentionPolicy.SOURCE)
@Target({ElementType.TYPE})
@Deprecated
public @interface State {
   String name() default "";

   String[] aliases() default {""};

   boolean terminal() default false;
}
