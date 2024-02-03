package org.jooq.impl;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.sql.Date;
import java.sql.Time;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Map;
import java.util.TreeSet;
import org.jooq.AggregateFunction;
import org.jooq.AlterIndexFinalStep;
import org.jooq.AlterIndexStep;
import org.jooq.AlterSchemaFinalStep;
import org.jooq.AlterSchemaStep;
import org.jooq.AlterTableDropStep;
import org.jooq.AlterTableFinalStep;
import org.jooq.AlterTableStep;
import org.jooq.CaseConditionStep;
import org.jooq.CaseValueStep;
import org.jooq.CaseWhenStep;
import org.jooq.Comparator;
import org.jooq.Condition;
import org.jooq.Configuration;
import org.jooq.Constraint;
import org.jooq.ConstraintTypeStep;
import org.jooq.CreateIndexFinalStep;
import org.jooq.CreateIndexStep;
import org.jooq.CreateIndexWhereStep;
import org.jooq.CreateTableAsStep;
import org.jooq.CreateTableColumnStep;
import org.jooq.CreateTableConstraintStep;
import org.jooq.CreateTableFinalStep;
import org.jooq.DDLQuery;
import org.jooq.DSLContext;
import org.jooq.DataType;
import org.jooq.DatePart;
import org.jooq.Delete;
import org.jooq.DeleteFinalStep;
import org.jooq.DeleteWhereStep;
import org.jooq.DropIndexFinalStep;
import org.jooq.DropIndexOnStep;
import org.jooq.DropSchemaFinalStep;
import org.jooq.DropSchemaStep;
import org.jooq.DropSequenceFinalStep;
import org.jooq.DropTableFinalStep;
import org.jooq.DropTableStep;
import org.jooq.DropViewFinalStep;
import org.jooq.Field;
import org.jooq.GroupField;
import org.jooq.Insert;
import org.jooq.InsertSetStep;
import org.jooq.InsertValuesStepN;
import org.jooq.JoinType;
import org.jooq.Merge;
import org.jooq.MergeFinalStep;
import org.jooq.MergeMatchedStep;
import org.jooq.MergeNotMatchedStep;
import org.jooq.Name;
import org.jooq.Parser;
import org.jooq.Queries;
import org.jooq.Query;
import org.jooq.Record;
import org.jooq.Schema;
import org.jooq.Select;
import org.jooq.Sequence;
import org.jooq.SortField;
import org.jooq.Table;
import org.jooq.TableField;
import org.jooq.TableLike;
import org.jooq.TableOptionalOnStep;
import org.jooq.Truncate;
import org.jooq.TruncateCascadeStep;
import org.jooq.TruncateFinalStep;
import org.jooq.TruncateIdentityStep;
import org.jooq.Update;
import org.jooq.WindowBeforeOverStep;
import org.jooq.WindowIgnoreNullsStep;
import org.jooq.WindowOverStep;
import org.jooq.WindowSpecification;
import org.jooq.WindowSpecificationOrderByStep;
import org.jooq.WindowSpecificationRowsAndStep;
import org.jooq.WindowSpecificationRowsStep;
import org.jooq.exception.DataAccessException;
import org.jooq.exception.SQLStateSubclass;

/** @deprecated */
@Deprecated
class ParserImpl implements Parser {
   private final Configuration configuration;
   private final DSLContext dsl;
   private static final String[] SELECT_KEYWORDS = new String[]{"CONNECT", "CROSS", "EXCEPT", "FETCH", "FULL", "FROM", "GROUP BY", "HAVING", "INNER", "INTERSECT", "JOIN", "LEFT", "LIMIT", "MINUS", "NATURAL", "OFFSET", "ON", "ORDER BY", "OUTER", "RIGHT", "SELECT", "START", "UNION", "USING", "WHERE"};

   ParserImpl(Configuration configuration) {
      this.configuration = configuration;
      this.dsl = DSL.using(configuration);
   }

   public final Queries parse(String sql) {
      ParserImpl.ParserContext ctx = new ParserImpl.ParserContext(this.dsl, sql);
      ArrayList result = new ArrayList();

      do {
         result.add(parseQuery(ctx));
      } while(parseIf(ctx, ";"));

      if (!ctx.done()) {
         throw new ParserImpl.ParserException(ctx);
      } else {
         return new QueriesImpl(result);
      }
   }

   public final Query parseQuery(String sql) {
      ParserImpl.ParserContext ctx = new ParserImpl.ParserContext(this.dsl, sql);
      Query result = parseQuery(ctx);
      if (!ctx.done()) {
         throw new ParserImpl.ParserException(ctx);
      } else {
         return result;
      }
   }

   public final Table<?> parseTable(String sql) {
      ParserImpl.ParserContext ctx = new ParserImpl.ParserContext(this.dsl, sql);
      Table<?> result = parseTable(ctx);
      if (!ctx.done()) {
         throw new ParserImpl.ParserException(ctx);
      } else {
         return result;
      }
   }

   public final Field<?> parseField(String sql) {
      ParserImpl.ParserContext ctx = new ParserImpl.ParserContext(this.dsl, sql);
      Field<?> result = parseField(ctx);
      if (!ctx.done()) {
         throw new ParserImpl.ParserException(ctx);
      } else {
         return result;
      }
   }

   public final Condition parseCondition(String sql) {
      ParserImpl.ParserContext ctx = new ParserImpl.ParserContext(this.dsl, sql);
      Condition result = parseCondition(ctx);
      if (!ctx.done()) {
         throw new ParserImpl.ParserException(ctx);
      } else {
         return result;
      }
   }

   public final Name parseName(String sql) {
      ParserImpl.ParserContext ctx = new ParserImpl.ParserContext(this.dsl, sql);
      Name result = parseName(ctx);
      if (!ctx.done()) {
         throw new ParserImpl.ParserException(ctx);
      } else {
         return result;
      }
   }

   static final Query parseQuery(ParserImpl.ParserContext ctx) {
      if (ctx.done()) {
         return null;
      } else {
         parseWhitespaceIf(ctx);

         try {
            SelectQueryImpl var6;
            DDLQuery var7;
            switch(ctx.character()) {
            case '(':
               var6 = parseSelect(ctx);
               return var6;
            case ')':
            case '*':
            case '+':
            case ',':
            case '-':
            case '.':
            case '/':
            case '0':
            case '1':
            case '2':
            case '3':
            case '4':
            case '5':
            case '6':
            case '7':
            case '8':
            case '9':
            case ':':
            case ';':
            case '<':
            case '=':
            case '>':
            case '?':
            case '@':
            case 'B':
            case 'E':
            case 'F':
            case 'G':
            case 'H':
            case 'J':
            case 'K':
            case 'L':
            case 'N':
            case 'O':
            case 'P':
            case 'Q':
            case 'V':
            case 'W':
            case 'X':
            case 'Y':
            case 'Z':
            case '[':
            case '\\':
            case ']':
            case '^':
            case '_':
            case '`':
            case 'b':
            case 'e':
            case 'f':
            case 'g':
            case 'h':
            case 'j':
            case 'k':
            case 'l':
            case 'n':
            case 'o':
            case 'p':
            case 'q':
            default:
               throw ctx.exception();
            case 'A':
            case 'a':
               if (peekKeyword(ctx, "ALTER")) {
                  var7 = parseAlter(ctx);
                  return var7;
               }

               throw ctx.exception();
            case 'C':
            case 'c':
               if (peekKeyword(ctx, "CREATE")) {
                  var7 = parseCreate(ctx);
                  return var7;
               }

               throw ctx.exception();
            case 'D':
            case 'd':
               if (peekKeyword(ctx, "DELETE")) {
                  Delete var10 = parseDelete(ctx);
                  return var10;
               } else {
                  if (!peekKeyword(ctx, "DROP")) {
                     throw ctx.exception();
                  }

                  var7 = parseDrop(ctx);
                  return var7;
               }
            case 'I':
            case 'i':
               if (peekKeyword(ctx, "INSERT")) {
                  Insert var9 = parseInsert(ctx);
                  return var9;
               }

               throw ctx.exception();
            case 'M':
            case 'm':
               if (peekKeyword(ctx, "MERGE")) {
                  Merge var8 = parseMerge(ctx);
                  return var8;
               }

               throw ctx.exception();
            case 'R':
            case 'r':
               if (peekKeyword(ctx, "RENAME")) {
                  var7 = parseRename(ctx);
                  return var7;
               }

               throw ctx.exception();
            case 'S':
            case 's':
               if (peekKeyword(ctx, "SELECT")) {
                  var6 = parseSelect(ctx);
                  return var6;
               }

               throw ctx.exception();
            case 'T':
            case 't':
               if (!peekKeyword(ctx, "TRUNCATE")) {
                  throw ctx.exception();
               }

               Truncate var5 = parseTruncate(ctx);
               return var5;
            case 'U':
            case 'u':
               if (!peekKeyword(ctx, "UPDATE")) {
                  throw ctx.exception();
               } else {
                  Update var1 = parseUpdate(ctx);
                  return var1;
               }
            }
         } finally {
            parseWhitespaceIf(ctx);
            if (!ctx.done() && ctx.character() != ';') {
               throw ctx.unexpectedToken();
            }
         }
      }
   }

   private static final SelectQueryImpl<Record> parseSelect(ParserImpl.ParserContext ctx) {
      SelectQueryImpl result = parseQueryPrimary(ctx);

      CombineOperator combine;
      while((combine = parseCombineOperatorIf(ctx)) != null) {
         switch(combine) {
         case UNION:
            result = (SelectQueryImpl)result.union(parseQueryPrimary(ctx));
            break;
         case UNION_ALL:
            result = (SelectQueryImpl)result.unionAll(parseQueryPrimary(ctx));
            break;
         case EXCEPT:
            result = (SelectQueryImpl)result.except(parseQueryPrimary(ctx));
            break;
         case EXCEPT_ALL:
            result = (SelectQueryImpl)result.exceptAll(parseQueryPrimary(ctx));
            break;
         case INTERSECT:
            result = (SelectQueryImpl)result.intersect(parseQueryPrimary(ctx));
            break;
         case INTERSECT_ALL:
            result = (SelectQueryImpl)result.intersectAll(parseQueryPrimary(ctx));
            break;
         default:
            ctx.unexpectedToken();
         }
      }

      if (parseKeywordIf(ctx, "ORDER BY")) {
         result.addOrderBy((Collection)parseSortSpecification(ctx));
      }

      if (!result.getLimit().isApplicable()) {
         boolean offsetStandard = false;
         boolean offsetPostgres = false;
         if (parseKeywordIf(ctx, "OFFSET")) {
            result.addOffset((int)parseUnsignedInteger(ctx));
            if (!parseKeywordIf(ctx, "ROWS") && !parseKeywordIf(ctx, "ROW")) {
               offsetPostgres = true;
            } else {
               offsetStandard = true;
            }
         }

         if (!offsetStandard && parseKeywordIf(ctx, "LIMIT")) {
            int limit = (int)parseUnsignedInteger(ctx);
            if (!offsetPostgres && parseIf(ctx, ',')) {
               result.addLimit(limit, (int)parseUnsignedInteger(ctx));
            } else if (!offsetPostgres && parseKeywordIf(ctx, "OFFSET")) {
               result.addLimit((int)parseUnsignedInteger(ctx), limit);
            } else {
               result.addLimit(limit);
            }
         } else if (!offsetPostgres && parseKeywordIf(ctx, "FETCH")) {
            if (!parseKeywordIf(ctx, "FIRST") && !parseKeywordIf(ctx, "NEXT")) {
               throw ctx.unexpectedToken();
            }

            result.addLimit((int)parseUnsignedInteger(ctx));
            if (!parseKeywordIf(ctx, "ROWS ONLY") && !parseKeywordIf(ctx, "ROW ONLY")) {
               throw ctx.unexpectedToken();
            }
         }
      }

      return result;
   }

   private static final SelectQueryImpl<Record> parseQueryPrimary(ParserImpl.ParserContext ctx) {
      if (parseIf(ctx, '(')) {
         SelectQueryImpl<Record> result = parseSelect(ctx);
         parse(ctx, ')');
         return result;
      } else {
         parseKeyword(ctx, "SELECT");
         boolean distinct = parseKeywordIf(ctx, "DISTINCT") || parseKeywordIf(ctx, "UNIQUE");
         Long limit = null;
         Long offset = null;
         if (!distinct) {
            parseKeywordIf(ctx, "ALL");
         }

         if (parseKeywordIf(ctx, "TOP")) {
            limit = parseUnsignedInteger(ctx);
            if (parseKeywordIf(ctx, "START AT")) {
               offset = parseUnsignedInteger(ctx);
            }
         }

         List<Field<?>> select = parseSelectList(ctx);
         List<Table<?>> from = null;
         Condition startWith = null;
         Condition connectBy = null;
         boolean connectByNoCycle = false;
         Condition where = null;
         List<GroupField> groupBy = null;
         Condition having = null;
         if (parseKeywordIf(ctx, "FROM")) {
            from = parseTables(ctx);
         }

         if (from != null && from.size() == 1 && ((Table)from.get(0)).getName().equalsIgnoreCase("dual")) {
            from = null;
         }

         if (parseKeywordIf(ctx, "START WITH")) {
            startWith = parseCondition(ctx);
            parseKeyword(ctx, "CONNECT BY");
            connectByNoCycle = parseKeywordIf(ctx, "NOCYCLE");
            connectBy = parseCondition(ctx);
         } else if (parseKeywordIf(ctx, "CONNECT BY")) {
            connectByNoCycle = parseKeywordIf(ctx, "NOCYCLE");
            connectBy = parseCondition(ctx);
            if (parseKeywordIf(ctx, "START WITH")) {
               startWith = parseCondition(ctx);
            }
         }

         if (parseKeywordIf(ctx, "WHERE")) {
            where = parseCondition(ctx);
         }

         if (parseKeywordIf(ctx, "GROUP BY")) {
            if (parseIf(ctx, '(')) {
               parse(ctx, ')');
               groupBy = Collections.emptyList();
            } else if (parseKeywordIf(ctx, "ROLLUP")) {
               parse(ctx, '(');
               groupBy = Collections.singletonList(DSL.rollup((Field[])parseFields(ctx).toArray(Tools.EMPTY_FIELD)));
               parse(ctx, ')');
            } else if (parseKeywordIf(ctx, "CUBE")) {
               parse(ctx, '(');
               groupBy = Collections.singletonList(DSL.cube((Field[])parseFields(ctx).toArray(Tools.EMPTY_FIELD)));
               parse(ctx, ')');
            } else if (parseKeywordIf(ctx, "GROUPING SETS")) {
               List<List<Field<?>>> fieldSets = new ArrayList();
               parse(ctx, '(');

               do {
                  parse(ctx, '(');
                  if (parseIf(ctx, ')')) {
                     fieldSets.add(Collections.emptyList());
                  } else {
                     fieldSets.add(parseFields(ctx));
                     parse(ctx, ')');
                  }
               } while(parseIf(ctx, ','));

               parse(ctx, ')');
               groupBy = Collections.singletonList(DSL.groupingSets((Collection[])fieldSets.toArray((Collection[])Tools.EMPTY_COLLECTION)));
            } else {
               groupBy = parseFields(ctx);
               if (parseKeywordIf(ctx, "WITH ROLLUP")) {
                  groupBy = Collections.singletonList(DSL.rollup((Field[])groupBy.toArray(Tools.EMPTY_FIELD)));
               }
            }
         }

         if (parseKeywordIf(ctx, "HAVING")) {
            having = parseCondition(ctx);
         }

         SelectQueryImpl<Record> result = (SelectQueryImpl)ctx.dsl.selectQuery();
         if (distinct) {
            result.setDistinct(distinct);
         }

         if (select.size() > 0) {
            result.addSelect((Collection)select);
         }

         if (from != null) {
            result.addFrom((Collection)from);
         }

         if (connectBy != null) {
            if (connectByNoCycle) {
               result.addConnectByNoCycle(connectBy);
            } else {
               result.addConnectBy(connectBy);
            }
         }

         if (startWith != null) {
            result.setConnectByStartWith(startWith);
         }

         if (where != null) {
            result.addConditions(where);
         }

         if (groupBy != null) {
            result.addGroupBy((Collection)groupBy);
         }

         if (having != null) {
            result.addHaving(having);
         }

         if (limit != null) {
            if (offset != null) {
               result.addLimit((int)offset, (int)limit);
            } else {
               result.addLimit((int)limit);
            }
         }

         return result;
      }
   }

   private static final Delete<?> parseDelete(ParserImpl.ParserContext ctx) {
      parseKeyword(ctx, "DELETE");
      parseKeywordIf(ctx, "FROM");
      Table<?> tableName = parseTableName(ctx);
      boolean where = parseKeywordIf(ctx, "WHERE");
      Condition condition = where ? parseCondition(ctx) : null;
      DeleteWhereStep<?> s1 = ctx.dsl.delete(tableName);
      DeleteFinalStep<?> s2 = where ? s1.where(condition) : s1;
      return (Delete)s2;
   }

