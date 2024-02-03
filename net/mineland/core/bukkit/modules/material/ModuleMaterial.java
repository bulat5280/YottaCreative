package net.mineland.core.bukkit.modules.material;

import com.google.common.collect.Lists;
import java.util.Arrays;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import net.mineland.core.bukkit.module.BukkitModule;
import net.mineland.core.bukkit.modules.nms.NMS;
import org.bukkit.Material;
import org.bukkit.entity.AreaEffectCloud;
import org.bukkit.entity.ThrownPotion;
import org.bukkit.material.Stairs;
import org.bukkit.plugin.Plugin;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;
import ua.govnojon.libs.config.Config;

public class ModuleMaterial extends BukkitModule {
   private List<Material> allowStand;
   private List<Material> passable;
   private List<Material> protectedBlocks;
   private List<Material> usableBlocks;
   private List<Material> boots;
   private List<Material> chestplate;
   private List<Material> helmets;
   private List<Material> leggings;
   private List<Material> potions;
   private List<Integer> negativePotions = Arrays.asList(2, 4, 7, 9, 15, 17, 18, 19, 20, 27);
   private Map<Material, Float> strengths = new HashMap();
   private List<Material> handed;

   public ModuleMaterial(int priority, Plugin plugin) {
      super("material", priority, plugin, (Config)null);
   }

   public void onEnable() {
      this.allowStand = Lists.newArrayList();
      this.passable = Lists.newArrayList();
      this.helmets = Lists.newArrayList();
      this.chestplate = Lists.newArrayList();
      this.leggings = Lists.newArrayList();
      this.boots = Lists.newArrayList();
      this.potions = Lists.newArrayList();
      Arrays.stream(Material.values()).forEach((m) -> {
         if (m.isBlock()) {
            if (m.isSolid() && !m.isTransparent() && !this.canBlockDealDamage(m)) {
               this.allowStand.add(m);
            } else if (!m.hasGravity() && !m.isOccluding()) {
               this.passable.add(m);
            }

            this.strengths.put(m, NMS.getManagerSingle().getStrength(m));
         } else if (this.isHelmet(m)) {
            this.helmets.add(m);
         } else if (this.isChestplate(m)) {
            this.chestplate.add(m);
         } else if (this.isLeggings(m)) {
            this.leggings.add(m);
         } else if (this.isBoots(m)) {
            this.boots.add(m);
         } else if (this.isPotion(m)) {
            this.potions.add(m);
         }

      });
      this.protectedBlocks = Arrays.asList(Material.CHEST, Material.FURNACE, Material.BURNING_FURNACE, Material.JUKEBOX, Material.ENDER_PORTAL_FRAME, Material.FLOWER_POT, Material.DISPENSER, Material.TRAPPED_CHEST, Material.HOPPER, Material.DROPPER, Material.BEACON, Material.BREWING_STAND, Material.CAULDRON, Material.TNT, Material.FIRE, Material.WHITE_SHULKER_BOX, Material.ORANGE_SHULKER_BOX, Material.MAGENTA_SHULKER_BOX, Material.LIGHT_BLUE_SHULKER_BOX, Material.YELLOW_SHULKER_BOX, Material.LIME_SHULKER_BOX, Material.PINK_SHULKER_BOX, Material.GRAY_SHULKER_BOX, Material.SILVER_SHULKER_BOX, Material.CYAN_SHULKER_BOX, Material.PURPLE_SHULKER_BOX, Material.BLUE_SHULKER_BOX, Material.BROWN_SHULKER_BOX, Material.GREEN_SHULKER_BOX, Material.RED_SHULKER_BOX, Material.BLACK_SHULKER_BOX);
      this.usableBlocks = Arrays.asList(Material.NOTE_BLOCK, Material.LEVER, Material.STONE_PLATE, Material.WOOD_PLATE, Material.IRON_PLATE, Material.GOLD_PLATE, Material.STONE_BUTTON, Material.TRAP_DOOR, Material.FENCE_GATE, Material.WOOD_BUTTON, Material.DAYLIGHT_DETECTOR, Material.DAYLIGHT_DETECTOR_INVERTED, Material.ACACIA_FENCE_GATE, Material.BIRCH_FENCE_GATE, Material.DARK_OAK_FENCE_GATE, Material.JUNGLE_FENCE_GATE, Material.SPRUCE_FENCE_GATE, Material.WOODEN_DOOR, Material.ACACIA_DOOR, Material.BIRCH_DOOR, Material.DARK_OAK_DOOR, Material.JUNGLE_DOOR, Material.SPRUCE_DOOR, Material.WOOD_DOOR, Material.REDSTONE_COMPARATOR_OFF, Material.REDSTONE_COMPARATOR_ON, Material.DIODE_BLOCK_OFF, Material.DIODE_BLOCK_ON, Material.TRIPWIRE, Material.CAKE_BLOCK, Material.BED_BLOCK, Material.DOUBLE_PLANT);
      this.handed = Arrays.asList(Material.DIAMOND_SWORD, Material.STONE_SWORD, Material.GOLD_SWORD, Material.IRON_SWORD, Material.WOOD_SWORD, Material.DIAMOND_SPADE, Material.GOLD_SPADE, Material.IRON_SPADE, Material.STONE_SPADE, Material.WOOD_SPADE, Material.IRON_PICKAXE, Material.DIAMOND_PICKAXE, Material.GOLD_PICKAXE, Material.STONE_PICKAXE, Material.WOOD_PICKAXE, Material.DIAMOND_AXE, Material.WOOD_AXE, Material.GOLD_AXE, Material.IRON_AXE, Material.STONE_AXE, Material.DIAMOND_HOE, Material.GOLD_HOE, Material.IRON_HOE, Material.STONE_HOE, Material.WOOD_HOE, Material.FISHING_ROD, Material.BOW, Material.STICK, Material.BONE, Material.BLAZE_ROD, Material.CARROT_STICK);
   }

