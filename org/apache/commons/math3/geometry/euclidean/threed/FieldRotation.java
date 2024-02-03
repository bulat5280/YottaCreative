package org.apache.commons.math3.geometry.euclidean.threed;

import java.io.Serializable;
import org.apache.commons.math3.Field;
import org.apache.commons.math3.RealFieldElement;
import org.apache.commons.math3.exception.MathArithmeticException;
import org.apache.commons.math3.exception.MathIllegalArgumentException;
import org.apache.commons.math3.exception.util.LocalizedFormats;
import org.apache.commons.math3.util.FastMath;
import org.apache.commons.math3.util.MathArrays;

public class FieldRotation<T extends RealFieldElement<T>> implements Serializable {
   private static final long serialVersionUID = 20130224L;
   private final T q0;
   private final T q1;
   private final T q2;
   private final T q3;

   public FieldRotation(T q0, T q1, T q2, T q3, boolean needsNormalization) {
      if (needsNormalization) {
         T inv = (RealFieldElement)((RealFieldElement)((RealFieldElement)((RealFieldElement)((RealFieldElement)((RealFieldElement)q0.multiply(q0)).add(q1.multiply(q1))).add(q2.multiply(q2))).add(q3.multiply(q3))).sqrt()).reciprocal();
         this.q0 = (RealFieldElement)inv.multiply(q0);
         this.q1 = (RealFieldElement)inv.multiply(q1);
         this.q2 = (RealFieldElement)inv.multiply(q2);
         this.q3 = (RealFieldElement)inv.multiply(q3);
      } else {
         this.q0 = q0;
         this.q1 = q1;
         this.q2 = q2;
         this.q3 = q3;
      }

   }

   public FieldRotation(FieldVector3D<T> axis, T angle) throws MathIllegalArgumentException {
      T norm = axis.getNorm();
      if (norm.getReal() == 0.0D) {
         throw new MathIllegalArgumentException(LocalizedFormats.ZERO_NORM_FOR_ROTATION_AXIS, new Object[0]);
      } else {
         T halfAngle = (RealFieldElement)angle.multiply(-0.5D);
         T coeff = (RealFieldElement)((RealFieldElement)halfAngle.sin()).divide(norm);
         this.q0 = (RealFieldElement)halfAngle.cos();
         this.q1 = (RealFieldElement)coeff.multiply(axis.getX());
         this.q2 = (RealFieldElement)coeff.multiply(axis.getY());
         this.q3 = (RealFieldElement)coeff.multiply(axis.getZ());
      }
   }

   public FieldRotation(T[][] m, double threshold) throws NotARotationMatrixException {
      if (m.length == 3 && m[0].length == 3 && m[1].length == 3 && m[2].length == 3) {
         T[][] ort = this.orthogonalizeMatrix(m, threshold);
         T d0 = (RealFieldElement)((RealFieldElement)ort[1][1].multiply(ort[2][2])).subtract(ort[2][1].multiply(ort[1][2]));
         T d1 = (RealFieldElement)((RealFieldElement)ort[0][1].multiply(ort[2][2])).subtract(ort[2][1].multiply(ort[0][2]));
         T d2 = (RealFieldElement)((RealFieldElement)ort[0][1].multiply(ort[1][2])).subtract(ort[1][1].multiply(ort[0][2]));
         T det = (RealFieldElement)((RealFieldElement)((RealFieldElement)ort[0][0].multiply(d0)).subtract(ort[1][0].multiply(d1))).add(ort[2][0].multiply(d2));
         if (det.getReal() < 0.0D) {
            throw new NotARotationMatrixException(LocalizedFormats.CLOSEST_ORTHOGONAL_MATRIX_HAS_NEGATIVE_DETERMINANT, new Object[]{det});
         } else {
            T[] quat = this.mat2quat(ort);
            this.q0 = quat[0];
            this.q1 = quat[1];
            this.q2 = quat[2];
            this.q3 = quat[3];
         }
      } else {
         throw new NotARotationMatrixException(LocalizedFormats.ROTATION_MATRIX_DIMENSIONS, new Object[]{m.length, m[0].length});
      }
   }

   public FieldRotation(FieldVector3D<T> u1, FieldVector3D<T> u2, FieldVector3D<T> v1, FieldVector3D<T> v2) throws MathArithmeticException {
      FieldVector3D<T> u3 = FieldVector3D.crossProduct(u1, u2).normalize();
      u2 = FieldVector3D.crossProduct(u3, u1).normalize();
      u1 = u1.normalize();
      FieldVector3D<T> v3 = FieldVector3D.crossProduct(v1, v2).normalize();
      v2 = FieldVector3D.crossProduct(v3, v1).normalize();
      v1 = v1.normalize();
      T[][] array = (RealFieldElement[][])MathArrays.buildArray(u1.getX().getField(), 3, 3);
      array[0][0] = (RealFieldElement)((RealFieldElement)((RealFieldElement)u1.getX().multiply(v1.getX())).add(u2.getX().multiply(v2.getX()))).add(u3.getX().multiply(v3.getX()));
      array[0][1] = (RealFieldElement)((RealFieldElement)((RealFieldElement)u1.getY().multiply(v1.getX())).add(u2.getY().multiply(v2.getX()))).add(u3.getY().multiply(v3.getX()));
      array[0][2] = (RealFieldElement)((RealFieldElement)((RealFieldElement)u1.getZ().multiply(v1.getX())).add(u2.getZ().multiply(v2.getX()))).add(u3.getZ().multiply(v3.getX()));
      array[1][0] = (RealFieldElement)((RealFieldElement)((RealFieldElement)u1.getX().multiply(v1.getY())).add(u2.getX().multiply(v2.getY()))).add(u3.getX().multiply(v3.getY()));
      array[1][1] = (RealFieldElement)((RealFieldElement)((RealFieldElement)u1.getY().multiply(v1.getY())).add(u2.getY().multiply(v2.getY()))).add(u3.getY().multiply(v3.getY()));
      array[1][2] = (RealFieldElement)((RealFieldElement)((RealFieldElement)u1.getZ().multiply(v1.getY())).add(u2.getZ().multiply(v2.getY()))).add(u3.getZ().multiply(v3.getY()));
      array[2][0] = (RealFieldElement)((RealFieldElement)((RealFieldElement)u1.getX().multiply(v1.getZ())).add(u2.getX().multiply(v2.getZ()))).add(u3.getX().multiply(v3.getZ()));
      array[2][1] = (RealFieldElement)((RealFieldElement)((RealFieldElement)u1.getY().multiply(v1.getZ())).add(u2.getY().multiply(v2.getZ()))).add(u3.getY().multiply(v3.getZ()));
      array[2][2] = (RealFieldElement)((RealFieldElement)((RealFieldElement)u1.getZ().multiply(v1.getZ())).add(u2.getZ().multiply(v2.getZ()))).add(u3.getZ().multiply(v3.getZ()));
      T[] quat = this.mat2quat(array);
      this.q0 = quat[0];
      this.q1 = quat[1];
      this.q2 = quat[2];
      this.q3 = quat[3];
   }

