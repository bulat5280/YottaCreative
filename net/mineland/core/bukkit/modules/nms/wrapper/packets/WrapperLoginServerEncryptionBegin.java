package net.mineland.core.bukkit.modules.nms.wrapper.packets;

import com.comphenix.protocol.PacketType;
import com.comphenix.protocol.PacketType.Login.Server;
import com.comphenix.protocol.events.PacketContainer;
import java.security.PublicKey;

public class WrapperLoginServerEncryptionBegin extends AbstractPacket {
   public static final PacketType TYPE;

   public WrapperLoginServerEncryptionBegin() {
      super(new PacketContainer(TYPE), TYPE);
      this.handle.getModifier().writeDefaults();
   }

   public WrapperLoginServerEncryptionBegin(PacketContainer packet) {
      super(packet, TYPE);
   }

   public String getServerId() {
      return (String)this.handle.getStrings().read(0);
   }

   public void setServerId(String value) {
      this.handle.getStrings().write(0, value);
   }

   public PublicKey getPublicKey() {
      return (PublicKey)this.handle.getSpecificModifier(PublicKey.class).read(0);
   }

   public void setPublicKey(PublicKey value) {
      this.handle.getSpecificModifier(PublicKey.class).write(0, value);
   }

   public byte[] getVerifyToken() {
      return (byte[])this.handle.getByteArrays().read(0);
   }

   public void setVerifyToken(byte[] value) {
      this.handle.getByteArrays().write(0, value);
   }

   static {
      TYPE = Server.ENCRYPTION_BEGIN;
   }
}
