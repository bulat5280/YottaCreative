package com.p6spy.engine.wrapper;

import com.p6spy.engine.common.ResultSetInformation;
import com.p6spy.engine.event.JdbcEventListener;
import java.io.InputStream;
import java.io.Reader;
import java.math.BigDecimal;
import java.net.URL;
import java.sql.Array;
import java.sql.Blob;
import java.sql.Clob;
import java.sql.Date;
import java.sql.NClob;
import java.sql.Ref;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.RowId;
import java.sql.SQLException;
import java.sql.SQLWarning;
import java.sql.SQLXML;
import java.sql.Statement;
import java.sql.Time;
import java.sql.Timestamp;
import java.util.Calendar;
import java.util.Map;

public class ResultSetWrapper extends AbstractWrapper implements ResultSet {
   private final ResultSet delegate;
   private final ResultSetInformation resultSetInformation;
   private final JdbcEventListener eventListener;

   public static ResultSet wrap(ResultSet delegate, ResultSetInformation resultSetInformation, JdbcEventListener eventListener) {
      return delegate == null ? null : new ResultSetWrapper(delegate, resultSetInformation, eventListener);
   }

   public ResultSetWrapper(ResultSet delegate, ResultSetInformation resultSetInformation, JdbcEventListener eventListener) {
      super(delegate);
      this.delegate = delegate;
      this.resultSetInformation = resultSetInformation;
      this.eventListener = eventListener;
   }

   public boolean next() throws SQLException {
      SQLException e = null;
      long start = System.nanoTime();
      boolean next = false;

      boolean var5;
      try {
         this.eventListener.onBeforeResultSetNext(this.resultSetInformation);
         next = this.delegate.next();
         var5 = next;
      } catch (SQLException var9) {
         e = var9;
         throw var9;
      } finally {
         this.eventListener.onAfterResultSetNext(this.resultSetInformation, System.nanoTime() - start, next, e);
      }

      return var5;
   }

   public void close() throws SQLException {
      SQLException e = null;

      try {
         this.delegate.close();
      } catch (SQLException var6) {
         e = var6;
         throw var6;
      } finally {
         this.eventListener.onAfterResultSetClose(this.resultSetInformation, e);
      }

   }

   public boolean wasNull() throws SQLException {
      return this.delegate.wasNull();
   }

   public String getString(int columnIndex) throws SQLException {
      Object var2 = null;

      try {
         String value = this.delegate.getString(columnIndex);
         this.eventListener.onAfterResultSetGet(this.resultSetInformation, columnIndex, value, (SQLException)null);
         return value;
      } catch (SQLException var4) {
         this.eventListener.onAfterResultSetGet(this.resultSetInformation, columnIndex, (Object)null, var4);
         throw var4;
      }
   }

   public boolean getBoolean(int columnIndex) throws SQLException {
      Object var2 = null;

      try {
         boolean value = this.delegate.getBoolean(columnIndex);
         this.eventListener.onAfterResultSetGet(this.resultSetInformation, columnIndex, value, (SQLException)null);
         return value;
      } catch (SQLException var4) {
         this.eventListener.onAfterResultSetGet(this.resultSetInformation, columnIndex, (Object)null, var4);
         throw var4;
      }
   }

   public byte getByte(int columnIndex) throws SQLException {
      Object var2 = null;

      try {
         byte value = this.delegate.getByte(columnIndex);
         this.eventListener.onAfterResultSetGet(this.resultSetInformation, columnIndex, value, (SQLException)null);
         return value;
      } catch (SQLException var4) {
         this.eventListener.onAfterResultSetGet(this.resultSetInformation, columnIndex, (Object)null, var4);
         throw var4;
      }
   }

   public short getShort(int columnIndex) throws SQLException {
      Object var2 = null;

      try {
         short value = this.delegate.getShort(columnIndex);
         this.eventListener.onAfterResultSetGet(this.resultSetInformation, columnIndex, value, (SQLException)null);
         return value;
      } catch (SQLException var4) {
         this.eventListener.onAfterResultSetGet(this.resultSetInformation, columnIndex, (Object)null, var4);
         throw var4;
      }
   }

   public int getInt(int columnIndex) throws SQLException {
      Object var2 = null;

      try {
         int value = this.delegate.getInt(columnIndex);
         this.eventListener.onAfterResultSetGet(this.resultSetInformation, columnIndex, value, (SQLException)null);
         return value;
      } catch (SQLException var4) {
         this.eventListener.onAfterResultSetGet(this.resultSetInformation, columnIndex, (Object)null, var4);
         throw var4;
      }
   }

   public long getLong(int columnIndex) throws SQLException {
      Object var2 = null;

      try {
         long value = this.delegate.getLong(columnIndex);
         this.eventListener.onAfterResultSetGet(this.resultSetInformation, columnIndex, value, (SQLException)null);
         return value;
      } catch (SQLException var5) {
         this.eventListener.onAfterResultSetGet(this.resultSetInformation, columnIndex, (Object)null, var5);
         throw var5;
      }
   }

   public float getFloat(int columnIndex) throws SQLException {
      Object var2 = null;

      try {
         float value = this.delegate.getFloat(columnIndex);
         this.eventListener.onAfterResultSetGet(this.resultSetInformation, columnIndex, value, (SQLException)null);
         return value;
      } catch (SQLException var4) {
         this.eventListener.onAfterResultSetGet(this.resultSetInformation, columnIndex, (Object)null, var4);
         throw var4;
      }
   }

   public double getDouble(int columnIndex) throws SQLException {
      Object var2 = null;

      try {
         double value = this.delegate.getDouble(columnIndex);
         this.eventListener.onAfterResultSetGet(this.resultSetInformation, columnIndex, value, (SQLException)null);
         return value;
      } catch (SQLException var5) {
         this.eventListener.onAfterResultSetGet(this.resultSetInformation, columnIndex, (Object)null, var5);
         throw var5;
      }
   }

   public BigDecimal getBigDecimal(int columnIndex, int scale) throws SQLException {
      Object var3 = null;

      try {
         BigDecimal value = this.delegate.getBigDecimal(columnIndex, scale);
         this.eventListener.onAfterResultSetGet(this.resultSetInformation, columnIndex, value, (SQLException)null);
         return value;
      } catch (SQLException var5) {
         this.eventListener.onAfterResultSetGet(this.resultSetInformation, columnIndex, (Object)null, var5);
         throw var5;
      }
   }

