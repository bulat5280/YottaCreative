package org.jooq.util.derby;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.sql.Date;
import java.sql.Time;
import java.sql.Timestamp;
import java.util.UUID;
import org.jooq.DataType;
import org.jooq.SQLDialect;
import org.jooq.impl.DefaultDataType;
import org.jooq.impl.SQLDataType;
import org.jooq.types.UByte;
import org.jooq.types.UInteger;
import org.jooq.types.ULong;
import org.jooq.types.UShort;

public class DerbyDataType {
   public static final DataType<Short> SMALLINT;
   public static final DataType<Integer> INT;
   public static final DataType<Integer> INTEGER;
   public static final DataType<Long> BIGINT;
   public static final DataType<Double> DOUBLE;
   public static final DataType<Double> DOUBLEPRECISION;
   public static final DataType<Double> FLOAT;
   public static final DataType<Float> REAL;
   public static final DataType<BigDecimal> DECIMAL;
   public static final DataType<BigDecimal> DEC;
   public static final DataType<BigDecimal> NUMERIC;
   public static final DataType<String> VARCHAR;
   public static final DataType<String> LONGVARCHAR;
   public static final DataType<String> CHAR;
   public static final DataType<String> CHARACTER;
   public static final DataType<String> CLOB;
   public static final DataType<String> CHARACTERLARGEOBJECT;
   public static final DataType<String> CHARVARYING;
   public static final DataType<String> CHARACTERVARYING;
   public static final DataType<Boolean> BOOLEAN;
   public static final DataType<Date> DATE;
   public static final DataType<Time> TIME;
   public static final DataType<Timestamp> TIMESTAMP;
   public static final DataType<byte[]> BLOB;
   public static final DataType<byte[]> BINARYLARGEOBJECT;
   protected static final DataType<byte[]> __BINARY;
   protected static final DataType<Boolean> __BIT;
   protected static final DataType<byte[]> __LONGVARBINARY;
   protected static final DataType<String> __NCHAR;
   protected static final DataType<String> __NCLOB;
   protected static final DataType<String> __LONGNVARCHAR;
   protected static final DataType<String> __NVARCHAR;
   protected static final DataType<Byte> __TINYINT;
   protected static final DataType<byte[]> __VARBINARY;
   protected static final DataType<UByte> __TINYINTUNSIGNED;
   protected static final DataType<UShort> __SMALLINTUNSIGNED;
   protected static final DataType<UInteger> __INTEGERUNSIGNED;
   protected static final DataType<ULong> __BIGINTUNSIGNED;
   protected static final DataType<BigInteger> __BIGINTEGER;
   protected static final DataType<UUID> __UUID;
   public static final DataType<byte[]> CHARFORBITDATA;
   public static final DataType<byte[]> CHARACTERFORBITDATA;
   public static final DataType<byte[]> LONGVARCHARFORBITDATA;
   public static final DataType<byte[]> VARCHARFORBITDATA;
   public static final DataType<byte[]> CHARVARYINGFORBITDATA;
   public static final DataType<byte[]> CHARACTERVARYINGFORBITDATA;
   public static final DataType<String> ORGAPACHEDERBYCATALOGTYPEDESCRIPTOR;
   public static final DataType<String> ORGAPACHEDERBYCATALOGINDEXDESCRIPTOR;
   public static final DataType<String> JAVAIOSERIALIZABLE;

