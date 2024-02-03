package net.mineland.creative.modules.coding.actions.gui;

import net.mineland.api.user.IUser;
import net.mineland.core.bukkit.module.Module;
import net.mineland.core.bukkit.modules.gui.GuiItem;
import net.mineland.core.bukkit.modules.gui.GuiMenu;
import net.mineland.core.bukkit.modules.message.Message;
import net.mineland.core.bukkit.modules.user.User;
import net.mineland.creative.modules.coding.actions.Action;
import net.mineland.creative.modules.coding.actions.ActionType;
import net.mineland.creative.modules.creative.ModuleCreative;
import net.mineland.creative.modules.creative.Plot;
import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.block.Block;
import org.bukkit.block.BlockFace;
import org.bukkit.block.Sign;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryOpenEvent;
import ua.govnojon.libs.bukkitutil.PlayerUtil;

public class GuiMenuActions extends GuiMenu {
   private static ModuleCreative moduleCreative = (ModuleCreative)Module.getInstance(ModuleCreative.class);

   public GuiMenuActions(String key, User user) {
      super(key, user, 4, Message.getMessage((IUser)user, "coding.gui.player_actions.title"));
   }

   protected void onInventoryOpenEvent(InventoryOpenEvent event) {
      this.clear();
      Action.Category category = (Action.Category)this.getUser().removeMetadata("coding.selected_category");
      if (category != null) {
         this.init(category);
      }

   }

   public void init(Action.Category category) {
      int pos = 0;
      ActionType[] var3 = ActionType.values();
      int var4 = var3.length;

      for(int var5 = 0; var5 < var4; ++var5) {
         ActionType actionType = var3[var5];
         if (actionType.getCategory() == category) {
            this.addItem(new GuiMenuActions.GuiItemPlayerAction(this, pos, actionType));
            ++pos;
         }
      }

   }

   public class GuiItemPlayerAction extends GuiItem {
      ActionType actionType;

      GuiItemPlayerAction(GuiMenu guiMenu, int pos, ActionType actionType) {
         super(guiMenu, pos, actionType.getIcon().toItemStack(), Message.getMessage((IUser)guiMenu.getUser(), "coding.gui.action." + actionType.name()));
         this.actionType = actionType;
      }

      public void click(InventoryClickEvent event) {
         Plot plot = GuiMenuActions.moduleCreative.getPlotManager().getCurrentPlot(this.getUser());
         Block block = (Block)this.getUser().removeMetadata("coding.selected_sign");
         if (block != null && block.getType() == Material.WALL_SIGN) {
            Sign sign;
            switch(this.actionType) {
            case SELECT_MOBS_BY_CONDITION:
            case SELECT_ENTITIES_BY_CONDITION:
            case SELECT_ALL_ENTITIES:
            case SELECT_ALL_MOBS:
            case SELECT_ALL_PLAYERS:
            case SELECT_DEFAULT_ENTITY:
            case SELECT_DEFAULT_PLAYER:
            case SELECT_FILTER_SELECTION_RANDOMLY:
            case SELECT_LAST_SPAWNED_MOB:
            case SELECT_RANDOM_ENTITY:
            case SELECT_RANDOM_MOB:
            case SELECT_RANDOM_PLAYER:
            case SELECT_PLAYERS_BY_CONDITION:
               PlayerUtil.playSound(this.getPlayer(), Sound.ENTITY_EXPERIENCE_ORB_PICKUP);
               sign = (Sign)block.getState();
               sign.setLine(1, "lang:coding.sign." + this.actionType.name());
               sign.update();
               this.getUser().setMetadata("coding.selected_sign", block);
               this.getUser().setMetadata("coding.select_object", this.actionType);
               GuiMenuActions.moduleGui.openGui(this.getUser(), GuiMenuConditionType.class);
               return;
            default:
               PlayerUtil.playSound(this.getPlayer(), Sound.ENTITY_EXPERIENCE_ORB_PICKUP);
               this.getPlayer().closeInventory();
               sign = (Sign)block.getState();
               if (block.getRelative(BlockFace.SOUTH).getType() == Material.PURPUR_BLOCK) {
                  sign.setLine(2, "lang:coding.sign." + this.actionType.name());
               } else {
                  sign.setLine(1, "lang:coding.sign." + this.actionType.name());
               }

               sign.update();
               if (plot != null) {
                  GuiMenuActions.moduleCreative.getPlotManager().parseCoding(plot);
               }

               if (this.actionType.hasChest()) {
                  block.getRelative(BlockFace.SOUTH).getRelative(BlockFace.UP).setType(Material.CHEST);
               } else {
                  block.getRelative(BlockFace.SOUTH).getRelative(BlockFace.UP).setType(Material.AIR);
               }
            }
         } else {
            this.getPlayer().closeInventory();
         }

      }
   }
}
