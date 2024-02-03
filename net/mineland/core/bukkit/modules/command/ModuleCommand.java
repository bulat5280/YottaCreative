package net.mineland.core.bukkit.modules.command;

import com.boydti.fawe.bukkit.BukkitCommand;
import com.boydti.fawe.object.FaweCommand;
import com.sk89q.bukkit.util.CommandInspector;
import com.sk89q.bukkit.util.DynamicPluginCommand;
import com.sk89q.minecraft.util.commands.CommandPermissions;
import com.sk89q.worldedit.util.command.CommandCallable;
import com.sk89q.worldedit.util.command.CommandMapping;
import com.sk89q.worldedit.util.command.SimpleDispatcher;
import com.sk89q.worldedit.util.command.parametric.ParametricCallable;
import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.TimeUnit;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;
import net.mineland.core.bukkit.module.BukkitModule;
import net.mineland.core.bukkit.modules.user.User;
import org.bukkit.Bukkit;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.PluginCommand;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.player.PlayerCommandPreprocessEvent;
import org.bukkit.event.server.TabCompleteEvent;
import org.bukkit.plugin.Plugin;
import ua.govnojon.libs.bukkitutil.schedule.Schedule;
import ua.govnojon.libs.config.Config;
import ua.govnojon.libs.myjava.MyObject;

public class ModuleCommand extends BukkitModule {
   private static ModuleCommand instance;
   public final Map<String, org.bukkit.command.Command> knownCommands = (Map)(new MyObject(Bukkit.getServer())).getField("commandMap").getField("knownCommands").getObject();
   private List<org.bukkit.command.Command> commands = new LinkedList();
   private Map<String, Map<Command, Long>> history = new HashMap();
   private Pattern commandPrefix = Pattern.compile("/\\S*:");
   private Pattern commandNamePrefix = Pattern.compile("\\S*:");

   public ModuleCommand(int priority, Plugin plugin) {
      super("command", priority, plugin, (Config)null);
      instance = this;
   }

   public static ModuleCommand getInstance() {
      return instance;
   }

   public void onFirstEnable() {
      this.registerListenersThis();
      Schedule.timer(() -> {
         long current = System.currentTimeMillis();
         ((List)this.history.entrySet().stream().filter((data) -> {
            ((List)((Map)data.getValue()).entrySet().stream().filter((dataCmd) -> {
               return (Long)dataCmd.getValue() < current;
            }).collect(Collectors.toList())).forEach((dataCmd) -> {
               Long var10000 = (Long)((Map)data.getValue()).remove(dataCmd.getKey());
            });
            return ((Map)data.getValue()).isEmpty();
         }).collect(Collectors.toList())).forEach((data) -> {
            Map var10000 = (Map)this.history.remove(data.getKey());
         });
      }, 10L, 10L, TimeUnit.MINUTES);
   }

   public void onEnable() {
      this.injectWorldEdit();
      this.injectFAWE();
      this.injectNoCheatPlus();
      this.injectSpartan();
      this.fixPluginPrefixExistCommands();
   }

   private void injectSpartan() {
      try {
         org.bukkit.command.Command command = (org.bukkit.command.Command)this.knownCommands.get("spartan");
         if (command != null) {
            command.setPermission("spartan.verbose");
         }
      } catch (Throwable var2) {
         var2.printStackTrace();
      }

   }

   private void injectNoCheatPlus() {
      try {
         org.bukkit.command.Command command = (org.bukkit.command.Command)this.knownCommands.get("nocheatplus");
         if (command != null) {
            command.setPermission("nocheatplus.filter.command.nocheatplus");
         }
      } catch (Throwable var2) {
         var2.printStackTrace();
      }

   }

   private void injectFAWE() {
      try {
         Field cmd = BukkitCommand.class.getDeclaredField("cmd");
         cmd.setAccessible(true);
         Iterator var2 = this.knownCommands.values().iterator();

         while(var2.hasNext()) {
            org.bukkit.command.Command command = (org.bukkit.command.Command)var2.next();
            if (command instanceof PluginCommand) {
               PluginCommand pluginCommand = (PluginCommand)command;
               CommandExecutor executor = pluginCommand.getExecutor();
               if (executor instanceof BukkitCommand) {
                  BukkitCommand bukkitCommand = (BukkitCommand)executor;
                  FaweCommand faweCommand = (FaweCommand)cmd.get(bukkitCommand);
                  String perm = faweCommand.getPerm();
                  command.setPermission(perm);
               }
            }
         }

         cmd.setAccessible(false);
      } catch (Throwable var9) {
         var9.printStackTrace();
      }

   }