   private static final Insert<?> parseInsert(ParserImpl.ParserContext ctx) {
      parseKeyword(ctx, "INSERT INTO");
      Table<?> tableName = parseTableName(ctx);
      Field<?>[] fields = null;
      if (parseIf(ctx, '(')) {
         fields = Tools.fieldsByName((String[])parseIdentifiers(ctx).toArray(Tools.EMPTY_STRING));
         parse(ctx, ')');
      }

      if (!parseKeywordIf(ctx, "VALUES")) {
         if (parseKeywordIf(ctx, "SET")) {
            Map<Field<?>, Object> map = parseSetClauseList(ctx);
            return ctx.dsl.insertInto(tableName).set(map);
         } else if (peekKeyword(ctx, "SELECT")) {
            SelectQueryImpl<Record> select = parseSelect(ctx);
            return (Insert)(fields == null ? ctx.dsl.insertInto(tableName).select(select) : ctx.dsl.insertInto(tableName).columns(fields).select(select));
         } else if (parseKeywordIf(ctx, "DEFAULT VALUES")) {
            if (fields != null) {
               throw ctx.exception();
            } else {
               return ctx.dsl.insertInto(tableName).defaultValues();
            }
         } else {
            throw ctx.unexpectedToken();
         }
      } else {
         ArrayList allValues = new ArrayList();

         do {
            parse(ctx, '(');
            List<Field<?>> values = parseFields(ctx);
            if (fields != null && fields.length != values.size()) {
               throw ctx.exception();
            }

            allValues.add(values);
            parse(ctx, ')');
         } while(parseIf(ctx, ','));

         InsertSetStep<?> step1 = ctx.dsl.insertInto(tableName);
         InsertValuesStepN<?> step2 = fields != null ? step1.columns(fields) : (InsertValuesStepN)step1;

         List values;
         for(Iterator var6 = allValues.iterator(); var6.hasNext(); step2 = step2.values((Collection)values)) {
            values = (List)var6.next();
         }

         return step2;
      }
   }

   private static final Update<?> parseUpdate(ParserImpl.ParserContext ctx) {
      parseKeyword(ctx, "UPDATE");
      Table<?> tableName = parseTableName(ctx);
      parseKeyword(ctx, "SET");
      Map<Field<?>, Object> map = parseSetClauseList(ctx);
      Condition condition = parseKeywordIf(ctx, "WHERE") ? parseCondition(ctx) : null;
      return (Update)(condition == null ? ctx.dsl.update(tableName).set(map) : ctx.dsl.update(tableName).set(map).where(new Condition[]{condition}));
   }

   private static final Map<Field<?>, Object> parseSetClauseList(ParserImpl.ParserContext ctx) {
      LinkedHashMap map = new LinkedHashMap();

      do {
         Field<?> field = parseFieldName(ctx);
         if (map.containsKey(field)) {
            throw ctx.exception();
         }

         parse(ctx, '=');
         Field<?> value = parseField(ctx);
         map.put(field, value);
      } while(parseIf(ctx, ','));

      return map;
   }

   private static final Merge<?> parseMerge(ParserImpl.ParserContext ctx) {
      parseKeyword(ctx, "MERGE INTO");
      Table<?> target = parseTableName(ctx);
      parseKeyword(ctx, "USING");
      parse(ctx, '(');
      TableLike<?> using = parseSelect(ctx);
      parse(ctx, ')');
      if (parseKeywordIf(ctx, "AS")) {
         using = ((TableLike)using).asTable(parseIdentifier(ctx));
      }

      parseKeyword(ctx, "ON");
      Condition on = parseCondition(ctx);
      boolean update = false;
      boolean insert = false;
      List<Field<?>> insertColumns = null;
      List<Field<?>> insertValues = null;
      Map updateSet = null;

      do {
         while(!update && (update = parseKeywordIf(ctx, "WHEN MATCHED THEN UPDATE SET"))) {
            updateSet = parseSetClauseList(ctx);
         }

         if (insert || !(insert = parseKeywordIf(ctx, "WHEN NOT MATCHED THEN INSERT"))) {
            if (!update && !insert) {
               throw ctx.exception();
            } else {
               MergeMatchedStep<?> s1 = ctx.dsl.mergeInto(target).using((TableLike)using).on(on);
               MergeNotMatchedStep<?> s2 = update ? s1.whenMatchedThenUpdate().set(updateSet) : s1;
               MergeFinalStep<?> s3 = insert ? ((MergeNotMatchedStep)s2).whenNotMatchedThenInsert((Collection)insertColumns).values((Collection)insertValues) : s2;
               return (Merge)s3;
            }
         }

         parse(ctx, '(');
         insertColumns = Arrays.asList(Tools.fieldsByName((Collection)parseIdentifiers(ctx)));
         parse(ctx, ')');
         parseKeyword(ctx, "VALUES");
         parse(ctx, '(');
         insertValues = parseFields(ctx);
         parse(ctx, ')');
      } while(insertColumns.size() == insertValues.size());

      throw ctx.exception();
   }

   private static final DDLQuery parseCreate(ParserImpl.ParserContext ctx) {
      parseKeyword(ctx, "CREATE");
      if (parseKeywordIf(ctx, "TABLE")) {
         return parseCreateTable(ctx);
      } else if (parseKeywordIf(ctx, "INDEX")) {
         return parseCreateIndex(ctx);
      } else if (parseKeywordIf(ctx, "SCHEMA")) {
         return parseCreateSchema(ctx);
      } else if (parseKeywordIf(ctx, "VIEW")) {
         return parseCreateView(ctx);
      } else {
         throw ctx.unexpectedToken();
      }
   }

   private static final DDLQuery parseAlter(ParserImpl.ParserContext ctx) {
      parseKeyword(ctx, "ALTER");
      if (parseKeywordIf(ctx, "TABLE")) {
         return parseAlterTable(ctx);
      } else if (parseKeywordIf(ctx, "INDEX")) {
         return parseAlterIndex(ctx);
      } else if (parseKeywordIf(ctx, "SCHEMA")) {
         return parseAlterSchema(ctx);
      } else if (parseKeywordIf(ctx, "VIEW")) {
         return parseAlterView(ctx);
      } else {
         throw ctx.unexpectedToken();
      }
   }

   private static final DDLQuery parseDrop(ParserImpl.ParserContext ctx) {
      parseKeyword(ctx, "DROP");
      if (parseKeywordIf(ctx, "TABLE")) {
         return parseDropTable(ctx);
      } else if (parseKeywordIf(ctx, "INDEX")) {
         return parseDropIndex(ctx);
      } else if (parseKeywordIf(ctx, "VIEW")) {
         return parseDropView(ctx);
      } else if (parseKeywordIf(ctx, "SEQUENCE")) {
         return parseDropSequence(ctx);
      } else if (parseKeywordIf(ctx, "SCHEMA")) {
         return parseDropSchema(ctx);
      } else {
         throw ctx.unexpectedToken();
      }
   }

   private static final Truncate<?> parseTruncate(ParserImpl.ParserContext ctx) {
      parseKeyword(ctx, "TRUNCATE");
      parseKeyword(ctx, "TABLE");
      Table<?> table = parseTableName(ctx);
      boolean continueIdentity = parseKeywordIf(ctx, "CONTINUE IDENTITY");
      boolean restartIdentity = !continueIdentity && parseKeywordIf(ctx, "RESTART IDENTITY");
      boolean cascade = parseKeywordIf(ctx, "CASCADE");
      boolean restrict = !cascade && parseKeywordIf(ctx, "RESTRICT");
      TruncateIdentityStep<?> step1 = ctx.dsl.truncate(table);
      TruncateCascadeStep<?> step2 = continueIdentity ? step1.continueIdentity() : (restartIdentity ? step1.restartIdentity() : step1);
      TruncateFinalStep<?> step3 = cascade ? ((TruncateCascadeStep)step2).cascade() : (restrict ? ((TruncateCascadeStep)step2).restrict() : step2);
      return (Truncate)step3;
   }

   private static final DDLQuery parseCreateView(ParserImpl.ParserContext ctx) {
      boolean ifNotExists = parseKeywordIf(ctx, "IF NOT EXISTS");
      Table<?> view = parseTableName(ctx);
      Field<?>[] fields = Tools.EMPTY_FIELD;
      if (parseIf(ctx, '(')) {
         fields = (Field[])parseFieldNames(ctx).toArray(fields);
         parse(ctx, ')');
      }

      parseKeyword(ctx, "AS");
      Select<?> select = parseSelect(ctx);
      if (fields.length > 0 && fields.length != select.getSelect().size()) {
         throw ctx.exception();
      } else {
         return ifNotExists ? ctx.dsl.createViewIfNotExists(view, fields).as(select) : ctx.dsl.createView(view, fields).as(select);
      }
   }

   private static final DDLQuery parseAlterView(ParserImpl.ParserContext ctx) {
      boolean ifExists = parseKeywordIf(ctx, "IF EXISTS");
      Table<?> oldName = parseTableName(ctx);
      parseKeyword(ctx, "RENAME TO");
      Table<?> newName = parseTableName(ctx);
      return ifExists ? ctx.dsl.alterViewIfExists(oldName).renameTo(newName) : ctx.dsl.alterView(oldName).renameTo(newName);
   }

   private static final DDLQuery parseDropView(ParserImpl.ParserContext ctx) {
      boolean ifExists = parseKeywordIf(ctx, "IF EXISTS");
      Table<?> tableName = parseTableName(ctx);
      DropViewFinalStep s1 = ifExists ? ctx.dsl.dropViewIfExists(tableName) : ctx.dsl.dropView(tableName);
      return s1;
   }

   private static final DDLQuery parseDropSequence(ParserImpl.ParserContext ctx) {
      boolean ifExists = parseKeywordIf(ctx, "IF EXISTS");
      Sequence<?> sequenceName = parseSequenceName(ctx);
      DropSequenceFinalStep s1 = ifExists ? ctx.dsl.dropSequenceIfExists(sequenceName) : ctx.dsl.dropSequence(sequenceName);
      return s1;
   }

   private static final DDLQuery parseCreateTable(ParserImpl.ParserContext ctx) {
      boolean ifNotExists = parseKeywordIf(ctx, "IF NOT EXISTS");
      Table<?> tableName = parseTableName(ctx);
      if (parseKeywordIf(ctx, "AS")) {
         Select<?> select = parseSelect(ctx);
         CreateTableAsStep<Record> s1 = ifNotExists ? ctx.dsl.createTableIfNotExists(tableName) : ctx.dsl.createTable(tableName);
         CreateTableFinalStep s2 = s1.as(select);
         return s2;
      } else {
         List<Field<?>> fields = new ArrayList();
         List<Constraint> constraints = new ArrayList();
         boolean primary = false;
         boolean noConstraint = true;
         parse(ctx, '(');

         label141:
         do {
            String fieldName = parseIdentifier(ctx);
            DataType<?> type = parseDataType(ctx);
            boolean nullable = false;
            boolean defaultValue = false;
            boolean unique = false;

            while(true) {
               while(true) {
                  if (!nullable) {
                     if (parseKeywordIf(ctx, "NULL")) {
                        type = type.nullable(true);
                        nullable = true;
                        continue;
                     }

                     if (parseKeywordIf(ctx, "NOT NULL")) {
                        type = type.nullable(false);
                        nullable = true;
                        continue;
                     }
                  }

                  if (!defaultValue && parseKeywordIf(ctx, "DEFAULT")) {
                     type = type.defaultValue(parseField(ctx));
                     defaultValue = true;
                  } else {
                     if (!unique) {
                        if (parseKeywordIf(ctx, "PRIMARY KEY")) {
                           constraints.add(DSL.primaryKey(fieldName));
                           primary = true;
                           unique = true;
                           continue;
                        }

                        if (parseKeywordIf(ctx, "UNIQUE")) {
                           constraints.add(DSL.unique(fieldName));
                           unique = true;
                           continue;
                        }
                     }

                     if (!parseKeywordIf(ctx, "CHECK")) {
                        fields.add(DSL.field(DSL.name(fieldName), type));
                        continue label141;
                     }

                     parse(ctx, '(');
                     constraints.add(DSL.check(parseCondition(ctx)));
                     parse(ctx, ')');
                  }
               }
            }
         } while(parseIf(ctx, ',') && (noConstraint = !peekKeyword(ctx, "PRIMARY KEY") && !peekKeyword(ctx, "UNIQUE") && !peekKeyword(ctx, "FOREIGN KEY") && !peekKeyword(ctx, "CHECK") && !peekKeyword(ctx, "CONSTRAINT")));

         if (!noConstraint) {
            do {
               ConstraintTypeStep constraint = null;
               if (parseKeywordIf(ctx, "CONSTRAINT")) {
                  constraint = DSL.constraint(parseIdentifier(ctx));
               }

               Field[] referencing;
               if (parseKeywordIf(ctx, "PRIMARY KEY")) {
                  if (primary) {
                     throw ctx.exception();
                  }

                  primary = true;
                  parse(ctx, '(');
                  referencing = (Field[])parseFieldNames(ctx).toArray(Tools.EMPTY_FIELD);
                  parse(ctx, ')');
                  constraints.add(constraint == null ? DSL.primaryKey(referencing) : constraint.primaryKey(referencing));
               } else if (parseKeywordIf(ctx, "UNIQUE")) {
                  parse(ctx, '(');
                  referencing = (Field[])parseFieldNames(ctx).toArray(Tools.EMPTY_FIELD);
                  parse(ctx, ')');
                  constraints.add(constraint == null ? DSL.unique(referencing) : constraint.unique(referencing));
               } else if (parseKeywordIf(ctx, "FOREIGN KEY")) {
                  parse(ctx, '(');
                  referencing = (Field[])parseFieldNames(ctx).toArray(Tools.EMPTY_FIELD);
                  parse(ctx, ')');
                  parseKeyword(ctx, "REFERENCES");
                  Table<?> referencedTable = parseTableName(ctx);
                  parse(ctx, '(');
                  Field<?>[] referencedFields = (Field[])parseFieldNames(ctx).toArray(Tools.EMPTY_FIELD);
                  parse(ctx, ')');
                  if (referencing.length != referencedFields.length) {
                     throw ctx.exception();
                  }

                  constraints.add(constraint == null ? DSL.foreignKey(referencing).references(referencedTable, referencedFields) : constraint.foreignKey(referencing).references(referencedTable, referencedFields));
               } else {
                  if (!parseKeywordIf(ctx, "CHECK")) {
                     throw ctx.unexpectedToken();
                  }

                  parse(ctx, '(');
                  Condition condition = parseCondition(ctx);
                  parse(ctx, ')');
                  constraints.add(constraint == null ? DSL.check(condition) : constraint.check(condition));
               }
            } while(parseIf(ctx, ','));
         }

         parse(ctx, ')');
         CreateTableAsStep<Record> s1 = ifNotExists ? ctx.dsl.createTableIfNotExists(tableName) : ctx.dsl.createTable(tableName);
         CreateTableColumnStep s2 = s1.columns((Collection)fields);
         CreateTableConstraintStep s3 = constraints.isEmpty() ? s2 : s2.constraints(constraints);
         return (DDLQuery)s3;
      }
   }

