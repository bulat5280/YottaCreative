package com.mysql.cj.x.json;

import com.mysql.cj.api.x.JsonValue;
import java.util.Iterator;
import java.util.TreeMap;

public class DbDoc extends TreeMap<String, JsonValue> implements JsonValue {
   private static final long serialVersionUID = 6557406141541247905L;

   public String toString() {
      StringBuilder sb = new StringBuilder("{");
      boolean isFirst = true;

      String key;
      for(Iterator var3 = this.keySet().iterator(); var3.hasNext(); sb.append("\n\"").append(key).append("\" : ").append(((JsonValue)this.get(key)).toString())) {
         key = (String)var3.next();
         if (isFirst) {
            isFirst = false;
         } else {
            sb.append(",");
         }
      }

      if (this.size() > 0) {
         sb.append("\n");
      }

      sb.append("}");
      return sb.toString();
   }

   public String toPackedString() {
      StringBuilder sb = new StringBuilder("{");
      boolean isFirst = true;
      Iterator var3 = this.keySet().iterator();

      while(var3.hasNext()) {
         String key = (String)var3.next();
         if (isFirst) {
            isFirst = false;
         } else {
            sb.append(",");
         }

         sb.append("\"").append(key).append("\":");
         if (JsonArray.class.equals(((JsonValue)this.get(key)).getClass())) {
            sb.append(((JsonArray)this.get(key)).toPackedString());
         } else if (DbDoc.class.equals(((JsonValue)this.get(key)).getClass())) {
            sb.append(((DbDoc)this.get(key)).toPackedString());
         } else {
            sb.append(((JsonValue)this.get(key)).toString());
         }
      }

      sb.append("}");
      return sb.toString();
   }

   public DbDoc add(String key, JsonValue val) {
      this.put(key, val);
      return this;
   }
}
