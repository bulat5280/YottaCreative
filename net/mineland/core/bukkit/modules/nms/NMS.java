package net.mineland.core.bukkit.modules.nms;

import com.comphenix.protocol.events.PacketContainer;
import com.comphenix.protocol.wrappers.Vector3F;
import com.mojang.authlib.GameProfile;
import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Set;
import java.util.UUID;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.function.Predicate;
import java.util.stream.Collectors;
import net.md_5.bungee.api.chat.BaseComponent;
import net.md_5.bungee.chat.ComponentSerializer;
import net.minecraft.server.v1_12_R1.AttributeModifier;
import net.minecraft.server.v1_12_R1.BiomeBase;
import net.minecraft.server.v1_12_R1.Block;
import net.minecraft.server.v1_12_R1.BlockPosition;
import net.minecraft.server.v1_12_R1.ChatMessageType;
import net.minecraft.server.v1_12_R1.ChunkGenerator;
import net.minecraft.server.v1_12_R1.ChunkProviderGenerate;
import net.minecraft.server.v1_12_R1.ChunkSection;
import net.minecraft.server.v1_12_R1.DataWatcherObject;
import net.minecraft.server.v1_12_R1.Entity;
import net.minecraft.server.v1_12_R1.EntityAreaEffectCloud;
import net.minecraft.server.v1_12_R1.EntityArmorStand;
import net.minecraft.server.v1_12_R1.EntityBoat;
import net.minecraft.server.v1_12_R1.EntityDragonFireball;
import net.minecraft.server.v1_12_R1.EntityEgg;
import net.minecraft.server.v1_12_R1.EntityEnderCrystal;
import net.minecraft.server.v1_12_R1.EntityEnderPearl;
import net.minecraft.server.v1_12_R1.EntityEnderSignal;
import net.minecraft.server.v1_12_R1.EntityEvokerFangs;
import net.minecraft.server.v1_12_R1.EntityExperienceOrb;
import net.minecraft.server.v1_12_R1.EntityFallingBlock;
import net.minecraft.server.v1_12_R1.EntityFireball;
import net.minecraft.server.v1_12_R1.EntityFireworks;
import net.minecraft.server.v1_12_R1.EntityFishingHook;
import net.minecraft.server.v1_12_R1.EntityHuman;
import net.minecraft.server.v1_12_R1.EntityItem;
import net.minecraft.server.v1_12_R1.EntityItemFrame;
import net.minecraft.server.v1_12_R1.EntityLeash;
import net.minecraft.server.v1_12_R1.EntityLiving;
import net.minecraft.server.v1_12_R1.EntityLlamaSpit;
import net.minecraft.server.v1_12_R1.EntityMinecartAbstract;
import net.minecraft.server.v1_12_R1.EntityPainting;
import net.minecraft.server.v1_12_R1.EntityPlayer;
import net.minecraft.server.v1_12_R1.EntityPotion;
import net.minecraft.server.v1_12_R1.EntityShulkerBullet;
import net.minecraft.server.v1_12_R1.EntitySmallFireball;
import net.minecraft.server.v1_12_R1.EntitySnowball;
import net.minecraft.server.v1_12_R1.EntitySpectralArrow;
import net.minecraft.server.v1_12_R1.EntityTNTPrimed;
import net.minecraft.server.v1_12_R1.EntityThrownExpBottle;
import net.minecraft.server.v1_12_R1.EntityTippedArrow;
import net.minecraft.server.v1_12_R1.EntityWitherSkull;
import net.minecraft.server.v1_12_R1.EnumHand;
import net.minecraft.server.v1_12_R1.EnumItemSlot;
import net.minecraft.server.v1_12_R1.EnumParticle;
import net.minecraft.server.v1_12_R1.GenLayer;
import net.minecraft.server.v1_12_R1.IBlockData;
import net.minecraft.server.v1_12_R1.IChatBaseComponent;
import net.minecraft.server.v1_12_R1.IInventory;
import net.minecraft.server.v1_12_R1.IntCache;
import net.minecraft.server.v1_12_R1.Item;
import net.minecraft.server.v1_12_R1.LocaleI18n;
import net.minecraft.server.v1_12_R1.MathHelper;
import net.minecraft.server.v1_12_R1.MinecraftKey;
import net.minecraft.server.v1_12_R1.NBTBase;
import net.minecraft.server.v1_12_R1.NBTTagByte;
import net.minecraft.server.v1_12_R1.NBTTagByteArray;
import net.minecraft.server.v1_12_R1.NBTTagCompound;
import net.minecraft.server.v1_12_R1.NBTTagDouble;
import net.minecraft.server.v1_12_R1.NBTTagFloat;
import net.minecraft.server.v1_12_R1.NBTTagInt;
import net.minecraft.server.v1_12_R1.NBTTagIntArray;
import net.minecraft.server.v1_12_R1.NBTTagList;
import net.minecraft.server.v1_12_R1.NBTTagLong;
import net.minecraft.server.v1_12_R1.NBTTagShort;
import net.minecraft.server.v1_12_R1.NBTTagString;
import net.minecraft.server.v1_12_R1.NonNullList;
import net.minecraft.server.v1_12_R1.Packet;
import net.minecraft.server.v1_12_R1.PacketDataSerializer;
import net.minecraft.server.v1_12_R1.PacketPlayOutAnimation;
import net.minecraft.server.v1_12_R1.PacketPlayOutBed;
import net.minecraft.server.v1_12_R1.PacketPlayOutBlockChange;
import net.minecraft.server.v1_12_R1.PacketPlayOutChat;
import net.minecraft.server.v1_12_R1.PacketPlayOutCustomPayload;
import net.minecraft.server.v1_12_R1.PacketPlayOutEntityDestroy;
import net.minecraft.server.v1_12_R1.PacketPlayOutEntityEquipment;
import net.minecraft.server.v1_12_R1.PacketPlayOutEntityHeadRotation;
import net.minecraft.server.v1_12_R1.PacketPlayOutEntityMetadata;
import net.minecraft.server.v1_12_R1.PacketPlayOutEntityTeleport;
import net.minecraft.server.v1_12_R1.PacketPlayOutMount;
import net.minecraft.server.v1_12_R1.PacketPlayOutNamedSoundEffect;
import net.minecraft.server.v1_12_R1.PacketPlayOutScoreboardTeam;
import net.minecraft.server.v1_12_R1.PacketPlayOutSetSlot;
import net.minecraft.server.v1_12_R1.PacketPlayOutSpawnEntity;
import net.minecraft.server.v1_12_R1.PacketPlayOutSpawnEntityExperienceOrb;
import net.minecraft.server.v1_12_R1.PacketPlayOutSpawnEntityLiving;
import net.minecraft.server.v1_12_R1.PacketPlayOutSpawnEntityPainting;
import net.minecraft.server.v1_12_R1.PacketPlayOutTabComplete;
import net.minecraft.server.v1_12_R1.PacketPlayOutTitle;
import net.minecraft.server.v1_12_R1.PacketPlayOutWorldEvent;
import net.minecraft.server.v1_12_R1.PacketPlayOutWorldParticles;
import net.minecraft.server.v1_12_R1.PlayerChunkMap;
import net.minecraft.server.v1_12_R1.PlayerConnection;
import net.minecraft.server.v1_12_R1.PlayerInteractManager;
import net.minecraft.server.v1_12_R1.Scoreboard;
import net.minecraft.server.v1_12_R1.ScoreboardTeam;
import net.minecraft.server.v1_12_R1.SoundCategory;
import net.minecraft.server.v1_12_R1.TileEntity;
import net.minecraft.server.v1_12_R1.Vector3f;
import net.minecraft.server.v1_12_R1.WorldServer;
import net.minecraft.server.v1_12_R1.IChatBaseComponent.ChatSerializer;
import net.minecraft.server.v1_12_R1.PacketPlayOutEntity.PacketPlayOutEntityLook;
import net.minecraft.server.v1_12_R1.PacketPlayOutEntity.PacketPlayOutRelEntityMove;
import net.minecraft.server.v1_12_R1.PacketPlayOutEntity.PacketPlayOutRelEntityMoveLook;
import net.minecraft.server.v1_12_R1.PacketPlayOutTitle.EnumTitleAction;
import net.mineland.core.bukkit.modules.nms.nbt.JsonToNbt;
import net.mineland.core.bukkit.modules.user.PlayerAbilitiesContainer;
import net.mineland.core.bukkit.modules.user.User;
import org.bukkit.Bukkit;
import org.bukkit.Chunk;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.Particle;
import org.bukkit.Sound;
import org.bukkit.World;
import org.bukkit.block.Biome;
import org.bukkit.block.BlockState;
import org.bukkit.craftbukkit.v1_12_R1.CraftChunk;
import org.bukkit.craftbukkit.v1_12_R1.CraftServer;
import org.bukkit.craftbukkit.v1_12_R1.CraftSound;
import org.bukkit.craftbukkit.v1_12_R1.CraftWorld;
import org.bukkit.craftbukkit.v1_12_R1.block.CraftBlockState;
import org.bukkit.craftbukkit.v1_12_R1.entity.CraftEntity;
import org.bukkit.craftbukkit.v1_12_R1.entity.CraftItem;
import org.bukkit.craftbukkit.v1_12_R1.entity.CraftLivingEntity;
import org.bukkit.craftbukkit.v1_12_R1.entity.CraftPlayer;
import org.bukkit.craftbukkit.v1_12_R1.inventory.CraftInventoryCustom;
import org.bukkit.craftbukkit.v1_12_R1.inventory.CraftItemStack;
import org.bukkit.craftbukkit.v1_12_R1.inventory.CraftMetaBook;
import org.bukkit.craftbukkit.v1_12_R1.util.CraftChatMessage;
import org.bukkit.craftbukkit.v1_12_R1.util.CraftMagicNumbers;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.event.entity.CreatureSpawnEvent.SpawnReason;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.InventoryHolder;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.util.Consumer;
import ua.govnojon.libs.bukkitutil.chatcomponent.ChatComponentUtil;
import ua.govnojon.libs.myjava.MyObject;

