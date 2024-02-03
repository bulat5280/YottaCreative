package net.mineland.creative.modules.coding.actions.ifplayer;

import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.stream.Collectors;
import net.mineland.core.bukkit.modules.message.Message;
import net.mineland.core.bukkit.modules.user.User;
import net.mineland.creative.modules.coding.ChestParser;
import net.mineland.creative.modules.coding.CodeUtils;
import net.mineland.creative.modules.coding.actions.Action;
import net.mineland.creative.modules.coding.actions.ActionIf;
import net.mineland.creative.modules.coding.actions.ActionType;
import net.mineland.creative.modules.coding.activators.Activator;
import net.mineland.creative.modules.coding.events.BlockEvent;
import net.mineland.creative.modules.coding.events.GameEvent;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.entity.Entity;
import org.bukkit.util.BlockVector;
import ua.govnojon.libs.bukkitutil.ItemData;

public class ActionIfPlayerBlockEquals extends ActionIf {
   public ActionIfPlayerBlockEquals(Activator activator) {
      super(activator);
   }

   public boolean expression(List<Entity> selectedEntities, GameEvent gameEvent) {
      if (gameEvent instanceof BlockEvent) {
         BlockEvent blockEvent = (BlockEvent)gameEvent;
         Block targetBlock = blockEvent.getBlock();
         if (targetBlock == null) {
            return true;
         } else {
            Iterator var5 = selectedEntities.iterator();

            ItemData blockData;
            BlockVector blockVector;
            List blocks;
            List vectors;
            do {
               if (!var5.hasNext()) {
                  return true;
               }

               Entity entity = (Entity)var5.next();
               blockData = new ItemData(targetBlock.getType());
               blockVector = targetBlock.getLocation().toVector().toBlockVector();
               List<Location> locations = this.getVarsLocations("locations", gameEvent, entity);
               blocks = this.getVars("blocks", gameEvent, entity);
               vectors = (List)locations.stream().map((location) -> {
                  return location.toVector().toBlockVector();
               }).collect(Collectors.toList());
            } while(blocks.contains(blockData) || vectors.contains(blockVector));

            return false;
         }
      } else {
         Collection<User> receivers = gameEvent.getPlot().getOnlinePlayers();
         Message condition = new Message("creative.condition.ifplayerblockequals", new String[0]);
         receivers.forEach((user) -> {
            user.sendMessage("creative.несовместимое_событие", "{condition}", condition.translate(user.getLang()));
         });
         return false;
      }
   }

   public ActionType getType() {
      return ActionType.IF_PLAYER_BLOCK_EQUALS;
   }

   public Action.Category getCategory() {
      return Action.Category.IF_PLAYER;
   }

   public ItemData getIcon() {
      return new ItemData(Material.GRASS);
   }

   public boolean parseChest(ChestParser chestParser) {
      this.putVar("locations", chestParser.getLocations());
      this.putVar("blocks", CodeUtils.sortByBlock(chestParser.getItems()));
      return true;
   }
}
