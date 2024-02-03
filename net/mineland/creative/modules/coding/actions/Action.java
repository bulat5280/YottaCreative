package net.mineland.creative.modules.coding.actions;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.Collectors;
import javax.annotation.Nullable;
import net.mineland.api.user.IUser;
import net.mineland.core.bukkit.module.Module;
import net.mineland.core.bukkit.modules.message.Message;
import net.mineland.core.bukkit.modules.nms.NMS;
import net.mineland.core.bukkit.modules.user.Lang;
import net.mineland.core.bukkit.modules.user.User;
import net.mineland.creative.modules.coding.ChestParser;
import net.mineland.creative.modules.coding.CodeUtils;
import net.mineland.creative.modules.coding.ModuleCoding;
import net.mineland.creative.modules.coding.activators.Activator;
import net.mineland.creative.modules.coding.dynamicvariables.DynamicVariable;
import net.mineland.creative.modules.coding.events.DamageEvent;
import net.mineland.creative.modules.coding.events.EntityEvent;
import net.mineland.creative.modules.coding.events.GameEvent;
import net.mineland.creative.modules.coding.events.GamePlayerEvent;
import net.mineland.creative.modules.coding.events.KillEvent;
import net.mineland.creative.modules.coding.exceptions.CallLimitExitException;
import net.mineland.creative.modules.coding.exceptions.ExitException;
import net.mineland.creative.modules.coding.values.objects.LocationValue;
import net.mineland.creative.modules.coding.values.objects.Value;
import net.mineland.creative.modules.coding.worldactivators.WorldActivator;
import net.mineland.creative.modules.creative.ModuleCreative;
import net.mineland.creative.modules.creative.Plot;
import net.mineland.creative.modules.creative.PlotMode;
import org.apache.commons.lang.StringUtils;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.entity.Entity;
import org.bukkit.inventory.ItemStack;
import ua.govnojon.libs.bukkitutil.ItemData;
import ua.govnojon.libs.bukkitutil.PlayerUtil;
import ua.govnojon.libs.bukkitutil.chatcomponent.ChatComponent;

public abstract class Action {
   protected static ModuleCreative moduleCreative = (ModuleCreative)Module.getInstance(ModuleCreative.class);
   private static ModuleCoding moduleCoding = (ModuleCoding)Module.getInstance(ModuleCoding.class);
   private Map<String, Object> variables = new HashMap();
   protected Activator activator;
   protected WorldActivator worldActivator;
   PlayerSelection selection;
   protected List<Entity> selectedEntities;

   protected Action(Activator activator) {
      this.selection = PlayerSelection.SELECTION;
      this.selectedEntities = new ArrayList();
      this.activator = activator;
   }

   protected Action(WorldActivator worldActivator) {
      this.selection = PlayerSelection.SELECTION;
      this.selectedEntities = new ArrayList();
      this.worldActivator = worldActivator;
   }

   public boolean execute(List<Entity> selectedEntities, GameEvent gameEvent, int stackCounter, AtomicInteger callCounter) {
      try {
         this.selectedEntities = (List)this.selection.select(selectedEntities, gameEvent).stream().filter((entity) -> {
            return gameEvent.getPlot().getPlotTerritory().isContains(entity.getLocation());
         }).collect(Collectors.toList());
         this.doRun(this.getSelectedEntities(), gameEvent, stackCounter, callCounter);
      } catch (ExitException var6) {
         throw var6;
      } catch (Exception var7) {
         gameEvent.getPlot().getOnlinePlayers().forEach((user) -> {
            this.sendException(user, var7);
         });
      }

      return true;
   }

   public WorldActivator getWorldActivator() {
      return this.worldActivator;
   }

   @Nullable
   public Activator getActivator() {
      return this.activator;
   }

   public boolean needPerm() {
      return false;
   }

   /** @deprecated */
   @Deprecated
   public boolean parseChest(ItemStack[] itemStacks) {
      return false;
   }

   public boolean parseChest(ChestParser chestParser) {
      return false;
   }

   public boolean doRun(List<Entity> selectedEntities, GameEvent gameEvent, int stackCounter, AtomicInteger callCounter) {
      if (gameEvent.getPlot().getPlotMode().equals(PlotMode.PLAYING) && !gameEvent.getPlot().stoppedCode) {
         this.checkCallLimit(gameEvent.getPlot(), callCounter);
         this.checkCallByTimeLimit(gameEvent.getPlot());
         return this.run(selectedEntities, gameEvent, stackCounter, callCounter);
      } else {
         return false;
      }
   }

