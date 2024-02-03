package net.mineland.creative.modules.creative.commands;

import net.mineland.core.bukkit.module.Module;
import net.mineland.core.bukkit.modules.command.Command;
import net.mineland.core.bukkit.modules.command.CommandEvent;
import net.mineland.core.bukkit.modules.user.User;
import net.mineland.creative.modules.creative.ModuleCreative;
import ua.govnojon.libs.bukkitutil.WorldUtil;

public class CommandLobby extends Command {
   private static ModuleCreative moduleCreative = (ModuleCreative)Module.getInstance(ModuleCreative.class);

   public CommandLobby() {
      super("spawn", (String)null, "leave", "s", "ы", "спавн");
   }

   public boolean execute(CommandEvent event) {
      event.checkIsConsole();
      String[] args = event.getArgs();
      User user = event.getUser();
      moduleCreative.getPlotManager().teleportToLobby(user, !user.getPlayer().getWorld().equals(WorldUtil.getDefaultWorld()));
      return true;
   }
}
