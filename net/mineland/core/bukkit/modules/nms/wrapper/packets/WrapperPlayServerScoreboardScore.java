package net.mineland.core.bukkit.modules.nms.wrapper.packets;

import com.comphenix.protocol.PacketType;
import com.comphenix.protocol.PacketType.Play.Server;
import com.comphenix.protocol.events.PacketContainer;
import com.comphenix.protocol.wrappers.EnumWrappers.ScoreboardAction;

public class WrapperPlayServerScoreboardScore extends AbstractPacket {
   public static final PacketType TYPE;

   public WrapperPlayServerScoreboardScore() {
      super(new PacketContainer(TYPE), TYPE);
      this.handle.getModifier().writeDefaults();
   }

   public WrapperPlayServerScoreboardScore(PacketContainer packet) {
      super(packet, TYPE);
   }

   public String getScoreName() {
      return (String)this.handle.getStrings().read(0);
   }

   public void setScoreName(String value) {
      this.handle.getStrings().write(0, value);
   }

   public String getObjectiveName() {
      return (String)this.handle.getStrings().read(1);
   }

   public void setObjectiveName(String value) {
      this.handle.getStrings().write(1, value);
   }

   public int getValue() {
      return (Integer)this.handle.getIntegers().read(0);
   }

   public void setValue(int value) {
      this.handle.getIntegers().write(0, value);
   }

   public ScoreboardAction getAction() {
      return (ScoreboardAction)this.handle.getScoreboardActions().read(0);
   }

   public void setScoreboardAction(ScoreboardAction value) {
      this.handle.getScoreboardActions().write(0, value);
   }

   static {
      TYPE = Server.SCOREBOARD_SCORE;
   }
}
