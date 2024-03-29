package org.jooq.util.mysql;

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

public class MySQLDataType {
   public static final DataType<Byte> TINYINT;
   public static final DataType<UByte> TINYINTUNSIGNED;
   public static final DataType<Short> SMALLINT;
   public static final DataType<UShort> SMALLINTUNSIGNED;
   public static final DataType<Integer> INT;
   public static final DataType<UInteger> INTUNSIGNED;
   public static final DataType<Integer> MEDIUMINT;
   public static final DataType<UInteger> MEDIUMINTUNSIGNED;
   public static final DataType<Integer> INTEGER;
   public static final DataType<UInteger> INTEGERUNSIGNED;
   public static final DataType<Long> BIGINT;
   public static final DataType<ULong> BIGINTUNSIGNED;
   public static final DataType<Double> DOUBLE;
   public static final DataType<Double> FLOAT;
   public static final DataType<Float> REAL;
   public static final DataType<Boolean> BOOLEAN;
   public static final DataType<Boolean> BOOL;
   public static final DataType<Boolean> BIT;
   public static final DataType<BigDecimal> DECIMAL;
   public static final DataType<BigDecimal> DEC;
   public static final DataType<String> VARCHAR;
   public static final DataType<String> CHAR;
   public static final DataType<byte[]> BLOB;
   public static final DataType<byte[]> BINARY;
   public static final DataType<byte[]> VARBINARY;
   public static final DataType<Date> DATE;
   public static final DataType<Time> TIME;
   public static final DataType<Timestamp> TIMESTAMP;
   public static final DataType<Timestamp> DATETIME;
   protected static final DataType<String> __NCHAR;
   protected static final DataType<String> __NCLOB;
   protected static final DataType<String> __LONGNVARCHAR;
   protected static final DataType<BigDecimal> __NUMERIC;
   protected static final DataType<String> __NVARCHAR;
   protected static final DataType<String> __LONGVARCHAR;
   protected static final DataType<byte[]> __LONGVARBINARY;
   protected static final DataType<BigInteger> __BIGINTEGER;
   protected static final DataType<UUID> __UUID;
   public static final DataType<String> TINYTEXT;
   public static final DataType<String> MEDIUMTEXT;
   public static final DataType<String> TEXT;
   public static final DataType<String> LONGTEXT;
   public static final DataType<String> ENUM;
   public static final DataType<String> SET;
   public static final DataType<byte[]> TINYBLOB;
   public static final DataType<byte[]> MEDIUMBLOB;
   public static final DataType<byte[]> LONGBLOB;
   public static final DataType<Date> YEAR;

