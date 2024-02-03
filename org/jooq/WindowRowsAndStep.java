package org.jooq;

public interface WindowRowsAndStep<T> {
   @Support({SQLDialect.POSTGRES})
   WindowFinalStep<T> andUnboundedPreceding();

   @Support({SQLDialect.POSTGRES})
   WindowFinalStep<T> andPreceding(int var1);

   @Support({SQLDialect.POSTGRES})
   WindowFinalStep<T> andCurrentRow();

   @Support({SQLDialect.POSTGRES})
   WindowFinalStep<T> andUnboundedFollowing();

   @Support({SQLDialect.POSTGRES})
   WindowFinalStep<T> andFollowing(int var1);
}
