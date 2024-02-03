package net.mineland.core.bukkit.modules.nms.wrapper.packets;

import com.comphenix.protocol.PacketType;
import com.comphenix.protocol.PacketType.Play.Server;
import com.comphenix.protocol.events.PacketContainer;
import com.comphenix.protocol.reflect.IntEnum;
import java.util.Collection;
import java.util.List;

public class WrapperPlayServerScoreboardTeam extends AbstractPacket {
   public static final PacketType TYPE;

   public WrapperPlayServerScoreboardTeam() {
      super(new PacketContainer(TYPE), TYPE);
      this.handle.getModifier().writeDefaults();
   }

   public WrapperPlayServerScoreboardTeam(PacketContainer packet) {
      super(packet, TYPE);
   }

   public WrapperPlayServerScoreboardTeam(String name, String displayName, String prefix, String suffix, Collection<String> players, String collisionRule, String nameTagVisibility, int mode) {
      this();
      this.setName(name);
      this.setDisplayName(displayName);
      this.setPrefix(prefix);
      this.setSuffix(suffix);
      this.setPlayers(players);
      this.setCollisionRule(collisionRule);
      this.setNameTagVisibility(nameTagVisibility);
      this.setMode(mode);
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

   public String getPrefix() {
      return (String)this.handle.getStrings().read(2);
   }

   public void setPrefix(String value) {
      this.handle.getStrings().write(2, value);
   }

   public String getSuffix() {
      return (String)this.handle.getStrings().read(3);
   }

   public void setSuffix(String value) {
      this.handle.getStrings().write(3, value);
   }

   public String getNameTagVisibility() {
      return (String)this.handle.getStrings().read(4);
   }

   public void setNameTagVisibility(WrapperPlayServerScoreboardTeam.NameTagVisibility nameTagVisibility) {
      this.handle.getStrings().write(4, nameTagVisibility.getKey());
   }

   public void setNameTagVisibility(String value) {
      this.handle.getStrings().write(4, value);
   }

   public int getColor() {
      return (Integer)this.handle.getIntegers().read(0);
   }

   public void setColor(int value) {
      this.handle.getIntegers().write(0, value);
   }

   public String getCollisionRule() {
      return (String)this.handle.getStrings().read(5);
   }

   public void setCollisionRule(String value) {
      this.handle.getStrings().write(5, value);
   }

   public Collection<String> getPlayers() {
      return (Collection)this.handle.getSpecificModifier(Collection.class).read(0);
   }

   public void setPlayers(Collection<String> value) {
      this.handle.getSpecificModifier(Collection.class).write(0, value);
   }

   public void setPlayers(List<String> value) {
      this.handle.getSpecificModifier(Collection.class).write(0, value);
   }

   public int getMode() {
      return (Integer)this.handle.getIntegers().read(1);
   }

   public void setMode(int value) {
      this.handle.getIntegers().write(1, value);
   }

   public int getPackOptionData() {
      return (Integer)this.handle.getIntegers().read(2);
   }

   public void setPackOptionData(int value) {
      this.handle.getIntegers().write(2, value);
   }

   public String toString() {
      return "WrapperPlayServerScoreboardTeam{name='" + this.getName() + '\'' + ", displayName='" + this.getDisplayName() + '\'' + ", prefix='" + this.getPrefix() + '\'' + ", suffix='" + this.getSuffix() + '\'' + ", nameTagVisibility='" + this.getNameTagVisibility() + '\'' + ", color=" + this.getColor() + ", collisionRule='" + this.getCollisionRule() + '\'' + ", players=" + this.getPlayers() + ", mode=" + this.getMode() + ", packOptionData=" + this.getPackOptionData() + '}';
   }

   static {
      TYPE = Server.SCOREBOARD_TEAM;
   }

   public static class Mode extends IntEnum {
      public static final int TEAM_CREATED = 0;
      public static final int TEAM_REMOVED = 1;
      public static final int TEAM_UPDATED = 2;
      public static final int PLAYERS_ADDED = 3;
      public static final int PLAYERS_REMOVED = 4;
      private static final WrapperPlayServerScoreboardTeam.Mode INSTANCE = new WrapperPlayServerScoreboardTeam.Mode();

      public static WrapperPlayServerScoreboardTeam.Mode getInstance() {
         return INSTANCE;
      }
   }

   public static enum NameTagVisibility {
      ALWAYS("always"),
      HIDE_FOR_OTHER_TEAMS("hideForOtherTeams"),
      HIDE_FOR_OWN_TEAM("hideForOwnTeam"),
      NEVER("never");

      private String key;

      private NameTagVisibility(String key) {
         this.key = key;
      }

      public String getKey() {
         return this.key;
      }
   }
}