   public byte[] getBytes(int columnIndex) throws SQLException {
      Object var2 = null;

      try {
         byte[] value = this.delegate.getBytes(columnIndex);
         this.eventListener.onAfterResultSetGet(this.resultSetInformation, columnIndex, value, (SQLException)null);
         return value;
      } catch (SQLException var4) {
         this.eventListener.onAfterResultSetGet(this.resultSetInformation, columnIndex, (Object)null, var4);
         throw var4;
      }
   }

   public Date getDate(int columnIndex) throws SQLException {
      Object var2 = null;

      try {
         Date value = this.delegate.getDate(columnIndex);
         this.eventListener.onAfterResultSetGet(this.resultSetInformation, columnIndex, value, (SQLException)null);
         return value;
      } catch (SQLException var4) {
         this.eventListener.onAfterResultSetGet(this.resultSetInformation, columnIndex, (Object)null, var4);
         throw var4;
      }
   }

   public Time getTime(int columnIndex) throws SQLException {
      Object var2 = null;

      try {
         Time value = this.delegate.getTime(columnIndex);
         this.eventListener.onAfterResultSetGet(this.resultSetInformation, columnIndex, value, (SQLException)null);
         return value;
      } catch (SQLException var4) {
         this.eventListener.onAfterResultSetGet(this.resultSetInformation, columnIndex, (Object)null, var4);
         throw var4;
      }
   }

   public Timestamp getTimestamp(int columnIndex) throws SQLException {
      Object var2 = null;

      try {
         Timestamp value = this.delegate.getTimestamp(columnIndex);
         this.eventListener.onAfterResultSetGet(this.resultSetInformation, columnIndex, value, (SQLException)null);
         return value;
      } catch (SQLException var4) {
         this.eventListener.onAfterResultSetGet(this.resultSetInformation, columnIndex, (Object)null, var4);
         throw var4;
      }
   }

   public InputStream getAsciiStream(int columnIndex) throws SQLException {
      Object var2 = null;

      try {
         InputStream value = this.delegate.getAsciiStream(columnIndex);
         this.eventListener.onAfterResultSetGet(this.resultSetInformation, columnIndex, value, (SQLException)null);
         return value;
      } catch (SQLException var4) {
         this.eventListener.onAfterResultSetGet(this.resultSetInformation, columnIndex, (Object)null, var4);
         throw var4;
      }
   }

   public InputStream getUnicodeStream(int columnIndex) throws SQLException {
      Object var2 = null;

      try {
         InputStream value = this.delegate.getUnicodeStream(columnIndex);
         this.eventListener.onAfterResultSetGet(this.resultSetInformation, columnIndex, value, (SQLException)null);
         return value;
      } catch (SQLException var4) {
         this.eventListener.onAfterResultSetGet(this.resultSetInformation, columnIndex, (Object)null, var4);
         throw var4;
      }
   }

   public InputStream getBinaryStream(int columnIndex) throws SQLException {
      Object var2 = null;

      try {
         InputStream value = this.delegate.getBinaryStream(columnIndex);
         this.eventListener.onAfterResultSetGet(this.resultSetInformation, columnIndex, value, (SQLException)null);
         return value;
      } catch (SQLException var4) {
         this.eventListener.onAfterResultSetGet(this.resultSetInformation, columnIndex, (Object)null, var4);
         throw var4;
      }
   }

   public String getString(String columnLabel) throws SQLException {
      Object var2 = null;

      try {
         String value = this.delegate.getString(columnLabel);
         this.eventListener.onAfterResultSetGet(this.resultSetInformation, columnLabel, value, (SQLException)null);
         return value;
      } catch (SQLException var4) {
         this.eventListener.onAfterResultSetGet(this.resultSetInformation, columnLabel, (Object)null, var4);
         throw var4;
      }
   }

   public boolean getBoolean(String columnLabel) throws SQLException {
      Object var2 = null;

      try {
         boolean value = this.delegate.getBoolean(columnLabel);
         this.eventListener.onAfterResultSetGet(this.resultSetInformation, columnLabel, value, (SQLException)null);
         return value;
      } catch (SQLException var4) {
         this.eventListener.onAfterResultSetGet(this.resultSetInformation, columnLabel, (Object)null, var4);
         throw var4;
      }
   }

   public byte getByte(String columnLabel) throws SQLException {
      Object var2 = null;

      try {
         byte value = this.delegate.getByte(columnLabel);
         this.eventListener.onAfterResultSetGet(this.resultSetInformation, columnLabel, value, (SQLException)null);
         return value;
      } catch (SQLException var4) {
         this.eventListener.onAfterResultSetGet(this.resultSetInformation, columnLabel, (Object)null, var4);
         throw var4;
      }
   }

   public short getShort(String columnLabel) throws SQLException {
      Object var2 = null;

      try {
         short value = this.delegate.getShort(columnLabel);
         this.eventListener.onAfterResultSetGet(this.resultSetInformation, columnLabel, value, (SQLException)null);
         return value;
      } catch (SQLException var4) {
         this.eventListener.onAfterResultSetGet(this.resultSetInformation, columnLabel, (Object)null, var4);
         throw var4;
      }
   }

   public int getInt(String columnLabel) throws SQLException {
      Object var2 = null;

      try {
         int value = this.delegate.getInt(columnLabel);
         this.eventListener.onAfterResultSetGet(this.resultSetInformation, columnLabel, value, (SQLException)null);
         return value;
      } catch (SQLException var4) {
         this.eventListener.onAfterResultSetGet(this.resultSetInformation, columnLabel, (Object)null, var4);
         throw var4;
      }
   }

   public long getLong(String columnLabel) throws SQLException {
      Object var2 = null;

      try {
         long value = this.delegate.getLong(columnLabel);
         this.eventListener.onAfterResultSetGet(this.resultSetInformation, columnLabel, value, (SQLException)null);
         return value;
      } catch (SQLException var5) {
         this.eventListener.onAfterResultSetGet(this.resultSetInformation, columnLabel, (Object)null, var5);
         throw var5;
      }
   }

