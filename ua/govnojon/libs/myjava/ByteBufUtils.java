package ua.govnojon.libs.myjava;

import com.comphenix.protocol.wrappers.BlockPosition;
import com.comphenix.protocol.wrappers.ChunkCoordIntPair;
import com.comphenix.protocol.wrappers.MultiBlockChangeInfo;
import com.comphenix.protocol.wrappers.Vector3F;
import com.comphenix.protocol.wrappers.WrappedBlockData;
import com.comphenix.protocol.wrappers.WrappedWatchableObject;
import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.zip.DataFormatException;
import java.util.zip.Deflater;
import java.util.zip.Inflater;
import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.inventory.ItemStack;
import org.bukkit.util.Vector;
import ua.govnojon.libs.bukkitutil.BukkitEnums;
import ua.govnojon.libs.bukkitutil.ItemStackUtil;

public class ByteBufUtils {
   public static void writeLocation(ByteBuf byteBuf, Location location) {
      byteBuf.writeFloat((float)location.getX());
      byteBuf.writeFloat((float)location.getY());
      byteBuf.writeFloat((float)location.getZ());
      byteBuf.writeFloat(location.getYaw());
      byteBuf.writeFloat(location.getPitch());
   }

   public static Location readLocation(ByteBuf byteBuf) {
      return new Location((World)null, (double)byteBuf.readFloat(), (double)byteBuf.readFloat(), (double)byteBuf.readFloat(), byteBuf.readFloat(), byteBuf.readFloat());
   }

   public static void writeVector(ByteBuf byteBuf, Vector vector) {
      byteBuf.writeFloat((float)vector.getX());
      byteBuf.writeFloat((float)vector.getY());
      byteBuf.writeFloat((float)vector.getZ());
   }

   public static void writeVector(ByteBuf byteBuf, Vector3F vector) {
      byteBuf.writeFloat(vector.getX());
      byteBuf.writeFloat(vector.getY());
      byteBuf.writeFloat(vector.getZ());
   }

   public static Vector readVector(ByteBuf byteBuf) {
      return new Vector(byteBuf.readFloat(), byteBuf.readFloat(), byteBuf.readFloat());
   }

   public static Vector3F readVector3F(ByteBuf byteBuf) {
      return new Vector3F(byteBuf.readFloat(), byteBuf.readFloat(), byteBuf.readFloat());
   }

   public static void writeItemStack(ByteBuf byteBuf, ItemStack itemStack) {
      writeString(byteBuf, ItemStackUtil.serialize(itemStack));
   }

   public static ItemStack readItemStack(ByteBuf byteBuf) {
      return ItemStackUtil.deserialize(readString(byteBuf));
   }

   public static ByteBuf bufFromString(String arg) {
      ByteBuf buf = Unpooled.buffer();
      buf.writeBytes(arg.getBytes());
      return buf;
   }

   public static String bufToString(ByteBuf buf) {
      byte[] bytes = new byte[buf.readableBytes()];
      buf.readBytes(bytes);
      return new String(bytes);
   }

   public static void writeString(ByteBuf byteBuf, String string) {
      byte[] bytes = string.getBytes();
      byteBuf.writeInt(bytes.length);
      byteBuf.writeBytes(bytes);
   }

   public static String readString(ByteBuf byteBuf) {
      byte[] bytes = new byte[byteBuf.readInt()];
      byteBuf.readBytes(bytes);
      return new String(bytes);
   }

   public static void writeObject(ByteBuf byteBuf, Object object) {
      try {
         ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
         Throwable var3 = null;

         try {
            ObjectOutputStream stream = new ObjectOutputStream(outputStream);
            Throwable var5 = null;

            try {
               stream.writeObject(object);
               stream.flush();
               byte[] bytes = outputStream.toByteArray();
               byteBuf.writeInt(bytes.length);
               byteBuf.writeBytes(bytes);
            } catch (Throwable var30) {
               var5 = var30;
               throw var30;
            } finally {
               if (stream != null) {
                  if (var5 != null) {
                     try {
                        stream.close();
                     } catch (Throwable var29) {
                        var5.addSuppressed(var29);
                     }
                  } else {
                     stream.close();
                  }
               }

            }
         } catch (Throwable var32) {
            var3 = var32;
            throw var32;
         } finally {
            if (outputStream != null) {
               if (var3 != null) {
                  try {
                     outputStream.close();
                  } catch (Throwable var28) {
                     var3.addSuppressed(var28);
                  }
               } else {
                  outputStream.close();
               }
            }

         }
      } catch (Exception var34) {
         var34.printStackTrace();
      }

   }

