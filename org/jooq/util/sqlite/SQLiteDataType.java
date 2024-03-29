package org.jooq.util.sqlite;

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

public class SQLiteDataType {
   public static final DataType<Byte> TINYINT;
   public static final DataType<Short> SMALLINT;
   public static final DataType<Short> INT2;
   public static final DataType<Integer> INT;
   public static final DataType<Integer> INTEGER;
   public static final DataType<Integer> MEDIUMINT;
   public static final DataType<Long> INT8;
   public static final DataType<Long> BIGINT;
   public static final DataType<BigInteger> UNSIGNEDBIGINT;
   public static final DataType<Double> DOUBLE;
   public static final DataType<Double> DOUBLEPRECISION;
   public static final DataType<Float> REAL;
   public static final DataType<Float> FLOAT;
   public static final DataType<BigDecimal> NUMERIC;
   public static final DataType<BigDecimal> DECIMAL;
   public static final DataType<String> LONGVARCHAR;
   public static final DataType<String> CHAR;
   public static final DataType<String> CHARACTER;
   public static final DataType<String> VARCHAR;
   public static final DataType<String> VARYINGCHARACTER;
   public static final DataType<String> NCHAR;
   public static final DataType<String> NATIVECHARACTER;
   public static final DataType<String> NVARCHAR;
   public static final DataType<String> CLOB;
   public static final DataType<String> TEXT;
   public static final DataType<Boolean> BOOLEAN;
   public static final DataType<Date> DATE;
   public static final DataType<Timestamp> DATETIME;
   public static final DataType<byte[]> LONGVARBINARY;
   public static final DataType<byte[]> BLOB;
   protected static final DataType<byte[]> __BINARY;
   protected static final DataType<Boolean> __BIT;
   protected static final DataType<Double> __FLOAT;
   protected static final DataType<String> __NCLOB;
   protected static final DataType<String> __LONGNVARCHAR;
   protected static final DataType<Time> __TIME;
   protected static final DataType<byte[]> __VARBINARY;
   protected static final DataType<UByte> __TINYINTUNSIGNED;
   protected static final DataType<UShort> __SMALLINTUNSIGNED;
   protected static final DataType<UInteger> __INTEGERUNSIGNED;
   protected static final DataType<ULong> __BIGINTUNSIGNED;
   protected static final DataType<UUID> __UUID;
   public static final DataType<Object> NULL;

