package com.mysql.cj.jdbc;

import com.mysql.cj.api.jdbc.JdbcConnection;
import com.mysql.cj.api.jdbc.result.ResultSetInternalMethods;
import com.mysql.cj.core.Messages;
import com.mysql.cj.core.MysqlType;
import com.mysql.cj.core.exceptions.CJException;
import com.mysql.cj.core.result.Field;
import com.mysql.cj.core.util.StringUtils;
import com.mysql.cj.jdbc.exceptions.SQLError;
import com.mysql.cj.jdbc.exceptions.SQLExceptionsMapping;
import com.mysql.cj.jdbc.io.ResultSetFactory;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

public class DatabaseMetaDataUsingInfoSchema extends DatabaseMetaData {
   protected DatabaseMetaDataUsingInfoSchema(JdbcConnection connToSet, String databaseToSet, ResultSetFactory resultSetFactory) throws SQLException {
      super(connToSet, databaseToSet, resultSetFactory);
   }

   protected ResultSet executeMetadataQuery(java.sql.PreparedStatement pStmt) throws SQLException {
      ResultSet rs = pStmt.executeQuery();
      ((ResultSetInternalMethods)rs).setOwningStatement((StatementImpl)null);
      return rs;
   }

   public ResultSet getColumnPrivileges(String catalog, String schema, String table, String columnNamePattern) throws SQLException {
      try {
         if (columnNamePattern == null) {
            if (!this.nullNamePatternMatchesAll) {
               throw SQLError.createSQLException(Messages.getString("DatabaseMetaData.9"), "S1009", this.getExceptionInterceptor());
            }

            columnNamePattern = "%";
         }

         if (catalog == null && this.nullCatalogMeansCurrent) {
            catalog = this.database;
         }

         String sql = "SELECT TABLE_SCHEMA AS TABLE_CAT, NULL AS TABLE_SCHEM, TABLE_NAME,COLUMN_NAME, NULL AS GRANTOR, GRANTEE, PRIVILEGE_TYPE AS PRIVILEGE, IS_GRANTABLE FROM INFORMATION_SCHEMA.COLUMN_PRIVILEGES WHERE TABLE_SCHEMA LIKE ? AND TABLE_NAME =? AND COLUMN_NAME LIKE ? ORDER BY COLUMN_NAME, PRIVILEGE_TYPE";
         java.sql.PreparedStatement pStmt = null;

         ResultSet var8;
         try {
            pStmt = this.prepareMetaDataSafeStatement(sql);
            if (catalog != null) {
               pStmt.setString(1, catalog);
            } else {
               pStmt.setString(1, "%");
            }

            pStmt.setString(2, table);
            pStmt.setString(3, columnNamePattern);
            ResultSet rs = this.executeMetadataQuery(pStmt);
            ((ResultSetInternalMethods)rs).getColumnDefinition().setFields(new Field[]{new Field("", "TABLE_CAT", this.getMetadataCollationIndex(), this.getMetadataEncoding(), MysqlType.CHAR, 64), new Field("", "TABLE_SCHEM", this.getMetadataCollationIndex(), this.getMetadataEncoding(), MysqlType.CHAR, 1), new Field("", "TABLE_NAME", this.getMetadataCollationIndex(), this.getMetadataEncoding(), MysqlType.CHAR, 64), new Field("", "COLUMN_NAME", this.getMetadataCollationIndex(), this.getMetadataEncoding(), MysqlType.CHAR, 64), new Field("", "GRANTOR", this.getMetadataCollationIndex(), this.getMetadataEncoding(), MysqlType.CHAR, 77), new Field("", "GRANTEE", this.getMetadataCollationIndex(), this.getMetadataEncoding(), MysqlType.CHAR, 77), new Field("", "PRIVILEGE", this.getMetadataCollationIndex(), this.getMetadataEncoding(), MysqlType.CHAR, 64), new Field("", "IS_GRANTABLE", this.getMetadataCollationIndex(), this.getMetadataEncoding(), MysqlType.CHAR, 3)});
            var8 = rs;
         } finally {
            if (pStmt != null) {
               pStmt.close();
            }

         }

         return var8;
      } catch (CJException var14) {
         throw SQLExceptionsMapping.translateException(var14, this.getExceptionInterceptor());
      }
   }

