package org.jooq;

@FunctionalInterface
public interface LoaderFieldMapper {
   Field<?> map(LoaderFieldMapper.LoaderFieldContext var1);

   public interface LoaderFieldContext {
      Field<?> field();

      int index();
   }
}
