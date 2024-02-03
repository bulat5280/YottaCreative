package org.jooq.impl;

import org.jooq.Context;
import org.jooq.Converter;
import org.jooq.Field;
import org.jooq.Record;
import org.jooq.Row;

final class RowField<ROW extends Row, REC extends Record> extends AbstractField<REC> {
   private static final long serialVersionUID = -2065258332642911588L;
   private final ROW row;
   private final Field<?>[] emulatedFields;

   RowField(ROW row) {
      this(row, "row");
   }

   RowField(final ROW row, String as) {
      super(as, SQLDataType.RECORD, "", new DefaultBinding(new Converter<Object, REC>() {
         public REC from(Object t) {
            return t == null ? null : DefaultBinding.pgNewRecord(Record.class, row.fields(), t);
         }

         public Object to(REC u) {
            throw new UnsupportedOperationException("Converting from nested records to bind values is not yet supported");
         }

         public Class<Object> fromType() {
            return Object.class;
         }

         public Class<REC> toType() {
            return RecordImpl.class;
         }
      }));
      this.row = row;
      this.emulatedFields = new Field[row.fields().length];

      for(int i = 0; i < this.emulatedFields.length; ++i) {
         this.emulatedFields[i] = row.field(i).as(as + "." + row.field(i).getName());
      }

   }

   Field<?>[] emulatedFields() {
      return this.emulatedFields;
   }

   public final void accept(Context<?> ctx) {
      if (ctx.declareFields()) {
         Object previous = ctx.data(Tools.DataKey.DATA_LIST_ALREADY_INDENTED);
         ctx.data(Tools.DataKey.DATA_LIST_ALREADY_INDENTED, true);
         ctx.visit(new SelectFieldList(this.emulatedFields()));
         ctx.data(Tools.DataKey.DATA_LIST_ALREADY_INDENTED, previous);
      } else {
         ctx.visit(this.row);
      }

   }

   public Field<REC> as(String alias) {
      return new RowField(this.row, alias);
   }

   public boolean declaresFields() {
      return true;
   }
}
