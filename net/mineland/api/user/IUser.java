package net.mineland.api.user;

import java.util.Map;
import java.util.function.Consumer;
import java.util.function.Supplier;
import java.util.stream.Stream;
import javax.annotation.Nullable;
import net.mineland.api.MLAPI;
import org.bukkit.entity.Player;
import ua.govnojon.libs.myjava.AsyncLoader;

public interface IUser {
   static Stream<IUser> getUsers() {
      return MLAPI.getUserManager().getUsers();
   }

   @Nullable
   static IUser getUser(String name) {
      return (IUser)getUsers().filter((user) -> {
         return user.getName().equalsIgnoreCase(name);
      }).findAny().orElse((Object)null);
   }

   static IUser getUser(Player player) {
      return MLAPI.getUserManager().getUser(player);
   }

   default <T> T getMetadata(String key, Class<T> c) {
      return this.getMetadata(key);
   }

   default <T> T getMetadata(String key, Supplier<T> supplier) {
      return this.getMetadata().computeIfAbsent(key, (k) -> {
         return supplier.get();
      });
   }

   default <T> T removeMetadata(String key) {
      return this.getMetadata().remove(key);
   }

   default <T> T getMetadata(String key) {
      return this.getMetadata().get(key);
   }

   default Object setMetadata(String key, Object object) {
      return this.getMetadata().put(key, object);
   }

   default <T> T removeMetadata(String key, Class<T> c) {
      return this.getMetadata().remove(key);
   }

   default boolean hasMetadata(String key) {
      return this.getMetadata().containsKey(key);
   }

   default <T> void getMetadata(String key, Consumer<T> consumer, Consumer<Consumer<T>> load) {
      this.getAsyncLoader().loadOrGet(key, consumer, load);
   }

   void connect(String var1);

   void connect(String var1, int var2);

   void connect(int var1);

   void connect(int var1, int var2);

   Map<String, Object> getMetadata();

   boolean hasPermission(@Nullable String var1);

   AsyncLoader<String, Object> getAsyncLoader();

   void sendMessage(String var1, String... var2);

   default String getDisplayName() {
      return "prefix" + this.getName();
   }

   String getName();

   default Player getPlayer() {
      throw new UnsupportedOperationException();
   }
}
