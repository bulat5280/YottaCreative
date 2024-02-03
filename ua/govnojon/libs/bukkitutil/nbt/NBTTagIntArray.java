package ua.govnojon.libs.bukkitutil.nbt;

import java.io.DataInput;
import java.io.DataOutput;
import java.io.IOException;
import java.util.Arrays;

public class NBTTagIntArray extends NBTBase {
   public int[] intArray;

   public NBTTagIntArray(String par1Str) {
      super(par1Str);
   }

   public NBTTagIntArray(String par1Str, int[] par2ArrayOfInteger) {
      super(par1Str);
      this.intArray = par2ArrayOfInteger;
   }

   void write(DataOutput par1DataOutput) throws IOException {
      par1DataOutput.writeInt(this.intArray.length);
      int[] var2 = this.intArray;
      int var3 = var2.length;

      for(int var4 = 0; var4 < var3; ++var4) {
         int anIntArray = var2[var4];
         par1DataOutput.writeInt(anIntArray);
      }

   }

   void load(DataInput par1DataInput, int par2) throws IOException {
      int var3 = par1DataInput.readInt();
      this.intArray = new int[var3];

      for(int var4 = 0; var4 < var3; ++var4) {
         this.intArray[var4] = par1DataInput.readInt();
      }

   }

   public byte getId() {
      return 11;
   }

   public String toString() {
      StringBuilder s = new StringBuilder("[");

      for(int i = 0; i < this.intArray.length; ++i) {
         s.append(this.intArray[i]);
         if (i != this.intArray.length - 1) {
            s.append(", ");
         }
      }

      s.append("]");
      return s.toString();
   }

   public NBTBase copy() {
      int[] var1 = new int[this.intArray.length];
      System.arraycopy(this.intArray, 0, var1, 0, this.intArray.length);
      return new NBTTagIntArray(this.getName(), var1);
   }

   public boolean equals(Object par1Obj) {
      if (!super.equals(par1Obj)) {
         return false;
      } else {
         NBTTagIntArray var2 = (NBTTagIntArray)par1Obj;
         return this.intArray == null && var2.intArray == null || this.intArray != null && Arrays.equals(this.intArray, var2.intArray);
      }
   }

   public int hashCode() {
      return super.hashCode() ^ Arrays.hashCode(this.intArray);
   }

   public void setValue(Object value) {
      if (value instanceof int[]) {
         this.intArray = (int[])((int[])value);
      }

   }
}
