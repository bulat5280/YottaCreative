package org.jooq.impl;

import java.io.IOException;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.StringWriter;
import java.io.Writer;
import java.sql.Date;
import java.sql.ResultSet;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.ListIterator;
import java.util.Map;
import java.util.Set;
import javax.xml.bind.DatatypeConverter;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import org.jooq.Attachable;
import org.jooq.AttachableInternal;
import org.jooq.CSVFormat;
import org.jooq.Configuration;
import org.jooq.Converter;
import org.jooq.DSLContext;
import org.jooq.DataType;
import org.jooq.EnumType;
import org.jooq.Field;
import org.jooq.ForeignKey;
import org.jooq.JSONFormat;
import org.jooq.Name;
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
import org.jooq.RecordHandler;
import org.jooq.RecordMapper;
import org.jooq.RecordType;
import org.jooq.Result;
import org.jooq.Row;
import org.jooq.Schema;
import org.jooq.Table;
import org.jooq.TableField;
import org.jooq.TableRecord;
import org.jooq.UpdatableRecord;
import org.jooq.exception.InvalidResultException;
import org.jooq.tools.Convert;
import org.jooq.tools.StringUtils;
import org.jooq.tools.jdbc.MockResultSet;
import org.jooq.tools.json.JSONArray;
import org.jooq.tools.json.JSONObject;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.xml.sax.Attributes;
import org.xml.sax.ContentHandler;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.AttributesImpl;

final class ResultImpl<R extends Record> implements Result<R>, AttachableInternal {
   private static final long serialVersionUID = 6416154375799578362L;
   private Configuration configuration;
   private final Fields<R> fields;
   private final List<R> records;

   ResultImpl(Configuration configuration, Collection<? extends Field<?>> fields) {
      this(configuration, new Fields(fields));
   }

   ResultImpl(Configuration configuration, Field<?>... fields) {
      this(configuration, new Fields(fields));
   }

   ResultImpl(Configuration configuration, Fields<R> fields) {
      this.configuration = configuration;
      this.fields = fields;
      this.records = new ArrayList();
   }

   public final void attach(Configuration c) {
      this.configuration = c;
      Iterator var2 = this.records.iterator();

      while(var2.hasNext()) {
         R record = (Record)var2.next();
         if (record != null) {
            record.attach(c);
         }
      }

   }

   public final void detach() {
      this.attach((Configuration)null);
   }

   public final Configuration configuration() {
      return this.configuration;
   }

   public final RecordType<R> recordType() {
      return this.fields;
   }

   public final Row fieldsRow() {
      return new RowImpl(this.fields);
   }

   public final <T> Field<T> field(Field<T> field) {
      return this.fields.field(field);
   }

   public final Field<?> field(String name) {
      return this.fields.field(name);
   }

   public final <T> Field<T> field(String name, Class<T> type) {
      return this.fields.field(name, type);
   }

   public final <T> Field<T> field(String name, DataType<T> dataType) {
      return this.fields.field(name, dataType);
   }

   public final Field<?> field(Name name) {
      return this.fields.field(name);
   }

   public final <T> Field<T> field(Name name, Class<T> type) {
      return this.fields.field(name, type);
   }

   public final <T> Field<T> field(Name name, DataType<T> dataType) {
      return this.fields.field(name, dataType);
   }

   public final Field<?> field(int index) {
      return this.fields.field(index);
   }

   public final <T> Field<T> field(int index, Class<T> type) {
      return this.fields.field(index, type);
   }

   public final <T> Field<T> field(int index, DataType<T> dataType) {
      return this.fields.field(index, dataType);
   }

   public final Field<?>[] fields() {
      return (Field[])this.fields.fields().clone();
   }

   public final Field<?>[] fields(Field<?>... f) {
      return this.fields.fields(f);
   }

   public final Field<?>[] fields(int... indexes) {
      return this.fields.fields(indexes);
   }

   public final Field<?>[] fields(String... names) {
      return this.fields.fields(names);
   }

   public final Field<?>[] fields(Name... names) {
      return this.fields.fields(names);
   }

   public final boolean isEmpty() {
      return this.records.isEmpty();
   }

   public final boolean isNotEmpty() {
      return !this.records.isEmpty();
   }

   public final <T> T getValue(int index, Field<T> field) {
      return this.get(index).get(field);
   }

   /** @deprecated */
   @Deprecated
   public final <T> T getValue(int index, Field<T> field, T defaultValue) {
      return this.get(index).getValue(field, defaultValue);
   }

   public final Object getValue(int index, int fieldIndex) {
      return this.get(index).get(fieldIndex);
   }

   /** @deprecated */
   @Deprecated
   public final Object getValue(int index, int fieldIndex, Object defaultValue) {
      return this.get(index).getValue(fieldIndex, defaultValue);
   }

   public final Object getValue(int index, String fieldName) {
      return this.get(index).get(fieldName);
   }

   /** @deprecated */
   @Deprecated
   public final Object getValue(int index, String fieldName, Object defaultValue) {
      return this.get(index).getValue(fieldName, defaultValue);
   }

   public final <T> List<T> getValues(Field<T> field) {
      return this.getValues(Tools.indexOrFail(this.fieldsRow(), field));
   }

   public final <T> List<T> getValues(Field<?> field, Class<? extends T> type) {
      return Convert.convert((Collection)this.getValues(field), (Class)type);
   }

   public final <T, U> List<U> getValues(Field<T> field, Converter<? super T, ? extends U> converter) {
      return Convert.convert((Collection)this.getValues(field), (Converter)converter);
   }

   public final List<?> getValues(int fieldIndex) {
      List<Object> result = new ArrayList(this.size());
      Iterator var3 = this.iterator();

      while(var3.hasNext()) {
         R record = (Record)var3.next();
         result.add(record.get(fieldIndex));
      }

      return result;
   }

   public final <T> List<T> getValues(int fieldIndex, Class<? extends T> type) {
      return Convert.convert((Collection)this.getValues(fieldIndex), (Class)type);
   }

   public final <U> List<U> getValues(int fieldIndex, Converter<?, ? extends U> converter) {
      return Convert.convert((Collection)this.getValues(fieldIndex), (Converter)converter);
   }

   public final List<?> getValues(String fieldName) {
      return this.getValues(this.field(fieldName));
   }

   public final <T> List<T> getValues(String fieldName, Class<? extends T> type) {
      return Convert.convert((Collection)this.getValues(fieldName), (Class)type);
   }

   public final <U> List<U> getValues(String fieldName, Converter<?, ? extends U> converter) {
      return Convert.convert((Collection)this.getValues(fieldName), (Converter)converter);
   }

   public final List<?> getValues(Name fieldName) {
      return this.getValues(this.field(fieldName));
   }

   public final <T> List<T> getValues(Name fieldName, Class<? extends T> type) {
      return Convert.convert((Collection)this.getValues(fieldName), (Class)type);
   }

   public final <U> List<U> getValues(Name fieldName, Converter<?, ? extends U> converter) {
      return Convert.convert((Collection)this.getValues(fieldName), (Converter)converter);
   }

   final void addRecord(R record) {
      this.records.add(record);
   }

   public final String format() {
      StringWriter writer = new StringWriter();
      this.format((Writer)writer);
      return writer.toString();
   }

   public final void format(OutputStream stream) {
      this.format((Writer)(new OutputStreamWriter(stream)));
   }

   public final void format(Writer writer) {
      this.format((Writer)writer, 50);
   }

   public final String format(int maxRecords) {
      StringWriter writer = new StringWriter();
      this.format((Writer)writer, maxRecords);
      return writer.toString();
   }

   public final void format(OutputStream stream, int maxRecords) {
      this.format((Writer)(new OutputStreamWriter(stream)), maxRecords);
   }

   public final void format(Writer writer, int maxRecords) {
      try {
         int COL_MIN_WIDTH = true;
         int COL_MAX_WIDTH = true;
         int NUM_COL_MAX_WIDTH = true;
         int MAX_RECORDS = Math.min(50, maxRecords);
         int[] decimalPlaces = new int[this.fields.fields.length];
         int[] widths = new int[this.fields.fields.length];

         int colMaxWidth;
         String padded;
         for(colMaxWidth = 0; colMaxWidth < this.fields.fields.length; ++colMaxWidth) {
            if (Number.class.isAssignableFrom(this.fields.fields[colMaxWidth].getType())) {
               List<Integer> decimalPlacesList = new ArrayList();
               decimalPlacesList.add(0);

               for(int i = 0; i < Math.min(MAX_RECORDS, this.size()); ++i) {
                  padded = format0(this.getValue(i, colMaxWidth), this.get(i).changed(colMaxWidth), true);
                  decimalPlacesList.add(getDecimalPlaces(padded));
               }

               decimalPlaces[colMaxWidth] = (Integer)Collections.max(decimalPlacesList);
            }
         }

         String padded;
         int index;
         for(index = 0; index < this.fields.fields.length; ++index) {
            boolean isNumCol = Number.class.isAssignableFrom(this.fields.fields[index].getType());
            colMaxWidth = isNumCol ? 100 : 50;
            List<Integer> widthList = new ArrayList();
            widthList.add(Math.min(colMaxWidth, Math.max(4, this.fields.fields[index].getName().length())));

            for(int i = 0; i < Math.min(MAX_RECORDS, this.size()); ++i) {
               padded = format0(this.getValue(i, index), this.get(i).changed(index), true);
               if (isNumCol) {
                  padded = alignNumberValue(decimalPlaces[index], padded);
               }

               widthList.add(Math.min(colMaxWidth, padded.length()));
            }

            widths[index] = (Integer)Collections.max(widthList);
         }

         writer.append("+");

         for(index = 0; index < this.fields.fields.length; ++index) {
            writer.append(StringUtils.rightPad("", widths[index], "-"));
            writer.append("+");
         }

         writer.append("\n|");

         for(index = 0; index < this.fields.fields.length; ++index) {
            if (Number.class.isAssignableFrom(this.fields.fields[index].getType())) {
               padded = StringUtils.leftPad(this.fields.fields[index].getName(), widths[index]);
            } else {
               padded = StringUtils.rightPad(this.fields.fields[index].getName(), widths[index]);
            }

            writer.append(StringUtils.abbreviate(padded, widths[index]));
            writer.append("|");
         }

         writer.append("\n+");

         for(index = 0; index < this.fields.fields.length; ++index) {
            writer.append(StringUtils.rightPad("", widths[index], "-"));
            writer.append("+");
         }

         for(index = 0; index < Math.min(maxRecords, this.size()); ++index) {
            writer.append("\n|");

            for(int index = 0; index < this.fields.fields.length; ++index) {
               String value = format0(this.getValue(index, index), this.get(index).changed(index), true).replace("\n", "{lf}").replace("\r", "{cr}");
               if (Number.class.isAssignableFrom(this.fields.fields[index].getType())) {
                  value = alignNumberValue(decimalPlaces[index], value);
                  padded = StringUtils.leftPad(value, widths[index]);
               } else {
                  padded = StringUtils.rightPad(value, widths[index]);
               }

               writer.append(StringUtils.abbreviate(padded, widths[index]));
               writer.append("|");
            }
         }

         if (this.size() > 0) {
            writer.append("\n+");

            for(index = 0; index < this.fields.fields.length; ++index) {
               writer.append(StringUtils.rightPad("", widths[index], "-"));
               writer.append("+");
            }
         }

         if (maxRecords < this.size()) {
            writer.append("\n|...");
            writer.append("" + (this.size() - maxRecords));
            writer.append(" record(s) truncated...");
         }

         writer.flush();
      } catch (IOException var15) {
         throw new org.jooq.exception.IOException("Exception while writing TEXT", var15);
      }
   }

