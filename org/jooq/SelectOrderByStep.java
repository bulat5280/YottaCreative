package org.jooq;

import java.util.Collection;

public interface SelectOrderByStep<R extends Record> extends SelectLimitStep<R> {
   @Support
   <T1> SelectSeekStep1<R, T1> orderBy(Field<T1> var1);

   @Support
   <T1, T2> SelectSeekStep2<R, T1, T2> orderBy(Field<T1> var1, Field<T2> var2);

   @Support
   <T1, T2, T3> SelectSeekStep3<R, T1, T2, T3> orderBy(Field<T1> var1, Field<T2> var2, Field<T3> var3);

   @Support
   <T1, T2, T3, T4> SelectSeekStep4<R, T1, T2, T3, T4> orderBy(Field<T1> var1, Field<T2> var2, Field<T3> var3, Field<T4> var4);

   @Support
   <T1, T2, T3, T4, T5> SelectSeekStep5<R, T1, T2, T3, T4, T5> orderBy(Field<T1> var1, Field<T2> var2, Field<T3> var3, Field<T4> var4, Field<T5> var5);

   @Support
   <T1, T2, T3, T4, T5, T6> SelectSeekStep6<R, T1, T2, T3, T4, T5, T6> orderBy(Field<T1> var1, Field<T2> var2, Field<T3> var3, Field<T4> var4, Field<T5> var5, Field<T6> var6);

   @Support
   <T1, T2, T3, T4, T5, T6, T7> SelectSeekStep7<R, T1, T2, T3, T4, T5, T6, T7> orderBy(Field<T1> var1, Field<T2> var2, Field<T3> var3, Field<T4> var4, Field<T5> var5, Field<T6> var6, Field<T7> var7);

   @Support
   <T1, T2, T3, T4, T5, T6, T7, T8> SelectSeekStep8<R, T1, T2, T3, T4, T5, T6, T7, T8> orderBy(Field<T1> var1, Field<T2> var2, Field<T3> var3, Field<T4> var4, Field<T5> var5, Field<T6> var6, Field<T7> var7, Field<T8> var8);

   @Support
   <T1, T2, T3, T4, T5, T6, T7, T8, T9> SelectSeekStep9<R, T1, T2, T3, T4, T5, T6, T7, T8, T9> orderBy(Field<T1> var1, Field<T2> var2, Field<T3> var3, Field<T4> var4, Field<T5> var5, Field<T6> var6, Field<T7> var7, Field<T8> var8, Field<T9> var9);

   @Support
   <T1, T2, T3, T4, T5, T6, T7, T8, T9, T10> SelectSeekStep10<R, T1, T2, T3, T4, T5, T6, T7, T8, T9, T10> orderBy(Field<T1> var1, Field<T2> var2, Field<T3> var3, Field<T4> var4, Field<T5> var5, Field<T6> var6, Field<T7> var7, Field<T8> var8, Field<T9> var9, Field<T10> var10);

   @Support
   <T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11> SelectSeekStep11<R, T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11> orderBy(Field<T1> var1, Field<T2> var2, Field<T3> var3, Field<T4> var4, Field<T5> var5, Field<T6> var6, Field<T7> var7, Field<T8> var8, Field<T9> var9, Field<T10> var10, Field<T11> var11);

   @Support
   <T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12> SelectSeekStep12<R, T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12> orderBy(Field<T1> var1, Field<T2> var2, Field<T3> var3, Field<T4> var4, Field<T5> var5, Field<T6> var6, Field<T7> var7, Field<T8> var8, Field<T9> var9, Field<T10> var10, Field<T11> var11, Field<T12> var12);

   @Support
   <T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13> SelectSeekStep13<R, T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13> orderBy(Field<T1> var1, Field<T2> var2, Field<T3> var3, Field<T4> var4, Field<T5> var5, Field<T6> var6, Field<T7> var7, Field<T8> var8, Field<T9> var9, Field<T10> var10, Field<T11> var11, Field<T12> var12, Field<T13> var13);

   @Support
   <T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14> SelectSeekStep14<R, T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14> orderBy(Field<T1> var1, Field<T2> var2, Field<T3> var3, Field<T4> var4, Field<T5> var5, Field<T6> var6, Field<T7> var7, Field<T8> var8, Field<T9> var9, Field<T10> var10, Field<T11> var11, Field<T12> var12, Field<T13> var13, Field<T14> var14);

   @Support
   <T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14, T15> SelectSeekStep15<R, T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14, T15> orderBy(Field<T1> var1, Field<T2> var2, Field<T3> var3, Field<T4> var4, Field<T5> var5, Field<T6> var6, Field<T7> var7, Field<T8> var8, Field<T9> var9, Field<T10> var10, Field<T11> var11, Field<T12> var12, Field<T13> var13, Field<T14> var14, Field<T15> var15);

