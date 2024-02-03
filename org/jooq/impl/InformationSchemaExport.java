package org.jooq.impl;

import java.util.Iterator;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;
import org.jooq.Configuration;
import org.jooq.Field;
import org.jooq.ForeignKey;
import org.jooq.Key;
import org.jooq.Schema;
import org.jooq.Sequence;
import org.jooq.Table;
import org.jooq.UniqueKey;
import org.jooq.tools.StringUtils;
import org.jooq.util.xml.jaxb.Column;
import org.jooq.util.xml.jaxb.InformationSchema;
import org.jooq.util.xml.jaxb.KeyColumnUsage;
import org.jooq.util.xml.jaxb.ReferentialConstraint;
import org.jooq.util.xml.jaxb.TableConstraint;
import org.jooq.util.xml.jaxb.TableConstraintType;

final class InformationSchemaExport {
   static final InformationSchema exportTables(Configuration configuration, List<Table<?>> tables) {
      InformationSchema result = new InformationSchema();
      Set<Schema> includedSchemas = new LinkedHashSet();
      Set<Table<?>> includedTables = new LinkedHashSet(tables);
      Iterator var5 = tables.iterator();

      Table t;
      while(var5.hasNext()) {
         t = (Table)var5.next();
         includedSchemas.add(t.getSchema());
      }

      var5 = includedSchemas.iterator();

      while(var5.hasNext()) {
         Schema s = (Schema)var5.next();
         exportSchema0(result, s);
      }

      var5 = tables.iterator();

      while(var5.hasNext()) {
         t = (Table)var5.next();
         exportTable0(configuration, result, t, includedTables);
      }

      return result;
   }

   static final InformationSchema exportSchemas(Configuration configuration, List<Schema> schemas) {
      InformationSchema result = new InformationSchema();
      Set<Table<?>> includedTables = new LinkedHashSet();
      Iterator var4 = schemas.iterator();

      Schema s;
      Iterator var6;
      Table t;
      while(var4.hasNext()) {
         s = (Schema)var4.next();
         var6 = s.getTables().iterator();

         while(var6.hasNext()) {
            t = (Table)var6.next();
            includedTables.add(t);
         }
      }

      var4 = schemas.iterator();

      while(var4.hasNext()) {
         s = (Schema)var4.next();
         exportSchema0(result, s);
         var6 = s.getTables().iterator();

         while(var6.hasNext()) {
            t = (Table)var6.next();
            exportTable0(configuration, result, t, includedTables);
         }

         var6 = s.getSequences().iterator();

         while(var6.hasNext()) {
            Sequence<?> q = (Sequence)var6.next();
            exportSequences0(configuration, result, q);
         }
      }

      return result;
   }

   private static final void exportSequences0(Configuration configuration, InformationSchema result, Sequence<?> q) {
      org.jooq.util.xml.jaxb.Sequence iq = new org.jooq.util.xml.jaxb.Sequence();
      if (!StringUtils.isBlank(q.getCatalog().getName())) {
         iq.setSequenceCatalog(q.getCatalog().getName());
      }

      if (!StringUtils.isBlank(q.getSchema().getName())) {
         iq.setSequenceSchema(q.getSchema().getName());
      }

      iq.setSequenceName(q.getName());
      iq.setDataType(q.getDataType().getTypeName(configuration));
      if (q.getDataType().hasLength()) {
         iq.setCharacterMaximumLength(q.getDataType().length());
      }

      if (q.getDataType().hasPrecision()) {
         iq.setNumericPrecision(q.getDataType().precision());
      }

      if (q.getDataType().hasScale()) {
         iq.setNumericScale(q.getDataType().scale());
      }

      result.getSequences().add(iq);
   }

   private static final void exportSchema0(InformationSchema result, Schema s) {
      org.jooq.util.xml.jaxb.Schema is = new org.jooq.util.xml.jaxb.Schema();
      if (!StringUtils.isBlank(s.getCatalog().getName())) {
         is.setCatalogName(s.getCatalog().getName());
      }

      if (!StringUtils.isBlank(s.getName())) {
         is.setSchemaName(s.getName());
         result.getSchemata().add(is);
      }

   }

