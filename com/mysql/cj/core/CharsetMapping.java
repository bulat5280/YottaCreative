package com.mysql.cj.core;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Set;
import java.util.TreeMap;

public class CharsetMapping {
   public static final int MAP_SIZE = 255;
   public static final String[] COLLATION_INDEX_TO_COLLATION_NAME;
   public static final MysqlCharset[] COLLATION_INDEX_TO_CHARSET;
   public static final Map<String, MysqlCharset> CHARSET_NAME_TO_CHARSET;
   public static final Map<String, Integer> CHARSET_NAME_TO_COLLATION_INDEX;
   private static final Map<String, List<MysqlCharset>> JAVA_ENCODING_UC_TO_MYSQL_CHARSET;
   private static final Set<String> MULTIBYTE_ENCODINGS;
   public static final Set<Integer> UTF8MB4_INDEXES;
   private static final String MYSQL_CHARSET_NAME_armscii8 = "armscii8";
   private static final String MYSQL_CHARSET_NAME_ascii = "ascii";
   private static final String MYSQL_CHARSET_NAME_big5 = "big5";
   private static final String MYSQL_CHARSET_NAME_binary = "binary";
   private static final String MYSQL_CHARSET_NAME_cp1250 = "cp1250";
   private static final String MYSQL_CHARSET_NAME_cp1251 = "cp1251";
   private static final String MYSQL_CHARSET_NAME_cp1256 = "cp1256";
   private static final String MYSQL_CHARSET_NAME_cp1257 = "cp1257";
   private static final String MYSQL_CHARSET_NAME_cp850 = "cp850";
   private static final String MYSQL_CHARSET_NAME_cp852 = "cp852";
   private static final String MYSQL_CHARSET_NAME_cp866 = "cp866";
   private static final String MYSQL_CHARSET_NAME_cp932 = "cp932";
   private static final String MYSQL_CHARSET_NAME_dec8 = "dec8";
   private static final String MYSQL_CHARSET_NAME_eucjpms = "eucjpms";
   private static final String MYSQL_CHARSET_NAME_euckr = "euckr";
   private static final String MYSQL_CHARSET_NAME_gb18030 = "gb18030";
   private static final String MYSQL_CHARSET_NAME_gb2312 = "gb2312";
   private static final String MYSQL_CHARSET_NAME_gbk = "gbk";
   private static final String MYSQL_CHARSET_NAME_geostd8 = "geostd8";
   private static final String MYSQL_CHARSET_NAME_greek = "greek";
   private static final String MYSQL_CHARSET_NAME_hebrew = "hebrew";
   private static final String MYSQL_CHARSET_NAME_hp8 = "hp8";
   private static final String MYSQL_CHARSET_NAME_keybcs2 = "keybcs2";
   private static final String MYSQL_CHARSET_NAME_koi8r = "koi8r";
   private static final String MYSQL_CHARSET_NAME_koi8u = "koi8u";
   private static final String MYSQL_CHARSET_NAME_latin1 = "latin1";
   private static final String MYSQL_CHARSET_NAME_latin2 = "latin2";
   private static final String MYSQL_CHARSET_NAME_latin5 = "latin5";
   private static final String MYSQL_CHARSET_NAME_latin7 = "latin7";
   private static final String MYSQL_CHARSET_NAME_macce = "macce";
   private static final String MYSQL_CHARSET_NAME_macroman = "macroman";
   private static final String MYSQL_CHARSET_NAME_sjis = "sjis";
   private static final String MYSQL_CHARSET_NAME_swe7 = "swe7";
   private static final String MYSQL_CHARSET_NAME_tis620 = "tis620";
   private static final String MYSQL_CHARSET_NAME_ucs2 = "ucs2";
   private static final String MYSQL_CHARSET_NAME_ujis = "ujis";
   private static final String MYSQL_CHARSET_NAME_utf16 = "utf16";
   private static final String MYSQL_CHARSET_NAME_utf16le = "utf16le";
   private static final String MYSQL_CHARSET_NAME_utf32 = "utf32";
   private static final String MYSQL_CHARSET_NAME_utf8 = "utf8";
   private static final String MYSQL_CHARSET_NAME_utf8mb4 = "utf8mb4";
   private static final String NOT_USED = "latin1";
   public static final int MYSQL_COLLATION_INDEX_utf8 = 33;
   public static final int MYSQL_COLLATION_INDEX_binary = 63;
   private static int numberOfEncodingsConfigured = 0;

