package ua.govnojon.libs.bukkitutil.nbt;

import java.io.DataInput;
import java.io.DataOutput;

public class NBTTagEnd extends NBTBase {
   public NBTTagEnd() {
      super((String)null);
   }

   void load(DataInput par1DataInput, int par2) {
   }

   void write(DataOutput par1DataOutput) {
   }

   public byte getId() {
      return 0;
   }

   public String toString() {
      return "END";
   }

   public NBTBase copy() {
      return new NBTTagEnd();
   }

   /** @deprecated */
   @Deprecated
   public void setValue(Object value) {
   }
}
