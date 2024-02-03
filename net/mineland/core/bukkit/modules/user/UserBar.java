package net.mineland.core.bukkit.modules.user;

import net.mineland.core.bukkit.module.Module;
import net.mineland.core.bukkit.modules.mainitem.MainBar;
import net.mineland.core.bukkit.modules.mainitem.ModuleMainItem;

public class UserBar {
   private static ModuleMainItem moduleMainItem = (ModuleMainItem)Module.getInstance(ModuleMainItem.class);

   public static MainBar getMainBar(User user) {
      return moduleMainItem.getMainBar(user);
   }
}
