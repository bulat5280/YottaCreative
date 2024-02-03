package org.jooq.impl;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.sql.Connection;
import java.sql.Date;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Time;
import java.sql.Timestamp;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.OffsetDateTime;
import java.time.OffsetTime;
import java.time.temporal.Temporal;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Properties;
import java.util.UUID;
import javax.sql.DataSource;
import org.jooq.AggregateFunction;
import org.jooq.AlterIndexStep;
import org.jooq.AlterSchemaStep;
import org.jooq.AlterSequenceStep;
import org.jooq.AlterTableStep;
import org.jooq.AlterViewStep;
import org.jooq.ArrayAggOrderByStep;
import org.jooq.Attachable;
import org.jooq.Case;
import org.jooq.CaseConditionStep;
import org.jooq.CaseValueStep;
import org.jooq.Catalog;
import org.jooq.CommonTableExpression;
import org.jooq.Condition;
import org.jooq.Configuration;
import org.jooq.ConnectionProvider;
import org.jooq.ConstraintFinalStep;
import org.jooq.ConstraintForeignKeyReferencesStep1;
import org.jooq.ConstraintForeignKeyReferencesStep10;
import org.jooq.ConstraintForeignKeyReferencesStep11;
import org.jooq.ConstraintForeignKeyReferencesStep12;
import org.jooq.ConstraintForeignKeyReferencesStep13;
import org.jooq.ConstraintForeignKeyReferencesStep14;
import org.jooq.ConstraintForeignKeyReferencesStep15;
import org.jooq.ConstraintForeignKeyReferencesStep16;
import org.jooq.ConstraintForeignKeyReferencesStep17;
import org.jooq.ConstraintForeignKeyReferencesStep18;
import org.jooq.ConstraintForeignKeyReferencesStep19;
import org.jooq.ConstraintForeignKeyReferencesStep2;
import org.jooq.ConstraintForeignKeyReferencesStep20;
import org.jooq.ConstraintForeignKeyReferencesStep21;
import org.jooq.ConstraintForeignKeyReferencesStep22;
import org.jooq.ConstraintForeignKeyReferencesStep3;
import org.jooq.ConstraintForeignKeyReferencesStep4;
import org.jooq.ConstraintForeignKeyReferencesStep5;
import org.jooq.ConstraintForeignKeyReferencesStep6;
import org.jooq.ConstraintForeignKeyReferencesStep7;
import org.jooq.ConstraintForeignKeyReferencesStep8;
import org.jooq.ConstraintForeignKeyReferencesStep9;
import org.jooq.ConstraintForeignKeyReferencesStepN;
import org.jooq.ConstraintTypeStep;
import org.jooq.CreateIndexStep;
import org.jooq.CreateSchemaFinalStep;
import org.jooq.CreateSequenceFinalStep;
import org.jooq.CreateTableAsStep;
import org.jooq.CreateViewAsStep;
import org.jooq.DSLContext;
import org.jooq.DataType;
import org.jooq.DatePart;
import org.jooq.DeleteWhereStep;
import org.jooq.DropIndexOnStep;
import org.jooq.DropSchemaStep;
import org.jooq.DropSequenceFinalStep;
import org.jooq.DropTableStep;
import org.jooq.DropViewFinalStep;
import org.jooq.Field;
import org.jooq.FieldOrRow;
import org.jooq.GroupConcatOrderByStep;
import org.jooq.GroupField;
import org.jooq.InsertSetStep;
import org.jooq.InsertValuesStep1;
import org.jooq.InsertValuesStep10;
import org.jooq.InsertValuesStep11;
import org.jooq.InsertValuesStep12;
import org.jooq.InsertValuesStep13;
import org.jooq.InsertValuesStep14;
import org.jooq.InsertValuesStep15;
import org.jooq.InsertValuesStep16;
import org.jooq.InsertValuesStep17;
import org.jooq.InsertValuesStep18;
import org.jooq.InsertValuesStep19;
import org.jooq.InsertValuesStep2;
import org.jooq.InsertValuesStep20;
import org.jooq.InsertValuesStep21;
import org.jooq.InsertValuesStep22;
import org.jooq.InsertValuesStep3;
import org.jooq.InsertValuesStep4;
import org.jooq.InsertValuesStep5;
import org.jooq.InsertValuesStep6;
import org.jooq.InsertValuesStep7;
import org.jooq.InsertValuesStep8;
import org.jooq.InsertValuesStep9;
import org.jooq.InsertValuesStepN;
import org.jooq.Keyword;
import org.jooq.MergeKeyStep1;
import org.jooq.MergeKeyStep10;
import org.jooq.MergeKeyStep11;
import org.jooq.MergeKeyStep12;
import org.jooq.MergeKeyStep13;
import org.jooq.MergeKeyStep14;
import org.jooq.MergeKeyStep15;
import org.jooq.MergeKeyStep16;
import org.jooq.MergeKeyStep17;
import org.jooq.MergeKeyStep18;
import org.jooq.MergeKeyStep19;
import org.jooq.MergeKeyStep2;
import org.jooq.MergeKeyStep20;
import org.jooq.MergeKeyStep21;
import org.jooq.MergeKeyStep22;
import org.jooq.MergeKeyStep3;
import org.jooq.MergeKeyStep4;
import org.jooq.MergeKeyStep5;
import org.jooq.MergeKeyStep6;
import org.jooq.MergeKeyStep7;
import org.jooq.MergeKeyStep8;
import org.jooq.MergeKeyStep9;
import org.jooq.MergeKeyStepN;
import org.jooq.MergeUsingStep;
import org.jooq.Name;
import org.jooq.Operator;
import org.jooq.OrderedAggregateFunction;
import org.jooq.OrderedAggregateFunctionOfDeferredType;
import org.jooq.Param;
import org.jooq.PlainSQL;
import org.jooq.QuantifiedSelect;
import org.jooq.Queries;
import org.jooq.Query;
import org.jooq.QueryPart;
import org.jooq.Record;
import org.jooq.Record1;
import org.jooq.Record10;
import org.jooq.Record11;
import org.jooq.Record12;
import org.jooq.Record13;
import org.jooq.Record14;
import org.jooq.Record15;
import org.jooq.Record16;
import org.jooq.Record17;
import org.jooq.Record18;
import org.jooq.Record19;
import org.jooq.Record2;
import org.jooq.Record20;
import org.jooq.Record21;
import org.jooq.Record22;
import org.jooq.Record3;
import org.jooq.Record4;
import org.jooq.Record5;
import org.jooq.Record6;
import org.jooq.Record7;
import org.jooq.Record8;
import org.jooq.Record9;
import org.jooq.RecordType;
import org.jooq.Result;
import org.jooq.ResultQuery;
import org.jooq.Row1;
import org.jooq.Row10;
import org.jooq.Row11;
import org.jooq.Row12;
import org.jooq.Row13;
import org.jooq.Row14;
import org.jooq.Row15;
import org.jooq.Row16;
import org.jooq.Row17;
import org.jooq.Row18;
import org.jooq.Row19;
import org.jooq.Row2;
import org.jooq.Row20;
import org.jooq.Row21;
import org.jooq.Row22;
import org.jooq.Row3;
import org.jooq.Row4;
import org.jooq.Row5;
import org.jooq.Row6;
import org.jooq.Row7;
import org.jooq.Row8;
import org.jooq.Row9;
import org.jooq.RowN;
import org.jooq.SQL;
import org.jooq.SQLDialect;
import org.jooq.Schema;
import org.jooq.Select;
import org.jooq.SelectField;
import org.jooq.SelectSelectStep;
import org.jooq.SelectWhereStep;
import org.jooq.Sequence;
import org.jooq.SortField;
import org.jooq.Support;
import org.jooq.Table;
import org.jooq.TableLike;
import org.jooq.TruncateIdentityStep;
import org.jooq.UDTRecord;
import org.jooq.UpdateSetFirstStep;
import org.jooq.WindowIgnoreNullsStep;
import org.jooq.WindowOverStep;
import org.jooq.WindowSpecificationFinalStep;
import org.jooq.WindowSpecificationOrderByStep;
import org.jooq.WindowSpecificationRowsAndStep;
import org.jooq.WindowSpecificationRowsStep;
import org.jooq.WithAsStep;
import org.jooq.WithAsStep1;
import org.jooq.WithAsStep10;
import org.jooq.WithAsStep11;
import org.jooq.WithAsStep12;
import org.jooq.WithAsStep13;
import org.jooq.WithAsStep14;
import org.jooq.WithAsStep15;
import org.jooq.WithAsStep16;
import org.jooq.WithAsStep17;
import org.jooq.WithAsStep18;
import org.jooq.WithAsStep19;
import org.jooq.WithAsStep2;
import org.jooq.WithAsStep20;
import org.jooq.WithAsStep21;
import org.jooq.WithAsStep22;
import org.jooq.WithAsStep3;
import org.jooq.WithAsStep4;
import org.jooq.WithAsStep5;
import org.jooq.WithAsStep6;
import org.jooq.WithAsStep7;
import org.jooq.WithAsStep8;
import org.jooq.WithAsStep9;
import org.jooq.WithStep;
import org.jooq.conf.Settings;
import org.jooq.exception.SQLDialectNotSupportedException;
import org.jooq.tools.Convert;
import org.jooq.tools.jdbc.JDBCUtils;
import org.jooq.types.DayToSecond;
import org.jooq.types.UByte;
import org.jooq.types.UInteger;
import org.jooq.types.ULong;
import org.jooq.types.UShort;

public class DSL {
   public static DSLContext using(SQLDialect dialect) {
      return new DefaultDSLContext(dialect, (Settings)null);
   }

   public static DSLContext using(SQLDialect dialect, Settings settings) {
      return new DefaultDSLContext(dialect, settings);
   }

   public static DSLContext using(String url) {
      try {
         Connection connection = DriverManager.getConnection(url);
         return using((ConnectionProvider)(new DefaultConnectionProvider(connection, true)), (SQLDialect)JDBCUtils.dialect(connection));
      } catch (SQLException var2) {
         throw Tools.translate("Error when initialising Connection", var2);
      }
   }

   public static DSLContext using(String url, String username, String password) {
      try {
         Connection connection = DriverManager.getConnection(url, username, password);
         return using((ConnectionProvider)(new DefaultConnectionProvider(connection, true)), (SQLDialect)JDBCUtils.dialect(connection));
      } catch (SQLException var4) {
         throw Tools.translate("Error when initialising Connection", var4);
      }
   }

   public static DSLContext using(String url, Properties properties) {
      try {
         Connection connection = DriverManager.getConnection(url, properties);
         return using((ConnectionProvider)(new DefaultConnectionProvider(connection, true)), (SQLDialect)JDBCUtils.dialect(connection));
      } catch (SQLException var3) {
         throw Tools.translate("Error when initialising Connection", var3);
      }
   }

   public static DSLContext using(Connection connection) {
      return new DefaultDSLContext(connection, JDBCUtils.dialect(connection), (Settings)null);
   }

   public static DSLContext using(Connection connection, SQLDialect dialect) {
      return new DefaultDSLContext(connection, dialect, (Settings)null);
   }

   public static DSLContext using(Connection connection, Settings settings) {
      return new DefaultDSLContext(connection, JDBCUtils.dialect(connection), settings);
   }

   public static DSLContext using(Connection connection, SQLDialect dialect, Settings settings) {
      return new DefaultDSLContext(connection, dialect, settings);
   }

   public static DSLContext using(DataSource datasource, SQLDialect dialect) {
      return new DefaultDSLContext(datasource, dialect);
   }

   public static DSLContext using(DataSource datasource, SQLDialect dialect, Settings settings) {
      return new DefaultDSLContext(datasource, dialect, settings);
   }

   public static DSLContext using(ConnectionProvider connectionProvider, SQLDialect dialect) {
      return new DefaultDSLContext(connectionProvider, dialect);
   }

   public static DSLContext using(ConnectionProvider connectionProvider, SQLDialect dialect, Settings settings) {
      return new DefaultDSLContext(connectionProvider, dialect, settings);
   }

   public static DSLContext using(Configuration configuration) {
      return new DefaultDSLContext(configuration);
   }

   @Support({SQLDialect.FIREBIRD, SQLDialect.HSQLDB, SQLDialect.POSTGRES})
   public static WithAsStep with(String alias) {
      return (new WithImpl((Configuration)null, false)).with(alias);
   }

   @Support({SQLDialect.FIREBIRD, SQLDialect.HSQLDB, SQLDialect.POSTGRES})
   public static WithAsStep with(String alias, String... fieldAliases) {
      return (new WithImpl((Configuration)null, false)).with(alias, fieldAliases);
   }

   @Support({SQLDialect.FIREBIRD, SQLDialect.HSQLDB, SQLDialect.POSTGRES})
   public static WithAsStep with(String alias, java.util.function.Function<? super Field<?>, ? extends String> fieldNameFunction) {
      return (new WithImpl((Configuration)null, false)).with(alias, fieldNameFunction);
   }

   @Support({SQLDialect.FIREBIRD, SQLDialect.HSQLDB, SQLDialect.POSTGRES})
   public static WithAsStep1 with(String alias, String fieldAlias1) {
      return (new WithImpl((Configuration)null, false)).with(alias, fieldAlias1);
   }

   @Support({SQLDialect.FIREBIRD, SQLDialect.HSQLDB, SQLDialect.POSTGRES})
   public static WithAsStep2 with(String alias, String fieldAlias1, String fieldAlias2) {
      return (new WithImpl((Configuration)null, false)).with(alias, fieldAlias1, fieldAlias2);
   }

   @Support({SQLDialect.FIREBIRD, SQLDialect.HSQLDB, SQLDialect.POSTGRES})
   public static WithAsStep3 with(String alias, String fieldAlias1, String fieldAlias2, String fieldAlias3) {
      return (new WithImpl((Configuration)null, false)).with(alias, fieldAlias1, fieldAlias2, fieldAlias3);
   }

   @Support({SQLDialect.FIREBIRD, SQLDialect.HSQLDB, SQLDialect.POSTGRES})
   public static WithAsStep4 with(String alias, String fieldAlias1, String fieldAlias2, String fieldAlias3, String fieldAlias4) {
      return (new WithImpl((Configuration)null, false)).with(alias, fieldAlias1, fieldAlias2, fieldAlias3, fieldAlias4);
   }

   @Support({SQLDialect.FIREBIRD, SQLDialect.HSQLDB, SQLDialect.POSTGRES})
   public static WithAsStep5 with(String alias, String fieldAlias1, String fieldAlias2, String fieldAlias3, String fieldAlias4, String fieldAlias5) {
      return (new WithImpl((Configuration)null, false)).with(alias, fieldAlias1, fieldAlias2, fieldAlias3, fieldAlias4, fieldAlias5);
   }

   @Support({SQLDialect.FIREBIRD, SQLDialect.HSQLDB, SQLDialect.POSTGRES})
   public static WithAsStep6 with(String alias, String fieldAlias1, String fieldAlias2, String fieldAlias3, String fieldAlias4, String fieldAlias5, String fieldAlias6) {
      return (new WithImpl((Configuration)null, false)).with(alias, fieldAlias1, fieldAlias2, fieldAlias3, fieldAlias4, fieldAlias5, fieldAlias6);
   }

   @Support({SQLDialect.FIREBIRD, SQLDialect.HSQLDB, SQLDialect.POSTGRES})
   public static WithAsStep7 with(String alias, String fieldAlias1, String fieldAlias2, String fieldAlias3, String fieldAlias4, String fieldAlias5, String fieldAlias6, String fieldAlias7) {
      return (new WithImpl((Configuration)null, false)).with(alias, fieldAlias1, fieldAlias2, fieldAlias3, fieldAlias4, fieldAlias5, fieldAlias6, fieldAlias7);
   }

   @Support({SQLDialect.FIREBIRD, SQLDialect.HSQLDB, SQLDialect.POSTGRES})
   public static WithAsStep8 with(String alias, String fieldAlias1, String fieldAlias2, String fieldAlias3, String fieldAlias4, String fieldAlias5, String fieldAlias6, String fieldAlias7, String fieldAlias8) {
      return (new WithImpl((Configuration)null, false)).with(alias, fieldAlias1, fieldAlias2, fieldAlias3, fieldAlias4, fieldAlias5, fieldAlias6, fieldAlias7, fieldAlias8);
   }

   @Support({SQLDialect.FIREBIRD, SQLDialect.HSQLDB, SQLDialect.POSTGRES})
   public static WithAsStep9 with(String alias, String fieldAlias1, String fieldAlias2, String fieldAlias3, String fieldAlias4, String fieldAlias5, String fieldAlias6, String fieldAlias7, String fieldAlias8, String fieldAlias9) {
      return (new WithImpl((Configuration)null, false)).with(alias, fieldAlias1, fieldAlias2, fieldAlias3, fieldAlias4, fieldAlias5, fieldAlias6, fieldAlias7, fieldAlias8, fieldAlias9);
   }

   @Support({SQLDialect.FIREBIRD, SQLDialect.HSQLDB, SQLDialect.POSTGRES})
   public static WithAsStep10 with(String alias, String fieldAlias1, String fieldAlias2, String fieldAlias3, String fieldAlias4, String fieldAlias5, String fieldAlias6, String fieldAlias7, String fieldAlias8, String fieldAlias9, String fieldAlias10) {
      return (new WithImpl((Configuration)null, false)).with(alias, fieldAlias1, fieldAlias2, fieldAlias3, fieldAlias4, fieldAlias5, fieldAlias6, fieldAlias7, fieldAlias8, fieldAlias9, fieldAlias10);
   }

   @Support({SQLDialect.FIREBIRD, SQLDialect.HSQLDB, SQLDialect.POSTGRES})
   public static WithAsStep11 with(String alias, String fieldAlias1, String fieldAlias2, String fieldAlias3, String fieldAlias4, String fieldAlias5, String fieldAlias6, String fieldAlias7, String fieldAlias8, String fieldAlias9, String fieldAlias10, String fieldAlias11) {
      return (new WithImpl((Configuration)null, false)).with(alias, fieldAlias1, fieldAlias2, fieldAlias3, fieldAlias4, fieldAlias5, fieldAlias6, fieldAlias7, fieldAlias8, fieldAlias9, fieldAlias10, fieldAlias11);
   }

   @Support({SQLDialect.FIREBIRD, SQLDialect.HSQLDB, SQLDialect.POSTGRES})
   public static WithAsStep12 with(String alias, String fieldAlias1, String fieldAlias2, String fieldAlias3, String fieldAlias4, String fieldAlias5, String fieldAlias6, String fieldAlias7, String fieldAlias8, String fieldAlias9, String fieldAlias10, String fieldAlias11, String fieldAlias12) {
      return (new WithImpl((Configuration)null, false)).with(alias, fieldAlias1, fieldAlias2, fieldAlias3, fieldAlias4, fieldAlias5, fieldAlias6, fieldAlias7, fieldAlias8, fieldAlias9, fieldAlias10, fieldAlias11, fieldAlias12);
   }

   @Support({SQLDialect.FIREBIRD, SQLDialect.HSQLDB, SQLDialect.POSTGRES})
   public static WithAsStep13 with(String alias, String fieldAlias1, String fieldAlias2, String fieldAlias3, String fieldAlias4, String fieldAlias5, String fieldAlias6, String fieldAlias7, String fieldAlias8, String fieldAlias9, String fieldAlias10, String fieldAlias11, String fieldAlias12, String fieldAlias13) {
      return (new WithImpl((Configuration)null, false)).with(alias, fieldAlias1, fieldAlias2, fieldAlias3, fieldAlias4, fieldAlias5, fieldAlias6, fieldAlias7, fieldAlias8, fieldAlias9, fieldAlias10, fieldAlias11, fieldAlias12, fieldAlias13);
   }

   @Support({SQLDialect.FIREBIRD, SQLDialect.HSQLDB, SQLDialect.POSTGRES})
   public static WithAsStep14 with(String alias, String fieldAlias1, String fieldAlias2, String fieldAlias3, String fieldAlias4, String fieldAlias5, String fieldAlias6, String fieldAlias7, String fieldAlias8, String fieldAlias9, String fieldAlias10, String fieldAlias11, String fieldAlias12, String fieldAlias13, String fieldAlias14) {
      return (new WithImpl((Configuration)null, false)).with(alias, fieldAlias1, fieldAlias2, fieldAlias3, fieldAlias4, fieldAlias5, fieldAlias6, fieldAlias7, fieldAlias8, fieldAlias9, fieldAlias10, fieldAlias11, fieldAlias12, fieldAlias13, fieldAlias14);
   }

   @Support({SQLDialect.FIREBIRD, SQLDialect.HSQLDB, SQLDialect.POSTGRES})
   public static WithAsStep15 with(String alias, String fieldAlias1, String fieldAlias2, String fieldAlias3, String fieldAlias4, String fieldAlias5, String fieldAlias6, String fieldAlias7, String fieldAlias8, String fieldAlias9, String fieldAlias10, String fieldAlias11, String fieldAlias12, String fieldAlias13, String fieldAlias14, String fieldAlias15) {
      return (new WithImpl((Configuration)null, false)).with(alias, fieldAlias1, fieldAlias2, fieldAlias3, fieldAlias4, fieldAlias5, fieldAlias6, fieldAlias7, fieldAlias8, fieldAlias9, fieldAlias10, fieldAlias11, fieldAlias12, fieldAlias13, fieldAlias14, fieldAlias15);
   }

   @Support({SQLDialect.FIREBIRD, SQLDialect.HSQLDB, SQLDialect.POSTGRES})
   public static WithAsStep16 with(String alias, String fieldAlias1, String fieldAlias2, String fieldAlias3, String fieldAlias4, String fieldAlias5, String fieldAlias6, String fieldAlias7, String fieldAlias8, String fieldAlias9, String fieldAlias10, String fieldAlias11, String fieldAlias12, String fieldAlias13, String fieldAlias14, String fieldAlias15, String fieldAlias16) {
      return (new WithImpl((Configuration)null, false)).with(alias, fieldAlias1, fieldAlias2, fieldAlias3, fieldAlias4, fieldAlias5, fieldAlias6, fieldAlias7, fieldAlias8, fieldAlias9, fieldAlias10, fieldAlias11, fieldAlias12, fieldAlias13, fieldAlias14, fieldAlias15, fieldAlias16);
   }

   @Support({SQLDialect.FIREBIRD, SQLDialect.HSQLDB, SQLDialect.POSTGRES})
   public static WithAsStep17 with(String alias, String fieldAlias1, String fieldAlias2, String fieldAlias3, String fieldAlias4, String fieldAlias5, String fieldAlias6, String fieldAlias7, String fieldAlias8, String fieldAlias9, String fieldAlias10, String fieldAlias11, String fieldAlias12, String fieldAlias13, String fieldAlias14, String fieldAlias15, String fieldAlias16, String fieldAlias17) {
      return (new WithImpl((Configuration)null, false)).with(alias, fieldAlias1, fieldAlias2, fieldAlias3, fieldAlias4, fieldAlias5, fieldAlias6, fieldAlias7, fieldAlias8, fieldAlias9, fieldAlias10, fieldAlias11, fieldAlias12, fieldAlias13, fieldAlias14, fieldAlias15, fieldAlias16, fieldAlias17);
   }

   @Support({SQLDialect.FIREBIRD, SQLDialect.HSQLDB, SQLDialect.POSTGRES})
   public static WithAsStep18 with(String alias, String fieldAlias1, String fieldAlias2, String fieldAlias3, String fieldAlias4, String fieldAlias5, String fieldAlias6, String fieldAlias7, String fieldAlias8, String fieldAlias9, String fieldAlias10, String fieldAlias11, String fieldAlias12, String fieldAlias13, String fieldAlias14, String fieldAlias15, String fieldAlias16, String fieldAlias17, String fieldAlias18) {
      return (new WithImpl((Configuration)null, false)).with(alias, fieldAlias1, fieldAlias2, fieldAlias3, fieldAlias4, fieldAlias5, fieldAlias6, fieldAlias7, fieldAlias8, fieldAlias9, fieldAlias10, fieldAlias11, fieldAlias12, fieldAlias13, fieldAlias14, fieldAlias15, fieldAlias16, fieldAlias17, fieldAlias18);
   }

   @Support({SQLDialect.FIREBIRD, SQLDialect.HSQLDB, SQLDialect.POSTGRES})
   public static WithAsStep19 with(String alias, String fieldAlias1, String fieldAlias2, String fieldAlias3, String fieldAlias4, String fieldAlias5, String fieldAlias6, String fieldAlias7, String fieldAlias8, String fieldAlias9, String fieldAlias10, String fieldAlias11, String fieldAlias12, String fieldAlias13, String fieldAlias14, String fieldAlias15, String fieldAlias16, String fieldAlias17, String fieldAlias18, String fieldAlias19) {
      return (new WithImpl((Configuration)null, false)).with(alias, fieldAlias1, fieldAlias2, fieldAlias3, fieldAlias4, fieldAlias5, fieldAlias6, fieldAlias7, fieldAlias8, fieldAlias9, fieldAlias10, fieldAlias11, fieldAlias12, fieldAlias13, fieldAlias14, fieldAlias15, fieldAlias16, fieldAlias17, fieldAlias18, fieldAlias19);
   }

   @Support({SQLDialect.FIREBIRD, SQLDialect.HSQLDB, SQLDialect.POSTGRES})
   public static WithAsStep20 with(String alias, String fieldAlias1, String fieldAlias2, String fieldAlias3, String fieldAlias4, String fieldAlias5, String fieldAlias6, String fieldAlias7, String fieldAlias8, String fieldAlias9, String fieldAlias10, String fieldAlias11, String fieldAlias12, String fieldAlias13, String fieldAlias14, String fieldAlias15, String fieldAlias16, String fieldAlias17, String fieldAlias18, String fieldAlias19, String fieldAlias20) {
      return (new WithImpl((Configuration)null, false)).with(alias, fieldAlias1, fieldAlias2, fieldAlias3, fieldAlias4, fieldAlias5, fieldAlias6, fieldAlias7, fieldAlias8, fieldAlias9, fieldAlias10, fieldAlias11, fieldAlias12, fieldAlias13, fieldAlias14, fieldAlias15, fieldAlias16, fieldAlias17, fieldAlias18, fieldAlias19, fieldAlias20);
   }

   @Support({SQLDialect.FIREBIRD, SQLDialect.HSQLDB, SQLDialect.POSTGRES})
   public static WithAsStep21 with(String alias, String fieldAlias1, String fieldAlias2, String fieldAlias3, String fieldAlias4, String fieldAlias5, String fieldAlias6, String fieldAlias7, String fieldAlias8, String fieldAlias9, String fieldAlias10, String fieldAlias11, String fieldAlias12, String fieldAlias13, String fieldAlias14, String fieldAlias15, String fieldAlias16, String fieldAlias17, String fieldAlias18, String fieldAlias19, String fieldAlias20, String fieldAlias21) {
      return (new WithImpl((Configuration)null, false)).with(alias, fieldAlias1, fieldAlias2, fieldAlias3, fieldAlias4, fieldAlias5, fieldAlias6, fieldAlias7, fieldAlias8, fieldAlias9, fieldAlias10, fieldAlias11, fieldAlias12, fieldAlias13, fieldAlias14, fieldAlias15, fieldAlias16, fieldAlias17, fieldAlias18, fieldAlias19, fieldAlias20, fieldAlias21);
   }

   @Support({SQLDialect.FIREBIRD, SQLDialect.HSQLDB, SQLDialect.POSTGRES})
   public static WithAsStep22 with(String alias, String fieldAlias1, String fieldAlias2, String fieldAlias3, String fieldAlias4, String fieldAlias5, String fieldAlias6, String fieldAlias7, String fieldAlias8, String fieldAlias9, String fieldAlias10, String fieldAlias11, String fieldAlias12, String fieldAlias13, String fieldAlias14, String fieldAlias15, String fieldAlias16, String fieldAlias17, String fieldAlias18, String fieldAlias19, String fieldAlias20, String fieldAlias21, String fieldAlias22) {
      return (new WithImpl((Configuration)null, false)).with(alias, fieldAlias1, fieldAlias2, fieldAlias3, fieldAlias4, fieldAlias5, fieldAlias6, fieldAlias7, fieldAlias8, fieldAlias9, fieldAlias10, fieldAlias11, fieldAlias12, fieldAlias13, fieldAlias14, fieldAlias15, fieldAlias16, fieldAlias17, fieldAlias18, fieldAlias19, fieldAlias20, fieldAlias21, fieldAlias22);
   }

   @Support({SQLDialect.FIREBIRD, SQLDialect.HSQLDB, SQLDialect.POSTGRES})
   public static WithStep with(CommonTableExpression<?>... tables) {
      return (new WithImpl((Configuration)null, false)).with(tables);
   }

   @Support({SQLDialect.FIREBIRD, SQLDialect.H2, SQLDialect.HSQLDB, SQLDialect.POSTGRES})
   public static WithAsStep withRecursive(String alias) {
      return (new WithImpl((Configuration)null, true)).with(alias);
   }

   @Support({SQLDialect.FIREBIRD, SQLDialect.H2, SQLDialect.HSQLDB, SQLDialect.POSTGRES})
   public static WithAsStep withRecursive(String alias, String... fieldAliases) {
      return (new WithImpl((Configuration)null, true)).with(alias, fieldAliases);
   }

   @Support({SQLDialect.FIREBIRD, SQLDialect.H2, SQLDialect.HSQLDB, SQLDialect.POSTGRES})
   public static WithAsStep withRecursive(String alias, java.util.function.Function<? super Field<?>, ? extends String> fieldNameFunction) {
      return (new WithImpl((Configuration)null, true)).with(alias, fieldNameFunction);
   }

   @Support({SQLDialect.FIREBIRD, SQLDialect.H2, SQLDialect.HSQLDB, SQLDialect.POSTGRES})
   public static WithAsStep1 withRecursive(String alias, String fieldAlias1) {
      return (new WithImpl((Configuration)null, true)).with(alias, fieldAlias1);
   }

   @Support({SQLDialect.FIREBIRD, SQLDialect.H2, SQLDialect.HSQLDB, SQLDialect.POSTGRES})
   public static WithAsStep2 withRecursive(String alias, String fieldAlias1, String fieldAlias2) {
      return (new WithImpl((Configuration)null, true)).with(alias, fieldAlias1, fieldAlias2);
   }

   @Support({SQLDialect.FIREBIRD, SQLDialect.H2, SQLDialect.HSQLDB, SQLDialect.POSTGRES})
   public static WithAsStep3 withRecursive(String alias, String fieldAlias1, String fieldAlias2, String fieldAlias3) {
      return (new WithImpl((Configuration)null, true)).with(alias, fieldAlias1, fieldAlias2, fieldAlias3);
   }

   @Support({SQLDialect.FIREBIRD, SQLDialect.H2, SQLDialect.HSQLDB, SQLDialect.POSTGRES})
   public static WithAsStep4 withRecursive(String alias, String fieldAlias1, String fieldAlias2, String fieldAlias3, String fieldAlias4) {
      return (new WithImpl((Configuration)null, true)).with(alias, fieldAlias1, fieldAlias2, fieldAlias3, fieldAlias4);
   }

   @Support({SQLDialect.FIREBIRD, SQLDialect.H2, SQLDialect.HSQLDB, SQLDialect.POSTGRES})
   public static WithAsStep5 withRecursive(String alias, String fieldAlias1, String fieldAlias2, String fieldAlias3, String fieldAlias4, String fieldAlias5) {
      return (new WithImpl((Configuration)null, true)).with(alias, fieldAlias1, fieldAlias2, fieldAlias3, fieldAlias4, fieldAlias5);
   }

   @Support({SQLDialect.FIREBIRD, SQLDialect.H2, SQLDialect.HSQLDB, SQLDialect.POSTGRES})
   public static WithAsStep6 withRecursive(String alias, String fieldAlias1, String fieldAlias2, String fieldAlias3, String fieldAlias4, String fieldAlias5, String fieldAlias6) {
      return (new WithImpl((Configuration)null, true)).with(alias, fieldAlias1, fieldAlias2, fieldAlias3, fieldAlias4, fieldAlias5, fieldAlias6);
   }

   @Support({SQLDialect.FIREBIRD, SQLDialect.H2, SQLDialect.HSQLDB, SQLDialect.POSTGRES})
   public static WithAsStep7 withRecursive(String alias, String fieldAlias1, String fieldAlias2, String fieldAlias3, String fieldAlias4, String fieldAlias5, String fieldAlias6, String fieldAlias7) {
      return (new WithImpl((Configuration)null, true)).with(alias, fieldAlias1, fieldAlias2, fieldAlias3, fieldAlias4, fieldAlias5, fieldAlias6, fieldAlias7);
   }

   @Support({SQLDialect.FIREBIRD, SQLDialect.H2, SQLDialect.HSQLDB, SQLDialect.POSTGRES})
   public static WithAsStep8 withRecursive(String alias, String fieldAlias1, String fieldAlias2, String fieldAlias3, String fieldAlias4, String fieldAlias5, String fieldAlias6, String fieldAlias7, String fieldAlias8) {
      return (new WithImpl((Configuration)null, true)).with(alias, fieldAlias1, fieldAlias2, fieldAlias3, fieldAlias4, fieldAlias5, fieldAlias6, fieldAlias7, fieldAlias8);
   }

   @Support({SQLDialect.FIREBIRD, SQLDialect.H2, SQLDialect.HSQLDB, SQLDialect.POSTGRES})
   public static WithAsStep9 withRecursive(String alias, String fieldAlias1, String fieldAlias2, String fieldAlias3, String fieldAlias4, String fieldAlias5, String fieldAlias6, String fieldAlias7, String fieldAlias8, String fieldAlias9) {
      return (new WithImpl((Configuration)null, true)).with(alias, fieldAlias1, fieldAlias2, fieldAlias3, fieldAlias4, fieldAlias5, fieldAlias6, fieldAlias7, fieldAlias8, fieldAlias9);
   }

   @Support({SQLDialect.FIREBIRD, SQLDialect.H2, SQLDialect.HSQLDB, SQLDialect.POSTGRES})
   public static WithAsStep10 withRecursive(String alias, String fieldAlias1, String fieldAlias2, String fieldAlias3, String fieldAlias4, String fieldAlias5, String fieldAlias6, String fieldAlias7, String fieldAlias8, String fieldAlias9, String fieldAlias10) {
      return (new WithImpl((Configuration)null, true)).with(alias, fieldAlias1, fieldAlias2, fieldAlias3, fieldAlias4, fieldAlias5, fieldAlias6, fieldAlias7, fieldAlias8, fieldAlias9, fieldAlias10);
   }

   @Support({SQLDialect.FIREBIRD, SQLDialect.H2, SQLDialect.HSQLDB, SQLDialect.POSTGRES})
   public static WithAsStep11 withRecursive(String alias, String fieldAlias1, String fieldAlias2, String fieldAlias3, String fieldAlias4, String fieldAlias5, String fieldAlias6, String fieldAlias7, String fieldAlias8, String fieldAlias9, String fieldAlias10, String fieldAlias11) {
      return (new WithImpl((Configuration)null, true)).with(alias, fieldAlias1, fieldAlias2, fieldAlias3, fieldAlias4, fieldAlias5, fieldAlias6, fieldAlias7, fieldAlias8, fieldAlias9, fieldAlias10, fieldAlias11);
   }

   @Support({SQLDialect.FIREBIRD, SQLDialect.H2, SQLDialect.HSQLDB, SQLDialect.POSTGRES})
   public static WithAsStep12 withRecursive(String alias, String fieldAlias1, String fieldAlias2, String fieldAlias3, String fieldAlias4, String fieldAlias5, String fieldAlias6, String fieldAlias7, String fieldAlias8, String fieldAlias9, String fieldAlias10, String fieldAlias11, String fieldAlias12) {
      return (new WithImpl((Configuration)null, true)).with(alias, fieldAlias1, fieldAlias2, fieldAlias3, fieldAlias4, fieldAlias5, fieldAlias6, fieldAlias7, fieldAlias8, fieldAlias9, fieldAlias10, fieldAlias11, fieldAlias12);
   }

   @Support({SQLDialect.FIREBIRD, SQLDialect.H2, SQLDialect.HSQLDB, SQLDialect.POSTGRES})
   public static WithAsStep13 withRecursive(String alias, String fieldAlias1, String fieldAlias2, String fieldAlias3, String fieldAlias4, String fieldAlias5, String fieldAlias6, String fieldAlias7, String fieldAlias8, String fieldAlias9, String fieldAlias10, String fieldAlias11, String fieldAlias12, String fieldAlias13) {
      return (new WithImpl((Configuration)null, true)).with(alias, fieldAlias1, fieldAlias2, fieldAlias3, fieldAlias4, fieldAlias5, fieldAlias6, fieldAlias7, fieldAlias8, fieldAlias9, fieldAlias10, fieldAlias11, fieldAlias12, fieldAlias13);
   }

   @Support({SQLDialect.FIREBIRD, SQLDialect.H2, SQLDialect.HSQLDB, SQLDialect.POSTGRES})
   public static WithAsStep14 withRecursive(String alias, String fieldAlias1, String fieldAlias2, String fieldAlias3, String fieldAlias4, String fieldAlias5, String fieldAlias6, String fieldAlias7, String fieldAlias8, String fieldAlias9, String fieldAlias10, String fieldAlias11, String fieldAlias12, String fieldAlias13, String fieldAlias14) {
      return (new WithImpl((Configuration)null, true)).with(alias, fieldAlias1, fieldAlias2, fieldAlias3, fieldAlias4, fieldAlias5, fieldAlias6, fieldAlias7, fieldAlias8, fieldAlias9, fieldAlias10, fieldAlias11, fieldAlias12, fieldAlias13, fieldAlias14);
   }

   @Support({SQLDialect.FIREBIRD, SQLDialect.H2, SQLDialect.HSQLDB, SQLDialect.POSTGRES})
   public static WithAsStep15 withRecursive(String alias, String fieldAlias1, String fieldAlias2, String fieldAlias3, String fieldAlias4, String fieldAlias5, String fieldAlias6, String fieldAlias7, String fieldAlias8, String fieldAlias9, String fieldAlias10, String fieldAlias11, String fieldAlias12, String fieldAlias13, String fieldAlias14, String fieldAlias15) {
      return (new WithImpl((Configuration)null, true)).with(alias, fieldAlias1, fieldAlias2, fieldAlias3, fieldAlias4, fieldAlias5, fieldAlias6, fieldAlias7, fieldAlias8, fieldAlias9, fieldAlias10, fieldAlias11, fieldAlias12, fieldAlias13, fieldAlias14, fieldAlias15);
   }

   @Support({SQLDialect.FIREBIRD, SQLDialect.H2, SQLDialect.HSQLDB, SQLDialect.POSTGRES})
   public static WithAsStep16 withRecursive(String alias, String fieldAlias1, String fieldAlias2, String fieldAlias3, String fieldAlias4, String fieldAlias5, String fieldAlias6, String fieldAlias7, String fieldAlias8, String fieldAlias9, String fieldAlias10, String fieldAlias11, String fieldAlias12, String fieldAlias13, String fieldAlias14, String fieldAlias15, String fieldAlias16) {
      return (new WithImpl((Configuration)null, true)).with(alias, fieldAlias1, fieldAlias2, fieldAlias3, fieldAlias4, fieldAlias5, fieldAlias6, fieldAlias7, fieldAlias8, fieldAlias9, fieldAlias10, fieldAlias11, fieldAlias12, fieldAlias13, fieldAlias14, fieldAlias15, fieldAlias16);
   }

   @Support({SQLDialect.FIREBIRD, SQLDialect.H2, SQLDialect.HSQLDB, SQLDialect.POSTGRES})
   public static WithAsStep17 withRecursive(String alias, String fieldAlias1, String fieldAlias2, String fieldAlias3, String fieldAlias4, String fieldAlias5, String fieldAlias6, String fieldAlias7, String fieldAlias8, String fieldAlias9, String fieldAlias10, String fieldAlias11, String fieldAlias12, String fieldAlias13, String fieldAlias14, String fieldAlias15, String fieldAlias16, String fieldAlias17) {
      return (new WithImpl((Configuration)null, true)).with(alias, fieldAlias1, fieldAlias2, fieldAlias3, fieldAlias4, fieldAlias5, fieldAlias6, fieldAlias7, fieldAlias8, fieldAlias9, fieldAlias10, fieldAlias11, fieldAlias12, fieldAlias13, fieldAlias14, fieldAlias15, fieldAlias16, fieldAlias17);
   }

   @Support({SQLDialect.FIREBIRD, SQLDialect.H2, SQLDialect.HSQLDB, SQLDialect.POSTGRES})
   public static WithAsStep18 withRecursive(String alias, String fieldAlias1, String fieldAlias2, String fieldAlias3, String fieldAlias4, String fieldAlias5, String fieldAlias6, String fieldAlias7, String fieldAlias8, String fieldAlias9, String fieldAlias10, String fieldAlias11, String fieldAlias12, String fieldAlias13, String fieldAlias14, String fieldAlias15, String fieldAlias16, String fieldAlias17, String fieldAlias18) {
      return (new WithImpl((Configuration)null, true)).with(alias, fieldAlias1, fieldAlias2, fieldAlias3, fieldAlias4, fieldAlias5, fieldAlias6, fieldAlias7, fieldAlias8, fieldAlias9, fieldAlias10, fieldAlias11, fieldAlias12, fieldAlias13, fieldAlias14, fieldAlias15, fieldAlias16, fieldAlias17, fieldAlias18);
   }

   @Support({SQLDialect.FIREBIRD, SQLDialect.H2, SQLDialect.HSQLDB, SQLDialect.POSTGRES})
   public static WithAsStep19 withRecursive(String alias, String fieldAlias1, String fieldAlias2, String fieldAlias3, String fieldAlias4, String fieldAlias5, String fieldAlias6, String fieldAlias7, String fieldAlias8, String fieldAlias9, String fieldAlias10, String fieldAlias11, String fieldAlias12, String fieldAlias13, String fieldAlias14, String fieldAlias15, String fieldAlias16, String fieldAlias17, String fieldAlias18, String fieldAlias19) {
      return (new WithImpl((Configuration)null, true)).with(alias, fieldAlias1, fieldAlias2, fieldAlias3, fieldAlias4, fieldAlias5, fieldAlias6, fieldAlias7, fieldAlias8, fieldAlias9, fieldAlias10, fieldAlias11, fieldAlias12, fieldAlias13, fieldAlias14, fieldAlias15, fieldAlias16, fieldAlias17, fieldAlias18, fieldAlias19);
   }

   @Support({SQLDialect.FIREBIRD, SQLDialect.H2, SQLDialect.HSQLDB, SQLDialect.POSTGRES})
   public static WithAsStep20 withRecursive(String alias, String fieldAlias1, String fieldAlias2, String fieldAlias3, String fieldAlias4, String fieldAlias5, String fieldAlias6, String fieldAlias7, String fieldAlias8, String fieldAlias9, String fieldAlias10, String fieldAlias11, String fieldAlias12, String fieldAlias13, String fieldAlias14, String fieldAlias15, String fieldAlias16, String fieldAlias17, String fieldAlias18, String fieldAlias19, String fieldAlias20) {
      return (new WithImpl((Configuration)null, true)).with(alias, fieldAlias1, fieldAlias2, fieldAlias3, fieldAlias4, fieldAlias5, fieldAlias6, fieldAlias7, fieldAlias8, fieldAlias9, fieldAlias10, fieldAlias11, fieldAlias12, fieldAlias13, fieldAlias14, fieldAlias15, fieldAlias16, fieldAlias17, fieldAlias18, fieldAlias19, fieldAlias20);
   }

   @Support({SQLDialect.FIREBIRD, SQLDialect.H2, SQLDialect.HSQLDB, SQLDialect.POSTGRES})
   public static WithAsStep21 withRecursive(String alias, String fieldAlias1, String fieldAlias2, String fieldAlias3, String fieldAlias4, String fieldAlias5, String fieldAlias6, String fieldAlias7, String fieldAlias8, String fieldAlias9, String fieldAlias10, String fieldAlias11, String fieldAlias12, String fieldAlias13, String fieldAlias14, String fieldAlias15, String fieldAlias16, String fieldAlias17, String fieldAlias18, String fieldAlias19, String fieldAlias20, String fieldAlias21) {
      return (new WithImpl((Configuration)null, true)).with(alias, fieldAlias1, fieldAlias2, fieldAlias3, fieldAlias4, fieldAlias5, fieldAlias6, fieldAlias7, fieldAlias8, fieldAlias9, fieldAlias10, fieldAlias11, fieldAlias12, fieldAlias13, fieldAlias14, fieldAlias15, fieldAlias16, fieldAlias17, fieldAlias18, fieldAlias19, fieldAlias20, fieldAlias21);
   }

   @Support({SQLDialect.FIREBIRD, SQLDialect.H2, SQLDialect.HSQLDB, SQLDialect.POSTGRES})
   public static WithAsStep22 withRecursive(String alias, String fieldAlias1, String fieldAlias2, String fieldAlias3, String fieldAlias4, String fieldAlias5, String fieldAlias6, String fieldAlias7, String fieldAlias8, String fieldAlias9, String fieldAlias10, String fieldAlias11, String fieldAlias12, String fieldAlias13, String fieldAlias14, String fieldAlias15, String fieldAlias16, String fieldAlias17, String fieldAlias18, String fieldAlias19, String fieldAlias20, String fieldAlias21, String fieldAlias22) {
      return (new WithImpl((Configuration)null, true)).with(alias, fieldAlias1, fieldAlias2, fieldAlias3, fieldAlias4, fieldAlias5, fieldAlias6, fieldAlias7, fieldAlias8, fieldAlias9, fieldAlias10, fieldAlias11, fieldAlias12, fieldAlias13, fieldAlias14, fieldAlias15, fieldAlias16, fieldAlias17, fieldAlias18, fieldAlias19, fieldAlias20, fieldAlias21, fieldAlias22);
   }

   @Support({SQLDialect.FIREBIRD, SQLDialect.H2, SQLDialect.HSQLDB, SQLDialect.POSTGRES})
   public static WithStep withRecursive(CommonTableExpression<?>... tables) {
      return (new WithImpl((Configuration)null, true)).with(tables);
   }

   @Support
   public static <R extends Record> SelectWhereStep<R> selectFrom(Table<R> table) {
      return (new SelectImpl(new DefaultConfiguration(), (WithImpl)null)).from((TableLike)table);
   }

   @Support
   public static SelectSelectStep<Record> select(Collection<? extends SelectField<?>> fields) {
      return (new SelectImpl(new DefaultConfiguration(), (WithImpl)null)).select(fields);
   }

   @Support
   public static SelectSelectStep<Record> select(SelectField<?>... fields) {
      return (new SelectImpl(new DefaultConfiguration(), (WithImpl)null)).select(fields);
   }

   @Support
   public static <T1> SelectSelectStep<Record1<T1>> select(SelectField<T1> field1) {
      return select(field1);
   }

   @Support
   public static <T1, T2> SelectSelectStep<Record2<T1, T2>> select(SelectField<T1> field1, SelectField<T2> field2) {
      return select(field1, field2);
   }

   @Support
   public static <T1, T2, T3> SelectSelectStep<Record3<T1, T2, T3>> select(SelectField<T1> field1, SelectField<T2> field2, SelectField<T3> field3) {
      return select(field1, field2, field3);
   }

   @Support
   public static <T1, T2, T3, T4> SelectSelectStep<Record4<T1, T2, T3, T4>> select(SelectField<T1> field1, SelectField<T2> field2, SelectField<T3> field3, SelectField<T4> field4) {
      return select(field1, field2, field3, field4);
   }

   @Support
   public static <T1, T2, T3, T4, T5> SelectSelectStep<Record5<T1, T2, T3, T4, T5>> select(SelectField<T1> field1, SelectField<T2> field2, SelectField<T3> field3, SelectField<T4> field4, SelectField<T5> field5) {
      return select(field1, field2, field3, field4, field5);
   }

   @Support
   public static <T1, T2, T3, T4, T5, T6> SelectSelectStep<Record6<T1, T2, T3, T4, T5, T6>> select(SelectField<T1> field1, SelectField<T2> field2, SelectField<T3> field3, SelectField<T4> field4, SelectField<T5> field5, SelectField<T6> field6) {
      return select(field1, field2, field3, field4, field5, field6);
   }

   @Support
   public static <T1, T2, T3, T4, T5, T6, T7> SelectSelectStep<Record7<T1, T2, T3, T4, T5, T6, T7>> select(SelectField<T1> field1, SelectField<T2> field2, SelectField<T3> field3, SelectField<T4> field4, SelectField<T5> field5, SelectField<T6> field6, SelectField<T7> field7) {
      return select(field1, field2, field3, field4, field5, field6, field7);
   }

   @Support
   public static <T1, T2, T3, T4, T5, T6, T7, T8> SelectSelectStep<Record8<T1, T2, T3, T4, T5, T6, T7, T8>> select(SelectField<T1> field1, SelectField<T2> field2, SelectField<T3> field3, SelectField<T4> field4, SelectField<T5> field5, SelectField<T6> field6, SelectField<T7> field7, SelectField<T8> field8) {
      return select(field1, field2, field3, field4, field5, field6, field7, field8);
   }

   @Support
   public static <T1, T2, T3, T4, T5, T6, T7, T8, T9> SelectSelectStep<Record9<T1, T2, T3, T4, T5, T6, T7, T8, T9>> select(SelectField<T1> field1, SelectField<T2> field2, SelectField<T3> field3, SelectField<T4> field4, SelectField<T5> field5, SelectField<T6> field6, SelectField<T7> field7, SelectField<T8> field8, SelectField<T9> field9) {
      return select(field1, field2, field3, field4, field5, field6, field7, field8, field9);
   }

   @Support
   public static <T1, T2, T3, T4, T5, T6, T7, T8, T9, T10> SelectSelectStep<Record10<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10>> select(SelectField<T1> field1, SelectField<T2> field2, SelectField<T3> field3, SelectField<T4> field4, SelectField<T5> field5, SelectField<T6> field6, SelectField<T7> field7, SelectField<T8> field8, SelectField<T9> field9, SelectField<T10> field10) {
      return select(field1, field2, field3, field4, field5, field6, field7, field8, field9, field10);
   }

   @Support
   public static <T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11> SelectSelectStep<Record11<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11>> select(SelectField<T1> field1, SelectField<T2> field2, SelectField<T3> field3, SelectField<T4> field4, SelectField<T5> field5, SelectField<T6> field6, SelectField<T7> field7, SelectField<T8> field8, SelectField<T9> field9, SelectField<T10> field10, SelectField<T11> field11) {
      return select(field1, field2, field3, field4, field5, field6, field7, field8, field9, field10, field11);
   }

   @Support
   public static <T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12> SelectSelectStep<Record12<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12>> select(SelectField<T1> field1, SelectField<T2> field2, SelectField<T3> field3, SelectField<T4> field4, SelectField<T5> field5, SelectField<T6> field6, SelectField<T7> field7, SelectField<T8> field8, SelectField<T9> field9, SelectField<T10> field10, SelectField<T11> field11, SelectField<T12> field12) {
      return select(field1, field2, field3, field4, field5, field6, field7, field8, field9, field10, field11, field12);
   }

   @Support
   public static <T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13> SelectSelectStep<Record13<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13>> select(SelectField<T1> field1, SelectField<T2> field2, SelectField<T3> field3, SelectField<T4> field4, SelectField<T5> field5, SelectField<T6> field6, SelectField<T7> field7, SelectField<T8> field8, SelectField<T9> field9, SelectField<T10> field10, SelectField<T11> field11, SelectField<T12> field12, SelectField<T13> field13) {
      return select(field1, field2, field3, field4, field5, field6, field7, field8, field9, field10, field11, field12, field13);
   }

   @Support
   public static <T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14> SelectSelectStep<Record14<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14>> select(SelectField<T1> field1, SelectField<T2> field2, SelectField<T3> field3, SelectField<T4> field4, SelectField<T5> field5, SelectField<T6> field6, SelectField<T7> field7, SelectField<T8> field8, SelectField<T9> field9, SelectField<T10> field10, SelectField<T11> field11, SelectField<T12> field12, SelectField<T13> field13, SelectField<T14> field14) {
      return select(field1, field2, field3, field4, field5, field6, field7, field8, field9, field10, field11, field12, field13, field14);
   }

   @Support
   public static <T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14, T15> SelectSelectStep<Record15<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14, T15>> select(SelectField<T1> field1, SelectField<T2> field2, SelectField<T3> field3, SelectField<T4> field4, SelectField<T5> field5, SelectField<T6> field6, SelectField<T7> field7, SelectField<T8> field8, SelectField<T9> field9, SelectField<T10> field10, SelectField<T11> field11, SelectField<T12> field12, SelectField<T13> field13, SelectField<T14> field14, SelectField<T15> field15) {
      return select(field1, field2, field3, field4, field5, field6, field7, field8, field9, field10, field11, field12, field13, field14, field15);
   }

   @Support
   public static <T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14, T15, T16> SelectSelectStep<Record16<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14, T15, T16>> select(SelectField<T1> field1, SelectField<T2> field2, SelectField<T3> field3, SelectField<T4> field4, SelectField<T5> field5, SelectField<T6> field6, SelectField<T7> field7, SelectField<T8> field8, SelectField<T9> field9, SelectField<T10> field10, SelectField<T11> field11, SelectField<T12> field12, SelectField<T13> field13, SelectField<T14> field14, SelectField<T15> field15, SelectField<T16> field16) {
      return select(field1, field2, field3, field4, field5, field6, field7, field8, field9, field10, field11, field12, field13, field14, field15, field16);
   }

   @Support
   public static <T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14, T15, T16, T17> SelectSelectStep<Record17<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14, T15, T16, T17>> select(SelectField<T1> field1, SelectField<T2> field2, SelectField<T3> field3, SelectField<T4> field4, SelectField<T5> field5, SelectField<T6> field6, SelectField<T7> field7, SelectField<T8> field8, SelectField<T9> field9, SelectField<T10> field10, SelectField<T11> field11, SelectField<T12> field12, SelectField<T13> field13, SelectField<T14> field14, SelectField<T15> field15, SelectField<T16> field16, SelectField<T17> field17) {
      return select(field1, field2, field3, field4, field5, field6, field7, field8, field9, field10, field11, field12, field13, field14, field15, field16, field17);
   }

   @Support
   public static <T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14, T15, T16, T17, T18> SelectSelectStep<Record18<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14, T15, T16, T17, T18>> select(SelectField<T1> field1, SelectField<T2> field2, SelectField<T3> field3, SelectField<T4> field4, SelectField<T5> field5, SelectField<T6> field6, SelectField<T7> field7, SelectField<T8> field8, SelectField<T9> field9, SelectField<T10> field10, SelectField<T11> field11, SelectField<T12> field12, SelectField<T13> field13, SelectField<T14> field14, SelectField<T15> field15, SelectField<T16> field16, SelectField<T17> field17, SelectField<T18> field18) {
      return select(field1, field2, field3, field4, field5, field6, field7, field8, field9, field10, field11, field12, field13, field14, field15, field16, field17, field18);
   }

   @Support
   public static <T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14, T15, T16, T17, T18, T19> SelectSelectStep<Record19<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14, T15, T16, T17, T18, T19>> select(SelectField<T1> field1, SelectField<T2> field2, SelectField<T3> field3, SelectField<T4> field4, SelectField<T5> field5, SelectField<T6> field6, SelectField<T7> field7, SelectField<T8> field8, SelectField<T9> field9, SelectField<T10> field10, SelectField<T11> field11, SelectField<T12> field12, SelectField<T13> field13, SelectField<T14> field14, SelectField<T15> field15, SelectField<T16> field16, SelectField<T17> field17, SelectField<T18> field18, SelectField<T19> field19) {
      return select(field1, field2, field3, field4, field5, field6, field7, field8, field9, field10, field11, field12, field13, field14, field15, field16, field17, field18, field19);
   }

   @Support
   public static <T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14, T15, T16, T17, T18, T19, T20> SelectSelectStep<Record20<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14, T15, T16, T17, T18, T19, T20>> select(SelectField<T1> field1, SelectField<T2> field2, SelectField<T3> field3, SelectField<T4> field4, SelectField<T5> field5, SelectField<T6> field6, SelectField<T7> field7, SelectField<T8> field8, SelectField<T9> field9, SelectField<T10> field10, SelectField<T11> field11, SelectField<T12> field12, SelectField<T13> field13, SelectField<T14> field14, SelectField<T15> field15, SelectField<T16> field16, SelectField<T17> field17, SelectField<T18> field18, SelectField<T19> field19, SelectField<T20> field20) {
      return select(field1, field2, field3, field4, field5, field6, field7, field8, field9, field10, field11, field12, field13, field14, field15, field16, field17, field18, field19, field20);
   }

   @Support
   public static <T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14, T15, T16, T17, T18, T19, T20, T21> SelectSelectStep<Record21<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14, T15, T16, T17, T18, T19, T20, T21>> select(SelectField<T1> field1, SelectField<T2> field2, SelectField<T3> field3, SelectField<T4> field4, SelectField<T5> field5, SelectField<T6> field6, SelectField<T7> field7, SelectField<T8> field8, SelectField<T9> field9, SelectField<T10> field10, SelectField<T11> field11, SelectField<T12> field12, SelectField<T13> field13, SelectField<T14> field14, SelectField<T15> field15, SelectField<T16> field16, SelectField<T17> field17, SelectField<T18> field18, SelectField<T19> field19, SelectField<T20> field20, SelectField<T21> field21) {
      return select(field1, field2, field3, field4, field5, field6, field7, field8, field9, field10, field11, field12, field13, field14, field15, field16, field17, field18, field19, field20, field21);
   }

   @Support
   public static <T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14, T15, T16, T17, T18, T19, T20, T21, T22> SelectSelectStep<Record22<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14, T15, T16, T17, T18, T19, T20, T21, T22>> select(SelectField<T1> field1, SelectField<T2> field2, SelectField<T3> field3, SelectField<T4> field4, SelectField<T5> field5, SelectField<T6> field6, SelectField<T7> field7, SelectField<T8> field8, SelectField<T9> field9, SelectField<T10> field10, SelectField<T11> field11, SelectField<T12> field12, SelectField<T13> field13, SelectField<T14> field14, SelectField<T15> field15, SelectField<T16> field16, SelectField<T17> field17, SelectField<T18> field18, SelectField<T19> field19, SelectField<T20> field20, SelectField<T21> field21, SelectField<T22> field22) {
      return select(field1, field2, field3, field4, field5, field6, field7, field8, field9, field10, field11, field12, field13, field14, field15, field16, field17, field18, field19, field20, field21, field22);
   }

   @Support
   public static SelectSelectStep<Record> selectDistinct(Collection<? extends SelectField<?>> fields) {
      return (new SelectImpl(new DefaultConfiguration(), (WithImpl)null, true)).select(fields);
   }

   @Support
   public static SelectSelectStep<Record> selectDistinct(SelectField<?>... fields) {
      return (new SelectImpl(new DefaultConfiguration(), (WithImpl)null, true)).select(fields);
   }

   @Support
   public static <T1> SelectSelectStep<Record1<T1>> selectDistinct(SelectField<T1> field1) {
      return selectDistinct(field1);
   }

   @Support
   public static <T1, T2> SelectSelectStep<Record2<T1, T2>> selectDistinct(SelectField<T1> field1, SelectField<T2> field2) {
      return selectDistinct(field1, field2);
   }

   @Support
   public static <T1, T2, T3> SelectSelectStep<Record3<T1, T2, T3>> selectDistinct(SelectField<T1> field1, SelectField<T2> field2, SelectField<T3> field3) {
      return selectDistinct(field1, field2, field3);
   }

   @Support
   public static <T1, T2, T3, T4> SelectSelectStep<Record4<T1, T2, T3, T4>> selectDistinct(SelectField<T1> field1, SelectField<T2> field2, SelectField<T3> field3, SelectField<T4> field4) {
      return selectDistinct(field1, field2, field3, field4);
   }

   @Support
   public static <T1, T2, T3, T4, T5> SelectSelectStep<Record5<T1, T2, T3, T4, T5>> selectDistinct(SelectField<T1> field1, SelectField<T2> field2, SelectField<T3> field3, SelectField<T4> field4, SelectField<T5> field5) {
      return selectDistinct(field1, field2, field3, field4, field5);
   }

   @Support
   public static <T1, T2, T3, T4, T5, T6> SelectSelectStep<Record6<T1, T2, T3, T4, T5, T6>> selectDistinct(SelectField<T1> field1, SelectField<T2> field2, SelectField<T3> field3, SelectField<T4> field4, SelectField<T5> field5, SelectField<T6> field6) {
      return selectDistinct(field1, field2, field3, field4, field5, field6);
   }

   @Support
   public static <T1, T2, T3, T4, T5, T6, T7> SelectSelectStep<Record7<T1, T2, T3, T4, T5, T6, T7>> selectDistinct(SelectField<T1> field1, SelectField<T2> field2, SelectField<T3> field3, SelectField<T4> field4, SelectField<T5> field5, SelectField<T6> field6, SelectField<T7> field7) {
      return selectDistinct(field1, field2, field3, field4, field5, field6, field7);
   }

   @Support
   public static <T1, T2, T3, T4, T5, T6, T7, T8> SelectSelectStep<Record8<T1, T2, T3, T4, T5, T6, T7, T8>> selectDistinct(SelectField<T1> field1, SelectField<T2> field2, SelectField<T3> field3, SelectField<T4> field4, SelectField<T5> field5, SelectField<T6> field6, SelectField<T7> field7, SelectField<T8> field8) {
      return selectDistinct(field1, field2, field3, field4, field5, field6, field7, field8);
   }

   @Support
   public static <T1, T2, T3, T4, T5, T6, T7, T8, T9> SelectSelectStep<Record9<T1, T2, T3, T4, T5, T6, T7, T8, T9>> selectDistinct(SelectField<T1> field1, SelectField<T2> field2, SelectField<T3> field3, SelectField<T4> field4, SelectField<T5> field5, SelectField<T6> field6, SelectField<T7> field7, SelectField<T8> field8, SelectField<T9> field9) {
      return selectDistinct(field1, field2, field3, field4, field5, field6, field7, field8, field9);
   }

   @Support
   public static <T1, T2, T3, T4, T5, T6, T7, T8, T9, T10> SelectSelectStep<Record10<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10>> selectDistinct(SelectField<T1> field1, SelectField<T2> field2, SelectField<T3> field3, SelectField<T4> field4, SelectField<T5> field5, SelectField<T6> field6, SelectField<T7> field7, SelectField<T8> field8, SelectField<T9> field9, SelectField<T10> field10) {
      return selectDistinct(field1, field2, field3, field4, field5, field6, field7, field8, field9, field10);
   }

   @Support
   public static <T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11> SelectSelectStep<Record11<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11>> selectDistinct(SelectField<T1> field1, SelectField<T2> field2, SelectField<T3> field3, SelectField<T4> field4, SelectField<T5> field5, SelectField<T6> field6, SelectField<T7> field7, SelectField<T8> field8, SelectField<T9> field9, SelectField<T10> field10, SelectField<T11> field11) {
      return selectDistinct(field1, field2, field3, field4, field5, field6, field7, field8, field9, field10, field11);
   }

   @Support
   public static <T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12> SelectSelectStep<Record12<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12>> selectDistinct(SelectField<T1> field1, SelectField<T2> field2, SelectField<T3> field3, SelectField<T4> field4, SelectField<T5> field5, SelectField<T6> field6, SelectField<T7> field7, SelectField<T8> field8, SelectField<T9> field9, SelectField<T10> field10, SelectField<T11> field11, SelectField<T12> field12) {
      return selectDistinct(field1, field2, field3, field4, field5, field6, field7, field8, field9, field10, field11, field12);
   }

   @Support
   public static <T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13> SelectSelectStep<Record13<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13>> selectDistinct(SelectField<T1> field1, SelectField<T2> field2, SelectField<T3> field3, SelectField<T4> field4, SelectField<T5> field5, SelectField<T6> field6, SelectField<T7> field7, SelectField<T8> field8, SelectField<T9> field9, SelectField<T10> field10, SelectField<T11> field11, SelectField<T12> field12, SelectField<T13> field13) {
      return selectDistinct(field1, field2, field3, field4, field5, field6, field7, field8, field9, field10, field11, field12, field13);
   }

   @Support
   public static <T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14> SelectSelectStep<Record14<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14>> selectDistinct(SelectField<T1> field1, SelectField<T2> field2, SelectField<T3> field3, SelectField<T4> field4, SelectField<T5> field5, SelectField<T6> field6, SelectField<T7> field7, SelectField<T8> field8, SelectField<T9> field9, SelectField<T10> field10, SelectField<T11> field11, SelectField<T12> field12, SelectField<T13> field13, SelectField<T14> field14) {
      return selectDistinct(field1, field2, field3, field4, field5, field6, field7, field8, field9, field10, field11, field12, field13, field14);
   }

   @Support
   public static <T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14, T15> SelectSelectStep<Record15<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14, T15>> selectDistinct(SelectField<T1> field1, SelectField<T2> field2, SelectField<T3> field3, SelectField<T4> field4, SelectField<T5> field5, SelectField<T6> field6, SelectField<T7> field7, SelectField<T8> field8, SelectField<T9> field9, SelectField<T10> field10, SelectField<T11> field11, SelectField<T12> field12, SelectField<T13> field13, SelectField<T14> field14, SelectField<T15> field15) {
      return selectDistinct(field1, field2, field3, field4, field5, field6, field7, field8, field9, field10, field11, field12, field13, field14, field15);
   }

   @Support
   public static <T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14, T15, T16> SelectSelectStep<Record16<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14, T15, T16>> selectDistinct(SelectField<T1> field1, SelectField<T2> field2, SelectField<T3> field3, SelectField<T4> field4, SelectField<T5> field5, SelectField<T6> field6, SelectField<T7> field7, SelectField<T8> field8, SelectField<T9> field9, SelectField<T10> field10, SelectField<T11> field11, SelectField<T12> field12, SelectField<T13> field13, SelectField<T14> field14, SelectField<T15> field15, SelectField<T16> field16) {
      return selectDistinct(field1, field2, field3, field4, field5, field6, field7, field8, field9, field10, field11, field12, field13, field14, field15, field16);
   }

   @Support
   public static <T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14, T15, T16, T17> SelectSelectStep<Record17<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14, T15, T16, T17>> selectDistinct(SelectField<T1> field1, SelectField<T2> field2, SelectField<T3> field3, SelectField<T4> field4, SelectField<T5> field5, SelectField<T6> field6, SelectField<T7> field7, SelectField<T8> field8, SelectField<T9> field9, SelectField<T10> field10, SelectField<T11> field11, SelectField<T12> field12, SelectField<T13> field13, SelectField<T14> field14, SelectField<T15> field15, SelectField<T16> field16, SelectField<T17> field17) {
      return selectDistinct(field1, field2, field3, field4, field5, field6, field7, field8, field9, field10, field11, field12, field13, field14, field15, field16, field17);
   }

   @Support
   public static <T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14, T15, T16, T17, T18> SelectSelectStep<Record18<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14, T15, T16, T17, T18>> selectDistinct(SelectField<T1> field1, SelectField<T2> field2, SelectField<T3> field3, SelectField<T4> field4, SelectField<T5> field5, SelectField<T6> field6, SelectField<T7> field7, SelectField<T8> field8, SelectField<T9> field9, SelectField<T10> field10, SelectField<T11> field11, SelectField<T12> field12, SelectField<T13> field13, SelectField<T14> field14, SelectField<T15> field15, SelectField<T16> field16, SelectField<T17> field17, SelectField<T18> field18) {
      return selectDistinct(field1, field2, field3, field4, field5, field6, field7, field8, field9, field10, field11, field12, field13, field14, field15, field16, field17, field18);
   }

   @Support
   public static <T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14, T15, T16, T17, T18, T19> SelectSelectStep<Record19<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14, T15, T16, T17, T18, T19>> selectDistinct(SelectField<T1> field1, SelectField<T2> field2, SelectField<T3> field3, SelectField<T4> field4, SelectField<T5> field5, SelectField<T6> field6, SelectField<T7> field7, SelectField<T8> field8, SelectField<T9> field9, SelectField<T10> field10, SelectField<T11> field11, SelectField<T12> field12, SelectField<T13> field13, SelectField<T14> field14, SelectField<T15> field15, SelectField<T16> field16, SelectField<T17> field17, SelectField<T18> field18, SelectField<T19> field19) {
      return selectDistinct(field1, field2, field3, field4, field5, field6, field7, field8, field9, field10, field11, field12, field13, field14, field15, field16, field17, field18, field19);
   }

   @Support
   public static <T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14, T15, T16, T17, T18, T19, T20> SelectSelectStep<Record20<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14, T15, T16, T17, T18, T19, T20>> selectDistinct(SelectField<T1> field1, SelectField<T2> field2, SelectField<T3> field3, SelectField<T4> field4, SelectField<T5> field5, SelectField<T6> field6, SelectField<T7> field7, SelectField<T8> field8, SelectField<T9> field9, SelectField<T10> field10, SelectField<T11> field11, SelectField<T12> field12, SelectField<T13> field13, SelectField<T14> field14, SelectField<T15> field15, SelectField<T16> field16, SelectField<T17> field17, SelectField<T18> field18, SelectField<T19> field19, SelectField<T20> field20) {
      return selectDistinct(field1, field2, field3, field4, field5, field6, field7, field8, field9, field10, field11, field12, field13, field14, field15, field16, field17, field18, field19, field20);
   }

   @Support
   public static <T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14, T15, T16, T17, T18, T19, T20, T21> SelectSelectStep<Record21<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14, T15, T16, T17, T18, T19, T20, T21>> selectDistinct(SelectField<T1> field1, SelectField<T2> field2, SelectField<T3> field3, SelectField<T4> field4, SelectField<T5> field5, SelectField<T6> field6, SelectField<T7> field7, SelectField<T8> field8, SelectField<T9> field9, SelectField<T10> field10, SelectField<T11> field11, SelectField<T12> field12, SelectField<T13> field13, SelectField<T14> field14, SelectField<T15> field15, SelectField<T16> field16, SelectField<T17> field17, SelectField<T18> field18, SelectField<T19> field19, SelectField<T20> field20, SelectField<T21> field21) {
      return selectDistinct(field1, field2, field3, field4, field5, field6, field7, field8, field9, field10, field11, field12, field13, field14, field15, field16, field17, field18, field19, field20, field21);
   }

   @Support
   public static <T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14, T15, T16, T17, T18, T19, T20, T21, T22> SelectSelectStep<Record22<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14, T15, T16, T17, T18, T19, T20, T21, T22>> selectDistinct(SelectField<T1> field1, SelectField<T2> field2, SelectField<T3> field3, SelectField<T4> field4, SelectField<T5> field5, SelectField<T6> field6, SelectField<T7> field7, SelectField<T8> field8, SelectField<T9> field9, SelectField<T10> field10, SelectField<T11> field11, SelectField<T12> field12, SelectField<T13> field13, SelectField<T14> field14, SelectField<T15> field15, SelectField<T16> field16, SelectField<T17> field17, SelectField<T18> field18, SelectField<T19> field19, SelectField<T20> field20, SelectField<T21> field21, SelectField<T22> field22) {
      return selectDistinct(field1, field2, field3, field4, field5, field6, field7, field8, field9, field10, field11, field12, field13, field14, field15, field16, field17, field18, field19, field20, field21, field22);
   }

   @Support
   public static SelectSelectStep<Record1<Integer>> selectZero() {
      return (new SelectImpl(new DefaultConfiguration(), (WithImpl)null)).select(zero().as("zero"));
   }

   @Support
   public static SelectSelectStep<Record1<Integer>> selectOne() {
      return (new SelectImpl(new DefaultConfiguration(), (WithImpl)null)).select(one().as("one"));
   }

   @Support
   public static SelectSelectStep<Record1<Integer>> selectCount() {
      return (new SelectImpl(new DefaultConfiguration(), (WithImpl)null)).select(count());
   }

   @Support
   public static <R extends Record> InsertSetStep<R> insertInto(Table<R> into) {
      return using((Configuration)(new DefaultConfiguration())).insertInto(into);
   }

   @Support
   public static <R extends Record, T1> InsertValuesStep1<R, T1> insertInto(Table<R> into, Field<T1> field1) {
      return (InsertValuesStep1)insertInto(into, field1);
   }

   @Support
   public static <R extends Record, T1, T2> InsertValuesStep2<R, T1, T2> insertInto(Table<R> into, Field<T1> field1, Field<T2> field2) {
      return (InsertValuesStep2)insertInto(into, field1, field2);
   }

   @Support
   public static <R extends Record, T1, T2, T3> InsertValuesStep3<R, T1, T2, T3> insertInto(Table<R> into, Field<T1> field1, Field<T2> field2, Field<T3> field3) {
      return (InsertValuesStep3)insertInto(into, field1, field2, field3);
   }

   @Support
   public static <R extends Record, T1, T2, T3, T4> InsertValuesStep4<R, T1, T2, T3, T4> insertInto(Table<R> into, Field<T1> field1, Field<T2> field2, Field<T3> field3, Field<T4> field4) {
      return (InsertValuesStep4)insertInto(into, field1, field2, field3, field4);
   }

   @Support
   public static <R extends Record, T1, T2, T3, T4, T5> InsertValuesStep5<R, T1, T2, T3, T4, T5> insertInto(Table<R> into, Field<T1> field1, Field<T2> field2, Field<T3> field3, Field<T4> field4, Field<T5> field5) {
      return (InsertValuesStep5)insertInto(into, field1, field2, field3, field4, field5);
   }

   @Support
   public static <R extends Record, T1, T2, T3, T4, T5, T6> InsertValuesStep6<R, T1, T2, T3, T4, T5, T6> insertInto(Table<R> into, Field<T1> field1, Field<T2> field2, Field<T3> field3, Field<T4> field4, Field<T5> field5, Field<T6> field6) {
      return (InsertValuesStep6)insertInto(into, field1, field2, field3, field4, field5, field6);
   }

   @Support
   public static <R extends Record, T1, T2, T3, T4, T5, T6, T7> InsertValuesStep7<R, T1, T2, T3, T4, T5, T6, T7> insertInto(Table<R> into, Field<T1> field1, Field<T2> field2, Field<T3> field3, Field<T4> field4, Field<T5> field5, Field<T6> field6, Field<T7> field7) {
      return (InsertValuesStep7)insertInto(into, field1, field2, field3, field4, field5, field6, field7);
   }

   @Support
   public static <R extends Record, T1, T2, T3, T4, T5, T6, T7, T8> InsertValuesStep8<R, T1, T2, T3, T4, T5, T6, T7, T8> insertInto(Table<R> into, Field<T1> field1, Field<T2> field2, Field<T3> field3, Field<T4> field4, Field<T5> field5, Field<T6> field6, Field<T7> field7, Field<T8> field8) {
      return (InsertValuesStep8)insertInto(into, field1, field2, field3, field4, field5, field6, field7, field8);
   }

   @Support
   public static <R extends Record, T1, T2, T3, T4, T5, T6, T7, T8, T9> InsertValuesStep9<R, T1, T2, T3, T4, T5, T6, T7, T8, T9> insertInto(Table<R> into, Field<T1> field1, Field<T2> field2, Field<T3> field3, Field<T4> field4, Field<T5> field5, Field<T6> field6, Field<T7> field7, Field<T8> field8, Field<T9> field9) {
      return (InsertValuesStep9)insertInto(into, field1, field2, field3, field4, field5, field6, field7, field8, field9);
   }

   @Support
   public static <R extends Record, T1, T2, T3, T4, T5, T6, T7, T8, T9, T10> InsertValuesStep10<R, T1, T2, T3, T4, T5, T6, T7, T8, T9, T10> insertInto(Table<R> into, Field<T1> field1, Field<T2> field2, Field<T3> field3, Field<T4> field4, Field<T5> field5, Field<T6> field6, Field<T7> field7, Field<T8> field8, Field<T9> field9, Field<T10> field10) {
      return (InsertValuesStep10)insertInto(into, field1, field2, field3, field4, field5, field6, field7, field8, field9, field10);
   }

   @Support
   public static <R extends Record, T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11> InsertValuesStep11<R, T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11> insertInto(Table<R> into, Field<T1> field1, Field<T2> field2, Field<T3> field3, Field<T4> field4, Field<T5> field5, Field<T6> field6, Field<T7> field7, Field<T8> field8, Field<T9> field9, Field<T10> field10, Field<T11> field11) {
      return (InsertValuesStep11)insertInto(into, field1, field2, field3, field4, field5, field6, field7, field8, field9, field10, field11);
   }

   @Support
   public static <R extends Record, T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12> InsertValuesStep12<R, T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12> insertInto(Table<R> into, Field<T1> field1, Field<T2> field2, Field<T3> field3, Field<T4> field4, Field<T5> field5, Field<T6> field6, Field<T7> field7, Field<T8> field8, Field<T9> field9, Field<T10> field10, Field<T11> field11, Field<T12> field12) {
      return (InsertValuesStep12)insertInto(into, field1, field2, field3, field4, field5, field6, field7, field8, field9, field10, field11, field12);
   }

   @Support
   public static <R extends Record, T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13> InsertValuesStep13<R, T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13> insertInto(Table<R> into, Field<T1> field1, Field<T2> field2, Field<T3> field3, Field<T4> field4, Field<T5> field5, Field<T6> field6, Field<T7> field7, Field<T8> field8, Field<T9> field9, Field<T10> field10, Field<T11> field11, Field<T12> field12, Field<T13> field13) {
      return (InsertValuesStep13)insertInto(into, field1, field2, field3, field4, field5, field6, field7, field8, field9, field10, field11, field12, field13);
   }

   @Support
   public static <R extends Record, T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14> InsertValuesStep14<R, T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14> insertInto(Table<R> into, Field<T1> field1, Field<T2> field2, Field<T3> field3, Field<T4> field4, Field<T5> field5, Field<T6> field6, Field<T7> field7, Field<T8> field8, Field<T9> field9, Field<T10> field10, Field<T11> field11, Field<T12> field12, Field<T13> field13, Field<T14> field14) {
      return (InsertValuesStep14)insertInto(into, field1, field2, field3, field4, field5, field6, field7, field8, field9, field10, field11, field12, field13, field14);
   }

   @Support
   public static <R extends Record, T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14, T15> InsertValuesStep15<R, T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14, T15> insertInto(Table<R> into, Field<T1> field1, Field<T2> field2, Field<T3> field3, Field<T4> field4, Field<T5> field5, Field<T6> field6, Field<T7> field7, Field<T8> field8, Field<T9> field9, Field<T10> field10, Field<T11> field11, Field<T12> field12, Field<T13> field13, Field<T14> field14, Field<T15> field15) {
      return (InsertValuesStep15)insertInto(into, field1, field2, field3, field4, field5, field6, field7, field8, field9, field10, field11, field12, field13, field14, field15);
   }

   @Support
   public static <R extends Record, T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14, T15, T16> InsertValuesStep16<R, T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14, T15, T16> insertInto(Table<R> into, Field<T1> field1, Field<T2> field2, Field<T3> field3, Field<T4> field4, Field<T5> field5, Field<T6> field6, Field<T7> field7, Field<T8> field8, Field<T9> field9, Field<T10> field10, Field<T11> field11, Field<T12> field12, Field<T13> field13, Field<T14> field14, Field<T15> field15, Field<T16> field16) {
      return (InsertValuesStep16)insertInto(into, field1, field2, field3, field4, field5, field6, field7, field8, field9, field10, field11, field12, field13, field14, field15, field16);
   }

   @Support
   public static <R extends Record, T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14, T15, T16, T17> InsertValuesStep17<R, T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14, T15, T16, T17> insertInto(Table<R> into, Field<T1> field1, Field<T2> field2, Field<T3> field3, Field<T4> field4, Field<T5> field5, Field<T6> field6, Field<T7> field7, Field<T8> field8, Field<T9> field9, Field<T10> field10, Field<T11> field11, Field<T12> field12, Field<T13> field13, Field<T14> field14, Field<T15> field15, Field<T16> field16, Field<T17> field17) {
      return (InsertValuesStep17)insertInto(into, field1, field2, field3, field4, field5, field6, field7, field8, field9, field10, field11, field12, field13, field14, field15, field16, field17);
   }

   @Support
   public static <R extends Record, T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14, T15, T16, T17, T18> InsertValuesStep18<R, T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14, T15, T16, T17, T18> insertInto(Table<R> into, Field<T1> field1, Field<T2> field2, Field<T3> field3, Field<T4> field4, Field<T5> field5, Field<T6> field6, Field<T7> field7, Field<T8> field8, Field<T9> field9, Field<T10> field10, Field<T11> field11, Field<T12> field12, Field<T13> field13, Field<T14> field14, Field<T15> field15, Field<T16> field16, Field<T17> field17, Field<T18> field18) {
      return (InsertValuesStep18)insertInto(into, field1, field2, field3, field4, field5, field6, field7, field8, field9, field10, field11, field12, field13, field14, field15, field16, field17, field18);
   }

   @Support
   public static <R extends Record, T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14, T15, T16, T17, T18, T19> InsertValuesStep19<R, T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14, T15, T16, T17, T18, T19> insertInto(Table<R> into, Field<T1> field1, Field<T2> field2, Field<T3> field3, Field<T4> field4, Field<T5> field5, Field<T6> field6, Field<T7> field7, Field<T8> field8, Field<T9> field9, Field<T10> field10, Field<T11> field11, Field<T12> field12, Field<T13> field13, Field<T14> field14, Field<T15> field15, Field<T16> field16, Field<T17> field17, Field<T18> field18, Field<T19> field19) {
      return (InsertValuesStep19)insertInto(into, field1, field2, field3, field4, field5, field6, field7, field8, field9, field10, field11, field12, field13, field14, field15, field16, field17, field18, field19);
   }

   @Support
   public static <R extends Record, T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14, T15, T16, T17, T18, T19, T20> InsertValuesStep20<R, T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14, T15, T16, T17, T18, T19, T20> insertInto(Table<R> into, Field<T1> field1, Field<T2> field2, Field<T3> field3, Field<T4> field4, Field<T5> field5, Field<T6> field6, Field<T7> field7, Field<T8> field8, Field<T9> field9, Field<T10> field10, Field<T11> field11, Field<T12> field12, Field<T13> field13, Field<T14> field14, Field<T15> field15, Field<T16> field16, Field<T17> field17, Field<T18> field18, Field<T19> field19, Field<T20> field20) {
      return (InsertValuesStep20)insertInto(into, field1, field2, field3, field4, field5, field6, field7, field8, field9, field10, field11, field12, field13, field14, field15, field16, field17, field18, field19, field20);
   }

   @Support
   public static <R extends Record, T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14, T15, T16, T17, T18, T19, T20, T21> InsertValuesStep21<R, T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14, T15, T16, T17, T18, T19, T20, T21> insertInto(Table<R> into, Field<T1> field1, Field<T2> field2, Field<T3> field3, Field<T4> field4, Field<T5> field5, Field<T6> field6, Field<T7> field7, Field<T8> field8, Field<T9> field9, Field<T10> field10, Field<T11> field11, Field<T12> field12, Field<T13> field13, Field<T14> field14, Field<T15> field15, Field<T16> field16, Field<T17> field17, Field<T18> field18, Field<T19> field19, Field<T20> field20, Field<T21> field21) {
      return (InsertValuesStep21)insertInto(into, field1, field2, field3, field4, field5, field6, field7, field8, field9, field10, field11, field12, field13, field14, field15, field16, field17, field18, field19, field20, field21);
   }

   @Support
   public static <R extends Record, T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14, T15, T16, T17, T18, T19, T20, T21, T22> InsertValuesStep22<R, T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14, T15, T16, T17, T18, T19, T20, T21, T22> insertInto(Table<R> into, Field<T1> field1, Field<T2> field2, Field<T3> field3, Field<T4> field4, Field<T5> field5, Field<T6> field6, Field<T7> field7, Field<T8> field8, Field<T9> field9, Field<T10> field10, Field<T11> field11, Field<T12> field12, Field<T13> field13, Field<T14> field14, Field<T15> field15, Field<T16> field16, Field<T17> field17, Field<T18> field18, Field<T19> field19, Field<T20> field20, Field<T21> field21, Field<T22> field22) {
      return (InsertValuesStep22)insertInto(into, field1, field2, field3, field4, field5, field6, field7, field8, field9, field10, field11, field12, field13, field14, field15, field16, field17, field18, field19, field20, field21, field22);
   }

   @Support
   public static <R extends Record> InsertValuesStepN<R> insertInto(Table<R> into, Field<?>... fields) {
      return using((Configuration)(new DefaultConfiguration())).insertInto(into, fields);
   }

   @Support
   public static <R extends Record> InsertValuesStepN<R> insertInto(Table<R> into, Collection<? extends Field<?>> fields) {
      return using((Configuration)(new DefaultConfiguration())).insertInto(into, fields);
   }

   @Support
   public static <R extends Record> UpdateSetFirstStep<R> update(Table<R> table) {
      return using((Configuration)(new DefaultConfiguration())).update(table);
   }

   @Support({SQLDialect.CUBRID, SQLDialect.FIREBIRD_3_0, SQLDialect.HSQLDB})
   public static <R extends Record> MergeUsingStep<R> mergeInto(Table<R> table) {
      return using((Configuration)(new DefaultConfiguration())).mergeInto(table);
   }

   @Support({SQLDialect.CUBRID, SQLDialect.FIREBIRD_3_0, SQLDialect.H2, SQLDialect.HSQLDB, SQLDialect.MARIADB, SQLDialect.MYSQL, SQLDialect.POSTGRES_9_5})
   public static <R extends Record, T1> MergeKeyStep1<R, T1> mergeInto(Table<R> table, Field<T1> field1) {
      return using((Configuration)(new DefaultConfiguration())).mergeInto(table, field1);
   }

   @Support({SQLDialect.CUBRID, SQLDialect.FIREBIRD_3_0, SQLDialect.H2, SQLDialect.HSQLDB, SQLDialect.MARIADB, SQLDialect.MYSQL, SQLDialect.POSTGRES_9_5})
   public static <R extends Record, T1, T2> MergeKeyStep2<R, T1, T2> mergeInto(Table<R> table, Field<T1> field1, Field<T2> field2) {
      return using((Configuration)(new DefaultConfiguration())).mergeInto(table, field1, field2);
   }

   @Support({SQLDialect.CUBRID, SQLDialect.FIREBIRD_3_0, SQLDialect.H2, SQLDialect.HSQLDB, SQLDialect.MARIADB, SQLDialect.MYSQL, SQLDialect.POSTGRES_9_5})
   public static <R extends Record, T1, T2, T3> MergeKeyStep3<R, T1, T2, T3> mergeInto(Table<R> table, Field<T1> field1, Field<T2> field2, Field<T3> field3) {
      return using((Configuration)(new DefaultConfiguration())).mergeInto(table, field1, field2, field3);
   }

   @Support({SQLDialect.CUBRID, SQLDialect.FIREBIRD_3_0, SQLDialect.H2, SQLDialect.HSQLDB, SQLDialect.MARIADB, SQLDialect.MYSQL, SQLDialect.POSTGRES_9_5})
   public static <R extends Record, T1, T2, T3, T4> MergeKeyStep4<R, T1, T2, T3, T4> mergeInto(Table<R> table, Field<T1> field1, Field<T2> field2, Field<T3> field3, Field<T4> field4) {
      return using((Configuration)(new DefaultConfiguration())).mergeInto(table, field1, field2, field3, field4);
   }

   @Support({SQLDialect.CUBRID, SQLDialect.FIREBIRD_3_0, SQLDialect.H2, SQLDialect.HSQLDB, SQLDialect.MARIADB, SQLDialect.MYSQL, SQLDialect.POSTGRES_9_5})
   public static <R extends Record, T1, T2, T3, T4, T5> MergeKeyStep5<R, T1, T2, T3, T4, T5> mergeInto(Table<R> table, Field<T1> field1, Field<T2> field2, Field<T3> field3, Field<T4> field4, Field<T5> field5) {
      return using((Configuration)(new DefaultConfiguration())).mergeInto(table, field1, field2, field3, field4, field5);
   }

   @Support({SQLDialect.CUBRID, SQLDialect.FIREBIRD_3_0, SQLDialect.H2, SQLDialect.HSQLDB, SQLDialect.MARIADB, SQLDialect.MYSQL, SQLDialect.POSTGRES_9_5})
   public static <R extends Record, T1, T2, T3, T4, T5, T6> MergeKeyStep6<R, T1, T2, T3, T4, T5, T6> mergeInto(Table<R> table, Field<T1> field1, Field<T2> field2, Field<T3> field3, Field<T4> field4, Field<T5> field5, Field<T6> field6) {
      return using((Configuration)(new DefaultConfiguration())).mergeInto(table, field1, field2, field3, field4, field5, field6);
   }

   @Support({SQLDialect.CUBRID, SQLDialect.FIREBIRD_3_0, SQLDialect.H2, SQLDialect.HSQLDB, SQLDialect.MARIADB, SQLDialect.MYSQL, SQLDialect.POSTGRES_9_5})
   public static <R extends Record, T1, T2, T3, T4, T5, T6, T7> MergeKeyStep7<R, T1, T2, T3, T4, T5, T6, T7> mergeInto(Table<R> table, Field<T1> field1, Field<T2> field2, Field<T3> field3, Field<T4> field4, Field<T5> field5, Field<T6> field6, Field<T7> field7) {
      return using((Configuration)(new DefaultConfiguration())).mergeInto(table, field1, field2, field3, field4, field5, field6, field7);
   }

   @Support({SQLDialect.CUBRID, SQLDialect.FIREBIRD_3_0, SQLDialect.H2, SQLDialect.HSQLDB, SQLDialect.MARIADB, SQLDialect.MYSQL, SQLDialect.POSTGRES_9_5})
   public static <R extends Record, T1, T2, T3, T4, T5, T6, T7, T8> MergeKeyStep8<R, T1, T2, T3, T4, T5, T6, T7, T8> mergeInto(Table<R> table, Field<T1> field1, Field<T2> field2, Field<T3> field3, Field<T4> field4, Field<T5> field5, Field<T6> field6, Field<T7> field7, Field<T8> field8) {
      return using((Configuration)(new DefaultConfiguration())).mergeInto(table, field1, field2, field3, field4, field5, field6, field7, field8);
   }

   @Support({SQLDialect.CUBRID, SQLDialect.FIREBIRD_3_0, SQLDialect.H2, SQLDialect.HSQLDB, SQLDialect.MARIADB, SQLDialect.MYSQL, SQLDialect.POSTGRES_9_5})
   public static <R extends Record, T1, T2, T3, T4, T5, T6, T7, T8, T9> MergeKeyStep9<R, T1, T2, T3, T4, T5, T6, T7, T8, T9> mergeInto(Table<R> table, Field<T1> field1, Field<T2> field2, Field<T3> field3, Field<T4> field4, Field<T5> field5, Field<T6> field6, Field<T7> field7, Field<T8> field8, Field<T9> field9) {
      return using((Configuration)(new DefaultConfiguration())).mergeInto(table, field1, field2, field3, field4, field5, field6, field7, field8, field9);
   }

   @Support({SQLDialect.CUBRID, SQLDialect.FIREBIRD_3_0, SQLDialect.H2, SQLDialect.HSQLDB, SQLDialect.MARIADB, SQLDialect.MYSQL, SQLDialect.POSTGRES_9_5})
   public static <R extends Record, T1, T2, T3, T4, T5, T6, T7, T8, T9, T10> MergeKeyStep10<R, T1, T2, T3, T4, T5, T6, T7, T8, T9, T10> mergeInto(Table<R> table, Field<T1> field1, Field<T2> field2, Field<T3> field3, Field<T4> field4, Field<T5> field5, Field<T6> field6, Field<T7> field7, Field<T8> field8, Field<T9> field9, Field<T10> field10) {
      return using((Configuration)(new DefaultConfiguration())).mergeInto(table, field1, field2, field3, field4, field5, field6, field7, field8, field9, field10);
   }

   @Support({SQLDialect.CUBRID, SQLDialect.FIREBIRD_3_0, SQLDialect.H2, SQLDialect.HSQLDB, SQLDialect.MARIADB, SQLDialect.MYSQL, SQLDialect.POSTGRES_9_5})
   public static <R extends Record, T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11> MergeKeyStep11<R, T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11> mergeInto(Table<R> table, Field<T1> field1, Field<T2> field2, Field<T3> field3, Field<T4> field4, Field<T5> field5, Field<T6> field6, Field<T7> field7, Field<T8> field8, Field<T9> field9, Field<T10> field10, Field<T11> field11) {
      return using((Configuration)(new DefaultConfiguration())).mergeInto(table, field1, field2, field3, field4, field5, field6, field7, field8, field9, field10, field11);
   }

   @Support({SQLDialect.CUBRID, SQLDialect.FIREBIRD_3_0, SQLDialect.H2, SQLDialect.HSQLDB, SQLDialect.MARIADB, SQLDialect.MYSQL, SQLDialect.POSTGRES_9_5})
   public static <R extends Record, T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12> MergeKeyStep12<R, T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12> mergeInto(Table<R> table, Field<T1> field1, Field<T2> field2, Field<T3> field3, Field<T4> field4, Field<T5> field5, Field<T6> field6, Field<T7> field7, Field<T8> field8, Field<T9> field9, Field<T10> field10, Field<T11> field11, Field<T12> field12) {
      return using((Configuration)(new DefaultConfiguration())).mergeInto(table, field1, field2, field3, field4, field5, field6, field7, field8, field9, field10, field11, field12);
   }

   @Support({SQLDialect.CUBRID, SQLDialect.FIREBIRD_3_0, SQLDialect.H2, SQLDialect.HSQLDB, SQLDialect.MARIADB, SQLDialect.MYSQL, SQLDialect.POSTGRES_9_5})
   public static <R extends Record, T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13> MergeKeyStep13<R, T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13> mergeInto(Table<R> table, Field<T1> field1, Field<T2> field2, Field<T3> field3, Field<T4> field4, Field<T5> field5, Field<T6> field6, Field<T7> field7, Field<T8> field8, Field<T9> field9, Field<T10> field10, Field<T11> field11, Field<T12> field12, Field<T13> field13) {
      return using((Configuration)(new DefaultConfiguration())).mergeInto(table, field1, field2, field3, field4, field5, field6, field7, field8, field9, field10, field11, field12, field13);
   }

   @Support({SQLDialect.CUBRID, SQLDialect.FIREBIRD_3_0, SQLDialect.H2, SQLDialect.HSQLDB, SQLDialect.MARIADB, SQLDialect.MYSQL, SQLDialect.POSTGRES_9_5})
   public static <R extends Record, T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14> MergeKeyStep14<R, T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14> mergeInto(Table<R> table, Field<T1> field1, Field<T2> field2, Field<T3> field3, Field<T4> field4, Field<T5> field5, Field<T6> field6, Field<T7> field7, Field<T8> field8, Field<T9> field9, Field<T10> field10, Field<T11> field11, Field<T12> field12, Field<T13> field13, Field<T14> field14) {
      return using((Configuration)(new DefaultConfiguration())).mergeInto(table, field1, field2, field3, field4, field5, field6, field7, field8, field9, field10, field11, field12, field13, field14);
   }

   @Support({SQLDialect.CUBRID, SQLDialect.FIREBIRD_3_0, SQLDialect.H2, SQLDialect.HSQLDB, SQLDialect.MARIADB, SQLDialect.MYSQL, SQLDialect.POSTGRES_9_5})
   public static <R extends Record, T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14, T15> MergeKeyStep15<R, T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14, T15> mergeInto(Table<R> table, Field<T1> field1, Field<T2> field2, Field<T3> field3, Field<T4> field4, Field<T5> field5, Field<T6> field6, Field<T7> field7, Field<T8> field8, Field<T9> field9, Field<T10> field10, Field<T11> field11, Field<T12> field12, Field<T13> field13, Field<T14> field14, Field<T15> field15) {
      return using((Configuration)(new DefaultConfiguration())).mergeInto(table, field1, field2, field3, field4, field5, field6, field7, field8, field9, field10, field11, field12, field13, field14, field15);
   }

   @Support({SQLDialect.CUBRID, SQLDialect.FIREBIRD_3_0, SQLDialect.H2, SQLDialect.HSQLDB, SQLDialect.MARIADB, SQLDialect.MYSQL, SQLDialect.POSTGRES_9_5})
   public static <R extends Record, T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14, T15, T16> MergeKeyStep16<R, T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14, T15, T16> mergeInto(Table<R> table, Field<T1> field1, Field<T2> field2, Field<T3> field3, Field<T4> field4, Field<T5> field5, Field<T6> field6, Field<T7> field7, Field<T8> field8, Field<T9> field9, Field<T10> field10, Field<T11> field11, Field<T12> field12, Field<T13> field13, Field<T14> field14, Field<T15> field15, Field<T16> field16) {
      return using((Configuration)(new DefaultConfiguration())).mergeInto(table, field1, field2, field3, field4, field5, field6, field7, field8, field9, field10, field11, field12, field13, field14, field15, field16);
   }

   @Support({SQLDialect.CUBRID, SQLDialect.FIREBIRD_3_0, SQLDialect.H2, SQLDialect.HSQLDB, SQLDialect.MARIADB, SQLDialect.MYSQL, SQLDialect.POSTGRES_9_5})
   public static <R extends Record, T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14, T15, T16, T17> MergeKeyStep17<R, T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14, T15, T16, T17> mergeInto(Table<R> table, Field<T1> field1, Field<T2> field2, Field<T3> field3, Field<T4> field4, Field<T5> field5, Field<T6> field6, Field<T7> field7, Field<T8> field8, Field<T9> field9, Field<T10> field10, Field<T11> field11, Field<T12> field12, Field<T13> field13, Field<T14> field14, Field<T15> field15, Field<T16> field16, Field<T17> field17) {
      return using((Configuration)(new DefaultConfiguration())).mergeInto(table, field1, field2, field3, field4, field5, field6, field7, field8, field9, field10, field11, field12, field13, field14, field15, field16, field17);
   }

   @Support({SQLDialect.CUBRID, SQLDialect.FIREBIRD_3_0, SQLDialect.H2, SQLDialect.HSQLDB, SQLDialect.MARIADB, SQLDialect.MYSQL, SQLDialect.POSTGRES_9_5})
   public static <R extends Record, T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14, T15, T16, T17, T18> MergeKeyStep18<R, T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14, T15, T16, T17, T18> mergeInto(Table<R> table, Field<T1> field1, Field<T2> field2, Field<T3> field3, Field<T4> field4, Field<T5> field5, Field<T6> field6, Field<T7> field7, Field<T8> field8, Field<T9> field9, Field<T10> field10, Field<T11> field11, Field<T12> field12, Field<T13> field13, Field<T14> field14, Field<T15> field15, Field<T16> field16, Field<T17> field17, Field<T18> field18) {
      return using((Configuration)(new DefaultConfiguration())).mergeInto(table, field1, field2, field3, field4, field5, field6, field7, field8, field9, field10, field11, field12, field13, field14, field15, field16, field17, field18);
   }

   @Support({SQLDialect.CUBRID, SQLDialect.FIREBIRD_3_0, SQLDialect.H2, SQLDialect.HSQLDB, SQLDialect.MARIADB, SQLDialect.MYSQL, SQLDialect.POSTGRES_9_5})
   public static <R extends Record, T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14, T15, T16, T17, T18, T19> MergeKeyStep19<R, T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14, T15, T16, T17, T18, T19> mergeInto(Table<R> table, Field<T1> field1, Field<T2> field2, Field<T3> field3, Field<T4> field4, Field<T5> field5, Field<T6> field6, Field<T7> field7, Field<T8> field8, Field<T9> field9, Field<T10> field10, Field<T11> field11, Field<T12> field12, Field<T13> field13, Field<T14> field14, Field<T15> field15, Field<T16> field16, Field<T17> field17, Field<T18> field18, Field<T19> field19) {
      return using((Configuration)(new DefaultConfiguration())).mergeInto(table, field1, field2, field3, field4, field5, field6, field7, field8, field9, field10, field11, field12, field13, field14, field15, field16, field17, field18, field19);
   }

   @Support({SQLDialect.CUBRID, SQLDialect.FIREBIRD_3_0, SQLDialect.H2, SQLDialect.HSQLDB, SQLDialect.MARIADB, SQLDialect.MYSQL, SQLDialect.POSTGRES_9_5})
   public static <R extends Record, T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14, T15, T16, T17, T18, T19, T20> MergeKeyStep20<R, T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14, T15, T16, T17, T18, T19, T20> mergeInto(Table<R> table, Field<T1> field1, Field<T2> field2, Field<T3> field3, Field<T4> field4, Field<T5> field5, Field<T6> field6, Field<T7> field7, Field<T8> field8, Field<T9> field9, Field<T10> field10, Field<T11> field11, Field<T12> field12, Field<T13> field13, Field<T14> field14, Field<T15> field15, Field<T16> field16, Field<T17> field17, Field<T18> field18, Field<T19> field19, Field<T20> field20) {
      return using((Configuration)(new DefaultConfiguration())).mergeInto(table, field1, field2, field3, field4, field5, field6, field7, field8, field9, field10, field11, field12, field13, field14, field15, field16, field17, field18, field19, field20);
   }

   @Support({SQLDialect.CUBRID, SQLDialect.FIREBIRD_3_0, SQLDialect.H2, SQLDialect.HSQLDB, SQLDialect.MARIADB, SQLDialect.MYSQL, SQLDialect.POSTGRES_9_5})
   public static <R extends Record, T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14, T15, T16, T17, T18, T19, T20, T21> MergeKeyStep21<R, T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14, T15, T16, T17, T18, T19, T20, T21> mergeInto(Table<R> table, Field<T1> field1, Field<T2> field2, Field<T3> field3, Field<T4> field4, Field<T5> field5, Field<T6> field6, Field<T7> field7, Field<T8> field8, Field<T9> field9, Field<T10> field10, Field<T11> field11, Field<T12> field12, Field<T13> field13, Field<T14> field14, Field<T15> field15, Field<T16> field16, Field<T17> field17, Field<T18> field18, Field<T19> field19, Field<T20> field20, Field<T21> field21) {
      return using((Configuration)(new DefaultConfiguration())).mergeInto(table, field1, field2, field3, field4, field5, field6, field7, field8, field9, field10, field11, field12, field13, field14, field15, field16, field17, field18, field19, field20, field21);
   }

   @Support({SQLDialect.CUBRID, SQLDialect.FIREBIRD_3_0, SQLDialect.H2, SQLDialect.HSQLDB, SQLDialect.MARIADB, SQLDialect.MYSQL, SQLDialect.POSTGRES_9_5})
   public static <R extends Record, T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14, T15, T16, T17, T18, T19, T20, T21, T22> MergeKeyStep22<R, T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14, T15, T16, T17, T18, T19, T20, T21, T22> mergeInto(Table<R> table, Field<T1> field1, Field<T2> field2, Field<T3> field3, Field<T4> field4, Field<T5> field5, Field<T6> field6, Field<T7> field7, Field<T8> field8, Field<T9> field9, Field<T10> field10, Field<T11> field11, Field<T12> field12, Field<T13> field13, Field<T14> field14, Field<T15> field15, Field<T16> field16, Field<T17> field17, Field<T18> field18, Field<T19> field19, Field<T20> field20, Field<T21> field21, Field<T22> field22) {
      return using((Configuration)(new DefaultConfiguration())).mergeInto(table, field1, field2, field3, field4, field5, field6, field7, field8, field9, field10, field11, field12, field13, field14, field15, field16, field17, field18, field19, field20, field21, field22);
   }

   @Support({SQLDialect.CUBRID, SQLDialect.H2, SQLDialect.HSQLDB})
   public static <R extends Record> MergeKeyStepN<R> mergeInto(Table<R> table, Field<?>... fields) {
      return using((Configuration)(new DefaultConfiguration())).mergeInto(table, fields);
   }

   @Support({SQLDialect.CUBRID, SQLDialect.H2, SQLDialect.HSQLDB})
   public static <R extends Record> MergeKeyStepN<R> mergeInto(Table<R> table, Collection<? extends Field<?>> fields) {
      return using((Configuration)(new DefaultConfiguration())).mergeInto(table, fields);
   }

   @Support
   public static <R extends Record> DeleteWhereStep<R> deleteFrom(Table<R> table) {
      return using((Configuration)(new DefaultConfiguration())).deleteFrom(table);
   }

   @Support
   public static <R extends Record> DeleteWhereStep<R> delete(Table<R> table) {
      return using((Configuration)(new DefaultConfiguration())).deleteFrom(table);
   }

   @Support
   public static ConstraintTypeStep constraint() {
      return new ConstraintImpl();
   }

   @Support
   public static ConstraintTypeStep constraint(Name name) {
      return new ConstraintImpl(name);
   }

   @Support
   public static ConstraintTypeStep constraint(String name) {
      return constraint(name(name));
   }

   @Support
   public static ConstraintFinalStep primaryKey(String... fields) {
      return constraint().primaryKey(fields);
   }

   @Support
   public static ConstraintFinalStep primaryKey(Field<?>... fields) {
      return constraint().primaryKey(fields);
   }

   @Support
   public static ConstraintForeignKeyReferencesStepN foreignKey(String... fields) {
      return constraint().foreignKey(fields);
   }

   @Support
   public static ConstraintForeignKeyReferencesStepN foreignKey(Field<?>... fields) {
      return constraint().foreignKey(fields);
   }

   @Support
   public static <T1> ConstraintForeignKeyReferencesStep1<T1> foreignKey(Field<T1> field1) {
      return constraint().foreignKey(field1);
   }

   @Support
   public static <T1, T2> ConstraintForeignKeyReferencesStep2<T1, T2> foreignKey(Field<T1> field1, Field<T2> field2) {
      return constraint().foreignKey(field1, field2);
   }

   @Support
   public static <T1, T2, T3> ConstraintForeignKeyReferencesStep3<T1, T2, T3> foreignKey(Field<T1> field1, Field<T2> field2, Field<T3> field3) {
      return constraint().foreignKey(field1, field2, field3);
   }

   @Support
   public static <T1, T2, T3, T4> ConstraintForeignKeyReferencesStep4<T1, T2, T3, T4> foreignKey(Field<T1> field1, Field<T2> field2, Field<T3> field3, Field<T4> field4) {
      return constraint().foreignKey(field1, field2, field3, field4);
   }

   @Support
   public static <T1, T2, T3, T4, T5> ConstraintForeignKeyReferencesStep5<T1, T2, T3, T4, T5> foreignKey(Field<T1> field1, Field<T2> field2, Field<T3> field3, Field<T4> field4, Field<T5> field5) {
      return constraint().foreignKey(field1, field2, field3, field4, field5);
   }

   @Support
   public static <T1, T2, T3, T4, T5, T6> ConstraintForeignKeyReferencesStep6<T1, T2, T3, T4, T5, T6> foreignKey(Field<T1> field1, Field<T2> field2, Field<T3> field3, Field<T4> field4, Field<T5> field5, Field<T6> field6) {
      return constraint().foreignKey(field1, field2, field3, field4, field5, field6);
   }

   @Support
   public static <T1, T2, T3, T4, T5, T6, T7> ConstraintForeignKeyReferencesStep7<T1, T2, T3, T4, T5, T6, T7> foreignKey(Field<T1> field1, Field<T2> field2, Field<T3> field3, Field<T4> field4, Field<T5> field5, Field<T6> field6, Field<T7> field7) {
      return constraint().foreignKey(field1, field2, field3, field4, field5, field6, field7);
   }

   @Support
   public static <T1, T2, T3, T4, T5, T6, T7, T8> ConstraintForeignKeyReferencesStep8<T1, T2, T3, T4, T5, T6, T7, T8> foreignKey(Field<T1> field1, Field<T2> field2, Field<T3> field3, Field<T4> field4, Field<T5> field5, Field<T6> field6, Field<T7> field7, Field<T8> field8) {
      return constraint().foreignKey(field1, field2, field3, field4, field5, field6, field7, field8);
   }

   @Support
   public static <T1, T2, T3, T4, T5, T6, T7, T8, T9> ConstraintForeignKeyReferencesStep9<T1, T2, T3, T4, T5, T6, T7, T8, T9> foreignKey(Field<T1> field1, Field<T2> field2, Field<T3> field3, Field<T4> field4, Field<T5> field5, Field<T6> field6, Field<T7> field7, Field<T8> field8, Field<T9> field9) {
      return constraint().foreignKey(field1, field2, field3, field4, field5, field6, field7, field8, field9);
   }

   @Support
   public static <T1, T2, T3, T4, T5, T6, T7, T8, T9, T10> ConstraintForeignKeyReferencesStep10<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10> foreignKey(Field<T1> field1, Field<T2> field2, Field<T3> field3, Field<T4> field4, Field<T5> field5, Field<T6> field6, Field<T7> field7, Field<T8> field8, Field<T9> field9, Field<T10> field10) {
      return constraint().foreignKey(field1, field2, field3, field4, field5, field6, field7, field8, field9, field10);
   }

   @Support
   public static <T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11> ConstraintForeignKeyReferencesStep11<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11> foreignKey(Field<T1> field1, Field<T2> field2, Field<T3> field3, Field<T4> field4, Field<T5> field5, Field<T6> field6, Field<T7> field7, Field<T8> field8, Field<T9> field9, Field<T10> field10, Field<T11> field11) {
      return constraint().foreignKey(field1, field2, field3, field4, field5, field6, field7, field8, field9, field10, field11);
   }

   @Support
   public static <T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12> ConstraintForeignKeyReferencesStep12<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12> foreignKey(Field<T1> field1, Field<T2> field2, Field<T3> field3, Field<T4> field4, Field<T5> field5, Field<T6> field6, Field<T7> field7, Field<T8> field8, Field<T9> field9, Field<T10> field10, Field<T11> field11, Field<T12> field12) {
      return constraint().foreignKey(field1, field2, field3, field4, field5, field6, field7, field8, field9, field10, field11, field12);
   }

   @Support
   public static <T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13> ConstraintForeignKeyReferencesStep13<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13> foreignKey(Field<T1> field1, Field<T2> field2, Field<T3> field3, Field<T4> field4, Field<T5> field5, Field<T6> field6, Field<T7> field7, Field<T8> field8, Field<T9> field9, Field<T10> field10, Field<T11> field11, Field<T12> field12, Field<T13> field13) {
      return constraint().foreignKey(field1, field2, field3, field4, field5, field6, field7, field8, field9, field10, field11, field12, field13);
   }

   @Support
   public static <T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14> ConstraintForeignKeyReferencesStep14<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14> foreignKey(Field<T1> field1, Field<T2> field2, Field<T3> field3, Field<T4> field4, Field<T5> field5, Field<T6> field6, Field<T7> field7, Field<T8> field8, Field<T9> field9, Field<T10> field10, Field<T11> field11, Field<T12> field12, Field<T13> field13, Field<T14> field14) {
      return constraint().foreignKey(field1, field2, field3, field4, field5, field6, field7, field8, field9, field10, field11, field12, field13, field14);
   }

   @Support
   public static <T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14, T15> ConstraintForeignKeyReferencesStep15<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14, T15> foreignKey(Field<T1> field1, Field<T2> field2, Field<T3> field3, Field<T4> field4, Field<T5> field5, Field<T6> field6, Field<T7> field7, Field<T8> field8, Field<T9> field9, Field<T10> field10, Field<T11> field11, Field<T12> field12, Field<T13> field13, Field<T14> field14, Field<T15> field15) {
      return constraint().foreignKey(field1, field2, field3, field4, field5, field6, field7, field8, field9, field10, field11, field12, field13, field14, field15);
   }

   @Support
   public static <T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14, T15, T16> ConstraintForeignKeyReferencesStep16<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14, T15, T16> foreignKey(Field<T1> field1, Field<T2> field2, Field<T3> field3, Field<T4> field4, Field<T5> field5, Field<T6> field6, Field<T7> field7, Field<T8> field8, Field<T9> field9, Field<T10> field10, Field<T11> field11, Field<T12> field12, Field<T13> field13, Field<T14> field14, Field<T15> field15, Field<T16> field16) {
      return constraint().foreignKey(field1, field2, field3, field4, field5, field6, field7, field8, field9, field10, field11, field12, field13, field14, field15, field16);
   }

   @Support
   public static <T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14, T15, T16, T17> ConstraintForeignKeyReferencesStep17<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14, T15, T16, T17> foreignKey(Field<T1> field1, Field<T2> field2, Field<T3> field3, Field<T4> field4, Field<T5> field5, Field<T6> field6, Field<T7> field7, Field<T8> field8, Field<T9> field9, Field<T10> field10, Field<T11> field11, Field<T12> field12, Field<T13> field13, Field<T14> field14, Field<T15> field15, Field<T16> field16, Field<T17> field17) {
      return constraint().foreignKey(field1, field2, field3, field4, field5, field6, field7, field8, field9, field10, field11, field12, field13, field14, field15, field16, field17);
   }

   @Support
   public static <T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14, T15, T16, T17, T18> ConstraintForeignKeyReferencesStep18<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14, T15, T16, T17, T18> foreignKey(Field<T1> field1, Field<T2> field2, Field<T3> field3, Field<T4> field4, Field<T5> field5, Field<T6> field6, Field<T7> field7, Field<T8> field8, Field<T9> field9, Field<T10> field10, Field<T11> field11, Field<T12> field12, Field<T13> field13, Field<T14> field14, Field<T15> field15, Field<T16> field16, Field<T17> field17, Field<T18> field18) {
      return constraint().foreignKey(field1, field2, field3, field4, field5, field6, field7, field8, field9, field10, field11, field12, field13, field14, field15, field16, field17, field18);
   }

   @Support
   public static <T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14, T15, T16, T17, T18, T19> ConstraintForeignKeyReferencesStep19<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14, T15, T16, T17, T18, T19> foreignKey(Field<T1> field1, Field<T2> field2, Field<T3> field3, Field<T4> field4, Field<T5> field5, Field<T6> field6, Field<T7> field7, Field<T8> field8, Field<T9> field9, Field<T10> field10, Field<T11> field11, Field<T12> field12, Field<T13> field13, Field<T14> field14, Field<T15> field15, Field<T16> field16, Field<T17> field17, Field<T18> field18, Field<T19> field19) {
      return constraint().foreignKey(field1, field2, field3, field4, field5, field6, field7, field8, field9, field10, field11, field12, field13, field14, field15, field16, field17, field18, field19);
   }

   @Support
   public static <T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14, T15, T16, T17, T18, T19, T20> ConstraintForeignKeyReferencesStep20<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14, T15, T16, T17, T18, T19, T20> foreignKey(Field<T1> field1, Field<T2> field2, Field<T3> field3, Field<T4> field4, Field<T5> field5, Field<T6> field6, Field<T7> field7, Field<T8> field8, Field<T9> field9, Field<T10> field10, Field<T11> field11, Field<T12> field12, Field<T13> field13, Field<T14> field14, Field<T15> field15, Field<T16> field16, Field<T17> field17, Field<T18> field18, Field<T19> field19, Field<T20> field20) {
      return constraint().foreignKey(field1, field2, field3, field4, field5, field6, field7, field8, field9, field10, field11, field12, field13, field14, field15, field16, field17, field18, field19, field20);
   }

   @Support
   public static <T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14, T15, T16, T17, T18, T19, T20, T21> ConstraintForeignKeyReferencesStep21<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14, T15, T16, T17, T18, T19, T20, T21> foreignKey(Field<T1> field1, Field<T2> field2, Field<T3> field3, Field<T4> field4, Field<T5> field5, Field<T6> field6, Field<T7> field7, Field<T8> field8, Field<T9> field9, Field<T10> field10, Field<T11> field11, Field<T12> field12, Field<T13> field13, Field<T14> field14, Field<T15> field15, Field<T16> field16, Field<T17> field17, Field<T18> field18, Field<T19> field19, Field<T20> field20, Field<T21> field21) {
      return constraint().foreignKey(field1, field2, field3, field4, field5, field6, field7, field8, field9, field10, field11, field12, field13, field14, field15, field16, field17, field18, field19, field20, field21);
   }

   @Support
   public static <T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14, T15, T16, T17, T18, T19, T20, T21, T22> ConstraintForeignKeyReferencesStep22<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14, T15, T16, T17, T18, T19, T20, T21, T22> foreignKey(Field<T1> field1, Field<T2> field2, Field<T3> field3, Field<T4> field4, Field<T5> field5, Field<T6> field6, Field<T7> field7, Field<T8> field8, Field<T9> field9, Field<T10> field10, Field<T11> field11, Field<T12> field12, Field<T13> field13, Field<T14> field14, Field<T15> field15, Field<T16> field16, Field<T17> field17, Field<T18> field18, Field<T19> field19, Field<T20> field20, Field<T21> field21, Field<T22> field22) {
      return constraint().foreignKey(field1, field2, field3, field4, field5, field6, field7, field8, field9, field10, field11, field12, field13, field14, field15, field16, field17, field18, field19, field20, field21, field22);
   }

   @Support
   public static ConstraintForeignKeyReferencesStep1<?> foreignKey(String field1) {
      return constraint().foreignKey(field1);
   }

   @Support
   public static ConstraintForeignKeyReferencesStep2<?, ?> foreignKey(String field1, String field2) {
      return constraint().foreignKey(field1, field2);
   }

   @Support
   public static ConstraintForeignKeyReferencesStep3<?, ?, ?> foreignKey(String field1, String field2, String field3) {
      return constraint().foreignKey(field1, field2, field3);
   }

   @Support
   public static ConstraintForeignKeyReferencesStep4<?, ?, ?, ?> foreignKey(String field1, String field2, String field3, String field4) {
      return constraint().foreignKey(field1, field2, field3, field4);
   }

   @Support
   public static ConstraintForeignKeyReferencesStep5<?, ?, ?, ?, ?> foreignKey(String field1, String field2, String field3, String field4, String field5) {
      return constraint().foreignKey(field1, field2, field3, field4, field5);
   }

   @Support
   public static ConstraintForeignKeyReferencesStep6<?, ?, ?, ?, ?, ?> foreignKey(String field1, String field2, String field3, String field4, String field5, String field6) {
      return constraint().foreignKey(field1, field2, field3, field4, field5, field6);
   }

   @Support
   public static ConstraintForeignKeyReferencesStep7<?, ?, ?, ?, ?, ?, ?> foreignKey(String field1, String field2, String field3, String field4, String field5, String field6, String field7) {
      return constraint().foreignKey(field1, field2, field3, field4, field5, field6, field7);
   }

   @Support
   public static ConstraintForeignKeyReferencesStep8<?, ?, ?, ?, ?, ?, ?, ?> foreignKey(String field1, String field2, String field3, String field4, String field5, String field6, String field7, String field8) {
      return constraint().foreignKey(field1, field2, field3, field4, field5, field6, field7, field8);
   }

   @Support
   public static ConstraintForeignKeyReferencesStep9<?, ?, ?, ?, ?, ?, ?, ?, ?> foreignKey(String field1, String field2, String field3, String field4, String field5, String field6, String field7, String field8, String field9) {
      return constraint().foreignKey(field1, field2, field3, field4, field5, field6, field7, field8, field9);
   }

   @Support
   public static ConstraintForeignKeyReferencesStep10<?, ?, ?, ?, ?, ?, ?, ?, ?, ?> foreignKey(String field1, String field2, String field3, String field4, String field5, String field6, String field7, String field8, String field9, String field10) {
      return constraint().foreignKey(field1, field2, field3, field4, field5, field6, field7, field8, field9, field10);
   }

   @Support
   public static ConstraintForeignKeyReferencesStep11<?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?> foreignKey(String field1, String field2, String field3, String field4, String field5, String field6, String field7, String field8, String field9, String field10, String field11) {
      return constraint().foreignKey(field1, field2, field3, field4, field5, field6, field7, field8, field9, field10, field11);
   }

   @Support
   public static ConstraintForeignKeyReferencesStep12<?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?> foreignKey(String field1, String field2, String field3, String field4, String field5, String field6, String field7, String field8, String field9, String field10, String field11, String field12) {
      return constraint().foreignKey(field1, field2, field3, field4, field5, field6, field7, field8, field9, field10, field11, field12);
   }

   @Support
   public static ConstraintForeignKeyReferencesStep13<?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?> foreignKey(String field1, String field2, String field3, String field4, String field5, String field6, String field7, String field8, String field9, String field10, String field11, String field12, String field13) {
      return constraint().foreignKey(field1, field2, field3, field4, field5, field6, field7, field8, field9, field10, field11, field12, field13);
   }

   @Support
   public static ConstraintForeignKeyReferencesStep14<?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?> foreignKey(String field1, String field2, String field3, String field4, String field5, String field6, String field7, String field8, String field9, String field10, String field11, String field12, String field13, String field14) {
      return constraint().foreignKey(field1, field2, field3, field4, field5, field6, field7, field8, field9, field10, field11, field12, field13, field14);
   }

   @Support
   public static ConstraintForeignKeyReferencesStep15<?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?> foreignKey(String field1, String field2, String field3, String field4, String field5, String field6, String field7, String field8, String field9, String field10, String field11, String field12, String field13, String field14, String field15) {
      return constraint().foreignKey(field1, field2, field3, field4, field5, field6, field7, field8, field9, field10, field11, field12, field13, field14, field15);
   }

   @Support
   public static ConstraintForeignKeyReferencesStep16<?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?> foreignKey(String field1, String field2, String field3, String field4, String field5, String field6, String field7, String field8, String field9, String field10, String field11, String field12, String field13, String field14, String field15, String field16) {
      return constraint().foreignKey(field1, field2, field3, field4, field5, field6, field7, field8, field9, field10, field11, field12, field13, field14, field15, field16);
   }

   @Support
   public static ConstraintForeignKeyReferencesStep17<?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?> foreignKey(String field1, String field2, String field3, String field4, String field5, String field6, String field7, String field8, String field9, String field10, String field11, String field12, String field13, String field14, String field15, String field16, String field17) {
      return constraint().foreignKey(field1, field2, field3, field4, field5, field6, field7, field8, field9, field10, field11, field12, field13, field14, field15, field16, field17);
   }

   @Support
   public static ConstraintForeignKeyReferencesStep18<?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?> foreignKey(String field1, String field2, String field3, String field4, String field5, String field6, String field7, String field8, String field9, String field10, String field11, String field12, String field13, String field14, String field15, String field16, String field17, String field18) {
      return constraint().foreignKey(field1, field2, field3, field4, field5, field6, field7, field8, field9, field10, field11, field12, field13, field14, field15, field16, field17, field18);
   }

   @Support
   public static ConstraintForeignKeyReferencesStep19<?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?> foreignKey(String field1, String field2, String field3, String field4, String field5, String field6, String field7, String field8, String field9, String field10, String field11, String field12, String field13, String field14, String field15, String field16, String field17, String field18, String field19) {
      return constraint().foreignKey(field1, field2, field3, field4, field5, field6, field7, field8, field9, field10, field11, field12, field13, field14, field15, field16, field17, field18, field19);
   }

   @Support
   public static ConstraintForeignKeyReferencesStep20<?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?> foreignKey(String field1, String field2, String field3, String field4, String field5, String field6, String field7, String field8, String field9, String field10, String field11, String field12, String field13, String field14, String field15, String field16, String field17, String field18, String field19, String field20) {
      return constraint().foreignKey(field1, field2, field3, field4, field5, field6, field7, field8, field9, field10, field11, field12, field13, field14, field15, field16, field17, field18, field19, field20);
   }

   @Support
   public static ConstraintForeignKeyReferencesStep21<?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?> foreignKey(String field1, String field2, String field3, String field4, String field5, String field6, String field7, String field8, String field9, String field10, String field11, String field12, String field13, String field14, String field15, String field16, String field17, String field18, String field19, String field20, String field21) {
      return constraint().foreignKey(field1, field2, field3, field4, field5, field6, field7, field8, field9, field10, field11, field12, field13, field14, field15, field16, field17, field18, field19, field20, field21);
   }

   @Support
   public static ConstraintForeignKeyReferencesStep22<?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?> foreignKey(String field1, String field2, String field3, String field4, String field5, String field6, String field7, String field8, String field9, String field10, String field11, String field12, String field13, String field14, String field15, String field16, String field17, String field18, String field19, String field20, String field21, String field22) {
      return constraint().foreignKey(field1, field2, field3, field4, field5, field6, field7, field8, field9, field10, field11, field12, field13, field14, field15, field16, field17, field18, field19, field20, field21, field22);
   }

   @Support
   public static ConstraintFinalStep unique(String... fields) {
      return constraint().unique(fields);
   }

   @Support
   public static ConstraintFinalStep unique(Field<?>... fields) {
      return constraint().unique(fields);
   }

   @Support({SQLDialect.CUBRID, SQLDialect.DERBY, SQLDialect.FIREBIRD, SQLDialect.H2, SQLDialect.HSQLDB, SQLDialect.POSTGRES, SQLDialect.SQLITE})
   public static ConstraintFinalStep check(Condition condition) {
      return constraint().check(condition);
   }

   @Support({SQLDialect.H2, SQLDialect.HSQLDB, SQLDialect.POSTGRES})
   public static CreateSchemaFinalStep createSchema(String schema) {
      return using((Configuration)(new DefaultConfiguration())).createSchema(schema);
   }

   @Support({SQLDialect.H2, SQLDialect.HSQLDB, SQLDialect.POSTGRES})
   public static CreateSchemaFinalStep createSchema(Name table) {
      return using((Configuration)(new DefaultConfiguration())).createSchema(table);
   }

   @Support({SQLDialect.H2, SQLDialect.HSQLDB, SQLDialect.POSTGRES})
   public static CreateSchemaFinalStep createSchema(Schema schema) {
      return using((Configuration)(new DefaultConfiguration())).createSchema(schema);
   }

   @Support({SQLDialect.H2, SQLDialect.POSTGRES})
   public static CreateSchemaFinalStep createSchemaIfNotExists(String schema) {
      return using((Configuration)(new DefaultConfiguration())).createSchemaIfNotExists(schema);
   }

   @Support({SQLDialect.H2, SQLDialect.POSTGRES})
   public static CreateSchemaFinalStep createSchemaIfNotExists(Name table) {
      return using((Configuration)(new DefaultConfiguration())).createSchemaIfNotExists(table);
   }

   @Support({SQLDialect.H2, SQLDialect.POSTGRES})
   public static CreateSchemaFinalStep createSchemaIfNotExists(Schema schema) {
      return using((Configuration)(new DefaultConfiguration())).createSchemaIfNotExists(schema);
   }

   @Support({SQLDialect.CUBRID, SQLDialect.DERBY, SQLDialect.FIREBIRD, SQLDialect.H2, SQLDialect.HSQLDB, SQLDialect.MARIADB, SQLDialect.MYSQL, SQLDialect.POSTGRES, SQLDialect.SQLITE})
   public static CreateTableAsStep<Record> createTable(String table) {
      return using((Configuration)(new DefaultConfiguration())).createTable(table);
   }

   @Support({SQLDialect.CUBRID, SQLDialect.DERBY, SQLDialect.FIREBIRD, SQLDialect.H2, SQLDialect.HSQLDB, SQLDialect.MARIADB, SQLDialect.MYSQL, SQLDialect.POSTGRES, SQLDialect.SQLITE})
   public static CreateTableAsStep<Record> createTable(Name table) {
      return using((Configuration)(new DefaultConfiguration())).createTable(table);
   }

   @Support({SQLDialect.CUBRID, SQLDialect.DERBY, SQLDialect.FIREBIRD, SQLDialect.H2, SQLDialect.HSQLDB, SQLDialect.MARIADB, SQLDialect.MYSQL, SQLDialect.POSTGRES, SQLDialect.SQLITE})
   public static CreateTableAsStep<Record> createTable(Table<?> table) {
      return using((Configuration)(new DefaultConfiguration())).createTable(table);
   }

   @Support({SQLDialect.FIREBIRD, SQLDialect.H2, SQLDialect.HSQLDB, SQLDialect.MARIADB, SQLDialect.MYSQL, SQLDialect.POSTGRES, SQLDialect.SQLITE})
   public static CreateTableAsStep<Record> createTableIfNotExists(String table) {
      return using((Configuration)(new DefaultConfiguration())).createTableIfNotExists(table);
   }

   @Support({SQLDialect.FIREBIRD, SQLDialect.H2, SQLDialect.HSQLDB, SQLDialect.MARIADB, SQLDialect.MYSQL, SQLDialect.POSTGRES, SQLDialect.SQLITE})
   public static CreateTableAsStep<Record> createTableIfNotExists(Name table) {
      return using((Configuration)(new DefaultConfiguration())).createTableIfNotExists(table);
   }

   @Support({SQLDialect.FIREBIRD, SQLDialect.H2, SQLDialect.HSQLDB, SQLDialect.MARIADB, SQLDialect.MYSQL, SQLDialect.POSTGRES, SQLDialect.SQLITE})
   public static CreateTableAsStep<Record> createTableIfNotExists(Table<?> table) {
      return using((Configuration)(new DefaultConfiguration())).createTableIfNotExists(table);
   }

   @Support({SQLDialect.MARIADB, SQLDialect.MYSQL, SQLDialect.POSTGRES})
   public static CreateTableAsStep<Record> createTemporaryTable(String table) {
      return using((Configuration)(new DefaultConfiguration())).createTemporaryTable(table);
   }

   @Support({SQLDialect.MARIADB, SQLDialect.MYSQL, SQLDialect.POSTGRES})
   public static CreateTableAsStep<Record> createTemporaryTable(Name table) {
      return using((Configuration)(new DefaultConfiguration())).createTemporaryTable(table);
   }

   @Support({SQLDialect.MARIADB, SQLDialect.MYSQL, SQLDialect.POSTGRES})
   public static CreateTableAsStep<Record> createTemporaryTable(Table<?> table) {
      return using((Configuration)(new DefaultConfiguration())).createTemporaryTable(table);
   }

   @Support({SQLDialect.MARIADB, SQLDialect.MYSQL, SQLDialect.POSTGRES})
   public static CreateTableAsStep<Record> createGlobalTemporaryTable(String table) {
      return using((Configuration)(new DefaultConfiguration())).createGlobalTemporaryTable(table);
   }

   @Support({SQLDialect.MARIADB, SQLDialect.MYSQL, SQLDialect.POSTGRES})
   public static CreateTableAsStep<Record> createGlobalTemporaryTable(Name table) {
      return using((Configuration)(new DefaultConfiguration())).createGlobalTemporaryTable(table);
   }

   @Support({SQLDialect.MARIADB, SQLDialect.MYSQL, SQLDialect.POSTGRES})
   public static CreateTableAsStep<Record> createGlobalTemporaryTable(Table<?> table) {
      return using((Configuration)(new DefaultConfiguration())).createGlobalTemporaryTable(table);
   }

   @Support
   public static CreateViewAsStep createView(String view, String... fields) {
      return using((Configuration)(new DefaultConfiguration())).createView(view, fields);
   }

   @Support
   public static CreateViewAsStep createView(Name view, Name... fields) {
      return using((Configuration)(new DefaultConfiguration())).createView(view, fields);
   }

   @Support
   public static CreateViewAsStep createView(Table<?> view, Field<?>... fields) {
      return using((Configuration)(new DefaultConfiguration())).createView(view, fields);
   }

   @Support
   public static CreateViewAsStep createView(String view, java.util.function.Function<? super Field<?>, ? extends String> fieldNameFunction) {
      return using((Configuration)(new DefaultConfiguration())).createView(view, fieldNameFunction);
   }

   @Support
   public static CreateViewAsStep createView(Name view, java.util.function.Function<? super Field<?>, ? extends Name> fieldNameFunction) {
      return using((Configuration)(new DefaultConfiguration())).createView(view, fieldNameFunction);
   }

   @Support
   public static CreateViewAsStep createView(Table<?> view, java.util.function.Function<? super Field<?>, ? extends Field<?>> fieldNameFunction) {
      return using((Configuration)(new DefaultConfiguration())).createView(view, fieldNameFunction);
   }

   @Support({SQLDialect.FIREBIRD, SQLDialect.H2, SQLDialect.HSQLDB, SQLDialect.MARIADB, SQLDialect.MYSQL, SQLDialect.POSTGRES, SQLDialect.SQLITE})
   public static CreateViewAsStep createViewIfNotExists(String view, String... fields) {
      return using((Configuration)(new DefaultConfiguration())).createViewIfNotExists(view, fields);
   }

   @Support({SQLDialect.FIREBIRD, SQLDialect.H2, SQLDialect.HSQLDB, SQLDialect.MARIADB, SQLDialect.MYSQL, SQLDialect.POSTGRES, SQLDialect.SQLITE})
   public static CreateViewAsStep createViewIfNotExists(Name view, Name... fields) {
      return using((Configuration)(new DefaultConfiguration())).createViewIfNotExists(view, fields);
   }

   @Support({SQLDialect.FIREBIRD, SQLDialect.H2, SQLDialect.HSQLDB, SQLDialect.MARIADB, SQLDialect.MYSQL, SQLDialect.POSTGRES, SQLDialect.SQLITE})
   public static CreateViewAsStep createViewIfNotExists(Table<?> view, Field<?>... fields) {
      return using((Configuration)(new DefaultConfiguration())).createViewIfNotExists(view, fields);
   }

   @Support({SQLDialect.FIREBIRD, SQLDialect.H2, SQLDialect.HSQLDB, SQLDialect.MARIADB, SQLDialect.MYSQL, SQLDialect.POSTGRES, SQLDialect.SQLITE})
   public static CreateViewAsStep createViewIfNotExists(String view, java.util.function.Function<? super Field<?>, ? extends String> fieldNameFunction) {
      return using((Configuration)(new DefaultConfiguration())).createViewIfNotExists(view, fieldNameFunction);
   }

   @Support({SQLDialect.FIREBIRD, SQLDialect.H2, SQLDialect.HSQLDB, SQLDialect.MARIADB, SQLDialect.MYSQL, SQLDialect.POSTGRES, SQLDialect.SQLITE})
   public static CreateViewAsStep createViewIfNotExists(Name view, java.util.function.Function<? super Field<?>, ? extends Name> fieldNameFunction) {
      return using((Configuration)(new DefaultConfiguration())).createViewIfNotExists(view, fieldNameFunction);
   }

   @Support({SQLDialect.FIREBIRD, SQLDialect.H2, SQLDialect.HSQLDB, SQLDialect.MARIADB, SQLDialect.MYSQL, SQLDialect.POSTGRES, SQLDialect.SQLITE})
   public static CreateViewAsStep createViewIfNotExists(Table<?> view, java.util.function.Function<? super Field<?>, ? extends Field<?>> fieldNameFunction) {
      return using((Configuration)(new DefaultConfiguration())).createViewIfNotExists(view, fieldNameFunction);
   }

   @Support({SQLDialect.CUBRID, SQLDialect.DERBY, SQLDialect.FIREBIRD, SQLDialect.H2, SQLDialect.HSQLDB, SQLDialect.MARIADB, SQLDialect.MYSQL, SQLDialect.POSTGRES, SQLDialect.SQLITE})
   public static CreateIndexStep createIndex(String index) {
      return using((Configuration)(new DefaultConfiguration())).createIndex(index);
   }

   @Support({SQLDialect.CUBRID, SQLDialect.DERBY, SQLDialect.FIREBIRD, SQLDialect.H2, SQLDialect.HSQLDB, SQLDialect.MARIADB, SQLDialect.MYSQL, SQLDialect.POSTGRES, SQLDialect.SQLITE})
   public static CreateIndexStep createIndex(Name index) {
      return using((Configuration)(new DefaultConfiguration())).createIndex(index);
   }

   @Support({SQLDialect.FIREBIRD, SQLDialect.H2, SQLDialect.HSQLDB, SQLDialect.POSTGRES, SQLDialect.SQLITE})
   public static CreateIndexStep createIndexIfNotExists(String index) {
      return using((Configuration)(new DefaultConfiguration())).createIndexIfNotExists(index);
   }

   @Support({SQLDialect.FIREBIRD, SQLDialect.H2, SQLDialect.HSQLDB, SQLDialect.POSTGRES, SQLDialect.SQLITE})
   public static CreateIndexStep createIndexIfNotExists(Name index) {
      return using((Configuration)(new DefaultConfiguration())).createIndexIfNotExists(index);
   }

   @Support({SQLDialect.CUBRID, SQLDialect.DERBY, SQLDialect.FIREBIRD, SQLDialect.H2, SQLDialect.HSQLDB, SQLDialect.MARIADB, SQLDialect.MYSQL, SQLDialect.POSTGRES, SQLDialect.SQLITE})
   public static CreateIndexStep createUniqueIndex(String index) {
      return using((Configuration)(new DefaultConfiguration())).createUniqueIndex(index);
   }

   @Support({SQLDialect.CUBRID, SQLDialect.DERBY, SQLDialect.FIREBIRD, SQLDialect.H2, SQLDialect.HSQLDB, SQLDialect.MARIADB, SQLDialect.MYSQL, SQLDialect.POSTGRES, SQLDialect.SQLITE})
   public static CreateIndexStep createUniqueIndex(Name index) {
      return using((Configuration)(new DefaultConfiguration())).createUniqueIndex(index);
   }

   @Support({SQLDialect.FIREBIRD, SQLDialect.H2, SQLDialect.HSQLDB, SQLDialect.POSTGRES, SQLDialect.SQLITE})
   public static CreateIndexStep createUniqueIndexIfNotExists(String index) {
      return using((Configuration)(new DefaultConfiguration())).createUniqueIndexIfNotExists(index);
   }

   @Support({SQLDialect.FIREBIRD, SQLDialect.H2, SQLDialect.HSQLDB, SQLDialect.POSTGRES, SQLDialect.SQLITE})
   public static CreateIndexStep createUniqueIndexIfNotExists(Name index) {
      return using((Configuration)(new DefaultConfiguration())).createUniqueIndexIfNotExists(index);
   }

   @Support({SQLDialect.CUBRID, SQLDialect.DERBY, SQLDialect.FIREBIRD, SQLDialect.H2, SQLDialect.HSQLDB, SQLDialect.POSTGRES})
   public static CreateSequenceFinalStep createSequence(String sequence) {
      return using((Configuration)(new DefaultConfiguration())).createSequence(sequence);
   }

   @Support({SQLDialect.CUBRID, SQLDialect.DERBY, SQLDialect.FIREBIRD, SQLDialect.H2, SQLDialect.HSQLDB, SQLDialect.POSTGRES})
   public static CreateSequenceFinalStep createSequence(Name sequence) {
      return using((Configuration)(new DefaultConfiguration())).createSequence(sequence);
   }

   @Support({SQLDialect.CUBRID, SQLDialect.DERBY, SQLDialect.FIREBIRD, SQLDialect.H2, SQLDialect.HSQLDB, SQLDialect.POSTGRES})
   public static CreateSequenceFinalStep createSequence(Sequence<?> sequence) {
      return using((Configuration)(new DefaultConfiguration())).createSequence(sequence);
   }

   @Support({SQLDialect.FIREBIRD, SQLDialect.H2, SQLDialect.HSQLDB, SQLDialect.POSTGRES})
   public static CreateSequenceFinalStep createSequenceIfNotExists(String sequence) {
      return using((Configuration)(new DefaultConfiguration())).createSequenceIfNotExists(sequence);
   }

   @Support({SQLDialect.FIREBIRD, SQLDialect.H2, SQLDialect.HSQLDB, SQLDialect.POSTGRES})
   public static CreateSequenceFinalStep createSequenceIfNotExists(Name sequence) {
      return using((Configuration)(new DefaultConfiguration())).createSequenceIfNotExists(sequence);
   }

   @Support({SQLDialect.FIREBIRD, SQLDialect.H2, SQLDialect.HSQLDB, SQLDialect.POSTGRES})
   public static CreateSequenceFinalStep createSequenceIfNotExists(Sequence<?> sequence) {
      return using((Configuration)(new DefaultConfiguration())).createSequenceIfNotExists(sequence);
   }

   @Support({SQLDialect.CUBRID, SQLDialect.FIREBIRD, SQLDialect.H2, SQLDialect.HSQLDB, SQLDialect.POSTGRES})
   public static AlterSequenceStep<BigInteger> alterSequence(String sequence) {
      return using((Configuration)(new DefaultConfiguration())).alterSequence(sequence);
   }

   @Support({SQLDialect.CUBRID, SQLDialect.FIREBIRD, SQLDialect.H2, SQLDialect.HSQLDB, SQLDialect.POSTGRES})
   public static AlterSequenceStep<BigInteger> alterSequence(Name sequence) {
      return using((Configuration)(new DefaultConfiguration())).alterSequence(sequence);
   }

   @Support({SQLDialect.CUBRID, SQLDialect.FIREBIRD, SQLDialect.H2, SQLDialect.HSQLDB, SQLDialect.POSTGRES})
   public static <T extends Number> AlterSequenceStep<T> alterSequence(Sequence<T> sequence) {
      return using((Configuration)(new DefaultConfiguration())).alterSequence(sequence);
   }

   @Support({SQLDialect.POSTGRES})
   public static AlterSequenceStep<BigInteger> alterSequenceIfExists(String sequence) {
      return using((Configuration)(new DefaultConfiguration())).alterSequenceIfExists(sequence);
   }

   @Support({SQLDialect.POSTGRES})
   public static AlterSequenceStep<BigInteger> alterSequenceIfExists(Name sequence) {
      return using((Configuration)(new DefaultConfiguration())).alterSequenceIfExists(sequence);
   }

   @Support({SQLDialect.POSTGRES})
   public static <T extends Number> AlterSequenceStep<T> alterSequenceIfExists(Sequence<T> sequence) {
      return using((Configuration)(new DefaultConfiguration())).alterSequenceIfExists(sequence);
   }

   @Support
   public static AlterTableStep alterTable(String table) {
      return using((Configuration)(new DefaultConfiguration())).alterTable(table);
   }

   @Support
   public static AlterTableStep alterTable(Name table) {
      return using((Configuration)(new DefaultConfiguration())).alterTable(table);
   }

   @Support
   public static AlterTableStep alterTable(Table<?> table) {
      return using((Configuration)(new DefaultConfiguration())).alterTable(table);
   }

   @Support({SQLDialect.H2, SQLDialect.POSTGRES})
   public static AlterTableStep alterTableIfExists(String table) {
      return using((Configuration)(new DefaultConfiguration())).alterTableIfExists(table);
   }

   @Support({SQLDialect.H2, SQLDialect.POSTGRES})
   public static AlterTableStep alterTableIfExists(Name table) {
      return using((Configuration)(new DefaultConfiguration())).alterTableIfExists(table);
   }

   @Support({SQLDialect.H2, SQLDialect.POSTGRES})
   public static AlterTableStep alterTableIfExists(Table<?> table) {
      return using((Configuration)(new DefaultConfiguration())).alterTableIfExists(table);
   }

   @Support({SQLDialect.HSQLDB, SQLDialect.POSTGRES})
   public static AlterSchemaStep alterSchema(String schema) {
      return using((Configuration)(new DefaultConfiguration())).alterSchema(schema);
   }

   @Support({SQLDialect.HSQLDB, SQLDialect.POSTGRES})
   public static AlterSchemaStep alterSchema(Name schema) {
      return using((Configuration)(new DefaultConfiguration())).alterSchema(schema);
   }

   @Support({SQLDialect.HSQLDB, SQLDialect.POSTGRES})
   public static AlterSchemaStep alterSchema(Schema schema) {
      return using((Configuration)(new DefaultConfiguration())).alterSchema(schema);
   }

   @Support({SQLDialect.POSTGRES})
   public static AlterSchemaStep alterSchemaIfExists(String schema) {
      return using((Configuration)(new DefaultConfiguration())).alterSchemaIfExists(schema);
   }

   @Support({SQLDialect.POSTGRES})
   public static AlterSchemaStep alterSchemaIfExists(Name schema) {
      return using((Configuration)(new DefaultConfiguration())).alterSchemaIfExists(schema);
   }

   @Support({SQLDialect.POSTGRES})
   public static AlterSchemaStep alterSchemaIfExists(Schema schema) {
      return using((Configuration)(new DefaultConfiguration())).alterSchemaIfExists(schema);
   }

   @Support({SQLDialect.HSQLDB, SQLDialect.POSTGRES})
   public static AlterViewStep alterView(String view) {
      return using((Configuration)(new DefaultConfiguration())).alterView(view);
   }

   @Support({SQLDialect.HSQLDB, SQLDialect.POSTGRES})
   public static AlterViewStep alterView(Name view) {
      return using((Configuration)(new DefaultConfiguration())).alterView(view);
   }

   @Support({SQLDialect.HSQLDB, SQLDialect.POSTGRES})
   public static AlterViewStep alterView(Table<?> view) {
      return using((Configuration)(new DefaultConfiguration())).alterView(view);
   }

   @Support({SQLDialect.POSTGRES})
   public static AlterViewStep alterViewIfExists(String view) {
      return using((Configuration)(new DefaultConfiguration())).alterViewIfExists(view);
   }

   @Support({SQLDialect.POSTGRES})
   public static AlterViewStep alterViewIfExists(Name view) {
      return using((Configuration)(new DefaultConfiguration())).alterViewIfExists(view);
   }

   @Support({SQLDialect.POSTGRES})
   public static AlterViewStep alterViewIfExists(Table<?> view) {
      return using((Configuration)(new DefaultConfiguration())).alterViewIfExists(view);
   }

   @Support({SQLDialect.H2, SQLDialect.HSQLDB, SQLDialect.POSTGRES})
   public static AlterIndexStep alterIndex(String index) {
      return using((Configuration)(new DefaultConfiguration())).alterIndex(index);
   }

   @Support({SQLDialect.H2, SQLDialect.HSQLDB, SQLDialect.POSTGRES})
   public static AlterIndexStep alterIndex(Name index) {
      return using((Configuration)(new DefaultConfiguration())).alterIndex(index);
   }

   @Support({SQLDialect.H2, SQLDialect.POSTGRES})
   public static AlterIndexStep alterIndexIfExists(String index) {
      return using((Configuration)(new DefaultConfiguration())).alterIndexIfExists(index);
   }

   @Support({SQLDialect.H2, SQLDialect.POSTGRES})
   public static AlterIndexStep alterIndexIfExists(Name index) {
      return using((Configuration)(new DefaultConfiguration())).alterIndexIfExists(index);
   }

   @Support({SQLDialect.H2, SQLDialect.HSQLDB, SQLDialect.POSTGRES})
   public static DropSchemaStep dropSchema(String schema) {
      return using((Configuration)(new DefaultConfiguration())).dropSchema(schema);
   }

   @Support({SQLDialect.H2, SQLDialect.HSQLDB, SQLDialect.POSTGRES})
   public static DropSchemaStep dropSchema(Name schema) {
      return using((Configuration)(new DefaultConfiguration())).dropSchema(schema);
   }

   @Support({SQLDialect.H2, SQLDialect.HSQLDB, SQLDialect.POSTGRES})
   public static DropSchemaStep dropSchema(Schema schema) {
      return using((Configuration)(new DefaultConfiguration())).dropSchema(schema);
   }

   @Support({SQLDialect.H2, SQLDialect.POSTGRES})
   public static DropSchemaStep dropSchemaIfExists(String schema) {
      return using((Configuration)(new DefaultConfiguration())).dropSchemaIfExists(schema);
   }

   @Support({SQLDialect.H2, SQLDialect.POSTGRES})
   public static DropSchemaStep dropSchemaIfExists(Name schema) {
      return using((Configuration)(new DefaultConfiguration())).dropSchemaIfExists(schema);
   }

   @Support({SQLDialect.H2, SQLDialect.POSTGRES})
   public static DropSchemaStep dropSchemaIfExists(Schema schema) {
      return using((Configuration)(new DefaultConfiguration())).dropSchemaIfExists(schema);
   }

   @Support
   public static DropViewFinalStep dropView(String view) {
      return using((Configuration)(new DefaultConfiguration())).dropView(view);
   }

   @Support
   public static DropViewFinalStep dropView(Name view) {
      return using((Configuration)(new DefaultConfiguration())).dropView(view);
   }

   @Support
   public static DropViewFinalStep dropView(Table<?> view) {
      return using((Configuration)(new DefaultConfiguration())).dropView(view);
   }

   @Support({SQLDialect.CUBRID, SQLDialect.DERBY, SQLDialect.FIREBIRD, SQLDialect.H2, SQLDialect.HSQLDB, SQLDialect.MARIADB, SQLDialect.MYSQL, SQLDialect.POSTGRES, SQLDialect.SQLITE})
   public static DropViewFinalStep dropViewIfExists(String view) {
      return using((Configuration)(new DefaultConfiguration())).dropViewIfExists(view);
   }

   @Support({SQLDialect.CUBRID, SQLDialect.DERBY, SQLDialect.FIREBIRD, SQLDialect.H2, SQLDialect.HSQLDB, SQLDialect.MARIADB, SQLDialect.MYSQL, SQLDialect.POSTGRES, SQLDialect.SQLITE})
   public static DropViewFinalStep dropViewIfExists(Name view) {
      return using((Configuration)(new DefaultConfiguration())).dropViewIfExists(view);
   }

   @Support({SQLDialect.CUBRID, SQLDialect.DERBY, SQLDialect.FIREBIRD, SQLDialect.H2, SQLDialect.HSQLDB, SQLDialect.MARIADB, SQLDialect.MYSQL, SQLDialect.POSTGRES, SQLDialect.SQLITE})
   public static DropViewFinalStep dropViewIfExists(Table<?> view) {
      return using((Configuration)(new DefaultConfiguration())).dropViewIfExists(view);
   }

   @Support
   public static DropTableStep dropTable(String table) {
      return using((Configuration)(new DefaultConfiguration())).dropTable(table);
   }

   @Support
   public static DropTableStep dropTable(Name table) {
      return using((Configuration)(new DefaultConfiguration())).dropTable(table);
   }

   @Support
   public static DropTableStep dropTable(Table<?> table) {
      return using((Configuration)(new DefaultConfiguration())).dropTable(table);
   }

   @Support({SQLDialect.CUBRID, SQLDialect.DERBY, SQLDialect.FIREBIRD, SQLDialect.H2, SQLDialect.HSQLDB, SQLDialect.MARIADB, SQLDialect.MYSQL, SQLDialect.POSTGRES, SQLDialect.SQLITE})
   public static DropTableStep dropTableIfExists(String table) {
      return using((Configuration)(new DefaultConfiguration())).dropTableIfExists(table);
   }

   @Support({SQLDialect.CUBRID, SQLDialect.DERBY, SQLDialect.FIREBIRD, SQLDialect.H2, SQLDialect.HSQLDB, SQLDialect.MARIADB, SQLDialect.MYSQL, SQLDialect.POSTGRES, SQLDialect.SQLITE})
   public static DropTableStep dropTableIfExists(Name table) {
      return using((Configuration)(new DefaultConfiguration())).dropTableIfExists(table);
   }

   @Support({SQLDialect.CUBRID, SQLDialect.DERBY, SQLDialect.FIREBIRD, SQLDialect.H2, SQLDialect.HSQLDB, SQLDialect.MARIADB, SQLDialect.MYSQL, SQLDialect.POSTGRES, SQLDialect.SQLITE})
   public static DropTableStep dropTableIfExists(Table<?> table) {
      return using((Configuration)(new DefaultConfiguration())).dropTableIfExists(table);
   }

   @Support
   public static DropIndexOnStep dropIndex(String index) {
      return using((Configuration)(new DefaultConfiguration())).dropIndex(index);
   }

   @Support
   public static DropIndexOnStep dropIndex(Name index) {
      return using((Configuration)(new DefaultConfiguration())).dropIndex(index);
   }

   @Support({SQLDialect.CUBRID, SQLDialect.DERBY, SQLDialect.FIREBIRD, SQLDialect.H2, SQLDialect.HSQLDB, SQLDialect.MARIADB, SQLDialect.MYSQL, SQLDialect.POSTGRES, SQLDialect.SQLITE})
   public static DropIndexOnStep dropIndexIfExists(String index) {
      return using((Configuration)(new DefaultConfiguration())).dropIndexIfExists(index);
   }

   @Support({SQLDialect.CUBRID, SQLDialect.DERBY, SQLDialect.FIREBIRD, SQLDialect.H2, SQLDialect.HSQLDB, SQLDialect.MARIADB, SQLDialect.MYSQL, SQLDialect.POSTGRES, SQLDialect.SQLITE})
   public static DropIndexOnStep dropIndexIfExists(Name index) {
      return using((Configuration)(new DefaultConfiguration())).dropIndexIfExists(index);
   }

   @Support({SQLDialect.CUBRID, SQLDialect.DERBY, SQLDialect.FIREBIRD, SQLDialect.H2, SQLDialect.HSQLDB, SQLDialect.POSTGRES})
   public static <T extends Number> DropSequenceFinalStep dropSequence(String sequence) {
      return using((Configuration)(new DefaultConfiguration())).dropSequence(sequence);
   }

   @Support({SQLDialect.CUBRID, SQLDialect.DERBY, SQLDialect.FIREBIRD, SQLDialect.H2, SQLDialect.HSQLDB, SQLDialect.POSTGRES})
   public static <T extends Number> DropSequenceFinalStep dropSequence(Name sequence) {
      return using((Configuration)(new DefaultConfiguration())).dropSequence(sequence);
   }

   @Support({SQLDialect.CUBRID, SQLDialect.DERBY, SQLDialect.FIREBIRD, SQLDialect.H2, SQLDialect.HSQLDB, SQLDialect.POSTGRES})
   public static <T extends Number> DropSequenceFinalStep dropSequence(Sequence<?> sequence) {
      return using((Configuration)(new DefaultConfiguration())).dropSequence(sequence);
   }

   @Support({SQLDialect.CUBRID, SQLDialect.FIREBIRD, SQLDialect.H2, SQLDialect.HSQLDB, SQLDialect.POSTGRES})
   public static <T extends Number> DropSequenceFinalStep dropSequenceIfExists(String sequence) {
      return using((Configuration)(new DefaultConfiguration())).dropSequenceIfExists(sequence);
   }

   @Support({SQLDialect.CUBRID, SQLDialect.FIREBIRD, SQLDialect.H2, SQLDialect.HSQLDB, SQLDialect.POSTGRES})
   public static <T extends Number> DropSequenceFinalStep dropSequenceIfExists(Name sequence) {
      return using((Configuration)(new DefaultConfiguration())).dropSequenceIfExists(sequence);
   }

   @Support({SQLDialect.CUBRID, SQLDialect.FIREBIRD, SQLDialect.H2, SQLDialect.HSQLDB, SQLDialect.POSTGRES})
   public static <T extends Number> DropSequenceFinalStep dropSequenceIfExists(Sequence<?> sequence) {
      return using((Configuration)(new DefaultConfiguration())).dropSequenceIfExists(sequence);
   }

   @Support
   public static TruncateIdentityStep<Record> truncate(Name table) {
      return using((Configuration)(new DefaultConfiguration())).truncate(table);
   }

   @Support
   public static <R extends Record> TruncateIdentityStep<R> truncate(Table<R> table) {
      return using((Configuration)(new DefaultConfiguration())).truncate(table);
   }

   @Support({SQLDialect.CUBRID, SQLDialect.DERBY, SQLDialect.FIREBIRD, SQLDialect.H2, SQLDialect.HSQLDB, SQLDialect.MARIADB, SQLDialect.MYSQL, SQLDialect.POSTGRES})
   public static <R extends Record> QuantifiedSelect<R> all(Select<R> select) {
      return new QuantifiedSelectImpl(Quantifier.ALL, select);
   }

   @Support({SQLDialect.CUBRID, SQLDialect.DERBY, SQLDialect.FIREBIRD, SQLDialect.H2, SQLDialect.HSQLDB, SQLDialect.MARIADB, SQLDialect.MYSQL, SQLDialect.POSTGRES})
   public static <T> QuantifiedSelect<Record1<T>> all(T... array) {
      return all((Field)val((Object)array));
   }

   @Support({SQLDialect.H2, SQLDialect.HSQLDB, SQLDialect.POSTGRES})
   public static <T> QuantifiedSelect<Record1<T>> all(Field<T[]> array) {
      return new QuantifiedSelectImpl(Quantifier.ALL, array);
   }

   @Support({SQLDialect.CUBRID, SQLDialect.DERBY, SQLDialect.FIREBIRD, SQLDialect.H2, SQLDialect.HSQLDB, SQLDialect.MARIADB, SQLDialect.MYSQL, SQLDialect.POSTGRES})
   public static <R extends Record> QuantifiedSelect<R> any(Select<R> select) {
      return new QuantifiedSelectImpl(Quantifier.ANY, select);
   }

   @Support({SQLDialect.CUBRID, SQLDialect.DERBY, SQLDialect.FIREBIRD, SQLDialect.H2, SQLDialect.HSQLDB, SQLDialect.MARIADB, SQLDialect.MYSQL, SQLDialect.POSTGRES})
   public static <T> QuantifiedSelect<Record1<T>> any(T... array) {
      return any((Field)val((Object)array));
   }

   @Support({SQLDialect.H2, SQLDialect.HSQLDB, SQLDialect.POSTGRES})
   public static <T> QuantifiedSelect<Record1<T>> any(Field<T[]> array) {
      return new QuantifiedSelectImpl(Quantifier.ANY, array);
   }

   @Support
   public static <R extends Record> Table<R> table(Select<R> select) {
      return select.asTable();
   }

   @Support
   public static <R extends Record> Table<R> table(Result<R> result) {
      int size = result.size();
      RowN[] rows = new RowN[size];

      for(int i = 0; i < size; ++i) {
         rows[i] = (RowN)((Record)result.get(i)).valuesRow();
      }

      Field<?>[] fields = result.fields();
      String[] columns = new String[fields.length];

      for(int i = 0; i < fields.length; ++i) {
         columns[i] = fields[i].getName();
      }

      return values(rows).as("v", columns);
   }

   @Support
   public static <R extends Record> Table<R> table(R record) {
      return table((Record[])(new Record[]{record}));
   }

   @Support
   public static <R extends Record> Table<R> table(R... records) {
      if (records != null && records.length != 0) {
         Result<R> result = new ResultImpl(Tools.configuration((Attachable)records[0]), records[0].fields());
         result.addAll(Arrays.asList(records));
         return table((Result)result);
      } else {
         return new Dual();
      }
   }

   @Support
   public static Table<?> table(List<?> list) {
      return table(list.toArray());
   }

   @Support
   public static Table<?> table(Object[] array) {
      return table((Field)val((Object)array));
   }

   @Support({SQLDialect.H2, SQLDialect.HSQLDB, SQLDialect.POSTGRES})
   public static Table<?> table(Field<?> cursor) {
      return unnest(cursor);
   }

   @Support
   public static Table<?> unnest(List<?> list) {
      return unnest(list.toArray());
   }

   @Support
   public static Table<?> unnest(Object[] array) {
      return unnest((Field)val((Object)array));
   }

   @Support({SQLDialect.H2, SQLDialect.HSQLDB, SQLDialect.POSTGRES})
   public static Table<?> unnest(Field<?> cursor) {
      if (cursor == null) {
         throw new IllegalArgumentException();
      } else if (cursor.getType() == Result.class) {
         return new FunctionTable(cursor);
      } else if (cursor.getType().isArray() && cursor.getType() != byte[].class) {
         return new ArrayTable(cursor);
      } else {
         throw new SQLDialectNotSupportedException("Converting arbitrary types into array tables is currently not supported");
      }
   }

   @Support
   public static Table<Record> dual() {
      return new Dual(true);
   }

   @Support({SQLDialect.CUBRID, SQLDialect.POSTGRES})
   public static Table<Record1<Integer>> generateSeries(int from, int to) {
      return generateSeries(val(from), val(to));
   }

   @Support({SQLDialect.CUBRID, SQLDialect.POSTGRES})
   public static Table<Record1<Integer>> generateSeries(int from, Field<Integer> to) {
      return generateSeries(val(from), nullSafe(to));
   }

   @Support({SQLDialect.CUBRID, SQLDialect.POSTGRES})
   public static Table<Record1<Integer>> generateSeries(Field<Integer> from, int to) {
      return new GenerateSeries(nullSafe(from), val(to));
   }

   @Support({SQLDialect.CUBRID, SQLDialect.POSTGRES})
   public static Table<Record1<Integer>> generateSeries(Field<Integer> from, Field<Integer> to) {
      return new GenerateSeries(nullSafe(from), nullSafe(to));
   }

   @Support({SQLDialect.POSTGRES_9_3})
   public static <R extends Record> Table<R> lateral(TableLike<R> table) {
      return new Lateral(table.asTable());
   }

   @Support({SQLDialect.POSTGRES})
   public static Table<Record> rowsFrom(Table<?>... tables) {
      return new RowsFrom(tables);
   }

   public static Keyword keyword(String keyword) {
      return new KeywordImpl(keyword);
   }

   public static Name name(String... qualifiedName) {
      return new NameImpl(qualifiedName);
   }

   public static Name name(Collection<String> qualifiedName) {
      return new NameImpl((String[])qualifiedName.toArray(Tools.EMPTY_STRING));
   }

   public static QueryPart list(QueryPart... parts) {
      return list((Collection)Arrays.asList(parts));
   }

   public static QueryPart list(Collection<? extends QueryPart> parts) {
      return new QueryPartList(parts);
   }

   @Support({SQLDialect.CUBRID, SQLDialect.DERBY, SQLDialect.H2, SQLDialect.HSQLDB, SQLDialect.MARIADB, SQLDialect.MYSQL, SQLDialect.POSTGRES})
   public static Field<Object> defaultValue() {
      return defaultValue(Object.class);
   }

   @Support({SQLDialect.CUBRID, SQLDialect.DERBY, SQLDialect.H2, SQLDialect.HSQLDB, SQLDialect.MARIADB, SQLDialect.MYSQL, SQLDialect.POSTGRES})
   public static <T> Field<T> defaultValue(Class<T> type) {
      return defaultValue(getDataType(type));
   }

   @Support({SQLDialect.CUBRID, SQLDialect.DERBY, SQLDialect.H2, SQLDialect.HSQLDB, SQLDialect.MARIADB, SQLDialect.MYSQL, SQLDialect.POSTGRES})
   public static <T> Field<T> defaultValue(DataType<T> type) {
      return new SQLField(type, keyword("default"));
   }

   @Support({SQLDialect.CUBRID, SQLDialect.DERBY, SQLDialect.H2, SQLDialect.HSQLDB, SQLDialect.MARIADB, SQLDialect.MYSQL, SQLDialect.POSTGRES})
   public static <T> Field<T> defaultValue(Field<T> field) {
      return new SQLField(field.getDataType(), keyword("default"));
   }

   /** @deprecated */
   @Deprecated
   @Support
   public static Schema schemaByName(String name) {
      return new SchemaImpl(name);
   }

   @Support
   public static Catalog catalog(Name name) {
      return new CatalogImpl(name);
   }

   @Support
   public static Schema schema(Name name) {
      return new SchemaImpl(name);
   }

   /** @deprecated */
   @Deprecated
   @Support({SQLDialect.CUBRID, SQLDialect.DERBY, SQLDialect.FIREBIRD, SQLDialect.H2, SQLDialect.HSQLDB, SQLDialect.POSTGRES})
   public static Sequence<BigInteger> sequenceByName(String... qualifiedName) {
      return sequenceByName(BigInteger.class, qualifiedName);
   }

   /** @deprecated */
   @Deprecated
   @Support({SQLDialect.CUBRID, SQLDialect.DERBY, SQLDialect.FIREBIRD, SQLDialect.H2, SQLDialect.HSQLDB, SQLDialect.POSTGRES})
   public static <T extends Number> Sequence<T> sequenceByName(Class<T> type, String... qualifiedName) {
      return sequenceByName(getDataType(type), qualifiedName);
   }

   /** @deprecated */
   @Deprecated
   @Support({SQLDialect.CUBRID, SQLDialect.DERBY, SQLDialect.FIREBIRD, SQLDialect.H2, SQLDialect.HSQLDB, SQLDialect.POSTGRES})
   public static <T extends Number> Sequence<T> sequenceByName(DataType<T> type, String... qualifiedName) {
      if (qualifiedName == null) {
         throw new NullPointerException();
      } else if (qualifiedName.length >= 1 && qualifiedName.length <= 2) {
         String name = qualifiedName[qualifiedName.length - 1];
         Schema schema = qualifiedName.length == 2 ? schemaByName(qualifiedName[0]) : null;
         return new SequenceImpl(name, schema, type);
      } else {
         throw new IllegalArgumentException("Must provide a qualified name of length 1 or 2 : " + name(qualifiedName));
      }
   }

   @Support({SQLDialect.CUBRID, SQLDialect.DERBY, SQLDialect.FIREBIRD, SQLDialect.H2, SQLDialect.HSQLDB, SQLDialect.POSTGRES})
   public static Sequence<BigInteger> sequence(Name name) {
      return sequence(name, BigInteger.class);
   }

   @Support({SQLDialect.CUBRID, SQLDialect.DERBY, SQLDialect.FIREBIRD, SQLDialect.H2, SQLDialect.HSQLDB, SQLDialect.POSTGRES})
   public static <T extends Number> Sequence<T> sequence(Name name, Class<T> type) {
      return sequence(name, getDataType(type));
   }

   @Support({SQLDialect.CUBRID, SQLDialect.DERBY, SQLDialect.FIREBIRD, SQLDialect.H2, SQLDialect.HSQLDB, SQLDialect.POSTGRES})
   public static <T extends Number> Sequence<T> sequence(Name name, DataType<T> type) {
      if (name == null) {
         throw new NullPointerException();
      } else if (name.getName().length >= 1 && name.getName().length <= 2) {
         String n = name.getName()[name.getName().length - 1];
         Schema s = name.getName().length == 2 ? schema(name(name.getName()[0])) : null;
         return new SequenceImpl(n, s, type);
      } else {
         throw new IllegalArgumentException("Must provide a qualified name of length 1 or 2 : " + name);
      }
   }

   /** @deprecated */
   @Deprecated
   @Support
   public static Table<Record> tableByName(String... qualifiedName) {
      return table(name(qualifiedName));
   }

   @Support
   public static Table<Record> table(Name name) {
      return new QualifiedTable(name);
   }

   /** @deprecated */
   @Deprecated
   @Support
   public static Field<Object> fieldByName(String... qualifiedName) {
      return fieldByName(Object.class, qualifiedName);
   }

   /** @deprecated */
   @Deprecated
   @Support
   public static <T> Field<T> fieldByName(Class<T> type, String... qualifiedName) {
      return fieldByName(getDataType(type), qualifiedName);
   }

   /** @deprecated */
   @Deprecated
   @Support
   public static <T> Field<T> fieldByName(DataType<T> type, String... qualifiedName) {
      return field(name(qualifiedName), type);
   }

   @Support
   public static Field<Object> field(Name name) {
      return field(name, Object.class);
   }

   @Support
   public static <T> Field<T> field(Name name, Class<T> type) {
      return field(name, getDataType(type));
   }

   @Support
   public static <T> Field<T> field(Name name, DataType<T> type) {
      return new QualifiedField(name, type);
   }

   @Support
   public static Queries queries(Query... queries) {
      return queries((Collection)Arrays.asList(queries));
   }

   @Support
   public static Queries queries(Collection<? extends Query> queries) {
      return new QueriesImpl(queries);
   }

   @Support
   @PlainSQL
   public static SQL sql(String sql) {
      return sql(sql);
   }

   @Support
   @PlainSQL
   public static SQL sql(String sql, QueryPart... parts) {
      return sql(sql, (Object[])parts);
   }

   @Support
   @PlainSQL
   public static SQL sql(String sql, Object... bindings) {
      return new SQLImpl(sql, bindings);
   }

   /** @deprecated */
   @Deprecated
   @Support
   @PlainSQL
   public static QueryPart queryPart(String sql) {
      return sql(sql);
   }

   /** @deprecated */
   @Deprecated
   @Support
   @PlainSQL
   public static QueryPart queryPart(String sql, QueryPart... parts) {
      return sql(sql, parts);
   }

   /** @deprecated */
   @Deprecated
   @Support
   @PlainSQL
   public static QueryPart queryPart(String sql, Object... bindings) {
      return sql(sql, bindings);
   }

   @Support
   @PlainSQL
   public static Query query(SQL sql) {
      return using((Configuration)(new DefaultConfiguration())).query(sql);
   }

   @Support
   @PlainSQL
   public static Query query(String sql) {
      return using((Configuration)(new DefaultConfiguration())).query(sql);
   }

   @Support
   @PlainSQL
   public static Query query(String sql, Object... bindings) {
      return using((Configuration)(new DefaultConfiguration())).query(sql, bindings);
   }

   @Support
   @PlainSQL
   public static Query query(String sql, QueryPart... parts) {
      return using((Configuration)(new DefaultConfiguration())).query(sql, parts);
   }

   @Support
   @PlainSQL
   public static ResultQuery<Record> resultQuery(SQL sql) {
      return using((Configuration)(new DefaultConfiguration())).resultQuery(sql);
   }

   @Support
   @PlainSQL
   public static ResultQuery<Record> resultQuery(String sql) {
      return using((Configuration)(new DefaultConfiguration())).resultQuery(sql);
   }

   @Support
   @PlainSQL
   public static ResultQuery<Record> resultQuery(String sql, Object... bindings) {
      return using((Configuration)(new DefaultConfiguration())).resultQuery(sql, bindings);
   }

   @Support
   @PlainSQL
   public static ResultQuery<Record> resultQuery(String sql, QueryPart... parts) {
      return using((Configuration)(new DefaultConfiguration())).resultQuery(sql, parts);
   }

   @Support
   @PlainSQL
   public static Table<Record> table(SQL sql) {
      return new SQLTable(sql);
   }

   @Support
   @PlainSQL
   public static Table<Record> table(String sql) {
      return table(sql);
   }

   @Support
   @PlainSQL
   public static Table<Record> table(String sql, Object... bindings) {
      return table(sql(sql, bindings));
   }

   @Support
   @PlainSQL
   public static Table<Record> table(String sql, QueryPart... parts) {
      return table(sql, (Object[])parts);
   }

   @Support({SQLDialect.CUBRID, SQLDialect.DERBY, SQLDialect.FIREBIRD, SQLDialect.H2, SQLDialect.HSQLDB, SQLDialect.POSTGRES})
   @PlainSQL
   public static Sequence<BigInteger> sequence(String sql) {
      return sequence(sql, BigInteger.class);
   }

   @Support({SQLDialect.CUBRID, SQLDialect.DERBY, SQLDialect.FIREBIRD, SQLDialect.H2, SQLDialect.HSQLDB, SQLDialect.POSTGRES})
   @PlainSQL
   public static <T extends Number> Sequence<T> sequence(String sql, Class<T> type) {
      return sequence(sql, getDataType(type));
   }

   @Support({SQLDialect.CUBRID, SQLDialect.DERBY, SQLDialect.FIREBIRD, SQLDialect.H2, SQLDialect.HSQLDB, SQLDialect.POSTGRES})
   @PlainSQL
   public static <T extends Number> Sequence<T> sequence(String sql, DataType<T> type) {
      return new SequenceImpl(sql, (Schema)null, type, true);
   }

   @Support
   @PlainSQL
   public static Field<Object> field(SQL sql) {
      return field(sql, Object.class);
   }

   @Support
   @PlainSQL
   public static Field<Object> field(String sql) {
      return field(sql);
   }

   @Support
   @PlainSQL
   public static Field<Object> field(String sql, Object... bindings) {
      return field(sql, Object.class, bindings);
   }

   @Support
   @PlainSQL
   public static <T> Field<T> field(SQL sql, Class<T> type) {
      return field(sql, getDataType(type));
   }

   @Support
   @PlainSQL
   public static <T> Field<T> field(String sql, Class<T> type) {
      return field(sql, type);
   }

   @Support
   @PlainSQL
   public static <T> Field<T> field(String sql, Class<T> type, Object... bindings) {
      return field(sql, getDataType(type), bindings);
   }

   @Support
   @PlainSQL
   public static <T> Field<T> field(SQL sql, DataType<T> type) {
      return new SQLField(type, sql);
   }

   @Support
   @PlainSQL
   public static <T> Field<T> field(String sql, DataType<T> type) {
      return field(sql, type);
   }

   @Support
   @PlainSQL
   public static <T> Field<T> field(String sql, DataType<T> type, Object... bindings) {
      return field(sql(sql, bindings), type);
   }

   @Support
   @PlainSQL
   public static <T> Field<T> field(String sql, DataType<T> type, QueryPart... parts) {
      return field(sql(sql, parts), type);
   }

   @Support
   @PlainSQL
   public static Field<Object> field(String sql, QueryPart... parts) {
      return field(sql, (Object[])parts);
   }

   @Support
   @PlainSQL
   public static <T> Field<T> field(String sql, Class<T> type, QueryPart... parts) {
      return field(sql, getDataType(type), (Object[])parts);
   }

   @Support
   @PlainSQL
   public static <T> Field<T> function(String name, Class<T> type, Field<?>... arguments) {
      return function(name, getDataType(type), nullSafe(arguments));
   }

   @Support
   @PlainSQL
   public static <T> Field<T> function(String name, DataType<T> type, Field<?>... arguments) {
      return new Function(name, type, nullSafe(arguments));
   }

   @Support
   public static <T> Field<T> function(Name name, Class<T> type, Field<?>... arguments) {
      return function(name, getDataType(type), nullSafe(arguments));
   }

   @Support
   public static <T> Field<T> function(Name name, DataType<T> type, Field<?>... arguments) {
      return new Function(name, type, nullSafe(arguments));
   }

   @Support
   @PlainSQL
   public static Condition condition(SQL sql) {
      return new SQLCondition(sql);
   }

   @Support
   @PlainSQL
   public static Condition condition(String sql) {
      return condition(sql);
   }

   @Support
   @PlainSQL
   public static Condition condition(String sql, Object... bindings) {
      return condition(sql(sql, bindings));
   }

   @Support
   @PlainSQL
   public static Condition condition(String sql, QueryPart... parts) {
      return condition(sql, (Object[])parts);
   }

   @Support
   public static Condition condition(Boolean value) {
      return condition(Tools.field(value, (Class)Boolean.class));
   }

   @Support
   public static Condition condition(Field<Boolean> field) {
      return new FieldCondition(field);
   }

   @Support
   public static Condition condition(Map<Field<?>, ?> map) {
      return new MapCondition(map);
   }

   @Support
   public static Condition condition(Record record) {
      return new RecordCondition(record);
   }

   @Support
   public static Condition trueCondition() {
      return new TrueCondition();
   }

   @Support
   public static Condition falseCondition() {
      return new FalseCondition();
   }

   @Support
   public static Condition and(Condition... conditions) {
      return condition(Operator.AND, conditions);
   }

   @Support
   public static Condition and(Collection<? extends Condition> conditions) {
      return condition(Operator.AND, conditions);
   }

   @Support
   public static Condition or(Condition... conditions) {
      return condition(Operator.OR, conditions);
   }

   @Support
   public static Condition or(Collection<? extends Condition> conditions) {
      return condition(Operator.OR, conditions);
   }

   @Support
   public static Condition condition(Operator operator, Condition... conditions) {
      return condition((Operator)operator, (Collection)Arrays.asList(conditions));
   }

   @Support
   public static Condition condition(Operator operator, Collection<? extends Condition> conditions) {
      return new CombinedCondition(operator, conditions);
   }

   @Support
   public static Condition exists(Select<?> query) {
      return new ExistsCondition(query, true);
   }

   @Support
   public static Condition notExists(Select<?> query) {
      return new ExistsCondition(query, false);
   }

   @Support
   public static Condition not(Condition condition) {
      return condition.not();
   }

   /** @deprecated */
   @Deprecated
   @Support
   public static Field<Boolean> not(Boolean value) {
      return not(Tools.field(value, (Class)Boolean.class));
   }

   @Support
   public static Field<Boolean> not(Field<Boolean> field) {
      return new NotField(field);
   }

   @Support
   public static Field<Boolean> field(Condition condition) {
      return new ConditionAsField(condition);
   }

   @Support
   public static <T> Field<T> field(SelectField<T> field) {
      return field instanceof Field ? (Field)field : field("{0}", field.getDataType(), field);
   }

   @Support
   private static <T1> Field<Record1<T1>> field(Row1<T1> row) {
      return new RowField(row);
   }

   @Support
   private static <T1, T2> Field<Record2<T1, T2>> field(Row2<T1, T2> row) {
      return new RowField(row);
   }

   @Support
   private static <T1, T2, T3> Field<Record3<T1, T2, T3>> field(Row3<T1, T2, T3> row) {
      return new RowField(row);
   }

   @Support
   private static <T1, T2, T3, T4> Field<Record4<T1, T2, T3, T4>> field(Row4<T1, T2, T3, T4> row) {
      return new RowField(row);
   }

   @Support
   private static <T1, T2, T3, T4, T5> Field<Record5<T1, T2, T3, T4, T5>> field(Row5<T1, T2, T3, T4, T5> row) {
      return new RowField(row);
   }

   @Support
   private static <T1, T2, T3, T4, T5, T6> Field<Record6<T1, T2, T3, T4, T5, T6>> field(Row6<T1, T2, T3, T4, T5, T6> row) {
      return new RowField(row);
   }

   @Support
   private static <T1, T2, T3, T4, T5, T6, T7> Field<Record7<T1, T2, T3, T4, T5, T6, T7>> field(Row7<T1, T2, T3, T4, T5, T6, T7> row) {
      return new RowField(row);
   }

   @Support
   private static <T1, T2, T3, T4, T5, T6, T7, T8> Field<Record8<T1, T2, T3, T4, T5, T6, T7, T8>> field(Row8<T1, T2, T3, T4, T5, T6, T7, T8> row) {
      return new RowField(row);
   }

   @Support
   private static <T1, T2, T3, T4, T5, T6, T7, T8, T9> Field<Record9<T1, T2, T3, T4, T5, T6, T7, T8, T9>> field(Row9<T1, T2, T3, T4, T5, T6, T7, T8, T9> row) {
      return new RowField(row);
   }

   @Support
   private static <T1, T2, T3, T4, T5, T6, T7, T8, T9, T10> Field<Record10<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10>> field(Row10<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10> row) {
      return new RowField(row);
   }

   @Support
   private static <T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11> Field<Record11<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11>> field(Row11<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11> row) {
      return new RowField(row);
   }

   @Support
   private static <T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12> Field<Record12<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12>> field(Row12<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12> row) {
      return new RowField(row);
   }

   @Support
   private static <T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13> Field<Record13<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13>> field(Row13<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13> row) {
      return new RowField(row);
   }

   @Support
   private static <T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14> Field<Record14<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14>> field(Row14<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14> row) {
      return new RowField(row);
   }

   @Support
   private static <T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14, T15> Field<Record15<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14, T15>> field(Row15<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14, T15> row) {
      return new RowField(row);
   }

   @Support
   private static <T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14, T15, T16> Field<Record16<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14, T15, T16>> field(Row16<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14, T15, T16> row) {
      return new RowField(row);
   }

   @Support
   private static <T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14, T15, T16, T17> Field<Record17<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14, T15, T16, T17>> field(Row17<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14, T15, T16, T17> row) {
      return new RowField(row);
   }

   @Support
   private static <T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14, T15, T16, T17, T18> Field<Record18<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14, T15, T16, T17, T18>> field(Row18<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14, T15, T16, T17, T18> row) {
      return new RowField(row);
   }

   @Support
   private static <T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14, T15, T16, T17, T18, T19> Field<Record19<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14, T15, T16, T17, T18, T19>> field(Row19<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14, T15, T16, T17, T18, T19> row) {
      return new RowField(row);
   }

   @Support
   private static <T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14, T15, T16, T17, T18, T19, T20> Field<Record20<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14, T15, T16, T17, T18, T19, T20>> field(Row20<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14, T15, T16, T17, T18, T19, T20> row) {
      return new RowField(row);
   }

   @Support
   private static <T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14, T15, T16, T17, T18, T19, T20, T21> Field<Record21<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14, T15, T16, T17, T18, T19, T20, T21>> field(Row21<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14, T15, T16, T17, T18, T19, T20, T21> row) {
      return new RowField(row);
   }

   @Support
   private static <T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14, T15, T16, T17, T18, T19, T20, T21, T22> Field<Record22<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14, T15, T16, T17, T18, T19, T20, T21, T22>> field(Row22<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14, T15, T16, T17, T18, T19, T20, T21, T22> row) {
      return new RowField(row);
   }

   @Support
   public static <T1> Field<Record1<T1>> rowField(Row1<T1> row) {
      return new RowField(row);
   }

   @Support
   public static <T1, T2> Field<Record2<T1, T2>> rowField(Row2<T1, T2> row) {
      return new RowField(row);
   }

   @Support
   public static <T1, T2, T3> Field<Record3<T1, T2, T3>> rowField(Row3<T1, T2, T3> row) {
      return new RowField(row);
   }

   @Support
   public static <T1, T2, T3, T4> Field<Record4<T1, T2, T3, T4>> rowField(Row4<T1, T2, T3, T4> row) {
      return new RowField(row);
   }

   @Support
   public static <T1, T2, T3, T4, T5> Field<Record5<T1, T2, T3, T4, T5>> rowField(Row5<T1, T2, T3, T4, T5> row) {
      return new RowField(row);
   }

   @Support
   public static <T1, T2, T3, T4, T5, T6> Field<Record6<T1, T2, T3, T4, T5, T6>> rowField(Row6<T1, T2, T3, T4, T5, T6> row) {
      return new RowField(row);
   }

   @Support
   public static <T1, T2, T3, T4, T5, T6, T7> Field<Record7<T1, T2, T3, T4, T5, T6, T7>> rowField(Row7<T1, T2, T3, T4, T5, T6, T7> row) {
      return new RowField(row);
   }

   @Support
   public static <T1, T2, T3, T4, T5, T6, T7, T8> Field<Record8<T1, T2, T3, T4, T5, T6, T7, T8>> rowField(Row8<T1, T2, T3, T4, T5, T6, T7, T8> row) {
      return new RowField(row);
   }

   @Support
   public static <T1, T2, T3, T4, T5, T6, T7, T8, T9> Field<Record9<T1, T2, T3, T4, T5, T6, T7, T8, T9>> rowField(Row9<T1, T2, T3, T4, T5, T6, T7, T8, T9> row) {
      return new RowField(row);
   }

   @Support
   public static <T1, T2, T3, T4, T5, T6, T7, T8, T9, T10> Field<Record10<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10>> rowField(Row10<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10> row) {
      return new RowField(row);
   }

   @Support
   public static <T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11> Field<Record11<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11>> rowField(Row11<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11> row) {
      return new RowField(row);
   }

   @Support
   public static <T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12> Field<Record12<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12>> rowField(Row12<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12> row) {
      return new RowField(row);
   }

   @Support
   public static <T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13> Field<Record13<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13>> rowField(Row13<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13> row) {
      return new RowField(row);
   }

   @Support
   public static <T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14> Field<Record14<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14>> rowField(Row14<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14> row) {
      return new RowField(row);
   }

   @Support
   public static <T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14, T15> Field<Record15<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14, T15>> rowField(Row15<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14, T15> row) {
      return new RowField(row);
   }

   @Support
   public static <T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14, T15, T16> Field<Record16<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14, T15, T16>> rowField(Row16<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14, T15, T16> row) {
      return new RowField(row);
   }

   @Support
   public static <T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14, T15, T16, T17> Field<Record17<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14, T15, T16, T17>> rowField(Row17<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14, T15, T16, T17> row) {
      return new RowField(row);
   }

   @Support
   public static <T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14, T15, T16, T17, T18> Field<Record18<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14, T15, T16, T17, T18>> rowField(Row18<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14, T15, T16, T17, T18> row) {
      return new RowField(row);
   }

   @Support
   public static <T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14, T15, T16, T17, T18, T19> Field<Record19<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14, T15, T16, T17, T18, T19>> rowField(Row19<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14, T15, T16, T17, T18, T19> row) {
      return new RowField(row);
   }

   @Support
   public static <T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14, T15, T16, T17, T18, T19, T20> Field<Record20<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14, T15, T16, T17, T18, T19, T20>> rowField(Row20<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14, T15, T16, T17, T18, T19, T20> row) {
      return new RowField(row);
   }

   @Support
   public static <T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14, T15, T16, T17, T18, T19, T20, T21> Field<Record21<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14, T15, T16, T17, T18, T19, T20, T21>> rowField(Row21<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14, T15, T16, T17, T18, T19, T20, T21> row) {
      return new RowField(row);
   }

   @Support
   public static <T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14, T15, T16, T17, T18, T19, T20, T21, T22> Field<Record22<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14, T15, T16, T17, T18, T19, T20, T21, T22>> rowField(Row22<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14, T15, T16, T17, T18, T19, T20, T21, T22> row) {
      return new RowField(row);
   }

   @Support
   public static <T> Field<T> field(Select<? extends Record1<T>> select) {
      return select == null ? NULL() : select.asField();
   }

   @Support
   public static Case choose() {
      return decode();
   }

   @Support
   public static <V> CaseValueStep<V> choose(V value) {
      return decode().value(value);
   }

   @Support
   public static <V> CaseValueStep<V> choose(Field<V> value) {
      return decode().value(value);
   }

   @Support
   public static <T> CaseConditionStep<T> when(Condition condition, T result) {
      return decode().when(condition, result);
   }

   @Support
   public static <T> CaseConditionStep<T> when(Condition condition, Field<T> result) {
      return decode().when(condition, result);
   }

   @Support
   public static <T> CaseConditionStep<T> when(Condition condition, Select<? extends Record1<T>> result) {
      return decode().when(condition, result);
   }

   @Support
   public static Case decode() {
      return new CaseImpl();
   }

   @Support
   public static <Z, T> Field<Z> decode(T value, T search, Z result) {
      return decode(value, search, result);
   }

   @Support
   public static <Z, T> Field<Z> decode(T value, T search, Z result, Object... more) {
      return decode(Tools.field(value), Tools.field(search), Tools.field(result), (Field[])Tools.fields(more).toArray(Tools.EMPTY_FIELD));
   }

   @Support
   public static <Z, T> Field<Z> decode(Field<T> value, Field<T> search, Field<Z> result) {
      return decode(nullSafe(value), nullSafe(search), nullSafe(result), Tools.EMPTY_FIELD);
   }

   @Support
   public static <Z, T> Field<Z> decode(Field<T> value, Field<T> search, Field<Z> result, Field<?>... more) {
      return new Decode(nullSafe(value), nullSafe(search), nullSafe(result), nullSafe(more));
   }

   @Support
   public static <T> Field<T> coerce(Object value, Field<T> as) {
      return Tools.field(value).coerce(as);
   }

   @Support
   public static <T> Field<T> coerce(Object value, Class<T> as) {
      return Tools.field(value).coerce(as);
   }

   @Support
   public static <T> Field<T> coerce(Object value, DataType<T> as) {
      return Tools.field(value).coerce(as);
   }

   @Support
   public static <T> Field<T> coerce(Field<?> field, Field<T> as) {
      return nullSafe(field).coerce(as);
   }

   @Support
   public static <T> Field<T> coerce(Field<?> field, Class<T> as) {
      return nullSafe(field).coerce(as);
   }

   @Support
   public static <T> Field<T> coerce(Field<?> field, DataType<T> as) {
      return nullSafe(field).coerce(as);
   }

   @Support
   public static <T> Field<T> cast(Object value, Field<T> as) {
      return Tools.field(value, as).cast(as);
   }

   @Support
   public static <T> Field<T> cast(Field<?> field, Field<T> as) {
      return nullSafe(field).cast(as);
   }

   @Support
   public static <T> Field<T> castNull(Field<T> as) {
      return NULL().cast(as);
   }

   @Support
   public static <T> Field<T> cast(Object value, Class<T> type) {
      return Tools.field(value, type).cast(type);
   }

   @Support
   public static <T> Field<T> cast(Field<?> field, Class<T> type) {
      return nullSafe(field).cast(type);
   }

   @Support
   public static <T> Field<T> castNull(DataType<T> type) {
      return NULL().cast(type);
   }

   @Support
   public static <T> Field<T> cast(Object value, DataType<T> type) {
      return Tools.field(value).cast(type);
   }

   @Support
   public static <T> Field<T> cast(Field<?> field, DataType<T> type) {
      return nullSafe(field).cast(type);
   }

   @Support
   public static <T> Field<T> castNull(Class<T> type) {
      return NULL().cast(type);
   }

   static <T> Field<T>[] castAll(Class<T> type, Field<?>... fields) {
      Field<?>[] castFields = new Field[fields.length];

      for(int i = 0; i < fields.length; ++i) {
         castFields[i] = fields[i].cast(type);
      }

      return (Field[])castFields;
   }

   @Support
   public static <T> Field<T> coalesce(T value, T... values) {
      return coalesce0(Tools.field(value), (Field[])Tools.fields(values).toArray(Tools.EMPTY_FIELD));
   }

   @Support
   public static <T> Field<T> coalesce(Field<T> field, T value) {
      return coalesce0(field, Tools.field(value, field));
   }

   @Support
   public static <T> Field<T> coalesce(Field<T> field, Field<?>... fields) {
      return coalesce0(field, fields);
   }

   static <T> Field<T> coalesce0(Field<T> field, Field<?>... fields) {
      return new Coalesce(nullSafeDataType(field), nullSafe(Tools.combine(field, fields)));
   }

   @Support
   public static <T> Field<T> isnull(T value, T defaultValue) {
      return nvl(value, defaultValue);
   }

   @Support
   public static <T> Field<T> isnull(T value, Field<T> defaultValue) {
      return nvl(value, defaultValue);
   }

   @Support
   public static <T> Field<T> isnull(Field<T> value, T defaultValue) {
      return nvl(value, defaultValue);
   }

   @Support
   public static <T> Field<T> isnull(Field<T> value, Field<T> defaultValue) {
      return nvl(value, defaultValue);
   }

   @Support
   public static <T> Field<T> nvl(T value, T defaultValue) {
      return nvl0(Tools.field(value), Tools.field(defaultValue));
   }

   @Support
   public static <T> Field<T> nvl(T value, Field<T> defaultValue) {
      return nvl0(Tools.field(value), nullSafe(defaultValue));
   }

   @Support
   public static <T> Field<T> nvl(Field<T> value, T defaultValue) {
      return nvl0(nullSafe(value), Tools.field(defaultValue));
   }

   @Support
   public static <T> Field<T> nvl(Field<T> value, Field<T> defaultValue) {
      return nvl0(value, defaultValue);
   }

   @Support
   public static <T> Field<T> ifnull(T value, T defaultValue) {
      return nvl(value, defaultValue);
   }

   @Support
   public static <T> Field<T> ifnull(T value, Field<T> defaultValue) {
      return nvl(value, defaultValue);
   }

   @Support
   public static <T> Field<T> ifnull(Field<T> value, T defaultValue) {
      return nvl(value, defaultValue);
   }

   @Support
   public static <T> Field<T> ifnull(Field<T> value, Field<T> defaultValue) {
      return nvl(value, defaultValue);
   }

   static <T> Field<T> nvl0(Field<T> value, Field<T> defaultValue) {
      return new Nvl(nullSafe(value), nullSafe(defaultValue));
   }

   @Support
   public static <Z> Field<Z> nvl2(Field<?> value, Z valueIfNotNull, Z valueIfNull) {
      return nvl20(nullSafe(value), Tools.field(valueIfNotNull), Tools.field(valueIfNull));
   }

   @Support
   public static <Z> Field<Z> nvl2(Field<?> value, Z valueIfNotNull, Field<Z> valueIfNull) {
      return nvl20(nullSafe(value), Tools.field(valueIfNotNull), nullSafe(valueIfNull));
   }

   @Support
   public static <Z> Field<Z> nvl2(Field<?> value, Field<Z> valueIfNotNull, Z valueIfNull) {
      return nvl20(nullSafe(value), nullSafe(valueIfNotNull), Tools.field(valueIfNull));
   }

   @Support
   public static <Z> Field<Z> nvl2(Field<?> value, Field<Z> valueIfNotNull, Field<Z> valueIfNull) {
      return nvl20(value, valueIfNotNull, valueIfNull);
   }

   static <Z> Field<Z> nvl20(Field<?> value, Field<Z> valueIfNotNull, Field<Z> valueIfNull) {
      return new Nvl2(nullSafe(value), nullSafe(valueIfNotNull), nullSafe(valueIfNull));
   }

   @Support
   public static <T> Field<T> nullif(T value, T other) {
      return nullif0(Tools.field(value), Tools.field(other));
   }

   @Support
   public static <T> Field<T> nullif(T value, Field<T> other) {
      return nullif0(Tools.field(value), nullSafe(other));
   }

   @Support
   public static <T> Field<T> nullif(Field<T> value, T other) {
      return nullif0(nullSafe(value), Tools.field(other));
   }

   @Support
   public static <T> Field<T> nullif(Field<T> value, Field<T> other) {
      return nullif0(value, other);
   }

   static <T> Field<T> nullif0(Field<T> value, Field<T> other) {
      return new NullIf(nullSafe(value), nullSafe(other));
   }

   @Support
   public static Field<String> upper(String value) {
      return upper((Field)Tools.field(value));
   }

   @Support
   public static Field<String> upper(Field<String> field) {
      return new Upper(nullSafe(field));
   }

   @Support
   public static Field<String> lower(String value) {
      return lower(Tools.field(value, (Class)String.class));
   }

   @Support
   public static Field<String> lower(Field<String> field) {
      return new Lower(nullSafe(field));
   }

   @Support
   public static Field<String> trim(String value) {
      return trim(Tools.field(value, (Class)String.class));
   }

   @Support
   public static Field<String> trim(Field<String> field) {
      return new Trim(nullSafe(field));
   }

   @Support
   public static Field<String> rtrim(String value) {
      return rtrim((Field)Tools.field(value));
   }

   @Support
   public static Field<String> rtrim(Field<String> field) {
      return new RTrim(nullSafe(field));
   }

   @Support
   public static Field<String> ltrim(String value) {
      return ltrim(Tools.field(value, (Class)String.class));
   }

   @Support
   public static Field<String> ltrim(Field<String> field) {
      return new LTrim(nullSafe(field));
   }

   @Support({SQLDialect.CUBRID, SQLDialect.FIREBIRD, SQLDialect.H2, SQLDialect.HSQLDB, SQLDialect.MARIADB, SQLDialect.MYSQL, SQLDialect.POSTGRES, SQLDialect.SQLITE})
   public static Field<String> rpad(Field<String> field, int length) {
      return rpad(nullSafe(field), Tools.field(length));
   }

   @Support({SQLDialect.CUBRID, SQLDialect.FIREBIRD, SQLDialect.H2, SQLDialect.HSQLDB, SQLDialect.MARIADB, SQLDialect.MYSQL, SQLDialect.POSTGRES, SQLDialect.SQLITE})
   public static Field<String> rpad(Field<String> field, Field<? extends Number> length) {
      return new Rpad(nullSafe(field), nullSafe(length));
   }

   @Support({SQLDialect.CUBRID, SQLDialect.FIREBIRD, SQLDialect.H2, SQLDialect.HSQLDB, SQLDialect.MARIADB, SQLDialect.MYSQL, SQLDialect.POSTGRES, SQLDialect.SQLITE})
   public static Field<String> rpad(Field<String> field, int length, char character) {
      return rpad(field, length, Character.toString(character));
   }

   @Support({SQLDialect.CUBRID, SQLDialect.FIREBIRD, SQLDialect.H2, SQLDialect.HSQLDB, SQLDialect.MARIADB, SQLDialect.MYSQL, SQLDialect.POSTGRES, SQLDialect.SQLITE})
   public static Field<String> rpad(Field<String> field, int length, String character) {
      return rpad(nullSafe(field), Tools.field(length), Tools.field(character, (Class)String.class));
   }

   @Support({SQLDialect.CUBRID, SQLDialect.FIREBIRD, SQLDialect.H2, SQLDialect.HSQLDB, SQLDialect.MARIADB, SQLDialect.MYSQL, SQLDialect.POSTGRES, SQLDialect.SQLITE})
   public static Field<String> rpad(Field<String> field, Field<? extends Number> length, Field<String> character) {
      return new Rpad(nullSafe(field), nullSafe(length), nullSafe(character));
   }

   @Support({SQLDialect.CUBRID, SQLDialect.FIREBIRD, SQLDialect.H2, SQLDialect.HSQLDB, SQLDialect.MARIADB, SQLDialect.MYSQL, SQLDialect.POSTGRES, SQLDialect.SQLITE})
   public static Field<String> lpad(Field<String> field, int length) {
      return lpad(nullSafe(field), Tools.field(length));
   }

   @Support({SQLDialect.CUBRID, SQLDialect.FIREBIRD, SQLDialect.H2, SQLDialect.HSQLDB, SQLDialect.MARIADB, SQLDialect.MYSQL, SQLDialect.POSTGRES, SQLDialect.SQLITE})
   public static Field<String> lpad(Field<String> field, Field<? extends Number> length) {
      return new Lpad(nullSafe(field), nullSafe(length));
   }

   @Support({SQLDialect.CUBRID, SQLDialect.FIREBIRD, SQLDialect.H2, SQLDialect.HSQLDB, SQLDialect.MARIADB, SQLDialect.MYSQL, SQLDialect.POSTGRES, SQLDialect.SQLITE})
   public static Field<String> lpad(Field<String> field, int length, char character) {
      return lpad(field, length, Character.toString(character));
   }

   @Support({SQLDialect.CUBRID, SQLDialect.FIREBIRD, SQLDialect.H2, SQLDialect.HSQLDB, SQLDialect.MARIADB, SQLDialect.MYSQL, SQLDialect.POSTGRES, SQLDialect.SQLITE})
   public static Field<String> lpad(Field<String> field, int length, String character) {
      return lpad(nullSafe(field), Tools.field(length), Tools.field(character, (Class)String.class));
   }

   @Support({SQLDialect.CUBRID, SQLDialect.FIREBIRD, SQLDialect.H2, SQLDialect.HSQLDB, SQLDialect.MARIADB, SQLDialect.MYSQL, SQLDialect.POSTGRES, SQLDialect.SQLITE})
   public static Field<String> lpad(Field<String> field, Field<? extends Number> length, Field<String> character) {
      return new Lpad(nullSafe(field), nullSafe(length), nullSafe(character));
   }

   @Support({SQLDialect.CUBRID, SQLDialect.FIREBIRD, SQLDialect.H2, SQLDialect.HSQLDB, SQLDialect.MARIADB, SQLDialect.MYSQL, SQLDialect.POSTGRES, SQLDialect.SQLITE})
   public static Field<String> repeat(String field, int count) {
      return repeat((Field)Tools.field(field, (Class)String.class), Tools.field(count));
   }

   @Support({SQLDialect.CUBRID, SQLDialect.FIREBIRD, SQLDialect.H2, SQLDialect.HSQLDB, SQLDialect.MARIADB, SQLDialect.MYSQL, SQLDialect.POSTGRES, SQLDialect.SQLITE})
   public static Field<String> repeat(String field, Field<? extends Number> count) {
      return repeat(Tools.field(field, (Class)String.class), nullSafe(count));
   }

   @Support({SQLDialect.CUBRID, SQLDialect.FIREBIRD, SQLDialect.H2, SQLDialect.HSQLDB, SQLDialect.MARIADB, SQLDialect.MYSQL, SQLDialect.POSTGRES, SQLDialect.SQLITE})
   public static Field<String> repeat(Field<String> field, int count) {
      return repeat((Field)nullSafe(field), Tools.field(count));
   }

   @Support({SQLDialect.CUBRID, SQLDialect.FIREBIRD, SQLDialect.H2, SQLDialect.HSQLDB, SQLDialect.MARIADB, SQLDialect.MYSQL, SQLDialect.POSTGRES, SQLDialect.SQLITE})
   public static Field<String> repeat(Field<String> field, Field<? extends Number> count) {
      return new Repeat(nullSafe(field), nullSafe(count));
   }

   @Support({SQLDialect.CUBRID, SQLDialect.FIREBIRD, SQLDialect.H2, SQLDialect.HSQLDB, SQLDialect.MARIADB, SQLDialect.MYSQL, SQLDialect.POSTGRES, SQLDialect.SQLITE})
   public static Field<String> space(int value) {
      return space(val(value));
   }

   @Support({SQLDialect.CUBRID, SQLDialect.FIREBIRD, SQLDialect.H2, SQLDialect.HSQLDB, SQLDialect.MARIADB, SQLDialect.MYSQL, SQLDialect.POSTGRES, SQLDialect.SQLITE})
   public static Field<String> space(Field<Integer> value) {
      return new Space(nullSafe(value));
   }

   @Support({SQLDialect.CUBRID, SQLDialect.HSQLDB, SQLDialect.MARIADB, SQLDialect.MYSQL, SQLDialect.POSTGRES})
   public static Field<String> reverse(String value) {
      return reverse((Field)val(value));
   }

   @Support({SQLDialect.CUBRID, SQLDialect.HSQLDB, SQLDialect.MARIADB, SQLDialect.MYSQL, SQLDialect.POSTGRES})
   public static Field<String> reverse(Field<String> field) {
      return new Reverse(nullSafe(field));
   }

   @Support({SQLDialect.CUBRID, SQLDialect.DERBY, SQLDialect.FIREBIRD, SQLDialect.H2, SQLDialect.HSQLDB, SQLDialect.MARIADB, SQLDialect.MYSQL, SQLDialect.POSTGRES, SQLDialect.SQLITE})
   public static String escape(String value, char escape) {
      String esc = "" + escape;
      return value.replace(esc, esc + esc).replace("%", esc + "%").replace("_", esc + "_");
   }

   @Support({SQLDialect.CUBRID, SQLDialect.FIREBIRD, SQLDialect.H2, SQLDialect.HSQLDB, SQLDialect.MARIADB, SQLDialect.MYSQL, SQLDialect.POSTGRES, SQLDialect.SQLITE})
   public static Field<String> escape(Field<String> field, char escape) {
      String esc = "" + escape;
      Field<String> replace = replace(field, (Field)inline(esc), (Field)inline(esc + esc));
      replace = replace(replace, (Field)inline("%"), (Field)inline(esc + "%"));
      replace = replace(replace, (Field)inline("_"), (Field)inline(esc + "_"));
      return replace;
   }

   @Support({SQLDialect.CUBRID, SQLDialect.FIREBIRD, SQLDialect.H2, SQLDialect.HSQLDB, SQLDialect.MARIADB, SQLDialect.MYSQL, SQLDialect.POSTGRES, SQLDialect.SQLITE})
   public static Field<String> replace(Field<String> field, String search) {
      return replace(nullSafe(field), Tools.field(search, (Class)String.class));
   }

   @Support({SQLDialect.CUBRID, SQLDialect.FIREBIRD, SQLDialect.H2, SQLDialect.HSQLDB, SQLDialect.MARIADB, SQLDialect.MYSQL, SQLDialect.POSTGRES, SQLDialect.SQLITE})
   public static Field<String> replace(Field<String> field, Field<String> search) {
      return new Replace(new Field[]{nullSafe(field), nullSafe(search)});
   }

   @Support({SQLDialect.CUBRID, SQLDialect.FIREBIRD, SQLDialect.H2, SQLDialect.HSQLDB, SQLDialect.MARIADB, SQLDialect.MYSQL, SQLDialect.POSTGRES, SQLDialect.SQLITE})
   public static Field<String> replace(Field<String> field, String search, String replace) {
      return replace(nullSafe(field), Tools.field(search, (Class)String.class), Tools.field(replace, (Class)String.class));
   }

   @Support({SQLDialect.CUBRID, SQLDialect.FIREBIRD, SQLDialect.H2, SQLDialect.HSQLDB, SQLDialect.MARIADB, SQLDialect.MYSQL, SQLDialect.POSTGRES, SQLDialect.SQLITE})
   public static Field<String> replace(Field<String> field, Field<String> search, Field<String> replace) {
      return new Replace(new Field[]{nullSafe(field), nullSafe(search), nullSafe(replace)});
   }

   @Support({SQLDialect.CUBRID, SQLDialect.DERBY, SQLDialect.FIREBIRD, SQLDialect.H2, SQLDialect.HSQLDB, SQLDialect.MARIADB, SQLDialect.MYSQL, SQLDialect.POSTGRES, SQLDialect.SQLITE})
   public static Field<Integer> position(String in, String search) {
      return position(Tools.field(in, (Class)String.class), Tools.field(search, (Class)String.class));
   }

   @Support({SQLDialect.CUBRID, SQLDialect.DERBY, SQLDialect.FIREBIRD, SQLDialect.H2, SQLDialect.HSQLDB, SQLDialect.MARIADB, SQLDialect.MYSQL, SQLDialect.POSTGRES, SQLDialect.SQLITE})
   public static Field<Integer> position(String in, Field<String> search) {
      return position(Tools.field(in, (Class)String.class), nullSafe(search));
   }

   @Support({SQLDialect.CUBRID, SQLDialect.DERBY, SQLDialect.FIREBIRD, SQLDialect.H2, SQLDialect.HSQLDB, SQLDialect.MARIADB, SQLDialect.MYSQL, SQLDialect.POSTGRES, SQLDialect.SQLITE})
   public static Field<Integer> position(Field<String> in, String search) {
      return position(nullSafe(in), Tools.field(search, (Class)String.class));
   }

   @Support({SQLDialect.CUBRID, SQLDialect.DERBY, SQLDialect.FIREBIRD, SQLDialect.H2, SQLDialect.HSQLDB, SQLDialect.MARIADB, SQLDialect.MYSQL, SQLDialect.POSTGRES, SQLDialect.SQLITE})
   public static Field<Integer> position(Field<String> in, Field<String> search) {
      return new Position(nullSafe(search), nullSafe(in));
   }

   @Support({SQLDialect.CUBRID, SQLDialect.DERBY, SQLDialect.FIREBIRD, SQLDialect.H2, SQLDialect.HSQLDB, SQLDialect.MARIADB, SQLDialect.MYSQL, SQLDialect.POSTGRES, SQLDialect.SQLITE})
   public static Field<Integer> position(String in, String search, int startIndex) {
      return position((Field)Tools.field(in, (Class)String.class), (Field)Tools.field(search, (Class)String.class), Tools.field(startIndex));
   }

   @Support({SQLDialect.CUBRID, SQLDialect.DERBY, SQLDialect.FIREBIRD, SQLDialect.H2, SQLDialect.HSQLDB, SQLDialect.MARIADB, SQLDialect.MYSQL, SQLDialect.POSTGRES, SQLDialect.SQLITE})
   public static Field<Integer> position(String in, Field<String> search, int startIndex) {
      return position((Field)Tools.field(in, (Class)String.class), (Field)nullSafe(search), Tools.field(startIndex));
   }

   @Support({SQLDialect.CUBRID, SQLDialect.DERBY, SQLDialect.FIREBIRD, SQLDialect.H2, SQLDialect.HSQLDB, SQLDialect.MARIADB, SQLDialect.MYSQL, SQLDialect.POSTGRES, SQLDialect.SQLITE})
   public static Field<Integer> position(Field<String> in, String search, int startIndex) {
      return position((Field)nullSafe(in), (Field)Tools.field(search, (Class)String.class), Tools.field(startIndex));
   }

   @Support({SQLDialect.CUBRID, SQLDialect.DERBY, SQLDialect.FIREBIRD, SQLDialect.H2, SQLDialect.HSQLDB, SQLDialect.MARIADB, SQLDialect.MYSQL, SQLDialect.POSTGRES, SQLDialect.SQLITE})
   public static Field<Integer> position(Field<String> in, Field<String> search, int startIndex) {
      return position((Field)nullSafe(search), (Field)nullSafe(in), Tools.field(startIndex));
   }

   @Support({SQLDialect.CUBRID, SQLDialect.DERBY, SQLDialect.FIREBIRD, SQLDialect.H2, SQLDialect.HSQLDB, SQLDialect.MARIADB, SQLDialect.MYSQL, SQLDialect.POSTGRES, SQLDialect.SQLITE})
   public static Field<Integer> position(String in, String search, Field<? extends Number> startIndex) {
      return position(Tools.field(in, (Class)String.class), Tools.field(search, (Class)String.class), nullSafe(startIndex));
   }

   @Support({SQLDialect.CUBRID, SQLDialect.DERBY, SQLDialect.FIREBIRD, SQLDialect.H2, SQLDialect.HSQLDB, SQLDialect.MARIADB, SQLDialect.MYSQL, SQLDialect.POSTGRES, SQLDialect.SQLITE})
   public static Field<Integer> position(String in, Field<String> search, Field<? extends Number> startIndex) {
      return position(Tools.field(in, (Class)String.class), nullSafe(search), nullSafe(startIndex));
   }

   @Support({SQLDialect.CUBRID, SQLDialect.DERBY, SQLDialect.FIREBIRD, SQLDialect.H2, SQLDialect.HSQLDB, SQLDialect.MARIADB, SQLDialect.MYSQL, SQLDialect.POSTGRES, SQLDialect.SQLITE})
   public static Field<Integer> position(Field<String> in, String search, Field<? extends Number> startIndex) {
      return position(nullSafe(in), Tools.field(search, (Class)String.class), nullSafe(startIndex));
   }

   @Support({SQLDialect.CUBRID, SQLDialect.DERBY, SQLDialect.FIREBIRD, SQLDialect.H2, SQLDialect.HSQLDB, SQLDialect.MARIADB, SQLDialect.MYSQL, SQLDialect.POSTGRES, SQLDialect.SQLITE})
   public static Field<Integer> position(Field<String> in, Field<String> search, Field<? extends Number> startIndex) {
      return new Position(nullSafe(search), nullSafe(in), nullSafe(startIndex));
   }

   @Support({SQLDialect.CUBRID, SQLDialect.FIREBIRD, SQLDialect.H2, SQLDialect.HSQLDB, SQLDialect.MARIADB, SQLDialect.MYSQL, SQLDialect.POSTGRES})
   public static Field<Integer> ascii(String field) {
      return ascii(Tools.field(field, (Class)String.class));
   }

   @Support({SQLDialect.CUBRID, SQLDialect.FIREBIRD, SQLDialect.H2, SQLDialect.HSQLDB, SQLDialect.MARIADB, SQLDialect.MYSQL, SQLDialect.POSTGRES})
   public static Field<Integer> ascii(Field<String> field) {
      return new Ascii(nullSafe(field));
   }

   @Support
   public static Field<String> concat(Field<String> field, String value) {
      return concat(nullSafe(field), Tools.field(value, (Class)String.class));
   }

   @Support
   public static Field<String> concat(String value, Field<String> field) {
      return concat(Tools.field(value, (Class)String.class), nullSafe(field));
   }

   @Support
   public static Field<String> concat(String... values) {
      return concat((Field[])Tools.fields((Object[])values).toArray(Tools.EMPTY_FIELD));
   }

   @Support
   public static Field<String> concat(Field<?>... fields) {
      return new Concat(nullSafe(fields));
   }

   @Support
   public static Field<String> substring(Field<String> field, int startingPosition) {
      return substring(nullSafe(field), Tools.field(startingPosition));
   }

   @Support
   public static Field<String> substring(Field<String> field, Field<? extends Number> startingPosition) {
      return new Substring(new Field[]{nullSafe(field), nullSafe(startingPosition)});
   }

   @Support
   public static Field<String> substring(Field<String> field, int startingPosition, int length) {
      return substring(nullSafe(field), Tools.field(startingPosition), Tools.field(length));
   }

   @Support
   public static Field<String> substring(Field<String> field, Field<? extends Number> startingPosition, Field<? extends Number> length) {
      return new Substring(new Field[]{nullSafe(field), nullSafe(startingPosition), nullSafe(length)});
   }

   @Support
   public static Field<String> mid(Field<String> field, int startingPosition, int length) {
      return substring(nullSafe(field), Tools.field(startingPosition), Tools.field(length));
   }

   @Support
   public static Field<String> mid(Field<String> field, Field<? extends Number> startingPosition, Field<? extends Number> length) {
      return substring(nullSafe(field), nullSafe(startingPosition), nullSafe(length));
   }

   @Support
   public static Field<String> left(String field, int length) {
      return left((Field)Tools.field(field), Tools.field(length));
   }

   @Support
   public static Field<String> left(String field, Field<? extends Number> length) {
      return left((Field)Tools.field(field), nullSafe(length));
   }

   @Support
   public static Field<String> left(Field<String> field, int length) {
      return left((Field)nullSafe(field), Tools.field(length));
   }

   @Support
   public static Field<String> left(Field<String> field, Field<? extends Number> length) {
      return new Left(field, length);
   }

   @Support
   public static Field<String> right(String field, int length) {
      return right((Field)Tools.field(field), Tools.field(length));
   }

   @Support
   public static Field<String> right(String field, Field<? extends Number> length) {
      return right((Field)Tools.field(field), nullSafe(length));
   }

   @Support
   public static Field<String> right(Field<String> field, int length) {
      return right((Field)nullSafe(field), Tools.field(length));
   }

   @Support
   public static Field<String> right(Field<String> field, Field<? extends Number> length) {
      return new Right(field, length);
   }

   @Support
   public static Field<Integer> length(String value) {
      return length(Tools.field(value, (Class)String.class));
   }

   @Support
   public static Field<Integer> length(Field<String> field) {
      return charLength(field);
   }

   @Support
   public static Field<Integer> charLength(String value) {
      return charLength((Field)Tools.field(value));
   }

   @Support
   public static Field<Integer> charLength(Field<String> field) {
      return new Function(Term.CHAR_LENGTH, SQLDataType.INTEGER, new QueryPart[]{nullSafe(field)});
   }

   @Support
   public static Field<Integer> bitLength(String value) {
      return bitLength((Field)Tools.field(value));
   }

   @Support
   public static Field<Integer> bitLength(Field<String> field) {
      return new Function(Term.BIT_LENGTH, SQLDataType.INTEGER, new QueryPart[]{nullSafe(field)});
   }

   @Support
   public static Field<Integer> octetLength(String value) {
      return octetLength(Tools.field(value, (Class)String.class));
   }

   @Support
   public static Field<Integer> octetLength(Field<String> field) {
      return new Function(Term.OCTET_LENGTH, SQLDataType.INTEGER, new QueryPart[]{nullSafe(field)});
   }

   @Support({SQLDialect.MARIADB, SQLDialect.MYSQL})
   public static Field<String> md5(String string) {
      return md5((Field)Tools.field(string));
   }

   @Support({SQLDialect.MARIADB, SQLDialect.MYSQL})
   public static Field<String> md5(Field<String> string) {
      return new MD5(nullSafe(string));
   }

   @Support
   public static Field<Date> currentDate() {
      return new CurrentDate(SQLDataType.DATE);
   }

   @Support
   public static Field<Time> currentTime() {
      return new CurrentTime(SQLDataType.TIME);
   }

   @Support
   public static Field<Timestamp> currentTimestamp() {
      return new CurrentTimestamp(SQLDataType.TIMESTAMP);
   }

   @Support
   public static Field<LocalDate> currentLocalDate() {
      return new CurrentDate(SQLDataType.LOCALDATE);
   }

   @Support
   public static Field<LocalTime> currentLocalTime() {
      return new CurrentTime(SQLDataType.LOCALTIME);
   }

   @Support
   public static Field<LocalDateTime> currentLocalDateTime() {
      return new CurrentTimestamp(SQLDataType.LOCALDATETIME);
   }

   @Support
   public static Field<OffsetTime> currentOffsetTime() {
      return currentTime().cast(SQLDataType.OFFSETTIME);
   }

   @Support
   public static Field<OffsetDateTime> currentOffsetDateTime() {
      return currentTimestamp().cast(SQLDataType.OFFSETDATETIME);
   }

   @Support
   public static Field<Integer> dateDiff(Date date1, Date date2) {
      return dateDiff((Field)Tools.field(date1), (Field)Tools.field(date2));
   }

   @Support
   public static Field<Integer> dateDiff(Field<Date> date1, Date date2) {
      return dateDiff((Field)nullSafe(date1), (Field)Tools.field(date2));
   }

   @Support
   public static Field<Date> dateAdd(Date date, Number interval) {
      return dateAdd((Field)Tools.field(date), (Field)Tools.field((Object)interval));
   }

   @Support
   public static Field<Date> dateAdd(Field<Date> date, Field<? extends Number> interval) {
      return nullSafe(date).add(interval);
   }

   @Support
   public static Field<Date> dateAdd(Date date, Number interval, DatePart datePart) {
      return dateAdd((Field)Tools.field(date), (Field)Tools.field((Object)interval), datePart);
   }

   @Support
   public static Field<Date> dateAdd(Date date, Field<? extends Number> interval, DatePart datePart) {
      return dateAdd((Field)Tools.field(date), (Field)nullSafe(interval), datePart);
   }

   @Support
   public static Field<Date> dateAdd(Field<Date> date, Number interval, DatePart datePart) {
      return dateAdd(nullSafe(date), Tools.field((Object)interval), datePart);
   }

   @Support
   public static Field<Date> dateAdd(Field<Date> date, Field<? extends Number> interval, DatePart datePart) {
      return new DateAdd(nullSafe(date), nullSafe(interval), datePart);
   }

   @Support
   public static Field<Date> dateSub(Date date, Number interval) {
      return dateSub((Field)Tools.field(date), (Field)Tools.field((Object)interval));
   }

   @Support
   public static Field<Date> dateSub(Field<Date> date, Field<? extends Number> interval) {
      return nullSafe(date).sub(interval);
   }

   @Support
   public static Field<Date> dateSub(Date date, Number interval, DatePart datePart) {
      return dateSub((Field)Tools.field(date), (Field)Tools.field((Object)interval), datePart);
   }

   @Support
   public static Field<Date> dateSub(Date date, Field<? extends Number> interval, DatePart datePart) {
      return dateSub((Field)Tools.field(date), (Field)nullSafe(interval), datePart);
   }

   @Support
   public static Field<Date> dateSub(Field<Date> date, Number interval, DatePart datePart) {
      return dateSub(nullSafe(date), Tools.field((Object)interval), datePart);
   }

   @Support
   public static Field<Date> dateSub(Field<Date> date, Field<? extends Number> interval, DatePart datePart) {
      return new DateAdd(nullSafe(date), nullSafe(interval).neg(), datePart);
   }

   @Support
   public static Field<Integer> dateDiff(Date date1, Field<Date> date2) {
      return dateDiff((Field)Tools.field(date1), (Field)nullSafe(date2));
   }

   @Support
   public static Field<Integer> dateDiff(Field<Date> date1, Field<Date> date2) {
      return new DateDiff(nullSafe(date1), nullSafe(date2));
   }

   @Support
   public static Field<Timestamp> timestampAdd(Timestamp timestamp, Number interval) {
      return timestampAdd((Field)Tools.field(timestamp), (Field)Tools.field((Object)interval));
   }

   @Support
   public static Field<Timestamp> timestampAdd(Field<Timestamp> timestamp, Field<? extends Number> interval) {
      return nullSafe(timestamp).add(interval);
   }

   @Support
   public static Field<Timestamp> timestampAdd(Timestamp date, Number interval, DatePart datePart) {
      return new DateAdd(Tools.field(date), Tools.field((Object)interval), datePart);
   }

   @Support
   public static Field<Timestamp> timestampAdd(Timestamp date, Field<? extends Number> interval, DatePart datePart) {
      return new DateAdd(Tools.field(date), nullSafe(interval), datePart);
   }

   @Support
   public static Field<Timestamp> timestampAdd(Field<Timestamp> date, Number interval, DatePart datePart) {
      return new DateAdd(nullSafe(date), Tools.field((Object)interval), datePart);
   }

   @Support
   public static Field<Timestamp> timestampAdd(Field<Timestamp> date, Field<? extends Number> interval, DatePart datePart) {
      return new DateAdd(nullSafe(date), nullSafe(interval), datePart);
   }

   @Support
   public static Field<DayToSecond> timestampDiff(Timestamp timestamp1, Timestamp timestamp2) {
      return timestampDiff((Field)Tools.field(timestamp1), (Field)Tools.field(timestamp2));
   }

   @Support
   public static Field<DayToSecond> timestampDiff(Field<Timestamp> timestamp1, Timestamp timestamp2) {
      return timestampDiff((Field)nullSafe(timestamp1), (Field)Tools.field(timestamp2));
   }

   @Support
   public static Field<DayToSecond> timestampDiff(Timestamp timestamp1, Field<Timestamp> timestamp2) {
      return timestampDiff((Field)Tools.field(timestamp1), (Field)nullSafe(timestamp2));
   }

   @Support
   public static Field<DayToSecond> timestampDiff(Field<Timestamp> timestamp1, Field<Timestamp> timestamp2) {
      return new TimestampDiff(nullSafe(timestamp1), nullSafe(timestamp2));
   }

   @Support({SQLDialect.CUBRID, SQLDialect.H2, SQLDialect.HSQLDB, SQLDialect.POSTGRES})
   public static Field<Date> trunc(Date date) {
      return trunc(date, DatePart.DAY);
   }

   @Support({SQLDialect.CUBRID, SQLDialect.H2, SQLDialect.HSQLDB, SQLDialect.POSTGRES})
   public static Field<Date> trunc(Date date, DatePart part) {
      return trunc((Field)Tools.field(date), (DatePart)part);
   }

   @Support({SQLDialect.CUBRID, SQLDialect.H2, SQLDialect.HSQLDB, SQLDialect.POSTGRES})
   public static Field<LocalDate> trunc(LocalDate date) {
      return trunc(date, DatePart.DAY);
   }

   @Support({SQLDialect.CUBRID, SQLDialect.H2, SQLDialect.HSQLDB, SQLDialect.POSTGRES})
   public static Field<LocalDate> trunc(LocalDate date, DatePart part) {
      return trunc((Field)Tools.field(date), (DatePart)part);
   }

   @Support({SQLDialect.CUBRID, SQLDialect.H2, SQLDialect.HSQLDB, SQLDialect.POSTGRES})
   public static Field<Timestamp> trunc(Timestamp timestamp) {
      return trunc(timestamp, DatePart.DAY);
   }

   @Support({SQLDialect.CUBRID, SQLDialect.H2, SQLDialect.HSQLDB, SQLDialect.POSTGRES})
   public static Field<Timestamp> trunc(Timestamp timestamp, DatePart part) {
      return trunc((Field)Tools.field(timestamp), (DatePart)part);
   }

   @Support({SQLDialect.CUBRID, SQLDialect.H2, SQLDialect.HSQLDB, SQLDialect.POSTGRES})
   public static Field<LocalDateTime> trunc(LocalDateTime timestamp) {
      return trunc(timestamp, DatePart.DAY);
   }

   @Support({SQLDialect.CUBRID, SQLDialect.H2, SQLDialect.HSQLDB, SQLDialect.POSTGRES})
   public static Field<LocalDateTime> trunc(LocalDateTime timestamp, DatePart part) {
      return trunc((Field)Tools.field(timestamp), (DatePart)part);
   }

   @Support({SQLDialect.CUBRID, SQLDialect.H2, SQLDialect.HSQLDB, SQLDialect.POSTGRES})
   public static <T> Field<T> trunc(Field<T> date) {
      return trunc(date, DatePart.DAY);
   }

   @Support({SQLDialect.CUBRID, SQLDialect.H2, SQLDialect.HSQLDB, SQLDialect.POSTGRES})
   public static <T> Field<T> trunc(Field<T> date, DatePart part) {
      return new TruncDate(date, part);
   }

   @Support
   public static Field<Integer> extract(java.util.Date value, DatePart datePart) {
      return extract(Tools.field((Object)value), datePart);
   }

   @Support
   public static Field<Integer> extract(Temporal value, DatePart datePart) {
      return extract(Tools.field((Object)value), datePart);
   }

   @Support
   public static Field<Integer> extract(Field<?> field, DatePart datePart) {
      return new Extract(nullSafe(field), datePart);
   }

   @Support
   public static Field<Integer> year(java.util.Date value) {
      return extract(value, DatePart.YEAR);
   }

   @Support
   public static Field<Integer> year(Temporal value) {
      return extract(value, DatePart.YEAR);
   }

   @Support
   public static Field<Integer> year(Field<?> field) {
      return extract(field, DatePart.YEAR);
   }

   @Support
   public static Field<Integer> month(java.util.Date value) {
      return extract(value, DatePart.MONTH);
   }

   @Support
   public static Field<Integer> month(Temporal value) {
      return extract(value, DatePart.MONTH);
   }

   @Support
   public static Field<Integer> month(Field<?> field) {
      return extract(field, DatePart.MONTH);
   }

   @Support
   public static Field<Integer> day(java.util.Date value) {
      return extract(value, DatePart.DAY);
   }

   @Support
   public static Field<Integer> day(Temporal value) {
      return extract(value, DatePart.DAY);
   }

   @Support
   public static Field<Integer> day(Field<?> field) {
      return extract(field, DatePart.DAY);
   }

   @Support
   public static Field<Integer> hour(java.util.Date value) {
      return extract(value, DatePart.HOUR);
   }

   @Support
   public static Field<Integer> hour(Temporal value) {
      return extract(value, DatePart.HOUR);
   }

   @Support
   public static Field<Integer> hour(Field<?> field) {
      return extract(field, DatePart.HOUR);
   }

   @Support
   public static Field<Integer> minute(java.util.Date value) {
      return extract(value, DatePart.MINUTE);
   }

   @Support
   public static Field<Integer> minute(Temporal value) {
      return extract(value, DatePart.MINUTE);
   }

   @Support
   public static Field<Integer> minute(Field<?> field) {
      return extract(field, DatePart.MINUTE);
   }

   @Support
   public static Field<Integer> second(java.util.Date value) {
      return extract(value, DatePart.SECOND);
   }

   @Support
   public static Field<Integer> second(Temporal value) {
      return extract(value, DatePart.SECOND);
   }

   @Support
   public static Field<Integer> second(Field<?> field) {
      return extract(field, DatePart.SECOND);
   }

   @Support({SQLDialect.CUBRID, SQLDialect.FIREBIRD, SQLDialect.H2, SQLDialect.HSQLDB, SQLDialect.MARIADB, SQLDialect.MYSQL, SQLDialect.POSTGRES, SQLDialect.SQLITE})
   public static Field<Date> date(String value) {
      return Tools.field(Convert.convert((Object)value, (Class)Date.class), Date.class);
   }

   @Support({SQLDialect.CUBRID, SQLDialect.FIREBIRD, SQLDialect.H2, SQLDialect.HSQLDB, SQLDialect.MARIADB, SQLDialect.MYSQL, SQLDialect.POSTGRES, SQLDialect.SQLITE})
   public static Field<Date> date(java.util.Date value) {
      return date(Tools.field((Object)value));
   }

   @Support({SQLDialect.CUBRID, SQLDialect.FIREBIRD, SQLDialect.H2, SQLDialect.HSQLDB, SQLDialect.MARIADB, SQLDialect.MYSQL, SQLDialect.POSTGRES, SQLDialect.SQLITE})
   public static Field<Date> date(Field<? extends java.util.Date> field) {
      return new DateOrTime(field, SQLDataType.DATE);
   }

   @Support({SQLDialect.CUBRID, SQLDialect.FIREBIRD, SQLDialect.H2, SQLDialect.HSQLDB, SQLDialect.MARIADB, SQLDialect.MYSQL, SQLDialect.POSTGRES, SQLDialect.SQLITE})
   public static Field<Time> time(String value) {
      return Tools.field(Convert.convert((Object)value, (Class)Time.class), Time.class);
   }

   @Support({SQLDialect.CUBRID, SQLDialect.FIREBIRD, SQLDialect.H2, SQLDialect.HSQLDB, SQLDialect.MARIADB, SQLDialect.MYSQL, SQLDialect.POSTGRES, SQLDialect.SQLITE})
   public static Field<Time> time(java.util.Date value) {
      return time(Tools.field((Object)value));
   }

   @Support({SQLDialect.CUBRID, SQLDialect.FIREBIRD, SQLDialect.H2, SQLDialect.HSQLDB, SQLDialect.MARIADB, SQLDialect.MYSQL, SQLDialect.POSTGRES, SQLDialect.SQLITE})
   public static Field<Time> time(Field<? extends java.util.Date> field) {
      return new DateOrTime(field, SQLDataType.TIME);
   }

   @Support({SQLDialect.CUBRID, SQLDialect.FIREBIRD, SQLDialect.H2, SQLDialect.HSQLDB, SQLDialect.MARIADB, SQLDialect.MYSQL, SQLDialect.POSTGRES, SQLDialect.SQLITE})
   public static Field<Timestamp> timestamp(String value) {
      return Tools.field(Convert.convert((Object)value, (Class)Timestamp.class), Timestamp.class);
   }

   @Support({SQLDialect.CUBRID, SQLDialect.FIREBIRD, SQLDialect.H2, SQLDialect.HSQLDB, SQLDialect.MARIADB, SQLDialect.MYSQL, SQLDialect.POSTGRES, SQLDialect.SQLITE})
   public static Field<Timestamp> timestamp(java.util.Date value) {
      return timestamp(Tools.field((Object)value));
   }

   @Support({SQLDialect.CUBRID, SQLDialect.FIREBIRD, SQLDialect.H2, SQLDialect.HSQLDB, SQLDialect.MARIADB, SQLDialect.MYSQL, SQLDialect.POSTGRES, SQLDialect.SQLITE})
   public static Field<Timestamp> timestamp(Field<? extends java.util.Date> field) {
      return new DateOrTime(field, SQLDataType.TIMESTAMP);
   }

   @Support({SQLDialect.CUBRID, SQLDialect.FIREBIRD, SQLDialect.H2, SQLDialect.HSQLDB, SQLDialect.MARIADB, SQLDialect.MYSQL, SQLDialect.POSTGRES, SQLDialect.SQLITE})
   public static Field<LocalDate> localDate(String value) {
      return Tools.field(Convert.convert((Object)value, (Class)LocalDate.class), LocalDate.class);
   }

   @Support({SQLDialect.CUBRID, SQLDialect.FIREBIRD, SQLDialect.H2, SQLDialect.HSQLDB, SQLDialect.MARIADB, SQLDialect.MYSQL, SQLDialect.POSTGRES, SQLDialect.SQLITE})
   public static Field<LocalDate> localDate(LocalDate value) {
      return localDate((Field)Tools.field(value));
   }

   @Support({SQLDialect.CUBRID, SQLDialect.FIREBIRD, SQLDialect.H2, SQLDialect.HSQLDB, SQLDialect.MARIADB, SQLDialect.MYSQL, SQLDialect.POSTGRES, SQLDialect.SQLITE})
   public static Field<LocalDate> localDate(Field<LocalDate> field) {
      return new DateOrTime(field, SQLDataType.LOCALDATE);
   }

   @Support({SQLDialect.CUBRID, SQLDialect.FIREBIRD, SQLDialect.H2, SQLDialect.HSQLDB, SQLDialect.MARIADB, SQLDialect.MYSQL, SQLDialect.POSTGRES, SQLDialect.SQLITE})
   public static Field<LocalTime> localTime(String value) {
      return Tools.field(Convert.convert((Object)value, (Class)LocalTime.class), LocalTime.class);
   }

   @Support({SQLDialect.CUBRID, SQLDialect.FIREBIRD, SQLDialect.H2, SQLDialect.HSQLDB, SQLDialect.MARIADB, SQLDialect.MYSQL, SQLDialect.POSTGRES, SQLDialect.SQLITE})
   public static Field<LocalTime> localTime(LocalTime value) {
      return localTime((Field)Tools.field(value));
   }

   @Support({SQLDialect.CUBRID, SQLDialect.FIREBIRD, SQLDialect.H2, SQLDialect.HSQLDB, SQLDialect.MARIADB, SQLDialect.MYSQL, SQLDialect.POSTGRES, SQLDialect.SQLITE})
   public static Field<LocalTime> localTime(Field<LocalTime> field) {
      return new DateOrTime(field, SQLDataType.LOCALTIME);
   }

   @Support({SQLDialect.CUBRID, SQLDialect.FIREBIRD, SQLDialect.H2, SQLDialect.HSQLDB, SQLDialect.MARIADB, SQLDialect.MYSQL, SQLDialect.POSTGRES, SQLDialect.SQLITE})
   public static Field<LocalDateTime> localDateTime(String value) {
      return Tools.field(Convert.convert((Object)value, (Class)LocalDateTime.class), LocalDateTime.class);
   }

   @Support({SQLDialect.CUBRID, SQLDialect.FIREBIRD, SQLDialect.H2, SQLDialect.HSQLDB, SQLDialect.MARIADB, SQLDialect.MYSQL, SQLDialect.POSTGRES, SQLDialect.SQLITE})
   public static Field<LocalDateTime> localDateTime(LocalDateTime value) {
      return localDateTime((Field)Tools.field(value));
   }

   @Support({SQLDialect.CUBRID, SQLDialect.FIREBIRD, SQLDialect.H2, SQLDialect.HSQLDB, SQLDialect.MARIADB, SQLDialect.MYSQL, SQLDialect.POSTGRES, SQLDialect.SQLITE})
   public static Field<LocalDateTime> localDateTime(Field<LocalDateTime> field) {
      return new DateOrTime(field, SQLDataType.LOCALDATETIME);
   }

   @Support({SQLDialect.POSTGRES})
   public static Field<OffsetTime> offsetTime(String value) {
      return Tools.field(Convert.convert((Object)value, (Class)OffsetTime.class), OffsetTime.class);
   }

   @Support({SQLDialect.POSTGRES})
   public static Field<OffsetTime> offsetTime(OffsetTime value) {
      return offsetTime((Field)Tools.field(value));
   }

   @Support({SQLDialect.POSTGRES})
   public static Field<OffsetTime> offsetTime(Field<OffsetTime> field) {
      return new DateOrTime(field, SQLDataType.OFFSETTIME);
   }

   @Support({SQLDialect.POSTGRES})
   public static Field<OffsetDateTime> offsetDateTime(String value) {
      return Tools.field(Convert.convert((Object)value, (Class)OffsetDateTime.class), OffsetDateTime.class);
   }

   @Support({SQLDialect.POSTGRES})
   public static Field<OffsetDateTime> offsetDateTime(OffsetDateTime value) {
      return offsetDateTime((Field)Tools.field(value));
   }

   @Support({SQLDialect.POSTGRES})
   public static Field<OffsetDateTime> offsetDateTime(Field<OffsetDateTime> field) {
      return new DateOrTime(field, SQLDataType.OFFSETDATETIME);
   }

   @Support({SQLDialect.H2, SQLDialect.HSQLDB, SQLDialect.POSTGRES})
   public static Field<Date> toDate(String value, String format) {
      return toDate(Tools.field(value, (DataType)SQLDataType.VARCHAR), Tools.field(format, (DataType)SQLDataType.VARCHAR));
   }

   @Support({SQLDialect.H2, SQLDialect.HSQLDB, SQLDialect.POSTGRES})
   public static Field<Date> toDate(String value, Field<String> format) {
      return toDate(Tools.field(value, (DataType)SQLDataType.VARCHAR), nullSafe(format));
   }

   @Support({SQLDialect.H2, SQLDialect.HSQLDB, SQLDialect.POSTGRES})
   public static Field<Date> toDate(Field<String> value, String format) {
      return toDate(nullSafe(value), Tools.field(format, (DataType)SQLDataType.VARCHAR));
   }

   @Support({SQLDialect.H2, SQLDialect.HSQLDB, SQLDialect.POSTGRES})
   public static Field<Date> toDate(Field<String> value, Field<String> format) {
      return field("{to_date}({0}, {1})", SQLDataType.DATE, nullSafe(value), nullSafe(format));
   }

   @Support({SQLDialect.H2, SQLDialect.HSQLDB, SQLDialect.POSTGRES})
   public static Field<Timestamp> toTimestamp(String value, String format) {
      return toTimestamp(Tools.field(value, (DataType)SQLDataType.VARCHAR), Tools.field(format, (DataType)SQLDataType.VARCHAR));
   }

   @Support({SQLDialect.H2, SQLDialect.HSQLDB, SQLDialect.POSTGRES})
   public static Field<Timestamp> toTimestamp(String value, Field<String> format) {
      return toTimestamp(Tools.field(value, (DataType)SQLDataType.VARCHAR), nullSafe(format));
   }

   @Support({SQLDialect.H2, SQLDialect.HSQLDB, SQLDialect.POSTGRES})
   public static Field<Timestamp> toTimestamp(Field<String> value, String format) {
      return toTimestamp(nullSafe(value), Tools.field(format, (DataType)SQLDataType.VARCHAR));
   }

   @Support({SQLDialect.H2, SQLDialect.HSQLDB, SQLDialect.POSTGRES})
   public static Field<Timestamp> toTimestamp(Field<String> value, Field<String> format) {
      return field("{to_timestamp}({0}, {1})", SQLDataType.TIMESTAMP, nullSafe(value), nullSafe(format));
   }

   @Support({SQLDialect.H2, SQLDialect.HSQLDB, SQLDialect.POSTGRES})
   public static Field<LocalDate> toLocalDate(String value, String format) {
      return toDate(value, format).coerce(SQLDataType.LOCALDATE);
   }

   @Support({SQLDialect.H2, SQLDialect.HSQLDB, SQLDialect.POSTGRES})
   public static Field<LocalDate> toLocalDate(String value, Field<String> format) {
      return toDate(value, format).coerce(SQLDataType.LOCALDATE);
   }

   @Support({SQLDialect.H2, SQLDialect.HSQLDB, SQLDialect.POSTGRES})
   public static Field<LocalDate> toLocalDate(Field<String> value, String format) {
      return toDate(value, format).coerce(SQLDataType.LOCALDATE);
   }

   @Support({SQLDialect.H2, SQLDialect.HSQLDB, SQLDialect.POSTGRES})
   public static Field<LocalDate> toLocalDate(Field<String> value, Field<String> format) {
      return toDate(value, format).coerce(SQLDataType.LOCALDATE);
   }

   @Support({SQLDialect.H2, SQLDialect.HSQLDB, SQLDialect.POSTGRES})
   public static Field<LocalDateTime> toLocalDateTime(String value, String format) {
      return toTimestamp(value, format).coerce(SQLDataType.LOCALDATETIME);
   }

   @Support({SQLDialect.H2, SQLDialect.HSQLDB, SQLDialect.POSTGRES})
   public static Field<LocalDateTime> toLocalDateTime(String value, Field<String> format) {
      return toTimestamp(value, format).coerce(SQLDataType.LOCALDATETIME);
   }

   @Support({SQLDialect.H2, SQLDialect.HSQLDB, SQLDialect.POSTGRES})
   public static Field<LocalDateTime> toLocalDateTime(Field<String> value, String format) {
      return toTimestamp(value, format).coerce(SQLDataType.LOCALDATETIME);
   }

   @Support({SQLDialect.H2, SQLDialect.HSQLDB, SQLDialect.POSTGRES})
   public static Field<LocalDateTime> toLocalDateTime(Field<String> value, Field<String> format) {
      return toTimestamp(value, format).coerce(SQLDataType.LOCALDATETIME);
   }

   @Support({SQLDialect.CUBRID, SQLDialect.MARIADB, SQLDialect.MYSQL, SQLDialect.POSTGRES_9_5})
   public static GroupField rollup(Field<?>... fields) {
      return rollup((FieldOrRow[])nullSafe(fields));
   }

   @Support({SQLDialect.CUBRID, SQLDialect.MARIADB, SQLDialect.MYSQL, SQLDialect.POSTGRES_9_5})
   public static GroupField rollup(FieldOrRow... fields) {
      return new Rollup(fields);
   }

   @Support({SQLDialect.POSTGRES_9_5})
   public static GroupField cube(Field<?>... fields) {
      return cube((FieldOrRow[])nullSafe(fields));
   }

   @Support({SQLDialect.POSTGRES_9_5})
   public static GroupField cube(FieldOrRow... fields) {
      return field("{cube}({0})", Object.class, new QueryPartList(fields));
   }

   @Support({SQLDialect.POSTGRES_9_5})
   public static GroupField groupingSets(Field<?>... fields) {
      List<Field<?>>[] array = new List[fields.length];

      for(int i = 0; i < fields.length; ++i) {
         array[i] = Arrays.asList(fields[i]);
      }

      return groupingSets((Collection[])array);
   }

   @Support({SQLDialect.POSTGRES_9_5})
   public static GroupField groupingSets(Field<?>[]... fieldSets) {
      List<Field<?>>[] array = new List[fieldSets.length];

      for(int i = 0; i < fieldSets.length; ++i) {
         array[i] = Arrays.asList(fieldSets[i]);
      }

      return groupingSets((Collection[])array);
   }

   @Support({SQLDialect.POSTGRES_9_5})
   public static GroupField groupingSets(Collection<? extends Field<?>>... fieldSets) {
      WrappedList[] array = new WrappedList[fieldSets.length];

      for(int i = 0; i < fieldSets.length; ++i) {
         array[i] = new WrappedList(new QueryPartList(fieldSets[i]));
      }

      return new Function("grouping sets", SQLDataType.OTHER, array);
   }

   @Support({SQLDialect.POSTGRES_9_5})
   public static Field<Integer> grouping(Field<?> field) {
      return function("grouping", Integer.class, nullSafe(field));
   }

   @Support({})
   public static Field<Integer> groupingId(Field<?>... fields) {
      return function("grouping_id", Integer.class, nullSafe(fields));
   }

   @Support({SQLDialect.CUBRID, SQLDialect.FIREBIRD, SQLDialect.FIREBIRD, SQLDialect.H2, SQLDialect.HSQLDB, SQLDialect.MARIADB, SQLDialect.MYSQL, SQLDialect.POSTGRES, SQLDialect.SQLITE})
   public static Field<Integer> bitCount(Number value) {
      return bitCount(Tools.field((Object)value));
   }

   @Support({SQLDialect.CUBRID, SQLDialect.FIREBIRD, SQLDialect.FIREBIRD, SQLDialect.H2, SQLDialect.HSQLDB, SQLDialect.MARIADB, SQLDialect.MYSQL, SQLDialect.POSTGRES, SQLDialect.SQLITE})
   public static Field<Integer> bitCount(Field<? extends Number> field) {
      return new BitCount(nullSafe(field));
   }

   @Support({SQLDialect.CUBRID, SQLDialect.FIREBIRD, SQLDialect.H2, SQLDialect.HSQLDB, SQLDialect.MARIADB, SQLDialect.MYSQL, SQLDialect.POSTGRES, SQLDialect.SQLITE})
   public static <T extends Number> Field<T> bitNot(T value) {
      return bitNot(Tools.field((Object)value));
   }

   @Support({SQLDialect.CUBRID, SQLDialect.FIREBIRD, SQLDialect.H2, SQLDialect.HSQLDB, SQLDialect.MARIADB, SQLDialect.MYSQL, SQLDialect.POSTGRES, SQLDialect.SQLITE})
   public static <T extends Number> Field<T> bitNot(Field<T> field) {
      return new Neg(nullSafe(field), ExpressionOperator.BIT_NOT);
   }

   @Support({SQLDialect.CUBRID, SQLDialect.FIREBIRD, SQLDialect.H2, SQLDialect.HSQLDB, SQLDialect.MARIADB, SQLDialect.MYSQL, SQLDialect.POSTGRES, SQLDialect.SQLITE})
   public static <T extends Number> Field<T> bitAnd(T value1, T value2) {
      return bitAnd(Tools.field((Object)value1), Tools.field((Object)value2));
   }

   @Support({SQLDialect.CUBRID, SQLDialect.FIREBIRD, SQLDialect.H2, SQLDialect.HSQLDB, SQLDialect.MARIADB, SQLDialect.MYSQL, SQLDialect.POSTGRES, SQLDialect.SQLITE})
   public static <T extends Number> Field<T> bitAnd(T value1, Field<T> value2) {
      return bitAnd(Tools.field((Object)value1), nullSafe(value2));
   }

   @Support({SQLDialect.CUBRID, SQLDialect.FIREBIRD, SQLDialect.H2, SQLDialect.HSQLDB, SQLDialect.MARIADB, SQLDialect.MYSQL, SQLDialect.POSTGRES, SQLDialect.SQLITE})
   public static <T extends Number> Field<T> bitAnd(Field<T> value1, T value2) {
      return bitAnd(nullSafe(value1), Tools.field((Object)value2));
   }

   @Support({SQLDialect.CUBRID, SQLDialect.FIREBIRD, SQLDialect.H2, SQLDialect.HSQLDB, SQLDialect.MARIADB, SQLDialect.MYSQL, SQLDialect.POSTGRES, SQLDialect.SQLITE})
   public static <T extends Number> Field<T> bitAnd(Field<T> field1, Field<T> field2) {
      return new Expression(ExpressionOperator.BIT_AND, nullSafe(field1), new Field[]{nullSafe(field2)});
   }

   @Support({SQLDialect.CUBRID, SQLDialect.FIREBIRD, SQLDialect.H2, SQLDialect.HSQLDB, SQLDialect.MARIADB, SQLDialect.MYSQL, SQLDialect.POSTGRES, SQLDialect.SQLITE})
   public static <T extends Number> Field<T> bitNand(T value1, T value2) {
      return bitNand(Tools.field((Object)value1), Tools.field((Object)value2));
   }

   @Support({SQLDialect.CUBRID, SQLDialect.FIREBIRD, SQLDialect.H2, SQLDialect.HSQLDB, SQLDialect.MARIADB, SQLDialect.MYSQL, SQLDialect.POSTGRES, SQLDialect.SQLITE})
   public static <T extends Number> Field<T> bitNand(T value1, Field<T> value2) {
      return bitNand(Tools.field((Object)value1), nullSafe(value2));
   }

   @Support({SQLDialect.CUBRID, SQLDialect.FIREBIRD, SQLDialect.H2, SQLDialect.HSQLDB, SQLDialect.MARIADB, SQLDialect.MYSQL, SQLDialect.POSTGRES, SQLDialect.SQLITE})
   public static <T extends Number> Field<T> bitNand(Field<T> value1, T value2) {
      return bitNand(nullSafe(value1), Tools.field((Object)value2));
   }

   @Support({SQLDialect.CUBRID, SQLDialect.FIREBIRD, SQLDialect.H2, SQLDialect.HSQLDB, SQLDialect.MARIADB, SQLDialect.MYSQL, SQLDialect.POSTGRES, SQLDialect.SQLITE})
   public static <T extends Number> Field<T> bitNand(Field<T> field1, Field<T> field2) {
      return new Expression(ExpressionOperator.BIT_NAND, nullSafe(field1), new Field[]{nullSafe(field2)});
   }

   @Support({SQLDialect.CUBRID, SQLDialect.FIREBIRD, SQLDialect.H2, SQLDialect.HSQLDB, SQLDialect.MARIADB, SQLDialect.MYSQL, SQLDialect.POSTGRES, SQLDialect.SQLITE})
   public static <T extends Number> Field<T> bitOr(T value1, T value2) {
      return bitOr(Tools.field((Object)value1), Tools.field((Object)value2));
   }

   @Support({SQLDialect.CUBRID, SQLDialect.FIREBIRD, SQLDialect.H2, SQLDialect.HSQLDB, SQLDialect.MARIADB, SQLDialect.MYSQL, SQLDialect.POSTGRES, SQLDialect.SQLITE})
   public static <T extends Number> Field<T> bitOr(T value1, Field<T> value2) {
      return bitOr(Tools.field((Object)value1), nullSafe(value2));
   }

   @Support({SQLDialect.CUBRID, SQLDialect.FIREBIRD, SQLDialect.H2, SQLDialect.HSQLDB, SQLDialect.MARIADB, SQLDialect.MYSQL, SQLDialect.POSTGRES, SQLDialect.SQLITE})
   public static <T extends Number> Field<T> bitOr(Field<T> value1, T value2) {
      return bitOr(nullSafe(value1), Tools.field((Object)value2));
   }

   @Support({SQLDialect.CUBRID, SQLDialect.FIREBIRD, SQLDialect.H2, SQLDialect.HSQLDB, SQLDialect.MARIADB, SQLDialect.MYSQL, SQLDialect.POSTGRES, SQLDialect.SQLITE})
   public static <T extends Number> Field<T> bitOr(Field<T> field1, Field<T> field2) {
      return new Expression(ExpressionOperator.BIT_OR, nullSafe(field1), new Field[]{nullSafe(field2)});
   }

   @Support({SQLDialect.CUBRID, SQLDialect.FIREBIRD, SQLDialect.H2, SQLDialect.HSQLDB, SQLDialect.MARIADB, SQLDialect.MYSQL, SQLDialect.POSTGRES, SQLDialect.SQLITE})
   public static <T extends Number> Field<T> bitNor(T value1, T value2) {
      return bitNor(Tools.field((Object)value1), Tools.field((Object)value2));
   }

   @Support({SQLDialect.CUBRID, SQLDialect.FIREBIRD, SQLDialect.H2, SQLDialect.HSQLDB, SQLDialect.MARIADB, SQLDialect.MYSQL, SQLDialect.POSTGRES, SQLDialect.SQLITE})
   public static <T extends Number> Field<T> bitNor(T value1, Field<T> value2) {
      return bitNor(Tools.field((Object)value1), nullSafe(value2));
   }

   @Support({SQLDialect.CUBRID, SQLDialect.FIREBIRD, SQLDialect.H2, SQLDialect.HSQLDB, SQLDialect.MARIADB, SQLDialect.MYSQL, SQLDialect.POSTGRES, SQLDialect.SQLITE})
   public static <T extends Number> Field<T> bitNor(Field<T> value1, T value2) {
      return bitNor(nullSafe(value1), Tools.field((Object)value2));
   }

   @Support({SQLDialect.CUBRID, SQLDialect.FIREBIRD, SQLDialect.H2, SQLDialect.HSQLDB, SQLDialect.MARIADB, SQLDialect.MYSQL, SQLDialect.POSTGRES, SQLDialect.SQLITE})
   public static <T extends Number> Field<T> bitNor(Field<T> field1, Field<T> field2) {
      return new Expression(ExpressionOperator.BIT_NOR, nullSafe(field1), new Field[]{nullSafe(field2)});
   }

   @Support({SQLDialect.CUBRID, SQLDialect.FIREBIRD, SQLDialect.H2, SQLDialect.HSQLDB, SQLDialect.MARIADB, SQLDialect.MYSQL, SQLDialect.POSTGRES, SQLDialect.SQLITE})
   public static <T extends Number> Field<T> bitXor(T value1, T value2) {
      return bitXor(Tools.field((Object)value1), Tools.field((Object)value2));
   }

   @Support({SQLDialect.CUBRID, SQLDialect.FIREBIRD, SQLDialect.H2, SQLDialect.HSQLDB, SQLDialect.MARIADB, SQLDialect.MYSQL, SQLDialect.POSTGRES, SQLDialect.SQLITE})
   public static <T extends Number> Field<T> bitXor(T value1, Field<T> value2) {
      return bitXor(Tools.field((Object)value1), nullSafe(value2));
   }

   @Support({SQLDialect.CUBRID, SQLDialect.FIREBIRD, SQLDialect.H2, SQLDialect.HSQLDB, SQLDialect.MARIADB, SQLDialect.MYSQL, SQLDialect.POSTGRES, SQLDialect.SQLITE})
   public static <T extends Number> Field<T> bitXor(Field<T> value1, T value2) {
      return bitXor(nullSafe(value1), Tools.field((Object)value2));
   }

   @Support({SQLDialect.CUBRID, SQLDialect.FIREBIRD, SQLDialect.H2, SQLDialect.HSQLDB, SQLDialect.MARIADB, SQLDialect.MYSQL, SQLDialect.POSTGRES, SQLDialect.SQLITE})
   public static <T extends Number> Field<T> bitXor(Field<T> field1, Field<T> field2) {
      return new Expression(ExpressionOperator.BIT_XOR, nullSafe(field1), new Field[]{nullSafe(field2)});
   }

   @Support({SQLDialect.CUBRID, SQLDialect.FIREBIRD, SQLDialect.H2, SQLDialect.HSQLDB, SQLDialect.MARIADB, SQLDialect.MYSQL, SQLDialect.POSTGRES, SQLDialect.SQLITE})
   public static <T extends Number> Field<T> bitXNor(T value1, T value2) {
      return bitXNor(Tools.field((Object)value1), Tools.field((Object)value2));
   }

   @Support({SQLDialect.CUBRID, SQLDialect.FIREBIRD, SQLDialect.H2, SQLDialect.HSQLDB, SQLDialect.MARIADB, SQLDialect.MYSQL, SQLDialect.POSTGRES, SQLDialect.SQLITE})
   public static <T extends Number> Field<T> bitXNor(T value1, Field<T> value2) {
      return bitXNor(Tools.field((Object)value1), nullSafe(value2));
   }

   @Support({SQLDialect.CUBRID, SQLDialect.FIREBIRD, SQLDialect.H2, SQLDialect.HSQLDB, SQLDialect.MARIADB, SQLDialect.MYSQL, SQLDialect.POSTGRES, SQLDialect.SQLITE})
   public static <T extends Number> Field<T> bitXNor(Field<T> value1, T value2) {
      return bitXNor(nullSafe(value1), Tools.field((Object)value2));
   }

   @Support({SQLDialect.CUBRID, SQLDialect.FIREBIRD, SQLDialect.H2, SQLDialect.HSQLDB, SQLDialect.MARIADB, SQLDialect.MYSQL, SQLDialect.POSTGRES, SQLDialect.SQLITE})
   public static <T extends Number> Field<T> bitXNor(Field<T> field1, Field<T> field2) {
      return new Expression(ExpressionOperator.BIT_XNOR, nullSafe(field1), new Field[]{nullSafe(field2)});
   }

   @Support({SQLDialect.CUBRID, SQLDialect.FIREBIRD, SQLDialect.H2, SQLDialect.HSQLDB, SQLDialect.MARIADB, SQLDialect.MYSQL, SQLDialect.POSTGRES, SQLDialect.SQLITE})
   public static <T extends Number> Field<T> shl(T value1, Number value2) {
      return shl(Tools.field((Object)value1), Tools.field((Object)value2));
   }

   @Support({SQLDialect.CUBRID, SQLDialect.FIREBIRD, SQLDialect.H2, SQLDialect.HSQLDB, SQLDialect.MARIADB, SQLDialect.MYSQL, SQLDialect.POSTGRES, SQLDialect.SQLITE})
   public static <T extends Number> Field<T> shl(T value1, Field<? extends Number> value2) {
      return shl(Tools.field((Object)value1), nullSafe(value2));
   }

   @Support({SQLDialect.CUBRID, SQLDialect.FIREBIRD, SQLDialect.H2, SQLDialect.HSQLDB, SQLDialect.MARIADB, SQLDialect.MYSQL, SQLDialect.POSTGRES, SQLDialect.SQLITE})
   public static <T extends Number> Field<T> shl(Field<T> value1, Number value2) {
      return shl(nullSafe(value1), Tools.field((Object)value2));
   }

   @Support({SQLDialect.CUBRID, SQLDialect.FIREBIRD, SQLDialect.H2, SQLDialect.HSQLDB, SQLDialect.MARIADB, SQLDialect.MYSQL, SQLDialect.POSTGRES, SQLDialect.SQLITE})
   public static <T extends Number> Field<T> shl(Field<T> field1, Field<? extends Number> field2) {
      return new Expression(ExpressionOperator.SHL, nullSafe(field1), new Field[]{nullSafe(field2)});
   }

   @Support({SQLDialect.CUBRID, SQLDialect.FIREBIRD, SQLDialect.H2, SQLDialect.HSQLDB, SQLDialect.MARIADB, SQLDialect.MYSQL, SQLDialect.POSTGRES, SQLDialect.SQLITE})
   public static <T extends Number> Field<T> shr(T value1, Number value2) {
      return shr(Tools.field((Object)value1), Tools.field((Object)value2));
   }

   @Support({SQLDialect.CUBRID, SQLDialect.FIREBIRD, SQLDialect.H2, SQLDialect.HSQLDB, SQLDialect.MARIADB, SQLDialect.MYSQL, SQLDialect.POSTGRES, SQLDialect.SQLITE})
   public static <T extends Number> Field<T> shr(T value1, Field<? extends Number> value2) {
      return shr(Tools.field((Object)value1), nullSafe(value2));
   }

   @Support({SQLDialect.CUBRID, SQLDialect.FIREBIRD, SQLDialect.H2, SQLDialect.HSQLDB, SQLDialect.MARIADB, SQLDialect.MYSQL, SQLDialect.POSTGRES, SQLDialect.SQLITE})
   public static <T extends Number> Field<T> shr(Field<T> value1, Number value2) {
      return shr(nullSafe(value1), Tools.field((Object)value2));
   }

   @Support({SQLDialect.CUBRID, SQLDialect.H2, SQLDialect.FIREBIRD, SQLDialect.HSQLDB, SQLDialect.MARIADB, SQLDialect.MYSQL, SQLDialect.POSTGRES, SQLDialect.SQLITE})
   public static <T extends Number> Field<T> shr(Field<T> field1, Field<? extends Number> field2) {
      return new Expression(ExpressionOperator.SHR, nullSafe(field1), new Field[]{nullSafe(field2)});
   }

   @Support({SQLDialect.CUBRID, SQLDialect.DERBY, SQLDialect.FIREBIRD, SQLDialect.H2, SQLDialect.HSQLDB, SQLDialect.MARIADB, SQLDialect.MYSQL, SQLDialect.POSTGRES, SQLDialect.SQLITE})
   public static Field<BigDecimal> rand() {
      return new Rand();
   }

   @Support
   public static <T> Field<T> greatest(T value, T... values) {
      return greatest(Tools.field(value), (Field[])Tools.fields(values).toArray(Tools.EMPTY_FIELD));
   }

   @Support
   public static <T> Field<T> greatest(Field<T> field, Field<?>... others) {
      return new Greatest(nullSafeDataType(field), nullSafe(Tools.combine(field, others)));
   }

   @Support
   public static <T> Field<T> least(T value, T... values) {
      return least(Tools.field(value), (Field[])Tools.fields(values).toArray(Tools.EMPTY_FIELD));
   }

   @Support
   public static <T> Field<T> least(Field<T> field, Field<?>... others) {
      return new Least(nullSafeDataType(field), nullSafe(Tools.combine(field, others)));
   }

   @Support
   public static Field<Integer> sign(Number value) {
      return sign(Tools.field((Object)value));
   }

   @Support
   public static Field<Integer> sign(Field<? extends Number> field) {
      return new Sign(nullSafe(field));
   }

   @Support
   public static <T extends Number> Field<T> abs(T value) {
      return abs(Tools.field((Object)value));
   }

   @Support
   public static <T extends Number> Field<T> abs(Field<T> field) {
      return function("abs", nullSafeDataType(field), nullSafe(field));
   }

   @Support
   public static <T extends Number> Field<T> round(T value) {
      return round(Tools.field((Object)value));
   }

   @Support
   public static <T extends Number> Field<T> round(Field<T> field) {
      return new Round(nullSafe(field));
   }

   @Support
   public static <T extends Number> Field<T> round(T value, int decimals) {
      return round(Tools.field((Object)value), decimals);
   }

   @Support
   public static <T extends Number> Field<T> round(Field<T> field, int decimals) {
      return new Round(nullSafe(field), decimals);
   }

   @Support
   public static <T extends Number> Field<T> floor(T value) {
      return floor(Tools.field((Object)value));
   }

   @Support
   public static <T extends Number> Field<T> floor(Field<T> field) {
      return new Floor(nullSafe(field));
   }

   @Support
   public static <T extends Number> Field<T> ceil(T value) {
      return ceil(Tools.field((Object)value));
   }

   @Support
   public static <T extends Number> Field<T> ceil(Field<T> field) {
      return new Ceil(nullSafe(field));
   }

   @Support({SQLDialect.CUBRID, SQLDialect.DERBY, SQLDialect.FIREBIRD, SQLDialect.H2, SQLDialect.HSQLDB, SQLDialect.MARIADB, SQLDialect.MYSQL, SQLDialect.POSTGRES})
   public static <T extends Number> Field<T> trunc(T number) {
      return trunc((Field)Tools.field((Object)number), (Field)inline((int)0));
   }

   @Support({SQLDialect.CUBRID, SQLDialect.DERBY, SQLDialect.FIREBIRD, SQLDialect.H2, SQLDialect.HSQLDB, SQLDialect.MARIADB, SQLDialect.MYSQL, SQLDialect.POSTGRES})
   public static <T extends Number> Field<T> trunc(T number, int decimals) {
      return trunc((Field)Tools.field((Object)number), (Field)inline(decimals));
   }

   @Support({SQLDialect.CUBRID, SQLDialect.DERBY, SQLDialect.FIREBIRD, SQLDialect.H2, SQLDialect.HSQLDB, SQLDialect.MARIADB, SQLDialect.MYSQL, SQLDialect.POSTGRES})
   public static <T extends Number> Field<T> trunc(Field<T> number, int decimals) {
      return trunc((Field)nullSafe(number), (Field)inline(decimals));
   }

   @Support({SQLDialect.CUBRID, SQLDialect.DERBY, SQLDialect.FIREBIRD, SQLDialect.H2, SQLDialect.HSQLDB, SQLDialect.MARIADB, SQLDialect.MYSQL, SQLDialect.POSTGRES})
   public static <T extends Number> Field<T> trunc(T number, Field<Integer> decimals) {
      return trunc(Tools.field((Object)number), nullSafe(decimals));
   }

   @Support({SQLDialect.CUBRID, SQLDialect.DERBY, SQLDialect.FIREBIRD, SQLDialect.H2, SQLDialect.HSQLDB, SQLDialect.MARIADB, SQLDialect.MYSQL, SQLDialect.POSTGRES})
   public static <T extends Number> Field<T> trunc(Field<T> number, Field<Integer> decimals) {
      return new Trunc(nullSafe(number), nullSafe(decimals));
   }

   @Support({SQLDialect.CUBRID, SQLDialect.DERBY, SQLDialect.FIREBIRD, SQLDialect.H2, SQLDialect.HSQLDB, SQLDialect.MARIADB, SQLDialect.MYSQL, SQLDialect.POSTGRES})
   public static Field<BigDecimal> sqrt(Number value) {
      return sqrt(Tools.field((Object)value));
   }

   @Support({SQLDialect.CUBRID, SQLDialect.DERBY, SQLDialect.FIREBIRD, SQLDialect.H2, SQLDialect.HSQLDB, SQLDialect.MARIADB, SQLDialect.MYSQL, SQLDialect.POSTGRES})
   public static Field<BigDecimal> sqrt(Field<? extends Number> field) {
      return new Sqrt(nullSafe(field));
   }

   @Support({SQLDialect.CUBRID, SQLDialect.DERBY, SQLDialect.FIREBIRD, SQLDialect.H2, SQLDialect.HSQLDB, SQLDialect.MARIADB, SQLDialect.MYSQL, SQLDialect.POSTGRES})
   public static Field<BigDecimal> exp(Number value) {
      return exp(Tools.field((Object)value));
   }

   @Support({SQLDialect.CUBRID, SQLDialect.DERBY, SQLDialect.FIREBIRD, SQLDialect.H2, SQLDialect.HSQLDB, SQLDialect.MARIADB, SQLDialect.MYSQL, SQLDialect.POSTGRES})
   public static Field<BigDecimal> exp(Field<? extends Number> field) {
      return function("exp", SQLDataType.NUMERIC, nullSafe(field));
   }

   @Support({SQLDialect.CUBRID, SQLDialect.DERBY, SQLDialect.FIREBIRD, SQLDialect.H2, SQLDialect.HSQLDB, SQLDialect.MARIADB, SQLDialect.MYSQL, SQLDialect.POSTGRES})
   public static Field<BigDecimal> ln(Number value) {
      return ln(Tools.field((Object)value));
   }

   @Support({SQLDialect.CUBRID, SQLDialect.DERBY, SQLDialect.FIREBIRD, SQLDialect.H2, SQLDialect.HSQLDB, SQLDialect.MARIADB, SQLDialect.MYSQL, SQLDialect.POSTGRES})
   public static Field<BigDecimal> ln(Field<? extends Number> field) {
      return new Ln(nullSafe(field));
   }

   @Support({SQLDialect.CUBRID, SQLDialect.DERBY, SQLDialect.FIREBIRD, SQLDialect.H2, SQLDialect.HSQLDB, SQLDialect.MARIADB, SQLDialect.MYSQL, SQLDialect.POSTGRES})
   public static Field<BigDecimal> log(Number value, int base) {
      return log(Tools.field((Object)value), base);
   }

   @Support({SQLDialect.CUBRID, SQLDialect.DERBY, SQLDialect.FIREBIRD, SQLDialect.H2, SQLDialect.HSQLDB, SQLDialect.MARIADB, SQLDialect.MYSQL, SQLDialect.POSTGRES})
   public static Field<BigDecimal> log(Field<? extends Number> field, int base) {
      return new Ln(nullSafe(field), base);
   }

   @Support({SQLDialect.CUBRID, SQLDialect.DERBY, SQLDialect.FIREBIRD, SQLDialect.H2, SQLDialect.HSQLDB, SQLDialect.MARIADB, SQLDialect.MYSQL, SQLDialect.POSTGRES})
   public static Field<BigDecimal> power(Number value, Number exponent) {
      return power(Tools.field((Object)value), Tools.field((Object)exponent));
   }

   @Support({SQLDialect.CUBRID, SQLDialect.DERBY, SQLDialect.FIREBIRD, SQLDialect.H2, SQLDialect.HSQLDB, SQLDialect.MARIADB, SQLDialect.MYSQL, SQLDialect.POSTGRES})
   public static Field<BigDecimal> power(Field<? extends Number> field, Number exponent) {
      return power(nullSafe(field), Tools.field((Object)exponent));
   }

   @Support({SQLDialect.CUBRID, SQLDialect.DERBY, SQLDialect.FIREBIRD, SQLDialect.H2, SQLDialect.HSQLDB, SQLDialect.MARIADB, SQLDialect.MYSQL, SQLDialect.POSTGRES})
   public static Field<BigDecimal> power(Number value, Field<? extends Number> exponent) {
      return power(Tools.field((Object)value), nullSafe(exponent));
   }

   @Support({SQLDialect.CUBRID, SQLDialect.DERBY, SQLDialect.FIREBIRD, SQLDialect.H2, SQLDialect.HSQLDB, SQLDialect.MARIADB, SQLDialect.MYSQL, SQLDialect.POSTGRES})
   public static Field<BigDecimal> power(Field<? extends Number> field, Field<? extends Number> exponent) {
      return new Power(nullSafe(field), nullSafe(exponent));
   }

   @Support({SQLDialect.CUBRID, SQLDialect.DERBY, SQLDialect.FIREBIRD, SQLDialect.H2, SQLDialect.HSQLDB, SQLDialect.MARIADB, SQLDialect.MYSQL, SQLDialect.POSTGRES})
   public static Field<BigDecimal> acos(Number value) {
      return acos(Tools.field((Object)value));
   }

   @Support({SQLDialect.CUBRID, SQLDialect.DERBY, SQLDialect.FIREBIRD, SQLDialect.H2, SQLDialect.HSQLDB, SQLDialect.MARIADB, SQLDialect.MYSQL, SQLDialect.POSTGRES})
   public static Field<BigDecimal> acos(Field<? extends Number> field) {
      return new Acos(nullSafe(field));
   }

   @Support({SQLDialect.CUBRID, SQLDialect.DERBY, SQLDialect.FIREBIRD, SQLDialect.H2, SQLDialect.HSQLDB, SQLDialect.MARIADB, SQLDialect.MYSQL, SQLDialect.POSTGRES})
   public static Field<BigDecimal> asin(Number value) {
      return asin(Tools.field((Object)value));
   }

   @Support({SQLDialect.CUBRID, SQLDialect.DERBY, SQLDialect.FIREBIRD, SQLDialect.H2, SQLDialect.HSQLDB, SQLDialect.MARIADB, SQLDialect.MYSQL, SQLDialect.POSTGRES})
   public static Field<BigDecimal> asin(Field<? extends Number> field) {
      return new Asin(nullSafe(field));
   }

   @Support({SQLDialect.CUBRID, SQLDialect.DERBY, SQLDialect.FIREBIRD, SQLDialect.H2, SQLDialect.HSQLDB, SQLDialect.MARIADB, SQLDialect.MYSQL, SQLDialect.POSTGRES})
   public static Field<BigDecimal> atan(Number value) {
      return atan(Tools.field((Object)value));
   }

   @Support({SQLDialect.CUBRID, SQLDialect.DERBY, SQLDialect.FIREBIRD, SQLDialect.H2, SQLDialect.HSQLDB, SQLDialect.MARIADB, SQLDialect.MYSQL, SQLDialect.POSTGRES})
   public static Field<BigDecimal> atan(Field<? extends Number> field) {
      return new Atan(nullSafe(field));
   }

   @Support({SQLDialect.CUBRID, SQLDialect.DERBY, SQLDialect.FIREBIRD, SQLDialect.H2, SQLDialect.HSQLDB, SQLDialect.MARIADB, SQLDialect.MYSQL, SQLDialect.POSTGRES})
   public static Field<BigDecimal> atan2(Number x, Number y) {
      return atan2(Tools.field((Object)x), Tools.field((Object)y));
   }

   @Support({SQLDialect.CUBRID, SQLDialect.DERBY, SQLDialect.FIREBIRD, SQLDialect.H2, SQLDialect.HSQLDB, SQLDialect.MARIADB, SQLDialect.MYSQL, SQLDialect.POSTGRES})
   public static Field<BigDecimal> atan2(Number x, Field<? extends Number> y) {
      return atan2(Tools.field((Object)x), nullSafe(y));
   }

   @Support({SQLDialect.CUBRID, SQLDialect.DERBY, SQLDialect.FIREBIRD, SQLDialect.H2, SQLDialect.HSQLDB, SQLDialect.MARIADB, SQLDialect.MYSQL, SQLDialect.POSTGRES})
   public static Field<BigDecimal> atan2(Field<? extends Number> x, Number y) {
      return atan2(nullSafe(x), Tools.field((Object)y));
   }

   @Support({SQLDialect.CUBRID, SQLDialect.DERBY, SQLDialect.FIREBIRD, SQLDialect.H2, SQLDialect.HSQLDB, SQLDialect.MARIADB, SQLDialect.MYSQL, SQLDialect.POSTGRES})
   public static Field<BigDecimal> atan2(Field<? extends Number> x, Field<? extends Number> y) {
      return new Function(Term.ATAN2, SQLDataType.NUMERIC, new QueryPart[]{nullSafe(x), nullSafe(y)});
   }

   @Support({SQLDialect.CUBRID, SQLDialect.DERBY, SQLDialect.FIREBIRD, SQLDialect.H2, SQLDialect.HSQLDB, SQLDialect.MARIADB, SQLDialect.MYSQL, SQLDialect.POSTGRES})
   public static Field<BigDecimal> cos(Number value) {
      return cos(Tools.field((Object)value));
   }

   @Support({SQLDialect.CUBRID, SQLDialect.DERBY, SQLDialect.FIREBIRD, SQLDialect.H2, SQLDialect.HSQLDB, SQLDialect.MARIADB, SQLDialect.MYSQL, SQLDialect.POSTGRES})
   public static Field<BigDecimal> cos(Field<? extends Number> field) {
      return function("cos", SQLDataType.NUMERIC, nullSafe(field));
   }

   @Support({SQLDialect.CUBRID, SQLDialect.DERBY, SQLDialect.FIREBIRD, SQLDialect.H2, SQLDialect.HSQLDB, SQLDialect.MARIADB, SQLDialect.MYSQL, SQLDialect.POSTGRES})
   public static Field<BigDecimal> sin(Number value) {
      return sin(Tools.field((Object)value));
   }

   @Support({SQLDialect.CUBRID, SQLDialect.DERBY, SQLDialect.FIREBIRD, SQLDialect.H2, SQLDialect.HSQLDB, SQLDialect.MARIADB, SQLDialect.MYSQL, SQLDialect.POSTGRES})
   public static Field<BigDecimal> sin(Field<? extends Number> field) {
      return function("sin", SQLDataType.NUMERIC, nullSafe(field));
   }

   @Support({SQLDialect.CUBRID, SQLDialect.DERBY, SQLDialect.FIREBIRD, SQLDialect.H2, SQLDialect.HSQLDB, SQLDialect.MARIADB, SQLDialect.MYSQL, SQLDialect.POSTGRES})
   public static Field<BigDecimal> tan(Number value) {
      return tan(Tools.field((Object)value));
   }

   @Support({SQLDialect.CUBRID, SQLDialect.DERBY, SQLDialect.FIREBIRD, SQLDialect.H2, SQLDialect.HSQLDB, SQLDialect.MARIADB, SQLDialect.MYSQL, SQLDialect.POSTGRES})
   public static Field<BigDecimal> tan(Field<? extends Number> field) {
      return function("tan", SQLDataType.NUMERIC, nullSafe(field));
   }

   @Support({SQLDialect.CUBRID, SQLDialect.DERBY, SQLDialect.FIREBIRD, SQLDialect.H2, SQLDialect.HSQLDB, SQLDialect.MARIADB, SQLDialect.MYSQL, SQLDialect.POSTGRES})
   public static Field<BigDecimal> cot(Number value) {
      return cot(Tools.field((Object)value));
   }

   @Support({SQLDialect.CUBRID, SQLDialect.DERBY, SQLDialect.FIREBIRD, SQLDialect.H2, SQLDialect.HSQLDB, SQLDialect.MARIADB, SQLDialect.MYSQL, SQLDialect.POSTGRES})
   public static Field<BigDecimal> cot(Field<? extends Number> field) {
      return new Cot(nullSafe(field));
   }

   @Support({SQLDialect.CUBRID, SQLDialect.DERBY, SQLDialect.FIREBIRD, SQLDialect.H2, SQLDialect.HSQLDB, SQLDialect.MARIADB, SQLDialect.MYSQL, SQLDialect.POSTGRES})
   public static Field<BigDecimal> sinh(Number value) {
      return sinh(Tools.field((Object)value));
   }

   @Support({SQLDialect.CUBRID, SQLDialect.DERBY, SQLDialect.FIREBIRD, SQLDialect.H2, SQLDialect.HSQLDB, SQLDialect.MARIADB, SQLDialect.MYSQL, SQLDialect.POSTGRES})
   public static Field<BigDecimal> sinh(Field<? extends Number> field) {
      return new Sinh(nullSafe(field));
   }

   @Support({SQLDialect.CUBRID, SQLDialect.DERBY, SQLDialect.FIREBIRD, SQLDialect.H2, SQLDialect.HSQLDB, SQLDialect.MARIADB, SQLDialect.MYSQL, SQLDialect.POSTGRES})
   public static Field<BigDecimal> cosh(Number value) {
      return cosh(Tools.field((Object)value));
   }

   @Support({SQLDialect.CUBRID, SQLDialect.DERBY, SQLDialect.FIREBIRD, SQLDialect.H2, SQLDialect.HSQLDB, SQLDialect.MARIADB, SQLDialect.MYSQL, SQLDialect.POSTGRES})
   public static Field<BigDecimal> cosh(Field<? extends Number> field) {
      return new Cosh(nullSafe(field));
   }

   @Support({SQLDialect.CUBRID, SQLDialect.DERBY, SQLDialect.FIREBIRD, SQLDialect.H2, SQLDialect.HSQLDB, SQLDialect.MARIADB, SQLDialect.MYSQL, SQLDialect.POSTGRES})
   public static Field<BigDecimal> tanh(Number value) {
      return tanh(Tools.field((Object)value));
   }

   @Support({SQLDialect.CUBRID, SQLDialect.DERBY, SQLDialect.FIREBIRD, SQLDialect.H2, SQLDialect.HSQLDB, SQLDialect.MARIADB, SQLDialect.MYSQL, SQLDialect.POSTGRES})
   public static Field<BigDecimal> tanh(Field<? extends Number> field) {
      return new Tanh(nullSafe(field));
   }

   @Support({SQLDialect.CUBRID, SQLDialect.DERBY, SQLDialect.FIREBIRD, SQLDialect.H2, SQLDialect.HSQLDB, SQLDialect.MARIADB, SQLDialect.MYSQL, SQLDialect.POSTGRES})
   public static Field<BigDecimal> coth(Number value) {
      return coth(Tools.field((Object)value));
   }

   @Support({SQLDialect.CUBRID, SQLDialect.DERBY, SQLDialect.FIREBIRD, SQLDialect.H2, SQLDialect.HSQLDB, SQLDialect.MARIADB, SQLDialect.MYSQL, SQLDialect.POSTGRES})
   public static Field<BigDecimal> coth(Field<? extends Number> field) {
      field = nullSafe(field);
      return exp(field.mul((int)2)).add((int)1).div(exp(field.mul((int)2)).sub((int)1));
   }

   @Support
   public static Field<BigDecimal> deg(Number value) {
      return deg(Tools.field((Object)value));
   }

   @Support
   public static Field<BigDecimal> deg(Field<? extends Number> field) {
      return new Degrees(nullSafe(field));
   }

   @Support
   public static Field<BigDecimal> rad(Number value) {
      return rad(Tools.field((Object)value));
   }

   @Support
   public static Field<BigDecimal> rad(Field<? extends Number> field) {
      return new Radians(nullSafe(field));
   }

   @Support({SQLDialect.CUBRID})
   public static Field<Integer> level() {
      return field("level", Integer.class);
   }

   @Support({SQLDialect.CUBRID})
   public static Field<Boolean> connectByIsCycle() {
      return field("connect_by_iscycle", Boolean.class);
   }

   @Support({SQLDialect.CUBRID})
   public static Field<Boolean> connectByIsLeaf() {
      return field("connect_by_isleaf", Boolean.class);
   }

   @Support({SQLDialect.CUBRID})
   public static <T> Field<T> connectByRoot(Field<T> field) {
      return field("{connect_by_root} {0}", nullSafe(field).getDataType(), field);
   }

   @Support({SQLDialect.CUBRID})
   public static Field<String> sysConnectByPath(Field<?> field, String separator) {
      return field("{sys_connect_by_path}({0}, {1})", String.class, field, inline(separator));
   }

   @Support({SQLDialect.CUBRID})
   public static <T> Field<T> prior(Field<T> field) {
      return new Prior(field);
   }

   @Support({SQLDialect.CUBRID})
   public static Field<Integer> rownum() {
      return field("rownum", Integer.class);
   }

   @Support
   public static AggregateFunction<Integer> count() {
      return count(Function.ASTERISK);
   }

   @Support
   public static AggregateFunction<Integer> count(Field<?> field) {
      return new Function("count", SQLDataType.INTEGER, new QueryPart[]{nullSafe(field)});
   }

   @Support
   public static AggregateFunction<Integer> count(Table<?> table) {
      return new CountTable(table, false);
   }

   @Support({SQLDialect.CUBRID, SQLDialect.DERBY, SQLDialect.H2, SQLDialect.HSQLDB, SQLDialect.FIREBIRD, SQLDialect.MARIADB, SQLDialect.MYSQL, SQLDialect.POSTGRES, SQLDialect.SQLITE})
   public static AggregateFunction<Integer> countDistinct(Field<?> field) {
      return new Function("count", true, SQLDataType.INTEGER, new QueryPart[]{nullSafe(field)});
   }

   @Support({SQLDialect.CUBRID, SQLDialect.DERBY, SQLDialect.H2, SQLDialect.HSQLDB, SQLDialect.FIREBIRD, SQLDialect.MARIADB, SQLDialect.MYSQL, SQLDialect.POSTGRES, SQLDialect.SQLITE})
   public static AggregateFunction<Integer> countDistinct(Table<?> table) {
      return new CountTable(table, true);
   }

   @Support({SQLDialect.HSQLDB, SQLDialect.MYSQL, SQLDialect.POSTGRES})
   public static AggregateFunction<Integer> countDistinct(Field<?>... fields) {
      return new Function("count", true, SQLDataType.INTEGER, nullSafe(fields));
   }

   @Support
   public static AggregateFunction<Boolean> every(Field<Boolean> field) {
      return boolAnd(field);
   }

   @Support
   public static AggregateFunction<Boolean> every(Condition condition) {
      return boolAnd(condition);
   }

   @Support
   public static AggregateFunction<Boolean> boolAnd(Field<Boolean> field) {
      return boolAnd(condition(nullSafe(field)));
   }

   @Support
   public static AggregateFunction<Boolean> boolAnd(Condition condition) {
      return new BoolAnd(condition);
   }

   @Support
   public static AggregateFunction<Boolean> boolOr(Field<Boolean> field) {
      return boolOr(condition(nullSafe(field)));
   }

   @Support
   public static AggregateFunction<Boolean> boolOr(Condition condition) {
      return new BoolOr(condition);
   }

   @Support({SQLDialect.HSQLDB, SQLDialect.POSTGRES})
   public static <T> ArrayAggOrderByStep<T[]> arrayAgg(Field<T> field) {
      return new Function(Term.ARRAY_AGG, field.getDataType().getArrayDataType(), new QueryPart[]{nullSafe(field)});
   }

   @Support({SQLDialect.H2, SQLDialect.HSQLDB, SQLDialect.POSTGRES})
   public static <T> Field<T[]> array(T... values) {
      return array((Collection)Tools.fields(values));
   }

   @SafeVarargs
   @Support({SQLDialect.H2, SQLDialect.HSQLDB, SQLDialect.POSTGRES})
   public static <T> Field<T[]> array(Field<T>... fields) {
      return array((Collection)Arrays.asList(fields));
   }

   @Support({SQLDialect.H2, SQLDialect.HSQLDB, SQLDialect.POSTGRES})
   public static <T> Field<T[]> array(Collection<? extends Field<T>> fields) {
      return new Array(fields);
   }

   @Support
   public static <T> AggregateFunction<T> max(Field<T> field) {
      return new Function("max", nullSafeDataType(field), new QueryPart[]{nullSafe(field)});
   }

   @Support
   public static <T> AggregateFunction<T> maxDistinct(Field<T> field) {
      return new Function("max", true, nullSafeDataType(field), new QueryPart[]{nullSafe(field)});
   }

   @Support
   public static <T> AggregateFunction<T> min(Field<T> field) {
      return new Function("min", nullSafeDataType(field), new QueryPart[]{nullSafe(field)});
   }

   @Support
   public static <T> AggregateFunction<T> minDistinct(Field<T> field) {
      return new Function("min", true, nullSafeDataType(field), new QueryPart[]{nullSafe(field)});
   }

   @Support
   public static AggregateFunction<BigDecimal> sum(Field<? extends Number> field) {
      return new Function("sum", SQLDataType.NUMERIC, new QueryPart[]{nullSafe(field)});
   }

   @Support
   public static AggregateFunction<BigDecimal> sumDistinct(Field<? extends Number> field) {
      return new Function("sum", true, SQLDataType.NUMERIC, new QueryPart[]{nullSafe(field)});
   }

   @Support
   public static AggregateFunction<BigDecimal> avg(Field<? extends Number> field) {
      return new Function("avg", SQLDataType.NUMERIC, new QueryPart[]{nullSafe(field)});
   }

   @Support
   public static AggregateFunction<BigDecimal> avgDistinct(Field<? extends Number> field) {
      return new Function("avg", true, SQLDataType.NUMERIC, new QueryPart[]{nullSafe(field)});
   }

   @Support({SQLDialect.CUBRID, SQLDialect.HSQLDB, SQLDialect.POSTGRES_9_4})
   public static AggregateFunction<BigDecimal> median(Field<? extends Number> field) {
      return new Function(Term.MEDIAN, SQLDataType.NUMERIC, new QueryPart[]{nullSafe(field)});
   }

   @Support({SQLDialect.CUBRID, SQLDialect.H2, SQLDialect.HSQLDB, SQLDialect.MARIADB, SQLDialect.MYSQL, SQLDialect.POSTGRES})
   public static AggregateFunction<BigDecimal> stddevPop(Field<? extends Number> field) {
      return new Function(Term.STDDEV_POP, SQLDataType.NUMERIC, new QueryPart[]{nullSafe(field)});
   }

   @Support({SQLDialect.CUBRID, SQLDialect.H2, SQLDialect.HSQLDB, SQLDialect.MARIADB, SQLDialect.MYSQL, SQLDialect.POSTGRES})
   public static AggregateFunction<BigDecimal> stddevSamp(Field<? extends Number> field) {
      return new Function(Term.STDDEV_SAMP, SQLDataType.NUMERIC, new QueryPart[]{nullSafe(field)});
   }

   @Support({SQLDialect.CUBRID, SQLDialect.H2, SQLDialect.HSQLDB, SQLDialect.MARIADB, SQLDialect.MYSQL, SQLDialect.POSTGRES})
   public static AggregateFunction<BigDecimal> varPop(Field<? extends Number> field) {
      return new Function(Term.VAR_POP, SQLDataType.NUMERIC, new QueryPart[]{nullSafe(field)});
   }

   @Support({SQLDialect.CUBRID, SQLDialect.H2, SQLDialect.HSQLDB, SQLDialect.MARIADB, SQLDialect.MYSQL, SQLDialect.POSTGRES})
   public static AggregateFunction<BigDecimal> varSamp(Field<? extends Number> field) {
      return new Function(Term.VAR_SAMP, SQLDataType.NUMERIC, new QueryPart[]{nullSafe(field)});
   }

   @Support({SQLDialect.POSTGRES})
   public static AggregateFunction<BigDecimal> regrSlope(Field<? extends Number> y, Field<? extends Number> x) {
      return new Function("regr_slope", SQLDataType.NUMERIC, new QueryPart[]{nullSafe(y), nullSafe(x)});
   }

   @Support({SQLDialect.POSTGRES})
   public static AggregateFunction<BigDecimal> regrIntercept(Field<? extends Number> y, Field<? extends Number> x) {
      return new Function("regr_intercept", SQLDataType.NUMERIC, new QueryPart[]{nullSafe(y), nullSafe(x)});
   }

   @Support({SQLDialect.POSTGRES})
   public static AggregateFunction<BigDecimal> regrCount(Field<? extends Number> y, Field<? extends Number> x) {
      return new Function("regr_count", SQLDataType.NUMERIC, new QueryPart[]{nullSafe(y), nullSafe(x)});
   }

   @Support({SQLDialect.POSTGRES})
   public static AggregateFunction<BigDecimal> regrR2(Field<? extends Number> y, Field<? extends Number> x) {
      return new Function("regr_r2", SQLDataType.NUMERIC, new QueryPart[]{nullSafe(y), nullSafe(x)});
   }

   @Support({SQLDialect.POSTGRES})
   public static AggregateFunction<BigDecimal> regrAvgX(Field<? extends Number> y, Field<? extends Number> x) {
      return new Function("regr_avgx", SQLDataType.NUMERIC, new QueryPart[]{nullSafe(y), nullSafe(x)});
   }

   @Support({SQLDialect.POSTGRES})
   public static AggregateFunction<BigDecimal> regrAvgY(Field<? extends Number> y, Field<? extends Number> x) {
      return new Function("regr_avgy", SQLDataType.NUMERIC, new QueryPart[]{nullSafe(y), nullSafe(x)});
   }

   @Support({SQLDialect.POSTGRES})
   public static AggregateFunction<BigDecimal> regrSXX(Field<? extends Number> y, Field<? extends Number> x) {
      return new Function("regr_sxx", SQLDataType.NUMERIC, new QueryPart[]{nullSafe(y), nullSafe(x)});
   }

   @Support({SQLDialect.POSTGRES})
   public static AggregateFunction<BigDecimal> regrSYY(Field<? extends Number> y, Field<? extends Number> x) {
      return new Function("regr_syy", SQLDataType.NUMERIC, new QueryPart[]{nullSafe(y), nullSafe(x)});
   }

   @Support({SQLDialect.POSTGRES})
   public static AggregateFunction<BigDecimal> regrSXY(Field<? extends Number> y, Field<? extends Number> x) {
      return new Function("regr_sxy", SQLDataType.NUMERIC, new QueryPart[]{nullSafe(y), nullSafe(x)});
   }

   @Support({SQLDialect.CUBRID, SQLDialect.H2, SQLDialect.HSQLDB, SQLDialect.MARIADB, SQLDialect.MYSQL, SQLDialect.POSTGRES})
   public static OrderedAggregateFunction<String> listAgg(Field<?> field) {
      return new Function(Term.LIST_AGG, SQLDataType.VARCHAR, new QueryPart[]{nullSafe(field)});
   }

   @Support({SQLDialect.CUBRID, SQLDialect.H2, SQLDialect.HSQLDB, SQLDialect.MARIADB, SQLDialect.MYSQL, SQLDialect.POSTGRES})
   public static OrderedAggregateFunction<String> listAgg(Field<?> field, String separator) {
      return new Function(Term.LIST_AGG, SQLDataType.VARCHAR, new QueryPart[]{nullSafe(field), inline(separator)});
   }

   @Support({SQLDialect.CUBRID, SQLDialect.H2, SQLDialect.HSQLDB, SQLDialect.MARIADB, SQLDialect.MYSQL, SQLDialect.POSTGRES, SQLDialect.SQLITE})
   public static GroupConcatOrderByStep groupConcat(Field<?> field) {
      return new GroupConcat(nullSafe(field));
   }

   @Support({SQLDialect.CUBRID, SQLDialect.H2, SQLDialect.HSQLDB, SQLDialect.MARIADB, SQLDialect.MYSQL, SQLDialect.POSTGRES, SQLDialect.SQLITE})
   public static AggregateFunction<String> groupConcat(Field<?> field, String separator) {
      return (new GroupConcat(nullSafe(field))).separator(separator);
   }

   @Support({SQLDialect.CUBRID, SQLDialect.H2, SQLDialect.HSQLDB, SQLDialect.MARIADB, SQLDialect.MYSQL, SQLDialect.POSTGRES})
   public static GroupConcatOrderByStep groupConcatDistinct(Field<?> field) {
      return new GroupConcat(nullSafe(field), true);
   }

   @Support({SQLDialect.POSTGRES_9_4})
   public static OrderedAggregateFunctionOfDeferredType mode() {
      return new Mode();
   }

   @Support({SQLDialect.POSTGRES_9_4})
   public static OrderedAggregateFunction<Integer> rank(Field<?>... fields) {
      return new Function("rank", SQLDataType.INTEGER, fields);
   }

   @Support({SQLDialect.POSTGRES_9_4})
   public static OrderedAggregateFunction<Integer> denseRank(Field<?>... fields) {
      return new Function("dense_rank", SQLDataType.INTEGER, fields);
   }

   @Support({SQLDialect.POSTGRES_9_4})
   public static OrderedAggregateFunction<Integer> percentRank(Field<?>... fields) {
      return new Function("percent_rank", SQLDataType.INTEGER, fields);
   }

   @Support({SQLDialect.POSTGRES_9_4})
   public static OrderedAggregateFunction<BigDecimal> cumeDist(Field<?>... fields) {
      return new Function("cume_dist", SQLDataType.NUMERIC, fields);
   }

   @Support({SQLDialect.POSTGRES_9_4})
   public static OrderedAggregateFunction<BigDecimal> percentileCont(Number number) {
      return percentileCont((Field)val((Object)number));
   }

   @Support({SQLDialect.POSTGRES_9_4})
   public static OrderedAggregateFunction<BigDecimal> percentileCont(Field<? extends Number> field) {
      return new Function("percentile_cont", SQLDataType.NUMERIC, new QueryPart[]{nullSafe(field)});
   }

   @Support({SQLDialect.POSTGRES_9_4})
   public static OrderedAggregateFunction<BigDecimal> percentileDisc(Number number) {
      return percentileDisc((Field)val((Object)number));
   }

   @Support({SQLDialect.POSTGRES_9_4})
   public static OrderedAggregateFunction<BigDecimal> percentileDisc(Field<? extends Number> field) {
      return new Function("percentile_disc", SQLDataType.NUMERIC, new QueryPart[]{nullSafe(field)});
   }

   @Support({SQLDialect.CUBRID, SQLDialect.FIREBIRD_3_0, SQLDialect.POSTGRES})
   public static WindowSpecificationOrderByStep partitionBy(Field<?>... fields) {
      return (new WindowSpecificationImpl()).partitionBy(fields);
   }

   @Support({SQLDialect.CUBRID, SQLDialect.FIREBIRD_3_0, SQLDialect.POSTGRES})
   public static WindowSpecificationOrderByStep partitionBy(Collection<? extends Field<?>> fields) {
      return (new WindowSpecificationImpl()).partitionBy(fields);
   }

   @Support({SQLDialect.CUBRID, SQLDialect.FIREBIRD_3_0, SQLDialect.POSTGRES})
   public static WindowSpecificationRowsStep orderBy(Field<?>... fields) {
      return (new WindowSpecificationImpl()).orderBy(fields);
   }

   @Support({SQLDialect.CUBRID, SQLDialect.FIREBIRD_3_0, SQLDialect.POSTGRES})
   public static WindowSpecificationRowsStep orderBy(SortField<?>... fields) {
      return (new WindowSpecificationImpl()).orderBy(fields);
   }

   @Support({SQLDialect.CUBRID, SQLDialect.FIREBIRD_3_0, SQLDialect.POSTGRES})
   public static WindowSpecificationRowsStep orderBy(Collection<? extends SortField<?>> fields) {
      return (new WindowSpecificationImpl()).orderBy(fields);
   }

   @Support({SQLDialect.POSTGRES})
   public static WindowSpecificationFinalStep rowsUnboundedPreceding() {
      return (new WindowSpecificationImpl()).rowsUnboundedPreceding();
   }

   @Support({SQLDialect.POSTGRES})
   public static WindowSpecificationFinalStep rowsPreceding(int number) {
      return (new WindowSpecificationImpl()).rowsPreceding(number);
   }

   @Support({SQLDialect.POSTGRES})
   public static WindowSpecificationFinalStep rowsCurrentRow() {
      return (new WindowSpecificationImpl()).rowsCurrentRow();
   }

   @Support({SQLDialect.POSTGRES})
   public static WindowSpecificationFinalStep rowsUnboundedFollowing() {
      return (new WindowSpecificationImpl()).rowsUnboundedFollowing();
   }

   @Support({SQLDialect.POSTGRES})
   public static WindowSpecificationFinalStep rowsFollowing(int number) {
      return (new WindowSpecificationImpl()).rowsFollowing(number);
   }

   @Support({SQLDialect.POSTGRES})
   public static WindowSpecificationRowsAndStep rowsBetweenUnboundedPreceding() {
      return (new WindowSpecificationImpl()).rowsBetweenUnboundedPreceding();
   }

   @Support({SQLDialect.POSTGRES})
   public static WindowSpecificationRowsAndStep rowsBetweenPreceding(int number) {
      return (new WindowSpecificationImpl()).rowsBetweenPreceding(number);
   }

   @Support({SQLDialect.POSTGRES})
   public static WindowSpecificationRowsAndStep rowsBetweenCurrentRow() {
      return (new WindowSpecificationImpl()).rowsBetweenCurrentRow();
   }

   @Support({SQLDialect.POSTGRES})
   public static WindowSpecificationRowsAndStep rowsBetweenUnboundedFollowing() {
      return (new WindowSpecificationImpl()).rowsBetweenUnboundedFollowing();
   }

   @Support({SQLDialect.POSTGRES})
   public static WindowSpecificationRowsAndStep rowsBetweenFollowing(int number) {
      return (new WindowSpecificationImpl()).rowsBetweenFollowing(number);
   }

   @Support({SQLDialect.POSTGRES})
   public static WindowSpecificationFinalStep rangeUnboundedPreceding() {
      return (new WindowSpecificationImpl()).rangeUnboundedPreceding();
   }

   @Support({SQLDialect.POSTGRES})
   public static WindowSpecificationFinalStep rangePreceding(int number) {
      return (new WindowSpecificationImpl()).rangePreceding(number);
   }

   @Support({SQLDialect.POSTGRES})
   public static WindowSpecificationFinalStep rangeCurrentRow() {
      return (new WindowSpecificationImpl()).rangeCurrentRow();
   }

   @Support({SQLDialect.POSTGRES})
   public static WindowSpecificationFinalStep rangeUnboundedFollowing() {
      return (new WindowSpecificationImpl()).rangeUnboundedFollowing();
   }

   @Support({SQLDialect.POSTGRES})
   public static WindowSpecificationFinalStep rangeFollowing(int number) {
      return (new WindowSpecificationImpl()).rangeFollowing(number);
   }

   @Support({SQLDialect.POSTGRES})
   public static WindowSpecificationRowsAndStep rangeBetweenUnboundedPreceding() {
      return (new WindowSpecificationImpl()).rangeBetweenUnboundedPreceding();
   }

   @Support({SQLDialect.POSTGRES})
   public static WindowSpecificationRowsAndStep rangeBetweenPreceding(int number) {
      return (new WindowSpecificationImpl()).rangeBetweenPreceding(number);
   }

   @Support({SQLDialect.POSTGRES})
   public static WindowSpecificationRowsAndStep rangeBetweenCurrentRow() {
      return (new WindowSpecificationImpl()).rangeBetweenCurrentRow();
   }

   @Support({SQLDialect.POSTGRES})
   public static WindowSpecificationRowsAndStep rangeBetweenUnboundedFollowing() {
      return (new WindowSpecificationImpl()).rangeBetweenUnboundedFollowing();
   }

   @Support({SQLDialect.POSTGRES})
   public static WindowSpecificationRowsAndStep rangeBetweenFollowing(int number) {
      return (new WindowSpecificationImpl()).rangeBetweenFollowing(number);
   }

   @Support({SQLDialect.CUBRID, SQLDialect.DERBY, SQLDialect.FIREBIRD_3_0, SQLDialect.H2, SQLDialect.HSQLDB, SQLDialect.POSTGRES})
   public static WindowOverStep<Integer> rowNumber() {
      return new Function(Term.ROW_NUMBER, SQLDataType.INTEGER, new QueryPart[0]);
   }

   @Support({SQLDialect.CUBRID, SQLDialect.FIREBIRD_3_0, SQLDialect.POSTGRES})
   public static WindowOverStep<Integer> rank() {
      return new Function("rank", SQLDataType.INTEGER, new QueryPart[0]);
   }

   @Support({SQLDialect.CUBRID, SQLDialect.FIREBIRD_3_0, SQLDialect.POSTGRES})
   public static WindowOverStep<Integer> denseRank() {
      return new Function("dense_rank", SQLDataType.INTEGER, new QueryPart[0]);
   }

   @Support({SQLDialect.CUBRID, SQLDialect.POSTGRES})
   public static WindowOverStep<BigDecimal> percentRank() {
      return new Function("percent_rank", SQLDataType.NUMERIC, new QueryPart[0]);
   }

   @Support({SQLDialect.CUBRID, SQLDialect.POSTGRES})
   public static WindowOverStep<BigDecimal> cumeDist() {
      return new Function("cume_dist", SQLDataType.NUMERIC, new QueryPart[0]);
   }

   @Support({SQLDialect.CUBRID, SQLDialect.POSTGRES})
   public static WindowOverStep<Integer> ntile(int number) {
      return new Function("ntile", SQLDataType.INTEGER, new QueryPart[]{inline(number)});
   }

   @Support({SQLDialect.CUBRID, SQLDialect.POSTGRES})
   public static WindowOverStep<BigDecimal> ratioToReport(Number number) {
      return ratioToReport(Tools.field((Object)number));
   }

   @Support({SQLDialect.CUBRID, SQLDialect.POSTGRES})
   public static WindowOverStep<BigDecimal> ratioToReport(Field<? extends Number> field) {
      return new RatioToReport(nullSafe(field));
   }

   @Support({SQLDialect.CUBRID, SQLDialect.FIREBIRD_3_0, SQLDialect.POSTGRES})
   public static <T> WindowIgnoreNullsStep<T> firstValue(Field<T> field) {
      return new Function("first_value", nullSafeDataType(field), new QueryPart[]{nullSafe(field)});
   }

   @Support({SQLDialect.CUBRID, SQLDialect.FIREBIRD_3_0, SQLDialect.POSTGRES})
   public static <T> WindowIgnoreNullsStep<T> lastValue(Field<T> field) {
      return new Function("last_value", nullSafeDataType(field), new QueryPart[]{nullSafe(field)});
   }

   @Support({SQLDialect.FIREBIRD_3_0, SQLDialect.POSTGRES})
   public static <T> WindowIgnoreNullsStep<T> nthValue(Field<T> field, int nth) {
      return nthValue(field, val(nth));
   }

   @Support({SQLDialect.FIREBIRD_3_0, SQLDialect.POSTGRES})
   public static <T> WindowIgnoreNullsStep<T> nthValue(Field<T> field, Field<Integer> nth) {
      return new Function("nth_value", nullSafeDataType(field), new QueryPart[]{nullSafe(field), nullSafe(nth)});
   }

   @Support({SQLDialect.CUBRID, SQLDialect.FIREBIRD_3_0, SQLDialect.POSTGRES})
   public static <T> WindowIgnoreNullsStep<T> lead(Field<T> field) {
      return new LeadLag("lead", nullSafe(field));
   }

   @Support({SQLDialect.CUBRID, SQLDialect.FIREBIRD_3_0, SQLDialect.POSTGRES})
   public static <T> WindowIgnoreNullsStep<T> lead(Field<T> field, int offset) {
      return new LeadLag("lead", nullSafe(field), offset);
   }

   @Support({SQLDialect.CUBRID, SQLDialect.FIREBIRD_3_0, SQLDialect.POSTGRES})
   public static <T> WindowIgnoreNullsStep<T> lead(Field<T> field, int offset, T defaultValue) {
      return lead(nullSafe(field), offset, Tools.field(defaultValue));
   }

   @Support({SQLDialect.CUBRID, SQLDialect.FIREBIRD_3_0, SQLDialect.POSTGRES})
   public static <T> WindowIgnoreNullsStep<T> lead(Field<T> field, int offset, Field<T> defaultValue) {
      return new LeadLag("lead", nullSafe(field), offset, nullSafe(defaultValue));
   }

   @Support({SQLDialect.CUBRID, SQLDialect.FIREBIRD_3_0, SQLDialect.POSTGRES})
   public static <T> WindowIgnoreNullsStep<T> lag(Field<T> field) {
      return new LeadLag("lag", nullSafe(field));
   }

   @Support({SQLDialect.CUBRID, SQLDialect.FIREBIRD_3_0, SQLDialect.POSTGRES})
   public static <T> WindowIgnoreNullsStep<T> lag(Field<T> field, int offset) {
      return new LeadLag("lag", nullSafe(field), offset);
   }

   @Support({SQLDialect.CUBRID, SQLDialect.FIREBIRD_3_0, SQLDialect.POSTGRES})
   public static <T> WindowIgnoreNullsStep<T> lag(Field<T> field, int offset, T defaultValue) {
      return lag(nullSafe(field), offset, Tools.field(defaultValue));
   }

   @Support({SQLDialect.CUBRID, SQLDialect.FIREBIRD_3_0, SQLDialect.POSTGRES})
   public static <T> WindowIgnoreNullsStep<T> lag(Field<T> field, int offset, Field<T> defaultValue) {
      return new LeadLag("lag", nullSafe(field), offset, nullSafe(defaultValue));
   }

   @Support
   public static <T> Param<Object> param() {
      return param(Object.class);
   }

   @Support
   public static <T> Param<T> param(Class<T> type) {
      return param(DefaultDataType.getDataType((SQLDialect)null, (Class)type));
   }

   @Support
   public static <T> Param<T> param(DataType<T> type) {
      return new Val((Object)null, type);
   }

   @Support
   public static <T> Param<T> param(Field<T> field) {
      return param(field.getDataType());
   }

   @Support
   public static Param<Object> param(String name) {
      return param(name, Object.class);
   }

   @Support
   public static <T> Param<T> param(String name, Class<T> type) {
      return param(name, DefaultDataType.getDataType((SQLDialect)null, (Class)type));
   }

   @Support
   public static <T> Param<T> param(String name, DataType<T> type) {
      return new Val((Object)null, type, name);
   }

   @Support
   public static <T> Param<T> param(String name, Field<T> type) {
      return param(name, type.getDataType());
   }

   @Support
   public static <T> Param<T> param(String name, T value) {
      return new Val(value, Tools.field(value).getDataType(), name);
   }

   @Support
   public static <T> Param<T> value(T value) {
      return val(value);
   }

   public static Param<Byte> value(byte value) {
      return value(value, (DataType)SQLDataType.TINYINT);
   }

   public static Param<Byte> value(Byte value) {
      return value(value, (DataType)SQLDataType.TINYINT);
   }

   public static Param<UByte> value(UByte value) {
      return value(value, (DataType)SQLDataType.TINYINTUNSIGNED);
   }

   public static Param<Short> value(short value) {
      return value(value, (DataType)SQLDataType.SMALLINT);
   }

   public static Param<Short> value(Short value) {
      return value(value, (DataType)SQLDataType.SMALLINT);
   }

   public static Param<UShort> value(UShort value) {
      return value(value, (DataType)SQLDataType.SMALLINTUNSIGNED);
   }

   public static Param<Integer> value(int value) {
      return value(value, (DataType)SQLDataType.INTEGER);
   }

   public static Param<Integer> value(Integer value) {
      return value(value, (DataType)SQLDataType.INTEGER);
   }

   public static Param<UInteger> value(UInteger value) {
      return value(value, (DataType)SQLDataType.INTEGERUNSIGNED);
   }

   public static Param<Long> value(long value) {
      return value(value, (DataType)SQLDataType.BIGINT);
   }

   public static Param<Long> value(Long value) {
      return value(value, (DataType)SQLDataType.BIGINT);
   }

   public static Param<ULong> value(ULong value) {
      return value(value, (DataType)SQLDataType.BIGINTUNSIGNED);
   }

   public static Param<Float> value(float value) {
      return value(value, (DataType)SQLDataType.REAL);
   }

   public static Param<Float> value(Float value) {
      return value(value, (DataType)SQLDataType.REAL);
   }

   public static Param<Double> value(double value) {
      return value(value, (DataType)SQLDataType.DOUBLE);
   }

   public static Param<Double> value(Double value) {
      return value(value, (DataType)SQLDataType.DOUBLE);
   }

   public static Param<Boolean> value(boolean value) {
      return value(value, (DataType)SQLDataType.BOOLEAN);
   }

   public static Param<Boolean> value(Boolean value) {
      return value(value, (DataType)SQLDataType.BOOLEAN);
   }

   public static Param<BigDecimal> value(BigDecimal value) {
      return value(value, (DataType)SQLDataType.DECIMAL);
   }

   public static Param<BigInteger> value(BigInteger value) {
      return value(value, (DataType)SQLDataType.DECIMAL_INTEGER);
   }

   public static Param<byte[]> value(byte[] value) {
      return value(value, (DataType)SQLDataType.VARBINARY);
   }

   public static Param<String> value(String value) {
      return value(value, (DataType)SQLDataType.VARCHAR);
   }

   public static Param<Date> value(Date value) {
      return value(value, (DataType)SQLDataType.DATE);
   }

   public static Param<Time> value(Time value) {
      return value(value, (DataType)SQLDataType.TIME);
   }

   public static Param<Timestamp> value(Timestamp value) {
      return value(value, (DataType)SQLDataType.TIMESTAMP);
   }

   public static Param<LocalDate> value(LocalDate value) {
      return value(value, (DataType)SQLDataType.LOCALDATE);
   }

   public static Param<LocalTime> value(LocalTime value) {
      return value(value, (DataType)SQLDataType.LOCALTIME);
   }

   public static Param<LocalDateTime> value(LocalDateTime value) {
      return value(value, (DataType)SQLDataType.LOCALDATETIME);
   }

   public static Param<OffsetTime> value(OffsetTime value) {
      return value(value, (DataType)SQLDataType.OFFSETTIME);
   }

   public static Param<OffsetDateTime> value(OffsetDateTime value) {
      return value(value, (DataType)SQLDataType.OFFSETDATETIME);
   }

   public static Param<UUID> value(UUID value) {
      return value(value, (DataType)SQLDataType.UUID);
   }

   @Support
   public static <T> Param<T> value(Object value, Class<T> type) {
      return val(value, type);
   }

   @Support
   public static <T> Param<T> value(Object value, Field<T> field) {
      return val(value, field);
   }

   @Support
   public static <T> Param<T> value(Object value, DataType<T> type) {
      return val(value, type);
   }

   @Support
   public static <T> Param<T> inline(T value) {
      Param<T> val = val(value);
      val.setInline(true);
      return val;
   }

   public static Param<Byte> inline(byte value) {
      return inline(value, (DataType)SQLDataType.TINYINT);
   }

   public static Param<Byte> inline(Byte value) {
      return inline(value, (DataType)SQLDataType.TINYINT);
   }

   public static Param<UByte> inline(UByte value) {
      return inline(value, (DataType)SQLDataType.TINYINTUNSIGNED);
   }

   public static Param<Short> inline(short value) {
      return inline(value, (DataType)SQLDataType.SMALLINT);
   }

   public static Param<Short> inline(Short value) {
      return inline(value, (DataType)SQLDataType.SMALLINT);
   }

   public static Param<UShort> inline(UShort value) {
      return inline(value, (DataType)SQLDataType.SMALLINTUNSIGNED);
   }

   public static Param<Integer> inline(int value) {
      return inline(value, (DataType)SQLDataType.INTEGER);
   }

   public static Param<Integer> inline(Integer value) {
      return inline(value, (DataType)SQLDataType.INTEGER);
   }

   public static Param<UInteger> inline(UInteger value) {
      return inline(value, (DataType)SQLDataType.INTEGERUNSIGNED);
   }

   public static Param<Long> inline(long value) {
      return inline(value, (DataType)SQLDataType.BIGINT);
   }

   public static Param<Long> inline(Long value) {
      return inline(value, (DataType)SQLDataType.BIGINT);
   }

   public static Param<ULong> inline(ULong value) {
      return inline(value, (DataType)SQLDataType.BIGINTUNSIGNED);
   }

   public static Param<Float> inline(float value) {
      return inline(value, (DataType)SQLDataType.REAL);
   }

   public static Param<Float> inline(Float value) {
      return inline(value, (DataType)SQLDataType.REAL);
   }

   public static Param<Double> inline(double value) {
      return inline(value, (DataType)SQLDataType.DOUBLE);
   }

   public static Param<Double> inline(Double value) {
      return inline(value, (DataType)SQLDataType.DOUBLE);
   }

   public static Param<Boolean> inline(boolean value) {
      return inline(value, (DataType)SQLDataType.BOOLEAN);
   }

   public static Param<Boolean> inline(Boolean value) {
      return inline(value, (DataType)SQLDataType.BOOLEAN);
   }

   public static Param<BigDecimal> inline(BigDecimal value) {
      return inline(value, (DataType)SQLDataType.DECIMAL);
   }

   public static Param<BigInteger> inline(BigInteger value) {
      return inline(value, (DataType)SQLDataType.DECIMAL_INTEGER);
   }

   public static Param<byte[]> inline(byte[] value) {
      return inline(value, (DataType)SQLDataType.VARBINARY);
   }

   public static Param<String> inline(String value) {
      return inline(value, (DataType)SQLDataType.VARCHAR);
   }

   public static Param<Date> inline(Date value) {
      return inline(value, (DataType)SQLDataType.DATE);
   }

   public static Param<Time> inline(Time value) {
      return inline(value, (DataType)SQLDataType.TIME);
   }

   public static Param<Timestamp> inline(Timestamp value) {
      return inline(value, (DataType)SQLDataType.TIMESTAMP);
   }

   public static Param<LocalDate> inline(LocalDate value) {
      return inline(value, (DataType)SQLDataType.LOCALDATE);
   }

   public static Param<LocalTime> inline(LocalTime value) {
      return inline(value, (DataType)SQLDataType.LOCALTIME);
   }

   public static Param<LocalDateTime> inline(LocalDateTime value) {
      return inline(value, (DataType)SQLDataType.LOCALDATETIME);
   }

   public static Param<OffsetTime> inline(OffsetTime value) {
      return inline(value, (DataType)SQLDataType.OFFSETTIME);
   }

   public static Param<OffsetDateTime> inline(OffsetDateTime value) {
      return inline(value, (DataType)SQLDataType.OFFSETDATETIME);
   }

   public static Param<UUID> inline(UUID value) {
      return inline(value, (DataType)SQLDataType.UUID);
   }

   @Support
   public static Param<String> inline(char character) {
      return inline("" + character);
   }

   @Support
   public static Param<String> inline(Character character) {
      return inline(character == null ? null : "" + character);
   }

   @Support
   public static Param<String> inline(CharSequence character) {
      return inline((Object)(character == null ? null : "" + character));
   }

   @Support
   public static <T> Param<T> inline(Object value, Class<T> type) {
      Param<T> val = val(value, type);
      val.setInline(true);
      return val;
   }

   @Support
   public static <T> Param<T> inline(Object value, Field<T> field) {
      Param<T> val = val(value, field);
      val.setInline(true);
      return val;
   }

   @Support
   public static <T> Param<T> inline(Object value, DataType<T> type) {
      Param<T> val = val(value, type);
      val.setInline(true);
      return val;
   }

   @Support
   public static <T> Param<T> val(T value) {
      Class<?> type = value == null ? Object.class : value.getClass();
      return val(value, getDataType(type));
   }

   public static Param<Byte> val(byte value) {
      return val(value, (DataType)SQLDataType.TINYINT);
   }

   public static Param<Byte> val(Byte value) {
      return val(value, (DataType)SQLDataType.TINYINT);
   }

   public static Param<UByte> val(UByte value) {
      return val(value, (DataType)SQLDataType.TINYINTUNSIGNED);
   }

   public static Param<Short> val(short value) {
      return val(value, (DataType)SQLDataType.SMALLINT);
   }

   public static Param<Short> val(Short value) {
      return val(value, (DataType)SQLDataType.SMALLINT);
   }

   public static Param<UShort> val(UShort value) {
      return val(value, (DataType)SQLDataType.SMALLINTUNSIGNED);
   }

   public static Param<Integer> val(int value) {
      return val(value, (DataType)SQLDataType.INTEGER);
   }

   public static Param<Integer> val(Integer value) {
      return val(value, (DataType)SQLDataType.INTEGER);
   }

   public static Param<UInteger> val(UInteger value) {
      return val(value, (DataType)SQLDataType.INTEGERUNSIGNED);
   }

   public static Param<Long> val(long value) {
      return val(value, (DataType)SQLDataType.BIGINT);
   }

   public static Param<Long> val(Long value) {
      return val(value, (DataType)SQLDataType.BIGINT);
   }

   public static Param<ULong> val(ULong value) {
      return val(value, (DataType)SQLDataType.BIGINTUNSIGNED);
   }

   public static Param<Float> val(float value) {
      return val(value, (DataType)SQLDataType.REAL);
   }

   public static Param<Float> val(Float value) {
      return val(value, (DataType)SQLDataType.REAL);
   }

   public static Param<Double> val(double value) {
      return val(value, (DataType)SQLDataType.DOUBLE);
   }

   public static Param<Double> val(Double value) {
      return val(value, (DataType)SQLDataType.DOUBLE);
   }

   public static Param<Boolean> val(boolean value) {
      return val(value, (DataType)SQLDataType.BOOLEAN);
   }

   public static Param<Boolean> val(Boolean value) {
      return val(value, (DataType)SQLDataType.BOOLEAN);
   }

   public static Param<BigDecimal> val(BigDecimal value) {
      return val(value, (DataType)SQLDataType.DECIMAL);
   }

   public static Param<BigInteger> val(BigInteger value) {
      return val(value, (DataType)SQLDataType.DECIMAL_INTEGER);
   }

   public static Param<byte[]> val(byte[] value) {
      return val(value, (DataType)SQLDataType.VARBINARY);
   }

   public static Param<String> val(String value) {
      return val(value, (DataType)SQLDataType.VARCHAR);
   }

   public static Param<Date> val(Date value) {
      return val(value, (DataType)SQLDataType.DATE);
   }

   public static Param<Time> val(Time value) {
      return val(value, (DataType)SQLDataType.TIME);
   }

   public static Param<Timestamp> val(Timestamp value) {
      return val(value, (DataType)SQLDataType.TIMESTAMP);
   }

   public static Param<LocalDate> val(LocalDate value) {
      return val(value, (DataType)SQLDataType.LOCALDATE);
   }

   public static Param<LocalTime> val(LocalTime value) {
      return val(value, (DataType)SQLDataType.LOCALTIME);
   }

   public static Param<LocalDateTime> val(LocalDateTime value) {
      return val(value, (DataType)SQLDataType.LOCALDATETIME);
   }

   public static Param<OffsetTime> val(OffsetTime value) {
      return val(value, (DataType)SQLDataType.OFFSETTIME);
   }

   public static Param<OffsetDateTime> val(OffsetDateTime value) {
      return val(value, (DataType)SQLDataType.OFFSETDATETIME);
   }

   public static Param<UUID> val(UUID value) {
      return val(value, (DataType)SQLDataType.UUID);
   }

   @Support
   public static <T> Param<T> val(Object value, Class<T> type) {
      return val(value, getDataType(type));
   }

   @Support
   public static <T> Param<T> val(Object value, Field<T> field) {
      return val(value, nullSafeDataType(field));
   }

   @Support
   public static <T> Param<T> val(Object value, DataType<T> type) {
      if (value instanceof UDTRecord) {
         return new UDTConstant((UDTRecord)value);
      } else {
         T converted = type.convert(value);
         return new Val(converted, mostSpecific(converted, type));
      }
   }

   private static <T> DataType<T> mostSpecific(T value, DataType<T> dataType) {
      if (value != null && !(dataType instanceof ConvertedDataType)) {
         Class<T> valueType = value.getClass();
         Class<T> coercionType = dataType.getType();
         if (valueType != coercionType && coercionType.isAssignableFrom(valueType)) {
            return DefaultDataType.getDataType((SQLDialect)null, valueType, dataType);
         }
      }

      return dataType;
   }

   public static <T1> RecordType<Record> recordType(Field<?>[] fields) {
      return new Fields(fields);
   }

   public static <T1> RecordType<Record> recordType(Collection<? extends Field<?>> fields) {
      return new Fields(fields);
   }

   public static <T1> RecordType<Record1<T1>> recordType(Field<T1> field1) {
      return new Fields(new Field[]{field1});
   }

   public static <T1, T2> RecordType<Record2<T1, T2>> recordType(Field<T1> field1, Field<T2> field2) {
      return new Fields(new Field[]{field1, field2});
   }

   public static <T1, T2, T3> RecordType<Record3<T1, T2, T3>> recordType(Field<T1> field1, Field<T2> field2, Field<T3> field3) {
      return new Fields(new Field[]{field1, field2, field3});
   }

   public static <T1, T2, T3, T4> RecordType<Record4<T1, T2, T3, T4>> recordType(Field<T1> field1, Field<T2> field2, Field<T3> field3, Field<T4> field4) {
      return new Fields(new Field[]{field1, field2, field3, field4});
   }

   public static <T1, T2, T3, T4, T5> RecordType<Record5<T1, T2, T3, T4, T5>> recordType(Field<T1> field1, Field<T2> field2, Field<T3> field3, Field<T4> field4, Field<T5> field5) {
      return new Fields(new Field[]{field1, field2, field3, field4, field5});
   }

   public static <T1, T2, T3, T4, T5, T6> RecordType<Record6<T1, T2, T3, T4, T5, T6>> recordType(Field<T1> field1, Field<T2> field2, Field<T3> field3, Field<T4> field4, Field<T5> field5, Field<T6> field6) {
      return new Fields(new Field[]{field1, field2, field3, field4, field5, field6});
   }

   public static <T1, T2, T3, T4, T5, T6, T7> RecordType<Record7<T1, T2, T3, T4, T5, T6, T7>> recordType(Field<T1> field1, Field<T2> field2, Field<T3> field3, Field<T4> field4, Field<T5> field5, Field<T6> field6, Field<T7> field7) {
      return new Fields(new Field[]{field1, field2, field3, field4, field5, field6, field7});
   }

   public static <T1, T2, T3, T4, T5, T6, T7, T8> RecordType<Record8<T1, T2, T3, T4, T5, T6, T7, T8>> recordType(Field<T1> field1, Field<T2> field2, Field<T3> field3, Field<T4> field4, Field<T5> field5, Field<T6> field6, Field<T7> field7, Field<T8> field8) {
      return new Fields(new Field[]{field1, field2, field3, field4, field5, field6, field7, field8});
   }

   public static <T1, T2, T3, T4, T5, T6, T7, T8, T9> RecordType<Record9<T1, T2, T3, T4, T5, T6, T7, T8, T9>> recordType(Field<T1> field1, Field<T2> field2, Field<T3> field3, Field<T4> field4, Field<T5> field5, Field<T6> field6, Field<T7> field7, Field<T8> field8, Field<T9> field9) {
      return new Fields(new Field[]{field1, field2, field3, field4, field5, field6, field7, field8, field9});
   }

   public static <T1, T2, T3, T4, T5, T6, T7, T8, T9, T10> RecordType<Record10<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10>> recordType(Field<T1> field1, Field<T2> field2, Field<T3> field3, Field<T4> field4, Field<T5> field5, Field<T6> field6, Field<T7> field7, Field<T8> field8, Field<T9> field9, Field<T10> field10) {
      return new Fields(new Field[]{field1, field2, field3, field4, field5, field6, field7, field8, field9, field10});
   }

   public static <T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11> RecordType<Record11<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11>> recordType(Field<T1> field1, Field<T2> field2, Field<T3> field3, Field<T4> field4, Field<T5> field5, Field<T6> field6, Field<T7> field7, Field<T8> field8, Field<T9> field9, Field<T10> field10, Field<T11> field11) {
      return new Fields(new Field[]{field1, field2, field3, field4, field5, field6, field7, field8, field9, field10, field11});
   }

   public static <T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12> RecordType<Record12<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12>> recordType(Field<T1> field1, Field<T2> field2, Field<T3> field3, Field<T4> field4, Field<T5> field5, Field<T6> field6, Field<T7> field7, Field<T8> field8, Field<T9> field9, Field<T10> field10, Field<T11> field11, Field<T12> field12) {
      return new Fields(new Field[]{field1, field2, field3, field4, field5, field6, field7, field8, field9, field10, field11, field12});
   }

   public static <T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13> RecordType<Record13<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13>> recordType(Field<T1> field1, Field<T2> field2, Field<T3> field3, Field<T4> field4, Field<T5> field5, Field<T6> field6, Field<T7> field7, Field<T8> field8, Field<T9> field9, Field<T10> field10, Field<T11> field11, Field<T12> field12, Field<T13> field13) {
      return new Fields(new Field[]{field1, field2, field3, field4, field5, field6, field7, field8, field9, field10, field11, field12, field13});
   }

   public static <T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14> RecordType<Record14<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14>> recordType(Field<T1> field1, Field<T2> field2, Field<T3> field3, Field<T4> field4, Field<T5> field5, Field<T6> field6, Field<T7> field7, Field<T8> field8, Field<T9> field9, Field<T10> field10, Field<T11> field11, Field<T12> field12, Field<T13> field13, Field<T14> field14) {
      return new Fields(new Field[]{field1, field2, field3, field4, field5, field6, field7, field8, field9, field10, field11, field12, field13, field14});
   }

   public static <T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14, T15> RecordType<Record15<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14, T15>> recordType(Field<T1> field1, Field<T2> field2, Field<T3> field3, Field<T4> field4, Field<T5> field5, Field<T6> field6, Field<T7> field7, Field<T8> field8, Field<T9> field9, Field<T10> field10, Field<T11> field11, Field<T12> field12, Field<T13> field13, Field<T14> field14, Field<T15> field15) {
      return new Fields(new Field[]{field1, field2, field3, field4, field5, field6, field7, field8, field9, field10, field11, field12, field13, field14, field15});
   }

   public static <T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14, T15, T16> RecordType<Record16<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14, T15, T16>> recordType(Field<T1> field1, Field<T2> field2, Field<T3> field3, Field<T4> field4, Field<T5> field5, Field<T6> field6, Field<T7> field7, Field<T8> field8, Field<T9> field9, Field<T10> field10, Field<T11> field11, Field<T12> field12, Field<T13> field13, Field<T14> field14, Field<T15> field15, Field<T16> field16) {
      return new Fields(new Field[]{field1, field2, field3, field4, field5, field6, field7, field8, field9, field10, field11, field12, field13, field14, field15, field16});
   }

   public static <T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14, T15, T16, T17> RecordType<Record17<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14, T15, T16, T17>> recordType(Field<T1> field1, Field<T2> field2, Field<T3> field3, Field<T4> field4, Field<T5> field5, Field<T6> field6, Field<T7> field7, Field<T8> field8, Field<T9> field9, Field<T10> field10, Field<T11> field11, Field<T12> field12, Field<T13> field13, Field<T14> field14, Field<T15> field15, Field<T16> field16, Field<T17> field17) {
      return new Fields(new Field[]{field1, field2, field3, field4, field5, field6, field7, field8, field9, field10, field11, field12, field13, field14, field15, field16, field17});
   }

   public static <T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14, T15, T16, T17, T18> RecordType<Record18<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14, T15, T16, T17, T18>> recordType(Field<T1> field1, Field<T2> field2, Field<T3> field3, Field<T4> field4, Field<T5> field5, Field<T6> field6, Field<T7> field7, Field<T8> field8, Field<T9> field9, Field<T10> field10, Field<T11> field11, Field<T12> field12, Field<T13> field13, Field<T14> field14, Field<T15> field15, Field<T16> field16, Field<T17> field17, Field<T18> field18) {
      return new Fields(new Field[]{field1, field2, field3, field4, field5, field6, field7, field8, field9, field10, field11, field12, field13, field14, field15, field16, field17, field18});
   }

   public static <T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14, T15, T16, T17, T18, T19> RecordType<Record19<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14, T15, T16, T17, T18, T19>> recordType(Field<T1> field1, Field<T2> field2, Field<T3> field3, Field<T4> field4, Field<T5> field5, Field<T6> field6, Field<T7> field7, Field<T8> field8, Field<T9> field9, Field<T10> field10, Field<T11> field11, Field<T12> field12, Field<T13> field13, Field<T14> field14, Field<T15> field15, Field<T16> field16, Field<T17> field17, Field<T18> field18, Field<T19> field19) {
      return new Fields(new Field[]{field1, field2, field3, field4, field5, field6, field7, field8, field9, field10, field11, field12, field13, field14, field15, field16, field17, field18, field19});
   }

   public static <T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14, T15, T16, T17, T18, T19, T20> RecordType<Record20<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14, T15, T16, T17, T18, T19, T20>> recordType(Field<T1> field1, Field<T2> field2, Field<T3> field3, Field<T4> field4, Field<T5> field5, Field<T6> field6, Field<T7> field7, Field<T8> field8, Field<T9> field9, Field<T10> field10, Field<T11> field11, Field<T12> field12, Field<T13> field13, Field<T14> field14, Field<T15> field15, Field<T16> field16, Field<T17> field17, Field<T18> field18, Field<T19> field19, Field<T20> field20) {
      return new Fields(new Field[]{field1, field2, field3, field4, field5, field6, field7, field8, field9, field10, field11, field12, field13, field14, field15, field16, field17, field18, field19, field20});
   }

   public static <T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14, T15, T16, T17, T18, T19, T20, T21> RecordType<Record21<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14, T15, T16, T17, T18, T19, T20, T21>> recordType(Field<T1> field1, Field<T2> field2, Field<T3> field3, Field<T4> field4, Field<T5> field5, Field<T6> field6, Field<T7> field7, Field<T8> field8, Field<T9> field9, Field<T10> field10, Field<T11> field11, Field<T12> field12, Field<T13> field13, Field<T14> field14, Field<T15> field15, Field<T16> field16, Field<T17> field17, Field<T18> field18, Field<T19> field19, Field<T20> field20, Field<T21> field21) {
      return new Fields(new Field[]{field1, field2, field3, field4, field5, field6, field7, field8, field9, field10, field11, field12, field13, field14, field15, field16, field17, field18, field19, field20, field21});
   }

   public static <T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14, T15, T16, T17, T18, T19, T20, T21, T22> RecordType<Record22<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14, T15, T16, T17, T18, T19, T20, T21, T22>> recordType(Field<T1> field1, Field<T2> field2, Field<T3> field3, Field<T4> field4, Field<T5> field5, Field<T6> field6, Field<T7> field7, Field<T8> field8, Field<T9> field9, Field<T10> field10, Field<T11> field11, Field<T12> field12, Field<T13> field13, Field<T14> field14, Field<T15> field15, Field<T16> field16, Field<T17> field17, Field<T18> field18, Field<T19> field19, Field<T20> field20, Field<T21> field21, Field<T22> field22) {
      return new Fields(new Field[]{field1, field2, field3, field4, field5, field6, field7, field8, field9, field10, field11, field12, field13, field14, field15, field16, field17, field18, field19, field20, field21, field22});
   }

   @Support
   public static <T1> Row1<T1> row(T1 t1) {
      return row(Tools.field(t1));
   }

   @Support
   public static <T1, T2> Row2<T1, T2> row(T1 t1, T2 t2) {
      return row(Tools.field(t1), Tools.field(t2));
   }

   @Support
   public static <T1, T2, T3> Row3<T1, T2, T3> row(T1 t1, T2 t2, T3 t3) {
      return row(Tools.field(t1), Tools.field(t2), Tools.field(t3));
   }

   @Support
   public static <T1, T2, T3, T4> Row4<T1, T2, T3, T4> row(T1 t1, T2 t2, T3 t3, T4 t4) {
      return row(Tools.field(t1), Tools.field(t2), Tools.field(t3), Tools.field(t4));
   }

   @Support
   public static <T1, T2, T3, T4, T5> Row5<T1, T2, T3, T4, T5> row(T1 t1, T2 t2, T3 t3, T4 t4, T5 t5) {
      return row(Tools.field(t1), Tools.field(t2), Tools.field(t3), Tools.field(t4), Tools.field(t5));
   }

   @Support
   public static <T1, T2, T3, T4, T5, T6> Row6<T1, T2, T3, T4, T5, T6> row(T1 t1, T2 t2, T3 t3, T4 t4, T5 t5, T6 t6) {
      return row(Tools.field(t1), Tools.field(t2), Tools.field(t3), Tools.field(t4), Tools.field(t5), Tools.field(t6));
   }

   @Support
   public static <T1, T2, T3, T4, T5, T6, T7> Row7<T1, T2, T3, T4, T5, T6, T7> row(T1 t1, T2 t2, T3 t3, T4 t4, T5 t5, T6 t6, T7 t7) {
      return row(Tools.field(t1), Tools.field(t2), Tools.field(t3), Tools.field(t4), Tools.field(t5), Tools.field(t6), Tools.field(t7));
   }

   @Support
   public static <T1, T2, T3, T4, T5, T6, T7, T8> Row8<T1, T2, T3, T4, T5, T6, T7, T8> row(T1 t1, T2 t2, T3 t3, T4 t4, T5 t5, T6 t6, T7 t7, T8 t8) {
      return row(Tools.field(t1), Tools.field(t2), Tools.field(t3), Tools.field(t4), Tools.field(t5), Tools.field(t6), Tools.field(t7), Tools.field(t8));
   }

   @Support
   public static <T1, T2, T3, T4, T5, T6, T7, T8, T9> Row9<T1, T2, T3, T4, T5, T6, T7, T8, T9> row(T1 t1, T2 t2, T3 t3, T4 t4, T5 t5, T6 t6, T7 t7, T8 t8, T9 t9) {
      return row(Tools.field(t1), Tools.field(t2), Tools.field(t3), Tools.field(t4), Tools.field(t5), Tools.field(t6), Tools.field(t7), Tools.field(t8), Tools.field(t9));
   }

   @Support
   public static <T1, T2, T3, T4, T5, T6, T7, T8, T9, T10> Row10<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10> row(T1 t1, T2 t2, T3 t3, T4 t4, T5 t5, T6 t6, T7 t7, T8 t8, T9 t9, T10 t10) {
      return row(Tools.field(t1), Tools.field(t2), Tools.field(t3), Tools.field(t4), Tools.field(t5), Tools.field(t6), Tools.field(t7), Tools.field(t8), Tools.field(t9), Tools.field(t10));
   }

   @Support
   public static <T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11> Row11<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11> row(T1 t1, T2 t2, T3 t3, T4 t4, T5 t5, T6 t6, T7 t7, T8 t8, T9 t9, T10 t10, T11 t11) {
      return row(Tools.field(t1), Tools.field(t2), Tools.field(t3), Tools.field(t4), Tools.field(t5), Tools.field(t6), Tools.field(t7), Tools.field(t8), Tools.field(t9), Tools.field(t10), Tools.field(t11));
   }

   @Support
   public static <T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12> Row12<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12> row(T1 t1, T2 t2, T3 t3, T4 t4, T5 t5, T6 t6, T7 t7, T8 t8, T9 t9, T10 t10, T11 t11, T12 t12) {
      return row(Tools.field(t1), Tools.field(t2), Tools.field(t3), Tools.field(t4), Tools.field(t5), Tools.field(t6), Tools.field(t7), Tools.field(t8), Tools.field(t9), Tools.field(t10), Tools.field(t11), Tools.field(t12));
   }

   @Support
   public static <T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13> Row13<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13> row(T1 t1, T2 t2, T3 t3, T4 t4, T5 t5, T6 t6, T7 t7, T8 t8, T9 t9, T10 t10, T11 t11, T12 t12, T13 t13) {
      return row(Tools.field(t1), Tools.field(t2), Tools.field(t3), Tools.field(t4), Tools.field(t5), Tools.field(t6), Tools.field(t7), Tools.field(t8), Tools.field(t9), Tools.field(t10), Tools.field(t11), Tools.field(t12), Tools.field(t13));
   }

   @Support
   public static <T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14> Row14<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14> row(T1 t1, T2 t2, T3 t3, T4 t4, T5 t5, T6 t6, T7 t7, T8 t8, T9 t9, T10 t10, T11 t11, T12 t12, T13 t13, T14 t14) {
      return row(Tools.field(t1), Tools.field(t2), Tools.field(t3), Tools.field(t4), Tools.field(t5), Tools.field(t6), Tools.field(t7), Tools.field(t8), Tools.field(t9), Tools.field(t10), Tools.field(t11), Tools.field(t12), Tools.field(t13), Tools.field(t14));
   }

   @Support
   public static <T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14, T15> Row15<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14, T15> row(T1 t1, T2 t2, T3 t3, T4 t4, T5 t5, T6 t6, T7 t7, T8 t8, T9 t9, T10 t10, T11 t11, T12 t12, T13 t13, T14 t14, T15 t15) {
      return row(Tools.field(t1), Tools.field(t2), Tools.field(t3), Tools.field(t4), Tools.field(t5), Tools.field(t6), Tools.field(t7), Tools.field(t8), Tools.field(t9), Tools.field(t10), Tools.field(t11), Tools.field(t12), Tools.field(t13), Tools.field(t14), Tools.field(t15));
   }

   @Support
   public static <T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14, T15, T16> Row16<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14, T15, T16> row(T1 t1, T2 t2, T3 t3, T4 t4, T5 t5, T6 t6, T7 t7, T8 t8, T9 t9, T10 t10, T11 t11, T12 t12, T13 t13, T14 t14, T15 t15, T16 t16) {
      return row(Tools.field(t1), Tools.field(t2), Tools.field(t3), Tools.field(t4), Tools.field(t5), Tools.field(t6), Tools.field(t7), Tools.field(t8), Tools.field(t9), Tools.field(t10), Tools.field(t11), Tools.field(t12), Tools.field(t13), Tools.field(t14), Tools.field(t15), Tools.field(t16));
   }

   @Support
   public static <T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14, T15, T16, T17> Row17<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14, T15, T16, T17> row(T1 t1, T2 t2, T3 t3, T4 t4, T5 t5, T6 t6, T7 t7, T8 t8, T9 t9, T10 t10, T11 t11, T12 t12, T13 t13, T14 t14, T15 t15, T16 t16, T17 t17) {
      return row(Tools.field(t1), Tools.field(t2), Tools.field(t3), Tools.field(t4), Tools.field(t5), Tools.field(t6), Tools.field(t7), Tools.field(t8), Tools.field(t9), Tools.field(t10), Tools.field(t11), Tools.field(t12), Tools.field(t13), Tools.field(t14), Tools.field(t15), Tools.field(t16), Tools.field(t17));
   }

   @Support
   public static <T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14, T15, T16, T17, T18> Row18<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14, T15, T16, T17, T18> row(T1 t1, T2 t2, T3 t3, T4 t4, T5 t5, T6 t6, T7 t7, T8 t8, T9 t9, T10 t10, T11 t11, T12 t12, T13 t13, T14 t14, T15 t15, T16 t16, T17 t17, T18 t18) {
      return row(Tools.field(t1), Tools.field(t2), Tools.field(t3), Tools.field(t4), Tools.field(t5), Tools.field(t6), Tools.field(t7), Tools.field(t8), Tools.field(t9), Tools.field(t10), Tools.field(t11), Tools.field(t12), Tools.field(t13), Tools.field(t14), Tools.field(t15), Tools.field(t16), Tools.field(t17), Tools.field(t18));
   }

   @Support
   public static <T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14, T15, T16, T17, T18, T19> Row19<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14, T15, T16, T17, T18, T19> row(T1 t1, T2 t2, T3 t3, T4 t4, T5 t5, T6 t6, T7 t7, T8 t8, T9 t9, T10 t10, T11 t11, T12 t12, T13 t13, T14 t14, T15 t15, T16 t16, T17 t17, T18 t18, T19 t19) {
      return row(Tools.field(t1), Tools.field(t2), Tools.field(t3), Tools.field(t4), Tools.field(t5), Tools.field(t6), Tools.field(t7), Tools.field(t8), Tools.field(t9), Tools.field(t10), Tools.field(t11), Tools.field(t12), Tools.field(t13), Tools.field(t14), Tools.field(t15), Tools.field(t16), Tools.field(t17), Tools.field(t18), Tools.field(t19));
   }

   @Support
   public static <T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14, T15, T16, T17, T18, T19, T20> Row20<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14, T15, T16, T17, T18, T19, T20> row(T1 t1, T2 t2, T3 t3, T4 t4, T5 t5, T6 t6, T7 t7, T8 t8, T9 t9, T10 t10, T11 t11, T12 t12, T13 t13, T14 t14, T15 t15, T16 t16, T17 t17, T18 t18, T19 t19, T20 t20) {
      return row(Tools.field(t1), Tools.field(t2), Tools.field(t3), Tools.field(t4), Tools.field(t5), Tools.field(t6), Tools.field(t7), Tools.field(t8), Tools.field(t9), Tools.field(t10), Tools.field(t11), Tools.field(t12), Tools.field(t13), Tools.field(t14), Tools.field(t15), Tools.field(t16), Tools.field(t17), Tools.field(t18), Tools.field(t19), Tools.field(t20));
   }

   @Support
   public static <T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14, T15, T16, T17, T18, T19, T20, T21> Row21<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14, T15, T16, T17, T18, T19, T20, T21> row(T1 t1, T2 t2, T3 t3, T4 t4, T5 t5, T6 t6, T7 t7, T8 t8, T9 t9, T10 t10, T11 t11, T12 t12, T13 t13, T14 t14, T15 t15, T16 t16, T17 t17, T18 t18, T19 t19, T20 t20, T21 t21) {
      return row(Tools.field(t1), Tools.field(t2), Tools.field(t3), Tools.field(t4), Tools.field(t5), Tools.field(t6), Tools.field(t7), Tools.field(t8), Tools.field(t9), Tools.field(t10), Tools.field(t11), Tools.field(t12), Tools.field(t13), Tools.field(t14), Tools.field(t15), Tools.field(t16), Tools.field(t17), Tools.field(t18), Tools.field(t19), Tools.field(t20), Tools.field(t21));
   }

   @Support
   public static <T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14, T15, T16, T17, T18, T19, T20, T21, T22> Row22<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14, T15, T16, T17, T18, T19, T20, T21, T22> row(T1 t1, T2 t2, T3 t3, T4 t4, T5 t5, T6 t6, T7 t7, T8 t8, T9 t9, T10 t10, T11 t11, T12 t12, T13 t13, T14 t14, T15 t15, T16 t16, T17 t17, T18 t18, T19 t19, T20 t20, T21 t21, T22 t22) {
      return row(Tools.field(t1), Tools.field(t2), Tools.field(t3), Tools.field(t4), Tools.field(t5), Tools.field(t6), Tools.field(t7), Tools.field(t8), Tools.field(t9), Tools.field(t10), Tools.field(t11), Tools.field(t12), Tools.field(t13), Tools.field(t14), Tools.field(t15), Tools.field(t16), Tools.field(t17), Tools.field(t18), Tools.field(t19), Tools.field(t20), Tools.field(t21), Tools.field(t22));
   }

   @Support
   public static RowN row(Object... values) {
      return row((Field[])Tools.fields(values).toArray(Tools.EMPTY_FIELD));
   }

   @Support
   public static <T1> Row1<T1> row(Field<T1> t1) {
      return new RowImpl(new Field[]{t1});
   }

   @Support
   public static <T1, T2> Row2<T1, T2> row(Field<T1> t1, Field<T2> t2) {
      return new RowImpl(new Field[]{t1, t2});
   }

   @Support
   public static <T1, T2, T3> Row3<T1, T2, T3> row(Field<T1> t1, Field<T2> t2, Field<T3> t3) {
      return new RowImpl(new Field[]{t1, t2, t3});
   }

   @Support
   public static <T1, T2, T3, T4> Row4<T1, T2, T3, T4> row(Field<T1> t1, Field<T2> t2, Field<T3> t3, Field<T4> t4) {
      return new RowImpl(new Field[]{t1, t2, t3, t4});
   }

   @Support
   public static <T1, T2, T3, T4, T5> Row5<T1, T2, T3, T4, T5> row(Field<T1> t1, Field<T2> t2, Field<T3> t3, Field<T4> t4, Field<T5> t5) {
      return new RowImpl(new Field[]{t1, t2, t3, t4, t5});
   }

   @Support
   public static <T1, T2, T3, T4, T5, T6> Row6<T1, T2, T3, T4, T5, T6> row(Field<T1> t1, Field<T2> t2, Field<T3> t3, Field<T4> t4, Field<T5> t5, Field<T6> t6) {
      return new RowImpl(new Field[]{t1, t2, t3, t4, t5, t6});
   }

   @Support
   public static <T1, T2, T3, T4, T5, T6, T7> Row7<T1, T2, T3, T4, T5, T6, T7> row(Field<T1> t1, Field<T2> t2, Field<T3> t3, Field<T4> t4, Field<T5> t5, Field<T6> t6, Field<T7> t7) {
      return new RowImpl(new Field[]{t1, t2, t3, t4, t5, t6, t7});
   }

   @Support
   public static <T1, T2, T3, T4, T5, T6, T7, T8> Row8<T1, T2, T3, T4, T5, T6, T7, T8> row(Field<T1> t1, Field<T2> t2, Field<T3> t3, Field<T4> t4, Field<T5> t5, Field<T6> t6, Field<T7> t7, Field<T8> t8) {
      return new RowImpl(new Field[]{t1, t2, t3, t4, t5, t6, t7, t8});
   }

   @Support
   public static <T1, T2, T3, T4, T5, T6, T7, T8, T9> Row9<T1, T2, T3, T4, T5, T6, T7, T8, T9> row(Field<T1> t1, Field<T2> t2, Field<T3> t3, Field<T4> t4, Field<T5> t5, Field<T6> t6, Field<T7> t7, Field<T8> t8, Field<T9> t9) {
      return new RowImpl(new Field[]{t1, t2, t3, t4, t5, t6, t7, t8, t9});
   }

   @Support
   public static <T1, T2, T3, T4, T5, T6, T7, T8, T9, T10> Row10<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10> row(Field<T1> t1, Field<T2> t2, Field<T3> t3, Field<T4> t4, Field<T5> t5, Field<T6> t6, Field<T7> t7, Field<T8> t8, Field<T9> t9, Field<T10> t10) {
      return new RowImpl(new Field[]{t1, t2, t3, t4, t5, t6, t7, t8, t9, t10});
   }

   @Support
   public static <T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11> Row11<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11> row(Field<T1> t1, Field<T2> t2, Field<T3> t3, Field<T4> t4, Field<T5> t5, Field<T6> t6, Field<T7> t7, Field<T8> t8, Field<T9> t9, Field<T10> t10, Field<T11> t11) {
      return new RowImpl(new Field[]{t1, t2, t3, t4, t5, t6, t7, t8, t9, t10, t11});
   }

   @Support
   public static <T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12> Row12<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12> row(Field<T1> t1, Field<T2> t2, Field<T3> t3, Field<T4> t4, Field<T5> t5, Field<T6> t6, Field<T7> t7, Field<T8> t8, Field<T9> t9, Field<T10> t10, Field<T11> t11, Field<T12> t12) {
      return new RowImpl(new Field[]{t1, t2, t3, t4, t5, t6, t7, t8, t9, t10, t11, t12});
   }

   @Support
   public static <T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13> Row13<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13> row(Field<T1> t1, Field<T2> t2, Field<T3> t3, Field<T4> t4, Field<T5> t5, Field<T6> t6, Field<T7> t7, Field<T8> t8, Field<T9> t9, Field<T10> t10, Field<T11> t11, Field<T12> t12, Field<T13> t13) {
      return new RowImpl(new Field[]{t1, t2, t3, t4, t5, t6, t7, t8, t9, t10, t11, t12, t13});
   }

   @Support
   public static <T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14> Row14<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14> row(Field<T1> t1, Field<T2> t2, Field<T3> t3, Field<T4> t4, Field<T5> t5, Field<T6> t6, Field<T7> t7, Field<T8> t8, Field<T9> t9, Field<T10> t10, Field<T11> t11, Field<T12> t12, Field<T13> t13, Field<T14> t14) {
      return new RowImpl(new Field[]{t1, t2, t3, t4, t5, t6, t7, t8, t9, t10, t11, t12, t13, t14});
   }

   @Support
   public static <T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14, T15> Row15<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14, T15> row(Field<T1> t1, Field<T2> t2, Field<T3> t3, Field<T4> t4, Field<T5> t5, Field<T6> t6, Field<T7> t7, Field<T8> t8, Field<T9> t9, Field<T10> t10, Field<T11> t11, Field<T12> t12, Field<T13> t13, Field<T14> t14, Field<T15> t15) {
      return new RowImpl(new Field[]{t1, t2, t3, t4, t5, t6, t7, t8, t9, t10, t11, t12, t13, t14, t15});
   }

   @Support
   public static <T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14, T15, T16> Row16<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14, T15, T16> row(Field<T1> t1, Field<T2> t2, Field<T3> t3, Field<T4> t4, Field<T5> t5, Field<T6> t6, Field<T7> t7, Field<T8> t8, Field<T9> t9, Field<T10> t10, Field<T11> t11, Field<T12> t12, Field<T13> t13, Field<T14> t14, Field<T15> t15, Field<T16> t16) {
      return new RowImpl(new Field[]{t1, t2, t3, t4, t5, t6, t7, t8, t9, t10, t11, t12, t13, t14, t15, t16});
   }

   @Support
   public static <T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14, T15, T16, T17> Row17<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14, T15, T16, T17> row(Field<T1> t1, Field<T2> t2, Field<T3> t3, Field<T4> t4, Field<T5> t5, Field<T6> t6, Field<T7> t7, Field<T8> t8, Field<T9> t9, Field<T10> t10, Field<T11> t11, Field<T12> t12, Field<T13> t13, Field<T14> t14, Field<T15> t15, Field<T16> t16, Field<T17> t17) {
      return new RowImpl(new Field[]{t1, t2, t3, t4, t5, t6, t7, t8, t9, t10, t11, t12, t13, t14, t15, t16, t17});
   }

   @Support
   public static <T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14, T15, T16, T17, T18> Row18<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14, T15, T16, T17, T18> row(Field<T1> t1, Field<T2> t2, Field<T3> t3, Field<T4> t4, Field<T5> t5, Field<T6> t6, Field<T7> t7, Field<T8> t8, Field<T9> t9, Field<T10> t10, Field<T11> t11, Field<T12> t12, Field<T13> t13, Field<T14> t14, Field<T15> t15, Field<T16> t16, Field<T17> t17, Field<T18> t18) {
      return new RowImpl(new Field[]{t1, t2, t3, t4, t5, t6, t7, t8, t9, t10, t11, t12, t13, t14, t15, t16, t17, t18});
   }

   @Support
   public static <T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14, T15, T16, T17, T18, T19> Row19<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14, T15, T16, T17, T18, T19> row(Field<T1> t1, Field<T2> t2, Field<T3> t3, Field<T4> t4, Field<T5> t5, Field<T6> t6, Field<T7> t7, Field<T8> t8, Field<T9> t9, Field<T10> t10, Field<T11> t11, Field<T12> t12, Field<T13> t13, Field<T14> t14, Field<T15> t15, Field<T16> t16, Field<T17> t17, Field<T18> t18, Field<T19> t19) {
      return new RowImpl(new Field[]{t1, t2, t3, t4, t5, t6, t7, t8, t9, t10, t11, t12, t13, t14, t15, t16, t17, t18, t19});
   }

   @Support
   public static <T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14, T15, T16, T17, T18, T19, T20> Row20<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14, T15, T16, T17, T18, T19, T20> row(Field<T1> t1, Field<T2> t2, Field<T3> t3, Field<T4> t4, Field<T5> t5, Field<T6> t6, Field<T7> t7, Field<T8> t8, Field<T9> t9, Field<T10> t10, Field<T11> t11, Field<T12> t12, Field<T13> t13, Field<T14> t14, Field<T15> t15, Field<T16> t16, Field<T17> t17, Field<T18> t18, Field<T19> t19, Field<T20> t20) {
      return new RowImpl(new Field[]{t1, t2, t3, t4, t5, t6, t7, t8, t9, t10, t11, t12, t13, t14, t15, t16, t17, t18, t19, t20});
   }

   @Support
   public static <T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14, T15, T16, T17, T18, T19, T20, T21> Row21<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14, T15, T16, T17, T18, T19, T20, T21> row(Field<T1> t1, Field<T2> t2, Field<T3> t3, Field<T4> t4, Field<T5> t5, Field<T6> t6, Field<T7> t7, Field<T8> t8, Field<T9> t9, Field<T10> t10, Field<T11> t11, Field<T12> t12, Field<T13> t13, Field<T14> t14, Field<T15> t15, Field<T16> t16, Field<T17> t17, Field<T18> t18, Field<T19> t19, Field<T20> t20, Field<T21> t21) {
      return new RowImpl(new Field[]{t1, t2, t3, t4, t5, t6, t7, t8, t9, t10, t11, t12, t13, t14, t15, t16, t17, t18, t19, t20, t21});
   }

   @Support
   public static <T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14, T15, T16, T17, T18, T19, T20, T21, T22> Row22<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14, T15, T16, T17, T18, T19, T20, T21, T22> row(Field<T1> t1, Field<T2> t2, Field<T3> t3, Field<T4> t4, Field<T5> t5, Field<T6> t6, Field<T7> t7, Field<T8> t8, Field<T9> t9, Field<T10> t10, Field<T11> t11, Field<T12> t12, Field<T13> t13, Field<T14> t14, Field<T15> t15, Field<T16> t16, Field<T17> t17, Field<T18> t18, Field<T19> t19, Field<T20> t20, Field<T21> t21, Field<T22> t22) {
      return new RowImpl(new Field[]{t1, t2, t3, t4, t5, t6, t7, t8, t9, t10, t11, t12, t13, t14, t15, t16, t17, t18, t19, t20, t21, t22});
   }

   @Support
   public static RowN row(Field<?>... values) {
      return new RowImpl(values);
   }

   @Support
   public static RowN row(Collection<?> values) {
      Collection<Field<?>> fields = new ArrayList();
      Iterator var2 = values.iterator();

      while(var2.hasNext()) {
         Object o = var2.next();
         fields.add(o instanceof Field ? (Field)o : val(o));
      }

      return new RowImpl(fields);
   }

   @Support
   public static Table<Record> values(RowN... rows) {
      Values.assertNotEmpty(rows);
      int size = rows[0].size();
      String[] columns = new String[size];

      for(int i = 0; i < size; ++i) {
         columns[i] = "c" + i;
      }

      return (new Values(rows)).as("v", columns);
   }

   @SafeVarargs
   @Support
   public static <T1> Table<Record1<T1>> values(Row1<T1>... rows) {
      return (new Values(rows)).as("v", "c1");
   }

   @SafeVarargs
   @Support
   public static <T1, T2> Table<Record2<T1, T2>> values(Row2<T1, T2>... rows) {
      return (new Values(rows)).as("v", "c1", "c2");
   }

   @SafeVarargs
   @Support
   public static <T1, T2, T3> Table<Record3<T1, T2, T3>> values(Row3<T1, T2, T3>... rows) {
      return (new Values(rows)).as("v", "c1", "c2", "c3");
   }

   @SafeVarargs
   @Support
   public static <T1, T2, T3, T4> Table<Record4<T1, T2, T3, T4>> values(Row4<T1, T2, T3, T4>... rows) {
      return (new Values(rows)).as("v", "c1", "c2", "c3", "c4");
   }

   @SafeVarargs
   @Support
   public static <T1, T2, T3, T4, T5> Table<Record5<T1, T2, T3, T4, T5>> values(Row5<T1, T2, T3, T4, T5>... rows) {
      return (new Values(rows)).as("v", "c1", "c2", "c3", "c4", "c5");
   }

   @SafeVarargs
   @Support
   public static <T1, T2, T3, T4, T5, T6> Table<Record6<T1, T2, T3, T4, T5, T6>> values(Row6<T1, T2, T3, T4, T5, T6>... rows) {
      return (new Values(rows)).as("v", "c1", "c2", "c3", "c4", "c5", "c6");
   }

   @SafeVarargs
   @Support
   public static <T1, T2, T3, T4, T5, T6, T7> Table<Record7<T1, T2, T3, T4, T5, T6, T7>> values(Row7<T1, T2, T3, T4, T5, T6, T7>... rows) {
      return (new Values(rows)).as("v", "c1", "c2", "c3", "c4", "c5", "c6", "c7");
   }

   @SafeVarargs
   @Support
   public static <T1, T2, T3, T4, T5, T6, T7, T8> Table<Record8<T1, T2, T3, T4, T5, T6, T7, T8>> values(Row8<T1, T2, T3, T4, T5, T6, T7, T8>... rows) {
      return (new Values(rows)).as("v", "c1", "c2", "c3", "c4", "c5", "c6", "c7", "c8");
   }

   @SafeVarargs
   @Support
   public static <T1, T2, T3, T4, T5, T6, T7, T8, T9> Table<Record9<T1, T2, T3, T4, T5, T6, T7, T8, T9>> values(Row9<T1, T2, T3, T4, T5, T6, T7, T8, T9>... rows) {
      return (new Values(rows)).as("v", "c1", "c2", "c3", "c4", "c5", "c6", "c7", "c8", "c9");
   }

   @SafeVarargs
   @Support
   public static <T1, T2, T3, T4, T5, T6, T7, T8, T9, T10> Table<Record10<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10>> values(Row10<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10>... rows) {
      return (new Values(rows)).as("v", "c1", "c2", "c3", "c4", "c5", "c6", "c7", "c8", "c9", "c10");
   }

   @SafeVarargs
   @Support
   public static <T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11> Table<Record11<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11>> values(Row11<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11>... rows) {
      return (new Values(rows)).as("v", "c1", "c2", "c3", "c4", "c5", "c6", "c7", "c8", "c9", "c10", "c11");
   }

   @SafeVarargs
   @Support
   public static <T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12> Table<Record12<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12>> values(Row12<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12>... rows) {
      return (new Values(rows)).as("v", "c1", "c2", "c3", "c4", "c5", "c6", "c7", "c8", "c9", "c10", "c11", "c12");
   }

   @SafeVarargs
   @Support
   public static <T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13> Table<Record13<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13>> values(Row13<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13>... rows) {
      return (new Values(rows)).as("v", "c1", "c2", "c3", "c4", "c5", "c6", "c7", "c8", "c9", "c10", "c11", "c12", "c13");
   }

   @SafeVarargs
   @Support
   public static <T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14> Table<Record14<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14>> values(Row14<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14>... rows) {
      return (new Values(rows)).as("v", "c1", "c2", "c3", "c4", "c5", "c6", "c7", "c8", "c9", "c10", "c11", "c12", "c13", "c14");
   }

   @SafeVarargs
   @Support
   public static <T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14, T15> Table<Record15<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14, T15>> values(Row15<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14, T15>... rows) {
      return (new Values(rows)).as("v", "c1", "c2", "c3", "c4", "c5", "c6", "c7", "c8", "c9", "c10", "c11", "c12", "c13", "c14", "c15");
   }

   @SafeVarargs
   @Support
   public static <T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14, T15, T16> Table<Record16<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14, T15, T16>> values(Row16<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14, T15, T16>... rows) {
      return (new Values(rows)).as("v", "c1", "c2", "c3", "c4", "c5", "c6", "c7", "c8", "c9", "c10", "c11", "c12", "c13", "c14", "c15", "c16");
   }

   @SafeVarargs
   @Support
   public static <T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14, T15, T16, T17> Table<Record17<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14, T15, T16, T17>> values(Row17<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14, T15, T16, T17>... rows) {
      return (new Values(rows)).as("v", "c1", "c2", "c3", "c4", "c5", "c6", "c7", "c8", "c9", "c10", "c11", "c12", "c13", "c14", "c15", "c16", "c17");
   }

   @SafeVarargs
   @Support
   public static <T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14, T15, T16, T17, T18> Table<Record18<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14, T15, T16, T17, T18>> values(Row18<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14, T15, T16, T17, T18>... rows) {
      return (new Values(rows)).as("v", "c1", "c2", "c3", "c4", "c5", "c6", "c7", "c8", "c9", "c10", "c11", "c12", "c13", "c14", "c15", "c16", "c17", "c18");
   }

   @SafeVarargs
   @Support
   public static <T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14, T15, T16, T17, T18, T19> Table<Record19<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14, T15, T16, T17, T18, T19>> values(Row19<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14, T15, T16, T17, T18, T19>... rows) {
      return (new Values(rows)).as("v", "c1", "c2", "c3", "c4", "c5", "c6", "c7", "c8", "c9", "c10", "c11", "c12", "c13", "c14", "c15", "c16", "c17", "c18", "c19");
   }

   @SafeVarargs
   @Support
   public static <T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14, T15, T16, T17, T18, T19, T20> Table<Record20<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14, T15, T16, T17, T18, T19, T20>> values(Row20<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14, T15, T16, T17, T18, T19, T20>... rows) {
      return (new Values(rows)).as("v", "c1", "c2", "c3", "c4", "c5", "c6", "c7", "c8", "c9", "c10", "c11", "c12", "c13", "c14", "c15", "c16", "c17", "c18", "c19", "c20");
   }

   @SafeVarargs
   @Support
   public static <T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14, T15, T16, T17, T18, T19, T20, T21> Table<Record21<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14, T15, T16, T17, T18, T19, T20, T21>> values(Row21<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14, T15, T16, T17, T18, T19, T20, T21>... rows) {
      return (new Values(rows)).as("v", "c1", "c2", "c3", "c4", "c5", "c6", "c7", "c8", "c9", "c10", "c11", "c12", "c13", "c14", "c15", "c16", "c17", "c18", "c19", "c20", "c21");
   }

   @SafeVarargs
   @Support
   public static <T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14, T15, T16, T17, T18, T19, T20, T21, T22> Table<Record22<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14, T15, T16, T17, T18, T19, T20, T21, T22>> values(Row22<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14, T15, T16, T17, T18, T19, T20, T21, T22>... rows) {
      return (new Values(rows)).as("v", "c1", "c2", "c3", "c4", "c5", "c6", "c7", "c8", "c9", "c10", "c11", "c12", "c13", "c14", "c15", "c16", "c17", "c18", "c19", "c20", "c21", "c22");
   }

   static Field<?> NULL() {
      return field("null");
   }

   protected static <T> Field<T> nullSafe(Field<T> field) {
      return (Field)(field == null ? val((Object)null) : field);
   }

   protected static <T> Field<T> nullSafe(Field<T> field, DataType<?> type) {
      return (Field)(field == null ? val((Object)null, (DataType)type) : field);
   }

   protected static Field<?>[] nullSafe(Field<?>... fields) {
      if (fields == null) {
         return Tools.EMPTY_FIELD;
      } else {
         Field<?>[] result = new Field[fields.length];

         for(int i = 0; i < fields.length; ++i) {
            result[i] = nullSafe(fields[i]);
         }

         return result;
      }
   }

   protected static <T> DataType<T> nullSafeDataType(Field<T> field) {
      return field == null ? SQLDataType.OTHER : field.getDataType();
   }

   @Support
   public static Param<Integer> zero() {
      return inline((int)0);
   }

   @Support
   public static Param<Integer> one() {
      return inline((int)1);
   }

   @Support
   public static Param<Integer> two() {
      return inline((int)2);
   }

   @Support
   public static Field<BigDecimal> pi() {
      return new Pi();
   }

   @Support
   public static Field<BigDecimal> e() {
      return new Euler();
   }

   @Support({SQLDialect.CUBRID, SQLDialect.DERBY, SQLDialect.FIREBIRD, SQLDialect.H2, SQLDialect.HSQLDB, SQLDialect.MARIADB, SQLDialect.MYSQL, SQLDialect.POSTGRES, SQLDialect.SQLITE})
   public static Field<String> currentUser() {
      return new CurrentUser();
   }

   @Support({SQLDialect.CUBRID, SQLDialect.DERBY, SQLDialect.FIREBIRD, SQLDialect.H2, SQLDialect.HSQLDB, SQLDialect.MARIADB, SQLDialect.MYSQL, SQLDialect.POSTGRES, SQLDialect.SQLITE})
   public static Field<String> currentSchema() {
      return new CurrentSchema();
   }

   @Support
   public static <T> DataType<T> getDataType(Class<T> type) {
      return DefaultDataType.getDataType(SQLDialect.DEFAULT, type);
   }

   protected DSL() {
      throw new UnsupportedOperationException();
   }

   private DSL(Connection connection, SQLDialect dialect) {
      throw new UnsupportedOperationException();
   }

   private DSL(Connection connection, SQLDialect dialect, Settings settings) {
      throw new UnsupportedOperationException();
   }

   private DSL(DataSource datasource, SQLDialect dialect) {
      throw new UnsupportedOperationException();
   }

   private DSL(DataSource datasource, SQLDialect dialect, Settings settings) {
      throw new UnsupportedOperationException();
   }

   private DSL(SQLDialect dialect) {
      throw new UnsupportedOperationException();
   }

   private DSL(SQLDialect dialect, Settings settings) {
      throw new UnsupportedOperationException();
   }
}
