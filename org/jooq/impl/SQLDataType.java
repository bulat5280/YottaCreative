package org.jooq.impl;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.sql.Date;
import java.sql.Time;
import java.sql.Timestamp;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.OffsetDateTime;
import java.time.OffsetTime;
import java.util.UUID;
import org.jooq.DataType;
import org.jooq.Record;
import org.jooq.Result;
import org.jooq.SQLDialect;
import org.jooq.types.DayToSecond;
import org.jooq.types.UByte;
import org.jooq.types.UInteger;
import org.jooq.types.ULong;
import org.jooq.types.UShort;
import org.jooq.types.YearToMonth;
import org.jooq.util.cubrid.CUBRIDDataType;
import org.jooq.util.derby.DerbyDataType;
import org.jooq.util.firebird.FirebirdDataType;
import org.jooq.util.h2.H2DataType;
import org.jooq.util.hsqldb.HSQLDBDataType;
import org.jooq.util.mariadb.MariaDBDataType;
import org.jooq.util.mysql.MySQLDataType;
import org.jooq.util.postgres.PostgresDataType;
import org.jooq.util.sqlite.SQLiteDataType;

public final class SQLDataType {
   public static final DataType<String> VARCHAR = new DefaultDataType((SQLDialect)null, String.class, "varchar");
   public static final DataType<String> CHAR = new DefaultDataType((SQLDialect)null, String.class, "char");
   public static final DataType<String> LONGVARCHAR = new DefaultDataType((SQLDialect)null, String.class, "longvarchar");
   public static final DataType<String> CLOB = new DefaultDataType((SQLDialect)null, String.class, "clob");
   public static final DataType<String> NVARCHAR = new DefaultDataType((SQLDialect)null, String.class, "nvarchar");
   public static final DataType<String> NCHAR = new DefaultDataType((SQLDialect)null, String.class, "nchar");
   public static final DataType<String> LONGNVARCHAR = new DefaultDataType((SQLDialect)null, String.class, "longnvarchar");
   public static final DataType<String> NCLOB = new DefaultDataType((SQLDialect)null, String.class, "nclob");
   public static final DataType<Boolean> BOOLEAN = new DefaultDataType((SQLDialect)null, Boolean.class, "boolean");
   public static final DataType<Boolean> BIT = new DefaultDataType((SQLDialect)null, Boolean.class, "bit");
   public static final DataType<Byte> TINYINT = new DefaultDataType((SQLDialect)null, Byte.class, "tinyint");
   public static final DataType<Short> SMALLINT = new DefaultDataType((SQLDialect)null, Short.class, "smallint");
   public static final DataType<Integer> INTEGER = new DefaultDataType((SQLDialect)null, Integer.class, "integer");
   public static final DataType<Long> BIGINT = new DefaultDataType((SQLDialect)null, Long.class, "bigint");
   public static final DataType<BigInteger> DECIMAL_INTEGER = new DefaultDataType((SQLDialect)null, BigInteger.class, "decimal_integer");
   public static final DataType<UByte> TINYINTUNSIGNED = new DefaultDataType((SQLDialect)null, UByte.class, "tinyint unsigned");
   public static final DataType<UShort> SMALLINTUNSIGNED = new DefaultDataType((SQLDialect)null, UShort.class, "smallint unsigned");
   public static final DataType<UInteger> INTEGERUNSIGNED = new DefaultDataType((SQLDialect)null, UInteger.class, "integer unsigned");
   public static final DataType<ULong> BIGINTUNSIGNED = new DefaultDataType((SQLDialect)null, ULong.class, "bigint unsigned");
   public static final DataType<Double> DOUBLE = new DefaultDataType((SQLDialect)null, Double.class, "double");
   public static final DataType<Double> FLOAT = new DefaultDataType((SQLDialect)null, Double.class, "float");
   public static final DataType<Float> REAL = new DefaultDataType((SQLDialect)null, Float.class, "real");
   public static final DataType<BigDecimal> NUMERIC = new DefaultDataType((SQLDialect)null, BigDecimal.class, "numeric");
   public static final DataType<BigDecimal> DECIMAL = new DefaultDataType((SQLDialect)null, BigDecimal.class, "decimal");
   public static final DataType<Date> DATE = new DefaultDataType((SQLDialect)null, Date.class, "date");
   public static final DataType<Timestamp> TIMESTAMP = new DefaultDataType((SQLDialect)null, Timestamp.class, "timestamp");
   public static final DataType<Time> TIME = new DefaultDataType((SQLDialect)null, Time.class, "time");
   public static final DataType<YearToMonth> INTERVALYEARTOMONTH = new DefaultDataType((SQLDialect)null, YearToMonth.class, "interval year to month");
   public static final DataType<DayToSecond> INTERVALDAYTOSECOND = new DefaultDataType((SQLDialect)null, DayToSecond.class, "interval day to second");
   public static final DataType<LocalDate> LOCALDATE = new DefaultDataType((SQLDialect)null, LocalDate.class, "date");
   public static final DataType<LocalTime> LOCALTIME = new DefaultDataType((SQLDialect)null, LocalTime.class, "time");
   public static final DataType<LocalDateTime> LOCALDATETIME = new DefaultDataType((SQLDialect)null, LocalDateTime.class, "timestamp");
   public static final DataType<OffsetTime> OFFSETTIME = new DefaultDataType((SQLDialect)null, OffsetTime.class, "time with time zone");
   public static final DataType<OffsetDateTime> OFFSETDATETIME = new DefaultDataType((SQLDialect)null, OffsetDateTime.class, "timestamp with time zone");
   public static final DataType<OffsetTime> TIMEWITHTIMEZONE;
   public static final DataType<OffsetDateTime> TIMESTAMPWITHTIMEZONE;
   public static final DataType<byte[]> BINARY;
   public static final DataType<byte[]> VARBINARY;
   public static final DataType<byte[]> LONGVARBINARY;
   public static final DataType<byte[]> BLOB;
   public static final DataType<Object> OTHER;
   public static final DataType<Record> RECORD;
   public static final DataType<Result<Record>> RESULT;
   public static final DataType<UUID> UUID;