   public FieldRotation(FieldVector3D<T> u, FieldVector3D<T> v) throws MathArithmeticException {
      T normProduct = (RealFieldElement)u.getNorm().multiply(v.getNorm());
      if (normProduct.getReal() == 0.0D) {
         throw new MathArithmeticException(LocalizedFormats.ZERO_NORM_FOR_ROTATION_DEFINING_VECTOR, new Object[0]);
      } else {
         T dot = FieldVector3D.dotProduct(u, v);
         if (dot.getReal() < -0.999999999999998D * normProduct.getReal()) {
            FieldVector3D<T> w = u.orthogonal();
            this.q0 = (RealFieldElement)normProduct.getField().getZero();
            this.q1 = (RealFieldElement)w.getX().negate();
            this.q2 = (RealFieldElement)w.getY().negate();
            this.q3 = (RealFieldElement)w.getZ().negate();
         } else {
            this.q0 = (RealFieldElement)((RealFieldElement)((RealFieldElement)((RealFieldElement)dot.divide(normProduct)).add(1.0D)).multiply(0.5D)).sqrt();
            T coeff = (RealFieldElement)((RealFieldElement)((RealFieldElement)this.q0.multiply(normProduct)).multiply(2.0D)).reciprocal();
            FieldVector3D<T> q = FieldVector3D.crossProduct(v, u);
            this.q1 = (RealFieldElement)coeff.multiply(q.getX());
            this.q2 = (RealFieldElement)coeff.multiply(q.getY());
            this.q3 = (RealFieldElement)coeff.multiply(q.getZ());
         }

      }
   }

   public FieldRotation(RotationOrder order, T alpha1, T alpha2, T alpha3) {
      T one = (RealFieldElement)alpha1.getField().getOne();
      FieldRotation<T> r1 = new FieldRotation(new FieldVector3D(one, order.getA1()), alpha1);
      FieldRotation<T> r2 = new FieldRotation(new FieldVector3D(one, order.getA2()), alpha2);
      FieldRotation<T> r3 = new FieldRotation(new FieldVector3D(one, order.getA3()), alpha3);
      FieldRotation<T> composed = r1.applyTo(r2.applyTo(r3));
      this.q0 = composed.q0;
      this.q1 = composed.q1;
      this.q2 = composed.q2;
      this.q3 = composed.q3;
   }

   private T[] mat2quat(T[][] ort) {
      T[] quat = (RealFieldElement[])MathArrays.buildArray(ort[0][0].getField(), 4);
      T s = (RealFieldElement)((RealFieldElement)ort[0][0].add(ort[1][1])).add(ort[2][2]);
      RealFieldElement inv;
      if (s.getReal() > -0.19D) {
         quat[0] = (RealFieldElement)((RealFieldElement)((RealFieldElement)s.add(1.0D)).sqrt()).multiply(0.5D);
         inv = (RealFieldElement)((RealFieldElement)quat[0].reciprocal()).multiply(0.25D);
         quat[1] = (RealFieldElement)inv.multiply(ort[1][2].subtract(ort[2][1]));
         quat[2] = (RealFieldElement)inv.multiply(ort[2][0].subtract(ort[0][2]));
         quat[3] = (RealFieldElement)inv.multiply(ort[0][1].subtract(ort[1][0]));
      } else {
         s = (RealFieldElement)((RealFieldElement)ort[0][0].subtract(ort[1][1])).subtract(ort[2][2]);
         if (s.getReal() > -0.19D) {
            quat[1] = (RealFieldElement)((RealFieldElement)((RealFieldElement)s.add(1.0D)).sqrt()).multiply(0.5D);
            inv = (RealFieldElement)((RealFieldElement)quat[1].reciprocal()).multiply(0.25D);
            quat[0] = (RealFieldElement)inv.multiply(ort[1][2].subtract(ort[2][1]));
            quat[2] = (RealFieldElement)inv.multiply(ort[0][1].add(ort[1][0]));
            quat[3] = (RealFieldElement)inv.multiply(ort[0][2].add(ort[2][0]));
         } else {
            s = (RealFieldElement)((RealFieldElement)ort[1][1].subtract(ort[0][0])).subtract(ort[2][2]);
            if (s.getReal() > -0.19D) {
               quat[2] = (RealFieldElement)((RealFieldElement)((RealFieldElement)s.add(1.0D)).sqrt()).multiply(0.5D);
               inv = (RealFieldElement)((RealFieldElement)quat[2].reciprocal()).multiply(0.25D);
               quat[0] = (RealFieldElement)inv.multiply(ort[2][0].subtract(ort[0][2]));
               quat[1] = (RealFieldElement)inv.multiply(ort[0][1].add(ort[1][0]));
               quat[3] = (RealFieldElement)inv.multiply(ort[2][1].add(ort[1][2]));
            } else {
               s = (RealFieldElement)((RealFieldElement)ort[2][2].subtract(ort[0][0])).subtract(ort[1][1]);
               quat[3] = (RealFieldElement)((RealFieldElement)((RealFieldElement)s.add(1.0D)).sqrt()).multiply(0.5D);
               inv = (RealFieldElement)((RealFieldElement)quat[3].reciprocal()).multiply(0.25D);
               quat[0] = (RealFieldElement)inv.multiply(ort[0][1].subtract(ort[1][0]));
               quat[1] = (RealFieldElement)inv.multiply(ort[0][2].add(ort[2][0]));
               quat[2] = (RealFieldElement)inv.multiply(ort[2][1].add(ort[1][2]));
            }
         }
      }

      return quat;
   }

   public FieldRotation<T> revert() {
      return new FieldRotation((RealFieldElement)this.q0.negate(), this.q1, this.q2, this.q3, false);
   }

   public T getQ0() {
      return this.q0;
   }

   public T getQ1() {
      return this.q1;
   }

   public T getQ2() {
      return this.q2;
   }

   public T getQ3() {
      return this.q3;
   }

   public FieldVector3D<T> getAxis() {
      T squaredSine = (RealFieldElement)((RealFieldElement)((RealFieldElement)this.q1.multiply(this.q1)).add(this.q2.multiply(this.q2))).add(this.q3.multiply(this.q3));
      if (squaredSine.getReal() == 0.0D) {
         Field<T> field = squaredSine.getField();
         return new FieldVector3D((RealFieldElement)field.getOne(), (RealFieldElement)field.getZero(), (RealFieldElement)field.getZero());
      } else {
         RealFieldElement inverse;
         if (this.q0.getReal() < 0.0D) {
            inverse = (RealFieldElement)((RealFieldElement)squaredSine.sqrt()).reciprocal();
            return new FieldVector3D((RealFieldElement)this.q1.multiply(inverse), (RealFieldElement)this.q2.multiply(inverse), (RealFieldElement)this.q3.multiply(inverse));
         } else {
            inverse = (RealFieldElement)((RealFieldElement)((RealFieldElement)squaredSine.sqrt()).reciprocal()).negate();
            return new FieldVector3D((RealFieldElement)this.q1.multiply(inverse), (RealFieldElement)this.q2.multiply(inverse), (RealFieldElement)this.q3.multiply(inverse));
         }
      }
   }

