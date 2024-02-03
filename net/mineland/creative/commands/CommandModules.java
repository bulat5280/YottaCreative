package net.mineland.creative.commands;

import net.mineland.core.bukkit.module.Module;
import net.mineland.core.bukkit.modules.command.Command;
import net.mineland.core.bukkit.modules.command.CommandEvent;
import net.mineland.core.bukkit.modules.gui.ModuleGui;
import net.mineland.creative.modules.coding.gui.modules.GuiMenuModules;

public class CommandModules extends Command {
   private static ModuleGui moduleGui = (ModuleGui)Module.getInstance(ModuleGui.class);

   public CommandModules() {
      super("modules", (String)null);
   }

   public boolean execute(CommandEvent event) {
      moduleGui.openGui(event.getUser(), GuiMenuModules.class);
      return false;
   }
}