   private static final void exportTable0(Configuration configuration, InformationSchema result, Table<?> t, Set<Table<?>> includedTables) {
      org.jooq.util.xml.jaxb.Table it = new org.jooq.util.xml.jaxb.Table();
      if (!StringUtils.isBlank(t.getCatalog().getName())) {
         it.setTableCatalog(t.getCatalog().getName());
      }

      if (!StringUtils.isBlank(t.getSchema().getName())) {
         it.setTableSchema(t.getSchema().getName());
      }

      it.setTableName(t.getName());
      result.getTables().add(it);
      Field<?>[] fields = t.fields();

      for(int i = 0; i < fields.length; ++i) {
         Field<?> f = fields[i];
         Column ic = new Column();
         if (!StringUtils.isBlank(t.getCatalog().getName())) {
            ic.setTableCatalog(t.getCatalog().getName());
         }

         if (!StringUtils.isBlank(t.getSchema().getName())) {
            ic.setTableSchema(t.getSchema().getName());
         }

         ic.setTableName(t.getName());
         ic.setColumnName(t.getName());
         ic.setDataType(f.getDataType().getTypeName(configuration));
         if (f.getDataType().hasLength()) {
            ic.setCharacterMaximumLength(f.getDataType().length());
         }

         if (f.getDataType().hasPrecision()) {
            ic.setNumericPrecision(f.getDataType().precision());
         }

         if (f.getDataType().hasScale()) {
            ic.setNumericScale(f.getDataType().scale());
         }

         ic.setColumnDefault(DSL.using(configuration).render(f.getDataType().defaultValue()));
         ic.setIsNullable(f.getDataType().nullable());
         ic.setOrdinalPosition(i + 1);
         result.getColumns().add(ic);
      }

      Iterator var9 = t.getKeys().iterator();

      while(var9.hasNext()) {
         UniqueKey<?> key = (UniqueKey)var9.next();
         exportKey0(result, t, key, key.isPrimary() ? TableConstraintType.PRIMARY_KEY : TableConstraintType.UNIQUE);
      }

      var9 = t.getReferences().iterator();

      while(var9.hasNext()) {
         ForeignKey<?, ?> fk = (ForeignKey)var9.next();
         if (includedTables.contains(fk.getKey().getTable())) {
            exportKey0(result, t, fk, TableConstraintType.FOREIGN_KEY);
         }
      }

   }

   private static final void exportKey0(InformationSchema result, Table<?> t, Key<?> key, TableConstraintType constraintType) {
      TableConstraint tc = new TableConstraint();
      String catalogName = t.getCatalog().getName();
      String schemaName = t.getSchema().getName();
      tc.setConstraintName(key.getName());
      tc.setConstraintType(constraintType);
      if (!StringUtils.isBlank(catalogName)) {
         tc.setConstraintCatalog(catalogName);
         tc.setTableCatalog(catalogName);
      }

      if (!StringUtils.isBlank(schemaName)) {
         tc.setConstraintSchema(schemaName);
         tc.setTableSchema(schemaName);
      }

      tc.setTableName(t.getName());
      result.getTableConstraints().add(tc);
      int i = 0;
      Iterator var8 = key.getFields().iterator();

      while(var8.hasNext()) {
         Field<?> f = (Field)var8.next();
         KeyColumnUsage kc = new KeyColumnUsage();
         if (!StringUtils.isBlank(catalogName)) {
            kc.setConstraintCatalog(catalogName);
            kc.setTableCatalog(catalogName);
         }

         if (!StringUtils.isBlank(schemaName)) {
            kc.setConstraintSchema(schemaName);
            kc.setTableSchema(schemaName);
         }

         kc.setColumnName(f.getName());
         kc.setTableName(t.getName());
         ++i;
         kc.setOrdinalPosition(i);
         kc.setConstraintName(key.getName());
         result.getKeyColumnUsages().add(kc);
      }

      if (constraintType == TableConstraintType.FOREIGN_KEY) {
         ReferentialConstraint rc = new ReferentialConstraint();
         UniqueKey<?> uk = ((ForeignKey)key).getKey();
         String ukCatalogName = uk.getTable().getCatalog().getName();
         String ukSchemaName = uk.getTable().getSchema().getName();
         if (!StringUtils.isBlank(catalogName)) {
            rc.setConstraintCatalog(catalogName);
         }

         if (!StringUtils.isBlank(ukCatalogName)) {
            rc.setUniqueConstraintCatalog(ukCatalogName);
         }

         if (!StringUtils.isBlank(schemaName)) {
            rc.setConstraintSchema(schemaName);
         }

         if (!StringUtils.isBlank(ukSchemaName)) {
            rc.setUniqueConstraintSchema(ukSchemaName);
         }

         rc.setConstraintName(key.getName());
         rc.setUniqueConstraintName(uk.getName());
         result.getReferentialConstraints().add(rc);
      }

   }

   private InformationSchemaExport() {
   }
}
