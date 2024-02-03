package org.jooq;

import java.util.function.Function;

public interface FieldLike {
   <T> Field<T> asField();

   <T> Field<T> asField(String var1);

   @Support
   <T> Field<T> asField(Function<? super Field<T>, ? extends String> var1);
}
