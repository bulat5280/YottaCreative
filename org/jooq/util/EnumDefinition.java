package org.jooq.util;

import java.util.List;

public interface EnumDefinition extends Definition {
   List<String> getLiterals();

   boolean isSynthetic();
}