   private static final String alignNumberValue(Integer columnDecimalPlaces, String value) {
      if (!"{null}".equals(value) && columnDecimalPlaces != 0) {
         int decimalPlaces = getDecimalPlaces(value);
         int rightPadSize = value.length() + columnDecimalPlaces - decimalPlaces;
         if (decimalPlaces == 0) {
            value = StringUtils.rightPad(value, rightPadSize + 1);
         } else {
            value = StringUtils.rightPad(value, rightPadSize);
         }
      }

      return value;
   }

   private static final int getDecimalPlaces(String value) {
      int decimalPlaces = 0;
      int dotIndex = value.indexOf(".");
      if (dotIndex != -1) {
         decimalPlaces = value.length() - dotIndex - 1;
      }

      return decimalPlaces;
   }

   public final String formatHTML() {
      StringWriter writer = new StringWriter();
      this.formatHTML((Writer)writer);
      return writer.toString();
   }

   public final void formatHTML(OutputStream stream) {
      this.formatHTML((Writer)(new OutputStreamWriter(stream)));
   }

   public final void formatHTML(Writer writer) {
      try {
         writer.append("<table>");
         writer.append("<thead>");
         writer.append("<tr>");
         Field[] var2 = this.fields.fields;
         int var3 = var2.length;

         int index;
         for(index = 0; index < var3; ++index) {
            Field<?> field = var2[index];
            writer.append("<th>");
            writer.append(this.escapeXML(field.getName()));
            writer.append("</th>");
         }

         writer.append("</tr>");
         writer.append("</thead>");
         writer.append("<tbody>");
         Iterator var7 = this.iterator();

         while(var7.hasNext()) {
            Record record = (Record)var7.next();
            writer.append("<tr>");

            for(index = 0; index < this.fields.fields.length; ++index) {
               writer.append("<td>");
               writer.append(this.escapeXML(format0(record.getValue(index), false, true)));
               writer.append("</td>");
            }

            writer.append("</tr>");
         }

         writer.append("</tbody>");
         writer.append("</table>");
         writer.flush();
      } catch (IOException var6) {
         throw new org.jooq.exception.IOException("Exception while writing HTML", var6);
      }
   }

   public final String formatCSV() {
      return this.formatCSV(true);
   }

   public final String formatCSV(boolean header) {
      StringWriter writer = new StringWriter();
      this.formatCSV((Writer)writer, header);
      return writer.toString();
   }

   public final void formatCSV(OutputStream stream) {
      this.formatCSV(stream, true);
   }

   public final void formatCSV(OutputStream stream, boolean header) {
      this.formatCSV((Writer)(new OutputStreamWriter(stream)), header);
   }

   public final void formatCSV(Writer writer) {
      this.formatCSV(writer, true);
   }

   public final void formatCSV(Writer writer, boolean header) {
      this.formatCSV(writer, header, ',', "\"\"");
   }

   public final String formatCSV(char delimiter) {
      return this.formatCSV(true, delimiter);
   }

   public final String formatCSV(boolean header, char delimiter) {
      StringWriter writer = new StringWriter();
      this.formatCSV((Writer)writer, delimiter);
      return writer.toString();
   }

   public final void formatCSV(OutputStream stream, char delimiter) {
      this.formatCSV(stream, true, delimiter);
   }

   public final void formatCSV(OutputStream stream, boolean header, char delimiter) {
      this.formatCSV((Writer)(new OutputStreamWriter(stream)), delimiter);
   }

   public final void formatCSV(Writer writer, char delimiter) {
      this.formatCSV(writer, true, delimiter);
   }

   public final void formatCSV(Writer writer, boolean header, char delimiter) {
      this.formatCSV(writer, header, delimiter, "\"\"");
   }

   public final String formatCSV(char delimiter, String nullString) {
      return this.formatCSV(true, delimiter, nullString);
   }

   public final String formatCSV(boolean header, char delimiter, String nullString) {
      StringWriter writer = new StringWriter();
      this.formatCSV((Writer)writer, header, delimiter, nullString);
      return writer.toString();
   }

   public final String formatCSV(CSVFormat format) {
      StringWriter writer = new StringWriter();
      this.formatCSV((Writer)writer, format);
      return writer.toString();
   }

   public final void formatCSV(OutputStream stream, char delimiter, String nullString) {
      this.formatCSV(stream, true, delimiter, nullString);
   }

   public final void formatCSV(OutputStream stream, boolean header, char delimiter, String nullString) {
      this.formatCSV((Writer)(new OutputStreamWriter(stream)), header, delimiter, nullString);
   }

   public final void formatCSV(OutputStream stream, CSVFormat format) {
      this.formatCSV((Writer)(new OutputStreamWriter(stream)), format);
   }

   public final void formatCSV(Writer writer, char delimiter, String nullString) {
      this.formatCSV(writer, true, delimiter, nullString);
   }

   public final void formatCSV(Writer writer, boolean header, char delimiter, String nullString) {
      this.formatCSV(writer, (new CSVFormat()).header(header).delimiter(delimiter).nullString(nullString));
   }

   public final void formatCSV(Writer writer, CSVFormat format) {
      try {
         int index;
         if (format.header()) {
            String sep1 = "";
            Field[] var4 = this.fields.fields;
            int var5 = var4.length;

            for(index = 0; index < var5; ++index) {
               Field<?> field = var4[index];
               writer.append(sep1);
               writer.append(this.formatCSV0(field.getName(), format));
               sep1 = format.delimiter();
            }

            writer.append(format.newline());
         }

         Iterator var9 = this.iterator();

         while(var9.hasNext()) {
            Record record = (Record)var9.next();
            String sep2 = "";

            for(index = 0; index < this.fields.fields.length; ++index) {
               writer.append(sep2);
               writer.append(this.formatCSV0(record.getValue(index), format));
               sep2 = format.delimiter();
            }

            writer.append(format.newline());
         }

         writer.flush();
      } catch (IOException var8) {
         throw new org.jooq.exception.IOException("Exception while writing CSV", var8);
      }
   }

   private final String formatCSV0(Object value, CSVFormat format) {
      if (value == null) {
         return format.nullString();
      } else if ("".equals(value.toString())) {
         return format.emptyString();
      } else {
         String result = format0(value, false, false);
         switch(format.quote()) {
         case NEVER:
            return result;
         case SPECIAL_CHARACTERS:
            if (!StringUtils.containsAny(result, ',', ';', '\t', '"', '\n', '\r', '\'', '\\')) {
               return result;
            }
         case ALWAYS:
         default:
            return format.quoteString() + result.replace("\\", "\\\\").replace(format.quoteString(), format.quoteString() + format.quoteString()) + format.quoteString();
         }
      }
   }

   private final Object formatJSON0(Object value) {
      return value instanceof byte[] ? DatatypeConverter.printBase64Binary((byte[])((byte[])value)) : value;
   }

   private static final String format0(Object value, boolean changed, boolean visual) {
      String formatted = changed && visual ? "*" : "";
      if (value == null) {
         formatted = formatted + (visual ? "{null}" : null);
      } else if (value.getClass() == byte[].class) {
         formatted = formatted + DatatypeConverter.printBase64Binary((byte[])((byte[])value));
      } else if (value.getClass().isArray()) {
         formatted = formatted + Arrays.toString((Object[])((Object[])value));
      } else if (value instanceof EnumType) {
         formatted = formatted + ((EnumType)value).getLiteral();
      } else if (value instanceof Record) {
         formatted = formatted + ((Record)value).valuesRow().toString();
      } else if (value instanceof Date) {
         String date = value.toString();
         if (Date.valueOf(date).equals(value)) {
            formatted = formatted + date;
         } else {
            formatted = formatted + new Timestamp(((Date)value).getTime());
         }
      } else {
         formatted = formatted + value.toString();
      }

      return formatted;
   }

