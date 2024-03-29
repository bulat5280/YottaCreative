package com.mysql.cj.core;

import com.mysql.cj.core.exceptions.FeatureNotAvailableException;
import com.mysql.cj.core.util.StringUtils;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.sql.Date;
import java.sql.SQLType;
import java.sql.Time;
import java.sql.Timestamp;

public enum MysqlType implements SQLType {
   DECIMAL("DECIMAL", 3, BigDecimal.class, 64, true, 65L, "[(M[,D])] [UNSIGNED] [ZEROFILL]"),
   DECIMAL_UNSIGNED("DECIMAL UNSIGNED", 3, BigDecimal.class, 96, true, 65L, "[(M[,D])] [UNSIGNED] [ZEROFILL]"),
   TINYINT("TINYINT", -6, Integer.class, 64, true, 3L, "[(M)] [UNSIGNED] [ZEROFILL]"),
   TINYINT_UNSIGNED("TINYINT UNSIGNED", -6, Integer.class, 96, true, 3L, "[(M)] [UNSIGNED] [ZEROFILL]"),
   BOOLEAN("BOOLEAN", 16, Boolean.class, 0, false, 3L, ""),
   SMALLINT("SMALLINT", 5, Integer.class, 64, true, 5L, "[(M)] [UNSIGNED] [ZEROFILL]"),
   SMALLINT_UNSIGNED("SMALLINT UNSIGNED", 5, Integer.class, 96, true, 5L, "[(M)] [UNSIGNED] [ZEROFILL]"),
   INT("INT", 4, Integer.class, 64, true, 10L, "[(M)] [UNSIGNED] [ZEROFILL]"),
   INT_UNSIGNED("INT UNSIGNED", 4, Long.class, 96, true, 10L, "[(M)] [UNSIGNED] [ZEROFILL]"),
   FLOAT("FLOAT", 7, Float.class, 64, true, 12L, "[(M,D)] [UNSIGNED] [ZEROFILL]"),
   FLOAT_UNSIGNED("FLOAT UNSIGNED", 7, Float.class, 96, true, 12L, "[(M,D)] [UNSIGNED] [ZEROFILL]"),
   DOUBLE("DOUBLE", 8, Double.class, 64, true, 22L, "[(M,D)] [UNSIGNED] [ZEROFILL]"),
   DOUBLE_UNSIGNED("DOUBLE UNSIGNED", 8, Double.class, 96, true, 22L, "[(M,D)] [UNSIGNED] [ZEROFILL]"),
   NULL("NULL", 0, Object.class, 0, false, 0L, ""),
   TIMESTAMP("TIMESTAMP", 93, Timestamp.class, 0, false, 26L, "[(fsp)]"),
   BIGINT("BIGINT", -5, Long.class, 64, true, 19L, "[(M)] [UNSIGNED] [ZEROFILL]"),
   BIGINT_UNSIGNED("BIGINT UNSIGNED", -5, BigInteger.class, 96, true, 20L, "[(M)] [UNSIGNED] [ZEROFILL]"),
   MEDIUMINT("MEDIUMINT", 4, Integer.class, 64, true, 7L, "[(M)] [UNSIGNED] [ZEROFILL]"),
   MEDIUMINT_UNSIGNED("MEDIUMINT UNSIGNED", 4, Integer.class, 96, true, 8L, "[(M)] [UNSIGNED] [ZEROFILL]"),
   DATE("DATE", 91, Date.class, 0, false, 10L, ""),
   TIME("TIME", 92, Time.class, 0, false, 16L, "[(fsp)]"),
   DATETIME("DATETIME", 93, Timestamp.class, 0, false, 26L, "[(fsp)]"),
   YEAR("YEAR", 91, Date.class, 0, false, 4L, "[(4)]"),
   VARCHAR("VARCHAR", 12, String.class, 0, false, 65535L, "(M) [CHARACTER SET charset_name] [COLLATE collation_name]"),
   VARBINARY("VARBINARY", -3, (Class)null, 0, false, 65535L, "(M)"),
   BIT("BIT", -7, Boolean.class, 0, true, 1L, "[(M)]"),
   JSON("JSON", -1, String.class, 0, false, 1073741824L, ""),
   ENUM("ENUM", 1, String.class, 0, false, 65535L, "('value1','value2',...) [CHARACTER SET charset_name] [COLLATE collation_name]"),
   SET("SET", 1, String.class, 0, false, 64L, "('value1','value2',...) [CHARACTER SET charset_name] [COLLATE collation_name]"),
   TINYBLOB("TINYBLOB", -3, (Class)null, 0, false, 255L, ""),
   TINYTEXT("TINYTEXT", 12, String.class, 0, false, 255L, " [CHARACTER SET charset_name] [COLLATE collation_name]"),
   MEDIUMBLOB("MEDIUMBLOB", -4, (Class)null, 0, false, 16777215L, ""),
   MEDIUMTEXT("MEDIUMTEXT", -1, String.class, 0, false, 16777215L, " [CHARACTER SET charset_name] [COLLATE collation_name]"),
   LONGBLOB("LONGBLOB", -4, (Class)null, 0, false, 4294967295L, ""),
   LONGTEXT("LONGTEXT", -1, String.class, 0, false, 4294967295L, " [CHARACTER SET charset_name] [COLLATE collation_name]"),
   BLOB("BLOB", -4, (Class)null, 0, false, 65535L, "[(M)]"),
   TEXT("TEXT", -1, String.class, 0, false, 65535L, "[(M)] [CHARACTER SET charset_name] [COLLATE collation_name]"),
   CHAR("CHAR", 1, String.class, 0, false, 255L, "[(M)] [CHARACTER SET charset_name] [COLLATE collation_name]"),
   BINARY("BINARY", -2, (Class)null, 0, false, 255L, "(M)"),
   GEOMETRY("GEOMETRY", -2, (Class)null, 0, false, 65535L, ""),
   UNKNOWN("UNKNOWN", 1111, (Class)null, 0, false, 65535L, "");