public class NMS {
   private static ExecutorService senderPacket = Executors.newSingleThreadExecutor();
   private final DataWatcherObject<Byte> skinSkinParts;
   private Inventory inventory = Bukkit.createInventory((InventoryHolder)null, 9, "");
   private EnumParticle[] values = EnumParticle.values();
   private EnumItemSlot[] valuesSlots = EnumItemSlot.values();
   private Field fieldEntity_au;
   private Inventory iventory = Bukkit.createInventory((InventoryHolder)null, 9, "");

   public NMS() {
      try {
         this.fieldEntity_au = Entity.class.getDeclaredField("au");
         this.fieldEntity_au.setAccessible(true);
      } catch (Exception var2) {
         var2.printStackTrace();
      }

      this.skinSkinParts = (DataWatcherObject)(new MyObject(EntityPlayer.class)).getField("br").getObject();
   }

   public static ExecutorService getSenderPacket() {
      return senderPacket;
   }

   public static ModuleNMS getModuleNMS() {
      return ModuleNMS.getInstance();
   }

   public static NMS getManager() {
      return ModuleNMS.getNmsManager();
   }

   public static NMSSingle getManagerSingle() {
      return ModuleNMS.getNmsSingle();
   }

   public static NMSItemMap getManagerItemMap() {
      return ModuleNMS.getNmsItemMap();
   }

   public static NMSSend getManagerSend() {
      return ModuleNMS.getNmsSend();
   }

   public static String getMinecraftID(ItemStack stack) {
      int check = Item.getId(CraftItemStack.asNMSCopy(stack).getItem());
      MinecraftKey matching = (MinecraftKey)Item.REGISTRY.keySet().stream().filter((key) -> {
         return Item.getId((Item)Item.REGISTRY.get(key)) == check;
      }).findFirst().orElse((Object)null);
      return Objects.toString(matching, (String)null);
   }

   public static String serializeToJson(ItemStack itemStack) {
      NBTTagCompound nbtTagCompound = new NBTTagCompound();
      CraftItemStack.asNMSCopy(itemStack).save(nbtTagCompound);
      return nbtTagCompound.toString();
   }

   public static ItemStack deserializeFromJson(String string) {
      NBTTagCompound nbtTagCompound = JsonToNbt.getTagFromJson(string);
      net.minecraft.server.v1_12_R1.ItemStack itemStack = new net.minecraft.server.v1_12_R1.ItemStack(nbtTagCompound);
      return CraftItemStack.asBukkitCopy(itemStack);
   }

   public void clearAllChunksInWorldNoSaveWithPredicate(World world, Predicate<Chunk> chunkPredicate) {
      CraftWorld craftWorld = (CraftWorld)world;
      WorldServer handle = craftWorld.getHandle();
      Set<net.minecraft.server.v1_12_R1.Chunk> free = (Set)handle.getChunkProviderServer().chunks.values().stream().filter((chunk) -> {
         return chunkPredicate.test(chunk.bukkitChunk);
      }).peek(net.minecraft.server.v1_12_R1.Chunk::removeEntities).collect(Collectors.toSet());
      PlayerChunkMap playerChunkMap = handle.getPlayerChunkMap();
      List<EntityHuman> humanList = (List)handle.players.stream().filter((pl) -> {
         return free.contains(handle.getChunkAt((int)Math.floor(pl.locX) >> 4, (int)Math.floor(pl.locZ) >> 4));
      }).peek((pl) -> {
         playerChunkMap.removePlayer((EntityPlayer)pl);
      }).collect(Collectors.toList());
      free.forEach((chunk) -> {
         handle.getChunkProviderServer().unloadQueue.remove(chunk.chunkKey);
         handle.getChunkProviderServer().chunks.remove(chunk.chunkKey);
      });
      humanList.forEach((pl) -> {
         playerChunkMap.addPlayer((EntityPlayer)pl);
      });
   }

   public void setUser(Player p, User user) {
      EntityHuman eh = ((CraftPlayer)p).getHandle();
      MyObject.wrap((Object)eh).setField("abilities", new PlayerAbilitiesContainer(eh.abilities, user));
   }

   public User getUser(Player p) {
      if (p instanceof CraftPlayer) {
         EntityPlayer ep = ((CraftPlayer)p).getHandle();
         return ep.abilities instanceof PlayerAbilitiesContainer ? ((PlayerAbilitiesContainer)ep.abilities).getUser() : null;
      } else {
         return null;
      }
   }

