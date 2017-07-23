/*
 Copyright (c) 2011, 2012 Herve Girod. All rights reserved.
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
 * Represent a "AND" expression.
 *
 * @version 0.10
 */
public class ExprAND extends AbstractAryExpression {
   public ExprAND() {
   }

   @Override
   public Object clone() throws CloneNotSupportedException {
      ExprAND and = new ExprAND();
      and.setExpression1(expr1);
      and.setExpression2(expr2);
      and.block = block;
      return and;
   }

   @Override
   public String getExpressionName() {
      return "&&";
   }

   @Override
   public final boolean evalAsBoolean() {
      boolean value2 = false;
      boolean value1 = false;
      if (expr2 != null) {
         value2 = expr2.evalAsBoolean();
      }
      // by doing this we ensure that we only test the first part of the expression if it is already false
      if (!value2) {
         return false;
      }
      if (expr1 != null) {
         value1 = expr1.evalAsBoolean();
      }
      return value1;
   }

   @Override
   public Object eval() throws ArithmeticException {
      // by doing this we ensure that we only test the first part of the expression if it is already false
      Object o2 = expr2.eval();
      if (o2 instanceof Boolean) {
         boolean b2 = ((Boolean) o2).booleanValue();
         if (!b2) {
            return false;
         }
         Object o1 = expr1.eval();
         if (o1 instanceof Boolean) {
            boolean b1 = ((Boolean) o1).booleanValue();
            return b1;
         } else {
            throw new ArithmeticException("Boolean Expression use non boolean elements");
         }
      } else {
         throw new ArithmeticException("Boolean Expression use non boolean elements");
      }
   }

   @Override
   public short getResultType() {
      return TYPE_BOOL;
   }

   @Override
   public short getResultStructure() {
      return STRUCT_SCALAR;
   }
}