   public ResultSet getColumns(String catalog, String schemaPattern, String tableName, String columnNamePattern) throws SQLException {
      try {
         if (columnNamePattern == null) {
            if (!this.nullNamePatternMatchesAll) {
               throw SQLError.createSQLException(Messages.getString("DatabaseMetaData.9"), "S1009", this.getExceptionInterceptor());
            }

            columnNamePattern = "%";
         }

         if (catalog == null && this.nullCatalogMeansCurrent) {
            catalog = this.database;
         }

         StringBuilder sqlBuf = new StringBuilder("SELECT TABLE_SCHEMA AS TABLE_CAT, NULL AS TABLE_SCHEM, TABLE_NAME, COLUMN_NAME,");
         this.appendJdbcTypeMappingQuery(sqlBuf, "DATA_TYPE", "COLUMN_TYPE");
         sqlBuf.append(" AS DATA_TYPE, ");
         sqlBuf.append("UPPER(CASE");
         sqlBuf.append(" WHEN LOCATE('UNSIGNED', UPPER(COLUMN_TYPE)) != 0 AND LOCATE('UNSIGNED', UPPER(DATA_TYPE)) = 0 AND LOCATE('SET', UPPER(DATA_TYPE)) <> 1 AND LOCATE('ENUM', UPPER(DATA_TYPE)) <> 1 THEN CONCAT(DATA_TYPE, ' UNSIGNED')");
         if (this.tinyInt1isBit) {
            sqlBuf.append(" WHEN UPPER(DATA_TYPE)='TINYINT' THEN CASE");
            if (this.transformedBitIsBoolean) {
               sqlBuf.append(" WHEN LOCATE('(1)', COLUMN_TYPE) != 0 THEN 'BOOLEAN'");
            } else {
               sqlBuf.append(" WHEN LOCATE('(1)', COLUMN_TYPE) != 0 THEN 'BIT'");
            }

            sqlBuf.append(" WHEN LOCATE('UNSIGNED', UPPER(COLUMN_TYPE)) != 0 AND LOCATE('UNSIGNED', UPPER(DATA_TYPE)) = 0 THEN 'TINYINT UNSIGNED'");
            sqlBuf.append(" ELSE DATA_TYPE END ");
         }

         sqlBuf.append(" WHEN UPPER(DATA_TYPE)='POINT' THEN 'GEOMETRY'");
         sqlBuf.append(" WHEN UPPER(DATA_TYPE)='LINESTRING' THEN 'GEOMETRY'");
         sqlBuf.append(" WHEN UPPER(DATA_TYPE)='POLYGON' THEN 'GEOMETRY'");
         sqlBuf.append(" WHEN UPPER(DATA_TYPE)='MULTIPOINT' THEN 'GEOMETRY'");
         sqlBuf.append(" WHEN UPPER(DATA_TYPE)='MULTILINESTRING' THEN 'GEOMETRY'");
         sqlBuf.append(" WHEN UPPER(DATA_TYPE)='MULTIPOLYGON' THEN 'GEOMETRY'");
         sqlBuf.append(" WHEN UPPER(DATA_TYPE)='GEOMETRYCOLLECTION' THEN 'GEOMETRY'");
         sqlBuf.append(" ELSE UPPER(DATA_TYPE) END) AS TYPE_NAME,");
         sqlBuf.append("UPPER(CASE");
         sqlBuf.append(" WHEN UPPER(DATA_TYPE)='DATE' THEN 10");
         sqlBuf.append(" WHEN UPPER(DATA_TYPE)='TIME' THEN 16");
         sqlBuf.append(" WHEN UPPER(DATA_TYPE)='DATETIME' THEN 26");
         sqlBuf.append(" WHEN UPPER(DATA_TYPE)='TIMESTAMP' THEN 26");
         sqlBuf.append(" WHEN UPPER(DATA_TYPE)='YEAR' THEN 4");
         if (this.tinyInt1isBit) {
            sqlBuf.append(" WHEN UPPER(DATA_TYPE)='TINYINT' AND LOCATE('(1)', COLUMN_TYPE) != 0 THEN 1");
         }

         sqlBuf.append(" WHEN UPPER(DATA_TYPE)='MEDIUMINT' AND LOCATE('UNSIGNED', UPPER(COLUMN_TYPE)) != 0 THEN 8");
         sqlBuf.append(" WHEN UPPER(DATA_TYPE)='JSON' THEN 1073741824");
         sqlBuf.append(" WHEN UPPER(DATA_TYPE)='GEOMETRY' THEN 65535");
         sqlBuf.append(" WHEN UPPER(DATA_TYPE)='POINT' THEN 65535");
         sqlBuf.append(" WHEN UPPER(DATA_TYPE)='LINESTRING' THEN 65535");
         sqlBuf.append(" WHEN UPPER(DATA_TYPE)='POLYGON' THEN 65535");
         sqlBuf.append(" WHEN UPPER(DATA_TYPE)='MULTIPOINT' THEN 65535");
         sqlBuf.append(" WHEN UPPER(DATA_TYPE)='MULTILINESTRING' THEN 65535");
         sqlBuf.append(" WHEN UPPER(DATA_TYPE)='MULTIPOLYGON' THEN 65535");
         sqlBuf.append(" WHEN UPPER(DATA_TYPE)='GEOMETRYCOLLECTION' THEN 65535");
         sqlBuf.append(" WHEN CHARACTER_MAXIMUM_LENGTH IS NULL THEN NUMERIC_PRECISION");
         sqlBuf.append(" WHEN CHARACTER_MAXIMUM_LENGTH > ");
         sqlBuf.append(Integer.MAX_VALUE);
         sqlBuf.append(" THEN ");
         sqlBuf.append(Integer.MAX_VALUE);
         sqlBuf.append(" ELSE CHARACTER_MAXIMUM_LENGTH");
         sqlBuf.append(" END) AS COLUMN_SIZE,");
         sqlBuf.append(maxBufferSize);
         sqlBuf.append(" AS BUFFER_LENGTH,");
         sqlBuf.append("UPPER(CASE");
         sqlBuf.append(" WHEN UPPER(DATA_TYPE)='DECIMAL' THEN NUMERIC_SCALE");
         sqlBuf.append(" WHEN UPPER(DATA_TYPE)='FLOAT' OR UPPER(DATA_TYPE)='DOUBLE' THEN");
         sqlBuf.append(" CASE WHEN NUMERIC_SCALE IS NULL THEN 0");
         sqlBuf.append(" ELSE NUMERIC_SCALE END");
         sqlBuf.append(" ELSE NULL END) AS DECIMAL_DIGITS,");
         sqlBuf.append("10 AS NUM_PREC_RADIX,");
         sqlBuf.append("UPPER(CASE");
         sqlBuf.append(" WHEN IS_NULLABLE='NO' THEN ");
         sqlBuf.append(0);
         sqlBuf.append(" ELSE CASE WHEN IS_NULLABLE='YES' THEN ");
         sqlBuf.append(1);
         sqlBuf.append(" ELSE ");
         sqlBuf.append(2);
         sqlBuf.append(" END END) AS NULLABLE,");
         sqlBuf.append("COLUMN_COMMENT AS REMARKS,");
         sqlBuf.append("COLUMN_DEFAULT AS COLUMN_DEF,");
         sqlBuf.append("0 AS SQL_DATA_TYPE,");
         sqlBuf.append("0 AS SQL_DATETIME_SUB,");
         sqlBuf.append("CASE WHEN CHARACTER_OCTET_LENGTH > ");
         sqlBuf.append(Integer.MAX_VALUE);
         sqlBuf.append(" THEN ");
         sqlBuf.append(Integer.MAX_VALUE);
         sqlBuf.append(" ELSE CHARACTER_OCTET_LENGTH END AS CHAR_OCTET_LENGTH,");
         sqlBuf.append("ORDINAL_POSITION, IS_NULLABLE, NULL AS SCOPE_CATALOG, NULL AS SCOPE_SCHEMA, NULL AS SCOPE_TABLE, NULL AS SOURCE_DATA_TYPE,");
         sqlBuf.append("IF (EXTRA LIKE '%auto_increment%','YES','NO') AS IS_AUTOINCREMENT, ");
         sqlBuf.append("IF (EXTRA LIKE '%GENERATED%','YES','NO') AS IS_GENERATEDCOLUMN ");
         sqlBuf.append("FROM INFORMATION_SCHEMA.COLUMNS WHERE ");
         boolean operatingOnInformationSchema = "information_schema".equalsIgnoreCase(catalog);
         if (catalog != null) {
            if (!operatingOnInformationSchema && (StringUtils.indexOfIgnoreCase(0, catalog, "%") != -1 || StringUtils.indexOfIgnoreCase(0, catalog, "_") != -1)) {
               sqlBuf.append("TABLE_SCHEMA LIKE ? AND ");
            } else {
               sqlBuf.append("TABLE_SCHEMA = ? AND ");
            }
         } else {
            sqlBuf.append("TABLE_SCHEMA LIKE ? AND ");
         }

         if (tableName != null) {
            if (StringUtils.indexOfIgnoreCase(0, tableName, "%") == -1 && StringUtils.indexOfIgnoreCase(0, tableName, "_") == -1) {
               sqlBuf.append("TABLE_NAME = ? AND ");
            } else {
               sqlBuf.append("TABLE_NAME LIKE ? AND ");
            }
         } else {
            sqlBuf.append("TABLE_NAME LIKE ? AND ");
         }

         if (StringUtils.indexOfIgnoreCase(0, columnNamePattern, "%") == -1 && StringUtils.indexOfIgnoreCase(0, columnNamePattern, "_") == -1) {
            sqlBuf.append("COLUMN_NAME = ? ");
         } else {
            sqlBuf.append("COLUMN_NAME LIKE ? ");
         }

         sqlBuf.append("ORDER BY TABLE_SCHEMA, TABLE_NAME, ORDINAL_POSITION");
         java.sql.PreparedStatement pStmt = null;

         ResultSet var9;
         try {
            pStmt = this.prepareMetaDataSafeStatement(sqlBuf.toString());
            if (catalog != null) {
               pStmt.setString(1, catalog);
            } else {
               pStmt.setString(1, "%");
            }

            pStmt.setString(2, tableName);
            pStmt.setString(3, columnNamePattern);
            ResultSet rs = this.executeMetadataQuery(pStmt);
            ((ResultSetInternalMethods)rs).getColumnDefinition().setFields(this.createColumnsFields());
            var9 = rs;
         } finally {
            if (pStmt != null) {
               pStmt.close();
            }

         }

         return var9;
      } catch (CJException var15) {
         throw SQLExceptionsMapping.translateException(var15, this.getExceptionInterceptor());
      }
   }