   @Support
   <T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14, T15, T16> SelectSeekStep16<R, T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14, T15, T16> orderBy(Field<T1> var1, Field<T2> var2, Field<T3> var3, Field<T4> var4, Field<T5> var5, Field<T6> var6, Field<T7> var7, Field<T8> var8, Field<T9> var9, Field<T10> var10, Field<T11> var11, Field<T12> var12, Field<T13> var13, Field<T14> var14, Field<T15> var15, Field<T16> var16);

   @Support
   <T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14, T15, T16, T17> SelectSeekStep17<R, T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14, T15, T16, T17> orderBy(Field<T1> var1, Field<T2> var2, Field<T3> var3, Field<T4> var4, Field<T5> var5, Field<T6> var6, Field<T7> var7, Field<T8> var8, Field<T9> var9, Field<T10> var10, Field<T11> var11, Field<T12> var12, Field<T13> var13, Field<T14> var14, Field<T15> var15, Field<T16> var16, Field<T17> var17);

   @Support
   <T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14, T15, T16, T17, T18> SelectSeekStep18<R, T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14, T15, T16, T17, T18> orderBy(Field<T1> var1, Field<T2> var2, Field<T3> var3, Field<T4> var4, Field<T5> var5, Field<T6> var6, Field<T7> var7, Field<T8> var8, Field<T9> var9, Field<T10> var10, Field<T11> var11, Field<T12> var12, Field<T13> var13, Field<T14> var14, Field<T15> var15, Field<T16> var16, Field<T17> var17, Field<T18> var18);

   @Support
   <T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14, T15, T16, T17, T18, T19> SelectSeekStep19<R, T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14, T15, T16, T17, T18, T19> orderBy(Field<T1> var1, Field<T2> var2, Field<T3> var3, Field<T4> var4, Field<T5> var5, Field<T6> var6, Field<T7> var7, Field<T8> var8, Field<T9> var9, Field<T10> var10, Field<T11> var11, Field<T12> var12, Field<T13> var13, Field<T14> var14, Field<T15> var15, Field<T16> var16, Field<T17> var17, Field<T18> var18, Field<T19> var19);

   @Support
   <T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14, T15, T16, T17, T18, T19, T20> SelectSeekStep20<R, T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14, T15, T16, T17, T18, T19, T20> orderBy(Field<T1> var1, Field<T2> var2, Field<T3> var3, Field<T4> var4, Field<T5> var5, Field<T6> var6, Field<T7> var7, Field<T8> var8, Field<T9> var9, Field<T10> var10, Field<T11> var11, Field<T12> var12, Field<T13> var13, Field<T14> var14, Field<T15> var15, Field<T16> var16, Field<T17> var17, Field<T18> var18, Field<T19> var19, Field<T20> var20);

   @Support
   <T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14, T15, T16, T17, T18, T19, T20, T21> SelectSeekStep21<R, T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14, T15, T16, T17, T18, T19, T20, T21> orderBy(Field<T1> var1, Field<T2> var2, Field<T3> var3, Field<T4> var4, Field<T5> var5, Field<T6> var6, Field<T7> var7, Field<T8> var8, Field<T9> var9, Field<T10> var10, Field<T11> var11, Field<T12> var12, Field<T13> var13, Field<T14> var14, Field<T15> var15, Field<T16> var16, Field<T17> var17, Field<T18> var18, Field<T19> var19, Field<T20> var20, Field<T21> var21);

   @Support
   <T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14, T15, T16, T17, T18, T19, T20, T21, T22> SelectSeekStep22<R, T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14, T15, T16, T17, T18, T19, T20, T21, T22> orderBy(Field<T1> var1, Field<T2> var2, Field<T3> var3, Field<T4> var4, Field<T5> var5, Field<T6> var6, Field<T7> var7, Field<T8> var8, Field<T9> var9, Field<T10> var10, Field<T11> var11, Field<T12> var12, Field<T13> var13, Field<T14> var14, Field<T15> var15, Field<T16> var16, Field<T17> var17, Field<T18> var18, Field<T19> var19, Field<T20> var20, Field<T21> var21, Field<T22> var22);

   @Support
   SelectSeekStepN<R> orderBy(Field<?>... var1);

   @Support
   <T1> SelectSeekStep1<R, T1> orderBy(SortField<T1> var1);

   @Support
   <T1, T2> SelectSeekStep2<R, T1, T2> orderBy(SortField<T1> var1, SortField<T2> var2);

   @Support
   <T1, T2, T3> SelectSeekStep3<R, T1, T2, T3> orderBy(SortField<T1> var1, SortField<T2> var2, SortField<T3> var3);

   @Support
   <T1, T2, T3, T4> SelectSeekStep4<R, T1, T2, T3, T4> orderBy(SortField<T1> var1, SortField<T2> var2, SortField<T3> var3, SortField<T4> var4);

