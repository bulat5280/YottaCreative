package net.mineland.core.bukkit.modules.nms.wrapper.packets;

import com.comphenix.protocol.PacketType;
import com.comphenix.protocol.PacketType.Status.Server;
import com.comphenix.protocol.events.PacketContainer;
import com.comphenix.protocol.wrappers.WrappedServerPing;

public class WrapperStatusServerServerInfo extends AbstractPacket {
   public static final PacketType TYPE;

   public WrapperStatusServerServerInfo() {
      super(new PacketContainer(TYPE), TYPE);
      this.handle.getModifier().writeDefaults();
   }

   public WrapperStatusServerServerInfo(PacketContainer packet) {
      super(packet, TYPE);
   }

   public WrappedServerPing getJsonResponse() {
      return (WrappedServerPing)this.handle.getServerPings().read(0);
   }

   public void setJsonResponse(WrappedServerPing value) {
      this.handle.getServerPings().write(0, value);
   }

   static {
      TYPE = Server.SERVER_INFO;
   }
}
