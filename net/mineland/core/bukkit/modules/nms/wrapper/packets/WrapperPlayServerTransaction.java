package net.mineland.core.bukkit.modules.nms.wrapper.packets;

import com.comphenix.protocol.PacketType;
import com.comphenix.protocol.PacketType.Play.Server;
import com.comphenix.protocol.events.PacketContainer;

public class WrapperPlayServerTransaction extends AbstractPacket {
   public static final PacketType TYPE;

   public WrapperPlayServerTransaction() {
      super(new PacketContainer(TYPE), TYPE);
      this.handle.getModifier().writeDefaults();
   }

   public WrapperPlayServerTransaction(PacketContainer packet) {
      super(packet, TYPE);
   }

   public int getWindowId() {
      return (Integer)this.handle.getIntegers().read(0);
   }

   public void setWindowId(int value) {
      this.handle.getIntegers().write(0, value);
   }

   public short getActionNumber() {
      return (Short)this.handle.getShorts().read(0);
   }

   public void setActionNumber(short value) {
      this.handle.getShorts().write(0, value);
   }

   public boolean getAccepted() {
      return (Boolean)this.handle.getBooleans().read(0);
   }

   public void setAccepted(boolean value) {
      this.handle.getBooleans().write(0, value);
   }

   static {
      TYPE = Server.TRANSACTION;
   }
}
