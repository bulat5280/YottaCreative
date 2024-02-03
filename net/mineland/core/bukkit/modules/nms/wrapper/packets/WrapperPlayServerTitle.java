package net.mineland.core.bukkit.modules.nms.wrapper.packets;

import com.comphenix.protocol.PacketType;
import com.comphenix.protocol.PacketType.Play.Server;
import com.comphenix.protocol.events.PacketContainer;
import com.comphenix.protocol.wrappers.WrappedChatComponent;
import com.comphenix.protocol.wrappers.EnumWrappers.TitleAction;

public class WrapperPlayServerTitle extends AbstractPacket {
   public static final PacketType TYPE;

   public WrapperPlayServerTitle() {
      super(new PacketContainer(TYPE), TYPE);
      this.handle.getModifier().writeDefaults();
   }

   public WrapperPlayServerTitle(PacketContainer packet) {
      super(packet, TYPE);
   }

   public TitleAction getAction() {
      return (TitleAction)this.handle.getTitleActions().read(0);
   }

   public void setAction(TitleAction value) {
      this.handle.getTitleActions().write(0, value);
   }

   public WrappedChatComponent getTitle() {
      return (WrappedChatComponent)this.handle.getChatComponents().read(0);
   }

   public void setTitle(WrappedChatComponent value) {
      this.handle.getChatComponents().write(0, value);
   }

   public int getFadeIn() {
      return (Integer)this.handle.getIntegers().read(0);
   }

   public void setFadeIn(int value) {
      this.handle.getIntegers().write(0, value);
   }

   public int getStay() {
      return (Integer)this.handle.getIntegers().read(1);
   }

   public void setStay(int value) {
      this.handle.getIntegers().write(1, value);
   }

   public int getFadeOut() {
      return (Integer)this.handle.getIntegers().read(2);
   }

   public void setFadeOut(int value) {
      this.handle.getIntegers().write(2, value);
   }

   static {
      TYPE = Server.TITLE;
   }
}
