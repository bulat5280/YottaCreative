package ua.govnojon.libs.bukkitutil;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;
import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.block.Block;
import org.bukkit.block.BlockFace;

public class TextToBlockUtil {
   public static HashMap<Character, int[][]> alphabet = new HashMap();

   public static ArrayList<Location> GetTextLocations(String string, Location loc, BlockFace face) {
      if (alphabet.isEmpty()) {
         PopulateAlphabet();
      }

      ArrayList<Location> locs = new ArrayList();
      Block block = loc.getBlock();
      int width = 0;
      char[] var6 = string.toLowerCase().toCharArray();
      int bX = var6.length;

      int bY;
      for(bY = 0; bY < bX; ++bY) {
         char c = var6[bY];
         int[][] letter = (int[][])alphabet.get(c);
         if (letter != null) {
            width += (letter[0].length + 1) * 3;
         }
      }

      block = block.getRelative(face, -1 * width / 2 + 1);
      World world = block.getWorld();
      bX = block.getX();
      bY = block.getY();
      int bZ = block.getZ();
      char[] var25 = string.toLowerCase().toCharArray();
      int var11 = var25.length;

      for(int var12 = 0; var12 < var11; ++var12) {
         char c = var25[var12];
         int[][] letter = (int[][])alphabet.get(c);
         if (letter != null) {
            int[][] var15 = letter;
            int var16 = letter.length;

            for(int var17 = 0; var17 < var16; ++var17) {
               int[] aLetter = var15[var17];
               int[] var19 = aLetter;
               int var20 = aLetter.length;

               for(int var21 = 0; var21 < var20; ++var21) {
                  int anALetter = var19[var21];
                  if (anALetter == 1) {
                     locs.add(new Location(world, (double)bX, (double)bY, (double)bZ));
                  }

                  bX += face.getModX() * 3;
                  bY += face.getModY() * 3;
                  bZ += face.getModZ() * 3;
               }

               bX += face.getModX() * -3 * aLetter.length;
               bY += face.getModY() * -3 * aLetter.length;
               bZ += face.getModZ() * -3 * aLetter.length;
               bY -= 3;
            }

            bY += 15;
            bX += face.getModX() * (letter[0].length + 1) * 3;
            bY += face.getModY() * (letter[0].length + 1) * 3;
            bZ += face.getModZ() * (letter[0].length + 1) * 3;
         }
      }

      return locs;
   }

   public static Collection<Block> MakeText(String string, Location loc, BlockFace face, int id, byte data, TextToBlockUtil.TextAlign align) {
      return MakeText(string, loc, face, id, data, align, true);
   }

   private static Collection<Block> MakeText(String string, Location loc, BlockFace face, int id, byte data, TextToBlockUtil.TextAlign align, boolean setAir) {
      HashSet<Block> changes = new HashSet();
      if (alphabet.isEmpty()) {
         PopulateAlphabet();
      }

      Block block = loc.getBlock();
      int width = 0;
      char[] var10 = string.toLowerCase().toCharArray();
      int bX = var10.length;

      int bY;
      for(bY = 0; bY < bX; ++bY) {
         char c = var10[bY];
         int[][] letter = (int[][])alphabet.get(c);
         if (letter != null) {
            width += letter[0].length + 1;
         }
      }

      if (align == TextToBlockUtil.TextAlign.CENTER || align == TextToBlockUtil.TextAlign.RIGHT) {
         int divisor = 1;
         if (align == TextToBlockUtil.TextAlign.CENTER) {
            divisor = 2;
         }

         block = block.getRelative(face, -1 * width / divisor + 1);
      }

      int i;
      World world;
      int bZ;
      if (setAir) {
         world = loc.getWorld();
         bX = loc.getBlockX();
         bY = loc.getBlockY();
         bZ = loc.getBlockZ();

         for(int y = 0; y < 5; ++y) {
            if (align == TextToBlockUtil.TextAlign.CENTER) {
               for(i = -64; i <= 64; ++i) {
                  if (world.getBlockAt(bX + i * face.getModX(), bY + i * face.getModY(), bZ + i * face.getModZ()).getTypeId() == id) {
                     ChunkBlockSet(world, bX + i * face.getModX(), bY + i * face.getModY(), bZ + i * face.getModZ(), 0, (byte)0, true);
                  }
               }
            }

            if (align == TextToBlockUtil.TextAlign.LEFT) {
               for(i = 0; i <= 128; ++i) {
                  if (world.getBlockAt(bX + i * face.getModX(), bY + i * face.getModY(), bZ + i * face.getModZ()).getTypeId() == id) {
                     ChunkBlockSet(world, bX + i * face.getModX(), bY + i * face.getModY(), bZ + i * face.getModZ(), 0, (byte)0, true);
                  }
               }
            }

            if (align == TextToBlockUtil.TextAlign.RIGHT) {
               for(i = -128; i <= 0; ++i) {
                  if (world.getBlockAt(bX + i * face.getModX(), bY + i * face.getModY(), bZ + i * face.getModZ()).getTypeId() == id) {
                     ChunkBlockSet(world, bX + i * face.getModX(), bY + i * face.getModY(), bZ + i * face.getModZ(), 0, (byte)0, true);
                  }
               }
            }

            --bY;
         }
      }

      world = block.getWorld();
      bX = block.getX();
      bY = block.getY();
      bZ = block.getZ();
      char[] var31 = string.toLowerCase().toCharArray();
      i = var31.length;

      for(int var16 = 0; var16 < i; ++var16) {
         char c = var31[var16];
         int[][] letter = (int[][])alphabet.get(c);
         if (letter != null) {
            int[][] var19 = letter;
            int var20 = letter.length;

            for(int var21 = 0; var21 < var20; ++var21) {
               int[] aLetter = var19[var21];
               int[] var23 = aLetter;
               int var24 = aLetter.length;

               for(int var25 = 0; var25 < var24; ++var25) {
                  int anALetter = var23[var25];
                  if (anALetter == 1) {
                     changes.add(world.getBlockAt(bX, bY, bZ));
                     ChunkBlockSet(world, bX, bY, bZ, id, data, true);
                  }

                  bX += face.getModX();
                  bY += face.getModY();
                  bZ += face.getModZ();
               }

               bX += face.getModX() * -1 * aLetter.length;
               bY += face.getModY() * -1 * aLetter.length;
               bZ += face.getModZ() * -1 * aLetter.length;
               --bY;
            }

            bY += 5;
            bX += face.getModX() * (letter[0].length + 1);
            bY += face.getModY() * (letter[0].length + 1);
            bZ += face.getModZ() * (letter[0].length + 1);
         }
      }

      return changes;
   }

