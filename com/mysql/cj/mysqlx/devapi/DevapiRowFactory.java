package com.mysql.cj.mysqlx.devapi;

import com.mysql.cj.api.x.Row;
import com.mysql.cj.core.result.Field;
import com.mysql.cj.mysqlx.result.RowToElement;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.TimeZone;
import java.util.stream.IntStream;

public class DevapiRowFactory implements RowToElement<Row> {
   private Map<String, Integer> fieldNameToIndex;
   private ArrayList<Field> metadata;
   private TimeZone defaultTimeZone;

   public DevapiRowFactory(ArrayList<Field> metadata, TimeZone defaultTimeZone) {
      this.metadata = metadata;
      this.defaultTimeZone = defaultTimeZone;
   }

   private Map<String, Integer> getFieldNameToIndexMap() {
      if (this.fieldNameToIndex == null) {
         this.fieldNameToIndex = new HashMap();
         IntStream.range(0, this.metadata.size()).forEach((i) -> {
            Integer var10000 = (Integer)this.fieldNameToIndex.put(((Field)this.metadata.get(i)).getColumnLabel(), i);
         });
      }

      return this.fieldNameToIndex;
   }

   public Row apply(com.mysql.cj.api.result.Row internalRow) {
      return new RowImpl(internalRow, this::getFieldNameToIndexMap, this.defaultTimeZone);
   }
}
