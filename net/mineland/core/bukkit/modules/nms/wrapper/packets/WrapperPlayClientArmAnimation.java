package net.mineland.core.bukkit.modules.nms.wrapper.packets;

import com.comphenix.protocol.PacketType;
import com.comphenix.protocol.PacketType.Play.Client;
import com.comphenix.protocol.events.PacketContainer;

public class WrapperPlayClientArmAnimation extends AbstractPacket {
   public static final PacketType TYPE;

   public WrapperPlayClientArmAnimation() {
      super(new PacketContainer(TYPE), TYPE);
      this.handle.getModifier().writeDefaults();
   }

   public WrapperPlayClientArmAnimation(PacketContainer packet) {
      super(packet, TYPE);
   }

   static {
      TYPE = Client.ARM_ANIMATION;
   }
}
