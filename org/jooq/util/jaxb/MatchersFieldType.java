package org.jooq.util.jaxb;

import java.io.Serializable;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlType;
import javax.xml.bind.annotation.adapters.XmlJavaTypeAdapter;
import org.jooq.util.jaxb.tools.StringAdapter;

@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(
   name = "MatchersFieldType",
   propOrder = {}
)
public class MatchersFieldType implements Serializable {
   private static final long serialVersionUID = 390L;
   @XmlJavaTypeAdapter(StringAdapter.class)
   protected String expression;
   protected MatcherRule fieldIdentifier;
   protected MatcherRule fieldMember;
   protected MatcherRule fieldSetter;
   protected MatcherRule fieldGetter;

   public String getExpression() {
      return this.expression;
   }

   public void setExpression(String value) {
      this.expression = value;
   }

   public MatcherRule getFieldIdentifier() {
      return this.fieldIdentifier;
   }

   public void setFieldIdentifier(MatcherRule value) {
      this.fieldIdentifier = value;
   }

   public MatcherRule getFieldMember() {
      return this.fieldMember;
   }

   public void setFieldMember(MatcherRule value) {
      this.fieldMember = value;
   }

   public MatcherRule getFieldSetter() {
      return this.fieldSetter;
   }

   public void setFieldSetter(MatcherRule value) {
      this.fieldSetter = value;
   }

   public MatcherRule getFieldGetter() {
      return this.fieldGetter;
   }

   public void setFieldGetter(MatcherRule value) {
      this.fieldGetter = value;
   }

   public MatchersFieldType withExpression(String value) {
      this.setExpression(value);
      return this;
   }

   public MatchersFieldType withFieldIdentifier(MatcherRule value) {
      this.setFieldIdentifier(value);
      return this;
   }

   public MatchersFieldType withFieldMember(MatcherRule value) {
      this.setFieldMember(value);
      return this;
   }

   public MatchersFieldType withFieldSetter(MatcherRule value) {
      this.setFieldSetter(value);
      return this;
   }

   public MatchersFieldType withFieldGetter(MatcherRule value) {
      this.setFieldGetter(value);
      return this;
   }
}
