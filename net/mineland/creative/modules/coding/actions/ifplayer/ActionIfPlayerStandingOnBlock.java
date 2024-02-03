package net.mineland.creative.modules.coding.actions.ifplayer;

import java.util.Iterator;
import java.util.List;
import java.util.stream.Collectors;
import net.mineland.creative.modules.coding.CodeUtils;
import net.mineland.creative.modules.coding.actions.Action;
import net.mineland.creative.modules.coding.actions.ActionIf;
import net.mineland.creative.modules.coding.actions.ActionType;
import net.mineland.creative.modules.coding.activators.Activator;
import net.mineland.creative.modules.coding.events.GameEvent;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.block.BlockFace;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.util.BlockVector;
import ua.govnojon.libs.bukkitutil.ItemData;

public class ActionIfPlayerStandingOnBlock extends ActionIf {
   public ActionIfPlayerStandingOnBlock(Activator activator) {
      super(activator);
   }

   public boolean expression(List<Entity> selectedEntities, GameEvent gameEvent) {
      Iterator var3 = selectedEntities.iterator();

      ItemData blockData;
      BlockVector blockVector;
      List blocks;
      List vectors;
      do {
         if (!var3.hasNext()) {
            return true;
         }

         Entity entity = (Entity)var3.next();
         Player player = (Player)entity;
         Block targetBlock = player.getLocation().getBlock().getRelative(BlockFace.DOWN);
         blockData = new ItemData(targetBlock.getType(), targetBlock.getData());
         blockVector = targetBlock.getLocation().toVector().toBlockVector();
         List<Location> locations = this.getVarsLocations("locations", gameEvent, entity);
         blocks = this.getVars("blocks", gameEvent, entity);
         vectors = (List)locations.stream().map((location) -> {
            return location.toVector().toBlockVector();
         }).collect(Collectors.toList());
      } while(blocks.contains(blockData) || vectors.contains(blockVector));

      return false;
   }

   public ActionType getType() {
      return ActionType.IF_PLAYER_STANDING_ON;
   }

   public Action.Category getCategory() {
      return Action.Category.IF_PLAYER;
   }

   public ItemData getIcon() {
      return new ItemData(Material.GOLD_ORE);
   }

   public boolean parseChest(ItemStack[] itemStacks) {
      this.putVar("locations", CodeUtils.sortByType(itemStacks, Material.PAPER, Material.APPLE));
      this.putVar("blocks", CodeUtils.sortByBlock(itemStacks));
      return true;
   }
}
