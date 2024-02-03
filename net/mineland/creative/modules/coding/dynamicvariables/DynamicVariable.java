package net.mineland.creative.modules.coding.dynamicvariables;

import java.util.Objects;
import net.mineland.creative.modules.creative.Plot;
import ua.govnojon.libs.bukkitutil.chatcomponent.ChatComponentUtil;

public class DynamicVariable {
   private String name;
   private Object value;
   private boolean save;

   public DynamicVariable(String name) {
      this(name, (Object)null);
   }

   public DynamicVariable(String name, Object value) {
      this(name, value, false);
   }

   public DynamicVariable(String name, Object value, boolean save) {
      this.name = ChatComponentUtil.removeColors(name);
      this.value = value;
      this.save = save;
   }

   public String getName() {
      return this.name;
   }

   public void setName(String name) {
      this.name = ChatComponentUtil.removeColors(name);
   }

   public void setValue(Plot plot, Object value) {
      this.setValue(plot, value, this.save);
   }

   public void setValue(Plot plot, Object value, boolean save) {
      this.value = value;
      this.save = save;
      plot.getCodeHandler().getDynamicVariables().put(this.getName(), new DynamicVariable(this.getName(), value, save));
   }

   public Object getValue(Plot plot) {
      DynamicVariable dynamicVariable = (DynamicVariable)plot.getCodeHandler().getDynamicVariables().get(this.getName());
      return dynamicVariable != null ? dynamicVariable.value : null;
   }

   public boolean isSave() {
      return this.save;
   }

   public void setSave(boolean save) {
      this.save = save;
   }

   public boolean equals(Object o) {
      if (this == o) {
         return true;
      } else if (o != null && this.getClass() == o.getClass()) {
         DynamicVariable that = (DynamicVariable)o;
         return Objects.equals(this.getName(), that.getName()) && Objects.equals(this.value, that.value);
      } else {
         return false;
      }
   }

   public int hashCode() {
      return Objects.hash(new Object[]{this.getName(), this.value});
   }
}
