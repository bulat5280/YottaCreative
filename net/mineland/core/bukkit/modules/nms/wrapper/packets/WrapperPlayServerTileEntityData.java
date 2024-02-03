package net.mineland.core.bukkit.modules.nms.wrapper.packets;

import com.comphenix.protocol.PacketType;
import com.comphenix.protocol.PacketType.Play.Server;
import com.comphenix.protocol.events.PacketContainer;
import com.comphenix.protocol.wrappers.BlockPosition;
import com.comphenix.protocol.wrappers.nbt.NbtBase;

public class WrapperPlayServerTileEntityData extends AbstractPacket {
   public static final PacketType TYPE;

   public WrapperPlayServerTileEntityData() {
      super(new PacketContainer(TYPE), TYPE);
      this.handle.getModifier().writeDefaults();
   }

   public WrapperPlayServerTileEntityData(PacketContainer packet) {
      super(packet, TYPE);
   }

   public BlockPosition getLocation() {
      return (BlockPosition)this.handle.getBlockPositionModifier().read(0);
   }

   public void setLocation(BlockPosition value) {
      this.handle.getBlockPositionModifier().write(0, value);
   }

   public int getAction() {
      return (Integer)this.handle.getIntegers().read(0);
   }

   public void setAction(int value) {
      this.handle.getIntegers().write(0, value);
   }

   public NbtBase<?> getNbtData() {
      return (NbtBase)this.handle.getNbtModifier().read(0);
   }

   public void setNbtData(NbtBase<?> value) {
      this.handle.getNbtModifier().write(0, value);
   }

   static {
      TYPE = Server.TILE_ENTITY_DATA;
   }
}
