package net.mineland.creative.modules.coding;

import com.google.common.collect.Lists;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Set;
import java.util.Map.Entry;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.function.Consumer;
import java.util.stream.Collectors;
import java.util.stream.Stream;
import net.mineland.core.bukkit.module.Module;
import net.mineland.core.bukkit.modules.user.User;
import net.mineland.creative.modules.coding.activators.Activator;
import net.mineland.creative.modules.coding.activators.FunctionActivator;
import net.mineland.creative.modules.coding.activators.GameLoopActivator;
import net.mineland.creative.modules.coding.activators.NamedActivator;
import net.mineland.creative.modules.coding.dynamicvariables.DynamicVariable;
import net.mineland.creative.modules.coding.events.GameEvent;
import net.mineland.creative.modules.coding.exceptions.ExitException;
import net.mineland.creative.modules.coding.exceptions.TimerLimitExitException;
import net.mineland.creative.modules.coding.exceptions.TimerPeriodLimitExitException;
import net.mineland.creative.modules.creative.ModuleCreative;
import net.mineland.creative.modules.creative.Plot;
import org.bukkit.Location;
import org.bukkit.entity.Entity;
import ua.govnojon.libs.bukkitutil.schedule.Schedule;
import ua.govnojon.libs.config.Config;
import ua.govnojon.libs.myjava.Try;

public class CodeHandler {
   private static ModuleCreative moduleCreative = (ModuleCreative)Module.getInstance(ModuleCreative.class);
   private List<Activator> activators;
   private Set<Schedule> schedulers;
   private List<PlotScoreboard> plotScoreboards;
   private HashMap<String, GameLoopActivator> gameLoops;
   private HashMap<String, DynamicVariable> dynamicVariables;
   private Plot plot;
   private int blocksLimit = 5000;
   private Entity lastSpawnedEntity;

   public CodeHandler(List<Activator> activatorList, Plot plot) {
      this.activators = activatorList;
      this.plotScoreboards = Lists.newArrayList();
      this.schedulers = new HashSet();
      this.gameLoops = new HashMap();
      this.dynamicVariables = new HashMap();
      this.plot = plot;
      User owner = plot.getOwner();
      if (owner != null) {
         this.lastSpawnedEntity = owner.getPlayer();
      }

      Try.ignore(() -> {
         this.schedule(() -> {
            this.addBlocksLimit(5000);
         }).timer(1L, 1L, TimeUnit.SECONDS);
      });
      Config config = new Config(plot.getVariables());
      config.getKeys(false).forEach((name) -> {
         String type = config.getString(name + ".type", "text");
         byte var5 = -1;
         switch(type.hashCode()) {
         case 107328:
            if (type.equals("loc")) {
               var5 = 2;
            }
            break;
         case 109446:
            if (type.equals("num")) {
               var5 = 0;
            }
            break;
         case 3556653:
            if (type.equals("text")) {
               var5 = 1;
            }
         }

         switch(var5) {
         case 0:
            this.dynamicVariables.put(name, new DynamicVariable(name, config.getDouble(name + ".value"), true));
            break;
         case 1:
            this.dynamicVariables.put(name, new DynamicVariable(name, config.getString(name + ".value"), true));
            break;
         case 2:
            this.dynamicVariables.put(name, new DynamicVariable(name, config.getLocation(name + ".value"), true));
         }

      });
   }

   public void handle(GameEvent gameEvent) {
      Iterator var2 = this.activators.iterator();

      while(var2.hasNext()) {
         Activator activator = (Activator)var2.next();
         if (activator.getType().getEventClass() == gameEvent.getClass()) {
            try {
               activator.execute(gameEvent, 0, new AtomicInteger());
            } catch (ExitException var5) {
               this.plot.onExitException(var5);
            }
         }
      }

   }

   public NamedActivator getNamedActivator(String name) {
      Stream var10000 = this.activators.stream().filter((activator) -> {
         return activator instanceof NamedActivator;
      });
      NamedActivator.class.getClass();
      return (NamedActivator)var10000.map(NamedActivator.class::cast).filter((activator) -> {
         return name.equals(activator.getCustomName());
      }).findAny().orElse((Object)null);
   }

   public List<Activator> getActivators() {
      return this.activators;
   }

   public void setActivators(List<Activator> activators) {
      this.activators = activators;
   }

   public HashMap<String, DynamicVariable> getDynamicVariables() {
      return this.dynamicVariables;
   }

   public List<FunctionActivator> getFunctions() {
      Stream var10000 = this.activators.stream().filter((activator) -> {
         return activator instanceof FunctionActivator;
      });
      FunctionActivator.class.getClass();
      return (List)var10000.map(FunctionActivator.class::cast).collect(Collectors.toList());
   }

   public void saveVariables() {
      Config config = new Config("");
      Iterator var2 = this.dynamicVariables.entrySet().iterator();

      while(var2.hasNext()) {
         Entry<String, DynamicVariable> entry = (Entry)var2.next();
         String name = (String)entry.getKey();
         DynamicVariable dynamicVariable = (DynamicVariable)entry.getValue();
         if (dynamicVariable.isSave()) {
            Object value = dynamicVariable.getValue(this.getPlot());
            if (value instanceof Number) {
               config.set(name + ".type", "num");
            }

            if (value instanceof String) {
               config.set(name + ".type", "text");
            }

            if (value instanceof Location) {
               config.set(name + ".type", "loc");
            }

            config.set(name + ".value", value);
         }
      }

      this.getPlot().setVariables(config.saveToString());
   }

