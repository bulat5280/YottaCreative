package net.mineland.creative.modules.creative.commands;

import net.mineland.core.bukkit.module.Module;
import net.mineland.core.bukkit.modules.command.Command;
import net.mineland.core.bukkit.modules.command.CommandEvent;
import net.mineland.core.bukkit.modules.user.User;
import net.mineland.creative.modules.creative.ModuleCreative;
import ua.govnojon.libs.bukkitutil.PlayerUtil;

public class CommandLoadPlot extends Command {
   private static ModuleCreative moduleCreative = (ModuleCreative)Module.getInstance(ModuleCreative.class);

   public CommandLoadPlot() {
      super("loadplot", "mineland.creative.command.loadplot");
   }

   public boolean execute(CommandEvent event) {
      event.checkIsConsole();
      String[] args = event.getArgs();
      User user = event.getUser();
      if (event.size() == 0) {
         return false;
      } else {
         if (event.size() == 1) {
            moduleCreative.getPlotManager().loadPlot(Integer.parseInt(args[0]), (plot) -> {
               moduleCreative.getLogger().info("Загрузили плот " + plot.getId() + " Владелец: " + user.getPlayer().getName());
               plot.addPlayer(user);
               PlayerUtil.sendTitle(user.getPlayer(), "", "", 0, 20, 0);
            });
         }

         return true;
      }
   }
}