   public float getFloat(String columnLabel) throws SQLException {
      Object var2 = null;

      try {
         float value = this.delegate.getFloat(columnLabel);
         this.eventListener.onAfterResultSetGet(this.resultSetInformation, columnLabel, value, (SQLException)null);
         return value;
      } catch (SQLException var4) {
         this.eventListener.onAfterResultSetGet(this.resultSetInformation, columnLabel, (Object)null, var4);
         throw var4;
      }
   }

   public double getDouble(String columnLabel) throws SQLException {
      Object var2 = null;

      try {
         double value = this.delegate.getDouble(columnLabel);
         this.eventListener.onAfterResultSetGet(this.resultSetInformation, columnLabel, value, (SQLException)null);
         return value;
      } catch (SQLException var5) {
         this.eventListener.onAfterResultSetGet(this.resultSetInformation, columnLabel, (Object)null, var5);
         throw var5;
      }
   }

   public BigDecimal getBigDecimal(String columnLabel, int scale) throws SQLException {
      Object var3 = null;

      try {
         BigDecimal value = this.delegate.getBigDecimal(columnLabel, scale);
         this.eventListener.onAfterResultSetGet(this.resultSetInformation, columnLabel, value, (SQLException)null);
         return value;
      } catch (SQLException var5) {
         this.eventListener.onAfterResultSetGet(this.resultSetInformation, columnLabel, (Object)null, var5);
         throw var5;
      }
   }

   public byte[] getBytes(String columnLabel) throws SQLException {
      Object var2 = null;

      try {
         byte[] value = this.delegate.getBytes(columnLabel);
         this.eventListener.onAfterResultSetGet(this.resultSetInformation, columnLabel, value, (SQLException)null);
         return value;
      } catch (SQLException var4) {
         this.eventListener.onAfterResultSetGet(this.resultSetInformation, columnLabel, (Object)null, var4);
         throw var4;
      }
   }

   public Date getDate(String columnLabel) throws SQLException {
      Object var2 = null;

      try {
         Date value = this.delegate.getDate(columnLabel);
         this.eventListener.onAfterResultSetGet(this.resultSetInformation, columnLabel, value, (SQLException)null);
         return value;
      } catch (SQLException var4) {
         this.eventListener.onAfterResultSetGet(this.resultSetInformation, columnLabel, (Object)null, var4);
         throw var4;
      }
   }

   public Time getTime(String columnLabel) throws SQLException {
      Object var2 = null;

      try {
         Time value = this.delegate.getTime(columnLabel);
         this.eventListener.onAfterResultSetGet(this.resultSetInformation, columnLabel, value, (SQLException)null);
         return value;
      } catch (SQLException var4) {
         this.eventListener.onAfterResultSetGet(this.resultSetInformation, columnLabel, (Object)null, var4);
         throw var4;
      }
   }

   public Timestamp getTimestamp(String columnLabel) throws SQLException {
      Object var2 = null;

      try {
         Timestamp value = this.delegate.getTimestamp(columnLabel);
         this.eventListener.onAfterResultSetGet(this.resultSetInformation, columnLabel, value, (SQLException)null);
         return value;
      } catch (SQLException var4) {
         this.eventListener.onAfterResultSetGet(this.resultSetInformation, columnLabel, (Object)null, var4);
         throw var4;
      }
   }

   public InputStream getAsciiStream(String columnLabel) throws SQLException {
      Object var2 = null;

      try {
         InputStream value = this.delegate.getAsciiStream(columnLabel);
         this.eventListener.onAfterResultSetGet(this.resultSetInformation, columnLabel, value, (SQLException)null);
         return value;
      } catch (SQLException var4) {
         this.eventListener.onAfterResultSetGet(this.resultSetInformation, columnLabel, (Object)null, var4);
         throw var4;
      }
   }

   public InputStream getUnicodeStream(String columnLabel) throws SQLException {
      Object var2 = null;

      try {
         InputStream value = this.delegate.getUnicodeStream(columnLabel);
         this.eventListener.onAfterResultSetGet(this.resultSetInformation, columnLabel, value, (SQLException)null);
         return value;
      } catch (SQLException var4) {
         this.eventListener.onAfterResultSetGet(this.resultSetInformation, columnLabel, (Object)null, var4);
         throw var4;
      }
   }

   public InputStream getBinaryStream(String columnLabel) throws SQLException {
      Object var2 = null;

      try {
         InputStream value = this.delegate.getBinaryStream(columnLabel);
         this.eventListener.onAfterResultSetGet(this.resultSetInformation, columnLabel, value, (SQLException)null);
         return value;
      } catch (SQLException var4) {
         this.eventListener.onAfterResultSetGet(this.resultSetInformation, columnLabel, (Object)null, var4);
         throw var4;
      }
   }

   public SQLWarning getWarnings() throws SQLException {
      return this.delegate.getWarnings();
   }

   public void clearWarnings() throws SQLException {
      this.delegate.clearWarnings();
   }

   public String getCursorName() throws SQLException {
      return this.delegate.getCursorName();
   }

   public ResultSetMetaData getMetaData() throws SQLException {
      return this.delegate.getMetaData();
   }

   public Object getObject(int columnIndex) throws SQLException {
      Object var2 = null;

      try {
         Object value = this.delegate.getObject(columnIndex);
         this.eventListener.onAfterResultSetGet(this.resultSetInformation, columnIndex, value, (SQLException)null);
         return value;
      } catch (SQLException var4) {
         this.eventListener.onAfterResultSetGet(this.resultSetInformation, columnIndex, (Object)null, var4);
         throw var4;
      }
   }

   public Object getObject(String columnLabel) throws SQLException {
      Object var2 = null;

      try {
         Object value = this.delegate.getObject(columnLabel);
         this.eventListener.onAfterResultSetGet(this.resultSetInformation, columnLabel, value, (SQLException)null);
         return value;
      } catch (SQLException var4) {
         this.eventListener.onAfterResultSetGet(this.resultSetInformation, columnLabel, (Object)null, var4);
         throw var4;
      }
   }

   public int findColumn(String columnLabel) throws SQLException {
      return this.delegate.findColumn(columnLabel);
   }

