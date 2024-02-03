package org.jooq.impl;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.sql.Blob;
import java.sql.CallableStatement;
import java.sql.Clob;
import java.sql.Date;
import java.sql.ResultSet;
import java.sql.SQLData;
import java.sql.SQLException;
import java.sql.SQLInput;
import java.sql.Time;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.OffsetDateTime;
import java.time.OffsetTime;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import org.jooq.Attachable;
import org.jooq.Binding;
import org.jooq.BindingGetResultSetContext;
import org.jooq.BindingGetSQLInputContext;
import org.jooq.BindingGetStatementContext;
import org.jooq.BindingRegisterContext;
import org.jooq.BindingSQLContext;
import org.jooq.BindingSetSQLOutputContext;
import org.jooq.BindingSetStatementContext;
import org.jooq.Configuration;
import org.jooq.Context;
import org.jooq.Converter;
import org.jooq.Converters;
import org.jooq.DataType;
import org.jooq.EnumType;
import org.jooq.Field;
import org.jooq.Record;
import org.jooq.RenderContext;
import org.jooq.Result;
import org.jooq.Row;
import org.jooq.SQLDialect;
import org.jooq.Schema;
import org.jooq.Scope;
import org.jooq.UDTRecord;
import org.jooq.conf.ParamType;
import org.jooq.exception.ControlFlowSignal;
import org.jooq.exception.DataTypeException;
import org.jooq.exception.MappingException;
import org.jooq.exception.SQLDialectNotSupportedException;
import org.jooq.tools.Convert;
import org.jooq.tools.JooqLogger;
import org.jooq.tools.jdbc.JDBCUtils;
import org.jooq.tools.jdbc.MockArray;
import org.jooq.tools.reflect.Reflect;
import org.jooq.types.DayToSecond;
import org.jooq.types.Interval;
import org.jooq.types.UByte;
import org.jooq.types.UInteger;
import org.jooq.types.ULong;
import org.jooq.types.UNumber;
import org.jooq.types.UShort;
import org.jooq.types.YearToMonth;
import org.jooq.util.postgres.PostgresUtils;

public class DefaultBinding<T, U> implements Binding<T, U> {
   static final JooqLogger log = JooqLogger.getLogger(DefaultBinding.class);
   private static final char[] HEX = "0123456789abcdef".toCharArray();
   private static final long serialVersionUID = -198499389344950496L;
   final Class<T> type;
   final Converter<T, U> converter;
   /** @deprecated */
   @Deprecated
   final boolean isLob;
   private static final Pattern LENIENT_OFFSET_PATTERN = Pattern.compile("(\\d{4}-\\d{2}-\\d{2})[T ](\\d{2}:\\d{2}:\\d{2}(?:\\.\\d+)?)(?: +)?(([+-])(\\d)?(\\d)(:\\d{2})?)?");

   public DefaultBinding(Converter<T, U> converter) {
      this(converter, false);
   }

   DefaultBinding(Converter<T, U> converter, boolean isLob) {
      this.type = converter.fromType();
      this.converter = converter;
      this.isLob = isLob;
   }

   static <T, X, U> Binding<T, U> newBinding(final Converter<X, U> converter, DataType<T> type, final Binding<T, X> binding) {
      Object theBinding;
      if (converter == null && binding == null) {
         theBinding = type.getBinding();
      } else if (converter == null) {
         theBinding = binding;
      } else if (binding == null) {
         theBinding = new DefaultBinding(converter, type.isLob());
      } else {
         theBinding = new Binding<T, U>() {
            private static final long serialVersionUID = 8912340791845209886L;
            final Converter<T, U> theConverter = Converters.of(binding.converter(), converter);

            public Converter<T, U> converter() {
               return this.theConverter;
            }

            public void sql(BindingSQLContext<U> ctx) throws SQLException {
               binding.sql(ctx.convert(converter));
            }

            public void register(BindingRegisterContext<U> ctx) throws SQLException {
               binding.register(ctx.convert(converter));
            }

            public void set(BindingSetStatementContext<U> ctx) throws SQLException {
               binding.set(ctx.convert(converter));
            }

            public void set(BindingSetSQLOutputContext<U> ctx) throws SQLException {
               binding.set(ctx.convert(converter));
            }

            public void get(BindingGetResultSetContext<U> ctx) throws SQLException {
               binding.get(ctx.convert(converter));
            }

            public void get(BindingGetStatementContext<U> ctx) throws SQLException {
               binding.get(ctx.convert(converter));
            }

            public void get(BindingGetSQLInputContext<U> ctx) throws SQLException {
               binding.get(ctx.convert(converter));
            }
         };
      }

      return (Binding)theBinding;
   }

   public Converter<T, U> converter() {
      return this.converter;
   }

   public void sql(BindingSQLContext<U> ctx) {
      T converted = this.converter.to(ctx.value());
      switch(ctx.render().castMode()) {
      case NEVER:
         this.toSQL(ctx, converted);
         return;
      case ALWAYS:
         this.toSQLCast(ctx, converted);
         return;
      default:
         if (this.shouldCast(ctx, converted)) {
            this.toSQLCast(ctx, converted);
         } else {
            this.toSQL(ctx, converted);
         }

      }
   }

   private final boolean shouldCast(BindingSQLContext<U> ctx, T converted) {
      if (ctx.render().paramType() != ParamType.INLINED && !(converted instanceof EnumType)) {
         switch(ctx.family()) {
         case DERBY:
         case FIREBIRD:
         case H2:
         case HSQLDB:
         case CUBRID:
         case POSTGRES:
            return true;
         }
      }

      if (Interval.class.isAssignableFrom(this.type)) {
         switch(ctx.family()) {
         case POSTGRES:
            return true;
         }
      }

      if (this.type == OffsetTime.class || this.type == OffsetDateTime.class) {
         switch(ctx.family()) {
         case POSTGRES:
            return true;
         }
      }

      return false;
   }

