package net.mineland.creative.modules.coding.actions.ifentity;

import java.util.Iterator;
import java.util.List;
import net.mineland.creative.modules.coding.ChestParser;
import net.mineland.creative.modules.coding.CodeUtils;
import net.mineland.creative.modules.coding.actions.Action;
import net.mineland.creative.modules.coding.actions.ActionIf;
import net.mineland.creative.modules.coding.actions.ActionType;
import net.mineland.creative.modules.coding.activators.Activator;
import net.mineland.creative.modules.coding.events.GameEvent;
import org.bukkit.Material;
import org.bukkit.entity.Entity;
import org.bukkit.inventory.ItemStack;
import ua.govnojon.libs.bukkitutil.ItemData;

public class ActionIfEntityIsType extends ActionIf {
   public ActionIfEntityIsType(Activator activator) {
      super(activator);
   }

   public boolean expression(List<Entity> selectedEntities, GameEvent gameEvent) {
      Iterator var3 = selectedEntities.iterator();

      Entity entity;
      List entityTypes;
      do {
         if (!var3.hasNext()) {
            return true;
         }

         entity = (Entity)var3.next();
         ItemStack[] items = (ItemStack[])this.getVar("items", new ItemStack[0], gameEvent, entity);
         entityTypes = CodeUtils.getEntityTypes(items);
      } while(entityTypes.contains(entity.getType()));

      return false;
   }

   public ActionType getType() {
      return ActionType.IF_ENTITY_IS_TYPE;
   }

   public Action.Category getCategory() {
      return Action.Category.IF_ENTITY;
   }

   public ItemData getIcon() {
      return new ItemData(Material.MONSTER_EGG);
   }

   public boolean parseChest(ChestParser chestParser) {
      this.putVar("items", chestParser.getItems(), false);
      return true;
   }
}