   private final String name;
   protected int jdbcType;
   protected final Class<?> javaClass;
   private final int flagsMask;
   private final boolean isDecimal;
   private final Long precision;
   private final String createParams;
   public static final int FIELD_FLAG_NOT_NULL = 1;
   public static final int FIELD_FLAG_PRIMARY_KEY = 2;
   public static final int FIELD_FLAG_UNIQUE_KEY = 4;
   public static final int FIELD_FLAG_MULTIPLE_KEY = 8;
   public static final int FIELD_FLAG_BLOB = 16;
   public static final int FIELD_FLAG_UNSIGNED = 32;
   public static final int FIELD_FLAG_ZEROFILL = 64;
   public static final int FIELD_FLAG_BINARY = 128;
   public static final int FIELD_FLAG_AUTO_INCREMENT = 512;
   private static final boolean IS_DECIMAL = true;
   private static final boolean IS_NOT_DECIMAL = false;

   public static MysqlType getByName(String fullMysqlTypeName) {
      String typeName = "";
      if (fullMysqlTypeName.indexOf("(") != -1) {
         typeName = fullMysqlTypeName.substring(0, fullMysqlTypeName.indexOf("(")).trim();
      } else {
         typeName = fullMysqlTypeName;
      }

      if (StringUtils.indexOfIgnoreCase(typeName, "DECIMAL") == -1 && StringUtils.indexOfIgnoreCase(typeName, "DEC") == -1 && StringUtils.indexOfIgnoreCase(typeName, "NUMERIC") == -1 && StringUtils.indexOfIgnoreCase(typeName, "FIXED") == -1) {
         if (StringUtils.indexOfIgnoreCase(typeName, "TINYBLOB") != -1) {
            return TINYBLOB;
         } else if (StringUtils.indexOfIgnoreCase(typeName, "TINYTEXT") != -1) {
            return TINYTEXT;
         } else if (StringUtils.indexOfIgnoreCase(typeName, "TINYINT") == -1 && StringUtils.indexOfIgnoreCase(typeName, "TINY") == -1 && StringUtils.indexOfIgnoreCase(typeName, "INT1") == -1) {
            if (StringUtils.indexOfIgnoreCase(typeName, "MEDIUMINT") == -1 && StringUtils.indexOfIgnoreCase(typeName, "INT24") == -1 && StringUtils.indexOfIgnoreCase(typeName, "INT3") == -1 && StringUtils.indexOfIgnoreCase(typeName, "MIDDLEINT") == -1) {
               if (StringUtils.indexOfIgnoreCase(typeName, "SMALLINT") == -1 && StringUtils.indexOfIgnoreCase(typeName, "INT2") == -1) {
                  if (StringUtils.indexOfIgnoreCase(typeName, "BIGINT") == -1 && StringUtils.indexOfIgnoreCase(typeName, "SERIAL") == -1 && StringUtils.indexOfIgnoreCase(typeName, "INT8") == -1) {
                     if (StringUtils.indexOfIgnoreCase(typeName, "POINT") != -1) {
                        return GEOMETRY;
                     } else if (StringUtils.indexOfIgnoreCase(typeName, "INT") == -1 && StringUtils.indexOfIgnoreCase(typeName, "INTEGER") == -1 && StringUtils.indexOfIgnoreCase(typeName, "INT4") == -1) {
                        if (StringUtils.indexOfIgnoreCase(typeName, "DOUBLE") == -1 && StringUtils.indexOfIgnoreCase(typeName, "REAL") == -1 && StringUtils.indexOfIgnoreCase(typeName, "FLOAT8") == -1) {
                           if (StringUtils.indexOfIgnoreCase(typeName, "FLOAT") != -1) {
                              return StringUtils.indexOfIgnoreCase(fullMysqlTypeName, "UNSIGNED") != -1 ? FLOAT_UNSIGNED : FLOAT;
                           } else if (StringUtils.indexOfIgnoreCase(typeName, "NULL") != -1) {
                              return NULL;
                           } else if (StringUtils.indexOfIgnoreCase(typeName, "TIMESTAMP") != -1) {
                              return TIMESTAMP;
                           } else if (StringUtils.indexOfIgnoreCase(typeName, "DATETIME") != -1) {
                              return DATETIME;
                           } else if (StringUtils.indexOfIgnoreCase(typeName, "DATE") != -1) {
                              return DATE;
                           } else if (StringUtils.indexOfIgnoreCase(typeName, "TIME") != -1) {
                              return TIME;
                           } else if (StringUtils.indexOfIgnoreCase(typeName, "YEAR") != -1) {
                              return YEAR;
                           } else if (StringUtils.indexOfIgnoreCase(typeName, "LONGBLOB") != -1) {
                              return LONGBLOB;
                           } else if (StringUtils.indexOfIgnoreCase(typeName, "LONGTEXT") != -1) {
                              return LONGTEXT;
                           } else if (StringUtils.indexOfIgnoreCase(typeName, "MEDIUMBLOB") == -1 && StringUtils.indexOfIgnoreCase(typeName, "LONG VARBINARY") == -1) {
                              if (StringUtils.indexOfIgnoreCase(typeName, "MEDIUMTEXT") == -1 && StringUtils.indexOfIgnoreCase(typeName, "LONG VARCHAR") == -1 && StringUtils.indexOfIgnoreCase(typeName, "LONG") == -1) {
                                 if (StringUtils.indexOfIgnoreCase(typeName, "VARCHAR") == -1 && StringUtils.indexOfIgnoreCase(typeName, "NVARCHAR") == -1 && StringUtils.indexOfIgnoreCase(typeName, "NATIONAL VARCHAR") == -1 && StringUtils.indexOfIgnoreCase(typeName, "CHARACTER VARYING") == -1) {
                                    if (StringUtils.indexOfIgnoreCase(typeName, "VARBINARY") != -1) {
                                       return VARBINARY;
                                    } else if (StringUtils.indexOfIgnoreCase(typeName, "BINARY") == -1 && StringUtils.indexOfIgnoreCase(typeName, "CHAR BYTE") == -1) {
                                       if (StringUtils.indexOfIgnoreCase(typeName, "LINESTRING") != -1) {
                                          return GEOMETRY;
                                       } else if (StringUtils.indexOfIgnoreCase(typeName, "STRING") == -1 && StringUtils.indexOfIgnoreCase(typeName, "CHAR") == -1 && StringUtils.indexOfIgnoreCase(typeName, "NCHAR") == -1 && StringUtils.indexOfIgnoreCase(typeName, "NATIONAL CHAR") == -1 && StringUtils.indexOfIgnoreCase(typeName, "CHARACTER") == -1) {
                                          if (StringUtils.indexOfIgnoreCase(typeName, "BOOLEAN") == -1 && StringUtils.indexOfIgnoreCase(typeName, "BOOL") == -1) {
                                             if (StringUtils.indexOfIgnoreCase(typeName, "BIT") != -1) {
                                                return BIT;
                                             } else if (StringUtils.indexOfIgnoreCase(typeName, "JSON") != -1) {
                                                return JSON;
                                             } else if (StringUtils.indexOfIgnoreCase(typeName, "ENUM") != -1) {
                                                return ENUM;
                                             } else if (StringUtils.indexOfIgnoreCase(typeName, "SET") != -1) {
                                                return SET;
                                             } else if (StringUtils.indexOfIgnoreCase(typeName, "BLOB") != -1) {
                                                return BLOB;
                                             } else if (StringUtils.indexOfIgnoreCase(typeName, "TEXT") != -1) {
                                                return TEXT;
                                             } else {
                                                return StringUtils.indexOfIgnoreCase(typeName, "GEOMETRY") == -1 && StringUtils.indexOfIgnoreCase(typeName, "POINT") == -1 && StringUtils.indexOfIgnoreCase(typeName, "POLYGON") == -1 ? UNKNOWN : GEOMETRY;
                                             }
                                          } else {
                                             return BOOLEAN;
                                          }
                                       } else {
                                          return CHAR;
                                       }
                                    } else {
                                       return BINARY;
                                    }
                                 } else {
                                    return VARCHAR;
                                 }
                              } else {
                                 return MEDIUMTEXT;
                              }
                           } else {
                              return MEDIUMBLOB;
                           }
                        } else {
                           return StringUtils.indexOfIgnoreCase(fullMysqlTypeName, "UNSIGNED") != -1 ? DOUBLE_UNSIGNED : DOUBLE;
                        }
                     } else {
                        return StringUtils.indexOfIgnoreCase(fullMysqlTypeName, "UNSIGNED") != -1 ? INT_UNSIGNED : INT;
                     }
                  } else {
                     return StringUtils.indexOfIgnoreCase(fullMysqlTypeName, "UNSIGNED") != -1 ? BIGINT_UNSIGNED : BIGINT;
                  }
               } else {
                  return StringUtils.indexOfIgnoreCase(fullMysqlTypeName, "UNSIGNED") != -1 ? SMALLINT_UNSIGNED : SMALLINT;
               }
            } else {
               return StringUtils.indexOfIgnoreCase(fullMysqlTypeName, "UNSIGNED") != -1 ? MEDIUMINT_UNSIGNED : MEDIUMINT;
            }
         } else {
            return StringUtils.indexOfIgnoreCase(fullMysqlTypeName, "UNSIGNED") != -1 ? TINYINT_UNSIGNED : TINYINT;
         }
      } else {
         return StringUtils.indexOfIgnoreCase(fullMysqlTypeName, "UNSIGNED") != -1 ? DECIMAL_UNSIGNED : DECIMAL;
      }
   }

