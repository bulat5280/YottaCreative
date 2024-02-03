package net.mineland.core.bukkit.module.event;

import com.comphenix.protocol.events.PacketAdapter;
import org.bukkit.event.Listener;

public enum ModuleDataType {
   BUKKIT_LISTENER(Listener.class, new ModuleDataBukkitListener()),
   PROTOCOL_LIB_LISTENER(PacketAdapter.class, new ModuleDataProtocolLibListener());

   private Class type;
   private ModuleData control;

   private ModuleDataType(Class type, ModuleData control) {
      this.type = type;
      this.control = control;
   }

   public static ModuleDataType getTypeByClass(Object o) {
      ModuleDataType[] var1 = values();
      int var2 = var1.length;

      for(int var3 = 0; var3 < var2; ++var3) {
         ModuleDataType type = var1[var3];
         if (type.type.isInstance(o)) {
            return type;
         }
      }

      return null;
   }

   public ModuleData getController() {
      return this.control;
   }
}