   public void sendActionBar(Player player, String message) {
      senderPacket.submit(() -> {
         CraftPlayer p = (CraftPlayer)player;
         IChatBaseComponent cbc = ChatSerializer.a("{\"text\": \"" + message + "\"}");
         PacketPlayOutChat ppoc = new PacketPlayOutChat(cbc, ChatMessageType.GAME_INFO);
         p.getHandle().playerConnection.sendPacket(ppoc);
      });
   }

   /** @deprecated */
   @Deprecated
   public void sendTitle(Player player, String textTitle, String textSubtitle, int a, int b, int c) {
      if (textTitle == null) {
         textTitle = "";
      }

      if (textSubtitle == null) {
         textSubtitle = "";
      }

      senderPacket.submit(() -> {
         IChatBaseComponent chatTitle = ChatSerializer.a("{\"text\": \"" + textTitle + "\"}");
         IChatBaseComponent chatSubTitle = ChatSerializer.a("{\"text\": \"" + textSubtitle + "\"}");
         PacketPlayOutTitle title = new PacketPlayOutTitle(EnumTitleAction.TITLE, chatTitle);
         PacketPlayOutTitle subTitle = new PacketPlayOutTitle(EnumTitleAction.SUBTITLE, chatSubTitle);
         PacketPlayOutTitle length = new PacketPlayOutTitle(a, b, c);
         PlayerConnection connection = ((CraftPlayer)player).getHandle().playerConnection;
         connection.sendPacket(subTitle);
         connection.sendPacket(title);
         connection.sendPacket(length);
      });
   }

   public String getLocale(String key) {
      return LocaleI18n.get(key);
   }

   public void addEntityToWorld(org.bukkit.entity.Entity entity) {
      Entity handle = ((CraftEntity)entity).getHandle();
      handle.dead = false;
      ((CraftWorld)entity.getWorld()).addEntity(handle, SpawnReason.CUSTOM);
   }

   public void removeEntityFromWorld(org.bukkit.entity.Entity entity) {
      Entity handle = ((CraftEntity)entity).getHandle();
      ((CraftWorld)entity.getWorld()).getHandle().entityList.remove(handle);
   }

   public void addEntityToWorldList(org.bukkit.entity.Entity entity) {
      Entity handle = ((CraftEntity)entity).getHandle();
      ((CraftWorld)entity.getWorld()).getHandle().entityList.add(handle);
   }

   public void sendCustomPayload(Player p, String tag, byte[] bytes) {
      senderPacket.submit(() -> {
         ByteBuf buf = Unpooled.buffer();
         buf.writeBytes(bytes);
         ((CraftPlayer)p).getHandle().playerConnection.sendPacket(new PacketPlayOutCustomPayload(tag, new PacketDataSerializer(buf)));
      });
   }

   public boolean equalsSkullOwner(ItemStack item1, ItemStack item2) {
      net.minecraft.server.v1_12_R1.ItemStack itemStack1 = CraftItemStack.asNMSCopy(item1);
      net.minecraft.server.v1_12_R1.ItemStack itemStack2 = CraftItemStack.asNMSCopy(item2);
      NBTTagCompound tag1 = itemStack1.getTag();
      NBTTagCompound tag2 = itemStack2.getTag();
      if (tag1 != null && tag2 != null) {
         return Objects.equals(tag1.getCompound("SkullOwner"), tag2.getCompound("SkullOwner"));
      } else {
         throw new NullPointerException("tag1 or tag2 equals null");
      }
   }

   public ItemStack setItemAttribute(ItemStack stack, String name, double value, int slot) {
      net.minecraft.server.v1_12_R1.ItemStack item = CraftItemStack.asNMSCopy(stack);
      item.a(name, new AttributeModifier(name, value, (int)value), EnumItemSlot.values()[slot]);
      return CraftItemStack.asBukkitCopy(item);
   }

   public float getBlockDurability(Player p, Location location) {
      EntityPlayer ep = ((CraftPlayer)p).getHandle();
      BlockPosition pos = new BlockPosition(location.getX(), location.getY(), location.getZ());
      IBlockData ibd = ep.getWorld().getChunkAtWorldCoords(pos).getBlockData(pos);
      return 1.0F / ibd.getBlock().getDamage(ibd, ep, ep.getWorld(), pos);
   }

   public void breakBlockByPlayer(Player p, Location location) {
      EntityPlayer ep = ((CraftPlayer)p).getHandle();
      BlockPosition pos = new BlockPosition(location.getBlockX(), location.getBlockY(), location.getBlockZ());
      this.sendWorldEvent((Player)p, 2001, location.getBlockX(), location.getBlockY(), location.getBlockZ(), Block.getCombinedId(ep.getWorld().getType(pos)), false);
      ep.playerInteractManager.breakBlock(pos);
   }

   public boolean isEffectiveTool(ItemStack item, Location loc) {
      BlockPosition pos = new BlockPosition(loc.getX(), loc.getY(), loc.getZ());
      IBlockData ibd = ((CraftWorld)loc.getWorld()).getHandle().getChunkAtWorldCoords(pos).getBlockData(pos);
      net.minecraft.server.v1_12_R1.ItemStack is = CraftItemStack.asNMSCopy(item);
      return (double)is.getItem().getDestroySpeed(is, ibd) > 1.0D;
   }

   public List<String> getEffectiveToolBlocks(Material tool) {
      List<String> blocks = new LinkedList();
      net.minecraft.server.v1_12_R1.ItemStack is = CraftItemStack.asNMSCopy(new ItemStack(tool));
      Block.REGISTRY.keySet().forEach((key) -> {
         IBlockData blockData = ((Block)Block.REGISTRY.get(key)).getBlockData();
         if ((double)is.getItem().getDestroySpeed(is, blockData) > 1.0D) {
            blocks.add(key.getKey());
         }

      });
      return blocks;
   }

   public List<String> getVanillaBlockIds() {
      List<String> blocks = new LinkedList();
      Block.REGISTRY.keySet().forEach((key) -> {
         blocks.add(key.getKey());
      });
      return blocks;
   }

   public ItemStack addCanDestroyTags(ItemStack itemStack, List<String> blocks) {
      net.minecraft.server.v1_12_R1.ItemStack stack = CraftItemStack.asNMSCopy(itemStack);
      NBTTagCompound itemTags = stack.hasTag() ? stack.getTag() : new NBTTagCompound();
      NBTTagList canDestroy = new NBTTagList();
      blocks.forEach((block) -> {
         canDestroy.add(new NBTTagString(block));
      });
      itemTags.set("CanDestroy", canDestroy);
      itemTags.setInt("HideFlags", 24);
      stack.setTag(itemTags);
      return CraftItemStack.asBukkitCopy(stack);
   }

   public ItemStack addCanPlaceTags(ItemStack itemStack, List<String> blocks) {
      net.minecraft.server.v1_12_R1.ItemStack stack = CraftItemStack.asNMSCopy(itemStack);
      NBTTagCompound itemTags = stack.hasTag() ? stack.getTag() : new NBTTagCompound();
      NBTTagList canPlace = new NBTTagList();
      blocks.forEach((block) -> {
         canPlace.add(new NBTTagString(block));
      });
      itemTags.set("CanPlaceOn", canPlace);
      itemTags.setInt("HideFlags", 24);
      stack.setTag(itemTags);
      return CraftItemStack.asBukkitCopy(stack);
   }

