package com.mysql.cj.mysqla.io;

import com.mysql.cj.api.mysqla.io.PacketPayload;
import com.mysql.cj.api.mysqla.io.ProtocolEntityFactory;
import com.mysql.cj.api.mysqla.result.ColumnDefinition;

public class ColumnDefinitionFactory implements ProtocolEntityFactory<ColumnDefinition> {
   private long columnCount;
   private ColumnDefinition columnDefinitionFromCache;

   public ColumnDefinitionFactory(long columnCount, ColumnDefinition columnDefinitionFromCache) {
      this.columnCount = columnCount;
      this.columnDefinitionFromCache = columnDefinitionFromCache;
   }

   public long getColumnCount() {
      return this.columnCount;
   }

   public ColumnDefinition getColumnDefinitionFromCache() {
      return this.columnDefinitionFromCache;
   }

   public ColumnDefinition createFromPacketPayload(PacketPayload packetPayload) {
      return null;
   }
}
