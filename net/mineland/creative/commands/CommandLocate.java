package net.mineland.creative.commands;

import net.mineland.core.bukkit.module.Module;
import net.mineland.core.bukkit.modules.command.Command;
import net.mineland.core.bukkit.modules.command.CommandEvent;
import net.mineland.core.bukkit.modules.user.User;
import net.mineland.creative.modules.creative.ModuleCreative;
import net.mineland.creative.modules.creative.Plot;

public class CommandLocate extends Command {
   public CommandLocate() {
      super("locate", (String)null);
   }

   public boolean execute(CommandEvent event) {
      ModuleCreative moduleCreative = (ModuleCreative)Module.getInstance(ModuleCreative.class);
      Plot your_plot = moduleCreative.getPlotManager().getCurrentPlot(event.getUser());
      event.argsToLowerCase0();
      String[] args = event.getArgs();
      if (args.length != 1) {
         if (your_plot != null) {
            event.getUser().sendMessage("вы_в_мире", "{plot}", String.valueOf(your_plot.getId()), "{owner}", your_plot.getOwnerName());
            return true;
         }

         if (your_plot == null) {
            event.getUser().sendMessage("ты_на_спавне");
            return true;
         }
      }

      if (args.length == 1) {
         User plot_user = event.getUser(event.arg(0));
         Plot user_plot = moduleCreative.getPlotManager().getCurrentPlot(plot_user);
         if (user_plot != null) {
            event.getUser().sendMessage("игрок_в_мире", "{plot}", String.valueOf(user_plot.getId()), "{user}", plot_user.getName(), "{owner}", user_plot.getOwnerName());
            return true;
         }

         if (user_plot == null) {
            event.getUser().sendMessage("игрок_на_спавне", "{user}", event.getUser(event.arg(0)).getName());
            return true;
         }
      }

      return false;
   }
}
