package net.mineland.core.bukkit.modules.nms.wrapper.packets;

import com.comphenix.protocol.PacketType;
import com.comphenix.protocol.PacketType.Play.Server;
import com.comphenix.protocol.events.PacketContainer;

public class WrapperPlayServerWindowData extends AbstractPacket {
   public static final PacketType TYPE;

   public WrapperPlayServerWindowData() {
      super(new PacketContainer(TYPE), TYPE);
      this.handle.getModifier().writeDefaults();
   }

   public WrapperPlayServerWindowData(PacketContainer packet) {
      super(packet, TYPE);
   }

   public int getWindowId() {
      return (Integer)this.handle.getIntegers().read(0);
   }

   public void setWindowId(int value) {
      this.handle.getIntegers().write(0, value);
   }

   public int getProperty() {
      return (Integer)this.handle.getIntegers().read(1);
   }

   public void setProperty(int value) {
      this.handle.getIntegers().write(1, value);
   }

   public int getValue() {
      return (Integer)this.handle.getIntegers().read(2);
   }

   public void setValue(int value) {
      this.handle.getIntegers().write(2, value);
   }

   static {
      TYPE = Server.WINDOW_DATA;
   }
}
