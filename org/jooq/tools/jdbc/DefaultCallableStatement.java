package org.jooq.tools.jdbc;

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

public class DefaultCallableStatement extends DefaultPreparedStatement implements CallableStatement {
   public DefaultCallableStatement(CallableStatement delegate) {
      super(delegate);
   }

   public CallableStatement getDelegate() {
      return (CallableStatement)super.getDelegate();
   }

   public void registerOutParameter(int parameterIndex, int sqlType) throws SQLException {
      this.getDelegate().registerOutParameter(parameterIndex, sqlType);
   }

   public void registerOutParameter(int parameterIndex, int sqlType, int scale) throws SQLException {
      this.getDelegate().registerOutParameter(parameterIndex, sqlType, scale);
   }

   public boolean wasNull() throws SQLException {
      return this.getDelegate().wasNull();
   }

   public String getString(int parameterIndex) throws SQLException {
      return this.getDelegate().getString(parameterIndex);
   }

   public boolean getBoolean(int parameterIndex) throws SQLException {
      return this.getDelegate().getBoolean(parameterIndex);
   }

   public byte getByte(int parameterIndex) throws SQLException {
      return this.getDelegate().getByte(parameterIndex);
   }

   public short getShort(int parameterIndex) throws SQLException {
      return this.getDelegate().getShort(parameterIndex);
   }

   public int getInt(int parameterIndex) throws SQLException {
      return this.getDelegate().getInt(parameterIndex);
   }

   public long getLong(int parameterIndex) throws SQLException {
      return this.getDelegate().getLong(parameterIndex);
   }

   public float getFloat(int parameterIndex) throws SQLException {
      return this.getDelegate().getFloat(parameterIndex);
   }

   public double getDouble(int parameterIndex) throws SQLException {
      return this.getDelegate().getDouble(parameterIndex);
   }

   /** @deprecated */
   @Deprecated
   public BigDecimal getBigDecimal(int parameterIndex, int scale) throws SQLException {
      return this.getDelegate().getBigDecimal(parameterIndex, scale);
   }

   public byte[] getBytes(int parameterIndex) throws SQLException {
      return this.getDelegate().getBytes(parameterIndex);
   }

   public Date getDate(int parameterIndex) throws SQLException {
      return this.getDelegate().getDate(parameterIndex);
   }

   public Time getTime(int parameterIndex) throws SQLException {
      return this.getDelegate().getTime(parameterIndex);
   }

   public Timestamp getTimestamp(int parameterIndex) throws SQLException {
      return this.getDelegate().getTimestamp(parameterIndex);
   }

   public Object getObject(int parameterIndex) throws SQLException {
      return this.getDelegate().getObject(parameterIndex);
   }

   public BigDecimal getBigDecimal(int parameterIndex) throws SQLException {
      return this.getDelegate().getBigDecimal(parameterIndex);
   }

   public Object getObject(int parameterIndex, Map<String, Class<?>> map) throws SQLException {
      return this.getDelegate().getObject(parameterIndex, map);
   }

   public Ref getRef(int parameterIndex) throws SQLException {
      return this.getDelegate().getRef(parameterIndex);
   }

   public Blob getBlob(int parameterIndex) throws SQLException {
      return this.getDelegate().getBlob(parameterIndex);
   }

   public Clob getClob(int parameterIndex) throws SQLException {
      return this.getDelegate().getClob(parameterIndex);
   }

   public Array getArray(int parameterIndex) throws SQLException {
      return this.getDelegate().getArray(parameterIndex);
   }

   public Date getDate(int parameterIndex, Calendar cal) throws SQLException {
      return this.getDelegate().getDate(parameterIndex, cal);
   }

   public Time getTime(int parameterIndex, Calendar cal) throws SQLException {
      return this.getDelegate().getTime(parameterIndex, cal);
   }

   public Timestamp getTimestamp(int parameterIndex, Calendar cal) throws SQLException {
      return this.getDelegate().getTimestamp(parameterIndex, cal);
   }

