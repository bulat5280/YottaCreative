package net.mineland.core.bukkit.modules.nms.wrapper.packets;

import com.comphenix.protocol.PacketType;
import com.comphenix.protocol.PacketType.Play.Server;
import com.comphenix.protocol.events.PacketContainer;
import com.comphenix.protocol.wrappers.EnumWrappers.Difficulty;

public class WrapperPlayServerServerDifficulty extends AbstractPacket {
   public static final PacketType TYPE;

   public WrapperPlayServerServerDifficulty() {
      super(new PacketContainer(TYPE), TYPE);
      this.handle.getModifier().writeDefaults();
   }

   public WrapperPlayServerServerDifficulty(PacketContainer packet) {
      super(packet, TYPE);
   }

   public Difficulty getDifficulty() {
      return (Difficulty)this.handle.getDifficulties().read(0);
   }

   public void setDifficulty(Difficulty value) {
      this.handle.getDifficulties().write(0, value);
   }

   static {
      TYPE = Server.SERVER_DIFFICULTY;
   }
}