   public Reader getCharacterStream(int columnIndex) throws SQLException {
      Object var2 = null;

      try {
         Reader value = this.delegate.getCharacterStream(columnIndex);
         this.eventListener.onAfterResultSetGet(this.resultSetInformation, columnIndex, value, (SQLException)null);
         return value;
      } catch (SQLException var4) {
         this.eventListener.onAfterResultSetGet(this.resultSetInformation, columnIndex, (Object)null, var4);
         throw var4;
      }
   }

   public Reader getCharacterStream(String columnLabel) throws SQLException {
      Object var2 = null;

      try {
         Reader value = this.delegate.getCharacterStream(columnLabel);
         this.eventListener.onAfterResultSetGet(this.resultSetInformation, columnLabel, value, (SQLException)null);
         return value;
      } catch (SQLException var4) {
         this.eventListener.onAfterResultSetGet(this.resultSetInformation, columnLabel, (Object)null, var4);
         throw var4;
      }
   }

   public BigDecimal getBigDecimal(int columnIndex) throws SQLException {
      Object var2 = null;

      try {
         BigDecimal value = this.delegate.getBigDecimal(columnIndex);
         this.eventListener.onAfterResultSetGet(this.resultSetInformation, columnIndex, value, (SQLException)null);
         return value;
      } catch (SQLException var4) {
         this.eventListener.onAfterResultSetGet(this.resultSetInformation, columnIndex, (Object)null, var4);
         throw var4;
      }
   }

   public BigDecimal getBigDecimal(String columnLabel) throws SQLException {
      Object var2 = null;

      try {
         BigDecimal value = this.delegate.getBigDecimal(columnLabel);
         this.eventListener.onAfterResultSetGet(this.resultSetInformation, columnLabel, value, (SQLException)null);
         return value;
      } catch (SQLException var4) {
         this.eventListener.onAfterResultSetGet(this.resultSetInformation, columnLabel, (Object)null, var4);
         throw var4;
      }
   }

   public boolean isBeforeFirst() throws SQLException {
      return this.delegate.isBeforeFirst();
   }

   public boolean isAfterLast() throws SQLException {
      return this.delegate.isAfterLast();
   }

   public boolean isFirst() throws SQLException {
      return this.delegate.isFirst();
   }

   public boolean isLast() throws SQLException {
      return this.delegate.isLast();
   }

   public void beforeFirst() throws SQLException {
      this.delegate.beforeFirst();
   }

   public void afterLast() throws SQLException {
      this.delegate.afterLast();
   }

   public boolean first() throws SQLException {
      return this.delegate.first();
   }

   public boolean last() throws SQLException {
      return this.delegate.last();
   }

   public int getRow() throws SQLException {
      return this.delegate.getRow();
   }

   public boolean absolute(int row) throws SQLException {
      return this.delegate.absolute(row);
   }

   public boolean relative(int rows) throws SQLException {
      return this.delegate.relative(rows);
   }

   public boolean previous() throws SQLException {
      return this.delegate.previous();
   }

   public void setFetchDirection(int direction) throws SQLException {
      this.delegate.setFetchDirection(direction);
   }

   public int getFetchDirection() throws SQLException {
      return this.delegate.getFetchDirection();
   }

   public void setFetchSize(int rows) throws SQLException {
      this.delegate.setFetchSize(rows);
   }

   public int getFetchSize() throws SQLException {
      return this.delegate.getFetchSize();
   }

   public int getType() throws SQLException {
      return this.delegate.getType();
   }

   public int getConcurrency() throws SQLException {
      return this.delegate.getConcurrency();
   }

   public boolean rowUpdated() throws SQLException {
      return this.delegate.rowUpdated();
   }

   public boolean rowInserted() throws SQLException {
      return this.delegate.rowInserted();
   }

   public boolean rowDeleted() throws SQLException {
      return this.delegate.rowDeleted();
   }

   public void updateNull(int columnIndex) throws SQLException {
      this.delegate.updateNull(columnIndex);
   }

   public void updateBoolean(int columnIndex, boolean x) throws SQLException {
      this.delegate.updateBoolean(columnIndex, x);
   }

   public void updateByte(int columnIndex, byte x) throws SQLException {
      this.delegate.updateByte(columnIndex, x);
   }

   public void updateShort(int columnIndex, short x) throws SQLException {
      this.delegate.updateShort(columnIndex, x);
   }

   public void updateInt(int columnIndex, int x) throws SQLException {
      this.delegate.updateInt(columnIndex, x);
   }

   public void updateLong(int columnIndex, long x) throws SQLException {
      this.delegate.updateLong(columnIndex, x);
   }

   public void updateFloat(int columnIndex, float x) throws SQLException {
      this.delegate.updateFloat(columnIndex, x);
   }

   public void updateDouble(int columnIndex, double x) throws SQLException {
      this.delegate.updateDouble(columnIndex, x);
   }

   public void updateBigDecimal(int columnIndex, BigDecimal x) throws SQLException {
      this.delegate.updateBigDecimal(columnIndex, x);
   }

   public void updateString(int columnIndex, String x) throws SQLException {
      this.delegate.updateString(columnIndex, x);
   }

   public void updateBytes(int columnIndex, byte[] x) throws SQLException {
      this.delegate.updateBytes(columnIndex, x);
   }

   public void updateDate(int columnIndex, Date x) throws SQLException {
      this.delegate.updateDate(columnIndex, x);
   }

   public void updateTime(int columnIndex, Time x) throws SQLException {
      this.delegate.updateTime(columnIndex, x);
   }

   public void updateTimestamp(int columnIndex, Timestamp x) throws SQLException {
      this.delegate.updateTimestamp(columnIndex, x);
   }

   public void updateAsciiStream(int columnIndex, InputStream x, int length) throws SQLException {
      this.delegate.updateAsciiStream(columnIndex, x, length);
   }

   public void updateBinaryStream(int columnIndex, InputStream x, int length) throws SQLException {
      this.delegate.updateBinaryStream(columnIndex, x, length);
   }

   public void updateCharacterStream(int columnIndex, Reader x, int length) throws SQLException {
      this.delegate.updateCharacterStream(columnIndex, x, length);
   }