   public void registerOutParameter(int parameterIndex, int sqlType, String typeName) throws SQLException {
      this.getDelegate().registerOutParameter(parameterIndex, sqlType, typeName);
   }

   public void registerOutParameter(String parameterName, int sqlType) throws SQLException {
      this.getDelegate().registerOutParameter(parameterName, sqlType);
   }

   public void registerOutParameter(String parameterName, int sqlType, int scale) throws SQLException {
      this.getDelegate().registerOutParameter(parameterName, sqlType, scale);
   }

   public void registerOutParameter(String parameterName, int sqlType, String typeName) throws SQLException {
      this.getDelegate().registerOutParameter(parameterName, sqlType, typeName);
   }

   public URL getURL(int parameterIndex) throws SQLException {
      return this.getDelegate().getURL(parameterIndex);
   }

   public void setURL(String parameterName, URL val) throws SQLException {
      this.getDelegate().setURL(parameterName, val);
   }

   public void setNull(String parameterName, int sqlType) throws SQLException {
      this.getDelegate().setNull(parameterName, sqlType);
   }

   public void setBoolean(String parameterName, boolean x) throws SQLException {
      this.getDelegate().setBoolean(parameterName, x);
   }

   public void setByte(String parameterName, byte x) throws SQLException {
      this.getDelegate().setByte(parameterName, x);
   }

   public void setShort(String parameterName, short x) throws SQLException {
      this.getDelegate().setShort(parameterName, x);
   }

   public void setInt(String parameterName, int x) throws SQLException {
      this.getDelegate().setInt(parameterName, x);
   }

   public void setLong(String parameterName, long x) throws SQLException {
      this.getDelegate().setLong(parameterName, x);
   }

   public void setFloat(String parameterName, float x) throws SQLException {
      this.getDelegate().setFloat(parameterName, x);
   }

   public void setDouble(String parameterName, double x) throws SQLException {
      this.getDelegate().setDouble(parameterName, x);
   }

   public void setBigDecimal(String parameterName, BigDecimal x) throws SQLException {
      this.getDelegate().setBigDecimal(parameterName, x);
   }

   public void setString(String parameterName, String x) throws SQLException {
      this.getDelegate().setString(parameterName, x);
   }

   public void setBytes(String parameterName, byte[] x) throws SQLException {
      this.getDelegate().setBytes(parameterName, x);
   }

   public void setDate(String parameterName, Date x) throws SQLException {
      this.getDelegate().setDate(parameterName, x);
   }

   public void setTime(String parameterName, Time x) throws SQLException {
      this.getDelegate().setTime(parameterName, x);
   }

   public void setTimestamp(String parameterName, Timestamp x) throws SQLException {
      this.getDelegate().setTimestamp(parameterName, x);
   }

   public void setAsciiStream(String parameterName, InputStream x, int length) throws SQLException {
      this.getDelegate().setAsciiStream(parameterName, x, length);
   }

   public void setBinaryStream(String parameterName, InputStream x, int length) throws SQLException {
      this.getDelegate().setBinaryStream(parameterName, x, length);
   }

   public void setObject(String parameterName, Object x, int targetSqlType, int scale) throws SQLException {
      this.getDelegate().setObject(parameterName, x, targetSqlType, scale);
   }

   public void setObject(String parameterName, Object x, int targetSqlType) throws SQLException {
      this.getDelegate().setObject(parameterName, x, targetSqlType);
   }

   public void setObject(String parameterName, Object x) throws SQLException {
      this.getDelegate().setObject(parameterName, x);
   }

   public void setCharacterStream(String parameterName, Reader reader, int length) throws SQLException {
      this.getDelegate().setCharacterStream(parameterName, reader, length);
   }

   public void setDate(String parameterName, Date x, Calendar cal) throws SQLException {
      this.getDelegate().setDate(parameterName, x, cal);
   }

