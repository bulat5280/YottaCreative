package org.jooq.impl;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.io.StringReader;
import java.io.UnsupportedEncodingException;
import java.nio.charset.Charset;
import java.nio.charset.CharsetDecoder;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.stream.Stream;
import org.jooq.BatchBindStep;
import org.jooq.Condition;
import org.jooq.Configuration;
import org.jooq.DSLContext;
import org.jooq.Field;
import org.jooq.InsertQuery;
import org.jooq.Loader;
import org.jooq.LoaderCSVOptionsStep;
import org.jooq.LoaderCSVStep;
import org.jooq.LoaderContext;
import org.jooq.LoaderError;
import org.jooq.LoaderFieldMapper;
import org.jooq.LoaderJSONOptionsStep;
import org.jooq.LoaderJSONStep;
import org.jooq.LoaderListenerStep;
import org.jooq.LoaderOptionsStep;
import org.jooq.LoaderRowListener;
import org.jooq.LoaderRowsStep;
import org.jooq.LoaderXMLStep;
import org.jooq.Query;
import org.jooq.Record;
import org.jooq.Select;
import org.jooq.SelectQuery;
import org.jooq.Table;
import org.jooq.exception.DataAccessException;
import org.jooq.exception.LoaderConfigurationException;
import org.jooq.tools.StringUtils;
import org.jooq.tools.csv.CSVReader;
import org.xml.sax.InputSource;

final class LoaderImpl<R extends Record> implements LoaderOptionsStep<R>, LoaderRowsStep<R>, LoaderXMLStep<R>, LoaderCSVStep<R>, LoaderCSVOptionsStep<R>, LoaderJSONStep<R>, LoaderJSONOptionsStep<R>, Loader<R> {
   private static final int ON_DUPLICATE_KEY_ERROR = 0;
   private static final int ON_DUPLICATE_KEY_IGNORE = 1;
   private static final int ON_DUPLICATE_KEY_UPDATE = 2;
   private static final int ON_ERROR_ABORT = 0;
   private static final int ON_ERROR_IGNORE = 1;
   private static final int COMMIT_NONE = 0;
   private static final int COMMIT_AFTER = 1;
   private static final int COMMIT_ALL = 2;
   private static final int BATCH_NONE = 0;
   private static final int BATCH_AFTER = 1;
   private static final int BATCH_ALL = 2;
   private static final int BULK_NONE = 0;
   private static final int BULK_AFTER = 1;
   private static final int BULK_ALL = 2;
   private static final int CONTENT_CSV = 0;
   private static final int CONTENT_XML = 1;
   private static final int CONTENT_JSON = 2;
   private static final int CONTENT_ARRAYS = 3;
   private final DSLContext create;
   private final Configuration configuration;
   private final Table<R> table;
   private int onDuplicate = 0;
   private int onError = 0;
   private int commit = 0;
   private int commitAfter = 1;
   private int batch = 0;
   private int batchAfter = 1;
   private int bulk = 0;
   private int bulkAfter = 1;
   private int content = 0;
   private final LoaderImpl<R>.InputDelay data = new LoaderImpl.InputDelay();
   private Iterator<? extends Object[]> arrays;
   private int ignoreRows = 1;
   private char quote = '"';
   private char separator = ',';
   private String nullString = null;
   private Field<?>[] source;
   private Field<?>[] fields;
   private LoaderFieldMapper fieldMapper;
   private boolean[] primaryKey;
   private LoaderRowListener listener;
   private LoaderContext result = new LoaderImpl.DefaultLoaderContext();
   private int ignored;
   private int processed;
   private int stored;
   private int executed;
   private int buffered;
   private final List<LoaderError> errors;

   LoaderImpl(Configuration configuration, Table<R> table) {
      this.create = DSL.using(configuration);
      this.configuration = configuration;
      this.table = table;
      this.errors = new ArrayList();
   }

   public final LoaderImpl<R> onDuplicateKeyError() {
      this.onDuplicate = 0;
      return this;
   }

   public final LoaderImpl<R> onDuplicateKeyIgnore() {
      if (this.table.getPrimaryKey() == null) {
         throw new IllegalStateException("ON DUPLICATE KEY IGNORE only works on tables with explicit primary keys. Table is not updatable : " + this.table);
      } else {
         this.onDuplicate = 1;
         return this;
      }
   }

   public final LoaderImpl<R> onDuplicateKeyUpdate() {
      if (this.table.getPrimaryKey() == null) {
         throw new IllegalStateException("ON DUPLICATE KEY UPDATE only works on tables with explicit primary keys. Table is not updatable : " + this.table);
      } else {
         this.onDuplicate = 2;
         return this;
      }
   }

