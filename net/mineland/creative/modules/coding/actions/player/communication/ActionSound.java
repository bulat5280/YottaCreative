package net.mineland.creative.modules.coding.actions.player.communication;

import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;
import net.mineland.creative.modules.coding.ChestParser;
import net.mineland.creative.modules.coding.actions.Action;
import net.mineland.creative.modules.coding.actions.ActionType;
import net.mineland.creative.modules.coding.activators.Activator;
import net.mineland.creative.modules.coding.events.GameEvent;
import org.bukkit.Material;
import org.bukkit.SoundCategory;
import org.bukkit.entity.Entity;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;
import ua.govnojon.libs.bukkitutil.ItemData;

public class ActionSound extends Action {
   public ActionSound(Activator activator) {
      super(activator);
   }

   public boolean run(List<Entity> selectedEntities, GameEvent gameEvent, int stackCounter, AtomicInteger callCounter) {
      selectedEntities.forEach((entity) -> {
         if (entity.getType() == EntityType.PLAYER) {
            Player player = (Player)entity;
            String sound = this.getVarString("sound", gameEvent, entity);
            float volume = this.getVar("volume", 1.0F, gameEvent, entity);
            float pitch = this.getVar("pitch", 1.0F, gameEvent, entity);
            player.playSound(player.getLocation(), sound, SoundCategory.MASTER, volume, pitch);
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
      return new ItemData(Material.GREEN_RECORD);
   }

   public boolean parseChest(ChestParser chestParser) {
      this.putVar("sound", chestParser.getText(0));
      this.putVar("volume", chestParser.getNumber(0));
      this.putVar("pitch", chestParser.getNumber(1));
      return true;
   }
}
