package ua.govnojon.libs.bukkitutil;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.Closeable;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.File;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.util.ArrayList;
import java.util.zip.DeflaterOutputStream;
import java.util.zip.GZIPInputStream;
import java.util.zip.InflaterInputStream;

public class RegionFile implements Closeable {
   static final int CHUNK_HEADER_SIZE = 5;
   private static final int VERSION_GZIP = 1;
   private static final int VERSION_DEFLATE = 2;
   private static final int SECTOR_BYTES = 4096;
   private static final int SECTOR_INTS = 1024;
   private static final byte[] emptySector = new byte[4096];
   private final File fileName;
   private final int[] offsets = new int[1024];
   private final int[] chunkTimestamps = new int[1024];
   private RandomAccessFile file;
   private ArrayList<Boolean> sectorFree;
   private int sizeDelta;
   private long lastModified = 0L;

   public RegionFile(File path) {
      this.fileName = path;
      this.debugln("REGION LOAD " + this.fileName);
      this.sizeDelta = 0;

      try {
         if (path.exists()) {
            this.lastModified = path.lastModified();
         }

         this.file = new RandomAccessFile(path, "rw");
         int nSectors;
         if (this.file.length() < 4096L) {
            for(nSectors = 0; nSectors < 1024; ++nSectors) {
               this.file.writeInt(0);
            }

            for(nSectors = 0; nSectors < 1024; ++nSectors) {
               this.file.writeInt(0);
            }

            this.sizeDelta += 8192;
         }

         if ((this.file.length() & 4095L) != 0L) {
            for(nSectors = 0; (long)nSectors < (this.file.length() & 4095L); ++nSectors) {
               this.file.write(0);
            }
         }

         nSectors = (int)this.file.length() / 4096;
         this.sectorFree = new ArrayList(nSectors);

         int i;
         for(i = 0; i < nSectors; ++i) {
            this.sectorFree.add(true);
         }

         this.sectorFree.set(0, false);
         this.sectorFree.set(1, false);
         this.file.seek(0L);

         int offset;
         for(i = 0; i < 1024; ++i) {
            offset = this.file.readInt();
            this.offsets[i] = offset;
            if (offset != 0 && (offset >> 8) + (offset & 255) <= this.sectorFree.size()) {
               for(int sectorNum = 0; sectorNum < (offset & 255); ++sectorNum) {
                  this.sectorFree.set((offset >> 8) + sectorNum, false);
               }
            }
         }

         for(i = 0; i < 1024; ++i) {
            offset = this.file.readInt();
            this.chunkTimestamps[i] = offset;
         }
      } catch (IOException var6) {
         var6.printStackTrace();
      }

   }

   public long lastModified() {
      return this.lastModified;
   }

   public synchronized int getSizeDelta() {
      int ret = this.sizeDelta;
      this.sizeDelta = 0;
      return ret;
   }

   private void debug(String in) {
   }

   private void debugln(String in) {
      this.debug(in + "\n");
   }

   private void debug(String mode, int x, int z, String in) {
      this.debug("REGION " + mode + " " + this.fileName.getName() + "[" + x + "," + z + "] = " + in);
   }

   private void debug(String mode, int x, int z, int count, String in) {
      this.debug("REGION " + mode + " " + this.fileName.getName() + "[" + x + "," + z + "] " + count + "B = " + in);
   }

   private void debugln(String mode, int x, int z, String in) {
      this.debug(mode, x, z, in + "\n");
   }

   public synchronized DataInputStream getChunkDataInputStream(int x, int z) {
      if (this.outOfBounds(x, z)) {
         this.debugln("READ", x, z, "out of bounds");
         return null;
      } else {
         try {
            int offset = this.getOffset(x, z);
            if (offset == 0) {
               return null;
            } else {
               int sectorNumber = offset >> 8;
               int numSectors = offset & 255;
               if (sectorNumber + numSectors > this.sectorFree.size()) {
                  this.debugln("READ", x, z, "invalid sector");
                  return null;
               } else {
                  this.file.seek((long)(sectorNumber * 4096));
                  int length = this.file.readInt();
                  if (length > 4096 * numSectors) {
                     this.debugln("READ", x, z, "invalid length: " + length + " > 4096 * " + numSectors);
                     return null;
                  } else {
                     byte version = this.file.readByte();
                     byte[] data;
                     DataInputStream ret;
                     if (version == 1) {
                        data = new byte[length - 1];
                        this.file.read(data);
                        ret = new DataInputStream(new GZIPInputStream(new ByteArrayInputStream(data)));
                        return ret;
                     } else if (version == 2) {
                        data = new byte[length - 1];
                        this.file.read(data);
                        ret = new DataInputStream(new InflaterInputStream(new ByteArrayInputStream(data)));
                        return ret;
                     } else {
                        this.debugln("READ", x, z, "unknown version " + version);
                        return null;
                     }
                  }
               }
            }
         } catch (IOException var10) {
            this.debugln("READ", x, z, "exception");
            return null;
         }
      }
   }

