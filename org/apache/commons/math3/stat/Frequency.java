package org.apache.commons.math3.stat;

import java.io.Serializable;
import java.text.NumberFormat;
import java.util.Collection;
import java.util.Comparator;
import java.util.Iterator;
import java.util.TreeMap;
import java.util.Map.Entry;
import org.apache.commons.math3.exception.MathIllegalArgumentException;
import org.apache.commons.math3.exception.NullArgumentException;
import org.apache.commons.math3.exception.util.LocalizedFormats;
import org.apache.commons.math3.util.MathUtils;

public class Frequency implements Serializable {
   private static final long serialVersionUID = -3845586908418844111L;
   private final TreeMap<Comparable<?>, Long> freqTable;

   public Frequency() {
      this.freqTable = new TreeMap();
   }

   public Frequency(Comparator<?> comparator) {
      this.freqTable = new TreeMap(comparator);
   }

   public String toString() {
      NumberFormat nf = NumberFormat.getPercentInstance();
      StringBuilder outBuffer = new StringBuilder();
      outBuffer.append("Value \t Freq. \t Pct. \t Cum Pct. \n");
      Iterator iter = this.freqTable.keySet().iterator();

      while(iter.hasNext()) {
         Comparable<?> value = (Comparable)iter.next();
         outBuffer.append(value);
         outBuffer.append('\t');
         outBuffer.append(this.getCount(value));
         outBuffer.append('\t');
         outBuffer.append(nf.format(this.getPct(value)));
         outBuffer.append('\t');
         outBuffer.append(nf.format(this.getCumPct(value)));
         outBuffer.append('\n');
      }

      return outBuffer.toString();
   }

   public void addValue(Comparable<?> v) throws MathIllegalArgumentException {
      this.incrementValue(v, 1L);
   }

   public void incrementValue(Comparable<?> v, long increment) {
      Comparable<?> obj = v;
      if (v instanceof Integer) {
         obj = ((Integer)v).longValue();
      }

      try {
         Long count = (Long)this.freqTable.get(obj);
         if (count == null) {
            this.freqTable.put(obj, increment);
         } else {
            this.freqTable.put(obj, count + increment);
         }

      } catch (ClassCastException var6) {
         throw new MathIllegalArgumentException(LocalizedFormats.INSTANCES_NOT_COMPARABLE_TO_EXISTING_VALUES, new Object[]{v.getClass().getName()});
      }
   }

   public void addValue(int v) throws MathIllegalArgumentException {
      this.addValue((long)v);
   }

   public void addValue(long v) throws MathIllegalArgumentException {
      this.addValue(v);
   }

   public void addValue(char v) throws MathIllegalArgumentException {
      this.addValue(v);
   }

   public void clear() {
      this.freqTable.clear();
   }

   public Iterator<Comparable<?>> valuesIterator() {
      return this.freqTable.keySet().iterator();
   }

   public Iterator<Entry<Comparable<?>, Long>> entrySetIterator() {
      return this.freqTable.entrySet().iterator();
   }

   public long getSumFreq() {
      long result = 0L;

      for(Iterator iterator = this.freqTable.values().iterator(); iterator.hasNext(); result += (Long)iterator.next()) {
      }

      return result;
   }

   public long getCount(Comparable<?> v) {
      if (v instanceof Integer) {
         return this.getCount(((Integer)v).longValue());
      } else {
         long result = 0L;

         try {
            Long count = (Long)this.freqTable.get(v);
            if (count != null) {
               result = count;
            }
         } catch (ClassCastException var5) {
         }

         return result;
      }
   }

   public long getCount(int v) {
      return this.getCount((long)v);
   }

   public long getCount(long v) {
      return this.getCount(v);
   }

   public long getCount(char v) {
      return this.getCount(v);
   }

   public int getUniqueCount() {
      return this.freqTable.keySet().size();
   }

