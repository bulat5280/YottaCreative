package net.mineland.core.bukkit.module.event;

import com.comphenix.protocol.ProtocolLibrary;
import com.comphenix.protocol.events.PacketAdapter;
import net.mineland.core.bukkit.module.Module;

public class ModuleDataProtocolLibListener implements ModuleData {
   public void register(Module module, Object o) {
      PacketAdapter adapter = (PacketAdapter)o;
      ProtocolLibrary.getProtocolManager().addPacketListener(adapter);
   }

   public void unregister(Module module, Object o) {
      PacketAdapter adapter = (PacketAdapter)o;
      ProtocolLibrary.getProtocolManager().removePacketListener(adapter);
   }
}
