package net.mineland.creative.modules.coding.actions.select;

import java.util.ArrayList;
import java.util.List;
import net.mineland.creative.modules.coding.ChestParser;
import net.mineland.creative.modules.coding.actions.ActionSelect;
import net.mineland.creative.modules.coding.actions.ActionType;
import net.mineland.creative.modules.coding.activators.Activator;
import net.mineland.creative.modules.coding.events.GameEvent;
import org.bukkit.Material;
import org.bukkit.entity.Entity;
import ua.govnojon.libs.bukkitutil.ItemData;
import ua.govnojon.libs.myjava.RandomUtil;

public class ActionSelectFilterSelectionRandomly extends ActionSelect {
   public ActionSelectFilterSelectionRandomly(Activator activator) {
      super(activator);
   }

   public List<Entity> execute(GameEvent gameEvent) {
      if (this.getActivator() == null) {
         return new ArrayList();
      } else {
         List<Entity> selectedEntities = this.getActivator().getSelectedEntities();
         if (selectedEntities.size() <= 0) {
            return new ArrayList();
         } else {
            int count = this.getVar("count", 1, gameEvent, (Entity)selectedEntities.get(0));
            if (count > selectedEntities.size()) {
               return selectedEntities;
            } else {
               ArrayList entities = new ArrayList(selectedEntities);

               while(entities.size() != count) {
                  Entity entity = (Entity)RandomUtil.getRandomObject((List)entities);
                  entities.remove(entity);
               }

               return entities;
            }
         }
      }
   }

   public ActionType getType() {
      return ActionType.SELECT_FILTER_SELECTION_RANDOMLY;
   }

   public ItemData getIcon() {
      return new ItemData(Material.COMMAND_MINECART);
   }

   public boolean parseChest(ChestParser chestParser) {
      this.putVar("count", chestParser.getNumber(0));
      return true;
   }
}
