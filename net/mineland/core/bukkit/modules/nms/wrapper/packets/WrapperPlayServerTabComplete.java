package net.mineland.core.bukkit.modules.nms.wrapper.packets;

import com.comphenix.protocol.PacketType;
import com.comphenix.protocol.PacketType.Play.Server;
import com.comphenix.protocol.events.PacketContainer;

public class WrapperPlayServerTabComplete extends AbstractPacket {
   public static final PacketType TYPE;

   public WrapperPlayServerTabComplete() {
      super(new PacketContainer(TYPE), TYPE);
      this.handle.getModifier().writeDefaults();
   }

   public WrapperPlayServerTabComplete(PacketContainer packet) {
      super(packet, TYPE);
   }

   public int getCount() {
      return ((String[])this.handle.getStringArrays().read(0)).length;
   }

   public String[] getMatches() {
      return (String[])this.handle.getStringArrays().read(0);
   }

   public void setMatches(String[] value) {
      this.handle.getStringArrays().write(0, value);
   }

   static {
      TYPE = Server.TAB_COMPLETE;
   }
}
