package com.mysql.cj.mysqlx.devapi;

import com.mysql.cj.api.x.Column;
import com.mysql.cj.api.x.Type;
import com.mysql.cj.core.CharsetMapping;
import com.mysql.cj.core.MysqlType;
import com.mysql.cj.core.result.Field;

public class ColumnImpl implements Column {
   private Field field;

   public ColumnImpl(Field f) {
      this.field = f;
   }

   public String getSchemaName() {
      return this.field.getDatabaseName();
   }

   public String getTableName() {
      return this.field.getOriginalTableName();
   }

   public String getTableLabel() {
      return this.field.getTableName();
   }

   public String getColumnName() {
      return this.field.getOriginalName();
   }

   public String getColumnLabel() {
      return this.field.getName();
   }

   public Type getType() {
      switch(this.field.getMysqlType()) {
      case BIT:
         return Type.BIT;
      case BIGINT:
         switch((int)this.field.getLength()) {
         case 4:
            return Type.TINYINT;
         case 6:
            return Type.SMALLINT;
         case 9:
            return Type.MEDIUMINT;
         case 11:
            return Type.INT;
         case 20:
            return Type.BIGINT;
         default:
            throw new IllegalArgumentException("Unknown field length `" + this.field.getLength() + "` for signed int");
         }
      case BIGINT_UNSIGNED:
         switch((int)this.field.getLength()) {
         case 3:
            return Type.TINYINT;
         case 5:
            return Type.SMALLINT;
         case 8:
            return Type.MEDIUMINT;
         case 10:
            return Type.INT;
         case 20:
            return Type.BIGINT;
         default:
            throw new IllegalArgumentException("Unknown field length `" + this.field.getLength() + "` for unsigned int");
         }
      case FLOAT:
      case FLOAT_UNSIGNED:
         return Type.FLOAT;
      case DECIMAL:
      case DECIMAL_UNSIGNED:
         return Type.DECIMAL;
      case DOUBLE:
      case DOUBLE_UNSIGNED:
         return Type.DOUBLE;
      case CHAR:
      case VARCHAR:
         return Type.STRING;
      case JSON:
         return Type.JSON;
      case VARBINARY:
         return Type.BYTES;
      case TIME:
         return Type.TIME;
      case DATETIME:
         switch((int)this.field.getLength()) {
         case 10:
            return Type.DATE;
         case 19:
            return Type.DATETIME;
         default:
            throw new IllegalArgumentException("Unknown field length `" + this.field.getLength() + "` for datetime");
         }
      case TIMESTAMP:
         return Type.TIMESTAMP;
      case SET:
         return Type.SET;
      case ENUM:
         return Type.ENUM;
      case GEOMETRY:
         return Type.GEOMETRY;
      default:
         throw new IllegalArgumentException("Unknown type in metadata: " + this.field.getMysqlType());
      }
   }

   public long getLength() {
      return this.field.getLength();
   }

   public int getFractionalDigits() {
      return this.field.getDecimals();
   }

   public boolean isNumberSigned() {
      return !this.field.isUnsigned();
   }

   public String getCollationName() {
      return CharsetMapping.COLLATION_INDEX_TO_COLLATION_NAME[this.field.getCollationIndex()];
   }

   public String getCharacterSetName() {
      return CharsetMapping.getMysqlCharsetNameForCollationIndex(this.field.getCollationIndex());
   }

   public boolean isPadded() {
      return this.field.isZeroFill() || this.field.getMysqlType() == MysqlType.CHAR;
   }

   public boolean isNullable() {
      return !this.field.isNotNull();
   }

   public boolean isAutoIncrement() {
      return this.field.isAutoIncrement();
   }

   public boolean isPrimaryKey() {
      return this.field.isPrimaryKey();
   }

   public boolean isUniqueKey() {
      return this.field.isUniqueKey();
   }

   public boolean isPartKey() {
      return this.field.isMultipleKey();
   }
}