   private final void toSQLCast(BindingSQLContext<U> ctx, T converted) {
      DataType<T> dataType = DefaultDataType.getDataType(ctx.dialect(), this.type);
      DataType<T> sqlDataType = dataType.getSQLDataType();
      SQLDialect family = ctx.family();
      if (converted != null && this.type == BigDecimal.class && Arrays.asList(SQLDialect.CUBRID, SQLDialect.DERBY, SQLDialect.FIREBIRD, SQLDialect.HSQLDB).contains(family)) {
         int scale = ((BigDecimal)converted).scale();
         int precision = ((BigDecimal)converted).precision();
         if (scale >= precision) {
            precision = scale + 1;
         }

         this.toSQLCast(ctx, converted, dataType, 0, precision, scale);
      } else if (SQLDataType.OTHER == sqlDataType) {
         if (converted != null) {
            this.toSQLCast(ctx, converted, DefaultDataType.getDataType(family, converted.getClass()), 0, 0, 0);
         } else if (Arrays.asList().contains(family)) {
            ctx.render().sql(ctx.variable());
         } else {
            this.toSQLCast(ctx, converted, DefaultDataType.getDataType(family, String.class), 0, 0, 0);
         }
      } else if (!Arrays.asList(SQLDialect.POSTGRES).contains(family) || sqlDataType != null && sqlDataType.isTemporal()) {
         if ((sqlDataType == SQLDataType.VARCHAR || sqlDataType == SQLDataType.CHAR) && Arrays.asList(SQLDialect.FIREBIRD).contains(family)) {
            this.toSQLCast(ctx, converted, dataType, this.getValueLength(converted), 0, 0);
         } else {
            this.toSQLCast(ctx, converted, dataType, dataType.length(), dataType.precision(), dataType.scale());
         }
      } else {
         this.toSQL(ctx, converted);
      }

   }

   private final int getValueLength(T value) {
      String string = (String)value;
      if (string == null) {
         return 1;
      } else {
         int length = string.length();

         for(int i = 0; i < length; ++i) {
            if (string.charAt(i) > 127) {
               return Math.min(32672, 4 * length);
            }
         }

         return Math.min(32672, length);
      }
   }

   private final void toSQLCast(BindingSQLContext<U> ctx, T converted, DataType<?> dataType, int length, int precision, int scale) {
      ctx.render().keyword("cast").sql('(');
      this.toSQL(ctx, converted);
      ctx.render().sql(' ').keyword("as").sql(' ').sql(dataType.length(length).precision(precision, scale).getCastTypeName(ctx.configuration())).sql(')');
   }

   private final void toSQL(BindingSQLContext<U> ctx, Object val) {
      SQLDialect family = ctx.family();
      RenderContext render = ctx.render();
      if (render.paramType() == ParamType.INLINED) {
         if (val == null) {
            render.keyword("null");
         } else if (this.type == Boolean.class) {
            if (Arrays.asList(SQLDialect.FIREBIRD, SQLDialect.SQLITE).contains(family)) {
               render.sql((Boolean)val ? "1" : "0");
            } else {
               render.keyword(((Boolean)val).toString());
            }
         } else if (this.type == byte[].class) {
            byte[] binary = (byte[])((byte[])val);
            if (Arrays.asList().contains(family)) {
               render.sql("0x").sql(convertBytesToHex(binary));
            } else if (Arrays.asList(SQLDialect.DERBY, SQLDialect.H2, SQLDialect.HSQLDB, SQLDialect.MARIADB, SQLDialect.MYSQL, SQLDialect.SQLITE).contains(family)) {
               render.sql("X'").sql(convertBytesToHex(binary)).sql('\'');
            } else if (Arrays.asList().contains(family)) {
               render.keyword("hextoraw('").sql(convertBytesToHex(binary)).sql("')");
            } else if (family == SQLDialect.POSTGRES) {
               render.sql("E'").sql(PostgresUtils.toPGString(binary)).keyword("'::bytea");
            } else {
               render.sql("X'").sql(convertBytesToHex(binary)).sql('\'');
            }
         } else if (Interval.class.isAssignableFrom(this.type)) {
            render.sql('\'').sql(this.escape(val, render)).sql('\'');
         } else if (Double.class.isAssignableFrom(this.type) && ((Double)val).isNaN()) {
            if (SQLDialect.POSTGRES == family) {
               ((RenderContext)render.visit(DSL.inline("NaN"))).sql("::").keyword("float8");
            } else {
               render.sql(((Number)val).toString());
            }
         } else if (Float.class.isAssignableFrom(this.type) && ((Float)val).isNaN()) {
            if (SQLDialect.POSTGRES == family) {
               ((RenderContext)render.visit(DSL.inline("NaN"))).sql("::").keyword("float4");
            } else {
               render.sql(((Number)val).toString());
            }
         } else if (Number.class.isAssignableFrom(this.type)) {
            render.sql(((Number)val).toString());
         } else if (this.type == Date.class) {
            if (Arrays.asList(SQLDialect.SQLITE).contains(family)) {
               render.sql('\'').sql(this.escape(val, render)).sql('\'');
            } else if (family == SQLDialect.DERBY) {
               render.keyword("date('").sql(this.escape(val, render)).sql("')");
            } else if (family == SQLDialect.MYSQL) {
               render.keyword("{d '").sql(this.escape(val, render)).sql("'}");
            } else {
               render.keyword("date '").sql(this.escape(val, render)).sql('\'');
            }
         } else if (this.type == Timestamp.class) {
            if (Arrays.asList(SQLDialect.SQLITE).contains(family)) {
               render.sql('\'').sql(this.escape(val, render)).sql('\'');
            } else if (family == SQLDialect.DERBY) {
               render.keyword("timestamp('").sql(this.escape(val, render)).sql("')");
            } else if (family == SQLDialect.CUBRID) {
               render.keyword("datetime '").sql(this.escape(val, render)).sql('\'');
            } else if (family == SQLDialect.MYSQL) {
               render.keyword("{ts '").sql(this.escape(val, render)).sql("'}");
            } else {
               render.keyword("timestamp '").sql(this.escape(val, render)).sql('\'');
            }
         } else if (this.type == Time.class) {
            if (Arrays.asList(SQLDialect.SQLITE).contains(family)) {
               render.sql('\'').sql((new SimpleDateFormat("HH:mm:ss")).format((Time)val)).sql('\'');
            } else if (family == SQLDialect.DERBY) {
               render.keyword("time").sql("('").sql(this.escape(val, render)).sql("')");
            } else if (family == SQLDialect.MYSQL) {
               render.keyword("{t '").sql(this.escape(val, render)).sql("'}");
            } else {
               render.keyword("time").sql(" '").sql(this.escape(val, render)).sql('\'');
            }
         } else {
            String separator;
            if (this.type.isArray()) {
               separator = "";
               Object[] var6;
               int var7;
               int var8;
               Object o;
               if (family == SQLDialect.H2) {
                  render.sql('(');
                  var6 = (Object[])((Object[])val);
                  var7 = var6.length;

                  for(var8 = 0; var8 < var7; ++var8) {
                     o = var6[var8];
                     render.sql(separator);
                     (new DefaultBinding(Converters.identity(this.type.getComponentType()), this.isLob)).sql(new DefaultBindingSQLContext(ctx.configuration(), ctx.data(), ctx.render(), o));
                     separator = ", ";
                  }

                  render.sql(')');
               } else if (family == SQLDialect.POSTGRES) {
                  render.visit(DSL.cast((Field)DSL.inline(PostgresUtils.toPGArrayString((Object[])((Object[])val))), (Class)this.type));
               } else {
                  render.keyword("ARRAY");
                  render.sql('[');
                  var6 = (Object[])((Object[])val);
                  var7 = var6.length;

                  for(var8 = 0; var8 < var7; ++var8) {
                     o = var6[var8];
                     render.sql(separator);
                     (new DefaultBinding(Converters.identity(this.type.getComponentType()), this.isLob)).sql(new DefaultBindingSQLContext(ctx.configuration(), ctx.data(), ctx.render(), o));
                     separator = ", ";
                  }

                  render.sql(']');
                  if (family == SQLDialect.POSTGRES && EnumType.class.isAssignableFrom(this.type.getComponentType())) {
                     pgRenderEnumCast(render, this.type);
                  }
               }
            } else if (EnumType.class.isAssignableFrom(this.type)) {
               separator = ((EnumType)val).getLiteral();
               if (separator == null) {
                  (new DefaultBinding(Converters.identity(String.class), this.isLob)).sql(new DefaultBindingSQLContext(ctx.configuration(), ctx.data(), ctx.render(), separator));
               } else {
                  (new DefaultBinding(Converters.identity(String.class), this.isLob)).sql(new DefaultBindingSQLContext(ctx.configuration(), ctx.data(), ctx.render(), separator));
               }
            } else if (UDTRecord.class.isAssignableFrom(this.type)) {
               render.sql("[UDT]");
            } else {
               render.sql('\'').sql(this.escape(val, render), true).sql('\'');
            }
         }
      } else if (family == SQLDialect.POSTGRES) {
         if (EnumType.class.isAssignableFrom(this.type) || this.type.isArray() && EnumType.class.isAssignableFrom(this.type.getComponentType())) {
            render.sql(ctx.variable());
            pgRenderEnumCast(render, this.type);
         } else if (this.type.isArray() && byte[].class != this.type) {
            render.sql(ctx.variable());
            render.sql("::");
            render.keyword(DefaultDataType.getDataType(family, this.type).getCastTypeName(render.configuration()));
         } else {
            render.sql(ctx.variable());
         }
      } else {
         render.sql(ctx.variable());
      }

   }

