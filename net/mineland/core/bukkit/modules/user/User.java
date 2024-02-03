package net.mineland.core.bukkit.modules.user;

import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.stream.Collectors;
import java.util.stream.Stream;
import net.mineland.api.MLAPI;
import net.mineland.api.user.IUser;
import net.mineland.core.bukkit.module.Module;
import net.mineland.core.bukkit.modules.message.Message;
import org.bukkit.Bukkit;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.event.player.PlayerEvent;
import ua.govnojon.libs.myjava.AsyncLoader;

public class User implements IUser {
   private static ModuleUser moduleUser = (ModuleUser)Module.getInstance(ModuleUser.class);
   private Player player;
   private String name;
   private Map<String, Object> metadata = new HashMap();
   private AsyncLoader<String, Object> asyncLoader;

   public User(String name) {
      this.asyncLoader = new AsyncLoader(this.metadata);
      this.name = name;
   }

   public static User getUser(Player player) {
      return (User)IUser.getUser(player);
   }

   public static User getUser(String name) {
      return (User)IUser.getUser(name);
   }

   public static Player getPlayer(String name) {
      return (Player)getUsers().stream().filter((it) -> {
         return it.getPlayer().getName().equalsIgnoreCase(name);
      }).map(User::getPlayer).findFirst().orElse((Object)null);
   }

   public static User getUser(CommandSender player) {
      return (User)MLAPI.getUserManager().getUser((Player)player);
   }

   public static User getUser(PlayerEvent event) {
      return getUser(event.getPlayer());
   }

   public static List<Player> getPlayers() {
      return (List)Bukkit.getOnlinePlayers();
   }

   public static Collection<User> getUsers() {
      Stream var10000 = MLAPI.getUserManager().getUsers();
      User.class.getClass();
      return (Collection)var10000.map(User.class::cast).collect(Collectors.toList());
   }

   public static ModuleUser getModuleUser() {
      return moduleUser;
   }

   public static Collection<User> getUsers(Collection<Player> players) {
      return (Collection)players.stream().map(User::getUser).collect(Collectors.toList());
   }

   public static List<String> getPlayersNames() {
      return (List)getUsers().stream().filter((user) -> {
         return !user.hasMetadata("vanish");
      }).map((user) -> {
         return user.getPlayer().getName();
      }).collect(Collectors.toList());
   }

   public void connect(String serverName) {
   }

   public void connect(String serverName, int serverNumber) {
   }

   public void connect(int serverID) {
   }

   public void connect(int serverID, int serverNumber) {
   }

   public Map<String, Object> getMetadata() {
      return this.metadata;
   }

   public AsyncLoader<String, Object> getAsyncLoader() {
      return this.asyncLoader;
   }

   public boolean equals(Object o) {
      if (this == o) {
         return true;
      } else if (o != null && this.getClass() == o.getClass()) {
         User user = (User)o;
         return Objects.equals(this.name, user.name);
      } else {
         return false;
      }
   }

   public int hashCode() {
      return Objects.hash(new Object[]{this.name});
   }

   public void sendMessage(String key, String... replaced) {
      Message.sendMessage(this, key, replaced);
   }

   public User getActiveUser() {
      return getUser(this.name);
   }

   public boolean isActive() {
      return this == this.getActiveUser();
   }

   public String getName() {
      return this.name;
   }

   public Player getPlayer() {
      return this.player;
   }

   public void setPlayer(Player player) {
      this.player = player;
      player.setDisplayName(this.getDisplayName());
   }

   public Lang getLang() {
      return Lang.RU;
   }

   public boolean hasPermission(String perm) {
      return perm != null && !perm.isEmpty() ? this.player.hasPermission(perm.toLowerCase()) : true;
   }

   public String toString() {
      return "User{name='" + this.name + '\'' + '}';
   }
}
