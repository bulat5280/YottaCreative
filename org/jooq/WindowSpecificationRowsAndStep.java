package org.jooq;

public interface WindowSpecificationRowsAndStep {
   @Support({SQLDialect.POSTGRES})
   WindowSpecificationFinalStep andUnboundedPreceding();

   @Support({SQLDialect.POSTGRES})
   WindowSpecificationFinalStep andPreceding(int var1);

   @Support({SQLDialect.POSTGRES})
   WindowSpecificationFinalStep andCurrentRow();

   @Support({SQLDialect.POSTGRES})
   WindowSpecificationFinalStep andUnboundedFollowing();

   @Support({SQLDialect.POSTGRES})
   WindowSpecificationFinalStep andFollowing(int var1);
}
