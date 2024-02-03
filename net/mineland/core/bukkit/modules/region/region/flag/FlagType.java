package net.mineland.core.bukkit.modules.region.region.flag;

import java.util.stream.Stream;
import org.bukkit.WeatherType;

public enum FlagType {
   PVP(new String[]{"allow", "deny", "member"}),
   ALLOW_PLACE((String[])null),
   PLACE(new String[]{"allow", "deny", "member"}),
   HUNGER(new String[]{"allow", "deny", "member"}),
   PROTECT(new String[]{"allow", "deny", "member"}),
   USE(new String[]{"allow", "deny", "member"}),
   CONTAINER(new String[]{"allow", "deny", "member"}),
   ALLOW_INTERACT_ITEMS((String[])null),
   ALLOW_INTERACT_BLOCKS((String[])null),
   SOIL_FADE(new String[]{"allow", "deny"}),
   ICE_FADE(new String[]{"allow", "deny"}),
   IGNITE(new String[]{"allow", "deny"}),
   DAMAGE_PLAYER(new String[]{"allow", "deny"}),
   DAMAGE_ENTITY(new String[]{"allow", "deny", "member"}),
   TRAMPLE_SOIL(new String[]{"allow", "deny", "member"}),
   SPAWN(new String[]{"allow", "deny"}),
   PLAYER_SPAWN_ENTITY(new String[]{"allow", "deny", "member"}),
   DENY_SPAWN((String[])null),
   ALLOW_SPAWN((String[])null),
   ENTITY_EXPLODE(new String[]{"allow", "deny"}),
   COMMAND(new String[]{"allow", "deny"}),
   DENY_COMMAND((String[])null),
   ALLOW_COMMAND((String[])null),
   DENY_SPAWN_REASON((String[])null),
   DENY_TELEPORT_CAUSE((String[])null),
   PICKUP(new String[]{"allow", "deny", "member"}),
   TIME(FlagType.Values.Times.getStringValues()),
   WEATHER(FlagType.Values.Weathers.getStringValues()),
   DROP_PLAYER(new String[]{"allow", "deny", "member"}),
   DROP(new String[]{"allow", "deny"}),
   EXECUTE_COMMAND((String[])null),
   ENTITY_SPAWN_LIMIT((String[])null),
   RELOG(new String[]{"allow", "deny"}),
   SNOW_MELTS(new String[]{"allow", "deny"}),
   SNOWFALL(new String[]{"allow", "deny"}),
   ICE_FORM(new String[]{"allow", "deny"}),
   POTION_SPLASH(new String[]{"allow", "deny"}),
   LEAF_DECAY(new String[]{"allow", "deny"}),
   LAUNCH_PROJECTILE(new String[]{"allow", "deny", "member"});

   private String[] values;

   private FlagType(String... values) {
      this.values = values;
   }

   public static FlagType forName(String name) {
      try {
         return valueOf(name.toUpperCase());
      } catch (Exception var2) {
         return null;
      }
   }

   public static boolean search(String value, String search) {
      if (value != null && value.length() != 0 && search.length() != 0) {
         char[] val = value.toCharArray();
         char[] sea = search.toCharArray();
         int pos = 0;

         for(int i = 0; i < val.length; ++i) {
            if (val[i] == sea[pos++]) {
               if (pos != sea.length) {
                  continue;
               }

               if (i + 1 == val.length || val[i + 1] == ';') {
                  return true;
               }
            }

            for(pos = 0; i < val.length && val[i] != ';'; ++i) {
            }
         }

         return false;
      } else {
         return false;
      }
   }

   public static boolean searchIgnoreCase(String value, String search) {
      return value != null && value.length() != 0 && search.length() != 0 && search(value.toLowerCase(), search.toLowerCase());
   }

   public static boolean searchStart(String value, String search) {
      if (value != null && value.length() > 1 && search.length() > 1) {
         char[] val = value.toCharArray();
         char[] sea = search.toCharArray();
         int pos = 0;

         for(int i = 0; i < val.length && pos < sea.length; ++i) {
            if (val[i] == sea[pos++]) {
               if (i + 1 >= val.length || val[i + 1] == ';') {
                  return true;
               }
            } else {
               for(pos = 0; i < val.length && val[i] != ';'; ++i) {
               }
            }
         }

         return false;
      } else {
         return false;
      }
   }

   public static boolean searchStartIngoreCase(String value, String search) {
      return value != null && value.length() != 0 && search.length() != 0 && searchStart(value.toLowerCase(), search.toLowerCase());
   }

   public String[] getValues() {
      return this.values;
   }

   public boolean isEmptyValues() {
      return this.values == null || values().length == 0;
   }

   public boolean isContainsValue(String value) {
      String[] var2 = this.values;
      int var3 = var2.length;

      for(int var4 = 0; var4 < var3; ++var4) {
         String v = var2[var4];
         if (v.equals(value)) {
            return true;
         }
      }

      return false;
   }

   public static final class Values {
      public static final String ALLOW = "allow";
      public static final String DENY = "deny";
      public static final String MEMBER = "member";

      public static String byList(String... values) {
         return String.join(";", values);
      }

      public static enum Weathers {
         SERVER((WeatherType)null),
         DOWNFALL(WeatherType.DOWNFALL),
         CLEAR(WeatherType.CLEAR);

         private static final String[] values = (String[])Stream.of(values()).map(Enum::name).toArray((x$0) -> {
            return new String[x$0];
         });
         private WeatherType value;

         private Weathers(WeatherType value) {
            this.value = value;
         }

         public static FlagType.Values.Weathers forName(String name) {
            try {
               return valueOf(name);
            } catch (Exception var2) {
               return null;
            }
         }

         public static String[] getStringValues() {
            return values;
         }

         public WeatherType getValue() {
            return this.value;
         }

         public String toString() {
            return this.name();
         }
      }

      public static enum Times {
         SERVER(-1),
         v1(19000),
         v2(20000),
         v3(21000),
         v4(22000),
         v5(23000),
         v6(0),
         v7(1000),
         v8(2000),
         v9(3000),
         v10(4000),
         v11(5000),
         v12(6000),
         v13(7000),
         v14(8000),
         v15(9000),
         v16(10000),
         v17(11000),
         v18(12000),
         v19(13000),
         v20(14000),
         v21(15000),
         v22(16000),
         v23(17000),
         v24(18000);

         private static final String[] values = (String[])Stream.of(values()).map(Enum::name).toArray((x$0) -> {
            return new String[x$0];
         });
         private int value;

         private Times(int value) {
            this.value = value;
         }

         public static FlagType.Values.Times forName(String name) {
            try {
               return valueOf(name);
            } catch (Exception var2) {
               return null;
            }
         }

         public static String[] getStringValues() {
            return values;
         }

         public int getValue() {
            return this.value;
         }

         public String toString() {
            return this.name();
         }
      }
   }
}
