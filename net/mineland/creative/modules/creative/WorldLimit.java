package net.mineland.creative.modules.creative;

public class WorldLimit {
   private long timeMillis;
   private int limitOperations;

   public WorldLimit(long timeMillis, int limitOperations) {
      this.timeMillis = timeMillis;
      this.limitOperations = limitOperations;
   }

   public long getTimeMillis() {
      return this.timeMillis;
   }

   public int getLimitOperations() {
      return this.limitOperations;
   }

   public void setTimeMillis(long timeMillis) {
      this.timeMillis = timeMillis;
   }

   public void setLimitOperations(int limitOperations) {
      this.limitOperations = limitOperations;
   }

   public boolean equals(Object o) {
      if (o == this) {
         return true;
      } else if (!(o instanceof WorldLimit)) {
         return false;
      } else {
         WorldLimit other = (WorldLimit)o;
         if (!other.canEqual(this)) {
            return false;
         } else if (this.getTimeMillis() != other.getTimeMillis()) {
            return false;
         } else {
            return this.getLimitOperations() == other.getLimitOperations();
         }
      }
   }

   protected boolean canEqual(Object other) {
      return other instanceof WorldLimit;
   }

   public int hashCode() {
      int PRIME = true;
      int result = 1;
      long $timeMillis = this.getTimeMillis();
      int result = result * 59 + (int)($timeMillis >>> 32 ^ $timeMillis);
      result = result * 59 + this.getLimitOperations();
      return result;
   }

   public String toString() {
      return "WorldLimit(timeMillis=" + this.getTimeMillis() + ", limitOperations=" + this.getLimitOperations() + ")";
   }
}