   private static final DDLQuery parseAlterTable(ParserImpl.ParserContext ctx) {
      boolean ifExists = parseKeywordIf(ctx, "IF EXISTS");
      Table<?> tableName = parseTableName(ctx);
      parseWhitespaceIf(ctx);
      AlterTableStep s1 = ifExists ? ctx.dsl.alterTableIfExists(tableName) : ctx.dsl.alterTable(tableName);
      String oldName;
      String fieldName;
      switch(ctx.character()) {
      case 'A':
      case 'a':
         if (parseKeywordIf(ctx, "ADD")) {
            ConstraintTypeStep constraint = null;
            if (parseKeywordIf(ctx, "CONSTRAINT")) {
               constraint = DSL.constraint(parseIdentifier(ctx));
            }

            Field[] referencing;
            if (parseKeywordIf(ctx, "PRIMARY KEY")) {
               parse(ctx, '(');
               referencing = (Field[])parseFieldNames(ctx).toArray(Tools.EMPTY_FIELD);
               parse(ctx, ')');
               return constraint == null ? s1.add(DSL.primaryKey(referencing)) : s1.add(constraint.primaryKey(referencing));
            }

            if (parseKeywordIf(ctx, "UNIQUE")) {
               parse(ctx, '(');
               referencing = (Field[])parseFieldNames(ctx).toArray(Tools.EMPTY_FIELD);
               parse(ctx, ')');
               return constraint == null ? s1.add(DSL.unique(referencing)) : s1.add(constraint.unique(referencing));
            }

            if (parseKeywordIf(ctx, "FOREIGN KEY")) {
               parse(ctx, '(');
               referencing = (Field[])parseFieldNames(ctx).toArray(Tools.EMPTY_FIELD);
               parse(ctx, ')');
               parseKeyword(ctx, "REFERENCES");
               Table<?> referencedTable = parseTableName(ctx);
               parse(ctx, '(');
               Field<?>[] referencedFields = (Field[])parseFieldNames(ctx).toArray(Tools.EMPTY_FIELD);
               parse(ctx, ')');
               if (referencing.length != referencedFields.length) {
                  throw ctx.exception();
               }

               return constraint == null ? s1.add(DSL.foreignKey(referencing).references(referencedTable, referencedFields)) : s1.add(constraint.foreignKey(referencing).references(referencedTable, referencedFields));
            }

            if (parseKeywordIf(ctx, "CHECK")) {
               parse(ctx, '(');
               Condition condition = parseCondition(ctx);
               parse(ctx, ')');
               return constraint == null ? s1.add(DSL.check(condition)) : s1.add(constraint.check(condition));
            }

            if (constraint != null) {
               throw ctx.unexpectedToken();
            }

            parseKeywordIf(ctx, "COLUMN");
            fieldName = parseIdentifier(ctx);
            DataType type = parseDataType(ctx);
            boolean nullable = false;
            boolean defaultValue = false;
            boolean unique = false;

            while(true) {
               while(!nullable) {
                  if (parseKeywordIf(ctx, "NULL")) {
                     type = type.nullable(true);
                     nullable = true;
                  } else {
                     if (!parseKeywordIf(ctx, "NOT NULL")) {
                        break;
                     }

                     type = type.nullable(false);
                     nullable = true;
                  }
               }

               if (defaultValue || !parseKeywordIf(ctx, "DEFAULT")) {
                  if (!unique) {
                     if (parseKeywordIf(ctx, "PRIMARY KEY")) {
                        throw ctx.unexpectedToken();
                     }

                     if (parseKeywordIf(ctx, "UNIQUE")) {
                        throw ctx.unexpectedToken();
                     }
                  }

                  if (parseKeywordIf(ctx, "CHECK")) {
                     throw ctx.unexpectedToken();
                  }

                  return s1.add(DSL.field(DSL.name(fieldName), type), type);
               }

               type = type.defaultValue(parseField(ctx));
               defaultValue = true;
            }
         }
         break;
      case 'D':
      case 'd':
         if (parseKeywordIf(ctx, "DROP")) {
            if (parseKeywordIf(ctx, "COLUMN")) {
               Field<?> field = parseFieldName(ctx);
               boolean cascade = parseKeywordIf(ctx, "CASCADE");
               boolean restrict = !cascade && parseKeywordIf(ctx, "RESTRICT");
               AlterTableDropStep s2 = s1.dropColumn((Field)field);
               AlterTableFinalStep s3 = cascade ? s2.cascade() : (restrict ? s2.restrict() : s2);
               return (DDLQuery)s3;
            }

            if (parseKeywordIf(ctx, "CONSTRAINT")) {
               oldName = parseIdentifier(ctx);
               return s1.dropConstraint(oldName);
            }
         }
         break;
      case 'R':
      case 'r':
         if (parseKeywordIf(ctx, "RENAME")) {
            if (parseKeywordIf(ctx, "TO")) {
               oldName = parseIdentifier(ctx);
               return s1.renameTo(oldName);
            }

            if (parseKeywordIf(ctx, "COLUMN")) {
               oldName = parseIdentifier(ctx);
               parseKeyword(ctx, "TO");
               fieldName = parseIdentifier(ctx);
               return s1.renameColumn(oldName).to(fieldName);
            }

            if (parseKeywordIf(ctx, "CONSTRAINT")) {
               oldName = parseIdentifier(ctx);
               parseKeyword(ctx, "TO");
               fieldName = parseIdentifier(ctx);
               return s1.renameConstraint(oldName).to(fieldName);
            }
         }
      }

      throw ctx.unexpectedToken();
   }

   private static final DDLQuery parseRename(ParserImpl.ParserContext ctx) {
      parseKeyword(ctx, "RENAME");
      parseWhitespaceIf(ctx);
      Table oldName;
      Table newName;
      switch(ctx.character()) {
      case 'C':
      case 'c':
         if (parseKeywordIf(ctx, "COLUMN")) {
            TableField<?, ?> oldName = parseFieldName(ctx);
            parseKeyword(ctx, "TO");
            Field<?> newName = parseFieldName(ctx);
            return ctx.dsl.alterTable(oldName.getTable().getName()).renameColumn((Field)oldName).to((Field)newName);
         }
         break;
      case 'I':
      case 'i':
         if (parseKeywordIf(ctx, "INDEX")) {
            Name oldName = parseIndexName(ctx);
            parseKeyword(ctx, "TO");
            Name newName = parseIndexName(ctx);
            return ctx.dsl.alterIndex(oldName).renameTo(newName);
         }
         break;
      case 'S':
      case 's':
         if (parseKeywordIf(ctx, "SCHEMA")) {
            Schema oldName = parseSchemaName(ctx);
            parseKeyword(ctx, "TO");
            Schema newName = parseSchemaName(ctx);
            return ctx.dsl.alterSchema(oldName).renameTo(newName);
         }

         if (parseKeywordIf(ctx, "SEQUENCE")) {
            Sequence<?> oldName = parseSequenceName(ctx);
            parseKeyword(ctx, "TO");
            Sequence<?> newName = parseSequenceName(ctx);
            return ctx.dsl.alterSequence(oldName).renameTo(newName);
         }
         break;
      case 'V':
      case 'v':
         if (parseKeywordIf(ctx, "VIEW")) {
            oldName = parseTableName(ctx);
            parseKeyword(ctx, "TO");
            newName = parseTableName(ctx);
            return ctx.dsl.alterView(oldName).renameTo(newName);
         }
      }

      parseKeywordIf(ctx, "TABLE");
      oldName = parseTableName(ctx);
      parseKeyword(ctx, "TO");
      newName = parseTableName(ctx);
      return ctx.dsl.alterTable(oldName).renameTo(newName);
   }

   private static final DDLQuery parseDropTable(ParserImpl.ParserContext ctx) {
      boolean ifExists = parseKeywordIf(ctx, "IF EXISTS");
      Table<?> tableName = parseTableName(ctx);
      boolean cascade = parseKeywordIf(ctx, "CASCADE");
      boolean restrict = !cascade && parseKeywordIf(ctx, "RESTRICT");
      DropTableStep s1 = ifExists ? ctx.dsl.dropTableIfExists(tableName) : ctx.dsl.dropTable(tableName);
      DropTableFinalStep s2 = cascade ? s1.cascade() : (restrict ? s1.restrict() : s1);
      return (DDLQuery)s2;
   }

   private static final DDLQuery parseCreateSchema(ParserImpl.ParserContext ctx) {
      boolean ifNotExists = parseKeywordIf(ctx, "IF NOT EXISTS");
      Schema schemaName = parseSchemaName(ctx);
      return ifNotExists ? ctx.dsl.createSchemaIfNotExists(schemaName) : ctx.dsl.createSchema(schemaName);
   }

   private static final DDLQuery parseAlterSchema(ParserImpl.ParserContext ctx) {
      boolean ifExists = parseKeywordIf(ctx, "IF EXISTS");
      Schema schemaName = parseSchemaName(ctx);
      parseKeyword(ctx, "RENAME TO");
      Schema newName = parseSchemaName(ctx);
      AlterSchemaStep s1 = ifExists ? ctx.dsl.alterSchemaIfExists(schemaName) : ctx.dsl.alterSchema(schemaName);
      AlterSchemaFinalStep s2 = s1.renameTo(newName);
      return s2;
   }

   private static final DDLQuery parseDropSchema(ParserImpl.ParserContext ctx) {
      boolean ifExists = parseKeywordIf(ctx, "IF EXISTS");
      Schema schemaName = parseSchemaName(ctx);
      boolean cascade = parseKeywordIf(ctx, "CASCADE");
      boolean restrict = !cascade && parseKeywordIf(ctx, "RESTRICT");
      DropSchemaStep s1 = ifExists ? ctx.dsl.dropSchemaIfExists(schemaName) : ctx.dsl.dropSchema(schemaName);
      DropSchemaFinalStep s2 = cascade ? s1.cascade() : (restrict ? s1.restrict() : s1);
      return (DDLQuery)s2;
   }

   private static final DDLQuery parseCreateIndex(ParserImpl.ParserContext ctx) {
      boolean ifNotExists = parseKeywordIf(ctx, "IF NOT EXISTS");
      Name indexName = parseIndexName(ctx);
      parseKeyword(ctx, "ON");
      Table<?> tableName = parseTableName(ctx);
      parse(ctx, '(');
      Field<?>[] fieldNames = Tools.fieldsByName((Collection)parseIdentifiers(ctx));
      parse(ctx, ')');
      Condition condition = parseKeywordIf(ctx, "WHERE") ? parseCondition(ctx) : null;
      CreateIndexStep s1 = ifNotExists ? ctx.dsl.createIndexIfNotExists(indexName) : ctx.dsl.createIndex(indexName);
      CreateIndexWhereStep s2 = s1.on(tableName, fieldNames);
      CreateIndexFinalStep s3 = condition != null ? s2.where(condition) : s2;
      return (DDLQuery)s3;
   }

   private static final DDLQuery parseAlterIndex(ParserImpl.ParserContext ctx) {
      boolean ifExists = parseKeywordIf(ctx, "IF EXISTS");
      Name indexName = parseIndexName(ctx);
      parseKeyword(ctx, "RENAME TO");
      Name newName = parseIndexName(ctx);
      AlterIndexStep s1 = ifExists ? ctx.dsl.alterIndexIfExists(indexName) : ctx.dsl.alterIndex(indexName);
      AlterIndexFinalStep s2 = s1.renameTo(newName);
      return s2;
   }

   private static final DDLQuery parseDropIndex(ParserImpl.ParserContext ctx) {
      boolean ifExists = parseKeywordIf(ctx, "IF EXISTS");
      Name indexName = parseIndexName(ctx);
      boolean on = parseKeywordIf(ctx, "ON");
      Table<?> onTable = on ? parseTableName(ctx) : null;
      DropIndexOnStep s1 = ifExists ? ctx.dsl.dropIndexIfExists(indexName) : ctx.dsl.dropIndex(indexName);
      DropIndexFinalStep s2 = on ? s1.on(onTable) : s1;
      return (DDLQuery)s2;
   }

   static final Condition parseCondition(ParserImpl.ParserContext ctx) {
      Condition condition;
      for(condition = parseBooleanTerm(ctx); parseKeywordIf(ctx, "OR"); condition = condition.or(parseBooleanTerm(ctx))) {
      }

      return condition;
   }

   private static final Condition parseBooleanTerm(ParserImpl.ParserContext ctx) {
      Condition condition;
      for(condition = parseBooleanFactor(ctx); parseKeywordIf(ctx, "AND"); condition = condition.and(parseBooleanFactor(ctx))) {
      }

      return condition;
   }

   private static final Condition parseBooleanFactor(ParserImpl.ParserContext ctx) {
      boolean not = parseKeywordIf(ctx, "NOT");
      Condition condition = parseBooleanTest(ctx);
      return not ? condition.not() : condition;
   }

   private static final Condition parseBooleanTest(ParserImpl.ParserContext ctx) {
      Condition condition = parseBooleanPrimary(ctx);
      if (parseKeywordIf(ctx, "IS")) {
         Field<Boolean> field = DSL.field(condition);
         boolean not = parseKeywordIf(ctx, "NOT");
         ParserImpl.TruthValue truth = parseTruthValue(ctx);
         switch(truth) {
         case FALSE:
            return not ? field.ne((Field)DSL.inline(false)) : field.eq((Field)DSL.inline(false));
         case TRUE:
            return not ? field.ne((Field)DSL.inline(true)) : field.eq((Field)DSL.inline(true));
         case NULL:
            return not ? field.isNotNull() : field.isNull();
         default:
            throw ctx.internalError();
         }
      } else {
         return condition;
      }
   }

   private static final Condition parseBooleanPrimary(ParserImpl.ParserContext ctx) {
      if (parseIf(ctx, '(')) {
         Condition result = parseCondition(ctx);
         parse(ctx, ')');
         return result;
      } else {
         ParserImpl.TruthValue truth = parseTruthValueIf(ctx);
         if (truth != null) {
            Comparator comp = parseComparatorIf(ctx);
            switch(truth) {
            case FALSE:
               return comp == null ? DSL.condition(false) : DSL.inline(false).compare(comp, parseField(ctx));
            case TRUE:
               return comp == null ? DSL.condition(true) : DSL.inline(true).compare(comp, parseField(ctx));
            case NULL:
               return comp == null ? DSL.condition((Boolean)null) : DSL.inline((Boolean)null).compare(comp, parseField(ctx));
            default:
               throw ctx.exception();
            }
         } else {
            return parsePredicate(ctx);
         }
      }
   }

   private static final Condition parsePredicate(ParserImpl.ParserContext ctx) {
      if (parseKeywordIf(ctx, "EXISTS")) {
         parse(ctx, '(');
         Select<?> select = parseSelect(ctx);
         parse(ctx, ')');
         return DSL.exists(select);
      } else {
         Field left = parseFieldConcat(ctx, (ParserImpl.Type)null);
         boolean not = parseKeywordIf(ctx, "NOT");
         Comparator comp;
         boolean escape;
         boolean symmetric;
         if (!not && (comp = parseComparatorIf(ctx)) != null) {
            symmetric = parseKeywordIf(ctx, "ALL");
            escape = !symmetric && (parseKeywordIf(ctx, "ANY") || parseKeywordIf(ctx, "SOME"));
            if (symmetric || escape) {
               parse(ctx, '(');
            }

            Condition result = symmetric ? left.compare(comp, DSL.all((Select)parseSelect(ctx))) : (escape ? left.compare(comp, DSL.any((Select)parseSelect(ctx))) : left.compare(comp, parseFieldConcat(ctx, (ParserImpl.Type)null)));
            if (symmetric || escape) {
               parse(ctx, ')');
            }

            return result;
         } else {
            Field right;
            if (!not && parseKeywordIf(ctx, "IS")) {
               not = parseKeywordIf(ctx, "NOT");
               if (parseKeywordIf(ctx, "NULL")) {
                  return not ? left.isNotNull() : left.isNull();
               } else {
                  parseKeyword(ctx, "DISTINCT FROM");
                  right = parseFieldConcat(ctx, (ParserImpl.Type)null);
                  return not ? left.isNotDistinctFrom(right) : left.isDistinctFrom(right);
               }
            } else if (parseKeywordIf(ctx, "IN")) {
               parse(ctx, '(');
               Condition result;
               if (peekKeyword(ctx, "SELECT")) {
                  result = not ? left.notIn((Select)parseSelect(ctx)) : left.in((Select)parseSelect(ctx));
               } else {
                  result = not ? left.notIn((Collection)parseFields(ctx)) : left.in((Collection)parseFields(ctx));
               }

               parse(ctx, ')');
               return result;
            } else if (parseKeywordIf(ctx, "BETWEEN")) {
               symmetric = parseKeywordIf(ctx, "SYMMETRIC");
               Field r1 = parseFieldConcat(ctx, (ParserImpl.Type)null);
               parseKeyword(ctx, "AND");
               Field r2 = parseFieldConcat(ctx, (ParserImpl.Type)null);
               return symmetric ? (not ? left.notBetweenSymmetric(r1, r2) : left.betweenSymmetric(r1, r2)) : (not ? left.notBetween(r1, r2) : left.between(r1, r2));
            } else if (parseKeywordIf(ctx, "LIKE")) {
               right = parseFieldConcat(ctx, (ParserImpl.Type)null);
               escape = parseKeywordIf(ctx, "ESCAPE");
               char character = escape ? parseCharacterLiteral(ctx) : 32;
               return (Condition)(escape ? (not ? left.notLike(right, character) : left.like(right, character)) : (not ? left.notLike(right) : left.like(right)));
            } else {
               throw ctx.exception();
            }
         }
      }
   }

   private static final List<Table<?>> parseTables(ParserImpl.ParserContext ctx) {
      parseWhitespaceIf(ctx);
      ArrayList result = new ArrayList();

      do {
         result.add(parseTable(ctx));
      } while(parseIf(ctx, ','));

      return result;
   }

   private static final Table<?> parseTable(ParserImpl.ParserContext ctx) {
      Table result = parseTableFactor(ctx);

      while(true) {
         Table<?> joined = parseJoinedTableIf(ctx, result);
         if (joined == null) {
            return result;
         }

         result = joined;
      }
   }

   private static final Table<?> parseTableFactor(ParserImpl.ParserContext ctx) {
      return parseTablePrimary(ctx);
   }

   private static final Table<?> parseTablePrimary(ParserImpl.ParserContext ctx) {
      Table<?> result = null;
      if (parseKeywordIf(ctx, "LATERAL")) {
         parse(ctx, '(');
         result = DSL.lateral(parseSelect(ctx));
         parse(ctx, ')');
      } else {
         if (parseKeywordIf(ctx, "UNNEST")) {
            throw ctx.exception();
         }

         if (parseIf(ctx, '(')) {
            if (!peekKeyword(ctx, "SELECT")) {
               int var2;
               for(var2 = 0; parseIf(ctx, '('); ++var2) {
               }

               result = parseJoinedTable(ctx);

               while(var2-- > 0) {
                  parse(ctx, ')');
               }

               parse(ctx, ')');
               return result;
            }

            result = DSL.table((Select)parseSelect(ctx));
            parse(ctx, ')');
         } else {
            result = parseTableName(ctx);
         }
      }

      String alias = null;
      List<String> columnAliases = null;
      if (parseKeywordIf(ctx, "AS")) {
         alias = parseIdentifier(ctx);
      } else if (!peekKeyword(ctx, SELECT_KEYWORDS)) {
         alias = parseIdentifierIf(ctx);
      }

      if (alias != null) {
         if (parseIf(ctx, '(')) {
            columnAliases = parseIdentifiers(ctx);
            parse(ctx, ')');
         }

         if (columnAliases != null) {
            result = result.as(alias, (String[])columnAliases.toArray(Tools.EMPTY_STRING));
         } else {
            result = result.as(alias);
         }
      }

      return result;
   }

