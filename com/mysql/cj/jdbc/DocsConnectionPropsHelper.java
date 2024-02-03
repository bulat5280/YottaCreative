package com.mysql.cj.jdbc;

import com.mysql.cj.core.conf.PropertyDefinitions;

public class DocsConnectionPropsHelper {
   public static void main(String[] args) throws Exception {
      System.out.println(PropertyDefinitions.exposeAsXml());
   }
}
