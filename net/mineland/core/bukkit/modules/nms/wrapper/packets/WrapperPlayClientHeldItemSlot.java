package net.mineland.core.bukkit.modules.nms.wrapper.packets;

import com.comphenix.protocol.PacketType;
import com.comphenix.protocol.PacketType.Play.Client;
import com.comphenix.protocol.events.PacketContainer;

public class WrapperPlayClientHeldItemSlot extends AbstractPacket {
   public static final PacketType TYPE;

   public WrapperPlayClientHeldItemSlot() {
      super(new PacketContainer(TYPE), TYPE);
      this.handle.getModifier().writeDefaults();
   }

   public WrapperPlayClientHeldItemSlot(PacketContainer packet) {
      super(packet, TYPE);
   }

   public int getSlot() {
      return (Integer)this.handle.getIntegers().read(0);
   }

   public void setSlot(int value) {
      this.handle.getIntegers().write(0, value);
   }

   static {
      TYPE = Client.HELD_ITEM_SLOT;
   }
}
