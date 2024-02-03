package net.mineland.creative.modules.coding;

import net.mineland.creative.modules.coding.values.objects.LocationValue;
import net.mineland.creative.modules.coding.values.objects.NumberValue;
import org.apache.commons.lang.ArrayUtils;
import org.bukkit.inventory.ItemStack;
import ua.govnojon.libs.bukkitutil.ItemStackUtil;

public class ChestParser {
   private ItemStack[] originalItems;
   private ItemStack[] items;
   private ItemStack[] texts;
   private ItemStack[] numbers;
   private ItemStack[] locations;
   private ItemStack[] potions;
   private ItemStack[] eggs;
   private ItemStack[] particles;
   private ItemStack[] dynamicVariables;

   public ChestParser(ItemStack[] itemStacks) {
      this.originalItems = itemStacks;
      this.items = CodeUtils.sortNotNull(itemStacks);
      this.texts = new ItemStack[0];
      this.numbers = new ItemStack[0];
      this.locations = new ItemStack[0];
      this.potions = new ItemStack[0];
      this.eggs = new ItemStack[0];
      this.particles = new ItemStack[0];
      this.dynamicVariables = new ItemStack[0];
      this.sort();
   }

   private void sort() {
      for(int i = 0; i < this.items.length; ++i) {
         ItemStack itemStack = this.items[i];
         if (!ItemStackUtil.isNullOrAir(itemStack)) {
            switch(itemStack.getType()) {
            case BOOK:
               this.texts = (ItemStack[])((ItemStack[])ArrayUtils.add(this.texts, itemStack));
               break;
            case SLIME_BALL:
               this.numbers = (ItemStack[])((ItemStack[])ArrayUtils.add(this.numbers, itemStack));
               break;
            case PAPER:
               this.locations = (ItemStack[])((ItemStack[])ArrayUtils.add(this.locations, itemStack));
               break;
            case LINGERING_POTION:
            case SPLASH_POTION:
            case POTION:
               this.potions = (ItemStack[])((ItemStack[])ArrayUtils.add(this.potions, itemStack));
               break;
            case MONSTER_EGG:
               this.eggs = (ItemStack[])((ItemStack[])ArrayUtils.add(this.eggs, itemStack));
               break;
            case NETHER_STAR:
               this.particles = (ItemStack[])((ItemStack[])ArrayUtils.add(this.particles, itemStack));
               break;
            case APPLE:
               Object o = CodeUtils.parseItem(itemStack);
               if (o instanceof NumberValue) {
                  this.numbers = (ItemStack[])((ItemStack[])ArrayUtils.add(this.numbers, itemStack));
               }

               if (o instanceof LocationValue) {
                  this.locations = (ItemStack[])((ItemStack[])ArrayUtils.add(this.locations, itemStack));
               }
               break;
            case MAGMA_CREAM:
               this.dynamicVariables = (ItemStack[])((ItemStack[])ArrayUtils.add(this.dynamicVariables, itemStack));
               this.numbers = (ItemStack[])((ItemStack[])ArrayUtils.add(this.numbers, itemStack));
               this.texts = (ItemStack[])((ItemStack[])ArrayUtils.add(this.texts, itemStack));
               this.locations = (ItemStack[])((ItemStack[])ArrayUtils.add(this.locations, itemStack));
            }
         }
      }

   }

   public ItemStack getItem(int index) {
      try {
         return this.items[index];
      } catch (ArrayIndexOutOfBoundsException var3) {
         return null;
      }
   }

   public ItemStack[] getItems() {
      return this.items;
   }

   public ItemStack[] getOriginalItems() {
      return this.originalItems;
   }

   public ItemStack getText(int index) {
      try {
         return this.texts[index];
      } catch (ArrayIndexOutOfBoundsException var3) {
         return null;
      }
   }

   public ItemStack[] getTexts() {
      return this.texts;
   }

   public ItemStack getNumber(int index) {
      try {
         return this.numbers[index];
      } catch (ArrayIndexOutOfBoundsException var3) {
         return null;
      }
   }

   public ItemStack[] getNumbers() {
      return this.numbers;
   }

   public ItemStack getLocation(int index) {
      try {
         return this.locations[index];
      } catch (ArrayIndexOutOfBoundsException var3) {
         return null;
      }
   }

   public ItemStack[] getLocations() {
      return this.locations;
   }

   public ItemStack getPotion(int index) {
      try {
         return this.potions[index];
      } catch (ArrayIndexOutOfBoundsException var3) {
         return null;
      }
   }

   public ItemStack[] getPotions() {
      return this.potions;
   }

   public ItemStack getEgg(int index) {
      try {
         return this.eggs[index];
      } catch (ArrayIndexOutOfBoundsException var3) {
         return null;
      }
   }

   public ItemStack[] getEggs() {
      return this.eggs;
   }

   public ItemStack getParticle(int index) {
      try {
         return this.particles[index];
      } catch (ArrayIndexOutOfBoundsException var3) {
         return null;
      }
   }

   public ItemStack[] getParticles() {
      return this.particles;
   }

   public ItemStack getDynamicVariable(int index) {
      try {
         return this.dynamicVariables[index];
      } catch (ArrayIndexOutOfBoundsException var3) {
         return null;
      }
   }

   public ItemStack[] getDynamicVariables() {
      return this.dynamicVariables;
   }
}
