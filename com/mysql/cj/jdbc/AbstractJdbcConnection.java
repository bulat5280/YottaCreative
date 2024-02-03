package com.mysql.cj.jdbc;

import com.mysql.cj.api.jdbc.JdbcConnection;
import com.mysql.cj.api.jdbc.JdbcPropertySet;
import java.io.Serializable;

public abstract class AbstractJdbcConnection implements Serializable, JdbcConnection {
   private static final long serialVersionUID = 8869245000140781024L;
   protected JdbcPropertySet propertySet = new JdbcPropertySetImpl();

   public JdbcPropertySet getPropertySet() {
      return this.propertySet;
   }
}
