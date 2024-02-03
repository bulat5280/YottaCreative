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

public class DefaultResultSet extends JDBC41ResultSet implements ResultSet {
   private ResultSet delegate;

   public DefaultResultSet(ResultSet delegate) {
      this.delegate = delegate;
   }

   public ResultSet getDelegate() {
      return this.delegate;
   }

   public <T> T unwrap(Class<T> iface) throws SQLException {
      return this.delegate.unwrap(iface);
   }

   public boolean isWrapperFor(Class<?> iface) throws SQLException {
      return this.delegate.isWrapperFor(iface);
   }

   public boolean next() throws SQLException {
      return this.delegate.next();
   }

   public void close() throws SQLException {
      this.delegate.close();
   }

   public boolean wasNull() throws SQLException {
      return this.delegate.wasNull();
   }

   public String getString(int columnIndex) throws SQLException {
      return this.delegate.getString(columnIndex);
   }

   public boolean getBoolean(int columnIndex) throws SQLException {
      return this.delegate.getBoolean(columnIndex);
   }

   public byte getByte(int columnIndex) throws SQLException {
      return this.delegate.getByte(columnIndex);
   }

   public short getShort(int columnIndex) throws SQLException {
      return this.delegate.getShort(columnIndex);
   }

   public int getInt(int columnIndex) throws SQLException {
      return this.delegate.getInt(columnIndex);
   }

   public long getLong(int columnIndex) throws SQLException {
      return this.delegate.getLong(columnIndex);
   }

   public float getFloat(int columnIndex) throws SQLException {
      return this.delegate.getFloat(columnIndex);
   }

   public double getDouble(int columnIndex) throws SQLException {
      return this.delegate.getDouble(columnIndex);
   }

   /** @deprecated */
   @Deprecated
   public BigDecimal getBigDecimal(int columnIndex, int scale) throws SQLException {
      return this.delegate.getBigDecimal(columnIndex, scale);
   }

   public byte[] getBytes(int columnIndex) throws SQLException {
      return this.delegate.getBytes(columnIndex);
   }

   public Date getDate(int columnIndex) throws SQLException {
      return this.delegate.getDate(columnIndex);
   }

   public Time getTime(int columnIndex) throws SQLException {
      return this.delegate.getTime(columnIndex);
   }

   public Timestamp getTimestamp(int columnIndex) throws SQLException {
      return this.delegate.getTimestamp(columnIndex);
   }

   public InputStream getAsciiStream(int columnIndex) throws SQLException {
      return this.delegate.getAsciiStream(columnIndex);
   }

   /** @deprecated */
   @Deprecated
   public InputStream getUnicodeStream(int columnIndex) throws SQLException {
      return this.delegate.getUnicodeStream(columnIndex);
   }

   public InputStream getBinaryStream(int columnIndex) throws SQLException {
      return this.delegate.getBinaryStream(columnIndex);
   }

   public String getString(String columnLabel) throws SQLException {
      return this.delegate.getString(columnLabel);
   }

   public boolean getBoolean(String columnLabel) throws SQLException {
      return this.delegate.getBoolean(columnLabel);
   }

   public byte getByte(String columnLabel) throws SQLException {
      return this.delegate.getByte(columnLabel);
   }

   public short getShort(String columnLabel) throws SQLException {
      return this.delegate.getShort(columnLabel);
   }

   public int getInt(String columnLabel) throws SQLException {
      return this.delegate.getInt(columnLabel);
   }

   public long getLong(String columnLabel) throws SQLException {
      return this.delegate.getLong(columnLabel);
   }

   public float getFloat(String columnLabel) throws SQLException {
      return this.delegate.getFloat(columnLabel);
   }

   public double getDouble(String columnLabel) throws SQLException {
      return this.delegate.getDouble(columnLabel);
   }

   /** @deprecated */
   @Deprecated
   public BigDecimal getBigDecimal(String columnLabel, int scale) throws SQLException {
      return this.delegate.getBigDecimal(columnLabel, scale);
   }

   public byte[] getBytes(String columnLabel) throws SQLException {
      return this.delegate.getBytes(columnLabel);
   }

   public Date getDate(String columnLabel) throws SQLException {
      return this.delegate.getDate(columnLabel);
   }

   public Time getTime(String columnLabel) throws SQLException {
      return this.delegate.getTime(columnLabel);
   }

   public Timestamp getTimestamp(String columnLabel) throws SQLException {
      return this.delegate.getTimestamp(columnLabel);
   }

   public InputStream getAsciiStream(String columnLabel) throws SQLException {
      return this.delegate.getAsciiStream(columnLabel);
   }

   /** @deprecated */
   @Deprecated
   public InputStream getUnicodeStream(String columnLabel) throws SQLException {
      return this.delegate.getUnicodeStream(columnLabel);
   }