   public int getBlockCombinedId(Player p, Location loc) {
      BlockPosition pos = new BlockPosition(loc.getBlockX(), loc.getBlockY(), loc.getBlockZ());
      return Block.getCombinedId(((CraftPlayer)p).getHandle().getWorld().getType(pos));
   }

   public void sendBlockBreakEffect(Player p, Collection<? extends Player> players, Location loc) {
      PacketPlayOutWorldEvent packet = new PacketPlayOutWorldEvent(2001, new BlockPosition(loc.getBlockX(), loc.getBlockY(), loc.getBlockZ()), this.getBlockCombinedId(p, loc), false);
      players.stream().filter((pl) -> {
         return pl.canSee(p) && !pl.equals(p);
      }).forEach((pl) -> {
         ((CraftPlayer)pl).getHandle().playerConnection.sendPacket(packet);
      });
   }

   public void sendEntityAnimation(Collection<Player> players, org.bukkit.entity.Entity entity, int animation) {
      Entity handleEntity = ((CraftEntity)entity).getHandle();
      PacketPlayOutAnimation packet = new PacketPlayOutAnimation(handleEntity, animation);
      this.broadcast((Collection)players, (Packet)packet);
   }

   public void sendPlayerUseBed(Collection<? extends Player> players, org.bukkit.entity.Entity entity, Location loc) {
      PacketPlayOutBed packet = new PacketPlayOutBed(((CraftPlayer)entity).getHandle(), new BlockPosition(loc.getX(), loc.getY(), loc.getZ()));
      this.broadcast((Collection)players, (Packet)packet);
   }

   public void sendTeleportEntity(Collection<Player> players, org.bukkit.entity.Entity entity) {
      PacketPlayOutEntityTeleport packet = new PacketPlayOutEntityTeleport(((CraftEntity)entity).getHandle());
      senderPacket.submit(() -> {
         players.forEach((player) -> {
            ((CraftPlayer)player).getHandle().playerConnection.sendPacket(packet);
         });
      });
   }

   public void sendRelativeMove(List<Player> players, org.bukkit.entity.Entity entity, Location newLoc) {
      Entity handle = ((CraftEntity)entity).getHandle();
      PacketPlayOutRelEntityMoveLook packet = new PacketPlayOutRelEntityMoveLook(entity.getEntityId(), (long)(newLoc.getX() * 32.0D - handle.locX * 32.0D) * 128L, (long)(newLoc.getY() * 32.0D - handle.locY * 32.0D) * 128L, (long)(newLoc.getZ() * 32.0D - handle.locZ * 32.0D) * 128L, (byte)((int)(newLoc.getYaw() * 256.0F / 360.0F)), (byte)((int)(newLoc.getPitch() * 256.0F / 360.0F)), entity.isOnGround());
      this.broadcast((Collection)players, (Packet)packet);
   }

   public void sendRelativeMove(Collection<? extends Player> players, org.bukkit.entity.Entity entity, long x, long y, long z) {
      Entity handle = ((CraftEntity)entity).getHandle();
      PacketPlayOutRelEntityMove packet = new PacketPlayOutRelEntityMove(entity.getEntityId(), x, y, z, false);
      this.broadcast((Collection)players, (Packet)packet);
   }

   public void sendDestroyEntity(Collection<Player> players, org.bukkit.entity.Entity entity) {
      PacketPlayOutEntityDestroy packet = new PacketPlayOutEntityDestroy(new int[]{entity.getEntityId()});
      this.broadcast((Collection)players, (Packet)packet);
   }

   public void sendDestroyEntity(Collection<Player> players, int... ids) {
      PacketPlayOutEntityDestroy packet = new PacketPlayOutEntityDestroy(ids);
      this.broadcast((Collection)players, (Packet)packet);
   }

   public void sendWorldEvent(Collection<Player> players, int eventId, int x, int y, int z, int data, boolean seeAbove) {
      PacketPlayOutWorldEvent packet = new PacketPlayOutWorldEvent(eventId, new BlockPosition(x, y, z), data, seeAbove);
      this.broadcast((Collection)players, (Packet)packet);
   }

   public void sendWorldEvent(Player player, int eventId, int x, int y, int z, int data, boolean seeAbove) {
      PacketPlayOutWorldEvent packet = new PacketPlayOutWorldEvent(eventId, new BlockPosition(x, y, z), data, seeAbove);
      senderPacket.submit(() -> {
         ((CraftPlayer)player).getHandle().playerConnection.sendPacket(packet);
      });
   }

   public void sendMetadataEntity(Collection<Player> players, org.bukkit.entity.Entity entity) {
      PacketPlayOutEntityMetadata packet = new PacketPlayOutEntityMetadata(entity.getEntityId(), ((CraftEntity)entity).getHandle().getDataWatcher(), true);
      senderPacket.submit(() -> {
         players.forEach((player) -> {
            ((CraftPlayer)player).getHandle().playerConnection.sendPacket(packet);
         });
      });
   }

   public org.bukkit.entity.Entity spawn(World world, Location spawn, EntityType type, SpawnReason reason) {
      return ((CraftWorld)world).spawn(spawn, type.getEntityClass(), (Consumer)null, reason);
   }

   public void setBlockSilent(Chunk chunk, int x, int y, int z, Material material, int data) {
      int i = x & 15;
      int j = z & 15;
      ChunkSection chunkSection = ((CraftChunk)chunk).getHandle().getSections()[y >> 4];
      chunkSection.setType(i, y & 15, j, Block.getById(material.getId()).fromLegacyData(data));
   }

   public void regenerateChunks(List<Chunk> chunks, final Biome biome, long seed) {
      if (!chunks.isEmpty()) {
         World world = ((Chunk)chunks.get(0)).getWorld();
         WorldServer nmsWorld = ((CraftWorld)world).getHandle();
         long currentSeed = nmsWorld.getSeed();
         GenLayer currentGenLayer1 = (GenLayer)(new MyObject(nmsWorld.getWorldChunkManager())).get("b");
         GenLayer currentGenLayer2 = (GenLayer)(new MyObject(nmsWorld.getWorldChunkManager())).get("c");
         ChunkGenerator currentGenerator = nmsWorld.getChunkProviderServer().chunkGenerator;
         GenLayer genLayer = new GenLayer(seed) {
            public int[] a(int areaX, int areaY, int areaWidth, int areaHeight) {
               int[] biomes = IntCache.a(areaWidth * areaHeight);
               Arrays.fill(biomes, biome.ordinal());
               return biomes;
            }
         };
         ChunkProviderGenerate generator = new ChunkProviderGenerate(nmsWorld, seed, false, "");
         (new MyObject(generator)).set("D", new BiomeBase[]{BiomeBase.getBiome(biome.ordinal())});
         (new MyObject(nmsWorld.getWorldChunkManager())).set("b", genLayer);
         (new MyObject(nmsWorld.getWorldChunkManager())).set("c", genLayer);
         (new MyObject(nmsWorld.getChunkProviderServer())).set("chunkGenerator", generator);
         (new MyObject(nmsWorld.worldData)).set("e", seed);
         chunks.forEach((chunk) -> {
            world.regenerateChunk(chunk.getX(), chunk.getZ());
         });
         (new MyObject(nmsWorld.getWorldChunkManager())).set("b", currentGenLayer1);
         (new MyObject(nmsWorld.getWorldChunkManager())).set("c", currentGenLayer2);
         (new MyObject(nmsWorld.getChunkProviderServer())).set("chunkGenerator", currentGenerator);
         (new MyObject(nmsWorld.worldData)).set("e", currentSeed);
      }
   }