   public ResultSet getCrossReference(String primaryCatalog, String primarySchema, String primaryTable, String foreignCatalog, String foreignSchema, String foreignTable) throws SQLException {
      try {
         if (primaryTable == null) {
            throw SQLError.createSQLException(Messages.getString("DatabaseMetaData.2"), "S1009", this.getExceptionInterceptor());
         } else {
            if (primaryCatalog == null && this.nullCatalogMeansCurrent) {
               primaryCatalog = this.database;
            }

            if (foreignCatalog == null && this.nullCatalogMeansCurrent) {
               foreignCatalog = this.database;
            }

            String sql = "SELECT A.REFERENCED_TABLE_SCHEMA AS PKTABLE_CAT,NULL AS PKTABLE_SCHEM, A.REFERENCED_TABLE_NAME AS PKTABLE_NAME,A.REFERENCED_COLUMN_NAME AS PKCOLUMN_NAME, A.TABLE_SCHEMA AS FKTABLE_CAT, NULL AS FKTABLE_SCHEM, A.TABLE_NAME AS FKTABLE_NAME, A.COLUMN_NAME AS FKCOLUMN_NAME, A.ORDINAL_POSITION AS KEY_SEQ," + this.generateUpdateRuleClause() + " AS UPDATE_RULE," + this.generateDeleteRuleClause() + " AS DELETE_RULE," + "A.CONSTRAINT_NAME AS FK_NAME," + "(SELECT CONSTRAINT_NAME FROM" + " INFORMATION_SCHEMA.TABLE_CONSTRAINTS" + " WHERE TABLE_SCHEMA = A.REFERENCED_TABLE_SCHEMA AND" + " TABLE_NAME = A.REFERENCED_TABLE_NAME AND" + " CONSTRAINT_TYPE IN ('UNIQUE','PRIMARY KEY') LIMIT 1)" + " AS PK_NAME," + 7 + " AS DEFERRABILITY " + "FROM " + "INFORMATION_SCHEMA.KEY_COLUMN_USAGE A JOIN " + "INFORMATION_SCHEMA.TABLE_CONSTRAINTS B " + "USING (TABLE_SCHEMA, TABLE_NAME, CONSTRAINT_NAME) " + this.generateOptionalRefContraintsJoin() + "WHERE " + "B.CONSTRAINT_TYPE = 'FOREIGN KEY' " + "AND A.REFERENCED_TABLE_SCHEMA LIKE ? AND A.REFERENCED_TABLE_NAME=? " + "AND A.TABLE_SCHEMA LIKE ? AND A.TABLE_NAME=? ORDER BY A.TABLE_SCHEMA, A.TABLE_NAME, A.ORDINAL_POSITION";
            java.sql.PreparedStatement pStmt = null;

            ResultSet var10;
            try {
               pStmt = this.prepareMetaDataSafeStatement(sql);
               if (primaryCatalog != null) {
                  pStmt.setString(1, primaryCatalog);
               } else {
                  pStmt.setString(1, "%");
               }

               pStmt.setString(2, primaryTable);
               if (foreignCatalog != null) {
                  pStmt.setString(3, foreignCatalog);
               } else {
                  pStmt.setString(3, "%");
               }

               pStmt.setString(4, foreignTable);
               ResultSet rs = this.executeMetadataQuery(pStmt);
               ((ResultSetInternalMethods)rs).getColumnDefinition().setFields(this.createFkMetadataFields());
               var10 = rs;
            } finally {
               if (pStmt != null) {
                  pStmt.close();
               }

            }

            return var10;
         }
      } catch (CJException var16) {
         throw SQLExceptionsMapping.translateException(var16, this.getExceptionInterceptor());
      }
   }

   public ResultSet getExportedKeys(String catalog, String schema, String table) throws SQLException {
      try {
         if (table == null) {
            throw SQLError.createSQLException(Messages.getString("DatabaseMetaData.2"), "S1009", this.getExceptionInterceptor());
         } else {
            if (catalog == null && this.nullCatalogMeansCurrent) {
               catalog = this.database;
            }

            String sql = "SELECT A.REFERENCED_TABLE_SCHEMA AS PKTABLE_CAT, NULL AS PKTABLE_SCHEM, A.REFERENCED_TABLE_NAME AS PKTABLE_NAME, A.REFERENCED_COLUMN_NAME AS PKCOLUMN_NAME, A.TABLE_SCHEMA AS FKTABLE_CAT, NULL AS FKTABLE_SCHEM, A.TABLE_NAME AS FKTABLE_NAME,A.COLUMN_NAME AS FKCOLUMN_NAME, A.ORDINAL_POSITION AS KEY_SEQ," + this.generateUpdateRuleClause() + " AS UPDATE_RULE," + this.generateDeleteRuleClause() + " AS DELETE_RULE," + "A.CONSTRAINT_NAME AS FK_NAME," + "(SELECT CONSTRAINT_NAME FROM" + " INFORMATION_SCHEMA.TABLE_CONSTRAINTS" + " WHERE TABLE_SCHEMA = A.REFERENCED_TABLE_SCHEMA AND" + " TABLE_NAME = A.REFERENCED_TABLE_NAME AND" + " CONSTRAINT_TYPE IN ('UNIQUE','PRIMARY KEY') LIMIT 1)" + " AS PK_NAME," + 7 + " AS DEFERRABILITY " + "FROM " + "INFORMATION_SCHEMA.KEY_COLUMN_USAGE A JOIN " + "INFORMATION_SCHEMA.TABLE_CONSTRAINTS B " + "USING (TABLE_SCHEMA, TABLE_NAME, CONSTRAINT_NAME) " + this.generateOptionalRefContraintsJoin() + "WHERE " + "B.CONSTRAINT_TYPE = 'FOREIGN KEY' " + "AND A.REFERENCED_TABLE_SCHEMA LIKE ? AND A.REFERENCED_TABLE_NAME=? " + "ORDER BY A.TABLE_SCHEMA, A.TABLE_NAME, A.ORDINAL_POSITION";
            java.sql.PreparedStatement pStmt = null;

            ResultSet var7;
            try {
               pStmt = this.prepareMetaDataSafeStatement(sql);
               if (catalog != null) {
                  pStmt.setString(1, catalog);
               } else {
                  pStmt.setString(1, "%");
               }

               pStmt.setString(2, table);
               ResultSet rs = this.executeMetadataQuery(pStmt);
               ((ResultSetInternalMethods)rs).getColumnDefinition().setFields(this.createFkMetadataFields());
               var7 = rs;
            } finally {
               if (pStmt != null) {
                  pStmt.close();
               }

            }

            return var7;
         }
      } catch (CJException var13) {
         throw SQLExceptionsMapping.translateException(var13, this.getExceptionInterceptor());
      }
   }

