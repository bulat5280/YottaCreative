package org.apache.commons.math3.ode;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import org.apache.commons.math3.exception.DimensionMismatchException;
import org.apache.commons.math3.exception.MaxCountExceededException;

public class ExpandableStatefulODE {
   private final FirstOrderDifferentialEquations primary;
   private final EquationsMapper primaryMapper;
   private double time;
   private final double[] primaryState;
   private final double[] primaryStateDot;
   private List<ExpandableStatefulODE.SecondaryComponent> components;

   public ExpandableStatefulODE(FirstOrderDifferentialEquations primary) {
      int n = primary.getDimension();
      this.primary = primary;
      this.primaryMapper = new EquationsMapper(0, n);
      this.time = Double.NaN;
      this.primaryState = new double[n];
      this.primaryStateDot = new double[n];
      this.components = new ArrayList();
   }

   public FirstOrderDifferentialEquations getPrimary() {
      return this.primary;
   }

   public int getTotalDimension() {
      if (this.components.isEmpty()) {
         return this.primaryMapper.getDimension();
      } else {
         EquationsMapper lastMapper = ((ExpandableStatefulODE.SecondaryComponent)this.components.get(this.components.size() - 1)).mapper;
         return lastMapper.getFirstIndex() + lastMapper.getDimension();
      }
   }

   public void computeDerivatives(double t, double[] y, double[] yDot) throws MaxCountExceededException, DimensionMismatchException {
      this.primaryMapper.extractEquationData(y, this.primaryState);
      this.primary.computeDerivatives(t, this.primaryState, this.primaryStateDot);
      Iterator i$ = this.components.iterator();

      while(i$.hasNext()) {
         ExpandableStatefulODE.SecondaryComponent component = (ExpandableStatefulODE.SecondaryComponent)i$.next();
         component.mapper.extractEquationData(y, component.state);
         component.equation.computeDerivatives(t, this.primaryState, this.primaryStateDot, component.state, component.stateDot);
         component.mapper.insertEquationData(component.stateDot, yDot);
      }

      this.primaryMapper.insertEquationData(this.primaryStateDot, yDot);
   }

   public int addSecondaryEquations(SecondaryEquations secondary) {
      int firstIndex;
      if (this.components.isEmpty()) {
         this.components = new ArrayList();
         firstIndex = this.primary.getDimension();
      } else {
         ExpandableStatefulODE.SecondaryComponent last = (ExpandableStatefulODE.SecondaryComponent)this.components.get(this.components.size() - 1);
         firstIndex = last.mapper.getFirstIndex() + last.mapper.getDimension();
      }

      this.components.add(new ExpandableStatefulODE.SecondaryComponent(secondary, firstIndex));
      return this.components.size() - 1;
   }

   public EquationsMapper getPrimaryMapper() {
      return this.primaryMapper;
   }

   public EquationsMapper[] getSecondaryMappers() {
      EquationsMapper[] mappers = new EquationsMapper[this.components.size()];

      for(int i = 0; i < mappers.length; ++i) {
         mappers[i] = ((ExpandableStatefulODE.SecondaryComponent)this.components.get(i)).mapper;
      }

      return mappers;
   }

   public void setTime(double time) {
      this.time = time;
   }

   public double getTime() {
      return this.time;
   }

   public void setPrimaryState(double[] primaryState) throws DimensionMismatchException {
      if (primaryState.length != this.primaryState.length) {
         throw new DimensionMismatchException(primaryState.length, this.primaryState.length);
      } else {
         System.arraycopy(primaryState, 0, this.primaryState, 0, primaryState.length);
      }
   }

   public double[] getPrimaryState() {
      return (double[])this.primaryState.clone();
   }

   public double[] getPrimaryStateDot() {
      return (double[])this.primaryStateDot.clone();
   }

   public void setSecondaryState(int index, double[] secondaryState) throws DimensionMismatchException {
      double[] localArray = ((ExpandableStatefulODE.SecondaryComponent)this.components.get(index)).state;
      if (secondaryState.length != localArray.length) {
         throw new DimensionMismatchException(secondaryState.length, localArray.length);
      } else {
         System.arraycopy(secondaryState, 0, localArray, 0, secondaryState.length);
      }
   }

   public double[] getSecondaryState(int index) {
      return (double[])((ExpandableStatefulODE.SecondaryComponent)this.components.get(index)).state.clone();
   }

   public double[] getSecondaryStateDot(int index) {
      return (double[])((ExpandableStatefulODE.SecondaryComponent)this.components.get(index)).stateDot.clone();
   }

   public void setCompleteState(double[] completeState) throws DimensionMismatchException {
      if (completeState.length != this.getTotalDimension()) {
         throw new DimensionMismatchException(completeState.length, this.getTotalDimension());
      } else {
         this.primaryMapper.extractEquationData(completeState, this.primaryState);
         Iterator i$ = this.components.iterator();

         while(i$.hasNext()) {
            ExpandableStatefulODE.SecondaryComponent component = (ExpandableStatefulODE.SecondaryComponent)i$.next();
            component.mapper.extractEquationData(completeState, component.state);
         }

      }
   }

   public double[] getCompleteState() throws DimensionMismatchException {
      double[] completeState = new double[this.getTotalDimension()];
      this.primaryMapper.insertEquationData(this.primaryState, completeState);
      Iterator i$ = this.components.iterator();

      while(i$.hasNext()) {
         ExpandableStatefulODE.SecondaryComponent component = (ExpandableStatefulODE.SecondaryComponent)i$.next();
         component.mapper.insertEquationData(component.state, completeState);
      }

      return completeState;
   }

   private static class SecondaryComponent {
      private final SecondaryEquations equation;
      private final EquationsMapper mapper;
      private final double[] state;
      private final double[] stateDot;

      public SecondaryComponent(SecondaryEquations equation, int firstIndex) {
         int n = equation.getDimension();
         this.equation = equation;
         this.mapper = new EquationsMapper(firstIndex, n);
         this.state = new double[n];
         this.stateDot = new double[n];
      }
   }
}
