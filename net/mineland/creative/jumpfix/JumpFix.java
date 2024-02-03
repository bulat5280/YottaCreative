package net.mineland.creative.jumpfix;

import com.destroystokyo.paper.event.player.PlayerJumpEvent;
import net.mineland.core.bukkit.module.Module;
import net.mineland.core.bukkit.modules.user.User;
import net.mineland.creative.modules.coding.activators.player.PlayerJumpActivator;
import net.mineland.creative.modules.creative.ModuleCreative;
import net.mineland.creative.modules.creative.Plot;
import net.mineland.creative.modules.creative.PlotMode;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;

public class JumpFix implements Listener {
   @EventHandler
   public void on(PlayerJumpEvent event) {
      User user = User.getUser(event.getPlayer());
      ModuleCreative moduleCreative = (ModuleCreative)Module.getInstance(ModuleCreative.class);
      Plot plot = moduleCreative.getPlotManager().getCurrentPlot(user);
      if (plot != null && plot.getPlotMode() == PlotMode.PLAYING) {
         PlayerJumpActivator.Event gameEvent = new PlayerJumpActivator.Event(user, plot, event);
         gameEvent.callEvent();
         gameEvent.handle();
      }

   }
}
