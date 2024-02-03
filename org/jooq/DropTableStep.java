package org.jooq;

public interface DropTableStep extends DropTableFinalStep {
   @Support
   DropTableFinalStep cascade();

   @Support
   DropTableFinalStep restrict();
}
