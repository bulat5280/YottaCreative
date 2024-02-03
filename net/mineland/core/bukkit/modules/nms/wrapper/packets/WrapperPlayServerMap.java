package net.mineland.core.bukkit.modules.nms.wrapper.packets;

import com.comphenix.protocol.PacketType;
import com.comphenix.protocol.PacketType.Play.Server;
import com.comphenix.protocol.events.PacketContainer;

public class WrapperPlayServerMap extends AbstractPacket {
   public static final PacketType TYPE;

   public WrapperPlayServerMap() {
      super(new PacketContainer(TYPE), TYPE);
      this.handle.getModifier().writeDefaults();
   }

   public WrapperPlayServerMap(PacketContainer packet) {
      super(packet, TYPE);
   }

   public int getItemDamage() {
      return (Integer)this.handle.getIntegers().read(0);
   }

   public void setItemDamage(int value) {
      this.handle.getIntegers().write(0, value);
   }

   public byte getScale() {
      return (Byte)this.handle.getBytes().read(0);
   }

   public void setScale(byte value) {
      this.handle.getBytes().write(0, value);
   }

   public boolean getTrackingPosition() {
      return (Boolean)this.handle.getBooleans().read(0);
   }

   public void setTrackingPosition(boolean value) {
      this.handle.getBooleans().write(0, value);
   }

   public Object[] getMapIcons() {
      return (Object[])((Object[])this.handle.getModifier().read(3));
   }

   public void setMapIcons(Object[] value) {
      this.handle.getModifier().write(3, value);
   }

   public int getColumns() {
      return (Integer)this.handle.getIntegers().read(3);
   }

   public void setColumns(int value) {
      this.handle.getIntegers().write(3, value);
   }

   public int getRows() {
      return (Integer)this.handle.getIntegers().read(4);
   }

   public void setRows(int value) {
      this.handle.getIntegers().write(4, value);
   }

   public int getX() {
      return (Integer)this.handle.getIntegers().read(1);
   }

   public void setX(int value) {
      this.handle.getIntegers().write(1, value);
   }

   public int getZ() {
      return (Integer)this.handle.getIntegers().read(2);
   }

   public void setZ(int value) {
      this.handle.getIntegers().write(2, value);
   }

   public byte[] getData() {
      return (byte[])this.handle.getByteArrays().read(0);
   }

   public void setData(byte[] value) {
      this.handle.getByteArrays().write(0, value);
   }

   static {
      TYPE = Server.MAP;
   }
}
