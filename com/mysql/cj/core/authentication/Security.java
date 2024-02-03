package com.mysql.cj.core.authentication;

import com.mysql.cj.core.exceptions.AssertionFailedException;
import com.mysql.cj.core.util.StringUtils;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class Security {
   public static void xorString(byte[] from, byte[] to, byte[] scramble, int length) {
      int pos = 0;

      for(int scrambleLength = scramble.length; pos < length; ++pos) {
         to[pos] = (byte)(from[pos] ^ scramble[pos % scrambleLength]);
      }

   }

   public static byte[] scramble411(String password, byte[] seed, String passwordEncoding) {
      byte[] passwordBytes = passwordEncoding != null && passwordEncoding.length() != 0 ? StringUtils.getBytes(password, passwordEncoding) : StringUtils.getBytes(password);
      return scramble411(passwordBytes, seed);
   }

   public static byte[] scramble411(byte[] password, byte[] seed) {
      MessageDigest md;
      try {
         md = MessageDigest.getInstance("SHA-1");
      } catch (NoSuchAlgorithmException var8) {
         throw new AssertionFailedException(var8);
      }

      byte[] passwordHashStage1 = md.digest(password);
      md.reset();
      byte[] passwordHashStage2 = md.digest(passwordHashStage1);
      md.reset();
      md.update(seed);
      md.update(passwordHashStage2);
      byte[] toBeXord = md.digest();
      int numToXor = toBeXord.length;

      for(int i = 0; i < numToXor; ++i) {
         toBeXord[i] ^= passwordHashStage1[i];
      }

      return toBeXord;
   }

   private Security() {
   }
}
