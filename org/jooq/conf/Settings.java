package org.jooq.conf;

import java.io.Serializable;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;

@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(
   name = "Settings",
   propOrder = {}
)
public class Settings extends SettingsBase implements Serializable, Cloneable {
   private static final long serialVersionUID = 390L;
   @XmlElement(
      defaultValue = "true"
   )
   protected Boolean renderCatalog = true;
   @XmlElement(
      defaultValue = "true"
   )
   protected Boolean renderSchema = true;
   protected RenderMapping renderMapping;
   @XmlElement(
      defaultValue = "QUOTED"
   )
   protected RenderNameStyle renderNameStyle;
   @XmlElement(
      defaultValue = "AS_IS"
   )
   protected RenderKeywordStyle renderKeywordStyle;
   @XmlElement(
      defaultValue = "false"
   )
   protected Boolean renderFormatted;
   @XmlElement(
      defaultValue = "false"
   )
   protected Boolean renderScalarSubqueriesForStoredFunctions;
   @XmlElement(
      defaultValue = "DEFAULT"
   )
   protected BackslashEscaping backslashEscaping;
   @XmlElement(
      defaultValue = "INDEXED"
   )
   protected ParamType paramType;
   @XmlElement(
      defaultValue = "PREPARED_STATEMENT"
   )
   protected StatementType statementType;
   @XmlElement(
      defaultValue = "true"
   )
   protected Boolean executeLogging;
   @XmlElement(
      defaultValue = "false"
   )
   protected Boolean executeWithOptimisticLocking;
   @XmlElement(
      defaultValue = "false"
   )
   protected Boolean executeWithOptimisticLockingExcludeUnversioned;
   @XmlElement(
      defaultValue = "true"
   )
   protected Boolean attachRecords;
   @XmlElement(
      defaultValue = "false"
   )
   protected Boolean updatablePrimaryKeys;
   @XmlElement(
      defaultValue = "true"
   )
   protected Boolean reflectionCaching;
   @XmlElement(
      defaultValue = "true"
   )
   protected Boolean fetchWarnings;
   @XmlElement(
      defaultValue = "false"
   )
   protected Boolean returnAllOnUpdatableRecord;
   @XmlElement(
      defaultValue = "true"
   )
   protected Boolean mapJPAAnnotations;
   @XmlElement(
      defaultValue = "0"
   )
   protected Integer queryTimeout;
   @XmlElement(
      defaultValue = "0"
   )
   protected Integer maxRows;
   @XmlElement(
      defaultValue = "0"
   )
   protected Integer fetchSize;
   @XmlElement(
      defaultValue = "true"
   )
   protected Boolean debugInfoOnStackTrace;
   @XmlElement(
      defaultValue = "false"
   )
   protected Boolean inListPadding;

   public Settings() {
      this.renderNameStyle = RenderNameStyle.QUOTED;
      this.renderKeywordStyle = RenderKeywordStyle.AS_IS;
      this.renderFormatted = false;
      this.renderScalarSubqueriesForStoredFunctions = false;
      this.backslashEscaping = BackslashEscaping.DEFAULT;
      this.paramType = ParamType.INDEXED;
      this.statementType = StatementType.PREPARED_STATEMENT;
      this.executeLogging = true;
      this.executeWithOptimisticLocking = false;
      this.executeWithOptimisticLockingExcludeUnversioned = false;
      this.attachRecords = true;
      this.updatablePrimaryKeys = false;
      this.reflectionCaching = true;
      this.fetchWarnings = true;
      this.returnAllOnUpdatableRecord = false;
      this.mapJPAAnnotations = true;
      this.queryTimeout = 0;
      this.maxRows = 0;
      this.fetchSize = 0;
      this.debugInfoOnStackTrace = true;
      this.inListPadding = false;
   }

   public Boolean isRenderCatalog() {
      return this.renderCatalog;
   }

   public void setRenderCatalog(Boolean value) {
      this.renderCatalog = value;
   }

   public Boolean isRenderSchema() {
      return this.renderSchema;
   }

   public void setRenderSchema(Boolean value) {
      this.renderSchema = value;
   }

   public RenderMapping getRenderMapping() {
      return this.renderMapping;
   }

   public void setRenderMapping(RenderMapping value) {
      this.renderMapping = value;
   }

   public RenderNameStyle getRenderNameStyle() {
      return this.renderNameStyle;
   }

   public void setRenderNameStyle(RenderNameStyle value) {
      this.renderNameStyle = value;
   }

   public RenderKeywordStyle getRenderKeywordStyle() {
      return this.renderKeywordStyle;
   }

   public void setRenderKeywordStyle(RenderKeywordStyle value) {
      this.renderKeywordStyle = value;
   }

   public Boolean isRenderFormatted() {
      return this.renderFormatted;
   }

   public void setRenderFormatted(Boolean value) {
      this.renderFormatted = value;
   }

   public Boolean isRenderScalarSubqueriesForStoredFunctions() {
      return this.renderScalarSubqueriesForStoredFunctions;
   }

   public void setRenderScalarSubqueriesForStoredFunctions(Boolean value) {
      this.renderScalarSubqueriesForStoredFunctions = value;
   }

   public BackslashEscaping getBackslashEscaping() {
      return this.backslashEscaping;
   }

   public void setBackslashEscaping(BackslashEscaping value) {
      this.backslashEscaping = value;
   }

   public ParamType getParamType() {
      return this.paramType;
   }

   public void setParamType(ParamType value) {
      this.paramType = value;
   }

   public StatementType getStatementType() {
      return this.statementType;
   }

   public void setStatementType(StatementType value) {
      this.statementType = value;
   }

   public Boolean isExecuteLogging() {
      return this.executeLogging;
   }

