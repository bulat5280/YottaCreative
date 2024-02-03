package org.jooq.tools;

import org.jooq.ExecuteContext;
import org.jooq.ExecuteListener;

public class StopWatchListener implements ExecuteListener {
   private static final long serialVersionUID = 7399239846062763212L;
   private final StopWatch watch = new StopWatch();

   public void start(ExecuteContext ctx) {
      this.watch.splitTrace("Initialising");
   }

   public void renderStart(ExecuteContext ctx) {
      this.watch.splitTrace("Rendering query");
   }

   public void renderEnd(ExecuteContext ctx) {
      this.watch.splitTrace("Query rendered");
   }

   public void prepareStart(ExecuteContext ctx) {
      this.watch.splitTrace("Preparing statement");
   }

   public void prepareEnd(ExecuteContext ctx) {
      this.watch.splitTrace("Statement prepared");
   }

   public void bindStart(ExecuteContext ctx) {
      this.watch.splitTrace("Binding variables");
   }

   public void bindEnd(ExecuteContext ctx) {
      this.watch.splitTrace("Variables bound");
   }

   public void executeStart(ExecuteContext ctx) {
      this.watch.splitTrace("Executing query");
   }

   public void executeEnd(ExecuteContext ctx) {
      this.watch.splitDebug("Query executed");
   }

   public void outStart(ExecuteContext ctx) {
      this.watch.splitDebug("Fetching out values");
   }

   public void outEnd(ExecuteContext ctx) {
      this.watch.splitDebug("Out values fetched");
   }

   public void fetchStart(ExecuteContext ctx) {
      this.watch.splitTrace("Fetching results");
   }

   public void resultStart(ExecuteContext ctx) {
      this.watch.splitTrace("Fetching result");
   }

   public void recordStart(ExecuteContext ctx) {
      this.watch.splitTrace("Fetching record");
   }

   public void recordEnd(ExecuteContext ctx) {
      this.watch.splitTrace("Record fetched");
   }

   public void resultEnd(ExecuteContext ctx) {
      this.watch.splitTrace("Result fetched");
   }

   public void fetchEnd(ExecuteContext ctx) {
      this.watch.splitTrace("Results fetched");
   }

   public void end(ExecuteContext ctx) {
      this.watch.splitDebug("Finishing");
   }

   public void exception(ExecuteContext ctx) {
      this.watch.splitDebug("Exception");
   }

   public void warning(ExecuteContext ctx) {
      this.watch.splitDebug("Warning");
   }
}