   static {
      SMALLINT = new DefaultDataType(SQLDialect.DERBY, SQLDataType.SMALLINT, "smallint");
      INT = new DefaultDataType(SQLDialect.DERBY, SQLDataType.INTEGER, "int");
      INTEGER = new DefaultDataType(SQLDialect.DERBY, SQLDataType.INTEGER, "integer");
      BIGINT = new DefaultDataType(SQLDialect.DERBY, SQLDataType.BIGINT, "bigint");
      DOUBLE = new DefaultDataType(SQLDialect.DERBY, SQLDataType.DOUBLE, "double");
      DOUBLEPRECISION = new DefaultDataType(SQLDialect.DERBY, SQLDataType.DOUBLE, "double precision");
      FLOAT = new DefaultDataType(SQLDialect.DERBY, SQLDataType.FLOAT, "float");
      REAL = new DefaultDataType(SQLDialect.DERBY, SQLDataType.REAL, "real");
      DECIMAL = new DefaultDataType(SQLDialect.DERBY, SQLDataType.DECIMAL, "decimal");
      DEC = new DefaultDataType(SQLDialect.DERBY, SQLDataType.DECIMAL, "dec");
      NUMERIC = new DefaultDataType(SQLDialect.DERBY, SQLDataType.NUMERIC, "numeric");
      VARCHAR = new DefaultDataType(SQLDialect.DERBY, SQLDataType.VARCHAR, "varchar", "varchar(32672)");
      LONGVARCHAR = new DefaultDataType(SQLDialect.DERBY, SQLDataType.LONGVARCHAR, "long varchar");
      CHAR = new DefaultDataType(SQLDialect.DERBY, SQLDataType.CHAR, "char", "varchar(32672)");
      CHARACTER = new DefaultDataType(SQLDialect.DERBY, SQLDataType.CHAR, "character", "varchar(32672)");
      CLOB = new DefaultDataType(SQLDialect.DERBY, SQLDataType.CLOB, "clob");
      CHARACTERLARGEOBJECT = new DefaultDataType(SQLDialect.DERBY, SQLDataType.CLOB, "character large object");
      CHARVARYING = new DefaultDataType(SQLDialect.DERBY, SQLDataType.VARCHAR, "char varying", "char varying(32672)");
      CHARACTERVARYING = new DefaultDataType(SQLDialect.DERBY, SQLDataType.VARCHAR, "character varying", "character varying(32672)");
      BOOLEAN = new DefaultDataType(SQLDialect.DERBY, SQLDataType.BOOLEAN, "boolean");
      DATE = new DefaultDataType(SQLDialect.DERBY, SQLDataType.DATE, "date");
      TIME = new DefaultDataType(SQLDialect.DERBY, SQLDataType.TIME, "time");
      TIMESTAMP = new DefaultDataType(SQLDialect.DERBY, SQLDataType.TIMESTAMP, "timestamp");
      BLOB = new DefaultDataType(SQLDialect.DERBY, SQLDataType.BLOB, "blob");
      BINARYLARGEOBJECT = new DefaultDataType(SQLDialect.DERBY, SQLDataType.BLOB, "binary large object");
      __BINARY = new DefaultDataType(SQLDialect.DERBY, SQLDataType.BINARY, "blob");
      __BIT = new DefaultDataType(SQLDialect.DERBY, SQLDataType.BIT, "boolean");
      __LONGVARBINARY = new DefaultDataType(SQLDialect.DERBY, SQLDataType.LONGVARBINARY, "blob");
      __NCHAR = new DefaultDataType(SQLDialect.DERBY, SQLDataType.NCHAR, "char", "varchar(32672)");
      __NCLOB = new DefaultDataType(SQLDialect.DERBY, SQLDataType.NCLOB, "clob");
      __LONGNVARCHAR = new DefaultDataType(SQLDialect.DERBY, SQLDataType.LONGNVARCHAR, "long varchar");
      __NVARCHAR = new DefaultDataType(SQLDialect.DERBY, SQLDataType.NVARCHAR, "varchar", "varchar(32672)");
      __TINYINT = new DefaultDataType(SQLDialect.DERBY, SQLDataType.TINYINT, "smallint");
      __VARBINARY = new DefaultDataType(SQLDialect.DERBY, SQLDataType.VARBINARY, "blob");
      __TINYINTUNSIGNED = new DefaultDataType(SQLDialect.DERBY, SQLDataType.TINYINTUNSIGNED, "smallint");
      __SMALLINTUNSIGNED = new DefaultDataType(SQLDialect.DERBY, SQLDataType.SMALLINTUNSIGNED, "int");
      __INTEGERUNSIGNED = new DefaultDataType(SQLDialect.DERBY, SQLDataType.INTEGERUNSIGNED, "bigint");
      __BIGINTUNSIGNED = new DefaultDataType(SQLDialect.DERBY, SQLDataType.BIGINTUNSIGNED, "decimal", "decimal(20)");
      __BIGINTEGER = new DefaultDataType(SQLDialect.DERBY, SQLDataType.DECIMAL_INTEGER, "decimal", "decimal(31)");
      __UUID = new DefaultDataType(SQLDialect.DERBY, SQLDataType.UUID, "varchar", "varchar(36)");
      CHARFORBITDATA = new DefaultDataType(SQLDialect.DERBY, SQLDataType.BINARY, "char for bit data");
      CHARACTERFORBITDATA = new DefaultDataType(SQLDialect.DERBY, SQLDataType.BINARY, "character for bit data");
      LONGVARCHARFORBITDATA = new DefaultDataType(SQLDialect.DERBY, SQLDataType.BINARY, "long varchar for bit data");
      VARCHARFORBITDATA = new DefaultDataType(SQLDialect.DERBY, SQLDataType.VARBINARY, "varchar for bit data", "varchar(32672) for bit data");
      CHARVARYINGFORBITDATA = new DefaultDataType(SQLDialect.DERBY, SQLDataType.VARBINARY, "char varying for bit data", "char varying(32672) for bit data");
      CHARACTERVARYINGFORBITDATA = new DefaultDataType(SQLDialect.DERBY, SQLDataType.VARBINARY, "character varying for bit data", "character varying (32672) for bit data");
      ORGAPACHEDERBYCATALOGTYPEDESCRIPTOR = new DefaultDataType(SQLDialect.DERBY, SQLDataType.CLOB, "org.apache.derby.catalog.TypeDescriptor");
      ORGAPACHEDERBYCATALOGINDEXDESCRIPTOR = new DefaultDataType(SQLDialect.DERBY, SQLDataType.CLOB, "org.apache.derby.catalog.IndexDescriptor");
      JAVAIOSERIALIZABLE = new DefaultDataType(SQLDialect.DERBY, SQLDataType.CLOB, "java.io.Serializable");
   }
}