   public final String formatJSON() {
      StringWriter writer = new StringWriter();
      this.formatJSON((Writer)writer);
      return writer.toString();
   }

   public final String formatJSON(JSONFormat format) {
      StringWriter writer = new StringWriter();
      this.formatJSON((Writer)writer, format);
      return writer.toString();
   }

   public final void formatJSON(OutputStream stream) {
      this.formatJSON((Writer)(new OutputStreamWriter(stream)));
   }

   public final void formatJSON(OutputStream stream, JSONFormat format) {
      this.formatJSON((Writer)(new OutputStreamWriter(stream)), format);
   }

   public final void formatJSON(Writer writer) {
      this.formatJSON(writer, new JSONFormat());
   }

   public final void formatJSON(Writer writer, JSONFormat format) {
      try {
         List<Map<String, String>> f = null;
         List<Object> r = new ArrayList();
         if (format.header()) {
            f = new ArrayList();
            Field[] var5 = this.fields.fields;
            int var6 = var5.length;

            for(int var7 = 0; var7 < var6; ++var7) {
               Field<?> field = var5[var7];
               Map<String, String> fieldMap = new LinkedHashMap();
               if (field instanceof TableField) {
                  Table<?> table = ((TableField)field).getTable();
                  if (table != null) {
                     Schema schema = table.getSchema();
                     if (schema != null) {
                        fieldMap.put("schema", schema.getName());
                     }

                     fieldMap.put("table", table.getName());
                  }
               }

               fieldMap.put("name", field.getName());
               fieldMap.put("type", field.getDataType().getTypeName().toUpperCase());
               f.add(fieldMap);
            }
         }

         Iterator var13;
         Record record;
         int index;
         label67:
         switch(format.recordFormat()) {
         case ARRAY:
            var13 = this.iterator();

            while(true) {
               if (!var13.hasNext()) {
                  break label67;
               }

               record = (Record)var13.next();
               List<Object> list = new ArrayList();

               for(index = 0; index < this.fields.fields.length; ++index) {
                  list.add(this.formatJSON0(record.get(index)));
               }

               r.add(list);
            }
         case OBJECT:
            var13 = this.iterator();

            while(true) {
               if (!var13.hasNext()) {
                  break label67;
               }

               record = (Record)var13.next();
               Map<String, Object> map = new LinkedHashMap();

               for(index = 0; index < this.fields.fields.length; ++index) {
                  map.put(record.field(index).getName(), this.formatJSON0(record.get(index)));
               }

               r.add(map);
            }
         default:
            throw new IllegalArgumentException("Format not supported: " + format);
         }

         if (f == null) {
            writer.append(JSONArray.toJSONString(r));
         } else {
            Map<String, List<?>> map = new LinkedHashMap();
            map.put("fields", f);
            map.put("records", r);
            writer.append(JSONObject.toJSONString(map));
         }

         writer.flush();
      } catch (IOException var12) {
         throw new org.jooq.exception.IOException("Exception while writing JSON", var12);
      }
   }

   public final String formatXML() {
      StringWriter writer = new StringWriter();
      this.formatXML((Writer)writer);
      return writer.toString();
   }

   public final void formatXML(OutputStream stream) {
      this.formatXML((Writer)(new OutputStreamWriter(stream)));
   }

   public final void formatXML(Writer writer) {
      try {
         writer.append("<result xmlns=\"http://www.jooq.org/xsd/jooq-export-3.7.0.xsd\">");
         writer.append("<fields>");
         Field[] var2 = this.fields.fields;
         int var3 = var2.length;

         int index;
         for(index = 0; index < var3; ++index) {
            Field<?> field = var2[index];
            writer.append("<field");
            if (field instanceof TableField) {
               Table<?> table = ((TableField)field).getTable();
               if (table != null) {
                  Schema schema = table.getSchema();
                  if (schema != null) {
                     writer.append(" schema=\"");
                     writer.append(this.escapeXML(schema.getName()));
                     writer.append("\"");
                  }

                  writer.append(" table=\"");
                  writer.append(this.escapeXML(table.getName()));
                  writer.append("\"");
               }
            }

            writer.append(" name=\"");
            writer.append(this.escapeXML(field.getName()));
            writer.append("\"");
            writer.append(" type=\"");
            writer.append(field.getDataType().getTypeName().toUpperCase());
            writer.append("\"/>");
         }

         writer.append("</fields>");
         writer.append("<records>");
         Iterator var9 = this.iterator();

         while(var9.hasNext()) {
            Record record = (Record)var9.next();
            writer.append("<record>");

            for(index = 0; index < this.fields.fields.length; ++index) {
               Object value = record.get(index);
               writer.append("<value field=\"");
               writer.append(this.escapeXML(this.fields.fields[index].getName()));
               writer.append("\"");
               if (value == null) {
                  writer.append("/>");
               } else {
                  writer.append(">");
                  writer.append(this.escapeXML(format0(value, false, false)));
                  writer.append("</value>");
               }
            }

            writer.append("</record>");
         }

         writer.append("</records>");
         writer.append("</result>");
         writer.flush();
      } catch (IOException var8) {
         throw new org.jooq.exception.IOException("Exception while writing XML", var8);
      }
   }

   public final String formatInsert() {
      StringWriter writer = new StringWriter();
      this.formatInsert((Writer)writer);
      return writer.toString();
   }

   public final void formatInsert(OutputStream stream) {
      this.formatInsert((Writer)(new OutputStreamWriter(stream)));
   }

   public final void formatInsert(Writer writer) {
      Table<?> table = null;
      if (this.records.size() > 0 && this.records.get(0) instanceof TableRecord) {
         table = ((TableRecord)this.records.get(0)).getTable();
      }

      if (table == null) {
         table = DSL.table(DSL.name("UNKNOWN_TABLE"));
      }

      this.formatInsert(writer, table, this.fields());
   }

   public final String formatInsert(Table<?> table, Field<?>... f) {
      StringWriter writer = new StringWriter();
      this.formatInsert((Writer)writer, table, f);
      return writer.toString();
   }

   public final void formatInsert(OutputStream stream, Table<?> table, Field<?>... f) {
      this.formatInsert((Writer)(new OutputStreamWriter(stream)), table, f);
   }

   public final void formatInsert(Writer writer, Table<?> table, Field<?>... f) {
      DSLContext ctx = DSL.using(this.configuration());

      try {
         Iterator var5 = this.iterator();

         while(var5.hasNext()) {
            R record = (Record)var5.next();
            writer.append(ctx.renderInlined(DSL.insertInto(table, f).values(record.intoArray())));
            writer.append(";\n");
         }

         writer.flush();
      } catch (IOException var7) {
         throw new org.jooq.exception.IOException("Exception while writing INSERTs", var7);
      }
   }

   public final Document intoXML() {
      try {
         DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
         DocumentBuilder builder = factory.newDocumentBuilder();
         Document document = builder.newDocument();
         Element eResult = document.createElement("result");
         eResult.setAttribute("xmlns", "http://www.jooq.org/xsd/jooq-export-3.7.0.xsd");
         document.appendChild(eResult);
         Element eFields = document.createElement("fields");
         eResult.appendChild(eFields);
         Field[] var6 = this.fields.fields;
         int var7 = var6.length;

         for(int var8 = 0; var8 < var7; ++var8) {
            Field<?> field = var6[var8];
            Element eField = document.createElement("field");
            if (field instanceof TableField) {
               Table<?> table = ((TableField)field).getTable();
               if (table != null) {
                  Schema schema = table.getSchema();
                  if (schema != null) {
                     eField.setAttribute("schema", schema.getName());
                  }

                  eField.setAttribute("table", table.getName());
               }
            }

            eField.setAttribute("name", field.getName());
            eField.setAttribute("type", field.getDataType().getTypeName().toUpperCase());
            eFields.appendChild(eField);
         }

         Element eRecords = document.createElement("records");
         eResult.appendChild(eRecords);
         Iterator var16 = this.iterator();

         while(var16.hasNext()) {
            Record record = (Record)var16.next();
            Element eRecord = document.createElement("record");
            eRecords.appendChild(eRecord);

            for(int index = 0; index < this.fields.fields.length; ++index) {
               Field<?> field = this.fields.fields[index];
               Object value = record.get(index);
               Element eValue = document.createElement("value");
               eValue.setAttribute("field", field.getName());
               eRecord.appendChild(eValue);
               if (value != null) {
                  eValue.setTextContent(format0(value, false, false));
               }
            }
         }

         return document;
      } catch (ParserConfigurationException var14) {
         throw new RuntimeException(var14);
      }
   }

   public final <H extends ContentHandler> H intoXML(H handler) throws SAXException {
      Attributes empty = new AttributesImpl();
      handler.startDocument();
      handler.startPrefixMapping("", "http://www.jooq.org/xsd/jooq-export-3.7.0.xsd");
      handler.startElement("", "", "result", empty);
      handler.startElement("", "", "fields", empty);
      Field[] var3 = this.fields.fields;
      int var4 = var3.length;

      int index;
      Field field;
      for(index = 0; index < var4; ++index) {
         field = var3[index];
         AttributesImpl attrs = new AttributesImpl();
         if (field instanceof TableField) {
            Table<?> table = ((TableField)field).getTable();
            if (table != null) {
               Schema schema = table.getSchema();
               if (schema != null) {
                  attrs.addAttribute("", "", "schema", "CDATA", schema.getName());
               }

               attrs.addAttribute("", "", "table", "CDATA", table.getName());
            }
         }

         attrs.addAttribute("", "", "name", "CDATA", field.getName());
         attrs.addAttribute("", "", "type", "CDATA", field.getDataType().getTypeName().toUpperCase());
         handler.startElement("", "", "field", attrs);
         handler.endElement("", "", "field");
      }

      handler.endElement("", "", "fields");
      handler.startElement("", "", "records", empty);
      Iterator var10 = this.iterator();

      while(var10.hasNext()) {
         Record record = (Record)var10.next();
         handler.startElement("", "", "record", empty);

         for(index = 0; index < this.fields.fields.length; ++index) {
            field = this.fields.fields[index];
            Object value = record.get(index);
            AttributesImpl attrs = new AttributesImpl();
            attrs.addAttribute("", "", "field", "CDATA", field.getName());
            handler.startElement("", "", "value", attrs);
            if (value != null) {
               char[] chars = format0(value, false, false).toCharArray();
               handler.characters(chars, 0, chars.length);
            }

            handler.endElement("", "", "value");
         }

         handler.endElement("", "", "record");
      }

      handler.endElement("", "", "records");
      handler.endPrefixMapping("");
      handler.endDocument();
      return handler;
   }

