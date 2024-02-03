package com.zaxxer.hikari.metrics.prometheus;

import com.zaxxer.hikari.metrics.IMetricsTracker;
import io.prometheus.client.Collector;
import io.prometheus.client.CollectorRegistry;
import io.prometheus.client.Counter;
import io.prometheus.client.Summary;
import io.prometheus.client.Counter.Builder;
import io.prometheus.client.Counter.Child;

class PrometheusMetricsTracker implements IMetricsTracker {
   private final Child connectionTimeoutCounter;
   private final io.prometheus.client.Summary.Child elapsedAcquiredSummary;
   private final io.prometheus.client.Summary.Child elapsedBorrowedSummary;
   private final io.prometheus.client.Summary.Child elapsedCreationSummary;
   private final Counter ctCounter;
   private final Summary eaSummary;
   private final Summary ebSummary;
   private final Summary ecSummary;
   private final Collector collector;

   PrometheusMetricsTracker(String poolName, Collector collector) {
      this.collector = collector;
      this.ctCounter = (Counter)((Builder)((Builder)((Builder)Counter.build().name("hikaricp_connection_timeout_count")).labelNames(new String[]{"pool"})).help("Connection timeout count")).register();
      this.connectionTimeoutCounter = (Child)this.ctCounter.labels(new String[]{poolName});
      this.eaSummary = (Summary)((io.prometheus.client.Summary.Builder)((io.prometheus.client.Summary.Builder)((io.prometheus.client.Summary.Builder)Summary.build().name("hikaricp_connection_acquired_nanos")).labelNames(new String[]{"pool"})).help("Connection acquired time (ns)")).register();
      this.elapsedAcquiredSummary = (io.prometheus.client.Summary.Child)this.eaSummary.labels(new String[]{poolName});
      this.ebSummary = (Summary)((io.prometheus.client.Summary.Builder)((io.prometheus.client.Summary.Builder)((io.prometheus.client.Summary.Builder)Summary.build().name("hikaricp_connection_usage_millis")).labelNames(new String[]{"pool"})).help("Connection usage (ms)")).register();
      this.elapsedBorrowedSummary = (io.prometheus.client.Summary.Child)this.ebSummary.labels(new String[]{poolName});
      this.ecSummary = (Summary)((io.prometheus.client.Summary.Builder)((io.prometheus.client.Summary.Builder)((io.prometheus.client.Summary.Builder)Summary.build().name("hikaricp_connection_creation_millis")).labelNames(new String[]{"pool"})).help("Connection creation (ms)")).register();
      this.elapsedCreationSummary = (io.prometheus.client.Summary.Child)this.ecSummary.labels(new String[]{poolName});
   }

   public void close() {
      CollectorRegistry.defaultRegistry.unregister(this.ctCounter);
      CollectorRegistry.defaultRegistry.unregister(this.eaSummary);
      CollectorRegistry.defaultRegistry.unregister(this.ebSummary);
      CollectorRegistry.defaultRegistry.unregister(this.ecSummary);
      CollectorRegistry.defaultRegistry.unregister(this.collector);
   }

   public void recordConnectionAcquiredNanos(long elapsedAcquiredNanos) {
      this.elapsedAcquiredSummary.observe((double)elapsedAcquiredNanos);
   }

   public void recordConnectionUsageMillis(long elapsedBorrowedMillis) {
      this.elapsedBorrowedSummary.observe((double)elapsedBorrowedMillis);
   }

   public void recordConnectionCreatedMillis(long connectionCreatedMillis) {
      this.elapsedCreationSummary.observe((double)connectionCreatedMillis);
   }

   public void recordConnectionTimeout() {
      this.connectionTimeoutCounter.inc();
   }
}
