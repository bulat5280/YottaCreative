package net.mineland.creative.modules.coding.actions;

import java.util.Iterator;
import java.util.List;
import java.util.Objects;
import java.util.concurrent.atomic.AtomicInteger;
import net.mineland.creative.modules.coding.ChestParser;
import net.mineland.creative.modules.coding.activators.Activator;
import net.mineland.creative.modules.coding.events.GameEvent;
import org.bukkit.Material;
import org.bukkit.entity.Entity;
import ua.govnojon.libs.bukkitutil.ItemData;
import ua.govnojon.libs.bukkitutil.chatcomponent.ChatComponentUtil;

public class ActionCallFunction extends Action {
   public ActionCallFunction(Activator activator) {
      super(activator);
   }

   public ActionType getType() {
      return ActionType.CALL_FUNCTION;
   }

   public Action.Category getCategory() {
      return Action.Category.FUNCTION;
   }

   public ItemData getIcon() {
      return new ItemData(Material.LAPIS_ORE);
   }

   public boolean run(List<Entity> selectedEntities, GameEvent gameEvent, int stackCounter, AtomicInteger callCounter) {
      Iterator var5 = selectedEntities.iterator();

      while(var5.hasNext()) {
         Entity entity = (Entity)var5.next();
         String name = this.getVarString("name", gameEvent, entity);
         gameEvent.getPlot().getCodeHandler().getFunctions().stream().filter((functionActivator) -> {
            return Objects.equals(functionActivator.getCustomName(), ChatComponentUtil.removeColors(name));
         }).findAny().ifPresent((function) -> {
            function.execute(gameEvent, selectedEntities, 1, callCounter);
         });
      }

      return true;
   }

   public boolean parseChest(ChestParser chestParser) {
      return false;
   }
}