   public T getAngle() {
      if (!(this.q0.getReal() < -0.1D) && !(this.q0.getReal() > 0.1D)) {
         return this.q0.getReal() < 0.0D ? (RealFieldElement)((RealFieldElement)((RealFieldElement)this.q0.negate()).acos()).multiply(2) : (RealFieldElement)((RealFieldElement)this.q0.acos()).multiply(2);
      } else {
         return (RealFieldElement)((RealFieldElement)((RealFieldElement)((RealFieldElement)((RealFieldElement)((RealFieldElement)this.q1.multiply(this.q1)).add(this.q2.multiply(this.q2))).add(this.q3.multiply(this.q3))).sqrt()).asin()).multiply(2);
      }
   }

   public T[] getAngles(RotationOrder order) throws CardanEulerSingularityException {
      FieldVector3D v1;
      FieldVector3D v2;
      if (order == RotationOrder.XYZ) {
         v1 = this.applyTo(this.vector(0.0D, 0.0D, 1.0D));
         v2 = this.applyInverseTo(this.vector(1.0D, 0.0D, 0.0D));
         if (!(v2.getZ().getReal() < -0.9999999999D) && !(v2.getZ().getReal() > 0.9999999999D)) {
            return this.buildArray((RealFieldElement)((RealFieldElement)v1.getY().negate()).atan2(v1.getZ()), (RealFieldElement)v2.getZ().asin(), (RealFieldElement)((RealFieldElement)v2.getY().negate()).atan2(v2.getX()));
         } else {
            throw new CardanEulerSingularityException(true);
         }
      } else if (order == RotationOrder.XZY) {
         v1 = this.applyTo(this.vector(0.0D, 1.0D, 0.0D));
         v2 = this.applyInverseTo(this.vector(1.0D, 0.0D, 0.0D));
         if (!(v2.getY().getReal() < -0.9999999999D) && !(v2.getY().getReal() > 0.9999999999D)) {
            return this.buildArray((RealFieldElement)v1.getZ().atan2(v1.getY()), (RealFieldElement)((RealFieldElement)v2.getY().asin()).negate(), (RealFieldElement)v2.getZ().atan2(v2.getX()));
         } else {
            throw new CardanEulerSingularityException(true);
         }
      } else if (order == RotationOrder.YXZ) {
         v1 = this.applyTo(this.vector(0.0D, 0.0D, 1.0D));
         v2 = this.applyInverseTo(this.vector(0.0D, 1.0D, 0.0D));
         if (!(v2.getZ().getReal() < -0.9999999999D) && !(v2.getZ().getReal() > 0.9999999999D)) {
            return this.buildArray((RealFieldElement)v1.getX().atan2(v1.getZ()), (RealFieldElement)((RealFieldElement)v2.getZ().asin()).negate(), (RealFieldElement)v2.getX().atan2(v2.getY()));
         } else {
            throw new CardanEulerSingularityException(true);
         }
      } else if (order == RotationOrder.YZX) {
         v1 = this.applyTo(this.vector(1.0D, 0.0D, 0.0D));
         v2 = this.applyInverseTo(this.vector(0.0D, 1.0D, 0.0D));
         if (!(v2.getX().getReal() < -0.9999999999D) && !(v2.getX().getReal() > 0.9999999999D)) {
            return this.buildArray((RealFieldElement)((RealFieldElement)v1.getZ().negate()).atan2(v1.getX()), (RealFieldElement)v2.getX().asin(), (RealFieldElement)((RealFieldElement)v2.getZ().negate()).atan2(v2.getY()));
         } else {
            throw new CardanEulerSingularityException(true);
         }
      } else if (order == RotationOrder.ZXY) {
         v1 = this.applyTo(this.vector(0.0D, 1.0D, 0.0D));
         v2 = this.applyInverseTo(this.vector(0.0D, 0.0D, 1.0D));
         if (!(v2.getY().getReal() < -0.9999999999D) && !(v2.getY().getReal() > 0.9999999999D)) {
            return this.buildArray((RealFieldElement)((RealFieldElement)v1.getX().negate()).atan2(v1.getY()), (RealFieldElement)v2.getY().asin(), (RealFieldElement)((RealFieldElement)v2.getX().negate()).atan2(v2.getZ()));
         } else {
            throw new CardanEulerSingularityException(true);
         }
      } else if (order == RotationOrder.ZYX) {
         v1 = this.applyTo(this.vector(1.0D, 0.0D, 0.0D));
         v2 = this.applyInverseTo(this.vector(0.0D, 0.0D, 1.0D));
         if (!(v2.getX().getReal() < -0.9999999999D) && !(v2.getX().getReal() > 0.9999999999D)) {
            return this.buildArray((RealFieldElement)v1.getY().atan2(v1.getX()), (RealFieldElement)((RealFieldElement)v2.getX().asin()).negate(), (RealFieldElement)v2.getY().atan2(v2.getZ()));
         } else {
            throw new CardanEulerSingularityException(true);
         }
      } else if (order == RotationOrder.XYX) {
         v1 = this.applyTo(this.vector(1.0D, 0.0D, 0.0D));
         v2 = this.applyInverseTo(this.vector(1.0D, 0.0D, 0.0D));
         if (!(v2.getX().getReal() < -0.9999999999D) && !(v2.getX().getReal() > 0.9999999999D)) {
            return this.buildArray((RealFieldElement)v1.getY().atan2(v1.getZ().negate()), (RealFieldElement)v2.getX().acos(), (RealFieldElement)v2.getY().atan2(v2.getZ()));
         } else {
            throw new CardanEulerSingularityException(false);
         }
      } else if (order == RotationOrder.XZX) {
         v1 = this.applyTo(this.vector(1.0D, 0.0D, 0.0D));
         v2 = this.applyInverseTo(this.vector(1.0D, 0.0D, 0.0D));
         if (!(v2.getX().getReal() < -0.9999999999D) && !(v2.getX().getReal() > 0.9999999999D)) {
            return this.buildArray((RealFieldElement)v1.getZ().atan2(v1.getY()), (RealFieldElement)v2.getX().acos(), (RealFieldElement)v2.getZ().atan2(v2.getY().negate()));
         } else {
            throw new CardanEulerSingularityException(false);
         }
      } else if (order == RotationOrder.YXY) {
         v1 = this.applyTo(this.vector(0.0D, 1.0D, 0.0D));
         v2 = this.applyInverseTo(this.vector(0.0D, 1.0D, 0.0D));
         if (!(v2.getY().getReal() < -0.9999999999D) && !(v2.getY().getReal() > 0.9999999999D)) {
            return this.buildArray((RealFieldElement)v1.getX().atan2(v1.getZ()), (RealFieldElement)v2.getY().acos(), (RealFieldElement)v2.getX().atan2(v2.getZ().negate()));
         } else {
            throw new CardanEulerSingularityException(false);
         }
      } else if (order == RotationOrder.YZY) {
         v1 = this.applyTo(this.vector(0.0D, 1.0D, 0.0D));
         v2 = this.applyInverseTo(this.vector(0.0D, 1.0D, 0.0D));
         if (!(v2.getY().getReal() < -0.9999999999D) && !(v2.getY().getReal() > 0.9999999999D)) {
            return this.buildArray((RealFieldElement)v1.getZ().atan2(v1.getX().negate()), (RealFieldElement)v2.getY().acos(), (RealFieldElement)v2.getZ().atan2(v2.getX()));
         } else {
            throw new CardanEulerSingularityException(false);
         }
      } else if (order == RotationOrder.ZXZ) {
         v1 = this.applyTo(this.vector(0.0D, 0.0D, 1.0D));
         v2 = this.applyInverseTo(this.vector(0.0D, 0.0D, 1.0D));
         if (!(v2.getZ().getReal() < -0.9999999999D) && !(v2.getZ().getReal() > 0.9999999999D)) {
            return this.buildArray((RealFieldElement)v1.getX().atan2(v1.getY().negate()), (RealFieldElement)v2.getZ().acos(), (RealFieldElement)v2.getX().atan2(v2.getY()));
         } else {
            throw new CardanEulerSingularityException(false);
         }
      } else {
         v1 = this.applyTo(this.vector(0.0D, 0.0D, 1.0D));
         v2 = this.applyInverseTo(this.vector(0.0D, 0.0D, 1.0D));
         if (!(v2.getZ().getReal() < -0.9999999999D) && !(v2.getZ().getReal() > 0.9999999999D)) {
            return this.buildArray((RealFieldElement)v1.getY().atan2(v1.getX()), (RealFieldElement)v2.getZ().acos(), (RealFieldElement)v2.getY().atan2(v2.getX().negate()));
         } else {
            throw new CardanEulerSingularityException(false);
         }
      }
   }