   private static final Table<?> parseJoinedTable(ParserImpl.ParserContext ctx) {
      Table<?> result = parseTableFactor(ctx);
      int i = 0;

      while(true) {
         Table<?> joined = parseJoinedTableIf(ctx, result);
         if (joined == null) {
            if (i != 0) {
               return result;
            }

            ctx.unexpectedToken();
         } else {
            result = joined;
         }

         ++i;
      }
   }

   private static final Table<?> parseJoinedTableIf(ParserImpl.ParserContext ctx, Table<?> left) {
      JoinType joinType = parseJoinTypeIf(ctx);
      if (joinType == null) {
         return null;
      } else {
         Table<?> right = parseTableFactor(ctx);
         TableOptionalOnStep<?> result1 = left.join((TableLike)right, (JoinType)joinType);
         Table<?> result2 = result1;
         switch(joinType) {
         case JOIN:
         case LEFT_OUTER_JOIN:
         case FULL_OUTER_JOIN:
         case RIGHT_OUTER_JOIN:
         case OUTER_APPLY:
            boolean on = parseKeywordIf(ctx, "ON");
            if (on) {
               result2 = result1.on(new Condition[]{parseCondition(ctx)});
            } else {
               parseKeyword(ctx, "USING");
               parse(ctx, '(');
               result2 = result1.using(Tools.fieldsByName((String[])parseIdentifiers(ctx).toArray(Tools.EMPTY_STRING)));
               parse(ctx, ')');
            }
         default:
            return (Table)result2;
         }
      }
   }

   private static final List<Field<?>> parseSelectList(ParserImpl.ParserContext ctx) {
      parseWhitespaceIf(ctx);
      if (parseIf(ctx, '*')) {
         return Collections.emptyList();
      } else {
         ArrayList result = new ArrayList();

         do {
            Field<?> field = parseField(ctx);
            String alias = null;
            if (parseKeywordIf(ctx, "AS")) {
               alias = parseIdentifier(ctx);
            } else if (!peekKeyword(ctx, SELECT_KEYWORDS)) {
               alias = parseIdentifierIf(ctx);
            }

            result.add(alias == null ? field : field.as(alias));
         } while(parseIf(ctx, ','));

         return result;
      }
   }

   private static final List<SortField<?>> parseSortSpecification(ParserImpl.ParserContext ctx) {
      ArrayList result = new ArrayList();

      do {
         Field<?> field = parseField(ctx);
         SortField sort;
         if (parseKeywordIf(ctx, "DESC")) {
            sort = field.desc();
         } else {
            if (!parseKeywordIf(ctx, "ASC")) {
            }

            sort = field.asc();
         }

         if (parseKeywordIf(ctx, "NULLS FIRST")) {
            sort = sort.nullsFirst();
         } else if (parseKeywordIf(ctx, "NULLS LAST")) {
            sort = sort.nullsLast();
         }

         result.add(sort);
      } while(parseIf(ctx, ','));

      return result;
   }

   private static final List<Field<?>> parseFields(ParserImpl.ParserContext ctx) {
      parseWhitespaceIf(ctx);
      ArrayList result = new ArrayList();

      do {
         result.add(parseField(ctx));
      } while(parseIf(ctx, ','));

      return result;
   }

   static final Field<?> parseField(ParserImpl.ParserContext ctx) {
      return parseField(ctx, (ParserImpl.Type)null);
   }

   private static final Field<?> parseField(ParserImpl.ParserContext ctx, ParserImpl.Type type) {
      if (ParserImpl.Type.B.is(type)) {
         Field<?> r = parseFieldAnd(ctx);

         Condition c;
         for(c = null; parseKeywordIf(ctx, "OR"); c = (c == null ? DSL.condition(r) : c).or(parseFieldAnd(ctx))) {
         }

         return c == null ? r : DSL.field(c);
      } else {
         return parseFieldConcat(ctx, type);
      }
   }

   private static final Field<?> parseFieldConcat(ParserImpl.ParserContext ctx, ParserImpl.Type type) {
      Field<?> r = parseFieldSum(ctx, type);
      if (ParserImpl.Type.S.is(type)) {
         while(parseIf(ctx, "||")) {
            r = DSL.concat(r, parseFieldSum(ctx, type));
         }
      }

      return r;
   }

   private static final Field<?> parseFieldSumParenthesised(ParserImpl.ParserContext ctx) {
      parse(ctx, '(');
      Field<?> r = parseFieldSum(ctx, ParserImpl.Type.N);
      parse(ctx, ')');
      return r;
   }

   private static final Field<?> parseFieldSum(ParserImpl.ParserContext ctx, ParserImpl.Type type) {
      Field<?> r = parseFieldFactor(ctx, type);
      if (ParserImpl.Type.N.is(type)) {
         while(true) {
            while(!parseIf(ctx, '+')) {
               if (!parseIf(ctx, '-')) {
                  return r;
               }

               r = r.sub(parseFieldFactor(ctx, type));
            }

            r = r.add(parseFieldFactor(ctx, type));
         }
      } else {
         return r;
      }
   }

   private static final Field<?> parseFieldFactor(ParserImpl.ParserContext ctx, ParserImpl.Type type) {
      Field<?> r = parseFieldTerm(ctx, type);
      if (ParserImpl.Type.N.is(type)) {
         while(true) {
            while(!parseIf(ctx, '*')) {
               if (parseIf(ctx, '/')) {
                  r = r.div(parseFieldTerm(ctx, type));
               } else {
                  if (!parseIf(ctx, '%')) {
                     return r;
                  }

                  r = r.mod(parseFieldTerm(ctx, type));
               }
            }

            r = r.mul(parseFieldTerm(ctx, type));
         }
      } else {
         return r;
      }
   }

   private static final Field<?> parseFieldAnd(ParserImpl.ParserContext ctx) {
      Field<?> r = parseFieldCondition(ctx);

      Condition c;
      for(c = null; parseKeywordIf(ctx, "AND"); c = (c == null ? DSL.condition(r) : c).and(parseFieldCondition(ctx))) {
      }

      return c == null ? r : DSL.field(c);
   }

   private static final Field<?> parseFieldCondition(ParserImpl.ParserContext ctx) {
      return parseFieldConcat(ctx, (ParserImpl.Type)null);
   }

   private static final Field<?> parseFieldTerm(ParserImpl.ParserContext ctx, ParserImpl.Type type) {
      parseWhitespaceIf(ctx);
      Field field;
      switch(ctx.character()) {
      case '\'':
         return DSL.inline(parseStringLiteral(ctx));
      case '(':
         parse(ctx, '(');
         if (peekKeyword(ctx, "SELECT")) {
            SelectQueryImpl<Record> select = parseSelect(ctx);
            if (select.getSelect().size() > 1) {
               throw ctx.exception();
            }

            field = DSL.field((Select)select);
            parse(ctx, ')');
            return field;
         }

         Field<?> r = parseField(ctx, type);
         parse(ctx, ')');
         return r;
      case ')':
      case '*':
      case ',':
      case '/':
      case ';':
      case '<':
      case '=':
      case '>':
      case '@':
      case 'J':
      case 'K':
      case 'Q':
      case 'V':
      case 'W':
      case 'X':
      case 'Z':
      case '[':
      case '\\':
      case ']':
      case '^':
      case '_':
      case '`':
      case 'j':
      case 'k':
      case 'q':
      case 'v':
      case 'w':
      case 'x':
      default:
         break;
      case '+':
         parse(ctx, '+');
         if (ParserImpl.Type.N.is(type)) {
            return parseFieldTerm(ctx, type);
         }
         break;
      case '-':
         parse(ctx, '-');
         if (ParserImpl.Type.N.is(type)) {
            if ((field = parseFieldUnsignedNumericLiteralIf(ctx, true)) != null) {
               return field;
            }

            return parseFieldTerm(ctx, type).neg();
         }
         break;
      case '.':
      case '0':
      case '1':
      case '2':
      case '3':
      case '4':
      case '5':
      case '6':
      case '7':
      case '8':
      case '9':
         if (ParserImpl.Type.N.is(type) && (field = parseFieldUnsignedNumericLiteralIf(ctx, false)) != null) {
            return field;
         }
         break;
      case ':':
      case '?':
         return parseBindVariable(ctx);
      case 'A':
      case 'a':
         if (ParserImpl.Type.N.is(type)) {
            if ((field = parseFieldAsciiIf(ctx)) != null) {
               return field;
            }

            if (parseKeywordIf(ctx, "ACOS")) {
               return DSL.acos(parseFieldSumParenthesised(ctx));
            }

            if (parseKeywordIf(ctx, "ASIN")) {
               return DSL.asin(parseFieldSumParenthesised(ctx));
            }

            if (parseKeywordIf(ctx, "ATAN")) {
               return DSL.atan(parseFieldSumParenthesised(ctx));
            }

            if ((field = parseFieldAtan2If(ctx)) != null) {
               return field;
            }
         }
         break;
      case 'B':
      case 'b':
         if (ParserImpl.Type.N.is(type) && (field = parseFieldBitLengthIf(ctx)) != null) {
            return field;
         }
         break;
      case 'C':
      case 'c':
         if (ParserImpl.Type.S.is(type)) {
            if ((field = parseFieldConcatIf(ctx)) != null) {
               return field;
            }

            if (parseKeywordIf(ctx, "CURRENT_SCHEMA")) {
               return DSL.currentSchema();
            }

            if (parseKeywordIf(ctx, "CURRENT_USER")) {
               return DSL.currentUser();
            }
         }

         if (ParserImpl.Type.N.is(type)) {
            if ((field = parseFieldCharIndexIf(ctx)) != null) {
               return field;
            }

            if ((field = parseFieldCharLengthIf(ctx)) != null) {
               return field;
            }

            if (parseKeywordIf(ctx, "CEILING") || parseKeywordIf(ctx, "CEIL")) {
               return DSL.ceil(parseFieldSumParenthesised(ctx));
            }

            if (parseKeywordIf(ctx, "COSH")) {
               return DSL.cosh(parseFieldSumParenthesised(ctx));
            }

            if (parseKeywordIf(ctx, "COS")) {
               return DSL.cos(parseFieldSumParenthesised(ctx));
            }

            if (parseKeywordIf(ctx, "COTH")) {
               return DSL.coth(parseFieldSumParenthesised(ctx));
            }

            if (parseKeywordIf(ctx, "COT")) {
               return DSL.cot(parseFieldSumParenthesised(ctx));
            }
         }

         if (ParserImpl.Type.D.is(type)) {
            if (parseKeywordIf(ctx, "CURRENT_TIMESTAMP")) {
               return DSL.currentTimestamp();
            }

            if (parseKeywordIf(ctx, "CURRENT_TIME")) {
               return DSL.currentTime();
            }

            if (parseKeywordIf(ctx, "CURRENT_DATE")) {
               return DSL.currentDate();
            }
         }

         if ((field = parseFieldCaseIf(ctx)) != null) {
            return field;
         }

         if ((field = parseCastIf(ctx)) != null) {
            return field;
         }

         if ((field = parseFieldCoalesceIf(ctx)) != null) {
            return field;
         }

         if ((field = parseFieldCumeDistIf(ctx)) != null) {
            return field;
         }
         break;
      case 'D':
      case 'd':
         if (ParserImpl.Type.D.is(type) && (field = parseFieldDateLiteralIf(ctx)) != null) {
            return field;
         }

         if (ParserImpl.Type.N.is(type)) {
            if ((field = parseFieldDenseRankIf(ctx)) != null) {
               return field;
            }

            if ((field = parseFieldDayIf(ctx)) != null) {
               return field;
            }

            if (parseKeywordIf(ctx, "DEGREE") || parseKeywordIf(ctx, "DEG")) {
               return DSL.deg(parseFieldSumParenthesised(ctx));
            }
         }
         break;
      case 'E':
      case 'e':
         if (ParserImpl.Type.N.is(type)) {
            if ((field = parseFieldExtractIf(ctx)) != null) {
               return field;
            }

            if (parseKeywordIf(ctx, "EXP")) {
               return DSL.exp(parseFieldSumParenthesised(ctx));
            }
         }
         break;
      case 'F':
      case 'f':
         if (ParserImpl.Type.N.is(type) && parseKeywordIf(ctx, "FLOOR")) {
            return DSL.floor(parseFieldSumParenthesised(ctx));
         }

         if ((field = parseFieldFirstValueIf(ctx)) != null) {
            return field;
         }
         break;
      case 'G':
      case 'g':
         if ((field = parseFieldGreatestIf(ctx)) != null) {
            return field;
         }

         if (ParserImpl.Type.N.is(type) && (field = parseFieldGroupingIdIf(ctx)) != null) {
            return field;
         }

         if (ParserImpl.Type.N.is(type) && (field = parseFieldGroupingIf(ctx)) != null) {
            return field;
         }
         break;
      case 'H':
      case 'h':
         if (ParserImpl.Type.N.is(type) && (field = parseFieldHourIf(ctx)) != null) {
            return field;
         }
         break;
      case 'I':
      case 'i':
         if (ParserImpl.Type.N.is(type) && (field = parseFieldInstrIf(ctx)) != null) {
            return field;
         }

         if ((field = parseFieldIfnullIf(ctx)) != null) {
            return field;
         }

         if ((field = parseFieldIsnullIf(ctx)) != null) {
            return field;
         }
         break;
      case 'L':
      case 'l':
         if (ParserImpl.Type.S.is(type)) {
            if ((field = parseFieldLowerIf(ctx)) != null) {
               return field;
            }

            if ((field = parseFieldLpadIf(ctx)) != null) {
               return field;
            }

            if ((field = parseFieldLtrimIf(ctx)) != null) {
               return field;
            }

            if ((field = parseFieldLeftIf(ctx)) != null) {
               return field;
            }
         }

         if (ParserImpl.Type.N.is(type)) {
            if ((field = parseFieldLengthIf(ctx)) != null) {
               return field;
            }

            if (parseKeywordIf(ctx, "LN")) {
               return DSL.ln(parseFieldSumParenthesised(ctx));
            }

            if ((field = parseFieldLogIf(ctx)) != null) {
               return field;
            }

            if (parseKeywordIf(ctx, "LEVEL")) {
               return DSL.level();
            }
         }

         if ((field = parseFieldLeastIf(ctx)) != null) {
            return field;
         }

         if ((field = parseFieldLeadLagIf(ctx)) != null) {
            return field;
         }

         if ((field = parseFieldLastValueIf(ctx)) != null) {
            return field;
         }
         break;
      case 'M':
      case 'm':
         if (ParserImpl.Type.N.is(type)) {
            if ((field = parseFieldModIf(ctx)) != null) {
               return field;
            }

            if ((field = parseFieldMonthIf(ctx)) != null) {
               return field;
            }

            if ((field = parseFieldMinuteIf(ctx)) != null) {
               return field;
            }
         }

         if (ParserImpl.Type.S.is(type)) {
            if ((field = parseFieldMidIf(ctx)) != null) {
               return field;
            }

            if ((field = parseFieldMd5If(ctx)) != null) {
               return field;
            }
         }
         break;
      case 'N':
      case 'n':
         if ((field = parseFieldNvl2If(ctx)) != null) {
            return field;
         }

         if ((field = parseFieldNvlIf(ctx)) != null) {
            return field;
         }

         if ((field = parseFieldNullifIf(ctx)) != null) {
            return field;
         }

         if ((field = parseFieldNtileIf(ctx)) != null) {
            return field;
         }

         if ((field = parseFieldNthValueIf(ctx)) != null) {
            return field;
         }
         break;
      case 'O':
      case 'o':
         if (ParserImpl.Type.N.is(type) && (field = parseFieldOctetLengthIf(ctx)) != null) {
            return field;
         }
         break;
      case 'P':
      case 'p':
         if (ParserImpl.Type.N.is(type)) {
            if ((field = parseFieldPositionIf(ctx)) != null) {
               return field;
            }

            if ((field = parseFieldPercentRankIf(ctx)) != null) {
               return field;
            }

            if ((field = parseFieldPowerIf(ctx)) != null) {
               return field;
            }
         }

         if (parseKeywordIf(ctx, "PRIOR")) {
            return DSL.prior(parseField(ctx));
         }
         break;
      case 'R':
      case 'r':
         if (ParserImpl.Type.S.is(type)) {
            if ((field = parseFieldReplaceIf(ctx)) != null) {
               return field;
            }

            if ((field = parseFieldRepeatIf(ctx)) != null) {
               return field;
            }

            if ((field = parseFieldReverseIf(ctx)) != null) {
               return field;
            }

            if ((field = parseFieldRpadIf(ctx)) != null) {
               return field;
            }

            if ((field = parseFieldRtrimIf(ctx)) != null) {
               return field;
            }

            if ((field = parseFieldRightIf(ctx)) != null) {
               return field;
            }
         }

         if (ParserImpl.Type.N.is(type)) {
            if ((field = parseFieldRowNumberIf(ctx)) != null) {
               return field;
            }

            if ((field = parseFieldRankIf(ctx)) != null) {
               return field;
            }

            if ((field = parseFieldRoundIf(ctx)) != null) {
               return field;
            }

            if (parseKeywordIf(ctx, "ROWNUM")) {
               return DSL.rownum();
            }

            if (parseKeywordIf(ctx, "RADIAN") || parseKeywordIf(ctx, "RAD")) {
               return DSL.rad(parseFieldSumParenthesised(ctx));
            }
         }
         break;
      case 'S':
      case 's':
         if (ParserImpl.Type.S.is(type)) {
            if ((field = parseFieldSubstringIf(ctx)) != null) {
               return field;
            }

            if ((field = parseFieldSpaceIf(ctx)) != null) {
               return field;
            }
         }

         if (!ParserImpl.Type.N.is(type)) {
            break;
         }

         if ((field = parseFieldSecondIf(ctx)) != null) {
            return field;
         }

         if ((field = parseFieldSignIf(ctx)) != null) {
            return field;
         }

         if (!parseKeywordIf(ctx, "SQRT") && !parseKeywordIf(ctx, "SQR")) {
            if (parseKeywordIf(ctx, "SINH")) {
               return DSL.sinh(parseFieldSumParenthesised(ctx));
            }

            if (parseKeywordIf(ctx, "SIN")) {
               return DSL.sin(parseFieldSumParenthesised(ctx));
            }
            break;
         }

         return DSL.sqrt(parseFieldSumParenthesised(ctx));
      case 'T':
      case 't':
         if (ParserImpl.Type.S.is(type) && (field = parseFieldTrimIf(ctx)) != null) {
            return field;
         }

         if (ParserImpl.Type.N.is(type)) {
            if ((field = parseFieldTruncIf(ctx)) != null) {
               return field;
            }

            if (parseKeywordIf(ctx, "TANH")) {
               return DSL.tanh(parseFieldSumParenthesised(ctx));
            }

            if (parseKeywordIf(ctx, "TAN")) {
               return DSL.tan(parseFieldSumParenthesised(ctx));
            }
         }

         if (ParserImpl.Type.D.is(type)) {
            if ((field = parseFieldTimestampLiteralIf(ctx)) != null) {
               return field;
            }

            if ((field = parseFieldTimeLiteralIf(ctx)) != null) {
               return field;
            }
         }
         break;
      case 'U':
      case 'u':
         if (ParserImpl.Type.S.is(type) && (field = parseFieldUpperIf(ctx)) != null) {
            return field;
         }
         break;
      case 'Y':
      case 'y':
         if (ParserImpl.Type.N.is(type) && (field = parseFieldYearIf(ctx)) != null) {
            return field;
         }
      }

      if ((field = parseAggregateFunctionIf(ctx)) != null) {
         return field;
      } else if ((field = parseBooleanValueExpressionIf(ctx)) != null) {
         return field;
      } else {
         return parseFieldName(ctx);
      }
   }

