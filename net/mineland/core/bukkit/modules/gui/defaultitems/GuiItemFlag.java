package net.mineland.core.bukkit.modules.gui.defaultitems;

import com.google.common.collect.Lists;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;
import net.mineland.api.user.IUser;
import net.mineland.core.bukkit.modules.gui.GuiItem;
import net.mineland.core.bukkit.modules.gui.GuiMenu;
import net.mineland.core.bukkit.modules.message.Message;
import net.mineland.core.bukkit.modules.user.Lang;
import net.mineland.core.bukkit.modules.user.User;
import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.ItemStack;

public abstract class GuiItemFlag<V extends GuiItemFlag.Flag> extends GuiItem {
   private String firstLine;
   private List<V> values;
   private V current;

   public GuiItemFlag(GuiMenu guiMenu, int x, int y, Material material) {
      super(guiMenu, x, y, material);
   }

   public GuiItemFlag(GuiMenu guiMenu, int x, int y, ItemStack itemStack) {
      super(guiMenu, x, y, itemStack);
   }

   public GuiItemFlag(GuiMenu guiMenu, int x, int y, Material material, short data, int amount) {
      super(guiMenu, x, y, material, data, amount);
   }

   public void click(InventoryClickEvent event) {
      if (this.current != null) {
         V previously = this.current;
         int index = this.values.indexOf(this.current);
         if (index != -1) {
            GuiItemFlag.Flag v;
            if (!event.getClick().isRightClick()) {
               if (!event.getClick().isLeftClick()) {
                  return;
               }

               do {
                  ++index;
                  GuiItemFlag.Flag var10000;
                  if (index >= this.values.size()) {
                     index = 0;
                     var10000 = (GuiItemFlag.Flag)this.values.get(0);
                  } else {
                     var10000 = (GuiItemFlag.Flag)this.values.get(index);
                  }

                  v = var10000;
               } while(!v.equals(this.current) && !v.accept(this.getUser()));

               this.setValue(v);
            } else {
               do {
                  --index;
                  v = index < 0 ? (GuiItemFlag.Flag)this.values.get(index = this.values.size() - 1) : (GuiItemFlag.Flag)this.values.get(index);
               } while(!v.equals(this.current) && !v.accept(this.getUser()));

               this.setValue(v);
            }

            if (!Objects.equals(previously, this.current)) {
               this.getPlayer().playSound(this.getPlayer().getLocation(), Sound.BLOCK_WOOD_PRESSUREPLATE_CLICK_ON, 1.0F, 1.0F);
               this.updateStage();
            }

         }
      }
   }

   public GuiItemFlag setValues(String first, V current, List<V> values) {
      this.firstLine = first;
      this.values = Lists.newArrayList(values);
      this.setValue(current);
      return this;
   }

   public void setValue(V value) {
      if (!Objects.equals(this.current, value)) {
         this.current = value;
         Lang lang = this.getUser().getLang();
         Message message = new Message("предмет.флаг.формат", new String[]{"{first}", this.firstLine});
         if (this.values.size() > 2) {
            message.replace("{hot.keys}", new Message("предмет.hotkeys", new String[0]));
         } else {
            message.replace("{hot.keys}", "");
         }

         message.replace("{variables}", (String)this.values.stream().map((v) -> {
            return (v.accept(this.getUser()) ? (v.equals(value) ? "§4 ● " : "§7 ○ ") : "§8 ◌ ") + v.toDisplay(this.getUser());
         }).collect(Collectors.joining("\n")));
         this.setText(message.translate(lang));
      }
   }

   public List<V> getValues() {
      return this.values;
   }

   public V getCurrent() {
      return this.current;
   }

   public abstract void updateStage();

   public interface Flag {
      String toDisplay(User var1);

      default boolean accept(User user) {
         return true;
      }
   }

   public static enum Value implements GuiItemFlag.Flag {
      ENABLE("включено"),
      ENABLE_ALL("включено_для_всех"),
      DISABLE("отключено"),
      MEMBER("влючено_для_жителей"),
      SMALL("маленький"),
      BIG("большой"),
      NOT_FOUND("не_выбрано");

      private String key;

      private Value(String key) {
         this.key = key;
      }

      public String getKey() {
         return this.key;
      }

      public String toDisplay(User user) {
         return Message.getMessage((IUser)user, this.key);
      }
   }
}
