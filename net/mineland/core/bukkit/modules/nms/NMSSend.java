package net.mineland.core.bukkit.modules.nms;

import java.lang.reflect.Field;
import java.util.Collection;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;
import java.util.UUID;
import net.minecraft.server.v1_12_R1.BlockPosition;
import net.minecraft.server.v1_12_R1.BossBattle;
import net.minecraft.server.v1_12_R1.DataWatcher;
import net.minecraft.server.v1_12_R1.DataWatcherObject;
import net.minecraft.server.v1_12_R1.EntityLiving;
import net.minecraft.server.v1_12_R1.MobEffect;
import net.minecraft.server.v1_12_R1.MobEffects;
import net.minecraft.server.v1_12_R1.Packet;
import net.minecraft.server.v1_12_R1.PacketPlayOutBlockBreakAnimation;
import net.minecraft.server.v1_12_R1.PacketPlayOutBoss;
import net.minecraft.server.v1_12_R1.PacketPlayOutEntityEffect;
import net.minecraft.server.v1_12_R1.PacketPlayOutEntityTeleport;
import net.minecraft.server.v1_12_R1.PacketPlayOutEntityVelocity;
import net.minecraft.server.v1_12_R1.PacketPlayOutMapChunk;
import net.minecraft.server.v1_12_R1.PacketPlayOutMount;
import net.minecraft.server.v1_12_R1.PotionUtil;
import net.minecraft.server.v1_12_R1.BossBattle.BarColor;
import net.minecraft.server.v1_12_R1.BossBattle.BarStyle;
import net.minecraft.server.v1_12_R1.IChatBaseComponent.ChatSerializer;
import net.minecraft.server.v1_12_R1.PacketPlayOutBoss.Action;
import net.minecraft.server.v1_12_R1.PacketPlayOutEntity.PacketPlayOutRelEntityMoveLook;
import org.bukkit.Chunk;
import org.bukkit.Location;
import org.bukkit.block.Block;
import org.bukkit.craftbukkit.v1_12_R1.CraftChunk;
import org.bukkit.craftbukkit.v1_12_R1.entity.CraftEntity;
import org.bukkit.craftbukkit.v1_12_R1.entity.CraftLivingEntity;
import org.bukkit.craftbukkit.v1_12_R1.entity.CraftPlayer;
import org.bukkit.entity.Entity;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import ua.govnojon.libs.bukkitutil.BukkitUtil;
import ua.govnojon.libs.myjava.MyObject;

public class NMSSend {
   private static DataWatcherObject<Integer> g;
   private static DataWatcherObject<Boolean> h;
   private int entityBlockBreakId = 1073741823;
   private UUID bossFirstUUID = UUID.randomUUID();
   private BarColor[] colors = BarColor.values();
   private BarStyle[] styles = BarStyle.values();
   private Field a;
   private Field b;
   private Field c;
   private Field d;

   public NMSSend() {
      try {
         this.a = PacketPlayOutEntityVelocity.class.getDeclaredField("a");
         this.b = PacketPlayOutEntityVelocity.class.getDeclaredField("b");
         this.c = PacketPlayOutEntityVelocity.class.getDeclaredField("c");
         this.d = PacketPlayOutEntityVelocity.class.getDeclaredField("d");
         this.a.setAccessible(true);
         this.b.setAccessible(true);
         this.c.setAccessible(true);
         this.d.setAccessible(true);
      } catch (Exception var2) {
         var2.printStackTrace();
      }

   }

   public void sendAddBossBar(Collection<Player> players, String text, org.bukkit.boss.BarColor barColor, org.bukkit.boss.BarStyle barStyle, float value) {
      BossBattle bossBattle = new BossBattle(this.bossFirstUUID, ChatSerializer.b("{\"text\": \"" + text + "\"}"), this.colors[barColor.ordinal()], this.styles[barStyle.ordinal()]) {
      };
      bossBattle.a(value);
      Packet packet = new PacketPlayOutBoss(Action.ADD, bossBattle);
      this.broadcast(players, packet);
   }

   public void sendUpdateBossBar(Collection<Player> players, String text, org.bukkit.boss.BarColor barColor, org.bukkit.boss.BarStyle barStyle, float value) {
      BossBattle bossBattle = new BossBattle(this.bossFirstUUID, ChatSerializer.b("{\"text\": \"" + text + "\"}"), this.colors[barColor.ordinal()], this.styles[barStyle.ordinal()]) {
      };
      bossBattle.a(value);
      this.broadcast(players, new PacketPlayOutBoss(Action.UPDATE_NAME, bossBattle));
      this.broadcast(players, new PacketPlayOutBoss(Action.UPDATE_PCT, bossBattle));
      this.broadcast(players, new PacketPlayOutBoss(Action.UPDATE_STYLE, bossBattle));
   }

   private void broadcast(Collection<Player> players, Packet packet) {
      NMS.getSenderPacket().submit(() -> {
         players.forEach((player) -> {
            ((CraftPlayer)player).getHandle().playerConnection.sendPacket(packet);
         });
      });
   }