   public static final String getMysqlCharsetForJavaEncoding(String javaEncoding, ServerVersion version) {
      List<MysqlCharset> mysqlCharsets = (List)JAVA_ENCODING_UC_TO_MYSQL_CHARSET.get(javaEncoding.toUpperCase(Locale.ENGLISH));
      if (mysqlCharsets != null) {
         Iterator<MysqlCharset> iter = mysqlCharsets.iterator();
         MysqlCharset currentChoice = null;

         while(true) {
            MysqlCharset charset;
            do {
               if (!iter.hasNext()) {
                  if (currentChoice != null) {
                     return currentChoice.charsetName;
                  }

                  return null;
               }

               charset = (MysqlCharset)iter.next();
               if (version == null) {
                  return charset.charsetName;
               }
            } while(currentChoice != null && currentChoice.priority >= charset.priority && currentChoice.minimumVersion.compareTo(charset.minimumVersion) >= 0);

            if (charset.isOkayForVersion(version)) {
               currentChoice = charset;
            }
         }
      } else {
         return null;
      }
   }

   public static int getCollationIndexForJavaEncoding(String javaEncoding, ServerVersion version) {
      String charsetName = getMysqlCharsetForJavaEncoding(javaEncoding, version);
      if (charsetName != null) {
         Integer ci = (Integer)CHARSET_NAME_TO_COLLATION_INDEX.get(charsetName);
         if (ci != null) {
            return ci;
         }
      }

      return 0;
   }

   public static String getMysqlCharsetNameForCollationIndex(Integer collationIndex) {
      return collationIndex != null && collationIndex > 0 && collationIndex < 255 ? COLLATION_INDEX_TO_CHARSET[collationIndex].charsetName : null;
   }

   public static String getJavaEncodingForMysqlCharset(String mysqlCharsetName, String javaEncoding) {
      String res = javaEncoding;
      MysqlCharset cs = (MysqlCharset)CHARSET_NAME_TO_CHARSET.get(mysqlCharsetName);
      if (cs != null) {
         res = cs.getMatchingJavaEncoding(javaEncoding);
      }

      return res;
   }

   public static String getJavaEncodingForMysqlCharset(String mysqlCharsetName) {
      return getJavaEncodingForMysqlCharset(mysqlCharsetName, (String)null);
   }

   public static String getJavaEncodingForCollationIndex(Integer collationIndex, String javaEncoding) {
      if (collationIndex != null && collationIndex > 0 && collationIndex < 255) {
         MysqlCharset cs = COLLATION_INDEX_TO_CHARSET[collationIndex];
         return cs.getMatchingJavaEncoding(javaEncoding);
      } else {
         return null;
      }
   }

   public static String getJavaEncodingForCollationIndex(Integer collationIndex) {
      return getJavaEncodingForCollationIndex(collationIndex, (String)null);
   }

   public static final int getNumberOfCharsetsConfigured() {
      return numberOfEncodingsConfigured;
   }

   public static final boolean isMultibyteCharset(String javaEncodingName) {
      return MULTIBYTE_ENCODINGS.contains(javaEncodingName.toUpperCase(Locale.ENGLISH));
   }

   public static int getMblen(String charsetName) {
      if (charsetName != null) {
         MysqlCharset cs = (MysqlCharset)CHARSET_NAME_TO_CHARSET.get(charsetName);
         if (cs != null) {
            return cs.mblen;
         }
      }

      return 0;
   }

