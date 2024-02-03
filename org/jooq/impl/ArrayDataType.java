package org.jooq.impl;

import org.jooq.Configuration;
import org.jooq.DataType;
import org.jooq.SQLDialect;

final class ArrayDataType<T> extends DefaultDataType<T[]> {
   private static final long serialVersionUID = 7883229760246533448L;
   private final DataType<T> elementType;

   public ArrayDataType(DataType<T> elementType) {
      super((SQLDialect)null, (Class)elementType.getArrayType(), elementType.getTypeName(), elementType.getCastTypeName());
      this.elementType = elementType;
   }

   public final String getTypeName(Configuration configuration) {
      String typeName = this.elementType.getTypeName(configuration);
      return getArrayType(configuration, typeName);
   }

   public final String getCastTypeName(Configuration configuration) {
      String castTypeName = this.elementType.getCastTypeName(configuration);
      return getArrayType(configuration, castTypeName);
   }

   private static String getArrayType(Configuration configuration, String dataType) {
      switch(configuration.family()) {
      case HSQLDB:
         return dataType + " array";
      case POSTGRES:
         return dataType + "[]";
      case H2:
         return "array";
      default:
         return dataType + "[]";
      }
   }
}