   private static void PopulateAlphabet() {
      alphabet.put('0', new int[][]{{1, 1, 1}, {1, 0, 1}, {1, 0, 1}, {1, 0, 1}, {1, 1, 1}});
      alphabet.put('1', new int[][]{{1, 1, 0}, {0, 1, 0}, {0, 1, 0}, {0, 1, 0}, {1, 1, 1}});
      alphabet.put('2', new int[][]{{1, 1, 1}, {0, 0, 1}, {1, 1, 1}, {1, 0, 0}, {1, 1, 1}});
      alphabet.put('3', new int[][]{{1, 1, 1}, {0, 0, 1}, {1, 1, 1}, {0, 0, 1}, {1, 1, 1}});
      alphabet.put('4', new int[][]{{1, 0, 1}, {1, 0, 1}, {1, 1, 1}, {0, 0, 1}, {0, 0, 1}});
      alphabet.put('5', new int[][]{{1, 1, 1}, {1, 0, 0}, {1, 1, 1}, {0, 0, 1}, {1, 1, 1}});
      alphabet.put('6', new int[][]{{1, 1, 1}, {1, 0, 0}, {1, 1, 1}, {1, 0, 1}, {1, 1, 1}});
      alphabet.put('7', new int[][]{{1, 1, 1}, {0, 0, 1}, {0, 0, 1}, {0, 0, 1}, {0, 0, 1}});
      alphabet.put('8', new int[][]{{1, 1, 1}, {1, 0, 1}, {1, 1, 1}, {1, 0, 1}, {1, 1, 1}});
      alphabet.put('9', new int[][]{{1, 1, 1}, {1, 0, 1}, {1, 1, 1}, {0, 0, 1}, {1, 1, 1}});
      alphabet.put('.', new int[][]{{0}, {0}, {0}, {0}, {1}});
      alphabet.put('!', new int[][]{{1}, {1}, {1}, {0}, {1}});
      alphabet.put(' ', new int[][]{{0, 0}, {0, 0}, {0, 0}, {0, 0}, {0, 0}});
      alphabet.put('a', new int[][]{{1, 1, 1, 1}, {1, 0, 0, 1}, {1, 1, 1, 1}, {1, 0, 0, 1}, {1, 0, 0, 1}});
      alphabet.put('b', new int[][]{{1, 1, 1, 0}, {1, 0, 0, 1}, {1, 1, 1, 0}, {1, 0, 0, 1}, {1, 1, 1, 0}});
      alphabet.put('c', new int[][]{{1, 1, 1, 1}, {1, 0, 0, 0}, {1, 0, 0, 0}, {1, 0, 0, 0}, {1, 1, 1, 1}});
      alphabet.put('d', new int[][]{{1, 1, 1, 0}, {1, 0, 0, 1}, {1, 0, 0, 1}, {1, 0, 0, 1}, {1, 1, 1, 0}});
      alphabet.put('e', new int[][]{{1, 1, 1, 1}, {1, 0, 0, 0}, {1, 1, 1, 0}, {1, 0, 0, 0}, {1, 1, 1, 1}});
      alphabet.put('f', new int[][]{{1, 1, 1, 1}, {1, 0, 0, 0}, {1, 1, 1, 0}, {1, 0, 0, 0}, {1, 0, 0, 0}});
      alphabet.put('g', new int[][]{{1, 1, 1, 1}, {1, 0, 0, 0}, {1, 0, 1, 1}, {1, 0, 0, 1}, {1, 1, 1, 1}});
      alphabet.put('h', new int[][]{{1, 0, 0, 1}, {1, 0, 0, 1}, {1, 1, 1, 1}, {1, 0, 0, 1}, {1, 0, 0, 1}});
      alphabet.put('i', new int[][]{{1, 1, 1}, {0, 1, 0}, {0, 1, 0}, {0, 1, 0}, {1, 1, 1}});
      alphabet.put('j', new int[][]{{1, 1, 1, 1}, {0, 0, 1, 0}, {0, 0, 1, 0}, {1, 0, 1, 0}, {1, 1, 1, 0}});
      alphabet.put('k', new int[][]{{1, 0, 0, 1}, {1, 0, 1, 0}, {1, 1, 0, 0}, {1, 0, 1, 0}, {1, 0, 0, 1}});
      alphabet.put('l', new int[][]{{1, 0, 0, 0}, {1, 0, 0, 0}, {1, 0, 0, 0}, {1, 0, 0, 0}, {1, 1, 1, 1}});
      alphabet.put('m', new int[][]{{1, 1, 1, 1, 1}, {1, 0, 1, 0, 1}, {1, 0, 1, 0, 1}, {1, 0, 0, 0, 1}, {1, 0, 0, 0, 1}});
      alphabet.put('n', new int[][]{{1, 0, 0, 1}, {1, 1, 0, 1}, {1, 0, 1, 1}, {1, 0, 0, 1}, {1, 0, 0, 1}});
      alphabet.put('o', new int[][]{{1, 1, 1, 1}, {1, 0, 0, 1}, {1, 0, 0, 1}, {1, 0, 0, 1}, {1, 1, 1, 1}});
      alphabet.put('p', new int[][]{{1, 1, 1, 1}, {1, 0, 0, 1}, {1, 1, 1, 1}, {1, 0, 0, 0}, {1, 0, 0, 0}});
      alphabet.put('q', new int[][]{{1, 1, 1, 1}, {1, 0, 0, 1}, {1, 0, 0, 1}, {1, 0, 1, 0}, {1, 1, 0, 1}});
      alphabet.put('r', new int[][]{{1, 1, 1, 1}, {1, 0, 0, 1}, {1, 1, 1, 0}, {1, 0, 0, 1}, {1, 0, 0, 1}});
      alphabet.put('s', new int[][]{{1, 1, 1, 1}, {1, 0, 0, 0}, {1, 1, 1, 1}, {0, 0, 0, 1}, {1, 1, 1, 1}});
      alphabet.put('t', new int[][]{{1, 1, 1, 1, 1}, {0, 0, 1, 0, 0}, {0, 0, 1, 0, 0}, {0, 0, 1, 0, 0}, {0, 0, 1, 0, 0}});
      alphabet.put('u', new int[][]{{1, 0, 0, 1}, {1, 0, 0, 1}, {1, 0, 0, 1}, {1, 0, 0, 1}, {1, 1, 1, 1}});
      alphabet.put('v', new int[][]{{1, 0, 0, 1}, {1, 0, 0, 1}, {1, 0, 0, 1}, {1, 0, 0, 1}, {0, 1, 1, 0}});
      alphabet.put('w', new int[][]{{1, 0, 0, 0, 1}, {1, 0, 0, 0, 1}, {1, 0, 1, 0, 1}, {1, 0, 1, 0, 1}, {1, 1, 1, 1, 1}});
      alphabet.put('x', new int[][]{{1, 0, 0, 1}, {1, 0, 0, 1}, {0, 1, 1, 0}, {1, 0, 0, 1}, {1, 0, 0, 1}});
      alphabet.put('y', new int[][]{{1, 0, 0, 1}, {1, 0, 0, 1}, {1, 1, 1, 1}, {0, 0, 0, 1}, {1, 1, 1, 1}});
      alphabet.put('z', new int[][]{{1, 1, 1, 1}, {0, 0, 0, 1}, {0, 0, 1, 0}, {0, 1, 0, 0}, {1, 1, 1, 1}});
   }

   private static void ChunkBlockSet(World world, int x, int y, int z, int id, byte data, boolean notifyPlayers) {
      world.getBlockAt(x, y, z).setTypeIdAndData(id, data, notifyPlayers);
   }

   public static enum TextAlign {
      LEFT,
      RIGHT,
      CENTER;
   }
}