   @Support
   <T1, T2, T3, T4, T5> SelectSeekStep5<R, T1, T2, T3, T4, T5> orderBy(SortField<T1> var1, SortField<T2> var2, SortField<T3> var3, SortField<T4> var4, SortField<T5> var5);

   @Support
   <T1, T2, T3, T4, T5, T6> SelectSeekStep6<R, T1, T2, T3, T4, T5, T6> orderBy(SortField<T1> var1, SortField<T2> var2, SortField<T3> var3, SortField<T4> var4, SortField<T5> var5, SortField<T6> var6);

   @Support
   <T1, T2, T3, T4, T5, T6, T7> SelectSeekStep7<R, T1, T2, T3, T4, T5, T6, T7> orderBy(SortField<T1> var1, SortField<T2> var2, SortField<T3> var3, SortField<T4> var4, SortField<T5> var5, SortField<T6> var6, SortField<T7> var7);

   @Support
   <T1, T2, T3, T4, T5, T6, T7, T8> SelectSeekStep8<R, T1, T2, T3, T4, T5, T6, T7, T8> orderBy(SortField<T1> var1, SortField<T2> var2, SortField<T3> var3, SortField<T4> var4, SortField<T5> var5, SortField<T6> var6, SortField<T7> var7, SortField<T8> var8);

   @Support
   <T1, T2, T3, T4, T5, T6, T7, T8, T9> SelectSeekStep9<R, T1, T2, T3, T4, T5, T6, T7, T8, T9> orderBy(SortField<T1> var1, SortField<T2> var2, SortField<T3> var3, SortField<T4> var4, SortField<T5> var5, SortField<T6> var6, SortField<T7> var7, SortField<T8> var8, SortField<T9> var9);

   @Support
   <T1, T2, T3, T4, T5, T6, T7, T8, T9, T10> SelectSeekStep10<R, T1, T2, T3, T4, T5, T6, T7, T8, T9, T10> orderBy(SortField<T1> var1, SortField<T2> var2, SortField<T3> var3, SortField<T4> var4, SortField<T5> var5, SortField<T6> var6, SortField<T7> var7, SortField<T8> var8, SortField<T9> var9, SortField<T10> var10);

   @Support
   <T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11> SelectSeekStep11<R, T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11> orderBy(SortField<T1> var1, SortField<T2> var2, SortField<T3> var3, SortField<T4> var4, SortField<T5> var5, SortField<T6> var6, SortField<T7> var7, SortField<T8> var8, SortField<T9> var9, SortField<T10> var10, SortField<T11> var11);

   @Support
   <T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12> SelectSeekStep12<R, T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12> orderBy(SortField<T1> var1, SortField<T2> var2, SortField<T3> var3, SortField<T4> var4, SortField<T5> var5, SortField<T6> var6, SortField<T7> var7, SortField<T8> var8, SortField<T9> var9, SortField<T10> var10, SortField<T11> var11, SortField<T12> var12);

   @Support
   <T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13> SelectSeekStep13<R, T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13> orderBy(SortField<T1> var1, SortField<T2> var2, SortField<T3> var3, SortField<T4> var4, SortField<T5> var5, SortField<T6> var6, SortField<T7> var7, SortField<T8> var8, SortField<T9> var9, SortField<T10> var10, SortField<T11> var11, SortField<T12> var12, SortField<T13> var13);

   @Support
   <T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14> SelectSeekStep14<R, T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14> orderBy(SortField<T1> var1, SortField<T2> var2, SortField<T3> var3, SortField<T4> var4, SortField<T5> var5, SortField<T6> var6, SortField<T7> var7, SortField<T8> var8, SortField<T9> var9, SortField<T10> var10, SortField<T11> var11, SortField<T12> var12, SortField<T13> var13, SortField<T14> var14);

   @Support
   <T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14, T15> SelectSeekStep15<R, T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14, T15> orderBy(SortField<T1> var1, SortField<T2> var2, SortField<T3> var3, SortField<T4> var4, SortField<T5> var5, SortField<T6> var6, SortField<T7> var7, SortField<T8> var8, SortField<T9> var9, SortField<T10> var10, SortField<T11> var11, SortField<T12> var12, SortField<T13> var13, SortField<T14> var14, SortField<T15> var15);

   @Support
   <T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14, T15, T16> SelectSeekStep16<R, T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14, T15, T16> orderBy(SortField<T1> var1, SortField<T2> var2, SortField<T3> var3, SortField<T4> var4, SortField<T5> var5, SortField<T6> var6, SortField<T7> var7, SortField<T8> var8, SortField<T9> var9, SortField<T10> var10, SortField<T11> var11, SortField<T12> var12, SortField<T13> var13, SortField<T14> var14, SortField<T15> var15, SortField<T16> var16);

