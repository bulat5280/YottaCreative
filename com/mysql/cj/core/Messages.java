package com.mysql.cj.core;

import java.text.MessageFormat;
import java.util.Locale;
import java.util.MissingResourceException;
import java.util.ResourceBundle;

public class Messages {
   private static final String BUNDLE_NAME = "com.mysql.cj.core.LocalizedErrorMessages";
   private static final ResourceBundle RESOURCE_BUNDLE;

   public static String getString(String key) {
      if (RESOURCE_BUNDLE == null) {
         throw new RuntimeException("Localized messages from resource bundle 'com.mysql.cj.core.LocalizedErrorMessages' not loaded during initialization of driver.");
      } else {
         try {
            if (key == null) {
               throw new IllegalArgumentException("Message key can not be null");
            } else {
               String message = RESOURCE_BUNDLE.getString(key);
               if (message == null) {
                  message = "Missing error message for key '" + key + "'";
               }

               return message;
            }
         } catch (MissingResourceException var2) {
            return '!' + key + '!';
         }
      }
   }

   public static String getString(String key, Object[] args) {
      return MessageFormat.format(getString(key), args);
   }

   private Messages() {
   }

   static {
      ResourceBundle temp = null;

      try {
         temp = ResourceBundle.getBundle("com.mysql.cj.core.LocalizedErrorMessages", Locale.getDefault(), Messages.class.getClassLoader());
      } catch (Throwable var9) {
         try {
            temp = ResourceBundle.getBundle("com.mysql.cj.core.LocalizedErrorMessages");
         } catch (Throwable var8) {
            RuntimeException rt = new RuntimeException("Can't load resource bundle due to underlying exception " + var9.toString());
            rt.initCause(var8);
            throw rt;
         }
      } finally {
         RESOURCE_BUNDLE = temp;
      }

   }
}
