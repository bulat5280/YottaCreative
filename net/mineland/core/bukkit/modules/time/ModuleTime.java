package net.mineland.core.bukkit.modules.time;

import net.mineland.core.bukkit.module.BukkitModule;
import net.mineland.core.bukkit.modules.message.Message;
import net.mineland.core.bukkit.modules.user.Lang;
import org.bukkit.plugin.Plugin;
import ua.govnojon.libs.config.Config;
import ua.govnojon.libs.myjava.StringUtil;

public class ModuleTime extends BukkitModule {
   public ModuleTime(int priority, Plugin plugin) {
      super("time", priority, plugin, (Config)null);
   }

   public void onEnable() {
   }

   public void onDisable() {
   }

   public String displayMinute(long second, Lang lang) {
      return this.display(second * 1000L, lang, true, true, true, false, false);
   }

   public String displaySecond(long second, Lang lang) {
      return this.display(second * 1000L, lang, true, true, true, true, false);
   }

   public String displayHour(long second, Lang lang) {
      return this.display(second * 1000L, lang, true, true, false, false, false);
   }

   public String displayMillisec(long millisecond, Lang lang) {
      return this.display(millisecond, lang, true, true, true, true, true);
   }

   public String display(long millisec, Lang lang, boolean days, boolean hours, boolean minutes, boolean seconds, boolean milliseconds) {
      if (millisec == 0L) {
         return Message.getMessage(lang, "формат_времени_только_что");
      } else {
         StringBuilder time = new StringBuilder();
         boolean negative = millisec < 0L;
         if (negative) {
            millisec = -millisec;
         }

         int sec = (int)(millisec / 1000L);
         millisec %= 1000L;
         long min;
         if (sec >= 86400 && days) {
            min = (long)(sec / 86400);
            time.append(min).append(' ').append(Message.getMessage(lang, "формат_времени_день")).append(' ');
            sec = (int)((long)sec - min * 86400L);
         }

         if (sec >= 3600 && hours) {
            min = (long)(sec / 3600);
            time.append(min).append(' ').append(Message.getMessage(lang, "формат_времени_час")).append(' ');
            sec = (int)((long)sec - min * 3600L);
         }

         if (sec >= 60 && minutes) {
            min = (long)(sec / 60);
            time.append(min).append(' ').append(Message.getMessage(lang, "формат_времени_минута")).append(' ');
            sec = (int)((long)sec - min * 60L);
         }

         if (sec > 0 && seconds) {
            time.append(sec).append(' ').append(Message.getMessage(lang, "формат_времени_секунда")).append(' ');
         }

         if (millisec > 0L && milliseconds) {
            time.append(millisec).append(' ').append(Message.getMessage(lang, "формат_времени_милисекунда")).append(' ');
         }

         if (negative) {
            time.append(Message.getMessage(lang, "формат_времени_назад")).append(' ');
         }

         return StringUtil.removeLast(time.toString(), 1);
      }
   }
}
