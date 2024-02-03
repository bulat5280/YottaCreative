package org.jooq;

import java.util.EventListener;

public interface VisitListener extends EventListener {
   void clauseStart(VisitContext var1);

   void clauseEnd(VisitContext var1);

   void visitStart(VisitContext var1);

   void visitEnd(VisitContext var1);
}