   private String generateOptionalRefContraintsJoin() {
      return "JOIN INFORMATION_SCHEMA.REFERENTIAL_CONSTRAINTS R ON (R.CONSTRAINT_NAME = B.CONSTRAINT_NAME AND R.TABLE_NAME = B.TABLE_NAME AND R.CONSTRAINT_SCHEMA = B.TABLE_SCHEMA) ";
   }

   private String generateDeleteRuleClause() {
      return "CASE WHEN R.DELETE_RULE='CASCADE' THEN " + String.valueOf(0) + " WHEN R.DELETE_RULE='SET NULL' THEN " + 2 + " WHEN R.DELETE_RULE='SET DEFAULT' THEN " + 4 + " WHEN R.DELETE_RULE='RESTRICT' THEN " + 1 + " WHEN R.DELETE_RULE='NO ACTION' THEN " + 3 + " ELSE " + 3 + " END ";
   }

   private String generateUpdateRuleClause() {
      return "CASE WHEN R.UPDATE_RULE='CASCADE' THEN " + String.valueOf(0) + " WHEN R.UPDATE_RULE='SET NULL' THEN " + 2 + " WHEN R.UPDATE_RULE='SET DEFAULT' THEN " + 4 + " WHEN R.UPDATE_RULE='RESTRICT' THEN " + 1 + " WHEN R.UPDATE_RULE='NO ACTION' THEN " + 3 + " ELSE " + 3 + " END ";
   }

   public ResultSet getImportedKeys(String catalog, String schema, String table) throws SQLException {
      try {
         if (table == null) {
            throw SQLError.createSQLException(Messages.getString("DatabaseMetaData.2"), "S1009", this.getExceptionInterceptor());
         } else {
            if (catalog == null && this.nullCatalogMeansCurrent) {
               catalog = this.database;
            }

            String sql = "SELECT A.REFERENCED_TABLE_SCHEMA AS PKTABLE_CAT, NULL AS PKTABLE_SCHEM, A.REFERENCED_TABLE_NAME AS PKTABLE_NAME,A.REFERENCED_COLUMN_NAME AS PKCOLUMN_NAME, A.TABLE_SCHEMA AS FKTABLE_CAT, NULL AS FKTABLE_SCHEM, A.TABLE_NAME AS FKTABLE_NAME, A.COLUMN_NAME AS FKCOLUMN_NAME, A.ORDINAL_POSITION AS KEY_SEQ," + this.generateUpdateRuleClause() + " AS UPDATE_RULE," + this.generateDeleteRuleClause() + " AS DELETE_RULE," + "A.CONSTRAINT_NAME AS FK_NAME," + "(SELECT CONSTRAINT_NAME FROM" + " INFORMATION_SCHEMA.TABLE_CONSTRAINTS" + " WHERE TABLE_SCHEMA = A.REFERENCED_TABLE_SCHEMA AND" + " TABLE_NAME = A.REFERENCED_TABLE_NAME AND" + " CONSTRAINT_TYPE IN ('UNIQUE','PRIMARY KEY') LIMIT 1)" + " AS PK_NAME," + 7 + " AS DEFERRABILITY " + "FROM " + "INFORMATION_SCHEMA.KEY_COLUMN_USAGE A " + "JOIN INFORMATION_SCHEMA.TABLE_CONSTRAINTS B USING " + "(CONSTRAINT_NAME, TABLE_NAME) " + this.generateOptionalRefContraintsJoin() + "WHERE " + "B.CONSTRAINT_TYPE = 'FOREIGN KEY' " + "AND A.TABLE_SCHEMA LIKE ? " + "AND A.TABLE_NAME=? " + "AND A.REFERENCED_TABLE_SCHEMA IS NOT NULL " + "ORDER BY A.REFERENCED_TABLE_SCHEMA, A.REFERENCED_TABLE_NAME, A.ORDINAL_POSITION";
            java.sql.PreparedStatement pStmt = null;

            ResultSet var7;
            try {
               pStmt = this.prepareMetaDataSafeStatement(sql);
               if (catalog != null) {
                  pStmt.setString(1, catalog);
               } else {
                  pStmt.setString(1, "%");
               }

               pStmt.setString(2, table);
               ResultSet rs = this.executeMetadataQuery(pStmt);
               ((ResultSetInternalMethods)rs).getColumnDefinition().setFields(this.createFkMetadataFields());
               var7 = rs;
            } finally {
               if (pStmt != null) {
                  pStmt.close();
               }

            }

            return var7;
         }
      } catch (CJException var13) {
         throw SQLExceptionsMapping.translateException(var13, this.getExceptionInterceptor());
      }
   }

   public ResultSet getIndexInfo(String catalog, String schema, String table, boolean unique, boolean approximate) throws SQLException {
      try {
         StringBuilder sqlBuf = new StringBuilder("SELECT TABLE_SCHEMA AS TABLE_CAT, NULL AS TABLE_SCHEM, TABLE_NAME, NON_UNIQUE,");
         sqlBuf.append("TABLE_SCHEMA AS INDEX_QUALIFIER, INDEX_NAME,3 AS TYPE, SEQ_IN_INDEX AS ORDINAL_POSITION, COLUMN_NAME,");
         sqlBuf.append("COLLATION AS ASC_OR_DESC, CARDINALITY, NULL AS PAGES, NULL AS FILTER_CONDITION FROM INFORMATION_SCHEMA.STATISTICS WHERE ");
         sqlBuf.append("TABLE_SCHEMA LIKE ? AND TABLE_NAME LIKE ?");
         if (unique) {
            sqlBuf.append(" AND NON_UNIQUE=0 ");
         }

         sqlBuf.append("ORDER BY NON_UNIQUE, INDEX_NAME, SEQ_IN_INDEX");
         java.sql.PreparedStatement pStmt = null;

         ResultSet var9;
         try {
            if (catalog == null && this.nullCatalogMeansCurrent) {
               catalog = this.database;
            }

            pStmt = this.prepareMetaDataSafeStatement(sqlBuf.toString());
            if (catalog != null) {
               pStmt.setString(1, catalog);
            } else {
               pStmt.setString(1, "%");
            }

            pStmt.setString(2, table);
            ResultSet rs = this.executeMetadataQuery(pStmt);
            ((ResultSetInternalMethods)rs).getColumnDefinition().setFields(this.createIndexInfoFields());
            var9 = rs;
         } finally {
            if (pStmt != null) {
               pStmt.close();
            }

         }

         return var9;
      } catch (CJException var15) {
         throw SQLExceptionsMapping.translateException(var15, this.getExceptionInterceptor());
      }
   }

