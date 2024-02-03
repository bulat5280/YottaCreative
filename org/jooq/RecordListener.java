package org.jooq;

import java.util.EventListener;

public interface RecordListener extends EventListener {
   void storeStart(RecordContext var1);

   void storeEnd(RecordContext var1);

   void insertStart(RecordContext var1);

   void insertEnd(RecordContext var1);

   void updateStart(RecordContext var1);

   void updateEnd(RecordContext var1);

   void deleteStart(RecordContext var1);

   void deleteEnd(RecordContext var1);

   void loadStart(RecordContext var1);

   void loadEnd(RecordContext var1);

   void refreshStart(RecordContext var1);

   void refreshEnd(RecordContext var1);

   void exception(RecordContext var1);
}
