package org.jooq.util;

import org.jooq.Name;
import org.jooq.SQLDialect;
import org.jooq.impl.DSL;
import org.jooq.impl.DefaultDataType;

public class DefaultDataTypeDefinition implements DataTypeDefinition {
   private final Database database;
   private final SchemaDefinition schema;
   private final String type;
   private final Name userType;
   private final String javaType;
   private final String converter;
   private final String binding;
   private final boolean nullable;
   private final String defaultValue;
   private final int length;
   private final int precision;
   private final int scale;

   private static final String defaultValue(Boolean defaultable) {
      return defaultable != null && defaultable ? "NULL" : null;
   }

   public DefaultDataTypeDefinition(Database database, SchemaDefinition schema, String typeName) {
      this(database, schema, typeName, (Number)null, (Number)null, (Number)null, (Boolean)null, (String)((String)null), (Name)((Name)null));
   }

   /** @deprecated */
   @Deprecated
   public DefaultDataTypeDefinition(Database database, SchemaDefinition schema, String typeName, Number length, Number precision, Number scale, Boolean nullable, Boolean defaultable) {
      this(database, schema, typeName, length, precision, scale, nullable, (Boolean)defaultable, (String)typeName, (String)null);
   }

   public DefaultDataTypeDefinition(Database database, SchemaDefinition schema, String typeName, Number length, Number precision, Number scale, Boolean nullable, String defaultValue) {
      this(database, schema, typeName, length, precision, scale, nullable, (String)defaultValue, (String)typeName, (String)null);
   }

   /** @deprecated */
   @Deprecated
   public DefaultDataTypeDefinition(Database database, SchemaDefinition schema, String typeName, Number length, Number precision, Number scale, Boolean nullable, Boolean defaultable, String userType) {
      this(database, schema, typeName, length, precision, scale, nullable, (Boolean)defaultable, (String)userType, (String)null);
   }

   /** @deprecated */
   @Deprecated
   public DefaultDataTypeDefinition(Database database, SchemaDefinition schema, String typeName, Number length, Number precision, Number scale, Boolean nullable, String defaultValue, String userType) {
      this(database, schema, typeName, length, precision, scale, nullable, defaultValue, DSL.name(userType));
   }

   /** @deprecated */
   @Deprecated
   public DefaultDataTypeDefinition(Database database, SchemaDefinition schema, String typeName, Number length, Number precision, Number scale, Boolean nullable, Boolean defaultValue, Name userType) {
      this(database, schema, typeName, length, precision, scale, nullable, defaultValue(defaultValue), userType);
   }

   public DefaultDataTypeDefinition(Database database, SchemaDefinition schema, String typeName, Number length, Number precision, Number scale, Boolean nullable, String defaultValue, Name userType) {
      this(database, schema, typeName, length, precision, scale, nullable, (String)defaultValue, (Name)userType, (String)null);
   }

   /** @deprecated */
   @Deprecated
   public DefaultDataTypeDefinition(Database database, SchemaDefinition schema, String typeName, Number length, Number precision, Number scale, Boolean nullable, Boolean defaultable, String userType, String converter) {
      this(database, schema, typeName, length, precision, scale, nullable, (Boolean)defaultable, (String)userType, converter, (String)null);
   }

   /** @deprecated */
   @Deprecated
   public DefaultDataTypeDefinition(Database database, SchemaDefinition schema, String typeName, Number length, Number precision, Number scale, Boolean nullable, String defaultValue, String userType, String converter) {
      this(database, schema, typeName, length, precision, scale, nullable, defaultValue, DSL.name(userType), converter);
   }

   public DefaultDataTypeDefinition(Database database, SchemaDefinition schema, String typeName, Number length, Number precision, Number scale, Boolean nullable, String defaultValue, Name userType, String converter) {
      this(database, schema, typeName, length, precision, scale, nullable, (String)defaultValue, (Name)userType, converter, (String)null);
   }

   /** @deprecated */
   @Deprecated
   public DefaultDataTypeDefinition(Database database, SchemaDefinition schema, String typeName, Number length, Number precision, Number scale, Boolean nullable, Boolean defaultable, String userType, String converter, String binding) {
      this(database, schema, typeName, length, precision, scale, nullable, (Boolean)defaultable, (String)userType, converter, binding, (String)null);
   }

   /** @deprecated */
   @Deprecated
   public DefaultDataTypeDefinition(Database database, SchemaDefinition schema, String typeName, Number length, Number precision, Number scale, Boolean nullable, String defaultValue, String userType, String converter, String binding) {
      this(database, schema, typeName, length, precision, scale, nullable, (String)defaultValue, (Name)DSL.name(userType), converter, binding, (String)null);
   }

