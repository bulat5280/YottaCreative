package net.mineland.creative.commands;

import net.mineland.core.bukkit.module.Module;
import net.mineland.core.bukkit.modules.command.Command;
import net.mineland.core.bukkit.modules.command.CommandEvent;
import net.mineland.creative.modules.creative.ModuleCreative;
import net.mineland.creative.modules.creative.PlayerMode;
import net.mineland.creative.modules.creative.Plot;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

public class CommandText extends Command {
   public CommandText() {
      super("text", (String)null);
   }

   private ItemStack edited(String message) {
      ItemStack itemStack = new ItemStack(Material.BOOK);
      ItemMeta itemMeta = itemStack.getItemMeta();
      itemMeta.setDisplayName(message.replace("&", "§"));
      itemStack.setItemMeta(itemMeta);
      return itemStack;
   }

   public boolean execute(CommandEvent event) {
      ModuleCreative moduleCreative = (ModuleCreative)Module.getInstance(ModuleCreative.class);
      Plot plot = moduleCreative.getPlotManager().getCurrentPlot(event.getUser());
      String[] args = event.getArgs();
      if (plot == null) {
         event.sendMessage("creative.not_in_world");
         return false;
      } else if (plot.getPlayerMode(event.getUser()) != PlayerMode.CODING) {
         event.sendMessage("creative.not_in_coding");
         return false;
      } else {
         String message = event.getArguments(0);
         event.checkSizeArguments(1);
         event.getPlayer().getInventory().addItem(new ItemStack[]{this.edited(message.replace("&", "§"))});
         return false;
      }
   }
}
