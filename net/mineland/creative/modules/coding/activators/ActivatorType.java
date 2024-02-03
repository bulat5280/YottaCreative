package net.mineland.creative.modules.coding.activators;

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
import net.mineland.creative.modules.coding.activators.player.PlayerBlockBreakActivator;
import net.mineland.creative.modules.coding.activators.player.PlayerBlockPlaceActivator;
import net.mineland.creative.modules.coding.activators.player.PlayerChangeSlotActivator;
import net.mineland.creative.modules.coding.activators.player.PlayerChatActivator;
import net.mineland.creative.modules.coding.activators.player.PlayerConsumeItemActivator;
import net.mineland.creative.modules.coding.activators.player.PlayerDamageMobActivator;
import net.mineland.creative.modules.coding.activators.player.PlayerDamagePlayerActivator;
import net.mineland.creative.modules.coding.activators.player.PlayerDeathActivator;
import net.mineland.creative.modules.coding.activators.player.PlayerDropItemActivator;
import net.mineland.creative.modules.coding.activators.player.PlayerFoodLevelChangeActivator;
import net.mineland.creative.modules.coding.activators.player.PlayerJoinActivator;
import net.mineland.creative.modules.coding.activators.player.PlayerJumpActivator;
import net.mineland.creative.modules.coding.activators.player.PlayerKillMobActivator;
import net.mineland.creative.modules.coding.activators.player.PlayerKillPlayerActivator;
import net.mineland.creative.modules.coding.activators.player.PlayerLeftClickActivator;
import net.mineland.creative.modules.coding.activators.player.PlayerMobDamagePlayerActivator;
import net.mineland.creative.modules.coding.activators.player.PlayerMobKillPlayerActivator;
import net.mineland.creative.modules.coding.activators.player.PlayerMoveActivator;
import net.mineland.creative.modules.coding.activators.player.PlayerPickupItemActivator;
import net.mineland.creative.modules.coding.activators.player.PlayerProjectileDamageActivator;
import net.mineland.creative.modules.coding.activators.player.PlayerQuitActivator;
import net.mineland.creative.modules.coding.activators.player.PlayerRightClickActivator;
import net.mineland.creative.modules.coding.activators.player.PlayerRightClickEntityActivator;
import net.mineland.creative.modules.coding.activators.player.PlayerSneakActivator;
import net.mineland.creative.modules.coding.activators.player.PlayerSprintActivator;
import net.mineland.creative.modules.coding.activators.player.PlayerStopSprintActivator;
import net.mineland.creative.modules.coding.activators.player.PlayerSwapHandsActivator;
import net.mineland.creative.modules.coding.activators.player.PlayerTakeDamageActivator;
import net.mineland.creative.modules.coding.activators.player.PlayerTakeFallDamageActivator;
import net.mineland.creative.modules.coding.activators.player.PlayerUnsneakActivator;
import net.mineland.creative.modules.coding.activators.player.PlayerVoteActivator;
import net.mineland.creative.modules.coding.events.EntityEvent;
import net.mineland.creative.modules.coding.events.GameEvent;
import net.mineland.creative.modules.coding.exceptions.ExitException;
import net.mineland.creative.modules.coding.exceptions.StackLimitExitException;
import net.mineland.creative.modules.creative.ModuleCreative;
import net.mineland.creative.modules.creative.Plot;
import net.mineland.creative.modules.creative.PlotMode;
import org.bukkit.entity.Entity;
import org.bukkit.event.Event;
import ua.govnojon.libs.bukkitutil.ItemData;

