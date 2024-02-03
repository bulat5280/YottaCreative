package org.jooq;

import java.io.Serializable;

public interface Attachable extends Serializable {
   void attach(Configuration var1);

   void detach();
}
