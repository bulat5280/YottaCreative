package net.mineland.core.bukkit.modules.nms.wrapper.packets;

import com.comphenix.protocol.PacketType;
import com.comphenix.protocol.PacketType.Play.Client;
import com.comphenix.protocol.events.PacketContainer;
import com.comphenix.protocol.wrappers.EnumWrappers.Hand;

public class WrapperPlayClientBlockPlace extends AbstractPacket {
   public static final PacketType TYPE;

   public WrapperPlayClientBlockPlace() {
      super(new PacketContainer(TYPE), TYPE);
      this.handle.getModifier().writeDefaults();
   }

   public WrapperPlayClientBlockPlace(PacketContainer packet) {
      super(packet, TYPE);
   }

   public Hand getHand() {
      return (Hand)this.handle.getHands().read(0);
   }

   public void setHand(Hand value) {
      this.handle.getHands().write(0, value);
   }

   public long getTimestamp() {
      return (Long)this.handle.getLongs().read(0);
   }

   public void setTimestamp(long value) {
      this.handle.getLongs().write(0, value);
   }

   static {
      TYPE = Client.BLOCK_PLACE;
   }
}
