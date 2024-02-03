package org.jooq;

import java.util.List;
import org.jooq.impl.SchemaImpl;

class RenamedSchema extends SchemaImpl {
   private static final long serialVersionUID = -3579885830845728730L;
   private final Schema delegate;

   RenamedSchema(Schema delegate, String rename) {
      super(rename);
      this.delegate = delegate;
   }

   public final List<Table<?>> getTables() {
      return this.delegate.getTables();
   }

   public final List<UDT<?>> getUDTs() {
      return this.delegate.getUDTs();
   }

   public final List<Sequence<?>> getSequences() {
      return this.delegate.getSequences();
   }
}
