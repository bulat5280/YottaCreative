package org.jooq.util.jaxb;

import java.io.Serializable;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlType;
import javax.xml.bind.annotation.adapters.XmlJavaTypeAdapter;
import org.jooq.util.jaxb.tools.StringAdapter;

@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(
   name = "MatchersRoutineType",
   propOrder = {}
)
public class MatchersRoutineType implements Serializable {
   private static final long serialVersionUID = 390L;
   @XmlJavaTypeAdapter(StringAdapter.class)
   protected String expression;
   protected MatcherRule routineClass;
   protected MatcherRule routineMethod;
   @XmlJavaTypeAdapter(StringAdapter.class)
   protected String routineImplements;

   public String getExpression() {
      return this.expression;
   }

   public void setExpression(String value) {
      this.expression = value;
   }

   public MatcherRule getRoutineClass() {
      return this.routineClass;
   }

   public void setRoutineClass(MatcherRule value) {
      this.routineClass = value;
   }

   public MatcherRule getRoutineMethod() {
      return this.routineMethod;
   }

   public void setRoutineMethod(MatcherRule value) {
      this.routineMethod = value;
   }

   public String getRoutineImplements() {
      return this.routineImplements;
   }

   public void setRoutineImplements(String value) {
      this.routineImplements = value;
   }

   public MatchersRoutineType withExpression(String value) {
      this.setExpression(value);
      return this;
   }

   public MatchersRoutineType withRoutineClass(MatcherRule value) {
      this.setRoutineClass(value);
      return this;
   }

   public MatchersRoutineType withRoutineMethod(MatcherRule value) {
      this.setRoutineMethod(value);
      return this;
   }

   public MatchersRoutineType withRoutineImplements(String value) {
      this.setRoutineImplements(value);
      return this;
   }
}
