package net.mineland.core.bukkit.modules.tracker;

import net.mineland.core.bukkit.modules.user.User;
import org.bukkit.Location;

public interface TrackerPlayer {
   void onMove(User var1, Location var2, Location var3);
}
