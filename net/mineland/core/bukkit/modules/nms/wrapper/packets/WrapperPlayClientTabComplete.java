package net.mineland.core.bukkit.modules.nms.wrapper.packets;

import com.comphenix.protocol.PacketType;
import com.comphenix.protocol.PacketType.Play.Client;
import com.comphenix.protocol.events.PacketContainer;
import com.comphenix.protocol.wrappers.BlockPosition;

public class WrapperPlayClientTabComplete extends AbstractPacket {
   public static final PacketType TYPE;

   public WrapperPlayClientTabComplete() {
      super(new PacketContainer(TYPE), TYPE);
      this.handle.getModifier().writeDefaults();
   }

   public WrapperPlayClientTabComplete(PacketContainer packet) {
      super(packet, TYPE);
   }

   public String getText() {
      return (String)this.handle.getStrings().read(0);
   }

   public void setText(String value) {
      this.handle.getStrings().write(0, value);
   }

   public BlockPosition getHasPosition() {
      return (BlockPosition)this.handle.getBlockPositionModifier().read(0);
   }

   public void setHasPosition(BlockPosition value) {
      this.handle.getBlockPositionModifier().write(0, value);
   }

   public BlockPosition getLookedAtBlock() {
      return (BlockPosition)this.handle.getBlockPositionModifier().read(0);
   }

   public void setLookedAtBlock(BlockPosition value) {
      this.handle.getBlockPositionModifier().write(0, value);
   }

   static {
      TYPE = Client.TAB_COMPLETE;
   }
}
