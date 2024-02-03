package net.mineland.creative.commands;

import net.mineland.core.bukkit.modules.command.Command;
import net.mineland.core.bukkit.modules.command.CommandEvent;

public class CommandWorldBan extends Command {
   public CommandWorldBan() {
      super("wban", "command.wban", "worldban", "worldb", "bbworld");
   }

   public boolean execute(CommandEvent event) {
      String[] args = event.getArgs();
      if (args.length != 0) {
         event.checkSizeArguments(2);
         String id = event.arg(0);
         if (args[0].equalsIgnoreCase(id)) {
         }
      }

      return false;
   }
}