   public static MysqlType getByJdbcType(int jdbcType) {
      switch(jdbcType) {
      case -16:
      case -1:
      case 2005:
      case 2011:
         return TEXT;
      case -15:
      case 1:
         return CHAR;
      case -9:
      case 12:
      case 70:
      case 2009:
         return VARCHAR;
      case -8:
      case 1111:
      case 2001:
      case 2002:
      case 2003:
      case 2006:
      default:
         return UNKNOWN;
      case -7:
         return BIT;
      case -6:
         return TINYINT;
      case -5:
         return BIGINT;
      case -4:
      case 2000:
      case 2004:
         return BLOB;
      case -3:
         return VARBINARY;
      case -2:
         return BINARY;
      case 0:
         return NULL;
      case 2:
      case 3:
         return DECIMAL;
      case 4:
         return INT;
      case 5:
         return SMALLINT;
      case 6:
      case 8:
         return DOUBLE;
      case 7:
         return FLOAT;
      case 16:
         return BOOLEAN;
      case 91:
         return DATE;
      case 92:
         return TIME;
      case 93:
         return TIMESTAMP;
      case 2012:
      case 2013:
      case 2014:
         throw new FeatureNotAvailableException("Document IDs are not assigned for SQL statements");
      }
   }

   public static boolean supportsConvert(int fromType, int toType) {
      switch(fromType) {
      case -7:
         return false;
      case -6:
      case -5:
      case 2:
      case 3:
      case 4:
      case 5:
      case 6:
      case 7:
      case 8:
         switch(toType) {
         case -6:
         case -5:
         case -4:
         case -3:
         case -2:
         case -1:
         case 1:
         case 2:
         case 3:
         case 4:
         case 5:
         case 6:
         case 7:
         case 8:
         case 12:
            return true;
         case 0:
         case 9:
         case 10:
         case 11:
         default:
            return false;
         }
      case -4:
      case -3:
      case -2:
      case -1:
      case 1:
      case 12:
         switch(toType) {
         case -6:
         case -5:
         case -4:
         case -3:
         case -2:
         case -1:
         case 1:
         case 2:
         case 3:
         case 4:
         case 5:
         case 6:
         case 7:
         case 8:
         case 12:
         case 91:
         case 92:
         case 93:
         case 1111:
            return true;
         default:
            return false;
         }
      case 0:
         return false;
      case 91:
         switch(toType) {
         case -4:
         case -3:
         case -2:
         case -1:
         case 1:
         case 12:
            return true;
         case 0:
         case 2:
         case 3:
         case 4:
         case 5:
         case 6:
         case 7:
         case 8:
         case 9:
         case 10:
         case 11:
         default:
            return false;
         }
      case 92:
         switch(toType) {
         case -4:
         case -3:
         case -2:
         case -1:
         case 1:
         case 12:
            return true;
         case 0:
         case 2:
         case 3:
         case 4:
         case 5:
         case 6:
         case 7:
         case 8:
         case 9:
         case 10:
         case 11:
         default:
            return false;
         }
      case 93:
         switch(toType) {
         case -4:
         case -3:
         case -2:
         case -1:
         case 1:
         case 12:
         case 91:
         case 92:
            return true;
         default:
            return false;
         }
      case 1111:
         switch(toType) {
         case -4:
         case -3:
         case -2:
         case -1:
         case 1:
         case 12:
            return true;
         case 0:
         case 2:
         case 3:
         case 4:
         case 5:
         case 6:
         case 7:
         case 8:
         case 9:
         case 10:
         case 11:
         default:
            return false;
         }
      default:
         return false;
      }
   }

   private MysqlType(String mysqlTypeName, int jdbcType, Class<?> javaClass, int allowedFlags, boolean isDec, Long precision, String createParams) {
      this.name = mysqlTypeName;
      this.jdbcType = jdbcType;
      this.javaClass = javaClass;
      this.flagsMask = allowedFlags;
      this.isDecimal = isDec;
      this.precision = precision;
      this.createParams = createParams;
   }

   public String getName() {
      return this.name;
   }

   public int getJdbcType() {
      return this.jdbcType;
   }

   public boolean isAllowed(int flag) {
      return (this.flagsMask & flag) > 0;
   }

   public String getClassName() {
      return this.javaClass == null ? "[B" : this.javaClass.getName();
   }

   public boolean isDecimal() {
      return this.isDecimal;
   }

   public Long getPrecision() {
      return this.precision;
   }

   public String getCreateParams() {
      return this.createParams;
   }

   public String getVendor() {
      return "com.mysql.cj";
   }

   public Integer getVendorTypeNumber() {
      return this.jdbcType;
   }
}
