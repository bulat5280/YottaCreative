package org.jooq;

public interface WindowSpecificationRowsStep extends WindowSpecificationFinalStep {
   @Support({SQLDialect.POSTGRES})
   WindowSpecificationFinalStep rowsUnboundedPreceding();

   @Support({SQLDialect.POSTGRES})
   WindowSpecificationFinalStep rowsPreceding(int var1);

   @Support({SQLDialect.POSTGRES})
   WindowSpecificationFinalStep rowsCurrentRow();

   @Support({SQLDialect.POSTGRES})
   WindowSpecificationFinalStep rowsUnboundedFollowing();

   @Support({SQLDialect.POSTGRES})
   WindowSpecificationFinalStep rowsFollowing(int var1);

   @Support({SQLDialect.POSTGRES})
   WindowSpecificationRowsAndStep rowsBetweenUnboundedPreceding();

   @Support({SQLDialect.POSTGRES})
   WindowSpecificationRowsAndStep rowsBetweenPreceding(int var1);

   @Support({SQLDialect.POSTGRES})
   WindowSpecificationRowsAndStep rowsBetweenCurrentRow();

   @Support({SQLDialect.POSTGRES})
   WindowSpecificationRowsAndStep rowsBetweenUnboundedFollowing();

   @Support({SQLDialect.POSTGRES})
   WindowSpecificationRowsAndStep rowsBetweenFollowing(int var1);

   @Support({SQLDialect.POSTGRES})
   WindowSpecificationFinalStep rangeUnboundedPreceding();

   @Support({SQLDialect.POSTGRES})
   WindowSpecificationFinalStep rangePreceding(int var1);

   @Support({SQLDialect.POSTGRES})
   WindowSpecificationFinalStep rangeCurrentRow();

   @Support({SQLDialect.POSTGRES})
   WindowSpecificationFinalStep rangeUnboundedFollowing();

   @Support({SQLDialect.POSTGRES})
   WindowSpecificationFinalStep rangeFollowing(int var1);

   @Support({SQLDialect.POSTGRES})
   WindowSpecificationRowsAndStep rangeBetweenUnboundedPreceding();

   @Support({SQLDialect.POSTGRES})
   WindowSpecificationRowsAndStep rangeBetweenPreceding(int var1);

   @Support({SQLDialect.POSTGRES})
   WindowSpecificationRowsAndStep rangeBetweenCurrentRow();

   @Support({SQLDialect.POSTGRES})
   WindowSpecificationRowsAndStep rangeBetweenUnboundedFollowing();

   @Support({SQLDialect.POSTGRES})
   WindowSpecificationRowsAndStep rangeBetweenFollowing(int var1);
}