   private final String escapeXML(String string) {
      return StringUtils.replaceEach(string, new String[]{"\"", "'", "<", ">", "&"}, new String[]{"&quot;", "&apos;", "&lt;", "&gt;", "&amp;"});
   }

   public final List<Map<String, Object>> intoMaps() {
      List<Map<String, Object>> list = new ArrayList();
      Iterator var2 = this.iterator();

      while(var2.hasNext()) {
         R record = (Record)var2.next();
         list.add(record.intoMap());
      }

      return list;
   }

   public final <K> Map<K, R> intoMap(Field<K> key) {
      return this.intoMap0(Tools.indexOrFail(this.fieldsRow(), key));
   }

   public final Map<?, R> intoMap(int keyFieldIndex) {
      return this.intoMap0(keyFieldIndex);
   }

   public final Map<?, R> intoMap(String keyFieldName) {
      return this.intoMap(this.field(keyFieldName));
   }

   public final Map<?, R> intoMap(Name keyFieldName) {
      return this.intoMap(this.field(keyFieldName));
   }

   private final <K> Map<K, R> intoMap0(int keyFieldIndex) {
      Map<K, R> map = new LinkedHashMap();
      Iterator var3 = this.iterator();

      Record record;
      do {
         if (!var3.hasNext()) {
            return map;
         }

         record = (Record)var3.next();
      } while(map.put(record.get(keyFieldIndex), record) == null);

      throw new InvalidResultException("Key " + keyFieldIndex + " is not unique in Result for " + this);
   }

   public final <K, V> Map<K, V> intoMap(Field<K> key, Field<V> value) {
      int kIndex = Tools.indexOrFail(this.fieldsRow(), key);
      int vIndex = Tools.indexOrFail(this.fieldsRow(), value);
      return this.intoMap0(kIndex, vIndex);
   }

   public final Map<?, ?> intoMap(int keyFieldIndex, int valueFieldIndex) {
      return this.intoMap0(keyFieldIndex, valueFieldIndex);
   }

   public final Map<?, ?> intoMap(String keyFieldName, String valueFieldName) {
      return this.intoMap(this.field(keyFieldName), this.field(valueFieldName));
   }

   public final Map<?, ?> intoMap(Name keyFieldName, Name valueFieldName) {
      return this.intoMap(this.field(keyFieldName), this.field(valueFieldName));
   }

   private final <K, V> Map<K, V> intoMap0(int kIndex, int vIndex) {
      Map<K, V> map = new LinkedHashMap();
      Iterator var4 = this.iterator();

      Record record;
      do {
         if (!var4.hasNext()) {
            return map;
         }

         record = (Record)var4.next();
      } while(map.put(record.get(kIndex), record.get(vIndex)) == null);

      throw new InvalidResultException("Key " + record.get(kIndex) + " is not unique in Result for " + this);
   }

   public final Map<Record, R> intoMap(int[] keyFieldIndexes) {
      return this.intoMap(this.fields(keyFieldIndexes));
   }

   public final Map<Record, R> intoMap(String[] keyFieldNames) {
      return this.intoMap(this.fields(keyFieldNames));
   }

   public final Map<Record, R> intoMap(Name[] keyFieldNames) {
      return this.intoMap(this.fields(keyFieldNames));
   }

   public final Map<Record, R> intoMap(Field<?>[] keys) {
      if (keys == null) {
         keys = new Field[0];
      }

      Map<Record, R> map = new LinkedHashMap();
      Iterator var3 = this.iterator();

      Record record;
      RecordImpl key;
      do {
         if (!var3.hasNext()) {
            return map;
         }

         record = (Record)var3.next();
         key = new RecordImpl(keys);
         Field[] var6 = keys;
         int var7 = keys.length;

         for(int var8 = 0; var8 < var7; ++var8) {
            Field<?> field = var6[var8];
            Tools.copyValue(key, field, record, field);
         }
      } while(map.put(key, record) == null);

      throw new InvalidResultException("Key list " + Arrays.asList(keys) + " is not unique in Result for " + this);
   }

   public final <E> Map<List<?>, E> intoMap(int[] keyFieldIndexes, Class<? extends E> type) {
      return this.intoMap(this.fields(keyFieldIndexes), type);
   }

   public final <E> Map<List<?>, E> intoMap(String[] keyFieldNames, Class<? extends E> type) {
      return this.intoMap(this.fields(keyFieldNames), type);
   }

   public final <E> Map<List<?>, E> intoMap(Name[] keyFieldNames, Class<? extends E> type) {
      return this.intoMap(this.fields(keyFieldNames), type);
   }

   public final <E> Map<List<?>, E> intoMap(Field<?>[] keys, Class<? extends E> type) {
      return this.intoMap(keys, Tools.configuration((Attachable)this).recordMapperProvider().provide(this.fields, type));
   }

   public final <E> Map<List<?>, E> intoMap(int[] keyFieldIndexes, RecordMapper<? super R, E> mapper) {
      return this.intoMap(this.fields(keyFieldIndexes), mapper);
   }

   public final <E> Map<List<?>, E> intoMap(String[] keyFieldNames, RecordMapper<? super R, E> mapper) {
      return this.intoMap(this.fields(keyFieldNames), mapper);
   }

   public final <E> Map<List<?>, E> intoMap(Name[] keyFieldNames, RecordMapper<? super R, E> mapper) {
      return this.intoMap(this.fields(keyFieldNames), mapper);
   }

   public final <E> Map<List<?>, E> intoMap(Field<?>[] keys, RecordMapper<? super R, E> mapper) {
      if (keys == null) {
         keys = new Field[0];
      }

      Map<List<?>, E> map = new LinkedHashMap();
      Iterator var4 = this.iterator();

      Record record;
      ArrayList keyValueList;
      do {
         if (!var4.hasNext()) {
            return map;
         }

         record = (Record)var4.next();
         keyValueList = new ArrayList();
         Field[] var7 = keys;
         int var8 = keys.length;

         for(int var9 = 0; var9 < var8; ++var9) {
            Field<?> key = var7[var9];
            keyValueList.add(record.get(key));
         }
      } while(map.put(keyValueList, mapper.map(record)) == null);

      throw new InvalidResultException("Key list " + keyValueList + " is not unique in Result for " + this);
   }

   public final <K> Map<K, R> intoMap(Class<? extends K> keyType) {
      return this.intoMap(Tools.configuration((Attachable)this).recordMapperProvider().provide(this.fields, keyType));
   }

   public final <K, V> Map<K, V> intoMap(Class<? extends K> keyType, Class<? extends V> valueType) {
      return this.intoMap(Tools.configuration((Attachable)this).recordMapperProvider().provide(this.fields, keyType), Tools.configuration((Attachable)this).recordMapperProvider().provide(this.fields, valueType));
   }

   public final <K, V> Map<K, V> intoMap(Class<? extends K> keyType, RecordMapper<? super R, V> valueMapper) {
      return this.intoMap(Tools.configuration((Attachable)this).recordMapperProvider().provide(this.fields, keyType), valueMapper);
   }

   public final <K> Map<K, R> intoMap(RecordMapper<? super R, K> keyMapper) {
      Map<K, R> map = new LinkedHashMap();
      Iterator var3 = this.iterator();

      Record record;
      Object key;
      do {
         if (!var3.hasNext()) {
            return map;
         }

         record = (Record)var3.next();
         key = keyMapper.map(record);
      } while(map.put(key, record) == null);

      throw new InvalidResultException("Key list " + key + " is not unique in Result for " + this);
   }

   public final <K, V> Map<K, V> intoMap(RecordMapper<? super R, K> keyMapper, Class<V> valueType) {
      return this.intoMap(keyMapper, Tools.configuration((Attachable)this).recordMapperProvider().provide(this.fields, valueType));
   }

   public final <K, V> Map<K, V> intoMap(RecordMapper<? super R, K> keyMapper, RecordMapper<? super R, V> valueMapper) {
      Map<K, V> map = new LinkedHashMap();
      Iterator var4 = this.iterator();

      Object key;
      Object value;
      do {
         if (!var4.hasNext()) {
            return map;
         }

         R record = (Record)var4.next();
         key = keyMapper.map(record);
         value = valueMapper.map(record);
      } while(map.put(key, value) == null);

      throw new InvalidResultException("Key list " + key + " is not unique in Result for " + this);
   }

   public final <S extends Record> Map<S, R> intoMap(Table<S> table) {
      Map<S, R> map = new LinkedHashMap();
      Iterator var3 = this.iterator();

      Record record;
      Record key;
      do {
         if (!var3.hasNext()) {
            return map;
         }

         record = (Record)var3.next();
         key = record.into(table);
      } while(map.put(key, record) == null);

      throw new InvalidResultException("Key list " + key + " is not unique in Result for " + this);
   }

