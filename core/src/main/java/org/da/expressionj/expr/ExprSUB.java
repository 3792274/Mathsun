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

/** Represent a "Subtract" expression.
 *
 * @version 0.9.2
 */
public class ExprSUB extends AbstractAryNumericExpression {
   public ExprSUB() {
   }
   
   @Override
   public Object clone() throws CloneNotSupportedException {
      ExprSUB sub = new ExprSUB();
      sub.setExpression1(expr1);
      sub.setExpression2(expr2);
      sub.block = block;
      return sub;
   }         

   @Override
   public String getExpressionName() {
      return "-";
   }

   @Override
   public final int evalAsInt() {
      int value2 = 0;
      int value1 = 0;
      if (expr2 != null) {
         value1 = expr2.evalAsInt();
      }
      if (expr1 != null) {
         value2 = expr1.evalAsInt();
      }
      return (value1 - value2);
   }

   @Override
   public final float evalAsFloat() {
      float value2 = 0;
      float value1 = 0;
      if (expr2 != null) {
         value1 = expr2.evalAsFloat();
      }
      if (expr1 != null) {
         value2 = expr1.evalAsFloat();
      }
      return (value1 - value2);
   }

   @Override
   public Object eval() throws ArithmeticException {
      Object o1 = null;
      if (expr2 != null) {
         o1 = expr2.eval();
      }
      Object o2 = expr1.eval();
      if (o2 instanceof Number) {
         if (o1 == null) {
            if (o2 instanceof Float) {
               return -((Number) o2).floatValue();
            } else if (o2 instanceof Double) {
               return -((Number) o2).floatValue();
            } else {
               return -((Number) o2).intValue();
            }
         } else if (o1 instanceof Number) {
            Number n1 = (Number) o1;
            Number n2 = (Number) o2;
            if ((o1 instanceof Float) || (o2 instanceof Float)) {
               return n1.floatValue() - n2.floatValue();
            } else if ((o1 instanceof Double) || (o2 instanceof Double)) {
               return n1.floatValue() - n2.floatValue();
            } else {
               return n1.intValue() - n2.intValue();
            }
         } else {
            throw new ArithmeticException("Arithmetic SUBSTRACT Expression use non numeric elements");
         }
      } else {
         throw new ArithmeticException("Arithmetic SUBSTRACT Expression use non numeric elements");
      }
   }
}