   public void setExecuteLogging(Boolean value) {
      this.executeLogging = value;
   }

   public Boolean isExecuteWithOptimisticLocking() {
      return this.executeWithOptimisticLocking;
   }

   public void setExecuteWithOptimisticLocking(Boolean value) {
      this.executeWithOptimisticLocking = value;
   }

   public Boolean isExecuteWithOptimisticLockingExcludeUnversioned() {
      return this.executeWithOptimisticLockingExcludeUnversioned;
   }

   public void setExecuteWithOptimisticLockingExcludeUnversioned(Boolean value) {
      this.executeWithOptimisticLockingExcludeUnversioned = value;
   }

   public Boolean isAttachRecords() {
      return this.attachRecords;
   }

   public void setAttachRecords(Boolean value) {
      this.attachRecords = value;
   }

   public Boolean isUpdatablePrimaryKeys() {
      return this.updatablePrimaryKeys;
   }

   public void setUpdatablePrimaryKeys(Boolean value) {
      this.updatablePrimaryKeys = value;
   }

   public Boolean isReflectionCaching() {
      return this.reflectionCaching;
   }

   public void setReflectionCaching(Boolean value) {
      this.reflectionCaching = value;
   }

   public Boolean isFetchWarnings() {
      return this.fetchWarnings;
   }

   public void setFetchWarnings(Boolean value) {
      this.fetchWarnings = value;
   }

   public Boolean isReturnAllOnUpdatableRecord() {
      return this.returnAllOnUpdatableRecord;
   }

   public void setReturnAllOnUpdatableRecord(Boolean value) {
      this.returnAllOnUpdatableRecord = value;
   }

   public Boolean isMapJPAAnnotations() {
      return this.mapJPAAnnotations;
   }

   public void setMapJPAAnnotations(Boolean value) {
      this.mapJPAAnnotations = value;
   }

   public Integer getQueryTimeout() {
      return this.queryTimeout;
   }

   public void setQueryTimeout(Integer value) {
      this.queryTimeout = value;
   }

   public Integer getMaxRows() {
      return this.maxRows;
   }

   public void setMaxRows(Integer value) {
      this.maxRows = value;
   }

   public Integer getFetchSize() {
      return this.fetchSize;
   }

   public void setFetchSize(Integer value) {
      this.fetchSize = value;
   }

   public Boolean isDebugInfoOnStackTrace() {
      return this.debugInfoOnStackTrace;
   }

   public void setDebugInfoOnStackTrace(Boolean value) {
      this.debugInfoOnStackTrace = value;
   }

   public Boolean isInListPadding() {
      return this.inListPadding;
   }

   public void setInListPadding(Boolean value) {
      this.inListPadding = value;
   }

   public Settings withRenderCatalog(Boolean value) {
      this.setRenderCatalog(value);
      return this;
   }

   public Settings withRenderSchema(Boolean value) {
      this.setRenderSchema(value);
      return this;
   }

   public Settings withRenderMapping(RenderMapping value) {
      this.setRenderMapping(value);
      return this;
   }

   public Settings withRenderNameStyle(RenderNameStyle value) {
      this.setRenderNameStyle(value);
      return this;
   }

   public Settings withRenderKeywordStyle(RenderKeywordStyle value) {
      this.setRenderKeywordStyle(value);
      return this;
   }

   public Settings withRenderFormatted(Boolean value) {
      this.setRenderFormatted(value);
      return this;
   }

   public Settings withRenderScalarSubqueriesForStoredFunctions(Boolean value) {
      this.setRenderScalarSubqueriesForStoredFunctions(value);
      return this;
   }

   public Settings withBackslashEscaping(BackslashEscaping value) {
      this.setBackslashEscaping(value);
      return this;
   }

   public Settings withParamType(ParamType value) {
      this.setParamType(value);
      return this;
   }

   public Settings withStatementType(StatementType value) {
      this.setStatementType(value);
      return this;
   }

   public Settings withExecuteLogging(Boolean value) {
      this.setExecuteLogging(value);
      return this;
   }

   public Settings withExecuteWithOptimisticLocking(Boolean value) {
      this.setExecuteWithOptimisticLocking(value);
      return this;
   }

   public Settings withExecuteWithOptimisticLockingExcludeUnversioned(Boolean value) {
      this.setExecuteWithOptimisticLockingExcludeUnversioned(value);
      return this;
   }

   public Settings withAttachRecords(Boolean value) {
      this.setAttachRecords(value);
      return this;
   }

   public Settings withUpdatablePrimaryKeys(Boolean value) {
      this.setUpdatablePrimaryKeys(value);
      return this;
   }

   public Settings withReflectionCaching(Boolean value) {
      this.setReflectionCaching(value);
      return this;
   }

   public Settings withFetchWarnings(Boolean value) {
      this.setFetchWarnings(value);
      return this;
   }

   public Settings withReturnAllOnUpdatableRecord(Boolean value) {
      this.setReturnAllOnUpdatableRecord(value);
      return this;
   }

   public Settings withMapJPAAnnotations(Boolean value) {
      this.setMapJPAAnnotations(value);
      return this;
   }

   public Settings withQueryTimeout(Integer value) {
      this.setQueryTimeout(value);
      return this;
   }

   public Settings withMaxRows(Integer value) {
      this.setMaxRows(value);
      return this;
   }

   public Settings withFetchSize(Integer value) {
      this.setFetchSize(value);
      return this;
   }

   public Settings withDebugInfoOnStackTrace(Boolean value) {
      this.setDebugInfoOnStackTrace(value);
      return this;
   }

   public Settings withInListPadding(Boolean value) {
      this.setInListPadding(value);
      return this;
   }
}
