package net.mineland.core.bukkit.modules.nms.wrapper.packets;

import com.comphenix.protocol.PacketType;
import com.comphenix.protocol.PacketType.Play.Server;
import com.comphenix.protocol.events.PacketContainer;
import com.comphenix.protocol.events.PacketEvent;
import org.bukkit.World;
import org.bukkit.entity.Entity;
import org.bukkit.potion.PotionEffectType;

public class WrapperPlayServerRemoveEntityEffect extends AbstractPacket {
   public static final PacketType TYPE;

   public WrapperPlayServerRemoveEntityEffect() {
      super(new PacketContainer(TYPE), TYPE);
      this.handle.getModifier().writeDefaults();
   }

   public WrapperPlayServerRemoveEntityEffect(PacketContainer packet) {
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

   public PotionEffectType getEffect() {
      return (PotionEffectType)this.handle.getEffectTypes().read(0);
   }

   public void setEffect(PotionEffectType value) {
      this.handle.getEffectTypes().write(0, value);
   }

   static {
      TYPE = Server.REMOVE_ENTITY_EFFECT;
   }
}
