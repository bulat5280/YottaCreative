package net.mineland.creative.modules.coding;

import java.io.File;
import java.util.Arrays;
import java.util.Iterator;
import java.util.List;
import java.util.Map.Entry;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;
import net.mineland.core.bukkit.module.BukkitModule;
import net.mineland.core.bukkit.module.Module;
import net.mineland.core.bukkit.modules.command.CommandManager;
import net.mineland.core.bukkit.modules.gui.ModuleGui;
import net.mineland.core.bukkit.modules.nms.NMS;
import net.mineland.creative.modules.coding.actions.gui.GuiMenuActionCategories;
import net.mineland.creative.modules.coding.actions.gui.GuiMenuActions;
import net.mineland.creative.modules.coding.actions.gui.GuiMenuConditionType;
import net.mineland.creative.modules.coding.actions.gui.GuiMenuFunctions;
import net.mineland.creative.modules.coding.actions.gui.GuiMenuPlayerSelection;
import net.mineland.creative.modules.coding.actions.gui.GuiMenuSetVariable;
import net.mineland.creative.modules.coding.activators.GameLoopActivator;
import net.mineland.creative.modules.coding.activators.gui.GuiMenuPlayerEvents;
import net.mineland.creative.modules.coding.commands.CommandBuild;
import net.mineland.creative.modules.coding.commands.CommandDev;
import net.mineland.creative.modules.coding.commands.CommandMode;
import net.mineland.creative.modules.coding.commands.CommandPlay;
import net.mineland.creative.modules.coding.exceptions.ExitException;
import net.mineland.creative.modules.coding.variables.GuiMenuParticleEffects;
import net.mineland.creative.modules.coding.variables.GuiMenuPotionEffects;
import net.mineland.creative.modules.coding.variables.GuiMenuValues;
import net.mineland.creative.modules.coding.variables.GuiMenuVariableItems;
import net.mineland.creative.modules.creative.ModuleCreative;
import net.mineland.creative.modules.creative.Plot;
import net.mineland.creative.modules.creative.PlotMode;
import org.apache.commons.io.FileUtils;
import org.bukkit.Bukkit;
import org.bukkit.World;
import org.bukkit.plugin.Plugin;
import org.bukkit.scheduler.BukkitTask;
import ua.govnojon.libs.bukkitutil.WorldUtil;
import ua.govnojon.libs.bukkitutil.schedule.Schedule;

public class ModuleCoding extends BukkitModule {
   private static ModuleGui moduleGui = (ModuleGui)Module.getInstance(ModuleGui.class);
   private static ModuleCreative moduleCreative = (ModuleCreative)Module.getInstance(ModuleCreative.class);
   private World codingWorld;
   private int codingSize = 3;
   private BukkitTask mainScheduler;
   private File codingPlotsDir;
   private List<String> filesNotCleanInCodingWorld = Arrays.asList("level.dat", "level.dat_old", "session.lock", "uid.dat");

   public ModuleCoding(int priority, Plugin plugin) {
      super("coding", priority, plugin);
   }

   public void onFirstEnable() {
      this.runTimerFreeCodingWorld();
   }

