package org.jooq.util.jaxb;

import java.io.Serializable;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlType;
import javax.xml.bind.annotation.adapters.XmlJavaTypeAdapter;
import org.jooq.util.jaxb.tools.StringAdapter;

@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(
   name = "ForcedType",
   propOrder = {}
)
public class ForcedType implements Serializable {
   private static final long serialVersionUID = 390L;
   @XmlJavaTypeAdapter(StringAdapter.class)
   protected String name;
   @XmlJavaTypeAdapter(StringAdapter.class)
   protected String userType;
   @XmlJavaTypeAdapter(StringAdapter.class)
   protected String converter;
   @XmlJavaTypeAdapter(StringAdapter.class)
   protected String binding;
   @XmlJavaTypeAdapter(StringAdapter.class)
   protected String expression;
   @XmlJavaTypeAdapter(StringAdapter.class)
   protected String expressions;
   @XmlJavaTypeAdapter(StringAdapter.class)
   protected String types;

   public String getName() {
      return this.name;
   }

   public void setName(String value) {
      this.name = value;
   }

   public String getUserType() {
      return this.userType;
   }

   public void setUserType(String value) {
      this.userType = value;
   }

   public String getConverter() {
      return this.converter;
   }

   public void setConverter(String value) {
      this.converter = value;
   }

   public String getBinding() {
      return this.binding;
   }

   public void setBinding(String value) {
      this.binding = value;
   }

   public String getExpression() {
      return this.expression;
   }

   public void setExpression(String value) {
      this.expression = value;
   }

   public String getExpressions() {
      return this.expressions;
   }

   public void setExpressions(String value) {
      this.expressions = value;
   }

   public String getTypes() {
      return this.types;
   }

   public void setTypes(String value) {
      this.types = value;
   }

   public ForcedType withName(String value) {
      this.setName(value);
      return this;
   }

   public ForcedType withUserType(String value) {
      this.setUserType(value);
      return this;
   }

   public ForcedType withConverter(String value) {
      this.setConverter(value);
      return this;
   }

   public ForcedType withBinding(String value) {
      this.setBinding(value);
      return this;
   }

   public ForcedType withExpression(String value) {
      this.setExpression(value);
      return this;
   }

   public ForcedType withExpressions(String value) {
      this.setExpressions(value);
      return this;
   }

   public ForcedType withTypes(String value) {
      this.setTypes(value);
      return this;
   }
}
