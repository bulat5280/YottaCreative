package net.mineland.creative.modules.creative.commands;

import net.mineland.core.bukkit.module.Module;
import net.mineland.core.bukkit.modules.command.Command;
import net.mineland.core.bukkit.modules.command.CommandEvent;
import net.mineland.core.bukkit.modules.gui.ModuleGui;
import net.mineland.core.bukkit.modules.user.User;
import net.mineland.creative.modules.creative.ModuleCreative;
import net.mineland.creative.modules.creative.Plot;
import net.mineland.creative.modules.creative.gui.GuiMenuMyWorlds;
import net.mineland.creative.modules.creative.gui.worldsettings.GuiMenuWorldSettings;

public class CommandPlot extends Command {
   private static ModuleCreative moduleCreative = (ModuleCreative)Module.getInstance(ModuleCreative.class);
   private static ModuleGui moduleGui = (ModuleGui)Module.getInstance(ModuleGui.class);

   public CommandPlot() {
      super("plot", (String)null, "world", "start");
   }

   public boolean execute(CommandEvent event) {
      event.checkIsConsole();
      String[] args = event.getArgs();
      User user = event.getUser();
      if (event.size() == 0) {
         if (user.getPlayer().getWorld() == moduleCreative.getLobbyLocation().getWorld()) {
            moduleGui.openGui(user, GuiMenuMyWorlds.class);
         } else {
            Plot plot = moduleCreative.getPlotManager().getCurrentPlot(user);
            if (plot != null && plot.isOwner(user)) {
               moduleGui.openGui(user, GuiMenuWorldSettings.class);
            } else {
               moduleGui.openGui(user, GuiMenuMyWorlds.class);
            }
         }
      } else if (event.size() == 1) {
         event.argsToLowerCase0();
         String var8 = event.arg(0);
         byte var5 = -1;
         switch(var8.hashCode()) {
         case -1269897735:
            if (var8.equals("clearvars")) {
               var5 = 2;
            }
            break;
         case -851634274:
            if (var8.equals("toggleopen")) {
               var5 = 1;
            }
            break;
         case 3612204:
            if (var8.equals("vars")) {
               var5 = 0;
            }
         }

         Plot plot;
         switch(var5) {
         case 0:
            plot = moduleCreative.getPlotManager().getCurrentPlot(user);
            if (plot != null && plot.isOwner(user)) {
               StringBuilder sb = new StringBuilder();
               plot.getCodeHandler().getDynamicVariables().forEach((s, dynamicVariable) -> {
                  sb.append(dynamicVariable.isSave()).append(dynamicVariable.getName()).append("§r: ").append(dynamicVariable.getValue(plot)).append("\n");
               });
               user.getPlayer().sendMessage(sb.toString());
            }
            break;
         case 1:
            plot = moduleCreative.getPlotManager().getCurrentPlot(user);
            if (plot != null && plot.isOwner(user)) {
               if (plot.isClosed()) {
                  plot.setIsClosed(false);
                  user.sendMessage("creative.commands.toggleopen.opened");
               } else {
                  plot.setIsClosed(true);
                  user.sendMessage("creative.commands.toggleopen.closed");
               }
            }
         case 2:
         }
      }

      return true;
   }
}