   public void setTime(String parameterName, Time x, Calendar cal) throws SQLException {
      this.getDelegate().setTime(parameterName, x, cal);
   }

   public void setTimestamp(String parameterName, Timestamp x, Calendar cal) throws SQLException {
      this.getDelegate().setTimestamp(parameterName, x, cal);
   }

   public void setNull(String parameterName, int sqlType, String typeName) throws SQLException {
      this.getDelegate().setNull(parameterName, sqlType, typeName);
   }

   public String getString(String parameterName) throws SQLException {
      return this.getDelegate().getString(parameterName);
   }

   public boolean getBoolean(String parameterName) throws SQLException {
      return this.getDelegate().getBoolean(parameterName);
   }

   public byte getByte(String parameterName) throws SQLException {
      return this.getDelegate().getByte(parameterName);
   }

   public short getShort(String parameterName) throws SQLException {
      return this.getDelegate().getShort(parameterName);
   }

   public int getInt(String parameterName) throws SQLException {
      return this.getDelegate().getInt(parameterName);
   }

   public long getLong(String parameterName) throws SQLException {
      return this.getDelegate().getLong(parameterName);
   }

   public float getFloat(String parameterName) throws SQLException {
      return this.getDelegate().getFloat(parameterName);
   }

   public double getDouble(String parameterName) throws SQLException {
      return this.getDelegate().getDouble(parameterName);
   }

   public byte[] getBytes(String parameterName) throws SQLException {
      return this.getDelegate().getBytes(parameterName);
   }

   public Date getDate(String parameterName) throws SQLException {
      return this.getDelegate().getDate(parameterName);
   }

   public Time getTime(String parameterName) throws SQLException {
      return this.getDelegate().getTime(parameterName);
   }

   public Timestamp getTimestamp(String parameterName) throws SQLException {
      return this.getDelegate().getTimestamp(parameterName);
   }

   public Object getObject(String parameterName) throws SQLException {
      return this.getDelegate().getObject(parameterName);
   }

   public BigDecimal getBigDecimal(String parameterName) throws SQLException {
      return this.getDelegate().getBigDecimal(parameterName);
   }

   public Object getObject(String parameterName, Map<String, Class<?>> map) throws SQLException {
      return this.getDelegate().getObject(parameterName, map);
   }

   public Ref getRef(String parameterName) throws SQLException {
      return this.getDelegate().getRef(parameterName);
   }

   public Blob getBlob(String parameterName) throws SQLException {
      return this.getDelegate().getBlob(parameterName);
   }

   public Clob getClob(String parameterName) throws SQLException {
      return this.getDelegate().getClob(parameterName);
   }

   public Array getArray(String parameterName) throws SQLException {
      return this.getDelegate().getArray(parameterName);
   }

   public Date getDate(String parameterName, Calendar cal) throws SQLException {
      return this.getDelegate().getDate(parameterName, cal);
   }

   public Time getTime(String parameterName, Calendar cal) throws SQLException {
      return this.getDelegate().getTime(parameterName, cal);
   }

   public Timestamp getTimestamp(String parameterName, Calendar cal) throws SQLException {
      return this.getDelegate().getTimestamp(parameterName, cal);
   }

   public URL getURL(String parameterName) throws SQLException {
      return this.getDelegate().getURL(parameterName);
   }

   public RowId getRowId(int parameterIndex) throws SQLException {
      return this.getDelegate().getRowId(parameterIndex);
   }

   public RowId getRowId(String parameterName) throws SQLException {
      return this.getDelegate().getRowId(parameterName);
   }

   public void setRowId(String parameterName, RowId x) throws SQLException {
      this.getDelegate().setRowId(parameterName, x);
   }

   public void setNString(String parameterName, String value) throws SQLException {
      this.getDelegate().setNString(parameterName, value);
   }

   public void setNCharacterStream(String parameterName, Reader value, long length) throws SQLException {
      this.getDelegate().setNCharacterStream(parameterName, value, length);
   }