   public void updateObject(int columnIndex, Object x, int scaleOrLength) throws SQLException {
      this.delegate.updateObject(columnIndex, x, scaleOrLength);
   }

   public void updateObject(int columnIndex, Object x) throws SQLException {
      this.delegate.updateObject(columnIndex, x);
   }

   public void updateNull(String columnLabel) throws SQLException {
      this.delegate.updateNull(columnLabel);
   }

   public void updateBoolean(String columnLabel, boolean x) throws SQLException {
      this.delegate.updateBoolean(columnLabel, x);
   }

   public void updateByte(String columnLabel, byte x) throws SQLException {
      this.delegate.updateByte(columnLabel, x);
   }

   public void updateShort(String columnLabel, short x) throws SQLException {
      this.delegate.updateShort(columnLabel, x);
   }

   public void updateInt(String columnLabel, int x) throws SQLException {
      this.delegate.updateInt(columnLabel, x);
   }

   public void updateLong(String columnLabel, long x) throws SQLException {
      this.delegate.updateLong(columnLabel, x);
   }

   public void updateFloat(String columnLabel, float x) throws SQLException {
      this.delegate.updateFloat(columnLabel, x);
   }

   public void updateDouble(String columnLabel, double x) throws SQLException {
      this.delegate.updateDouble(columnLabel, x);
   }

   public void updateBigDecimal(String columnLabel, BigDecimal x) throws SQLException {
      this.delegate.updateBigDecimal(columnLabel, x);
   }

   public void updateString(String columnLabel, String x) throws SQLException {
      this.delegate.updateString(columnLabel, x);
   }

   public void updateBytes(String columnLabel, byte[] x) throws SQLException {
      this.delegate.updateBytes(columnLabel, x);
   }

   public void updateDate(String columnLabel, Date x) throws SQLException {
      this.delegate.updateDate(columnLabel, x);
   }

   public void updateTime(String columnLabel, Time x) throws SQLException {
      this.delegate.updateTime(columnLabel, x);
   }

   public void updateTimestamp(String columnLabel, Timestamp x) throws SQLException {
      this.delegate.updateTimestamp(columnLabel, x);
   }

   public void updateAsciiStream(String columnLabel, InputStream x, int length) throws SQLException {
      this.delegate.updateAsciiStream(columnLabel, x, length);
   }

   public void updateBinaryStream(String columnLabel, InputStream x, int length) throws SQLException {
      this.delegate.updateBinaryStream(columnLabel, x, length);
   }

   public void updateCharacterStream(String columnLabel, Reader reader, int length) throws SQLException {
      this.delegate.updateCharacterStream(columnLabel, reader, length);
   }

   public void updateObject(String columnLabel, Object x, int scaleOrLength) throws SQLException {
      this.delegate.updateObject(columnLabel, x, scaleOrLength);
   }

   public void updateObject(String columnLabel, Object x) throws SQLException {
      this.delegate.updateObject(columnLabel, x);
   }

   public void insertRow() throws SQLException {
      this.delegate.insertRow();
   }

   public void updateRow() throws SQLException {
      this.delegate.updateRow();
   }

   public void deleteRow() throws SQLException {
      this.delegate.deleteRow();
   }

   public void refreshRow() throws SQLException {
      this.delegate.refreshRow();
   }

   public void cancelRowUpdates() throws SQLException {
      this.delegate.cancelRowUpdates();
   }

   public void moveToInsertRow() throws SQLException {
      this.delegate.moveToInsertRow();
   }

   public void moveToCurrentRow() throws SQLException {
      this.delegate.moveToCurrentRow();
   }

   public Statement getStatement() throws SQLException {
      return this.delegate.getStatement();
   }

   public Object getObject(int columnIndex, Map<String, Class<?>> map) throws SQLException {
      Object var3 = null;

      try {
         Object value = this.delegate.getObject(columnIndex, map);
         this.eventListener.onAfterResultSetGet(this.resultSetInformation, columnIndex, value, (SQLException)null);
         return value;
      } catch (SQLException var5) {
         this.eventListener.onAfterResultSetGet(this.resultSetInformation, columnIndex, (Object)null, var5);
         throw var5;
      }
   }

   public Ref getRef(int columnIndex) throws SQLException {
      Object var2 = null;

      try {
         Ref value = this.delegate.getRef(columnIndex);
         this.eventListener.onAfterResultSetGet(this.resultSetInformation, columnIndex, value, (SQLException)null);
         return value;
      } catch (SQLException var4) {
         this.eventListener.onAfterResultSetGet(this.resultSetInformation, columnIndex, (Object)null, var4);
         throw var4;
      }
   }

   public Blob getBlob(int columnIndex) throws SQLException {
      Object var2 = null;

      try {
         Blob value = this.delegate.getBlob(columnIndex);
         this.eventListener.onAfterResultSetGet(this.resultSetInformation, columnIndex, value, (SQLException)null);
         return value;
      } catch (SQLException var4) {
         this.eventListener.onAfterResultSetGet(this.resultSetInformation, columnIndex, (Object)null, var4);
         throw var4;
      }
   }

   public Clob getClob(int columnIndex) throws SQLException {
      Object var2 = null;

      try {
         Clob value = this.delegate.getClob(columnIndex);
         this.eventListener.onAfterResultSetGet(this.resultSetInformation, columnIndex, value, (SQLException)null);
         return value;
      } catch (SQLException var4) {
         this.eventListener.onAfterResultSetGet(this.resultSetInformation, columnIndex, (Object)null, var4);
         throw var4;
      }
   }

   public Array getArray(int columnIndex) throws SQLException {
      Object var2 = null;

      try {
         Array value = this.delegate.getArray(columnIndex);
         this.eventListener.onAfterResultSetGet(this.resultSetInformation, columnIndex, value, (SQLException)null);
         return value;
      } catch (SQLException var4) {
         this.eventListener.onAfterResultSetGet(this.resultSetInformation, columnIndex, (Object)null, var4);
         throw var4;
      }
   }