   public final LoaderImpl<R> onErrorIgnore() {
      this.onError = 1;
      return this;
   }

   public final LoaderImpl<R> onErrorAbort() {
      this.onError = 0;
      return this;
   }

   public final LoaderImpl<R> commitEach() {
      this.commit = 1;
      return this;
   }

   public final LoaderImpl<R> commitAfter(int number) {
      this.commit = 1;
      this.commitAfter = number;
      return this;
   }

   public final LoaderImpl<R> commitAll() {
      this.commit = 2;
      return this;
   }

   public final LoaderImpl<R> commitNone() {
      this.commit = 0;
      return this;
   }

   public final LoaderImpl<R> batchAll() {
      this.batch = 2;
      return this;
   }

   public final LoaderImpl<R> batchNone() {
      this.batch = 0;
      return this;
   }

   public final LoaderImpl<R> batchAfter(int number) {
      this.batch = 1;
      this.batchAfter = number;
      return this;
   }

   public final LoaderImpl<R> bulkAll() {
      this.bulk = 2;
      return this;
   }

   public final LoaderImpl<R> bulkNone() {
      this.bulk = 0;
      return this;
   }

   public final LoaderImpl<R> bulkAfter(int number) {
      this.bulk = 1;
      this.bulkAfter = number;
      return this;
   }

   public final LoaderRowsStep<R> loadArrays(Object[]... a) {
      return this.loadArrays((Iterable)Arrays.asList(a));
   }

   public final LoaderRowsStep<R> loadArrays(Iterable<? extends Object[]> a) {
      return this.loadArrays(a.iterator());
   }

   public final LoaderRowsStep<R> loadArrays(Iterator<? extends Object[]> a) {
      this.content = 3;
      this.arrays = a;
      return this;
   }

   public final LoaderRowsStep<R> loadRecords(Record... records) {
      return this.loadRecords((Iterable)Arrays.asList(records));
   }

   public final LoaderRowsStep<R> loadRecords(Iterable<? extends Record> records) {
      return this.loadRecords(records.iterator());
   }

   public final LoaderRowsStep<R> loadRecords(Iterator<? extends Record> records) {
      return this.loadArrays((Iterator)(new MappingIterator(records, new MappingIterator.Function<Record, Object[]>() {
         public final Object[] map(Record value) {
            if (value == null) {
               return null;
            } else {
               if (LoaderImpl.this.source == null) {
                  LoaderImpl.this.source = value.fields();
               }

               return value.intoArray();
            }
         }
      })));
   }

   public final LoaderRowsStep<R> loadArrays(Stream<? extends Object[]> a) {
      return this.loadArrays(a.iterator());
   }

   public final LoaderRowsStep<R> loadRecords(Stream<? extends Record> records) {
      return this.loadRecords(records.iterator());
   }

   public final LoaderImpl<R> loadCSV(File file) {
      this.content = 0;
      this.data.file = file;
      return this;
   }

   public final LoaderImpl<R> loadCSV(File file, String charsetName) {
      this.data.charsetName = charsetName;
      return this.loadCSV(file);
   }

   public final LoaderImpl<R> loadCSV(File file, Charset cs) {
      this.data.cs = cs;
      return this.loadCSV(file);
   }

   public final LoaderImpl<R> loadCSV(File file, CharsetDecoder dec) {
      this.data.dec = dec;
      return this.loadCSV(file);
   }

   public final LoaderImpl<R> loadCSV(String csv) {
      return this.loadCSV((Reader)(new StringReader(csv)));
   }

   public final LoaderImpl<R> loadCSV(InputStream stream) {
      return this.loadCSV((Reader)(new InputStreamReader(stream)));
   }

   public final LoaderImpl<R> loadCSV(InputStream stream, String charsetName) throws UnsupportedEncodingException {
      return this.loadCSV((Reader)(new InputStreamReader(stream, charsetName)));
   }

   public final LoaderImpl<R> loadCSV(InputStream stream, Charset cs) {
      return this.loadCSV((Reader)(new InputStreamReader(stream, cs)));
   }

   public final LoaderImpl<R> loadCSV(InputStream stream, CharsetDecoder dec) {
      return this.loadCSV((Reader)(new InputStreamReader(stream, dec)));
   }

   public final LoaderImpl<R> loadCSV(Reader reader) {
      this.content = 0;
      this.data.reader = new BufferedReader(reader);
      return this;
   }

   public final LoaderImpl<R> loadXML(File file) {
      this.content = 1;
      this.data.file = file;
      return this;
   }

