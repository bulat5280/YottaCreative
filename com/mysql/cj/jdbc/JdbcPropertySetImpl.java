package com.mysql.cj.jdbc;

import com.mysql.cj.api.conf.ModifiableProperty;
import com.mysql.cj.api.conf.PropertyDefinition;
import com.mysql.cj.api.conf.ReadableProperty;
import com.mysql.cj.api.exceptions.ExceptionInterceptor;
import com.mysql.cj.api.jdbc.JdbcPropertySet;
import com.mysql.cj.core.conf.DefaultPropertySet;
import com.mysql.cj.core.conf.PropertyDefinitions;
import com.mysql.cj.core.exceptions.CJException;
import com.mysql.cj.core.util.StringUtils;
import com.mysql.cj.jdbc.exceptions.SQLExceptionsMapping;
import java.sql.DriverPropertyInfo;
import java.sql.SQLException;
import java.util.Iterator;
import java.util.Properties;

public class JdbcPropertySetImpl extends DefaultPropertySet implements JdbcPropertySet {
   private static final long serialVersionUID = -8223499903182568260L;

   public <T> ModifiableProperty<T> getJdbcModifiableProperty(String name) throws SQLException {
      try {
         return this.getModifiableProperty(name);
      } catch (CJException var3) {
         throw SQLExceptionsMapping.translateException(var3);
      }
   }

   public void postInitialization() {
      if ((Integer)this.getIntegerReadableProperty("maxRows").getValue() == 0) {
         super.getModifiableProperty("maxRows").setValue(-1, (ExceptionInterceptor)null);
      }

      String testEncoding = (String)this.getStringReadableProperty("characterEncoding").getValue();
      if (testEncoding != null) {
         String testString = "abc";
         StringUtils.getBytes(testString, testEncoding);
      }

      if ((Boolean)this.getBooleanReadableProperty("useCursorFetch").getValue()) {
         super.getModifiableProperty("useServerPrepStmts").setValue(true);
      }

   }

   public DriverPropertyInfo[] exposeAsDriverPropertyInfo(Properties info, int slotsToReserve) throws SQLException {
      this.initializeProperties(info);
      int numProperties = PropertyDefinitions.PROPERTY_NAME_TO_PROPERTY_DEFINITION.size();
      int listSize = numProperties + slotsToReserve;
      DriverPropertyInfo[] driverProperties = new DriverPropertyInfo[listSize];
      int i = slotsToReserve;

      String propName;
      for(Iterator var7 = PropertyDefinitions.PROPERTY_NAME_TO_PROPERTY_DEFINITION.keySet().iterator(); var7.hasNext(); driverProperties[i++] = this.getAsDriverPropertyInfo(this.getReadableProperty(propName))) {
         propName = (String)var7.next();
      }

      return driverProperties;
   }

   private DriverPropertyInfo getAsDriverPropertyInfo(ReadableProperty<?> pr) {
      PropertyDefinition<?> pdef = pr.getPropertyDefinition();
      DriverPropertyInfo dpi = new DriverPropertyInfo(pdef.getName(), (String)null);
      dpi.choices = pdef.getAllowableValues();
      dpi.value = pr.getStringValue() != null ? pr.getStringValue() : null;
      dpi.required = false;
      dpi.description = pdef.getDescription();
      return dpi;
   }
}
