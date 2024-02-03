package org.jooq.util;

import org.jooq.Name;

public interface DataTypeDefinition {
   String getType();

   String getConverter();

   String getBinding();

   int getLength();

   int getPrecision();

   int getScale();

   String getUserType();

   Name getQualifiedUserType();

   String getJavaType();

   boolean isNullable();

   boolean isDefaulted();

   String getDefaultValue();

   boolean isUDT();

   boolean isArray();

   boolean isGenericNumberType();

   Database getDatabase();

   SchemaDefinition getSchema();
}
