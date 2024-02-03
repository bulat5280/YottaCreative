package org.jooq.util.mysql;

import org.jooq.EnumType;
import org.jooq.Field;
import org.jooq.SQLDialect;
import org.jooq.Support;
import org.jooq.impl.DSL;

public class MySQLDSL extends DSL {
   protected MySQLDSL() {
   }

   @Support({SQLDialect.MYSQL})
   public static Field<String> decode(String cryptString, String keyString) {
      return decode((Field)val(cryptString), (Field)val(keyString));
   }

   @Support({SQLDialect.MYSQL})
   public static Field<byte[]> decode(byte[] cryptString, byte[] keyString) {
      return decode((Field)val(cryptString), (Field)val(keyString));
   }

   @Support({SQLDialect.MYSQL})
   public static <T> Field<T> decode(Field<T> cryptString, Field<T> keyString) {
      return function("decode", cryptString.getType(), new Field[]{cryptString, keyString});
   }

   @Support({SQLDialect.MYSQL})
   public static Field<String> encode(String string, String keyString) {
      return encode((Field)val(string), (Field)val(keyString));
   }

   @Support({SQLDialect.MYSQL})
   public static Field<byte[]> encode(byte[] string, byte[] keyString) {
      return encode((Field)val(string), (Field)val(keyString));
   }

   @Support({SQLDialect.MYSQL})
   public static <T> Field<T> encode(Field<T> string, Field<T> keyString) {
      return function("encode", string.getType(), new Field[]{string, keyString});
   }

   @Support({SQLDialect.MYSQL})
   public static Field<String> aesDecrypt(String cryptString, String keyString) {
      return aesDecrypt((Field)val(cryptString), (Field)val(keyString));
   }

   @Support({SQLDialect.MYSQL})
   public static Field<byte[]> aesDecrypt(byte[] cryptString, byte[] keyString) {
      return aesDecrypt((Field)val(cryptString), (Field)val(keyString));
   }

   @Support({SQLDialect.MYSQL})
   public static <T> Field<T> aesDecrypt(Field<T> cryptString, Field<T> keyString) {
      return function("aes_decrypt", cryptString.getType(), new Field[]{cryptString, keyString});
   }

   @Support({SQLDialect.MYSQL})
   public static Field<String> aesEncrypt(String string, String keyString) {
      return aesEncrypt((Field)val(string), (Field)val(keyString));
   }

   @Support({SQLDialect.MYSQL})
   public static Field<byte[]> aesEncrypt(byte[] string, byte[] keyString) {
      return aesEncrypt((Field)val(string), (Field)val(keyString));
   }

   @Support({SQLDialect.MYSQL})
   public static <T> Field<T> aesEncrypt(Field<T> string, Field<T> keyString) {
      return function("aes_encrypt", string.getType(), new Field[]{string, keyString});
   }

   @Support({SQLDialect.MYSQL})
   public static Field<String> desDecrypt(String cryptString) {
      return desDecrypt((Field)val(cryptString));
   }

   @Support({SQLDialect.MYSQL})
   public static Field<byte[]> desDecrypt(byte[] cryptString) {
      return desDecrypt((Field)val(cryptString));
   }

   @Support({SQLDialect.MYSQL})
   public static <T> Field<T> desDecrypt(Field<T> cryptString) {
      return function("des_decrypt", cryptString.getType(), new Field[]{cryptString});
   }

   @Support({SQLDialect.MYSQL})
   public static Field<String> desDecrypt(String cryptString, String keyString) {
      return desDecrypt((Field)val(cryptString), (Field)val(keyString));
   }

   @Support({SQLDialect.MYSQL})
   public static Field<byte[]> desDecrypt(byte[] cryptString, byte[] keyString) {
      return desDecrypt((Field)val(cryptString), (Field)val(keyString));
   }

   @Support({SQLDialect.MYSQL})
   public static <T> Field<T> desDecrypt(Field<T> cryptString, Field<T> keyString) {
      return function("des_decrypt", cryptString.getType(), new Field[]{cryptString, keyString});
   }

   @Support({SQLDialect.MYSQL})
   public static Field<String> desEncrypt(String string) {
      return desEncrypt((Field)val(string));
   }

