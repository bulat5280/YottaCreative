package com.mysql.cj.mysqlx.result;

import com.mysql.cj.api.result.RowList;
import com.mysql.cj.core.result.Field;
import com.mysql.cj.mysqlx.io.MysqlxProtocol;
import java.util.ArrayList;
import java.util.NoSuchElementException;

public class MysqlxRowInputStream implements RowList {
   private ArrayList<Field> metadata;
   private MysqlxProtocol protocol;
   private boolean isDone = false;
   private int position = -1;
   private MysqlxRow next;

   public MysqlxRowInputStream(ArrayList<Field> metadata, MysqlxProtocol protocol) {
      this.metadata = metadata;
      this.protocol = protocol;
   }

   public MysqlxRow readRow() {
      if (!this.hasNext()) {
         this.isDone = true;
         return null;
      } else {
         ++this.position;
         MysqlxRow r = this.next;
         this.next = null;
         return r;
      }
   }

   public MysqlxRow next() {
      if (!this.hasNext()) {
         throw new NoSuchElementException();
      } else {
         return this.readRow();
      }
   }

   public boolean hasNext() {
      if (this.isDone) {
         return false;
      } else {
         if (this.next == null) {
            this.next = this.protocol.readRowOrNull(this.metadata);
         }

         return this.next != null;
      }
   }

   public int getPosition() {
      return this.position;
   }
}
