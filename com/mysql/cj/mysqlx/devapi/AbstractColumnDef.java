package com.mysql.cj.mysqlx.devapi;

import com.mysql.cj.api.x.ColumnDefinition;
import com.mysql.cj.api.x.Type;
import com.mysql.cj.core.Messages;
import com.mysql.cj.core.exceptions.ExceptionFactory;
import com.mysql.cj.core.exceptions.WrongArgumentException;
import com.mysql.cj.core.util.StringUtils;
import java.util.Arrays;
import java.util.stream.Collectors;

public abstract class AbstractColumnDef<T extends ColumnDefinition<T>> implements ColumnDefinition<T> {
   protected String name;
   protected Type type;
   protected Number length;
   protected Boolean notNull;
   protected boolean uniqueIndex = false;
   protected boolean primaryKey = false;
   protected String comment = null;
   protected Boolean unsigned;
   protected Number decimals;
   protected String charset = null;
   protected String collation = null;
   protected Boolean binary;
   protected String[] values;

   abstract T self();

   public T notNull() {
      this.notNull = true;
      return this.self();
   }

   public T uniqueIndex() {
      this.uniqueIndex = true;
      return this.self();
   }

   public T primaryKey() {
      this.primaryKey = true;
      return this.self();
   }

   public T comment(String cmt) {
      this.comment = cmt;
      return this.self();
   }

   public T unsigned() {
      this.unsigned = true;
      return this.self();
   }

   public T decimals(int val) {
      this.decimals = val;
      return this.self();
   }

   public T charset(String charsetName) {
      this.charset = charsetName;
      return this.self();
   }

   public T collation(String collationName) {
      this.collation = collationName;
      return this.self();
   }

   public T binary() {
      this.binary = true;
      return this.self();
   }

   public T values(String... val) {
      this.values = val;
      return this.self();
   }

   protected String getMysqlType() {
      StringBuilder sb = new StringBuilder();
      String mysqlTypeName;
      switch(this.type) {
      case STRING:
         mysqlTypeName = "VARCHAR";
         break;
      case BYTES:
         mysqlTypeName = "VARBINARY";
         break;
      default:
         mysqlTypeName = this.type.name();
      }

      sb.append(mysqlTypeName);
      if (this.length != null) {
         switch(this.type) {
         case JSON:
         case GEOMETRY:
            throw (WrongArgumentException)ExceptionFactory.createException(WrongArgumentException.class, Messages.getString("ColumnDefinition.0", new String[]{"Length parameter", mysqlTypeName, this.name}));
         default:
            sb.append("(").append(this.length);
            if (this.decimals != null) {
               switch(this.type) {
               case DECIMAL:
               case DOUBLE:
               case FLOAT:
                  sb.append(", ").append(this.decimals);
                  break;
               default:
                  throw (WrongArgumentException)ExceptionFactory.createException(WrongArgumentException.class, Messages.getString("ColumnDefinition.0", new String[]{"Decimals parameter", mysqlTypeName, this.name}));
               }
            }

            sb.append(")");
         }
      } else {
         switch(this.type) {
         case ENUM:
         case SET:
            sb.append((String)Arrays.stream(this.values).map((v) -> {
               return StringUtils.quoteIdentifier(v, "'", true);
            }).collect(Collectors.joining(",", "(", ")")));
            break;
         default:
            if (this.values != null) {
               throw (WrongArgumentException)ExceptionFactory.createException(WrongArgumentException.class, Messages.getString("ColumnDefinition.0", new String[]{(String)Arrays.stream(this.values).collect(Collectors.joining(", ")), mysqlTypeName, this.name}));
            }

            if (this.decimals != null) {
               throw (WrongArgumentException)ExceptionFactory.createException(WrongArgumentException.class, Messages.getString("ColumnDefinition.1", new String[]{this.name}));
            }
         }
      }

      if (this.unsigned != null && this.unsigned) {
         switch(this.type) {
         case DECIMAL:
         case DOUBLE:
         case FLOAT:
         case TINYINT:
         case SMALLINT:
         case MEDIUMINT:
         case INT:
         case BIGINT:
            sb.append(" UNSIGNED");
            break;
         case JSON:
         case GEOMETRY:
         case ENUM:
         case SET:
         default:
            throw (WrongArgumentException)ExceptionFactory.createException(WrongArgumentException.class, Messages.getString("ColumnDefinition.0", new String[]{"UNSIGNED", mysqlTypeName, this.name}));
         }
      }

      if (this.binary != null && this.binary) {
         if (this.type != Type.STRING) {
            throw (WrongArgumentException)ExceptionFactory.createException(WrongArgumentException.class, Messages.getString("ColumnDefinition.0", new String[]{"BINARY", mysqlTypeName, this.name}));
         }

         sb.append(" BINARY");
      }

      if (this.charset != null && !this.charset.isEmpty()) {
         switch(this.type) {
         case STRING:
         case ENUM:
         case SET:
            sb.append(" CHARACTER SET ").append(this.charset);
            break;
         default:
            throw (WrongArgumentException)ExceptionFactory.createException(WrongArgumentException.class, Messages.getString("ColumnDefinition.0", new String[]{"CHARACTER SET", mysqlTypeName, this.name}));
         }
      }

      if (this.collation != null && !this.collation.isEmpty()) {
         switch(this.type) {
         case STRING:
         case ENUM:
         case SET:
            sb.append(" COLLATE ").append(this.collation);
            break;
         default:
            throw (WrongArgumentException)ExceptionFactory.createException(WrongArgumentException.class, Messages.getString("ColumnDefinition.0", new String[]{"COLLATE", mysqlTypeName, this.name}));
         }
      }

      return sb.toString();
   }
}
