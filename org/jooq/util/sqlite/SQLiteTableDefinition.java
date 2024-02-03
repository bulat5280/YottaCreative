package org.jooq.util.sqlite;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import org.jooq.Condition;
import org.jooq.Record;
import org.jooq.util.AbstractTableDefinition;
import org.jooq.util.ColumnDefinition;
import org.jooq.util.DefaultColumnDefinition;
import org.jooq.util.DefaultDataTypeDefinition;
import org.jooq.util.SchemaDefinition;
import org.jooq.util.sqlite.sqlite_master.SQLiteMaster;

public class SQLiteTableDefinition extends AbstractTableDefinition {
   private static Boolean existsSqliteSequence;

   public SQLiteTableDefinition(SchemaDefinition schema, String name, String comment) {
      super(schema, name, comment);
   }

   public List<ColumnDefinition> getElements0() throws SQLException {
      List<ColumnDefinition> result = new ArrayList();
      int position = 0;
      Iterator var3 = this.create().fetch("pragma table_info('" + this.getName() + "')").iterator();

      while(var3.hasNext()) {
         Record record = (Record)var3.next();
         ++position;
         String name = (String)record.get("name", String.class);
         String dataType = ((String)record.get("type", String.class)).replaceAll("\\(\\d+(\\s*,\\s*\\d+)?\\)", "");
         Number precision = this.parsePrecision((String)record.get("type", String.class));
         Number scale = this.parseScale((String)record.get("type", String.class));
         int pk = (Integer)record.get("pk", Integer.TYPE);
         boolean identity = pk > 0 && this.existsSqliteSequence() && (Boolean)this.create().fetchOne("select count(*) from sqlite_sequence where name = ?", this.getName()).get(0, (Class)Boolean.class);
         DefaultDataTypeDefinition type = new DefaultDataTypeDefinition(this.getDatabase(), this.getSchema(), dataType, precision, precision, scale, !(Boolean)record.get("notnull", Boolean.TYPE), (String)record.get("dflt_value", String.class));
         ColumnDefinition column = new DefaultColumnDefinition(this.getDatabase().getTable(this.getSchema(), this.getName()), name, position, type, identity, (String)null);
         result.add(column);
      }

      return result;
   }

   private boolean existsSqliteSequence() {
      if (existsSqliteSequence == null) {
         existsSqliteSequence = (Boolean)this.create().selectCount().from(SQLiteMaster.SQLITE_MASTER).where(new Condition[]{SQLiteMaster.NAME.lower().eq((Object)"sqlite_sequence")}).fetchOne(0, Boolean.TYPE);
      }

      return existsSqliteSequence;
   }
}