   public void onDisable() {
   }

   public void onReload() {
   }

   public boolean canBlockDealDamage(Material material) {
      return material.isBlock() && (material == Material.CACTUS || material == Material.MAGMA || material == Material.LAVA || material == Material.STATIONARY_LAVA || material == Material.FIRE);
   }

   public boolean isAllowStand(Material material) {
      return this.allowStand.contains(material);
   }

   public boolean isPassable(Material material) {
      return this.passable.contains(material);
   }

   public boolean isProtected(Material material) {
      return this.protectedBlocks.contains(material);
   }

   public boolean isUsed(Material material) {
      return this.usableBlocks.contains(material);
   }

   public boolean isHelmet(Material material) {
      return material.name().endsWith("_HELMET");
   }

   public boolean isStairs(Material material) {
      return material.getData().equals(Stairs.class);
   }

   public boolean isChestplate(Material material) {
      return material.name().endsWith("_CHESTPLATE");
   }

   public boolean isLeggings(Material material) {
      return material.name().endsWith("_LEGGINGS");
   }

   public boolean isBoots(Material material) {
      return material.name().endsWith("_BOOTS");
   }

   public boolean isPotion(Material material) {
      return material.name().contains("POTION");
   }

   public boolean isSword(Material material) {
      return material.name().endsWith("_SWORD");
   }

   public boolean isPickaxe(Material material) {
      return material.name().endsWith("_PICKAXE");
   }

   public boolean isShovel(Material material) {
      return material.name().endsWith("_SHOVEL");
   }

   public boolean isAxe(Material material) {
      return material.name().endsWith("_AXE");
   }

   public boolean isHoe(Material material) {
      return material.name().endsWith("_HOE");
   }

   public List<Material> getAllowStand() {
      return this.allowStand;
   }

   public boolean hasNegativePotion(Collection<PotionEffect> effects) {
      return effects.stream().anyMatch((effect) -> {
         return effect != null && this.hasNegativePotion(effect.getType());
      });
   }

   public boolean hasNegativePotion(PotionEffectType type) {
      return type != null && this.negativePotions.contains(type.getId());
   }

   public boolean hasNegativePotion(AreaEffectCloud effectCloud) {
      return this.hasNegativePotion(effectCloud.getBasePotionData().getType().getEffectType()) || this.hasNegativePotion((Collection)effectCloud.getCustomEffects());
   }

   public boolean hasNegativePotion(ThrownPotion potion) {
      return this.hasNegativePotion(potion.getEffects());
   }

   public List<Material> getHelmet() {
      return this.helmets;
   }

   public List<Material> getChestplate() {
      return this.chestplate;
   }

   public List<Material> getLeggings() {
      return this.leggings;
   }

   public List<Material> getBoots() {
      return this.boots;
   }

   public List<Material> getPassable() {
      return this.passable;
   }

   public List<Material> getPotions() {
      return this.potions;
   }

   public List<Material> getProtected() {
      return this.protectedBlocks;
   }

   public List<Material> getUsable() {
      return this.usableBlocks;
   }

   public float getStrengthBlock(Material material) {
      return (Float)this.strengths.getOrDefault(material, 0.0F);
   }

   public List<Material> getHanded() {
      return this.handed;
   }

   public boolean isShears(Material type) {
      return type == Material.SHEARS;
   }
}
