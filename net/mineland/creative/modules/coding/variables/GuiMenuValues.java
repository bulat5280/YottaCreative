package net.mineland.creative.modules.coding.variables;

import net.mineland.api.user.IUser;
import net.mineland.core.bukkit.modules.gui.GuiItem;
import net.mineland.core.bukkit.modules.gui.GuiMenu;
import net.mineland.core.bukkit.modules.message.Message;
import net.mineland.core.bukkit.modules.user.User;
import net.mineland.creative.modules.coding.values.objects.Value;
import net.mineland.creative.modules.coding.values.objects.ValueType;
import org.bukkit.Material;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.ItemStack;
import ua.govnojon.libs.bukkitutil.ItemStackUtil;
import ua.govnojon.libs.bukkitutil.SimpleItem;

public class GuiMenuValues extends GuiMenu {
   public GuiMenuValues(String key, User user) {
      super(key, user, 3, Message.getMessage((IUser)user, "coding.gui.values.title"));

      for(int i = 0; i < ValueType.values().length; ++i) {
         ValueType valueType = ValueType.values()[i];
         Value value = valueType.create();
         this.addItem(new GuiMenuValues.GuiItemValue(this, i, value.getIcon().toItemStack(), valueType.name()));
      }

   }

   private class GuiItemValue extends GuiItem {
      private ItemStack item;

      GuiItemValue(GuiMenu guiMenu, int pos, ItemStack itemStack, String id) {
         super(guiMenu, pos, (ItemStack)(new SimpleItem(itemStack)).localizedName(id));
         this.setTextKey("coding.item.value." + id, new String[0]);
         this.item = (new SimpleItem(Material.APPLE)).localizedName(id);
         ItemStackUtil.setText(this.item, "lang:coding.item.value." + id);
      }

      public void click(InventoryClickEvent event) {
         this.getPlayer().getInventory().setItemInMainHand((new SimpleItem(this.item)).amount(this.getPlayer().getInventory().getItemInMainHand().getAmount()));
      }
   }
}
