package com.mysql.cj.x.json;

import com.mysql.cj.api.x.JsonValue;
import java.util.ArrayList;
import java.util.Iterator;

public class JsonArray extends ArrayList<JsonValue> implements JsonValue {
   private static final long serialVersionUID = 6557406141541247905L;

   public String toString() {
      StringBuilder sb = new StringBuilder("[");
      boolean isFirst = true;

      JsonValue val;
      for(Iterator var3 = this.iterator(); var3.hasNext(); sb.append(val.toString())) {
         val = (JsonValue)var3.next();
         if (isFirst) {
            isFirst = false;
         } else {
            sb.append(", ");
         }
      }

      sb.append("]");
      return sb.toString();
   }

   public String toPackedString() {
      StringBuilder sb = new StringBuilder("[");
      boolean isFirst = true;

      JsonValue val;
      for(Iterator var3 = this.iterator(); var3.hasNext(); sb.append(val.toString())) {
         val = (JsonValue)var3.next();
         if (isFirst) {
            isFirst = false;
         } else {
            sb.append(",");
         }
      }

      sb.append("]");
      return sb.toString();
   }

   public JsonArray addValue(JsonValue val) {
      this.add(val);
      return this;
   }
}
