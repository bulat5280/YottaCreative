package org.jooq.util.cubrid.dba;

import org.jooq.Record;
import org.jooq.TableField;
import org.jooq.UniqueKey;
import org.jooq.impl.AbstractKeys;
import org.jooq.util.cubrid.dba.tables.DbSerial;

public class Keys {
   public static final UniqueKey<Record> DB_SERIAL__PK_DB_SERIAL_NAME;

   static {
      DB_SERIAL__PK_DB_SERIAL_NAME = Keys.UniqueKeys0.DB_SERIAL__PK_DB_SERIAL_NAME;
   }

   private static class UniqueKeys0 extends AbstractKeys {
      public static final UniqueKey<Record> DB_SERIAL__PK_DB_SERIAL_NAME;

      static {
         DB_SERIAL__PK_DB_SERIAL_NAME = createUniqueKey(DbSerial.DB_SERIAL, new TableField[]{DbSerial.DB_SERIAL.NAME});
      }
   }
}