   public void addScoreboard(PlotScoreboard plotScoreboard) {
      this.plotScoreboards.add(plotScoreboard);
   }

   public void removeScoreboard(PlotScoreboard plotScoreboard) {
      Iterator var2 = (new ArrayList(plotScoreboard.getUsers())).iterator();

      while(var2.hasNext()) {
         User user = (User)var2.next();
         plotScoreboard.hide(user);
      }

      this.plotScoreboards.remove(plotScoreboard);
   }

   public PlotScoreboard getScoreboard(String name) {
      if (name == null) {
         return null;
      } else {
         Iterator var2 = this.plotScoreboards.iterator();

         PlotScoreboard plotScoreboard;
         do {
            if (!var2.hasNext()) {
               return null;
            }

            plotScoreboard = (PlotScoreboard)var2.next();
         } while(!plotScoreboard.getName().equals(name));

         return plotScoreboard;
      }
   }

   public Entity getLastSpawnedEntity() {
      return this.lastSpawnedEntity;
   }

   public void setLastSpawnedEntity(Entity lastSpawnedEntity) {
      this.lastSpawnedEntity = lastSpawnedEntity;
   }

   public Plot getPlot() {
      return this.plot;
   }

   public Schedule schedule(Runnable runnable) {
      return this.schedule((s) -> {
         runnable.run();
      });
   }

   public Schedule schedule(Consumer<Schedule> consumer) {
      this.checkSchedulerLimit();
      Schedule schedule = new Schedule(consumer) {
         public void cancel() {
            super.cancel();
            CodeHandler.this.schedulers.remove(this);
         }

         public Schedule later(long delay) {
            CodeHandler.this.checkSchedulerPeriodLimit(this, delay);
            return super.later(delay);
         }

         public Schedule later(long delay, TimeUnit time) {
            CodeHandler.this.checkSchedulerPeriodLimit(this, Schedule.toTicks(delay, time));
            return super.later(delay, time);
         }

         public Schedule timer(long delay, long period) {
            CodeHandler.this.checkSchedulerPeriodLimit(this, delay);
            CodeHandler.this.checkSchedulerPeriodLimit(this, period);
            return super.timer(delay, period);
         }

         public Schedule timer(long delay, long period, TimeUnit time) {
            CodeHandler.this.checkSchedulerPeriodLimit(this, Schedule.toTicks(delay, time));
            CodeHandler.this.checkSchedulerPeriodLimit(this, Schedule.toTicks(period, time));
            return super.timer(delay, period, time);
         }
      };
      this.schedulers.add(schedule);
      return schedule;
   }

   private void checkSchedulerPeriodLimit(Schedule schedule, long ticks) {
      if (ticks > 36000L) {
         this.schedulers.remove(schedule);
         User owner = this.plot.getOwner();
         if (owner != null) {
            moduleCreative.getLogger().warning("Прерываем код плота " + this.plot.getId() + ", период таймера лимит " + ticks + "/" + '負');
            throw new TimerPeriodLimitExitException();
         }
      }

   }

   private void checkSchedulerLimit() {
      if (this.schedulers.size() >= 100) {
         if (this.plot.getOwner() != null) {
            this.plot.getOwner().sendMessage("креатив.лимит.по.числу.таймеров", "{size}", String.valueOf(this.schedulers.size()), "{limit}", String.valueOf(100));
         }

         moduleCreative.getLogger().warning("Прерываем код плота " + this.plot.getId() + ", кол-во таймеров лимит " + this.schedulers.size() + "/" + 100);
         throw new TimerLimitExitException();
      }
   }

   public void cancelSchedulers() {
      Iterator var1 = (new HashSet(this.schedulers)).iterator();

      while(var1.hasNext()) {
         Schedule schedule = (Schedule)var1.next();
         schedule.cancel();
      }

      this.gameLoops.clear();
      this.saveVariables();
      this.dynamicVariables.entrySet().removeIf((stringDynamicVariableEntry) -> {
         return !((DynamicVariable)stringDynamicVariableEntry.getValue()).isSave();
      });
   }

   public void startGameLoop(String name, GameEvent gameEvent) {
      if (!this.gameLoops.containsKey(name)) {
         NamedActivator gameLoop = this.getNamedActivator(name);
         if (gameLoop instanceof GameLoopActivator) {
            GameLoopActivator gameLoopActivator = (GameLoopActivator)gameLoop;
            gameLoopActivator.setGameEvent(gameEvent);
            this.gameLoops.put(name, gameLoopActivator);
         }
      }

   }

   public void stopGameLoop(String name) {
      this.gameLoops.remove(name);
   }

   public HashMap<String, GameLoopActivator> getGameLoops() {
      return this.gameLoops;
   }

   public int getBlocksLimit() {
      return this.blocksLimit;
   }

   public void addBlocksLimit(int count) {
      if (this.blocksLimit < 50000) {
         this.blocksLimit += count;
      } else {
         this.blocksLimit = 50000;
      }

   }

   public boolean removeBlocksLimit(int count) {
      if (this.blocksLimit - count > 0) {
         this.blocksLimit -= count;
         return true;
      } else {
         return false;
      }
   }

   public Set<Schedule> getSchedulers() {
      return this.schedulers;
   }
}