   private static final Field<?> parseFieldAtan2If(ParserImpl.ParserContext ctx) {
      if (!parseKeywordIf(ctx, "ATN2") && !parseKeywordIf(ctx, "ATAN2")) {
         return null;
      } else {
         parse(ctx, '(');
         Field<?> x = parseFieldSum(ctx, ParserImpl.Type.N);
         parse(ctx, ',');
         Field<?> y = parseFieldSum(ctx, ParserImpl.Type.N);
         parse(ctx, ')');
         return DSL.atan2(x, y);
      }
   }

   private static final Field<?> parseFieldLogIf(ParserImpl.ParserContext ctx) {
      if (parseKeywordIf(ctx, "LOG")) {
         parse(ctx, '(');
         Field<?> arg1 = parseFieldSum(ctx, ParserImpl.Type.N);
         parse(ctx, ',');
         long arg2 = parseUnsignedInteger(ctx);
         parse(ctx, ')');
         return DSL.log(arg1, (int)arg2);
      } else {
         return null;
      }
   }

   private static final Field<?> parseFieldTruncIf(ParserImpl.ParserContext ctx) {
      if (parseKeywordIf(ctx, "TRUNC")) {
         parse(ctx, '(');
         Field<?> arg1 = parseFieldSum(ctx, ParserImpl.Type.N);
         parse(ctx, ',');
         Field<?> arg2 = parseFieldSum(ctx, ParserImpl.Type.N);
         parse(ctx, ')');
         return DSL.trunc(arg1, arg2);
      } else {
         return null;
      }
   }

   private static final Field<?> parseFieldRoundIf(ParserImpl.ParserContext ctx) {
      if (parseKeywordIf(ctx, "ROUND")) {
         Field<?> arg1 = null;
         Integer arg2 = null;
         parse(ctx, '(');
         arg1 = parseFieldSum(ctx, ParserImpl.Type.N);
         if (parseIf(ctx, ',')) {
            arg2 = (int)parseUnsignedInteger(ctx);
         }

         parse(ctx, ')');
         return arg2 == null ? DSL.round(arg1) : DSL.round(arg1, arg2);
      } else {
         return null;
      }
   }

   private static final Field<?> parseFieldPowerIf(ParserImpl.ParserContext ctx) {
      if (!parseKeywordIf(ctx, "POWER") && !parseKeywordIf(ctx, "POW")) {
         return null;
      } else {
         parse(ctx, '(');
         Field<?> arg1 = parseFieldSum(ctx, ParserImpl.Type.N);
         parse(ctx, ',');
         Field<?> arg2 = parseFieldSum(ctx, ParserImpl.Type.N);
         parse(ctx, ')');
         return DSL.power(arg1, arg2);
      }
   }

   private static final Field<?> parseFieldModIf(ParserImpl.ParserContext ctx) {
      if (parseKeywordIf(ctx, "MOD")) {
         parse(ctx, '(');
         Field<?> f1 = parseField(ctx, ParserImpl.Type.N);
         parse(ctx, ',');
         Field<?> f2 = parseField(ctx, ParserImpl.Type.N);
         parse(ctx, ')');
         return f1.mod(f2);
      } else {
         return null;
      }
   }

   private static Field<?> parseFieldLeastIf(ParserImpl.ParserContext ctx) {
      if (parseKeywordIf(ctx, "LEAST")) {
         parse(ctx, '(');
         List<Field<?>> fields = parseFields(ctx);
         parse(ctx, ')');
         return DSL.least((Field)fields.get(0), fields.size() > 1 ? (Field[])fields.subList(1, fields.size()).toArray(Tools.EMPTY_FIELD) : Tools.EMPTY_FIELD);
      } else {
         return null;
      }
   }

   private static Field<?> parseFieldGreatestIf(ParserImpl.ParserContext ctx) {
      if (parseKeywordIf(ctx, "GREATEST")) {
         parse(ctx, '(');
         List<Field<?>> fields = parseFields(ctx);
         parse(ctx, ')');
         return DSL.greatest((Field)fields.get(0), fields.size() > 1 ? (Field[])fields.subList(1, fields.size()).toArray(Tools.EMPTY_FIELD) : Tools.EMPTY_FIELD);
      } else {
         return null;
      }
   }

   private static final Field<?> parseFieldGroupingIf(ParserImpl.ParserContext ctx) {
      if (parseKeywordIf(ctx, "GROUPING")) {
         parse(ctx, '(');
         Field<?> field = parseField(ctx);
         parse(ctx, ')');
         return DSL.grouping(field);
      } else {
         return null;
      }
   }

   private static final Field<?> parseFieldGroupingIdIf(ParserImpl.ParserContext ctx) {
      if (parseKeywordIf(ctx, "GROUPING_ID")) {
         parse(ctx, '(');
         List<Field<?>> fields = parseFields(ctx);
         parse(ctx, ')');
         return DSL.groupingId((Field[])fields.toArray(Tools.EMPTY_FIELD));
      } else {
         return null;
      }
   }

   private static final Field<?> parseFieldTimestampLiteralIf(ParserImpl.ParserContext ctx) {
      if (parseKeywordIf(ctx, "TIMESTAMP")) {
         if (parseKeywordIf(ctx, "WITHOUT TIME ZONE")) {
            return DSL.inline(parseTimestampLiteral(ctx));
         } else if (parseIf(ctx, '(')) {
            Field<?> f = parseField(ctx, ParserImpl.Type.S);
            parse(ctx, ')');
            return DSL.timestamp(f);
         } else {
            return DSL.inline(parseTimestampLiteral(ctx));
         }
      } else {
         return null;
      }
   }

   private static final Timestamp parseTimestampLiteral(ParserImpl.ParserContext ctx) {
      try {
         return Timestamp.valueOf(parseStringLiteral(ctx));
      } catch (IllegalArgumentException var2) {
         throw ctx.exception();
      }
   }

   private static final Field<?> parseFieldTimeLiteralIf(ParserImpl.ParserContext ctx) {
      if (parseKeywordIf(ctx, "TIME")) {
         if (parseKeywordIf(ctx, "WITHOUT TIME ZONE")) {
            return DSL.inline(parseTimeLiteral(ctx));
         } else if (parseIf(ctx, '(')) {
            Field<?> f = parseField(ctx, ParserImpl.Type.S);
            parse(ctx, ')');
            return DSL.time(f);
         } else {
            return DSL.inline(parseTimeLiteral(ctx));
         }
      } else {
         return null;
      }
   }

   private static final Time parseTimeLiteral(ParserImpl.ParserContext ctx) {
      try {
         return Time.valueOf(parseStringLiteral(ctx));
      } catch (IllegalArgumentException var2) {
         throw ctx.exception();
      }
   }

   private static final Field<?> parseFieldDateLiteralIf(ParserImpl.ParserContext ctx) {
      if (parseKeywordIf(ctx, "DATE")) {
         if (parseIf(ctx, '(')) {
            Field<?> f = parseField(ctx, ParserImpl.Type.S);
            parse(ctx, ')');
            return DSL.date(f);
         } else {
            return DSL.inline(parseDateLiteral(ctx));
         }
      } else {
         return null;
      }
   }

   private static final Date parseDateLiteral(ParserImpl.ParserContext ctx) {
      try {
         return Date.valueOf(parseStringLiteral(ctx));
      } catch (IllegalArgumentException var2) {
         throw ctx.exception();
      }
   }

   private static final Field<?> parseFieldExtractIf(ParserImpl.ParserContext ctx) {
      if (parseKeywordIf(ctx, "EXTRACT")) {
         parse(ctx, '(');
         DatePart part = parseDatePart(ctx);
         parseKeyword(ctx, "FROM");
         Field<?> field = parseField(ctx);
         parse(ctx, ')');
         return DSL.extract(field, part);
      } else {
         return null;
      }
   }

   private static final DatePart parseDatePart(ParserImpl.ParserContext ctx) {
      DatePart[] var1 = DatePart.values();
      int var2 = var1.length;

      for(int var3 = 0; var3 < var2; ++var3) {
         DatePart part = var1[var3];
         if (parseKeywordIf(ctx, part.name())) {
            return part;
         }
      }

      throw ctx.unexpectedToken();
   }

   private static final Field<?> parseFieldAsciiIf(ParserImpl.ParserContext ctx) {
      if (parseKeywordIf(ctx, "ASCII")) {
         parse(ctx, '(');
         Field<?> arg = parseField(ctx, ParserImpl.Type.S);
         parse(ctx, ')');
         return DSL.ascii(arg);
      } else {
         return null;
      }
   }

   private static final Field<?> parseFieldConcatIf(ParserImpl.ParserContext ctx) {
      if (parseKeywordIf(ctx, "CONCAT")) {
         parse(ctx, '(');
         Field<String> result = DSL.concat((Field[])parseFields(ctx).toArray(Tools.EMPTY_FIELD));
         parse(ctx, ')');
         return result;
      } else {
         return null;
      }
   }

   private static final Field<?> parseFieldInstrIf(ParserImpl.ParserContext ctx) {
      if (parseKeywordIf(ctx, "INSTR")) {
         parse(ctx, '(');
         Field<String> f1 = parseField(ctx, ParserImpl.Type.S);
         parse(ctx, ',');
         Field<String> f2 = parseField(ctx, ParserImpl.Type.S);
         parse(ctx, ')');
         return DSL.position(f1, f2);
      } else {
         return null;
      }
   }

   private static final Field<?> parseFieldCharIndexIf(ParserImpl.ParserContext ctx) {
      if (parseKeywordIf(ctx, "CHARINDEX")) {
         parse(ctx, '(');
         Field<String> f1 = parseField(ctx, ParserImpl.Type.S);
         parse(ctx, ',');
         Field<String> f2 = parseField(ctx, ParserImpl.Type.S);
         parse(ctx, ')');
         return DSL.position(f2, f1);
      } else {
         return null;
      }
   }

   private static final Field<?> parseFieldLpadIf(ParserImpl.ParserContext ctx) {
      if (parseKeywordIf(ctx, "LPAD")) {
         parse(ctx, '(');
         Field<String> f1 = parseField(ctx, ParserImpl.Type.S);
         parse(ctx, ',');
         Field<Integer> f2 = parseField(ctx, ParserImpl.Type.N);
         Field<String> f3 = parseIf(ctx, ',') ? parseField(ctx, ParserImpl.Type.S) : null;
         parse(ctx, ')');
         return f3 == null ? DSL.lpad(f1, f2) : DSL.lpad(f1, f2, f3);
      } else {
         return null;
      }
   }

   private static final Field<?> parseFieldRpadIf(ParserImpl.ParserContext ctx) {
      if (parseKeywordIf(ctx, "RPAD")) {
         parse(ctx, '(');
         Field<String> f1 = parseField(ctx, ParserImpl.Type.S);
         parse(ctx, ',');
         Field<Integer> f2 = parseField(ctx, ParserImpl.Type.N);
         Field<String> f3 = parseIf(ctx, ',') ? parseField(ctx, ParserImpl.Type.S) : null;
         parse(ctx, ')');
         return f3 == null ? DSL.rpad(f1, f2) : DSL.rpad(f1, f2, f3);
      } else {
         return null;
      }
   }

   private static final Field<?> parseFieldPositionIf(ParserImpl.ParserContext ctx) {
      if (parseKeywordIf(ctx, "POSITION")) {
         parse(ctx, '(');
         Field<String> f1 = parseField(ctx, ParserImpl.Type.S);
         parseKeyword(ctx, "IN");
         Field<String> f2 = parseField(ctx, ParserImpl.Type.S);
         parse(ctx, ')');
         return DSL.position(f2, f1);
      } else {
         return null;
      }
   }

   private static final Field<?> parseFieldRepeatIf(ParserImpl.ParserContext ctx) {
      if (parseKeywordIf(ctx, "REPEAT")) {
         parse(ctx, '(');
         Field<String> field = parseField(ctx, ParserImpl.Type.S);
         parse(ctx, ',');
         Field<Integer> count = parseField(ctx, ParserImpl.Type.N);
         parse(ctx, ')');
         return DSL.repeat(field, count);
      } else {
         return null;
      }
   }

   private static final Field<?> parseFieldReplaceIf(ParserImpl.ParserContext ctx) {
      if (parseKeywordIf(ctx, "REPLACE")) {
         parse(ctx, '(');
         Field<String> f1 = parseField(ctx, ParserImpl.Type.S);
         parse(ctx, ',');
         Field<String> f2 = parseField(ctx, ParserImpl.Type.S);
         Field<String> f3 = parseIf(ctx, ',') ? parseField(ctx, ParserImpl.Type.S) : null;
         parse(ctx, ')');
         return f3 == null ? DSL.replace(f1, f2) : DSL.replace(f1, f2, f3);
      } else {
         return null;
      }
   }

   private static final Field<?> parseFieldReverseIf(ParserImpl.ParserContext ctx) {
      if (parseKeywordIf(ctx, "REVERSE")) {
         parse(ctx, '(');
         Field<String> f1 = parseField(ctx, ParserImpl.Type.S);
         parse(ctx, ')');
         return DSL.reverse(f1);
      } else {
         return null;
      }
   }

   private static final Field<?> parseFieldSpaceIf(ParserImpl.ParserContext ctx) {
      if (parseKeywordIf(ctx, "SPACE")) {
         parse(ctx, '(');
         Field<Integer> f1 = parseField(ctx, ParserImpl.Type.N);
         parse(ctx, ')');
         return DSL.space(f1);
      } else {
         return null;
      }
   }

   private static final Field<?> parseFieldSubstringIf(ParserImpl.ParserContext ctx) {
      boolean substring = parseKeywordIf(ctx, "SUBSTRING");
      boolean substr = !substring && parseKeywordIf(ctx, "SUBSTR");
      if (!substring && !substr) {
         return null;
      } else {
         boolean keywords = !substr;
         parse(ctx, '(');
         Field<String> f1 = parseFieldConcat(ctx, ParserImpl.Type.S);
         if (substr || !(keywords = parseKeywordIf(ctx, "FROM"))) {
            parse(ctx, ',');
         }

         Field<?> f2 = parseFieldSum(ctx, ParserImpl.Type.N);
         Field<?> f3 = (!keywords || !parseKeywordIf(ctx, "FOR")) && (keywords || !parseIf(ctx, ',')) ? null : parseFieldSum(ctx, ParserImpl.Type.N);
         parse(ctx, ')');
         return f3 == null ? DSL.substring(f1, f2) : DSL.substring(f1, f2, f3);
      }
   }

