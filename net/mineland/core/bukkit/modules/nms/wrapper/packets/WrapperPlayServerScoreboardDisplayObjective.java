package net.mineland.core.bukkit.modules.nms.wrapper.packets;

import com.comphenix.protocol.PacketType;
import com.comphenix.protocol.PacketType.Play.Server;
import com.comphenix.protocol.events.PacketContainer;

public class WrapperPlayServerScoreboardDisplayObjective extends AbstractPacket {
   public static final PacketType TYPE;

   public WrapperPlayServerScoreboardDisplayObjective() {
      super(new PacketContainer(TYPE), TYPE);
      this.handle.getModifier().writeDefaults();
   }

   public WrapperPlayServerScoreboardDisplayObjective(PacketContainer packet) {
      super(packet, TYPE);
   }

   public int getPosition() {
      return (Integer)this.handle.getIntegers().read(0);
   }

   public void setPosition(int value) {
      this.handle.getIntegers().write(0, value);
   }

   public String getScoreName() {
      return (String)this.handle.getStrings().read(0);
   }

   public void setScoreName(String value) {
      this.handle.getStrings().write(0, value);
   }

   static {
      TYPE = Server.SCOREBOARD_DISPLAY_OBJECTIVE;
   }
}
