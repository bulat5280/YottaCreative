package net.mineland.core.bukkit.modules.user;

import com.google.common.cache.RemovalListener;
import com.google.common.cache.RemovalNotification;
import org.jetbrains.annotations.NotNull;

public class RemovalListenerGetterUserSave implements RemovalListener<Object, Object> {
   public void onRemoval(@NotNull RemovalNotification<Object, Object> removal) {
      if (removal.getValue() instanceof GetterUser) {
         ((GetterUser)removal.getValue()).save();
      }

   }
}
