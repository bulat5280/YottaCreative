package net.mineland.core.bukkit.modules.command;

import java.util.List;
import java.util.Map;

public class CommandManager {
   private static ModuleCommand moduleCommand = ModuleCommand.getInstance();
   public static final Map<String, org.bukkit.command.Command> knownCommands;

   public static void registerCommand(org.bukkit.command.Command command) {
      moduleCommand.registerCommand(command);
   }

   public static List<org.bukkit.command.Command> getCommands() {
      return moduleCommand.getCommands();
   }

   public static org.bukkit.command.Command getCommand(String command) {
      return moduleCommand.getCommand(command);
   }

   public static void unregisterCommand(String command) {
      moduleCommand.unregisterCommand(command);
   }

   public static void unregisterCommand(org.bukkit.command.Command command) {
      unregisterCommand(command.getName());
   }

   public static void unregisterCommand(Class<? extends org.bukkit.command.Command> commandClass) {
      moduleCommand.unregisterCommand(commandClass);
   }

   static {
      knownCommands = moduleCommand.knownCommands;
   }
}