   public ResultSet getPrimaryKeys(String catalog, String schema, String table) throws SQLException {
      try {
         if (catalog == null && this.nullCatalogMeansCurrent) {
            catalog = this.database;
         }

         if (table == null) {
            throw SQLError.createSQLException(Messages.getString("DatabaseMetaData.2"), "S1009", this.getExceptionInterceptor());
         } else {
            String sql = "SELECT TABLE_SCHEMA AS TABLE_CAT, NULL AS TABLE_SCHEM, TABLE_NAME, COLUMN_NAME, SEQ_IN_INDEX AS KEY_SEQ, 'PRIMARY' AS PK_NAME FROM INFORMATION_SCHEMA.STATISTICS WHERE TABLE_SCHEMA LIKE ? AND TABLE_NAME LIKE ? AND INDEX_NAME='PRIMARY' ORDER BY TABLE_SCHEMA, TABLE_NAME, INDEX_NAME, SEQ_IN_INDEX";
            java.sql.PreparedStatement pStmt = null;

            ResultSet var7;
            try {
               pStmt = this.prepareMetaDataSafeStatement(sql);
               if (catalog != null) {
                  pStmt.setString(1, catalog);
               } else {
                  pStmt.setString(1, "%");
               }

               pStmt.setString(2, table);
               ResultSet rs = this.executeMetadataQuery(pStmt);
               ((ResultSetInternalMethods)rs).getColumnDefinition().setFields(new Field[]{new Field("", "TABLE_CAT", this.getMetadataCollationIndex(), this.getMetadataEncoding(), MysqlType.CHAR, 255), new Field("", "TABLE_SCHEM", this.getMetadataCollationIndex(), this.getMetadataEncoding(), MysqlType.CHAR, 0), new Field("", "TABLE_NAME", this.getMetadataCollationIndex(), this.getMetadataEncoding(), MysqlType.CHAR, 255), new Field("", "COLUMN_NAME", this.getMetadataCollationIndex(), this.getMetadataEncoding(), MysqlType.CHAR, 32), new Field("", "KEY_SEQ", this.getMetadataCollationIndex(), this.getMetadataEncoding(), MysqlType.SMALLINT, 5), new Field("", "PK_NAME", this.getMetadataCollationIndex(), this.getMetadataEncoding(), MysqlType.CHAR, 32)});
               var7 = rs;
            } finally {
               if (pStmt != null) {
                  pStmt.close();
               }

            }

            return var7;
         }
      } catch (CJException var13) {
         throw SQLExceptionsMapping.translateException(var13, this.getExceptionInterceptor());
      }
   }

   public ResultSet getProcedures(String catalog, String schemaPattern, String procedureNamePattern) throws SQLException {
      try {
         if (procedureNamePattern == null || procedureNamePattern.length() == 0) {
            if (!this.nullNamePatternMatchesAll) {
               throw SQLError.createSQLException(Messages.getString("DatabaseMetaData.11"), "S1009", this.getExceptionInterceptor());
            }

            procedureNamePattern = "%";
         }

         String db = null;
         if (catalog == null) {
            if (this.nullCatalogMeansCurrent) {
               db = this.database;
            }
         } else {
            db = catalog;
         }

         String sql = "SELECT ROUTINE_SCHEMA AS PROCEDURE_CAT, NULL AS PROCEDURE_SCHEM, ROUTINE_NAME AS PROCEDURE_NAME, NULL AS RESERVED_1, NULL AS RESERVED_2, NULL AS RESERVED_3, ROUTINE_COMMENT AS REMARKS, CASE WHEN ROUTINE_TYPE = 'PROCEDURE' THEN 1 WHEN ROUTINE_TYPE='FUNCTION' THEN 2 ELSE 0 END AS PROCEDURE_TYPE, ROUTINE_NAME AS SPECIFIC_NAME FROM INFORMATION_SCHEMA.ROUTINES WHERE " + this.getRoutineTypeConditionForGetProcedures() + "ROUTINE_SCHEMA LIKE ? AND ROUTINE_NAME LIKE ? ORDER BY ROUTINE_SCHEMA, ROUTINE_NAME, ROUTINE_TYPE";
         java.sql.PreparedStatement pStmt = null;

         ResultSet var8;
         try {
            pStmt = this.prepareMetaDataSafeStatement(sql);
            if (db != null) {
               pStmt.setString(1, db);
            } else {
               pStmt.setString(1, "%");
            }

            pStmt.setString(2, procedureNamePattern);
            ResultSet rs = this.executeMetadataQuery(pStmt);
            ((ResultSetInternalMethods)rs).getColumnDefinition().setFields(this.createFieldMetadataForGetProcedures());
            var8 = rs;
         } finally {
            if (pStmt != null) {
               pStmt.close();
            }

         }

         return var8;
      } catch (CJException var14) {
         throw SQLExceptionsMapping.translateException(var14, this.getExceptionInterceptor());
      }
   }

   protected String getRoutineTypeConditionForGetProcedures() {
      return (Boolean)this.conn.getPropertySet().getBooleanReadableProperty("getProceduresReturnsFunctions").getValue() ? "" : "ROUTINE_TYPE = 'PROCEDURE' AND ";
   }

   public ResultSet getProcedureColumns(String catalog, String schemaPattern, String procedureNamePattern, String columnNamePattern) throws SQLException {
      try {
         if (procedureNamePattern == null || procedureNamePattern.length() == 0) {
            if (!this.nullNamePatternMatchesAll) {
               throw SQLError.createSQLException(Messages.getString("DatabaseMetaData.11"), "S1009", this.getExceptionInterceptor());
            }

            procedureNamePattern = "%";
         }

         String db = null;
         if (catalog == null) {
            if (this.nullCatalogMeansCurrent) {
               db = this.database;
            }
         } else {
            db = catalog;
         }

         StringBuilder sqlBuf = new StringBuilder("SELECT SPECIFIC_SCHEMA AS PROCEDURE_CAT, NULL AS `PROCEDURE_SCHEM`, SPECIFIC_NAME AS `PROCEDURE_NAME`, IFNULL(PARAMETER_NAME, '') AS `COLUMN_NAME`, CASE WHEN PARAMETER_MODE = 'IN' THEN 1 WHEN PARAMETER_MODE = 'OUT' THEN 4 WHEN PARAMETER_MODE = 'INOUT' THEN 2 WHEN ORDINAL_POSITION = 0 THEN 5 ELSE 0 END AS `COLUMN_TYPE`, ");
         this.appendJdbcTypeMappingQuery(sqlBuf, "DATA_TYPE", "DTD_IDENTIFIER");
         sqlBuf.append(" AS `DATA_TYPE`, ");
         sqlBuf.append("UPPER(CASE WHEN LOCATE('UNSIGNED', UPPER(DATA_TYPE)) != 0 AND LOCATE('UNSIGNED', UPPER(DATA_TYPE)) = 0 THEN CONCAT(DATA_TYPE, ' UNSIGNED') ELSE DATA_TYPE END) AS `TYPE_NAME`,");
         sqlBuf.append("NUMERIC_PRECISION AS `PRECISION`, ");
         sqlBuf.append("CASE WHEN LCASE(DATA_TYPE)='date' THEN 10 WHEN LCASE(DATA_TYPE)='time' THEN 8 WHEN LCASE(DATA_TYPE)='datetime' THEN 19 WHEN LCASE(DATA_TYPE)='timestamp' THEN 19 WHEN CHARACTER_MAXIMUM_LENGTH IS NULL THEN NUMERIC_PRECISION WHEN CHARACTER_MAXIMUM_LENGTH > 2147483647 THEN 2147483647 ELSE CHARACTER_MAXIMUM_LENGTH END AS LENGTH, ");
         sqlBuf.append("NUMERIC_SCALE AS `SCALE`, ");
         sqlBuf.append("10 AS RADIX,");
         sqlBuf.append("1 AS `NULLABLE`, NULL AS `REMARKS`, NULL AS `COLUMN_DEF`, NULL AS `SQL_DATA_TYPE`, NULL AS `SQL_DATETIME_SUB`, CHARACTER_OCTET_LENGTH AS `CHAR_OCTET_LENGTH`, ORDINAL_POSITION, 'YES' AS `IS_NULLABLE`, SPECIFIC_NAME FROM INFORMATION_SCHEMA.PARAMETERS WHERE " + this.getRoutineTypeConditionForGetProcedureColumns() + "SPECIFIC_SCHEMA LIKE ? AND SPECIFIC_NAME LIKE ? AND (PARAMETER_NAME LIKE ? OR PARAMETER_NAME IS NULL) " + "ORDER BY SPECIFIC_SCHEMA, SPECIFIC_NAME, ROUTINE_TYPE, ORDINAL_POSITION");
         java.sql.PreparedStatement pStmt = null;

         ResultSet var9;
         try {
            pStmt = this.prepareMetaDataSafeStatement(sqlBuf.toString());
            if (db != null) {
               pStmt.setString(1, db);
            } else {
               pStmt.setString(1, "%");
            }

            pStmt.setString(2, procedureNamePattern);
            pStmt.setString(3, columnNamePattern);
            ResultSet rs = this.executeMetadataQuery(pStmt);
            ((ResultSetInternalMethods)rs).getColumnDefinition().setFields(this.createProcedureColumnsFields());
            var9 = rs;
         } finally {
            if (pStmt != null) {
               pStmt.close();
            }

         }

         return var9;
      } catch (CJException var15) {
         throw SQLExceptionsMapping.translateException(var15, this.getExceptionInterceptor());
      }
   }

