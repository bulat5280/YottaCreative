package com.p6spy.engine.wrapper;

import com.p6spy.engine.common.CallableStatementInformation;
import com.p6spy.engine.event.JdbcEventListener;
import java.io.InputStream;
import java.io.Reader;
import java.math.BigDecimal;
import java.net.URL;
import java.sql.Array;
import java.sql.Blob;
import java.sql.CallableStatement;
import java.sql.Clob;
import java.sql.Date;
import java.sql.NClob;
import java.sql.Ref;
import java.sql.RowId;
import java.sql.SQLException;
import java.sql.SQLXML;
import java.sql.Time;
import java.sql.Timestamp;
import java.util.Calendar;
import java.util.Map;

public class CallableStatementWrapper extends PreparedStatementWrapper implements CallableStatement {
   private final CallableStatement delegate;
   private final CallableStatementInformation statementInformation;

   public static CallableStatement wrap(CallableStatement delegate, CallableStatementInformation callableStatementInformation, JdbcEventListener eventListener) {
      return delegate == null ? null : new CallableStatementWrapper(delegate, callableStatementInformation, eventListener);
   }

   protected CallableStatementWrapper(CallableStatement delegate, CallableStatementInformation callableStatementInformation, JdbcEventListener eventListener) {
      super(delegate, callableStatementInformation, eventListener);
      this.delegate = delegate;
      this.statementInformation = callableStatementInformation;
   }

   public URL getURL(int parameterIndex) throws SQLException {
      return this.delegate.getURL(parameterIndex);
   }

   public void setURL(String parameterName, URL val) throws SQLException {
      SQLException e = null;

      try {
         this.delegate.setURL(parameterName, val);
      } catch (SQLException var8) {
         e = var8;
         throw var8;
      } finally {
         this.eventListener.onAfterCallableStatementSet(this.statementInformation, parameterName, val, e);
      }

   }

   public void setNull(String parameterName, int sqlType) throws SQLException {
      SQLException e = null;

      try {
         this.delegate.setNull(parameterName, sqlType);
      } catch (SQLException var8) {
         e = var8;
         throw var8;
      } finally {
         this.eventListener.onAfterCallableStatementSet(this.statementInformation, parameterName, (Object)null, e);
      }

   }

   public void setBoolean(String parameterName, boolean x) throws SQLException {
      SQLException e = null;

      try {
         this.delegate.setBoolean(parameterName, x);
      } catch (SQLException var8) {
         e = var8;
         throw var8;
      } finally {
         this.eventListener.onAfterCallableStatementSet(this.statementInformation, parameterName, x, e);
      }

   }

   public void setByte(String parameterName, byte x) throws SQLException {
      SQLException e = null;

      try {
         this.delegate.setByte(parameterName, x);
      } catch (SQLException var8) {
         e = var8;
         throw var8;
      } finally {
         this.eventListener.onAfterCallableStatementSet(this.statementInformation, parameterName, x, e);
      }

   }

   public void setShort(String parameterName, short x) throws SQLException {
      SQLException e = null;

      try {
         this.delegate.setShort(parameterName, x);
      } catch (SQLException var8) {
         e = var8;
         throw var8;
      } finally {
         this.eventListener.onAfterCallableStatementSet(this.statementInformation, parameterName, x, e);
      }

   }

   public void setInt(String parameterName, int x) throws SQLException {
      SQLException e = null;

      try {
         this.delegate.setInt(parameterName, x);
      } catch (SQLException var8) {
         e = var8;
         throw var8;
      } finally {
         this.eventListener.onAfterCallableStatementSet(this.statementInformation, parameterName, x, e);
      }

   }

   public void setLong(String parameterName, long x) throws SQLException {
      SQLException e = null;

      try {
         this.delegate.setLong(parameterName, x);
      } catch (SQLException var9) {
         e = var9;
         throw var9;
      } finally {
         this.eventListener.onAfterCallableStatementSet(this.statementInformation, parameterName, x, e);
      }

   }

   public void setFloat(String parameterName, float x) throws SQLException {
      SQLException e = null;

      try {
         this.delegate.setFloat(parameterName, x);
      } catch (SQLException var8) {
         e = var8;
         throw var8;
      } finally {
         this.eventListener.onAfterCallableStatementSet(this.statementInformation, parameterName, x, e);
      }

   }