   private final String escape(Object val, Context<?> context) {
      String result = val.toString();
      if (Tools.needsBackslashEscaping(context.configuration())) {
         result = result.replace("\\", "\\\\");
      }

      return result.replace("'", "''");
   }

   private static final String convertBytesToHex(byte[] value) {
      return convertBytesToHex(value, value.length);
   }

   private static final String convertBytesToHex(byte[] value, int len) {
      char[] buff = new char[len + len];
      char[] hex = HEX;

      for(int i = 0; i < len; ++i) {
         int c = value[i] & 255;
         buff[i + i] = hex[c >> 4];
         buff[i + i + 1] = hex[c & 15];
      }

      return new String(buff);
   }

   public void register(BindingRegisterContext<U> ctx) throws SQLException {
      Configuration configuration = ctx.configuration();
      int sqlType = DefaultDataType.getDataType(ctx.dialect(), this.type).getSQLType();
      if (log.isTraceEnabled()) {
         log.trace("Registering variable " + ctx.index(), (Object)("" + this.type));
      }

      switch(configuration.family()) {
      default:
         ctx.statement().registerOutParameter(ctx.index(), sqlType);
      }
   }

   public void set(BindingSetStatementContext<U> ctx) throws SQLException {
      Configuration configuration = ctx.configuration();
      SQLDialect dialect = ctx.dialect();
      T value = this.converter.to(ctx.value());
      if (log.isTraceEnabled()) {
         if (value != null && value.getClass().isArray() && value.getClass() != byte[].class) {
            log.trace("Binding variable " + ctx.index(), (Object)(Arrays.asList((Object[])((Object[])value)) + " (" + this.type + ")"));
         } else {
            log.trace("Binding variable " + ctx.index(), (Object)(value + " (" + this.type + ")"));
         }
      }

      if (value == null) {
         int sqlType = DefaultDataType.getDataType(dialect, this.type).getSQLType();
         if (UDTRecord.class.isAssignableFrom(this.type)) {
            ctx.statement().setNull(ctx.index(), sqlType, Tools.getMappedUDTName(configuration, this.type));
         } else if (Arrays.asList(SQLDialect.POSTGRES).contains(configuration.family()) && sqlType == 2004) {
            ctx.statement().setNull(ctx.index(), -2);
         } else if (sqlType != 1111) {
            ctx.statement().setNull(ctx.index(), sqlType);
         } else {
            ctx.statement().setObject(ctx.index(), (Object)null);
         }
      } else {
         Class<?> actualType = this.type;
         if (actualType == Object.class) {
            actualType = value.getClass();
         }

         if (actualType == Blob.class) {
            ctx.statement().setBlob(ctx.index(), (Blob)value);
         } else if (actualType == Boolean.class) {
            ctx.statement().setBoolean(ctx.index(), (Boolean)value);
         } else if (actualType == BigDecimal.class) {
            if (Arrays.asList(SQLDialect.SQLITE).contains(dialect.family())) {
               ctx.statement().setString(ctx.index(), value.toString());
            } else {
               ctx.statement().setBigDecimal(ctx.index(), (BigDecimal)value);
            }
         } else if (actualType == BigInteger.class) {
            if (Arrays.asList(SQLDialect.SQLITE).contains(dialect.family())) {
               ctx.statement().setString(ctx.index(), value.toString());
            } else {
               ctx.statement().setBigDecimal(ctx.index(), new BigDecimal((BigInteger)value));
            }
         } else if (actualType == Byte.class) {
            ctx.statement().setByte(ctx.index(), (Byte)value);
         } else if (actualType == byte[].class) {
            ctx.statement().setBytes(ctx.index(), (byte[])((byte[])value));
         } else if (actualType == Clob.class) {
            ctx.statement().setClob(ctx.index(), (Clob)value);
         } else if (actualType == Double.class) {
            ctx.statement().setDouble(ctx.index(), (Double)value);
         } else if (actualType == Float.class) {
            ctx.statement().setFloat(ctx.index(), (Float)value);
         } else if (actualType == Integer.class) {
            ctx.statement().setInt(ctx.index(), (Integer)value);
         } else if (actualType == Long.class) {
            ctx.statement().setLong(ctx.index(), (Long)value);
         } else if (actualType == Short.class) {
            ctx.statement().setShort(ctx.index(), (Short)value);
         } else if (actualType == String.class) {
            ctx.statement().setString(ctx.index(), (String)value);
         } else if (actualType == Date.class) {
            Date date = (Date)value;
            if (dialect == SQLDialect.SQLITE) {
               ctx.statement().setString(ctx.index(), date.toString());
            } else {
               ctx.statement().setDate(ctx.index(), date);
            }
         } else if (actualType == Time.class) {
            Time time = (Time)value;
            if (dialect == SQLDialect.SQLITE) {
               ctx.statement().setString(ctx.index(), time.toString());
            } else {
               ctx.statement().setTime(ctx.index(), time);
            }
         } else if (actualType == Timestamp.class) {
            Timestamp timestamp = (Timestamp)value;
            if (dialect == SQLDialect.SQLITE) {
               ctx.statement().setString(ctx.index(), timestamp.toString());
            } else {
               ctx.statement().setTimestamp(ctx.index(), timestamp);
            }
         } else if (actualType == LocalDate.class) {
            ctx.statement().setDate(ctx.index(), Date.valueOf((LocalDate)value));
         } else if (actualType == LocalTime.class) {
            ctx.statement().setTime(ctx.index(), Time.valueOf((LocalTime)value));
         } else if (actualType == LocalDateTime.class) {
            ctx.statement().setTimestamp(ctx.index(), Timestamp.valueOf((LocalDateTime)value));
         } else if (actualType == OffsetTime.class) {
            ctx.statement().setString(ctx.index(), value.toString());
         } else if (actualType == OffsetDateTime.class) {
            ctx.statement().setString(ctx.index(), value.toString());
         } else if (actualType == YearToMonth.class) {
            if (dialect.family() == SQLDialect.POSTGRES) {
               ctx.statement().setObject(ctx.index(), PostgresUtils.toPGInterval((YearToMonth)value));
            } else {
               ctx.statement().setString(ctx.index(), value.toString());
            }
         } else if (actualType == DayToSecond.class) {
            if (dialect.family() == SQLDialect.POSTGRES) {
               ctx.statement().setObject(ctx.index(), PostgresUtils.toPGInterval((DayToSecond)value));
            } else {
               ctx.statement().setString(ctx.index(), value.toString());
            }
         } else if (actualType == UByte.class) {
            ctx.statement().setShort(ctx.index(), ((UByte)value).shortValue());
         } else if (actualType == UShort.class) {
            ctx.statement().setInt(ctx.index(), ((UShort)value).intValue());
         } else if (actualType == UInteger.class) {
            ctx.statement().setLong(ctx.index(), ((UInteger)value).longValue());
         } else if (actualType == ULong.class) {
            ctx.statement().setBigDecimal(ctx.index(), new BigDecimal(value.toString()));
         } else if (actualType == UUID.class) {
            switch(dialect.family()) {
            case H2:
            case POSTGRES:
               ctx.statement().setObject(ctx.index(), value);
               break;
            default:
               ctx.statement().setString(ctx.index(), value.toString());
            }
         } else if (actualType.isArray()) {
            switch(dialect.family()) {
            case H2:
               ctx.statement().setObject(ctx.index(), value);
               break;
            case HSQLDB:
               Object[] a = (Object[])((Object[])value);
               Class<?> t = actualType;
               if (actualType == UUID[].class) {
                  a = Convert.convertArray(a, String[].class);
                  t = String[].class;
               }

               ctx.statement().setArray(ctx.index(), new MockArray(dialect, a, t));
               break;
            case CUBRID:
            default:
               throw new SQLDialectNotSupportedException("Cannot bind ARRAY types in dialect " + dialect);
            case POSTGRES:
               ctx.statement().setString(ctx.index(), PostgresUtils.toPGArrayString((Object[])((Object[])value)));
            }
         } else if (EnumType.class.isAssignableFrom(actualType)) {
            ctx.statement().setString(ctx.index(), ((EnumType)value).getLiteral());
         } else {
            ctx.statement().setObject(ctx.index(), value);
         }
      }

   }

