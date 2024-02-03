package org.jooq.impl;

import java.io.BufferedReader;
import java.io.Closeable;
import java.io.IOException;
import java.io.Reader;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import org.jooq.tools.json.ContainerFactory;
import org.jooq.tools.json.JSONParser;
import org.jooq.tools.json.ParseException;

final class JSONReader implements Closeable {
   private final BufferedReader br;
   private final JSONParser parser;
   private String[] fieldNames;
   private Map<String, Integer> fieldIndexes;
   private List<String[]> records;

   JSONReader(Reader reader) {
      this.br = new BufferedReader(reader);
      this.parser = new JSONParser();
   }

   final List<String[]> readAll() throws IOException {
      if (this.records == null) {
         try {
            LinkedHashMap<String, LinkedList<?>> jsonRoot = this.getJsonRoot();
            this.readFields(jsonRoot);
            this.readRecords(jsonRoot);
         } catch (ParseException var2) {
            throw new RuntimeException(var2);
         }
      }

      return this.records;
   }

   final String[] getFields() throws IOException {
      if (this.fieldNames == null) {
         this.readAll();
      }

      return this.fieldNames;
   }

   public final void close() throws IOException {
      this.br.close();
   }

   private final void readRecords(LinkedHashMap<String, LinkedList<?>> jsonRoot) {
      this.records = new ArrayList();

      String[] v;
      for(Iterator var2 = ((LinkedList)jsonRoot.get("records")).iterator(); var2.hasNext(); this.records.add(v)) {
         Object record = var2.next();
         v = new String[this.fieldNames.length];
         int i = 0;
         Iterator var6;
         Object value;
         if (record instanceof LinkedList) {
            for(var6 = ((LinkedList)record).iterator(); var6.hasNext(); v[i++] = value == null ? null : String.valueOf(value)) {
               value = var6.next();
            }
         } else {
            if (!(record instanceof LinkedHashMap)) {
               throw new IllegalArgumentException("Ill formed JSON : " + jsonRoot);
            }

            Entry entry;
            for(var6 = ((LinkedHashMap)record).entrySet().iterator(); var6.hasNext(); v[(Integer)this.fieldIndexes.get(entry.getKey())] = entry.getValue() == null ? null : String.valueOf(entry.getValue())) {
               entry = (Entry)var6.next();
            }
         }
      }

   }

   private LinkedHashMap<String, LinkedList<?>> getJsonRoot() throws IOException, ParseException {
      Object parse = this.parser.parse((Reader)this.br, (ContainerFactory)(new ContainerFactory() {
         public LinkedHashMap<String, Object> createObjectContainer() {
            return new LinkedHashMap();
         }

         public List<Object> createArrayContainer() {
            return new LinkedList();
         }
      }));
      return (LinkedHashMap)parse;
   }

   private final void readFields(LinkedHashMap<String, LinkedList<?>> jsonRoot) {
      LinkedList<LinkedHashMap<String, String>> fieldEntries = (LinkedList)jsonRoot.get("fields");
      this.fieldNames = new String[fieldEntries.size()];
      this.fieldIndexes = new HashMap();
      int i = 0;

      for(Iterator var4 = fieldEntries.iterator(); var4.hasNext(); ++i) {
         LinkedHashMap<String, String> key = (LinkedHashMap)var4.next();
         String name = (String)key.get("name");
         this.fieldNames[i] = name;
         this.fieldIndexes.put(name, i);
      }

   }
}
