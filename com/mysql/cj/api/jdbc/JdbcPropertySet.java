package com.mysql.cj.api.jdbc;

import com.mysql.cj.api.conf.ModifiableProperty;
import com.mysql.cj.api.conf.PropertySet;
import java.sql.DriverPropertyInfo;
import java.sql.SQLException;
import java.util.Properties;

public interface JdbcPropertySet extends PropertySet {
   <T> ModifiableProperty<T> getJdbcModifiableProperty(String var1) throws SQLException;

   DriverPropertyInfo[] exposeAsDriverPropertyInfo(Properties var1, int var2) throws SQLException;
}
