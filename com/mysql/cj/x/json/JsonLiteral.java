package com.mysql.cj.x.json;

import com.mysql.cj.api.x.JsonValue;

public enum JsonLiteral implements JsonValue {
   TRUE("true"),
   FALSE("false"),
   NULL("null");

   public final String value;

   private JsonLiteral(String val) {
      this.value = val;
   }

   public String toString() {
      return this.value;
   }
}
