package net.mineland.core.bukkit.modules.gui;

import net.mineland.api.user.IUser;
import net.mineland.core.bukkit.modules.message.Message;
import org.bukkit.Material;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.ItemStack;

public class GuiItemOpenPage extends GuiItem {
   private GuiPage page;
   private int open;

   GuiItemOpenPage(GuiPage page, int open, int x) {
      super(page.getGuiMenu(), x, page.getGuiMenu().getSize() / 9, (ItemStack)(new ItemStack(Material.ARROW)));
      this.page = page;
      this.open = open;
      this.setText(Message.getMessage((IUser)this.getUser(), open > page.getPage() ? "гуи_меню_следующая_страница" : "гуи_меню_предыдущая_страница", "%page%", String.valueOf(open + 1)));
   }

   public void click(InventoryClickEvent event) {
      this.page.getGuiMenu().openPage(this.open);
   }
}
