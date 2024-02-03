package org.jooq.util;

import java.util.List;

public interface DomainDefinition extends Definition {
   List<String> getCheckClauses();

   DataTypeDefinition getBaseType();
}
