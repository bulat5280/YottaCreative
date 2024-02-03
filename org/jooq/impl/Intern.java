package org.jooq.impl;

import java.io.Serializable;
import org.jooq.Field;
import org.jooq.Name;

final class Intern implements Serializable {
   private static final long serialVersionUID = 6455756912567274014L;
   int[] internIndexes;
   Field<?>[] internFields;
   String[] internNameStrings;
   Name[] internNames;

   final int[] internIndexes(Field<?>[] fields) {
      if (this.internIndexes != null) {
         return this.internIndexes;
      } else if (this.internFields != null) {
         return (new Fields(fields)).indexesOf(this.internFields);
      } else if (this.internNameStrings != null) {
         return (new Fields(fields)).indexesOf(this.internNameStrings);
      } else {
         return this.internNames != null ? (new Fields(fields)).indexesOf(this.internNames) : null;
      }
   }
}
