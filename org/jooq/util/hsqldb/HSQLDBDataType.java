package org.jooq.util.hsqldb;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.sql.Date;
import java.sql.Time;
import java.sql.Timestamp;
import java.util.UUID;
import org.jooq.DataType;
import org.jooq.Record;
import org.jooq.Result;
import org.jooq.SQLDialect;
import org.jooq.impl.DefaultDataType;
import org.jooq.impl.SQLDataType;
import org.jooq.types.DayToSecond;
import org.jooq.types.UByte;
import org.jooq.types.UInteger;
import org.jooq.types.ULong;
import org.jooq.types.UShort;
import org.jooq.types.YearToMonth;

public class HSQLDBDataType {
   public static final DataType<Byte> TINYINT;
   public static final DataType<Short> SMALLINT;
   public static final DataType<Integer> INT;
   public static final DataType<Integer> INTEGER;
   public static final DataType<Long> BIGINT;
   public static final DataType<Double> DOUBLE;
   public static final DataType<Double> DOUBLEPRECISION;
   public static final DataType<Double> FLOAT;
   public static final DataType<Float> REAL;
   public static final DataType<Boolean> BOOLEAN;
   public static final DataType<Boolean> BIT;
   public static final DataType<BigDecimal> DECIMAL;
   public static final DataType<BigDecimal> NUMERIC;
   public static final DataType<String> VARCHAR;
   public static final DataType<String> LONGVARCHAR;
   public static final DataType<String> CHAR;
   public static final DataType<String> CHARACTER;
   public static final DataType<String> CHARACTERVARYING;
   public static final DataType<String> CLOB;
   public static final DataType<String> CHARLARGEOBJECT;
   public static final DataType<String> CHARACTERLARGEOBJECT;
   public static final DataType<Date> DATE;
   public static final DataType<Time> TIME;
   public static final DataType<Timestamp> TIMESTAMP;
   public static final DataType<Timestamp> DATETIME;
   public static final DataType<byte[]> LONGVARBINARY;
   public static final DataType<byte[]> VARBINARY;
   public static final DataType<byte[]> BINARY;
   public static final DataType<byte[]> BLOB;
   public static final DataType<byte[]> BINARYLARGEOBJECT;
   public static final DataType<Object> OTHER;
   public static final DataType<YearToMonth> INTERVALYEARTOMONTH;
   public static final DataType<DayToSecond> INTERVALDAYTOSECOND;
   protected static final DataType<String> __NCHAR;
   protected static final DataType<String> __NCLOB;
   protected static final DataType<String> __LONGNVARCHAR;
   protected static final DataType<String> __NVARCHAR;
   protected static final DataType<UByte> __TINYINTUNSIGNED;
   protected static final DataType<UShort> __SMALLINTUNSIGNED;
   protected static final DataType<UInteger> __INTEGERUNSIGNED;
   protected static final DataType<ULong> __BIGINTUNSIGNED;
   protected static final DataType<BigInteger> __BIGINTEGER;
   protected static final DataType<UUID> __UUID;
   public static final DataType<String> VARCHARIGNORECASE;
   public static final DataType<Object> OBJECT;
   public static final DataType<Result<Record>> ROW;

