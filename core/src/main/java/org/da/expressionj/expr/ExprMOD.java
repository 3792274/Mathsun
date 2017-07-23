/*
 Copyright (c) 2012 Herve Girod. All rights reserved.
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

/**
 * Represent a "atn2" expression.
 *
 * @version 0.9.2
 */
public class ExprMOD extends AbstractAryNumericExpression {
   public ExprMOD() {
   }

   @Override
   public Object clone() throws CloneNotSupportedException {
      ExprMOD mod = new ExprMOD();
      mod.setExpression1(expr1);
      mod.setExpression2(expr2);
      mod.block = block;
      return mod;
   }

   @Override
   public String getExpressionName() {
      return "%";
   }

   @Override
   public final int evalAsInt() {
      int value2 = 0;
      int value1 = 0;
      if (expr2 != null) {
         value2 = expr2.evalAsInt();
      }
      if (expr1 != null) {
         value1 = expr1.evalAsInt();
      }
      return value2 % value1;
   }

   @Override
   public final float evalAsFloat() {
      float value2 = 0;
      float value1 = 0;
      if (expr2 != null) {
         value2 = expr2.evalAsFloat();
      }
      if (expr1 != null) {
         value1 = expr1.evalAsFloat();
      }
      return value2 % value1;
   }

   @Override
   public Object eval() throws ArithmeticException {
      Object o1 = expr1.eval();
      Object o2 = expr2.eval();
      if ((o1 instanceof Number) && (o2 instanceof Number)) {
         Number n1 = (Number) o1;
         Number n2 = (Number) o2;
         if ((o1 instanceof Float) || (o2 instanceof Float)) {
            return n2.floatValue() % n1.floatValue();
         } else if ((o1 instanceof Double) || (o2 instanceof Double)) {
            return n2.floatValue() % n1.floatValue();
         } else {
            return n2.intValue() % n1.intValue();
         }
      } else {
         throw new ArithmeticException("Arithmetic MODULO Expression use non numeric elements");
      }
   }
}
