package net.mineland.core.bukkit.modules.nms;

import com.comphenix.protocol.wrappers.WrappedDataWatcher;
import com.mojang.authlib.GameProfile;
import java.util.UUID;
import net.minecraft.server.v1_12_R1.DataWatcher;
import net.minecraft.server.v1_12_R1.EntityArmorStand;
import net.minecraft.server.v1_12_R1.EntityItemFrame;
import net.minecraft.server.v1_12_R1.EntityPlayer;
import net.minecraft.server.v1_12_R1.PlayerInteractManager;
import net.minecraft.server.v1_12_R1.World;
import org.bukkit.Bukkit;
import org.bukkit.craftbukkit.v1_12_R1.CraftServer;
import org.bukkit.craftbukkit.v1_12_R1.CraftWorld;
import ua.govnojon.libs.bukkitutil.WorldUtil;

public enum DataWatcherType {
   ITEM_FRAME((new EntityItemFrame(world())).getDataWatcher()),
   PLAYER((new EntityPlayer(((CraftServer)Bukkit.getServer()).getServer(), ((CraftWorld)WorldUtil.getDefaultWorld()).getHandle(), new GameProfile(UUID.randomUUID(), "Name"), new PlayerInteractManager(((CraftWorld)WorldUtil.getDefaultWorld()).getHandle()))).getDataWatcher()),
   ARMOR_STAND((new EntityArmorStand(world())).getDataWatcher());

   private WrappedDataWatcher dataWatcher;

   private DataWatcherType(DataWatcher dataWatcher) {
      this.dataWatcher = new WrappedDataWatcher(dataWatcher);
   }

   private static World world() {
      return ((CraftWorld)WorldUtil.getDefaultWorld()).getHandle();
   }

   public WrappedDataWatcher getDataWatcher() {
      return this.dataWatcher;
   }
}
