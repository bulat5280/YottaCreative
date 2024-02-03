package net.mineland.api.user;

import java.util.stream.Stream;
import org.bukkit.entity.Player;

public interface UserManager {
   default Stream<IUser> getUsers() {
      throw new UnsupportedOperationException();
   }

   default IUser getUser(Player player) {
      throw new UnsupportedOperationException();
   }
}
