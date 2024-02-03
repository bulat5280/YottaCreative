package net.mineland.core.bukkit.modules.message;

import java.util.HashMap;
import java.util.Map;
import net.mineland.core.bukkit.modules.user.Lang;

public class LangMessage {
   private Map<Lang, String> message = new HashMap();

   public Map<Lang, String> getMessage() {
      return this.message;
   }
}