   public void sendTeleportEntity(Player player, Entity entity, Location loc) {
      this.sendTeleportEntity((Collection)Collections.singletonList(player), entity, loc);
   }

   public void sendTeleportEntity(Collection<Player> players, Entity entity, Location loc) {
      net.minecraft.server.v1_12_R1.Entity handle = ((CraftEntity)entity).getHandle();
      double locX = handle.locX;
      double locY = handle.locY;
      double locZ = handle.locZ;
      float yaw = handle.yaw;
      float pitch = handle.pitch;
      handle.locX = loc.getX();
      handle.locY = loc.getY();
      handle.locZ = loc.getZ();
      handle.pitch = loc.getPitch();
      handle.yaw = loc.getYaw();
      this.broadcast(players, new PacketPlayOutEntityTeleport(handle));
      handle.locX = locX;
      handle.locY = locY;
      handle.locZ = locZ;
      handle.pitch = pitch;
      handle.yaw = yaw;
   }

   public void sendEffectEntity(Collection<Player> players, LivingEntity livingEntity) {
      EntityLiving handle = ((CraftLivingEntity)livingEntity).getHandle();
      this.invokeG(handle);
      NMS.getManager().sendMetadataEntity((Collection)players, livingEntity);
      handle.getEffects().forEach((mobEffect) -> {
         this.broadcast(players, new PacketPlayOutEntityEffect(handle.getId(), mobEffect));
      });
   }

   private void invokeG(EntityLiving handle) {
      Collection<MobEffect> effects = handle.getEffects();
      DataWatcher dataWatcher = handle.getDataWatcher();
      if (effects.isEmpty()) {
         dataWatcher.set(h, false);
         dataWatcher.set(g, 0);
         handle.setInvisible(false);
      } else {
         dataWatcher.set(h, EntityLiving.a(effects));
         dataWatcher.set(g, PotionUtil.a(effects));
         handle.setInvisible(handle.hasEffect(MobEffects.INVISIBILITY));
      }

   }

   public void sendBlockDamage(List<Player> players, Block block, int damage) {
      int id = block.getX() * 1024 * 1024 + block.getY() * 1024 * block.getZ();
      this.broadcast(players, new PacketPlayOutBlockBreakAnimation(id, new BlockPosition(block.getX(), block.getY(), block.getZ()), damage));
   }

   public void sendChunk(Chunk chunk) {
      PacketPlayOutMapChunk packet = new PacketPlayOutMapChunk(((CraftChunk)chunk).getHandle(), 65535);
      Iterator var3 = chunk.getWorld().getPlayers().iterator();

      while(var3.hasNext()) {
         Player p = (Player)var3.next();
         if (p.isOnline() && BukkitUtil.isLoadChunk(p, chunk)) {
            NMS.getSenderPacket().submit(() -> {
               ((CraftPlayer)p).getHandle().playerConnection.sendPacket(packet);
            });
         }
      }

      chunk.unload(true);
      chunk.load();
   }

   public void sendRelativeMove(List<Player> players, Entity entity, Location newLoc) {
      net.minecraft.server.v1_12_R1.Entity handle = ((CraftEntity)entity).getHandle();
      PacketPlayOutRelEntityMoveLook packet = new PacketPlayOutRelEntityMoveLook(entity.getEntityId(), (long)(newLoc.getX() * 32.0D - handle.locX * 32.0D) * 128L, (long)(newLoc.getY() * 32.0D - handle.locY * 32.0D) * 128L, (long)(newLoc.getZ() * 32.0D - handle.locZ * 32.0D) * 128L, (byte)((int)(newLoc.getYaw() * 256.0F / 360.0F)), (byte)((int)(newLoc.getPitch() * 256.0F / 360.0F)), entity.isOnGround());
      this.broadcast(players, packet);
   }

   public void sendTeleportEntityNotFlush(Collection<Player> players, Entity entity) {
      this.broadcastNotFlush(players, new PacketPlayOutEntityTeleport(((CraftEntity)entity).getHandle()));
   }

   private void broadcastNotFlush(Collection<Player> players, Packet packet) {
      NMS.getSenderPacket().submit(() -> {
         players.forEach((player) -> {
            ((CraftPlayer)player).getHandle().playerConnection.networkManager.channel.write(packet);
         });
      });
   }

   public void flush(Collection<Player> players) {
      NMS.getSenderPacket().submit(() -> {
         players.forEach((player) -> {
            ((CraftPlayer)player).getHandle().playerConnection.networkManager.channel.flush();
         });
      });
   }

   public void sendMount(Player player, Entity entity) {
      Packet packet = new PacketPlayOutMount(((CraftEntity)entity).getHandle());
      this.broadcast(Collections.singletonList(player), packet);
   }

   public void sendMount(Collection<Player> view, Entity entity) {
      Packet packet = new PacketPlayOutMount(((CraftEntity)entity).getHandle());
      this.broadcast(view, packet);
   }

   static {
      MyObject clazz = MyObject.wrap(EntityLiving.class);
      g = (DataWatcherObject)clazz.getField("g").getObject();
      h = (DataWatcherObject)clazz.getField("h").getObject();
   }
}
