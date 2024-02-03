package net.mineland.creative.commands;

import net.mineland.core.bukkit.module.Module;
import net.mineland.core.bukkit.modules.command.Command;
import net.mineland.core.bukkit.modules.command.CommandEvent;
import net.mineland.creative.modules.creative.ModuleCreative;
import net.mineland.creative.modules.creative.PlayerMode;
import net.mineland.creative.modules.creative.Plot;

public class CommandEditor extends Command {
   public CommandEditor() {
      super("editor", (String)null, "deveditor");
   }

   public boolean execute(CommandEvent event) {
      ModuleCreative moduleCreative = (ModuleCreative)Module.getInstance(ModuleCreative.class);
      Plot plot = moduleCreative.getPlotManager().getCurrentPlot(event.getUser());
      String[] args = event.getArgs();
      if (args.length == 0) {
         event.sendMessage("использование_команды_editor");
      }

      if (args.length != 0) {
         if (plot == null) {
            event.sendMessage("creative.not_in_world");
            return false;
         }

         if (plot.getPlayerMode(event.getUser()) != PlayerMode.CODING) {
            event.sendMessage("creative.not_in_coding");
            return false;
         }

         if (args[0].equalsIgnoreCase("floor") && args[1].equalsIgnoreCase("add")) {
            event.checkPerm("crystalsand.cmd.editor.floor.add");
            if (plot.getDevLevels() == 15) {
               event.sendMessage("creative.coding.много_этажей_кода", "{current}", String.valueOf(plot.getDevLevels()));
               return true;
            }

            if (plot.getDevLevels() <= 14) {
               plot.setDevLevels(plot.getDevLevels() + 1);
               event.sendMessage("creative.coding.этаж_добавлен", "{current}", String.valueOf(plot.getDevLevels()));
               return true;
            }
         }
      }

      return false;
   }
}
