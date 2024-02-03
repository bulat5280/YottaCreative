package org.jooq.conf;

import java.io.File;
import java.io.InputStream;
import javax.xml.bind.JAXB;
import org.jooq.tools.StringUtils;

public final class SettingsTools {
   private static final Settings DEFAULT_SETTINGS;

   public static final ParamType getParamType(Settings settings) {
      if (executeStaticStatements(settings)) {
         return ParamType.INLINED;
      } else {
         if (settings != null) {
            ParamType result = settings.getParamType();
            if (result != null) {
               return result;
            }
         }

         return ParamType.INDEXED;
      }
   }

   public static final StatementType getStatementType(Settings settings) {
      if (settings != null) {
         StatementType result = settings.getStatementType();
         if (result != null) {
            return result;
         }
      }

      return StatementType.PREPARED_STATEMENT;
   }

   public static final BackslashEscaping getBackslashEscaping(Settings settings) {
      if (settings != null) {
         BackslashEscaping result = settings.getBackslashEscaping();
         if (result != null) {
            return result;
         }
      }

      return BackslashEscaping.DEFAULT;
   }

   public static final boolean executePreparedStatements(Settings settings) {
      return getStatementType(settings) == StatementType.PREPARED_STATEMENT;
   }

   public static final boolean executeStaticStatements(Settings settings) {
      return getStatementType(settings) == StatementType.STATIC_STATEMENT;
   }

   public static final boolean updatablePrimaryKeys(Settings settings) {
      return (Boolean)StringUtils.defaultIfNull(settings.isUpdatablePrimaryKeys(), false);
   }

   public static final boolean reflectionCaching(Settings settings) {
      return (Boolean)StringUtils.defaultIfNull(settings.isReflectionCaching(), true);
   }

   public static final RenderMapping getRenderMapping(Settings settings) {
      if (settings.getRenderMapping() == null) {
         settings.setRenderMapping(new RenderMapping());
      }

      return settings.getRenderMapping();
   }

   public static final Settings defaultSettings() {
      return clone(DEFAULT_SETTINGS);
   }

   public static final Settings clone(Settings settings) {
      return (Settings)settings.clone();
   }

   public static int getQueryTimeout(int timeout, Settings settings) {
      return timeout != 0 ? timeout : (settings.getQueryTimeout() != null ? settings.getQueryTimeout() : 0);
   }

   public static int getMaxRows(int maxRows, Settings settings) {
      return maxRows != 0 ? maxRows : (settings.getMaxRows() != null ? settings.getMaxRows() : 0);
   }

   public static int getFetchSize(int fetchSize, Settings settings) {
      return fetchSize != 0 ? fetchSize : (settings.getFetchSize() != null ? settings.getFetchSize() : 0);
   }

   static {
      Settings settings = null;
      String property = System.getProperty("org.jooq.settings");
      InputStream in;
      if (property != null) {
         in = SettingsTools.class.getResourceAsStream(property);
         if (in != null) {
            settings = (Settings)JAXB.unmarshal(in, Settings.class);
         } else {
            settings = (Settings)JAXB.unmarshal(new File(property), Settings.class);
         }
      }

      if (settings == null) {
         in = SettingsTools.class.getResourceAsStream("/jooq-settings.xml");
         if (in != null) {
            settings = (Settings)JAXB.unmarshal(in, Settings.class);
         }
      }

      if (settings == null) {
         settings = new Settings();
      }

      DEFAULT_SETTINGS = settings;
   }
}