   private static final Field<?> parseFieldTrimIf(ParserImpl.ParserContext ctx) {
      if (parseKeywordIf(ctx, "TRIM")) {
         parse(ctx, '(');
         Field<String> f1 = parseField(ctx, ParserImpl.Type.S);
         parse(ctx, ')');
         return DSL.trim(f1);
      } else {
         return null;
      }
   }

   private static final Field<?> parseFieldRtrimIf(ParserImpl.ParserContext ctx) {
      if (parseKeywordIf(ctx, "RTRIM")) {
         parse(ctx, '(');
         Field<String> f1 = parseField(ctx, ParserImpl.Type.S);
         parse(ctx, ')');
         return DSL.rtrim(f1);
      } else {
         return null;
      }
   }

   private static final Field<?> parseFieldLtrimIf(ParserImpl.ParserContext ctx) {
      if (parseKeywordIf(ctx, "LTRIM")) {
         parse(ctx, '(');
         Field<String> f1 = parseField(ctx, ParserImpl.Type.S);
         parse(ctx, ')');
         return DSL.ltrim(f1);
      } else {
         return null;
      }
   }

   private static final Field<?> parseFieldMidIf(ParserImpl.ParserContext ctx) {
      if (parseKeywordIf(ctx, "MID")) {
         parse(ctx, '(');
         Field<String> f1 = parseField(ctx, ParserImpl.Type.S);
         parse(ctx, ',');
         Field<? extends Number> f2 = parseField(ctx, ParserImpl.Type.N);
         parse(ctx, ',');
         Field<? extends Number> f3 = parseField(ctx, ParserImpl.Type.N);
         parse(ctx, ')');
         return DSL.mid(f1, f2, f3);
      } else {
         return null;
      }
   }

   private static final Field<?> parseFieldLeftIf(ParserImpl.ParserContext ctx) {
      if (parseKeywordIf(ctx, "LEFT")) {
         parse(ctx, '(');
         Field<String> f1 = parseField(ctx, ParserImpl.Type.S);
         parse(ctx, ',');
         Field<? extends Number> f2 = parseField(ctx, ParserImpl.Type.N);
         parse(ctx, ')');
         return DSL.left(f1, f2);
      } else {
         return null;
      }
   }

   private static final Field<?> parseFieldRightIf(ParserImpl.ParserContext ctx) {
      if (parseKeywordIf(ctx, "RIGHT")) {
         parse(ctx, '(');
         Field<String> f1 = parseField(ctx, ParserImpl.Type.S);
         parse(ctx, ',');
         Field<? extends Number> f2 = parseField(ctx, ParserImpl.Type.N);
         parse(ctx, ')');
         return DSL.right(f1, f2);
      } else {
         return null;
      }
   }

   private static final Field<?> parseFieldMd5If(ParserImpl.ParserContext ctx) {
      if (parseKeywordIf(ctx, "MD5")) {
         parse(ctx, '(');
         Field<String> f1 = parseField(ctx, ParserImpl.Type.S);
         parse(ctx, ')');
         return DSL.md5(f1);
      } else {
         return null;
      }
   }

   private static final Field<?> parseFieldLengthIf(ParserImpl.ParserContext ctx) {
      if (parseKeywordIf(ctx, "LENGTH")) {
         parse(ctx, '(');
         Field<String> f1 = parseField(ctx, ParserImpl.Type.S);
         parse(ctx, ')');
         return DSL.length(f1);
      } else {
         return null;
      }
   }

   private static final Field<?> parseFieldCharLengthIf(ParserImpl.ParserContext ctx) {
      if (parseKeywordIf(ctx, "CHAR_LENGTH")) {
         parse(ctx, '(');
         Field<String> f1 = parseField(ctx, ParserImpl.Type.S);
         parse(ctx, ')');
         return DSL.charLength(f1);
      } else {
         return null;
      }
   }

   private static final Field<?> parseFieldBitLengthIf(ParserImpl.ParserContext ctx) {
      if (parseKeywordIf(ctx, "BIT_LENGTH")) {
         parse(ctx, '(');
         Field<String> f1 = parseField(ctx, ParserImpl.Type.S);
         parse(ctx, ')');
         return DSL.bitLength(f1);
      } else {
         return null;
      }
   }

   private static final Field<?> parseFieldOctetLengthIf(ParserImpl.ParserContext ctx) {
      if (parseKeywordIf(ctx, "OCTET_LENGTH")) {
         parse(ctx, '(');
         Field<String> f1 = parseField(ctx, ParserImpl.Type.S);
         parse(ctx, ')');
         return DSL.octetLength(f1);
      } else {
         return null;
      }
   }

   private static final Field<?> parseFieldLowerIf(ParserImpl.ParserContext ctx) {
      if (parseKeywordIf(ctx, "LOWER")) {
         parse(ctx, '(');
         Field<String> f1 = parseField(ctx, ParserImpl.Type.S);
         parse(ctx, ')');
         return DSL.lower(f1);
      } else {
         return null;
      }
   }

   private static final Field<?> parseFieldUpperIf(ParserImpl.ParserContext ctx) {
      if (parseKeywordIf(ctx, "UPPER")) {
         parse(ctx, '(');
         Field<String> f1 = parseField(ctx, ParserImpl.Type.S);
         parse(ctx, ')');
         return DSL.upper(f1);
      } else {
         return null;
      }
   }

   private static final Field<?> parseFieldYearIf(ParserImpl.ParserContext ctx) {
      if (parseKeywordIf(ctx, "YEAR")) {
         parse(ctx, '(');
         Field<Timestamp> f1 = parseField(ctx, ParserImpl.Type.D);
         parse(ctx, ')');
         return DSL.year(f1);
      } else {
         return null;
      }
   }

   private static final Field<?> parseFieldMonthIf(ParserImpl.ParserContext ctx) {
      if (parseKeywordIf(ctx, "MONTH")) {
         parse(ctx, '(');
         Field<Timestamp> f1 = parseField(ctx, ParserImpl.Type.D);
         parse(ctx, ')');
         return DSL.month(f1);
      } else {
         return null;
      }
   }

   private static final Field<?> parseFieldDayIf(ParserImpl.ParserContext ctx) {
      if (parseKeywordIf(ctx, "DAY")) {
         parse(ctx, '(');
         Field<Timestamp> f1 = parseField(ctx, ParserImpl.Type.D);
         parse(ctx, ')');
         return DSL.day(f1);
      } else {
         return null;
      }
   }

   private static final Field<?> parseFieldHourIf(ParserImpl.ParserContext ctx) {
      if (parseKeywordIf(ctx, "HOUR")) {
         parse(ctx, '(');
         Field<Timestamp> f1 = parseField(ctx, ParserImpl.Type.D);
         parse(ctx, ')');
         return DSL.hour(f1);
      } else {
         return null;
      }
   }

   private static final Field<?> parseFieldMinuteIf(ParserImpl.ParserContext ctx) {
      if (parseKeywordIf(ctx, "MINUTE")) {
         parse(ctx, '(');
         Field<Timestamp> f1 = parseField(ctx, ParserImpl.Type.D);
         parse(ctx, ')');
         return DSL.minute(f1);
      } else {
         return null;
      }
   }

   private static final Field<?> parseFieldSecondIf(ParserImpl.ParserContext ctx) {
      if (parseKeywordIf(ctx, "SECOND")) {
         parse(ctx, '(');
         Field<Timestamp> f1 = parseField(ctx, ParserImpl.Type.D);
         parse(ctx, ')');
         return DSL.second(f1);
      } else {
         return null;
      }
   }

   private static final Field<?> parseFieldSignIf(ParserImpl.ParserContext ctx) {
      if (parseKeywordIf(ctx, "SIGN")) {
         parse(ctx, '(');
         Field<?> f1 = parseField(ctx, ParserImpl.Type.N);
         parse(ctx, ')');
         return DSL.sign(f1);
      } else {
         return null;
      }
   }

   private static final Field<?> parseFieldIfnullIf(ParserImpl.ParserContext ctx) {
      if (parseKeywordIf(ctx, "IFNULL")) {
         parse(ctx, '(');
         Field<?> f1 = parseField(ctx);
         parse(ctx, ',');
         Field<?> f2 = parseField(ctx);
         parse(ctx, ')');
         return DSL.ifnull((Object)f1, (Object)f2);
      } else {
         return null;
      }
   }

   private static final Field<?> parseFieldIsnullIf(ParserImpl.ParserContext ctx) {
      if (parseKeywordIf(ctx, "ISNULL")) {
         parse(ctx, '(');
         Field<?> f1 = parseField(ctx);
         parse(ctx, ',');
         Field<?> f2 = parseField(ctx);
         parse(ctx, ')');
         return DSL.isnull((Object)f1, (Object)f2);
      } else {
         return null;
      }
   }

   private static final Field<?> parseFieldNvlIf(ParserImpl.ParserContext ctx) {
      if (parseKeywordIf(ctx, "NVL")) {
         parse(ctx, '(');
         Field<?> f1 = parseField(ctx);
         parse(ctx, ',');
         Field<?> f2 = parseField(ctx);
         parse(ctx, ')');
         return DSL.nvl((Object)f1, (Object)f2);
      } else {
         return null;
      }
   }

   private static final Field<?> parseFieldNvl2If(ParserImpl.ParserContext ctx) {
      if (parseKeywordIf(ctx, "NVL2")) {
         parse(ctx, '(');
         Field<?> f1 = parseField(ctx);
         parse(ctx, ',');
         Field<?> f2 = parseField(ctx);
         parse(ctx, ',');
         Field<?> f3 = parseField(ctx);
         parse(ctx, ')');
         return DSL.nvl2(f1, (Object)f2, (Object)f3);
      } else {
         return null;
      }
   }

   private static final Field<?> parseFieldNullifIf(ParserImpl.ParserContext ctx) {
      if (parseKeywordIf(ctx, "NULLIF")) {
         parse(ctx, '(');
         Field<?> f1 = parseField(ctx);
         parse(ctx, ',');
         Field<?> f2 = parseField(ctx);
         parse(ctx, ')');
         return DSL.nullif((Object)f1, (Object)f2);
      } else {
         return null;
      }
   }

   private static final Field<?> parseFieldCoalesceIf(ParserImpl.ParserContext ctx) {
      if (parseKeywordIf(ctx, "COALESCE")) {
         parse(ctx, '(');
         List<Field<?>> fields = parseFields(ctx);
         parse(ctx, ')');
         Field[] a = Tools.EMPTY_FIELD;
         return DSL.coalesce((Field)fields.get(0), fields.size() == 1 ? a : (Field[])fields.subList(1, fields.size()).toArray(a));
      } else {
         return null;
      }
   }

   private static final Field<?> parseFieldCaseIf(ParserImpl.ParserContext ctx) {
      if (parseKeywordIf(ctx, "CASE")) {
         Field when;
         if (parseKeywordIf(ctx, "WHEN")) {
            CaseConditionStep step = null;

            do {
               Condition condition = parseCondition(ctx);
               parseKeyword(ctx, "THEN");
               when = parseField(ctx);
               step = step == null ? DSL.when(condition, when) : step.when(condition, when);
            } while(parseKeywordIf(ctx, "WHEN"));

            Object result;
            if (parseKeywordIf(ctx, "ELSE")) {
               result = step.otherwise(parseField(ctx));
            } else {
               result = step;
            }

            parseKeyword(ctx, "END");
            return (Field)result;
         } else {
            CaseValueStep init = DSL.choose(parseField(ctx));
            CaseWhenStep step = null;
            parseKeyword(ctx, "WHEN");

            do {
               when = parseField(ctx);
               parseKeyword(ctx, "THEN");
               Field then = parseField(ctx);
               step = step == null ? init.when(when, then) : step.when(when, then);
            } while(parseKeywordIf(ctx, "WHEN"));

            Object result;
            if (parseKeywordIf(ctx, "ELSE")) {
               result = step.otherwise(parseField(ctx));
            } else {
               result = step;
            }

            parseKeyword(ctx, "END");
            return (Field)result;
         }
      } else {
         return null;
      }
   }

   private static final Field<?> parseCastIf(ParserImpl.ParserContext ctx) {
      if (parseKeywordIf(ctx, "CAST")) {
         parse(ctx, '(');
         Field<?> field = parseField(ctx);
         parseKeyword(ctx, "AS");
         DataType<?> type = parseDataType(ctx);
         return DSL.cast(field, type);
      } else {
         return null;
      }
   }

   private static final Field<Boolean> parseBooleanValueExpressionIf(ParserImpl.ParserContext ctx) {
      ParserImpl.TruthValue truth = parseTruthValueIf(ctx);
      if (truth != null) {
         switch(truth) {
         case FALSE:
            return DSL.inline(false);
         case TRUE:
            return DSL.inline(true);
         case NULL:
            return DSL.inline((Boolean)null);
         default:
            throw ctx.exception();
         }
      } else {
         return null;
      }
   }

   private static final Field<?> parseAggregateFunctionIf(ParserImpl.ParserContext ctx) {
      AggregateFunction<?> agg = parseCountIf(ctx);
      if (agg == null) {
         agg = parseGeneralSetFunctionIf(ctx);
      }

      if (agg == null) {
         agg = parseBinarySetFunctionIf(ctx);
      }

      if (agg == null) {
         return null;
      } else {
         Object over;
         Object result;
         if (parseKeywordIf(ctx, "FILTER")) {
            parse(ctx, '(');
            parseKeyword(ctx, "WHERE");
            Condition filter = parseCondition(ctx);
            parse(ctx, ')');
            result = over = agg.filterWhere(new Condition[]{filter});
         } else {
            over = agg;
            result = agg;
         }

         if (parseKeywordIf(ctx, "OVER")) {
            Object nameOrSpecification = parseWindowNameOrSpecification(ctx);
            if (nameOrSpecification instanceof Name) {
               result = ((WindowBeforeOverStep)over).over((Name)nameOrSpecification);
            } else if (nameOrSpecification instanceof WindowSpecification) {
               result = ((WindowBeforeOverStep)over).over((WindowSpecification)nameOrSpecification);
            } else {
               result = ((WindowBeforeOverStep)over).over();
            }
         }

         return (Field)result;
      }
   }

   private static Object parseWindowNameOrSpecification(ParserImpl.ParserContext ctx) {
      Object result;
      if (parseIf(ctx, '(')) {
         WindowSpecificationOrderByStep s1 = null;
         WindowSpecificationRowsStep s2 = null;
         WindowSpecificationRowsAndStep s3 = null;
         s1 = parseKeywordIf(ctx, "PARTITION BY") ? DSL.partitionBy((Collection)parseFields(ctx)) : null;
         s2 = parseKeywordIf(ctx, "ORDER BY") ? (s1 == null ? DSL.orderBy((Collection)parseSortSpecification(ctx)) : s1.orderBy((Collection)parseSortSpecification(ctx))) : s1;
         boolean rows = parseKeywordIf(ctx, "ROWS");
         if (!rows && !parseKeywordIf(ctx, "RANGE")) {
            result = s2;
         } else {
            int number;
            if (parseKeywordIf(ctx, "BETWEEN")) {
               if (parseKeywordIf(ctx, "UNBOUNDED")) {
                  if (parseKeywordIf(ctx, "PRECEDING")) {
                     s3 = s2 == null ? (rows ? DSL.rowsBetweenUnboundedPreceding() : DSL.rangeBetweenUnboundedPreceding()) : (rows ? ((WindowSpecificationRowsStep)s2).rowsBetweenUnboundedPreceding() : ((WindowSpecificationRowsStep)s2).rangeBetweenUnboundedPreceding());
                  } else {
                     parseKeyword(ctx, "FOLLOWING");
                     s3 = s2 == null ? (rows ? DSL.rowsBetweenUnboundedFollowing() : DSL.rangeBetweenUnboundedFollowing()) : (rows ? ((WindowSpecificationRowsStep)s2).rowsBetweenUnboundedFollowing() : ((WindowSpecificationRowsStep)s2).rangeBetweenUnboundedFollowing());
                  }
               } else if (parseKeywordIf(ctx, "CURRENT ROW")) {
                  s3 = s2 == null ? (rows ? DSL.rowsBetweenCurrentRow() : DSL.rangeBetweenCurrentRow()) : (rows ? ((WindowSpecificationRowsStep)s2).rowsBetweenCurrentRow() : ((WindowSpecificationRowsStep)s2).rangeBetweenCurrentRow());
               } else {
                  number = (int)parseUnsignedInteger(ctx);
                  if (parseKeywordIf(ctx, "PRECEDING")) {
                     s3 = s2 == null ? (rows ? DSL.rowsBetweenPreceding(number) : DSL.rangeBetweenPreceding(number)) : (rows ? ((WindowSpecificationRowsStep)s2).rowsBetweenPreceding(number) : ((WindowSpecificationRowsStep)s2).rangeBetweenPreceding(number));
                  } else {
                     parseKeyword(ctx, "FOLLOWING");
                     s3 = s2 == null ? (rows ? DSL.rowsBetweenFollowing(number) : DSL.rangeBetweenFollowing(number)) : (rows ? ((WindowSpecificationRowsStep)s2).rowsBetweenFollowing(number) : ((WindowSpecificationRowsStep)s2).rangeBetweenFollowing(number));
                  }
               }

               parseKeyword(ctx, "AND");
               if (parseKeywordIf(ctx, "UNBOUNDED")) {
                  if (parseKeywordIf(ctx, "PRECEDING")) {
                     result = s3.andUnboundedPreceding();
                  } else {
                     parseKeyword(ctx, "FOLLOWING");
                     result = s3.andUnboundedFollowing();
                  }
               } else if (parseKeywordIf(ctx, "CURRENT ROW")) {
                  result = s3.andCurrentRow();
               } else {
                  number = (int)parseUnsignedInteger(ctx);
                  if (parseKeywordIf(ctx, "PRECEDING")) {
                     result = s3.andPreceding(number);
                  } else {
                     parseKeyword(ctx, "FOLLOWING");
                     result = s3.andFollowing(number);
                  }
               }
            } else if (parseKeywordIf(ctx, "UNBOUNDED")) {
               if (parseKeywordIf(ctx, "PRECEDING")) {
                  result = s2 == null ? (rows ? DSL.rowsUnboundedPreceding() : DSL.rangeUnboundedPreceding()) : (rows ? ((WindowSpecificationRowsStep)s2).rowsUnboundedPreceding() : ((WindowSpecificationRowsStep)s2).rangeUnboundedPreceding());
               } else {
                  parseKeyword(ctx, "FOLLOWING");
                  result = s2 == null ? (rows ? DSL.rowsUnboundedFollowing() : DSL.rangeUnboundedFollowing()) : (rows ? ((WindowSpecificationRowsStep)s2).rowsUnboundedFollowing() : ((WindowSpecificationRowsStep)s2).rangeUnboundedFollowing());
               }
            } else if (parseKeywordIf(ctx, "CURRENT ROW")) {
               result = s2 == null ? (rows ? DSL.rowsCurrentRow() : DSL.rangeCurrentRow()) : (rows ? ((WindowSpecificationRowsStep)s2).rowsCurrentRow() : ((WindowSpecificationRowsStep)s2).rangeCurrentRow());
            } else {
               number = (int)parseUnsignedInteger(ctx);
               if (parseKeywordIf(ctx, "PRECEDING")) {
                  result = s2 == null ? (rows ? DSL.rowsPreceding(number) : DSL.rangePreceding(number)) : (rows ? ((WindowSpecificationRowsStep)s2).rowsPreceding(number) : ((WindowSpecificationRowsStep)s2).rangePreceding(number));
               } else {
                  parseKeyword(ctx, "FOLLOWING");
                  result = s2 == null ? (rows ? DSL.rowsFollowing(number) : DSL.rangeFollowing(number)) : (rows ? ((WindowSpecificationRowsStep)s2).rowsFollowing(number) : ((WindowSpecificationRowsStep)s2).rangeFollowing(number));
               }
            }
         }

         parse(ctx, ')');
      } else {
         result = DSL.name(parseIdentifier(ctx));
      }

      return result;
   }

