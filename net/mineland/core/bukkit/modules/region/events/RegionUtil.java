package net.mineland.core.bukkit.modules.region.events;

import java.util.List;
import net.mineland.core.bukkit.module.Module;
import net.mineland.core.bukkit.modules.region.ModuleRegion;
import net.mineland.core.bukkit.modules.region.region.Region;
import net.mineland.core.bukkit.modules.region.region.flag.FlagType;
import org.bukkit.entity.Player;
import org.bukkit.event.Cancellable;
import ua.govnojon.libs.bukkitutil.PlayerUtil;

public class RegionUtil {
   private static ModuleRegion moduleRegion = (ModuleRegion)Module.getInstance(ModuleRegion.class);

   public static boolean checkProtect(List<Region> regions, Player player, Cancellable event) {
      if (regions.isEmpty()) {
         return false;
      } else {
         Region region = moduleRegion.getPriorityRegionFlag(regions, FlagType.PROTECT);
         if (region != null) {
            String value = region.getFlagValue(FlagType.PROTECT);
            byte var6 = -1;
            switch(value.hashCode()) {
            case -1077769574:
               if (value.equals("member")) {
                  var6 = 2;
               }
               break;
            case 3079692:
               if (value.equals("deny")) {
                  var6 = 1;
               }
               break;
            case 92906313:
               if (value.equals("allow")) {
                  var6 = 0;
               }
            }

            switch(var6) {
            case 0:
               event.setCancelled(false);
               break;
            case 1:
               event.setCancelled(!player.hasPermission("mineland.libs.region.protect.deny"));
               return true;
            case 2:
               if (!region.isMember(player) && !player.hasPermission("mineland.libs.region.protect.member")) {
                  PlayerUtil.sendMessageDenyKey(player, "территория_защищена_для_вас");
                  event.setCancelled(true);
               }
            }
         }

         return false;
      }
   }
}
