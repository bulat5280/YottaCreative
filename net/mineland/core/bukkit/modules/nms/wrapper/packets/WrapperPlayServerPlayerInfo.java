package net.mineland.core.bukkit.modules.nms.wrapper.packets;

import com.comphenix.protocol.PacketType;
import com.comphenix.protocol.PacketType.Play.Server;
import com.comphenix.protocol.events.PacketContainer;
import com.comphenix.protocol.wrappers.PlayerInfoData;
import com.comphenix.protocol.wrappers.EnumWrappers.PlayerInfoAction;
import java.util.List;

public class WrapperPlayServerPlayerInfo extends AbstractPacket {
   public static final PacketType TYPE;

   public WrapperPlayServerPlayerInfo() {
      super(new PacketContainer(TYPE), TYPE);
      this.handle.getModifier().writeDefaults();
   }

   public WrapperPlayServerPlayerInfo(PacketContainer packet) {
      super(packet, TYPE);
   }

   public PlayerInfoAction getAction() {
      return (PlayerInfoAction)this.handle.getPlayerInfoAction().read(0);
   }

   public void setAction(PlayerInfoAction value) {
      this.handle.getPlayerInfoAction().write(0, value);
   }

   public List<PlayerInfoData> getData() {
      return (List)this.handle.getPlayerInfoDataLists().read(0);
   }

   public void setData(List<PlayerInfoData> value) {
      this.handle.getPlayerInfoDataLists().write(0, value);
   }

   public String toString() {
      return "WrapperPlayServerPlayerInfo{action=" + this.getAction() + ", data=" + this.getData() + '}';
   }

   static {
      TYPE = Server.PLAYER_INFO;
   }
}
