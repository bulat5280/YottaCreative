package net.mineland.creative.modules.coding.actions.game.entityspawning;

import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;
import net.mineland.creative.modules.coding.ChestParser;
import net.mineland.creative.modules.coding.actions.Action;
import net.mineland.creative.modules.coding.actions.ActionType;
import net.mineland.creative.modules.coding.activators.Activator;
import net.mineland.creative.modules.coding.events.GameEvent;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.TreeSpecies;
import org.bukkit.entity.Boat;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Minecart;
import org.bukkit.inventory.ItemStack;
import org.bukkit.material.MaterialData;
import ua.govnojon.libs.bukkitutil.ItemData;
import ua.govnojon.libs.bukkitutil.ItemStackUtil;

public class ActionSpawnVehicle extends Action {
   public ActionSpawnVehicle(Activator activator) {
      super(activator);
   }

   public ActionType getType() {
      return ActionType.GAME_SPAWN_VEHICLE;
   }

   public Action.Category getCategory() {
      return Action.Category.GAME_ENTITY_SPAWNING;
   }

   public ItemData getIcon() {
      return new ItemData(Material.MINECART);
   }

   public boolean run(List<Entity> selectedEntities, GameEvent gameEvent, int stackCounter, AtomicInteger callCounter) {
      selectedEntities.forEach((entity) -> {
         ItemStack item = (ItemStack)this.getVar("item", gameEvent, entity);
         if (!ItemStackUtil.isNullOrAir(item)) {
            Location location = this.getVarLocation("location", gameEvent, entity);
            if (location != null) {
               String name = this.getVarString("name", gameEvent, entity);
               int speed = this.getVar("speed", 0, gameEvent, entity);
               switch(item.getType()) {
               case COMMAND_MINECART:
               case EXPLOSIVE_MINECART:
               case HOPPER_MINECART:
               case POWERED_MINECART:
               case STORAGE_MINECART:
               case MINECART:
                  Minecart minecart = (Minecart)location.getWorld().spawn(location, Minecart.class);
                  if (speed != 0) {
                     minecart.setMaxSpeed((double)speed);
                  }

                  if (name != null && !name.isEmpty()) {
                     minecart.setCustomNameVisible(true);
                     minecart.setCustomName(name);
                  }

                  if (item.getType() == Material.STORAGE_MINECART) {
                     minecart.setDisplayBlock(new MaterialData(Material.CHEST));
                  }

                  if (item.getType() == Material.POWERED_MINECART) {
                     minecart.setDisplayBlock(new MaterialData(Material.FURNACE));
                  }

                  if (item.getType() == Material.HOPPER_MINECART) {
                     minecart.setDisplayBlock(new MaterialData(Material.HOPPER));
                  }

                  if (item.getType() == Material.EXPLOSIVE_MINECART) {
                     minecart.setDisplayBlock(new MaterialData(Material.TNT));
                  }

                  if (item.getType() == Material.COMMAND_MINECART) {
                     minecart.setDisplayBlock(new MaterialData(Material.COMMAND));
                  }
                  break;
               case BOAT_ACACIA:
               case BOAT_BIRCH:
               case BOAT_SPRUCE:
               case BOAT_DARK_OAK:
               case BOAT_JUNGLE:
               case BOAT:
                  Boat boat = (Boat)location.getWorld().spawn(location, Boat.class);
                  if (speed != 0) {
                     boat.setMaxSpeed((double)speed);
                  }

                  if (name != null && !name.isEmpty()) {
                     boat.setCustomNameVisible(true);
                     boat.setCustomName(name);
                  }

                  if (item.getType() == Material.BOAT_ACACIA) {
                     boat.setWoodType(TreeSpecies.ACACIA);
                  }

                  if (item.getType() == Material.BOAT_BIRCH) {
                     boat.setWoodType(TreeSpecies.BIRCH);
                  }

                  if (item.getType() == Material.BOAT_SPRUCE) {
                     boat.setWoodType(TreeSpecies.REDWOOD);
                  }

                  if (item.getType() == Material.BOAT_JUNGLE) {
                     boat.setWoodType(TreeSpecies.JUNGLE);
                  }

                  if (item.getType() == Material.BOAT_DARK_OAK) {
                     boat.setWoodType(TreeSpecies.DARK_OAK);
                  }
               }

            }
         }
      });
      return true;
   }

   public boolean parseChest(ChestParser chestParser) {
      this.putVar("item", chestParser.getItem(0), false);
      this.putVar("location", chestParser.getLocation(0));
      this.putVar("name", chestParser.getText(0));
      this.putVar("speed", chestParser.getNumber(0));
      return true;
   }
}