   @Support({SQLDialect.MYSQL})
   public static Field<byte[]> desEncrypt(byte[] string) {
      return desEncrypt((Field)val(string));
   }

   @Support({SQLDialect.MYSQL})
   public static <T> Field<T> desEncrypt(Field<T> string) {
      return function("des_encrypt", string.getType(), new Field[]{string});
   }

   @Support({SQLDialect.MYSQL})
   public static Field<String> desEncrypt(String string, String keyString) {
      return desEncrypt((Field)val(string), (Field)val(keyString));
   }

   @Support({SQLDialect.MYSQL})
   public static Field<byte[]> desEncrypt(byte[] string, byte[] keyString) {
      return desEncrypt((Field)val(string), (Field)val(keyString));
   }

   @Support({SQLDialect.MYSQL})
   public static <T> Field<T> desEncrypt(Field<T> string, Field<T> keyString) {
      return function("des_encrypt", string.getType(), new Field[]{string, keyString});
   }

   @Support({SQLDialect.MYSQL})
   public static Field<String> compress(String string) {
      return compress((Field)val(string));
   }

   @Support({SQLDialect.MYSQL})
   public static Field<byte[]> compress(byte[] string) {
      return compress((Field)val(string));
   }

   @Support({SQLDialect.MYSQL})
   public static <T> Field<T> compress(Field<T> string) {
      return function("compress", string.getType(), new Field[]{string});
   }

   @Support({SQLDialect.MYSQL})
   public static Field<String> uncompress(String string) {
      return uncompress((Field)val(string));
   }

   @Support({SQLDialect.MYSQL})
   public static Field<byte[]> uncompress(byte[] string) {
      return uncompress((Field)val(string));
   }

   @Support({SQLDialect.MYSQL})
   public static <T> Field<T> uncompress(Field<T> string) {
      return function("uncompress", string.getType(), new Field[]{string});
   }

   @Support({SQLDialect.MYSQL})
   public static Field<Integer> uncompressedLength(String string) {
      return uncompressedLength((Field)val(string));
   }

   @Support({SQLDialect.MYSQL})
   public static Field<Integer> uncompressedLength(byte[] string) {
      return uncompressedLength((Field)val(string));
   }

   @Support({SQLDialect.MYSQL})
   public static <T> Field<Integer> uncompressedLength(Field<T> string) {
      return function("uncompressed_length", Integer.class, new Field[]{string});
   }

   @Support({SQLDialect.MYSQL})
   public static Field<String> sha1(String string) {
      return sha1((Field)val(string));
   }

   @Support({SQLDialect.MYSQL})
   public static Field<byte[]> sha1(byte[] string) {
      return sha1((Field)val(string));
   }

   @Support({SQLDialect.MYSQL})
   public static <T> Field<T> sha1(Field<T> string) {
      return function("sha1", string.getType(), new Field[]{string});
   }

   @Support({SQLDialect.MYSQL})
   public static Field<String> sha2(String string, int hashLength) {
      return sha2(val(string), val(hashLength));
   }

   @Support({SQLDialect.MYSQL})
   public static Field<byte[]> sha2(byte[] string, int hashLength) {
      return sha2(val(string), val(hashLength));
   }

   @Support({SQLDialect.MYSQL})
   public static <T> Field<T> sha2(Field<T> string, Field<Integer> hashLength) {
      return function("sha2", string.getType(), new Field[]{string, hashLength});
   }

   @Support({SQLDialect.MYSQL})
   public static Field<String> password(String string) {
      return password((Field)val(string));
   }

   @Support({SQLDialect.MYSQL})
   public static Field<byte[]> password(byte[] string) {
      return password((Field)val(string));
   }

   @Support({SQLDialect.MYSQL})
   public static <T> Field<T> password(Field<T> string) {
      return function("password", string.getType(), new Field[]{string});
   }

   @Support({SQLDialect.MYSQL})
   public static <T> Field<T> values(Field<T> values) {
      return function("values", values.getDataType(), new Field[]{values});
   }

   public static <E extends Enum<E> & EnumType> E enumType(Class<E> type, int index) {
      if (index <= 0) {
         return null;
      } else {
         E[] values = (Enum[])type.getEnumConstants();
         return index > values.length ? null : values[index - 1];
      }
   }
}
