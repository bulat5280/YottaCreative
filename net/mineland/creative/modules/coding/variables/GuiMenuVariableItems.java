package net.mineland.creative.modules.coding.variables;

import net.mineland.api.user.IUser;
import net.mineland.core.bukkit.modules.gui.GuiMenu;
import net.mineland.core.bukkit.modules.message.Message;
import net.mineland.core.bukkit.modules.user.User;
import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.inventory.ItemStack;
import ua.govnojon.libs.bukkitutil.PlayerUtil;

public class GuiMenuVariableItems extends GuiMenu {
   public GuiMenuVariableItems(String key, User user) {
      super(key, user, 1, Message.getMessage((IUser)user, "coding.gui.variable_items.title"));
      this.addItem(0, new ItemStack(Material.BOOK), Message.getMessage((IUser)user, "coding.item.text"), (event) -> {
         this.getPlayer().getInventory().addItem(new ItemStack[]{event.getCurrentItem().clone()});
         PlayerUtil.playSound(this.getPlayer(), Sound.ENTITY_ITEM_PICKUP);
      });
      this.addItem(1, new ItemStack(Material.SLIME_BALL), Message.getMessage((IUser)user, "coding.item.number"), (event) -> {
         this.getPlayer().getInventory().addItem(new ItemStack[]{event.getCurrentItem().clone()});
         PlayerUtil.playSound(this.getPlayer(), Sound.ENTITY_ITEM_PICKUP);
      });
      this.addItem(2, new ItemStack(Material.PAPER), Message.getMessage((IUser)user, "coding.item.location"), (event) -> {
         this.getPlayer().getInventory().addItem(new ItemStack[]{event.getCurrentItem().clone()});
         PlayerUtil.playSound(this.getPlayer(), Sound.ENTITY_ITEM_PICKUP);
      });
      this.addItem(3, new ItemStack(Material.GLASS_BOTTLE), Message.getMessage((IUser)user, "coding.item.potion"), (event) -> {
         this.getPlayer().getInventory().addItem(new ItemStack[]{event.getCurrentItem().clone()});
         PlayerUtil.playSound(this.getPlayer(), Sound.ENTITY_ITEM_PICKUP);
      });
      this.addItem(4, new ItemStack(Material.MAGMA_CREAM), Message.getMessage((IUser)user, "coding.item.dynamic_variables"), (event) -> {
         this.getPlayer().getInventory().addItem(new ItemStack[]{event.getCurrentItem().clone()});
         PlayerUtil.playSound(this.getPlayer(), Sound.ENTITY_ITEM_PICKUP);
      });
      this.addItem(5, new ItemStack(Material.APPLE), Message.getMessage((IUser)user, "coding.item.values"), (event) -> {
         this.getPlayer().getInventory().addItem(new ItemStack[]{event.getCurrentItem().clone()});
         PlayerUtil.playSound(this.getPlayer(), Sound.ENTITY_ITEM_PICKUP);
      });
      this.addItem(6, new ItemStack(Material.NETHER_STAR), Message.getMessage((IUser)user, "coding.item.particles"), (event) -> {
         this.getPlayer().getInventory().addItem(new ItemStack[]{event.getCurrentItem().clone()});
         PlayerUtil.playSound(this.getPlayer(), Sound.ENTITY_ITEM_PICKUP);
      });
      this.addItem(7, new ItemStack(Material.PRISMARINE_SHARD), Message.getMessage((IUser)user, "coding.item.vector"), (event) -> {
         this.getPlayer().getInventory().addItem(new ItemStack[]{event.getCurrentItem().clone()});
         PlayerUtil.playSound(this.getPlayer(), Sound.ENTITY_ITEM_PICKUP);
      });
   }
}
