package org.jooq.util.jaxb;

import java.io.Serializable;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlType;
import javax.xml.bind.annotation.adapters.XmlJavaTypeAdapter;
import org.jooq.util.jaxb.tools.StringAdapter;

@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(
   name = "MatchersSequenceType",
   propOrder = {}
)
public class MatchersSequenceType implements Serializable {
   private static final long serialVersionUID = 390L;
   @XmlJavaTypeAdapter(StringAdapter.class)
   protected String expression;
   protected MatcherRule sequenceIdentifier;

   public String getExpression() {
      return this.expression;
   }

   public void setExpression(String value) {
      this.expression = value;
   }

   public MatcherRule getSequenceIdentifier() {
      return this.sequenceIdentifier;
   }

   public void setSequenceIdentifier(MatcherRule value) {
      this.sequenceIdentifier = value;
   }

   public MatchersSequenceType withExpression(String value) {
      this.setExpression(value);
      return this;
   }

   public MatchersSequenceType withSequenceIdentifier(MatcherRule value) {
      this.setSequenceIdentifier(value);
      return this;
   }
}
