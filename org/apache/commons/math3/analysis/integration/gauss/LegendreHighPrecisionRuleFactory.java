package org.apache.commons.math3.analysis.integration.gauss;

import java.math.BigDecimal;
import java.math.MathContext;
import org.apache.commons.math3.exception.DimensionMismatchException;
import org.apache.commons.math3.util.Pair;

public class LegendreHighPrecisionRuleFactory extends BaseRuleFactory<BigDecimal> {
   private final MathContext mContext;
   private final BigDecimal two;
   private final BigDecimal minusOne;
   private final BigDecimal oneHalf;

   public LegendreHighPrecisionRuleFactory() {
      this(MathContext.DECIMAL128);
   }

   public LegendreHighPrecisionRuleFactory(MathContext mContext) {
      this.mContext = mContext;
      this.two = new BigDecimal("2", mContext);
      this.minusOne = new BigDecimal("-1", mContext);
      this.oneHalf = new BigDecimal("0.5", mContext);
   }

   protected Pair<BigDecimal[], BigDecimal[]> computeRule(int numberOfPoints) throws DimensionMismatchException {
      if (numberOfPoints == 1) {
         return new Pair(new BigDecimal[]{BigDecimal.ZERO}, new BigDecimal[]{this.two});
      } else {
         BigDecimal[] previousPoints = (BigDecimal[])this.getRuleInternal(numberOfPoints - 1).getFirst();
         BigDecimal[] points = new BigDecimal[numberOfPoints];
         BigDecimal[] weights = new BigDecimal[numberOfPoints];
         int iMax = numberOfPoints / 2;

         BigDecimal a;
         BigDecimal b;
         BigDecimal tmp2;
         for(int i = 0; i < iMax; ++i) {
            a = i == 0 ? this.minusOne : previousPoints[i - 1];
            b = iMax == 1 ? BigDecimal.ONE : previousPoints[i];
            tmp2 = BigDecimal.ONE;
            BigDecimal pa = a;
            BigDecimal pmb = BigDecimal.ONE;
            BigDecimal pb = b;

            BigDecimal pmc;
            BigDecimal pc;
            BigDecimal tmp1;
            BigDecimal tmp2;
            BigDecimal tmp2;
            BigDecimal b_two_j_p_1;
            for(int j = 1; j < numberOfPoints; ++j) {
               pmc = new BigDecimal(2 * j + 1, this.mContext);
               pc = new BigDecimal(j, this.mContext);
               BigDecimal b_j_p_1 = new BigDecimal(j + 1, this.mContext);
               tmp1 = a.multiply(pmc, this.mContext);
               tmp1 = pa.multiply(tmp1, this.mContext);
               tmp2 = tmp2.multiply(pc, this.mContext);
               tmp2 = tmp1.subtract(tmp2, this.mContext);
               tmp2 = tmp2.divide(b_j_p_1, this.mContext);
               tmp1 = b.multiply(pmc, this.mContext);
               tmp1 = pb.multiply(tmp1, this.mContext);
               tmp2 = pmb.multiply(pc, this.mContext);
               b_two_j_p_1 = tmp1.subtract(tmp2, this.mContext);
               b_two_j_p_1 = b_two_j_p_1.divide(b_j_p_1, this.mContext);
               tmp2 = pa;
               pa = tmp2;
               pmb = pb;
               pb = b_two_j_p_1;
            }

            BigDecimal c = a.add(b, this.mContext).multiply(this.oneHalf, this.mContext);
            pmc = BigDecimal.ONE;
            pc = c;
            boolean done = false;

            while(!done) {
               tmp1 = b.subtract(a, this.mContext);
               tmp2 = c.ulp().multiply(BigDecimal.TEN, this.mContext);
               done = tmp1.compareTo(tmp2) <= 0;
               pmc = BigDecimal.ONE;
               pc = c;

               for(int j = 1; j < numberOfPoints; ++j) {
                  b_two_j_p_1 = new BigDecimal(2 * j + 1, this.mContext);
                  BigDecimal b_j = new BigDecimal(j, this.mContext);
                  BigDecimal b_j_p_1 = new BigDecimal(j + 1, this.mContext);
                  tmp1 = c.multiply(b_two_j_p_1, this.mContext);
                  tmp1 = pc.multiply(tmp1, this.mContext);
                  tmp2 = pmc.multiply(b_j, this.mContext);
                  BigDecimal ppc = tmp1.subtract(tmp2, this.mContext);
                  ppc = ppc.divide(b_j_p_1, this.mContext);
                  pmc = pc;
                  pc = ppc;
               }

               if (!done) {
                  if (pa.signum() * pc.signum() <= 0) {
                     b = c;
                  } else {
                     a = c;
                     pa = pc;
                  }

                  c = a.add(b, this.mContext).multiply(this.oneHalf, this.mContext);
               }
            }

            tmp1 = new BigDecimal(numberOfPoints, this.mContext);
            tmp2 = pmc.subtract(c.multiply(pc, this.mContext), this.mContext);
            tmp2 = tmp2.multiply(tmp1);
            tmp2 = tmp2.pow(2, this.mContext);
            tmp2 = c.pow(2, this.mContext);
            tmp2 = BigDecimal.ONE.subtract(tmp2, this.mContext);
            tmp2 = tmp2.multiply(this.two, this.mContext);
            tmp2 = tmp2.divide(tmp2, this.mContext);
            points[i] = c;
            weights[i] = tmp2;
            int idx = numberOfPoints - i - 1;
            points[idx] = c.negate(this.mContext);
            weights[idx] = tmp2;
         }

         if (numberOfPoints % 2 != 0) {
            BigDecimal pmc = BigDecimal.ONE;

            for(int j = 1; j < numberOfPoints; j += 2) {
               b = new BigDecimal(j, this.mContext);
               tmp2 = new BigDecimal(j + 1, this.mContext);
               pmc = pmc.multiply(b, this.mContext);
               pmc = pmc.divide(tmp2, this.mContext);
               pmc = pmc.negate(this.mContext);
            }

            a = new BigDecimal(numberOfPoints, this.mContext);
            b = pmc.multiply(a, this.mContext);
            b = b.pow(2, this.mContext);
            tmp2 = this.two.divide(b, this.mContext);
            points[iMax] = BigDecimal.ZERO;
            weights[iMax] = tmp2;
         }

         return new Pair(points, weights);
      }
   }
}
