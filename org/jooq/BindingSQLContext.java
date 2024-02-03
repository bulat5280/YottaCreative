package org.jooq;

public interface BindingSQLContext<U> extends Scope {
   RenderContext render();

   U value();

   String variable();

   <T> BindingSQLContext<T> convert(Converter<? extends T, ? super U> var1);
}