   public void set(BindingSetSQLOutputContext<U> ctx) throws SQLException {
      Configuration configuration = ctx.configuration();
      T value = this.converter.to(ctx.value());
      if (value == null) {
         ctx.output().writeObject((SQLData)null);
      } else if (this.type == Blob.class) {
         ctx.output().writeBlob((Blob)value);
      } else if (this.type == Boolean.class) {
         ctx.output().writeBoolean((Boolean)value);
      } else if (this.type == BigInteger.class) {
         ctx.output().writeBigDecimal(new BigDecimal((BigInteger)value));
      } else if (this.type == BigDecimal.class) {
         ctx.output().writeBigDecimal((BigDecimal)value);
      } else if (this.type == Byte.class) {
         ctx.output().writeByte((Byte)value);
      } else if (this.type == byte[].class) {
         if (this.isLob) {
            Blob blob = null;

            try {
               blob = (Blob)Reflect.on("oracle.sql.BLOB").call("createTemporary", Reflect.on((Object)ctx.output()).call("getSTRUCT").call("getJavaSqlConnection").get(), false, Reflect.on("oracle.sql.BLOB").get("DURATION_SESSION")).get();
               blob.setBytes(1L, (byte[])((byte[])value));
               ctx.output().writeBlob(blob);
            } finally {
               DefaultExecuteContext.register(blob);
            }
         } else {
            ctx.output().writeBytes((byte[])((byte[])value));
         }
      } else if (this.type == Clob.class) {
         ctx.output().writeClob((Clob)value);
      } else if (this.type == Date.class) {
         Date date = (Date)value;
         ctx.output().writeDate(date);
      } else if (this.type == Double.class) {
         ctx.output().writeDouble((Double)value);
      } else if (this.type == Float.class) {
         ctx.output().writeFloat((Float)value);
      } else if (this.type == Integer.class) {
         ctx.output().writeInt((Integer)value);
      } else if (this.type == Long.class) {
         ctx.output().writeLong((Long)value);
      } else if (this.type == Short.class) {
         ctx.output().writeShort((Short)value);
      } else if (this.type == String.class) {
         if (this.isLob) {
            Clob clob = null;

            try {
               clob = (Clob)Reflect.on("oracle.sql.CLOB").call("createTemporary", Reflect.on((Object)ctx.output()).call("getSTRUCT").call("getJavaSqlConnection").get(), false, Reflect.on("oracle.sql.CLOB").get("DURATION_SESSION")).get();
               clob.setString(1L, (String)value);
               ctx.output().writeClob(clob);
            } finally {
               DefaultExecuteContext.register(clob);
            }
         } else {
            ctx.output().writeString((String)value);
         }
      } else if (this.type == Time.class) {
         ctx.output().writeTime((Time)value);
      } else if (this.type == Timestamp.class) {
         ctx.output().writeTimestamp((Timestamp)value);
      } else if (this.type == YearToMonth.class) {
         ctx.output().writeString(value.toString());
      } else if (this.type == DayToSecond.class) {
         ctx.output().writeString(value.toString());
      } else if (UNumber.class.isAssignableFrom(this.type)) {
         ctx.output().writeString(value.toString());
      } else if (this.type == UUID.class) {
         ctx.output().writeString(value.toString());
      } else if (EnumType.class.isAssignableFrom(this.type)) {
         ctx.output().writeString(((EnumType)value).getLiteral());
      } else {
         if (!UDTRecord.class.isAssignableFrom(this.type)) {
            throw new UnsupportedOperationException("Type " + this.type + " is not supported");
         }

         ctx.output().writeObject((UDTRecord)value);
      }

   }

