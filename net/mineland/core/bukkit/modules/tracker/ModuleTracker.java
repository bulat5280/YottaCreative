package net.mineland.core.bukkit.modules.tracker;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;
import net.mineland.core.bukkit.module.BukkitModule;
import net.mineland.core.bukkit.modules.myevents.PlayerMoveOnBlockMyEvent;
import net.mineland.core.bukkit.modules.user.User;
import org.bukkit.Location;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.player.PlayerChangedWorldEvent;
import org.bukkit.event.player.PlayerTeleportEvent;
import org.bukkit.plugin.Plugin;
import ua.govnojon.libs.bukkitutil.LocationUtil;
import ua.govnojon.libs.bukkitutil.schedule.Schedule;
import ua.govnojon.libs.config.Config;
import ua.govnojon.libs.myjava.Try;

public class ModuleTracker extends BukkitModule {
   private Map<Integer, List<TrackerPlayer>> trackers = new HashMap();

   public ModuleTracker(int priority, Plugin plugin) {
      super("tracker", priority, plugin, (Config)null);
   }

   public void onFirstEnable() {
      this.registerListenersThis();
      Schedule.timer(() -> {
         User.getUsers().forEach((user) -> {
            Try.ignore(() -> {
               this.move(user, user.getPlayer().getLocation());
            });
         });
      }, 2L, 2L, TimeUnit.SECONDS);
   }

   public void onEnable() {
   }

   public void onDisable() {
   }

   public TrackerPlayer registerTracker(int distance, TrackerPlayer tracker) {
      ((List)this.trackers.computeIfAbsent(distance, (key) -> {
         return new ArrayList();
      })).add(tracker);
      return tracker;
   }

   public void unregisterTracker(TrackerPlayer tracker) {
      List var10000 = (List)this.trackers.entrySet().stream().filter((e) -> {
         return ((List)e.getValue()).remove(tracker);
      }).filter((e) -> {
         return ((List)e.getValue()).isEmpty();
      }).map(Entry::getKey).collect(Collectors.toList());
      Map var10001 = this.trackers;
      var10000.forEach(var10001::remove);
   }

   @EventHandler(
      priority = EventPriority.MONITOR,
      ignoreCancelled = true
   )
   public void on(PlayerMoveOnBlockMyEvent event) {
      User user = event.getUser();
      Location to = event.getTo();
      this.move(user, to);
   }

   @EventHandler(
      priority = EventPriority.MONITOR,
      ignoreCancelled = true
   )
   public void on(PlayerTeleportEvent event) {
      if (!LocationUtil.isInOneBlock(event.getTo(), event.getFrom())) {
         this.move(User.getUser(event.getPlayer()), event.getTo());
      }

   }

   @EventHandler(
      priority = EventPriority.MONITOR
   )
   public void onPlayerChangedWorldEvent(PlayerChangedWorldEvent event) {
      User user = User.getUser(event.getPlayer());
      this.move(user, user.getPlayer().getLocation());
   }

   private void move(User user, Location to) {
      Map data = (Map)user.getMetadata("tracker-player", () -> {
         return new HashMap(this.trackers.size());
      });

      try {
         this.trackers.forEach((distance, trackersPlayer) -> {
            Location from = (Location)data.get(distance);
            if (from == null) {
               data.put(distance, to);
            } else if (!from.getWorld().equals(to.getWorld()) || LocationUtil.hasDistance(to, from, (double)distance)) {
               data.put(distance, to);
               Iterator var6 = trackersPlayer.iterator();

               while(var6.hasNext()) {
                  TrackerPlayer tracker = (TrackerPlayer)var6.next();
                  Try.ignore(() -> {
                     tracker.onMove(user, from, to);
                  });
               }
            }

         });
      } catch (NullPointerException var5) {
         this.getLogger().severe("to: " + to + " user: " + user + " data: " + data);
         var5.printStackTrace();
      }

   }
}
