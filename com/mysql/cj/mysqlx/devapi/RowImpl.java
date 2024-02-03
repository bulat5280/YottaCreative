package com.mysql.cj.mysqlx.devapi;

import com.mysql.cj.api.x.Row;
import com.mysql.cj.core.exceptions.DataReadException;
import com.mysql.cj.core.io.BigDecimalValueFactory;
import com.mysql.cj.core.io.BooleanValueFactory;
import com.mysql.cj.core.io.ByteValueFactory;
import com.mysql.cj.core.io.DbDocValueFactory;
import com.mysql.cj.core.io.DoubleValueFactory;
import com.mysql.cj.core.io.IntegerValueFactory;
import com.mysql.cj.core.io.LongValueFactory;
import com.mysql.cj.core.io.StringValueFactory;
import com.mysql.cj.jdbc.io.JdbcDateValueFactory;
import com.mysql.cj.jdbc.io.JdbcTimeValueFactory;
import com.mysql.cj.jdbc.io.JdbcTimestampValueFactory;
import com.mysql.cj.x.json.DbDoc;
import java.math.BigDecimal;
import java.sql.Date;
import java.sql.Time;
import java.sql.Timestamp;
import java.util.Map;
import java.util.TimeZone;
import java.util.function.Supplier;

public class RowImpl implements Row {
   private com.mysql.cj.api.result.Row row;
   private Supplier<Map<String, Integer>> lazyFieldNameToIndex;
   private TimeZone defaultTimeZone;

   public RowImpl(com.mysql.cj.api.result.Row row, Supplier<Map<String, Integer>> lazyFieldNameToIndex, TimeZone defaultTimeZone) {
      this.row = row;
      this.lazyFieldNameToIndex = lazyFieldNameToIndex;
      this.defaultTimeZone = defaultTimeZone;
   }

   private int fieldNameToIndex(String fieldName) {
      Integer idx = (Integer)((Map)this.lazyFieldNameToIndex.get()).get(fieldName);
      if (idx == null) {
         throw new DataReadException("Invalid column");
      } else {
         return idx;
      }
   }

   public BigDecimal getBigDecimal(String fieldName) {
      return this.getBigDecimal(this.fieldNameToIndex(fieldName));
   }

   public BigDecimal getBigDecimal(int pos) {
      return (BigDecimal)this.row.getValue(pos, new BigDecimalValueFactory());
   }

   public boolean getBoolean(String fieldName) {
      return this.getBoolean(this.fieldNameToIndex(fieldName));
   }

   public boolean getBoolean(int pos) {
      return (Boolean)this.row.getValue(pos, new BooleanValueFactory());
   }

   public byte getByte(String fieldName) {
      return this.getByte(this.fieldNameToIndex(fieldName));
   }

   public byte getByte(int pos) {
      return (Byte)this.row.getValue(pos, new ByteValueFactory());
   }

   public Date getDate(String fieldName) {
      return this.getDate(this.fieldNameToIndex(fieldName));
   }

   public Date getDate(int pos) {
      return (Date)this.row.getValue(pos, new JdbcDateValueFactory(this.defaultTimeZone));
   }

   public DbDoc getDbDoc(String fieldName) {
      return this.getDbDoc(this.fieldNameToIndex(fieldName));
   }

   public DbDoc getDbDoc(int pos) {
      return (DbDoc)this.row.getValue(pos, new DbDocValueFactory());
   }

   public double getDouble(String fieldName) {
      return this.getDouble(this.fieldNameToIndex(fieldName));
   }

   public double getDouble(int pos) {
      return (Double)this.row.getValue(pos, new DoubleValueFactory());
   }

   public int getInt(String fieldName) {
      return this.getInt(this.fieldNameToIndex(fieldName));
   }

   public int getInt(int pos) {
      return (Integer)this.row.getValue(pos, new IntegerValueFactory());
   }

   public long getLong(String fieldName) {
      return this.getLong(this.fieldNameToIndex(fieldName));
   }

   public long getLong(int pos) {
      return (Long)this.row.getValue(pos, new LongValueFactory());
   }

   public String getString(String fieldName) {
      return this.getString(this.fieldNameToIndex(fieldName));
   }

   public String getString(int pos) {
      return (String)this.row.getValue(pos, new StringValueFactory());
   }

   public Time getTime(String fieldName) {
      return this.getTime(this.fieldNameToIndex(fieldName));
   }

   public Time getTime(int pos) {
      return (Time)this.row.getValue(pos, new JdbcTimeValueFactory(this.defaultTimeZone));
   }

   public Timestamp getTimestamp(String fieldName) {
      return this.getTimestamp(this.fieldNameToIndex(fieldName));
   }

   public Timestamp getTimestamp(int pos) {
      return (Timestamp)this.row.getValue(pos, new JdbcTimestampValueFactory(this.defaultTimeZone));
   }
}
