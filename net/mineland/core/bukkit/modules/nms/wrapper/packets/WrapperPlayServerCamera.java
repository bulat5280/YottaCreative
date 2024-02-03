package net.mineland.core.bukkit.modules.nms.wrapper.packets;

import com.comphenix.protocol.PacketType;
import com.comphenix.protocol.PacketType.Play.Server;
import com.comphenix.protocol.events.PacketContainer;

public class WrapperPlayServerCamera extends AbstractPacket {
   public static final PacketType TYPE;

   public WrapperPlayServerCamera() {
      super(new PacketContainer(TYPE), TYPE);
      this.handle.getModifier().writeDefaults();
   }

   public WrapperPlayServerCamera(PacketContainer packet) {
      super(packet, TYPE);
   }

   public int getCameraId() {
      return (Integer)this.handle.getIntegers().read(0);
   }

   public void setCameraId(int value) {
      this.handle.getIntegers().write(0, value);
   }

   static {
      TYPE = Server.CAMERA;
   }
}
