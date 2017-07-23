/*
 Copyright (c) 2011 Herve Girod. All rights reserved.
 DO NOT ALTER OR REMOVE COPYRIGHT NOTICES OR THIS FILE HEADER.

 This library is free software; you can redistribute it and/or
 modify it under the terms of the GNU Lesser General Public
 License as published by the Free Software Foundation; either
 version 2.1 of the License, or (at your option) any later version.

 This library is distributed in the hope that it will be useful,
 but WITHOUT ANY WARRANTY; without even the implied warranty of
 MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the GNU
 Lesser General Public License for more details.

 You should have received a copy of the GNU Lesser General Public
 License along with this library; if not, write to the Free Software
 Foundation, Inc., 51 Franklin Street, Fifth Floor, Boston, MA  02110-1301  USA

 If you have any questions about this project, you can visit
 the project website at the project page on http://expressionj.sourceforge.net
 */
package org.da.expressionj.expr;

import org.da.expressionj.model.Expression;
import org.da.expressionj.model.Value;

/**
 * Represent an array index.
 *
 * @version 0.9.2
 */
public class ExprArrayIndex extends AbstractUnaryExpression {
   private Value value = null;

   public ExprArrayIndex() {
   }
   
   @Override
   public Object clone() throws CloneNotSupportedException {
      ExprArrayIndex assign = new ExprArrayIndex();
      assign.value = value;
      assign.block = block;
      assign.expr = expr;
      return assign;
   }    

   public void setValue(Value value) {
      this.value = value;
   }

   @Override
   public String getExpressionName() {
      return expr.getExpressionName();
   }

   public Value getValue() {
      return value;
   }

   @Override
   public void setExpression(Expression expr) {
      this.expr = expr;
   }

   @Override
   public Expression getExpression() {
      return expr;
   }

   @Override
   public Object eval() throws ArithmeticException {
      if (value.getValue() == null) {
         throw new ArithmeticException("Array value " + Helper.getName(value) + " Undefined");
      } else if (value.getResultStructure() != Expression.STRUCT_ARRAY) {
         throw new ArithmeticException("Array value " + Helper.getName(value) + " is not an Array");
      } else {
         Object o = expr.eval();
         if (o instanceof Integer) {
            int index = ((Integer) o).intValue();
            Object valueContent = value.getValue();
            if (valueContent instanceof int[]) {
               int[] values = (int[]) valueContent;
               if (values.length < index) {
                  throw new ArithmeticException("Array value has not enough elements");
               } else {
                  return values[index];
               }
            } else if (valueContent instanceof float[]) {
               float[] values = (float[]) valueContent;
               if (values.length < index) {
                  throw new ArithmeticException("Array value has not enough elements");
               } else {
                  return values[index];
               }
            } else if (valueContent instanceof boolean[]) {
               boolean[] values = (boolean[]) valueContent;
               if (values.length < index) {
                  throw new ArithmeticException("Array value has not enough elements");
               } else {
                  return values[index];
               }
            } else {
               Object[] values = (Object[]) value.getValue();
               if (values.length < index) {
                  throw new ArithmeticException("Array value has not enough elements");
               } else {
                  return values[index];
               }
            }
         } else {
            throw new ArithmeticException("Array index Expression use non integer index");
         }
      }
   }

   @Override
   public short getResultType() {
      return TYPE_DYNAMIC;
   }

   @Override
   public short getResultStructure() {
      return STRUCT_SCALAR;
   }
}