   private static final Field<?> parseFieldRankIf(ParserImpl.ParserContext ctx) {
      if (parseKeywordIf(ctx, "RANK")) {
         parse(ctx, '(');
         parse(ctx, ')');
         return parseWindowFunction(ctx, (WindowIgnoreNullsStep)null, DSL.rank());
      } else {
         return null;
      }
   }

   private static final Field<?> parseFieldDenseRankIf(ParserImpl.ParserContext ctx) {
      if (parseKeywordIf(ctx, "DENSE_RANK")) {
         parse(ctx, '(');
         parse(ctx, ')');
         return parseWindowFunction(ctx, (WindowIgnoreNullsStep)null, DSL.denseRank());
      } else {
         return null;
      }
   }

   private static final Field<?> parseFieldPercentRankIf(ParserImpl.ParserContext ctx) {
      if (parseKeywordIf(ctx, "PERCENT_RANK")) {
         parse(ctx, '(');
         parse(ctx, ')');
         return parseWindowFunction(ctx, (WindowIgnoreNullsStep)null, DSL.percentRank());
      } else {
         return null;
      }
   }

   private static final Field<?> parseFieldCumeDistIf(ParserImpl.ParserContext ctx) {
      if (parseKeywordIf(ctx, "CUME_DIST")) {
         parse(ctx, '(');
         parse(ctx, ')');
         return parseWindowFunction(ctx, (WindowIgnoreNullsStep)null, DSL.cumeDist());
      } else {
         return null;
      }
   }

   private static final Field<?> parseFieldRowNumberIf(ParserImpl.ParserContext ctx) {
      if (parseKeywordIf(ctx, "ROW_NUMBER")) {
         parse(ctx, '(');
         parse(ctx, ')');
         return parseWindowFunction(ctx, (WindowIgnoreNullsStep)null, DSL.rowNumber());
      } else {
         return null;
      }
   }

   private static final Field<?> parseFieldNtileIf(ParserImpl.ParserContext ctx) {
      if (parseKeywordIf(ctx, "NTILE")) {
         parse(ctx, '(');
         int number = (int)parseUnsignedInteger(ctx);
         parse(ctx, ')');
         return parseWindowFunction(ctx, (WindowIgnoreNullsStep)null, DSL.ntile(number));
      } else {
         return null;
      }
   }

   private static final Field<?> parseFieldLeadLagIf(ParserImpl.ParserContext ctx) {
      boolean lead = parseKeywordIf(ctx, "LEAD");
      boolean lag = !lead && parseKeywordIf(ctx, "LAG");
      if (!lead && !lag) {
         return null;
      } else {
         parse(ctx, '(');
         Field<Void> f1 = parseField(ctx);
         Integer f2 = null;
         Field<Void> f3 = null;
         if (parseIf(ctx, ',')) {
            f2 = (int)parseUnsignedInteger(ctx);
            if (parseIf(ctx, ',')) {
               f3 = parseField(ctx);
            }
         }

         parse(ctx, ')');
         return parseWindowFunction(ctx, lead ? (f2 == null ? DSL.lead(f1) : (f3 == null ? DSL.lead(f1, f2) : DSL.lead(f1, f2, f3))) : (f2 == null ? DSL.lag(f1) : (f3 == null ? DSL.lag(f1, f2) : DSL.lag(f1, f2, f3))), (WindowOverStep)null);
      }
   }

   private static final Field<?> parseFieldFirstValueIf(ParserImpl.ParserContext ctx) {
      if (parseKeywordIf(ctx, "FIRST_VALUE")) {
         parse(ctx, '(');
         Field<Void> arg = parseField(ctx);
         parse(ctx, ')');
         return parseWindowFunction(ctx, DSL.firstValue(arg), (WindowOverStep)null);
      } else {
         return null;
      }
   }

   private static final Field<?> parseFieldLastValueIf(ParserImpl.ParserContext ctx) {
      if (parseKeywordIf(ctx, "LAST_VALUE")) {
         parse(ctx, '(');
         Field<Void> arg = parseField(ctx);
         parse(ctx, ')');
         return parseWindowFunction(ctx, DSL.lastValue(arg), (WindowOverStep)null);
      } else {
         return null;
      }
   }

   private static final Field<?> parseFieldNthValueIf(ParserImpl.ParserContext ctx) {
      if (parseKeywordIf(ctx, "NTH_VALUE")) {
         parse(ctx, '(');
         Field<?> f1 = parseField(ctx);
         parse(ctx, ',');
         int f2 = (int)parseUnsignedInteger(ctx);
         parse(ctx, ')');
         return parseWindowFunction(ctx, DSL.nthValue(f1, f2), (WindowOverStep)null);
      } else {
         return null;
      }
   }

   private static final Field<?> parseWindowFunction(ParserImpl.ParserContext ctx, WindowIgnoreNullsStep s1, WindowOverStep<?> s2) {
      if (s1 != null) {
         s2 = s1;
      }

      parseKeyword(ctx, "OVER");
      Object nameOrSpecification = parseWindowNameOrSpecification(ctx);
      Field<?> result = nameOrSpecification instanceof Name ? ((WindowOverStep)s2).over((Name)nameOrSpecification) : (nameOrSpecification instanceof WindowSpecification ? ((WindowOverStep)s2).over((WindowSpecification)nameOrSpecification) : ((WindowOverStep)s2).over());
      return (Field)result;
   }

   private static final AggregateFunction<?> parseBinarySetFunctionIf(ParserImpl.ParserContext ctx) {
      ParserImpl.BinarySetFunctionType type = parseBinarySetFunctionTypeIf(ctx);
      if (type == null) {
         return null;
      } else {
         parse(ctx, '(');
         Field<? extends Number> arg1 = parseFieldSum(ctx, ParserImpl.Type.N);
         parse(ctx, ',');
         Field<? extends Number> arg2 = parseFieldSum(ctx, ParserImpl.Type.N);
         parse(ctx, ')');
         switch(type) {
         case REGR_AVGX:
            return DSL.regrAvgX(arg1, arg2);
         case REGR_AVGY:
            return DSL.regrAvgY(arg1, arg2);
         case REGR_COUNT:
            return DSL.regrCount(arg1, arg2);
         case REGR_INTERCEPT:
            return DSL.regrIntercept(arg1, arg2);
         case REGR_R2:
            return DSL.regrR2(arg1, arg2);
         case REGR_SLOPE:
            return DSL.regrSlope(arg1, arg2);
         case REGR_SXX:
            return DSL.regrSXX(arg1, arg2);
         case REGR_SXY:
            return DSL.regrSXY(arg1, arg2);
         case REGR_SYY:
            return DSL.regrSYY(arg1, arg2);
         default:
            throw ctx.exception();
         }
      }
   }

   private static final AggregateFunction<?> parseGeneralSetFunctionIf(ParserImpl.ParserContext ctx) {
      ParserImpl.ComputationalOperation operation = parseComputationalOperationIf(ctx);
      if (operation == null) {
         return null;
      } else {
         parse(ctx, '(');
         boolean distinct = parseSetQuantifier(ctx);
         Field arg = parseField(ctx);
         parse(ctx, ')');
         switch(operation) {
         case AVG:
            return distinct ? DSL.avgDistinct(arg) : DSL.avg(arg);
         case MAX:
            return distinct ? DSL.maxDistinct(arg) : DSL.max(arg);
         case MIN:
            return distinct ? DSL.minDistinct(arg) : DSL.min(arg);
         case SUM:
            return distinct ? DSL.sumDistinct(arg) : DSL.sum(arg);
         case EVERY:
            return DSL.every(arg);
         case ANY:
            return DSL.boolOr(arg);
         case STDDEV_POP:
            return DSL.stddevPop(arg);
         case STDDEV_SAMP:
            return DSL.stddevSamp(arg);
         case VAR_POP:
            return DSL.varPop(arg);
         case VAR_SAMP:
            return DSL.varSamp(arg);
         default:
            throw ctx.unexpectedToken();
         }
      }
   }

   private static final AggregateFunction<?> parseCountIf(ParserImpl.ParserContext ctx) {
      if (parseKeywordIf(ctx, "COUNT")) {
         parse(ctx, '(');
         if (parseIf(ctx, '*')) {
            parse(ctx, ')');
            return DSL.count();
         } else {
            boolean distinct = parseSetQuantifier(ctx);
            List<Field<?>> fields = distinct ? parseFields(ctx) : Collections.singletonList(parseField(ctx));
            parse(ctx, ')');
            if (distinct) {
               return fields.size() > 0 ? DSL.countDistinct((Field[])fields.toArray(Tools.EMPTY_FIELD)) : DSL.countDistinct((Field)fields.get(0));
            } else {
               return DSL.count((Field)fields.get(0));
            }
         }
      } else {
         return null;
      }
   }

   private static final boolean parseSetQuantifier(ParserImpl.ParserContext ctx) {
      boolean distinct = parseKeywordIf(ctx, "DISTINCT");
      if (!distinct) {
         parseKeywordIf(ctx, "ALL");
      }

      return distinct;
   }

   private static final Schema parseSchemaName(ParserImpl.ParserContext ctx) {
      return DSL.schema(parseName(ctx));
   }

   private static final Table<?> parseTableName(ParserImpl.ParserContext ctx) {
      return DSL.table(parseName(ctx));
   }

   private static final TableField<?, ?> parseFieldName(ParserImpl.ParserContext ctx) {
      return (TableField)DSL.field(parseName(ctx));
   }

   private static final List<Field<?>> parseFieldNames(ParserImpl.ParserContext ctx) {
      ArrayList result = new ArrayList();

      do {
         result.add(parseFieldName(ctx));
      } while(parseIf(ctx, ','));

      return result;
   }

   private static final Sequence<?> parseSequenceName(ParserImpl.ParserContext ctx) {
      return DSL.sequence(parseName(ctx));
   }

   private static final Name parseIndexName(ParserImpl.ParserContext ctx) {
      return parseName(ctx);
   }

   static final Name parseName(ParserImpl.ParserContext ctx) {
      parseWhitespaceIf(ctx);
      if (ctx.done()) {
         throw ctx.exception();
      } else {
         List<String> name = new ArrayList();
         StringBuilder sb = new StringBuilder();
         int i = ctx.position;
         boolean identifierStart = true;
         boolean identifierEnd = false;

         while(true) {
            char c = ctx.character(i);
            if (c == '.') {
               if (identifierStart) {
                  throw new ParserImpl.ParserException(ctx);
               }

               name.add(sb.toString());
               sb = new StringBuilder();
               identifierStart = true;
               identifierEnd = false;
            } else if (!identifierEnd && Character.isJavaIdentifierPart(c)) {
               sb.append(c);
               identifierStart = false;
            } else {
               if (!Character.isWhitespace(c)) {
                  name.add(sb.toString());
                  identifierEnd = !identifierStart;
                  break;
               }

               identifierEnd = !identifierStart;
            }

            ++i;
            if (i == ctx.sql.length) {
               if (identifierStart) {
                  throw ctx.exception();
               }

               name.add(sb.toString());
               identifierEnd = !identifierStart;
               break;
            }
         }

         if (ctx.position == i) {
            throw ctx.exception();
         } else {
            ctx.position = i;
            return DSL.name((String[])name.toArray(Tools.EMPTY_STRING));
         }
      }
   }

   private static final List<String> parseIdentifiers(ParserImpl.ParserContext ctx) {
      LinkedHashSet result = new LinkedHashSet();

      while(result.add(parseIdentifier(ctx))) {
         if (!parseIf(ctx, ',')) {
            return new ArrayList(result);
         }
      }

      throw ctx.exception();
   }

   private static final String parseIdentifier(ParserImpl.ParserContext ctx) {
      String alias = parseIdentifierIf(ctx);
      if (alias == null) {
         throw ctx.exception();
      } else {
         return alias;
      }
   }

   private static final String parseIdentifierIf(ParserImpl.ParserContext ctx) {
      parseWhitespaceIf(ctx);

      int start;
      for(start = ctx.position; ctx.isIdentifierPart(); ++ctx.position) {
      }

      return ctx.position == start ? null : new String(ctx.sql, start, ctx.position - start);
   }

   private static final DataType<?> parseDataType(ParserImpl.ParserContext ctx) {
      parseWhitespaceIf(ctx);
      switch(ctx.character()) {
      case 'B':
      case 'b':
         if (parseKeywordIf(ctx, "BIGINT")) {
            return SQLDataType.BIGINT;
         } else if (parseKeywordIf(ctx, "BINARY")) {
            return parseDataTypeLength(ctx, SQLDataType.BINARY);
         } else if (parseKeywordIf(ctx, "BIT")) {
            return SQLDataType.BIT;
         } else if (parseKeywordIf(ctx, "BLOB")) {
            return SQLDataType.BLOB;
         } else if (parseKeywordIf(ctx, "BOOLEAN")) {
            return SQLDataType.BOOLEAN;
         }
      case 'C':
      case 'c':
         if (parseKeywordIf(ctx, "CHAR")) {
            return parseDataTypeLength(ctx, SQLDataType.CHAR);
         } else if (parseKeywordIf(ctx, "CLOB")) {
            return parseDataTypeLength(ctx, SQLDataType.CLOB);
         }
      case 'I':
      case 'i':
         if (parseKeywordIf(ctx, "INT") || parseKeywordIf(ctx, "INTEGER")) {
            return SQLDataType.INTEGER;
         }
      case 'V':
      case 'v':
         if (parseKeywordIf(ctx, "VARCHAR") || parseKeywordIf(ctx, "VARCHAR2") || parseKeywordIf(ctx, "CHARACTER VARYING")) {
            return parseDataTypeLength(ctx, SQLDataType.VARCHAR);
         } else if (parseKeywordIf(ctx, "VARBINARY")) {
            return parseDataTypeLength(ctx, SQLDataType.VARBINARY);
         }
      default:
         throw ctx.unexpectedToken();
      }
   }

   private static final DataType<?> parseDataTypeLength(ParserImpl.ParserContext ctx, DataType<?> result) {
      if (parseIf(ctx, '(')) {
         result = result.length((int)parseUnsignedInteger(ctx));
         parse(ctx, ')');
      }

      return result;
   }

   static final char parseCharacterLiteral(ParserImpl.ParserContext ctx) {
      parseWhitespaceIf(ctx);
      parse(ctx, '\'');
      char c = ctx.character();
      if (c == '\'') {
         parse(ctx, '\'');
      }

      ++ctx.position;
      parse(ctx, '\'');
      return c;
   }