   protected String getRoutineTypeConditionForGetProcedureColumns() {
      return (Boolean)this.conn.getPropertySet().getBooleanReadableProperty("getProceduresReturnsFunctions").getValue() ? "" : "ROUTINE_TYPE = 'PROCEDURE' AND ";
   }

   public ResultSet getTables(String catalog, String schemaPattern, String tableNamePattern, String[] types) throws SQLException {
      try {
         if (catalog == null && this.nullCatalogMeansCurrent) {
            catalog = this.database;
         }

         if (tableNamePattern == null) {
            if (!this.nullNamePatternMatchesAll) {
               throw SQLError.createSQLException(Messages.getString("DatabaseMetaData.13"), "S1009", this.getExceptionInterceptor());
            }

            tableNamePattern = "%";
         }

         String tmpCat = "";
         if (catalog != null && catalog.length() != 0) {
            tmpCat = catalog;
         } else if (this.nullCatalogMeansCurrent) {
            tmpCat = this.database;
         }

         List<String> parseList = StringUtils.splitDBdotName(tableNamePattern, tmpCat, this.quotedId, this.conn.isNoBackslashEscapesSet());
         String tableNamePat;
         if (parseList.size() == 2) {
            tableNamePat = (String)parseList.get(1);
         } else {
            tableNamePat = tableNamePattern;
         }

         java.sql.PreparedStatement pStmt = null;
         String sql = "SELECT TABLE_SCHEMA AS TABLE_CAT, NULL AS TABLE_SCHEM, TABLE_NAME, CASE WHEN TABLE_TYPE='BASE TABLE' THEN CASE WHEN TABLE_SCHEMA = 'mysql' OR TABLE_SCHEMA = 'performance_schema' THEN 'SYSTEM TABLE' ELSE 'TABLE' END WHEN TABLE_TYPE='TEMPORARY' THEN 'LOCAL_TEMPORARY' ELSE TABLE_TYPE END AS TABLE_TYPE, TABLE_COMMENT AS REMARKS, NULL AS TYPE_CAT, NULL AS TYPE_SCHEM, NULL AS TYPE_NAME, NULL AS SELF_REFERENCING_COL_NAME, NULL AS REF_GENERATION FROM INFORMATION_SCHEMA.TABLES WHERE ";
         boolean operatingOnInformationSchema = "information_schema".equalsIgnoreCase(catalog);
         if (catalog != null) {
            if (!operatingOnInformationSchema && (StringUtils.indexOfIgnoreCase(0, catalog, "%") != -1 || StringUtils.indexOfIgnoreCase(0, catalog, "_") != -1)) {
               sql = sql + "TABLE_SCHEMA LIKE ? ";
            } else {
               sql = sql + "TABLE_SCHEMA = ? ";
            }
         } else {
            sql = sql + "TABLE_SCHEMA LIKE ? ";
         }

         if (tableNamePat != null) {
            if (StringUtils.indexOfIgnoreCase(0, tableNamePat, "%") == -1 && StringUtils.indexOfIgnoreCase(0, tableNamePat, "_") == -1) {
               sql = sql + "AND TABLE_NAME = ? ";
            } else {
               sql = sql + "AND TABLE_NAME LIKE ? ";
            }
         } else {
            sql = sql + "AND TABLE_NAME LIKE ? ";
         }

         sql = sql + "HAVING TABLE_TYPE IN (?,?,?,?,?) ";
         sql = sql + "ORDER BY TABLE_TYPE, TABLE_SCHEMA, TABLE_NAME";

         ResultSet var22;
         try {
            pStmt = this.prepareMetaDataSafeStatement(sql);
            if (catalog != null) {
               pStmt.setString(1, catalog);
            } else {
               pStmt.setString(1, "%");
            }

            pStmt.setString(2, tableNamePat);
            int i;
            if (types != null && types.length != 0) {
               int idx;
               for(idx = 0; idx < 5; ++idx) {
                  pStmt.setNull(3 + idx, MysqlType.VARCHAR.getJdbcType());
               }

               idx = 3;

               for(i = 0; i < types.length; ++i) {
                  DatabaseMetaData.TableType tableType = DatabaseMetaData.TableType.getTableTypeEqualTo(types[i]);
                  if (tableType != DatabaseMetaData.TableType.UNKNOWN) {
                     pStmt.setString(idx++, tableType.getName());
                  }
               }
            } else {
               DatabaseMetaData.TableType[] tableTypes = DatabaseMetaData.TableType.values();

               for(i = 0; i < 5; ++i) {
                  pStmt.setString(3 + i, tableTypes[i].getName());
               }
            }

            ResultSet rs = this.executeMetadataQuery(pStmt);
            ((ResultSetInternalMethods)rs).setColumnDefinition(this.createTablesFields());
            var22 = rs;
         } finally {
            if (pStmt != null) {
               pStmt.close();
            }

         }

         return var22;
      } catch (CJException var19) {
         throw SQLExceptionsMapping.translateException(var19, this.getExceptionInterceptor());
      }
   }

