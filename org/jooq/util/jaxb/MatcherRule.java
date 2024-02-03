package org.jooq.util.jaxb;

import java.io.Serializable;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;
import javax.xml.bind.annotation.adapters.XmlJavaTypeAdapter;
import org.jooq.util.jaxb.tools.StringAdapter;

@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(
   name = "MatcherRule",
   propOrder = {}
)
public class MatcherRule implements Serializable {
   private static final long serialVersionUID = 390L;
   protected MatcherTransformType transform;
   @XmlElement(
      required = true
   )
   @XmlJavaTypeAdapter(StringAdapter.class)
   protected String expression;

   public MatcherTransformType getTransform() {
      return this.transform;
   }

   public void setTransform(MatcherTransformType value) {
      this.transform = value;
   }

   public String getExpression() {
      return this.expression;
   }

   public void setExpression(String value) {
      this.expression = value;
   }

   public MatcherRule withTransform(MatcherTransformType value) {
      this.setTransform(value);
      return this;
   }

   public MatcherRule withExpression(String value) {
      this.setExpression(value);
      return this;
   }
}
