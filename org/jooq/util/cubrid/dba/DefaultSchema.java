package org.jooq.util.cubrid.dba;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import org.jooq.Table;
import org.jooq.impl.SchemaImpl;
import org.jooq.util.cubrid.dba.tables.DbAttribute;
import org.jooq.util.cubrid.dba.tables.DbClass;
import org.jooq.util.cubrid.dba.tables.DbIndex;
import org.jooq.util.cubrid.dba.tables.DbIndexKey;
import org.jooq.util.cubrid.dba.tables.DbSerial;
import org.jooq.util.cubrid.dba.tables.DbUser;

public class DefaultSchema extends SchemaImpl {
   private static final long serialVersionUID = 191182248L;
   public static final DefaultSchema DEFAULT_SCHEMA = new DefaultSchema();

   private DefaultSchema() {
      super("");
   }

   public final List<Table<?>> getTables() {
      List result = new ArrayList();
      result.addAll(this.getTables0());
      return result;
   }

   private final List<Table<?>> getTables0() {
      return Arrays.asList(DbAttribute.DB_ATTRIBUTE, DbClass.DB_CLASS, DbIndex.DB_INDEX, DbIndexKey.DB_INDEX_KEY, DbSerial.DB_SERIAL, DbUser.DB_USER);
   }
}