   public void setDouble(String parameterName, double x) throws SQLException {
      SQLException e = null;

      try {
         this.delegate.setDouble(parameterName, x);
      } catch (SQLException var9) {
         e = var9;
         throw var9;
      } finally {
         this.eventListener.onAfterCallableStatementSet(this.statementInformation, parameterName, x, e);
      }

   }

   public void setBigDecimal(String parameterName, BigDecimal x) throws SQLException {
      SQLException e = null;

      try {
         this.delegate.setBigDecimal(parameterName, x);
      } catch (SQLException var8) {
         e = var8;
         throw var8;
      } finally {
         this.eventListener.onAfterCallableStatementSet(this.statementInformation, parameterName, x, e);
      }

   }

   public void setString(String parameterName, String x) throws SQLException {
      SQLException e = null;

      try {
         this.delegate.setString(parameterName, x);
      } catch (SQLException var8) {
         e = var8;
         throw var8;
      } finally {
         this.eventListener.onAfterCallableStatementSet(this.statementInformation, parameterName, x, e);
      }

   }

   public void setBytes(String parameterName, byte[] x) throws SQLException {
      SQLException e = null;

      try {
         this.delegate.setBytes(parameterName, x);
      } catch (SQLException var8) {
         e = var8;
         throw var8;
      } finally {
         this.eventListener.onAfterCallableStatementSet(this.statementInformation, parameterName, x, e);
      }

   }

   public void setDate(String parameterName, Date x) throws SQLException {
      SQLException e = null;

      try {
         this.delegate.setDate(parameterName, x);
      } catch (SQLException var8) {
         e = var8;
         throw var8;
      } finally {
         this.eventListener.onAfterCallableStatementSet(this.statementInformation, parameterName, x, e);
      }

   }

   public void setTime(String parameterName, Time x) throws SQLException {
      SQLException e = null;

      try {
         this.delegate.setTime(parameterName, x);
      } catch (SQLException var8) {
         e = var8;
         throw var8;
      } finally {
         this.eventListener.onAfterCallableStatementSet(this.statementInformation, parameterName, x, e);
      }

   }

   public void setTimestamp(String parameterName, Timestamp x) throws SQLException {
      SQLException e = null;

      try {
         this.delegate.setTimestamp(parameterName, x);
      } catch (SQLException var8) {
         e = var8;
         throw var8;
      } finally {
         this.eventListener.onAfterCallableStatementSet(this.statementInformation, parameterName, x, e);
      }

   }

   public void setAsciiStream(String parameterName, InputStream x, int length) throws SQLException {
      SQLException e = null;

      try {
         this.delegate.setAsciiStream(parameterName, x, length);
      } catch (SQLException var9) {
         e = var9;
         throw var9;
      } finally {
         this.eventListener.onAfterCallableStatementSet(this.statementInformation, parameterName, x, e);
      }

   }

   public void setBinaryStream(String parameterName, InputStream x, int length) throws SQLException {
      SQLException e = null;

      try {
         this.delegate.setBinaryStream(parameterName, x, length);
      } catch (SQLException var9) {
         e = var9;
         throw var9;
      } finally {
         this.eventListener.onAfterCallableStatementSet(this.statementInformation, parameterName, x, e);
      }

   }

   public void setObject(String parameterName, Object x, int targetSqlType, int scale) throws SQLException {
      SQLException e = null;

      try {
         this.delegate.setObject(parameterName, x, targetSqlType, scale);
      } catch (SQLException var10) {
         e = var10;
         throw var10;
      } finally {
         this.eventListener.onAfterCallableStatementSet(this.statementInformation, parameterName, x, e);
      }

   }

   public void setObject(String parameterName, Object x, int targetSqlType) throws SQLException {
      SQLException e = null;

      try {
         this.delegate.setObject(parameterName, x, targetSqlType);
      } catch (SQLException var9) {
         e = var9;
         throw var9;
      } finally {
         this.eventListener.onAfterCallableStatementSet(this.statementInformation, parameterName, x, e);
      }

   }

   public void setObject(String parameterName, Object x) throws SQLException {
      SQLException e = null;

      try {
         this.delegate.setObject(parameterName, x);
      } catch (SQLException var8) {
         e = var8;
         throw var8;
      } finally {
         this.eventListener.onAfterCallableStatementSet(this.statementInformation, parameterName, x, e);
      }

   }

