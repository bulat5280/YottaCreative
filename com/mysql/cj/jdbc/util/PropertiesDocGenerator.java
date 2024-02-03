package com.mysql.cj.jdbc.util;

import com.mysql.cj.core.conf.PropertyDefinitions;

public class PropertiesDocGenerator {
   public static void main(String[] args) {
      System.out.println(PropertyDefinitions.exposeAsXml());
   }
}
