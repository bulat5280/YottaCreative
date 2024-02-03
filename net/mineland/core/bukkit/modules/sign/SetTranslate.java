package net.mineland.core.bukkit.modules.sign;

import java.util.regex.Pattern;
import net.mineland.core.bukkit.module.Module;
import net.mineland.core.bukkit.modules.message.ModuleMessage;
import net.mineland.core.bukkit.modules.user.Lang;
import org.apache.commons.lang3.StringUtils;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.World;
import org.bukkit.block.Block;
import org.bukkit.block.Sign;

class SetTranslate {
   private static ModuleMessage moduleMessage = (ModuleMessage)Module.getInstance(ModuleMessage.class);
   private static Pattern pattern = Pattern.compile("\n+$");
   private String world;
   private int x;
   private int y;
   private int z;
   private String type;
   private int data;

   public SetTranslate() {
   }

   public SetTranslate(Block block) {
      this.world = block.getWorld().getName();
      this.x = block.getX();
      this.y = block.getY();
      this.z = block.getZ();
      this.type = block.getType().name();
      this.data = block.getData();
   }

   public String translate(String key, Lang lang) {
      key = StringUtils.remove(key, ' ').toLowerCase();
      moduleMessage.setMessage(lang, key, this.getText());
      Block block = this.getBlock();
      block.setType(Material.valueOf(this.type));
      block.setData((byte)this.data, false);
      Sign sign = (Sign)block.getState();
      sign.setLine(0, "[lang]");
      sign.setLine(1, key);
      sign.update();
      return key;
   }

   private Block getBlock() {
      World world = Bukkit.getWorld(this.world);
      return world.getBlockAt(this.x, this.y, this.z);
   }

   private String fixText(String[] lines) {
      return pattern.matcher(String.join("\n", lines)).replaceFirst("");
   }

   public String getText() {
      return this.fixText(((Sign)this.getBlock().getState()).getLines());
   }

   public boolean isValid() {
      Block block = this.getBlock();
      Material type = block.getType();
      if (!type.equals(Material.WALL_SIGN) && !type.equals(Material.SIGN_POST)) {
         return false;
      } else {
         Sign state = (Sign)block.getState();
         return !state.getLine(0).equals("[lang]");
      }
   }
}
