package net.mineland.core.bukkit.modules.command;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;
import javax.annotation.Nullable;
import net.mineland.core.bukkit.module.Module;
import net.mineland.core.bukkit.modules.message.Message;
import net.mineland.core.bukkit.modules.time.ModuleTime;
import net.mineland.core.bukkit.modules.user.User;
import org.apache.commons.lang3.StringUtils;
import org.bukkit.Bukkit;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import ua.govnojon.libs.bukkitutil.chatcomponent.ChatComponent;

public abstract class Command extends org.bukkit.command.Command {
   private static ModuleCommand moduleCommand = (ModuleCommand)Module.getInstance(ModuleCommand.class);
   private static ModuleTime moduleTime = (ModuleTime)Module.getInstance(ModuleTime.class);

   public Command(String name, @Nullable String perm, String... aliases) {
      super(name);
      this.setPermission(perm);
      this.setPermissionMessage("lang:нет_прав");
      this.setAliases(Arrays.asList(aliases));
   }

   static List<String> filterTabResponse(List<String> list, String[] args) {
      return (List)list.stream().filter((el) -> {
         return StringUtils.containsIgnoreCase(el, args[args.length - 1]);
      }).collect(Collectors.toList());
   }

   public boolean execute(CommandSender sender, String label, String[] args) {
      this.fixArgs(args);
      if (!this.testPermission(sender)) {
         return false;
      } else {
         if (sender instanceof Player) {
            User user = User.getUser(sender);
            long time = moduleCommand.getTimeSinceLastUse(this, user);
            long current = System.currentTimeMillis();
            boolean bypassDelay = sender.hasPermission("mineland.skyblock.command.bypass_command_delay");
            if (time > current) {
               user.sendMessage(bypassDelay ? "эту_команду_теперь_можно_часто" : "эту_команду_нельзя_часто", "%cmd%", label, "%time%", moduleTime.displaySecond((time - current) / 1000L, user.getLang()));
               if (!bypassDelay) {
                  return false;
               }
            }
         }

         boolean result;
         try {
            result = this.execute(new CommandEvent(this, args, sender, label));
         } catch (CommandException var10) {
            result = false;
         } catch (Exception var11) {
            Bukkit.getLogger().info("Ошибка во время выполнения команды /" + label + " " + String.join(" ", args));
            var11.printStackTrace();
            this.sendException(sender, var11);
            result = false;
         }

         return result;
      }
   }

   @Nullable
   public List<String> tabComplete(CommandSender sender, String label, String[] args) {
      this.fixArgs(args);

      List result;
      try {
         result = this.tab(new CommandEvent(this, args, sender, label));
      } catch (CommandException var6) {
         result = null;
      } catch (Exception var7) {
         var7.printStackTrace();
         result = null;
      }

      return result == null ? null : filterTabResponse(result, args);
   }

   @Nullable
   public List<String> tab(CommandEvent event) {
      return event.getPlayers();
   }

   public abstract boolean execute(CommandEvent var1);

   private void fixArgs(String[] args) {
      try {
         for(int i = 0; i < args.length; ++i) {
            String arg = args[i];
            if (arg.length() > 2) {
               char c0 = arg.charAt(0);
               char cl = arg.charAt(arg.length() - 1);
               if (c0 == '[' && cl == ']' || c0 == '<' && cl == '>') {
                  args[i] = arg.substring(1, arg.length() - 1);
               }
            }
         }
      } catch (Exception var6) {
         var6.printStackTrace();
      }

   }

   private void sendException(CommandSender sender, Throwable e) {
      sender.sendMessage(Message.getMessage(sender, "ошибка_команды"));
      if (sender.hasPermission("mineland.libs.error.report")) {
         ChatComponent.getErrorReport(e, Message.getMessage(sender, "ошибка_наведи")).send(sender);
      }

   }
}
