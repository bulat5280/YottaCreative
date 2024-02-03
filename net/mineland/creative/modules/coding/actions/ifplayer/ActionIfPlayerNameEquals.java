package net.mineland.creative.modules.coding.actions.ifplayer;

import java.util.Iterator;
import java.util.List;
import java.util.Objects;
import net.mineland.creative.modules.coding.ChestParser;
import net.mineland.creative.modules.coding.CodeUtils;
import net.mineland.creative.modules.coding.actions.Action;
import net.mineland.creative.modules.coding.actions.ActionIf;
import net.mineland.creative.modules.coding.actions.ActionType;
import net.mineland.creative.modules.coding.activators.Activator;
import net.mineland.creative.modules.coding.events.GameEvent;
import org.bukkit.Material;
import org.bukkit.entity.Entity;
import ua.govnojon.libs.bukkitutil.ItemData;

public class ActionIfPlayerNameEquals extends ActionIf {
   public ActionIfPlayerNameEquals(Activator activator) {
      super(activator);
   }

   public boolean expression(List<Entity> selectedEntities, GameEvent gameEvent) {
      Iterator var3 = selectedEntities.iterator();

      Entity entity;
      List names;
      do {
         if (!var3.hasNext()) {
            return true;
         }

         entity = (Entity)var3.next();
         names = this.getVarsStrings("name", gameEvent, entity);
      } while(this.nameEquals(entity, names));

      return false;
   }

   private boolean nameEquals(Entity entity, List<String> names) {
      String entityName = CodeUtils.getEntityName(entity);
      Iterator var4 = names.iterator();

      String name;
      do {
         if (!var4.hasNext()) {
            return false;
         }

         name = (String)var4.next();
      } while(!Objects.equals(name, entityName));

      return true;
   }

   public ActionType getType() {
      return ActionType.IF_PLAYER_WEARING;
   }

   public Action.Category getCategory() {
      return Action.Category.IF_PLAYER;
   }

   public ItemData getIcon() {
      return new ItemData(Material.NAME_TAG);
   }

   public boolean parseChest(ChestParser chestParser) {
      this.putVar("name", chestParser.getTexts());
      return true;
   }
}
