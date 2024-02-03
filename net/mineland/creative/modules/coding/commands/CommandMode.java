package net.mineland.creative.modules.coding.commands;

import java.util.Arrays;
import java.util.List;
import javax.annotation.Nullable;
import net.mineland.core.bukkit.modules.command.Command;
import net.mineland.core.bukkit.modules.command.CommandEvent;
import net.mineland.core.bukkit.modules.user.User;

public class CommandMode extends Command {
   private static List<String> arg0 = Arrays.asList("build", "code", "play");

   public CommandMode() {
      super("mode", (String)null);
   }

   @Nullable
   public List<String> tab(CommandEvent event) {
      return arg0;
   }

   public boolean execute(CommandEvent event) {
      event.checkIsConsole();
      String[] args = event.getArgs();
      User user = event.getUser();
      event.checkSizeArguments(1);
      String var4 = args[0];
      byte var5 = -1;
      switch(var4.hashCode()) {
      case -1430646092:
         if (var4.equals("building")) {
            var5 = 2;
         }
         break;
      case -1355086998:
         if (var4.equals("coding")) {
            var5 = 4;
         }
         break;
      case -493563858:
         if (var4.equals("playing")) {
            var5 = 7;
         }
         break;
      case 99349:
         if (var4.equals("dev")) {
            var5 = 5;
         }
         break;
      case 113291:
         if (var4.equals("run")) {
            var5 = 6;
         }
         break;
      case 3059181:
         if (var4.equals("code")) {
            var5 = 3;
         }
         break;
      case 3443508:
         if (var4.equals("play")) {
            var5 = 8;
         }
         break;
      case 94094958:
         if (var4.equals("build")) {
            var5 = 1;
         }
         break;
      case 1820422063:
         if (var4.equals("creative")) {
            var5 = 0;
         }
      }

      switch(var5) {
      case 0:
      case 1:
      case 2:
         user.getPlayer().chat("/build");
         break;
      case 3:
      case 4:
      case 5:
         user.getPlayer().chat("/dev");
         break;
      case 6:
      case 7:
      case 8:
         user.getPlayer().chat("/play");
      }

      return true;
   }
}
