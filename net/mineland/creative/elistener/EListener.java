package net.mineland.creative.elistener;

import net.mineland.core.bukkit.module.Module;
import net.mineland.core.bukkit.modules.user.User;
import net.mineland.creative.modules.creative.ModuleCreative;
import net.mineland.creative.modules.creative.PlayerMode;
import net.mineland.creative.modules.creative.Plot;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.AsyncPlayerChatEvent;
import ua.govnojon.libs.bukkitutil.ItemStackUtil;
import ua.govnojon.libs.bukkitutil.SimpleItem;

public class EListener implements Listener {
   @EventHandler
   public void onChat(AsyncPlayerChatEvent event) {
      ModuleCreative moduleCreative = (ModuleCreative)Module.getInstance(ModuleCreative.class);
      User user = User.getUser(event.getPlayer());
      Plot plot = moduleCreative.getPlotManager().getCurrentPlot(user);
      if (plot != null) {
         String message = event.getMessage().replace("&", "§").replace("§§", "&");
         if (plot.getPlayerMode(user) == PlayerMode.CODING) {
            SimpleItem item = new SimpleItem(user.getPlayer().getInventory().getItemInMainHand());
            if (!ItemStackUtil.isNullOrAir(item)) {
               boolean isCanceled = event.isCancelled();
               event.setCancelled(true);
               boolean updateItem = true;
               switch(item.getType()) {
               case PRISMARINE_SHARD:
               }
            }
         }

      }
   }
}
