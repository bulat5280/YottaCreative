package net.mineland.core.bukkit.modules.gui;

import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Objects;
import net.mineland.core.bukkit.module.BukkitModule;
import net.mineland.core.bukkit.module.ModuleReloadEvent;
import net.mineland.core.bukkit.modules.message.ModuleMessage;
import net.mineland.core.bukkit.modules.user.PlayerChangeLangMyEvent;
import net.mineland.core.bukkit.modules.user.User;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.block.BlockFace;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Event.Result;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryCloseEvent;
import org.bukkit.event.inventory.InventoryDragEvent;
import org.bukkit.event.inventory.InventoryOpenEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.InventoryView;
import org.bukkit.plugin.Plugin;
import ua.govnojon.libs.bukkitutil.schedule.Schedule;
import ua.govnojon.libs.config.Config;

public class ModuleGui extends BukkitModule {
   private static ModuleGui instance;
   private HashMap<String, GuiMenuCreator> registered = new HashMap();

   public ModuleGui(int priority, Plugin plugin) {
      super("gui", priority, plugin, (Config)null);
      instance = this;
   }

   public static ModuleGui getInstance() {
      return instance;
   }

   public void onFirstEnable() {
      this.registerListenersThis();
   }

   public void onEnable() {
   }

   public void onDisable() {
   }

   @EventHandler
   public void on(ModuleReloadEvent event) {
      if (event.getModule() instanceof ModuleMessage) {
         this.reloadGuisAll();
      }

   }

   @EventHandler(
      priority = EventPriority.MONITOR
   )
   public void onPlayerChangeLangMyEventMonitor(PlayerChangeLangMyEvent event) {
      this.reloadGuis(event.getUser());
   }

   @EventHandler(
      priority = EventPriority.HIGH,
      ignoreCancelled = true
   )
   public void onInventoryDragEvent(InventoryDragEvent event) {
      Iterator var2 = this.getGuisPlayer((Player)event.getWhoClicked()).iterator();

      while(var2.hasNext()) {
         GuiMenu guiMenu = (GuiMenu)var2.next();
         if (guiMenu.equals(event.getInventory())) {
            event.setResult(Result.DENY);
            break;
         }
      }

   }

   @EventHandler(
      priority = EventPriority.HIGH,
      ignoreCancelled = true
   )
   public void onInventoryClickEvent(InventoryClickEvent event) {
      Iterator var2 = this.getGuisPlayer((Player)event.getWhoClicked()).iterator();

      while(var2.hasNext()) {
         GuiMenu guiMenu = (GuiMenu)var2.next();
         if (guiMenu.equals(event.getInventory())) {
            guiMenu.onInventoryClickEvent(event);
            break;
         }
      }

   }

   @EventHandler(
      priority = EventPriority.HIGH,
      ignoreCancelled = true
   )
   public void onInventoryOpenEvent(InventoryOpenEvent event) {
      Player player = (Player)event.getPlayer();
      User user = User.getUser(player);
      Iterator var4 = this.getGuisPlayer(user).iterator();

      while(var4.hasNext()) {
         GuiMenu guiMenu = (GuiMenu)var4.next();
         if (guiMenu.equals(event.getInventory())) {
            if (!this.isOpenLoc(event.getPlayer().getLocation())) {
               event.setCancelled(true);
               player.setVelocity(player.getLocation().getDirection().multiply(0.5D).setY(0));
               Schedule.later(() -> {
                  if (user.isActive()) {
                     guiMenu.openOwner();
                  }

               }, 5L);
            } else {
               guiMenu.onInventoryOpenEvent(event);
            }
            break;
         }
      }

   }

   @EventHandler(
      priority = EventPriority.HIGH,
      ignoreCancelled = true
   )
   public void onInventoryCloseEvent(InventoryCloseEvent event) {
      Iterator iter = this.getGuisPlayer((Player)event.getPlayer()).iterator();

      while(iter.hasNext()) {
         GuiMenu guiMenu = (GuiMenu)iter.next();
         if (guiMenu.equals(event.getInventory())) {
            guiMenu.onInventoryCloseEvent(event);
            if (guiMenu.isUnloadOnClose()) {
               iter.remove();
            }
            break;
         }
      }

   }

