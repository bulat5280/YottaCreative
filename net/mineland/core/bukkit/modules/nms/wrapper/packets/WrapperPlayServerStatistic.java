package net.mineland.core.bukkit.modules.nms.wrapper.packets;

import com.comphenix.protocol.PacketType;
import com.comphenix.protocol.PacketType.Play.Server;
import com.comphenix.protocol.events.PacketContainer;
import com.comphenix.protocol.wrappers.WrappedStatistic;
import java.util.Map;

public class WrapperPlayServerStatistic extends AbstractPacket {
   public static final PacketType TYPE;

   public WrapperPlayServerStatistic() {
      super(new PacketContainer(TYPE), TYPE);
      this.handle.getModifier().writeDefaults();
   }

   public WrapperPlayServerStatistic(PacketContainer packet) {
      super(packet, TYPE);
   }

   public Map<WrappedStatistic, Integer> getStatistics() {
      return (Map)this.handle.getStatisticMaps().read(0);
   }

   public void setStatistics(Map<WrappedStatistic, Integer> value) {
      this.handle.getStatisticMaps().write(0, value);
   }

   static {
      TYPE = Server.STATISTIC;
   }
}
