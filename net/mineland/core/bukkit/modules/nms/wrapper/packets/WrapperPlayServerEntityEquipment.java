package net.mineland.core.bukkit.modules.nms.wrapper.packets;

import com.comphenix.protocol.PacketType;
import com.comphenix.protocol.PacketType.Play.Server;
import com.comphenix.protocol.events.PacketContainer;
import com.comphenix.protocol.events.PacketEvent;
import com.comphenix.protocol.wrappers.EnumWrappers.ItemSlot;
import org.bukkit.World;
import org.bukkit.entity.Entity;
import org.bukkit.inventory.ItemStack;

public class WrapperPlayServerEntityEquipment extends AbstractPacket {
   public static final PacketType TYPE;

   public WrapperPlayServerEntityEquipment() {
      super(new PacketContainer(TYPE), TYPE);
      this.handle.getModifier().writeDefaults();
   }

   public WrapperPlayServerEntityEquipment(PacketContainer packet) {
      super(packet, TYPE);
   }

   public int getEntityID() {
      return (Integer)this.handle.getIntegers().read(0);
   }

   public void setEntityID(int value) {
      this.handle.getIntegers().write(0, value);
   }

   public Entity getEntity(World world) {
      return (Entity)this.handle.getEntityModifier(world).read(0);
   }

   public Entity getEntity(PacketEvent event) {
      return this.getEntity(event.getPlayer().getWorld());
   }

   public ItemSlot getSlot() {
      return (ItemSlot)this.handle.getItemSlots().read(0);
   }

   public void setSlot(ItemSlot value) {
      this.handle.getItemSlots().write(0, value);
   }

   public ItemStack getItem() {
      return (ItemStack)this.handle.getItemModifier().read(0);
   }

   public void setItem(ItemStack value) {
      this.handle.getItemModifier().write(0, value);
   }

   static {
      TYPE = Server.ENTITY_EQUIPMENT;
   }
}
