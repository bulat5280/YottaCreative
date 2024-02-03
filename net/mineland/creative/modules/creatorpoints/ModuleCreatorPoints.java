package net.mineland.creative.modules.creatorpoints;

import net.mineland.core.bukkit.module.BukkitModule;
import org.bukkit.plugin.Plugin;
import ua.govnojon.libs.config.Config;

public class ModuleCreatorPoints extends BukkitModule {
   public ModuleCreatorPoints(int priority, Plugin plugin) {
      super("creator-points", priority, plugin, new Config(plugin, "creatorpoints.yml"));
   }

   public int getCreatorPoints(String username) {
      return this.getConfig().getInt("datas." + username + ".count");
   }

   public void addOnePoint(String username) {
      int total = this.getConfig().getInt("datas." + username + ".count") + 1;
      this.getConfig().set("datas." + username + ".count", total);
   }

   public void onEnable() {
      this.registerListenersThis();
      this.getConfig().createSectionIfNotExist("datas");
      this.getConfig().save();
   }

   public void onDisable() {
   }
}