   public void setCharacterStream(String parameterName, Reader reader, int length) throws SQLException {
      SQLException e = null;

      try {
         this.delegate.setCharacterStream(parameterName, reader, length);
      } catch (SQLException var9) {
         e = var9;
         throw var9;
      } finally {
         this.eventListener.onAfterCallableStatementSet(this.statementInformation, parameterName, reader, e);
      }

   }

   public void setDate(String parameterName, Date x, Calendar cal) throws SQLException {
      SQLException e = null;

      try {
         this.delegate.setDate(parameterName, x, cal);
      } catch (SQLException var9) {
         e = var9;
         throw var9;
      } finally {
         this.eventListener.onAfterCallableStatementSet(this.statementInformation, parameterName, x, e);
      }

   }

   public void setTime(String parameterName, Time x, Calendar cal) throws SQLException {
      SQLException e = null;

      try {
         this.delegate.setTime(parameterName, x, cal);
      } catch (SQLException var9) {
         e = var9;
         throw var9;
      } finally {
         this.eventListener.onAfterCallableStatementSet(this.statementInformation, parameterName, x, e);
      }

   }

   public void setTimestamp(String parameterName, Timestamp x, Calendar cal) throws SQLException {
      SQLException e = null;

      try {
         this.delegate.setTimestamp(parameterName, x, cal);
      } catch (SQLException var9) {
         e = var9;
         throw var9;
      } finally {
         this.eventListener.onAfterCallableStatementSet(this.statementInformation, parameterName, x, e);
      }

   }

   public void setNull(String parameterName, int sqlType, String typeName) throws SQLException {
      SQLException e = null;

      try {
         this.delegate.setNull(parameterName, sqlType, typeName);
      } catch (SQLException var9) {
         e = var9;
         throw var9;
      } finally {
         this.eventListener.onAfterCallableStatementSet(this.statementInformation, parameterName, (Object)null, e);
      }

   }

   public void setRowId(String parameterName, RowId x) throws SQLException {
      SQLException e = null;

      try {
         this.delegate.setRowId(parameterName, x);
      } catch (SQLException var8) {
         e = var8;
         throw var8;
      } finally {
         this.eventListener.onAfterCallableStatementSet(this.statementInformation, parameterName, x, e);
      }

   }

   public void setNString(String parameterName, String value) throws SQLException {
      SQLException e = null;

      try {
         this.delegate.setNString(parameterName, value);
      } catch (SQLException var8) {
         e = var8;
         throw var8;
      } finally {
         this.eventListener.onAfterCallableStatementSet(this.statementInformation, parameterName, value, e);
      }

   }

   public void setNCharacterStream(String parameterName, Reader value, long length) throws SQLException {
      SQLException e = null;

      try {
         this.delegate.setNCharacterStream(parameterName, value, length);
      } catch (SQLException var10) {
         e = var10;
         throw var10;
      } finally {
         this.eventListener.onAfterCallableStatementSet(this.statementInformation, parameterName, value, e);
      }

   }

   public void setNClob(String parameterName, NClob value) throws SQLException {
      SQLException e = null;

      try {
         this.delegate.setNClob(parameterName, value);
      } catch (SQLException var8) {
         e = var8;
         throw var8;
      } finally {
         this.eventListener.onAfterCallableStatementSet(this.statementInformation, parameterName, value, e);
      }

   }

   public void setClob(String parameterName, Reader reader, long length) throws SQLException {
      SQLException e = null;

      try {
         this.delegate.setClob(parameterName, reader, length);
      } catch (SQLException var10) {
         e = var10;
         throw var10;
      } finally {
         this.eventListener.onAfterCallableStatementSet(this.statementInformation, parameterName, reader, e);
      }

   }

   public void setBlob(String parameterName, InputStream inputStream, long length) throws SQLException {
      SQLException e = null;

      try {
         this.delegate.setBlob(parameterName, inputStream, length);
      } catch (SQLException var10) {
         e = var10;
         throw var10;
      } finally {
         this.eventListener.onAfterCallableStatementSet(this.statementInformation, parameterName, inputStream, e);
      }

   }

   public void setNClob(String parameterName, Reader reader, long length) throws SQLException {
      SQLException e = null;

      try {
         this.delegate.setNClob(parameterName, reader, length);
      } catch (SQLException var10) {
         e = var10;
         throw var10;
      } finally {
         this.eventListener.onAfterCallableStatementSet(this.statementInformation, parameterName, reader, e);
      }

   }

