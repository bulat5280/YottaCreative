package com.p6spy.engine.outage;

import com.p6spy.engine.spy.P6ModuleManager;
import com.p6spy.engine.spy.option.P6OptionsRepository;
import java.util.HashMap;
import java.util.Map;
import javax.management.StandardMBean;

public class P6OutageOptions extends StandardMBean implements P6OutageLoadableOptions {
   public static final String OUTAGEDETECTIONINTERVAL = "outagedetectioninterval";
   public static final String OUTAGEDETECTION = "outagedetection";
   public static final Map<String, String> defaults = new HashMap();
   private final P6OptionsRepository optionsRepository;

   public P6OutageOptions(P6OptionsRepository optionsRepository) {
      super(P6OutageOptionsMBean.class, false);
      this.optionsRepository = optionsRepository;
   }

   public Map<String, String> getDefaults() {
      return defaults;
   }

   public void load(Map<String, String> options) {
      this.setOutageDetection((String)options.get("outagedetection"));
      this.setOutageDetectionInterval((String)options.get("outagedetectioninterval"));
   }

   public static P6OutageLoadableOptions getActiveInstance() {
      return (P6OutageLoadableOptions)P6ModuleManager.getInstance().getOptions(P6OutageOptions.class);
   }

   public boolean getOutageDetection() {
      return (Boolean)this.optionsRepository.get(Boolean.class, "outagedetection");
   }

   public void setOutageDetection(String outagedetection) {
      this.optionsRepository.set(Boolean.class, "outagedetection", outagedetection);
   }

   public void setOutageDetection(boolean outagedetection) {
      this.optionsRepository.set(Boolean.class, "outagedetection", outagedetection);
   }

   public long getOutageDetectionInterval() {
      return (Long)this.optionsRepository.get(Long.class, "outagedetectioninterval");
   }

   public long getOutageDetectionIntervalMS() {
      return this.getOutageDetectionInterval() * 1000L;
   }

   public void setOutageDetectionInterval(String outagedetectioninterval) {
      this.optionsRepository.set(Long.class, "outagedetectioninterval", outagedetectioninterval);
   }

   public void setOutageDetectionInterval(long outagedetectioninterval) {
      this.optionsRepository.set(Long.class, "outagedetectioninterval", outagedetectioninterval);
   }

   static {
      defaults.put("outagedetection", Boolean.toString(false));
      defaults.put("outagedetectioninterval", Long.toString(30L));
   }
}
