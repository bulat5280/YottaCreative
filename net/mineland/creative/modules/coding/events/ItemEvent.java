package net.mineland.creative.modules.coding.events;

import org.bukkit.inventory.ItemStack;

public interface ItemEvent {
   ItemStack getItem();

   void setItem(ItemStack var1);
}
