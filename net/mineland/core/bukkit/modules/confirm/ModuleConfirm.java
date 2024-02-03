package net.mineland.core.bukkit.modules.confirm;

import java.util.HashMap;
import java.util.Map;
import java.util.function.Consumer;
import net.mineland.api.user.IUser;
import net.mineland.core.bukkit.module.BukkitModule;
import net.mineland.core.bukkit.modules.message.Message;
import net.mineland.core.bukkit.modules.user.User;
import org.apache.commons.lang3.StringUtils;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.player.AsyncPlayerChatEvent;
import org.bukkit.plugin.Plugin;
import ua.govnojon.libs.bukkitutil.PlayerUtil;
import ua.govnojon.libs.bukkitutil.chatcomponent.ChatComponent;
import ua.govnojon.libs.bukkitutil.chatcomponent.ChatComponentUtil;
import ua.govnojon.libs.bukkitutil.chatcomponent.Click;
import ua.govnojon.libs.bukkitutil.chatcomponent.Hover;
import ua.govnojon.libs.bukkitutil.schedule.Schedule;
import ua.govnojon.libs.config.Config;

public class ModuleConfirm extends BukkitModule {
   public ModuleConfirm(int priority, Plugin plugin) {
      super("confirm", priority, plugin, (Config)null);
   }

   public void onFirstEnable() {
      this.registerListenersThis();
   }

   public void onEnable() {
   }

   public void onDisable() {
   }

   public void openURL(User user, String url, Runnable deny) {
      String urlDisplay;
      if (!StringUtils.startsWithIgnoreCase(url, "http")) {
         urlDisplay = "http://" + url;
      } else {
         urlDisplay = url;
      }

      urlDisplay = urlDisplay.length() > 85 ? urlDisplay.substring(0, 85) + "..." : urlDisplay;
      Consumer<Boolean> consumer = (done) -> {
         if (!done) {
            deny.run();
         }

      };
      String hash = String.valueOf(consumer.hashCode());
      ChatComponent component = new ChatComponent();
      component.addText(Message.getMessage(user.getLang(), "открытие.url", "{url}", urlDisplay) + "\n\n", (Click)Click.open_url, url);
      component.addText(Message.getMessage((IUser)user, "открыть_ссылку"), Hover.show_text, Message.getMessage((IUser)user, "открыть_ссылку_подсказка"), Click.open_url, url);
      component.addText("\n\n");
      component.addText(Message.getMessage((IUser)user, "назад"), Hover.show_text, Message.getMessage((IUser)user, "гуи_предмет_назад"), Click.run_command, "confirm#" + hash + "#false");
      this.confirm(user, consumer, component);
   }

   public void confirm(User user, String text, Consumer<Boolean> consumer) {
      String hash = String.valueOf(consumer.hashCode());
      ChatComponent component = new ChatComponent();
      component.addText(text + "\n\n");
      component.addText(Message.getMessage((IUser)user, "подтвердить"), Hover.show_text, Message.getMessage((IUser)user, "гуи_предмет_подтвердить"), Click.run_command, "confirm#" + hash + "#true");
      component.addText("\n\n");
      component.addText(Message.getMessage((IUser)user, "отменить"), Hover.show_text, Message.getMessage((IUser)user, "гуи_предмет_отменить"), Click.run_command, "confirm#" + hash + "#false");
      this.confirm(user, consumer, component);
   }

   private void confirm(User user, Consumer<Boolean> consumer, ChatComponent component) {
      ChatComponentUtil.fixVisibleColors(component.getBaseComponent());
      String hash = String.valueOf(consumer.hashCode());
      Map<String, Consumer<Boolean>> map = (Map)user.getMetadata("confirm-system", HashMap::new);
      map.put(hash, consumer);
      PlayerUtil.openBook(user, component);
   }

   @EventHandler(
      priority = EventPriority.LOWEST,
      ignoreCancelled = true
   )
   public void on(AsyncPlayerChatEvent event) {
      if (event.getMessage().startsWith("confirm#")) {
         User user = User.getUser(event.getPlayer());
         String[] data = event.getMessage().split("#");
         Map<String, Consumer<Boolean>> map = (Map)user.getMetadata("confirm-system", HashMap::new);
         Consumer<Boolean> consumer = (Consumer)map.remove(data[1]);
         if (consumer != null) {
            event.setCancelled(true);

            try {
               boolean done = Boolean.parseBoolean(data[2]);
               Schedule.run(() -> {
                  consumer.accept(done);
               });
            } catch (Exception var7) {
            }
         }
      }

   }
}