   private void checkCallByTimeLimit(Plot plot) {
      int tick = NMS.getManager().getTick();
      if (tick - plot.startActionTick >= 5) {
         plot.startActionTick = tick;
         plot.actionCounter = 0;
      } else {
         ++plot.actionCounter;
         if (plot.actionCounter > 200) {
            if (plot.getOwner() != null) {
               plot.getOwner().sendMessage("лимит.вызовов", "{size}", String.valueOf(plot.actionCounter), "{limit}", String.valueOf(200));
            }

            moduleCreative.getLogger().warning("" + plot.getId() + ", " + plot.actionCounter + "/ 5 ");
            throw new CallLimitExitException();
         }
      }

   }

   private void checkCallLimit(Plot plot, AtomicInteger callCounter) {
      int limit = moduleCreative.getCallLimit(plot.getOwner());
      if (callCounter.get() >= limit) {
         if (plot.getOwner() != null) {
            plot.getOwner().sendMessage("лимит.вызовов.мда", "{size}", String.valueOf(callCounter.get()), "{limit}", String.valueOf(limit));
         }

         moduleCreative.getLogger().warning("" + plot.getId() + ", " + callCounter.get() + "/" + limit);
         throw new CallLimitExitException();
      } else {
         callCounter.incrementAndGet();
      }
   }

   public PlayerSelection getSelection() {
      return this.selection;
   }

   public void setSelection(PlayerSelection selection) {
      this.selection = selection;
   }

   public List<Entity> getSelectedEntities() {
      return (List)this.selectedEntities.stream().filter((entity) -> {
         return this.getActivator() != null && this.getActivator().getPlot().inTerritory(entity.getLocation());
      }).collect(Collectors.toList());
   }

   public <T> T getVar(String key, GameEvent gameEvent, Entity entity) {
      Object variable = this.variables.get(key);
      if (variable instanceof Value) {
         try {
            return ((Value)variable).get(gameEvent, entity);
         } catch (ClassCastException var6) {
            return null;
         }
      } else if (variable instanceof DynamicVariable) {
         try {
            DynamicVariable dynamicVariable = (DynamicVariable)variable;
            return (new DynamicVariable(this.replacePlaceholders(dynamicVariable.getName(), gameEvent, entity))).getValue(gameEvent.getPlot());
         } catch (ClassCastException var7) {
            return null;
         }
      } else {
         try {
            return variable;
         } catch (ClassCastException var8) {
            return null;
         }
      }
   }

   public <T> T getVar(String key, T def, GameEvent gameEvent, Entity entity) {
      try {
         T variable = this.getVar(key, gameEvent, entity);
         return variable != null ? variable : def;
      } catch (ClassCastException var6) {
         return null;
      }
   }

   public DynamicVariable getDynamicVariable(String key, GameEvent gameEvent, Entity entity) {
      if (this.variables.containsKey(key)) {
         try {
            DynamicVariable dynamicVariable = (DynamicVariable)this.variables.get(key);
            DynamicVariable result = new DynamicVariable(this.replacePlaceholders(dynamicVariable.getName(), gameEvent, entity));
            result.setSave(dynamicVariable.isSave());
            return result;
         } catch (ClassCastException var6) {
            return null;
         }
      } else {
         return null;
      }
   }

   public String getVarString(String key, GameEvent gameEvent, Entity entity) {
      Object object = this.getVar(key, gameEvent, entity);
      if (object == null) {
         return null;
      } else {
         String string = object instanceof Double ? object.toString().replace(".0", "") : object.toString();
         return this.replacePlaceholders(string, gameEvent, entity);
      }
   }

   public String replacePlaceholders(String string, GameEvent gameEvent, Entity entity) {
      if (string == null) {
         return null;
      } else {
         string = StringUtils.replace(string, "%selected%", String.join("", (Iterable)this.getSelectedEntities().stream().map(CodeUtils::getEntityName).collect(Collectors.toList())));
         string = StringUtils.replace(string, "%default%", CodeUtils.getEntityName(gameEvent.getDefaultEntity()));
         string = StringUtils.replace(string, "%player%", CodeUtils.getEntityName(gameEvent instanceof GamePlayerEvent ? ((GamePlayerEvent)gameEvent).getPlayer() : null));
         string = StringUtils.replace(string, "%victim%", gameEvent instanceof DamageEvent ? CodeUtils.getEntityName(((DamageEvent)gameEvent).getVictim()) : CodeUtils.getEntityName(gameEvent instanceof KillEvent ? ((KillEvent)gameEvent).getVictim() : null));
         string = StringUtils.replace(string, "%damager%", CodeUtils.getEntityName(gameEvent instanceof DamageEvent ? ((DamageEvent)gameEvent).getDamager() : null));
         string = StringUtils.replace(string, "%killer%", CodeUtils.getEntityName(gameEvent instanceof KillEvent ? ((KillEvent)gameEvent).getKiller() : null));
         string = StringUtils.replace(string, "%shooter%", CodeUtils.getEntityName(gameEvent instanceof DamageEvent ? ((DamageEvent)gameEvent).getShooter() : null));
         string = StringUtils.replace(string, "%entity%", CodeUtils.getEntityName(gameEvent instanceof EntityEvent ? ((EntityEvent)gameEvent).getEntity() : null));
         return string;
      }
   }

