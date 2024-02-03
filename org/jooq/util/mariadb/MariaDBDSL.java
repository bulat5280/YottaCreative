package org.jooq.util.mariadb;

import org.jooq.EnumType;
import org.jooq.Field;
import org.jooq.impl.DSL;

public class MariaDBDSL extends DSL {
   protected MariaDBDSL() {
   }

   public static Field<String> decode(String cryptString, String keyString) {
      return decode((Field)val(cryptString), (Field)val(keyString));
   }

   public static Field<String> decode(Field<String> cryptString, Field<String> keyString) {
      return function("decode", String.class, new Field[]{cryptString, keyString});
   }

   public static Field<String> encode(String string, String keyString) {
      return encode((Field)val(string), (Field)val(keyString));
   }

   public static Field<String> encode(Field<String> string, Field<String> keyString) {
      return function("encode", String.class, new Field[]{string, keyString});
   }

   public static Field<String> aesDecrypt(String cryptString, String keyString) {
      return aesDecrypt((Field)val(cryptString), (Field)val(keyString));
   }

   public static Field<String> aesDecrypt(Field<String> cryptString, Field<String> keyString) {
      return function("aes_decrypt", String.class, new Field[]{cryptString, keyString});
   }

   public static Field<String> aesEncrypt(String string, String keyString) {
      return aesEncrypt((Field)val(string), (Field)val(keyString));
   }

   public static Field<String> aesEncrypt(Field<String> string, Field<String> keyString) {
      return function("aes_encrypt", String.class, new Field[]{string, keyString});
   }

   public static Field<String> desDecrypt(String cryptString) {
      return desDecrypt((Field)val(cryptString));
   }

   public static Field<String> desDecrypt(Field<String> cryptString) {
      return function("des_decrypt", String.class, new Field[]{cryptString});
   }

   public static Field<String> desDecrypt(String cryptString, String keyString) {
      return desDecrypt((Field)val(cryptString), (Field)val(keyString));
   }

   public static Field<String> desDecrypt(Field<String> cryptString, Field<String> keyString) {
      return function("des_decrypt", String.class, new Field[]{cryptString, keyString});
   }

   public static Field<String> desEncrypt(String string) {
      return desEncrypt((Field)val(string));
   }

   public static Field<String> desEncrypt(Field<String> string) {
      return function("des_encrypt", String.class, new Field[]{string});
   }

   public static Field<String> desEncrypt(String string, String keyString) {
      return desEncrypt((Field)val(string), (Field)val(keyString));
   }

   public static Field<String> desEncrypt(Field<String> string, Field<String> keyString) {
      return function("des_encrypt", String.class, new Field[]{string, keyString});
   }

   public static Field<String> compress(String string) {
      return compress((Field)val(string));
   }

   public static Field<String> compress(Field<String> string) {
      return function("compress", String.class, new Field[]{string});
   }

   public static Field<String> uncompress(String string) {
      return uncompress((Field)val(string));
   }

   public static Field<String> uncompress(Field<String> string) {
      return function("uncompress", String.class, new Field[]{string});
   }

   public static Field<Integer> uncompressedLength(String string) {
      return uncompressedLength((Field)val(string));
   }

   public static Field<Integer> uncompressedLength(Field<String> string) {
      return function("uncompressed_length", Integer.class, new Field[]{string});
   }

   public static Field<String> sha1(String string) {
      return sha1((Field)val(string));
   }

   public static Field<String> sha1(Field<String> string) {
      return function("sha1", String.class, new Field[]{string});
   }

   public static Field<String> password(String string) {
      return password((Field)val(string));
   }

   public static Field<String> password(Field<String> string) {
      return function("password", String.class, new Field[]{string});
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
