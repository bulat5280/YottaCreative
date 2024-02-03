package ua.govnojon.libs.bukkitutil.nbt;

import java.io.BufferedInputStream;
import java.io.DataInput;
import java.io.DataInputStream;
import java.io.DataOutput;
import java.io.DataOutputStream;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.RandomAccessFile;
import java.util.zip.GZIPInputStream;
import java.util.zip.GZIPOutputStream;

public class CompressedStreamTools {
   public static boolean isGZipped(File f) {
      int magic = 0;

      try {
         RandomAccessFile raf = new RandomAccessFile(f, "r");
         magic = raf.read() & 255 | raf.read() << 8 & '\uff00';
         raf.close();
      } catch (Throwable var3) {
         var3.printStackTrace();
      }

      return magic == 35615;
   }

   public static NBTTagCompound readCompressed(InputStream par0InputStream) throws IOException {
      DataInputStream var1 = new DataInputStream(new BufferedInputStream(new GZIPInputStream(par0InputStream)));
      Throwable var3 = null;

      NBTTagCompound var2;
      try {
         var2 = read(var1);
      } catch (Throwable var12) {
         var3 = var12;
         throw var12;
      } finally {
         if (var1 != null) {
            if (var3 != null) {
               try {
                  var1.close();
               } catch (Throwable var11) {
                  var3.addSuppressed(var11);
               }
            } else {
               var1.close();
            }
         }

      }

      return var2;
   }

   public static void writeCompressed(NBTTagCompound par0NBTTagCompound, OutputStream par1OutputStream) throws IOException {
      DataOutputStream var2 = new DataOutputStream(new GZIPOutputStream(par1OutputStream));
      Throwable var3 = null;

      try {
         write(par0NBTTagCompound, var2);
      } catch (Throwable var12) {
         var3 = var12;
         throw var12;
      } finally {
         if (var2 != null) {
            if (var3 != null) {
               try {
                  var2.close();
               } catch (Throwable var11) {
                  var3.addSuppressed(var11);
               }
            } else {
               var2.close();
            }
         }

      }

   }

   public static NBTTagCompound read(DataInput par0DataInput) throws IOException {
      NBTBase var1 = NBTBase.readNamedTag(par0DataInput);
      if (var1 instanceof NBTTagCompound) {
         return (NBTTagCompound)var1;
      } else {
         throw new IOException("Root tag must be a named compound tag");
      }
   }

   public static void write(NBTTagCompound par0NBTTagCompound, DataOutput par1DataOutput) throws IOException {
      NBTBase.writeNamedTag(par0NBTTagCompound, par1DataOutput);
   }
}
