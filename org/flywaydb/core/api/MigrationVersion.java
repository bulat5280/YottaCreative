package org.flywaydb.core.api;

import java.math.BigInteger;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Pattern;

public final class MigrationVersion implements Comparable<MigrationVersion> {
   public static final MigrationVersion EMPTY = new MigrationVersion((BigInteger)null, "<< Empty Schema >>");
   public static final MigrationVersion LATEST = new MigrationVersion(BigInteger.valueOf(-1L), "<< Latest Version >>");
   public static final MigrationVersion CURRENT = new MigrationVersion(BigInteger.valueOf(-2L), "<< Current Version >>");
   private static Pattern splitPattern = Pattern.compile("\\.(?=\\d)");
   private final List<BigInteger> versionParts;
   private final String displayText;

   public static MigrationVersion fromVersion(String version) {
      if ("current".equalsIgnoreCase(version)) {
         return CURRENT;
      } else if (LATEST.getVersion().equals(version)) {
         return LATEST;
      } else {
         return version == null ? EMPTY : new MigrationVersion(version);
      }
   }

   private MigrationVersion(String version) {
      String normalizedVersion = version.replace('_', '.');
      this.versionParts = this.tokenize(normalizedVersion);
      this.displayText = normalizedVersion;
   }

   private MigrationVersion(BigInteger version, String displayText) {
      this.versionParts = new ArrayList();
      this.versionParts.add(version);
      this.displayText = displayText;
   }

   public String toString() {
      return this.displayText;
   }

   public String getVersion() {
      if (this.equals(EMPTY)) {
         return null;
      } else {
         return this.equals(LATEST) ? Long.toString(Long.MAX_VALUE) : this.displayText;
      }
   }

   public boolean equals(Object o) {
      if (this == o) {
         return true;
      } else if (o != null && this.getClass() == o.getClass()) {
         MigrationVersion version1 = (MigrationVersion)o;
         return this.compareTo(version1) == 0;
      } else {
         return false;
      }
   }

   public int hashCode() {
      return this.versionParts == null ? 0 : this.versionParts.hashCode();
   }

   public int compareTo(MigrationVersion o) {
      if (o == null) {
         return 1;
      } else if (this == EMPTY) {
         return o == EMPTY ? 0 : Integer.MIN_VALUE;
      } else if (this == CURRENT) {
         return o == CURRENT ? 0 : Integer.MIN_VALUE;
      } else if (this == LATEST) {
         return o == LATEST ? 0 : Integer.MAX_VALUE;
      } else if (o == EMPTY) {
         return Integer.MAX_VALUE;
      } else if (o == CURRENT) {
         return Integer.MAX_VALUE;
      } else if (o == LATEST) {
         return Integer.MIN_VALUE;
      } else {
         List<BigInteger> parts1 = this.versionParts;
         List<BigInteger> parts2 = o.versionParts;
         int largestNumberOfParts = Math.max(parts1.size(), parts2.size());

         for(int i = 0; i < largestNumberOfParts; ++i) {
            int compared = this.getOrZero(parts1, i).compareTo(this.getOrZero(parts2, i));
            if (compared != 0) {
               return compared;
            }
         }

         return 0;
      }
   }

   private BigInteger getOrZero(List<BigInteger> elements, int i) {
      return i < elements.size() ? (BigInteger)elements.get(i) : BigInteger.ZERO;
   }

   private List<BigInteger> tokenize(String str) {
      ArrayList parts = new ArrayList();

      try {
         String[] var3 = splitPattern.split(str);
         int var4 = var3.length;

         for(int var5 = 0; var5 < var4; ++var5) {
            String part = var3[var5];
            parts.add(new BigInteger(part));
         }
      } catch (NumberFormatException var7) {
         throw new FlywayException("Invalid version containing non-numeric characters. Only 0..9 and . are allowed. Invalid version: " + str);
      }

      for(int i = parts.size() - 1; i > 0 && ((BigInteger)parts.get(i)).equals(BigInteger.ZERO); --i) {
         parts.remove(i);
      }

      return parts;
   }
}