   public ResultSet getVersionColumns(String catalog, String schema, String table) throws SQLException {
      try {
         if (catalog == null && this.nullCatalogMeansCurrent) {
            catalog = this.database;
         }

         if (table == null) {
            throw SQLError.createSQLException(Messages.getString("DatabaseMetaData.2"), "S1009", this.getExceptionInterceptor());
         } else {
            StringBuilder sqlBuf = new StringBuilder("SELECT NULL AS SCOPE, COLUMN_NAME, ");
            this.appendJdbcTypeMappingQuery(sqlBuf, "DATA_TYPE", "COLUMN_TYPE");
            sqlBuf.append(" AS DATA_TYPE, ");
            sqlBuf.append("UPPER(COLUMN_TYPE) AS TYPE_NAME, ");
            sqlBuf.append("CASE WHEN LCASE(DATA_TYPE)='date' THEN 10 WHEN LCASE(DATA_TYPE)='time' THEN 8 WHEN LCASE(DATA_TYPE)='datetime' THEN 19 WHEN LCASE(DATA_TYPE)='timestamp' THEN 19 WHEN CHARACTER_MAXIMUM_LENGTH IS NULL THEN NUMERIC_PRECISION WHEN CHARACTER_MAXIMUM_LENGTH > 2147483647 THEN 2147483647 ELSE CHARACTER_MAXIMUM_LENGTH END AS COLUMN_SIZE, ");
            sqlBuf.append(maxBufferSize + " AS BUFFER_LENGTH,NUMERIC_SCALE AS DECIMAL_DIGITS, " + Integer.toString(1) + " AS PSEUDO_COLUMN FROM INFORMATION_SCHEMA.COLUMNS " + "WHERE TABLE_SCHEMA LIKE ? AND TABLE_NAME LIKE ? AND EXTRA LIKE '%on update CURRENT_TIMESTAMP%'");
            java.sql.PreparedStatement pStmt = null;

            ResultSet var7;
            try {
               pStmt = this.prepareMetaDataSafeStatement(sqlBuf.toString());
               if (catalog != null) {
                  pStmt.setString(1, catalog);
               } else {
                  pStmt.setString(1, "%");
               }

               pStmt.setString(2, table);
               ResultSet rs = this.executeMetadataQuery(pStmt);
               ((ResultSetInternalMethods)rs).getColumnDefinition().setFields(new Field[]{new Field("", "SCOPE", this.getMetadataCollationIndex(), this.getMetadataEncoding(), MysqlType.SMALLINT, 5), new Field("", "COLUMN_NAME", this.getMetadataCollationIndex(), this.getMetadataEncoding(), MysqlType.CHAR, 32), new Field("", "DATA_TYPE", this.getMetadataCollationIndex(), this.getMetadataEncoding(), MysqlType.INT, 5), new Field("", "TYPE_NAME", this.getMetadataCollationIndex(), this.getMetadataEncoding(), MysqlType.CHAR, 16), new Field("", "COLUMN_SIZE", this.getMetadataCollationIndex(), this.getMetadataEncoding(), MysqlType.INT, 16), new Field("", "BUFFER_LENGTH", this.getMetadataCollationIndex(), this.getMetadataEncoding(), MysqlType.INT, 16), new Field("", "DECIMAL_DIGITS", this.getMetadataCollationIndex(), this.getMetadataEncoding(), MysqlType.SMALLINT, 16), new Field("", "PSEUDO_COLUMN", this.getMetadataCollationIndex(), this.getMetadataEncoding(), MysqlType.SMALLINT, 5)});
               var7 = rs;
            } finally {
               if (pStmt != null) {
                  pStmt.close();
               }

            }

            return var7;
         }
      } catch (CJException var13) {
         throw SQLExceptionsMapping.translateException(var13, this.getExceptionInterceptor());
      }
   }

   public ResultSet getFunctionColumns(String catalog, String schemaPattern, String functionNamePattern, String columnNamePattern) throws SQLException {
      try {
         if (functionNamePattern == null || functionNamePattern.length() == 0) {
            if (!this.nullNamePatternMatchesAll) {
               throw SQLError.createSQLException(Messages.getString("DatabaseMetaData.11"), "S1009", this.getExceptionInterceptor());
            }

            functionNamePattern = "%";
         }

         String db = null;
         if (catalog == null) {
            if (this.nullCatalogMeansCurrent) {
               db = this.database;
            }
         } else {
            db = catalog;
         }

         StringBuilder sqlBuf = new StringBuilder("SELECT SPECIFIC_SCHEMA AS FUNCTION_CAT, NULL AS `FUNCTION_SCHEM`, SPECIFIC_NAME AS `FUNCTION_NAME`, ");
         sqlBuf.append("IFNULL(PARAMETER_NAME, '') AS `COLUMN_NAME`, CASE WHEN PARAMETER_MODE = 'IN' THEN ");
         sqlBuf.append(this.getFunctionConstant(DatabaseMetaDataUsingInfoSchema.FunctionConstant.FUNCTION_COLUMN_IN));
         sqlBuf.append(" WHEN PARAMETER_MODE = 'OUT' THEN ");
         sqlBuf.append(this.getFunctionConstant(DatabaseMetaDataUsingInfoSchema.FunctionConstant.FUNCTION_COLUMN_OUT));
         sqlBuf.append(" WHEN PARAMETER_MODE = 'INOUT' THEN ");
         sqlBuf.append(this.getFunctionConstant(DatabaseMetaDataUsingInfoSchema.FunctionConstant.FUNCTION_COLUMN_INOUT));
         sqlBuf.append(" WHEN ORDINAL_POSITION = 0 THEN ");
         sqlBuf.append(this.getFunctionConstant(DatabaseMetaDataUsingInfoSchema.FunctionConstant.FUNCTION_COLUMN_RETURN));
         sqlBuf.append(" ELSE ");
         sqlBuf.append(this.getFunctionConstant(DatabaseMetaDataUsingInfoSchema.FunctionConstant.FUNCTION_COLUMN_UNKNOWN));
         sqlBuf.append(" END AS `COLUMN_TYPE`, ");
         this.appendJdbcTypeMappingQuery(sqlBuf, "DATA_TYPE", "DTD_IDENTIFIER");
         sqlBuf.append(" AS `DATA_TYPE`, ");
         sqlBuf.append("UPPER(CASE WHEN LOCATE('UNSIGNED', UPPER(DATA_TYPE)) != 0 AND LOCATE('UNSIGNED', UPPER(DATA_TYPE)) = 0 THEN CONCAT(DATA_TYPE, ' UNSIGNED') ELSE DATA_TYPE END) AS `TYPE_NAME`,");
         sqlBuf.append("NUMERIC_PRECISION AS `PRECISION`, ");
         sqlBuf.append("CASE WHEN LCASE(DATA_TYPE)='date' THEN 10 WHEN LCASE(DATA_TYPE)='time' THEN 8 WHEN LCASE(DATA_TYPE)='datetime' THEN 19 WHEN LCASE(DATA_TYPE)='timestamp' THEN 19 WHEN CHARACTER_MAXIMUM_LENGTH IS NULL THEN NUMERIC_PRECISION WHEN CHARACTER_MAXIMUM_LENGTH > 2147483647 THEN 2147483647 ELSE CHARACTER_MAXIMUM_LENGTH END AS LENGTH, ");
         sqlBuf.append("NUMERIC_SCALE AS `SCALE`, ");
         sqlBuf.append("10 AS RADIX,");
         sqlBuf.append(this.getFunctionConstant(DatabaseMetaDataUsingInfoSchema.FunctionConstant.FUNCTION_NULLABLE) + " AS `NULLABLE`,  NULL AS `REMARKS`, " + "CHARACTER_OCTET_LENGTH AS `CHAR_OCTET_LENGTH`,  ORDINAL_POSITION, 'YES' AS `IS_NULLABLE`, SPECIFIC_NAME " + "FROM INFORMATION_SCHEMA.PARAMETERS WHERE " + "SPECIFIC_SCHEMA LIKE ? AND SPECIFIC_NAME LIKE ? AND (PARAMETER_NAME LIKE ? OR PARAMETER_NAME IS NULL) " + "AND ROUTINE_TYPE='FUNCTION' ORDER BY SPECIFIC_SCHEMA, SPECIFIC_NAME, ORDINAL_POSITION");
         java.sql.PreparedStatement pStmt = null;

         ResultSet var9;
         try {
            pStmt = this.prepareMetaDataSafeStatement(sqlBuf.toString());
            if (db != null) {
               pStmt.setString(1, db);
            } else {
               pStmt.setString(1, "%");
            }

            pStmt.setString(2, functionNamePattern);
            pStmt.setString(3, columnNamePattern);
            ResultSet rs = this.executeMetadataQuery(pStmt);
            ((ResultSetInternalMethods)rs).getColumnDefinition().setFields(this.createFunctionColumnsFields());
            var9 = rs;
         } finally {
            if (pStmt != null) {
               pStmt.close();
            }

         }

         return var9;
      } catch (CJException var15) {
         throw SQLExceptionsMapping.translateException(var15, this.getExceptionInterceptor());
      }
   }