   public final <E, S extends Record> Map<S, E> intoMap(Table<S> table, Class<? extends E> type) {
      return this.intoMap(table, Tools.configuration((Attachable)this).recordMapperProvider().provide(this.fields, type));
   }

   public final <E, S extends Record> Map<S, E> intoMap(Table<S> table, RecordMapper<? super R, E> mapper) {
      Map<S, E> map = new LinkedHashMap();
      Iterator var4 = this.iterator();

      Record record;
      Record key;
      do {
         if (!var4.hasNext()) {
            return map;
         }

         record = (Record)var4.next();
         key = record.into(table);
      } while(map.put(key, mapper.map(record)) == null);

      throw new InvalidResultException("Key list " + key + " is not unique in Result for " + this);
   }

   public final <E> Map<?, E> intoMap(int keyFieldIndex, Class<? extends E> type) {
      return this.intoMap(keyFieldIndex, Tools.configuration((Attachable)this).recordMapperProvider().provide(this.fields, type));
   }

   public final <E> Map<?, E> intoMap(String keyFieldName, Class<? extends E> type) {
      return this.intoMap(keyFieldName, Tools.configuration((Attachable)this).recordMapperProvider().provide(this.fields, type));
   }

   public final <E> Map<?, E> intoMap(Name keyFieldName, Class<? extends E> type) {
      return this.intoMap(keyFieldName, Tools.configuration((Attachable)this).recordMapperProvider().provide(this.fields, type));
   }

   public final <K, E> Map<K, E> intoMap(Field<K> key, Class<? extends E> type) {
      return this.intoMap(key, Tools.configuration((Attachable)this).recordMapperProvider().provide(this.fields, type));
   }

   public final <E> Map<?, E> intoMap(int keyFieldIndex, RecordMapper<? super R, E> mapper) {
      return this.intoMap0(keyFieldIndex, mapper);
   }

   public final <E> Map<?, E> intoMap(String keyFieldName, RecordMapper<? super R, E> mapper) {
      return this.intoMap(this.field(keyFieldName), mapper);
   }

   public final <E> Map<?, E> intoMap(Name keyFieldName, RecordMapper<? super R, E> mapper) {
      return this.intoMap(this.field(keyFieldName), mapper);
   }

   public final <K, E> Map<K, E> intoMap(Field<K> key, RecordMapper<? super R, E> mapper) {
      return this.intoMap0(Tools.indexOrFail(this.fieldsRow(), key), mapper);
   }

   private final <K, E> Map<K, E> intoMap0(int keyFieldIndex, RecordMapper<? super R, E> mapper) {
      Map<K, E> map = new LinkedHashMap();
      Iterator var4 = this.iterator();

      Record record;
      do {
         if (!var4.hasNext()) {
            return map;
         }

         record = (Record)var4.next();
      } while(map.put(record.get(keyFieldIndex), mapper.map(record)) == null);

      throw new InvalidResultException("Key " + keyFieldIndex + " is not unique in Result for " + this);
   }

   public final <K> Map<K, Result<R>> intoGroups(Field<K> key) {
      return this.intoGroups0(Tools.indexOrFail(this.fieldsRow(), key));
   }

   public final Map<?, Result<R>> intoGroups(int keyFieldIndex) {
      return this.intoGroups0(keyFieldIndex);
   }

   public final Map<?, Result<R>> intoGroups(String keyFieldName) {
      return this.intoGroups(this.field(keyFieldName));
   }

   public final Map<?, Result<R>> intoGroups(Name keyFieldName) {
      return this.intoGroups(this.field(keyFieldName));
   }

   private final <K> Map<K, Result<R>> intoGroups0(int keyFieldIndex) {
      Map<K, Result<R>> map = new LinkedHashMap();

      Record record;
      Object result;
      for(Iterator var3 = this.iterator(); var3.hasNext(); ((Result)result).add(record)) {
         record = (Record)var3.next();
         K val = record.get(keyFieldIndex);
         result = (Result)map.get(val);
         if (result == null) {
            result = new ResultImpl(this.configuration, this.fields);
            map.put(val, result);
         }
      }

      return map;
   }

   public final <K, V> Map<K, List<V>> intoGroups(Field<K> key, Field<V> value) {
      int kIndex = Tools.indexOrFail(this.fieldsRow(), key);
      int vIndex = Tools.indexOrFail(this.fieldsRow(), value);
      return this.intoGroups0(kIndex, vIndex);
   }

   public final Map<?, List<?>> intoGroups(int keyFieldIndex, int valueFieldIndex) {
      return this.intoGroups0(keyFieldIndex, valueFieldIndex);
   }

   public final Map<?, List<?>> intoGroups(String keyFieldName, String valueFieldName) {
      return this.intoGroups(this.field(keyFieldName), this.field(valueFieldName));
   }

   public final Map<?, List<?>> intoGroups(Name keyFieldName, Name valueFieldName) {
      return this.intoGroups(this.field(keyFieldName), this.field(valueFieldName));
   }

   private final <K, V> Map<K, List<V>> intoGroups0(int kIndex, int vIndex) {
      Map<K, List<V>> map = new LinkedHashMap();

      Object v;
      Object result;
      for(Iterator var4 = this.iterator(); var4.hasNext(); ((List)result).add(v)) {
         R record = (Record)var4.next();
         K k = record.get(kIndex);
         v = record.get(vIndex);
         result = (List)map.get(k);
         if (result == null) {
            result = new ArrayList();
            map.put(k, result);
         }
      }

      return map;
   }

   public final <E> Map<?, List<E>> intoGroups(int keyFieldIndex, Class<? extends E> type) {
      return this.intoGroups(keyFieldIndex, Tools.configuration((Attachable)this).recordMapperProvider().provide(this.fields, type));
   }

   public final <E> Map<?, List<E>> intoGroups(String keyFieldName, Class<? extends E> type) {
      return this.intoGroups(keyFieldName, Tools.configuration((Attachable)this).recordMapperProvider().provide(this.fields, type));
   }

   public final <E> Map<?, List<E>> intoGroups(Name keyFieldName, Class<? extends E> type) {
      return this.intoGroups(keyFieldName, Tools.configuration((Attachable)this).recordMapperProvider().provide(this.fields, type));
   }

   public final <K, E> Map<K, List<E>> intoGroups(Field<K> key, Class<? extends E> type) {
      return this.intoGroups(key, Tools.configuration((Attachable)this).recordMapperProvider().provide(this.fields, type));
   }

   public final <K, E> Map<K, List<E>> intoGroups(Field<K> key, RecordMapper<? super R, E> mapper) {
      return this.intoGroups0(Tools.indexOrFail(this.fieldsRow(), key), mapper);
   }

   public final <E> Map<?, List<E>> intoGroups(int keyFieldIndex, RecordMapper<? super R, E> mapper) {
      return this.intoGroups0(keyFieldIndex, mapper);
   }

   public final <E> Map<?, List<E>> intoGroups(String keyFieldName, RecordMapper<? super R, E> mapper) {
      return this.intoGroups(this.field(keyFieldName), mapper);
   }

   public final <E> Map<?, List<E>> intoGroups(Name keyFieldName, RecordMapper<? super R, E> mapper) {
      return this.intoGroups(this.field(keyFieldName), mapper);
   }

   private final <K, E> Map<K, List<E>> intoGroups0(int keyFieldIndex, RecordMapper<? super R, E> mapper) {
      Map<K, List<E>> map = new LinkedHashMap();

      Record record;
      Object list;
      for(Iterator var4 = this.iterator(); var4.hasNext(); ((List)list).add(mapper.map(record))) {
         record = (Record)var4.next();
         K keyVal = record.get(keyFieldIndex);
         list = (List)map.get(keyVal);
         if (list == null) {
            list = new ArrayList();
            map.put(keyVal, list);
         }
      }

      return map;
   }

   public final Map<Record, Result<R>> intoGroups(int[] keyFieldIndexes) {
      return this.intoGroups(this.fields(keyFieldIndexes));
   }

   public final Map<Record, Result<R>> intoGroups(String[] keyFieldNames) {
      return this.intoGroups(this.fields(keyFieldNames));
   }

   public final Map<Record, Result<R>> intoGroups(Name[] keyFieldNames) {
      return this.intoGroups(this.fields(keyFieldNames));
   }

   public final Map<Record, Result<R>> intoGroups(Field<?>[] keys) {
      if (keys == null) {
         keys = new Field[0];
      }

      Map<Record, Result<R>> map = new LinkedHashMap();

      Record record;
      Object result;
      for(Iterator var3 = this.iterator(); var3.hasNext(); ((Result)result).add(record)) {
         record = (Record)var3.next();
         RecordImpl key = new RecordImpl(keys);
         Field[] var6 = keys;
         int var7 = keys.length;

         for(int var8 = 0; var8 < var7; ++var8) {
            Field<?> field = var6[var8];
            Tools.copyValue(key, field, record, field);
         }

         result = (Result)map.get(key);
         if (result == null) {
            result = new ResultImpl(this.configuration(), this.fields);
            map.put(key, result);
         }
      }

      return map;
   }

   public final <E> Map<Record, List<E>> intoGroups(int[] keyFieldIndexes, Class<? extends E> type) {
      return this.intoGroups(keyFieldIndexes, Tools.configuration((Attachable)this).recordMapperProvider().provide(this.fields, type));
   }

   public final <E> Map<Record, List<E>> intoGroups(String[] keyFieldNames, Class<? extends E> type) {
      return this.intoGroups(keyFieldNames, Tools.configuration((Attachable)this).recordMapperProvider().provide(this.fields, type));
   }

   public final <E> Map<Record, List<E>> intoGroups(Name[] keyFieldNames, Class<? extends E> type) {
      return this.intoGroups(keyFieldNames, Tools.configuration((Attachable)this).recordMapperProvider().provide(this.fields, type));
   }

