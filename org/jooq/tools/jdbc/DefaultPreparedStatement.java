package org.jooq.tools.jdbc;

import java.io.InputStream;
import java.io.Reader;
import java.math.BigDecimal;
import java.net.URL;
import java.sql.Array;
import java.sql.Blob;
import java.sql.Clob;
import java.sql.Date;
import java.sql.NClob;
import java.sql.ParameterMetaData;
import java.sql.PreparedStatement;
import java.sql.Ref;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.RowId;
import java.sql.SQLException;
import java.sql.SQLXML;
import java.sql.Time;
import java.sql.Timestamp;
import java.util.Calendar;

public class DefaultPreparedStatement extends DefaultStatement implements PreparedStatement {
   public DefaultPreparedStatement(PreparedStatement delegate) {
      super(delegate);
   }

   public PreparedStatement getDelegate() {
      return (PreparedStatement)super.getDelegate();
   }

   public ResultSet executeQuery() throws SQLException {
      return this.getDelegate().executeQuery();
   }

   public int executeUpdate() throws SQLException {
      return this.getDelegate().executeUpdate();
   }

   public void setNull(int parameterIndex, int sqlType) throws SQLException {
      this.getDelegate().setNull(parameterIndex, sqlType);
   }

   public void setBoolean(int parameterIndex, boolean x) throws SQLException {
      this.getDelegate().setBoolean(parameterIndex, x);
   }

   public void setByte(int parameterIndex, byte x) throws SQLException {
      this.getDelegate().setByte(parameterIndex, x);
   }

   public void setShort(int parameterIndex, short x) throws SQLException {
      this.getDelegate().setShort(parameterIndex, x);
   }

   public void setInt(int parameterIndex, int x) throws SQLException {
      this.getDelegate().setInt(parameterIndex, x);
   }

   public void setLong(int parameterIndex, long x) throws SQLException {
      this.getDelegate().setLong(parameterIndex, x);
   }

   public void setFloat(int parameterIndex, float x) throws SQLException {
      this.getDelegate().setFloat(parameterIndex, x);
   }

   public void setDouble(int parameterIndex, double x) throws SQLException {
      this.getDelegate().setDouble(parameterIndex, x);
   }

   public void setBigDecimal(int parameterIndex, BigDecimal x) throws SQLException {
      this.getDelegate().setBigDecimal(parameterIndex, x);
   }

   public void setString(int parameterIndex, String x) throws SQLException {
      this.getDelegate().setString(parameterIndex, x);
   }

   public void setBytes(int parameterIndex, byte[] x) throws SQLException {
      this.getDelegate().setBytes(parameterIndex, x);
   }

   public void setDate(int parameterIndex, Date x) throws SQLException {
      this.getDelegate().setDate(parameterIndex, x);
   }

   public void setTime(int parameterIndex, Time x) throws SQLException {
      this.getDelegate().setTime(parameterIndex, x);
   }

   public void setTimestamp(int parameterIndex, Timestamp x) throws SQLException {
      this.getDelegate().setTimestamp(parameterIndex, x);
   }

   public void setAsciiStream(int parameterIndex, InputStream x, int length) throws SQLException {
      this.getDelegate().setAsciiStream(parameterIndex, x, length);
   }

   /** @deprecated */
   @Deprecated
   public void setUnicodeStream(int parameterIndex, InputStream x, int length) throws SQLException {
      this.getDelegate().setUnicodeStream(parameterIndex, x, length);
   }

   public void setBinaryStream(int parameterIndex, InputStream x, int length) throws SQLException {
      this.getDelegate().setBinaryStream(parameterIndex, x, length);
   }

   public void clearParameters() throws SQLException {
      this.getDelegate().clearParameters();
   }

   public void setObject(int parameterIndex, Object x, int targetSqlType) throws SQLException {
      this.getDelegate().setObject(parameterIndex, x, targetSqlType);
   }

   public void setObject(int parameterIndex, Object x) throws SQLException {
      this.getDelegate().setObject(parameterIndex, x);
   }

   public boolean execute() throws SQLException {
      return this.getDelegate().execute();
   }

   public void addBatch() throws SQLException {
      this.getDelegate().addBatch();
   }

   public void setCharacterStream(int parameterIndex, Reader reader, int length) throws SQLException {
      this.getDelegate().setCharacterStream(parameterIndex, reader, length);
   }

   public void setRef(int parameterIndex, Ref x) throws SQLException {
      this.getDelegate().setRef(parameterIndex, x);
   }

