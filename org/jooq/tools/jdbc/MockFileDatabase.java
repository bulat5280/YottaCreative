package org.jooq.tools.jdbc;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.LineNumberReader;
import java.io.Reader;
import java.io.StringReader;
import java.sql.SQLException;
import java.sql.SQLFeatureNotSupportedException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.regex.Pattern;
import org.jooq.DSLContext;
import org.jooq.SQLDialect;
import org.jooq.exception.MockFileDatabaseException;
import org.jooq.impl.DSL;
import org.jooq.tools.JooqLogger;

public class MockFileDatabase implements MockDataProvider {
   private static final JooqLogger log = JooqLogger.getLogger(MockFileDatabase.class);
   private final LineNumberReader in;
   private final Map<String, List<MockResult>> matchExactly;
   private final Map<Pattern, List<MockResult>> matchPattern;
   private final DSLContext create;
   private String nullLiteral;

   public MockFileDatabase(File file) throws IOException {
      this(file, "UTF-8");
   }

   public MockFileDatabase(File file, String encoding) throws IOException {
      this((InputStream)(new FileInputStream(file)), encoding);
   }

   public MockFileDatabase(InputStream stream) throws IOException {
      this(stream, "UTF-8");
   }

   public MockFileDatabase(InputStream stream, String encoding) throws IOException {
      this((Reader)(new InputStreamReader(stream, encoding)));
   }

   public MockFileDatabase(Reader reader) throws IOException {
      this(new LineNumberReader(reader));
   }

   public MockFileDatabase(String string) throws IOException {
      this((Reader)(new StringReader(string)));
   }

   public MockFileDatabase nullLiteral(String literal) {
      this.nullLiteral = literal;
      return this;
   }

   private MockFileDatabase(LineNumberReader reader) throws IOException {
      this.in = reader;
      this.matchExactly = new LinkedHashMap();
      this.matchPattern = new LinkedHashMap();
      this.create = DSL.using(SQLDialect.DEFAULT);
      this.load();
   }

   private void load() throws FileNotFoundException, IOException {
      ((<undefinedtype>)(new Object() {
         private StringBuilder currentSQL = new StringBuilder();
         private StringBuilder currentResult = new StringBuilder();
         private String previousSQL = null;

         private void load() throws FileNotFoundException, IOException {
            while(true) {
               try {
                  String line = this.readLine();
                  if (line != null) {
                     if (line.startsWith("#")) {
                        continue;
                     }

                     if (line.startsWith(">")) {
                        this.currentResult.append(line.substring(2));
                        this.currentResult.append("\n");
                        continue;
                     }

                     if (line.startsWith("@")) {
                        this.loadOneResult(line);
                        this.currentResult = new StringBuilder();
                        continue;
                     }

                     if (line.endsWith(";")) {
                        this.currentSQL.append(line.substring(0, line.length() - 1));
                        if (!MockFileDatabase.this.matchExactly.containsKey(this.previousSQL)) {
                           MockFileDatabase.this.matchExactly.put(this.previousSQL, (Object)null);
                        }

                        this.previousSQL = this.currentSQL.toString();
                        this.currentSQL = new StringBuilder();
                        if (MockFileDatabase.log.isDebugEnabled()) {
                           MockFileDatabase.log.debug("Loaded SQL", (Object)this.previousSQL);
                        }
                        continue;
                     }

                     if (this.currentResult.length() > 0) {
                        this.loadOneResult("");
                        this.currentResult = new StringBuilder();
                     }

                     this.currentSQL.append(line);
                     continue;
                  }

                  if (this.currentResult.length() > 0) {
                     this.loadOneResult("");
                     this.currentResult = new StringBuilder();
                  }
               } finally {
                  if (MockFileDatabase.this.in != null) {
                     MockFileDatabase.this.in.close();
                  }

               }

               return;
            }
         }

         private void loadOneResult(String line) {
            List<MockResult> results = (List)MockFileDatabase.this.matchExactly.get(this.previousSQL);
            if (results == null) {
               results = new ArrayList();
               MockFileDatabase.this.matchExactly.put(this.previousSQL, results);
            }

            MockResult mock = this.parse(line);
            ((List)results).add(mock);
            if (MockFileDatabase.log.isDebugEnabled()) {
               String comment = "Loaded Result";
               String[] var5 = mock.data.format(5).split("\n");
               int var6 = var5.length;

               for(int var7 = 0; var7 < var6; ++var7) {
                  String l = var5[var7];
                  MockFileDatabase.log.debug(comment, (Object)l);
                  comment = "";
               }
            }

         }

         private MockResult parse(String rowString) {
            int rows = 0;
            if (rowString.startsWith("@ rows:")) {
               rows = Integer.parseInt(rowString.substring(7).trim());
            }

            MockResult result = new MockResult(rows, MockFileDatabase.this.nullLiteral == null ? MockFileDatabase.this.create.fetchFromTXT(this.currentResult.toString()) : MockFileDatabase.this.create.fetchFromTXT(this.currentResult.toString(), MockFileDatabase.this.nullLiteral));
            if (rows != result.data.size()) {
               throw new MockFileDatabaseException("Rows mismatch. Declared: " + rows + ". Actual: " + result.data.size() + ".");
            } else {
               return result;
            }
         }

         private String readLine() throws IOException {
            String line;
            do {
               line = MockFileDatabase.this.in.readLine();
               if (line == null) {
                  return line;
               }

               line = line.trim();
            } while(line.length() <= 0);

            return line;
         }
      })).load();
   }

   public MockResult[] execute(MockExecuteContext ctx) throws SQLException {
      if (ctx.batch()) {
         throw new SQLFeatureNotSupportedException("Not yet supported");
      } else {
         String sql = ctx.sql();
         String inlined = null;
         List<MockResult> list = (List)this.matchExactly.get(sql);
         if (list == null) {
            inlined = this.create.query(sql, ctx.bindings()).toString();
            list = (List)this.matchExactly.get(inlined);
         }

         if (list == null) {
            Iterator var5 = this.matchPattern.entrySet().iterator();

            label30:
            while(true) {
               Entry entry;
               do {
                  if (!var5.hasNext()) {
                     break label30;
                  }

                  entry = (Entry)var5.next();
               } while(!((Pattern)entry.getKey()).matcher(sql).matches() && !((Pattern)entry.getKey()).matcher(inlined).matches());

               list = (List)entry.getValue();
            }
         }

         if (list == null) {
            throw new SQLException("Invalid SQL: " + sql);
         } else {
            return (MockResult[])list.toArray(new MockResult[list.size()]);
         }
      }
   }
}
