package org.jooq;

public interface Record1<T1> extends Record {
   Row1<T1> fieldsRow();

   Row1<T1> valuesRow();

   Field<T1> field1();

   T1 value1();

   Record1<T1> value1(T1 var1);

   Record1<T1> values(T1 var1);
}
