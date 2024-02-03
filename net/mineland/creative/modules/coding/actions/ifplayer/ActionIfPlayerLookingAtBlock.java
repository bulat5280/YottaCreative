package net.mineland.creative.modules.coding.actions.ifplayer;

import java.util.Iterator;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;
import net.mineland.creative.modules.coding.ChestParser;
import net.mineland.creative.modules.coding.CodeUtils;
import net.mineland.creative.modules.coding.actions.Action;
import net.mineland.creative.modules.coding.actions.ActionIf;
import net.mineland.creative.modules.coding.actions.ActionType;
import net.mineland.creative.modules.coding.activators.Activator;
import net.mineland.creative.modules.coding.events.GameEvent;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.util.BlockVector;
import ua.govnojon.libs.bukkitutil.ItemData;

public class ActionIfPlayerLookingAtBlock extends ActionIf {
   public ActionIfPlayerLookingAtBlock(Activator activator) {
      super(activator);
   }

   public boolean expression(List<Entity> selectedEntities, GameEvent gameEvent) {
      Iterator var3 = selectedEntities.iterator();

      while(var3.hasNext()) {
         Entity entity = (Entity)var3.next();
         Player player = (Player)entity;
         Block targetBlock = player.getLocation().getBlock();

         try {
            targetBlock = player.getTargetBlock((Set)null, 120);
         } catch (IllegalStateException var12) {
         }

         ItemData currentBlock = new ItemData(targetBlock.getType(), targetBlock.getData());
         List<Location> locations = this.getVarsLocations("locations", gameEvent, entity);
         List<ItemData> blocks = this.getVars("blocks", gameEvent, entity);
         List<BlockVector> vectors = (List)locations.stream().map((location) -> {
            return location.toVector().toBlockVector();
         }).collect(Collectors.toList());
         BlockVector blockVector = targetBlock.getLocation().toVector().toBlockVector();
         if (!vectors.contains(blockVector) && !blocks.contains(currentBlock)) {
            return false;
         }
      }

      return true;
   }

   public ActionType getType() {
      return ActionType.IF_PLAYER_LOOKING_AT;
   }

   public Action.Category getCategory() {
      return Action.Category.IF_PLAYER;
   }

   public ItemData getIcon() {
      return new ItemData(Material.IRON_ORE);
   }

   public boolean parseChest(ChestParser chestParser) {
      this.putVar("blocks", CodeUtils.sortByBlock(chestParser.getItems()));
      this.putVar("locations", chestParser.getLocations());
      return true;
   }
}
