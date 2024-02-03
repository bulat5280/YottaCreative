package net.mineland.core.bukkit.modules.nms.wrapper.packets;

import com.comphenix.protocol.PacketType;
import com.comphenix.protocol.PacketType.Play.Client;
import com.comphenix.protocol.events.PacketContainer;
import com.comphenix.protocol.wrappers.BlockPosition;
import com.comphenix.protocol.wrappers.EnumWrappers.Direction;
import com.comphenix.protocol.wrappers.EnumWrappers.Hand;

public class WrapperPlayClientUseItem extends AbstractPacket {
   public static final PacketType TYPE;

   public WrapperPlayClientUseItem() {
      super(new PacketContainer(TYPE), TYPE);
      this.handle.getModifier().writeDefaults();
   }

   public WrapperPlayClientUseItem(PacketContainer packet) {
      super(packet, TYPE);
   }

   public BlockPosition getLocation() {
      return (BlockPosition)this.handle.getBlockPositionModifier().read(0);
   }

   public void setLocation(BlockPosition value) {
      this.handle.getBlockPositionModifier().write(0, value);
   }

   public Direction getFace() {
      return (Direction)this.handle.getDirections().read(0);
   }

   public void setFace(Direction value) {
      this.handle.getDirections().write(0, value);
   }

   public Hand getHand() {
      return (Hand)this.handle.getHands().read(0);
   }

   public void setHand(Hand value) {
      this.handle.getHands().write(0, value);
   }

   public float getCursorPositionX() {
      return (Float)this.handle.getFloat().read(0);
   }

   public void setCursorPositionX(float value) {
      this.handle.getFloat().write(0, value);
   }

   public float getCursorPositionY() {
      return (Float)this.handle.getFloat().read(1);
   }

   public void setCursorPositionY(float value) {
      this.handle.getFloat().write(1, value);
   }

   public float getCursorPositionZ() {
      return (Float)this.handle.getFloat().read(2);
   }

   public void setCursorPositionZ(float value) {
      this.handle.getFloat().write(2, value);
   }

   static {
      TYPE = Client.USE_ITEM;
   }
}