   static {
      MysqlCharset[] charset = new MysqlCharset[]{new MysqlCharset("ascii", 1, 0, new String[]{"US-ASCII", "ASCII"}), new MysqlCharset("big5", 2, 0, new String[]{"Big5"}), new MysqlCharset("gbk", 2, 0, new String[]{"GBK"}), new MysqlCharset("sjis", 2, 0, new String[]{"SHIFT_JIS", "Cp943", "WINDOWS-31J"}), new MysqlCharset("cp932", 2, 1, new String[]{"WINDOWS-31J"}), new MysqlCharset("gb2312", 2, 0, new String[]{"GB2312"}), new MysqlCharset("ujis", 3, 0, new String[]{"EUC_JP"}), new MysqlCharset("eucjpms", 3, 0, new String[]{"EUC_JP_Solaris"}, new ServerVersion(5, 0, 3)), new MysqlCharset("gb18030", 4, 0, new String[]{"GB18030"}, new ServerVersion(5, 7, 4)), new MysqlCharset("euckr", 2, 0, new String[]{"EUC-KR"}), new MysqlCharset("latin1", 1, 1, new String[]{"Cp1252", "ISO8859_1"}), new MysqlCharset("swe7", 1, 0, new String[]{"Cp1252"}), new MysqlCharset("hp8", 1, 0, new String[]{"Cp1252"}), new MysqlCharset("dec8", 1, 0, new String[]{"Cp1252"}), new MysqlCharset("armscii8", 1, 0, new String[]{"Cp1252"}), new MysqlCharset("geostd8", 1, 0, new String[]{"Cp1252"}), new MysqlCharset("latin2", 1, 0, new String[]{"ISO8859_2"}), new MysqlCharset("greek", 1, 0, new String[]{"ISO8859_7", "greek"}), new MysqlCharset("latin7", 1, 0, new String[]{"ISO-8859-13"}), new MysqlCharset("hebrew", 1, 0, new String[]{"ISO8859_8"}), new MysqlCharset("latin5", 1, 0, new String[]{"ISO8859_9"}), new MysqlCharset("cp850", 1, 0, new String[]{"Cp850", "Cp437"}), new MysqlCharset("cp852", 1, 0, new String[]{"Cp852"}), new MysqlCharset("keybcs2", 1, 0, new String[]{"Cp852"}), new MysqlCharset("cp866", 1, 0, new String[]{"Cp866"}), new MysqlCharset("koi8r", 1, 1, new String[]{"KOI8_R"}), new MysqlCharset("koi8u", 1, 0, new String[]{"KOI8_R"}), new MysqlCharset("tis620", 1, 0, new String[]{"TIS620"}), new MysqlCharset("cp1250", 1, 0, new String[]{"Cp1250"}), new MysqlCharset("cp1251", 1, 1, new String[]{"Cp1251"}), new MysqlCharset("cp1256", 1, 0, new String[]{"Cp1256"}), new MysqlCharset("cp1257", 1, 0, new String[]{"Cp1257"}), new MysqlCharset("macroman", 1, 0, new String[]{"MacRoman"}), new MysqlCharset("macce", 1, 0, new String[]{"MacCentralEurope"}), new MysqlCharset("utf8", 3, 1, new String[]{"UTF-8"}), new MysqlCharset("utf8mb4", 4, 0, new String[]{"UTF-8"}), new MysqlCharset("ucs2", 2, 0, new String[]{"UnicodeBig"}), new MysqlCharset("binary", 1, 1, new String[]{"ISO8859_1"}), new MysqlCharset("utf16", 4, 0, new String[]{"UTF-16"}), new MysqlCharset("utf16le", 4, 0, new String[]{"UTF-16LE"}), new MysqlCharset("utf32", 4, 0, new String[]{"UTF-32"})};
      HashMap<String, MysqlCharset> charsetNameToMysqlCharsetMap = new HashMap();
      HashMap<String, List<MysqlCharset>> javaUcToMysqlCharsetMap = new HashMap();
      Set<String> tempMultibyteEncodings = new HashSet();

      for(int i = 0; i < charset.length; ++i) {
         String charsetName = charset[i].charsetName;
         charsetNameToMysqlCharsetMap.put(charsetName, charset[i]);
         numberOfEncodingsConfigured += charset[i].javaEncodingsUc.size();
         Iterator var6 = charset[i].javaEncodingsUc.iterator();

         while(var6.hasNext()) {
            String encUC = (String)var6.next();
            List<MysqlCharset> charsets = (List)javaUcToMysqlCharsetMap.get(encUC);
            if (charsets == null) {
               charsets = new ArrayList();
               javaUcToMysqlCharsetMap.put(encUC, charsets);
            }

            ((List)charsets).add(charset[i]);
            if (charset[i].mblen > 1) {
               tempMultibyteEncodings.add(encUC);
            }
         }
      }

      CHARSET_NAME_TO_CHARSET = Collections.unmodifiableMap(charsetNameToMysqlCharsetMap);
      JAVA_ENCODING_UC_TO_MYSQL_CHARSET = Collections.unmodifiableMap(javaUcToMysqlCharsetMap);
      MULTIBYTE_ENCODINGS = Collections.unmodifiableSet(tempMultibyteEncodings);
      Collation[] collation = new Collation[]{null, new Collation(1, "big5_chinese_ci", 1, "big5"), new Collation(2, "latin2_czech_cs", 0, "latin2"), new Collation(3, "dec8_swedish_ci", 0, "dec8"), new Collation(4, "cp850_general_ci", 1, "cp850"), new Collation(5, "latin1_german1_ci", 1, "latin1"), new Collation(6, "hp8_english_ci", 0, "hp8"), new Collation(7, "koi8r_general_ci", 0, "koi8r"), new Collation(8, "latin1_swedish_ci", 0, "latin1"), new Collation(9, "latin2_general_ci", 1, "latin2"), new Collation(10, "swe7_swedish_ci", 0, "swe7"), new Collation(11, "ascii_general_ci", 0, "ascii"), new Collation(12, "ujis_japanese_ci", 0, "ujis"), new Collation(13, "sjis_japanese_ci", 0, "sjis"), new Collation(14, "cp1251_bulgarian_ci", 0, "cp1251"), new Collation(15, "latin1_danish_ci", 0, "latin1"), new Collation(16, "hebrew_general_ci", 0, "hebrew"), new Collation(17, "not_implemented", 0, "latin1"), new Collation(18, "tis620_thai_ci", 0, "tis620"), new Collation(19, "euckr_korean_ci", 0, "euckr"), new Collation(20, "latin7_estonian_cs", 0, "latin7"), new Collation(21, "latin2_hungarian_ci", 0, "latin2"), new Collation(22, "koi8u_general_ci", 0, "koi8u"), new Collation(23, "cp1251_ukrainian_ci", 0, "cp1251"), new Collation(24, "gb2312_chinese_ci", 0, "gb2312"), new Collation(25, "greek_general_ci", 0, "greek"), new Collation(26, "cp1250_general_ci", 1, "cp1250"), new Collation(27, "latin2_croatian_ci", 0, "latin2"), new Collation(28, "gbk_chinese_ci", 1, "gbk"), new Collation(29, "cp1257_lithuanian_ci", 0, "cp1257"), new Collation(30, "latin5_turkish_ci", 1, "latin5"), new Collation(31, "latin1_german2_ci", 0, "latin1"), new Collation(32, "armscii8_general_ci", 0, "armscii8"), new Collation(33, "utf8_general_ci", 1, "utf8"), new Collation(34, "cp1250_czech_cs", 0, "cp1250"), new Collation(35, "ucs2_general_ci", 1, "ucs2"), new Collation(36, "cp866_general_ci", 1, "cp866"), new Collation(37, "keybcs2_general_ci", 1, "keybcs2"), new Collation(38, "macce_general_ci", 1, "macce"), new Collation(39, "macroman_general_ci", 1, "macroman"), new Collation(40, "cp852_general_ci", 1, "cp852"), new Collation(41, "latin7_general_ci", 1, "latin7"), new Collation(42, "latin7_general_cs", 0, "latin7"), new Collation(43, "macce_bin", 0, "macce"), new Collation(44, "cp1250_croatian_ci", 0, "cp1250"), new Collation(45, "utf8mb4_general_ci", 1, "utf8mb4"), new Collation(46, "utf8mb4_bin", 0, "utf8mb4"), new Collation(47, "latin1_bin", 0, "latin1"), new Collation(48, "latin1_general_ci", 0, "latin1"), new Collation(49, "latin1_general_cs", 0, "latin1"), new Collation(50, "cp1251_bin", 0, "cp1251"), new Collation(51, "cp1251_general_ci", 1, "cp1251"), new Collation(52, "cp1251_general_cs", 0, "cp1251"), new Collation(53, "macroman_bin", 0, "macroman"), new Collation(54, "utf16_general_ci", 1, "utf16"), new Collation(55, "utf16_bin", 0, "utf16"), new Collation(56, "utf16le_general_ci", 1, "utf16le"), new Collation(57, "cp1256_general_ci", 1, "cp1256"), new Collation(58, "cp1257_bin", 0, "cp1257"), new Collation(59, "cp1257_general_ci", 1, "cp1257"), new Collation(60, "utf32_general_ci", 1, "utf32"), new Collation(61, "utf32_bin", 0, "utf32"), new Collation(62, "utf16le_bin", 0, "utf16le"), new Collation(63, "binary", 1, "binary"), new Collation(64, "armscii8_bin", 0, "armscii8"), new Collation(65, "ascii_bin", 0, "ascii"), new Collation(66, "cp1250_bin", 0, "cp1250"), new Collation(67, "cp1256_bin", 0, "cp1256"), new Collation(68, "cp866_bin", 0, "cp866"), new Collation(69, "dec8_bin", 0, "dec8"), new Collation(70, "greek_bin", 0, "greek"), new Collation(71, "hebrew_bin", 0, "hebrew"), new Collation(72, "hp8_bin", 0, "hp8"), new Collation(73, "keybcs2_bin", 0, "keybcs2"), new Collation(74, "koi8r_bin", 0, "koi8r"), new Collation(75, "koi8u_bin", 0, "koi8u"), new Collation(76, "not_implemented", 0, "latin1"), new Collation(77, "latin2_bin", 0, "latin2"), new Collation(78, "latin5_bin", 0, "latin5"), new Collation(79, "latin7_bin", 0, "latin7"), new Collation(80, "cp850_bin", 0, "cp850"), new Collation(81, "cp852_bin", 0, "cp852"), new Collation(82, "swe7_bin", 0, "swe7"), new Collation(83, "utf8_bin", 0, "utf8"), new Collation(84, "big5_bin", 0, "big5"), new Collation(85, "euckr_bin", 0, "euckr"), new Collation(86, "gb2312_bin", 0, "gb2312"), new Collation(87, "gbk_bin", 0, "gbk"), new Collation(88, "sjis_bin", 0, "sjis"), new Collation(89, "tis620_bin", 0, "tis620"), new Collation(90, "ucs2_bin", 0, "ucs2"), new Collation(91, "ujis_bin", 0, "ujis"), new Collation(92, "geostd8_general_ci", 0, "geostd8"), new Collation(93, "geostd8_bin", 0, "geostd8"), new Collation(94, "latin1_spanish_ci", 0, "latin1"), new Collation(95, "cp932_japanese_ci", 1, "cp932"), new Collation(96, "cp932_bin", 0, "cp932"), new Collation(97, "eucjpms_japanese_ci", 1, "eucjpms"), new Collation(98, "eucjpms_bin", 0, "eucjpms"), new Collation(99, "cp1250_polish_ci", 0, "cp1250"), new Collation(100, "not_implemented", 0, "latin1"), new Collation(101, "utf16_unicode_ci", 0, "utf16"), new Collation(102, "utf16_icelandic_ci", 0, "utf16"), new Collation(103, "utf16_latvian_ci", 0, "utf16"), new Collation(104, "utf16_romanian_ci", 0, "utf16"), new Collation(105, "utf16_slovenian_ci", 0, "utf16"), new Collation(106, "utf16_polish_ci", 0, "utf16"), new Collation(107, "utf16_estonian_ci", 0, "utf16"), new Collation(108, "utf16_spanish_ci", 0, "utf16"), new Collation(109, "utf16_swedish_ci", 0, "utf16"), new Collation(110, "utf16_turkish_ci", 0, "utf16"), new Collation(111, "utf16_czech_ci", 0, "utf16"), new Collation(112, "utf16_danish_ci", 0, "utf16"), new Collation(113, "utf16_lithuanian_ci", 0, "utf16"), new Collation(114, "utf16_slovak_ci", 0, "utf16"), new Collation(115, "utf16_spanish2_ci", 0, "utf16"), new Collation(116, "utf16_roman_ci", 0, "utf16"), new Collation(117, "utf16_persian_ci", 0, "utf16"), new Collation(118, "utf16_esperanto_ci", 0, "utf16"), new Collation(119, "utf16_hungarian_ci", 0, "utf16"), new Collation(120, "utf16_sinhala_ci", 0, "utf16"), new Collation(121, "utf16_german2_ci", 0, "utf16"), new Collation(122, "utf16_croatian_ci", 0, "utf16"), new Collation(123, "utf16_unicode_520_ci", 0, "utf16"), new Collation(124, "utf16_vietnamese_ci", 0, "utf16"), new Collation(125, "not_implemented", 0, "latin1"), new Collation(126, "not_implemented", 0, "latin1"), new Collation(127, "not_implemented", 0, "latin1"), new Collation(128, "ucs2_unicode_ci", 0, "ucs2"), new Collation(129, "ucs2_icelandic_ci", 0, "ucs2"), new Collation(130, "ucs2_latvian_ci", 0, "ucs2"), new Collation(131, "ucs2_romanian_ci", 0, "ucs2"), new Collation(132, "ucs2_slovenian_ci", 0, "ucs2"), new Collation(133, "ucs2_polish_ci", 0, "ucs2"), new Collation(134, "ucs2_estonian_ci", 0, "ucs2"), new Collation(135, "ucs2_spanish_ci", 0, "ucs2"), new Collation(136, "ucs2_swedish_ci", 0, "ucs2"), new Collation(137, "ucs2_turkish_ci", 0, "ucs2"), new Collation(138, "ucs2_czech_ci", 0, "ucs2"), new Collation(139, "ucs2_danish_ci", 0, "ucs2"), new Collation(140, "ucs2_lithuanian_ci", 0, "ucs2"), new Collation(141, "ucs2_slovak_ci", 0, "ucs2"), new Collation(142, "ucs2_spanish2_ci", 0, "ucs2"), new Collation(143, "ucs2_roman_ci", 0, "ucs2"), new Collation(144, "ucs2_persian_ci", 0, "ucs2"), new Collation(145, "ucs2_esperanto_ci", 0, "ucs2"), new Collation(146, "ucs2_hungarian_ci", 0, "ucs2"), new Collation(147, "ucs2_sinhala_ci", 0, "ucs2"), new Collation(148, "ucs2_german2_ci", 0, "ucs2"), new Collation(149, "ucs2_croatian_ci", 0, "ucs2"), new Collation(150, "ucs2_unicode_520_ci", 0, "ucs2"), new Collation(151, "ucs2_vietnamese_ci", 0, "ucs2"), new Collation(152, "not_implemented", 0, "latin1"), new Collation(153, "not_implemented", 0, "latin1"), new Collation(154, "not_implemented", 0, "latin1"), new Collation(155, "not_implemented", 0, "latin1"), new Collation(156, "not_implemented", 0, "latin1"), new Collation(157, "not_implemented", 0, "latin1"), new Collation(158, "not_implemented", 0, "latin1"), new Collation(159, "ucs2_general_mysql500_ci", 0, "ucs2"), new Collation(160, "utf32_unicode_ci", 0, "utf32"), new Collation(161, "utf32_icelandic_ci", 0, "utf32"), new Collation(162, "utf32_latvian_ci", 0, "utf32"), new Collation(163, "utf32_romanian_ci", 0, "utf32"), new Collation(164, "utf32_slovenian_ci", 0, "utf32"), new Collation(165, "utf32_polish_ci", 0, "utf32"), new Collation(166, "utf32_estonian_ci", 0, "utf32"), new Collation(167, "utf32_spanish_ci", 0, "utf32"), new Collation(168, "utf32_swedish_ci", 0, "utf32"), new Collation(169, "utf32_turkish_ci", 0, "utf32"), new Collation(170, "utf32_czech_ci", 0, "utf32"), new Collation(171, "utf32_danish_ci", 0, "utf32"), new Collation(172, "utf32_lithuanian_ci", 0, "utf32"), new Collation(173, "utf32_slovak_ci", 0, "utf32"), new Collation(174, "utf32_spanish2_ci", 0, "utf32"), new Collation(175, "utf32_roman_ci", 0, "utf32"), new Collation(176, "utf32_persian_ci", 0, "utf32"), new Collation(177, "utf32_esperanto_ci", 0, "utf32"), new Collation(178, "utf32_hungarian_ci", 0, "utf32"), new Collation(179, "utf32_sinhala_ci", 0, "utf32"), new Collation(180, "utf32_german2_ci", 0, "utf32"), new Collation(181, "utf32_croatian_ci", 0, "utf32"), new Collation(182, "utf32_unicode_520_ci", 0, "utf32"), new Collation(183, "utf32_vietnamese_ci", 0, "utf32"), new Collation(184, "not_implemented", 0, "latin1"), new Collation(185, "not_implemented", 0, "latin1"), new Collation(186, "not_implemented", 0, "latin1"), new Collation(187, "not_implemented", 0, "latin1"), new Collation(188, "not_implemented", 0, "latin1"), new Collation(189, "not_implemented", 0, "latin1"), new Collation(190, "not_implemented", 0, "latin1"), new Collation(191, "not_implemented", 0, "latin1"), new Collation(192, "utf8_unicode_ci", 0, "utf8"), new Collation(193, "utf8_icelandic_ci", 0, "utf8"), new Collation(194, "utf8_latvian_ci", 0, "utf8"), new Collation(195, "utf8_romanian_ci", 0, "utf8"), new Collation(196, "utf8_slovenian_ci", 0, "utf8"), new Collation(197, "utf8_polish_ci", 0, "utf8"), new Collation(198, "utf8_estonian_ci", 0, "utf8"), new Collation(199, "utf8_spanish_ci", 0, "utf8"), new Collation(200, "utf8_swedish_ci", 0, "utf8"), new Collation(201, "utf8_turkish_ci", 0, "utf8"), new Collation(202, "utf8_czech_ci", 0, "utf8"), new Collation(203, "utf8_danish_ci", 0, "utf8"), new Collation(204, "utf8_lithuanian_ci", 0, "utf8"), new Collation(205, "utf8_slovak_ci", 0, "utf8"), new Collation(206, "utf8_spanish2_ci", 0, "utf8"), new Collation(207, "utf8_roman_ci", 0, "utf8"), new Collation(208, "utf8_persian_ci", 0, "utf8"), new Collation(209, "utf8_esperanto_ci", 0, "utf8"), new Collation(210, "utf8_hungarian_ci", 0, "utf8"), new Collation(211, "utf8_sinhala_ci", 0, "utf8"), new Collation(212, "utf8_german2_ci", 0, "utf8"), new Collation(213, "utf8_croatian_ci", 0, "utf8"), new Collation(214, "utf8_unicode_520_ci", 0, "utf8"), new Collation(215, "utf8_vietnamese_ci", 0, "utf8"), new Collation(216, "not_implemented", 0, "latin1"), new Collation(217, "not_implemented", 0, "latin1"), new Collation(218, "not_implemented", 0, "latin1"), new Collation(219, "not_implemented", 0, "latin1"), new Collation(220, "not_implemented", 0, "latin1"), new Collation(221, "not_implemented", 0, "latin1"), new Collation(222, "not_implemented", 0, "latin1"), new Collation(223, "utf8_general_mysql500_ci", 0, "utf8"), new Collation(224, "utf8mb4_unicode_ci", 0, "utf8mb4"), new Collation(225, "utf8mb4_icelandic_ci", 0, "utf8mb4"), new Collation(226, "utf8mb4_latvian_ci", 0, "utf8mb4"), new Collation(227, "utf8mb4_romanian_ci", 0, "utf8mb4"), new Collation(228, "utf8mb4_slovenian_ci", 0, "utf8mb4"), new Collation(229, "utf8mb4_polish_ci", 0, "utf8mb4"), new Collation(230, "utf8mb4_estonian_ci", 0, "utf8mb4"), new Collation(231, "utf8mb4_spanish_ci", 0, "utf8mb4"), new Collation(232, "utf8mb4_swedish_ci", 0, "utf8mb4"), new Collation(233, "utf8mb4_turkish_ci", 0, "utf8mb4"), new Collation(234, "utf8mb4_czech_ci", 0, "utf8mb4"), new Collation(235, "utf8mb4_danish_ci", 0, "utf8mb4"), new Collation(236, "utf8mb4_lithuanian_ci", 0, "utf8mb4"), new Collation(237, "utf8mb4_slovak_ci", 0, "utf8mb4"), new Collation(238, "utf8mb4_spanish2_ci", 0, "utf8mb4"), new Collation(239, "utf8mb4_roman_ci", 0, "utf8mb4"), new Collation(240, "utf8mb4_persian_ci", 0, "utf8mb4"), new Collation(241, "utf8mb4_esperanto_ci", 0, "utf8mb4"), new Collation(242, "utf8mb4_hungarian_ci", 0, "utf8mb4"), new Collation(243, "utf8mb4_sinhala_ci", 0, "utf8mb4"), new Collation(244, "utf8mb4_german2_ci", 0, "utf8mb4"), new Collation(245, "utf8mb4_croatian_ci", 0, "utf8mb4"), new Collation(246, "utf8mb4_unicode_520_ci", 0, "utf8mb4"), new Collation(247, "utf8mb4_vietnamese_ci", 0, "utf8mb4"), new Collation(248, "gb18030_chinese_ci", 1, "gb18030"), new Collation(249, "gb18030_bin", 0, "gb18030"), new Collation(250, "gb18030_unicode_520_ci", 0, "gb18030"), new Collation(251, "not_implemented", 0, "latin1"), new Collation(252, "not_implemented", 0, "latin1"), new Collation(253, "not_implemented", 0, "latin1"), new Collation(254, "not_implemented", 0, "latin1")};
      COLLATION_INDEX_TO_COLLATION_NAME = new String[255];
      COLLATION_INDEX_TO_CHARSET = new MysqlCharset[255];
      Map<String, Integer> charsetNameToCollationIndexMap = new TreeMap();
      Map<String, Integer> charsetNameToCollationPriorityMap = new TreeMap();
      Set<Integer> tempUTF8MB4Indexes = new HashSet();

      int i;
      for(i = 1; i < 255; ++i) {
         COLLATION_INDEX_TO_COLLATION_NAME[i] = collation[i].collationName;
         COLLATION_INDEX_TO_CHARSET[i] = collation[i].mysqlCharset;
         String charsetName = collation[i].mysqlCharset.charsetName;
         if (!charsetNameToCollationIndexMap.containsKey(charsetName) || (Integer)charsetNameToCollationPriorityMap.get(charsetName) < collation[i].priority) {
            charsetNameToCollationIndexMap.put(charsetName, i);
            charsetNameToCollationPriorityMap.put(charsetName, collation[i].priority);
         }

         if (charsetName.equals("utf8mb4")) {
            tempUTF8MB4Indexes.add(i);
         }
      }

      for(i = 1; i < 255; ++i) {
         if (COLLATION_INDEX_TO_COLLATION_NAME[i] == null) {
            throw new RuntimeException("Assertion failure: No mapping from charset index " + i + " to a mysql collation");
         }

         if (COLLATION_INDEX_TO_COLLATION_NAME[i] == null) {
            throw new RuntimeException("Assertion failure: No mapping from charset index " + i + " to a Java character set");
         }
      }

      CHARSET_NAME_TO_COLLATION_INDEX = Collections.unmodifiableMap(charsetNameToCollationIndexMap);
      UTF8MB4_INDEXES = Collections.unmodifiableSet(tempUTF8MB4Indexes);
   }
}