   public void get(BindingGetResultSetContext<U> ctx) throws SQLException {
      T result = null;
      if (this.type == Blob.class) {
         result = ctx.resultSet().getBlob(ctx.index());
      } else if (this.type == Boolean.class) {
         result = JDBCUtils.wasNull(ctx.resultSet(), ctx.resultSet().getBoolean(ctx.index()));
      } else if (this.type == BigInteger.class) {
         if (ctx.configuration().dialect() == SQLDialect.SQLITE) {
            result = Convert.convert((Object)ctx.resultSet().getString(ctx.index()), (Class)BigInteger.class);
         } else {
            BigDecimal b = ctx.resultSet().getBigDecimal(ctx.index());
            result = b == null ? null : b.toBigInteger();
         }
      } else if (this.type == BigDecimal.class) {
         if (ctx.configuration().dialect() == SQLDialect.SQLITE) {
            result = Convert.convert((Object)ctx.resultSet().getString(ctx.index()), (Class)BigDecimal.class);
         } else {
            result = ctx.resultSet().getBigDecimal(ctx.index());
         }
      } else if (this.type == Byte.class) {
         result = JDBCUtils.wasNull((ResultSet)ctx.resultSet(), (Number)ctx.resultSet().getByte(ctx.index()));
      } else if (this.type == byte[].class) {
         result = ctx.resultSet().getBytes(ctx.index());
      } else if (this.type == Clob.class) {
         result = ctx.resultSet().getClob(ctx.index());
      } else if (this.type == Date.class) {
         result = getDate(ctx.family(), ctx.resultSet(), ctx.index());
      } else if (this.type == Double.class) {
         result = JDBCUtils.wasNull((ResultSet)ctx.resultSet(), (Number)ctx.resultSet().getDouble(ctx.index()));
      } else if (this.type == Float.class) {
         result = JDBCUtils.wasNull((ResultSet)ctx.resultSet(), (Number)ctx.resultSet().getFloat(ctx.index()));
      } else if (this.type == Integer.class) {
         result = JDBCUtils.wasNull((ResultSet)ctx.resultSet(), (Number)ctx.resultSet().getInt(ctx.index()));
      } else if (this.type == LocalDate.class) {
         result = this.localDate(getDate(ctx.family(), ctx.resultSet(), ctx.index()));
      } else if (this.type == LocalTime.class) {
         result = this.localTime(getTime(ctx.family(), ctx.resultSet(), ctx.index()));
      } else if (this.type == LocalDateTime.class) {
         result = this.localDateTime(getTimestamp(ctx.family(), ctx.resultSet(), ctx.index()));
      } else if (this.type == Long.class) {
         result = JDBCUtils.wasNull((ResultSet)ctx.resultSet(), (Number)ctx.resultSet().getLong(ctx.index()));
      } else if (this.type == OffsetTime.class) {
         result = this.offsetTime(ctx.resultSet().getString(ctx.index()));
      } else if (this.type == OffsetDateTime.class) {
         result = this.offsetDateTime(ctx.resultSet().getString(ctx.index()));
      } else if (this.type == Short.class) {
         result = JDBCUtils.wasNull((ResultSet)ctx.resultSet(), (Number)ctx.resultSet().getShort(ctx.index()));
      } else if (this.type == String.class) {
         result = ctx.resultSet().getString(ctx.index());
      } else if (this.type == Time.class) {
         result = getTime(ctx.family(), ctx.resultSet(), ctx.index());
      } else if (this.type == Timestamp.class) {
         result = getTimestamp(ctx.family(), ctx.resultSet(), ctx.index());
      } else {
         Object object;
         String string;
         if (this.type == YearToMonth.class) {
            if (ctx.family() == SQLDialect.POSTGRES) {
               object = ctx.resultSet().getObject(ctx.index());
               result = object == null ? null : PostgresUtils.toYearToMonth(object);
            } else {
               string = ctx.resultSet().getString(ctx.index());
               result = string == null ? null : YearToMonth.valueOf(string);
            }
         } else if (this.type == DayToSecond.class) {
            if (ctx.family() == SQLDialect.POSTGRES) {
               object = ctx.resultSet().getObject(ctx.index());
               result = object == null ? null : PostgresUtils.toDayToSecond(object);
            } else {
               string = ctx.resultSet().getString(ctx.index());
               result = string == null ? null : DayToSecond.valueOf(string);
            }
         } else if (this.type == UByte.class) {
            result = Convert.convert((Object)ctx.resultSet().getString(ctx.index()), (Class)UByte.class);
         } else if (this.type == UShort.class) {
            result = Convert.convert((Object)ctx.resultSet().getString(ctx.index()), (Class)UShort.class);
         } else if (this.type == UInteger.class) {
            result = Convert.convert((Object)ctx.resultSet().getString(ctx.index()), (Class)UInteger.class);
         } else if (this.type == ULong.class) {
            result = Convert.convert((Object)ctx.resultSet().getString(ctx.index()), (Class)ULong.class);
         } else if (this.type == UUID.class) {
            switch(ctx.family()) {
            case H2:
            case POSTGRES:
               result = ctx.resultSet().getObject(ctx.index());
               break;
            default:
               result = Convert.convert((Object)ctx.resultSet().getString(ctx.index()), (Class)UUID.class);
            }
         } else if (this.type.isArray()) {
            switch(ctx.family()) {
            case POSTGRES:
               result = pgGetArray(ctx, ctx.resultSet(), this.type, ctx.index());
               break;
            default:
               result = convertArray(ctx.resultSet().getArray(ctx.index()), this.type);
            }
         } else if (EnumType.class.isAssignableFrom(this.type)) {
            result = getEnumType(this.type, ctx.resultSet().getString(ctx.index()));
         } else if (Record.class.isAssignableFrom(this.type)) {
            switch(ctx.family()) {
            case POSTGRES:
               result = pgNewRecord(this.type, (Field[])null, ctx.resultSet().getObject(ctx.index()));
               break;
            default:
               result = ctx.resultSet().getObject(ctx.index(), typeMap(this.type, ctx.configuration()));
            }
         } else if (Result.class.isAssignableFrom(this.type)) {
            ResultSet nested = (ResultSet)ctx.resultSet().getObject(ctx.index());
            result = DSL.using(ctx.configuration()).fetch(nested);
         } else {
            result = unlob(ctx.resultSet().getObject(ctx.index()));
         }
      }

      if (result instanceof Attachable && Tools.attachRecords(ctx.configuration())) {
         ((Attachable)result).attach(ctx.configuration());
      }

      ctx.value(this.converter.from(result));
   }

