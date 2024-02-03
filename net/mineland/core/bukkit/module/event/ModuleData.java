package net.mineland.core.bukkit.module.event;

import net.mineland.core.bukkit.module.Module;

public interface ModuleData {
   void register(Module var1, Object var2);

   void unregister(Module var1, Object var2);
}
