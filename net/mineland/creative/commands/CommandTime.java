package net.mineland.creative.commands;

import net.mineland.core.bukkit.module.Module;
import net.mineland.core.bukkit.modules.command.Command;
import net.mineland.core.bukkit.modules.command.CommandEvent;
import net.mineland.creative.modules.creative.ModuleCreative;
import net.mineland.creative.modules.creative.Plot;

public class CommandTime extends Command {
   public CommandTime() {
      super("time", (String)null);
   }

   public boolean execute(CommandEvent event) {
      ModuleCreative moduleCreative = (ModuleCreative)Module.getInstance(ModuleCreative.class);
      Plot plot = moduleCreative.getPlotManager().getCurrentPlot(event.getUser());
      String[] args = event.getArgs();
      if (plot == null) {
         event.sendMessage("creative.not_in_world");
         return false;
      } else if (!plot.isOwner(event.getUser())) {
         event.sendMessage("нет_прав_в_этом_мире");
         return false;
      } else {
         return false;
      }
   }
}