   public Object getObject(String columnLabel, Map<String, Class<?>> map) throws SQLException {
      Object var3 = null;

      try {
         Object value = this.delegate.getObject(columnLabel, map);
         this.eventListener.onAfterResultSetGet(this.resultSetInformation, columnLabel, value, (SQLException)null);
         return value;
      } catch (SQLException var5) {
         this.eventListener.onAfterResultSetGet(this.resultSetInformation, columnLabel, (Object)null, var5);
         throw var5;
      }
   }

   public Ref getRef(String columnLabel) throws SQLException {
      Object var2 = null;

      try {
         Ref value = this.delegate.getRef(columnLabel);
         this.eventListener.onAfterResultSetGet(this.resultSetInformation, columnLabel, value, (SQLException)null);
         return value;
      } catch (SQLException var4) {
         this.eventListener.onAfterResultSetGet(this.resultSetInformation, columnLabel, (Object)null, var4);
         throw var4;
      }
   }

   public Blob getBlob(String columnLabel) throws SQLException {
      Object var2 = null;

      try {
         Blob value = this.delegate.getBlob(columnLabel);
         this.eventListener.onAfterResultSetGet(this.resultSetInformation, columnLabel, value, (SQLException)null);
         return value;
      } catch (SQLException var4) {
         this.eventListener.onAfterResultSetGet(this.resultSetInformation, columnLabel, (Object)null, var4);
         throw var4;
      }
   }

   public Clob getClob(String columnLabel) throws SQLException {
      Object var2 = null;

      try {
         Clob value = this.delegate.getClob(columnLabel);
         this.eventListener.onAfterResultSetGet(this.resultSetInformation, columnLabel, value, (SQLException)null);
         return value;
      } catch (SQLException var4) {
         this.eventListener.onAfterResultSetGet(this.resultSetInformation, columnLabel, (Object)null, var4);
         throw var4;
      }
   }

   public Array getArray(String columnLabel) throws SQLException {
      Object var2 = null;

      try {
         Array value = this.delegate.getArray(columnLabel);
         this.eventListener.onAfterResultSetGet(this.resultSetInformation, columnLabel, value, (SQLException)null);
         return value;
      } catch (SQLException var4) {
         this.eventListener.onAfterResultSetGet(this.resultSetInformation, columnLabel, (Object)null, var4);
         throw var4;
      }
   }

   public Date getDate(int columnIndex, Calendar cal) throws SQLException {
      Object var3 = null;

      try {
         Date value = this.delegate.getDate(columnIndex, cal);
         this.eventListener.onAfterResultSetGet(this.resultSetInformation, columnIndex, value, (SQLException)null);
         return value;
      } catch (SQLException var5) {
         this.eventListener.onAfterResultSetGet(this.resultSetInformation, columnIndex, (Object)null, var5);
         throw var5;
      }
   }

   public Date getDate(String columnLabel, Calendar cal) throws SQLException {
      Object var3 = null;

      try {
         Date value = this.delegate.getDate(columnLabel, cal);
         this.eventListener.onAfterResultSetGet(this.resultSetInformation, columnLabel, value, (SQLException)null);
         return value;
      } catch (SQLException var5) {
         this.eventListener.onAfterResultSetGet(this.resultSetInformation, columnLabel, (Object)null, var5);
         throw var5;
      }
   }

   public Time getTime(int columnIndex, Calendar cal) throws SQLException {
      Object var3 = null;

      try {
         Time value = this.delegate.getTime(columnIndex, cal);
         this.eventListener.onAfterResultSetGet(this.resultSetInformation, columnIndex, value, (SQLException)null);
         return value;
      } catch (SQLException var5) {
         this.eventListener.onAfterResultSetGet(this.resultSetInformation, columnIndex, (Object)null, var5);
         throw var5;
      }
   }

   public Time getTime(String columnLabel, Calendar cal) throws SQLException {
      Object var3 = null;

      try {
         Time value = this.delegate.getTime(columnLabel, cal);
         this.eventListener.onAfterResultSetGet(this.resultSetInformation, columnLabel, value, (SQLException)null);
         return value;
      } catch (SQLException var5) {
         this.eventListener.onAfterResultSetGet(this.resultSetInformation, columnLabel, (Object)null, var5);
         throw var5;
      }
   }

   public Timestamp getTimestamp(int columnIndex, Calendar cal) throws SQLException {
      Object var3 = null;

      try {
         Timestamp value = this.delegate.getTimestamp(columnIndex, cal);
         this.eventListener.onAfterResultSetGet(this.resultSetInformation, columnIndex, value, (SQLException)null);
         return value;
      } catch (SQLException var5) {
         this.eventListener.onAfterResultSetGet(this.resultSetInformation, columnIndex, (Object)null, var5);
         throw var5;
      }
   }

   public Timestamp getTimestamp(String columnLabel, Calendar cal) throws SQLException {
      Object var3 = null;

      try {
         Timestamp value = this.delegate.getTimestamp(columnLabel, cal);
         this.eventListener.onAfterResultSetGet(this.resultSetInformation, columnLabel, value, (SQLException)null);
         return value;
      } catch (SQLException var5) {
         this.eventListener.onAfterResultSetGet(this.resultSetInformation, columnLabel, (Object)null, var5);
         throw var5;
      }
   }

   public URL getURL(int columnIndex) throws SQLException {
      Object var2 = null;

      try {
         URL value = this.delegate.getURL(columnIndex);
         this.eventListener.onAfterResultSetGet(this.resultSetInformation, columnIndex, value, (SQLException)null);
         return value;
      } catch (SQLException var4) {
         this.eventListener.onAfterResultSetGet(this.resultSetInformation, columnIndex, (Object)null, var4);
         throw var4;
      }
   }

   public URL getURL(String columnLabel) throws SQLException {
      Object var2 = null;

      try {
         URL value = this.delegate.getURL(columnLabel);
         this.eventListener.onAfterResultSetGet(this.resultSetInformation, columnLabel, value, (SQLException)null);
         return value;
      } catch (SQLException var4) {
         this.eventListener.onAfterResultSetGet(this.resultSetInformation, columnLabel, (Object)null, var4);
         throw var4;
      }
   }

   public void updateRef(int columnIndex, Ref x) throws SQLException {
      this.delegate.updateRef(columnIndex, x);
   }

   public void updateRef(String columnLabel, Ref x) throws SQLException {
      this.delegate.updateRef(columnLabel, x);
   }

