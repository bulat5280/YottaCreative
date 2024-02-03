package net.mineland.core.bukkit.modules.nms.wrapper.packets;

import com.comphenix.protocol.PacketType;
import com.comphenix.protocol.PacketType.Play.Server;
import com.comphenix.protocol.events.PacketContainer;
import com.comphenix.protocol.events.PacketEvent;
import com.comphenix.protocol.wrappers.WrappedChatComponent;
import org.bukkit.World;
import org.bukkit.entity.Entity;

public class WrapperPlayServerOpenWindow extends AbstractPacket {
   public static final PacketType TYPE;

   public WrapperPlayServerOpenWindow() {
      super(new PacketContainer(TYPE), TYPE);
      this.handle.getModifier().writeDefaults();
   }

   public WrapperPlayServerOpenWindow(PacketContainer packet) {
      super(packet, TYPE);
   }

   public int getWindowID() {
      return (Integer)this.handle.getIntegers().read(0);
   }

   public void setWindowID(int value) {
      this.handle.getIntegers().write(0, value);
   }

   public String getInventoryType() {
      return (String)this.handle.getStrings().read(0);
   }

   public void setInventoryType(String value) {
      this.handle.getStrings().write(0, value);
   }

   public WrappedChatComponent getWindowTitle() {
      return (WrappedChatComponent)this.handle.getChatComponents().read(0);
   }

   public void setWindowTitle(WrappedChatComponent value) {
      this.handle.getChatComponents().write(0, value);
   }

   public int getNumberOfSlots() {
      return (Integer)this.handle.getIntegers().read(1);
   }

   public void setNumberOfSlots(int value) {
      this.handle.getIntegers().write(1, value);
   }

   public int getEntityID() {
      return (Integer)this.handle.getIntegers().read(0);
   }

   public void setEntityID(int value) {
      this.handle.getIntegers().write(0, value);
   }

   public Entity getEntity(World world) {
      return (Entity)this.handle.getEntityModifier(world).read(0);
   }

   public Entity getEntity(PacketEvent event) {
      return this.getEntity(event.getPlayer().getWorld());
   }

   static {
      TYPE = Server.OPEN_WINDOW;
   }
}
