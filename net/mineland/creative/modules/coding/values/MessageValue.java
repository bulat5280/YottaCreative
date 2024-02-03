package net.mineland.creative.modules.coding.values;

import net.mineland.creative.modules.coding.events.ChatEvent;
import net.mineland.creative.modules.coding.events.GameEvent;
import net.mineland.creative.modules.coding.values.objects.StringValue;
import org.bukkit.Material;
import org.bukkit.entity.Entity;
import ua.govnojon.libs.bukkitutil.ItemData;

public class MessageValue extends StringValue {
   public String get(GameEvent gameEvent, Entity entity) {
      return gameEvent instanceof ChatEvent ? ((ChatEvent)gameEvent).getMessage() : null;
   }

   public ItemData getIcon() {
      return new ItemData(Material.BOOK_AND_QUILL);
   }
}
