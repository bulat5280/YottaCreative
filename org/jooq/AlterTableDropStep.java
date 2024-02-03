package org.jooq;

public interface AlterTableDropStep extends AlterTableFinalStep {
   @Support
   AlterTableFinalStep cascade();

   @Support
   AlterTableFinalStep restrict();
}
