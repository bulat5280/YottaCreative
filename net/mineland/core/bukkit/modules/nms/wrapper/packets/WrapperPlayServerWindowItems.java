package net.mineland.core.bukkit.modules.nms.wrapper.packets;

import com.comphenix.protocol.PacketType;
import com.comphenix.protocol.PacketType.Play.Server;
import com.comphenix.protocol.events.PacketContainer;
import java.util.List;
import org.bukkit.inventory.ItemStack;

public class WrapperPlayServerWindowItems extends AbstractPacket {
   public static final PacketType TYPE;

   public WrapperPlayServerWindowItems() {
      super(new PacketContainer(TYPE), TYPE);
      this.handle.getModifier().writeDefaults();
   }

   public WrapperPlayServerWindowItems(PacketContainer packet) {
      super(packet, TYPE);
   }

   public int getWindowId() {
      return (Integer)this.handle.getIntegers().read(0);
   }

   public void setWindowId(int value) {
      this.handle.getIntegers().write(0, value);
   }

   public List<ItemStack> getSlotData() {
      return (List)this.handle.getItemListModifier().read(0);
   }

   public void setSlotData(List<ItemStack> value) {
      this.handle.getItemListModifier().write(0, value);
   }

   static {
      TYPE = Server.WINDOW_ITEMS;
   }
}
