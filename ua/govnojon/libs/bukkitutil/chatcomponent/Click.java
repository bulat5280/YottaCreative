package ua.govnojon.libs.bukkitutil.chatcomponent;

import net.md_5.bungee.api.chat.ClickEvent.Action;

public enum Click {
   open_url(Action.OPEN_URL),
   open_file(Action.OPEN_FILE),
   run_command(Action.RUN_COMMAND),
   suggest_command(Action.SUGGEST_COMMAND),
   change_page(Action.CHANGE_PAGE);

   Action action;

   private Click(Action action) {
      this.action = action;
   }

   public String get(Object o) {
      return String.valueOf(o);
   }
}
