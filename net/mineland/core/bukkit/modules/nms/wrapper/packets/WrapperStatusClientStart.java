package net.mineland.core.bukkit.modules.nms.wrapper.packets;

import com.comphenix.protocol.PacketType;
import com.comphenix.protocol.PacketType.Status.Client;
import com.comphenix.protocol.events.PacketContainer;

public class WrapperStatusClientStart extends AbstractPacket {
   public static final PacketType TYPE;

   public WrapperStatusClientStart() {
      super(new PacketContainer(TYPE), TYPE);
      this.handle.getModifier().writeDefaults();
   }

   public WrapperStatusClientStart(PacketContainer packet) {
      super(packet, TYPE);
   }

   static {
      TYPE = Client.START;
   }
}
