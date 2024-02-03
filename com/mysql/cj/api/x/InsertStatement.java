package com.mysql.cj.api.x;

import java.util.Arrays;
import java.util.List;

public interface InsertStatement extends Statement<InsertStatement, Result> {
   InsertStatement values(List<Object> var1);

   default InsertStatement values(Object... values) {
      return this.values(Arrays.asList(values));
   }
}