   public void setSQLXML(String parameterName, SQLXML xmlObject) throws SQLException {
      SQLException e = null;

      try {
         this.delegate.setSQLXML(parameterName, xmlObject);
      } catch (SQLException var8) {
         e = var8;
         throw var8;
      } finally {
         this.eventListener.onAfterCallableStatementSet(this.statementInformation, parameterName, xmlObject, e);
      }

   }

   public void setBlob(String parameterName, Blob x) throws SQLException {
      SQLException e = null;

      try {
         this.delegate.setBlob(parameterName, x);
      } catch (SQLException var8) {
         e = var8;
         throw var8;
      } finally {
         this.eventListener.onAfterCallableStatementSet(this.statementInformation, parameterName, x, e);
      }

   }

   public void setClob(String parameterName, Clob x) throws SQLException {
      SQLException e = null;

      try {
         this.delegate.setClob(parameterName, x);
      } catch (SQLException var8) {
         e = var8;
         throw var8;
      } finally {
         this.eventListener.onAfterCallableStatementSet(this.statementInformation, parameterName, x, e);
      }

   }

   public void setAsciiStream(String parameterName, InputStream x, long length) throws SQLException {
      SQLException e = null;

      try {
         this.delegate.setAsciiStream(parameterName, x, length);
      } catch (SQLException var10) {
         e = var10;
         throw var10;
      } finally {
         this.eventListener.onAfterCallableStatementSet(this.statementInformation, parameterName, x, e);
      }

   }

   public void setBinaryStream(String parameterName, InputStream x, long length) throws SQLException {
      SQLException e = null;

      try {
         this.delegate.setBinaryStream(parameterName, x, length);
      } catch (SQLException var10) {
         e = var10;
         throw var10;
      } finally {
         this.eventListener.onAfterCallableStatementSet(this.statementInformation, parameterName, x, e);
      }

   }

   public void setCharacterStream(String parameterName, Reader reader, long length) throws SQLException {
      SQLException e = null;

      try {
         this.delegate.setCharacterStream(parameterName, reader, length);
      } catch (SQLException var10) {
         e = var10;
         throw var10;
      } finally {
         this.eventListener.onAfterCallableStatementSet(this.statementInformation, parameterName, reader, e);
      }

   }

   public void setAsciiStream(String parameterName, InputStream x) throws SQLException {
      SQLException e = null;

      try {
         this.delegate.setAsciiStream(parameterName, x);
      } catch (SQLException var8) {
         e = var8;
         throw var8;
      } finally {
         this.eventListener.onAfterCallableStatementSet(this.statementInformation, parameterName, x, e);
      }

   }

   public void setBinaryStream(String parameterName, InputStream x) throws SQLException {
      SQLException e = null;

      try {
         this.delegate.setBinaryStream(parameterName, x);
      } catch (SQLException var8) {
         e = var8;
         throw var8;
      } finally {
         this.eventListener.onAfterCallableStatementSet(this.statementInformation, parameterName, x, e);
      }

   }

   public void setCharacterStream(String parameterName, Reader reader) throws SQLException {
      SQLException e = null;

      try {
         this.delegate.setCharacterStream(parameterName, reader);
      } catch (SQLException var8) {
         e = var8;
         throw var8;
      } finally {
         this.eventListener.onAfterCallableStatementSet(this.statementInformation, parameterName, reader, e);
      }

   }

   public void setNCharacterStream(String parameterName, Reader value) throws SQLException {
      SQLException e = null;

      try {
         this.delegate.setNCharacterStream(parameterName, value);
      } catch (SQLException var8) {
         e = var8;
         throw var8;
      } finally {
         this.eventListener.onAfterCallableStatementSet(this.statementInformation, parameterName, value, e);
      }

   }

   public void setClob(String parameterName, Reader reader) throws SQLException {
      SQLException e = null;

      try {
         this.delegate.setClob(parameterName, reader);
      } catch (SQLException var8) {
         e = var8;
         throw var8;
      } finally {
         this.eventListener.onAfterCallableStatementSet(this.statementInformation, parameterName, reader, e);
      }

   }

   public void setBlob(String parameterName, InputStream inputStream) throws SQLException {
      SQLException e = null;

      try {
         this.delegate.setBlob(parameterName, inputStream);
      } catch (SQLException var8) {
         e = var8;
         throw var8;
      } finally {
         this.eventListener.onAfterCallableStatementSet(this.statementInformation, parameterName, inputStream, e);
      }

   }

