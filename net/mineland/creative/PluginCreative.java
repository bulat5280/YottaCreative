package net.mineland.creative;

import java.io.File;
import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.net.URL;
import java.net.URLClassLoader;
import net.mineland.core.bukkit.module.ModuleListener;
import net.mineland.core.bukkit.module.ModuleManager;
import net.mineland.core.bukkit.modules.analyzer.ModuleAnalyzer;
import net.mineland.core.bukkit.modules.command.CommandManager;
import net.mineland.core.bukkit.modules.command.ModuleCommand;
import net.mineland.core.bukkit.modules.confirm.ModuleConfirm;
import net.mineland.core.bukkit.modules.customdeaths.ModuleCustomDeath;
import net.mineland.core.bukkit.modules.gui.GuiManager;
import net.mineland.core.bukkit.modules.gui.ModuleGui;
import net.mineland.core.bukkit.modules.locale.ModuleLocale;
import net.mineland.core.bukkit.modules.mainitem.ModuleMainItem;
import net.mineland.core.bukkit.modules.material.ModuleMaterial;
import net.mineland.core.bukkit.modules.message.ModuleMessage;
import net.mineland.core.bukkit.modules.myevents.ModuleMyEvents;
import net.mineland.core.bukkit.modules.mysql.ModuleMySQL;
import net.mineland.core.bukkit.modules.nms.ModuleNMS;
import net.mineland.core.bukkit.modules.potions.ModulePotions;
import net.mineland.core.bukkit.modules.region.ModuleRegion;
import net.mineland.core.bukkit.modules.scoreboard.ModuleScoreboard;
import net.mineland.core.bukkit.modules.sign.ModuleSign;
import net.mineland.core.bukkit.modules.time.ModuleTime;
import net.mineland.core.bukkit.modules.tracker.ModuleTracker;
import net.mineland.core.bukkit.modules.user.ModuleUser;
import net.mineland.creative.commands.CommandAd;
import net.mineland.creative.commands.CommandEditor;
import net.mineland.creative.commands.CommandLocate;
import net.mineland.creative.commands.CommandText;
import net.mineland.creative.commands.CommandUser;
import net.mineland.creative.gui.GuiMenuOpublikWorlds;
import net.mineland.creative.jumpfix.JumpFix;
import net.mineland.creative.modules.coding.ModuleCoding;
import net.mineland.creative.modules.coding.worldactivators.activators.gui.GuiWorldActivators;
import net.mineland.creative.modules.creative.ModuleCreative;
import net.mineland.creative.modules.creative.gui.worldsettings.GuiMenuCategoryDevSettings;
import net.mineland.creative.modules.creative.gui.worldsettings.GuiMenuCategoryRightsSettings;
import net.mineland.creative.modules.creative.gui.worldsettings.GuiMenuCategoryWorldSettings;
import net.mineland.creative.modules.creatorpoints.ModuleCreatorPoints;
import org.bukkit.Bukkit;
import org.bukkit.plugin.Plugin;
import org.bukkit.plugin.java.JavaPlugin;

public class PluginCreative extends JavaPlugin {
   private static Plugin instance;

   public static Plugin getInstance() {
      return instance;
   }

   public PluginCreative() {
      instance = this;
   }

   public void onLoad() {
      try {
         int count = 0;
         File library = new File("." + File.separator + "library");
         if (!library.exists()) {
            library.mkdir();
         }

         File[] var3 = library.listFiles((dir, name) -> {
            return name.endsWith(".jar");
         });
         int var4 = var3.length;

         for(int var5 = 0; var5 < var4; ++var5) {
            File file = var3[var5];

            try {
               long start = System.currentTimeMillis();
               this.loadLib(file);
               ++count;
            } catch (Exception var9) {
               var9.printStackTrace();
            }
         }
      } catch (Throwable var10) {
         var10.printStackTrace();
         Bukkit.shutdown();
      }

   }

   private void loadLib(File file) throws IOException, NoSuchMethodException, InvocationTargetException, IllegalAccessException {
      URLClassLoader loader = (URLClassLoader)this.getClassLoader();
      Method addUrl = URLClassLoader.class.getDeclaredMethod("addURL", URL.class);
      addUrl.setAccessible(true);
      addUrl.invoke(loader, file.toURI().toURL());
      addUrl.setAccessible(false);
   }

   public void onEnable() {
      try {
         Bukkit.getPluginManager().registerEvents(new ModuleListener(), this);
         Bukkit.getPluginManager().registerEvents(new JumpFix(), this);
         ModuleManager.registerModule(this, 0, ModuleAnalyzer::new);
         ModuleManager.registerModule(this, -5000, ModuleMySQL::new);
         ModuleManager.registerModule(this, -2000, ModuleCommand::new);
         ModuleManager.registerModule(this, -900, ModuleMessage::new);
         ModuleManager.registerModule(this, -900, ModulePotions::new);
         ModuleManager.registerModule(this, -900, ModuleTime::new);
         ModuleManager.registerModule(this, -310, ModuleGui::new);
         ModuleManager.registerModule(this, -300, ModuleUser::new);
         ModuleManager.registerModule(this, -210, ModuleScoreboard::new);
         ModuleManager.registerModule(this, -200, ModuleNMS::new);
         ModuleManager.registerModule(this, -110, ModuleLocale::new);
         ModuleManager.registerModule(this, -100, ModuleMainItem::new);
         ModuleManager.registerModule(this, -30, ModuleMaterial::new);
         ModuleManager.registerModule(this, -20, ModuleRegion::new);
         ModuleManager.registerModule(this, -10, ModuleMyEvents::new);
         ModuleManager.registerModule(this, 0, ModuleConfirm::new);
         ModuleManager.registerModule(this, 0, ModuleCustomDeath::new);
         ModuleManager.registerModule(this, 0, ModuleCreative::new);
         ModuleManager.registerModule(this, 100, ModuleCoding::new);
         ModuleManager.registerModule(this, -10, ModuleSign::new);
         ModuleManager.registerModule(this, -600, ModuleTracker::new);
         ModuleManager.registerModule(this, -600, ModuleCreatorPoints::new);
         ModuleManager.enableModules();
         ModuleManager.afterEnable(() -> {
            Bukkit.getPluginManager().registerEvents(new Listener2(), this);
            CommandManager.unregisterCommand("time");
            CommandManager.unregisterCommand("locate");
            CommandManager.registerCommand(new CommandUser());
            CommandManager.registerCommand(new CommandText());
            CommandManager.registerCommand(new CommandAd());
            CommandManager.registerCommand(new CommandLocate());
            CommandManager.registerCommand(new CommandEditor());
            GuiManager.registerGui(GuiMenuOpublikWorlds.class, GuiMenuOpublikWorlds::new);
            GuiManager.registerGui(GuiWorldActivators.class, GuiWorldActivators::new);
            GuiManager.registerGui(GuiMenuCategoryDevSettings.class, GuiMenuCategoryDevSettings::new);
            GuiManager.registerGui(GuiMenuCategoryRightsSettings.class, GuiMenuCategoryRightsSettings::new);
            GuiManager.registerGui(GuiMenuCategoryWorldSettings.class, GuiMenuCategoryWorldSettings::new);
         });
      } catch (Throwable var2) {
         var2.printStackTrace();
         Bukkit.shutdown();
      }

   }
}
