package net.mineland.creative.modules.coding.actions;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;
import net.mineland.core.bukkit.modules.user.User;
import net.mineland.creative.modules.coding.events.DamageEvent;
import net.mineland.creative.modules.coding.events.GameEvent;
import net.mineland.creative.modules.coding.events.KillEvent;
import org.bukkit.Material;
import org.bukkit.entity.Entity;
import ua.govnojon.libs.bukkitutil.ItemData;
import ua.govnojon.libs.myjava.RandomUtil;

public enum PlayerSelection {
   SELECTION(new ItemData(Material.NETHER_STAR)),
   DEFAULT(new ItemData(Material.POTATO_ITEM)),
   RANDOM(new ItemData(Material.EYE_OF_ENDER)),
   ALL(new ItemData(Material.BEACON)),
   KILLER(new ItemData(Material.DIAMOND_SWORD)),
   DAMAGER(new ItemData(Material.IRON_SWORD)),
   SHOOTER(new ItemData(Material.BOW)),
   VICTIM(new ItemData(Material.SKULL_ITEM));

   private ItemData icon;

   private PlayerSelection(ItemData itemData) {
      this.icon = itemData;
   }

   public ItemData getIcon() {
      return this.icon;
   }

   public List<Entity> select(List<Entity> currentSelection, GameEvent gameEvent) {
      Entity shooter;
      switch(this) {
      case SELECTION:
         return currentSelection;
      case RANDOM:
         return Collections.singletonList(RandomUtil.getRandomObject((List)gameEvent.getPlot().getOnlinePlayers().stream().map(User::getPlayer).collect(Collectors.toList())));
      case ALL:
         return (List)gameEvent.getPlot().getOnlinePlayers().stream().map(User::getPlayer).collect(Collectors.toList());
      case KILLER:
         if (gameEvent instanceof KillEvent) {
            shooter = ((KillEvent)gameEvent).getKiller();
            if (shooter != null) {
               return Collections.singletonList(shooter);
            }

            return new ArrayList();
         }

         return new ArrayList();
      case DAMAGER:
         if (gameEvent instanceof DamageEvent) {
            shooter = ((DamageEvent)gameEvent).getDamager();
            if (shooter != null) {
               return Collections.singletonList(shooter);
            }

            return new ArrayList();
         }

         return new ArrayList();
      case SHOOTER:
         if (gameEvent instanceof DamageEvent) {
            shooter = ((DamageEvent)gameEvent).getShooter();
            if (shooter != null) {
               return Collections.singletonList(shooter);
            }

            return new ArrayList();
         }

         return new ArrayList();
      case VICTIM:
         shooter = gameEvent instanceof DamageEvent ? ((DamageEvent)gameEvent).getVictim() : (gameEvent instanceof KillEvent ? ((KillEvent)gameEvent).getVictim() : null);
         if (shooter != null) {
            return Collections.singletonList(shooter);
         }

         return new ArrayList();
      case DEFAULT:
         return Collections.singletonList(gameEvent.getDefaultEntity());
      default:
         return currentSelection;
      }
   }
}
