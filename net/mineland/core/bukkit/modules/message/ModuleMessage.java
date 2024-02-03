package net.mineland.core.bukkit.modules.message;

import java.util.Arrays;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.TimeUnit;
import net.mineland.core.bukkit.module.BukkitModule;
import net.mineland.core.bukkit.modules.mysql.SQL;
import net.mineland.core.bukkit.modules.user.Lang;
import net.mineland.core.bukkit.modules.user.User;
import org.apache.commons.lang3.StringUtils;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.plugin.Plugin;
import org.jooq.Record;
import org.jooq.Result;
import ua.govnojon.libs.bukkitutil.chatcomponent.ChatComponentUtil;
import ua.govnojon.libs.bukkitutil.schedule.Schedule;
import ua.govnojon.libs.config.Config;

public class ModuleMessage extends BukkitModule {
   private Map<String, LangMessage> messages = new HashMap();
   private List<String> notFound = new LinkedList();
   private Lang[] values = Lang.values();

   public ModuleMessage(int priority, Plugin plugin) {
      super("message", priority, plugin, (Config)null);
   }

   public void onFirstEnable() {
      SQL.sync("CREATE TABLE IF NOT EXISTS `messages` (\n  `key` varchar(100) CHARACTER SET utf8 NOT NULL,\n  `index` varchar(15) CHARACTER SET utf8 NOT NULL,\n  `message_ru` text COLLATE utf8mb4_unicode_ci NOT NULL,\n  `message_en` text COLLATE utf8mb4_unicode_ci NOT NULL,\n  `is_translated` tinyint(1) NOT NULL DEFAULT '0',\n  `last_change` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,\n  PRIMARY KEY (`key`)\n) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;");
      SQL.sync("CREATE TABLE IF NOT EXISTS `message_history` (\n  `id` int(11) NOT NULL AUTO_INCREMENT,\n  `key` varchar(100) CHARACTER SET utf8 NOT NULL,\n  `message_ru` text COLLATE utf8mb4_unicode_ci NOT NULL,\n  `message_en` text COLLATE utf8mb4_unicode_ci NOT NULL,\n  `change_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP,\n  PRIMARY KEY (`id`)\n) ENGINE=InnoDB  DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci AUTO_INCREMENT=1 ;");
   }

   public void onEnable() {
      this.getLogger().info("Загрузка настроек сообщений.");
      Schedule.timer(this.notFound::clear, 10L, 10L, TimeUnit.MINUTES);
      SQL.async((create) -> {
         this.addAll(create.fetch("SELECT `key`, `message_ru`, `message_en` FROM `messages`"));
      });
   }

   private void addAll(Result<Record> records) {
      Map<String, LangMessage> tmpMessages = new HashMap();
      records.forEach((record) -> {
         String key = ((String)record.get("key", String.class)).toLowerCase();
         tmpMessages.put(key, this.createMessage(record));
      });
      this.messages.clear();
      this.messages = tmpMessages;
   }

   private LangMessage createMessage(Record record) {
      LangMessage message = new LangMessage();
      Arrays.stream(this.values).forEach((lang) -> {
         String msg = this.fix((String)record.get("message_" + lang.getLang(), String.class));
         if (msg != null && !msg.isEmpty()) {
            message.getMessage().put(lang, msg);
         }

      });
      return message;
   }

   public void onDisable() {
   }

   public String getMessage(Player player, String key) {
      User user = User.getUser(player);
      return this.getMessage(user == null ? Lang.RU : user.getLang(), key);
   }

   public String getMessage(CommandSender sender, String key) {
      return this.getMessage(Lang.getLang(sender), key);
   }

   public String getMessage(Lang lang, String key) {
      if (lang == null) {
         throw new NullPointerException("lang == null");
      } else {
         key = ChatComponentUtil.removeColors(key.toLowerCase());
         LangMessage message = (LangMessage)this.messages.get(key);
         if (message == null) {
            return this.notFound(key);
         } else {
            String msg = (String)message.getMessage().get(lang);
            if (msg != null && !msg.isEmpty()) {
               return msg;
            } else {
               return message.getMessage().isEmpty() ? this.notFound(key) : (String)message.getMessage().values().iterator().next();
            }
         }
      }
   }

   public boolean isMessageExist(String key) {
      LangMessage message = (LangMessage)this.messages.get(key);
      return message != null;
   }

   public String notFound(String key) {
      String messageNotFound = "Сообщение с ключом '" + key + "' не найдено в БД.";
      if (!this.notFound.contains(key)) {
         SQL.async((create) -> {
            return create.fetchOne("\tSELECT `key`, `message_ru`, `message_en` FROM `messages` WHERE `key`=? LIMIT 1", key);
         }, (record) -> {
            if (record == null) {
               this.notFound.add(key);

               try {
                  SQL.async("INSERT INTO `messages` (`key`, `index`, `message_ru`, `message_en`) VALUES (?, ?, ?, ?)", key, key.contains(".") ? key.split("\\.")[0] : "", "", messageNotFound);
               } catch (Exception var5) {
               }
            } else {
               this.messages.put(key, this.createMessage(record));
            }

         });
      }

      return messageNotFound;
   }

   public Map<String, LangMessage> getMessages() {
      return this.messages;
   }

   public List<String> getNotFound() {
      return this.notFound;
   }

   private String fix(String msg) {
      if (msg != null && !msg.isEmpty()) {
         msg = StringUtils.replace(msg, "&", "§");
         msg = StringUtils.replace(msg, "\\n", "\n");
         msg = StringUtils.replace(msg, "\r", "");
         return msg;
      } else {
         return null;
      }
   }

   public void setMessage(Lang lang, String key, String text) {
      key = key.toLowerCase();
      LangMessage message = (LangMessage)this.messages.computeIfAbsent(key, (k) -> {
         return new LangMessage();
      });
      String msg = this.fix(text);
      if (msg != null && !msg.isEmpty()) {
         message.getMessage().put(lang, msg);
      }

      SQL.async((create) -> {
         create.execute("INSERT IGNORE INTO `messages`(`key`, `index`, `message_ru`, `message_en`) VALUES (?,'','','')", key);
         create.execute("UPDATE `messages` SET `message_" + lang.getLang() + "`=? WHERE `key`=?;", text, key);
      });
   }
}
