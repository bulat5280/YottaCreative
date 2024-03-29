package com.mysql.cj.jdbc.admin;

import com.mysql.cj.api.exceptions.ExceptionInterceptor;
import com.mysql.cj.jdbc.util.TimeUtil;
import java.sql.DriverManager;
import java.sql.ResultSet;

public class TimezoneDump {
   private static final String DEFAULT_URL = "jdbc:mysql:///test";

   public static void main(String[] args) throws Exception {
      String jdbcUrl = "jdbc:mysql:///test";
      if (args.length == 1 && args[0] != null) {
         jdbcUrl = args[0];
      }

      Class.forName("com.mysql.cj.jdbc.Driver").newInstance();
      ResultSet rs = null;

      try {
         rs = DriverManager.getConnection(jdbcUrl).createStatement().executeQuery("SHOW VARIABLES LIKE 'timezone'");

         while(rs.next()) {
            String timezoneFromServer = rs.getString(2);
            System.out.println("MySQL timezone name: " + timezoneFromServer);
            String canonicalTimezone = TimeUtil.getCanonicalTimezone(timezoneFromServer, (ExceptionInterceptor)null);
            System.out.println("Java timezone name: " + canonicalTimezone);
         }
      } finally {
         if (rs != null) {
            rs.close();
         }

      }

   }
}
