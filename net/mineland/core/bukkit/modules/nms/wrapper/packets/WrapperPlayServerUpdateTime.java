package net.mineland.core.bukkit.modules.nms.wrapper.packets;

import com.comphenix.protocol.PacketType;
import com.comphenix.protocol.PacketType.Play.Server;
import com.comphenix.protocol.events.PacketContainer;

public class WrapperPlayServerUpdateTime extends AbstractPacket {
   public static final PacketType TYPE;

   public WrapperPlayServerUpdateTime() {
      super(new PacketContainer(TYPE), TYPE);
      this.handle.getModifier().writeDefaults();
   }

   public WrapperPlayServerUpdateTime(PacketContainer packet) {
      super(packet, TYPE);
   }

   public long getAgeOfTheWorld() {
      return (Long)this.handle.getLongs().read(0);
   }

   public void setAgeOfTheWorld(long value) {
      this.handle.getLongs().write(0, value);
   }

   public long getTimeOfDay() {
      return (Long)this.handle.getLongs().read(1);
   }

   public void setTimeOfDay(long value) {
      this.handle.getLongs().write(1, value);
   }

   static {
      TYPE = Server.UPDATE_TIME;
   }
}
