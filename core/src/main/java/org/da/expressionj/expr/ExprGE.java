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

/** Represent a "Greater or equal than" expression.
 *
 * @version 0.9.2
 */
public class ExprGE extends AbstractAryExpression {
   public ExprGE() {
   }
   
   @Override
   public Object clone() throws CloneNotSupportedException {
      ExprGE ge = new ExprGE();
      ge.setExpression1(expr1);
      ge.setExpression2(expr2);
      ge.block = block;
      return ge;
   }         

   @Override
   public String getExpressionName() {
      return ">=";
   }
   
   @Override
   public final boolean evalAsBoolean() {
      if ((expr1.getResultType() == TYPE_INTEGER) && (expr1.getResultType() == TYPE_INTEGER)) {
         int value2 = expr1.evalAsInt();
         int value1 = expr2.evalAsInt();
         return (value1 >= value2);
      } else {
         float value2 = expr1.evalAsFloat();
         float value1 = expr2.evalAsFloat();
         return (value1 >= value2);
      }
   }   

   @Override
   public Object eval() throws ArithmeticException {
      Object o1 = expr2.eval();
      Object o2 = expr1.eval();
      if ((o1 instanceof Number) && (o2 instanceof Number)) {
         Number n1 = (Number) o1;
         Number n2 = (Number) o2;
         return n1.floatValue() >= n2.floatValue();
      } else {
         throw new ArithmeticException("Comparison Expression use non numeric elements");
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