   private boolean isOpenLoc(Location location) {
      Block block = location.getBlock();
      return !block.getType().equals(Material.PORTAL) && !block.getRelative(BlockFace.WEST).getType().equals(Material.PORTAL) && !block.getRelative(BlockFace.EAST).getType().equals(Material.PORTAL) && !block.getRelative(BlockFace.SOUTH).getType().equals(Material.PORTAL) && !block.getRelative(BlockFace.NORTH).getType().equals(Material.PORTAL);
   }

   public void reloadGuis(User user) {
      GuiMenu open = this.getOpenGui(user);
      this.getGuisPlayer(user).clear();
      if (open != null) {
         this.openGui(user, open.getKey());
      }

   }

   public void reloadGui(User user, String key) {
      GuiMenu open = this.getOpenGui(user);
      List<GuiMenu> guiMenus = this.getGuisPlayer(user);
      GuiMenu reload = null;
      Iterator var6 = guiMenus.iterator();

      while(var6.hasNext()) {
         GuiMenu guiMenu = (GuiMenu)var6.next();
         if (guiMenu.getKey().equals(key)) {
            reload = guiMenu;
            guiMenus.remove(guiMenu);
            break;
         }
      }

      if (reload != null && open != null && reload.equals(open)) {
         this.getGuiPlayer(user, key).openOwner();
      }

   }

   public void reloadGui(Player player, String key) {
      this.reloadGui(User.getUser(player), key);
   }

   public void reloadGui(Class<? extends GuiMenu> clazz) {
      Iterator var2 = User.getUsers().iterator();

      while(var2.hasNext()) {
         User player = (User)var2.next();
         this.reloadGui(player, clazz);
      }

   }

   public void reloadGui(String key) {
      Iterator var2 = User.getUsers().iterator();

      while(var2.hasNext()) {
         User player = (User)var2.next();
         this.reloadGui(player, key);
      }

   }

   public GuiMenu getOpenGui(User user) {
      InventoryView view = user.getPlayer().getOpenInventory();
      if (view == null) {
         return null;
      } else {
         Inventory inventory = view.getTopInventory();
         if (inventory == null) {
            return null;
         } else {
            GuiMenu guiMenuCurrent = null;
            Iterator var5 = this.getGuisPlayer(user).iterator();

            while(var5.hasNext()) {
               GuiMenu guiMenu = (GuiMenu)var5.next();
               if (guiMenu.equals(inventory)) {
                  guiMenuCurrent = guiMenu;
                  break;
               }
            }

            return guiMenuCurrent;
         }
      }
   }

   public GuiMenu getOpenGui(Player player) {
      return this.getOpenGui(User.getUser(player));
   }

   public void registerGui(String key, GuiMenuCreator creator) {
      this.removeCache(key);
      this.registered.put(key, creator);
   }

   public void registerGui(Class<? extends GuiMenu> clazz, GuiMenuCreator creator) {
      this.registerGui(clazz.getSimpleName().toLowerCase(), creator);
   }

   public void unregisterGui(String key) {
      this.registered.remove(key);
      this.removeCache(key);
   }

   public void unregisterGui(Class<? extends GuiMenu> clazz) {
      this.unregisterGui(clazz.getSimpleName().toLowerCase());
   }

   private void removeCache(String key) {
      User.getUsers().forEach((user) -> {
         this.getGuisPlayer(user).removeIf((gui) -> {
            if (gui.getKey().equals(key)) {
               if (Objects.equals(gui.getPlayer().getOpenInventory(), gui.getInventory())) {
                  gui.getPlayer().closeInventory();
               }

               return true;
            } else {
               return false;
            }
         });
      });
   }

   public List<GuiMenu> getGuis() {
      List<GuiMenu> guiMenus = new LinkedList();
      Iterator var2 = Bukkit.getOnlinePlayers().iterator();

      while(var2.hasNext()) {
         Player player = (Player)var2.next();
         guiMenus.addAll(this.getGuisPlayer(player));
      }

      return guiMenus;
   }

