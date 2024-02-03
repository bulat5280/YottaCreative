package net.mineland.core.bukkit.modules.nms.wrapper.packets;

import com.comphenix.protocol.PacketType;
import com.comphenix.protocol.PacketType.Play.Server;
import com.comphenix.protocol.events.PacketContainer;
import org.bukkit.inventory.ItemStack;

public class WrapperPlayServerSetSlot extends AbstractPacket {
   public static final PacketType TYPE;

   public WrapperPlayServerSetSlot() {
      super(new PacketContainer(TYPE), TYPE);
      this.handle.getModifier().writeDefaults();
   }

   public WrapperPlayServerSetSlot(PacketContainer packet) {
      super(packet, TYPE);
   }

   public int getWindowId() {
      return (Integer)this.handle.getIntegers().read(0);
   }

   public void setWindowId(int value) {
      this.handle.getIntegers().write(0, value);
   }

   public int getSlot() {
      return (Integer)this.handle.getIntegers().read(1);
   }

   public void setSlot(int value) {
      this.handle.getIntegers().write(1, value);
   }

   public ItemStack getSlotData() {
      return (ItemStack)this.handle.getItemModifier().read(0);
   }

   public void setSlotData(ItemStack value) {
      this.handle.getItemModifier().write(0, value);
   }

   static {
      TYPE = Server.SET_SLOT;
   }
}
