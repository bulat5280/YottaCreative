package ua.govnojon.libs.bukkitutil.chatcomponent;

import net.md_5.bungee.api.chat.HoverEvent.Action;

public enum Hover {
   show_text(Action.SHOW_TEXT),
   show_achievement(Action.SHOW_ACHIEVEMENT),
   show_item(Action.SHOW_ITEM),
   show_entity(Action.SHOW_ENTITY);

   Action action;

   private Hover(Action action) {
      this.action = action;
   }

   public String get(Object o) {
      return String.valueOf(o);
   }
}