   public final <E> Map<Record, List<E>> intoGroups(Field<?>[] keys, Class<? extends E> type) {
      return this.intoGroups(keys, Tools.configuration((Attachable)this).recordMapperProvider().provide(this.fields, type));
   }

   public final <E> Map<Record, List<E>> intoGroups(int[] keyFieldIndexes, RecordMapper<? super R, E> mapper) {
      return this.intoGroups(this.fields(keyFieldIndexes), mapper);
   }

   public final <E> Map<Record, List<E>> intoGroups(String[] keyFieldNames, RecordMapper<? super R, E> mapper) {
      return this.intoGroups(this.fields(keyFieldNames), mapper);
   }

   public final <E> Map<Record, List<E>> intoGroups(Name[] keyFieldNames, RecordMapper<? super R, E> mapper) {
      return this.intoGroups(this.fields(keyFieldNames), mapper);
   }

   public final <E> Map<Record, List<E>> intoGroups(Field<?>[] keys, RecordMapper<? super R, E> mapper) {
      if (keys == null) {
         keys = new Field[0];
      }

      Map<Record, List<E>> map = new LinkedHashMap();

      Record record;
      Object list;
      for(Iterator var4 = this.iterator(); var4.hasNext(); ((List)list).add(mapper.map(record))) {
         record = (Record)var4.next();
         RecordImpl key = new RecordImpl(keys);
         Field[] var7 = keys;
         int var8 = keys.length;

         for(int var9 = 0; var9 < var8; ++var9) {
            Field<?> field = var7[var9];
            Tools.copyValue(key, field, record, field);
         }

         list = (List)map.get(key);
         if (list == null) {
            list = new ArrayList();
            map.put(key, list);
         }
      }

      return map;
   }

   public final <K> Map<K, Result<R>> intoGroups(Class<? extends K> keyType) {
      return this.intoGroups(Tools.configuration((Attachable)this).recordMapperProvider().provide(this.fields, keyType));
   }

   public final <K, V> Map<K, List<V>> intoGroups(Class<? extends K> keyType, Class<? extends V> valueType) {
      return this.intoGroups(Tools.configuration((Attachable)this).recordMapperProvider().provide(this.fields, keyType), Tools.configuration((Attachable)this).recordMapperProvider().provide(this.fields, valueType));
   }

   public final <K, V> Map<K, List<V>> intoGroups(Class<? extends K> keyType, RecordMapper<? super R, V> valueMapper) {
      return this.intoGroups(Tools.configuration((Attachable)this).recordMapperProvider().provide(this.fields, keyType), valueMapper);
   }

   public final <K> Map<K, Result<R>> intoGroups(RecordMapper<? super R, K> keyMapper) {
      Map<K, Result<R>> map = new LinkedHashMap();

      Record record;
      Object result;
      for(Iterator var3 = this.iterator(); var3.hasNext(); ((Result)result).add(record)) {
         record = (Record)var3.next();
         K key = keyMapper.map(record);
         result = (Result)map.get(key);
         if (result == null) {
            result = new ResultImpl(this.configuration(), this.fields());
            map.put(key, result);
         }
      }

      return map;
   }

   public final <K, V> Map<K, List<V>> intoGroups(RecordMapper<? super R, K> keyMapper, Class<V> valueType) {
      return this.intoGroups(keyMapper, Tools.configuration((Attachable)this).recordMapperProvider().provide(this.fields, valueType));
   }

   public final <K, V> Map<K, List<V>> intoGroups(RecordMapper<? super R, K> keyMapper, RecordMapper<? super R, V> valueMapper) {
      Map<K, List<V>> map = new LinkedHashMap();

      Record record;
      Object list;
      for(Iterator var4 = this.iterator(); var4.hasNext(); ((List)list).add(valueMapper.map(record))) {
         record = (Record)var4.next();
         K key = keyMapper.map(record);
         list = (List)map.get(key);
         if (list == null) {
            list = new ArrayList();
            map.put(key, list);
         }
      }

      return map;
   }

   public final <S extends Record> Map<S, Result<R>> intoGroups(Table<S> table) {
      Map<S, Result<R>> map = new LinkedHashMap();

      Record record;
      Object result;
      for(Iterator var3 = this.iterator(); var3.hasNext(); ((Result)result).add(record)) {
         record = (Record)var3.next();
         S key = record.into(table);
         result = (Result)map.get(key);
         if (result == null) {
            result = new ResultImpl(this.configuration(), this.fields);
            map.put(key, result);
         }
      }

      return map;
   }

   public final <E, S extends Record> Map<S, List<E>> intoGroups(Table<S> table, Class<? extends E> type) {
      return this.intoGroups(table, Tools.configuration((Attachable)this).recordMapperProvider().provide(this.fields, type));
   }

   public final <E, S extends Record> Map<S, List<E>> intoGroups(Table<S> table, RecordMapper<? super R, E> mapper) {
      Map<S, List<E>> map = new LinkedHashMap();

      Record record;
      Object list;
      for(Iterator var4 = this.iterator(); var4.hasNext(); ((List)list).add(mapper.map(record))) {
         record = (Record)var4.next();
         S key = record.into(table);
         list = (List)map.get(key);
         if (list == null) {
            list = new ArrayList();
            map.put(key, list);
         }
      }

      return map;
   }

   /** @deprecated */
   @Deprecated
   public final Object[][] intoArray() {
      return this.intoArrays();
   }

   public final Object[][] intoArrays() {
      int size = this.size();
      Object[][] array = new Object[size][];

      for(int i = 0; i < size; ++i) {
         array[i] = this.get(i).intoArray();
      }

      return array;
   }

   public final Object[] intoArray(int fieldIndex) {
      Class<?> type = this.fields.fields[fieldIndex].getType();
      List<?> list = this.getValues(fieldIndex);
      return list.toArray((Object[])((Object[])java.lang.reflect.Array.newInstance(type, list.size())));
   }

   public final <T> T[] intoArray(int fieldIndex, Class<? extends T> type) {
      return (Object[])Convert.convertArray(this.intoArray(fieldIndex), type);
   }

   public final <U> U[] intoArray(int fieldIndex, Converter<?, ? extends U> converter) {
      return Convert.convertArray(this.intoArray(fieldIndex), converter);
   }

   public final Object[] intoArray(String fieldName) {
      Class<?> type = this.field(fieldName).getType();
      List<?> list = this.getValues(fieldName);
      return list.toArray((Object[])((Object[])java.lang.reflect.Array.newInstance(type, list.size())));
   }

   public final <T> T[] intoArray(String fieldName, Class<? extends T> type) {
      return (Object[])Convert.convertArray(this.intoArray(fieldName), type);
   }

   public final <U> U[] intoArray(String fieldName, Converter<?, ? extends U> converter) {
      return Convert.convertArray(this.intoArray(fieldName), converter);
   }

   public final Object[] intoArray(Name fieldName) {
      Class<?> type = this.field(fieldName).getType();
      List<?> list = this.getValues(fieldName);
      return list.toArray((Object[])((Object[])java.lang.reflect.Array.newInstance(type, list.size())));
   }

   public final <T> T[] intoArray(Name fieldName, Class<? extends T> type) {
      return (Object[])Convert.convertArray(this.intoArray(fieldName), type);
   }

   public final <U> U[] intoArray(Name fieldName, Converter<?, ? extends U> converter) {
      return Convert.convertArray(this.intoArray(fieldName), converter);
   }

   public final <T> T[] intoArray(Field<T> field) {
      return this.getValues(field).toArray((Object[])((Object[])java.lang.reflect.Array.newInstance(field.getType(), 0)));
   }

   public final <T> T[] intoArray(Field<?> field, Class<? extends T> type) {
      return (Object[])Convert.convertArray(this.intoArray(field), type);
   }

   public final <T, U> U[] intoArray(Field<T> field, Converter<? super T, ? extends U> converter) {
      return Convert.convertArray(this.intoArray(field), converter);
   }

   public final Set<?> intoSet(int fieldIndex) {
      return new LinkedHashSet(this.getValues(fieldIndex));
   }

   public final <T> Set<T> intoSet(int fieldIndex, Class<? extends T> type) {
      return new LinkedHashSet(this.getValues(fieldIndex, type));
   }

   public final <U> Set<U> intoSet(int fieldIndex, Converter<?, ? extends U> converter) {
      return new LinkedHashSet(this.getValues(fieldIndex, converter));
   }

   public final Set<?> intoSet(String fieldName) {
      return new LinkedHashSet(this.getValues(fieldName));
   }

   public final <T> Set<T> intoSet(String fieldName, Class<? extends T> type) {
      return new LinkedHashSet(this.getValues(fieldName, type));
   }

   public final <U> Set<U> intoSet(String fieldName, Converter<?, ? extends U> converter) {
      return new LinkedHashSet(this.getValues(fieldName, converter));
   }

   public final Set<?> intoSet(Name fieldName) {
      return new LinkedHashSet(this.getValues(fieldName));
   }

   public final <T> Set<T> intoSet(Name fieldName, Class<? extends T> type) {
      return new LinkedHashSet(this.getValues(fieldName, type));
   }

   public final <U> Set<U> intoSet(Name fieldName, Converter<?, ? extends U> converter) {
      return new LinkedHashSet(this.getValues(fieldName, converter));
   }

   public final <T> Set<T> intoSet(Field<T> field) {
      return new LinkedHashSet(this.getValues(field));
   }

   public final <T> Set<T> intoSet(Field<?> field, Class<? extends T> type) {
      return new LinkedHashSet(this.getValues(field, type));
   }

   public final <T, U> Set<U> intoSet(Field<T> field, Converter<? super T, ? extends U> converter) {
      return new LinkedHashSet(this.getValues(field, converter));
   }