   private final LocalDate localDate(Date date) {
      return date == null ? null : date.toLocalDate();
   }

   private final LocalTime localTime(Time time) {
      return time == null ? null : time.toLocalTime();
   }

   private final LocalDateTime localDateTime(Timestamp timestamp) {
      return timestamp == null ? null : timestamp.toLocalDateTime();
   }

   private final OffsetTime offsetTime(String string) {
      if (string == null) {
         return null;
      } else {
         if (string.lastIndexOf(43) == string.length() - 3 || string.lastIndexOf(45) == string.length() - 3) {
            string = string + ":00";
         }

         return OffsetTime.parse(string);
      }
   }

   private final OffsetDateTime offsetDateTime(String string) {
      if (string == null) {
         return null;
      } else {
         Matcher m = LENIENT_OFFSET_PATTERN.matcher(string);
         if (m.find()) {
            StringBuilder sb = new StringBuilder(m.group(1));
            sb.append('T');
            sb.append(m.group(2));
            if (m.group(3) != null) {
               sb.append(m.group(4));
               sb.append(m.group(5) == null ? "0" : m.group(5));
               sb.append(m.group(6));
               sb.append(m.group(7) == null ? ":00" : m.group(7));
            } else {
               sb.append("+00:00");
            }

            return OffsetDateTime.parse(sb.toString());
         } else {
            return OffsetDateTime.parse(string);
         }
      }
   }

   public void get(BindingGetStatementContext<U> ctx) throws SQLException {
      T result = null;
      if (this.type == Blob.class) {
         result = ctx.statement().getBlob(ctx.index());
      } else if (this.type == Boolean.class) {
         result = JDBCUtils.wasNull(ctx.statement(), ctx.statement().getBoolean(ctx.index()));
      } else if (this.type == BigInteger.class) {
         BigDecimal d = ctx.statement().getBigDecimal(ctx.index());
         result = d == null ? null : d.toBigInteger();
      } else if (this.type == BigDecimal.class) {
         result = ctx.statement().getBigDecimal(ctx.index());
      } else if (this.type == Byte.class) {
         result = JDBCUtils.wasNull((CallableStatement)ctx.statement(), (Number)ctx.statement().getByte(ctx.index()));
      } else if (this.type == byte[].class) {
         result = ctx.statement().getBytes(ctx.index());
      } else if (this.type == Clob.class) {
         result = ctx.statement().getClob(ctx.index());
      } else if (this.type == Date.class) {
         result = ctx.statement().getDate(ctx.index());
      } else if (this.type == Double.class) {
         result = JDBCUtils.wasNull((CallableStatement)ctx.statement(), (Number)ctx.statement().getDouble(ctx.index()));
      } else if (this.type == Float.class) {
         result = JDBCUtils.wasNull((CallableStatement)ctx.statement(), (Number)ctx.statement().getFloat(ctx.index()));
      } else if (this.type == Integer.class) {
         result = JDBCUtils.wasNull((CallableStatement)ctx.statement(), (Number)ctx.statement().getInt(ctx.index()));
      } else if (this.type == Long.class) {
         result = JDBCUtils.wasNull((CallableStatement)ctx.statement(), (Number)ctx.statement().getLong(ctx.index()));
      } else if (this.type == Short.class) {
         result = JDBCUtils.wasNull((CallableStatement)ctx.statement(), (Number)ctx.statement().getShort(ctx.index()));
      } else if (this.type == String.class) {
         result = ctx.statement().getString(ctx.index());
      } else if (this.type == Time.class) {
         result = ctx.statement().getTime(ctx.index());
      } else if (this.type == Timestamp.class) {
         result = ctx.statement().getTimestamp(ctx.index());
      } else {
         Object object;
         String string;
         if (this.type == YearToMonth.class) {
            if (ctx.family() == SQLDialect.POSTGRES) {
               object = ctx.statement().getObject(ctx.index());
               result = object == null ? null : PostgresUtils.toYearToMonth(object);
            } else {
               string = ctx.statement().getString(ctx.index());
               result = string == null ? null : YearToMonth.valueOf(string);
            }
         } else if (this.type == DayToSecond.class) {
            if (ctx.family() == SQLDialect.POSTGRES) {
               object = ctx.statement().getObject(ctx.index());
               result = object == null ? null : PostgresUtils.toDayToSecond(object);
            } else {
               string = ctx.statement().getString(ctx.index());
               result = string == null ? null : DayToSecond.valueOf(string);
            }
         } else if (this.type == UByte.class) {
            string = ctx.statement().getString(ctx.index());
            result = string == null ? null : UByte.valueOf(string);
         } else if (this.type == UShort.class) {
            string = ctx.statement().getString(ctx.index());
            result = string == null ? null : UShort.valueOf(string);
         } else if (this.type == UInteger.class) {
            string = ctx.statement().getString(ctx.index());
            result = string == null ? null : UInteger.valueOf(string);
         } else if (this.type == ULong.class) {
            string = ctx.statement().getString(ctx.index());
            result = string == null ? null : ULong.valueOf(string);
         } else if (this.type == UUID.class) {
            switch(ctx.family()) {
            case H2:
            case POSTGRES:
               result = ctx.statement().getObject(ctx.index());
               break;
            default:
               result = Convert.convert((Object)ctx.statement().getString(ctx.index()), (Class)UUID.class);
            }
         } else if (this.type.isArray()) {
            result = convertArray(ctx.statement().getObject(ctx.index()), this.type);
         } else if (EnumType.class.isAssignableFrom(this.type)) {
            result = getEnumType(this.type, ctx.statement().getString(ctx.index()));
         } else if (Record.class.isAssignableFrom(this.type)) {
            switch(ctx.family()) {
            case POSTGRES:
               result = pgNewRecord(this.type, (Field[])null, ctx.statement().getObject(ctx.index()));
               break;
            default:
               result = ctx.statement().getObject(ctx.index(), typeMap(this.type, ctx.configuration()));
            }
         } else if (Result.class.isAssignableFrom(this.type)) {
            ResultSet nested = (ResultSet)ctx.statement().getObject(ctx.index());
            result = DSL.using(ctx.configuration()).fetch(nested);
         } else {
            result = ctx.statement().getObject(ctx.index());
         }
      }

      if (result instanceof Attachable && Tools.attachRecords(ctx.configuration())) {
         ((Attachable)result).attach(ctx.configuration());
      }

      ctx.value(this.converter.from(result));
   }

   static final Map<String, Class<?>> typeMap(Class<?> type, Configuration configuration) {
      return typeMap(type, configuration, new HashMap());
   }

