package net.mineland.core.bukkit.modules.user;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.function.Consumer;
import java.util.stream.Collectors;
import java.util.stream.Stream;
import net.mineland.api.MLAPI;
import net.mineland.api.user.IUser;
import net.mineland.api.user.UserManager;
import net.mineland.core.bukkit.LibsPlugin;
import net.mineland.core.bukkit.module.BukkitModule;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.plugin.Plugin;
import ua.govnojon.libs.config.Config;

public class ModuleUser extends BukkitModule implements UserManager {
   private static ModuleUser instance;
   private UserListener userListener;
   private Map<String, User> userMap = new HashMap();

   public ModuleUser(int priority, Plugin plugin) {
      super("user", priority, plugin, (Config)null);
      instance = this;
      MLAPI.setUserManager(this);
   }

   public static ModuleUser getInstance() {
      return instance;
   }

   public void onEnable() {
      this.registerData(this.userListener = new UserListener(this));
      Bukkit.getMessenger().registerOutgoingPluginChannel(LibsPlugin.getInstance(), "ChoseRegistrationOut");
   }

   public void onDisable() {
      this.unregisterData(this.userListener);
      Bukkit.getMessenger().unregisterOutgoingPluginChannel(LibsPlugin.getInstance(), "ChoseRegistrationOut");
   }

   public Stream<IUser> getUsers() {
      return Bukkit.getOnlinePlayers().stream().map(this::getUser);
   }

   public IUser getUser(Player player) {
      return player == null ? null : (IUser)this.userMap.get(player.getName());
   }

   public void getPlayerDatas(List<String> ids, Consumer<List<PlayerId>> consumer) {
      consumer.accept(ids.stream().map(PlayerId::new).collect(Collectors.toList()));
   }

   public void getPlayerData(String id, Consumer<PlayerId> consumer) {
      consumer.accept(new PlayerId(id));
   }

   public Map<String, User> getUserMap() {
      return this.userMap;
   }
}
