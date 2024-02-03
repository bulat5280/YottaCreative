package com.mysql.cj.mysqla;

import com.mysql.cj.api.mysqla.io.PacketPayload;
import com.mysql.cj.core.Messages;
import com.mysql.cj.core.util.StringUtils;

public class MysqlaUtils {
   private MysqlaUtils() {
   }

   public static byte[] encodeMysqlThreeByteInteger(int i) {
      byte[] b = new byte[]{(byte)(i & 255), (byte)(i >>> 8), (byte)(i >>> 16)};
      return b;
   }

   public static void encodeMysqlThreeByteInteger(int i, byte[] b, int offset) {
      b[offset++] = (byte)(i & 255);
      b[offset++] = (byte)(i >>> 8);
      b[offset] = (byte)(i >>> 16);
   }

   public static int decodeMysqlThreeByteInteger(byte[] b) {
      return decodeMysqlThreeByteInteger(b, 0);
   }

   public static int decodeMysqlThreeByteInteger(byte[] b, int offset) {
      return (b[offset + 0] & 255) + ((b[offset + 1] & 255) << 8) + ((b[offset + 2] & 255) << 16);
   }

   public static long bitToLong(byte[] bytes, int offset, int length) {
      long valueAsLong = 0L;

      for(int i = 0; i < length; ++i) {
         valueAsLong = valueAsLong << 8 | (long)(bytes[offset + i] & 255);
      }

      return valueAsLong;
   }

   public static String extractSqlFromPacket(String possibleSqlQuery, PacketPayload queryPacket, int endOfQueryPacketPosition, int maxQuerySizeToLog) {
      String extractedSql = null;
      if (possibleSqlQuery != null) {
         if (possibleSqlQuery.length() > maxQuerySizeToLog) {
            StringBuilder truncatedQueryBuf = new StringBuilder(possibleSqlQuery.substring(0, maxQuerySizeToLog));
            truncatedQueryBuf.append(Messages.getString("MysqlIO.25"));
            extractedSql = truncatedQueryBuf.toString();
         } else {
            extractedSql = possibleSqlQuery;
         }
      }

      if (extractedSql == null) {
         int extractPosition = endOfQueryPacketPosition;
         boolean truncated = false;
         if (endOfQueryPacketPosition > maxQuerySizeToLog) {
            extractPosition = maxQuerySizeToLog;
            truncated = true;
         }

         extractedSql = StringUtils.toString(queryPacket.getByteBuffer(), 1, extractPosition - 1);
         if (truncated) {
            extractedSql = extractedSql + Messages.getString("MysqlIO.25");
         }
      }

      return extractedSql;
   }

   public static int getBinaryEncodedLength(int type) {
      switch(type) {
      case 0:
      case 7:
      case 10:
      case 11:
      case 12:
      case 15:
      case 16:
      case 245:
      case 246:
      case 249:
      case 250:
      case 251:
      case 252:
      case 253:
      case 254:
      case 255:
         return 0;
      case 1:
         return 1;
      case 2:
      case 13:
         return 2;
      case 3:
      case 4:
      case 9:
         return 4;
      case 5:
      case 8:
         return 8;
      default:
         return -1;
      }
   }
}