   public double getPct(Comparable<?> v) {
      long sumFreq = this.getSumFreq();
      return sumFreq == 0L ? Double.NaN : (double)this.getCount(v) / (double)sumFreq;
   }

   public double getPct(int v) {
      return this.getPct((long)v);
   }

   public double getPct(long v) {
      return this.getPct(v);
   }

   public double getPct(char v) {
      return this.getPct(v);
   }

   public long getCumFreq(Comparable<?> v) {
      if (this.getSumFreq() == 0L) {
         return 0L;
      } else if (v instanceof Integer) {
         return this.getCumFreq(((Integer)v).longValue());
      } else {
         Comparator<Comparable<?>> c = this.freqTable.comparator();
         if (c == null) {
            c = new Frequency.NaturalComparator();
         }

         long result = 0L;

         try {
            Long value = (Long)this.freqTable.get(v);
            if (value != null) {
               result = value;
            }
         } catch (ClassCastException var7) {
            return result;
         }

         if (((Comparator)c).compare(v, this.freqTable.firstKey()) < 0) {
            return 0L;
         } else if (((Comparator)c).compare(v, this.freqTable.lastKey()) >= 0) {
            return this.getSumFreq();
         } else {
            Comparable nextValue;
            for(Iterator values = this.valuesIterator(); values.hasNext(); result += this.getCount(nextValue)) {
               nextValue = (Comparable)values.next();
               if (((Comparator)c).compare(v, nextValue) <= 0) {
                  return result;
               }
            }

            return result;
         }
      }
   }

   public long getCumFreq(int v) {
      return this.getCumFreq((long)v);
   }

   public long getCumFreq(long v) {
      return this.getCumFreq(v);
   }

   public long getCumFreq(char v) {
      return this.getCumFreq(v);
   }

   public double getCumPct(Comparable<?> v) {
      long sumFreq = this.getSumFreq();
      return sumFreq == 0L ? Double.NaN : (double)this.getCumFreq(v) / (double)sumFreq;
   }

   public double getCumPct(int v) {
      return this.getCumPct((long)v);
   }

   public double getCumPct(long v) {
      return this.getCumPct(v);
   }

   public double getCumPct(char v) {
      return this.getCumPct(v);
   }

   public void merge(Frequency other) throws NullArgumentException {
      MathUtils.checkNotNull(other, LocalizedFormats.NULL_NOT_ALLOWED);
      Iterator iter = other.entrySetIterator();

      while(iter.hasNext()) {
         Entry<Comparable<?>, Long> entry = (Entry)iter.next();
         this.incrementValue((Comparable)entry.getKey(), (Long)entry.getValue());
      }

   }

   public void merge(Collection<Frequency> others) throws NullArgumentException {
      MathUtils.checkNotNull(others, LocalizedFormats.NULL_NOT_ALLOWED);
      Iterator i$ = others.iterator();

      while(i$.hasNext()) {
         Frequency freq = (Frequency)i$.next();
         this.merge(freq);
      }

   }

   public int hashCode() {
      int prime = true;
      int result = 1;
      int result = 31 * result + (this.freqTable == null ? 0 : this.freqTable.hashCode());
      return result;
   }

   public boolean equals(Object obj) {
      if (this == obj) {
         return true;
      } else if (!(obj instanceof Frequency)) {
         return false;
      } else {
         Frequency other = (Frequency)obj;
         if (this.freqTable == null) {
            if (other.freqTable != null) {
               return false;
            }
         } else if (!this.freqTable.equals(other.freqTable)) {
            return false;
         }

         return true;
      }
   }

   private static class NaturalComparator<T extends Comparable<T>> implements Comparator<Comparable<T>>, Serializable {
      private static final long serialVersionUID = -3852193713161395148L;

      private NaturalComparator() {
      }

      public int compare(Comparable<T> o1, Comparable<T> o2) {
         return o1.compareTo(o2);
      }

      // $FF: synthetic method
      NaturalComparator(Object x0) {
         this();
      }
   }
}
