package net.mineland.creative.commands;

import java.util.concurrent.TimeUnit;
import net.mineland.core.bukkit.module.Module;
import net.mineland.core.bukkit.modules.command.Command;
import net.mineland.core.bukkit.modules.command.CommandEvent;
import net.mineland.creative.modules.creative.ModuleCreative;
import net.mineland.creative.modules.creative.Plot;
import net.mineland.creative.modules.creative.PlotMode;

public class CommandAd extends Command {
   private final ModuleCreative moduleCreative = (ModuleCreative)Module.getInstance(ModuleCreative.class);

   public CommandAd() {
      super("ad", (String)null);
   }

   public boolean execute(CommandEvent event) {
      Plot plot1;
      if (event.getArgs().length != 1) {
         event.checkPerm("mineland.creative.command.ad");
         plot1 = this.moduleCreative.getPlotManager().getCurrentPlot(event.getUser());
         if (plot1 == null) {
            event.sendMessage("creative.not_in_world");
            return false;
         } else if (plot1.isClosed()) {
            event.sendMessage("мир_закрыт");
            return false;
         } else if (!plot1.getPlotMode().equals(PlotMode.PLAYING)) {
            event.sendMessage("creative.not_in_playing_mode");
            return false;
         } else if (!plot1.isOwner(event.getUser())) {
            event.sendMessage("нет_прав");
            return false;
         } else {
            event.setNextUse(TimeUnit.MINUTES.toMillis(5L));
            return true;
         }
      } else {
         plot1 = this.moduleCreative.getPlotManager().getPlot(event.getInt(event.getArgs()[0]));
         if (plot1 == null) {
            event.sendMessage("creative.not_in_world");
            return false;
         } else {
            Plot currentPlot = this.moduleCreative.getPlotManager().getCurrentPlot(event.getUser());
            if (plot1 == currentPlot) {
               event.sendMessage("creative.already_in_world");
               return false;
            } else {
               if (plot1.isClosed() && !event.getUser().hasPermission("creative.enter.bypass") && !plot1.isOwner(event.getUser())) {
                  event.getUser().sendMessage("мир_закрыт");
               } else {
                  if (currentPlot != null) {
                     this.moduleCreative.getPlotManager().teleportToLobby(event.getUser(), true);
                  }

                  plot1.addPlayer(event.getUser());
               }

               return true;
            }
         }
      }
   }
}
