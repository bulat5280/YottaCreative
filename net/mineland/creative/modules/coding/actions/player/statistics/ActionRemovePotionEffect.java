package net.mineland.creative.modules.coding.actions.player.statistics;

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

public class ActionRemovePotionEffect extends Action {
   public ActionRemovePotionEffect(Activator activator) {
      super(activator);
   }

   public boolean run(List<Entity> selectedEntities, GameEvent gameEvent, int stackCounter, AtomicInteger callCounter) {
      selectedEntities.forEach((entity) -> {
         Player player = (Player)entity;
         ItemStack potion = (ItemStack)this.getVar("potion", gameEvent, entity);
         if (!ItemStackUtil.isNullOrAir(potion)) {
            PotionMeta potionMeta = (PotionMeta)potion.getItemMeta();
            PotionEffectType potionEffectType = potionMeta.getBasePotionData().getType().getEffectType();
            if (potionEffectType == null) {
               potionEffectType = ((PotionEffect)potionMeta.getCustomEffects().get(0)).getType();
            }

            player.removePotionEffect(potionEffectType);
         }
      });
      return true;
   }

   public ActionType getType() {
      return ActionType.PLAYER_REMOVE_POTION_EFFECT;
   }

   public Action.Category getCategory() {
      return Action.Category.PLAYER_PARAMETERS;
   }

   public ItemData getIcon() {
      return new ItemData(Material.CAULDRON_ITEM);
   }

   public boolean parseChest(ChestParser chestParser) {
      this.putVar("potion", chestParser.getPotion(0), false);
      return true;
   }
}
