package net.mineland.core.bukkit.modules.user;

import java.util.LinkedList;
import java.util.List;
import java.util.concurrent.TimeUnit;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.player.AsyncPlayerPreLoginEvent;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerLoginEvent;
import org.bukkit.event.player.PlayerQuitEvent;
import org.bukkit.event.player.AsyncPlayerPreLoginEvent.Result;
import ua.govnojon.libs.bukkitutil.schedule.Schedule;

public class UserListener implements Listener {
   private List<UserListener.PlayerLoad> loads = new LinkedList();
   private ModuleUser moduleUser;

   UserListener(ModuleUser moduleUser) {
      this.moduleUser = moduleUser;
      Schedule.timer(() -> {
         long current = System.currentTimeMillis();
         this.loads.removeIf((playerLoad) -> {
            return current - playerLoad.time > 60000L;
         });
      }, 10L, 10L, TimeUnit.MINUTES);
   }

   @EventHandler(
      priority = EventPriority.LOWEST
   )
   public void on(AsyncPlayerPreLoginEvent event) {
      try {
         String name = event.getName();
         User user = new User(name);
         this.loads.add(new UserListener.PlayerLoad(name, user));
         AsyncUserPreLoginEvent joinEvent = new AsyncUserPreLoginEvent(event.getName(), event.getAddress(), event.getUniqueId(), user);
         joinEvent.setKickMessage(event.getKickMessage());
         joinEvent.setLoginResult(event.getLoginResult());
         Bukkit.getPluginManager().callEvent(joinEvent);
         event.setLoginResult(joinEvent.getLoginResult());
         event.setKickMessage(joinEvent.getKickMessage());
      } catch (Exception var5) {
         event.setLoginResult(Result.KICK_OTHER);
         event.setKickMessage("§c[Pre Login] Error load you data\n§8Exception: " + var5.getLocalizedMessage());
         var5.printStackTrace();
      }

   }

   @EventHandler(
      priority = EventPriority.LOWEST
   )
   public void onPlayerLoginEvent(PlayerLoginEvent event) {
      try {
         Player player = event.getPlayer();
         String name = player.getName();
         UserListener.PlayerLoad load = (UserListener.PlayerLoad)this.loads.stream().filter((playerLoad) -> {
            return playerLoad.name.equals(name);
         }).findFirst().orElse((Object)null);
         if (load == null) {
            event.setResult(org.bukkit.event.player.PlayerLoginEvent.Result.KICK_OTHER);
            event.setKickMessage("§c[Login] Error load you data\n§8PlayerLoad == null");
         } else {
            this.loads.remove(load);
            User user = load.user;
            user.setPlayer(event.getPlayer());
            this.moduleUser.getUserMap().put(player.getName(), user);
         }
      } catch (Exception var6) {
         event.setResult(org.bukkit.event.player.PlayerLoginEvent.Result.KICK_OTHER);
         event.setKickMessage("§c[Login] Error load you data\n§8Exception: " + var6.getLocalizedMessage());
         var6.printStackTrace();
      }

   }

   @EventHandler(
      priority = EventPriority.MONITOR
   )
   public void onQuit(PlayerQuitEvent event) {
      this.moduleUser.getUserMap().remove(event.getPlayer().getName());
   }

   @EventHandler(
      priority = EventPriority.HIGHEST
   )
   public void onJoin(PlayerJoinEvent event) {
      Player player = event.getPlayer();
      User user = User.getUser(player);
   }

   private static class PlayerLoad {
      private String name;
      private User user;
      private long time;

      private PlayerLoad(String name, User user) {
         this.time = System.currentTimeMillis();
         this.name = name;
         this.user = user;
      }

      // $FF: synthetic method
      PlayerLoad(String x0, User x1, Object x2) {
         this(x0, x1);
      }
   }
}
