package net.mineland.core.bukkit.module;

import java.util.logging.Level;
import java.util.logging.LogRecord;
import java.util.logging.Logger;
import org.bukkit.plugin.Plugin;

public class ModuleLogger extends Logger {
   private String pluginName;

   public ModuleLogger(Plugin context, Module module) {
      super(context.getDescription().getName(), (String)null);
      this.pluginName = "[" + module.getName() + "] ";
      this.setParent(context.getLogger());
      this.setLevel(Level.ALL);
   }

   public String getPluginName(Object plugin) {
      return plugin instanceof Plugin ? ((Plugin)plugin).getDataFolder().getName() : null;
   }

   public void log(LogRecord logRecord) {
      logRecord.setMessage(this.pluginName + logRecord.getMessage());
      super.log(logRecord);
   }

   public void severeLoadParameter(String data) {
      this.severe("Ошибка загрузки значения '" + data + "'.");
   }
}
