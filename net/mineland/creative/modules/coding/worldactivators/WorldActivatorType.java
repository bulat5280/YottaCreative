package net.mineland.creative.modules.coding.worldactivators;

import java.lang.reflect.Constructor;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;
import net.mineland.core.bukkit.module.Module;
import net.mineland.core.bukkit.modules.user.User;
import net.mineland.creative.modules.coding.actions.Action;
import net.mineland.creative.modules.coding.actions.ActionIf;
import net.mineland.creative.modules.coding.actions.ActionSelect;
import net.mineland.creative.modules.coding.actions.ActionType;
import net.mineland.creative.modules.coding.events.EntityEvent;
import net.mineland.creative.modules.coding.events.GameEvent;
import net.mineland.creative.modules.coding.exceptions.ExitException;
import net.mineland.creative.modules.coding.exceptions.StackLimitExitException;
import net.mineland.creative.modules.coding.worldactivators.activators.WorldLoadActivator;
import net.mineland.creative.modules.creative.ModuleCreative;
import net.mineland.creative.modules.creative.Plot;
import net.mineland.creative.modules.creative.PlotMode;
import org.bukkit.entity.Entity;
import org.bukkit.event.Event;
import sun.misc.JavaLangAccess;
import sun.misc.SharedSecrets;
import ua.govnojon.libs.bukkitutil.ItemData;

public enum WorldActivatorType {
   WORLD_LOAD(WorldLoadActivator.class, WorldLoadActivator.class);

   private Class<? extends WorldActivator> activatorClass;
   private Class<? extends Event> eventClass;
   private WorldActivator activator;
   protected static ModuleCreative moduleCreative = (ModuleCreative)Module.getInstance(ModuleCreative.class);

   public Class<? extends Event> getEventClass() {
      return this.eventClass;
   }

   public static boolean executeActions(WorldActivator activator, GameEvent gameEvent, List<Action> actionList, int stackCounter, AtomicInteger callCounter) {
      return executeActions(activator, gameEvent, actionList, 0, stackCounter, callCounter);
   }

   public static boolean executeActions(WorldActivator activator, GameEvent gameEvent, List<Action> actionList, int fromIndex, int stackCounter, AtomicInteger callCounter) {
      if (gameEvent.getPlot().getPlotMode().equals(PlotMode.PLAYING) && !gameEvent.getPlot().stoppedCode) {
         checkGameStackLimit(activator.getPlot(), stackCounter);
         ++stackCounter;

         try {
            checkJavaStackOverFlow(activator.getPlot());
         } catch (StackLimitExitException var17) {
            throw var17;
         } catch (Exception var18) {
         }

         List<Entity> selectedEntities = activator.getSelectedEntities();

         for(int i = fromIndex; i < actionList.size(); ++i) {
            Action action = (Action)actionList.get(i);
            switch(action.getType()) {
            case GAME_WAIT:
               int duration = action.getVar("duration", 1, gameEvent, (Entity)selectedEntities.get(0));
               int nextIndex = i + 1;
               gameEvent.getPlot().getCodeHandler().schedule(() -> {
                  try {
                     executeActions(activator, gameEvent, actionList, nextIndex, stackCounter, callCounter);
                  } catch (ExitException var7) {
                     gameEvent.getPlot().onExitException(var7);
                  }

               }).later((long)duration);
               return false;
            case ELSE:
               break;
            case CALL_FUNCTION:
               action.doRun(selectedEntities, gameEvent, stackCounter, callCounter);
               break;
            default:
               List entities;
               if (action instanceof ActionSelect) {
                  ActionSelect actionSelect = (ActionSelect)action;
                  entities = actionSelect.execute(gameEvent);
                  activator.setSelectedEntities(entities);
                  selectedEntities = entities;
               } else if (action instanceof ActionIf) {
                  if (action.getActivator() != null) {
                     WorldActivatorType activatorType = action.getWorldActivator().getType();
                     boolean bool;
                     if (activatorType.name().startsWith("PLAYER_") && gameEvent instanceof EntityEvent && action.getCategory() == Action.Category.IF_ENTITY) {
                        entities = Collections.singletonList(((EntityEvent)gameEvent).getEntity());
                        if (((ActionIf)action).condition(entities, gameEvent)) {
                           bool = action.execute(selectedEntities, gameEvent, stackCounter, callCounter);
                           if (!bool) {
                              return false;
                           }
                        } else if (actionList.size() > i + 1) {
                           Action elseAction = (Action)actionList.get(i + 1);
                           if (elseAction.getType() == ActionType.ELSE) {
                              boolean bool = elseAction.execute(selectedEntities, gameEvent, stackCounter, callCounter);
                              if (!bool) {
                                 return false;
                              }
                           }
                        }
                     } else if (((ActionIf)action).condition(selectedEntities, gameEvent)) {
                        boolean bool = action.execute(selectedEntities, gameEvent, stackCounter, callCounter);
                        if (!bool) {
                           return false;
                        }
                     } else if (actionList.size() > i + 1) {
                        Action elseAction = (Action)actionList.get(i + 1);
                        if (elseAction.getType() == ActionType.ELSE) {
                           bool = elseAction.execute(selectedEntities, gameEvent, stackCounter, callCounter);
                           if (!bool) {
                              return false;
                           }
                        }
                     }
                  }
               } else {
                  boolean execute = action.execute(selectedEntities, gameEvent, stackCounter, callCounter);
                  if (!execute) {
                     return false;
                  }
               }
            }
         }

         return true;
      } else {
         return false;
      }
   }

   private static void checkJavaStackOverFlow(Plot plot) throws StackLimitExitException {
      JavaLangAccess javaLangAccess = SharedSecrets.getJavaLangAccess();
      int depth = javaLangAccess.getStackTraceDepth(new Throwable());
      if (depth > 100) {
         User owner = plot.getOwner();
         if (owner != null) {
            owner.sendMessage("креатив.лимит.java.стака", "{size}", String.valueOf(depth), "{limit}", String.valueOf(100));
         }

         moduleCreative.getLogger().warning("" + plot.getId() + ", java " + depth + "/" + 'd');
         throw new StackLimitExitException();
      }
   }

   private static void checkGameStackLimit(Plot plot, int stackCounter) throws StackLimitExitException {
      User owner = plot.getOwner();
      int limit = moduleCreative.getStackLimit(owner);
      if (stackCounter >= limit) {
         if (owner != null) {
            owner.sendMessage("креатив.лимит.стака", "{size}", String.valueOf(stackCounter), "{limit}", String.valueOf(limit));
         }

         moduleCreative.getLogger().warning("" + plot.getId() + ", " + stackCounter + "/" + limit);
         throw new StackLimitExitException();
      }
   }

   public Class<? extends WorldActivator> getActivatorClass() {
      return this.activatorClass;
   }

   public ItemData getIcon() {
      return this.activator.getIcon();
   }

   private WorldActivatorType(Class<? extends WorldActivator> activatorClass, Class<? extends Event> eventClass) {
      this.activatorClass = activatorClass;
      this.eventClass = eventClass;
      this.activator = this.create((Plot)null);
   }

   public WorldActivator create(Plot plot) {
      WorldActivator activator = null;

      try {
         Constructor<? extends WorldActivator> constructor = this.activatorClass.getConstructor(Plot.class);
         activator = (WorldActivator)constructor.newInstance(plot);
      } catch (Exception var4) {
         var4.printStackTrace();
      }

      return activator;
   }
}