   private T[] buildArray(T a0, T a1, T a2) {
      T[] array = (RealFieldElement[])MathArrays.buildArray(a0.getField(), 3);
      array[0] = a0;
      array[1] = a1;
      array[2] = a2;
      return array;
   }

   private FieldVector3D<T> vector(double x, double y, double z) {
      T zero = (RealFieldElement)this.q0.getField().getZero();
      return new FieldVector3D((RealFieldElement)zero.add(x), (RealFieldElement)zero.add(y), (RealFieldElement)zero.add(z));
   }

   public T[][] getMatrix() {
      T q0q0 = (RealFieldElement)this.q0.multiply(this.q0);
      T q0q1 = (RealFieldElement)this.q0.multiply(this.q1);
      T q0q2 = (RealFieldElement)this.q0.multiply(this.q2);
      T q0q3 = (RealFieldElement)this.q0.multiply(this.q3);
      T q1q1 = (RealFieldElement)this.q1.multiply(this.q1);
      T q1q2 = (RealFieldElement)this.q1.multiply(this.q2);
      T q1q3 = (RealFieldElement)this.q1.multiply(this.q3);
      T q2q2 = (RealFieldElement)this.q2.multiply(this.q2);
      T q2q3 = (RealFieldElement)this.q2.multiply(this.q3);
      T q3q3 = (RealFieldElement)this.q3.multiply(this.q3);
      T[][] m = (RealFieldElement[][])MathArrays.buildArray(this.q0.getField(), 3, 3);
      m[0][0] = (RealFieldElement)((RealFieldElement)((RealFieldElement)q0q0.add(q1q1)).multiply(2)).subtract(1.0D);
      m[1][0] = (RealFieldElement)((RealFieldElement)q1q2.subtract(q0q3)).multiply(2);
      m[2][0] = (RealFieldElement)((RealFieldElement)q1q3.add(q0q2)).multiply(2);
      m[0][1] = (RealFieldElement)((RealFieldElement)q1q2.add(q0q3)).multiply(2);
      m[1][1] = (RealFieldElement)((RealFieldElement)((RealFieldElement)q0q0.add(q2q2)).multiply(2)).subtract(1.0D);
      m[2][1] = (RealFieldElement)((RealFieldElement)q2q3.subtract(q0q1)).multiply(2);
      m[0][2] = (RealFieldElement)((RealFieldElement)q1q3.subtract(q0q2)).multiply(2);
      m[1][2] = (RealFieldElement)((RealFieldElement)q2q3.add(q0q1)).multiply(2);
      m[2][2] = (RealFieldElement)((RealFieldElement)((RealFieldElement)q0q0.add(q3q3)).multiply(2)).subtract(1.0D);
      return m;
   }

   public Rotation toRotation() {
      return new Rotation(this.q0.getReal(), this.q1.getReal(), this.q2.getReal(), this.q3.getReal(), false);
   }

   public FieldVector3D<T> applyTo(FieldVector3D<T> u) {
      T x = u.getX();
      T y = u.getY();
      T z = u.getZ();
      T s = (RealFieldElement)((RealFieldElement)((RealFieldElement)this.q1.multiply(x)).add(this.q2.multiply(y))).add(this.q3.multiply(z));
      return new FieldVector3D((RealFieldElement)((RealFieldElement)((RealFieldElement)((RealFieldElement)this.q0.multiply(((RealFieldElement)x.multiply(this.q0)).subtract(((RealFieldElement)this.q2.multiply(z)).subtract(this.q3.multiply(y))))).add(s.multiply(this.q1))).multiply(2)).subtract(x), (RealFieldElement)((RealFieldElement)((RealFieldElement)((RealFieldElement)this.q0.multiply(((RealFieldElement)y.multiply(this.q0)).subtract(((RealFieldElement)this.q3.multiply(x)).subtract(this.q1.multiply(z))))).add(s.multiply(this.q2))).multiply(2)).subtract(y), (RealFieldElement)((RealFieldElement)((RealFieldElement)((RealFieldElement)this.q0.multiply(((RealFieldElement)z.multiply(this.q0)).subtract(((RealFieldElement)this.q1.multiply(y)).subtract(this.q2.multiply(x))))).add(s.multiply(this.q3))).multiply(2)).subtract(z));
   }

