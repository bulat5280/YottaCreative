package com.mysql.cj.jdbc.util;

import com.mysql.cj.jdbc.exceptions.SQLError;

public class ErrorMappingsDocGenerator {
   public static void main(String[] args) throws Exception {
      SQLError.dumpSqlStatesMappingsAsXml();
   }
}
