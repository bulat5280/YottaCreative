package net.mineland.creative.modules.coding.events;

import org.bukkit.entity.Entity;

public interface DamageEvent {
   double getDamage();

   Entity getDamager();

   Entity getShooter();

   Entity getVictim();
}
