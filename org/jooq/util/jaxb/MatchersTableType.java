package org.jooq.util.jaxb;

import java.io.Serializable;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlType;
import javax.xml.bind.annotation.adapters.XmlJavaTypeAdapter;
import org.jooq.util.jaxb.tools.StringAdapter;

@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(
   name = "MatchersTableType",
   propOrder = {}
)
public class MatchersTableType implements Serializable {
   private static final long serialVersionUID = 390L;
   @XmlJavaTypeAdapter(StringAdapter.class)
   protected String expression;
   protected MatcherRule tableClass;
   protected MatcherRule tableIdentifier;
   @XmlJavaTypeAdapter(StringAdapter.class)
   protected String tableImplements;
   protected MatcherRule recordClass;
   @XmlJavaTypeAdapter(StringAdapter.class)
   protected String recordImplements;
   protected MatcherRule interfaceClass;
   @XmlJavaTypeAdapter(StringAdapter.class)
   protected String interfaceImplements;
   protected MatcherRule daoClass;
   @XmlJavaTypeAdapter(StringAdapter.class)
   protected String daoImplements;
   protected MatcherRule pojoClass;
   @XmlJavaTypeAdapter(StringAdapter.class)
   protected String pojoExtends;
   @XmlJavaTypeAdapter(StringAdapter.class)
   protected String pojoImplements;

   public String getExpression() {
      return this.expression;
   }

   public void setExpression(String value) {
      this.expression = value;
   }

   public MatcherRule getTableClass() {
      return this.tableClass;
   }

   public void setTableClass(MatcherRule value) {
      this.tableClass = value;
   }

   public MatcherRule getTableIdentifier() {
      return this.tableIdentifier;
   }

   public void setTableIdentifier(MatcherRule value) {
      this.tableIdentifier = value;
   }

   public String getTableImplements() {
      return this.tableImplements;
   }

   public void setTableImplements(String value) {
      this.tableImplements = value;
   }

   public MatcherRule getRecordClass() {
      return this.recordClass;
   }

   public void setRecordClass(MatcherRule value) {
      this.recordClass = value;
   }

   public String getRecordImplements() {
      return this.recordImplements;
   }

   public void setRecordImplements(String value) {
      this.recordImplements = value;
   }

   public MatcherRule getInterfaceClass() {
      return this.interfaceClass;
   }

   public void setInterfaceClass(MatcherRule value) {
      this.interfaceClass = value;
   }

   public String getInterfaceImplements() {
      return this.interfaceImplements;
   }

   public void setInterfaceImplements(String value) {
      this.interfaceImplements = value;
   }

   public MatcherRule getDaoClass() {
      return this.daoClass;
   }

   public void setDaoClass(MatcherRule value) {
      this.daoClass = value;
   }

   public String getDaoImplements() {
      return this.daoImplements;
   }

   public void setDaoImplements(String value) {
      this.daoImplements = value;
   }

   public MatcherRule getPojoClass() {
      return this.pojoClass;
   }

   public void setPojoClass(MatcherRule value) {
      this.pojoClass = value;
   }

   public String getPojoExtends() {
      return this.pojoExtends;
   }

   public void setPojoExtends(String value) {
      this.pojoExtends = value;
   }

   public String getPojoImplements() {
      return this.pojoImplements;
   }

   public void setPojoImplements(String value) {
      this.pojoImplements = value;
   }

   public MatchersTableType withExpression(String value) {
      this.setExpression(value);
      return this;
   }

   public MatchersTableType withTableClass(MatcherRule value) {
      this.setTableClass(value);
      return this;
   }

   public MatchersTableType withTableIdentifier(MatcherRule value) {
      this.setTableIdentifier(value);
      return this;
   }

   public MatchersTableType withTableImplements(String value) {
      this.setTableImplements(value);
      return this;
   }

   public MatchersTableType withRecordClass(MatcherRule value) {
      this.setRecordClass(value);
      return this;
   }

   public MatchersTableType withRecordImplements(String value) {
      this.setRecordImplements(value);
      return this;
   }

   public MatchersTableType withInterfaceClass(MatcherRule value) {
      this.setInterfaceClass(value);
      return this;
   }

   public MatchersTableType withInterfaceImplements(String value) {
      this.setInterfaceImplements(value);
      return this;
   }

   public MatchersTableType withDaoClass(MatcherRule value) {
      this.setDaoClass(value);
      return this;
   }

   public MatchersTableType withDaoImplements(String value) {
      this.setDaoImplements(value);
      return this;
   }

   public MatchersTableType withPojoClass(MatcherRule value) {
      this.setPojoClass(value);
      return this;
   }

   public MatchersTableType withPojoExtends(String value) {
      this.setPojoExtends(value);
      return this;
   }

   public MatchersTableType withPojoImplements(String value) {
      this.setPojoImplements(value);
      return this;
   }
}