   public FieldVector3D<T> applyTo(Vector3D u) {
      double x = u.getX();
      double y = u.getY();
      double z = u.getZ();
      T s = (RealFieldElement)((RealFieldElement)((RealFieldElement)this.q1.multiply(x)).add(this.q2.multiply(y))).add(this.q3.multiply(z));
      return new FieldVector3D((RealFieldElement)((RealFieldElement)((RealFieldElement)((RealFieldElement)this.q0.multiply(((RealFieldElement)this.q0.multiply(x)).subtract(((RealFieldElement)this.q2.multiply(z)).subtract(this.q3.multiply(y))))).add(s.multiply(this.q1))).multiply(2)).subtract(x), (RealFieldElement)((RealFieldElement)((RealFieldElement)((RealFieldElement)this.q0.multiply(((RealFieldElement)this.q0.multiply(y)).subtract(((RealFieldElement)this.q3.multiply(x)).subtract(this.q1.multiply(z))))).add(s.multiply(this.q2))).multiply(2)).subtract(y), (RealFieldElement)((RealFieldElement)((RealFieldElement)((RealFieldElement)this.q0.multiply(((RealFieldElement)this.q0.multiply(z)).subtract(((RealFieldElement)this.q1.multiply(y)).subtract(this.q2.multiply(x))))).add(s.multiply(this.q3))).multiply(2)).subtract(z));
   }

   public void applyTo(T[] in, T[] out) {
      T x = in[0];
      T y = in[1];
      T z = in[2];
      T s = (RealFieldElement)((RealFieldElement)((RealFieldElement)this.q1.multiply(x)).add(this.q2.multiply(y))).add(this.q3.multiply(z));
      out[0] = (RealFieldElement)((RealFieldElement)((RealFieldElement)((RealFieldElement)this.q0.multiply(((RealFieldElement)x.multiply(this.q0)).subtract(((RealFieldElement)this.q2.multiply(z)).subtract(this.q3.multiply(y))))).add(s.multiply(this.q1))).multiply(2)).subtract(x);
      out[1] = (RealFieldElement)((RealFieldElement)((RealFieldElement)((RealFieldElement)this.q0.multiply(((RealFieldElement)y.multiply(this.q0)).subtract(((RealFieldElement)this.q3.multiply(x)).subtract(this.q1.multiply(z))))).add(s.multiply(this.q2))).multiply(2)).subtract(y);
      out[2] = (RealFieldElement)((RealFieldElement)((RealFieldElement)((RealFieldElement)this.q0.multiply(((RealFieldElement)z.multiply(this.q0)).subtract(((RealFieldElement)this.q1.multiply(y)).subtract(this.q2.multiply(x))))).add(s.multiply(this.q3))).multiply(2)).subtract(z);
   }

   public void applyTo(double[] in, T[] out) {
      double x = in[0];
      double y = in[1];
      double z = in[2];
      T s = (RealFieldElement)((RealFieldElement)((RealFieldElement)this.q1.multiply(x)).add(this.q2.multiply(y))).add(this.q3.multiply(z));
      out[0] = (RealFieldElement)((RealFieldElement)((RealFieldElement)((RealFieldElement)this.q0.multiply(((RealFieldElement)this.q0.multiply(x)).subtract(((RealFieldElement)this.q2.multiply(z)).subtract(this.q3.multiply(y))))).add(s.multiply(this.q1))).multiply(2)).subtract(x);
      out[1] = (RealFieldElement)((RealFieldElement)((RealFieldElement)((RealFieldElement)this.q0.multiply(((RealFieldElement)this.q0.multiply(y)).subtract(((RealFieldElement)this.q3.multiply(x)).subtract(this.q1.multiply(z))))).add(s.multiply(this.q2))).multiply(2)).subtract(y);
      out[2] = (RealFieldElement)((RealFieldElement)((RealFieldElement)((RealFieldElement)this.q0.multiply(((RealFieldElement)this.q0.multiply(z)).subtract(((RealFieldElement)this.q1.multiply(y)).subtract(this.q2.multiply(x))))).add(s.multiply(this.q3))).multiply(2)).subtract(z);
   }

   public static <T extends RealFieldElement<T>> FieldVector3D<T> applyTo(Rotation r, FieldVector3D<T> u) {
      T x = u.getX();
      T y = u.getY();
      T z = u.getZ();
      T s = (RealFieldElement)((RealFieldElement)((RealFieldElement)x.multiply(r.getQ1())).add(y.multiply(r.getQ2()))).add(z.multiply(r.getQ3()));
      return new FieldVector3D((RealFieldElement)((RealFieldElement)((RealFieldElement)((RealFieldElement)((RealFieldElement)((RealFieldElement)x.multiply(r.getQ0())).subtract(((RealFieldElement)z.multiply(r.getQ2())).subtract(y.multiply(r.getQ3())))).multiply(r.getQ0())).add(s.multiply(r.getQ1()))).multiply(2)).subtract(x), (RealFieldElement)((RealFieldElement)((RealFieldElement)((RealFieldElement)((RealFieldElement)((RealFieldElement)y.multiply(r.getQ0())).subtract(((RealFieldElement)x.multiply(r.getQ3())).subtract(z.multiply(r.getQ1())))).multiply(r.getQ0())).add(s.multiply(r.getQ2()))).multiply(2)).subtract(y), (RealFieldElement)((RealFieldElement)((RealFieldElement)((RealFieldElement)((RealFieldElement)((RealFieldElement)z.multiply(r.getQ0())).subtract(((RealFieldElement)y.multiply(r.getQ1())).subtract(x.multiply(r.getQ2())))).multiply(r.getQ0())).add(s.multiply(r.getQ3()))).multiply(2)).subtract(z));
   }

   public FieldVector3D<T> applyInverseTo(FieldVector3D<T> u) {
      T x = u.getX();
      T y = u.getY();
      T z = u.getZ();
      T s = (RealFieldElement)((RealFieldElement)((RealFieldElement)this.q1.multiply(x)).add(this.q2.multiply(y))).add(this.q3.multiply(z));
      T m0 = (RealFieldElement)this.q0.negate();
      return new FieldVector3D((RealFieldElement)((RealFieldElement)((RealFieldElement)((RealFieldElement)m0.multiply(((RealFieldElement)x.multiply(m0)).subtract(((RealFieldElement)this.q2.multiply(z)).subtract(this.q3.multiply(y))))).add(s.multiply(this.q1))).multiply(2)).subtract(x), (RealFieldElement)((RealFieldElement)((RealFieldElement)((RealFieldElement)m0.multiply(((RealFieldElement)y.multiply(m0)).subtract(((RealFieldElement)this.q3.multiply(x)).subtract(this.q1.multiply(z))))).add(s.multiply(this.q2))).multiply(2)).subtract(y), (RealFieldElement)((RealFieldElement)((RealFieldElement)((RealFieldElement)m0.multiply(((RealFieldElement)z.multiply(m0)).subtract(((RealFieldElement)this.q1.multiply(y)).subtract(this.q2.multiply(x))))).add(s.multiply(this.q3))).multiply(2)).subtract(z));
   }

