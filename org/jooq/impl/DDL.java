package org.jooq.impl;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.EnumSet;
import java.util.Iterator;
import java.util.List;
import org.jooq.Catalog;
import org.jooq.Constraint;
import org.jooq.DDLFlag;
import org.jooq.DSLContext;
import org.jooq.Field;
import org.jooq.ForeignKey;
import org.jooq.Queries;
import org.jooq.Query;
import org.jooq.Schema;
import org.jooq.Table;
import org.jooq.UniqueKey;

final class DDL {
   private final DSLContext ctx;
   private final EnumSet<DDLFlag> flags;

   DDL(DSLContext ctx, DDLFlag... flags) {
      this.ctx = ctx;
      this.flags = EnumSet.noneOf(DDLFlag.class);
      DDLFlag[] var3 = flags;
      int var4 = flags.length;

      for(int var5 = 0; var5 < var4; ++var5) {
         DDLFlag flag = var3[var5];
         this.flags.add(flag);
      }

   }

   final Queries queries(Table<?> table) {
      List<Constraint> constraints = new ArrayList();
      if (this.flags.contains(DDLFlag.TABLE)) {
         Iterator var3;
         UniqueKey key;
         if (this.flags.contains(DDLFlag.PRIMARY_KEY)) {
            var3 = table.getKeys().iterator();

            while(var3.hasNext()) {
               key = (UniqueKey)var3.next();
               if (key.isPrimary()) {
                  constraints.add(DSL.constraint(key.getName()).primaryKey((Field[])key.getFieldsArray()));
               }
            }
         }

         if (this.flags.contains(DDLFlag.UNIQUE)) {
            var3 = table.getKeys().iterator();

            while(var3.hasNext()) {
               key = (UniqueKey)var3.next();
               if (!key.isPrimary()) {
                  constraints.add(DSL.constraint(key.getName()).unique((Field[])key.getFieldsArray()));
               }
            }
         }

         if (this.flags.contains(DDLFlag.FOREIGN_KEY)) {
            var3 = table.getReferences().iterator();

            while(var3.hasNext()) {
               ForeignKey<?, ?> key = (ForeignKey)var3.next();
               constraints.add(DSL.constraint(key.getName()).foreignKey((Field[])key.getFieldsArray()).references((Table)key.getKey().getTable(), (Field[])key.getKey().getFieldsArray()));
            }
         }
      }

      return DSL.queries(this.ctx.createTable(table).columns(table.fields()).constraints(constraints));
   }

   final Queries queries(Schema schema) {
      List<Query> queries = new ArrayList();
      if (this.flags.contains(DDLFlag.SCHEMA)) {
         queries.add(this.ctx.createSchema(schema.getName()));
      }

      if (this.flags.contains(DDLFlag.TABLE)) {
         Iterator var3;
         Table table;
         ArrayList constraints;
         for(var3 = schema.getTables().iterator(); var3.hasNext(); queries.add(this.ctx.createTable(table).columns(table.fields()).constraints(constraints))) {
            table = (Table)var3.next();
            constraints = new ArrayList();
            Iterator var6;
            UniqueKey key;
            if (this.flags.contains(DDLFlag.PRIMARY_KEY)) {
               var6 = table.getKeys().iterator();

               while(var6.hasNext()) {
                  key = (UniqueKey)var6.next();
                  if (key.isPrimary()) {
                     constraints.add(DSL.constraint(key.getName()).primaryKey((Field[])key.getFieldsArray()));
                  }
               }
            }

            if (this.flags.contains(DDLFlag.UNIQUE)) {
               var6 = table.getKeys().iterator();

               while(var6.hasNext()) {
                  key = (UniqueKey)var6.next();
                  if (!key.isPrimary()) {
                     constraints.add(DSL.constraint(key.getName()).unique((Field[])key.getFieldsArray()));
                  }
               }
            }
         }

         if (this.flags.contains(DDLFlag.FOREIGN_KEY)) {
            var3 = schema.getTables().iterator();

            while(var3.hasNext()) {
               table = (Table)var3.next();
               Iterator var8 = table.getReferences().iterator();

               while(var8.hasNext()) {
                  ForeignKey<?, ?> key = (ForeignKey)var8.next();
                  queries.add(this.ctx.alterTable(table).add(DSL.constraint(key.getName()).foreignKey((Field[])key.getFieldsArray()).references((Table)key.getKey().getTable(), (Field[])key.getKey().getFieldsArray())));
               }
            }
         }
      }

      return DSL.queries((Collection)queries);
   }

   final Queries queries(Catalog catalog) {
      List<Query> queries = new ArrayList();
      Iterator var3 = catalog.getSchemas().iterator();

      while(var3.hasNext()) {
         Schema schema = (Schema)var3.next();
         queries.addAll(Arrays.asList(this.queries(schema).queries()));
      }

      return DSL.queries((Collection)queries);
   }
}
