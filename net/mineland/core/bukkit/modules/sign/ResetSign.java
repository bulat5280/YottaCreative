package net.mineland.core.bukkit.modules.sign;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.World;
import org.bukkit.block.Block;
import org.bukkit.block.Sign;

class ResetSign {
   private String world;
   private int x;
   private int y;
   private int z;
   private String type;
   private int data;
   private String key;

   public ResetSign() {
   }

   public ResetSign(Block block, String key) {
      this.world = block.getWorld().getName();
      this.x = block.getX();
      this.y = block.getY();
      this.z = block.getZ();
      this.type = block.getType().name();
      this.data = block.getData();
      this.key = key;
   }

   public void reset() {
      World world = Bukkit.getWorld(this.world);
      Block block = world.getBlockAt(this.x, this.y, this.z);
      block.setType(Material.valueOf(this.type));
      block.setData((byte)this.data, false);
      Sign sign = (Sign)block.getState();
      sign.setLine(0, "[lang]");
      sign.setLine(1, this.key);
      sign.update();
   }

   public String getKey() {
      return this.key;
   }
}
