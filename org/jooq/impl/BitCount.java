package org.jooq.impl;

import org.jooq.Configuration;
import org.jooq.Field;

final class BitCount extends AbstractFunction<Integer> {
   private static final long serialVersionUID = 7624782102883057433L;

   BitCount(Field<?> field) {
      super("bit_count", SQLDataType.INTEGER, field);
   }

   final Field<Integer> getFunction0(Configuration configuration) {
      Field<?> field = this.getArguments()[0];
      switch(configuration.family()) {
      case MARIADB:
      case MYSQL:
         return DSL.function("bit_count", this.getDataType(), this.getArguments());
      case H2:
      case HSQLDB:
         if (field.getType() == Byte.class) {
            return DSL.bitAnd((Field)field, (Field)DSL.inline((byte)1)).add(DSL.bitAnd((Field)field, (Field)DSL.inline((byte)2)).div((Field)DSL.inline((byte)2))).add(DSL.bitAnd((Field)field, (Field)DSL.inline((byte)4)).div((Field)DSL.inline((byte)4))).add(DSL.bitAnd((Field)field, (Field)DSL.inline((byte)8)).div((Field)DSL.inline((byte)8))).add(DSL.bitAnd((Field)field, (Field)DSL.inline((byte)16)).div((Field)DSL.inline((byte)16))).add(DSL.bitAnd((Field)field, (Field)DSL.inline((byte)32)).div((Field)DSL.inline((byte)32))).add(DSL.bitAnd((Field)field, (Field)DSL.inline((byte)64)).div((Field)DSL.inline((byte)64))).add(DSL.bitAnd((Field)field, (Field)DSL.inline((byte)-128)).div((Field)DSL.inline((byte)-128))).cast(Integer.class);
         } else if (field.getType() == Short.class) {
            return DSL.bitAnd((Field)field, (Field)DSL.inline((short)1)).add(DSL.bitAnd((Field)field, (Field)DSL.inline((short)2)).div((Field)DSL.inline((short)2))).add(DSL.bitAnd((Field)field, (Field)DSL.inline((short)4)).div((Field)DSL.inline((short)4))).add(DSL.bitAnd((Field)field, (Field)DSL.inline((short)8)).div((Field)DSL.inline((short)8))).add(DSL.bitAnd((Field)field, (Field)DSL.inline((short)16)).div((Field)DSL.inline((short)16))).add(DSL.bitAnd((Field)field, (Field)DSL.inline((short)32)).div((Field)DSL.inline((short)32))).add(DSL.bitAnd((Field)field, (Field)DSL.inline((short)64)).div((Field)DSL.inline((short)64))).add(DSL.bitAnd((Field)field, (Field)DSL.inline((short)128)).div((Field)DSL.inline((short)128))).add(DSL.bitAnd((Field)field, (Field)DSL.inline((short)256)).div((Field)DSL.inline((short)256))).add(DSL.bitAnd((Field)field, (Field)DSL.inline((short)512)).div((Field)DSL.inline((short)512))).add(DSL.bitAnd((Field)field, (Field)DSL.inline((short)1024)).div((Field)DSL.inline((short)1024))).add(DSL.bitAnd((Field)field, (Field)DSL.inline((short)2048)).div((Field)DSL.inline((short)2048))).add(DSL.bitAnd((Field)field, (Field)DSL.inline((short)4096)).div((Field)DSL.inline((short)4096))).add(DSL.bitAnd((Field)field, (Field)DSL.inline((short)8192)).div((Field)DSL.inline((short)8192))).add(DSL.bitAnd((Field)field, (Field)DSL.inline((short)16384)).div((Field)DSL.inline((short)16384))).add(DSL.bitAnd((Field)field, (Field)DSL.inline((short)-32768)).div((Field)DSL.inline((short)-32768))).cast(Integer.class);
         } else if (field.getType() == Integer.class) {
            return DSL.bitAnd((Field)field, (Field)DSL.inline((int)1)).add(DSL.bitAnd((Field)field, (Field)DSL.inline((int)2)).div((Field)DSL.inline((int)2))).add(DSL.bitAnd((Field)field, (Field)DSL.inline((int)4)).div((Field)DSL.inline((int)4))).add(DSL.bitAnd((Field)field, (Field)DSL.inline((int)8)).div((Field)DSL.inline((int)8))).add(DSL.bitAnd((Field)field, (Field)DSL.inline((int)16)).div((Field)DSL.inline((int)16))).add(DSL.bitAnd((Field)field, (Field)DSL.inline((int)32)).div((Field)DSL.inline((int)32))).add(DSL.bitAnd((Field)field, (Field)DSL.inline((int)64)).div((Field)DSL.inline((int)64))).add(DSL.bitAnd((Field)field, (Field)DSL.inline((int)128)).div((Field)DSL.inline((int)128))).add(DSL.bitAnd((Field)field, (Field)DSL.inline((int)256)).div((Field)DSL.inline((int)256))).add(DSL.bitAnd((Field)field, (Field)DSL.inline((int)512)).div((Field)DSL.inline((int)512))).add(DSL.bitAnd((Field)field, (Field)DSL.inline((int)1024)).div((Field)DSL.inline((int)1024))).add(DSL.bitAnd((Field)field, (Field)DSL.inline((int)2048)).div((Field)DSL.inline((int)2048))).add(DSL.bitAnd((Field)field, (Field)DSL.inline((int)4096)).div((Field)DSL.inline((int)4096))).add(DSL.bitAnd((Field)field, (Field)DSL.inline((int)8192)).div((Field)DSL.inline((int)8192))).add(DSL.bitAnd((Field)field, (Field)DSL.inline((int)16384)).div((Field)DSL.inline((int)16384))).add(DSL.bitAnd((Field)field, (Field)DSL.inline((int)32768)).div((Field)DSL.inline((int)32768))).add(DSL.bitAnd((Field)field, (Field)DSL.inline(65536)).div((Field)DSL.inline(65536))).add(DSL.bitAnd((Field)field, (Field)DSL.inline(131072)).div((Field)DSL.inline(131072))).add(DSL.bitAnd((Field)field, (Field)DSL.inline(262144)).div((Field)DSL.inline(262144))).add(DSL.bitAnd((Field)field, (Field)DSL.inline(524288)).div((Field)DSL.inline(524288))).add(DSL.bitAnd((Field)field, (Field)DSL.inline(1048576)).div((Field)DSL.inline(1048576))).add(DSL.bitAnd((Field)field, (Field)DSL.inline(2097152)).div((Field)DSL.inline(2097152))).add(DSL.bitAnd((Field)field, (Field)DSL.inline(4194304)).div((Field)DSL.inline(4194304))).add(DSL.bitAnd((Field)field, (Field)DSL.inline(8388608)).div((Field)DSL.inline(8388608))).add(DSL.bitAnd((Field)field, (Field)DSL.inline(16777216)).div((Field)DSL.inline(16777216))).add(DSL.bitAnd((Field)field, (Field)DSL.inline(33554432)).div((Field)DSL.inline(33554432))).add(DSL.bitAnd((Field)field, (Field)DSL.inline(67108864)).div((Field)DSL.inline(67108864))).add(DSL.bitAnd((Field)field, (Field)DSL.inline(134217728)).div((Field)DSL.inline(134217728))).add(DSL.bitAnd((Field)field, (Field)DSL.inline(268435456)).div((Field)DSL.inline(268435456))).add(DSL.bitAnd((Field)field, (Field)DSL.inline(536870912)).div((Field)DSL.inline(536870912))).add(DSL.bitAnd((Field)field, (Field)DSL.inline(1073741824)).div((Field)DSL.inline(1073741824))).add(DSL.bitAnd((Field)field, (Field)DSL.inline(Integer.MIN_VALUE)).div((Field)DSL.inline(Integer.MIN_VALUE)));
         } else {
            if (field.getType() == Long.class) {
               return DSL.bitAnd((Field)field, (Field)DSL.inline(1L)).add(DSL.bitAnd((Field)field, (Field)DSL.inline(2L)).div((Field)DSL.inline(2L))).add(DSL.bitAnd((Field)field, (Field)DSL.inline(4L)).div((Field)DSL.inline(4L))).add(DSL.bitAnd((Field)field, (Field)DSL.inline(8L)).div((Field)DSL.inline(8L))).add(DSL.bitAnd((Field)field, (Field)DSL.inline(16L)).div((Field)DSL.inline(16L))).add(DSL.bitAnd((Field)field, (Field)DSL.inline(32L)).div((Field)DSL.inline(32L))).add(DSL.bitAnd((Field)field, (Field)DSL.inline(64L)).div((Field)DSL.inline(64L))).add(DSL.bitAnd((Field)field, (Field)DSL.inline(128L)).div((Field)DSL.inline(128L))).add(DSL.bitAnd((Field)field, (Field)DSL.inline(256L)).div((Field)DSL.inline(256L))).add(DSL.bitAnd((Field)field, (Field)DSL.inline(512L)).div((Field)DSL.inline(512L))).add(DSL.bitAnd((Field)field, (Field)DSL.inline(1024L)).div((Field)DSL.inline(1024L))).add(DSL.bitAnd((Field)field, (Field)DSL.inline(2048L)).div((Field)DSL.inline(2048L))).add(DSL.bitAnd((Field)field, (Field)DSL.inline(4096L)).div((Field)DSL.inline(4096L))).add(DSL.bitAnd((Field)field, (Field)DSL.inline(8192L)).div((Field)DSL.inline(8192L))).add(DSL.bitAnd((Field)field, (Field)DSL.inline(16384L)).div((Field)DSL.inline(16384L))).add(DSL.bitAnd((Field)field, (Field)DSL.inline(32768L)).div((Field)DSL.inline(32768L))).add(DSL.bitAnd((Field)field, (Field)DSL.inline(65536L)).div((Field)DSL.inline(65536L))).add(DSL.bitAnd((Field)field, (Field)DSL.inline(131072L)).div((Field)DSL.inline(131072L))).add(DSL.bitAnd((Field)field, (Field)DSL.inline(262144L)).div((Field)DSL.inline(262144L))).add(DSL.bitAnd((Field)field, (Field)DSL.inline(524288L)).div((Field)DSL.inline(524288L))).add(DSL.bitAnd((Field)field, (Field)DSL.inline(1048576L)).div((Field)DSL.inline(1048576L))).add(DSL.bitAnd((Field)field, (Field)DSL.inline(2097152L)).div((Field)DSL.inline(2097152L))).add(DSL.bitAnd((Field)field, (Field)DSL.inline(4194304L)).div((Field)DSL.inline(4194304L))).add(DSL.bitAnd((Field)field, (Field)DSL.inline(8388608L)).div((Field)DSL.inline(8388608L))).add(DSL.bitAnd((Field)field, (Field)DSL.inline(16777216L)).div((Field)DSL.inline(16777216L))).add(DSL.bitAnd((Field)field, (Field)DSL.inline(33554432L)).div((Field)DSL.inline(33554432L))).add(DSL.bitAnd((Field)field, (Field)DSL.inline(67108864L)).div((Field)DSL.inline(67108864L))).add(DSL.bitAnd((Field)field, (Field)DSL.inline(134217728L)).div((Field)DSL.inline(134217728L))).add(DSL.bitAnd((Field)field, (Field)DSL.inline(268435456L)).div((Field)DSL.inline(268435456L))).add(DSL.bitAnd((Field)field, (Field)DSL.inline(536870912L)).div((Field)DSL.inline(536870912L))).add(DSL.bitAnd((Field)field, (Field)DSL.inline(1073741824L)).div((Field)DSL.inline(1073741824L))).add(DSL.bitAnd((Field)field, (Field)DSL.inline(2147483648L)).div((Field)DSL.inline(2147483648L))).add(DSL.bitAnd((Field)field, (Field)DSL.inline(4294967296L)).div((Field)DSL.inline(4294967296L))).add(DSL.bitAnd((Field)field, (Field)DSL.inline(8589934592L)).div((Field)DSL.inline(8589934592L))).add(DSL.bitAnd((Field)field, (Field)DSL.inline(17179869184L)).div((Field)DSL.inline(17179869184L))).add(DSL.bitAnd((Field)field, (Field)DSL.inline(34359738368L)).div((Field)DSL.inline(34359738368L))).add(DSL.bitAnd((Field)field, (Field)DSL.inline(68719476736L)).div((Field)DSL.inline(68719476736L))).add(DSL.bitAnd((Field)field, (Field)DSL.inline(137438953472L)).div((Field)DSL.inline(137438953472L))).add(DSL.bitAnd((Field)field, (Field)DSL.inline(274877906944L)).div((Field)DSL.inline(274877906944L))).add(DSL.bitAnd((Field)field, (Field)DSL.inline(549755813888L)).div((Field)DSL.inline(549755813888L))).add(DSL.bitAnd((Field)field, (Field)DSL.inline(1099511627776L)).div((Field)DSL.inline(1099511627776L))).add(DSL.bitAnd((Field)field, (Field)DSL.inline(2199023255552L)).div((Field)DSL.inline(2199023255552L))).add(DSL.bitAnd((Field)field, (Field)DSL.inline(4398046511104L)).div((Field)DSL.inline(4398046511104L))).add(DSL.bitAnd((Field)field, (Field)DSL.inline(8796093022208L)).div((Field)DSL.inline(8796093022208L))).add(DSL.bitAnd((Field)field, (Field)DSL.inline(17592186044416L)).div((Field)DSL.inline(17592186044416L))).add(DSL.bitAnd((Field)field, (Field)DSL.inline(35184372088832L)).div((Field)DSL.inline(35184372088832L))).add(DSL.bitAnd((Field)field, (Field)DSL.inline(70368744177664L)).div((Field)DSL.inline(70368744177664L))).add(DSL.bitAnd((Field)field, (Field)DSL.inline(140737488355328L)).div((Field)DSL.inline(140737488355328L))).add(DSL.bitAnd((Field)field, (Field)DSL.inline(281474976710656L)).div((Field)DSL.inline(281474976710656L))).add(DSL.bitAnd((Field)field, (Field)DSL.inline(562949953421312L)).div((Field)DSL.inline(562949953421312L))).add(DSL.bitAnd((Field)field, (Field)DSL.inline(1125899906842624L)).div((Field)DSL.inline(1125899906842624L))).add(DSL.bitAnd((Field)field, (Field)DSL.inline(2251799813685248L)).div((Field)DSL.inline(2251799813685248L))).add(DSL.bitAnd((Field)field, (Field)DSL.inline(4503599627370496L)).div((Field)DSL.inline(4503599627370496L))).add(DSL.bitAnd((Field)field, (Field)DSL.inline(9007199254740992L)).div((Field)DSL.inline(9007199254740992L))).add(DSL.bitAnd((Field)field, (Field)DSL.inline(18014398509481984L)).div((Field)DSL.inline(18014398509481984L))).add(DSL.bitAnd((Field)field, (Field)DSL.inline(36028797018963968L)).div((Field)DSL.inline(36028797018963968L))).add(DSL.bitAnd((Field)field, (Field)DSL.inline(72057594037927936L)).div((Field)DSL.inline(72057594037927936L))).add(DSL.bitAnd((Field)field, (Field)DSL.inline(144115188075855872L)).div((Field)DSL.inline(144115188075855872L))).add(DSL.bitAnd((Field)field, (Field)DSL.inline(288230376151711744L)).div((Field)DSL.inline(288230376151711744L))).add(DSL.bitAnd((Field)field, (Field)DSL.inline(576460752303423488L)).div((Field)DSL.inline(576460752303423488L))).add(DSL.bitAnd((Field)field, (Field)DSL.inline(1152921504606846976L)).div((Field)DSL.inline(1152921504606846976L))).add(DSL.bitAnd((Field)field, (Field)DSL.inline(2305843009213693952L)).div((Field)DSL.inline(2305843009213693952L))).add(DSL.bitAnd((Field)field, (Field)DSL.inline(4611686018427387904L)).div((Field)DSL.inline(4611686018427387904L))).add(DSL.bitAnd((Field)field, (Field)DSL.inline(Long.MIN_VALUE)).div((Field)DSL.inline(0L))).cast(Integer.class);
            }

            return DSL.function("bit_count", this.getDataType(), this.getArguments());
         }
      default:
         Field var10000;
         Field var10001;
         byte i;
         if (field.getType() == Byte.class) {
            i = 0;
            var10000 = DSL.bitAnd((Field)field, (Field)DSL.inline((byte)1));
            var10001 = DSL.bitAnd((Field)field, (Field)DSL.inline((byte)2));
            byte i = (byte)(i + 1);
            var10000 = var10000.add(DSL.shr((Field)var10001, (Field)DSL.inline(i)));
            var10001 = DSL.bitAnd((Field)field, (Field)DSL.inline((byte)4));
            ++i;
            var10000 = var10000.add(DSL.shr((Field)var10001, (Field)DSL.inline(i)));
            var10001 = DSL.bitAnd((Field)field, (Field)DSL.inline((byte)8));
            ++i;
            var10000 = var10000.add(DSL.shr((Field)var10001, (Field)DSL.inline(i)));
            var10001 = DSL.bitAnd((Field)field, (Field)DSL.inline((byte)16));
            ++i;
            var10000 = var10000.add(DSL.shr((Field)var10001, (Field)DSL.inline(i)));
            var10001 = DSL.bitAnd((Field)field, (Field)DSL.inline((byte)32));
            ++i;
            var10000 = var10000.add(DSL.shr((Field)var10001, (Field)DSL.inline(i)));
            var10001 = DSL.bitAnd((Field)field, (Field)DSL.inline((byte)64));
            ++i;
            var10000 = var10000.add(DSL.shr((Field)var10001, (Field)DSL.inline(i)));
            var10001 = DSL.bitAnd((Field)field, (Field)DSL.inline((byte)-128));
            ++i;
            return var10000.add(DSL.shr((Field)var10001, (Field)DSL.inline(i))).cast(Integer.class);
         } else if (field.getType() == Short.class) {
            i = 0;
            var10000 = DSL.bitAnd((Field)field, (Field)DSL.inline((short)1));
            var10001 = DSL.bitAnd((Field)field, (Field)DSL.inline((short)2));
            short i = (short)(i + 1);
            var10000 = var10000.add(DSL.shr((Field)var10001, (Field)DSL.inline(i)));
            var10001 = DSL.bitAnd((Field)field, (Field)DSL.inline((short)4));
            ++i;
            var10000 = var10000.add(DSL.shr((Field)var10001, (Field)DSL.inline(i)));
            var10001 = DSL.bitAnd((Field)field, (Field)DSL.inline((short)8));
            ++i;
            var10000 = var10000.add(DSL.shr((Field)var10001, (Field)DSL.inline(i)));
            var10001 = DSL.bitAnd((Field)field, (Field)DSL.inline((short)16));
            ++i;
            var10000 = var10000.add(DSL.shr((Field)var10001, (Field)DSL.inline(i)));
            var10001 = DSL.bitAnd((Field)field, (Field)DSL.inline((short)32));
            ++i;
            var10000 = var10000.add(DSL.shr((Field)var10001, (Field)DSL.inline(i)));
            var10001 = DSL.bitAnd((Field)field, (Field)DSL.inline((short)64));
            ++i;
            var10000 = var10000.add(DSL.shr((Field)var10001, (Field)DSL.inline(i)));
            var10001 = DSL.bitAnd((Field)field, (Field)DSL.inline((short)128));
            ++i;
            var10000 = var10000.add(DSL.shr((Field)var10001, (Field)DSL.inline(i)));
            var10001 = DSL.bitAnd((Field)field, (Field)DSL.inline((short)256));
            ++i;
            var10000 = var10000.add(DSL.shr((Field)var10001, (Field)DSL.inline(i)));
            var10001 = DSL.bitAnd((Field)field, (Field)DSL.inline((short)512));
            ++i;
            var10000 = var10000.add(DSL.shr((Field)var10001, (Field)DSL.inline(i)));
            var10001 = DSL.bitAnd((Field)field, (Field)DSL.inline((short)1024));
            ++i;
            var10000 = var10000.add(DSL.shr((Field)var10001, (Field)DSL.inline(i)));
            var10001 = DSL.bitAnd((Field)field, (Field)DSL.inline((short)2048));
            ++i;
            var10000 = var10000.add(DSL.shr((Field)var10001, (Field)DSL.inline(i)));
            var10001 = DSL.bitAnd((Field)field, (Field)DSL.inline((short)4096));
            ++i;
            var10000 = var10000.add(DSL.shr((Field)var10001, (Field)DSL.inline(i)));
            var10001 = DSL.bitAnd((Field)field, (Field)DSL.inline((short)8192));
            ++i;
            var10000 = var10000.add(DSL.shr((Field)var10001, (Field)DSL.inline(i)));
            var10001 = DSL.bitAnd((Field)field, (Field)DSL.inline((short)16384));
            ++i;
            var10000 = var10000.add(DSL.shr((Field)var10001, (Field)DSL.inline(i)));
            var10001 = DSL.bitAnd((Field)field, (Field)DSL.inline((short)-32768));
            ++i;
            return var10000.add(DSL.shr((Field)var10001, (Field)DSL.inline(i))).cast(Integer.class);
         } else if (field.getType() == Integer.class) {
            i = 0;
            var10000 = DSL.bitAnd((Field)field, (Field)DSL.inline((int)1));
            var10001 = DSL.bitAnd((Field)field, (Field)DSL.inline((int)2));
            int i = i + 1;
            var10000 = var10000.add(DSL.shr((Field)var10001, (Field)DSL.inline(i)));
            var10001 = DSL.bitAnd((Field)field, (Field)DSL.inline((int)4));
            ++i;
            var10000 = var10000.add(DSL.shr((Field)var10001, (Field)DSL.inline(i)));
            var10001 = DSL.bitAnd((Field)field, (Field)DSL.inline((int)8));
            ++i;
            var10000 = var10000.add(DSL.shr((Field)var10001, (Field)DSL.inline(i)));
            var10001 = DSL.bitAnd((Field)field, (Field)DSL.inline((int)16));
            ++i;
            var10000 = var10000.add(DSL.shr((Field)var10001, (Field)DSL.inline(i)));
            var10001 = DSL.bitAnd((Field)field, (Field)DSL.inline((int)32));
            ++i;
            var10000 = var10000.add(DSL.shr((Field)var10001, (Field)DSL.inline(i)));
            var10001 = DSL.bitAnd((Field)field, (Field)DSL.inline((int)64));
            ++i;
            var10000 = var10000.add(DSL.shr((Field)var10001, (Field)DSL.inline(i)));
            var10001 = DSL.bitAnd((Field)field, (Field)DSL.inline((int)128));
            ++i;
            var10000 = var10000.add(DSL.shr((Field)var10001, (Field)DSL.inline(i)));
            var10001 = DSL.bitAnd((Field)field, (Field)DSL.inline((int)256));
            ++i;
            var10000 = var10000.add(DSL.shr((Field)var10001, (Field)DSL.inline(i)));
            var10001 = DSL.bitAnd((Field)field, (Field)DSL.inline((int)512));
            ++i;
            var10000 = var10000.add(DSL.shr((Field)var10001, (Field)DSL.inline(i)));
            var10001 = DSL.bitAnd((Field)field, (Field)DSL.inline((int)1024));
            ++i;
            var10000 = var10000.add(DSL.shr((Field)var10001, (Field)DSL.inline(i)));
            var10001 = DSL.bitAnd((Field)field, (Field)DSL.inline((int)2048));
            ++i;
            var10000 = var10000.add(DSL.shr((Field)var10001, (Field)DSL.inline(i)));
            var10001 = DSL.bitAnd((Field)field, (Field)DSL.inline((int)4096));
            ++i;
            var10000 = var10000.add(DSL.shr((Field)var10001, (Field)DSL.inline(i)));
            var10001 = DSL.bitAnd((Field)field, (Field)DSL.inline((int)8192));
            ++i;
            var10000 = var10000.add(DSL.shr((Field)var10001, (Field)DSL.inline(i)));
            var10001 = DSL.bitAnd((Field)field, (Field)DSL.inline((int)16384));
            ++i;
            var10000 = var10000.add(DSL.shr((Field)var10001, (Field)DSL.inline(i)));
            var10001 = DSL.bitAnd((Field)field, (Field)DSL.inline((int)32768));
            ++i;
            var10000 = var10000.add(DSL.shr((Field)var10001, (Field)DSL.inline(i)));
            var10001 = DSL.bitAnd((Field)field, (Field)DSL.inline(65536));
            ++i;
            var10000 = var10000.add(DSL.shr((Field)var10001, (Field)DSL.inline(i)));
            var10001 = DSL.bitAnd((Field)field, (Field)DSL.inline(131072));
            ++i;
            var10000 = var10000.add(DSL.shr((Field)var10001, (Field)DSL.inline(i)));
            var10001 = DSL.bitAnd((Field)field, (Field)DSL.inline(262144));
            ++i;
            var10000 = var10000.add(DSL.shr((Field)var10001, (Field)DSL.inline(i)));
            var10001 = DSL.bitAnd((Field)field, (Field)DSL.inline(524288));
            ++i;
            var10000 = var10000.add(DSL.shr((Field)var10001, (Field)DSL.inline(i)));
            var10001 = DSL.bitAnd((Field)field, (Field)DSL.inline(1048576));
            ++i;
            var10000 = var10000.add(DSL.shr((Field)var10001, (Field)DSL.inline(i)));
            var10001 = DSL.bitAnd((Field)field, (Field)DSL.inline(2097152));
            ++i;
            var10000 = var10000.add(DSL.shr((Field)var10001, (Field)DSL.inline(i)));
            var10001 = DSL.bitAnd((Field)field, (Field)DSL.inline(4194304));
            ++i;
            var10000 = var10000.add(DSL.shr((Field)var10001, (Field)DSL.inline(i)));
            var10001 = DSL.bitAnd((Field)field, (Field)DSL.inline(8388608));
            ++i;
            var10000 = var10000.add(DSL.shr((Field)var10001, (Field)DSL.inline(i)));
            var10001 = DSL.bitAnd((Field)field, (Field)DSL.inline(16777216));
            ++i;
            var10000 = var10000.add(DSL.shr((Field)var10001, (Field)DSL.inline(i)));
            var10001 = DSL.bitAnd((Field)field, (Field)DSL.inline(33554432));
            ++i;
            var10000 = var10000.add(DSL.shr((Field)var10001, (Field)DSL.inline(i)));
            var10001 = DSL.bitAnd((Field)field, (Field)DSL.inline(67108864));
            ++i;
            var10000 = var10000.add(DSL.shr((Field)var10001, (Field)DSL.inline(i)));
            var10001 = DSL.bitAnd((Field)field, (Field)DSL.inline(134217728));
            ++i;
            var10000 = var10000.add(DSL.shr((Field)var10001, (Field)DSL.inline(i)));
            var10001 = DSL.bitAnd((Field)field, (Field)DSL.inline(268435456));
            ++i;
            var10000 = var10000.add(DSL.shr((Field)var10001, (Field)DSL.inline(i)));
            var10001 = DSL.bitAnd((Field)field, (Field)DSL.inline(536870912));
            ++i;
            var10000 = var10000.add(DSL.shr((Field)var10001, (Field)DSL.inline(i)));
            var10001 = DSL.bitAnd((Field)field, (Field)DSL.inline(1073741824));
            ++i;
            var10000 = var10000.add(DSL.shr((Field)var10001, (Field)DSL.inline(i)));
            var10001 = DSL.bitAnd((Field)field, (Field)DSL.inline(Integer.MIN_VALUE));
            ++i;
            return var10000.add(DSL.shr((Field)var10001, (Field)DSL.inline(i)));
         } else if (field.getType() == Long.class) {
            long i = 0L;
            return DSL.bitAnd((Field)field, (Field)DSL.inline(1L)).add(DSL.shr((Field)DSL.bitAnd((Field)field, (Field)DSL.inline(2L)), (Field)DSL.inline(++i))).add(DSL.shr((Field)DSL.bitAnd((Field)field, (Field)DSL.inline(4L)), (Field)DSL.inline(++i))).add(DSL.shr((Field)DSL.bitAnd((Field)field, (Field)DSL.inline(8L)), (Field)DSL.inline(++i))).add(DSL.shr((Field)DSL.bitAnd((Field)field, (Field)DSL.inline(16L)), (Field)DSL.inline(++i))).add(DSL.shr((Field)DSL.bitAnd((Field)field, (Field)DSL.inline(32L)), (Field)DSL.inline(++i))).add(DSL.shr((Field)DSL.bitAnd((Field)field, (Field)DSL.inline(64L)), (Field)DSL.inline(++i))).add(DSL.shr((Field)DSL.bitAnd((Field)field, (Field)DSL.inline(128L)), (Field)DSL.inline(++i))).add(DSL.shr((Field)DSL.bitAnd((Field)field, (Field)DSL.inline(256L)), (Field)DSL.inline(++i))).add(DSL.shr((Field)DSL.bitAnd((Field)field, (Field)DSL.inline(512L)), (Field)DSL.inline(++i))).add(DSL.shr((Field)DSL.bitAnd((Field)field, (Field)DSL.inline(1024L)), (Field)DSL.inline(++i))).add(DSL.shr((Field)DSL.bitAnd((Field)field, (Field)DSL.inline(2048L)), (Field)DSL.inline(++i))).add(DSL.shr((Field)DSL.bitAnd((Field)field, (Field)DSL.inline(4096L)), (Field)DSL.inline(++i))).add(DSL.shr((Field)DSL.bitAnd((Field)field, (Field)DSL.inline(8192L)), (Field)DSL.inline(++i))).add(DSL.shr((Field)DSL.bitAnd((Field)field, (Field)DSL.inline(16384L)), (Field)DSL.inline(++i))).add(DSL.shr((Field)DSL.bitAnd((Field)field, (Field)DSL.inline(32768L)), (Field)DSL.inline(++i))).add(DSL.shr((Field)DSL.bitAnd((Field)field, (Field)DSL.inline(65536L)), (Field)DSL.inline(++i))).add(DSL.shr((Field)DSL.bitAnd((Field)field, (Field)DSL.inline(131072L)), (Field)DSL.inline(++i))).add(DSL.shr((Field)DSL.bitAnd((Field)field, (Field)DSL.inline(262144L)), (Field)DSL.inline(++i))).add(DSL.shr((Field)DSL.bitAnd((Field)field, (Field)DSL.inline(524288L)), (Field)DSL.inline(++i))).add(DSL.shr((Field)DSL.bitAnd((Field)field, (Field)DSL.inline(1048576L)), (Field)DSL.inline(++i))).add(DSL.shr((Field)DSL.bitAnd((Field)field, (Field)DSL.inline(2097152L)), (Field)DSL.inline(++i))).add(DSL.shr((Field)DSL.bitAnd((Field)field, (Field)DSL.inline(4194304L)), (Field)DSL.inline(++i))).add(DSL.shr((Field)DSL.bitAnd((Field)field, (Field)DSL.inline(8388608L)), (Field)DSL.inline(++i))).add(DSL.shr((Field)DSL.bitAnd((Field)field, (Field)DSL.inline(16777216L)), (Field)DSL.inline(++i))).add(DSL.shr((Field)DSL.bitAnd((Field)field, (Field)DSL.inline(33554432L)), (Field)DSL.inline(++i))).add(DSL.shr((Field)DSL.bitAnd((Field)field, (Field)DSL.inline(67108864L)), (Field)DSL.inline(++i))).add(DSL.shr((Field)DSL.bitAnd((Field)field, (Field)DSL.inline(134217728L)), (Field)DSL.inline(++i))).add(DSL.shr((Field)DSL.bitAnd((Field)field, (Field)DSL.inline(268435456L)), (Field)DSL.inline(++i))).add(DSL.shr((Field)DSL.bitAnd((Field)field, (Field)DSL.inline(536870912L)), (Field)DSL.inline(++i))).add(DSL.shr((Field)DSL.bitAnd((Field)field, (Field)DSL.inline(1073741824L)), (Field)DSL.inline(++i))).add(DSL.shr((Field)DSL.bitAnd((Field)field, (Field)DSL.inline(2147483648L)), (Field)DSL.inline(++i))).add(DSL.shr((Field)DSL.bitAnd((Field)field, (Field)DSL.inline(4294967296L)), (Field)DSL.inline(++i))).add(DSL.shr((Field)DSL.bitAnd((Field)field, (Field)DSL.inline(8589934592L)), (Field)DSL.inline(++i))).add(DSL.shr((Field)DSL.bitAnd((Field)field, (Field)DSL.inline(17179869184L)), (Field)DSL.inline(++i))).add(DSL.shr((Field)DSL.bitAnd((Field)field, (Field)DSL.inline(34359738368L)), (Field)DSL.inline(++i))).add(DSL.shr((Field)DSL.bitAnd((Field)field, (Field)DSL.inline(68719476736L)), (Field)DSL.inline(++i))).add(DSL.shr((Field)DSL.bitAnd((Field)field, (Field)DSL.inline(137438953472L)), (Field)DSL.inline(++i))).add(DSL.shr((Field)DSL.bitAnd((Field)field, (Field)DSL.inline(274877906944L)), (Field)DSL.inline(++i))).add(DSL.shr((Field)DSL.bitAnd((Field)field, (Field)DSL.inline(549755813888L)), (Field)DSL.inline(++i))).add(DSL.shr((Field)DSL.bitAnd((Field)field, (Field)DSL.inline(1099511627776L)), (Field)DSL.inline(++i))).add(DSL.shr((Field)DSL.bitAnd((Field)field, (Field)DSL.inline(2199023255552L)), (Field)DSL.inline(++i))).add(DSL.shr((Field)DSL.bitAnd((Field)field, (Field)DSL.inline(4398046511104L)), (Field)DSL.inline(++i))).add(DSL.shr((Field)DSL.bitAnd((Field)field, (Field)DSL.inline(8796093022208L)), (Field)DSL.inline(++i))).add(DSL.shr((Field)DSL.bitAnd((Field)field, (Field)DSL.inline(17592186044416L)), (Field)DSL.inline(++i))).add(DSL.shr((Field)DSL.bitAnd((Field)field, (Field)DSL.inline(35184372088832L)), (Field)DSL.inline(++i))).add(DSL.shr((Field)DSL.bitAnd((Field)field, (Field)DSL.inline(70368744177664L)), (Field)DSL.inline(++i))).add(DSL.shr((Field)DSL.bitAnd((Field)field, (Field)DSL.inline(140737488355328L)), (Field)DSL.inline(++i))).add(DSL.shr((Field)DSL.bitAnd((Field)field, (Field)DSL.inline(281474976710656L)), (Field)DSL.inline(++i))).add(DSL.shr((Field)DSL.bitAnd((Field)field, (Field)DSL.inline(562949953421312L)), (Field)DSL.inline(++i))).add(DSL.shr((Field)DSL.bitAnd((Field)field, (Field)DSL.inline(1125899906842624L)), (Field)DSL.inline(++i))).add(DSL.shr((Field)DSL.bitAnd((Field)field, (Field)DSL.inline(2251799813685248L)), (Field)DSL.inline(++i))).add(DSL.shr((Field)DSL.bitAnd((Field)field, (Field)DSL.inline(4503599627370496L)), (Field)DSL.inline(++i))).add(DSL.shr((Field)DSL.bitAnd((Field)field, (Field)DSL.inline(9007199254740992L)), (Field)DSL.inline(++i))).add(DSL.shr((Field)DSL.bitAnd((Field)field, (Field)DSL.inline(18014398509481984L)), (Field)DSL.inline(++i))).add(DSL.shr((Field)DSL.bitAnd((Field)field, (Field)DSL.inline(36028797018963968L)), (Field)DSL.inline(++i))).add(DSL.shr((Field)DSL.bitAnd((Field)field, (Field)DSL.inline(72057594037927936L)), (Field)DSL.inline(++i))).add(DSL.shr((Field)DSL.bitAnd((Field)field, (Field)DSL.inline(144115188075855872L)), (Field)DSL.inline(++i))).add(DSL.shr((Field)DSL.bitAnd((Field)field, (Field)DSL.inline(288230376151711744L)), (Field)DSL.inline(++i))).add(DSL.shr((Field)DSL.bitAnd((Field)field, (Field)DSL.inline(576460752303423488L)), (Field)DSL.inline(++i))).add(DSL.shr((Field)DSL.bitAnd((Field)field, (Field)DSL.inline(1152921504606846976L)), (Field)DSL.inline(++i))).add(DSL.shr((Field)DSL.bitAnd((Field)field, (Field)DSL.inline(2305843009213693952L)), (Field)DSL.inline(++i))).add(DSL.shr((Field)DSL.bitAnd((Field)field, (Field)DSL.inline(4611686018427387904L)), (Field)DSL.inline(++i))).add(DSL.shr((Field)DSL.bitAnd((Field)field, (Field)DSL.inline(Long.MIN_VALUE)), (Field)DSL.inline(++i))).cast(Integer.class);
         } else {
            return DSL.function("bit_count", this.getDataType(), this.getArguments());
         }
      }
   }
}
