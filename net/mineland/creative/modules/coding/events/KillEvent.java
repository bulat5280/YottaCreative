package net.mineland.creative.modules.coding.events;

import org.bukkit.entity.Entity;

public interface KillEvent {
   Entity getKiller();

   Entity getVictim();
}
