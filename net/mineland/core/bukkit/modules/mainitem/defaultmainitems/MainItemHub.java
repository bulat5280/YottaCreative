package net.mineland.core.bukkit.modules.mainitem.defaultmainitems;

import net.mineland.core.bukkit.modules.mainitem.MainBar;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;

public class MainItemHub extends MainItemCommand {
   public MainItemHub(MainBar hotBar) {
      super(hotBar, (ItemStack)(new ItemStack(Material.BED)), "main_предмет_в_лобби", 8, "/bungeechat /hub");
      this.setTranslate(true);
   }
}