   public DataOutputStream getChunkDataOutputStream(int x, int z) {
      return this.outOfBounds(x, z) ? null : new DataOutputStream(new DeflaterOutputStream(new RegionFile.ChunkBuffer(x, z)));
   }

   protected synchronized void write(int x, int z, byte[] data, int length) {
      try {
         int offset = this.getOffset(x, z);
         int sectorNumber = offset >> 8;
         int sectorsAllocated = offset & 255;
         int sectorsNeeded = (length + 5) / 4096 + 1;
         if (sectorsNeeded >= 256) {
            return;
         }

         if (sectorNumber != 0 && sectorsAllocated == sectorsNeeded) {
            this.debug("SAVE", x, z, length, "rewrite");
            this.write(sectorNumber, data, length);
         } else {
            int runStart;
            for(runStart = 0; runStart < sectorsAllocated; ++runStart) {
               this.sectorFree.set(sectorNumber + runStart, true);
            }

            runStart = this.sectorFree.indexOf(true);
            int runLength = 0;
            int i;
            if (runStart != -1) {
               for(i = runStart; i < this.sectorFree.size(); ++i) {
                  if (runLength != 0) {
                     if ((Boolean)this.sectorFree.get(i)) {
                        ++runLength;
                     } else {
                        runLength = 0;
                     }
                  } else if ((Boolean)this.sectorFree.get(i)) {
                     runStart = i;
                     runLength = 1;
                  }

                  if (runLength >= sectorsNeeded) {
                     break;
                  }
               }
            }

            if (runLength >= sectorsNeeded) {
               this.debug("SAVE", x, z, length, "reuse");
               sectorNumber = runStart;
               this.setOffset(x, z, runStart << 8 | sectorsNeeded);

               for(i = 0; i < sectorsNeeded; ++i) {
                  this.sectorFree.set(sectorNumber + i, false);
               }

               this.write(sectorNumber, data, length);
            } else {
               this.debug("SAVE", x, z, length, "grow");
               this.file.seek(this.file.length());
               sectorNumber = this.sectorFree.size();

               for(i = 0; i < sectorsNeeded; ++i) {
                  this.file.write(emptySector);
                  this.sectorFree.add(false);
               }

               this.sizeDelta += 4096 * sectorsNeeded;
               this.write(sectorNumber, data, length);
               this.setOffset(x, z, sectorNumber << 8 | sectorsNeeded);
            }
         }

         this.setTimestamp(x, z, (int)(System.currentTimeMillis() / 1000L));
      } catch (IOException var12) {
         var12.printStackTrace();
      }

   }

   private void write(int sectorNumber, byte[] data, int length) throws IOException {
      this.debugln(" " + sectorNumber);
      this.file.seek((long)(sectorNumber * 4096));
      this.file.writeInt(length + 1);
      this.file.writeByte(2);
      this.file.write(data, 0, length);
   }

   private boolean outOfBounds(int x, int z) {
      return x < 0 || x >= 32 || z < 0 || z >= 32;
   }

   private int getOffset(int x, int z) {
      return this.offsets[x + z * 32];
   }

   public boolean hasChunk(int x, int z) {
      return this.getOffset(x, z) != 0;
   }

   private void setOffset(int x, int z, int offset) throws IOException {
      this.offsets[x + z * 32] = offset;
      this.file.seek((long)((x + z * 32) * 4));
      this.file.writeInt(offset);
   }

   private void setTimestamp(int x, int z, int value) throws IOException {
      this.chunkTimestamps[x + z * 32] = value;
      this.file.seek((long)(4096 + (x + z * 32) * 4));
      this.file.writeInt(value);
   }

   public void close() throws IOException {
      this.file.close();
   }

   class ChunkBuffer extends ByteArrayOutputStream {
      private int x;
      private int z;

      public ChunkBuffer(int x, int z) {
         super(8096);
         this.x = x;
         this.z = z;
      }

      public void close() {
         RegionFile.this.write(this.x, this.z, this.buf, this.count);
      }
   }
}
