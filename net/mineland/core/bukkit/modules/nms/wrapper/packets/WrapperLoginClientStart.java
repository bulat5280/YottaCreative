package net.mineland.core.bukkit.modules.nms.wrapper.packets;

import com.comphenix.protocol.PacketType;
import com.comphenix.protocol.PacketType.Login.Client;
import com.comphenix.protocol.events.PacketContainer;
import com.comphenix.protocol.wrappers.WrappedGameProfile;

public class WrapperLoginClientStart extends AbstractPacket {
   public static final PacketType TYPE;

   public WrapperLoginClientStart() {
      super(new PacketContainer(TYPE), TYPE);
      this.handle.getModifier().writeDefaults();
   }

   public WrapperLoginClientStart(PacketContainer packet) {
      super(packet, TYPE);
   }

   public WrappedGameProfile getProfile() {
      return (WrappedGameProfile)this.handle.getGameProfiles().read(0);
   }

   public void setProfile(WrappedGameProfile value) {
      this.handle.getGameProfiles().write(0, value);
   }

   static {
      TYPE = Client.START;
   }
}