   static {
      TINYINT = new DefaultDataType(SQLDialect.MYSQL, SQLDataType.TINYINT, "tinyint", "signed");
      TINYINTUNSIGNED = new DefaultDataType(SQLDialect.MYSQL, SQLDataType.TINYINTUNSIGNED, "tinyint unsigned", "unsigned");
      SMALLINT = new DefaultDataType(SQLDialect.MYSQL, SQLDataType.SMALLINT, "smallint", "signed");
      SMALLINTUNSIGNED = new DefaultDataType(SQLDialect.MYSQL, SQLDataType.SMALLINTUNSIGNED, "smallint unsigned", "unsigned");
      INT = new DefaultDataType(SQLDialect.MYSQL, SQLDataType.INTEGER, "int", "signed");
      INTUNSIGNED = new DefaultDataType(SQLDialect.MYSQL, SQLDataType.INTEGERUNSIGNED, "int unsigned", "unsigned");
      MEDIUMINT = new DefaultDataType(SQLDialect.MYSQL, SQLDataType.INTEGER, "mediumint", "signed");
      MEDIUMINTUNSIGNED = new DefaultDataType(SQLDialect.MYSQL, SQLDataType.INTEGERUNSIGNED, "mediumint unsigned", "unsigned");
      INTEGER = new DefaultDataType(SQLDialect.MYSQL, SQLDataType.INTEGER, "integer", "signed");
      INTEGERUNSIGNED = new DefaultDataType(SQLDialect.MYSQL, SQLDataType.INTEGERUNSIGNED, "integer unsigned", "unsigned");
      BIGINT = new DefaultDataType(SQLDialect.MYSQL, SQLDataType.BIGINT, "bigint", "signed");
      BIGINTUNSIGNED = new DefaultDataType(SQLDialect.MYSQL, SQLDataType.BIGINTUNSIGNED, "bigint unsigned", "unsigned");
      DOUBLE = new DefaultDataType(SQLDialect.MYSQL, SQLDataType.DOUBLE, "double", "decimal");
      FLOAT = new DefaultDataType(SQLDialect.MYSQL, SQLDataType.FLOAT, "float", "decimal");
      REAL = new DefaultDataType(SQLDialect.MYSQL, SQLDataType.REAL, "real", "decimal");
      BOOLEAN = new DefaultDataType(SQLDialect.MYSQL, SQLDataType.BOOLEAN, "boolean", "unsigned");
      BOOL = new DefaultDataType(SQLDialect.MYSQL, SQLDataType.BOOLEAN, "bool", "unsigned");
      BIT = new DefaultDataType(SQLDialect.MYSQL, SQLDataType.BIT, "bit", "unsigned");
      DECIMAL = new DefaultDataType(SQLDialect.MYSQL, SQLDataType.DECIMAL, "decimal", "decimal");
      DEC = new DefaultDataType(SQLDialect.MYSQL, SQLDataType.DECIMAL, "dec", "decimal");
      VARCHAR = new DefaultDataType(SQLDialect.MYSQL, SQLDataType.VARCHAR, "varchar", "char");
      CHAR = new DefaultDataType(SQLDialect.MYSQL, SQLDataType.CHAR, "char", "char");
      BLOB = new DefaultDataType(SQLDialect.MYSQL, SQLDataType.BLOB, "blob", "binary");
      BINARY = new DefaultDataType(SQLDialect.MYSQL, SQLDataType.BINARY, "binary", "binary");
      VARBINARY = new DefaultDataType(SQLDialect.MYSQL, SQLDataType.VARBINARY, "varbinary", "binary");
      DATE = new DefaultDataType(SQLDialect.MYSQL, SQLDataType.DATE, "date", "date");
      TIME = new DefaultDataType(SQLDialect.MYSQL, SQLDataType.TIME, "time", "time");
      TIMESTAMP = new DefaultDataType(SQLDialect.MYSQL, SQLDataType.TIMESTAMP, "timestamp", "datetime");
      DATETIME = new DefaultDataType(SQLDialect.MYSQL, SQLDataType.TIMESTAMP, "datetime", "datetime");
      __NCHAR = new DefaultDataType(SQLDialect.MYSQL, SQLDataType.NCHAR, "char", "char");
      __NCLOB = new DefaultDataType(SQLDialect.MYSQL, SQLDataType.NCLOB, "clob", "char");
      __LONGNVARCHAR = new DefaultDataType(SQLDialect.MYSQL, SQLDataType.LONGNVARCHAR, "varchar", "char");
      __NUMERIC = new DefaultDataType(SQLDialect.MYSQL, SQLDataType.NUMERIC, "decimal", "decimal");
      __NVARCHAR = new DefaultDataType(SQLDialect.MYSQL, SQLDataType.NVARCHAR, "varchar", "char");
      __LONGVARCHAR = new DefaultDataType(SQLDialect.MYSQL, SQLDataType.LONGVARCHAR, "varchar", "char");
      __LONGVARBINARY = new DefaultDataType(SQLDialect.MYSQL, SQLDataType.LONGVARBINARY, "varbinary", "binary");
      __BIGINTEGER = new DefaultDataType(SQLDialect.MYSQL, SQLDataType.DECIMAL_INTEGER, "decimal", "decimal");
      __UUID = new DefaultDataType(SQLDialect.MYSQL, SQLDataType.UUID, "varchar", "char");
      TINYTEXT = new DefaultDataType(SQLDialect.MYSQL, SQLDataType.CLOB, "tinytext", "char");
      MEDIUMTEXT = new DefaultDataType(SQLDialect.MYSQL, SQLDataType.CLOB, "mediumtext", "char");
      TEXT = new DefaultDataType(SQLDialect.MYSQL, SQLDataType.CLOB, "text", "char");
      LONGTEXT = new DefaultDataType(SQLDialect.MYSQL, SQLDataType.CLOB, "longtext", "char");
      ENUM = new DefaultDataType(SQLDialect.MYSQL, SQLDataType.VARCHAR, "enum", "char");
      SET = new DefaultDataType(SQLDialect.MYSQL, SQLDataType.VARCHAR, "set", "char");
      TINYBLOB = new DefaultDataType(SQLDialect.MYSQL, SQLDataType.BLOB, "tinyblob", "binary");
      MEDIUMBLOB = new DefaultDataType(SQLDialect.MYSQL, SQLDataType.BLOB, "mediumblob", "binary");
      LONGBLOB = new DefaultDataType(SQLDialect.MYSQL, SQLDataType.BLOB, "longblob", "binary");
      YEAR = new DefaultDataType(SQLDialect.MYSQL, SQLDataType.DATE, "year", "date");
   }
}
