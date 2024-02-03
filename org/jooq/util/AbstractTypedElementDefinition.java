package org.jooq.util;

import java.io.StringWriter;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import javax.xml.bind.JAXB;
import org.jooq.DataType;
import org.jooq.Name;
import org.jooq.exception.SQLDialectNotSupportedException;
import org.jooq.impl.DateAsTimestampBinding;
import org.jooq.impl.DefaultDataType;
import org.jooq.impl.SQLDataType;
import org.jooq.tools.Convert;
import org.jooq.tools.JooqLogger;
import org.jooq.tools.StringUtils;
import org.jooq.util.jaxb.CustomType;
import org.jooq.util.jaxb.ForcedType;

abstract class AbstractTypedElementDefinition<T extends Definition> extends AbstractDefinition implements TypedElementDefinition<T> {
   private static final JooqLogger log = JooqLogger.getLogger(AbstractTypedElementDefinition.class);
   private static final Pattern LENGTH_PRECISION_SCALE_PATTERN = Pattern.compile("[\\w\\s]+(?:\\(\\s*?(\\d+)\\s*?\\)|\\(\\s*?(\\d+)\\s*?,\\s*?(\\d+)\\s*?\\))");
   private final T container;
   private final DataTypeDefinition definedType;
   private transient DataTypeDefinition type;

   public AbstractTypedElementDefinition(T container, String name, int position, DataTypeDefinition definedType, String comment) {
      super(container.getDatabase(), container.getSchema(), protectName(container.getName(), name, position), comment);
      this.container = container;
      this.definedType = definedType;
   }

   private static String protectName(String table, String name, int position) {
      if (name == null) {
         log.info("Missing name", (Object)("Object " + table + " holds a column without a name at position " + position));
         return "_" + position;
      } else {
         return name;
      }
   }

   public final T getContainer() {
      return this.container;
   }

   public List<Definition> getDefinitionPath() {
      List<Definition> result = new ArrayList();
      result.addAll(this.getContainer().getDefinitionPath());
      result.add(this);
      return result;
   }

   public DataTypeDefinition getType() {
      if (this.type == null) {
         this.type = mapDefinedType(this.container, this, this.definedType);
      }

      return this.type;
   }

   static DataTypeDefinition mapDefinedType(Definition container, Definition child, DataTypeDefinition definedType) {
      DataTypeDefinition result = definedType;
      Database db = container.getDatabase();
      log.debug("Type mapping", (Object)(child + " with type " + definedType.getType()));
      if (db.dateAsTimestamp()) {
         DataType dataType = null;

         try {
            dataType = DefaultDataType.getDataType(db.getDialect(), ((DataTypeDefinition)result).getType(), 0, 0);
         } catch (SQLDialectNotSupportedException var20) {
         }

         if (dataType != null && dataType.getSQLType() == 91) {
            DataType<?> forcedDataType = DefaultDataType.getDataType(db.getDialect(), SQLDataType.TIMESTAMP.getTypeName(), 0, 0);
            result = new DefaultDataTypeDefinition(db, child.getSchema(), forcedDataType.getTypeName(), 0, 0, 0, definedType.isNullable(), definedType.getDefaultValue(), (Name)null, (String)null, DateAsTimestampBinding.class.getName());
         }
      }

      ForcedType forcedType = db.getConfiguredForcedType(child, definedType);
      if (forcedType != null) {
         String type = forcedType.getName();
         String converter = null;
         String binding = ((DataTypeDefinition)result).getBinding();
         CustomType customType = customType(db, forcedType);
         if (customType != null) {
            type = !StringUtils.isBlank(customType.getType()) ? customType.getType() : customType.getName();
            if (!StringUtils.isBlank(customType.getConverter())) {
               converter = customType.getConverter();
            }

            if (!StringUtils.isBlank(customType.getBinding())) {
               binding = customType.getBinding();
            }
         }

         if (type != null) {
            log.info("Forcing type", (Object)(child + " with type " + definedType.getType() + " into " + type + (converter != null ? " using converter " + converter : "") + (binding != null ? " using binding " + binding : "")));
            DataType<?> forcedDataType = null;
            boolean n = ((DataTypeDefinition)result).isNullable();
            String d = ((DataTypeDefinition)result).getDefaultValue();
            int l = 0;
            int p = 0;
            int s = 0;
            Matcher matcher = LENGTH_PRECISION_SCALE_PATTERN.matcher(type);
            if (matcher.find()) {
               if (!StringUtils.isEmpty(matcher.group(1))) {
                  l = p = (Integer)Convert.convert((Object)matcher.group(1), (Class)Integer.TYPE);
               } else {
                  p = (Integer)Convert.convert((Object)matcher.group(2), (Class)Integer.TYPE);
                  s = (Integer)Convert.convert((Object)matcher.group(3), (Class)Integer.TYPE);
               }
            }

            try {
               forcedDataType = DefaultDataType.getDataType(db.getDialect(), type, p, s);
            } catch (SQLDialectNotSupportedException var19) {
            }

            if (forcedDataType != null) {
               result = new DefaultDataTypeDefinition(db, child.getSchema(), type, l, p, s, n, d, (Name)null, converter, binding);
            } else if (customType != null) {
               l = ((DataTypeDefinition)result).getLength();
               p = ((DataTypeDefinition)result).getPrecision();
               s = ((DataTypeDefinition)result).getScale();
               String t = ((DataTypeDefinition)result).getType();
               Name u = ((DataTypeDefinition)result).getQualifiedUserType();
               result = new DefaultDataTypeDefinition(db, child.getSchema(), t, l, p, s, n, d, u, converter, binding, type);
            } else {
               StringWriter writer = new StringWriter();
               JAXB.marshal(forcedType, writer);
               log.warn("Bad configuration for <forcedType/> " + forcedType.getName() + ". No matching <customType/> found, and no matching SQLDataType found: " + writer);
            }
         }
      }

      return (DataTypeDefinition)result;
   }

   static CustomType customType(Database db, ForcedType forcedType) {
      String name = forcedType.getName();
      if (!StringUtils.isBlank(forcedType.getUserType())) {
         return (new CustomType()).withBinding(forcedType.getBinding()).withConverter(forcedType.getConverter()).withName(name).withType(forcedType.getUserType());
      } else {
         if (name != null) {
            Iterator var3 = db.getConfiguredCustomTypes().iterator();

            while(var3.hasNext()) {
               CustomType type = (CustomType)var3.next();
               if (name.equals(type.getName())) {
                  return type;
               }
            }
         }

         return null;
      }
   }
}