   public void setNClob(String parameterName, Reader reader) throws SQLException {
      SQLException e = null;

      try {
         this.delegate.setNClob(parameterName, reader);
      } catch (SQLException var8) {
         e = var8;
         throw var8;
      } finally {
         this.eventListener.onAfterCallableStatementSet(this.statementInformation, parameterName, reader, e);
      }

   }

   public NClob getNClob(int parameterIndex) throws SQLException {
      return this.delegate.getNClob(parameterIndex);
   }

   public NClob getNClob(String parameterName) throws SQLException {
      return this.delegate.getNClob(parameterName);
   }

   public SQLXML getSQLXML(int parameterIndex) throws SQLException {
      return this.delegate.getSQLXML(parameterIndex);
   }

   public SQLXML getSQLXML(String parameterName) throws SQLException {
      return this.delegate.getSQLXML(parameterName);
   }

   public String getNString(int parameterIndex) throws SQLException {
      return this.delegate.getNString(parameterIndex);
   }

   public String getNString(String parameterName) throws SQLException {
      return this.delegate.getNString(parameterName);
   }

   public Reader getNCharacterStream(int parameterIndex) throws SQLException {
      return this.delegate.getNCharacterStream(parameterIndex);
   }

   public Reader getNCharacterStream(String parameterName) throws SQLException {
      return this.delegate.getNCharacterStream(parameterName);
   }

   public Reader getCharacterStream(int parameterIndex) throws SQLException {
      return this.delegate.getCharacterStream(parameterIndex);
   }

   public Reader getCharacterStream(String parameterName) throws SQLException {
      return this.delegate.getCharacterStream(parameterName);
   }

   public String getString(String parameterName) throws SQLException {
      return this.delegate.getString(parameterName);
   }

   public boolean getBoolean(String parameterName) throws SQLException {
      return this.delegate.getBoolean(parameterName);
   }

   public byte getByte(String parameterName) throws SQLException {
      return this.delegate.getByte(parameterName);
   }

   public short getShort(String parameterName) throws SQLException {
      return this.delegate.getShort(parameterName);
   }

   public int getInt(String parameterName) throws SQLException {
      return this.delegate.getInt(parameterName);
   }

   public long getLong(String parameterName) throws SQLException {
      return this.delegate.getLong(parameterName);
   }

   public float getFloat(String parameterName) throws SQLException {
      return this.delegate.getFloat(parameterName);
   }

   public double getDouble(String parameterName) throws SQLException {
      return this.delegate.getDouble(parameterName);
   }

   public byte[] getBytes(String parameterName) throws SQLException {
      return this.delegate.getBytes(parameterName);
   }

   public Date getDate(String parameterName) throws SQLException {
      return this.delegate.getDate(parameterName);
   }

   public Time getTime(String parameterName) throws SQLException {
      return this.delegate.getTime(parameterName);
   }

   public Timestamp getTimestamp(String parameterName) throws SQLException {
      return this.delegate.getTimestamp(parameterName);
   }

   public Object getObject(String parameterName) throws SQLException {
      return this.delegate.getObject(parameterName);
   }

   public BigDecimal getBigDecimal(String parameterName) throws SQLException {
      return this.delegate.getBigDecimal(parameterName);
   }

   public Object getObject(String parameterName, Map<String, Class<?>> map) throws SQLException {
      return this.delegate.getObject(parameterName, map);
   }

   public Ref getRef(String parameterName) throws SQLException {
      return this.delegate.getRef(parameterName);
   }

   public Blob getBlob(String parameterName) throws SQLException {
      return this.delegate.getBlob(parameterName);
   }

   public Clob getClob(String parameterName) throws SQLException {
      return this.delegate.getClob(parameterName);
   }

   public Array getArray(String parameterName) throws SQLException {
      return this.delegate.getArray(parameterName);
   }

   public Date getDate(String parameterName, Calendar cal) throws SQLException {
      return this.delegate.getDate(parameterName, cal);
   }

   public Time getTime(String parameterName, Calendar cal) throws SQLException {
      return this.delegate.getTime(parameterName, cal);
   }

   public Timestamp getTimestamp(String parameterName, Calendar cal) throws SQLException {
      return this.delegate.getTimestamp(parameterName, cal);
   }

   public URL getURL(String parameterName) throws SQLException {
      return this.delegate.getURL(parameterName);
   }

   public RowId getRowId(int parameterIndex) throws SQLException {
      return this.delegate.getRowId(parameterIndex);
   }

