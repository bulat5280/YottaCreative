package net.mineland.core.bukkit.modules.user;

import java.util.Objects;
import java.util.function.Consumer;
import javax.annotation.Nullable;
import ua.govnojon.libs.bukkitutil.PlayerUtil;

public class PlayerId implements GetterUser {
   private String name;

   public PlayerId(String name) {
      this.name = name;
   }

   public PlayerId(User user) {
      this.name = user.getName();
   }

   public String getName() {
      return this.name;
   }

   @Nullable
   public Lang getLang() {
      return Lang.RU;
   }

   @Nullable
   public User getUser() {
      return User.getUser(this.name);
   }

   public void getDisplayName(Consumer<String> name) {
      PlayerUtil.getDisplayName(this, name);
   }

   public int hashCode() {
      return Objects.hash(new Object[]{this.name});
   }

   public boolean equals(Object o) {
      if (this == o) {
         return true;
      } else if (o != null && this.getClass() == o.getClass()) {
         PlayerId playerId = (PlayerId)o;
         return Objects.equals(this.name, playerId.name);
      } else {
         return false;
      }
   }

   public String toString() {
      return "PlayerId{name='" + this.name + '\'' + '}';
   }
}