   public List<GuiMenu> getGuisPlayer(User user) {
      List<GuiMenu> guiMenus = (List)user.getMetadata("guis");
      if (guiMenus == null) {
         guiMenus = new LinkedList();
         user.setMetadata("guis", guiMenus);
      }

      return (List)guiMenus;
   }

   public List<GuiMenu> getGuisPlayer(Player player) {
      return this.getGuisPlayer(User.getUser(player));
   }

   public <T extends GuiMenu> T getGuiPlayer(User user, String key) {
      List<GuiMenu> guiMenus = this.getGuisPlayer(user);
      Iterator var4 = guiMenus.iterator();

      GuiMenu newGui;
      do {
         if (!var4.hasNext()) {
            GuiMenuCreator creator = (GuiMenuCreator)this.registered.get(key);
            if (creator == null) {
               return null;
            }

            newGui = creator.create(key, user);
            if (newGui != null) {
               guiMenus.add(newGui);
               Bukkit.getPluginManager().callEvent(new GuiMenuLoadEvent(user, newGui));
            }

            return newGui;
         }

         newGui = (GuiMenu)var4.next();
      } while(!newGui.getKey().equals(key));

      return newGui;
   }

   public GuiMenu getGuiPlayer(Player player, String key) {
      return this.getGuiPlayer(User.getUser(player), key);
   }

   public <T extends GuiMenu> T getGuiPlayer(User user, Class<? extends GuiMenu> clazz) {
      return this.getGuiPlayer(user, clazz.getSimpleName().toLowerCase());
   }

   public GuiMenu getGuiPlayer(Player player, Class<? extends GuiMenu> clazz) {
      return this.getGuiPlayer(User.getUser(player), clazz);
   }

   public boolean isLoadedGui(User user, String key) {
      List<GuiMenu> guiMenus = this.getGuisPlayer(user);
      Iterator var4 = guiMenus.iterator();

      GuiMenu g;
      do {
         if (!var4.hasNext()) {
            return false;
         }

         g = (GuiMenu)var4.next();
      } while(!g.getKey().equals(key));

      return true;
   }

   public boolean isLoadedGui(Player player, String key) {
      return this.isLoadedGui(User.getUser(player), key);
   }

   public <T extends GuiMenu> T getGuiPlayerIsLoad(User user, String key) {
      List<GuiMenu> guiMenus = this.getGuisPlayer(user);
      Iterator var4 = guiMenus.iterator();

      GuiMenu g;
      do {
         if (!var4.hasNext()) {
            return null;
         }

         g = (GuiMenu)var4.next();
      } while(!g.getKey().equals(key));

      return g;
   }

   public HashMap<String, GuiMenuCreator> getRegisteredGuis() {
      return this.registered;
   }

   public void openGui(User user, String keyGui) {
      GuiMenu guiMenu = this.getGuiPlayer(user, keyGui);
      if (guiMenu != null) {
         guiMenu.openOwner();
      } else {
         this.getLogger().warning("Игрок '" + user.getPlayer().getName() + "' попытался открыть гуи '" + keyGui + "', которого нету.");
      }

   }

   public void openGui(User user, GuiMenu gui) {
      List<GuiMenu> guiMenus = this.getGuisPlayer(user);
      guiMenus.removeIf((g) -> {
         return g.getKey().equals(gui.getKey());
      });
      guiMenus.add(gui);
      Bukkit.getPluginManager().callEvent(new GuiMenuLoadEvent(user, gui));
      gui.openOwner();
   }

   public void openGui(User user, Class<? extends GuiMenu> clazz) {
      GuiMenu guiMenu = this.getGuiPlayer(user, clazz);
      if (guiMenu != null) {
         guiMenu.open(user);
      } else {
         this.getLogger().warning("Игрок '" + user.getPlayer().getName() + "' попытался открыть гуи '" + clazz.getSimpleName().toLowerCase() + "', которого нету.");
      }

   }

   public void reloadGuisAll() {
      User.getUsers().forEach(this::reloadGuis);
   }

   public void reloadGui(User user, Class<? extends GuiMenu> guiMenuClass) {
      this.reloadGui(user, guiMenuClass.getSimpleName().toLowerCase());
   }
}