   static final Map<String, Class<?>> typeMap(Class<?> type, Configuration configuration, Map<String, Class<?>> result) {
      try {
         if (UDTRecord.class.isAssignableFrom(type)) {
            result.put(Tools.getMappedUDTName(configuration, type), type);
            UDTRecord<?> r = (UDTRecord)type.newInstance();
            Field[] var5 = r.getUDT().fields();
            int var6 = var5.length;

            for(int var7 = 0; var7 < var6; ++var7) {
               Field<?> field = var5[var7];
               typeMap(field.getType(), configuration, result);
            }
         }

         return result;
      } catch (Exception var9) {
         throw new MappingException("Error while collecting type map", var9);
      }
   }

   public void get(BindingGetSQLInputContext<U> ctx) throws SQLException {
      T result = null;
      if (this.type == Blob.class) {
         result = ctx.input().readBlob();
      } else if (this.type == Boolean.class) {
         result = JDBCUtils.wasNull(ctx.input(), ctx.input().readBoolean());
      } else if (this.type == BigInteger.class) {
         BigDecimal d = ctx.input().readBigDecimal();
         result = d == null ? null : d.toBigInteger();
      } else if (this.type == BigDecimal.class) {
         result = ctx.input().readBigDecimal();
      } else if (this.type == Byte.class) {
         result = JDBCUtils.wasNull((SQLInput)ctx.input(), (Number)ctx.input().readByte());
      } else if (this.type == byte[].class) {
         if (this.isLob) {
            Blob blob = null;

            try {
               blob = ctx.input().readBlob();
               result = blob == null ? null : blob.getBytes(1L, (int)blob.length());
            } finally {
               JDBCUtils.safeFree(blob);
            }
         } else {
            result = ctx.input().readBytes();
         }
      } else if (this.type == Clob.class) {
         result = ctx.input().readClob();
      } else if (this.type == Date.class) {
         result = ctx.input().readDate();
      } else if (this.type == Double.class) {
         result = JDBCUtils.wasNull((SQLInput)ctx.input(), (Number)ctx.input().readDouble());
      } else if (this.type == Float.class) {
         result = JDBCUtils.wasNull((SQLInput)ctx.input(), (Number)ctx.input().readFloat());
      } else if (this.type == Integer.class) {
         result = JDBCUtils.wasNull((SQLInput)ctx.input(), (Number)ctx.input().readInt());
      } else if (this.type == Long.class) {
         result = JDBCUtils.wasNull((SQLInput)ctx.input(), (Number)ctx.input().readLong());
      } else if (this.type == Short.class) {
         result = JDBCUtils.wasNull((SQLInput)ctx.input(), (Number)ctx.input().readShort());
      } else if (this.type == String.class) {
         result = ctx.input().readString();
      } else if (this.type == Time.class) {
         result = ctx.input().readTime();
      } else if (this.type == Timestamp.class) {
         result = ctx.input().readTimestamp();
      } else {
         String string;
         if (this.type == YearToMonth.class) {
            string = ctx.input().readString();
            result = string == null ? null : YearToMonth.valueOf(string);
         } else if (this.type == DayToSecond.class) {
            string = ctx.input().readString();
            result = string == null ? null : DayToSecond.valueOf(string);
         } else if (this.type == UByte.class) {
            string = ctx.input().readString();
            result = string == null ? null : UByte.valueOf(string);
         } else if (this.type == UShort.class) {
            string = ctx.input().readString();
            result = string == null ? null : UShort.valueOf(string);
         } else if (this.type == UInteger.class) {
            string = ctx.input().readString();
            result = string == null ? null : UInteger.valueOf(string);
         } else if (this.type == ULong.class) {
            string = ctx.input().readString();
            result = string == null ? null : ULong.valueOf(string);
         } else if (this.type == UUID.class) {
            result = Convert.convert((Object)ctx.input().readString(), (Class)UUID.class);
         } else if (this.type.isArray()) {
            java.sql.Array array = ctx.input().readArray();
            result = array == null ? null : array.getArray();
         } else if (EnumType.class.isAssignableFrom(this.type)) {
            result = getEnumType(this.type, ctx.input().readString());
         } else if (UDTRecord.class.isAssignableFrom(this.type)) {
            result = ctx.input().readObject();
         } else {
            result = unlob(ctx.input().readObject());
         }
      }

      ctx.value(this.converter.from(result));
   }

   private static Object unlob(Object object) throws SQLException {
      if (object instanceof Blob) {
         Blob blob = (Blob)object;

         byte[] var12;
         try {
            var12 = blob.getBytes(1L, (int)blob.length());
         } finally {
            JDBCUtils.safeFree(blob);
         }

         return var12;
      } else if (object instanceof Clob) {
         Clob clob = (Clob)object;

         String var2;
         try {
            var2 = clob.getSubString(1L, (int)clob.length());
         } finally {
            JDBCUtils.safeFree(clob);
         }

         return var2;
      } else {
         return object;
      }
   }

   private static final <E extends EnumType> E getEnumType(Class<? extends E> type, String literal) {
      try {
         EnumType[] list = Tools.enums(type);
         EnumType[] var3 = list;
         int var4 = list.length;

         for(int var5 = 0; var5 < var4; ++var5) {
            EnumType e = var3[var5];
            if (e.getLiteral().equals(literal)) {
               return e;
            }
         }

         return null;
      } catch (Exception var7) {
         throw new DataTypeException("Unknown enum literal found : " + literal);
      }
   }

   private static final Object[] convertArray(Object array, Class<? extends Object[]> type) throws SQLException {
      if (array instanceof Object[]) {
         return (Object[])Convert.convert(array, type);
      } else {
         return array instanceof java.sql.Array ? convertArray((java.sql.Array)array, type) : null;
      }
   }

   private static final Object[] convertArray(java.sql.Array array, Class<? extends Object[]> type) throws SQLException {
      return array != null ? (Object[])Convert.convert(array.getArray(), type) : null;
   }

   private static final Date getDate(SQLDialect family, ResultSet rs, int index) throws SQLException {
      if (family == SQLDialect.SQLITE) {
         String date = rs.getString(index);
         return date == null ? null : new Date(parse(Date.class, date));
      } else {
         return rs.getDate(index);
      }
   }

   private static final Time getTime(SQLDialect family, ResultSet rs, int index) throws SQLException {
      if (family == SQLDialect.SQLITE) {
         String time = rs.getString(index);
         return time == null ? null : new Time(parse(Time.class, time));
      } else {
         return rs.getTime(index);
      }
   }

   private static final Timestamp getTimestamp(SQLDialect family, ResultSet rs, int index) throws SQLException {
      if (family == SQLDialect.SQLITE) {
         String timestamp = rs.getString(index);
         return timestamp == null ? null : new Timestamp(parse(Timestamp.class, timestamp));
      } else {
         return rs.getTimestamp(index);
      }
   }