   public FieldVector3D<T> applyInverseTo(Vector3D u) {
      double x = u.getX();
      double y = u.getY();
      double z = u.getZ();
      T s = (RealFieldElement)((RealFieldElement)((RealFieldElement)this.q1.multiply(x)).add(this.q2.multiply(y))).add(this.q3.multiply(z));
      T m0 = (RealFieldElement)this.q0.negate();
      return new FieldVector3D((RealFieldElement)((RealFieldElement)((RealFieldElement)((RealFieldElement)m0.multiply(((RealFieldElement)m0.multiply(x)).subtract(((RealFieldElement)this.q2.multiply(z)).subtract(this.q3.multiply(y))))).add(s.multiply(this.q1))).multiply(2)).subtract(x), (RealFieldElement)((RealFieldElement)((RealFieldElement)((RealFieldElement)m0.multiply(((RealFieldElement)m0.multiply(y)).subtract(((RealFieldElement)this.q3.multiply(x)).subtract(this.q1.multiply(z))))).add(s.multiply(this.q2))).multiply(2)).subtract(y), (RealFieldElement)((RealFieldElement)((RealFieldElement)((RealFieldElement)m0.multiply(((RealFieldElement)m0.multiply(z)).subtract(((RealFieldElement)this.q1.multiply(y)).subtract(this.q2.multiply(x))))).add(s.multiply(this.q3))).multiply(2)).subtract(z));
   }

   public void applyInverseTo(T[] in, T[] out) {
      T x = in[0];
      T y = in[1];
      T z = in[2];
      T s = (RealFieldElement)((RealFieldElement)((RealFieldElement)this.q1.multiply(x)).add(this.q2.multiply(y))).add(this.q3.multiply(z));
      T m0 = (RealFieldElement)this.q0.negate();
      out[0] = (RealFieldElement)((RealFieldElement)((RealFieldElement)((RealFieldElement)m0.multiply(((RealFieldElement)x.multiply(m0)).subtract(((RealFieldElement)this.q2.multiply(z)).subtract(this.q3.multiply(y))))).add(s.multiply(this.q1))).multiply(2)).subtract(x);
      out[1] = (RealFieldElement)((RealFieldElement)((RealFieldElement)((RealFieldElement)m0.multiply(((RealFieldElement)y.multiply(m0)).subtract(((RealFieldElement)this.q3.multiply(x)).subtract(this.q1.multiply(z))))).add(s.multiply(this.q2))).multiply(2)).subtract(y);
      out[2] = (RealFieldElement)((RealFieldElement)((RealFieldElement)((RealFieldElement)m0.multiply(((RealFieldElement)z.multiply(m0)).subtract(((RealFieldElement)this.q1.multiply(y)).subtract(this.q2.multiply(x))))).add(s.multiply(this.q3))).multiply(2)).subtract(z);
   }

   public void applyInverseTo(double[] in, T[] out) {
      double x = in[0];
      double y = in[1];
      double z = in[2];
      T s = (RealFieldElement)((RealFieldElement)((RealFieldElement)this.q1.multiply(x)).add(this.q2.multiply(y))).add(this.q3.multiply(z));
      T m0 = (RealFieldElement)this.q0.negate();
      out[0] = (RealFieldElement)((RealFieldElement)((RealFieldElement)((RealFieldElement)m0.multiply(((RealFieldElement)m0.multiply(x)).subtract(((RealFieldElement)this.q2.multiply(z)).subtract(this.q3.multiply(y))))).add(s.multiply(this.q1))).multiply(2)).subtract(x);
      out[1] = (RealFieldElement)((RealFieldElement)((RealFieldElement)((RealFieldElement)m0.multiply(((RealFieldElement)m0.multiply(y)).subtract(((RealFieldElement)this.q3.multiply(x)).subtract(this.q1.multiply(z))))).add(s.multiply(this.q2))).multiply(2)).subtract(y);
      out[2] = (RealFieldElement)((RealFieldElement)((RealFieldElement)((RealFieldElement)m0.multiply(((RealFieldElement)m0.multiply(z)).subtract(((RealFieldElement)this.q1.multiply(y)).subtract(this.q2.multiply(x))))).add(s.multiply(this.q3))).multiply(2)).subtract(z);
   }

   public static <T extends RealFieldElement<T>> FieldVector3D<T> applyInverseTo(Rotation r, FieldVector3D<T> u) {
      T x = u.getX();
      T y = u.getY();
      T z = u.getZ();
      T s = (RealFieldElement)((RealFieldElement)((RealFieldElement)x.multiply(r.getQ1())).add(y.multiply(r.getQ2()))).add(z.multiply(r.getQ3()));
      double m0 = -r.getQ0();
      return new FieldVector3D((RealFieldElement)((RealFieldElement)((RealFieldElement)((RealFieldElement)((RealFieldElement)((RealFieldElement)x.multiply(m0)).subtract(((RealFieldElement)z.multiply(r.getQ2())).subtract(y.multiply(r.getQ3())))).multiply(m0)).add(s.multiply(r.getQ1()))).multiply(2)).subtract(x), (RealFieldElement)((RealFieldElement)((RealFieldElement)((RealFieldElement)((RealFieldElement)((RealFieldElement)y.multiply(m0)).subtract(((RealFieldElement)x.multiply(r.getQ3())).subtract(z.multiply(r.getQ1())))).multiply(m0)).add(s.multiply(r.getQ2()))).multiply(2)).subtract(y), (RealFieldElement)((RealFieldElement)((RealFieldElement)((RealFieldElement)((RealFieldElement)((RealFieldElement)z.multiply(m0)).subtract(((RealFieldElement)y.multiply(r.getQ1())).subtract(x.multiply(r.getQ2())))).multiply(m0)).add(s.multiply(r.getQ3()))).multiply(2)).subtract(z));
   }

   public FieldRotation<T> applyTo(FieldRotation<T> r) {
      return new FieldRotation((RealFieldElement)((RealFieldElement)r.q0.multiply(this.q0)).subtract(((RealFieldElement)((RealFieldElement)r.q1.multiply(this.q1)).add(r.q2.multiply(this.q2))).add(r.q3.multiply(this.q3))), (RealFieldElement)((RealFieldElement)((RealFieldElement)r.q1.multiply(this.q0)).add(r.q0.multiply(this.q1))).add(((RealFieldElement)r.q2.multiply(this.q3)).subtract(r.q3.multiply(this.q2))), (RealFieldElement)((RealFieldElement)((RealFieldElement)r.q2.multiply(this.q0)).add(r.q0.multiply(this.q2))).add(((RealFieldElement)r.q3.multiply(this.q1)).subtract(r.q1.multiply(this.q3))), (RealFieldElement)((RealFieldElement)((RealFieldElement)r.q3.multiply(this.q0)).add(r.q0.multiply(this.q3))).add(((RealFieldElement)r.q1.multiply(this.q2)).subtract(r.q2.multiply(this.q1))), false);
   }

