package net.mineland.creative.modules.creative;

import com.google.common.collect.ForwardingSet;
import java.util.Set;
import net.mineland.core.bukkit.modules.gui.ModuleGui;
import net.mineland.creative.modules.creative.gui.GuiMenuPlotsOnline;
import org.jetbrains.annotations.NotNull;
import ua.govnojon.libs.bukkitutil.schedule.Schedule;
import ua.govnojon.libs.myjava.Try;

public class PlotSet extends ForwardingSet<Plot> {
   private Set<Plot> delegate;
   private ModuleGui moduleGui;

   public PlotSet(Set<Plot> delegate, ModuleGui moduleGui) {
      this.delegate = delegate;
      this.moduleGui = moduleGui;
   }

   public void onChange() {
      Schedule.run(() -> {
         this.moduleGui.reloadGui(GuiMenuPlotsOnline.class);
      });
   }

   protected Set<Plot> delegate() {
      return this.delegate;
   }

   public boolean add(@NotNull Plot element) {
      boolean result = super.add(element);
      Try.ignore(this::onChange);
      return result;
   }

   public boolean remove(@NotNull Object object) {
      boolean result = super.remove(object);
      Try.ignore(this::onChange);
      return result;
   }
}