   public void updateBlob(int columnIndex, Blob x) throws SQLException {
      this.delegate.updateBlob(columnIndex, x);
   }

   public void updateBlob(String columnLabel, Blob x) throws SQLException {
      this.delegate.updateBlob(columnLabel, x);
   }

   public void updateClob(int columnIndex, Clob x) throws SQLException {
      this.delegate.updateClob(columnIndex, x);
   }

   public void updateClob(String columnLabel, Clob x) throws SQLException {
      this.delegate.updateClob(columnLabel, x);
   }

   public void updateArray(int columnIndex, Array x) throws SQLException {
      this.delegate.updateArray(columnIndex, x);
   }

   public void updateArray(String columnLabel, Array x) throws SQLException {
      this.delegate.updateArray(columnLabel, x);
   }

   public RowId getRowId(int columnIndex) throws SQLException {
      Object var2 = null;

      try {
         RowId value = this.delegate.getRowId(columnIndex);
         this.eventListener.onAfterResultSetGet(this.resultSetInformation, columnIndex, value, (SQLException)null);
         return value;
      } catch (SQLException var4) {
         this.eventListener.onAfterResultSetGet(this.resultSetInformation, columnIndex, (Object)null, var4);
         throw var4;
      }
   }

   public RowId getRowId(String columnLabel) throws SQLException {
      Object var2 = null;

      try {
         RowId value = this.delegate.getRowId(columnLabel);
         this.eventListener.onAfterResultSetGet(this.resultSetInformation, columnLabel, value, (SQLException)null);
         return value;
      } catch (SQLException var4) {
         this.eventListener.onAfterResultSetGet(this.resultSetInformation, columnLabel, (Object)null, var4);
         throw var4;
      }
   }

   public void updateRowId(int columnIndex, RowId x) throws SQLException {
      this.delegate.updateRowId(columnIndex, x);
   }

   public void updateRowId(String columnLabel, RowId x) throws SQLException {
      this.delegate.updateRowId(columnLabel, x);
   }

   public int getHoldability() throws SQLException {
      return this.delegate.getHoldability();
   }

   public boolean isClosed() throws SQLException {
      return this.delegate.isClosed();
   }

   public void updateNString(int columnIndex, String nString) throws SQLException {
      this.delegate.updateNString(columnIndex, nString);
   }

   public void updateNString(String columnLabel, String nString) throws SQLException {
      this.delegate.updateNString(columnLabel, nString);
   }

   public void updateNClob(int columnIndex, NClob nClob) throws SQLException {
      this.delegate.updateNClob(columnIndex, nClob);
   }

   public void updateNClob(String columnLabel, NClob nClob) throws SQLException {
      this.delegate.updateNClob(columnLabel, nClob);
   }

   public NClob getNClob(int columnIndex) throws SQLException {
      Object var2 = null;

      try {
         NClob value = this.delegate.getNClob(columnIndex);
         this.eventListener.onAfterResultSetGet(this.resultSetInformation, columnIndex, value, (SQLException)null);
         return value;
      } catch (SQLException var4) {
         this.eventListener.onAfterResultSetGet(this.resultSetInformation, columnIndex, (Object)null, var4);
         throw var4;
      }
   }

   public NClob getNClob(String columnLabel) throws SQLException {
      Object var2 = null;

      try {
         NClob value = this.delegate.getNClob(columnLabel);
         this.eventListener.onAfterResultSetGet(this.resultSetInformation, columnLabel, value, (SQLException)null);
         return value;
      } catch (SQLException var4) {
         this.eventListener.onAfterResultSetGet(this.resultSetInformation, columnLabel, (Object)null, var4);
         throw var4;
      }
   }

   public SQLXML getSQLXML(int columnIndex) throws SQLException {
      Object var2 = null;

      try {
         SQLXML value = this.delegate.getSQLXML(columnIndex);
         this.eventListener.onAfterResultSetGet(this.resultSetInformation, columnIndex, value, (SQLException)null);
         return value;
      } catch (SQLException var4) {
         this.eventListener.onAfterResultSetGet(this.resultSetInformation, columnIndex, (Object)null, var4);
         throw var4;
      }
   }

   public SQLXML getSQLXML(String columnLabel) throws SQLException {
      Object var2 = null;

      try {
         SQLXML value = this.delegate.getSQLXML(columnLabel);
         this.eventListener.onAfterResultSetGet(this.resultSetInformation, columnLabel, value, (SQLException)null);
         return value;
      } catch (SQLException var4) {
         this.eventListener.onAfterResultSetGet(this.resultSetInformation, columnLabel, (Object)null, var4);
         throw var4;
      }
   }

   public void updateSQLXML(int columnIndex, SQLXML xmlObject) throws SQLException {
      this.delegate.updateSQLXML(columnIndex, xmlObject);
   }

   public void updateSQLXML(String columnLabel, SQLXML xmlObject) throws SQLException {
      this.delegate.updateSQLXML(columnLabel, xmlObject);
   }

   public String getNString(int columnIndex) throws SQLException {
      Object var2 = null;

      try {
         String value = this.delegate.getNString(columnIndex);
         this.eventListener.onAfterResultSetGet(this.resultSetInformation, columnIndex, value, (SQLException)null);
         return value;
      } catch (SQLException var4) {
         this.eventListener.onAfterResultSetGet(this.resultSetInformation, columnIndex, (Object)null, var4);
         throw var4;
      }
   }

   public String getNString(String columnLabel) throws SQLException {
      Object var2 = null;

      try {
         String value = this.delegate.getNString(columnLabel);
         this.eventListener.onAfterResultSetGet(this.resultSetInformation, columnLabel, value, (SQLException)null);
         return value;
      } catch (SQLException var4) {
         this.eventListener.onAfterResultSetGet(this.resultSetInformation, columnLabel, (Object)null, var4);
         throw var4;
      }
   }

   public Reader getNCharacterStream(int columnIndex) throws SQLException {
      Object var2 = null;

      try {
         Reader value = this.delegate.getNCharacterStream(columnIndex);
         this.eventListener.onAfterResultSetGet(this.resultSetInformation, columnIndex, value, (SQLException)null);
         return value;
      } catch (SQLException var4) {
         this.eventListener.onAfterResultSetGet(this.resultSetInformation, columnIndex, (Object)null, var4);
         throw var4;
      }
   }