   static {
      TINYINT = new DefaultDataType(SQLDialect.HSQLDB, SQLDataType.TINYINT, "tinyint");
      SMALLINT = new DefaultDataType(SQLDialect.HSQLDB, SQLDataType.SMALLINT, "smallint");
      INT = new DefaultDataType(SQLDialect.HSQLDB, SQLDataType.INTEGER, "int");
      INTEGER = new DefaultDataType(SQLDialect.HSQLDB, SQLDataType.INTEGER, "integer");
      BIGINT = new DefaultDataType(SQLDialect.HSQLDB, SQLDataType.BIGINT, "bigint");
      DOUBLE = new DefaultDataType(SQLDialect.HSQLDB, SQLDataType.DOUBLE, "double");
      DOUBLEPRECISION = new DefaultDataType(SQLDialect.HSQLDB, SQLDataType.DOUBLE, "double precision");
      FLOAT = new DefaultDataType(SQLDialect.HSQLDB, SQLDataType.FLOAT, "float");
      REAL = new DefaultDataType(SQLDialect.HSQLDB, SQLDataType.REAL, "real");
      BOOLEAN = new DefaultDataType(SQLDialect.HSQLDB, SQLDataType.BOOLEAN, "boolean");
      BIT = new DefaultDataType(SQLDialect.HSQLDB, SQLDataType.BIT, "bit");
      DECIMAL = new DefaultDataType(SQLDialect.HSQLDB, SQLDataType.DECIMAL, "decimal");
      NUMERIC = new DefaultDataType(SQLDialect.HSQLDB, SQLDataType.NUMERIC, "numeric");
      VARCHAR = new DefaultDataType(SQLDialect.HSQLDB, SQLDataType.VARCHAR, "varchar", "varchar(32672)");
      LONGVARCHAR = new DefaultDataType(SQLDialect.HSQLDB, SQLDataType.LONGVARCHAR, "longvarchar");
      CHAR = new DefaultDataType(SQLDialect.HSQLDB, SQLDataType.CHAR, "char");
      CHARACTER = new DefaultDataType(SQLDialect.HSQLDB, SQLDataType.CHAR, "character");
      CHARACTERVARYING = new DefaultDataType(SQLDialect.HSQLDB, SQLDataType.VARCHAR, "character varying", "character varying(32672)");
      CLOB = new DefaultDataType(SQLDialect.HSQLDB, SQLDataType.CLOB, "clob");
      CHARLARGEOBJECT = new DefaultDataType(SQLDialect.HSQLDB, SQLDataType.CLOB, "char large object", "clob");
      CHARACTERLARGEOBJECT = new DefaultDataType(SQLDialect.HSQLDB, SQLDataType.CLOB, "character large object", "clob");
      DATE = new DefaultDataType(SQLDialect.HSQLDB, SQLDataType.DATE, "date");
      TIME = new DefaultDataType(SQLDialect.HSQLDB, SQLDataType.TIME, "time");
      TIMESTAMP = new DefaultDataType(SQLDialect.HSQLDB, SQLDataType.TIMESTAMP, "timestamp");
      DATETIME = new DefaultDataType(SQLDialect.HSQLDB, SQLDataType.TIMESTAMP, "datetime");
      LONGVARBINARY = new DefaultDataType(SQLDialect.HSQLDB, SQLDataType.LONGVARBINARY, "longvarbinary");
      VARBINARY = new DefaultDataType(SQLDialect.HSQLDB, SQLDataType.VARBINARY, "varbinary", "varbinary(32672)");
      BINARY = new DefaultDataType(SQLDialect.HSQLDB, SQLDataType.BINARY, "binary");
      BLOB = new DefaultDataType(SQLDialect.HSQLDB, SQLDataType.BLOB, "blob");
      BINARYLARGEOBJECT = new DefaultDataType(SQLDialect.HSQLDB, SQLDataType.BLOB, "binary large object", "blob");
      OTHER = new DefaultDataType(SQLDialect.HSQLDB, SQLDataType.OTHER, "other");
      INTERVALYEARTOMONTH = new DefaultDataType(SQLDialect.HSQLDB, SQLDataType.INTERVALYEARTOMONTH, "interval year to month");
      INTERVALDAYTOSECOND = new DefaultDataType(SQLDialect.HSQLDB, SQLDataType.INTERVALDAYTOSECOND, "interval day to second");
      __NCHAR = new DefaultDataType(SQLDialect.HSQLDB, SQLDataType.NCHAR, "char");
      __NCLOB = new DefaultDataType(SQLDialect.HSQLDB, SQLDataType.NCLOB, "clob");
      __LONGNVARCHAR = new DefaultDataType(SQLDialect.HSQLDB, SQLDataType.LONGNVARCHAR, "longvarchar");
      __NVARCHAR = new DefaultDataType(SQLDialect.HSQLDB, SQLDataType.NVARCHAR, "varchar", "varchar(32672)");
      __TINYINTUNSIGNED = new DefaultDataType(SQLDialect.HSQLDB, SQLDataType.TINYINTUNSIGNED, "smallint");
      __SMALLINTUNSIGNED = new DefaultDataType(SQLDialect.HSQLDB, SQLDataType.SMALLINTUNSIGNED, "int");
      __INTEGERUNSIGNED = new DefaultDataType(SQLDialect.HSQLDB, SQLDataType.INTEGERUNSIGNED, "bigint");
      __BIGINTUNSIGNED = new DefaultDataType(SQLDialect.HSQLDB, SQLDataType.BIGINTUNSIGNED, "decimal");
      __BIGINTEGER = new DefaultDataType(SQLDialect.HSQLDB, SQLDataType.DECIMAL_INTEGER, "decimal");
      __UUID = new DefaultDataType(SQLDialect.HSQLDB, SQLDataType.UUID, "varchar", "varchar(36)");
      VARCHARIGNORECASE = new DefaultDataType(SQLDialect.HSQLDB, SQLDataType.VARCHAR, "varchar_ignorecase", "varchar_ignorecase(32672)");
      OBJECT = new DefaultDataType(SQLDialect.HSQLDB, SQLDataType.OTHER, "object");
      ROW = new DefaultDataType(SQLDialect.HSQLDB, SQLDataType.RESULT, "row");
   }
}