   static {
      TINYINT = new DefaultDataType(SQLDialect.SQLITE, SQLDataType.TINYINT, "tinyint");
      SMALLINT = new DefaultDataType(SQLDialect.SQLITE, SQLDataType.SMALLINT, "smallint");
      INT2 = new DefaultDataType(SQLDialect.SQLITE, SQLDataType.SMALLINT, "int2");
      INT = new DefaultDataType(SQLDialect.SQLITE, SQLDataType.INTEGER, "int");
      INTEGER = new DefaultDataType(SQLDialect.SQLITE, SQLDataType.INTEGER, "integer");
      MEDIUMINT = new DefaultDataType(SQLDialect.SQLITE, SQLDataType.INTEGER, "mediumint");
      INT8 = new DefaultDataType(SQLDialect.SQLITE, SQLDataType.BIGINT, "int8");
      BIGINT = new DefaultDataType(SQLDialect.SQLITE, SQLDataType.BIGINT, "bigint");
      UNSIGNEDBIGINT = new DefaultDataType(SQLDialect.SQLITE, SQLDataType.DECIMAL_INTEGER, "unsigned big int");
      DOUBLE = new DefaultDataType(SQLDialect.SQLITE, SQLDataType.DOUBLE, "double");
      DOUBLEPRECISION = new DefaultDataType(SQLDialect.SQLITE, SQLDataType.DOUBLE, "double precision");
      REAL = new DefaultDataType(SQLDialect.SQLITE, SQLDataType.REAL, "real");
      FLOAT = new DefaultDataType(SQLDialect.SQLITE, SQLDataType.REAL, "float");
      NUMERIC = new DefaultDataType(SQLDialect.SQLITE, SQLDataType.NUMERIC, "numeric");
      DECIMAL = new DefaultDataType(SQLDialect.SQLITE, SQLDataType.DECIMAL, "decimal");
      LONGVARCHAR = new DefaultDataType(SQLDialect.SQLITE, SQLDataType.LONGVARCHAR, "longvarchar");
      CHAR = new DefaultDataType(SQLDialect.SQLITE, SQLDataType.CHAR, "char");
      CHARACTER = new DefaultDataType(SQLDialect.SQLITE, SQLDataType.CHAR, "character");
      VARCHAR = new DefaultDataType(SQLDialect.SQLITE, SQLDataType.VARCHAR, "varchar");
      VARYINGCHARACTER = new DefaultDataType(SQLDialect.SQLITE, SQLDataType.VARCHAR, "varying character");
      NCHAR = new DefaultDataType(SQLDialect.SQLITE, SQLDataType.NCHAR, "nchar");
      NATIVECHARACTER = new DefaultDataType(SQLDialect.SQLITE, SQLDataType.NCHAR, "native character");
      NVARCHAR = new DefaultDataType(SQLDialect.SQLITE, SQLDataType.NVARCHAR, "nvarchar");
      CLOB = new DefaultDataType(SQLDialect.SQLITE, SQLDataType.CLOB, "clob");
      TEXT = new DefaultDataType(SQLDialect.SQLITE, SQLDataType.CLOB, "text");
      BOOLEAN = new DefaultDataType(SQLDialect.SQLITE, SQLDataType.BOOLEAN, "boolean");
      DATE = new DefaultDataType(SQLDialect.SQLITE, SQLDataType.DATE, "date");
      DATETIME = new DefaultDataType(SQLDialect.SQLITE, SQLDataType.TIMESTAMP, "datetime");
      LONGVARBINARY = new DefaultDataType(SQLDialect.SQLITE, SQLDataType.LONGVARBINARY, "longvarbinary");
      BLOB = new DefaultDataType(SQLDialect.SQLITE, SQLDataType.BLOB, "blob");
      __BINARY = new DefaultDataType(SQLDialect.SQLITE, SQLDataType.BINARY, "longvarbinary");
      __BIT = new DefaultDataType(SQLDialect.SQLITE, SQLDataType.BIT, "boolean");
      __FLOAT = new DefaultDataType(SQLDialect.SQLITE, SQLDataType.FLOAT, "double");
      __NCLOB = new DefaultDataType(SQLDialect.SQLITE, SQLDataType.NCLOB, "nclob");
      __LONGNVARCHAR = new DefaultDataType(SQLDialect.SQLITE, SQLDataType.LONGNVARCHAR, "nvarchar");
      __TIME = new DefaultDataType(SQLDialect.SQLITE, SQLDataType.TIME, "datetime");
      __VARBINARY = new DefaultDataType(SQLDialect.SQLITE, SQLDataType.VARBINARY, "longvarbinary");
      __TINYINTUNSIGNED = new DefaultDataType(SQLDialect.SQLITE, SQLDataType.TINYINTUNSIGNED, "smallint");
      __SMALLINTUNSIGNED = new DefaultDataType(SQLDialect.SQLITE, SQLDataType.SMALLINTUNSIGNED, "int");
      __INTEGERUNSIGNED = new DefaultDataType(SQLDialect.SQLITE, SQLDataType.INTEGERUNSIGNED, "bigint");
      __BIGINTUNSIGNED = new DefaultDataType(SQLDialect.SQLITE, SQLDataType.BIGINTUNSIGNED, "numeric");
      __UUID = new DefaultDataType(SQLDialect.SQLITE, SQLDataType.UUID, "varchar");
      NULL = new DefaultDataType(SQLDialect.SQLITE, SQLDataType.OTHER, "null");
   }
}
