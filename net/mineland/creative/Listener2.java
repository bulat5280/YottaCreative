package net.mineland.creative;

import com.sk89q.worldedit.EditSession;
import com.sk89q.worldedit.MaxChangedBlocksException;
import com.sk89q.worldedit.Vector;
import com.sk89q.worldedit.blocks.BaseBlock;
import net.mineland.core.bukkit.module.Module;
import net.mineland.core.bukkit.modules.user.User;
import net.mineland.creative.modules.coding.CodeUtils;
import net.mineland.creative.modules.coding.ModuleCoding;
import net.mineland.creative.modules.creative.ModuleCreative;
import net.mineland.creative.modules.creative.Plot;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.block.BlockFace;
import org.bukkit.block.Sign;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockPlaceEvent;
import ua.govnojon.libs.bukkitutil.ItemData;
import ua.govnojon.libs.bukkitutil.WorldUtil;
import ua.govnojon.libs.bukkitutil.schedule.Schedule;

public class Listener2 implements Listener {
   private static ModuleCreative moduleCreative = (ModuleCreative)Module.getInstance(ModuleCreative.class);
   private static ModuleCoding moduleCoding = (ModuleCoding)Module.getInstance(ModuleCoding.class);

   private boolean placeCodeBlock(Block block, ItemData stoneBlockMaterial, String signLine) {
      switch(block.getType()) {
      case REDSTONE_BLOCK:
      case DIAMOND_BLOCK:
      case LAPIS_BLOCK:
      case EMERALD_BLOCK:
         if (block.getRelative(BlockFace.DOWN).getData() != 3) {
            return false;
         }
         break;
      default:
         if (block.getRelative(BlockFace.DOWN).getData() != 8) {
            return false;
         }
      }

      Block stoneBlock = block.getRelative(BlockFace.WEST);
      if (stoneBlock.getType() != Material.AIR) {
         Location pos1 = stoneBlock.getLocation();
         Location pos2 = CodeUtils.getLastBlock(pos1).getBlock().getRelative(BlockFace.NORTH).getRelative(BlockFace.UP).getLocation();
         int distance = false;
         byte distance;
         switch(block.getType()) {
         case WOOD:
         case BRICK:
         case OBSIDIAN:
         case RED_NETHER_BRICK:
         case ENDER_STONE:
            distance = 4;
            break;
         default:
            distance = 2;
         }

         Location moveTo = pos2.getBlock().getRelative(BlockFace.WEST, distance).getLocation();
         Plot plot = moduleCreative.getPlotManager().getPlot(moveTo);
         if (plot == null || !plot.inTerritory(moveTo)) {
            return false;
         }

         WorldUtil.moveBlocks(pos1, pos2, BlockFace.WEST, distance, (editSession) -> {
            this.placeStoneBlock(editSession, stoneBlock, stoneBlockMaterial, signLine);
            editSession.flushQueue();
         });
      } else {
         this.placeStoneBlock((EditSession)null, stoneBlock, stoneBlockMaterial, signLine);
      }

      return true;
   }

   private Vector toVector(Location location) {
      return new Vector(location.getX(), location.getY(), location.getZ());
   }

   private void placeStoneBlock(EditSession session, Block block, ItemData stoneBlockMaterial, String signLine) {
      if (session != null) {
         try {
            session.setBlock(this.toVector(block.getLocation()), new BaseBlock(stoneBlockMaterial.getType().ordinal(), stoneBlockMaterial.getData()));
            if (stoneBlockMaterial.getType() == Material.PISTON_BASE) {
               Location endPiston = block.getRelative(BlockFace.WEST, 2).getLocation();
               if (endPiston.getBlock().getType() != Material.AIR) {
                  Location pos1 = endPiston.getBlock().getRelative(BlockFace.EAST).getLocation();
                  Location pos2 = CodeUtils.getLastBlock(pos1).getBlock().getRelative(BlockFace.NORTH).getRelative(BlockFace.UP).getLocation();
                  Location moveTo = pos2.getBlock().getRelative(BlockFace.WEST, 2).getLocation();
                  Plot plot = moduleCreative.getPlotManager().getPlot(moveTo);
                  if (plot.inTerritory(moveTo)) {
                     WorldUtil.moveBlocks(pos1, pos2, BlockFace.WEST, 2, (editSession) -> {
                        try {
                           editSession.setBlock(this.toVector(endPiston), new BaseBlock(Material.PISTON_BASE.ordinal(), 5));
                        } catch (MaxChangedBlocksException var4) {
                           var4.printStackTrace();
                        }

                        editSession.flushQueue();
                     });
                  }
               } else {
                  session.setBlock(this.toVector(endPiston), new BaseBlock(Material.PISTON_BASE.ordinal(), 5));
               }
            }
         } catch (MaxChangedBlocksException var10) {
            var10.printStackTrace();
         }
      } else {
         block.setType(stoneBlockMaterial.getType());
         block.setData((byte)stoneBlockMaterial.getData());
         if (stoneBlockMaterial.getType() == Material.PISTON_BASE) {
            block.getRelative(BlockFace.WEST, 2).setType(Material.PISTON_BASE);
            block.getRelative(BlockFace.WEST, 2).setData((byte)5);
         }
      }

      Schedule.run(() -> {
         Block signBlock = block.getRelative(BlockFace.NORTH_EAST);
         signBlock.setType(Material.WALL_SIGN, false);
         Sign sign = (Sign)signBlock.getState();
         sign.setLine(0, signLine);
         if (stoneBlockMaterial.getType() == Material.EMERALD_ORE) {
            sign.setLine(2, String.valueOf(20));
         }

         sign.update();
      });
   }

   @EventHandler(
      ignoreCancelled = true,
      priority = EventPriority.HIGHEST
   )
   public void on(BlockPlaceEvent event) {
      User user = User.getUser(event.getPlayer());
      Plot plot = moduleCreative.getPlotManager().getCurrentPlot(user);
      if (plot != null) {
         Block block = event.getBlock();
         if (user.getPlayer().getWorld() == moduleCoding.getCodingWorld()) {
            switch(block.getType()) {
            case REDSTONE_BLOCK:
               event.setCancelled(!this.placeCodeBlock(block, new ItemData(Material.REDSTONE_ORE), "lang:coding.sign.world_event"));
            }
         }

      }
   }
}