   public FieldRotation<T> applyTo(Rotation r) {
      return new FieldRotation((RealFieldElement)((RealFieldElement)this.q0.multiply(r.getQ0())).subtract(((RealFieldElement)((RealFieldElement)this.q1.multiply(r.getQ1())).add(this.q2.multiply(r.getQ2()))).add(this.q3.multiply(r.getQ3()))), (RealFieldElement)((RealFieldElement)((RealFieldElement)this.q0.multiply(r.getQ1())).add(this.q1.multiply(r.getQ0()))).add(((RealFieldElement)this.q3.multiply(r.getQ2())).subtract(this.q2.multiply(r.getQ3()))), (RealFieldElement)((RealFieldElement)((RealFieldElement)this.q0.multiply(r.getQ2())).add(this.q2.multiply(r.getQ0()))).add(((RealFieldElement)this.q1.multiply(r.getQ3())).subtract(this.q3.multiply(r.getQ1()))), (RealFieldElement)((RealFieldElement)((RealFieldElement)this.q0.multiply(r.getQ3())).add(this.q3.multiply(r.getQ0()))).add(((RealFieldElement)this.q2.multiply(r.getQ1())).subtract(this.q1.multiply(r.getQ2()))), false);
   }

   public static <T extends RealFieldElement<T>> FieldRotation<T> applyTo(Rotation r1, FieldRotation<T> rInner) {
      return new FieldRotation((RealFieldElement)((RealFieldElement)rInner.q0.multiply(r1.getQ0())).subtract(((RealFieldElement)((RealFieldElement)rInner.q1.multiply(r1.getQ1())).add(rInner.q2.multiply(r1.getQ2()))).add(rInner.q3.multiply(r1.getQ3()))), (RealFieldElement)((RealFieldElement)((RealFieldElement)rInner.q1.multiply(r1.getQ0())).add(rInner.q0.multiply(r1.getQ1()))).add(((RealFieldElement)rInner.q2.multiply(r1.getQ3())).subtract(rInner.q3.multiply(r1.getQ2()))), (RealFieldElement)((RealFieldElement)((RealFieldElement)rInner.q2.multiply(r1.getQ0())).add(rInner.q0.multiply(r1.getQ2()))).add(((RealFieldElement)rInner.q3.multiply(r1.getQ1())).subtract(rInner.q1.multiply(r1.getQ3()))), (RealFieldElement)((RealFieldElement)((RealFieldElement)rInner.q3.multiply(r1.getQ0())).add(rInner.q0.multiply(r1.getQ3()))).add(((RealFieldElement)rInner.q1.multiply(r1.getQ2())).subtract(rInner.q2.multiply(r1.getQ1()))), false);
   }

   public FieldRotation<T> applyInverseTo(FieldRotation<T> r) {
      return new FieldRotation((RealFieldElement)((RealFieldElement)((RealFieldElement)r.q0.multiply(this.q0)).add(((RealFieldElement)((RealFieldElement)r.q1.multiply(this.q1)).add(r.q2.multiply(this.q2))).add(r.q3.multiply(this.q3)))).negate(), (RealFieldElement)((RealFieldElement)((RealFieldElement)r.q0.multiply(this.q1)).add(((RealFieldElement)r.q2.multiply(this.q3)).subtract(r.q3.multiply(this.q2)))).subtract(r.q1.multiply(this.q0)), (RealFieldElement)((RealFieldElement)((RealFieldElement)r.q0.multiply(this.q2)).add(((RealFieldElement)r.q3.multiply(this.q1)).subtract(r.q1.multiply(this.q3)))).subtract(r.q2.multiply(this.q0)), (RealFieldElement)((RealFieldElement)((RealFieldElement)r.q0.multiply(this.q3)).add(((RealFieldElement)r.q1.multiply(this.q2)).subtract(r.q2.multiply(this.q1)))).subtract(r.q3.multiply(this.q0)), false);
   }

   public FieldRotation<T> applyInverseTo(Rotation r) {
      return new FieldRotation((RealFieldElement)((RealFieldElement)((RealFieldElement)this.q0.multiply(r.getQ0())).add(((RealFieldElement)((RealFieldElement)this.q1.multiply(r.getQ1())).add(this.q2.multiply(r.getQ2()))).add(this.q3.multiply(r.getQ3())))).negate(), (RealFieldElement)((RealFieldElement)((RealFieldElement)this.q1.multiply(r.getQ0())).add(((RealFieldElement)this.q3.multiply(r.getQ2())).subtract(this.q2.multiply(r.getQ3())))).subtract(this.q0.multiply(r.getQ1())), (RealFieldElement)((RealFieldElement)((RealFieldElement)this.q2.multiply(r.getQ0())).add(((RealFieldElement)this.q1.multiply(r.getQ3())).subtract(this.q3.multiply(r.getQ1())))).subtract(this.q0.multiply(r.getQ2())), (RealFieldElement)((RealFieldElement)((RealFieldElement)this.q3.multiply(r.getQ0())).add(((RealFieldElement)this.q2.multiply(r.getQ1())).subtract(this.q1.multiply(r.getQ2())))).subtract(this.q0.multiply(r.getQ3())), false);
   }

   public static <T extends RealFieldElement<T>> FieldRotation<T> applyInverseTo(Rotation rOuter, FieldRotation<T> rInner) {
      return new FieldRotation((RealFieldElement)((RealFieldElement)((RealFieldElement)rInner.q0.multiply(rOuter.getQ0())).add(((RealFieldElement)((RealFieldElement)rInner.q1.multiply(rOuter.getQ1())).add(rInner.q2.multiply(rOuter.getQ2()))).add(rInner.q3.multiply(rOuter.getQ3())))).negate(), (RealFieldElement)((RealFieldElement)((RealFieldElement)rInner.q0.multiply(rOuter.getQ1())).add(((RealFieldElement)rInner.q2.multiply(rOuter.getQ3())).subtract(rInner.q3.multiply(rOuter.getQ2())))).subtract(rInner.q1.multiply(rOuter.getQ0())), (RealFieldElement)((RealFieldElement)((RealFieldElement)rInner.q0.multiply(rOuter.getQ2())).add(((RealFieldElement)rInner.q3.multiply(rOuter.getQ1())).subtract(rInner.q1.multiply(rOuter.getQ3())))).subtract(rInner.q2.multiply(rOuter.getQ0())), (RealFieldElement)((RealFieldElement)((RealFieldElement)rInner.q0.multiply(rOuter.getQ3())).add(((RealFieldElement)rInner.q1.multiply(rOuter.getQ2())).subtract(rInner.q2.multiply(rOuter.getQ1())))).subtract(rInner.q3.multiply(rOuter.getQ0())), false);
   }

