package net.mineland.core.bukkit.modules.permission;

import java.util.LinkedList;
import net.mineland.core.bukkit.modules.user.User;
import org.bukkit.entity.Player;
import ua.govnojon.libs.myjava.ElementMap;

public class PermissionMap<V> {
   private LinkedList<ElementMap<String, V>> data = new LinkedList();

   public void put(String perm, V object) {
      this.data.addFirst(new ElementMap(perm, object));
   }

   public V get(Player player) {
      ElementMap<String, V> e = (ElementMap)this.data.stream().filter((el) -> {
         return player.hasPermission((String)el.getKey());
      }).findFirst().orElse((Object)null);
      return e == null ? null : e.getValue();
   }

   public void clear() {
      this.data.clear();
   }

   public V get(User user, V def) {
      V v = this.get(user.getPlayer());
      return v == null ? def : v;
   }
}
