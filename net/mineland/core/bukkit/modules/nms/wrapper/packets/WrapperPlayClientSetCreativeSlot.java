package net.mineland.core.bukkit.modules.nms.wrapper.packets;

import com.comphenix.protocol.PacketType;
import com.comphenix.protocol.PacketType.Play.Client;
import com.comphenix.protocol.events.PacketContainer;
import org.bukkit.inventory.ItemStack;

public class WrapperPlayClientSetCreativeSlot extends AbstractPacket {
   public static final PacketType TYPE;

   public WrapperPlayClientSetCreativeSlot() {
      super(new PacketContainer(TYPE), TYPE);
      this.handle.getModifier().writeDefaults();
   }

   public WrapperPlayClientSetCreativeSlot(PacketContainer packet) {
      super(packet, TYPE);
   }

   public int getSlot() {
      return (Integer)this.handle.getIntegers().read(0);
   }

   public void setSlot(int value) {
      this.handle.getIntegers().write(0, value);
   }

   public ItemStack getClickedItem() {
      return (ItemStack)this.handle.getItemModifier().read(0);
   }

   public void setClickedItem(ItemStack value) {
      this.handle.getItemModifier().write(0, value);
   }

   static {
      TYPE = Client.SET_CREATIVE_SLOT;
   }
}
