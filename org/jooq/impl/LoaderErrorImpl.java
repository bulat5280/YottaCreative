package org.jooq.impl;

import org.jooq.LoaderError;
import org.jooq.Query;
import org.jooq.exception.DataAccessException;

final class LoaderErrorImpl implements LoaderError {
   private final DataAccessException exception;
   private final int rowIndex;
   private final String[] row;
   private final Query query;

   LoaderErrorImpl(DataAccessException exception, Object[] row, int rowIndex, Query query) {
      this.exception = exception;
      this.row = strings(row);
      this.rowIndex = rowIndex;
      this.query = query;
   }

   private static String[] strings(Object[] row) {
      if (row == null) {
         return null;
      } else {
         String[] result = new String[row.length];

         for(int i = 0; i < result.length; ++i) {
            result[i] = row[i] == null ? null : row[i].toString();
         }

         return result;
      }
   }

   public DataAccessException exception() {
      return this.exception;
   }

   public int rowIndex() {
      return this.rowIndex;
   }

   public String[] row() {
      return this.row;
   }

   public Query query() {
      return this.query;
   }
}
