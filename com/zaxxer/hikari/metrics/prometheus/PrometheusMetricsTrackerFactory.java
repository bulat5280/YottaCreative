package com.zaxxer.hikari.metrics.prometheus;

import com.zaxxer.hikari.metrics.IMetricsTracker;
import com.zaxxer.hikari.metrics.MetricsTrackerFactory;
import com.zaxxer.hikari.metrics.PoolStats;
import io.prometheus.client.Collector;

public class PrometheusMetricsTrackerFactory implements MetricsTrackerFactory {
   public IMetricsTracker create(String poolName, PoolStats poolStats) {
      Collector collector = (new HikariCPCollector(poolName, poolStats)).register();
      return new PrometheusMetricsTracker(poolName, collector);
   }
}