   public InputStream getBinaryStream(String columnLabel) throws SQLException {
      return this.delegate.getBinaryStream(columnLabel);
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
      return this.delegate.getObject(columnIndex);
   }

   public Object getObject(String columnLabel) throws SQLException {
      return this.delegate.getObject(columnLabel);
   }

   public int findColumn(String columnLabel) throws SQLException {
      return this.delegate.findColumn(columnLabel);
   }

   public Reader getCharacterStream(int columnIndex) throws SQLException {
      return this.delegate.getCharacterStream(columnIndex);
   }

   public Reader getCharacterStream(String columnLabel) throws SQLException {
      return this.delegate.getCharacterStream(columnLabel);
   }

   public BigDecimal getBigDecimal(int columnIndex) throws SQLException {
      return this.delegate.getBigDecimal(columnIndex);
   }

   public BigDecimal getBigDecimal(String columnLabel) throws SQLException {
      return this.delegate.getBigDecimal(columnLabel);
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
      return this.delegate.getObject(columnIndex, map);
   }

   public Ref getRef(int columnIndex) throws SQLException {
      return this.delegate.getRef(columnIndex);
   }

   public Blob getBlob(int columnIndex) throws SQLException {
      return this.delegate.getBlob(columnIndex);
   }

   public Clob getClob(int columnIndex) throws SQLException {
      return this.delegate.getClob(columnIndex);
   }

   public Array getArray(int columnIndex) throws SQLException {
      return this.delegate.getArray(columnIndex);
   }

   public Object getObject(String columnLabel, Map<String, Class<?>> map) throws SQLException {
      return this.delegate.getObject(columnLabel, map);
   }

   public Ref getRef(String columnLabel) throws SQLException {
      return this.delegate.getRef(columnLabel);
   }

   public Blob getBlob(String columnLabel) throws SQLException {
      return this.delegate.getBlob(columnLabel);
   }

   public Clob getClob(String columnLabel) throws SQLException {
      return this.delegate.getClob(columnLabel);
   }

   public Array getArray(String columnLabel) throws SQLException {
      return this.delegate.getArray(columnLabel);
   }

   public Date getDate(int columnIndex, Calendar cal) throws SQLException {
      return this.delegate.getDate(columnIndex, cal);
   }

   public Date getDate(String columnLabel, Calendar cal) throws SQLException {
      return this.delegate.getDate(columnLabel, cal);
   }

   public Time getTime(int columnIndex, Calendar cal) throws SQLException {
      return this.delegate.getTime(columnIndex, cal);
   }

   public Time getTime(String columnLabel, Calendar cal) throws SQLException {
      return this.delegate.getTime(columnLabel, cal);
   }

   public Timestamp getTimestamp(int columnIndex, Calendar cal) throws SQLException {
      return this.delegate.getTimestamp(columnIndex, cal);
   }

   public Timestamp getTimestamp(String columnLabel, Calendar cal) throws SQLException {
      return this.delegate.getTimestamp(columnLabel, cal);
   }

   public URL getURL(int columnIndex) throws SQLException {
      return this.delegate.getURL(columnIndex);
   }

   public URL getURL(String columnLabel) throws SQLException {
      return this.delegate.getURL(columnLabel);
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
      return this.delegate.getRowId(columnIndex);
   }

   public RowId getRowId(String columnLabel) throws SQLException {
      return this.delegate.getRowId(columnLabel);
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
      return this.delegate.getNClob(columnIndex);
   }

   public NClob getNClob(String columnLabel) throws SQLException {
      return this.delegate.getNClob(columnLabel);
   }

   public SQLXML getSQLXML(int columnIndex) throws SQLException {
      return this.delegate.getSQLXML(columnIndex);
   }

   public SQLXML getSQLXML(String columnLabel) throws SQLException {
      return this.delegate.getSQLXML(columnLabel);
   }

   public void updateSQLXML(int columnIndex, SQLXML xmlObject) throws SQLException {
      this.delegate.updateSQLXML(columnIndex, xmlObject);
   }

   public void updateSQLXML(String columnLabel, SQLXML xmlObject) throws SQLException {
      this.delegate.updateSQLXML(columnLabel, xmlObject);
   }

   public String getNString(int columnIndex) throws SQLException {
      return this.delegate.getNString(columnIndex);
   }

   public String getNString(String columnLabel) throws SQLException {
      return this.delegate.getNString(columnLabel);
   }

   public Reader getNCharacterStream(int columnIndex) throws SQLException {
      return this.delegate.getNCharacterStream(columnIndex);
   }

   public Reader getNCharacterStream(String columnLabel) throws SQLException {
      return this.delegate.getNCharacterStream(columnLabel);
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
}