   private void injectWorldEdit() {
      try {
         Field registeredWith = DynamicPluginCommand.class.getDeclaredField("registeredWith");
         registeredWith.setAccessible(true);
         Field commandPermissions = ParametricCallable.class.getDeclaredField("commandPermissions");
         commandPermissions.setAccessible(true);
         Iterator var3 = this.knownCommands.values().iterator();

         while(true) {
            while(true) {
               while(true) {
                  DynamicPluginCommand pluginCommand;
                  CommandMapping mapping;
                  do {
                     org.bukkit.command.Command command;
                     do {
                        if (!var3.hasNext()) {
                           registeredWith.setAccessible(false);
                           commandPermissions.setAccessible(false);
                           return;
                        }

                        command = (org.bukkit.command.Command)var3.next();
                     } while(!(command instanceof DynamicPluginCommand));

                     pluginCommand = (DynamicPluginCommand)command;
                     com.sk89q.worldedit.extension.platform.CommandManager instance = com.sk89q.worldedit.extension.platform.CommandManager.getInstance();
                     mapping = instance.getDispatcher().get(command.getName());
                  } while(mapping == null);

                  CommandCallable callable = mapping.getCallable();
                  if (callable instanceof SimpleDispatcher) {
                     SimpleDispatcher dispatcher = (SimpleDispatcher)callable;
                     Iterator var17 = dispatcher.getCommands().iterator();

                     while(var17.hasNext()) {
                        CommandMapping subcommand = (CommandMapping)var17.next();
                        List<String> perms = subcommand.getDescription().getPermissions();
                        if (!perms.isEmpty()) {
                           String perm = (String)perms.get(0);
                           pluginCommand.setPermissions(new String[]{perm});
                           CommandInspector old = (CommandInspector)registeredWith.get(pluginCommand);
                           registeredWith.set(pluginCommand, new ContainerCommandInspectorBukkit(old, perm));
                           break;
                        }
                     }
                  } else if (callable instanceof ParametricCallable) {
                     ParametricCallable parametricCallable = (ParametricCallable)callable;
                     List<String> perms = parametricCallable.getDescription().getPermissions();
                     CommandPermissions permissions = (CommandPermissions)commandPermissions.get(parametricCallable);
                     if (perms.isEmpty() && (permissions == null || permissions.value().length == 0)) {
                        String perm = "worldedit.noperm";
                        pluginCommand.setPermissions(new String[]{perm});
                        CommandInspector old = (CommandInspector)registeredWith.get(pluginCommand);
                        registeredWith.set(pluginCommand, new ContainerCommandInspectorBukkit(old, perm));
                     }
                  }
               }
            }
         }
      } catch (Throwable var15) {
         var15.printStackTrace();
      }
   }

   public void onDisable() {
   }

   private void fixPluginPrefixExistCommands() {
      Iterator var1 = (new ArrayList(this.knownCommands.keySet())).iterator();

      while(var1.hasNext()) {
         String key = (String)var1.next();
         Matcher matcher = this.commandNamePrefix.matcher(key);
         if (matcher.find()) {
            String newKey = matcher.replaceFirst("");
            org.bukkit.command.Command command = (org.bukkit.command.Command)this.knownCommands.remove(key);
            this.knownCommands.put(newKey, command);
         }
      }

   }

   @EventHandler(
      priority = EventPriority.LOWEST
   )
   public void on(PlayerCommandPreprocessEvent event) {
      event.setMessage(this.fixPluginPrefix(event.getMessage()));
   }

   @EventHandler(
      priority = EventPriority.LOWEST
   )
   public void on(TabCompleteEvent event) {
      List<String> completions = (List)event.getCompletions().stream().map(this::fixPluginPrefix).collect(Collectors.toList());
      event.setCompletions(completions);
   }

   private String fixPluginPrefix(String arg) {
      return this.commandPrefix.matcher(arg).replaceFirst("/");
   }

   public void registerCommand(org.bukkit.command.Command command) {
      this.unregisterCommand(command.getName());
      this.knownCommands.put(command.getName(), command);
      Iterator var2 = command.getAliases().iterator();

      while(var2.hasNext()) {
         String a = (String)var2.next();
         this.knownCommands.put(a, command);
      }

      this.commands.add(command);
   }

   public void unregisterCommand(String command) {
      org.bukkit.command.Command c = (org.bukkit.command.Command)this.knownCommands.remove(command);
      if (c != null) {
         this.knownCommands.remove(c.getName());
         Iterator var3 = c.getAliases().iterator();

         while(var3.hasNext()) {
            String a = (String)var3.next();
            this.knownCommands.remove(a);
         }

         this.commands.remove(c);
      }

   }

   public void unregisterCommand(Class<? extends org.bukkit.command.Command> commandClass) {
      this.knownCommands.entrySet().stream().filter((entry) -> {
         return ((org.bukkit.command.Command)entry.getValue()).getClass() == commandClass;
      }).findAny().ifPresent((entry) -> {
         org.bukkit.command.Command command = (org.bukkit.command.Command)entry.getValue();
         this.unregisterCommand(command.getName());
      });
   }

   public List<org.bukkit.command.Command> getCommands() {
      return this.commands;
   }

   public org.bukkit.command.Command getCommand(String command) {
      return (org.bukkit.command.Command)this.knownCommands.get(command);
   }

   public long getTimeSinceLastUse(Command command, User user) {
      Long last = (Long)this.getHistory(user).get(command);
      return last == null ? 0L : last;
   }

   public void setTimeSinceLastUse(Command command, User user, long delay) {
      this.getHistory(user).put(command, System.currentTimeMillis() + delay);
   }

   private Map<Command, Long> getHistory(User user) {
      return (Map)this.history.computeIfAbsent(user.getName(), (k) -> {
         return new HashMap();
      });
   }

   public Map<String, org.bukkit.command.Command> getKnownCommands() {
      return this.knownCommands;
   }
}
