package net.mineland.creative.modules.coding.commands;

import java.util.concurrent.TimeUnit;
import net.mineland.core.bukkit.module.Module;
import net.mineland.core.bukkit.modules.command.Command;
import net.mineland.core.bukkit.modules.command.CommandEvent;
import net.mineland.core.bukkit.modules.message.Message;
import net.mineland.core.bukkit.modules.user.User;
import net.mineland.creative.modules.creative.ModuleCreative;
import net.mineland.creative.modules.creative.PlayerMode;
import net.mineland.creative.modules.creative.Plot;
import net.mineland.creative.modules.creative.PlotMode;

public class CommandPlay extends Command {
   private static ModuleCreative moduleCreative = (ModuleCreative)Module.getInstance(ModuleCreative.class);

   public CommandPlay() {
      super("play", (String)null, "run");
   }

   public boolean execute(CommandEvent event) {
      event.checkIsConsole();
      event.setNextUse(TimeUnit.SECONDS.toMillis(10L));
      User user = event.getUser();
      Plot plot = moduleCreative.getPlotManager().getCurrentPlot(user);
      if (plot != null) {
         if (plot.getPlotMode() == PlotMode.PLAYING) {
            user.sendMessage("creative.command.play.set_play");
            moduleCreative.getPlotManager().parseCoding(plot);
            plot.setPlayerMode(user, PlayerMode.PLAYING);
            plot.getPlotMode().onPlayerJoin(user);
         } else if (plot.isOwner(user)) {
            Message.broadcast(plot.getOnlinePlayers(), "creative.world.set_play");
            plot.setPlayerMode(user, PlayerMode.PLAYING);
            plot.setPlotMode(PlotMode.PLAYING);
         } else {
            user.sendMessage("creative.command.play.world_not_in_play_mode");
         }
      } else {
         user.sendMessage("creative.not_in_world");
      }

      return true;
   }
}
