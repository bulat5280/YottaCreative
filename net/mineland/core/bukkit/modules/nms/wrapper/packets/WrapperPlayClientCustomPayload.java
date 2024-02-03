package net.mineland.core.bukkit.modules.nms.wrapper.packets;

import com.comphenix.protocol.PacketType;
import com.comphenix.protocol.PacketType.Play.Client;
import com.comphenix.protocol.events.PacketContainer;
import com.comphenix.protocol.utility.MinecraftReflection;
import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;

public class WrapperPlayClientCustomPayload extends AbstractPacket {
   public static final PacketType TYPE;

   public WrapperPlayClientCustomPayload() {
      super(new PacketContainer(TYPE), TYPE);
      this.handle.getModifier().writeDefaults();
   }

   public WrapperPlayClientCustomPayload(PacketContainer packet) {
      super(packet, TYPE);
   }

   public String getChannel() {
      return (String)this.handle.getStrings().read(0);
   }

   public void setChannel(String value) {
      this.handle.getStrings().write(0, value);
   }

   public ByteBuf getContentsBuffer() {
      return (ByteBuf)this.handle.getModifier().withType(ByteBuf.class).read(0);
   }

   public void setContentsBuffer(ByteBuf contents) {
      if (MinecraftReflection.is(MinecraftReflection.getPacketDataSerializerClass(), contents)) {
         this.handle.getModifier().withType(ByteBuf.class).write(0, contents);
      } else {
         Object serializer = MinecraftReflection.getPacketDataSerializer(contents);
         this.handle.getModifier().withType(ByteBuf.class).write(0, serializer);
      }

   }

   public byte[] getContents() {
      ByteBuf buffer = this.getContentsBuffer();
      byte[] array = new byte[buffer.readableBytes()];
      buffer.readBytes(array);
      return array;
   }

   public void setContents(byte[] content) {
      this.setContentsBuffer(Unpooled.copiedBuffer(content));
   }

   static {
      TYPE = Client.CUSTOM_PAYLOAD;
   }
}