   public void clearAllChunksInWorldNoSave(World world) {
      Bukkit.getLogger().warning("Удаление всех загруженных чанков мира '" + world.getName() + "'.");
      CraftWorld craftWorld = (CraftWorld)world;
      WorldServer handle = craftWorld.getHandle();
      handle.getChunkProviderServer().chunks.values().forEach(net.minecraft.server.v1_12_R1.Chunk::removeEntities);
      PlayerChunkMap playerChunkMap = handle.getPlayerChunkMap();
      handle.players.forEach((pl) -> {
         playerChunkMap.removePlayer((EntityPlayer)pl);
      });
      handle.getChunkProviderServer().unloadQueue.clear();
      handle.getChunkProviderServer().chunks.clear();
      handle.players.forEach((pl) -> {
         playerChunkMap.addPlayer((EntityPlayer)pl);
      });
   }

   public boolean isItem(ItemStack itemStack) {
      return itemStack instanceof CraftItemStack;
   }

   public boolean hasInventoryIcon(ItemStack itemStack) {
      if (!this.isItem(itemStack)) {
         this.iventory.setItem(0, itemStack);
         itemStack = this.iventory.getItem(0);
         return itemStack != null;
      } else {
         return true;
      }
   }

   public void sendBlockChange(Collection<? extends Player> players, Location loc, Material material, byte data) {
      players.forEach((pl) -> {
         this.sendBlockChange(pl, loc, material, data);
      });
   }

   public void sendBlockChange(Player player, Location loc, Material material, byte data) {
      this.sendBlockChange(player, loc, material.getId(), data);
   }

   public void sendBlockChange(Player player, Location loc, int material, byte data) {
      if (((CraftPlayer)player).getHandle().playerConnection != null) {
         PacketPlayOutBlockChange packet = new PacketPlayOutBlockChange(((CraftWorld)loc.getWorld()).getHandle(), new BlockPosition(loc.getBlockX(), loc.getBlockY(), loc.getBlockZ()));
         packet.block = CraftMagicNumbers.getBlock(material).fromLegacyData(data);
         senderPacket.submit(() -> {
            ((CraftPlayer)player).getHandle().playerConnection.sendPacket(packet);
         });
      }

   }

   /** @deprecated */
   @Deprecated
   public ItemStack fixItem(ItemStack item) {
      if (item == null) {
         return null;
      } else {
         if (!this.isItem(item)) {
            this.inventory.setItem(0, item);
            item = this.inventory.getItem(0);
         }

         return item;
      }
   }

   public boolean equals(ItemStack item1, ItemStack item2) {
      if (item1.getTypeId() != item2.getTypeId()) {
         return false;
      } else {
         item1 = this.fixItem(item1);
         item2 = this.fixItem(item2);
         if (item1.getDurability() != item2.getDurability()) {
            return false;
         } else {
            net.minecraft.server.v1_12_R1.ItemStack itemStack1 = CraftItemStack.asNMSCopy(item1);
            net.minecraft.server.v1_12_R1.ItemStack itemStack2 = CraftItemStack.asNMSCopy(item2);
            return Objects.equals(itemStack1.getTag(), itemStack2.getTag());
         }
      }
   }

   public void sendParticle(Collection<Player> players, Particle particle, boolean longDistance, double x, double y, double z, int count, double offsetX, double offsetY, double offsetZ, double speed, int... parameters) {
      PacketPlayOutWorldParticles packet = new PacketPlayOutWorldParticles(this.values[particle.ordinal()], longDistance, (float)x, (float)y, (float)z, (float)offsetX, (float)offsetY, (float)offsetZ, (float)speed, count, parameters);
      this.broadcast((Collection)players, (Packet)packet);
   }

   public String getNameItem(ItemStack item) {
      net.minecraft.server.v1_12_R1.ItemStack stack = CraftItemStack.asNMSCopy(item);
      return stack.getItem().b(stack);
   }

   public String getNameEnchantment(Enchantment enchantment) {
      net.minecraft.server.v1_12_R1.Enchantment nmsEnvhant = (net.minecraft.server.v1_12_R1.Enchantment)net.minecraft.server.v1_12_R1.Enchantment.enchantments.getId(enchantment.getId());
      return nmsEnvhant != null ? LocaleI18n.get(nmsEnvhant.a()) : enchantment.getName();
   }

   public Object getHandle(org.bukkit.entity.Entity entity) {
      return ((CraftEntity)entity).getHandle();
   }

   public void setLocation(org.bukkit.entity.Entity entity, Location l) {
      Entity handle = ((CraftEntity)entity).getHandle();
      handle.setLocation(l.getX(), l.getY(), l.getZ(), l.getYaw(), l.getPitch());
   }

   public void setLocation(org.bukkit.entity.Entity entity, double locX, double locY, double locZ, float yaw, float pitch) {
      Entity handle = ((CraftEntity)entity).getHandle();
      handle.setLocation(locX, locY, locZ, yaw, pitch);
   }

   public void setLocation(org.bukkit.entity.Entity entity, double locX, double locY, double locZ) {
      Entity handle = ((CraftEntity)entity).getHandle();
      handle.setLocation(locX, locY, locZ, handle.yaw, handle.pitch);
   }

   public boolean hasTags(ItemStack item) {
      NBTTagCompound compound = CraftItemStack.asNMSCopy(this.fixItem(item)).getTag();
      return compound != null && compound.d() != 0;
   }

   public Map<String, Object> getTags(ItemStack item) {
      NBTTagCompound compound = CraftItemStack.asNMSCopy(this.fixItem(item)).getTag();
      return (Map)(compound != null ? (Map)this.getInNbt(compound) : new HashMap());
   }

   public Map<String, Object> getTags(BlockState state) {
      CraftBlockState blockState = (CraftBlockState)state;
      TileEntity tileEntity = ((CraftWorld)blockState.getWorld()).getTileEntityAt(blockState.getX(), blockState.getY(), blockState.getZ());
      if (tileEntity == null) {
         return null;
      } else {
         NBTTagCompound compound = tileEntity.d();
         return (Map)(compound != null ? (Map)this.getInNbt(compound) : new HashMap());
      }
   }