   private static final long parse(Class<? extends java.util.Date> type, String date) throws SQLException {
      try {
         return Long.valueOf(date);
      } catch (NumberFormatException var3) {
         if (type == Timestamp.class) {
            return Timestamp.valueOf(date).getTime();
         } else if (type == Date.class) {
            return Date.valueOf(date.split(" ")[0]).getTime();
         } else if (type == Time.class) {
            return Time.valueOf(date).getTime();
         } else {
            throw new SQLException("Could not parse date " + date, var3);
         }
      }
   }

   private static final <T> T pgFromString(Class<T> type, String string) {
      return pgFromString(Converters.identity(type), string);
   }

   private static final <T> T pgFromString(Converter<?, T> converter, String string) {
      Class<T> type = converter.toType();
      if (string == null) {
         return null;
      } else {
         if (type != Blob.class) {
            if (type == Boolean.class) {
               return Convert.convert((Object)string, (Class)Boolean.class);
            }

            if (type == BigInteger.class) {
               return new BigInteger(string);
            }

            if (type == BigDecimal.class) {
               return new BigDecimal(string);
            }

            if (type == Byte.class) {
               return Byte.valueOf(string);
            }

            if (type == byte[].class) {
               return PostgresUtils.toBytes(string);
            }

            if (type != Clob.class) {
               if (type == Date.class) {
                  return Date.valueOf(string);
               }

               if (type == Double.class) {
                  return Double.valueOf(string);
               }

               if (type == Float.class) {
                  return Float.valueOf(string);
               }

               if (type == Integer.class) {
                  return Integer.valueOf(string);
               }

               if (type == Long.class) {
                  return Long.valueOf(string);
               }

               if (type == Short.class) {
                  return Short.valueOf(string);
               }

               if (type == String.class) {
                  return string;
               }

               if (type == Time.class) {
                  return Time.valueOf(string);
               }

               if (type == Timestamp.class) {
                  return Timestamp.valueOf(string);
               }

               if (type == UByte.class) {
                  return UByte.valueOf(string);
               }

               if (type == UShort.class) {
                  return UShort.valueOf(string);
               }

               if (type == UInteger.class) {
                  return UInteger.valueOf(string);
               }

               if (type == ULong.class) {
                  return ULong.valueOf(string);
               }

               if (type == UUID.class) {
                  return UUID.fromString(string);
               }

               if (type.isArray()) {
                  return pgNewArray(type, string);
               }

               if (EnumType.class.isAssignableFrom(type)) {
                  return getEnumType(type, string);
               }

               if (Record.class.isAssignableFrom(type)) {
                  return pgNewRecord(type, (Field[])null, string);
               }

               if (type == Object.class) {
                  return string;
               }

               return converter.from(pgFromString(converter.fromType(), string));
            }
         }

         throw new UnsupportedOperationException("Class " + type + " is not supported");
      }
   }

   static final Record pgNewRecord(Class<?> type, Field<?>[] fields, final Object object) {
      return object == null ? null : Tools.newRecord(true, type, fields).operate(new RecordOperation<Record, RuntimeException>() {
         public Record operate(Record record) {
            List<String> values = PostgresUtils.toPGObject(object.toString());
            Row row = record.fieldsRow();

            for(int i = 0; i < row.size(); ++i) {
               DefaultBinding.pgSetValue(record, row.field(i), (String)values.get(i));
            }

            return record;
         }
      });
   }

   private static final <T> T pgGetArray(Scope ctx, ResultSet rs, Class<T> type, int index) throws SQLException {
      java.sql.Array array = null;

      Object[] var5;
      try {
         array = rs.getArray(index);
         if (array == null) {
            var5 = null;
            return var5;
         }

         try {
            if (byte[][].class == type) {
               throw new ControlFlowSignal("GOTO the next array deserialisation strategy");
            }

            var5 = convertArray(array, type);
         } catch (Exception var26) {
            List<Object> result = new ArrayList();
            ResultSet arrayRs = null;

            try {
               arrayRs = array.getResultSet();

               while(arrayRs.next()) {
                  DefaultBindingGetResultSetContext<T> out = new DefaultBindingGetResultSetContext(ctx.configuration(), ctx.data(), arrayRs, 2);
                  (new DefaultBinding(Converters.identity(type.getComponentType()), false)).get((BindingGetResultSetContext)out);
                  result.add(out.value());
               }
            } catch (Exception var24) {
               Exception fatal = var24;
               String string = null;

               try {
                  string = rs.getString(index);
               } catch (SQLException var23) {
               }

               log.error("Cannot parse array", string, var24);
               Object var10 = null;
               return var10;
            } finally {
               JDBCUtils.safeClose(arrayRs);
            }

            Object[] var29 = convertArray((Object)result.toArray(), type);
            return var29;
         }
      } finally {
         JDBCUtils.safeFree(array);
      }

      return var5;
   }

   private static final Object[] pgNewArray(Class<?> type, String string) {
      if (string == null) {
         return null;
      } else {
         try {
            Class<?> component = type.getComponentType();
            List<String> values = PostgresUtils.toPGArray(string);
            if (values.isEmpty()) {
               return (Object[])((Object[])java.lang.reflect.Array.newInstance(component, 0));
            } else {
               Object[] result = (Object[])((Object[])java.lang.reflect.Array.newInstance(component, values.size()));

               for(int i = 0; i < values.size(); ++i) {
                  result[i] = pgFromString(type.getComponentType(), (String)values.get(i));
               }

               return result;
            }
         } catch (Exception var6) {
            throw new DataTypeException("Error while creating array", var6);
         }
      }
   }

   static final <T> void pgSetValue(Record record, Field<T> field, String value) {
      record.set(field, pgFromString(field.getConverter(), value));
   }

   private static final void pgRenderEnumCast(RenderContext render, Class<?> type) {
      Class<? extends EnumType> enumType = type.isArray() ? type.getComponentType() : type;
      EnumType[] enums = Tools.enums(enumType);
      if (enums != null && enums.length != 0) {
         Schema schema = enums[0].getSchema();
         if (schema != null) {
            render.sql("::");
            schema = DSL.using(render.configuration()).map(schema);
            if (schema != null && Boolean.TRUE.equals(render.configuration().settings().isRenderSchema())) {
               render.visit(schema);
               render.sql('.');
            }

            render.visit(DSL.name(enums[0].getName()));
         }

         if (type.isArray()) {
            render.sql("[]");
         }

      } else {
         throw new IllegalArgumentException("Not a valid EnumType : " + type);
      }
   }

   public String toString() {
      return "DefaultBinding [type=" + this.type + ", converter=" + this.converter + "]";
   }
}