   public static final DataType<String> VARCHAR(int length) {
      return VARCHAR.length(length);
   }

   public static final DataType<String> CHAR(int length) {
      return CHAR.length(length);
   }

   public static final DataType<String> LONGVARCHAR(int length) {
      return LONGVARCHAR.length(length);
   }

   public static final DataType<String> CLOB(int length) {
      return CLOB.length(length);
   }

   public static final DataType<String> NVARCHAR(int length) {
      return NVARCHAR.length(length);
   }

   public static final DataType<String> NCHAR(int length) {
      return NCHAR.length(length);
   }

   public static final DataType<String> LONGNVARCHAR(int length) {
      return LONGNVARCHAR.length(length);
   }

   public static final DataType<String> NCLOB(int length) {
      return NCLOB.length(length);
   }

   public static final DataType<BigDecimal> NUMERIC(int precision) {
      return NUMERIC.precision(precision);
   }

   public static final DataType<BigDecimal> NUMERIC(int precision, int scale) {
      return NUMERIC.precision(precision, scale);
   }

   public static final DataType<BigDecimal> DECIMAL(int precision) {
      return DECIMAL.precision(precision);
   }

   public static final DataType<BigDecimal> DECIMAL(int precision, int scale) {
      return DECIMAL.precision(precision, scale);
   }

   public static final DataType<byte[]> BINARY(int length) {
      return BINARY.length(length);
   }

   public static final DataType<byte[]> VARBINARY(int length) {
      return VARBINARY.length(length);
   }

   public static final DataType<byte[]> LONGVARBINARY(int length) {
      return LONGVARBINARY.length(length);
   }

   public static final DataType<byte[]> BLOB(int length) {
      return BLOB.length(length);
   }

   private SQLDataType() {
   }

   static {
      TIMEWITHTIMEZONE = OFFSETTIME;
      TIMESTAMPWITHTIMEZONE = OFFSETDATETIME;
      BINARY = new DefaultDataType((SQLDialect)null, byte[].class, "binary");
      VARBINARY = new DefaultDataType((SQLDialect)null, byte[].class, "varbinary");
      LONGVARBINARY = new DefaultDataType((SQLDialect)null, byte[].class, "longvarbinary");
      BLOB = new DefaultDataType((SQLDialect)null, byte[].class, "blob");
      OTHER = new DefaultDataType((SQLDialect)null, Object.class, "other");
      RECORD = new DefaultDataType((SQLDialect)null, Record.class, "record");
      RESULT = new DefaultDataType((SQLDialect)null, Result.class, "result");
      UUID = new DefaultDataType((SQLDialect)null, UUID.class, "uuid");

      try {
         Class.forName(CUBRIDDataType.class.getName());
         Class.forName(DerbyDataType.class.getName());
         Class.forName(FirebirdDataType.class.getName());
         Class.forName(H2DataType.class.getName());
         Class.forName(HSQLDBDataType.class.getName());
         Class.forName(MariaDBDataType.class.getName());
         Class.forName(MySQLDataType.class.getName());
         Class.forName(PostgresDataType.class.getName());
         Class.forName(SQLiteDataType.class.getName());
      } catch (Exception var1) {
      }

   }
}
