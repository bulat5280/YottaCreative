package net.mineland.core.bukkit.modules.nms.wrapper.packets;

import com.comphenix.protocol.PacketType;
import com.comphenix.protocol.PacketType.Login.Server;
import com.comphenix.protocol.events.PacketContainer;

public class WrapperLoginServerSetCompression extends AbstractPacket {
   public static final PacketType TYPE;

   public WrapperLoginServerSetCompression() {
      super(new PacketContainer(TYPE), TYPE);
      this.handle.getModifier().writeDefaults();
   }

   public WrapperLoginServerSetCompression(PacketContainer packet) {
      super(packet, TYPE);
   }

   public int getThreshold() {
      return (Integer)this.handle.getIntegers().read(0);
   }

   public void setThreshold(int value) {
      this.handle.getIntegers().write(0, value);
   }

   static {
      TYPE = Server.SET_COMPRESSION;
   }
}