   public final LoaderImpl<R> loadXML(File file, String charsetName) {
      this.data.charsetName = charsetName;
      return this.loadXML(file);
   }

   public final LoaderImpl<R> loadXML(File file, Charset cs) {
      this.data.cs = cs;
      return this.loadXML(file);
   }

   public final LoaderImpl<R> loadXML(File file, CharsetDecoder dec) {
      this.data.dec = dec;
      return this.loadXML(file);
   }

   public final LoaderImpl<R> loadXML(String xml) {
      return this.loadXML((Reader)(new StringReader(xml)));
   }

   public final LoaderImpl<R> loadXML(InputStream stream) {
      return this.loadXML((Reader)(new InputStreamReader(stream)));
   }

   public final LoaderImpl<R> loadXML(InputStream stream, String charsetName) throws UnsupportedEncodingException {
      return this.loadXML((Reader)(new InputStreamReader(stream, charsetName)));
   }

   public final LoaderImpl<R> loadXML(InputStream stream, Charset cs) {
      return this.loadXML((Reader)(new InputStreamReader(stream, cs)));
   }

   public final LoaderImpl<R> loadXML(InputStream stream, CharsetDecoder dec) {
      return this.loadXML((Reader)(new InputStreamReader(stream, dec)));
   }

   public final LoaderImpl<R> loadXML(Reader reader) {
      this.content = 1;
      throw new UnsupportedOperationException("This is not yet implemented");
   }

   public final LoaderImpl<R> loadXML(InputSource source) {
      this.content = 1;
      throw new UnsupportedOperationException("This is not yet implemented");
   }

   public final LoaderImpl<R> loadJSON(File file) {
      this.content = 2;
      this.data.file = file;
      return this;
   }

   public final LoaderImpl<R> loadJSON(File file, String charsetName) {
      this.data.charsetName = charsetName;
      return this.loadJSON(file);
   }

   public final LoaderImpl<R> loadJSON(File file, Charset cs) {
      this.data.cs = cs;
      return this.loadJSON(file);
   }

   public final LoaderImpl<R> loadJSON(File file, CharsetDecoder dec) {
      this.data.dec = dec;
      return this.loadJSON(file);
   }

   public final LoaderImpl<R> loadJSON(String json) {
      return this.loadJSON((Reader)(new StringReader(json)));
   }

   public final LoaderImpl<R> loadJSON(InputStream stream) {
      return this.loadJSON((Reader)(new InputStreamReader(stream)));
   }

   public final LoaderImpl<R> loadJSON(InputStream stream, String charsetName) throws UnsupportedEncodingException {
      return this.loadJSON((Reader)(new InputStreamReader(stream, charsetName)));
   }

   public final LoaderImpl<R> loadJSON(InputStream stream, Charset cs) {
      return this.loadJSON((Reader)(new InputStreamReader(stream, cs)));
   }

   public final LoaderImpl<R> loadJSON(InputStream stream, CharsetDecoder dec) {
      return this.loadJSON((Reader)(new InputStreamReader(stream, dec)));
   }

   public final LoaderImpl<R> loadJSON(Reader reader) {
      this.content = 2;
      this.data.reader = new BufferedReader(reader);
      return this;
   }

   public final LoaderImpl<R> fields(Field<?>... f) {
      this.fields = f;
      this.primaryKey = new boolean[f.length];
      if (this.table.getPrimaryKey() != null) {
         for(int i = 0; i < this.fields.length; ++i) {
            if (this.fields[i] != null && this.table.getPrimaryKey().getFields().contains(this.fields[i])) {
               this.primaryKey[i] = true;
            }
         }
      }

      return this;
   }

   public final LoaderImpl<R> fields(Collection<? extends Field<?>> f) {
      return this.fields((Field[])f.toArray(Tools.EMPTY_FIELD));
   }

   public final LoaderListenerStep<R> fields(LoaderFieldMapper mapper) {
      this.fieldMapper = mapper;
      return this;
   }

   private final void fields0(Object[] row) {
      Field<?>[] f = new Field[row.length];
      if (this.source == null) {
         this.source = Tools.fields(row.length);
      }

      for(final int i = 0; i < row.length; ++i) {
         f[i] = this.fieldMapper.map(new LoaderFieldMapper.LoaderFieldContext() {
            public int index() {
               return i;
            }

            public Field<?> field() {
               return LoaderImpl.this.source[i];
            }
         });
      }

      this.fields(f);
   }

   public final LoaderImpl<R> ignoreRows(int number) {
      this.ignoreRows = number;
      return this;
   }

   public final LoaderImpl<R> quote(char q) {
      this.quote = q;
      return this;
   }

