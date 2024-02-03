package org.jooq;

public interface WindowRowsStep<T> extends WindowFinalStep<T> {
   @Support({SQLDialect.POSTGRES})
   WindowFinalStep<T> rowsUnboundedPreceding();

   @Support({SQLDialect.POSTGRES})
   WindowFinalStep<T> rowsPreceding(int var1);

   @Support({SQLDialect.POSTGRES})
   WindowFinalStep<T> rowsCurrentRow();

   @Support({SQLDialect.POSTGRES})
   WindowFinalStep<T> rowsUnboundedFollowing();

   @Support({SQLDialect.POSTGRES})
   WindowFinalStep<T> rowsFollowing(int var1);

   @Support({SQLDialect.POSTGRES})
   WindowRowsAndStep<T> rowsBetweenUnboundedPreceding();

   @Support({SQLDialect.POSTGRES})
   WindowRowsAndStep<T> rowsBetweenPreceding(int var1);

   @Support({SQLDialect.POSTGRES})
   WindowRowsAndStep<T> rowsBetweenCurrentRow();

   @Support({SQLDialect.POSTGRES})
   WindowRowsAndStep<T> rowsBetweenUnboundedFollowing();

   @Support({SQLDialect.POSTGRES})
   WindowRowsAndStep<T> rowsBetweenFollowing(int var1);

   @Support({SQLDialect.POSTGRES})
   WindowFinalStep<T> rangeUnboundedPreceding();

   @Support({SQLDialect.POSTGRES})
   WindowFinalStep<T> rangePreceding(int var1);

   @Support({SQLDialect.POSTGRES})
   WindowFinalStep<T> rangeCurrentRow();

   @Support({SQLDialect.POSTGRES})
   WindowFinalStep<T> rangeUnboundedFollowing();

   @Support({SQLDialect.POSTGRES})
   WindowFinalStep<T> rangeFollowing(int var1);

   @Support({SQLDialect.POSTGRES})
   WindowRowsAndStep<T> rangeBetweenUnboundedPreceding();

   @Support({SQLDialect.POSTGRES})
   WindowRowsAndStep<T> rangeBetweenPreceding(int var1);

   @Support({SQLDialect.POSTGRES})
   WindowRowsAndStep<T> rangeBetweenCurrentRow();

   @Support({SQLDialect.POSTGRES})
   WindowRowsAndStep<T> rangeBetweenUnboundedFollowing();

   @Support({SQLDialect.POSTGRES})
   WindowRowsAndStep<T> rangeBetweenFollowing(int var1);
}
