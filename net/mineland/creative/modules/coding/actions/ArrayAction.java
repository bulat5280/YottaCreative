package net.mineland.creative.modules.coding.actions;

import java.util.LinkedList;
import java.util.List;
import net.mineland.creative.modules.coding.activators.Activator;

public abstract class ArrayAction extends Action {
   private List<Action> actions = new LinkedList();

   protected ArrayAction(Activator activator) {
      super(activator);
   }

   public List<Action> getActions() {
      return this.actions;
   }

   public void setActions(List<Action> actions) {
      this.actions = actions;
   }
}
