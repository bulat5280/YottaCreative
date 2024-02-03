package ua.govnojon.libs.bukkitutil;

import org.apache.commons.math3.util.FastMath;
import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.util.Vector;

public class Vec3 {
   private double x;
   private double y;
   private double z;

   public Vec3(double x, double y, double z) {
      this.x = x;
      this.y = y;
      this.z = z;
   }

   public Vec3(Location loc) {
      this(loc.getX(), loc.getY(), loc.getZ());
   }

   public Vec3(Vector vec) {
      this(vec.getX(), vec.getY(), vec.getZ());
   }

   public double getX() {
      return this.x;
   }

   public void setX(double x) {
      this.x = x;
   }

   public double getY() {
      return this.y;
   }

   public void setY(double y) {
      this.y = y;
   }

   public double getZ() {
      return this.z;
   }

   public void setZ(double z) {
      this.z = z;
   }

   public Vector toVector() {
      return new Vector(this.x, this.y, this.z);
   }

   public Location toLocation(World world) {
      return new Location(world, this.x, this.y, this.z);
   }

   public Location toLocation(World world, float yaw, float pitch) {
      return new Location(world, this.x, this.y, this.z, yaw, pitch);
   }

   public double distance(Vec3 vec) {
      return FastMath.sqrt(this.pow2(this.x - vec.x) + this.pow2(this.y - vec.y) + this.pow2(this.z - vec.z));
   }

   private double pow2(double val) {
      return val * val;
   }

   public Vec3 norm() {
      double length = this.length();
      this.x /= length;
      this.y /= length;
      this.z /= length;
      return this;
   }

   public Vec3 add(double x, double y, double z) {
      this.x += x;
      this.y += y;
      this.z += z;
      return this;
   }

   public Vec3 add(Vec3 vec) {
      return this.add(vec.x, vec.y, vec.z);
   }

   public Vec3 sub(double x, double y, double z) {
      this.x -= x;
      this.y -= y;
      this.z -= z;
      return this;
   }

   public Vec3 sub(Vec3 vec) {
      return this.sub(vec.x, vec.y, vec.z);
   }

   public Vec3 div(double x, double y, double z) {
      this.x /= x;
      this.y /= y;
      this.z /= z;
      return this;
   }

   public Vec3 div(Vec3 vec) {
      return this.div(vec.x, vec.y, vec.z);
   }

   public Vec3 mult(double x, double y, double z) {
      this.x *= x;
      this.y *= y;
      this.z *= z;
      return this;
   }

   public Vec3 mult(Vec3 vec) {
      return this.mult(vec.x, vec.y, vec.z);
   }

   public Vec3 mult(double mult) {
      return this.mult(mult, mult, mult);
   }

   public double scalar(Vec3 vec) {
      return this.x * vec.x + this.y * vec.y + this.z * vec.z;
   }

   public double angle(Vec3 vec) {
      return FastMath.acos(this.scalar(vec) / (this.length() * vec.length()));
   }

   public double length() {
      return FastMath.sqrt(this.x * this.x + this.y * this.y + this.z * this.z);
   }

   public Vec3 cross(Vec3 vec) {
      return new Vec3(this.y * vec.z - this.z * vec.y, this.z * vec.x - this.x * vec.z, this.x * vec.y - this.y * vec.x);
   }

   public Vec3 rotateX(double sin, double cos) {
      double prevY = this.y;
      this.y = prevY * cos - this.z * sin;
      this.z = prevY * sin + this.z * cos;
      return this;
   }

   public Vec3 rotateY(double sin, double cos) {
      double prevX = this.x;
      this.x = prevX * cos + this.z * sin;
      this.z = this.z * cos - prevX * sin;
      return this;
   }

   public Vec3 rotateZ(double sin, double cos) {
      double prevX = this.x;
      this.y = prevX * sin + this.y * cos;
      this.x = prevX * cos - this.y * sin;
      return this;
   }

   public Vec3 rotateZ(double angle) {
      double cos = FastMath.cos(angle);
      double sin = FastMath.sin(angle);
      return this.rotateZ(sin, cos);
   }

   public Vec3 rotateY(double angle) {
      double cos = FastMath.cos(angle);
      double sin = FastMath.sin(angle);
      return this.rotateY(sin, cos);
   }

   public Vec3 rotateX(double angle) {
      double cos = FastMath.cos(angle);
      double sin = FastMath.sin(angle);
      return this.rotateX(sin, cos);
   }

   public boolean equals(Object o) {
      if (this == o) {
         return true;
      } else if (o != null && this.getClass() == o.getClass()) {
         Vec3 vec3 = (Vec3)o;
         return Double.compare(vec3.x, this.x) == 0 && Double.compare(vec3.y, this.y) == 0 && Double.compare(vec3.z, this.z) == 0;
      } else {
         return false;
      }
   }

   public int hashCode() {
      long temp = Double.doubleToLongBits(this.x);
      int result = (int)(temp ^ temp >>> 32);
      temp = Double.doubleToLongBits(this.y);
      result = 31 * result + (int)(temp ^ temp >>> 32);
      temp = Double.doubleToLongBits(this.z);
      result = 31 * result + (int)(temp ^ temp >>> 32);
      return result;
   }

   public String toString() {
      return "Vec3{x=" + this.x + ", y=" + this.y + ", z=" + this.z + '}';
   }

   public Vec3 clone() {
      return new Vec3(this.x, this.y, this.z);
   }
}
