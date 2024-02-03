package net.mineland.creative.modules.coding.actions.ifentity;

import java.util.Iterator;
import java.util.List;
import net.mineland.creative.modules.coding.ChestParser;
import net.mineland.creative.modules.coding.actions.Action;
import net.mineland.creative.modules.coding.actions.ActionIf;
import net.mineland.creative.modules.coding.actions.ActionType;
import net.mineland.creative.modules.coding.activators.Activator;
import net.mineland.creative.modules.coding.events.GameEvent;
import org.bukkit.Material;
import org.bukkit.entity.Entity;
import org.bukkit.entity.LivingEntity;
import ua.govnojon.libs.bukkitutil.ItemData;

public class ActionIfEntityIsMob extends ActionIf {
   public ActionIfEntityIsMob(Activator activator) {
      super(activator);
   }

   public boolean expression(List<Entity> selectedEntities, GameEvent gameEvent) {
      Iterator var3 = selectedEntities.iterator();

      Entity entity;
      do {
         if (!var3.hasNext()) {
            return true;
         }

         entity = (Entity)var3.next();
      } while(entity instanceof LivingEntity);

      return false;
   }

   public ActionType getType() {
      return ActionType.IF_ENTITY_IS_MOB;
   }

   public Action.Category getCategory() {
      return Action.Category.IF_ENTITY;
   }

   public ItemData getIcon() {
      return new ItemData(Material.ROTTEN_FLESH);
   }

   public boolean parseChest(ChestParser chestParser) {
      return false;
   }
}
