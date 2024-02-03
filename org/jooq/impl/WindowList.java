package org.jooq.impl;

import org.jooq.WindowDefinition;

final class WindowList extends QueryPartList<WindowDefinition> {
   private static final long serialVersionUID = 4284724883554582081L;

   public final boolean declaresWindows() {
      return true;
   }
}