   public final Result<Record> into(Field<?>... f) {
      Result<Record> result = new ResultImpl(Tools.configuration((Attachable)this), f);
      Iterator var3 = this.iterator();

      while(var3.hasNext()) {
         Record record = (Record)var3.next();
         result.add(record.into(f));
      }

      return result;
   }

   public final <T1> Result<Record1<T1>> into(Field<T1> field1) {
      return this.into(field1);
   }

   public final <T1, T2> Result<Record2<T1, T2>> into(Field<T1> field1, Field<T2> field2) {
      return this.into(field1, field2);
   }

   public final <T1, T2, T3> Result<Record3<T1, T2, T3>> into(Field<T1> field1, Field<T2> field2, Field<T3> field3) {
      return this.into(field1, field2, field3);
   }

   public final <T1, T2, T3, T4> Result<Record4<T1, T2, T3, T4>> into(Field<T1> field1, Field<T2> field2, Field<T3> field3, Field<T4> field4) {
      return this.into(field1, field2, field3, field4);
   }

   public final <T1, T2, T3, T4, T5> Result<Record5<T1, T2, T3, T4, T5>> into(Field<T1> field1, Field<T2> field2, Field<T3> field3, Field<T4> field4, Field<T5> field5) {
      return this.into(field1, field2, field3, field4, field5);
   }

   public final <T1, T2, T3, T4, T5, T6> Result<Record6<T1, T2, T3, T4, T5, T6>> into(Field<T1> field1, Field<T2> field2, Field<T3> field3, Field<T4> field4, Field<T5> field5, Field<T6> field6) {
      return this.into(field1, field2, field3, field4, field5, field6);
   }

   public final <T1, T2, T3, T4, T5, T6, T7> Result<Record7<T1, T2, T3, T4, T5, T6, T7>> into(Field<T1> field1, Field<T2> field2, Field<T3> field3, Field<T4> field4, Field<T5> field5, Field<T6> field6, Field<T7> field7) {
      return this.into(field1, field2, field3, field4, field5, field6, field7);
   }

   public final <T1, T2, T3, T4, T5, T6, T7, T8> Result<Record8<T1, T2, T3, T4, T5, T6, T7, T8>> into(Field<T1> field1, Field<T2> field2, Field<T3> field3, Field<T4> field4, Field<T5> field5, Field<T6> field6, Field<T7> field7, Field<T8> field8) {
      return this.into(field1, field2, field3, field4, field5, field6, field7, field8);
   }

   public final <T1, T2, T3, T4, T5, T6, T7, T8, T9> Result<Record9<T1, T2, T3, T4, T5, T6, T7, T8, T9>> into(Field<T1> field1, Field<T2> field2, Field<T3> field3, Field<T4> field4, Field<T5> field5, Field<T6> field6, Field<T7> field7, Field<T8> field8, Field<T9> field9) {
      return this.into(field1, field2, field3, field4, field5, field6, field7, field8, field9);
   }

   public final <T1, T2, T3, T4, T5, T6, T7, T8, T9, T10> Result<Record10<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10>> into(Field<T1> field1, Field<T2> field2, Field<T3> field3, Field<T4> field4, Field<T5> field5, Field<T6> field6, Field<T7> field7, Field<T8> field8, Field<T9> field9, Field<T10> field10) {
      return this.into(field1, field2, field3, field4, field5, field6, field7, field8, field9, field10);
   }

   public final <T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11> Result<Record11<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11>> into(Field<T1> field1, Field<T2> field2, Field<T3> field3, Field<T4> field4, Field<T5> field5, Field<T6> field6, Field<T7> field7, Field<T8> field8, Field<T9> field9, Field<T10> field10, Field<T11> field11) {
      return this.into(field1, field2, field3, field4, field5, field6, field7, field8, field9, field10, field11);
   }

   public final <T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12> Result<Record12<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12>> into(Field<T1> field1, Field<T2> field2, Field<T3> field3, Field<T4> field4, Field<T5> field5, Field<T6> field6, Field<T7> field7, Field<T8> field8, Field<T9> field9, Field<T10> field10, Field<T11> field11, Field<T12> field12) {
      return this.into(field1, field2, field3, field4, field5, field6, field7, field8, field9, field10, field11, field12);
   }

   public final <T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13> Result<Record13<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13>> into(Field<T1> field1, Field<T2> field2, Field<T3> field3, Field<T4> field4, Field<T5> field5, Field<T6> field6, Field<T7> field7, Field<T8> field8, Field<T9> field9, Field<T10> field10, Field<T11> field11, Field<T12> field12, Field<T13> field13) {
      return this.into(field1, field2, field3, field4, field5, field6, field7, field8, field9, field10, field11, field12, field13);
   }

   public final <T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14> Result<Record14<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14>> into(Field<T1> field1, Field<T2> field2, Field<T3> field3, Field<T4> field4, Field<T5> field5, Field<T6> field6, Field<T7> field7, Field<T8> field8, Field<T9> field9, Field<T10> field10, Field<T11> field11, Field<T12> field12, Field<T13> field13, Field<T14> field14) {
      return this.into(field1, field2, field3, field4, field5, field6, field7, field8, field9, field10, field11, field12, field13, field14);
   }

   public final <T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14, T15> Result<Record15<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14, T15>> into(Field<T1> field1, Field<T2> field2, Field<T3> field3, Field<T4> field4, Field<T5> field5, Field<T6> field6, Field<T7> field7, Field<T8> field8, Field<T9> field9, Field<T10> field10, Field<T11> field11, Field<T12> field12, Field<T13> field13, Field<T14> field14, Field<T15> field15) {
      return this.into(field1, field2, field3, field4, field5, field6, field7, field8, field9, field10, field11, field12, field13, field14, field15);
   }

   public final <T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14, T15, T16> Result<Record16<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14, T15, T16>> into(Field<T1> field1, Field<T2> field2, Field<T3> field3, Field<T4> field4, Field<T5> field5, Field<T6> field6, Field<T7> field7, Field<T8> field8, Field<T9> field9, Field<T10> field10, Field<T11> field11, Field<T12> field12, Field<T13> field13, Field<T14> field14, Field<T15> field15, Field<T16> field16) {
      return this.into(field1, field2, field3, field4, field5, field6, field7, field8, field9, field10, field11, field12, field13, field14, field15, field16);
   }

   public final <T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14, T15, T16, T17> Result<Record17<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14, T15, T16, T17>> into(Field<T1> field1, Field<T2> field2, Field<T3> field3, Field<T4> field4, Field<T5> field5, Field<T6> field6, Field<T7> field7, Field<T8> field8, Field<T9> field9, Field<T10> field10, Field<T11> field11, Field<T12> field12, Field<T13> field13, Field<T14> field14, Field<T15> field15, Field<T16> field16, Field<T17> field17) {
      return this.into(field1, field2, field3, field4, field5, field6, field7, field8, field9, field10, field11, field12, field13, field14, field15, field16, field17);
   }

   public final <T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14, T15, T16, T17, T18> Result<Record18<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14, T15, T16, T17, T18>> into(Field<T1> field1, Field<T2> field2, Field<T3> field3, Field<T4> field4, Field<T5> field5, Field<T6> field6, Field<T7> field7, Field<T8> field8, Field<T9> field9, Field<T10> field10, Field<T11> field11, Field<T12> field12, Field<T13> field13, Field<T14> field14, Field<T15> field15, Field<T16> field16, Field<T17> field17, Field<T18> field18) {
      return this.into(field1, field2, field3, field4, field5, field6, field7, field8, field9, field10, field11, field12, field13, field14, field15, field16, field17, field18);
   }

   public final <T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14, T15, T16, T17, T18, T19> Result<Record19<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14, T15, T16, T17, T18, T19>> into(Field<T1> field1, Field<T2> field2, Field<T3> field3, Field<T4> field4, Field<T5> field5, Field<T6> field6, Field<T7> field7, Field<T8> field8, Field<T9> field9, Field<T10> field10, Field<T11> field11, Field<T12> field12, Field<T13> field13, Field<T14> field14, Field<T15> field15, Field<T16> field16, Field<T17> field17, Field<T18> field18, Field<T19> field19) {
      return this.into(field1, field2, field3, field4, field5, field6, field7, field8, field9, field10, field11, field12, field13, field14, field15, field16, field17, field18, field19);
   }

   public final <T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14, T15, T16, T17, T18, T19, T20> Result<Record20<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14, T15, T16, T17, T18, T19, T20>> into(Field<T1> field1, Field<T2> field2, Field<T3> field3, Field<T4> field4, Field<T5> field5, Field<T6> field6, Field<T7> field7, Field<T8> field8, Field<T9> field9, Field<T10> field10, Field<T11> field11, Field<T12> field12, Field<T13> field13, Field<T14> field14, Field<T15> field15, Field<T16> field16, Field<T17> field17, Field<T18> field18, Field<T19> field19, Field<T20> field20) {
      return this.into(field1, field2, field3, field4, field5, field6, field7, field8, field9, field10, field11, field12, field13, field14, field15, field16, field17, field18, field19, field20);
   }

   public final <T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14, T15, T16, T17, T18, T19, T20, T21> Result<Record21<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14, T15, T16, T17, T18, T19, T20, T21>> into(Field<T1> field1, Field<T2> field2, Field<T3> field3, Field<T4> field4, Field<T5> field5, Field<T6> field6, Field<T7> field7, Field<T8> field8, Field<T9> field9, Field<T10> field10, Field<T11> field11, Field<T12> field12, Field<T13> field13, Field<T14> field14, Field<T15> field15, Field<T16> field16, Field<T17> field17, Field<T18> field18, Field<T19> field19, Field<T20> field20, Field<T21> field21) {
      return this.into(field1, field2, field3, field4, field5, field6, field7, field8, field9, field10, field11, field12, field13, field14, field15, field16, field17, field18, field19, field20, field21);
   }