   public DefaultDataTypeDefinition(Database database, SchemaDefinition schema, String typeName, Number length, Number precision, Number scale, Boolean nullable, String defaultValue, Name userType, String converter, String binding) {
      this(database, schema, typeName, length, precision, scale, nullable, (String)defaultValue, (Name)userType, converter, binding, (String)null);
   }

   /** @deprecated */
   @Deprecated
   public DefaultDataTypeDefinition(Database database, SchemaDefinition schema, String typeName, Number length, Number precision, Number scale, Boolean nullable, Boolean defaultable, String userType, String converter, String binding, String javaType) {
      this(database, schema, typeName, length, precision, scale, nullable, defaultValue(defaultable), userType, converter, binding, javaType);
   }

   /** @deprecated */
   @Deprecated
   public DefaultDataTypeDefinition(Database database, SchemaDefinition schema, String typeName, Number length, Number precision, Number scale, Boolean nullable, String defaultValue, String userType, String converter, String binding, String javaType) {
      this(database, schema, typeName, length, precision, scale, nullable, defaultValue, DSL.name(userType), converter, binding, javaType);
   }

   public DefaultDataTypeDefinition(Database database, SchemaDefinition schema, String typeName, Number length, Number precision, Number scale, Boolean nullable, String defaultValue, Name userType, String converter, String binding, String javaType) {
      this.database = database;
      this.schema = schema;
      this.type = typeName == null ? "OTHER" : typeName;
      this.userType = userType;
      this.javaType = javaType;
      this.converter = converter;
      this.binding = binding;
      if (length != null && precision != null && length.intValue() != 0 && precision.intValue() != 0) {
         if (this.type.toLowerCase().matches(".*?(char|text|lob|xml|graphic|string).*?")) {
            precision = null;
            scale = null;
         } else {
            length = null;
         }
      }

      this.length = length == null ? 0 : length.intValue();
      this.precision = precision == null ? 0 : precision.intValue();
      this.scale = scale == null ? 0 : scale.intValue();
      this.nullable = nullable == null ? true : nullable;
      this.defaultValue = defaultValue;
   }

   public final Database getDatabase() {
      return this.database;
   }

   public final SchemaDefinition getSchema() {
      return this.schema;
   }

   private final SQLDialect getDialect() {
      return this.getDatabase().getDialect();
   }

   public final boolean isNullable() {
      return this.nullable;
   }

   public final boolean isDefaulted() {
      return this.getDefaultValue() != null;
   }

   public final String getDefaultValue() {
      return this.defaultValue;
   }

   public final boolean isUDT() {
      if (this.userType == null) {
         return false;
      } else {
         return this.getDatabase().getUDT(this.schema, this.userType) != null;
      }
   }

   public final boolean isArray() {
      if (this.userType == null) {
         return false;
      } else {
         return this.getDatabase().getArray(this.schema, this.userType) != null;
      }
   }

   public final String getType() {
      return this.type;
   }

   public final String getConverter() {
      return this.converter;
   }

   public final String getBinding() {
      return this.binding;
   }

   public final int getLength() {
      return this.length;
   }

   public final int getPrecision() {
      return this.precision;
   }

   public final int getScale() {
      return this.scale;
   }

   public final String getUserType() {
      return this.userType != null ? this.userType.last() : null;
   }

   public final Name getQualifiedUserType() {
      return this.userType;
   }

   public final String getJavaType() {
      return this.javaType;
   }

   public final boolean isGenericNumberType() {
      return false;
   }

   public int hashCode() {
      int prime = true;
      int result = 1;
      int result = 31 * result + (this.type == null ? 0 : this.type.hashCode());
      result = 31 * result + (this.userType == null ? 0 : this.userType.hashCode());
      return result;
   }

   public boolean equals(Object obj) {
      if (this == obj) {
         return true;
      } else if (obj instanceof DefaultDataTypeDefinition) {
         DefaultDataTypeDefinition other = (DefaultDataTypeDefinition)obj;
         if (!DefaultDataType.normalise(this.type).equals(DefaultDataType.normalise(other.type))) {
            return false;
         } else if (this.userType == null && other.userType == null) {
            return true;
         } else {
            return this.userType != null && other.userType != null ? DefaultDataType.normalise(this.userType.last()).equals(DefaultDataType.normalise(other.userType.last())) : false;
         }
      } else {
         return false;
      }
   }

   public final String toString() {
      StringBuilder sb = new StringBuilder();
      sb.append("DataType [ t=");
      sb.append(this.type);
      sb.append("; p=");
      sb.append(this.precision);
      sb.append("; s=");
      sb.append(this.scale);
      sb.append("; u=");
      sb.append(this.userType);
      sb.append("; j=");
      sb.append(this.javaType);
      sb.append(" ]");
      return sb.toString();
   }
}