   public static Object readObject(ByteBuf byteBuf) {
      byte[] bytes = new byte[byteBuf.readInt()];
      byteBuf.readBytes(bytes);

      try {
         ByteArrayInputStream outputStream = new ByteArrayInputStream(bytes);
         Throwable var3 = null;

         Object var6;
         try {
            ObjectInputStream stream = new ObjectInputStream(outputStream);
            Throwable var5 = null;

            try {
               var6 = stream.readObject();
            } catch (Throwable var31) {
               var6 = var31;
               var5 = var31;
               throw var31;
            } finally {
               if (stream != null) {
                  if (var5 != null) {
                     try {
                        stream.close();
                     } catch (Throwable var30) {
                        var5.addSuppressed(var30);
                     }
                  } else {
                     stream.close();
                  }
               }

            }
         } catch (Throwable var33) {
            var3 = var33;
            throw var33;
         } finally {
            if (outputStream != null) {
               if (var3 != null) {
                  try {
                     outputStream.close();
                  } catch (Throwable var29) {
                     var3.addSuppressed(var29);
                  }
               } else {
                  outputStream.close();
               }
            }

         }

         return var6;
      } catch (Exception var35) {
         var35.printStackTrace();
         return null;
      }
   }

   public static void writeBlockData(ByteBuf byteBuf, WrappedBlockData blockData) {
      byteBuf.writeShort(blockData.getType().ordinal());
      byteBuf.writeByte(blockData.getData());
   }

   public static WrappedBlockData readBlockData(ByteBuf byteBuf) {
      return WrappedBlockData.createData(BukkitEnums.materials[byteBuf.readShort()], byteBuf.readByte());
   }

   public static void writeBlockDataArray(ByteBuf byteBuf, WrappedBlockData[] blockData) {
      byteBuf.writeInt(blockData.length);
      WrappedBlockData[] var2 = blockData;
      int var3 = blockData.length;

      for(int var4 = 0; var4 < var3; ++var4) {
         WrappedBlockData blockDatum = var2[var4];
         writeBlockData(byteBuf, blockDatum);
      }

   }

   public static WrappedBlockData[] readBlockDataArray(ByteBuf byteBuf) {
      int size = byteBuf.readInt();
      WrappedBlockData[] blockData = new WrappedBlockData[size];

      for(int i = 0; i < size; ++i) {
         blockData[i] = readBlockData(byteBuf);
      }

      return blockData;
   }

   public static void writeBlockPosition(ByteBuf byteBuf, BlockPosition blockPosition) {
      byteBuf.writeShort(blockPosition.getX());
      byteBuf.writeShort(blockPosition.getY());
      byteBuf.writeShort(blockPosition.getZ());
   }

   public static BlockPosition readBlockPosition(ByteBuf byteBuf) {
      return new BlockPosition(byteBuf.readShort(), byteBuf.readShort(), byteBuf.readShort());
   }

   public static void writeChunkCoordIntPair(ByteBuf byteBuf, ChunkCoordIntPair chunkCoordIntPair) {
      byteBuf.writeShort(chunkCoordIntPair.getChunkX());
      byteBuf.writeShort(chunkCoordIntPair.getChunkZ());
   }

   public static ChunkCoordIntPair readChunkCoordIntPair(ByteBuf byteBuf) {
      return new ChunkCoordIntPair(byteBuf.readShort(), byteBuf.readShort());
   }

   public static void writeMultiBlockChangeInfo(ByteBuf byteBuf, MultiBlockChangeInfo multiBlockChangeInfo) {
      writeLocation(byteBuf, multiBlockChangeInfo.getLocation((World)null));
      writeBlockData(byteBuf, multiBlockChangeInfo.getData());
   }

   public static MultiBlockChangeInfo readMultiBlockChangeInfo(ByteBuf byteBuf) {
      return new MultiBlockChangeInfo(readLocation(byteBuf), readBlockData(byteBuf));
   }

