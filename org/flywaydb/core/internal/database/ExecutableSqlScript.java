package org.flywaydb.core.internal.database;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.Reader;
import java.io.StringReader;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import org.flywaydb.core.api.FlywayException;
import org.flywaydb.core.api.errorhandler.Warning;
import org.flywaydb.core.api.logging.Log;
import org.flywaydb.core.api.logging.LogFactory;
import org.flywaydb.core.internal.sqlscript.FlywaySqlScriptException;
import org.flywaydb.core.internal.sqlscript.SqlStatement;
import org.flywaydb.core.internal.util.StringUtils;
import org.flywaydb.core.internal.util.jdbc.ContextImpl;
import org.flywaydb.core.internal.util.jdbc.JdbcTemplate;
import org.flywaydb.core.internal.util.jdbc.Result;
import org.flywaydb.core.internal.util.scanner.Resource;

public abstract class ExecutableSqlScript<C extends ContextImpl> extends SqlScript {
   private static final Log LOG = LogFactory.getLog(ExecutableSqlScript.class);
   private final boolean mixed;
   private final List<SqlStatement<C>> sqlStatements;
   private boolean transactionalStatementFound;
   private boolean nonTransactionalStatementFound;

   public ExecutableSqlScript(Resource resource, String sqlScriptSource, boolean mixed) {
      super(resource);
      this.mixed = mixed;
      this.sqlStatements = this.parse(sqlScriptSource);
   }

   public boolean executeInTransaction() {
      return !this.nonTransactionalStatementFound;
   }

   public List<SqlStatement<C>> getSqlStatements() {
      return this.sqlStatements;
   }

   public void execute(JdbcTemplate jdbcTemplate) {
      Iterator var2 = this.sqlStatements.iterator();

      while(var2.hasNext()) {
         SqlStatement<C> sqlStatement = (SqlStatement)var2.next();
         C context = this.createContext();
         String sql = sqlStatement.getSql();
         LOG.debug("Executing SQL: " + sql);

         try {
            List<Result> results = sqlStatement.execute(context, jdbcTemplate);
            this.printWarnings(context);
            Iterator var7 = results.iterator();

            while(var7.hasNext()) {
               Result result = (Result)var7.next();
               if (result.getUpdateCount() != -1L) {
                  LOG.debug("Update Count: " + result.getUpdateCount());
               }
            }
         } catch (SQLException var9) {
            this.printWarnings(context);
            this.handleException(var9, sqlStatement, context);
         }
      }

   }

   protected void handleException(SQLException e, SqlStatement sqlStatement, C context) {
      throw new FlywaySqlScriptException(this.resource, sqlStatement, e);
   }

   protected C createContext() {
      return new ContextImpl();
   }

   private void printWarnings(C context) {
      Iterator var2 = context.getWarnings().iterator();

      while(var2.hasNext()) {
         Warning warning = (Warning)var2.next();
         if ("00000".equals(warning.getState())) {
            LOG.info("DB: " + warning.getMessage());
         } else {
            LOG.warn("DB: " + warning.getMessage() + " (SQL State: " + warning.getState() + " - Error Code: " + warning.getCode() + ")");
         }
      }

   }

   public List<SqlStatement<C>> parse(String sqlScriptSource) {
      if (this.resource != null) {
         LOG.debug("Parsing " + this.resource.getFilename() + " ...");
      }

      return this.linesToStatements(this.readLines(new StringReader(sqlScriptSource)));
   }

   public List<SqlStatement<C>> linesToStatements(List<String> lines) {
      List<SqlStatement<C>> statements = new ArrayList();
      Delimiter nonStandardDelimiter = null;
      SqlStatementBuilder sqlStatementBuilder = this.createSqlStatementBuilder();

      for(int lineNumber = 1; lineNumber <= lines.size(); ++lineNumber) {
         String line = (String)lines.get(lineNumber - 1);
         if (sqlStatementBuilder.isEmpty()) {
            if (!StringUtils.hasText(line)) {
               continue;
            }

            Delimiter newDelimiter = sqlStatementBuilder.extractNewDelimiterFromLine(line);
            if (newDelimiter != null) {
               nonStandardDelimiter = newDelimiter;
               continue;
            }

            sqlStatementBuilder.setLineNumber(lineNumber);
            if (nonStandardDelimiter != null) {
               sqlStatementBuilder.setDelimiter(nonStandardDelimiter);
            }
         }

         try {
            sqlStatementBuilder.addLine(line);
         } catch (Exception var8) {
            throw new FlywayException("Flyway parsing bug (" + var8.getMessage() + ") at line " + lineNumber + ": " + line, var8);
         }

         if (sqlStatementBuilder.canDiscard()) {
            sqlStatementBuilder = this.createSqlStatementBuilder();
         } else if (sqlStatementBuilder.isTerminated()) {
            this.addStatement(statements, sqlStatementBuilder);
            sqlStatementBuilder = this.createSqlStatementBuilder();
         }
      }

      if (!sqlStatementBuilder.isEmpty()) {
         this.addStatement(statements, sqlStatementBuilder);
      }

      return statements;
   }

   protected abstract SqlStatementBuilder createSqlStatementBuilder();

   private void addStatement(List<SqlStatement<C>> statements, SqlStatementBuilder sqlStatementBuilder) {
      SqlStatement<C> sqlStatement = sqlStatementBuilder.getSqlStatement();
      statements.add(sqlStatement);
      if (sqlStatementBuilder.executeInTransaction()) {
         this.transactionalStatementFound = true;
      } else {
         this.nonTransactionalStatementFound = true;
      }

      if (!this.mixed && this.transactionalStatementFound && this.nonTransactionalStatementFound) {
         throw new FlywayException("Detected both transactional and non-transactional statements within the same migration (even though mixed is false). Offending statement found at line " + sqlStatement.getLineNumber() + ": " + sqlStatement.getSql() + (sqlStatementBuilder.executeInTransaction() ? "" : " [non-transactional]"));
      } else {
         LOG.debug("Found statement at line " + sqlStatement.getLineNumber() + ": " + sqlStatement.getSql() + (sqlStatementBuilder.executeInTransaction() ? "" : " [non-transactional]"));
      }
   }

   private List<String> readLines(Reader reader) {
      List<String> lines = new ArrayList();
      BufferedReader bufferedReader = new BufferedReader(reader);

      try {
         String line;
         while((line = bufferedReader.readLine()) != null) {
            lines.add(line);
         }

         return lines;
      } catch (IOException var7) {
         String message = this.resource == null ? "Unable to parse lines" : "Unable to parse " + this.resource.getLocation() + " (" + this.resource.getLocationOnDisk() + ")";
         throw new FlywayException(message, var7);
      }
   }
}
