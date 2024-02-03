package net.mineland.creative.modules.coding;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.google.gson.JsonSyntaxException;
import java.util.Map;
import java.util.regex.Pattern;
import org.bukkit.Location;
import org.bukkit.inventory.ItemStack;
import ua.govnojon.libs.bukkitutil.ItemStackUtil;
import ua.govnojon.libs.config.Config;

public class Param {
   private static final Pattern INT = Pattern.compile("-?[1-9]+[0-9]*");
   private static final Pattern FLOAT = Pattern.compile("-?[0-9]+\\.?[0-9]*");
   private static final Pattern BOOLEAN = Pattern.compile("(?i)true|on|yes");
   private static JsonParser jsonParser = new JsonParser();
   private JsonObject jsonParams;

   public Param(String param) {
      try {
         this.jsonParams = (JsonObject)jsonParser.parse(param);
      } catch (JsonSyntaxException var3) {
         this.jsonParams = new JsonObject();
         System.out.println("Ошибка парсинга параметров: `" + param + "`");
         var3.printStackTrace();
      }

   }

   public Param(String key, String value) {
      this();
      this.jsonParams.addProperty(key, value);
   }

   public Param(Map<String, String> params) {
      this.jsonParams = new JsonObject();
      JsonObject var10001 = this.jsonParams;
      params.forEach(var10001::addProperty);
   }

   public Param() {
      this.jsonParams = new JsonObject();
   }

   public JsonObject getJsonParams() {
      return this.jsonParams;
   }

   public void remove(String key) {
      this.jsonParams.remove(key);
   }

   public String get(String key, String defParam) {
      return !this.jsonParams.has(key) ? defParam : this.jsonParams.get(key).getAsString();
   }

   public int get(String key, int defParam) {
      if (!this.jsonParams.has(key)) {
         return defParam;
      } else {
         String str = this.jsonParams.get(key).getAsString();
         return !INT.matcher(str).matches() ? defParam : Integer.parseInt(str);
      }
   }

   public String[] get(String key, String[] defParam) {
      if (!this.jsonParams.has(key)) {
         return defParam;
      } else {
         JsonArray jsonArray = this.jsonParams.get(key).getAsJsonArray();
         String[] array = new String[jsonArray.size()];

         for(int i = 0; i < jsonArray.size(); ++i) {
            array[i] = jsonArray.get(i).getAsString();
         }

         return array;
      }
   }

   public int[] get(String key, int[] defParam) {
      if (!this.jsonParams.has(key)) {
         return defParam;
      } else {
         JsonArray jsonArray = this.jsonParams.get(key).getAsJsonArray();
         int[] array = new int[jsonArray.size()];

         for(int i = 0; i < jsonArray.size(); ++i) {
            array[i] = jsonArray.get(i).getAsInt();
         }

         return array;
      }
   }

   public ItemStack[] get(String key, ItemStack[] defParam) {
      String[] strings = this.get(key, ItemStackUtil.serializeArray(defParam));
      return strings.length != 0 ? ItemStackUtil.deserializeArray(strings) : defParam;
   }

   public ItemStack get(String key, ItemStack defParam) {
      String string = this.get(key, ItemStackUtil.serialize(defParam));
      return !string.isEmpty() ? ItemStackUtil.deserialize(string) : defParam;
   }

   public Location get(String key, Location def) {
      String string = this.get(key, Config.toString(def));
      return !string.isEmpty() ? Config.toLocation(string) : def;
   }

   public boolean get(String key, boolean def) {
      return !this.jsonParams.has(key) ? def : this.jsonParams.get(key).getAsBoolean();
   }

   public void put(String key, String value) {
      this.jsonParams.addProperty(key, value);
   }

   public void put(String key, int value) {
      this.jsonParams.addProperty(key, value);
   }

   public void put(String key, ItemStack value) {
      this.jsonParams.addProperty(key, ItemStackUtil.serialize(value));
   }

   public void put(String key, Location value) {
      this.put(key, Config.toString(value));
   }

   public void put(String key, String[] value) {
      JsonArray jsonArray = new JsonArray();
      String[] var4 = value;
      int var5 = value.length;

      for(int var6 = 0; var6 < var5; ++var6) {
         String s = var4[var6];
         jsonArray.add(s);
      }

      this.jsonParams.add(key, jsonArray);
   }

   public void put(String key, Integer[] value) {
      JsonArray jsonArray = new JsonArray();
      Integer[] var4 = value;
      int var5 = value.length;

      for(int var6 = 0; var6 < var5; ++var6) {
         int i = var4[var6];
         jsonArray.add(i);
      }

      this.jsonParams.add(key, jsonArray);
   }

   public void put(String key, ItemStack[] value) {
      this.put(key, ItemStackUtil.serializeArray(value));
   }

   public void put(String key, boolean value) {
      this.jsonParams.addProperty(key, value);
   }

   public String toString() {
      return this.jsonParams.toString();
   }

   public String get(String key) {
      return this.get(key, "");
   }
}
