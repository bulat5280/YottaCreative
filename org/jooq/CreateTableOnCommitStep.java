package org.jooq;

public interface CreateTableOnCommitStep extends CreateTableFinalStep {
   @Support({SQLDialect.POSTGRES})
   CreateTableFinalStep onCommitDeleteRows();

   @Support({SQLDialect.POSTGRES})
   CreateTableFinalStep onCommitPreserveRows();

   @Support({SQLDialect.POSTGRES})
   CreateTableFinalStep onCommitDrop();
}
