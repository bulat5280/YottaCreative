package net.mineland.core.bukkit.modules.myevents;

import java.util.Arrays;
import java.util.List;
import net.mineland.core.bukkit.modules.user.User;
import org.bukkit.Bukkit;
import org.bukkit.GameMode;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.CreatureSpawnEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.event.vehicle.VehicleCreateEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.plugin.Plugin;

public class EventsSpawn implements Listener {
   private Location lastClickEggLocation;
   private Player lastClickEggPlayer;
   private ItemStack lastClickEggItem;
   private List<Material> eggs;
   private Plugin plugin;

   public EventsSpawn(Plugin plugin) {
      this.eggs = Arrays.asList(Material.MONSTER_EGG, Material.MINECART, Material.COMMAND_MINECART, Material.EXPLOSIVE_MINECART, Material.HOPPER_MINECART, Material.POWERED_MINECART, Material.STORAGE_MINECART, Material.BOAT, Material.BOAT_ACACIA, Material.BOAT_BIRCH, Material.BOAT_DARK_OAK, Material.BOAT_JUNGLE, Material.BOAT_SPRUCE, Material.ARMOR_STAND);
      this.plugin = plugin;
   }

   @EventHandler(
      priority = EventPriority.HIGHEST
   )
   public void onCreatureSpawnEvent(CreatureSpawnEvent event) {
      Location s = event.getLocation();
      Location l = this.lastClickEggLocation;
      if (l != null) {
         if (s.getWorld().equals(l.getWorld()) && s.distance(l) < 2.0D) {
            User user = User.getUser(this.lastClickEggPlayer);
            if (user != null) {
               PlayerEntitySpawnMyEvent myEvent = new PlayerEntitySpawnMyEvent(user, event.getEntity(), l);
               myEvent.setCancelled(event.isCancelled());
               Bukkit.getPluginManager().callEvent(myEvent);
               event.setCancelled(myEvent.isCancelled());
            }
         }

      }
   }

   @EventHandler(
      priority = EventPriority.MONITOR
   )
   public void onVehicleCreateEvent(VehicleCreateEvent event) {
      Location s = event.getVehicle().getLocation();
      Location l = this.lastClickEggLocation;
      if (l != null) {
         if (s.getWorld().equals(l.getWorld()) && s.distance(l) < 2.0D) {
            Bukkit.getScheduler().runTaskLater(this.plugin, () -> {
               if (event.getVehicle().isValid()) {
                  User user = User.getUser(this.lastClickEggPlayer);
                  if (user != null) {
                     PlayerEntitySpawnMyEvent myEvent = new PlayerEntitySpawnMyEvent(user, event.getVehicle(), l);
                     myEvent.setCancelled(false);
                     Bukkit.getPluginManager().callEvent(myEvent);
                     if (myEvent.isCancelled()) {
                        event.getVehicle().remove();
                        GameMode gameMode = this.lastClickEggPlayer.getGameMode();
                        if (gameMode.equals(GameMode.SURVIVAL) || gameMode.equals(GameMode.ADVENTURE)) {
                           ItemStack item = this.lastClickEggItem.clone();
                           item.setAmount(1);
                           this.lastClickEggPlayer.getInventory().addItem(new ItemStack[]{item});
                        }
                     }
                  }

               }
            }, 1L);
         }

      }
   }

   @EventHandler(
      priority = EventPriority.HIGHEST,
      ignoreCancelled = true
   )
   public void onPlayerInteractEvent(PlayerInteractEvent event) {
      if (event.getItem() != null && event.getClickedBlock() != null && event.getClickedBlock().getType().isBlock() && this.eggs.contains(event.getItem().getType())) {
         Block block = event.getClickedBlock();
         block = block.getRelative(event.getBlockFace());
         this.lastClickEggLocation = block.getLocation();
         this.lastClickEggPlayer = event.getPlayer();
         this.lastClickEggItem = event.getItem();
      }

   }
}