   private Object getInNbt(NBTBase nbt) {
      if (nbt instanceof NBTTagByte) {
         return ((NBTTagByte)nbt).g();
      } else if (nbt instanceof NBTTagByteArray) {
         return ((NBTTagByteArray)nbt).c();
      } else if (nbt instanceof NBTTagCompound) {
         NBTTagCompound compound = (NBTTagCompound)nbt;
         HashMap<String, Object> hashMap = new HashMap();
         compound.c().forEach((key) -> {
            hashMap.put(key, this.getInNbt(compound.get(key)));
         });
         return hashMap;
      } else if (nbt instanceof NBTTagDouble) {
         return ((NBTTagDouble)nbt).asDouble();
      } else if (nbt instanceof NBTTagFloat) {
         return ((NBTTagFloat)nbt).i();
      } else if (nbt instanceof NBTTagInt) {
         return ((NBTTagInt)nbt).e();
      } else if (nbt instanceof NBTTagIntArray) {
         return ((NBTTagIntArray)nbt).d();
      } else if (!(nbt instanceof NBTTagList)) {
         if (nbt instanceof NBTTagLong) {
            return ((NBTTagLong)nbt).d();
         } else if (nbt instanceof NBTTagShort) {
            return ((NBTTagShort)nbt).f();
         } else {
            return nbt instanceof NBTTagString ? ((NBTTagString)nbt).c_() : String.valueOf(nbt);
         }
      } else {
         NBTTagList nbtTagList = (NBTTagList)nbt;
         ArrayList<Object> list = new ArrayList(nbtTagList.size());

         for(int i = 0; i < nbtTagList.size(); ++i) {
            list.add(this.getInNbt(nbtTagList.i(i)));
         }

         return list;
      }
   }

   public void sendTeleportEntity(Player player, org.bukkit.entity.Entity entity) {
      this.sendTeleportEntity((Collection)Collections.singletonList(player), entity);
   }

   public void sendDestroyEntity(Player player, org.bukkit.entity.Entity entity) {
      this.sendDestroyEntity((Collection)Collections.singletonList(player), (org.bukkit.entity.Entity)entity);
   }

   public void sendMetadataEntity(Player player, org.bukkit.entity.Entity entity) {
      this.sendMetadataEntity((Collection)Collections.singletonList(player), entity);
   }

   public void sendParticle(Player player, Particle particle, boolean b, double x, double y, double z, int count, double offsetX, double offsetY, double offsetZ, double speed, int... parameters) {
      this.sendParticle((Collection)Collections.singletonList(player), particle, b, x, y, z, count, offsetX, offsetY, offsetZ, speed, parameters);
   }

   public void setInventorySize(Inventory inventory, int newSize) {
      try {
         IInventory iInventory = ((CraftInventoryCustom)inventory).getInventory();
         Field field = iInventory.getClass().getDeclaredField("items");
         field.setAccessible(true);
         field.set(iInventory, NonNullList.a(newSize, net.minecraft.server.v1_12_R1.ItemStack.a));
         field.setAccessible(false);
      } catch (Exception var5) {
         var5.printStackTrace();
      }

   }

   public void setInventoryTitle(Inventory inventory, String newTitle) {
      try {
         IInventory iInventory = ((CraftInventoryCustom)inventory).getInventory();
         Field field = iInventory.getClass().getDeclaredField("title");
         field.setAccessible(true);
         field.set(iInventory, newTitle);
         field.setAccessible(false);
      } catch (Exception var5) {
         var5.printStackTrace();
      }

   }

   public void sendDestroyEntity(Player player, List<Integer> ids) {
      PacketPlayOutEntityDestroy packet = new PacketPlayOutEntityDestroy(ids.stream().mapToInt((i) -> {
         return i;
      }).toArray());
      senderPacket.submit(() -> {
         ((CraftPlayer)player).getHandle().playerConnection.sendPacket(packet);
      });
   }

   public <T> T getHandleEntity(org.bukkit.entity.Entity entity, Class<T> c) {
      return ((CraftEntity)entity).getHandle();
   }

   public void sendDestroyEntity(Collection<Player> view, List<Integer> ids) {
      PacketPlayOutEntityDestroy packet = new PacketPlayOutEntityDestroy(ids.stream().mapToInt((i) -> {
         return i;
      }).toArray());
      this.broadcast((Collection)view, (Packet)packet);
   }

   public void sendEquipmentEntity(Player player, LivingEntity entity) {
      int id = entity.getEntityId();
      EntityLiving entityLiving = ((CraftLivingEntity)entity).getHandle();
      PlayerConnection connection = ((CraftPlayer)player).getHandle().playerConnection;
      senderPacket.submit(() -> {
         Arrays.stream(this.valuesSlots).forEach((itemSlot) -> {
            net.minecraft.server.v1_12_R1.ItemStack itemStack = entityLiving.getEquipment(itemSlot);
            connection.sendPacket(new PacketPlayOutEntityEquipment(id, itemSlot, itemStack));
         });
      });
   }

   public void sendEquipmentEntity(Collection<Player> players, LivingEntity entity) {
      int id = entity.getEntityId();
      EntityLiving entityLiving = ((CraftLivingEntity)entity).getHandle();
      EnumItemSlot[] var5 = this.valuesSlots;
      int var6 = var5.length;

      for(int var7 = 0; var7 < var6; ++var7) {
         EnumItemSlot itemSlot = var5[var7];
         net.minecraft.server.v1_12_R1.ItemStack itemStack = entityLiving.getEquipment(itemSlot);
         Packet<?> packet = new PacketPlayOutEntityEquipment(id, itemSlot, itemStack);
         this.broadcast((Collection)players, (Packet)packet);
      }

   }

   public void setItemEternal(org.bukkit.entity.Item item) {
      item.setPickupDelay(32767);
      EntityItem entityItem = (EntityItem)((CraftItem)item).getHandle();
      MyObject.wrap((Object)entityItem).setField("age", -32768);
   }

   public Object asNMSCopy(ItemStack item) {
      return CraftItemStack.asNMSCopy(this.fixItem(item));
   }

   public void sendLookEntity(Player player, org.bukkit.entity.Entity entity) {
      this.sendLookEntity((Collection)Collections.singletonList(player), entity);
   }

   public void sendLookEntity(Collection<Player> players, org.bukkit.entity.Entity entity) {
      Entity handle = ((CraftEntity)entity).getHandle();
      int j1 = MathHelper.d(handle.yaw * 256.0F / 360.0F);
      int k1 = MathHelper.d(handle.pitch * 256.0F / 360.0F);
      Packet<?> packet = new PacketPlayOutEntityLook(handle.getId(), (byte)j1, (byte)k1, handle.onGround);
      this.broadcast((Collection)players, (Packet)packet);
   }

   public void sendHeadRotationEntity(Player player, org.bukkit.entity.Entity entity) {
      this.sendHeadRotationEntity((Collection)Collections.singletonList(player), entity);
   }

   public void sendHeadRotationEntity(Collection<Player> players, org.bukkit.entity.Entity entity) {
      Entity handle = ((CraftEntity)entity).getHandle();
      Packet<?> packet = new PacketPlayOutEntityHeadRotation(handle, (byte)((int)(handle.yaw * 256.0F / 360.0F)));
      this.broadcast((Collection)players, (Packet)packet);
   }

   public void setEntityPositionRotation(org.bukkit.entity.Entity entity, Location location) {
      Entity handle = ((CraftEntity)entity).getHandle();
      handle.setPositionRotation(location.getX(), location.getY(), location.getZ(), location.getYaw(), location.getPitch());
   }

