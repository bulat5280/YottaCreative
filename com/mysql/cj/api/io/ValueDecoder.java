package com.mysql.cj.api.io;

public interface ValueDecoder {
   <T> T decodeDate(byte[] var1, int var2, int var3, ValueFactory<T> var4);

   <T> T decodeTime(byte[] var1, int var2, int var3, ValueFactory<T> var4);

   <T> T decodeTimestamp(byte[] var1, int var2, int var3, ValueFactory<T> var4);

   <T> T decodeInt1(byte[] var1, int var2, int var3, ValueFactory<T> var4);

   <T> T decodeUInt1(byte[] var1, int var2, int var3, ValueFactory<T> var4);

   <T> T decodeInt2(byte[] var1, int var2, int var3, ValueFactory<T> var4);

   <T> T decodeUInt2(byte[] var1, int var2, int var3, ValueFactory<T> var4);

   <T> T decodeInt4(byte[] var1, int var2, int var3, ValueFactory<T> var4);

   <T> T decodeUInt4(byte[] var1, int var2, int var3, ValueFactory<T> var4);

   <T> T decodeInt8(byte[] var1, int var2, int var3, ValueFactory<T> var4);

   <T> T decodeUInt8(byte[] var1, int var2, int var3, ValueFactory<T> var4);

   <T> T decodeFloat(byte[] var1, int var2, int var3, ValueFactory<T> var4);

   <T> T decodeDouble(byte[] var1, int var2, int var3, ValueFactory<T> var4);

   <T> T decodeDecimal(byte[] var1, int var2, int var3, ValueFactory<T> var4);

   <T> T decodeByteArray(byte[] var1, int var2, int var3, ValueFactory<T> var4);

   <T> T decodeBit(byte[] var1, int var2, int var3, ValueFactory<T> var4);
}
