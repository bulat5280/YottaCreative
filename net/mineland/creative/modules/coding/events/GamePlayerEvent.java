package net.mineland.creative.modules.coding.events;

import net.mineland.core.bukkit.module.Module;
import net.mineland.core.bukkit.modules.myevents.UserEvent;
import net.mineland.core.bukkit.modules.user.User;
import net.mineland.creative.modules.coding.CodeHandler;
import net.mineland.creative.modules.creative.ModuleCreative;
import net.mineland.creative.modules.creative.PlayerMode;
import net.mineland.creative.modules.creative.Plot;
import org.bukkit.entity.Entity;
import org.bukkit.event.Event;

public class GamePlayerEvent extends UserEvent implements GameEvent, HandleEvent {
   private static ModuleCreative moduleCreative = (ModuleCreative)Module.getInstance(ModuleCreative.class);
   private Plot plot;
   private Event handleEvent;

   public GamePlayerEvent(User user, Plot plot, Event event) {
      super(user);
      this.plot = plot;
      this.handleEvent = event;
   }

   public Plot getPlot() {
      return this.plot;
   }

   public Entity getDefaultEntity() {
      return this.getPlayer();
   }

   public Event getHandleEvent() {
      return this.handleEvent;
   }

   public void handle() {
      CodeHandler codeHandler = this.plot.getCodeHandler();
      if (codeHandler != null && this.getPlayer().getWorld() == moduleCreative.getPlotWorld() && moduleCreative.getPlotManager().getCurrentPlot(this.getUser()).getPlayerMode(this.getUser()) == PlayerMode.PLAYING) {
         codeHandler.handle(this);
      }

   }
}
