package net.mineland.core.bukkit.modules.customdeaths;

import java.util.List;
import net.mineland.core.bukkit.modules.myevents.UserEvent;
import net.mineland.core.bukkit.modules.user.User;
import org.bukkit.entity.Player;
import org.bukkit.event.Cancellable;
import org.bukkit.event.HandlerList;
import org.bukkit.event.entity.PlayerDeathEvent;

public class PlayerDeathMyEvent extends UserEvent implements Cancellable {
   private static final HandlerList handlers = new HandlerList();
   private List<DamageUser.Damage> history;
   private PlayerDeathEvent deathEvent;
   private DamageUser.Damage lastDamage;
   private DamageUser.Damage lastDamager;

   public PlayerDeathMyEvent(User user, PlayerDeathEvent deathEvent) {
      super(user);
      this.history = DamageUser.get(user).getHistory();
      this.deathEvent = deathEvent;
      if (!this.history.isEmpty()) {
         this.lastDamage = (DamageUser.Damage)this.history.get(this.history.size() - 1);

         for(int i = this.history.size() - 1; i >= 0; --i) {
            DamageUser.Damage damage = (DamageUser.Damage)this.history.get(i);
            if (damage.hasDamager()) {
               this.lastDamager = damage;
               break;
            }
         }
      }

   }

   public static HandlerList getHandlerList() {
      return handlers;
   }

   public List<DamageUser.Damage> getHistory() {
      return this.history;
   }

   public DamageUser.Damage getLastDamage() {
      return this.lastDamage;
   }

   public DamageUser.Damage getLastDamager() {
      return this.lastDamager;
   }

   public boolean hasDamages() {
      return !this.history.isEmpty();
   }

   public HandlerList getHandlers() {
      return handlers;
   }

   public PlayerDeathEvent getPlayerDeathEvent() {
      return this.deathEvent;
   }

   public boolean isCancelled() {
      return this.getPlayer().getHealth() > 0.0D;
   }

   public void setCancelled(boolean cancel) {
      Player player = this.getPlayer();
      if (cancel) {
         player.setHealth(player.getMaxHealth());
      } else {
         player.setHealth(0.0D);
      }

   }

   public boolean hasDamagers() {
      return this.lastDamager != null;
   }
}
