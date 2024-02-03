package net.mineland.core.bukkit.modules.user;

import org.bukkit.entity.Player;

public interface GetterUser {
   User getUser();

   default Player getPlayer() {
      User user = this.getUser();
      return user == null ? null : user.getPlayer();
   }

   default void save() {
   }
}