   private Packet createPacketSpawnBy(Entity entity) {
      if (entity instanceof EntityLiving) {
         return new PacketPlayOutSpawnEntityLiving((EntityLiving)entity);
      } else if (entity instanceof EntityPainting) {
         return new PacketPlayOutSpawnEntityPainting((EntityPainting)entity);
      } else if (entity instanceof EntityItem) {
         return new PacketPlayOutSpawnEntity(entity, 2, 1);
      } else if (entity instanceof EntityMinecartAbstract) {
         EntityMinecartAbstract entityminecartabstract = (EntityMinecartAbstract)entity;
         return new PacketPlayOutSpawnEntity(entity, 10, entityminecartabstract.v().a());
      } else if (entity instanceof EntityBoat) {
         return new PacketPlayOutSpawnEntity(entity, 1);
      } else if (entity instanceof EntityExperienceOrb) {
         return new PacketPlayOutSpawnEntityExperienceOrb((EntityExperienceOrb)entity);
      } else if (entity instanceof EntityFishingHook) {
         EntityHuman entityhuman = ((EntityFishingHook)entity).l();
         return new PacketPlayOutSpawnEntity(entity, 90, entityhuman == null ? entity.getId() : entityhuman.getId());
      } else if (entity instanceof EntitySpectralArrow) {
         return new PacketPlayOutSpawnEntity(entity, 91, 1 + (entity == null ? entity.getId() : entity.getId()));
      } else if (entity instanceof EntityTippedArrow) {
         return new PacketPlayOutSpawnEntity(entity, 60, 1 + (entity == null ? entity.getId() : entity.getId()));
      } else if (entity instanceof EntitySnowball) {
         return new PacketPlayOutSpawnEntity(entity, 61);
      } else if (entity instanceof EntityLlamaSpit) {
         return new PacketPlayOutSpawnEntity(entity, 68);
      } else if (entity instanceof EntityPotion) {
         return new PacketPlayOutSpawnEntity(entity, 73);
      } else if (entity instanceof EntityThrownExpBottle) {
         return new PacketPlayOutSpawnEntity(entity, 75);
      } else if (entity instanceof EntityEnderPearl) {
         return new PacketPlayOutSpawnEntity(entity, 65);
      } else if (entity instanceof EntityEnderSignal) {
         return new PacketPlayOutSpawnEntity(entity, 72);
      } else if (entity instanceof EntityFireworks) {
         return new PacketPlayOutSpawnEntity(entity, 76);
      } else if (entity instanceof EntityFireball) {
         EntityFireball entityfireball = (EntityFireball)entity;
         PacketPlayOutSpawnEntity packetplayoutspawnentity = null;
         byte b0 = 63;
         if (entity instanceof EntitySmallFireball) {
            b0 = 64;
         } else if (entity instanceof EntityDragonFireball) {
            b0 = 93;
         } else if (entity instanceof EntityWitherSkull) {
            b0 = 66;
         }

         if (entityfireball.shooter != null) {
            packetplayoutspawnentity = new PacketPlayOutSpawnEntity(entity, b0, ((EntityFireball)entity).shooter.getId());
         } else {
            packetplayoutspawnentity = new PacketPlayOutSpawnEntity(entity, b0, 0);
         }

         packetplayoutspawnentity.a((int)(entityfireball.dirX * 8000.0D));
         packetplayoutspawnentity.b((int)(entityfireball.dirY * 8000.0D));
         packetplayoutspawnentity.c((int)(entityfireball.dirZ * 8000.0D));
         return packetplayoutspawnentity;
      } else if (entity instanceof EntityShulkerBullet) {
         PacketPlayOutSpawnEntity packetplayoutspawnentity1 = new PacketPlayOutSpawnEntity(entity, 67, 0);
         packetplayoutspawnentity1.a((int)(entity.motX * 8000.0D));
         packetplayoutspawnentity1.b((int)(entity.motY * 8000.0D));
         packetplayoutspawnentity1.c((int)(entity.motZ * 8000.0D));
         return packetplayoutspawnentity1;
      } else if (entity instanceof EntityEgg) {
         return new PacketPlayOutSpawnEntity(entity, 62);
      } else if (entity instanceof EntityEvokerFangs) {
         return new PacketPlayOutSpawnEntity(entity, 79);
      } else if (entity instanceof EntityTNTPrimed) {
         return new PacketPlayOutSpawnEntity(entity, 50);
      } else if (entity instanceof EntityEnderCrystal) {
         return new PacketPlayOutSpawnEntity(entity, 51);
      } else if (entity instanceof EntityFallingBlock) {
         EntityFallingBlock entityfallingblock = (EntityFallingBlock)entity;
         return new PacketPlayOutSpawnEntity(entity, 70, Block.getCombinedId(entityfallingblock.getBlock()));
      } else if (entity instanceof EntityArmorStand) {
         return new PacketPlayOutSpawnEntity(entity, 78);
      } else if (entity instanceof EntityItemFrame) {
         EntityItemFrame entityitemframe = (EntityItemFrame)entity;
         return new PacketPlayOutSpawnEntity(entity, 71, entityitemframe.direction.get2DRotationValue(), entityitemframe.getBlockPosition());
      } else if (entity instanceof EntityLeash) {
         EntityLeash entityleash = (EntityLeash)entity;
         return new PacketPlayOutSpawnEntity(entity, 77, 0, entityleash.getBlockPosition());
      } else if (entity instanceof EntityAreaEffectCloud) {
         return new PacketPlayOutSpawnEntity(entity, 3);
      } else {
         throw new IllegalArgumentException("Don't know how to load " + entity.getClass() + "!");
      }
   }

   public org.bukkit.entity.Entity createEntity(Location loc, EntityType type) {
      CraftWorld craftWorld = (CraftWorld)loc.getWorld();
      Object entity;
      if (type.equals(EntityType.PLAYER)) {
         entity = new EntityPlayer(((CraftServer)Bukkit.getServer()).getServer(), craftWorld.getHandle(), new GameProfile(UUID.randomUUID(), "NPC"), new PlayerInteractManager(craftWorld.getHandle()));
         ((Entity)entity).getDataWatcher().set(this.skinSkinParts, (byte)127);
      } else if (type.equals(EntityType.DROPPED_ITEM)) {
         entity = new EntityItem(((CraftWorld)loc.getWorld()).getHandle(), loc.getX(), loc.getY(), loc.getZ(), CraftItemStack.asNMSCopy(new ItemStack(Material.STONE)));
         ((EntityItem)entity).pickupDelay = 5;
      } else {
         entity = ((CraftWorld)loc.getWorld()).createEntity(loc, type.getEntityClass());
      }

      return entity == null ? null : ((Entity)entity).getBukkitEntity();
   }

   public void addPassenger(org.bukkit.entity.Entity entity, org.bukkit.entity.Entity passenger) {
      Entity entityPass = ((CraftEntity)passenger).getHandle();
      Entity entityEntity = ((CraftEntity)entity).getHandle();

      try {
         this.fieldEntity_au.set(entityPass, entityEntity);
         entityEntity.passengers.add(entityPass);
      } catch (Exception var6) {
         var6.printStackTrace();
      }

   }

   public void setEntityId(org.bukkit.entity.Entity e, int id) {
      Entity entity = ((CraftEntity)e).getHandle();
      MyObject.wrap((Object)entity).setField("id", id);
   }

   public boolean getIsFallingDust(PacketContainer packet) {
      return ((EnumParticle)packet.getSpecificModifier(EnumParticle.class).read(0)).equals(EnumParticle.FALLING_DUST);
   }