   static final Field<?> parseBindVariable(ParserImpl.ParserContext ctx) {
      switch(ctx.character()) {
      case ':':
         parse(ctx, ':');
         return DSL.param(parseIdentifier(ctx));
      case '?':
         parse(ctx, '?');
         return DSL.val((Object)null, (Class)Object.class);
      default:
         throw ctx.exception();
      }
   }

   static final String parseStringLiteral(ParserImpl.ParserContext ctx) {
      parseWhitespaceIf(ctx);
      parse(ctx, '\'');
      StringBuilder sb = new StringBuilder();

      for(int i = ctx.position; i < ctx.sql.length; ++i) {
         char c = ctx.character(i);
         if (c == '\'') {
            if (ctx.character(i + 1) != '\'') {
               ctx.position = i + 1;
               return sb.toString();
            }

            ++i;
         }

         sb.append(c);
      }

      throw ctx.exception();
   }

   private static final Field<?> parseFieldUnsignedNumericLiteralIf(ParserImpl.ParserContext ctx, boolean minus) {
      Number r = parseUnsignedNumericLiteralIf(ctx, minus);
      return r == null ? null : DSL.inline((Object)r);
   }

   private static final Number parseUnsignedNumericLiteralIf(ParserImpl.ParserContext ctx, boolean minus) {
      StringBuilder sb = new StringBuilder();

      while(true) {
         char c = ctx.character();
         if (c < '0' || c > '9') {
            if (c != '.') {
               if (sb.length() == 0) {
                  return null;
               } else {
                  try {
                     return minus ? -Long.valueOf(sb.toString()) : Long.valueOf(sb.toString());
                  } catch (Exception var5) {
                     return minus ? (new BigInteger(sb.toString())).negate() : new BigInteger(sb.toString());
                  }
               }
            } else {
               sb.append(c);
               ++ctx.position;

               while(true) {
                  c = ctx.character();
                  if (c < '0' || c > '9') {
                     if (sb.length() == 0) {
                        return null;
                     } else {
                        return minus ? (new BigDecimal(sb.toString())).negate() : new BigDecimal(sb.toString());
                     }
                  }

                  sb.append(c);
                  ++ctx.position;
               }
            }
         }

         sb.append(c);
         ++ctx.position;
      }
   }

   private static final Long parseUnsignedInteger(ParserImpl.ParserContext ctx) {
      Long result = parseUnsignedIntegerIf(ctx);
      if (result == null) {
         throw ctx.exception();
      } else {
         return result;
      }
   }

   private static final Field<?> parseFieldUnsignedIntegerIf(ParserImpl.ParserContext ctx, boolean minus) {
      Long r = parseUnsignedIntegerIf(ctx);
      return r == null ? null : (minus ? DSL.inline(-r) : DSL.inline(r));
   }

   private static final Long parseUnsignedIntegerIf(ParserImpl.ParserContext ctx) {
      parseWhitespaceIf(ctx);
      StringBuilder sb = new StringBuilder();

      while(true) {
         char c = ctx.character();
         if (c < '0' || c > '9') {
            return sb.length() == 0 ? null : Long.valueOf(sb.toString());
         }

         sb.append(c);
         ++ctx.position;
      }
   }

   private static final JoinType parseJoinType(ParserImpl.ParserContext ctx) {
      JoinType result = parseJoinTypeIf(ctx);
      if (result == null) {
         ctx.unexpectedToken();
      }

      return result;
   }

   private static final JoinType parseJoinTypeIf(ParserImpl.ParserContext ctx) {
      if (parseKeywordIf(ctx, "CROSS JOIN")) {
         return JoinType.CROSS_JOIN;
      } else if (parseKeywordIf(ctx, "CROSS APPLY")) {
         return JoinType.CROSS_APPLY;
      } else if (parseKeywordIf(ctx, "CROSS JOIN")) {
         return JoinType.CROSS_JOIN;
      } else if (parseKeywordIf(ctx, "INNER")) {
         parseKeyword(ctx, "JOIN");
         return JoinType.JOIN;
      } else if (parseKeywordIf(ctx, "JOIN")) {
         return JoinType.JOIN;
      } else if (parseKeywordIf(ctx, "LEFT")) {
         parseKeywordIf(ctx, "OUTER");
         parseKeyword(ctx, "JOIN");
         return JoinType.LEFT_OUTER_JOIN;
      } else if (parseKeywordIf(ctx, "RIGHT")) {
         parseKeywordIf(ctx, "OUTER");
         parseKeyword(ctx, "JOIN");
         return JoinType.RIGHT_OUTER_JOIN;
      } else if (parseKeywordIf(ctx, "FULL OUTER JOIN")) {
         return JoinType.FULL_OUTER_JOIN;
      } else if (parseKeywordIf(ctx, "OUTER APPLY")) {
         return JoinType.OUTER_APPLY;
      } else {
         if (parseKeywordIf(ctx, "NATURAL")) {
            if (parseKeywordIf(ctx, "LEFT")) {
               parseKeywordIf(ctx, "OUTER");
               parseKeyword(ctx, "JOIN");
               return JoinType.NATURAL_LEFT_OUTER_JOIN;
            }

            if (parseKeywordIf(ctx, "RIGHT")) {
               parseKeywordIf(ctx, "OUTER");
               parseKeyword(ctx, "JOIN");
               return JoinType.NATURAL_RIGHT_OUTER_JOIN;
            }

            if (parseKeywordIf(ctx, "JOIN")) {
               return JoinType.NATURAL_JOIN;
            }
         }

         return null;
      }
   }

   static final ParserImpl.TruthValue parseTruthValue(ParserImpl.ParserContext ctx) {
      ParserImpl.TruthValue result = parseTruthValueIf(ctx);
      if (result == null) {
         throw ctx.exception();
      } else {
         return result;
      }
   }

   static final ParserImpl.TruthValue parseTruthValueIf(ParserImpl.ParserContext ctx) {
      parseWhitespaceIf(ctx);
      if (parseKeywordIf(ctx, "TRUE")) {
         return ParserImpl.TruthValue.TRUE;
      } else if (parseKeywordIf(ctx, "FALSE")) {
         return ParserImpl.TruthValue.FALSE;
      } else {
         return parseKeywordIf(ctx, "NULL") ? ParserImpl.TruthValue.NULL : null;
      }
   }

   private static final CombineOperator parseCombineOperatorIf(ParserImpl.ParserContext ctx) {
      parseWhitespaceIf(ctx);
      if (parseKeywordIf(ctx, "UNION")) {
         if (parseKeywordIf(ctx, "ALL")) {
            return CombineOperator.UNION_ALL;
         } else {
            return parseKeywordIf(ctx, "DISTINCT") ? CombineOperator.UNION : CombineOperator.UNION;
         }
      } else if (!parseKeywordIf(ctx, "EXCEPT") && !parseKeywordIf(ctx, "MINUS")) {
         if (parseKeywordIf(ctx, "INTERSECT")) {
            if (parseKeywordIf(ctx, "ALL")) {
               return CombineOperator.INTERSECT_ALL;
            } else {
               return parseKeywordIf(ctx, "DISTINCT") ? CombineOperator.INTERSECT : CombineOperator.INTERSECT;
            }
         } else {
            return null;
         }
      } else if (parseKeywordIf(ctx, "ALL")) {
         return CombineOperator.EXCEPT_ALL;
      } else {
         return parseKeywordIf(ctx, "DISTINCT") ? CombineOperator.EXCEPT : CombineOperator.EXCEPT;
      }
   }

   private static final ParserImpl.ComputationalOperation parseComputationalOperationIf(ParserImpl.ParserContext ctx) {
      parseWhitespaceIf(ctx);
      if (parseKeywordIf(ctx, "AVG")) {
         return ParserImpl.ComputationalOperation.AVG;
      } else if (parseKeywordIf(ctx, "MAX")) {
         return ParserImpl.ComputationalOperation.MAX;
      } else if (parseKeywordIf(ctx, "MIN")) {
         return ParserImpl.ComputationalOperation.MIN;
      } else if (parseKeywordIf(ctx, "SUM")) {
         return ParserImpl.ComputationalOperation.SUM;
      } else if (!parseKeywordIf(ctx, "EVERY") && !parseKeywordIf(ctx, "BOOL_AND")) {
         if (!parseKeywordIf(ctx, "ANY") && !parseKeywordIf(ctx, "SOME") && !parseKeywordIf(ctx, "BOOL_OR")) {
            if (parseKeywordIf(ctx, "STDDEV_POP")) {
               return ParserImpl.ComputationalOperation.STDDEV_POP;
            } else if (parseKeywordIf(ctx, "STDDEV_SAMP")) {
               return ParserImpl.ComputationalOperation.STDDEV_SAMP;
            } else if (parseKeywordIf(ctx, "VAR_POP")) {
               return ParserImpl.ComputationalOperation.VAR_POP;
            } else {
               return parseKeywordIf(ctx, "VAR_SAMP") ? ParserImpl.ComputationalOperation.VAR_SAMP : null;
            }
         } else {
            return ParserImpl.ComputationalOperation.ANY;
         }
      } else {
         return ParserImpl.ComputationalOperation.EVERY;
      }
   }

   private static final ParserImpl.BinarySetFunctionType parseBinarySetFunctionTypeIf(ParserImpl.ParserContext ctx) {
      parseWhitespaceIf(ctx);
      ParserImpl.BinarySetFunctionType[] var1 = ParserImpl.BinarySetFunctionType.values();
      int var2 = var1.length;

      for(int var3 = 0; var3 < var2; ++var3) {
         ParserImpl.BinarySetFunctionType type = var1[var3];
         if (parseKeywordIf(ctx, type.name())) {
            return type;
         }
      }

      return null;
   }

   private static final Comparator parseComparatorIf(ParserImpl.ParserContext ctx) {
      parseWhitespaceIf(ctx);
      if (parseIf(ctx, "=")) {
         return Comparator.EQUALS;
      } else if (!parseIf(ctx, "!=") && !parseIf(ctx, "<>")) {
         if (parseIf(ctx, ">=")) {
            return Comparator.GREATER_OR_EQUAL;
         } else if (parseIf(ctx, ">")) {
            return Comparator.GREATER;
         } else if (parseIf(ctx, "<=")) {
            return Comparator.LESS_OR_EQUAL;
         } else {
            return parseIf(ctx, "<") ? Comparator.LESS : null;
         }
      } else {
         return Comparator.NOT_EQUALS;
      }
   }

   private static final boolean parseIf(ParserImpl.ParserContext ctx, String string) {
      parseWhitespaceIf(ctx);
      int length = string.length();
      ctx.expectedTokens.add(string);
      if (ctx.sql.length < ctx.position + length) {
         return false;
      } else {
         for(int i = 0; i < length; ++i) {
            char c = string.charAt(i);
            if (ctx.sql[ctx.position + i] != c) {
               return false;
            }
         }

         ctx.position += length;
         ctx.expectedTokens.clear();
         return true;
      }
   }

   private static final boolean parseIf(ParserImpl.ParserContext ctx, char c) {
      parseWhitespaceIf(ctx);
      if (ctx.character() != c) {
         return false;
      } else {
         ++ctx.position;
         return true;
      }
   }

   private static final void parse(ParserImpl.ParserContext ctx, char c) {
      if (!parseIf(ctx, c)) {
         throw ctx.unexpectedToken();
      }
   }

   static final void parseKeyword(ParserImpl.ParserContext ctx, String string) {
      if (!parseKeywordIf(ctx, string)) {
         throw ctx.unexpectedToken();
      }
   }

   static final boolean parseKeywordIf(ParserImpl.ParserContext ctx, String string) {
      ctx.expectedTokens.add(string);
      if (peekKeyword(ctx, string, true)) {
         ctx.expectedTokens.clear();
         return true;
      } else {
         return false;
      }
   }

   private static final boolean peekKeyword(ParserImpl.ParserContext ctx, String... keywords) {
      String[] var2 = keywords;
      int var3 = keywords.length;

      for(int var4 = 0; var4 < var3; ++var4) {
         String keyword = var2[var4];
         if (peekKeyword(ctx, keyword)) {
            return true;
         }
      }

      return false;
   }

   private static final boolean peekKeyword(ParserImpl.ParserContext ctx, String keyword) {
      return peekKeyword(ctx, keyword, false);
   }

   private static final boolean peekKeyword(ParserImpl.ParserContext ctx, String keyword, boolean updatePosition) {
      parseWhitespaceIf(ctx);
      int length = keyword.length();
      if (ctx.sql.length < ctx.position + length) {
         return false;
      } else {
         int skip = 0;

         label43:
         while(ctx.position + skip < ctx.sql.length) {
            char c = ctx.character(ctx.position + skip);
            switch(c) {
            case '\t':
            case '\n':
            case '\r':
            case ' ':
            case '(':
               ++skip;
               break;
            default:
               break label43;
            }
         }

         for(int i = 0; i < length; ++i) {
            char c = keyword.charAt(i);
            switch(c) {
            case '\t':
            case '\n':
            case '\r':
            case ' ':
               skip += afterWhitespace(ctx, ctx.position + i + skip) - ctx.position - i - 1;
               break;
            default:
               if (upper(ctx.sql[ctx.position + i + skip]) != keyword.charAt(i)) {
                  return false;
               }
            }
         }

         if (ctx.isIdentifierPart(ctx.position + length + skip)) {
            return false;
         } else {
            if (updatePosition) {
               ctx.position = ctx.position + length + skip;
            }

            return true;
         }
      }
   }

   static final boolean parseWhitespaceIf(ParserImpl.ParserContext ctx) {
      int position = ctx.position;
      ctx.position = afterWhitespace(ctx, ctx.position);
      return position != ctx.position;
   }

   private static final int afterWhitespace(ParserImpl.ParserContext ctx, int position) {
      int i = position;

      while(i < ctx.sql.length) {
         switch(ctx.sql[i]) {
         case '\t':
         case '\n':
         case '\r':
         case ' ':
            position = i + 1;
            ++i;
            break;
         default:
            return position;
         }
      }

      return position;
   }

   private static final char upper(char c) {
      return c >= 'a' && c <= 'z' ? (char)(c - 32) : c;
   }

   public static void main(String[] args) {
      System.out.println((new ParserImpl(new DefaultConfiguration())).parse("DROP INDEX   y on a.b.c"));
   }

   static enum BinarySetFunctionType {
      REGR_SLOPE,
      REGR_INTERCEPT,
      REGR_COUNT,
      REGR_R2,
      REGR_AVGX,
      REGR_AVGY,
      REGR_SXX,
      REGR_SYY,
      REGR_SXY;
   }

   static enum ComputationalOperation {
      AVG,
      MAX,
      MIN,
      SUM,
      EVERY,
      ANY,
      SOME,
      COUNT,
      STDDEV_POP,
      STDDEV_SAMP,
      VAR_SAMP,
      VAR_POP;
   }

   static enum TruthValue {
      TRUE,
      FALSE,
      NULL;
   }

   static class ParserException extends DataAccessException {
      private static final long serialVersionUID = -724913199583039157L;
      private final ParserImpl.ParserContext ctx;

      public ParserException(ParserImpl.ParserContext ctx) {
         this(ctx, (String)null);
      }

      public ParserException(ParserImpl.ParserContext ctx, String message) {
         this(ctx, message, SQLStateSubclass.C42000_NO_SUBCLASS);
      }

      public ParserException(ParserImpl.ParserContext ctx, String message, SQLStateSubclass state) {
         this(ctx, message, state, (Throwable)null);
      }

      public ParserException(ParserImpl.ParserContext ctx, String message, SQLStateSubclass state, Throwable cause) {
         super(state + ": " + (message == null ? "" : message + ": ") + ctx.mark(), cause);
         this.ctx = ctx;
      }
   }

   static class ParserContext {
      final DSLContext dsl;
      final String sqlString;
      final char[] sql;
      final List<String> expectedTokens;
      int position = 0;

      ParserContext(DSLContext dsl, String sqlString) {
         this.dsl = dsl;
         this.sqlString = sqlString;
         this.sql = sqlString.toCharArray();
         this.expectedTokens = new ArrayList();
      }

      ParserImpl.ParserException internalError() {
         return new ParserImpl.ParserException(this, "Internal Error");
      }

      ParserImpl.ParserException exception() {
         return new ParserImpl.ParserException(this);
      }

      ParserImpl.ParserException unexpectedToken() {
         return new ParserImpl.ParserException(this, "Expected tokens: " + new TreeSet(this.expectedTokens));
      }

      char character() {
         return this.character(this.position);
      }

      char character(int pos) {
         return pos >= 0 && pos < this.sql.length ? this.sql[pos] : ' ';
      }

      boolean isWhitespace() {
         return Character.isWhitespace(this.character());
      }

      boolean isWhitespace(int pos) {
         return Character.isWhitespace(this.character(pos));
      }

      boolean isIdentifierPart() {
         return Character.isJavaIdentifierPart(this.character());
      }

      boolean isIdentifierPart(int pos) {
         return Character.isJavaIdentifierPart(this.character(pos));
      }

      boolean done() {
         return this.position >= this.sql.length;
      }

      String mark() {
         return this.sqlString.substring(0, this.position) + "[*]" + this.sqlString.substring(this.position);
      }

      public String toString() {
         return this.mark();
      }
   }

   static enum Type {
      D,
      S,
      N,
      B;

      boolean is(ParserImpl.Type type) {
         return type == null || type == this;
      }
   }
}
