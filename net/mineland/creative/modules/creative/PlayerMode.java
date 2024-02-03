package net.mineland.creative.modules.creative;

import net.mineland.core.bukkit.module.Module;
import net.mineland.core.bukkit.modules.mainitem.MainBar;
import net.mineland.core.bukkit.modules.mainitem.MainItem;
import net.mineland.core.bukkit.modules.mainitem.ModuleMainItem;
import net.mineland.core.bukkit.modules.mainitem.defaultmainitems.MainItemOpenGui;
import net.mineland.core.bukkit.modules.myevents.UserInteractMyEvent;
import net.mineland.core.bukkit.modules.user.User;
import net.mineland.creative.modules.coding.ModuleCoding;
import net.mineland.creative.modules.coding.PlotScoreboard;
import net.mineland.creative.modules.coding.variables.GuiMenuVariableItems;
import org.bukkit.GameMode;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import ua.govnojon.libs.bukkitutil.ItemData;
import ua.govnojon.libs.bukkitutil.PlayerUtil;

public enum PlayerMode {
   PLAYING {
      public void onPlayerJoin(Plot plot, User user) {
         Player player = user.getPlayer();
         PlayerUtil.setDefaultParameters(player, GameMode.ADVENTURE);
         player.teleport(plot.getSpawnLocation(PlayerMode.moduleCreative.getPlotWorld()));
         PlayerMode.moduleCreative.getPlotManager().sendWorldBorderPacker(user, plot.getCenterPosition().toLocation(PlayerMode.moduleCreative.getPlotWorld()), plot.getSize());
         PlayerMode.moduleMainItem.getMainBar(user).clear();
      }

      public void onPlayerQuit(Plot plot, User user) {
         PlayerMode.moduleMainItem.getMainBar(user).clear();
      }
   },
   CODING {
      public void onPlayerJoin(Plot plot, User user) {
         plot.setSaveCodingPlotOnUnload(true);
         Player player = user.getPlayer();
         player.getActivePotionEffects().forEach((potionEffect) -> {
            player.removePotionEffect(potionEffect.getType());
         });
         player.setGameMode(GameMode.CREATIVE);
         player.teleport(plot.getSpawnLocation(PlayerMode.moduleCoding.getCodingWorld()));
         PlayerMode.moduleMainItem.getMainBar(user).clear();
         PlotScoreboard plotScoreboard = (PlotScoreboard)user.getMetadata("plot_scoreboard");
         if (plotScoreboard != null) {
            plotScoreboard.hide(user);
         }

         this.addItem(user, Material.DIAMOND_BLOCK, "coding.item.player_event", 0);
         this.addItem(user, Material.WOOD, "coding.item.if_player", 1);
         this.addItem(user, Material.COBBLESTONE, "coding.item.player_action", 2);
         this.addItem(user, Material.NETHER_BRICK, "coding.item.game_action", 3);
         this.addItem(user, Material.LAPIS_BLOCK, "coding.item.function", 4);
         this.addItem(user, Material.LAPIS_ORE, "coding.item.call_function", 5);
         this.addItem(user, Material.REDSTONE_BLOCK, "coding.item.world_event", 6);
         PlayerMode.moduleMainItem.getMainBar(user).addItem(new MainItemOpenGui(PlayerMode.moduleMainItem.getMainBar(user), new ItemData(Material.IRON_INGOT), "coding.item.variable_items", 8, GuiMenuVariableItems.class)).setTranslate(true);
         this.addItem(user, Material.EMERALD_BLOCK, "coding.item.loop", 9);
         this.addItem(user, Material.RED_NETHER_BRICK, "coding.item.if_game", 10);
         this.addItem(user, Material.ENDER_STONE, "coding.item.else", 11);
         this.addItem(user, Material.IRON_BLOCK, "coding.item.set_variable", 18);
         this.addItem(user, Material.OBSIDIAN, "coding.item.if_variable", 19);
         this.addItem(user, Material.PURPUR_BLOCK, "coding.item.select_object", 20);
         this.addItem(user, Material.BRICK, "coding.item.if_entity", 28);
         this.addItem(user, Material.ARROW, "coding.item.not_arrow", 35);
         PlayerMode.moduleCreative.getPlotManager().sendWorldBorderPacker(user, plot.getCenterPosition().toLocation(PlayerMode.moduleCoding.getCodingWorld()), PlayerMode.moduleCoding.getCodingSize());
      }

      public void onPlayerQuit(Plot plot, User user) {
         PlayerMode.moduleMainItem.getMainBar(user).clear();
      }
   };

   private static ModuleCreative moduleCreative = (ModuleCreative)Module.getInstance(ModuleCreative.class);
   private static ModuleCoding moduleCoding = (ModuleCoding)Module.getInstance(ModuleCoding.class);
   private static ModuleMainItem moduleMainItem = (ModuleMainItem)Module.getInstance(ModuleMainItem.class);

   private PlayerMode() {
   }

   public void onPlayerJoin(Plot plot, User user) {
   }

   public void onPlayerQuit(Plot plot, User user) {
   }

   public void addItem(User user, Material material, String text, int slot) {
      MainBar mainBar = moduleMainItem.getMainBar(user);
      MainItem mainItem = new MainItem(mainBar, material, text, slot) {
         public void click(UserInteractMyEvent event) {
            event.setCancelled(false);
         }
      };
      mainItem.setAllowInteractWithBlocks(true);
      mainBar.addItem(mainItem).setTranslate(true);
   }

   // $FF: synthetic method
   PlayerMode(Object x2) {
      this();
   }
}
