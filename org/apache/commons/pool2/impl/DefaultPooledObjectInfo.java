package org.apache.commons.pool2.impl;

import java.io.PrintWriter;
import java.io.StringWriter;
import java.text.SimpleDateFormat;
import org.apache.commons.pool2.PooledObject;

public class DefaultPooledObjectInfo implements DefaultPooledObjectInfoMBean {
   private final PooledObject<?> pooledObject;

   public DefaultPooledObjectInfo(PooledObject<?> pooledObject) {
      this.pooledObject = pooledObject;
   }

   public long getCreateTime() {
      return this.pooledObject.getCreateTime();
   }

   public String getCreateTimeFormatted() {
      SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss Z");
      return sdf.format(this.pooledObject.getCreateTime());
   }

   public long getLastBorrowTime() {
      return this.pooledObject.getLastBorrowTime();
   }

   public String getLastBorrowTimeFormatted() {
      SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss Z");
      return sdf.format(this.pooledObject.getLastBorrowTime());
   }

   public String getLastBorrowTrace() {
      StringWriter sw = new StringWriter();
      this.pooledObject.printStackTrace(new PrintWriter(sw));
      return sw.toString();
   }

   public long getLastReturnTime() {
      return this.pooledObject.getLastReturnTime();
   }

   public String getLastReturnTimeFormatted() {
      SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss Z");
      return sdf.format(this.pooledObject.getLastReturnTime());
   }

   public String getPooledObjectType() {
      return this.pooledObject.getObject().getClass().getName();
   }

   public String getPooledObjectToString() {
      return this.pooledObject.getObject().toString();
   }

   public long getBorrowedCount() {
      return this.pooledObject instanceof DefaultPooledObject ? ((DefaultPooledObject)this.pooledObject).getBorrowedCount() : -1L;
   }

   public String toString() {
      StringBuilder builder = new StringBuilder();
      builder.append("DefaultPooledObjectInfo [pooledObject=");
      builder.append(this.pooledObject);
      builder.append("]");
      return builder.toString();
   }
}