   public final <T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14, T15, T16, T17, T18, T19, T20, T21, T22> Result<Record22<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14, T15, T16, T17, T18, T19, T20, T21, T22>> into(Field<T1> field1, Field<T2> field2, Field<T3> field3, Field<T4> field4, Field<T5> field5, Field<T6> field6, Field<T7> field7, Field<T8> field8, Field<T9> field9, Field<T10> field10, Field<T11> field11, Field<T12> field12, Field<T13> field13, Field<T14> field14, Field<T15> field15, Field<T16> field16, Field<T17> field17, Field<T18> field18, Field<T19> field19, Field<T20> field20, Field<T21> field21, Field<T22> field22) {
      return this.into(field1, field2, field3, field4, field5, field6, field7, field8, field9, field10, field11, field12, field13, field14, field15, field16, field17, field18, field19, field20, field21, field22);
   }

   public final <E> List<E> into(Class<? extends E> type) {
      List<E> list = new ArrayList(this.size());
      RecordMapper<R, E> mapper = Tools.configuration((Attachable)this).recordMapperProvider().provide(this.fields, type);
      Iterator var4 = this.iterator();

      while(var4.hasNext()) {
         R record = (Record)var4.next();
         list.add(mapper.map(record));
      }

      return list;
   }

   public final <Z extends Record> Result<Z> into(Table<Z> table) {
      Result<Z> list = new ResultImpl(this.configuration(), table.fields());
      Iterator var3 = this.iterator();

      while(var3.hasNext()) {
         R record = (Record)var3.next();
         list.add(record.into(table));
      }

      return list;
   }

   public final <H extends RecordHandler<? super R>> H into(H handler) {
      Iterator var2 = this.iterator();

      while(var2.hasNext()) {
         R record = (Record)var2.next();
         handler.next(record);
      }

      return handler;
   }

   public final ResultSet intoResultSet() {
      return new MockResultSet(this);
   }

   public final <E> List<E> map(RecordMapper<? super R, E> mapper) {
      List<E> result = new ArrayList();
      Iterator var3 = this.iterator();

      while(var3.hasNext()) {
         R record = (Record)var3.next();
         result.add(mapper.map(record));
      }

      return result;
   }

   public final <T extends Comparable<? super T>> Result<R> sortAsc(Field<T> field) {
      return this.sortAsc((Field)field, new ResultImpl.NaturalComparator());
   }

   public final Result<R> sortAsc(int fieldIndex) {
      return this.sortAsc(fieldIndex, new ResultImpl.NaturalComparator());
   }

   public final Result<R> sortAsc(String fieldName) {
      return this.sortAsc((String)fieldName, new ResultImpl.NaturalComparator());
   }

   public final Result<R> sortAsc(Name fieldName) {
      return this.sortAsc((Name)fieldName, new ResultImpl.NaturalComparator());
   }

   public final <T> Result<R> sortAsc(Field<T> field, Comparator<? super T> comparator) {
      return this.sortAsc(Tools.indexOrFail(this.fieldsRow(), field), comparator);
   }

   public final Result<R> sortAsc(int fieldIndex, Comparator<?> comparator) {
      return this.sortAsc((Comparator)(new ResultImpl.RecordComparator(fieldIndex, comparator)));
   }

   public final Result<R> sortAsc(String fieldName, Comparator<?> comparator) {
      return this.sortAsc(Tools.indexOrFail(this.fieldsRow(), fieldName), comparator);
   }

   public final Result<R> sortAsc(Name fieldName, Comparator<?> comparator) {
      return this.sortAsc(Tools.indexOrFail(this.fieldsRow(), fieldName), comparator);
   }

   public final Result<R> sortAsc(Comparator<? super R> comparator) {
      Collections.sort(this, comparator);
      return this;
   }

   public final <T extends Comparable<? super T>> Result<R> sortDesc(Field<T> field) {
      return this.sortAsc(field, Collections.reverseOrder(new ResultImpl.NaturalComparator()));
   }

   public final Result<R> sortDesc(int fieldIndex) {
      return this.sortAsc(fieldIndex, Collections.reverseOrder(new ResultImpl.NaturalComparator()));
   }

   public final Result<R> sortDesc(String fieldName) {
      return this.sortAsc(fieldName, Collections.reverseOrder(new ResultImpl.NaturalComparator()));
   }

   public final Result<R> sortDesc(Name fieldName) {
      return this.sortAsc(fieldName, Collections.reverseOrder(new ResultImpl.NaturalComparator()));
   }

   public final <T> Result<R> sortDesc(Field<T> field, Comparator<? super T> comparator) {
      return this.sortAsc(field, Collections.reverseOrder(comparator));
   }

   public final Result<R> sortDesc(int fieldIndex, Comparator<?> comparator) {
      return this.sortAsc(fieldIndex, Collections.reverseOrder(comparator));
   }

   public final Result<R> sortDesc(String fieldName, Comparator<?> comparator) {
      return this.sortAsc(fieldName, Collections.reverseOrder(comparator));
   }

   public final Result<R> sortDesc(Name fieldName, Comparator<?> comparator) {
      return this.sortAsc(fieldName, Collections.reverseOrder(comparator));
   }

   public final Result<R> sortDesc(Comparator<? super R> comparator) {
      return this.sortAsc(Collections.reverseOrder(comparator));
   }

   public final Result<R> intern(Field<?>... f) {
      return this.intern(this.fields.indexesOf(f));
   }

   public final Result<R> intern(int... fieldIndexes) {
      int[] var2 = fieldIndexes;
      int var3 = fieldIndexes.length;

      for(int var4 = 0; var4 < var3; ++var4) {
         int fieldIndex = var2[var4];
         if (this.fields.fields[fieldIndex].getType() == String.class) {
            Iterator var6 = this.iterator();

            while(var6.hasNext()) {
               Record record = (Record)var6.next();
               ((AbstractRecord)record).intern0(fieldIndex);
            }
         }
      }

      return this;
   }

   public final Result<R> intern(String... fieldNames) {
      return this.intern(this.fields.indexesOf(fieldNames));
   }

   public final Result<R> intern(Name... fieldNames) {
      return this.intern(this.fields.indexesOf(fieldNames));
   }

   public final <O extends UpdatableRecord<O>> Result<O> fetchParents(ForeignKey<R, O> key) {
      return key.fetchParents((Collection)this);
   }

   public final <O extends TableRecord<O>> Result<O> fetchChildren(ForeignKey<O, R> key) {
      return key.fetchChildren((Collection)this);
   }

   public String toString() {
      return this.format();
   }

   public int hashCode() {
      return this.records.hashCode();
   }

   public boolean equals(Object obj) {
      if (this == obj) {
         return true;
      } else if (obj instanceof ResultImpl) {
         ResultImpl<R> other = (ResultImpl)obj;
         return this.records.equals(other.records);
      } else {
         return false;
      }
   }

   public final int size() {
      return this.records.size();
   }

   public final boolean contains(Object o) {
      return this.records.contains(o);
   }

   public final Object[] toArray() {
      return this.records.toArray();
   }

   public final <T> T[] toArray(T[] a) {
      return this.records.toArray(a);
   }

   public final boolean add(R e) {
      return this.records.add(e);
   }

   public final boolean remove(Object o) {
      return this.records.remove(o);
   }

   public final boolean containsAll(Collection<?> c) {
      return this.records.containsAll(c);
   }

   public final boolean addAll(Collection<? extends R> c) {
      return this.records.addAll(c);
   }

   public final boolean addAll(int index, Collection<? extends R> c) {
      return this.records.addAll(index, c);
   }

   public final boolean removeAll(Collection<?> c) {
      return this.records.removeAll(c);
   }

   public final boolean retainAll(Collection<?> c) {
      return this.records.retainAll(c);
   }

   public final void clear() {
      this.records.clear();
   }

   public final R get(int index) {
      return (Record)this.records.get(index);
   }

   public final R set(int index, R element) {
      return (Record)this.records.set(index, element);
   }

   public final void add(int index, R element) {
      this.records.add(index, element);
   }

   public final R remove(int index) {
      return (Record)this.records.remove(index);
   }

   public final int indexOf(Object o) {
      return this.records.indexOf(o);
   }

   public final int lastIndexOf(Object o) {
      return this.records.lastIndexOf(o);
   }

   public final Iterator<R> iterator() {
      return this.records.iterator();
   }

   public final ListIterator<R> listIterator() {
      return this.records.listIterator();
   }

   public final ListIterator<R> listIterator(int index) {
      return this.records.listIterator(index);
   }

   public final List<R> subList(int fromIndex, int toIndex) {
      return this.records.subList(fromIndex, toIndex);
   }

   private static class NaturalComparator<T extends Comparable<? super T>> implements Comparator<T> {
      private NaturalComparator() {
      }

      public int compare(T o1, T o2) {
         if (o1 == null && o2 == null) {
            return 0;
         } else if (o1 == null) {
            return -1;
         } else {
            return o2 == null ? 1 : o1.compareTo(o2);
         }
      }

      // $FF: synthetic method
      NaturalComparator(Object x0) {
         this();
      }
   }

   private static class RecordComparator<T, R extends Record> implements Comparator<R> {
      private final Comparator<? super T> comparator;
      private final int fieldIndex;

      RecordComparator(int fieldIndex, Comparator<? super T> comparator) {
         this.fieldIndex = fieldIndex;
         this.comparator = comparator;
      }

      public int compare(R record1, R record2) {
         return this.comparator.compare(record1.get(this.fieldIndex), record2.get(this.fieldIndex));
      }
   }
}
