package net.mineland.creative.modules.creative.gui.worldsettings.flags;

import net.mineland.api.user.IUser;
import net.mineland.core.bukkit.module.Module;
import net.mineland.core.bukkit.modules.gui.GuiMenu;
import net.mineland.core.bukkit.modules.gui.defaultitems.GuiItemBack;
import net.mineland.core.bukkit.modules.gui.defaultitems.GuiItemFlag;
import net.mineland.core.bukkit.modules.message.Message;
import net.mineland.core.bukkit.modules.user.Lang;
import net.mineland.core.bukkit.modules.user.User;
import net.mineland.creative.modules.creative.ModuleCreative;
import net.mineland.creative.modules.creative.Plot;
import net.mineland.creative.modules.creative.gui.worldsettings.GuiMenuWorldSettings;
import org.bukkit.Material;

public class GuiMenuFlags extends GuiMenu {
   public GuiMenuFlags(String key, User player) {
      super(key, player, 3, Message.getMessage((IUser)player, "меню_флагов"));
      Lang lang = this.getUser().getLang();
      ModuleCreative moduleCreative = (ModuleCreative)Module.getInstance(ModuleCreative.class);
      Plot plot = moduleCreative.getPlotManager().getCurrentPlot(player);
      User owner = plot.getOwner();
      if (owner != null && !player.equals(owner)) {
         player.sendMessage(Message.getMessage((IUser)player, "вы_должны_быть_на_острове"));
      } else {
         this.addItem(new GuiItemFlagIsland(this, plot, 2, 2, Material.WOOD_DOOR, (short)0, 1, Message.getMessage(lang, "предмет_настройка_use"), "use", new GuiItemFlag.Value[]{GuiItemFlag.Value.MEMBER, GuiItemFlag.Value.ENABLE_ALL}));
         this.addItem(new GuiItemFlagIsland(this, plot, 6, 2, Material.SKULL_ITEM, (short)2, 1, Message.getMessage(lang, "предмет_настройка_спавн"), "spawn_monsters", new GuiItemFlag.Value[]{GuiItemFlag.Value.DISABLE, GuiItemFlag.Value.ENABLE}));
         this.addItem(new GuiItemFlagIsland(this, plot, 4, 2, Material.FLINT_AND_STEEL, (short)0, 1, Message.getMessage(lang, "предмет_настройка_горение"), "ignite", new GuiItemFlag.Value[]{GuiItemFlag.Value.DISABLE, GuiItemFlag.Value.ENABLE}));
         this.addItem(new GuiItemFlagIsland(this, plot, 8, 2, Material.TNT, (short)0, 1, Message.getMessage(lang, "предмет_настройка_взрывы"), "explode", new GuiItemFlag.Value[]{GuiItemFlag.Value.DISABLE, GuiItemFlag.Value.ENABLE}));
         this.addItem(new GuiItemBack(this, GuiMenuWorldSettings.class));
      }
   }
}
