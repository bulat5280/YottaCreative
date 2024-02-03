package com.p6spy.engine.spy.option;

import com.p6spy.engine.common.P6Util;
import com.p6spy.engine.spy.P6ModuleManager;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.net.URLConnection;
import java.util.Map;
import java.util.Properties;

public class SpyDotProperties implements P6OptionsSource {
   public static final String OPTIONS_FILE_PROPERTY = "spy.properties";
   public static final String DEFAULT_OPTIONS_FILE = "spy.properties";
   private final long lastModified;
   private SpyDotPropertiesReloader reloader;
   private final Map<String, String> options;

   public SpyDotProperties() throws IOException {
      URL url = this.locate();
      if (null == url) {
         this.lastModified = -1L;
         this.options = null;
      } else {
         this.lastModified = this.lastModified();
         InputStream in = null;

         try {
            in = url.openStream();
            Properties properties = new Properties();
            properties.load(in);
            this.options = P6Util.getPropertiesMap(properties);
         } finally {
            if (null != in) {
               try {
                  in.close();
               } catch (Exception var9) {
               }
            }

         }

      }
   }

   public boolean isModified() {
      return this.lastModified() != this.lastModified;
   }

   private long lastModified() {
      long lastMod = -1L;
      URLConnection con = null;
      URL url = this.locate();
      if (url != null) {
         boolean var18 = false;

         InputStream in;
         label152: {
            try {
               var18 = true;
               con = url.openConnection();
               lastMod = con.getLastModified();
               var18 = false;
               break label152;
            } catch (IOException var25) {
               var18 = false;
            } finally {
               if (var18) {
                  if (con != null) {
                     InputStream in = null;

                     try {
                        in = con.getInputStream();
                     } catch (IOException var20) {
                     }

                     if (in != null) {
                        try {
                           in.close();
                        } catch (IOException var19) {
                        }
                     }
                  }

               }
            }

            if (con != null) {
               in = null;

               try {
                  in = con.getInputStream();
               } catch (IOException var22) {
               }

               if (in != null) {
                  try {
                     in.close();
                  } catch (IOException var21) {
                  }

                  return lastMod;
               }
            }

            return lastMod;
         }

         if (con != null) {
            in = null;

            try {
               in = con.getInputStream();
            } catch (IOException var24) {
            }

            if (in != null) {
               try {
                  in.close();
               } catch (IOException var23) {
               }
            }
         }
      }

      return lastMod;
   }

   private URL locate() {
      String propsFileName = System.getProperty("spy.properties", "spy.properties");
      if (null == propsFileName || propsFileName.isEmpty()) {
         propsFileName = "spy.properties";
      }

      return P6Util.locateFile(propsFileName);
   }

   public Map<String, String> getOptions() {
      return this.options;
   }

   public void preDestroy(P6ModuleManager p6moduleManager) {
      if (this.reloader != null) {
         this.reloader.kill(p6moduleManager);
      }

   }

   public void postInit(P6ModuleManager p6moduleManager) {
      this.reloader = new SpyDotPropertiesReloader(this, p6moduleManager);
   }
}
