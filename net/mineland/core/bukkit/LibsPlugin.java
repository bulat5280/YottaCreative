package net.mineland.core.bukkit;

import net.mineland.core.bukkit.module.Module;
import net.mineland.core.bukkit.modules.gui.ModuleGui;
import net.mineland.creative.PluginCreative;
import org.bukkit.plugin.Plugin;

public class LibsPlugin {
   public static Plugin getInstance() {
      return PluginCreative.getInstance();
   }

   public static ModuleGui getModuleGui() {
      return (ModuleGui)Module.getInstance(ModuleGui.class);
   }
}
