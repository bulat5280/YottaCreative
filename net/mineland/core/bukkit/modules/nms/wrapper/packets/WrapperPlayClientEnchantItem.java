package net.mineland.core.bukkit.modules.nms.wrapper.packets;

import com.comphenix.protocol.PacketType;
import com.comphenix.protocol.PacketType.Play.Client;
import com.comphenix.protocol.events.PacketContainer;

public class WrapperPlayClientEnchantItem extends AbstractPacket {
   public static final PacketType TYPE;

   public WrapperPlayClientEnchantItem() {
      super(new PacketContainer(TYPE), TYPE);
      this.handle.getModifier().writeDefaults();
   }

   public WrapperPlayClientEnchantItem(PacketContainer packet) {
      super(packet, TYPE);
   }

   public int getWindowId() {
      return (Integer)this.handle.getIntegers().read(0);
   }

   public void setWindowId(byte value) {
      this.handle.getIntegers().write(0, Integer.valueOf(value));
   }

   public int getEnchantment() {
      return (Integer)this.handle.getIntegers().read(1);
   }

   public void setEnchantment(int value) {
      this.handle.getIntegers().write(1, value);
   }

   static {
      TYPE = Client.ENCHANT_ITEM;
   }
}
