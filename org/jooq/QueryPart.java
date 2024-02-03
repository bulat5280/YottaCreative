package org.jooq;

import java.io.Serializable;

public interface QueryPart extends Serializable {
   String toString();

   boolean equals(Object var1);

   int hashCode();
}
