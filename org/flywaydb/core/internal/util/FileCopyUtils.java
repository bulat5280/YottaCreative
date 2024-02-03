package org.flywaydb.core.internal.util;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.Reader;
import java.io.StringWriter;
import java.io.Writer;

public class FileCopyUtils {
   private FileCopyUtils() {
   }

   public static String copyToString(Reader in) throws IOException {
      StringWriter out = new StringWriter();
      copy((Reader)in, (Writer)out);
      String str = out.toString();
      return str.startsWith("\ufeff") ? str.substring(1) : str;
   }

   public static byte[] copyToByteArray(InputStream in) throws IOException {
      ByteArrayOutputStream out = new ByteArrayOutputStream(4096);
      copy((InputStream)in, (OutputStream)out);
      return out.toByteArray();
   }

   public static void copy(Reader in, Writer out) throws IOException {
      try {
         char[] buffer = new char[4096];

         int bytesRead;
         while((bytesRead = in.read(buffer)) != -1) {
            out.write(buffer, 0, bytesRead);
         }

         out.flush();
      } finally {
         try {
            in.close();
         } catch (IOException var12) {
         }

         try {
            out.close();
         } catch (IOException var11) {
         }

      }
   }

   public static int copy(InputStream in, OutputStream out) throws IOException {
      try {
         int byteCount = 0;

         int bytesRead;
         for(byte[] buffer = new byte[4096]; (bytesRead = in.read(buffer)) != -1; byteCount += bytesRead) {
            out.write(buffer, 0, bytesRead);
         }

         out.flush();
         int var5 = byteCount;
         return var5;
      } finally {
         try {
            in.close();
         } catch (IOException var15) {
         }

         try {
            out.close();
         } catch (IOException var14) {
         }

      }
   }
}
