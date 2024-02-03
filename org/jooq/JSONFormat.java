package org.jooq;

public final class JSONFormat {
   final boolean header;
   final JSONFormat.RecordFormat recordFormat;

   public JSONFormat() {
      this(true, JSONFormat.RecordFormat.ARRAY);
   }

   private JSONFormat(boolean header, JSONFormat.RecordFormat recordFormat) {
      this.header = header;
      this.recordFormat = recordFormat;
   }

   public JSONFormat header(boolean newHeader) {
      return new JSONFormat(newHeader, this.recordFormat);
   }

   public boolean header() {
      return this.header;
   }

   public JSONFormat recordFormat(JSONFormat.RecordFormat newRecordFormat) {
      return new JSONFormat(this.header, newRecordFormat);
   }

   public JSONFormat.RecordFormat recordFormat() {
      return this.recordFormat;
   }

   public static enum RecordFormat {
      ARRAY,
      OBJECT;
   }
}