   public void setNClob(String parameterName, NClob value) throws SQLException {
      this.getDelegate().setNClob(parameterName, value);
   }

   public void setClob(String parameterName, Reader reader, long length) throws SQLException {
      this.getDelegate().setClob(parameterName, reader, length);
   }

   public void setBlob(String parameterName, InputStream inputStream, long length) throws SQLException {
      this.getDelegate().setBlob(parameterName, inputStream, length);
   }

   public void setNClob(String parameterName, Reader reader, long length) throws SQLException {
      this.getDelegate().setNClob(parameterName, reader, length);
   }

   public NClob getNClob(int parameterIndex) throws SQLException {
      return this.getDelegate().getNClob(parameterIndex);
   }

   public NClob getNClob(String parameterName) throws SQLException {
      return this.getDelegate().getNClob(parameterName);
   }

   public void setSQLXML(String parameterName, SQLXML xmlObject) throws SQLException {
      this.getDelegate().setSQLXML(parameterName, xmlObject);
   }

   public SQLXML getSQLXML(int parameterIndex) throws SQLException {
      return this.getDelegate().getSQLXML(parameterIndex);
   }

   public SQLXML getSQLXML(String parameterName) throws SQLException {
      return this.getDelegate().getSQLXML(parameterName);
   }

   public String getNString(int parameterIndex) throws SQLException {
      return this.getDelegate().getNString(parameterIndex);
   }

   public String getNString(String parameterName) throws SQLException {
      return this.getDelegate().getNString(parameterName);
   }

   public Reader getNCharacterStream(int parameterIndex) throws SQLException {
      return this.getDelegate().getNCharacterStream(parameterIndex);
   }

   public Reader getNCharacterStream(String parameterName) throws SQLException {
      return this.getDelegate().getNCharacterStream(parameterName);
   }

   public Reader getCharacterStream(int parameterIndex) throws SQLException {
      return this.getDelegate().getCharacterStream(parameterIndex);
   }

   public Reader getCharacterStream(String parameterName) throws SQLException {
      return this.getDelegate().getCharacterStream(parameterName);
   }

   public void setBlob(String parameterName, Blob x) throws SQLException {
      this.getDelegate().setBlob(parameterName, x);
   }

   public void setClob(String parameterName, Clob x) throws SQLException {
      this.getDelegate().setClob(parameterName, x);
   }

   public void setAsciiStream(String parameterName, InputStream x, long length) throws SQLException {
      this.getDelegate().setAsciiStream(parameterName, x, length);
   }

   public void setBinaryStream(String parameterName, InputStream x, long length) throws SQLException {
      this.getDelegate().setBinaryStream(parameterName, x, length);
   }

   public void setCharacterStream(String parameterName, Reader reader, long length) throws SQLException {
      this.getDelegate().setCharacterStream(parameterName, reader, length);
   }

   public void setAsciiStream(String parameterName, InputStream x) throws SQLException {
      this.getDelegate().setAsciiStream(parameterName, x);
   }

   public void setBinaryStream(String parameterName, InputStream x) throws SQLException {
      this.getDelegate().setBinaryStream(parameterName, x);
   }

   public void setCharacterStream(String parameterName, Reader reader) throws SQLException {
      this.getDelegate().setCharacterStream(parameterName, reader);
   }

   public void setNCharacterStream(String parameterName, Reader value) throws SQLException {
      this.getDelegate().setNCharacterStream(parameterName, value);
   }

   public void setClob(String parameterName, Reader reader) throws SQLException {
      this.getDelegate().setClob(parameterName, reader);
   }

   public void setBlob(String parameterName, InputStream inputStream) throws SQLException {
      this.getDelegate().setBlob(parameterName, inputStream);
   }

   public void setNClob(String parameterName, Reader reader) throws SQLException {
      this.getDelegate().setNClob(parameterName, reader);
   }
}