   public static void writeMultiBlockChangeInfoArray(ByteBuf byteBuf, MultiBlockChangeInfo[] array) {
      byteBuf.writeInt(array.length);
      MultiBlockChangeInfo[] var2 = array;
      int var3 = array.length;

      for(int var4 = 0; var4 < var3; ++var4) {
         MultiBlockChangeInfo multiBlockChangeInfo = var2[var4];
         writeMultiBlockChangeInfo(byteBuf, multiBlockChangeInfo);
      }

   }

   public static MultiBlockChangeInfo[] readMultiBlockChangeInfoArray(ByteBuf byteBuf) {
      int size = byteBuf.readInt();
      MultiBlockChangeInfo[] multiBlockChangeInfo = new MultiBlockChangeInfo[size];

      for(int i = 0; i < size; ++i) {
         multiBlockChangeInfo[i] = readMultiBlockChangeInfo(byteBuf);
      }

      return multiBlockChangeInfo;
   }

   public static void writeMetadata(ByteBuf byteBuf, WrappedWatchableObject object) {
      byteBuf.writeInt(object.getIndex());
      Object value = object.getValue();
      if (value instanceof Boolean) {
         byteBuf.writeByte(0);
         byteBuf.writeBoolean((Boolean)value);
      } else if (value instanceof Byte) {
         byteBuf.writeByte(1);
         byteBuf.writeByte((Byte)value);
      } else if (value instanceof Short) {
         byteBuf.writeByte(2);
         byteBuf.writeShort((Short)value);
      } else if (value instanceof Integer) {
         byteBuf.writeByte(3);
         byteBuf.writeInt((Integer)value);
      } else if (value instanceof Float) {
         byteBuf.writeByte(4);
         byteBuf.writeFloat((Float)value);
      } else if (value instanceof Double) {
         byteBuf.writeByte(5);
         byteBuf.writeDouble((Double)value);
      } else if (value instanceof String) {
         byteBuf.writeByte(6);
         writeString(byteBuf, (String)value);
      } else if (value instanceof ItemStack) {
         byteBuf.writeByte(7);
         writeItemStack(byteBuf, (ItemStack)value);
      } else if (value instanceof Vector3F) {
         byteBuf.writeByte(8);
         writeVector(byteBuf, (Vector3F)value);
      }

   }

   public static WrappedWatchableObject readMetadata(ByteBuf byteBuf) {
      int index = byteBuf.readInt();
      Object value = null;
      switch(byteBuf.readByte()) {
      case 0:
         value = byteBuf.readBoolean();
         break;
      case 1:
         value = byteBuf.readByte();
         break;
      case 2:
         value = byteBuf.readShort();
         break;
      case 3:
         value = byteBuf.readInt();
         break;
      case 4:
         value = byteBuf.readFloat();
         break;
      case 5:
         value = byteBuf.readDouble();
         break;
      case 6:
         value = readString(byteBuf);
         break;
      case 7:
         value = readItemStack(byteBuf);
         break;
      case 8:
         value = readVector3F(byteBuf);
      }

      return new WrappedWatchableObject(index, value);
   }

   public static byte[] toBytes(ByteBuf buf) {
      byte[] bytes = new byte[buf.readableBytes()];
      buf.readBytes(bytes);
      return bytes;
   }

   public static byte[] compress(byte[] data) throws IOException {
      Deflater deflater = new Deflater();
      deflater.setInput(data);
      ByteArrayOutputStream outputStream = new ByteArrayOutputStream(data.length);
      deflater.finish();
      byte[] buffer = new byte[1024];

      while(!deflater.finished()) {
         int count = deflater.deflate(buffer);
         outputStream.write(buffer, 0, count);
      }

      outputStream.close();
      return outputStream.toByteArray();
   }

   public static byte[] decompress(byte[] data) throws IOException, DataFormatException {
      Inflater inflater = new Inflater();
      inflater.setInput(data);
      ByteArrayOutputStream outputStream = new ByteArrayOutputStream(data.length);
      byte[] buffer = new byte[1024];

      while(!inflater.finished()) {
         int count = inflater.inflate(buffer);
         outputStream.write(buffer, 0, count);
      }

      outputStream.close();
      return outputStream.toByteArray();
   }
}
