package net.mineland.core.bukkit.modules.command;

import com.sk89q.bukkit.util.CommandInspector;
import org.bukkit.command.CommandSender;

class ContainerCommandInspectorBukkit implements CommandInspector {
   private CommandInspector inspector;
   private String perm;

   public ContainerCommandInspectorBukkit(CommandInspector inspector, String perm) {
      this.inspector = inspector;
      this.perm = perm;
   }

   public String getShortText(org.bukkit.command.Command command) {
      return this.inspector.getShortText(command);
   }

   public String getFullText(org.bukkit.command.Command command) {
      return this.inspector.getFullText(command);
   }

   public boolean testPermission(CommandSender sender, org.bukkit.command.Command command) {
      return sender.hasPermission(this.perm);
   }
}