   protected int getFunctionConstant(DatabaseMetaDataUsingInfoSchema.FunctionConstant constant) {
      switch(constant) {
      case FUNCTION_COLUMN_IN:
         return 1;
      case FUNCTION_COLUMN_INOUT:
         return 2;
      case FUNCTION_COLUMN_OUT:
         return 3;
      case FUNCTION_COLUMN_RETURN:
         return 4;
      case FUNCTION_COLUMN_RESULT:
         return 5;
      case FUNCTION_COLUMN_UNKNOWN:
         return 0;
      case FUNCTION_NO_NULLS:
         return 0;
      case FUNCTION_NULLABLE:
         return 1;
      case FUNCTION_NULLABLE_UNKNOWN:
         return 2;
      default:
         return -1;
      }
   }

   public ResultSet getFunctions(String catalog, String schemaPattern, String functionNamePattern) throws SQLException {
      try {
         if (functionNamePattern == null || functionNamePattern.length() == 0) {
            if (!this.nullNamePatternMatchesAll) {
               throw SQLError.createSQLException(Messages.getString("DatabaseMetaData.22"), "S1009", this.getExceptionInterceptor());
            }

            functionNamePattern = "%";
         }

         String db = null;
         if (catalog == null) {
            if (this.nullCatalogMeansCurrent) {
               db = this.database;
            }
         } else {
            db = catalog;
         }

         String sql = "SELECT ROUTINE_SCHEMA AS FUNCTION_CAT, NULL AS FUNCTION_SCHEM, ROUTINE_NAME AS FUNCTION_NAME, ROUTINE_COMMENT AS REMARKS, " + this.getFunctionNoTableConstant() + " AS FUNCTION_TYPE, ROUTINE_NAME AS SPECIFIC_NAME FROM INFORMATION_SCHEMA.ROUTINES " + "WHERE ROUTINE_TYPE LIKE 'FUNCTION' AND ROUTINE_SCHEMA LIKE ? AND " + "ROUTINE_NAME LIKE ? ORDER BY FUNCTION_CAT, FUNCTION_SCHEM, FUNCTION_NAME, SPECIFIC_NAME";
         java.sql.PreparedStatement pStmt = null;

         ResultSet var8;
         try {
            pStmt = this.prepareMetaDataSafeStatement(sql);
            pStmt.setString(1, db != null ? db : "%");
            pStmt.setString(2, functionNamePattern);
            ResultSet rs = this.executeMetadataQuery(pStmt);
            ((ResultSetInternalMethods)rs).getColumnDefinition().setFields(new Field[]{new Field("", "FUNCTION_CAT", this.getMetadataCollationIndex(), this.getMetadataEncoding(), MysqlType.CHAR, 255), new Field("", "FUNCTION_SCHEM", this.getMetadataCollationIndex(), this.getMetadataEncoding(), MysqlType.CHAR, 255), new Field("", "FUNCTION_NAME", this.getMetadataCollationIndex(), this.getMetadataEncoding(), MysqlType.CHAR, 255), new Field("", "REMARKS", this.getMetadataCollationIndex(), this.getMetadataEncoding(), MysqlType.CHAR, 255), new Field("", "FUNCTION_TYPE", this.getMetadataCollationIndex(), this.getMetadataEncoding(), MysqlType.SMALLINT, 6), new Field("", "SPECIFIC_NAME", this.getMetadataCollationIndex(), this.getMetadataEncoding(), MysqlType.CHAR, 255)});
            var8 = rs;
         } finally {
            if (pStmt != null) {
               pStmt.close();
            }

         }

         return var8;
      } catch (CJException var14) {
         throw SQLExceptionsMapping.translateException(var14, this.getExceptionInterceptor());
      }
   }

   private final void appendJdbcTypeMappingQuery(StringBuilder buf, String mysqlTypeColumnName, String fullMysqlTypeColumnName) {
      buf.append("CASE ");
      MysqlType[] var4 = MysqlType.values();
      int var5 = var4.length;

      for(int var6 = 0; var6 < var5; ++var6) {
         MysqlType mysqlType = var4[var6];
         buf.append(" WHEN UPPER(");
         buf.append(mysqlTypeColumnName);
         buf.append(")='");
         buf.append(mysqlType.getName());
         buf.append("' THEN ");
         switch(mysqlType) {
         case TINYINT:
         case TINYINT_UNSIGNED:
            if (this.tinyInt1isBit) {
               buf.append("CASE");
               if (this.transformedBitIsBoolean) {
                  buf.append(" WHEN LOCATE('(1)', ");
                  buf.append(fullMysqlTypeColumnName);
                  buf.append(") != 0 THEN 16");
               } else {
                  buf.append(" WHEN LOCATE('(1)', ");
                  buf.append(fullMysqlTypeColumnName);
                  buf.append(") != 0 THEN -7");
               }

               buf.append(" ELSE -6 END ");
            } else {
               buf.append(mysqlType.getJdbcType());
            }
            break;
         default:
            buf.append(mysqlType.getJdbcType());
         }
      }

      buf.append(" WHEN UPPER(DATA_TYPE)='POINT' THEN -2");
      buf.append(" WHEN UPPER(DATA_TYPE)='LINESTRING' THEN -2");
      buf.append(" WHEN UPPER(DATA_TYPE)='POLYGON' THEN -2");
      buf.append(" WHEN UPPER(DATA_TYPE)='MULTIPOINT' THEN -2");
      buf.append(" WHEN UPPER(DATA_TYPE)='MULTILINESTRING' THEN -2");
      buf.append(" WHEN UPPER(DATA_TYPE)='MULTIPOLYGON' THEN -2");
      buf.append(" WHEN UPPER(DATA_TYPE)='GEOMETRYCOLLECTION' THEN -2");
      buf.append(" ELSE 1111");
      buf.append(" END ");
   }

   protected static enum FunctionConstant {
      FUNCTION_COLUMN_UNKNOWN,
      FUNCTION_COLUMN_IN,
      FUNCTION_COLUMN_INOUT,
      FUNCTION_COLUMN_OUT,
      FUNCTION_COLUMN_RETURN,
      FUNCTION_COLUMN_RESULT,
      FUNCTION_NO_NULLS,
      FUNCTION_NULLABLE,
      FUNCTION_NULLABLE_UNKNOWN;
   }
}
