package org.jooq.impl;

import java.util.stream.Stream;
import org.jooq.Binding;
import org.jooq.Catalog;
import org.jooq.Clause;
import org.jooq.Configuration;
import org.jooq.Context;
import org.jooq.Converter;
import org.jooq.DataType;
import org.jooq.Field;
import org.jooq.Name;
import org.jooq.Package;
import org.jooq.Row;
import org.jooq.Schema;
import org.jooq.UDT;
import org.jooq.UDTField;
import org.jooq.UDTRecord;

public class UDTImpl<R extends UDTRecord<R>> extends AbstractQueryPart implements UDT<R> {
   private static final long serialVersionUID = -2208672099190913126L;
   private final Schema schema;
   private final String name;
   private final Fields<R> fields;
   private final Package pkg;
   private transient DataType<R> type;

   public UDTImpl(String name, Schema schema) {
      this(name, schema, (Package)null);
   }

   public UDTImpl(String name, Schema schema, Package pkg) {
      this.fields = new Fields(new Field[0]);
      this.name = name;
      this.schema = schema;
      this.pkg = pkg;
   }

   public final Catalog getCatalog() {
      return this.getSchema() == null ? null : this.getSchema().getCatalog();
   }

   public Schema getSchema() {
      return this.schema;
   }

   public final Package getPackage() {
      return this.pkg;
   }

   public final String getName() {
      return this.name;
   }

   public final Row fieldsRow() {
      return new RowImpl(this.fields);
   }

   public final Stream<Field<?>> fieldStream() {
      return Stream.of(this.fields());
   }

   public final <T> Field<T> field(Field<T> field) {
      return this.fieldsRow().field(field);
   }

   public final Field<?> field(String string) {
      return this.fieldsRow().field(string);
   }

   public final Field<?> field(Name fieldName) {
      return this.fieldsRow().field(fieldName);
   }

   public final Field<?> field(int index) {
      return this.fieldsRow().field(index);
   }

   public final Field<?>[] fields() {
      return this.fieldsRow().fields();
   }

   public final Field<?>[] fields(Field<?>... f) {
      return this.fieldsRow().fields(f);
   }

   public final Field<?>[] fields(String... fieldNames) {
      return this.fieldsRow().fields(fieldNames);
   }

   public final Field<?>[] fields(Name... fieldNames) {
      return this.fieldsRow().fields(fieldNames);
   }

   public final Field<?>[] fields(int... fieldIndexes) {
      return this.fieldsRow().fields(fieldIndexes);
   }

   final Fields<R> fields0() {
      return this.fields;
   }

   public Class<R> getRecordType() {
      throw new UnsupportedOperationException();
   }

   public final boolean isSQLUsable() {
      return this.pkg == null;
   }

   public final R newRecord() {
      return DSL.using((Configuration)(new DefaultConfiguration())).newRecord((UDT)this);
   }

   public final DataType<R> getDataType() {
      if (this.type == null) {
         this.type = new UDTDataType(this);
      }

      return this.type;
   }

   public final void accept(Context<?> ctx) {
      Schema mappedSchema = Tools.getMappedSchema(ctx.configuration(), this.getSchema());
      if (mappedSchema != null) {
         ctx.visit(mappedSchema);
         ctx.sql('.');
      }

      if (this.getPackage() != null) {
         ctx.visit(this.getPackage());
         ctx.sql('.');
      }

      ctx.visit(DSL.name(this.getName()));
   }

   public final Clause[] clauses(Context<?> ctx) {
      return null;
   }

   protected static final <R extends UDTRecord<R>, T> UDTField<R, T> createField(String name, DataType<T> type, UDT<R> udt) {
      return createField(name, type, udt, "", (Converter)null, (Binding)null);
   }

   protected static final <R extends UDTRecord<R>, T> UDTField<R, T> createField(String name, DataType<T> type, UDT<R> udt, String comment) {
      return createField(name, type, udt, comment, (Converter)null, (Binding)null);
   }

   protected static final <R extends UDTRecord<R>, T, U> UDTField<R, U> createField(String name, DataType<T> type, UDT<R> udt, String comment, Converter<T, U> converter) {
      return createField(name, type, udt, comment, converter, (Binding)null);
   }

   protected static final <R extends UDTRecord<R>, T, U> UDTField<R, U> createField(String name, DataType<T> type, UDT<R> udt, String comment, Binding<T, U> binding) {
      return createField(name, type, udt, comment, (Converter)null, binding);
   }

   protected static final <R extends UDTRecord<R>, T, X, U> UDTField<R, U> createField(String name, DataType<T> type, UDT<R> udt, String comment, Converter<X, U> converter, Binding<T, X> binding) {
      Binding<T, U> actualBinding = DefaultBinding.newBinding(converter, type, binding);
      DataType<U> actualType = converter == null && binding == null ? type : type.asConvertedDataType(actualBinding);
      UDTFieldImpl<R, U> udtField = new UDTFieldImpl(name, actualType, udt, comment, actualBinding);
      return udtField;
   }

   public int hashCode() {
      return this.name.hashCode();
   }
}
