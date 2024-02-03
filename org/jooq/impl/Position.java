package org.jooq.impl;

import org.jooq.Configuration;
import org.jooq.Field;

final class Position extends AbstractFunction<Integer> {
   private static final long serialVersionUID = 3544690069533526544L;
   private final Field<String> search;
   private final Field<String> in;
   private final Field<? extends Number> startIndex;

   Position(Field<String> search, Field<String> in) {
      this(search, in, (Field)null);
   }

   Position(Field<String> search, Field<String> in, Field<? extends Number> startIndex) {
      super("position", SQLDataType.INTEGER, search, in, startIndex);
      this.search = search;
      this.in = in;
      this.startIndex = startIndex;
   }

   final Field<Integer> getFunction0(Configuration configuration) {
      if (this.startIndex != null) {
         switch(configuration.family()) {
         default:
            return DSL.position(DSL.substring(this.in, this.startIndex), this.search).add(this.startIndex).sub((Field)DSL.one());
         }
      } else {
         switch(configuration.family()) {
         case DERBY:
            return DSL.field("{locate}({0}, {1})", SQLDataType.INTEGER, this.search, this.in);
         case SQLITE:
            return DSL.field("{instr}({0}, {1})", SQLDataType.INTEGER, this.in, this.search);
         default:
            return DSL.field("{position}({0} {in} {1})", SQLDataType.INTEGER, this.search, this.in);
         }
      }
   }
}
