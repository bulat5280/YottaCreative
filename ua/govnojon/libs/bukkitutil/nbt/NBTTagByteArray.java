package ua.govnojon.libs.bukkitutil.nbt;

import java.io.DataInput;
import java.io.DataOutput;
import java.io.IOException;
import java.util.Arrays;

public class NBTTagByteArray extends NBTBase {
   public byte[] byteArray;

   public NBTTagByteArray(String par1Str) {
      super(par1Str);
   }

   public NBTTagByteArray(String par1Str, byte[] par2ArrayOfByte) {
      super(par1Str);
      this.byteArray = par2ArrayOfByte;
   }

   void write(DataOutput par1DataOutput) throws IOException {
      par1DataOutput.writeInt(this.byteArray.length);
      par1DataOutput.write(this.byteArray);
   }

   void load(DataInput par1DataInput, int par2) throws IOException {
      int var3 = par1DataInput.readInt();
      this.byteArray = new byte[var3];
      par1DataInput.readFully(this.byteArray);
   }

   public byte getId() {
      return 7;
   }

   public String toString() {
      StringBuilder s = new StringBuilder("[");

      for(int i = 0; i < this.byteArray.length; ++i) {
         s.append(this.byteArray[i]);
         if (i != this.byteArray.length - 1) {
            s.append(", ");
         }
      }

      s.append("]");
      return s.toString();
   }

   public NBTBase copy() {
      byte[] var1 = new byte[this.byteArray.length];
      System.arraycopy(this.byteArray, 0, var1, 0, this.byteArray.length);
      return new NBTTagByteArray(this.getName(), var1);
   }

   public boolean equals(Object par1Obj) {
      return super.equals(par1Obj) && Arrays.equals(this.byteArray, ((NBTTagByteArray)par1Obj).byteArray);
   }

   public int hashCode() {
      return super.hashCode() ^ Arrays.hashCode(this.byteArray);
   }

   public void setValue(Object value) {
      if (value instanceof byte[]) {
         this.byteArray = (byte[])((byte[])value);
      }

   }

   public String toModifyString() {
      return this.toString();
   }
}