   public RowId getRowId(String parameterName) throws SQLException {
      return this.delegate.getRowId(parameterName);
   }

   public void registerOutParameter(int parameterIndex, int sqlType) throws SQLException {
      this.delegate.registerOutParameter(parameterIndex, sqlType);
   }

   public void registerOutParameter(int parameterIndex, int sqlType, int scale) throws SQLException {
      this.delegate.registerOutParameter(parameterIndex, sqlType, scale);
   }

   public boolean wasNull() throws SQLException {
      return this.delegate.wasNull();
   }

   public String getString(int parameterIndex) throws SQLException {
      return this.delegate.getString(parameterIndex);
   }

   public boolean getBoolean(int parameterIndex) throws SQLException {
      return this.delegate.getBoolean(parameterIndex);
   }

   public byte getByte(int parameterIndex) throws SQLException {
      return this.delegate.getByte(parameterIndex);
   }

   public short getShort(int parameterIndex) throws SQLException {
      return this.delegate.getShort(parameterIndex);
   }

   public int getInt(int parameterIndex) throws SQLException {
      return this.delegate.getInt(parameterIndex);
   }

   public long getLong(int parameterIndex) throws SQLException {
      return this.delegate.getLong(parameterIndex);
   }

   public float getFloat(int parameterIndex) throws SQLException {
      return this.delegate.getFloat(parameterIndex);
   }

   public double getDouble(int parameterIndex) throws SQLException {
      return this.delegate.getDouble(parameterIndex);
   }

   public BigDecimal getBigDecimal(int parameterIndex, int scale) throws SQLException {
      return this.delegate.getBigDecimal(parameterIndex, scale);
   }

   public byte[] getBytes(int parameterIndex) throws SQLException {
      return this.delegate.getBytes(parameterIndex);
   }

   public Date getDate(int parameterIndex) throws SQLException {
      return this.delegate.getDate(parameterIndex);
   }

   public Time getTime(int parameterIndex) throws SQLException {
      return this.delegate.getTime(parameterIndex);
   }

   public Timestamp getTimestamp(int parameterIndex) throws SQLException {
      return this.delegate.getTimestamp(parameterIndex);
   }

   public Object getObject(int parameterIndex) throws SQLException {
      return this.delegate.getObject(parameterIndex);
   }

   public BigDecimal getBigDecimal(int parameterIndex) throws SQLException {
      return this.delegate.getBigDecimal(parameterIndex);
   }

   public Object getObject(int parameterIndex, Map<String, Class<?>> map) throws SQLException {
      return this.delegate.getObject(parameterIndex, map);
   }

   public Ref getRef(int parameterIndex) throws SQLException {
      return this.delegate.getRef(parameterIndex);
   }

   public Blob getBlob(int parameterIndex) throws SQLException {
      return this.delegate.getBlob(parameterIndex);
   }

   public Clob getClob(int parameterIndex) throws SQLException {
      return this.delegate.getClob(parameterIndex);
   }

   public Array getArray(int parameterIndex) throws SQLException {
      return this.delegate.getArray(parameterIndex);
   }

   public Date getDate(int parameterIndex, Calendar cal) throws SQLException {
      return this.delegate.getDate(parameterIndex, cal);
   }

   public Time getTime(int parameterIndex, Calendar cal) throws SQLException {
      return this.delegate.getTime(parameterIndex, cal);
   }

   public Timestamp getTimestamp(int parameterIndex, Calendar cal) throws SQLException {
      return this.delegate.getTimestamp(parameterIndex, cal);
   }

   public void registerOutParameter(int parameterIndex, int sqlType, String typeName) throws SQLException {
      this.delegate.registerOutParameter(parameterIndex, sqlType, typeName);
   }

   public void registerOutParameter(String parameterName, int sqlType) throws SQLException {
      this.delegate.registerOutParameter(parameterName, sqlType);
   }

   public void registerOutParameter(String parameterName, int sqlType, int scale) throws SQLException {
      this.delegate.registerOutParameter(parameterName, sqlType, scale);
   }

   public void registerOutParameter(String parameterName, int sqlType, String typeName) throws SQLException {
      this.delegate.registerOutParameter(parameterName, sqlType, typeName);
   }

   public <T> T getObject(int parameterIndex, Class<T> type) throws SQLException {
      return this.delegate.getObject(parameterIndex, type);
   }

   public <T> T getObject(String parameterName, Class<T> type) throws SQLException {
      return this.delegate.getObject(parameterName, type);
   }
}
