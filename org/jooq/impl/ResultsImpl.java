package org.jooq.impl;

import java.util.AbstractList;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import org.jooq.AttachableInternal;
import org.jooq.Configuration;
import org.jooq.Record;
import org.jooq.Result;
import org.jooq.ResultOrRows;
import org.jooq.Results;

final class ResultsImpl extends AbstractList<Result<Record>> implements Results, AttachableInternal {
   private static final long serialVersionUID = 1744826140354980500L;
   private Configuration configuration;
   private final List<ResultOrRows> results;

   ResultsImpl(Configuration configuration) {
      this.configuration = configuration;
      this.results = new ArrayList();
   }

   public final List<ResultOrRows> resultsOrRows() {
      return this.results;
   }

   public final void attach(Configuration c) {
      this.configuration = c;
      Iterator var2 = this.iterator();

      while(var2.hasNext()) {
         Result<?> result = (Result)var2.next();
         if (result != null) {
            result.attach(c);
         }
      }

   }

   public final void detach() {
      this.attach((Configuration)null);
   }

   public final Configuration configuration() {
      return this.configuration;
   }

   public String toString() {
      StringBuilder sb = new StringBuilder();
      String separator = "";

      for(Iterator var3 = this.results.iterator(); var3.hasNext(); separator = "\n") {
         ResultOrRows result = (ResultOrRows)var3.next();
         if (result.result() == null) {
            sb.append(separator).append("Update count: ").append(result.rows());
         } else {
            sb.append(separator).append("Result set:\n").append(result.result());
         }
      }

      return sb.toString();
   }

   public int hashCode() {
      return this.results.hashCode();
   }

   public boolean equals(Object obj) {
      if (this == obj) {
         return true;
      } else if (obj instanceof ResultsImpl) {
         ResultsImpl other = (ResultsImpl)obj;
         return this.results.equals(other.results);
      } else {
         return false;
      }
   }

   public final int size() {
      return this.list().size();
   }

   public final Result<Record> get(int index) {
      return (Result)this.list().get(index);
   }

   public Result<Record> set(int index, Result<Record> element) {
      return ((ResultOrRows)this.results.set(this.translatedIndex(index), new ResultsImpl.ResultOrRowsImpl(element))).result();
   }

   public void add(int index, Result<Record> element) {
      this.results.add(this.translatedIndex(index), new ResultsImpl.ResultOrRowsImpl(element));
   }

   public Result<Record> remove(int index) {
      return ((ResultOrRows)this.results.remove(this.translatedIndex(index))).result();
   }

   public void clear() {
      this.results.clear();
   }

   private final List<Result<Record>> list() {
      List<Result<Record>> list = new ArrayList();
      Iterator var2 = this.results.iterator();

      while(var2.hasNext()) {
         ResultOrRows result = (ResultOrRows)var2.next();
         if (result.result() != null) {
            list.add(result.result());
         }
      }

      return list;
   }

   private final int translatedIndex(int index) {
      int translated = 0;

      for(int i = 0; i < index; ++i) {
         while(((ResultOrRows)this.results.get(translated++)).result() == null) {
         }
      }

      return translated;
   }

   static final class ResultOrRowsImpl implements ResultOrRows {
      private final Result<Record> result;
      private final int rows;

      ResultOrRowsImpl(Result<Record> result) {
         this(result, result != null ? result.size() : 0);
      }

      ResultOrRowsImpl(int rows) {
         this((Result)null, rows);
      }

      private ResultOrRowsImpl(Result<Record> result, int rows) {
         this.result = result;
         this.rows = rows;
      }

      public final Result<Record> result() {
         return this.result;
      }

      public final int rows() {
         return this.rows;
      }

      public int hashCode() {
         int prime = true;
         int r = 1;
         int r = 31 * r + (this.result == null ? 0 : this.result.hashCode());
         r = 31 * r + this.rows;
         return r;
      }

      public boolean equals(Object obj) {
         if (this == obj) {
            return true;
         } else if (obj == null) {
            return false;
         } else if (this.getClass() != obj.getClass()) {
            return false;
         } else {
            ResultsImpl.ResultOrRowsImpl other = (ResultsImpl.ResultOrRowsImpl)obj;
            if (this.result == null) {
               if (other.result != null) {
                  return false;
               }
            } else if (!this.result.equals(other.result)) {
               return false;
            }

            return this.rows == other.rows;
         }
      }
   }
}
