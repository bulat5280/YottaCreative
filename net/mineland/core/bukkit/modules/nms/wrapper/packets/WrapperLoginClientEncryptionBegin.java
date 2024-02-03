package net.mineland.core.bukkit.modules.nms.wrapper.packets;

import com.comphenix.protocol.PacketType;
import com.comphenix.protocol.PacketType.Login.Client;
import com.comphenix.protocol.events.PacketContainer;

public class WrapperLoginClientEncryptionBegin extends AbstractPacket {
   public static final PacketType TYPE;

   public WrapperLoginClientEncryptionBegin() {
      super(new PacketContainer(TYPE), TYPE);
      this.handle.getModifier().writeDefaults();
   }

   public WrapperLoginClientEncryptionBegin(PacketContainer packet) {
      super(packet, TYPE);
   }

   public byte[] getSharedSecret() {
      return (byte[])this.handle.getByteArrays().read(0);
   }

   public void setSharedSecret(byte[] value) {
      this.handle.getByteArrays().write(0, value);
   }

   public byte[] getVerifyToken() {
      return (byte[])this.handle.getByteArrays().read(1);
   }

   public void setVerifyToken(byte[] value) {
      this.handle.getByteArrays().write(1, value);
   }

   static {
      TYPE = Client.ENCRYPTION_BEGIN;
   }
}