   public final LoaderImpl<R> separator(char s) {
      this.separator = s;
      return this;
   }

   public final LoaderImpl<R> nullString(String n) {
      this.nullString = n;
      return this;
   }

   public final LoaderImpl<R> onRow(LoaderRowListener l) {
      this.listener = l;
      return this;
   }

   public final LoaderImpl<R> execute() throws IOException {
      this.checkFlags();
      if (this.content == 0) {
         this.executeCSV();
      } else {
         if (this.content == 1) {
            throw new UnsupportedOperationException();
         }

         if (this.content == 2) {
            this.executeJSON();
         } else {
            if (this.content != 3) {
               throw new IllegalStateException();
            }

            this.executeRows();
         }
      }

      return this;
   }

   private void checkFlags() {
      if (this.batch != 0 && this.onDuplicate == 1) {
         throw new LoaderConfigurationException("Cannot apply batch loading with onDuplicateKeyIgnore flag. Turn off either flag.");
      } else if (this.bulk != 0 && this.onDuplicate != 0) {
         throw new LoaderConfigurationException("Cannot apply bulk loading with onDuplicateKey flags. Turn off either flag.");
      }
   }

   private void executeJSON() throws IOException {
      JSONReader reader = null;

      try {
         reader = new JSONReader(this.data.reader());
         this.source = Tools.fieldsByName(reader.getFields());
         List<String[]> allRecords = reader.readAll();
         this.executeSQL(allRecords.iterator());
      } catch (SQLException var6) {
         throw Tools.translate((String)null, var6);
      } finally {
         if (reader != null) {
            reader.close();
         }

      }

   }

   private final void executeCSV() throws IOException {
      CSVReader reader = null;

      try {
         if (this.ignoreRows == 1) {
            reader = new CSVReader(this.data.reader(), this.separator, this.quote, 0);
            this.source = Tools.fieldsByName(reader.next());
         } else {
            reader = new CSVReader(this.data.reader(), this.separator, this.quote, this.ignoreRows);
         }

         this.executeSQL(reader);
      } catch (SQLException var6) {
         throw Tools.translate((String)null, var6);
      } finally {
         if (reader != null) {
            reader.close();
         }

      }

   }

   private void executeRows() {
      try {
         this.executeSQL(this.arrays);
      } catch (SQLException var2) {
         throw Tools.translate((String)null, var2);
      }
   }

   private void executeSQL(Iterator<? extends Object[]> iterator) throws SQLException {
      Object[] row = null;
      BatchBindStep bind = null;
      InsertQuery insert = null;

      while(true) {
         if (!iterator.hasNext() || (row = (Object[])iterator.next()) == null) {
            if (this.buffered != 0) {
               try {
                  if (bind != null) {
                     bind.execute();
                  }

                  if (insert != null) {
                     insert.execute();
                  }

                  this.stored += this.buffered;
                  ++this.executed;
                  this.buffered = 0;
               } catch (DataAccessException var14) {
                  this.errors.add(new LoaderErrorImpl(var14, row, this.processed - 1, insert));
                  this.ignored += this.buffered;
                  this.buffered = 0;
               }

               if (this.onError == 0) {
               }
            }
            break;
         }

         try {
            if (this.fields == null) {
               this.fields0(row);
            }

            int i;
            for(i = 0; i < row.length; ++i) {
               if (StringUtils.equals(this.nullString, row[i])) {
                  row[i] = null;
               }
            }

            ++this.processed;
            if (this.onDuplicate == 1) {
               SelectQuery<R> select = this.create.selectQuery(this.table);

               for(int i = 0; i < row.length; ++i) {
                  if (i < this.fields.length && this.primaryKey[i]) {
                     select.addConditions(this.getCondition(this.fields[i], row[i]));
                  }
               }

               try {
                  if (this.create.fetchExists((Select)select)) {
                     ++this.ignored;
                     continue;
                  }
               } catch (DataAccessException var15) {
                  this.errors.add(new LoaderErrorImpl(var15, row, this.processed - 1, select));
               }
            }

            ++this.buffered;
            if (insert == null) {
               insert = this.create.insertQuery(this.table);
            }

            for(i = 0; i < row.length; ++i) {
               if (i < this.fields.length && this.fields[i] != null) {
                  this.addValue0(insert, this.fields[i], row[i]);
               }
            }

            if (this.onDuplicate == 2) {
               insert.onDuplicateKeyUpdate(true);

               for(i = 0; i < row.length; ++i) {
                  if (i < this.fields.length && this.fields[i] != null && !this.primaryKey[i]) {
                     this.addValueForUpdate0(insert, this.fields[i], row[i]);
                  }
               }
            } else if (this.onDuplicate == 0) {
            }

            try {
               if (this.bulk != 0 && (this.bulk == 2 || this.processed % this.bulkAfter != 0)) {
                  insert.newRecord();
               } else {
                  if (this.batch != 0) {
                     if (bind == null) {
                        bind = this.create.batch((Query)insert);
                     }

                     bind.bind(insert.getBindValues().toArray());
                     insert = null;
                     if (this.batch == 2 || this.processed % (this.bulkAfter * this.batchAfter) != 0) {
                        continue;
                     }
                  }

                  if (bind != null) {
                     bind.execute();
                  } else if (insert != null) {
                     insert.execute();
                  }

                  this.stored += this.buffered;
                  ++this.executed;
                  this.buffered = 0;
                  bind = null;
                  insert = null;
                  if (this.commit == 1 && this.processed % this.batchAfter == 0 && this.processed / this.batchAfter % this.commitAfter == 0) {
                     this.commit();
                  }
               }
            } catch (DataAccessException var16) {
               this.errors.add(new LoaderErrorImpl(var16, row, this.processed - 1, insert));
               this.ignored += this.buffered;
               this.buffered = 0;
               if (this.onError == 0) {
                  break;
               }
            }
         } finally {
            if (this.listener != null) {
               this.listener.row(this.result);
            }

         }
      }

      try {
         if (this.commit == 2) {
            if (!this.errors.isEmpty()) {
               this.stored = 0;
               this.rollback();
            } else {
               this.commit();
            }
         } else if (this.commit == 1) {
            this.commit();
         }
      } catch (DataAccessException var13) {
         this.errors.add(new LoaderErrorImpl(var13, (Object[])null, this.processed - 1, (Query)null));
      }

   }

