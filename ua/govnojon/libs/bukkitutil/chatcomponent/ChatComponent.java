package ua.govnojon.libs.bukkitutil.chatcomponent;

import net.md_5.bungee.api.chat.BaseComponent;
import net.md_5.bungee.api.chat.ClickEvent;
import net.md_5.bungee.api.chat.HoverEvent;
import net.md_5.bungee.api.chat.TextComponent;
import net.md_5.bungee.api.chat.TranslatableComponent;
import net.mineland.api.user.IUser;
import net.mineland.core.bukkit.modules.message.Message;
import net.mineland.core.bukkit.modules.user.Lang;
import net.mineland.core.bukkit.modules.user.User;
import org.apache.commons.lang3.StringUtils;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import ua.govnojon.libs.bukkitutil.PlayerUtil;

public class ChatComponent {
   private BaseComponent component = new TextComponent();

   public ChatComponent() {
   }

   public ChatComponent(String text) {
      this.addText(text);
   }

   public static void sendAcceptReject(Player player, String message, String cmd1, String cmd2) {
      Lang lang = User.getUser(player).getLang();
      player.sendMessage(message);
      player.sendMessage(" ");
      ChatComponent component = new ChatComponent();
      component.addText("          ");
      component.addText(Message.getMessage(lang, "принять"), Hover.show_text, Message.getMessage(lang, "подсказка_принять"), Click.run_command, cmd1);
      component.addText("          ");
      component.addText(Message.getMessage(lang, "отклонить"), Hover.show_text, Message.getMessage(lang, "подсказка_отклонить"), Click.run_command, cmd2);
      component.send(player);
      player.sendMessage(" ");
      PlayerUtil.sendActionBar(player, Message.getMessage(player, "нажмите_в_чате"));
   }

   public static void sendHubWelcomeMessage(User user) {
      StringBuilder sb = new StringBuilder();

      for(int i = 0; i < 25; ++i) {
         sb.append("\n ");
      }

      user.getPlayer().sendMessage(sb.toString());
      ChatComponent component = new ChatComponent();
      component.addText("§8§m-----------------------------------------------------\n");
      component.addText(Message.getMessage((IUser)user, "hub.welcome_message"));
      component.addText("\n \n ");
      component.addText(Message.getMessage((IUser)user, "social_network.name"), Hover.show_text, Message.getMessage((IUser)user, "открыть_ссылку_подсказка"), Click.open_url, Message.getMessage((IUser)user, "social_network.link"));
      component.addText(" §7- ");
      component.addText(Message.getMessage((IUser)user, "discord.name"), Hover.show_text, Message.getMessage((IUser)user, "открыть_ссылку_подсказка"), Click.open_url, Message.getMessage((IUser)user, "discord.link"));
      component.addText(" §7- ");
      component.addText(Message.getMessage((IUser)user, "forum.name"), Hover.show_text, Message.getMessage((IUser)user, "открыть_ссылку_подсказка"), Click.open_url, Message.getMessage((IUser)user, "forum.link"));
      component.addText("§8§m-----------------------------------------------------");
      component.send(user.getPlayer());
   }

   public static ChatComponent getErrorReport(Throwable e, String text) {
      e.printStackTrace();
      ChatComponent message = new ChatComponent();
      StringBuilder error = new StringBuilder(e.getClass().getName());
      if (e.getMessage() != null) {
         error.append(": ").append(e.getMessage());
      }

      int limit = 0;
      StackTraceElement[] var5 = e.getStackTrace();
      int var6 = var5.length;

      for(int var7 = 0; var7 < var6; ++var7) {
         StackTraceElement element = var5[var7];
         String elem = element.toString();
         if (!StringUtils.contains(elem, "net.mineland") && !StringUtils.contains(elem, "ua.govnojon")) {
            error.append("\n§7 at    ").append(element);
         } else {
            error.append("\n§c at    ").append(element);
         }

         if (limit++ > 24) {
            error.append("\n§7(Ну там еще ошибки...)");
            break;
         }
      }

      message.addText(text, (Hover)Hover.show_text, error.toString());
      return message;
   }

   public static String[] stack(String string, int limit) {
      if (string != null && string.length() > limit && limit > 0) {
         char[] value = string.toCharArray();
         String[] result = new String[value.length / limit + 1];

         for(int pos = 0; pos < result.length; ++pos) {
            int size = Math.min(limit, value.length - pos * limit);
            char[] line = new char[size];
            System.arraycopy(value, pos * limit, line, 0, size);
            result[pos] = new String(line);
         }

         return result;
      } else {
         return new String[]{string};
      }
   }

   /** @deprecated */
   @Deprecated
   public ChatComponent addTextClickAndHover(String text, String hover, String command) {
      return this.addText(text, Hover.show_text, hover, Click.run_command, command);
   }

   public ChatComponent addText(String text, Hover hover, Object valueHover, Click click, Object valueClick) {
      this.component.addExtra(this.build(text, hover, valueHover, click, valueClick));
      return this;
   }

   public ChatComponent addLocalizedText(String text) {
      this.component.addExtra(new TranslatableComponent(text, new Object[0]));
      return this;
   }

   private BaseComponent build(String text, Hover hover, Object valueHover, Click click, Object valueClick) {
      BaseComponent component = new TextComponent(text);
      if (hover != null) {
         HoverEvent hoverEvent = new HoverEvent(hover.action, new BaseComponent[]{new TextComponent(hover.get(valueHover))});
         component.setHoverEvent(hoverEvent);
      }

      if (click != null) {
         ClickEvent clickEvent = new ClickEvent(click.action, click.get(valueClick));
         component.setClickEvent(clickEvent);
      }

      return component;
   }

   public ChatComponent addFirstText(String text, Hover hover, Object valueHover, Click click, Object valueClick) {
      BaseComponent component = this.build(text, hover, valueHover, click, valueClick);
      component.addExtra(this.component);
      this.component = component;
      return this;
   }

   public ChatComponent addFirstText(String text) {
      return this.addFirstText(text, (Hover)null, (Object)null, (Click)null, (Object)null);
   }

   public ChatComponent addText(String text, Hover hover, Object valueHover) {
      this.addText(text, hover, valueHover, (Click)null, (Object)null);
      return this;
   }

   public ChatComponent addText(String text, Click click, Object valueClick) {
      this.addText(text, (Hover)null, (Object)null, click, valueClick);
      return this;
   }

   public ChatComponent addText(String text) {
      this.addText(text, (Hover)null, (Object)null, (Click)null, (Object)null);
      return this;
   }

   public void send(CommandSender player) {
      player.sendMessage(this.component);
   }

   public BaseComponent getBaseComponent() {
      return this.component;
   }
}
