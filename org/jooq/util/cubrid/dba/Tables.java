package org.jooq.util.cubrid.dba;

import org.jooq.util.cubrid.dba.tables.DbAttribute;
import org.jooq.util.cubrid.dba.tables.DbClass;
import org.jooq.util.cubrid.dba.tables.DbIndex;
import org.jooq.util.cubrid.dba.tables.DbIndexKey;
import org.jooq.util.cubrid.dba.tables.DbSerial;
import org.jooq.util.cubrid.dba.tables.DbUser;

public class Tables {
   public static final DbAttribute DB_ATTRIBUTE;
   public static final DbClass DB_CLASS;
   public static final DbIndex DB_INDEX;
   public static final DbIndexKey DB_INDEX_KEY;
   public static final DbSerial DB_SERIAL;
   public static final DbUser DB_USER;

   static {
      DB_ATTRIBUTE = DbAttribute.DB_ATTRIBUTE;
      DB_CLASS = DbClass.DB_CLASS;
      DB_INDEX = DbIndex.DB_INDEX;
      DB_INDEX_KEY = DbIndexKey.DB_INDEX_KEY;
      DB_SERIAL = DbSerial.DB_SERIAL;
      DB_USER = DbUser.DB_USER;
   }
}
