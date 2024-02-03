package com.mysql.cj.mysqlx.io;

import com.mysql.cj.api.x.Warning;
import com.mysql.cj.core.exceptions.WrongArgumentException;
import com.mysql.cj.core.io.StatementExecuteOk;
import com.mysql.cj.mysqlx.devapi.WarningImpl;
import com.mysql.cj.mysqlx.protobuf.MysqlxNotice;
import java.util.ArrayList;
import java.util.List;

public class StatementExecuteOkBuilder {
   private long rowsAffected = 0L;
   private Long lastInsertId = null;
   private List<Warning> warnings = new ArrayList();

   public void addNotice(MysqlxNotice.Frame notice) {
      if (notice.getType() == 1) {
         this.warnings.add(new WarningImpl((MysqlxNotice.Warning)MessageReader.parseNotice(notice.getPayload(), MysqlxNotice.Warning.class)));
      } else if (notice.getType() == 3) {
         MysqlxNotice.SessionStateChanged changeMsg = (MysqlxNotice.SessionStateChanged)MessageReader.parseNotice(notice.getPayload(), MysqlxNotice.SessionStateChanged.class);
         switch(changeMsg.getParam()) {
         case GENERATED_INSERT_ID:
            this.lastInsertId = changeMsg.getValue().getVUnsignedInt();
            break;
         case ROWS_AFFECTED:
            this.rowsAffected = changeMsg.getValue().getVUnsignedInt();
         case PRODUCED_MESSAGE:
            break;
         case CURRENT_SCHEMA:
         case ACCOUNT_EXPIRED:
         case ROWS_FOUND:
         case ROWS_MATCHED:
         case TRX_COMMITTED:
         case TRX_ROLLEDBACK:
         default:
            (new WrongArgumentException("unhandled SessionStateChanged notice! " + notice)).printStackTrace();
         }
      } else {
         (new WrongArgumentException("Got an unknown notice: " + notice)).printStackTrace();
      }

   }

   public StatementExecuteOk build() {
      return new StatementExecuteOk(this.rowsAffected, this.lastInsertId, this.warnings);
   }
}
