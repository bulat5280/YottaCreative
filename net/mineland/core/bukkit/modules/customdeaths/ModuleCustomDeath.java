package net.mineland.core.bukkit.modules.customdeaths;

import net.mineland.core.bukkit.module.BukkitModule;
import net.mineland.core.bukkit.modules.myevents.EntityDamageByEntityMyEvent;
import net.mineland.core.bukkit.modules.user.User;
import org.bukkit.Bukkit;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.event.entity.PlayerDeathEvent;
import org.bukkit.plugin.Plugin;
import ua.govnojon.libs.config.Config;

public class ModuleCustomDeath extends BukkitModule {
   public ModuleCustomDeath(int priority, Plugin plugin) {
      super("custom_deaths", priority, plugin, (Config)null);
   }

   public void onFirstEnable() {
      this.registerListenersThis();
   }

   public void onEnable() {
   }

   public void onDisable() {
   }

   @EventHandler(
      priority = EventPriority.MONITOR,
      ignoreCancelled = true
   )
   public void onEntityDamageByEntityEvent(EntityDamageByEntityMyEvent event) {
      if (event.getEntity() instanceof Player) {
         DamageUser.get(User.getUser((CommandSender)event.getEntity())).add(event);
      }

   }

   @EventHandler(
      priority = EventPriority.MONITOR,
      ignoreCancelled = true
   )
   public void onEntityDamage(EntityDamageEvent event) {
      if (event.getEntity() instanceof Player) {
         DamageUser.get(User.getUser((CommandSender)event.getEntity())).add(event);
      }

   }

   @EventHandler(
      priority = EventPriority.HIGH
   )
   public void onPlayerDeathEvent(PlayerDeathEvent event) {
      Bukkit.getPluginManager().callEvent(new PlayerDeathMyEvent(User.getUser(event.getEntity()), event));
   }

   public DamageUser getDamageUser(User user) {
      return DamageUser.get(user);
   }
}
