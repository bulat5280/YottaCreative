package net.mineland.creative.modules.coding.actions.game.entityspawning;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;
import net.mineland.creative.modules.coding.ChestParser;
import net.mineland.creative.modules.coding.actions.Action;
import net.mineland.creative.modules.coding.actions.ActionType;
import net.mineland.creative.modules.coding.activators.Activator;
import net.mineland.creative.modules.coding.events.GameEvent;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.attribute.Attribute;
import org.bukkit.attribute.AttributeInstance;
import org.bukkit.entity.Entity;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.LivingEntity;
import org.bukkit.inventory.ItemStack;
import org.bukkit.potion.PotionEffect;
import ua.govnojon.libs.bukkitutil.ItemData;

public class ActionSpawnMob extends Action {
   private static ArrayList<EntityType> forbidden = new ArrayList<EntityType>() {
      {
         this.add(EntityType.ENDER_DRAGON);
      }
   };

   public ActionSpawnMob(Activator activator) {
      super(activator);
   }

   public ActionType getType() {
      return ActionType.GAME_SPAWN_MOB;
   }

   public Action.Category getCategory() {
      return Action.Category.GAME_ENTITY_SPAWNING;
   }

   public ItemData getIcon() {
      return new ItemData(Material.MONSTER_EGG);
   }

   public boolean run(List<Entity> selectedEntities, GameEvent gameEvent, int stackCounter, AtomicInteger callCounter) {
      selectedEntities.forEach((selectedEntity) -> {
         EntityType mob = (EntityType)this.getVar("mob", gameEvent, selectedEntity);
         if (mob != null) {
            if (!forbidden.contains(mob)) {
               List<Location> locations = this.getVarsLocations("locations", gameEvent, selectedEntity);
               if (!locations.isEmpty()) {
                  int health = this.getVar("health", 0, gameEvent, selectedEntity);
                  String name = this.getVarString("name", gameEvent, selectedEntity);
                  List<PotionEffect> potions = this.getVars("potions", gameEvent, selectedEntity);
                  ItemStack mainHand = (ItemStack)this.getVar("main_hand", gameEvent, selectedEntity);
                  ItemStack helmet = (ItemStack)this.getVar("helmet", gameEvent, selectedEntity);
                  ItemStack chestplate = (ItemStack)this.getVar("chestplate", gameEvent, selectedEntity);
                  ItemStack leggings = (ItemStack)this.getVar("leggings", gameEvent, selectedEntity);
                  ItemStack boots = (ItemStack)this.getVar("boots", gameEvent, selectedEntity);
                  ItemStack offHand = (ItemStack)this.getVar("off_hand", gameEvent, selectedEntity);
                  locations.forEach((location) -> {
                     Entity spawnEntity = location.getWorld().spawnEntity(location, mob);
                     if (name != null && !name.isEmpty()) {
                        spawnEntity.setCustomNameVisible(true);
                        spawnEntity.setCustomName(name);
                     }

                     if (spawnEntity instanceof LivingEntity) {
                        LivingEntity livingEntity = (LivingEntity)spawnEntity;
                        if (health > 0) {
                           AttributeInstance attribute = livingEntity.getAttribute(Attribute.GENERIC_MAX_HEALTH);
                           if ((double)health > attribute.getValue()) {
                              attribute.setBaseValue((double)health);
                           }

                           livingEntity.setHealth((double)health);
                        }

                        potions.forEach((potionEffect) -> {
                           potionEffect.apply(livingEntity);
                        });
                        livingEntity.getEquipment().setItemInMainHand(mainHand);
                        livingEntity.getEquipment().setHelmet(helmet);
                        livingEntity.getEquipment().setChestplate(chestplate);
                        livingEntity.getEquipment().setLeggings(leggings);
                        livingEntity.getEquipment().setBoots(boots);
                        livingEntity.getEquipment().setItemInOffHand(offHand);
                     }

                  });
               }
            }
         }
      });
      return true;
   }

   public boolean parseChest(ChestParser chestParser) {
      this.putVar("mob", chestParser.getEgg(0));
      this.putVar("locations", chestParser.getLocations());
      this.putVar("health", chestParser.getNumber(0));
      this.putVar("name", chestParser.getText(0));
      this.putVar("potions", chestParser.getPotions());
      this.putVar("main_hand", chestParser.getOriginalItems()[18], false);
      this.putVar("helmet", chestParser.getOriginalItems()[19], false);
      this.putVar("chestplate", chestParser.getOriginalItems()[20], false);
      this.putVar("leggings", chestParser.getOriginalItems()[21], false);
      this.putVar("boots", chestParser.getOriginalItems()[22], false);
      this.putVar("off_hand", chestParser.getOriginalItems()[23], false);
      return true;
   }
}
