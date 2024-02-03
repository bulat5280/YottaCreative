package net.mineland.creative.commands;

import net.mineland.core.bukkit.module.Module;
import net.mineland.core.bukkit.modules.command.Command;
import net.mineland.core.bukkit.modules.command.CommandEvent;
import net.mineland.creative.modules.creative.ModuleCreative;
import net.mineland.creative.modules.creatorpoints.ModuleCreatorPoints;

public class CommandUser extends Command {
   private static ModuleCreative moduleCreative = (ModuleCreative)Module.getInstance(ModuleCreative.class);
   private static ModuleCreatorPoints moduleCreatorPoints = (ModuleCreatorPoints)Module.getInstance(ModuleCreatorPoints.class);

   public CommandUser() {
      super("user", (String)null);
   }

   public boolean execute(CommandEvent event) {
      String[] args = event.getArgs();
      if (args.length == 0) {
         event.sendMessage("использование_команды.user");
      }

      if (args.length != 0) {
         event.sendMessage("команда.в.разработке");
      }

      return false;
   }
}
