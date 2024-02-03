package net.mineland.creative.modules.coding.activators;

import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.Collectors;
import net.mineland.core.bukkit.modules.user.User;
import net.mineland.creative.modules.coding.events.GameEvent;
import net.mineland.creative.modules.creative.PlayerMode;
import net.mineland.creative.modules.creative.Plot;
import org.bukkit.Material;
import org.bukkit.entity.Entity;
import ua.govnojon.libs.bukkitutil.ItemData;

public class GameLoopActivator extends NamedActivator {
   private int ticks = 20;
   private int currentTicks;
   private GameEvent gameEvent;

   public GameLoopActivator(Plot plot) {
      super(plot);
      this.currentTicks = this.ticks;
   }

   public ActivatorType getType() {
      return ActivatorType.GAME_LOOP;
   }

   public ItemData getIcon() {
      return new ItemData(Material.EMERALD_BLOCK);
   }

   public int getTicks() {
      return this.ticks;
   }

   public void setTicks(int ticks) {
      this.ticks = ticks;
   }

   public int getCurrentTicks() {
      return this.currentTicks;
   }

   public void setCurrentTicks(int currentTicks) {
      this.currentTicks = currentTicks;
   }

   public void setGameEvent(GameEvent gameEvent) {
      this.gameEvent = gameEvent;
   }

   public GameEvent getGameEvent() {
      return this.gameEvent;
   }

   public void execute(GameEvent gameEvent, int stackCounter, AtomicInteger callCounter) {
      List<Entity> players = (List)gameEvent.getPlot().getOnlinePlayers().stream().filter((user) -> {
         return gameEvent.getPlot().getPlayerMode(user) == PlayerMode.PLAYING;
      }).map(User::getPlayer).filter((player) -> {
         return player.getWorld() == moduleCreative.getPlotWorld();
      }).collect(Collectors.toList());
      this.execute(gameEvent, players, stackCounter, callCounter);
   }
}