   public Location getVarLocation(String key, GameEvent gameEvent, Entity entity) {
      Object object = this.getVar(key, gameEvent, entity);
      Location location = null;
      if (object instanceof Location) {
         location = (Location)object;
      } else if (object instanceof DynamicVariable) {
         try {
            DynamicVariable dynamicVariable = (DynamicVariable)object;
            location = (Location)(new DynamicVariable(this.replacePlaceholders(dynamicVariable.getName(), gameEvent, entity))).getValue(gameEvent.getPlot());
         } catch (ClassCastException var7) {
         }
      } else if (object instanceof LocationValue) {
         location = ((LocationValue)object).get(gameEvent, entity);
      }

      return gameEvent.getPlot().getPlotTerritory().isContains(location) ? location : null;
   }

   public int getVar(String key, int def, GameEvent gameEvent, Entity entity) {
      if (this.variables.containsKey(key)) {
         Object var = this.getVar(key, gameEvent, entity);
         if (var instanceof Number) {
            return ((Number)var).intValue();
         }
      }

      return def;
   }

   public double getVar(String key, double def, GameEvent gameEvent, Entity entity) {
      if (this.variables.containsKey(key)) {
         try {
            Object var = this.getVar(key, gameEvent, entity);
            if (var instanceof Number) {
               return ((Number)var).doubleValue();
            }
         } catch (Exception var7) {
         }
      }

      return def;
   }

   public float getVar(String key, float def, GameEvent gameEvent, Entity entity) {
      if (this.variables.containsKey(key)) {
         Object var = this.getVar(key, gameEvent, entity);
         if (var instanceof Number) {
            return ((Number)var).floatValue();
         }
      }

      return def;
   }

   public <T> List<T> getVars(String key, GameEvent gameEvent, Entity entity) {
      Object var = this.getVar(key, gameEvent, entity);
      List objects;
      if (var instanceof ItemStack[]) {
         objects = Arrays.asList((Object[])((Object[])var));
      } else if (var instanceof List) {
         objects = (List)var;
      } else {
         objects = null;
      }

      return (List)(objects == null ? new ArrayList() : (List)objects.stream().map((o) -> {
         if (o instanceof Value) {
            try {
               return ((Value)o).get(gameEvent, entity);
            } catch (ClassCastException var5) {
               return null;
            }
         } else if (o instanceof DynamicVariable) {
            try {
               DynamicVariable dynamicVariable = (DynamicVariable)o;
               return (new DynamicVariable(this.replacePlaceholders(dynamicVariable.getName(), gameEvent, entity))).getValue(gameEvent.getPlot());
            } catch (ClassCastException var6) {
               return null;
            }
         } else {
            try {
               return o;
            } catch (ClassCastException var7) {
               return null;
            }
         }
      }).filter(Objects::nonNull).collect(Collectors.toList()));
   }

   public List<String> getVarsStrings(String key, GameEvent gameEvent, Entity entity) {
      List<Object> objects = (List)this.getVar(key, gameEvent, entity);
      return (List)(objects == null ? new ArrayList() : (List)objects.stream().map((o) -> {
         if (o instanceof Value) {
            return ((Value)o).get(gameEvent, entity).toString();
         } else if (o instanceof DynamicVariable) {
            DynamicVariable dynamicVariable = (DynamicVariable)o;
            Object value = (new DynamicVariable(this.replacePlaceholders(dynamicVariable.getName(), gameEvent, entity))).getValue(gameEvent.getPlot());
            String string = value == null ? null : (value instanceof Double ? value.toString().replace(".0", "") : value.toString());
            return this.replacePlaceholders(string, gameEvent, entity);
         } else if (o != null) {
            String stringx = o instanceof Double ? o.toString().replace(".0", "") : o.toString();
            return this.replacePlaceholders(stringx, gameEvent, entity);
         } else {
            return null;
         }
      }).filter(Objects::nonNull).collect(Collectors.toList()));
   }

   public List<Location> getVarsLocations(String key, GameEvent gameEvent, Entity entity) {
      List<Object> objects = (List)this.getVar(key, gameEvent, entity);
      return (List)(objects == null ? new ArrayList() : (List)objects.stream().map((o) -> {
         if (o instanceof LocationValue) {
            try {
               return ((LocationValue)o).get(gameEvent, entity);
            } catch (ClassCastException var5) {
               return null;
            }
         } else if (o instanceof DynamicVariable) {
            try {
               DynamicVariable dynamicVariable = (DynamicVariable)o;
               return (Location)(new DynamicVariable(this.replacePlaceholders(dynamicVariable.getName(), gameEvent, entity))).getValue(gameEvent.getPlot());
            } catch (ClassCastException var6) {
               return null;
            }
         } else {
            try {
               return (Location)o;
            } catch (ClassCastException var7) {
               return null;
            }
         }
      }).filter(Objects::nonNull).filter((location) -> {
         return gameEvent.getPlot().inTerritory(location);
      }).collect(Collectors.toList()));
   }

