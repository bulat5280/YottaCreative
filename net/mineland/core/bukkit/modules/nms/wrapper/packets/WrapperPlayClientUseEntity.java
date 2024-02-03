package net.mineland.core.bukkit.modules.nms.wrapper.packets;

import com.comphenix.protocol.PacketType;
import com.comphenix.protocol.PacketType.Play.Client;
import com.comphenix.protocol.events.PacketContainer;
import com.comphenix.protocol.events.PacketEvent;
import com.comphenix.protocol.wrappers.EnumWrappers.EntityUseAction;
import org.bukkit.World;
import org.bukkit.entity.Entity;
import org.bukkit.util.Vector;

public class WrapperPlayClientUseEntity extends AbstractPacket {
   public static final PacketType TYPE;

   public WrapperPlayClientUseEntity() {
      super(new PacketContainer(TYPE), TYPE);
      this.handle.getModifier().writeDefaults();
   }

   public WrapperPlayClientUseEntity(PacketContainer packet) {
      super(packet, TYPE);
   }

   public int getTargetID() {
      return (Integer)this.handle.getIntegers().read(0);
   }

   public void setTargetID(int value) {
      this.handle.getIntegers().write(0, value);
   }

   public Entity getTarget(World world) {
      return (Entity)this.handle.getEntityModifier(world).read(0);
   }

   public Entity getTarget(PacketEvent event) {
      return this.getTarget(event.getPlayer().getWorld());
   }

   public EntityUseAction getType() {
      return (EntityUseAction)this.handle.getEntityUseActions().read(0);
   }

   public void setType(EntityUseAction value) {
      this.handle.getEntityUseActions().write(0, value);
   }

   public Vector getTargetVector() {
      return (Vector)this.handle.getVectors().read(0);
   }

   public void setTargetVector(Vector value) {
      this.handle.getVectors().write(0, value);
   }

   static {
      TYPE = Client.USE_ENTITY;
   }
}
