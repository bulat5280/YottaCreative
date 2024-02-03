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
import org.bukkit.GameMode;
import ua.govnojon.libs.bukkitutil.PlayerUtil;

public class CommandBuild extends Command {
   private static ModuleCreative moduleCreative = (ModuleCreative)Module.getInstance(ModuleCreative.class);

   public CommandBuild() {
      super("build", (String)null);
   }

   public boolean execute(CommandEvent event) {
      event.checkIsConsole();
      event.setNextUse(TimeUnit.SECONDS.toMillis(10L));
      event.argsToLowerCase0();
      String[] args = event.getArgs();
      User user = event.getUser();
      Plot plot = moduleCreative.getPlotManager().getCurrentPlot(user);
      if (plot != null) {
         if (event.size() == 0) {
            if (plot.getPlotMode() == PlotMode.BUILDING) {
               user.sendMessage("creative.command.build.set_build");
               plot.setPlayerMode(user, PlayerMode.PLAYING);
               plot.getPlotMode().onPlayerJoin(user);
            } else if (plot.isOwner(user)) {
               Message.broadcast(plot.getOnlinePlayers(), "creative.world.set_build");
               plot.setPlotMode(PlotMode.BUILDING);
               plot.setPlayerMode(user, PlayerMode.PLAYING);
               plot.getPlotMode().onPlayerJoin(user);
            } else {
               user.sendMessage("creative.command.play.world_not_in_build_mode");
            }
         } else if (event.hasSize(1) && plot.isOwner(user)) {
            User subject = event.getUser(args[0]);
            if (subject != null && subject != user) {
               if (plot.getPlotRegion().isMember(subject)) {
                  PlayerUtil.sendTitleKey(subject, "creative.commands.mode.not_allowed_to_build", 10, 30, 10);
                  plot.getPlotRegion().removePlayer(subject);
                  subject.getPlayer().setGameMode(GameMode.ADVENTURE);
               } else if (plot.getOnlinePlayers().contains(subject)) {
                  PlayerUtil.sendTitleKey(subject, "creative.commands.mode.allowed_to_build", 10, 30, 10);
                  plot.getPlotRegion().addPlayer(subject);
                  subject.getPlayer().setGameMode(GameMode.CREATIVE);
               }
            }
         }
      } else {
         user.sendMessage("creative.not_in_world");
      }

      return true;
   }
}