   public void putVar(String key, Object object) {
      this.variables.put(key, object);
   }

   public void putVar(String key, ItemStack[] itemStacks) {
      this.putVar(key, itemStacks, true);
   }

   public void putVar(String key, ItemStack[] itemStacks, boolean parse) {
      if (parse) {
         List<Object> list = (List)Arrays.stream(itemStacks).map(CodeUtils::parseItem).filter(Objects::nonNull).collect(Collectors.toList());
         this.variables.put(key, list);
      } else {
         this.variables.put(key, itemStacks);
      }

   }

   public void putVar(String key, ItemStack itemStack, boolean parse) {
      if (parse) {
         Object object = CodeUtils.parseItem(itemStack);
         if (object != null) {
            this.variables.put(key, CodeUtils.parseItem(itemStack));
         }
      } else {
         this.variables.put(key, itemStack);
      }

   }

   public void putVar(String key, ItemStack itemStack) {
      this.putVar(key, itemStack, true);
   }

   public void sendException(User user, Exception ex) {
      user.sendMessage("coding.error_executing", "{action}", this.getDisplayName(user.getLang()));
      PlayerUtil.playSound(user.getPlayer(), Sound.BLOCK_ANVIL_USE, 2.14748365E9F, 2.0F);
      if (user.hasPermission("mineland.libs.error.report")) {
         ChatComponent.getErrorReport(ex, Message.getMessage((IUser)user, "ошибка.кода")).send(user.getPlayer());
      }

      if (this.getActivator() != null) {
         moduleCoding.getLogger().severe("[Plot #" + this.getActivator().getPlot().getId() + ", Owner: " + this.getActivator().getPlot().getOwner().getPlayer().getName() + "]: " + this.toString());
      }

      ex.printStackTrace();
   }

   public String getDisplayName(Lang lang) {
      return this.getActivator() != null ? "" + Message.getMessage(lang, "coding.sign." + this.getActivator().getType().name()) + " " + Message.getMessage(lang, "coding.sign." + this.getType().name()) : "" + Message.getMessage(lang, "coding.sign." + this.getType().name());
   }

   public String toString() {
      return this.getType().name() + "::" + this.variables.entrySet().toString();
   }

   public abstract ActionType getType();

   public abstract Action.Category getCategory();

   public abstract ItemData getIcon();

   public abstract boolean run(List<Entity> var1, GameEvent var2, int var3, AtomicInteger var4);

   public static enum Perm {
      GAMER,
      SKILLED,
      EXPERT,
      HERO;

      public String getPerm() {
         return "creative.coding." + this.name();
      }
   }

   public static enum Category {
      IF_PLAYER(new ItemData(Material.WOOD)),
      PLAYER_INVENTORY(new ItemData(Material.CHEST)),
      PLAYER_COMMUNICATION(new ItemData(Material.BEACON)),
      PLAYER_MOVEMENT(new ItemData(Material.LEATHER_BOOTS)),
      PLAYER_PARAMETERS(new ItemData(Material.APPLE)),
      PLAYER_SETTINGS(new ItemData(Material.ANVIL)),
      PLAYER_ANIMATIONS(new ItemData(Material.BED)),
      GAME_ENTITY_SPAWNING(new ItemData(Material.MONSTER_EGG)),
      GAME_CODE_UTILITY(new ItemData(Material.REDSTONE_COMPARATOR)),
      GAME_BLOCK_MANIPULATION(new ItemData(Material.GRASS)),
      GAME_SPECIAL_EFFECTS(new ItemData(Material.FIREWORK)),
      GAME_SCOREBOARD_MANIPULATION(new ItemData(Material.PAINTING)),
      GAME_WORLD_MANIPULATION(new ItemData(Material.BEDROCK)),
      GAME_CONFIGS(new ItemData(Material.PAPER)),
      IF_ENTITY(new ItemData(Material.BRICK)),
      IF_GAME(new ItemData(Material.RED_NETHER_BRICK)),
      FUNCTION(new ItemData(Material.LAPIS_BLOCK)),
      SET_VARIABLE(new ItemData(Material.IRON_BLOCK)),
      IF_VARIABLE(new ItemData(Material.OBSIDIAN)),
      SELECT_OBJECT(new ItemData(Material.PURPUR_BLOCK));

      private ItemData icon;

      private Category(ItemData icon) {
         this.icon = icon;
      }

      public ItemData getIcon() {
         return this.icon;
      }
   }
}
