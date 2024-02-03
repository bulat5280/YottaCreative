package net.mineland.core.bukkit.modules.message;

import java.util.Arrays;
import java.util.Collection;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.atomic.AtomicReference;
import java.util.function.BiFunction;
import java.util.function.Function;
import net.mineland.api.user.IUser;
import net.mineland.core.bukkit.module.Module;
import net.mineland.core.bukkit.modules.user.Lang;
import net.mineland.core.bukkit.modules.user.User;
import org.apache.commons.lang3.StringUtils;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import ua.govnojon.libs.bukkitutil.chatcomponent.ChatComponent;

public class Message {
   public static final String COLOR = "§";
   private static ModuleMessage moduleMessage = (ModuleMessage)Module.getInstance(ModuleMessage.class);
   private String key;
   private Map<String, Function<Lang, String>> replaced = new LinkedHashMap();

   public Message(String key, String... replaced) {
      this.key = key;

      for(int i = 0; i < replaced.length; i += 2) {
         String replacement = replaced[i + 1];
         this.replaced.put(replaced[i], (lang) -> {
            return replacement;
         });
      }

   }

   public static Message getMessage(String key, String... replaced) {
      return new Message(key, replaced);
   }

   public static String getMessage(Player player, String key, String... replaced) {
      return replaced(moduleMessage.getMessage(player, key), replaced);
   }

   public static String getMessage(CommandSender sender, String key, String... replaced) {
      return replaced(moduleMessage.getMessage(sender, key), replaced);
   }

   public static String getMessage(Lang lang, String key, String... replaced) {
      return replaced(moduleMessage.getMessage(lang, key), replaced);
   }

   public static List<String> getMultiLineMessage(Lang lang, String key, String... replaced) {
      String result = replaced(moduleMessage.getMessage(lang, key), replaced);
      return Arrays.asList(result.split("<>"));
   }

   public static String getMessage(IUser user, String key, String... replaced) {
      return replaced(moduleMessage.getMessage(((User)user).getLang(), key), replaced);
   }

   public static boolean isMessageExist(String key) {
      return moduleMessage.isMessageExist(key);
   }

   public static void broadcast(String key, String... replaced) {
      broadcast(User.getUsers(), key, replaced);
   }

   public static void broadcast(Collection<User> players, String key, String... replaced) {
      if (players.size() != 0) {
         Map<Lang, String> message = getMessageTranslate(key, replaced);
         broadcast(players, message);
      }
   }

   public static void broadcast(Map<Lang, String> message) {
      broadcast(User.getUsers(), message);
   }

   public static void broadcast(Collection<User> users, Map<Lang, String> message) {
      Iterator var2 = users.iterator();

      while(var2.hasNext()) {
         User player = (User)var2.next();
         player.getPlayer().sendMessage((String)message.get(player.getLang()));
      }

   }

   public static void broadcastChatComponent(Collection<User> users, Map<Lang, ChatComponent> message) {
      users.forEach((player) -> {
         ((ChatComponent)message.get(player.getLang())).send(player.getPlayer());
      });
   }

   public static void sendMessage(CommandSender sender, String key, String... replaced) {
      sender.sendMessage(getMessage(sender, key, replaced));
   }

   public static void sendMessage(Player player, String key, String... replaced) {
      sendMessage(User.getUser(player), key, replaced);
   }

   public static void sendMessage(User user, String key, String... replaced) {
      user.getPlayer().sendMessage(getMessage(user.getLang(), key, replaced));
   }

   public static Map<Lang, String> getMessageTranslate(Function<Lang, String> mapper) {
      Map<Lang, String> message = new HashMap();
      Lang[] var2 = Lang.values();
      int var3 = var2.length;

      for(int var4 = 0; var4 < var3; ++var4) {
         Lang lang = var2[var4];
         message.put(lang, mapper.apply(lang));
      }

      return message;
   }

   public static Map<Lang, String> getMessageTranslate(String key, String... replaced) {
      HashMap<Lang, String> message = new HashMap();
      Lang[] var3 = Lang.values();
      int var4 = var3.length;

      for(int var5 = 0; var5 < var4; ++var5) {
         Lang lang = var3[var5];
         message.put(lang, getMessage(lang, key, replaced));
      }

      return message;
   }

   private static String replaced(String mess, String... replaced) {
      for(int i = 0; i < replaced.length - 1; i += 2) {
         mess = StringUtils.replace(mess, replaced[i], replaced[i + 1]);
      }

      return mess;
   }

   public String getKey() {
      return this.key;
   }

   public void send(User user) {
      user.getPlayer().sendMessage(this.translate(user.getLang()));
   }

   public void send(User user, BiFunction<User, String, String> function) {
      user.getPlayer().sendMessage((String)function.apply(user, this.translate(user.getLang())));
   }

   public void broadcast(Collection<User> users) {
      Map<Lang, String> translate = this.translate();
      users.forEach((user) -> {
         user.getPlayer().sendMessage((String)translate.get(user.getLang()));
      });
   }

   public void broadcast(Collection<User> users, BiFunction<User, String, String> function) {
      Map<Lang, String> translate = this.translate();
      users.forEach((user) -> {
         user.getPlayer().sendMessage((String)function.apply(user, translate.get(user.getLang())));
      });
   }

   public void broadcast() {
      this.broadcast(User.getUsers());
   }

   public Message replace(String target, String replacement) {
      this.replaced.put(target, (lang) -> {
         return replacement;
      });
      return this;
   }

   public Message replace(String target, Message message) {
      this.replaced.put(target, message != null ? message::translate : null);
      return this;
   }

   public Message replace(String target, Function<Lang, String> function) {
      this.replaced.put(target, function);
      return this;
   }

   public String translate(Lang lang) {
      AtomicReference<String> message = new AtomicReference(getMessage(lang, this.key));
      this.replaced.forEach((target, replacement) -> {
         message.set(StringUtils.replace((String)message.get(), target, replacement != null ? (String)replacement.apply(lang) : ""));
      });
      return (String)message.get();
   }

   public Map<Lang, String> translate() {
      Map<Lang, String> message = new HashMap();
      Lang[] var2 = Lang.values();
      int var3 = var2.length;

      for(int var4 = 0; var4 < var3; ++var4) {
         Lang lang = var2[var4];
         AtomicReference<String> messageLang = new AtomicReference(getMessage(lang, this.key));
         this.replaced.forEach((target, replacement) -> {
            messageLang.set(StringUtils.replace((String)messageLang.get(), target, replacement != null ? (String)replacement.apply(lang) : ""));
         });
         message.put(lang, messageLang.get());
      }

      return message;
   }
}
