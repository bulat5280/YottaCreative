package net.mineland.creative.modules.coding.actions.player.communication;

import java.util.Arrays;
import java.util.List;
import java.util.Objects;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.Collectors;
import net.mineland.creative.modules.coding.actions.Action;
import net.mineland.creative.modules.coding.actions.ActionType;
import net.mineland.creative.modules.coding.activators.Activator;
import net.mineland.creative.modules.coding.events.GameEvent;
import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.entity.Entity;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import ua.govnojon.libs.bukkitutil.ItemData;
import ua.govnojon.libs.bukkitutil.ItemStackUtil;

public class ActionStopSound extends Action {
   public ActionStopSound(Activator activator) {
      super(activator);
   }

   public boolean run(List<Entity> selectedEntities, GameEvent gameEvent, int stackCounter, AtomicInteger callCounter) {
      selectedEntities.forEach((entity) -> {
         if (entity.getType() == EntityType.PLAYER) {
            Player player = (Player)entity;
            String[] strings = new String[36];

            for(int i = 0; i < 36; ++i) {
               strings[i] = this.getVarString("sound_" + i, gameEvent, entity);
            }

            List<String> sounds = (List)Arrays.stream(strings).filter(Objects::nonNull).collect(Collectors.toList());
            if (sounds.size() > 0) {
               sounds.forEach(player::stopSound);
            } else {
               Sound[] var6 = Sound.values();
               int var7 = var6.length;

               for(int var8 = 0; var8 < var7; ++var8) {
                  Sound sound = var6[var8];
                  player.stopSound(sound);
               }
            }
         }

      });
      return true;
   }

   public ActionType getType() {
      return ActionType.PLAYER_SOUND;
   }

   public Action.Category getCategory() {
      return Action.Category.PLAYER_COMMUNICATION;
   }

   public ItemData getIcon() {
      return new ItemData(Material.GOLD_RECORD);
   }

   public boolean parseChest(ItemStack[] itemStacks) {
      List<ItemStack> list = (List)Arrays.stream(itemStacks).filter((itemStack) -> {
         return !ItemStackUtil.isNullOrAir(itemStack);
      }).collect(Collectors.toList());

      for(int i = 0; i < list.size(); ++i) {
         this.putVar("sound_" + i, (ItemStack)list.get(i));
      }

      return true;
   }
}
