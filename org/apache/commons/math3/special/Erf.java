package org.apache.commons.math3.special;

import org.apache.commons.math3.util.FastMath;

public class Erf {
   private static final double X_CRIT = 0.4769362762044697D;

   private Erf() {
   }

   public static double erf(double x) {
      if (FastMath.abs(x) > 40.0D) {
         return x > 0.0D ? 1.0D : -1.0D;
      } else {
         double ret = Gamma.regularizedGammaP(0.5D, x * x, 1.0E-15D, 10000);
         return x < 0.0D ? -ret : ret;
      }
   }

   public static double erfc(double x) {
      if (FastMath.abs(x) > 40.0D) {
         return x > 0.0D ? 0.0D : 2.0D;
      } else {
         double ret = Gamma.regularizedGammaQ(0.5D, x * x, 1.0E-15D, 10000);
         return x < 0.0D ? 2.0D - ret : ret;
      }
   }

   public static double erf(double x1, double x2) {
      if (x1 > x2) {
         return -erf(x2, x1);
      } else {
         return x1 < -0.4769362762044697D ? (x2 < 0.0D ? erfc(-x2) - erfc(-x1) : erf(x2) - erf(x1)) : (x2 > 0.4769362762044697D && x1 > 0.0D ? erfc(x1) - erfc(x2) : erf(x2) - erf(x1));
      }
   }

   public static double erfInv(double x) {
      double w = -FastMath.log((1.0D - x) * (1.0D + x));
      double p;
      if (w < 6.25D) {
         w -= 3.125D;
         p = -3.64441206401782E-21D;
         p = -1.6850591381820166E-19D + p * w;
         p = 1.28584807152564E-18D + p * w;
         p = 1.1157877678025181E-17D + p * w;
         p = -1.333171662854621E-16D + p * w;
         p = 2.0972767875968562E-17D + p * w;
         p = 6.637638134358324E-15D + p * w;
         p = -4.054566272975207E-14D + p * w;
         p = -8.151934197605472E-14D + p * w;
         p = 2.6335093153082323E-12D + p * w;
         p = -1.2975133253453532E-11D + p * w;
         p = -5.415412054294628E-11D + p * w;
         p = 1.0512122733215323E-9D + p * w;
         p = -4.112633980346984E-9D + p * w;
         p = -2.9070369957882005E-8D + p * w;
         p = 4.2347877827932404E-7D + p * w;
         p = -1.3654692000834679E-6D + p * w;
         p = -1.3882523362786469E-5D + p * w;
         p = 1.8673420803405714E-4D + p * w;
         p = -7.40702534166267E-4D + p * w;
         p = -0.006033670871430149D + p * w;
         p = 0.24015818242558962D + p * w;
         p = 1.6536545626831027D + p * w;
      } else if (w < 16.0D) {
         w = FastMath.sqrt(w) - 3.25D;
         p = 2.2137376921775787E-9D;
         p = 9.075656193888539E-8D + p * w;
         p = -2.7517406297064545E-7D + p * w;
         p = 1.8239629214389228E-8D + p * w;
         p = 1.5027403968909828E-6D + p * w;
         p = -4.013867526981546E-6D + p * w;
         p = 2.9234449089955446E-6D + p * w;
         p = 1.2475304481671779E-5D + p * w;
         p = -4.7318229009055734E-5D + p * w;
         p = 6.828485145957318E-5D + p * w;
         p = 2.4031110387097894E-5D + p * w;
         p = -3.550375203628475E-4D + p * w;
         p = 9.532893797373805E-4D + p * w;
         p = -0.0016882755560235047D + p * w;
         p = 0.002491442096107851D + p * w;
         p = -0.003751208507569241D + p * w;
         p = 0.005370914553590064D + p * w;
         p = 1.0052589676941592D + p * w;
         p = 3.0838856104922208D + p * w;
      } else if (!Double.isInfinite(w)) {
         w = FastMath.sqrt(w) - 5.0D;
         p = -2.7109920616438573E-11D;
         p = -2.555641816996525E-10D + p * w;
         p = 1.5076572693500548E-9D + p * w;
         p = -3.789465440126737E-9D + p * w;
         p = 7.61570120807834E-9D + p * w;
         p = -1.496002662714924E-8D + p * w;
         p = 2.914795345090108E-8D + p * w;
         p = -6.771199775845234E-8D + p * w;
         p = 2.2900482228026655E-7D + p * w;
         p = -9.9298272942317E-7D + p * w;
         p = 4.526062597223154E-6D + p * w;
         p = -1.968177810553167E-5D + p * w;
         p = 7.599527703001776E-5D + p * w;
         p = -2.1503011930044477E-4D + p * w;
         p = -1.3871931833623122E-4D + p * w;
         p = 1.0103004648645344D + p * w;
         p = 4.849906401408584D + p * w;
      } else {
         p = Double.POSITIVE_INFINITY;
      }

      return p * x;
   }

   public static double erfcInv(double x) {
      return erfInv(1.0D - x);
   }
}