   public void onEnable() {
      try {
         this.codingPlotsDir = new File(Bukkit.getWorldContainer(), "coding_plots");
         if (!this.codingPlotsDir.exists()) {
            FileUtils.forceMkdir(this.codingPlotsDir);
         }

         this.registerData(new EventListener());
         this.codingWorld = WorldUtil.createOrLoadWorld("coding_world");
         this.codingWorld.setGameRuleValue("doDaylightCycle", "false");
         this.codingWorld.setGameRuleValue("doWeatherCycle", "false");
         this.codingWorld.setTime(6000L);
         this.getCodingWorld().setAutoSave(false);
         CommandManager.registerCommand(new CommandPlay());
         CommandManager.registerCommand(new CommandBuild());
         CommandManager.registerCommand(new CommandDev());
         CommandManager.registerCommand(new CommandMode());
         moduleGui.registerGui(GuiMenuPlayerEvents.class, GuiMenuPlayerEvents::new);
         moduleGui.registerGui(GuiMenuActionCategories.class, GuiMenuActionCategories::new);
         moduleGui.registerGui(GuiMenuActions.class, GuiMenuActions::new);
         moduleGui.registerGui(GuiMenuSetVariable.class, GuiMenuSetVariable::new);
         moduleGui.registerGui(GuiMenuFunctions.class, GuiMenuFunctions::new);
         moduleGui.registerGui(GuiMenuVariableItems.class, GuiMenuVariableItems::new);
         moduleGui.registerGui(GuiMenuPotionEffects.class, GuiMenuPotionEffects::new);
         moduleGui.registerGui(GuiMenuParticleEffects.class, GuiMenuParticleEffects::new);
         moduleGui.registerGui(GuiMenuValues.class, GuiMenuValues::new);
         moduleGui.registerGui(GuiMenuPlayerSelection.class, GuiMenuPlayerSelection::new);
         moduleGui.registerGui(GuiMenuConditionType.class, GuiMenuConditionType::new);
         this.mainScheduler = Schedule.timer(() -> {
            Iterator var1 = moduleCreative.getPlotManager().getPlots().iterator();

            while(var1.hasNext()) {
               Plot plot = (Plot)var1.next();

               try {
                  if (plot.getPlotMode() == PlotMode.PLAYING) {
                     Iterator iterator = plot.getCodeHandler().getGameLoops().entrySet().iterator();

                     while(iterator.hasNext() && plot.getPlotMode() == PlotMode.PLAYING && !plot.stoppedCode) {
                        Entry<String, GameLoopActivator> entry = (Entry)iterator.next();
                        GameLoopActivator gameLoopActivator = (GameLoopActivator)entry.getValue();
                        gameLoopActivator.setCurrentTicks(gameLoopActivator.getCurrentTicks() - 1);
                        if (gameLoopActivator.getCurrentTicks() <= 0) {
                           gameLoopActivator.setCurrentTicks(gameLoopActivator.getTicks());

                           try {
                              gameLoopActivator.execute(gameLoopActivator.getGameEvent(), 0, new AtomicInteger());
                           } catch (ExitException var7) {
                              plot.onExitException(var7);
                           }
                        }
                     }
                  }
               } catch (Exception var8) {
                  this.getLogger().severe("Ошибка таймера, плот " + plot);
               }
            }

         }, 1L, 1L);
         this.clearCodingWorld();
         this.runTimerFreeCodingWorld();
      } catch (Throwable var2) {
         throw var2;
      }
   }

   private void runTimerFreeCodingWorld() {
      Schedule.timer(() -> {
         this.getLogger().info("Удаляем чанки выгруженных регионов.");
         World world = this.getCodingWorld();
         NMS.getManager().clearAllChunksInWorldNoSaveWithPredicate(world, (chunk) -> {
            return moduleCreative.getPlotManager().getPlots().stream().noneMatch((plot) -> {
               return plot.getCodingRegion().getTerritory().isContains(chunk);
            });
         });
      }, 2L, 2L, TimeUnit.MINUTES);
   }

   public void clearCodingWorld() {
      World codingWorld = this.getCodingWorld();
      File folder = codingWorld.getWorldFolder();
      File[] var3 = folder.listFiles();
      int var4 = var3.length;

      for(int var5 = 0; var5 < var4; ++var5) {
         File file = var3[var5];

         try {
            if (!this.filesNotCleanInCodingWorld.contains(file.getName())) {
               FileUtils.forceDelete(file);
            }
         } catch (Exception var8) {
            this.getLogger().severe("Ошибка удаления файла" + file);
            var8.printStackTrace();
         }
      }

   }

   public void onDisable() {
      this.mainScheduler.cancel();
   }

   public World getCodingWorld() {
      return this.codingWorld;
   }

   public int getCodingSize() {
      return this.codingSize;
   }

   public File getCodingPlotsDir() {
      return this.codingPlotsDir;
   }
}