   @Support
   <T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14, T15, T16, T17> SelectSeekStep17<R, T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14, T15, T16, T17> orderBy(SortField<T1> var1, SortField<T2> var2, SortField<T3> var3, SortField<T4> var4, SortField<T5> var5, SortField<T6> var6, SortField<T7> var7, SortField<T8> var8, SortField<T9> var9, SortField<T10> var10, SortField<T11> var11, SortField<T12> var12, SortField<T13> var13, SortField<T14> var14, SortField<T15> var15, SortField<T16> var16, SortField<T17> var17);

   @Support
   <T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14, T15, T16, T17, T18> SelectSeekStep18<R, T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14, T15, T16, T17, T18> orderBy(SortField<T1> var1, SortField<T2> var2, SortField<T3> var3, SortField<T4> var4, SortField<T5> var5, SortField<T6> var6, SortField<T7> var7, SortField<T8> var8, SortField<T9> var9, SortField<T10> var10, SortField<T11> var11, SortField<T12> var12, SortField<T13> var13, SortField<T14> var14, SortField<T15> var15, SortField<T16> var16, SortField<T17> var17, SortField<T18> var18);

   @Support
   <T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14, T15, T16, T17, T18, T19> SelectSeekStep19<R, T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14, T15, T16, T17, T18, T19> orderBy(SortField<T1> var1, SortField<T2> var2, SortField<T3> var3, SortField<T4> var4, SortField<T5> var5, SortField<T6> var6, SortField<T7> var7, SortField<T8> var8, SortField<T9> var9, SortField<T10> var10, SortField<T11> var11, SortField<T12> var12, SortField<T13> var13, SortField<T14> var14, SortField<T15> var15, SortField<T16> var16, SortField<T17> var17, SortField<T18> var18, SortField<T19> var19);

   @Support
   <T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14, T15, T16, T17, T18, T19, T20> SelectSeekStep20<R, T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14, T15, T16, T17, T18, T19, T20> orderBy(SortField<T1> var1, SortField<T2> var2, SortField<T3> var3, SortField<T4> var4, SortField<T5> var5, SortField<T6> var6, SortField<T7> var7, SortField<T8> var8, SortField<T9> var9, SortField<T10> var10, SortField<T11> var11, SortField<T12> var12, SortField<T13> var13, SortField<T14> var14, SortField<T15> var15, SortField<T16> var16, SortField<T17> var17, SortField<T18> var18, SortField<T19> var19, SortField<T20> var20);

   @Support
   <T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14, T15, T16, T17, T18, T19, T20, T21> SelectSeekStep21<R, T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14, T15, T16, T17, T18, T19, T20, T21> orderBy(SortField<T1> var1, SortField<T2> var2, SortField<T3> var3, SortField<T4> var4, SortField<T5> var5, SortField<T6> var6, SortField<T7> var7, SortField<T8> var8, SortField<T9> var9, SortField<T10> var10, SortField<T11> var11, SortField<T12> var12, SortField<T13> var13, SortField<T14> var14, SortField<T15> var15, SortField<T16> var16, SortField<T17> var17, SortField<T18> var18, SortField<T19> var19, SortField<T20> var20, SortField<T21> var21);

   @Support
   <T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14, T15, T16, T17, T18, T19, T20, T21, T22> SelectSeekStep22<R, T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14, T15, T16, T17, T18, T19, T20, T21, T22> orderBy(SortField<T1> var1, SortField<T2> var2, SortField<T3> var3, SortField<T4> var4, SortField<T5> var5, SortField<T6> var6, SortField<T7> var7, SortField<T8> var8, SortField<T9> var9, SortField<T10> var10, SortField<T11> var11, SortField<T12> var12, SortField<T13> var13, SortField<T14> var14, SortField<T15> var15, SortField<T16> var16, SortField<T17> var17, SortField<T18> var18, SortField<T19> var19, SortField<T20> var20, SortField<T21> var21, SortField<T22> var22);

   @Support
   SelectSeekStepN<R> orderBy(SortField<?>... var1);

   @Support
   SelectSeekStepN<R> orderBy(Collection<? extends SortField<?>> var1);

   @Support
   SelectLimitStep<R> orderBy(int... var1);

   @Support({SQLDialect.CUBRID})
   SelectLimitStep<R> orderSiblingsBy(Field<?>... var1);

   @Support({SQLDialect.CUBRID})
   SelectLimitStep<R> orderSiblingsBy(SortField<?>... var1);

   @Support({SQLDialect.CUBRID})
   SelectLimitStep<R> orderSiblingsBy(Collection<? extends SortField<?>> var1);

   @Support({SQLDialect.CUBRID})
   SelectLimitStep<R> orderSiblingsBy(int... var1);
}
