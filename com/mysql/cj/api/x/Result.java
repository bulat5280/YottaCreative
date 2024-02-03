package com.mysql.cj.api.x;

import java.util.Iterator;
import java.util.List;

public interface Result {
   long getAffectedItemsCount();

   Long getAutoIncrementValue();

   List<String> getLastDocumentIds();

   int getWarningsCount();

   Iterator<Warning> getWarnings();
}