public enum ActivatorType {
   PLAYER_JOIN(PlayerJoinActivator.class, PlayerJoinActivator.Event.class),
   PLAYER_QUIT(PlayerQuitActivator.class, PlayerQuitActivator.Event.class),
   PLAYER_RIGHT_CLICK(PlayerRightClickActivator.class, PlayerRightClickActivator.Event.class),
   PLAYER_LEFT_CLICK(PlayerLeftClickActivator.class, PlayerLeftClickActivator.Event.class),
   PLAYER_KILL_PLAYER(PlayerKillPlayerActivator.class, PlayerKillPlayerActivator.Event.class),
   PLAYER_DEATH(PlayerDeathActivator.class, PlayerDeathActivator.Event.class),
   PLAYER_SNEAK(PlayerSneakActivator.class, PlayerSneakActivator.Event.class),
   PLAYER_UNSNEAK(PlayerUnsneakActivator.class, PlayerUnsneakActivator.Event.class),
   PLAYER_DAMAGE_PLAYER(PlayerDamagePlayerActivator.class, PlayerDamagePlayerActivator.Event.class),
   PLAYER_PROJECTILE_DAMAGE(PlayerProjectileDamageActivator.class, PlayerProjectileDamageActivator.Event.class),
   PLAYER_TAKE_DAMAGE(PlayerTakeDamageActivator.class, PlayerTakeDamageActivator.Event.class),
   PLAYER_KILL_MOB(PlayerKillMobActivator.class, PlayerKillMobActivator.Event.class),
   PLAYER_MOB_KILL_PLAYER(PlayerMobKillPlayerActivator.class, PlayerMobKillPlayerActivator.Event.class),
   PLAYER_DAMAGE_MOB(PlayerDamageMobActivator.class, PlayerDamageMobActivator.Event.class),
   PLAYER_MOB_DAMAGE_PLAYER(PlayerMobDamagePlayerActivator.class, PlayerMobDamagePlayerActivator.Event.class),
   PLAYER_RIGHT_CLICK_ENTITY(PlayerRightClickEntityActivator.class, PlayerRightClickEntityActivator.Event.class),
   PLAYER_BLOCK_PLACE(PlayerBlockPlaceActivator.class, PlayerBlockPlaceActivator.Event.class),
   PLAYER_BLOCK_BREAK(PlayerBlockBreakActivator.class, PlayerBlockBreakActivator.Event.class),
   PLAYER_PICKUP_ITEM(PlayerPickupItemActivator.class, PlayerPickupItemActivator.Event.class),
   PLAYER_DROP_ITEM(PlayerDropItemActivator.class, PlayerDropItemActivator.Event.class),
   PLAYER_CONSUME_ITEM(PlayerConsumeItemActivator.class, PlayerConsumeItemActivator.Event.class),
   PLAYER_FOOD_LEVEL_CHANGE(PlayerFoodLevelChangeActivator.class, PlayerFoodLevelChangeActivator.Event.class),
   PLAYER_SWAP_HANDS(PlayerSwapHandsActivator.class, PlayerSwapHandsActivator.Event.class),
   PLAYER_CHANGE_SLOT(PlayerChangeSlotActivator.class, PlayerChangeSlotActivator.Event.class),
   PLAYER_SPRINT(PlayerSprintActivator.class, PlayerSprintActivator.Event.class),
   PLAYER_JUMP(PlayerJumpActivator.class, PlayerJumpActivator.Event.class),
   PLAYER_STOP_SPRINT(PlayerStopSprintActivator.class, PlayerStopSprintActivator.Event.class),
   PLAYER_MOVE(PlayerMoveActivator.class, PlayerMoveActivator.Event.class),
   PLAYER_VOTE(PlayerVoteActivator.class, PlayerVoteActivator.Event.class),
   PLAYER_TAKE_FALL_DAMAGE(PlayerTakeFallDamageActivator.class, PlayerTakeFallDamageActivator.Event.class),
   PLAYER_CHAT(PlayerChatActivator.class, PlayerChatActivator.Event.class),
   GAME_LOOP(GameLoopActivator.class, (Class)null),
   FUNCTION(FunctionActivator.class, (Class)null);

   protected static ModuleCreative moduleCreative = (ModuleCreative)Module.getInstance(ModuleCreative.class);
   private Class<? extends Activator> activatorClass;
   private Class<? extends Event> eventClass;
   private Activator activator;

   private ActivatorType(Class<? extends Activator> activatorClass, Class<? extends Event> eventClass) {
      this.activatorClass = activatorClass;
      this.eventClass = eventClass;
      this.activator = this.create((Plot)null);
   }

   public static boolean executeActions(Activator activator, GameEvent gameEvent, List<Action> actionList, int stackCounter, AtomicInteger callCounter) {
      return executeActions(activator, gameEvent, actionList, 0, stackCounter, callCounter);
   }

   public static boolean executeActions(Activator activator, GameEvent gameEvent, List<Action> actionList, int fromIndex, int stackCounter, AtomicInteger callCounter) {
      if (gameEvent.getPlot().getPlotMode().equals(PlotMode.PLAYING) && !gameEvent.getPlot().stoppedCode) {
         checkGameStackLimit(activator.getPlot(), stackCounter);
         ++stackCounter;
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
                     ActivatorType activatorType = action.getActivator().getType();
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

   public Class<? extends Event> getEventClass() {
      return this.eventClass;
   }

   public Class<? extends Activator> getActivatorClass() {
      return this.activatorClass;
   }

   public ItemData getIcon() {
      return this.activator.getIcon();
   }

   public Activator create(Plot plot) {
      Activator activator = null;

      try {
         Constructor<? extends Activator> constructor = this.activatorClass.getConstructor(Plot.class);
         activator = (Activator)constructor.newInstance(plot);
      } catch (Exception var4) {
         var4.printStackTrace();
      }

      return activator;
   }
}