   private void commit() throws SQLException {
      Connection connection = this.configuration.connectionProvider().acquire();

      try {
         connection.commit();
      } finally {
         this.configuration.connectionProvider().release(connection);
      }

   }

   private void rollback() throws SQLException {
      Connection connection = this.configuration.connectionProvider().acquire();

      try {
         connection.rollback();
      } finally {
         this.configuration.connectionProvider().release(connection);
      }

   }

   private <T> void addValue0(InsertQuery<R> insert, Field<T> field, Object row) {
      insert.addValue(field, field.getDataType().convert(row));
   }

   private <T> void addValueForUpdate0(InsertQuery<R> insert, Field<T> field, Object row) {
      insert.addValueForUpdate(field, field.getDataType().convert(row));
   }

   private <T> Condition getCondition(Field<T> field, Object string) {
      return field.equal(field.getDataType().convert(string));
   }

   public final List<LoaderError> errors() {
      return this.errors;
   }

   public final int processed() {
      return this.processed;
   }

   public final int executed() {
      return this.executed;
   }

   public final int ignored() {
      return this.ignored;
   }

   public final int stored() {
      return this.stored;
   }

   public final LoaderContext result() {
      return this.result;
   }

   private class InputDelay {
      BufferedReader reader;
      File file;
      String charsetName;
      Charset cs;
      CharsetDecoder dec;

      private InputDelay() {
      }

      BufferedReader reader() throws IOException {
         if (this.reader != null) {
            return this.reader;
         } else if (this.file != null) {
            try {
               if (this.charsetName != null) {
                  return new BufferedReader(new InputStreamReader(new FileInputStream(this.file), this.charsetName));
               } else if (this.cs != null) {
                  return new BufferedReader(new InputStreamReader(new FileInputStream(this.file), this.cs));
               } else {
                  return this.dec != null ? new BufferedReader(new InputStreamReader(new FileInputStream(this.file), this.dec)) : new BufferedReader(new InputStreamReader(new FileInputStream(this.file)));
               }
            } catch (Exception var2) {
               throw new IOException(var2);
            }
         } else {
            return null;
         }
      }

      // $FF: synthetic method
      InputDelay(Object x1) {
         this();
      }
   }

   private class DefaultLoaderContext implements LoaderContext {
      private DefaultLoaderContext() {
      }

      public final List<LoaderError> errors() {
         return LoaderImpl.this.errors;
      }

      public final int processed() {
         return LoaderImpl.this.processed;
      }

      public final int executed() {
         return LoaderImpl.this.executed;
      }

      public final int ignored() {
         return LoaderImpl.this.ignored;
      }

      public final int stored() {
         return LoaderImpl.this.stored;
      }

      // $FF: synthetic method
      DefaultLoaderContext(Object x1) {
         this();
      }
   }
}
