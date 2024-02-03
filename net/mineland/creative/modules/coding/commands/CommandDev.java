package net.mineland.creative.modules.coding.commands;

import java.util.concurrent.TimeUnit;
import net.mineland.core.bukkit.module.Module;
import net.mineland.core.bukkit.modules.command.Command;
import net.mineland.core.bukkit.modules.command.CommandEvent;
import net.mineland.core.bukkit.modules.user.User;
import net.mineland.creative.modules.creative.ModuleCreative;
import net.mineland.creative.modules.creative.PlayerMode;
import net.mineland.creative.modules.creative.Plot;
import net.mineland.creative.modules.creative.generators.biomes.PlotGenerator;
import ua.govnojon.libs.bukkitutil.PlayerUtil;

public class CommandDev extends Command {
   private static ModuleCreative moduleCreative = (ModuleCreative)Module.getInstance(ModuleCreative.class);

   public CommandDev() {
      super("dev", (String)null);
   }

   public boolean execute(CommandEvent event) {
      event.checkIsConsole();
      event.setNextUse(TimeUnit.SECONDS.toMillis(5L));
      event.argsToLowerCase0();
      String[] args = event.getArgs();
      User user = event.getUser();
      if (!user.hasPermission("mineland.creative.coding.enter")) {
         user.sendMessage("dev.donate");
         return false;
      } else {
         Plot plot = moduleCreative.getPlotManager().getCurrentPlot(user);
         if (args.length != 1) {
            user.sendMessage("использование_команды_dev");
         }

         if (plot != null) {
            if (event.size() == 0) {
               if (plot.getCodingRegion().isMember(user)) {
                  plot.getPlotMode().onPlayerQuit(user);
                  if (moduleCreative.getPlotManager().getCurrentPlot(user) != null) {
                     PlotGenerator.generateCodingPlot(plot, () -> {
                        user.sendMessage("creative.command.dev.set_dev");
                        plot.setPlayerMode(user, PlayerMode.CODING);
                     });
                  }
               } else {
                  user.sendMessage("creative.command.dev.no_perm");
               }
            } else if (event.hasSize(1) && plot.isOwner(user)) {
               User subject = event.getUser(args[0]);
               if (!subject.hasPermission("mineland.creative.coding.enter")) {
                  event.getSender().sendMessage("У игрока нет доступа к кодингу.");
                  return false;
               }

               if (subject != null && subject != user) {
                  if (plot.getCodingRegion().isMember(subject)) {
                     PlayerUtil.sendTitleKey(subject, "creative.commands.mode.not_allowed_to_code", 10, 30, 10);
                     plot.getCodingRegion().removePlayer(subject);
                     if (plot.getPlayerMode(subject) == PlayerMode.CODING) {
                        plot.setPlayerMode(subject, PlayerMode.PLAYING);
                     }
                  } else if (plot.getOnlinePlayers().contains(subject)) {
                     PlayerUtil.sendTitleKey(subject, "creative.commands.mode.allowed_to_code", 10, 30, 10);
                     plot.getCodingRegion().addPlayer(subject);
                  }
               }
            }
         } else {
            user.sendMessage("creative.not_in_world");
         }

         return true;
      }
   }
}