   public void setBlob(int parameterIndex, Blob x) throws SQLException {
      this.getDelegate().setBlob(parameterIndex, x);
   }

   public void setClob(int parameterIndex, Clob x) throws SQLException {
      this.getDelegate().setClob(parameterIndex, x);
   }

   public void setArray(int parameterIndex, Array x) throws SQLException {
      this.getDelegate().setArray(parameterIndex, x);
   }

   public ResultSetMetaData getMetaData() throws SQLException {
      return this.getDelegate().getMetaData();
   }

   public void setDate(int parameterIndex, Date x, Calendar cal) throws SQLException {
      this.getDelegate().setDate(parameterIndex, x, cal);
   }

   public void setTime(int parameterIndex, Time x, Calendar cal) throws SQLException {
      this.getDelegate().setTime(parameterIndex, x, cal);
   }

   public void setTimestamp(int parameterIndex, Timestamp x, Calendar cal) throws SQLException {
      this.getDelegate().setTimestamp(parameterIndex, x, cal);
   }

   public void setNull(int parameterIndex, int sqlType, String typeName) throws SQLException {
      this.getDelegate().setNull(parameterIndex, sqlType, typeName);
   }

   public void setURL(int parameterIndex, URL x) throws SQLException {
      this.getDelegate().setURL(parameterIndex, x);
   }

   public ParameterMetaData getParameterMetaData() throws SQLException {
      return this.getDelegate().getParameterMetaData();
   }

   public void setRowId(int parameterIndex, RowId x) throws SQLException {
      this.getDelegate().setRowId(parameterIndex, x);
   }

   public void setNString(int parameterIndex, String value) throws SQLException {
      this.getDelegate().setNString(parameterIndex, value);
   }

   public void setNCharacterStream(int parameterIndex, Reader value, long length) throws SQLException {
      this.getDelegate().setNCharacterStream(parameterIndex, value, length);
   }

   public void setNClob(int parameterIndex, NClob value) throws SQLException {
      this.getDelegate().setNClob(parameterIndex, value);
   }

   public void setClob(int parameterIndex, Reader reader, long length) throws SQLException {
      this.getDelegate().setClob(parameterIndex, reader, length);
   }

   public void setBlob(int parameterIndex, InputStream inputStream, long length) throws SQLException {
      this.getDelegate().setBlob(parameterIndex, inputStream, length);
   }

   public void setNClob(int parameterIndex, Reader reader, long length) throws SQLException {
      this.getDelegate().setNClob(parameterIndex, reader, length);
   }

   public void setSQLXML(int parameterIndex, SQLXML xmlObject) throws SQLException {
      this.getDelegate().setSQLXML(parameterIndex, xmlObject);
   }

   public void setObject(int parameterIndex, Object x, int targetSqlType, int scaleOrLength) throws SQLException {
      this.getDelegate().setObject(parameterIndex, x, targetSqlType, scaleOrLength);
   }

   public void setAsciiStream(int parameterIndex, InputStream x, long length) throws SQLException {
      this.getDelegate().setAsciiStream(parameterIndex, x, length);
   }

   public void setBinaryStream(int parameterIndex, InputStream x, long length) throws SQLException {
      this.getDelegate().setBinaryStream(parameterIndex, x, length);
   }

   public void setCharacterStream(int parameterIndex, Reader reader, long length) throws SQLException {
      this.getDelegate().setCharacterStream(parameterIndex, reader, length);
   }

   public void setAsciiStream(int parameterIndex, InputStream x) throws SQLException {
      this.getDelegate().setAsciiStream(parameterIndex, x);
   }

   public void setBinaryStream(int parameterIndex, InputStream x) throws SQLException {
      this.getDelegate().setBinaryStream(parameterIndex, x);
   }

   public void setCharacterStream(int parameterIndex, Reader reader) throws SQLException {
      this.getDelegate().setCharacterStream(parameterIndex, reader);
   }

   public void setNCharacterStream(int parameterIndex, Reader value) throws SQLException {
      this.getDelegate().setNCharacterStream(parameterIndex, value);
   }

   public void setClob(int parameterIndex, Reader reader) throws SQLException {
      this.getDelegate().setClob(parameterIndex, reader);
   }

   public void setBlob(int parameterIndex, InputStream inputStream) throws SQLException {
      this.getDelegate().setBlob(parameterIndex, inputStream);
   }

   public void setNClob(int parameterIndex, Reader reader) throws SQLException {
      this.getDelegate().setNClob(parameterIndex, reader);
   }
}
