package net.mineland.core.bukkit.modules.nms;

import com.google.common.base.Predicate;
import com.google.common.collect.Iterators;
import com.google.common.collect.Maps;
import java.util.Iterator;
import java.util.Map;
import java.util.Random;
import javax.annotation.Nullable;

public enum EnumDirection {
   DOWN(0, 1, -1, "down", EnumDirection.EnumAxisDirection.NEGATIVE, EnumDirection.EnumAxis.Y),
   UP(1, 0, -1, "up", EnumDirection.EnumAxisDirection.POSITIVE, EnumDirection.EnumAxis.Y),
   NORTH(2, 3, 2, "north", EnumDirection.EnumAxisDirection.NEGATIVE, EnumDirection.EnumAxis.Z),
   SOUTH(3, 2, 0, "south", EnumDirection.EnumAxisDirection.POSITIVE, EnumDirection.EnumAxis.Z),
   WEST(4, 5, 1, "west", EnumDirection.EnumAxisDirection.NEGATIVE, EnumDirection.EnumAxis.X),
   EAST(5, 4, 3, "east", EnumDirection.EnumAxisDirection.POSITIVE, EnumDirection.EnumAxis.X);

   private static final EnumDirection[] n = new EnumDirection[6];
   private static final EnumDirection[] o = new EnumDirection[4];
   private static final Map<String, EnumDirection> p = Maps.newHashMap();
   private final int g;
   private final int h;
   private final int i;
   private final String j;
   private final EnumDirection.EnumAxis k;
   private final EnumDirection.EnumAxisDirection l;

   private EnumDirection(int var3, int var4, int var5, String var6, EnumDirection.EnumAxisDirection var7, EnumDirection.EnumAxis var8) {
      this.g = var3;
      this.i = var5;
      this.h = var4;
      this.j = var6;
      this.k = var8;
      this.l = var7;
   }

   public static EnumDirection fromType1(int var0) {
      return n[a(var0 % n.length)];
   }

   public static EnumDirection fromType2(int var0) {
      return o[a(var0 % o.length)];
   }

   public static EnumDirection fromAngle(double var0) {
      return fromType2(floor(var0 / 90.0D + 0.5D) & 3);
   }

   public static int floor(double var0) {
      int var2 = (int)var0;
      return var0 < (double)var2 ? var2 - 1 : var2;
   }

   public static EnumDirection a(Random var0) {
      return values()[var0.nextInt(values().length)];
   }

   public static EnumDirection a(EnumDirection.EnumAxisDirection var0, EnumDirection.EnumAxis var1) {
      EnumDirection[] var2 = values();
      int var3 = var2.length;
      EnumDirection[] var4 = var2;
      int var5 = var2.length;

      for(int var6 = 0; var6 < var5; ++var6) {
         EnumDirection var5 = var4[var6];
         if (var5.c() == var0 && var5.k() == var1) {
            return var5;
         }
      }

      throw new IllegalArgumentException("No such direction: " + var0 + " " + var1);
   }

   public static int a(int var0) {
      return var0 >= 0 ? var0 : -var0;
   }

   public int a() {
      return this.g;
   }

   public int get2DRotationValue() {
      return this.i;
   }

   public EnumDirection.EnumAxisDirection c() {
      return this.l;
   }

   public EnumDirection opposite() {
      return fromType1(this.h);
   }

   public EnumDirection e() {
      switch(this.ordinal()) {
      case 1:
         return EAST;
      case 2:
         return SOUTH;
      case 3:
         return WEST;
      case 4:
         return NORTH;
      default:
         throw new IllegalStateException("Unable to get Y-rotated facing of " + this);
      }
   }

   public EnumDirection f() {
      switch(this.ordinal()) {
      case 1:
         return WEST;
      case 2:
         return NORTH;
      case 3:
         return EAST;
      case 4:
         return SOUTH;
      default:
         throw new IllegalStateException("Unable to get CCW facing of " + this);
      }
   }

   public int getAdjacentX() {
      return this.k == EnumDirection.EnumAxis.X ? this.l.a() : 0;
   }

