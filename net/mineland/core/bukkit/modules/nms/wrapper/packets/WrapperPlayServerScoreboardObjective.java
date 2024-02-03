package net.mineland.core.bukkit.modules.nms.wrapper.packets;

import com.comphenix.protocol.PacketType;
import com.comphenix.protocol.PacketType.Play.Server;
import com.comphenix.protocol.events.PacketContainer;
import com.comphenix.protocol.reflect.IntEnum;

public class WrapperPlayServerScoreboardObjective extends AbstractPacket {
   public static final PacketType TYPE;

   public WrapperPlayServerScoreboardObjective() {
      super(new PacketContainer(TYPE), TYPE);
      this.handle.getModifier().writeDefaults();
   }

   public WrapperPlayServerScoreboardObjective(PacketContainer packet) {
      super(packet, TYPE);
   }

   public String getName() {
      return (String)this.handle.getStrings().read(0);
   }

   public void setName(String value) {
      this.handle.getStrings().write(0, value);
   }

   public String getDisplayName() {
      return (String)this.handle.getStrings().read(1);
   }

   public void setDisplayName(String value) {
      this.handle.getStrings().write(1, value);
   }

   public WrapperPlayServerScoreboardObjective.HealthDisplay getHealthDisplay() {
      return (WrapperPlayServerScoreboardObjective.HealthDisplay)this.handle.getEnumModifier(WrapperPlayServerScoreboardObjective.HealthDisplay.class, 2).read(0);
   }

   public void setHealthDisplay(WrapperPlayServerScoreboardObjective.HealthDisplay value) {
      this.handle.getEnumModifier(WrapperPlayServerScoreboardObjective.HealthDisplay.class, 2).write(0, value);
   }

   public int getMode() {
      return (Integer)this.handle.getIntegers().read(0);
   }

   public void setMode(int value) {
      this.handle.getIntegers().write(0, value);
   }

   static {
      TYPE = Server.SCOREBOARD_OBJECTIVE;
   }

   public static class Mode extends IntEnum {
      public static final int ADD_OBJECTIVE = 0;
      public static final int REMOVE_OBJECTIVE = 1;
      public static final int UPDATE_VALUE = 2;
      private static final WrapperPlayServerScoreboardObjective.Mode INSTANCE = new WrapperPlayServerScoreboardObjective.Mode();

      public static WrapperPlayServerScoreboardObjective.Mode getInstance() {
         return INSTANCE;
      }
   }

   public static enum HealthDisplay {
      INTEGER,
      HEARTS;
   }
}