   public void sendMount(Collection<Player> view, org.bukkit.entity.Entity entity) {
      Packet packet = new PacketPlayOutMount(((CraftEntity)entity).getHandle());
      this.broadcast((Collection)view, (Packet)packet);
   }

   public void broadcast(Collection<? extends Player> players, Object packet) {
      this.broadcast(players, (Packet)packet);
   }

   public void broadcast(Collection<? extends Player> players, Packet packet) {
      senderPacket.submit(() -> {
         players.forEach((player) -> {
            ((CraftPlayer)player).getHandle().playerConnection.sendPacket(packet);
         });
      });
   }

   public void broadcast(Player player, Object packet) {
      this.broadcast(player, (Packet)packet);
   }

   public void broadcast(Player player, Packet packet) {
      senderPacket.submit(() -> {
         ((CraftPlayer)player).getHandle().playerConnection.sendPacket(packet);
      });
   }

   public void broadcast(Collection<? extends Player> players, Packet... packets) {
      senderPacket.submit(() -> {
         players.forEach((player) -> {
            PlayerConnection playerConnection = ((CraftPlayer)player).getHandle().playerConnection;
            Packet[] var3 = packets;
            int var4 = packets.length;

            for(int var5 = 0; var5 < var4; ++var5) {
               Packet packet = var3[var5];
               playerConnection.sendPacket(packet);
            }

         });
      });
   }

   public void broadcast(Player player, Packet... packets) {
      senderPacket.submit(() -> {
         PlayerConnection playerConnection = ((CraftPlayer)player).getHandle().playerConnection;
         Packet[] var3 = packets;
         int var4 = packets.length;

         for(int var5 = 0; var5 < var4; ++var5) {
            Packet packet = var3[var5];
            playerConnection.sendPacket(packet);
         }

      });
   }

   public void sendSound(Collection<Player> view, Sound sound, double x, double y, double z, float volume, float pitch) {
      Packet packet = new PacketPlayOutNamedSoundEffect(CraftSound.getSoundEffect(CraftSound.getSound(sound)), SoundCategory.MASTER, x, y, z, volume, pitch);
      this.broadcast((Collection)view, (Packet)packet);
   }

   public void sendSound(Collection<Player> view, Sound sound, org.bukkit.SoundCategory soundCategory, double x, double y, double z, float volume, float pitch) {
      Packet packet = new PacketPlayOutNamedSoundEffect(CraftSound.getSoundEffect(CraftSound.getSound(sound)), SoundCategory.MASTER, x, y, z, volume, pitch);
      this.broadcast((Collection)view, (Packet)packet);
   }

   public void setPlayerListNameParam(Player player, String name) {
      ((CraftPlayer)player).getHandle().listName = CraftChatMessage.fromString(name)[0];
   }

   public void sendTabComplete(Player player, String[] args) {
      senderPacket.submit(() -> {
         PacketPlayOutTabComplete packet = new PacketPlayOutTabComplete(args);
         ((CraftPlayer)player).getHandle().playerConnection.sendPacket(packet);
      });
   }

   public void setMot(org.bukkit.entity.Entity hooked, double x, double y, double z) {
      Entity handle = ((CraftEntity)hooked).getHandle();
      handle.motX = x;
      handle.motY = y;
      handle.motZ = z;
   }

   public ItemStack setPages(ItemStack item, BaseComponent component) {
      return this.setPages(item, Collections.singletonList(component));
   }

   public ItemStack setPages(ItemStack item, List<BaseComponent> components) {
      if (!item.getType().equals(Material.WRITTEN_BOOK)) {
         throw new IllegalArgumentException("item.getType() != Material.WRITTEN_BOOK");
      } else {
         components.forEach(ChatComponentUtil::fixComponent);
         item = this.fixItem(item);
         ItemMeta meta = item.getItemMeta();
         CraftMetaBook craft = (CraftMetaBook)meta;
         craft.pages = (List)components.stream().map(ComponentSerializer::toString).map(ChatSerializer::a).collect(Collectors.toList());
         item.setItemMeta(meta);
         return item;
      }
   }

   public void openBook(Player player, ItemStack item) {
      if (!item.getType().equals(Material.WRITTEN_BOOK)) {
         throw new IllegalArgumentException("item.getType() != Material.WRITTEN_BOOK");
      } else {
         senderPacket.submit(() -> {
            player.closeInventory();
            ItemStack mainHand = player.getInventory().getItemInMainHand();
            int held = 36 + player.getInventory().getHeldItemSlot();
            PlayerConnection playerConnection = ((CraftPlayer)player).getHandle().playerConnection;
            playerConnection.sendPacket(new PacketPlayOutSetSlot(0, held, CraftItemStack.asNMSCopy(item)));
            PacketDataSerializer packetdataserializer = new PacketDataSerializer(Unpooled.buffer());
            packetdataserializer.a(EnumHand.MAIN_HAND);
            playerConnection.sendPacket(new PacketPlayOutCustomPayload("MC|BOpen", packetdataserializer));
            playerConnection.sendPacket(new PacketPlayOutSetSlot(0, held, CraftItemStack.asNMSCopy(mainHand)));
         });
      }
   }

   public Object fixAndToNMS(BaseComponent[] components) {
      IChatBaseComponent component = ChatSerializer.a(ComponentSerializer.toString(components));

      assert component != null;

      component = CraftChatMessage.fixComponent(component);
      return component;
   }

   public Class<Object> getClassIChatBaseComponent() {
      return IChatBaseComponent.class;
   }

   public boolean hasTag(ItemStack itemStack, String key) {
      NBTTagCompound compound = CraftItemStack.asNMSCopy(this.fixItem(itemStack)).getTag();
      return compound != null && compound.hasKey(key);
   }

   public void resetTeam(Collection<Player> players, String name) {
      PacketPlayOutScoreboardTeam packet = new PacketPlayOutScoreboardTeam(new ScoreboardTeam((Scoreboard)null, name), 1);
      this.broadcast((Collection)players, (Packet)packet);
   }

   public void setInvisible(org.bukkit.entity.Entity entity, boolean enable) {
      ((CraftEntity)entity).getHandle().setInvisible(enable);
   }

   public void setVelocity(org.bukkit.entity.Entity entity, double motX, double motY, double motZ) {
      Entity handle = ((CraftEntity)entity).getHandle();
      handle.motX = motX;
      handle.motY = motY;
      handle.motZ = motZ;
   }

   public List<Player> getPlayersInRadius(World worldBukkit, double locX, double locZ, double maxDistance) {
      WorldServer world = ((CraftWorld)worldBukkit).getHandle();
      double minX = locX - maxDistance;
      double minZ = locZ - maxDistance;
      double maxX = locX + maxDistance;
      double maxZ = locZ + maxDistance;
      return (List)world.players.stream().filter((human) -> {
         return human.locX > minX && human.locX < maxX && human.locZ > minZ && human.locZ < maxZ;
      }).map((entityHuman) -> {
         return (Player)entityHuman.getBukkitEntity();
      }).collect(Collectors.toList());
   }

   public Vector3f createVector3D(Vector3F vector3F) {
      return new Vector3f(vector3F.getX(), vector3F.getY(), vector3F.getZ());
   }

   public int getTick() {
      return ((CraftServer)Bukkit.getServer()).getServer().aq();
   }
}
