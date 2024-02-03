package org.jooq.impl;

import java.sql.SQLException;
import java.sql.SQLInput;
import java.sql.SQLOutput;
import java.util.Map;
import org.jooq.BindingGetSQLInputContext;
import org.jooq.BindingSetSQLOutputContext;
import org.jooq.Configuration;
import org.jooq.Converter;
import org.jooq.Field;
import org.jooq.Row;
import org.jooq.UDT;
import org.jooq.UDTRecord;

public class UDTRecordImpl<R extends UDTRecord<R>> extends AbstractRecord implements UDTRecord<R> {
   private static final long serialVersionUID = 5671315498175872799L;
   private final UDT<R> udt;

   public UDTRecordImpl(UDT<R> udt) {
      super(udt.fields());
      this.udt = udt;
   }

   public final UDT<R> getUDT() {
      return this.udt;
   }

   public Row fieldsRow() {
      return this.fields;
   }

   public Row valuesRow() {
      return new RowImpl(Tools.fields(this.intoArray(), this.fields.fields.fields()));
   }

   public final String getSQLTypeName() throws SQLException {
      Configuration configuration = DefaultExecuteContext.localConfiguration();
      return Tools.getMappedUDTName(configuration, (UDTRecord)this);
   }

   public final void readSQL(SQLInput stream, String typeName) throws SQLException {
      Configuration configuration = DefaultExecuteContext.localConfiguration();
      Map<Object, Object> data = DefaultExecuteContext.localData();
      Field[] var5 = this.getUDT().fields();
      int var6 = var5.length;

      for(int var7 = 0; var7 < var6; ++var7) {
         Field<?> field = var5[var7];
         this.setValue(configuration, data, stream, field);
      }

   }

   private final <T> void setValue(Configuration configuration, Map<Object, Object> data, SQLInput stream, Field<T> field) throws SQLException {
      DefaultBindingGetSQLInputContext<T> out = new DefaultBindingGetSQLInputContext(configuration, data, stream);
      field.getBinding().get((BindingGetSQLInputContext)out);
      this.set(field, out.value());
   }

   public final void writeSQL(SQLOutput stream) throws SQLException {
      Configuration configuration = DefaultExecuteContext.localConfiguration();
      Map<Object, Object> data = DefaultExecuteContext.localData();
      Field[] var4 = this.getUDT().fields();
      int var5 = var4.length;

      for(int var6 = 0; var6 < var5; ++var6) {
         Field<?> field = var4[var6];
         this.set(configuration, data, stream, field);
      }

   }

   private final <T> void set(Configuration configuration, Map<Object, Object> data, SQLOutput stream, Field<T> field) throws SQLException {
      field.getBinding().set((BindingSetSQLOutputContext)(new DefaultBindingSetSQLOutputContext(configuration, data, stream, this.get(field))));
   }

   public final <T> R with(Field<T> field, T value) {
      return (UDTRecord)super.with(field, value);
   }

   public final <T, U> R with(Field<T> field, U value, Converter<? extends T, ? super U> converter) {
      return (UDTRecord)super.with(field, value, converter);
   }

   public String toString() {
      return DSL.using(this.configuration()).renderInlined(DSL.inline((Object)this));
   }
}
