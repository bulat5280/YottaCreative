package net.mineland.core.bukkit.modules.gui;

import net.mineland.core.bukkit.modules.message.Message;
import net.mineland.core.bukkit.modules.user.Lang;
import net.mineland.core.bukkit.modules.user.User;

public enum AvailabilityType {
   SELECTED(5, "§a", "availability_type_selected"),
   AVAILABLE(1, "§6", "availability_type_available"),
   NOT_AVAILABLE(14, "§4", "availability_type_not_available"),
   FOR_GAMER(14, "§4", "для_gamer"),
   FOR_SKILLED(14, "§4", "для_skilled"),
   FOR_EXPERT(14, "§4", "для_expert"),
   FOR_HERO(14, "§4", "для_hero");

   private int panelColor;
   private String chatColor;
   private String messageKey;

   private AvailabilityType(int panelColor, String chatColor, String messageKey) {
      this.panelColor = panelColor;
      this.chatColor = chatColor;
      this.messageKey = messageKey;
   }

   public int getPanelColor() {
      return this.panelColor;
   }

   public String getChatColor() {
      return this.chatColor;
   }

   public String getMessage(Lang lang) {
      return this.chatColor + Message.getMessage(lang, this.messageKey);
   }

   public AvailabilityType getRightType(User set, String perm) {
      return set.getPlayer().hasPermission(perm) ? AVAILABLE : this;
   }
}
