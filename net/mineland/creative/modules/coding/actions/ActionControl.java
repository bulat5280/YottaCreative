package net.mineland.creative.modules.coding.actions;

import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;
import net.mineland.creative.modules.coding.Param;
import net.mineland.creative.modules.coding.activators.Activator;
import net.mineland.creative.modules.coding.events.GameEvent;
import org.bukkit.Material;
import org.bukkit.entity.Entity;
import ua.govnojon.libs.bukkitutil.ItemData;

public class ActionControl extends Action {
   private ActionControl.ControlType controlType;

   ActionControl(Activator activator, Param param) {
      super(activator);
      this.controlType = ActionControl.ControlType.valueOf(param.get("controlType").toUpperCase());
   }

   public boolean execute(List<Entity> selectedEntities, GameEvent gameEvent, int stackCounter, AtomicInteger callCounter) {
      return false;
   }

   public boolean run(List<Entity> selectedEntities, GameEvent gameEvent, int stackCounter, AtomicInteger callCounter) {
      return false;
   }

   public ActionType getType() {
      return null;
   }

   public Action.Category getCategory() {
      return null;
   }

   public ItemData getIcon() {
      return new ItemData(Material.COAL_BLOCK);
   }

   public ActionControl.ControlType getControlType() {
      return this.controlType;
   }

   public static enum ControlType {
      END,
      RETURN;
   }
}
