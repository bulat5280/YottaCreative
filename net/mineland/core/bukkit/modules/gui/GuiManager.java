package net.mineland.core.bukkit.modules.gui;

import java.util.HashMap;
import java.util.List;
import net.mineland.core.bukkit.LibsPlugin;
import net.mineland.core.bukkit.modules.user.User;
import org.bukkit.entity.Player;

public class GuiManager {
   public static ModuleGui moduleGui = LibsPlugin.getModuleGui();

   public static void registerGui(String key, GuiMenuCreator creator) {
      moduleGui.registerGui(key, creator);
   }

   public static void registerGui(Class<? extends GuiMenu> menu, GuiMenuCreator creator) {
      moduleGui.registerGui(menu, creator);
   }

   public static void unregisterGui(String key) {
      moduleGui.unregisterGui(key);
   }

   public static void unregisterGui(Class<? extends GuiMenu> menu) {
      moduleGui.unregisterGui(menu);
   }

   public static List<GuiMenu> getGuis() {
      return moduleGui.getGuis();
   }

   public static List<GuiMenu> getGuisPlayer(Player player) {
      return moduleGui.getGuisPlayer(player);
   }

   public static boolean isLoadGuiPlayer(Player player, String key) {
      return moduleGui.isLoadedGui(player, key);
   }

   public static GuiMenu getGuiPlayer(Player player, String key) {
      return moduleGui.getGuiPlayer(player, key);
   }

   public static GuiMenu getGuiPlayer(User player, String key) {
      return moduleGui.getGuiPlayer(player, key);
   }

   public static void reloadGui(Player player, String key) {
      moduleGui.reloadGui(player, key);
   }

   public static void reloadGui(String key) {
      moduleGui.reloadGui(key);
   }

   public static HashMap<String, GuiMenuCreator> getRegisteredGuis() {
      return moduleGui.getRegisteredGuis();
   }
}