   public int getAdjacentY() {
      return this.k == EnumDirection.EnumAxis.Y ? this.l.a() : 0;
   }

   public int getAdjacentZ() {
      return this.k == EnumDirection.EnumAxis.Z ? this.l.a() : 0;
   }

   public String j() {
      return this.j;
   }

   public EnumDirection.EnumAxis k() {
      return this.k;
   }

   public float l() {
      return (float)((this.i & 3) * 90);
   }

   public String toString() {
      return this.j;
   }

   public String getName() {
      return this.j;
   }

   static {
      EnumDirection[] var0 = values();
      int var1 = var0.length;
      EnumDirection[] var2 = var0;
      int var3 = var0.length;

      for(int var4 = 0; var4 < var3; ++var4) {
         EnumDirection var3 = var2[var4];
         n[var3.g] = var3;
         if (var3.k().c()) {
            o[var3.i] = var3;
         }

         p.put(var3.j().toLowerCase(), var3);
      }

   }

   public static enum EnumAxis implements Predicate<EnumDirection> {
      X("x", EnumDirection.EnumDirectionLimit.HORIZONTAL),
      Y("y", EnumDirection.EnumDirectionLimit.VERTICAL),
      Z("z", EnumDirection.EnumDirectionLimit.HORIZONTAL);

      private static final Map<String, EnumDirection.EnumAxis> d = Maps.newHashMap();
      private final String e;
      private final EnumDirection.EnumDirectionLimit f;

      private EnumAxis(String var3, EnumDirection.EnumDirectionLimit var4) {
         this.e = var3;
         this.f = var4;
      }

      public String a() {
         return this.e;
      }

      public boolean b() {
         return this.f == EnumDirection.EnumDirectionLimit.VERTICAL;
      }

      public boolean c() {
         return this.f == EnumDirection.EnumDirectionLimit.HORIZONTAL;
      }

      public String toString() {
         return this.e;
      }

      public boolean apply(@Nullable EnumDirection enumDirection) {
         return this.a(enumDirection);
      }

      public boolean a(@Nullable EnumDirection var1) {
         return var1 != null && var1.k() == this;
      }

      public EnumDirection.EnumDirectionLimit d() {
         return this.f;
      }

      public String getName() {
         return this.e;
      }

      static {
         EnumDirection.EnumAxis[] var0 = values();
         int var1 = var0.length;
         EnumDirection.EnumAxis[] var2 = var0;
         int var3 = var0.length;

         for(int var4 = 0; var4 < var3; ++var4) {
            EnumDirection.EnumAxis var3 = var2[var4];
            d.put(var3.a().toLowerCase(), var3);
         }

      }
   }

   public static enum EnumAxisDirection {
      POSITIVE(1, "Towards positive"),
      NEGATIVE(-1, "Towards negative");

      private final int c;
      private final String d;

      private EnumAxisDirection(int var3, String var4) {
         this.c = var3;
         this.d = var4;
      }

      public int a() {
         return this.c;
      }

      public String toString() {
         return this.d;
      }
   }

   public static enum EnumDirectionLimit implements Predicate<EnumDirection>, Iterable<EnumDirection> {
      HORIZONTAL,
      VERTICAL;

      public boolean apply(@Nullable EnumDirection enumDirection) {
         return this.a(enumDirection);
      }

      public EnumDirection[] a() {
         switch(this.ordinal()) {
         case 1:
            return new EnumDirection[]{EnumDirection.NORTH, EnumDirection.EAST, EnumDirection.SOUTH, EnumDirection.WEST};
         case 2:
            return new EnumDirection[]{EnumDirection.UP, EnumDirection.DOWN};
         default:
            throw new Error("Someone's been tampering with the universe!");
         }
      }

      public EnumDirection a(Random var1) {
         EnumDirection[] var2 = this.a();
         return var2[var1.nextInt(var2.length)];
      }

      public boolean a(@Nullable EnumDirection var1) {
         return var1 != null && var1.k().d() == this;
      }

      public Iterator<EnumDirection> iterator() {
         return Iterators.forArray(this.a());
      }
   }
}