   private T[][] orthogonalizeMatrix(T[][] m, double threshold) throws NotARotationMatrixException {
      T x00 = m[0][0];
      T x01 = m[0][1];
      T x02 = m[0][2];
      T x10 = m[1][0];
      T x11 = m[1][1];
      T x12 = m[1][2];
      T x20 = m[2][0];
      T x21 = m[2][1];
      T x22 = m[2][2];
      double fn = 0.0D;
      T[][] o = (RealFieldElement[][])MathArrays.buildArray(m[0][0].getField(), 3, 3);
      int i = 0;

      while(true) {
         ++i;
         if (i >= 11) {
            throw new NotARotationMatrixException(LocalizedFormats.UNABLE_TO_ORTHOGONOLIZE_MATRIX, new Object[]{i - 1});
         }

         T mx00 = (RealFieldElement)((RealFieldElement)((RealFieldElement)m[0][0].multiply(x00)).add(m[1][0].multiply(x10))).add(m[2][0].multiply(x20));
         T mx10 = (RealFieldElement)((RealFieldElement)((RealFieldElement)m[0][1].multiply(x00)).add(m[1][1].multiply(x10))).add(m[2][1].multiply(x20));
         T mx20 = (RealFieldElement)((RealFieldElement)((RealFieldElement)m[0][2].multiply(x00)).add(m[1][2].multiply(x10))).add(m[2][2].multiply(x20));
         T mx01 = (RealFieldElement)((RealFieldElement)((RealFieldElement)m[0][0].multiply(x01)).add(m[1][0].multiply(x11))).add(m[2][0].multiply(x21));
         T mx11 = (RealFieldElement)((RealFieldElement)((RealFieldElement)m[0][1].multiply(x01)).add(m[1][1].multiply(x11))).add(m[2][1].multiply(x21));
         T mx21 = (RealFieldElement)((RealFieldElement)((RealFieldElement)m[0][2].multiply(x01)).add(m[1][2].multiply(x11))).add(m[2][2].multiply(x21));
         T mx02 = (RealFieldElement)((RealFieldElement)((RealFieldElement)m[0][0].multiply(x02)).add(m[1][0].multiply(x12))).add(m[2][0].multiply(x22));
         T mx12 = (RealFieldElement)((RealFieldElement)((RealFieldElement)m[0][1].multiply(x02)).add(m[1][1].multiply(x12))).add(m[2][1].multiply(x22));
         T mx22 = (RealFieldElement)((RealFieldElement)((RealFieldElement)m[0][2].multiply(x02)).add(m[1][2].multiply(x12))).add(m[2][2].multiply(x22));
         o[0][0] = (RealFieldElement)x00.subtract(((RealFieldElement)((RealFieldElement)((RealFieldElement)((RealFieldElement)x00.multiply(mx00)).add(x01.multiply(mx10))).add(x02.multiply(mx20))).subtract(m[0][0])).multiply(0.5D));
         o[0][1] = (RealFieldElement)x01.subtract(((RealFieldElement)((RealFieldElement)((RealFieldElement)((RealFieldElement)x00.multiply(mx01)).add(x01.multiply(mx11))).add(x02.multiply(mx21))).subtract(m[0][1])).multiply(0.5D));
         o[0][2] = (RealFieldElement)x02.subtract(((RealFieldElement)((RealFieldElement)((RealFieldElement)((RealFieldElement)x00.multiply(mx02)).add(x01.multiply(mx12))).add(x02.multiply(mx22))).subtract(m[0][2])).multiply(0.5D));
         o[1][0] = (RealFieldElement)x10.subtract(((RealFieldElement)((RealFieldElement)((RealFieldElement)((RealFieldElement)x10.multiply(mx00)).add(x11.multiply(mx10))).add(x12.multiply(mx20))).subtract(m[1][0])).multiply(0.5D));
         o[1][1] = (RealFieldElement)x11.subtract(((RealFieldElement)((RealFieldElement)((RealFieldElement)((RealFieldElement)x10.multiply(mx01)).add(x11.multiply(mx11))).add(x12.multiply(mx21))).subtract(m[1][1])).multiply(0.5D));
         o[1][2] = (RealFieldElement)x12.subtract(((RealFieldElement)((RealFieldElement)((RealFieldElement)((RealFieldElement)x10.multiply(mx02)).add(x11.multiply(mx12))).add(x12.multiply(mx22))).subtract(m[1][2])).multiply(0.5D));
         o[2][0] = (RealFieldElement)x20.subtract(((RealFieldElement)((RealFieldElement)((RealFieldElement)((RealFieldElement)x20.multiply(mx00)).add(x21.multiply(mx10))).add(x22.multiply(mx20))).subtract(m[2][0])).multiply(0.5D));
         o[2][1] = (RealFieldElement)x21.subtract(((RealFieldElement)((RealFieldElement)((RealFieldElement)((RealFieldElement)x20.multiply(mx01)).add(x21.multiply(mx11))).add(x22.multiply(mx21))).subtract(m[2][1])).multiply(0.5D));
         o[2][2] = (RealFieldElement)x22.subtract(((RealFieldElement)((RealFieldElement)((RealFieldElement)((RealFieldElement)x20.multiply(mx02)).add(x21.multiply(mx12))).add(x22.multiply(mx22))).subtract(m[2][2])).multiply(0.5D));
         double corr00 = o[0][0].getReal() - m[0][0].getReal();
         double corr01 = o[0][1].getReal() - m[0][1].getReal();
         double corr02 = o[0][2].getReal() - m[0][2].getReal();
         double corr10 = o[1][0].getReal() - m[1][0].getReal();
         double corr11 = o[1][1].getReal() - m[1][1].getReal();
         double corr12 = o[1][2].getReal() - m[1][2].getReal();
         double corr20 = o[2][0].getReal() - m[2][0].getReal();
         double corr21 = o[2][1].getReal() - m[2][1].getReal();
         double corr22 = o[2][2].getReal() - m[2][2].getReal();
         double fn1 = corr00 * corr00 + corr01 * corr01 + corr02 * corr02 + corr10 * corr10 + corr11 * corr11 + corr12 * corr12 + corr20 * corr20 + corr21 * corr21 + corr22 * corr22;
         if (FastMath.abs(fn1 - fn) <= threshold) {
            return o;
         }

         x00 = o[0][0];
         x01 = o[0][1];
         x02 = o[0][2];
         x10 = o[1][0];
         x11 = o[1][1];
         x12 = o[1][2];
         x20 = o[2][0];
         x21 = o[2][1];
         x22 = o[2][2];
         fn = fn1;
      }
   }

   public static <T extends RealFieldElement<T>> T distance(FieldRotation<T> r1, FieldRotation<T> r2) {
      return r1.applyInverseTo(r2).getAngle();
   }
}