   public Reader getNCharacterStream(String columnLabel) throws SQLException {
      Object var2 = null;

      try {
         Reader value = this.delegate.getNCharacterStream(columnLabel);
         this.eventListener.onAfterResultSetGet(this.resultSetInformation, columnLabel, value, (SQLException)null);
         return value;
      } catch (SQLException var4) {
         this.eventListener.onAfterResultSetGet(this.resultSetInformation, columnLabel, (Object)null, var4);
         throw var4;
      }
   }

   public void updateNCharacterStream(int columnIndex, Reader x, long length) throws SQLException {
      this.delegate.updateNCharacterStream(columnIndex, x, length);
   }

   public void updateNCharacterStream(String columnLabel, Reader reader, long length) throws SQLException {
      this.delegate.updateNCharacterStream(columnLabel, reader, length);
   }

   public void updateAsciiStream(int columnIndex, InputStream x, long length) throws SQLException {
      this.delegate.updateAsciiStream(columnIndex, x, length);
   }

   public void updateBinaryStream(int columnIndex, InputStream x, long length) throws SQLException {
      this.delegate.updateBinaryStream(columnIndex, x, length);
   }

   public void updateCharacterStream(int columnIndex, Reader x, long length) throws SQLException {
      this.delegate.updateCharacterStream(columnIndex, x, length);
   }

   public void updateAsciiStream(String columnLabel, InputStream x, long length) throws SQLException {
      this.delegate.updateAsciiStream(columnLabel, x, length);
   }

   public void updateBinaryStream(String columnLabel, InputStream x, long length) throws SQLException {
      this.delegate.updateBinaryStream(columnLabel, x, length);
   }

   public void updateCharacterStream(String columnLabel, Reader reader, long length) throws SQLException {
      this.delegate.updateCharacterStream(columnLabel, reader, length);
   }

   public void updateBlob(int columnIndex, InputStream inputStream, long length) throws SQLException {
      this.delegate.updateBlob(columnIndex, inputStream, length);
   }

   public void updateBlob(String columnLabel, InputStream inputStream, long length) throws SQLException {
      this.delegate.updateBlob(columnLabel, inputStream, length);
   }

   public void updateClob(int columnIndex, Reader reader, long length) throws SQLException {
      this.delegate.updateClob(columnIndex, reader, length);
   }

   public void updateClob(String columnLabel, Reader reader, long length) throws SQLException {
      this.delegate.updateClob(columnLabel, reader, length);
   }

   public void updateNClob(int columnIndex, Reader reader, long length) throws SQLException {
      this.delegate.updateNClob(columnIndex, reader, length);
   }

   public void updateNClob(String columnLabel, Reader reader, long length) throws SQLException {
      this.delegate.updateNClob(columnLabel, reader, length);
   }

   public void updateNCharacterStream(int columnIndex, Reader x) throws SQLException {
      this.delegate.updateNCharacterStream(columnIndex, x);
   }

   public void updateNCharacterStream(String columnLabel, Reader reader) throws SQLException {
      this.delegate.updateNCharacterStream(columnLabel, reader);
   }

   public void updateAsciiStream(int columnIndex, InputStream x) throws SQLException {
      this.delegate.updateAsciiStream(columnIndex, x);
   }

   public void updateBinaryStream(int columnIndex, InputStream x) throws SQLException {
      this.delegate.updateBinaryStream(columnIndex, x);
   }

   public void updateCharacterStream(int columnIndex, Reader x) throws SQLException {
      this.delegate.updateCharacterStream(columnIndex, x);
   }

   public void updateAsciiStream(String columnLabel, InputStream x) throws SQLException {
      this.delegate.updateAsciiStream(columnLabel, x);
   }

   public void updateBinaryStream(String columnLabel, InputStream x) throws SQLException {
      this.delegate.updateBinaryStream(columnLabel, x);
   }

   public void updateCharacterStream(String columnLabel, Reader reader) throws SQLException {
      this.delegate.updateCharacterStream(columnLabel, reader);
   }

   public void updateBlob(int columnIndex, InputStream inputStream) throws SQLException {
      this.delegate.updateBlob(columnIndex, inputStream);
   }

   public void updateBlob(String columnLabel, InputStream inputStream) throws SQLException {
      this.delegate.updateBlob(columnLabel, inputStream);
   }

   public void updateClob(int columnIndex, Reader reader) throws SQLException {
      this.delegate.updateClob(columnIndex, reader);
   }

   public void updateClob(String columnLabel, Reader reader) throws SQLException {
      this.delegate.updateClob(columnLabel, reader);
   }

   public void updateNClob(int columnIndex, Reader reader) throws SQLException {
      this.delegate.updateNClob(columnIndex, reader);
   }

   public void updateNClob(String columnLabel, Reader reader) throws SQLException {
      this.delegate.updateNClob(columnLabel, reader);
   }

   public <T> T unwrap(Class<T> iface) throws SQLException {
      return this.delegate.unwrap(iface);
   }

   public boolean isWrapperFor(Class<?> iface) throws SQLException {
      return this.delegate.isWrapperFor(iface);
   }

   public <T> T getObject(int columnIndex, Class<T> type) throws SQLException {
      Object var3 = null;

      try {
         T value = this.delegate.getObject(columnIndex, type);
         this.eventListener.onAfterResultSetGet(this.resultSetInformation, columnIndex, value, (SQLException)null);
         return value;
      } catch (SQLException var5) {
         this.eventListener.onAfterResultSetGet(this.resultSetInformation, columnIndex, (Object)null, var5);
         throw var5;
      }
   }

   public <T> T getObject(String columnLabel, Class<T> type) throws SQLException {
      Object var3 = null;

      try {
         T value = this.delegate.getObject(columnLabel, type);
         this.eventListener.onAfterResultSetGet(this.resultSetInformation, columnLabel, value, (SQLException)null);
         return value;
      } catch (SQLException var5) {
         this.eventListener.onAfterResultSetGet(this.resultSetInformation, columnLabel, (Object)null, var5);
         throw var5;
      }
   }
}
