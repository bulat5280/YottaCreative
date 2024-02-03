package net.mineland.creative.modules.creative;

import com.comphenix.protocol.PacketType;
import com.comphenix.protocol.PacketType.Play.Server;
import com.comphenix.protocol.events.PacketAdapter;
import com.comphenix.protocol.events.PacketEvent;
import com.comphenix.protocol.wrappers.PlayerInfoData;
import com.comphenix.protocol.wrappers.EnumWrappers.PlayerInfoAction;
import net.mineland.core.bukkit.module.Module;
import net.mineland.core.bukkit.modules.nms.wrapper.packets.WrapperPlayServerNamedSoundEffect;
import net.mineland.core.bukkit.modules.nms.wrapper.packets.WrapperPlayServerPlayerInfo;
import net.mineland.core.bukkit.modules.nms.wrapper.packets.WrapperPlayServerRespawn;
import net.mineland.core.bukkit.modules.nms.wrapper.packets.WrapperPlayServerWorldEvent;
import net.mineland.core.bukkit.modules.user.User;
import org.bukkit.Location;
import org.bukkit.WorldType;

public class PacketListener extends PacketAdapter {
   private static ModuleCreative moduleCreative = (ModuleCreative)Module.getInstance(ModuleCreative.class);

   public PacketListener() {
      super(moduleCreative.getPlugin(), new PacketType[]{Server.RESPAWN, Server.WORLD_EVENT, Server.PLAYER_INFO});
   }

   public void onPacketSending(PacketEvent event) {
      if (event.getPacketType() == Server.RESPAWN) {
         WrapperPlayServerRespawn packet = new WrapperPlayServerRespawn(event.getPacket());
         packet.setLevelType(WorldType.FLAT);
      } else {
         Location location;
         Plot plot;
         User user;
         if (event.getPacketType() == Server.WORLD_EVENT) {
            WrapperPlayServerWorldEvent packet = new WrapperPlayServerWorldEvent(event.getPacket());
            location = packet.getLocation().toLocation(moduleCreative.getPlotWorld());
            plot = moduleCreative.getPlotManager().getPlot(location);
            if (plot != null) {
               user = User.getUser(event.getPlayer());
               if (!plot.getOnlinePlayers().contains(user)) {
                  event.setCancelled(true);
               }
            }
         } else if (event.getPacketType() == Server.NAMED_SOUND_EFFECT) {
            WrapperPlayServerNamedSoundEffect packet = new WrapperPlayServerNamedSoundEffect(event.getPacket());
            location = new Location(moduleCreative.getPlotWorld(), (double)(packet.getEffectPositionX() / 8), (double)(packet.getEffectPositionY() / 8), (double)(packet.getEffectPositionZ() / 8));
            plot = moduleCreative.getPlotManager().getPlot(location);
            if (plot != null) {
               user = User.getUser(event.getPlayer());
               if (!plot.getOnlinePlayers().contains(user)) {
                  event.setCancelled(true);
               }
            }
         } else if (event.getPacketType() == Server.PLAYER_INFO) {
            WrapperPlayServerPlayerInfo packet = new WrapperPlayServerPlayerInfo(event.getPacket());
            if (packet.getAction() == PlayerInfoAction.UPDATE_GAME_MODE) {
               PlayerInfoData playerInfoData = (PlayerInfoData)packet.getData().get(0);
               int latency = playerInfoData.getLatency();
               if (latency != -1 && event.getPlayer().getUniqueId() != playerInfoData.getProfile().getUUID()) {
                  event.setCancelled(true);
               }
            }
         }
      }

   }
}
