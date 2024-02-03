package org.jooq.util.derby;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import org.jooq.Condition;
import org.jooq.Field;
import org.jooq.Record;
import org.jooq.impl.DSL;
import org.jooq.util.AbstractTableDefinition;
import org.jooq.util.ColumnDefinition;
import org.jooq.util.DataTypeDefinition;
import org.jooq.util.DefaultColumnDefinition;
import org.jooq.util.DefaultDataTypeDefinition;
import org.jooq.util.SchemaDefinition;
import org.jooq.util.derby.sys.tables.Syscolumns;

public class DerbyTableDefinition extends AbstractTableDefinition {
   private final String tableid;

   public DerbyTableDefinition(SchemaDefinition schema, String name, String tableid) {
      super(schema, name, "");
      this.tableid = tableid;
   }

   public List<ColumnDefinition> getElements0() throws SQLException {
      List<ColumnDefinition> result = new ArrayList();
      Iterator var2 = this.create().select(Syscolumns.COLUMNNAME, Syscolumns.COLUMNNUMBER, Syscolumns.COLUMNDATATYPE, Syscolumns.COLUMNDEFAULT, Syscolumns.AUTOINCREMENTINC).from(Syscolumns.SYSCOLUMNS).where(new Condition[]{Syscolumns.REFERENCEID.equal(DSL.inline(this.tableid))}).orderBy(Syscolumns.COLUMNNUMBER).fetch().iterator();

      while(var2.hasNext()) {
         Record record = (Record)var2.next();
         String typeName = (String)record.get((Field)Syscolumns.COLUMNDATATYPE, (Class)String.class);
         Number precision = this.parsePrecision(typeName);
         Number scale = this.parseScale(typeName);
         DataTypeDefinition type = new DefaultDataTypeDefinition(this.getDatabase(), this.getSchema(), this.parseTypeName(typeName), precision, precision, scale, !this.parseNotNull(typeName), (String)record.get((Field)Syscolumns.COLUMNDEFAULT));
         ColumnDefinition column = new DefaultColumnDefinition(this.getDatabase().getTable(this.getSchema(), this.getName()), (String)record.get((Field)Syscolumns.COLUMNNAME), (Integer)record.get((Field)Syscolumns.COLUMNNUMBER), type, null != record.get((Field)Syscolumns.AUTOINCREMENTINC), (String)null);
         result.add(column);
      }

      return result;
   }
}
