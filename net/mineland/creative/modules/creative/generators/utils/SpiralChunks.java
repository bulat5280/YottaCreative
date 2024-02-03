package net.mineland.creative.modules.creative.generators.utils;

import com.sk89q.worldedit.Vector2D;
import java.util.LinkedList;
import java.util.List;

public class SpiralChunks {
   Vector2D origin;
   int size;
   List<Vector2D> list = new LinkedList();
   SpiralChunks.Direction direction;
   Vector2D point;

   public SpiralChunks(Vector2D origin, int size) {
      this.direction = SpiralChunks.Direction.NORTH;
      this.origin = origin;
      this.size = size;
   }

   public List<Vector2D> spiral() {
      this.point = this.origin.toVector().toVector2D();
      double sqrt = Math.pow((double)(this.size * 2), 2.0D);

      for(int steps = 1; (double)this.list.size() < sqrt; ++steps) {
         this.advance(steps);
         this.advance(steps);
      }

      return this.list;
   }

   public void advance(int steps) {
      for(int i = 0; i < steps; ++i) {
         if (this.inBounds(this.point)) {
            this.list.add(this.point);
         }

         this.point = this.direction.add(this.point);
      }

      this.direction = this.direction.next();
   }

   private boolean inBounds(Vector2D vector2D) {
      return this.inRange(this.origin.getBlockX() - this.size, this.origin.getBlockX() + this.size - 1, vector2D.getBlockX()) && this.inRange(this.origin.getBlockZ() - this.size, this.origin.getBlockZ() + this.size - 1, vector2D.getBlockZ());
   }

   private boolean inRange(int min, int max, int i) {
      return min <= i && i <= max;
   }

   static enum Direction {
      NORTH(new Vector2D(0, -1)) {
         SpiralChunks.Direction next() {
            return WEST;
         }
      },
      WEST(new Vector2D(-1, 0)) {
         SpiralChunks.Direction next() {
            return SOUTH;
         }
      },
      SOUTH(new Vector2D(0, 1)) {
         SpiralChunks.Direction next() {
            return EAST;
         }
      },
      EAST(new Vector2D(1, 0)) {
         SpiralChunks.Direction next() {
            return NORTH;
         }
      };

      Vector2D vector2D;

      private Direction(Vector2D vector2D) {
         this.vector2D = vector2D;
      }

      abstract SpiralChunks.Direction next();

      Vector2D add(Vector2D vector2D) {
         return this.vector2D.add(vector2D);
      }

      // $FF: synthetic method
      Direction(Vector2D x2, Object x3) {
         this(x2);
      }
   }
}
