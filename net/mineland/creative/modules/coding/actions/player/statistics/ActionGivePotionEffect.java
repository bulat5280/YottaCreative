package net.mineland.creative.modules.coding.actions.player.statistics;

import java.util.Iterator;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;
import net.mineland.creative.modules.coding.ChestParser;
import net.mineland.creative.modules.coding.actions.Action;
import net.mineland.creative.modules.coding.actions.ActionType;
import net.mineland.creative.modules.coding.activators.Activator;
import net.mineland.creative.modules.coding.events.GameEvent;
import org.bukkit.Material;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.PotionMeta;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;
import ua.govnojon.libs.bukkitutil.ItemData;
import ua.govnojon.libs.bukkitutil.ItemStackUtil;

public class ActionGivePotionEffect extends Action {
   private static final String POTION_PREFIX = "potion:";

   public ActionGivePotionEffect(Activator activator) {
      super(activator);
   }

   public boolean run(List<Entity> selectedEntities, GameEvent gameEvent, int stackCounter, AtomicInteger callCounter) {
      Iterator var5 = selectedEntities.iterator();

      while(var5.hasNext()) {
         Entity entity = (Entity)var5.next();
         Player player = (Player)entity;
         ItemStack potion = (ItemStack)this.getVar("potion", gameEvent, entity);
         if (!ItemStackUtil.isNullOrAir(potion)) {
            int duration = this.getVar("duration", 200, gameEvent, entity);
            int amplifier = this.getVar("amplifier", 0, gameEvent, entity);
            if (amplifier > 94) {
               amplifier = 94;
            } else if (amplifier < -100) {
               amplifier = -100;
            }

            PotionMeta potionMeta = (PotionMeta)potion.getItemMeta();
            PotionEffectType potionEffectType = potionMeta.getBasePotionData().getType().getEffectType();
            if (potionEffectType == null) {
               if (!potionMeta.getCustomEffects().isEmpty()) {
                  potionEffectType = ((PotionEffect)potionMeta.getCustomEffects().get(0)).getType();
               } else {
                  String displayName = potionMeta.getDisplayName();
                  if (displayName.startsWith("potion:")) {
                     String rawPotionType = displayName.substring("potion:".length());
                     potionEffectType = PotionEffectType.getByName(rawPotionType);
                  }
               }
            }

            if (potionEffectType != null) {
               PotionEffect potionEffect = new PotionEffect(potionEffectType, duration, amplifier);
               player.addPotionEffect(potionEffect, true);
            } else {
               moduleCreative.getLogger().severe("Ошибка выдачи зелья player:" + entity.getName() + ", \npotion:" + potion + ", \nduration:" + duration + ", \namplifier:" + amplifier + ", \npotionMeta:" + potionMeta + ", \npotionMeta.getCustomEffects():" + potionMeta.getCustomEffects());
               Thread.dumpStack();
            }
         }
      }

      return true;
   }

   public ActionType getType() {
      return ActionType.PLAYER_GIVE_POTION_EFFECT;
   }

   public Action.Category getCategory() {
      return Action.Category.PLAYER_PARAMETERS;
   }

   public ItemData getIcon() {
      return new ItemData(Material.BREWING_STAND_ITEM);
   }

   public boolean parseChest(ChestParser chestParser) {
      this.putVar("potion", chestParser.getPotion(0), false);
      this.putVar("duration", chestParser.getNumber(0));
      this.putVar("amplifier", chestParser.getNumber(1));
      return true;
   }
}
