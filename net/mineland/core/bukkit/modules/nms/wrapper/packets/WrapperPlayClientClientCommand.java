package net.mineland.core.bukkit.modules.nms.wrapper.packets;

import com.comphenix.protocol.PacketType;
import com.comphenix.protocol.PacketType.Play.Client;
import com.comphenix.protocol.events.PacketContainer;
import com.comphenix.protocol.wrappers.EnumWrappers.ClientCommand;

public class WrapperPlayClientClientCommand extends AbstractPacket {
   public static final PacketType TYPE;

   public WrapperPlayClientClientCommand() {
      super(new PacketContainer(TYPE), TYPE);
      this.handle.getModifier().writeDefaults();
   }

   public WrapperPlayClientClientCommand(PacketContainer packet) {
      super(packet, TYPE);
   }

   public ClientCommand getAction() {
      return (ClientCommand)this.handle.getClientCommands().read(0);
   }

   public void setAction(ClientCommand value) {
      this.handle.getClientCommands().write(0, value);
   }

   static {
      TYPE = Client.CLIENT_COMMAND;
   }
}
