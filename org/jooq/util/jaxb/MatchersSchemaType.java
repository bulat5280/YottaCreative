package org.jooq.util.jaxb;

import java.io.Serializable;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlType;
import javax.xml.bind.annotation.adapters.XmlJavaTypeAdapter;
import org.jooq.util.jaxb.tools.StringAdapter;

@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(
   name = "MatchersSchemaType",
   propOrder = {}
)
public class MatchersSchemaType implements Serializable {
   private static final long serialVersionUID = 390L;
   @XmlJavaTypeAdapter(StringAdapter.class)
   protected String expression;
   protected MatcherRule schemaClass;
   protected MatcherRule schemaIdentifier;
   @XmlJavaTypeAdapter(StringAdapter.class)
   protected String schemaImplements;

   public String getExpression() {
      return this.expression;
   }

   public void setExpression(String value) {
      this.expression = value;
   }

   public MatcherRule getSchemaClass() {
      return this.schemaClass;
   }

   public void setSchemaClass(MatcherRule value) {
      this.schemaClass = value;
   }

   public MatcherRule getSchemaIdentifier() {
      return this.schemaIdentifier;
   }

   public void setSchemaIdentifier(MatcherRule value) {
      this.schemaIdentifier = value;
   }

   public String getSchemaImplements() {
      return this.schemaImplements;
   }

   public void setSchemaImplements(String value) {
      this.schemaImplements = value;
   }

   public MatchersSchemaType withExpression(String value) {
      this.setExpression(value);
      return this;
   }

   public MatchersSchemaType withSchemaClass(MatcherRule value) {
      this.setSchemaClass(value);
      return this;
   }

   public MatchersSchemaType withSchemaIdentifier(MatcherRule value) {
      this.setSchemaIdentifier(value);
      return this;
   }

   public MatchersSchemaType withSchemaImplements(String value) {
      this.setSchemaImplements(value);
      return this;
   }
}
