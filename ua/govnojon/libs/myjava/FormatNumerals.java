package ua.govnojon.libs.myjava;

public class FormatNumerals {
   public static String formatRoman(int value) {
      switch(value) {
      case 1:
         return "I";
      case 2:
         return "II";
      case 3:
         return "III";
      case 4:
         return "IV";
      case 5:
         return "V";
      case 6:
         return "VI";
      case 7:
         return "VII";
      case 8:
         return "VIII";
      case 9:
         return "IX";
      case 10:
         return "X";
      default:
         return String.valueOf(value);
      }
   }

   public static String formatUpperIndex(int value) {
      char[] chars = String.valueOf(value).toCharArray();

      for(int i = 0; i < chars.length; ++i) {
         switch(chars[i]) {
         case '-':
            chars[i] = 8315;
         case '.':
         case '/':
         default:
            break;
         case '0':
            chars[i] = 8304;
            break;
         case '1':
            chars[i] = 185;
            break;
         case '2':
            chars[i] = 178;
            break;
         case '3':
            chars[i] = 179;
            break;
         case '4':
            chars[i] = 8308;
            break;
         case '5':
            chars[i] = 8309;
            break;
         case '6':
            chars[i] = 8310;
            break;
         case '7':
            chars[i] = 8311;
            break;
         case '8':
            chars[i] = 8312;
            break;
         case '9':
            chars[i] = 8313;
         }
      }

      return new String(chars);
   }
}
