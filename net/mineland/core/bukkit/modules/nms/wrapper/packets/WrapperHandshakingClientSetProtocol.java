package net.mineland.core.bukkit.modules.nms.wrapper.packets;

import com.comphenix.protocol.PacketType;
import com.comphenix.protocol.PacketType.Protocol;
import com.comphenix.protocol.PacketType.Handshake.Client;
import com.comphenix.protocol.events.PacketContainer;

public class WrapperHandshakingClientSetProtocol extends AbstractPacket {
   public static final PacketType TYPE;

   public WrapperHandshakingClientSetProtocol() {
      super(new PacketContainer(TYPE), TYPE);
      this.handle.getModifier().writeDefaults();
   }

   public WrapperHandshakingClientSetProtocol(PacketContainer packet) {
      super(packet, TYPE);
   }

   public int getProtocolVersion() {
      return (Integer)this.handle.getIntegers().read(0);
   }

   public void setProtocolVersion(int value) {
      this.handle.getIntegers().write(0, value);
   }

   public String getServerAddressHostnameOrIp() {
      return (String)this.handle.getStrings().read(0);
   }

   public void setServerAddressHostnameOrIp(String value) {
      this.handle.getStrings().write(0, value);
   }

   public int getServerPort() {
      return (Integer)this.handle.getIntegers().read(1);
   }

   public void setServerPort(int value) {
      this.handle.getIntegers().write(1, value);
   }

   public Protocol getNextState() {
      return (Protocol)this.handle.getProtocols().read(0);
   }

   public void setNextState(Protocol value) {
      this.handle.getProtocols().write(0, value);
   }

   static {
      TYPE = Client.SET_PROTOCOL;
   }
}
