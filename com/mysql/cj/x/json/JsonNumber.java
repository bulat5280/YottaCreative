package com.mysql.cj.x.json;

import com.mysql.cj.api.x.JsonValue;
import java.math.BigDecimal;

public class JsonNumber implements JsonValue {
   private String val = "null";

   public Integer getInteger() {
      return Integer.valueOf(this.val);
   }

   public BigDecimal getBigDecimal() {
      return new BigDecimal(this.val);
   }

   public JsonNumber setValue(String value) {
      this.val = (new BigDecimal(value)).toString();
      return this;
   }

   public String toString() {
      return this.val;
   }
}
