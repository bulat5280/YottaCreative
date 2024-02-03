package com.p6spy.engine.spy;

import java.util.Hashtable;
import javax.naming.Context;
import javax.naming.Name;
import javax.naming.Reference;
import javax.naming.spi.ObjectFactory;

public class P6DataSourceFactory implements ObjectFactory {
   protected static final String DATASOURCE_CLASS_NAME = P6DataSource.class.getName();

   public Object getObjectInstance(Object refObj, Name nm, Context ctx, Hashtable env) throws Exception {
      Reference ref = (Reference)refObj;
      String className = ref.getClassName();
      if (className != null && className.equals(DATASOURCE_CLASS_NAME)) {
         P6DataSource dataSource;
         try {
            dataSource = (P6DataSource)Class.forName(className).newInstance();
         } catch (Exception var9) {
            throw new RuntimeException("Unable to create DataSource of class '" + className + "': " + var9.toString());
         }

         dataSource.setRealDataSource((String)ref.get("dataSourceName").getContent());
         return dataSource;
      } else {
         return null;
      }
   }
}
