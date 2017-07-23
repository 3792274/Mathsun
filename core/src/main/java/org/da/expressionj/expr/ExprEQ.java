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

/** Represent a "Equal to" expression.
 * 
 * @version 0.9.2
 */
public class ExprEQ extends AbstractAryExpression {
   public ExprEQ() {
   }
   
   @Override
   public Object clone() throws CloneNotSupportedException {
      ExprEQ eq = new ExprEQ();
      eq.setExpression1(expr1);
      eq.setExpression2(expr2);
      eq.block = block;
      return eq;
   }      

   @Override
   public String getExpressionName() {
      return "==";
   }

   @Override
   public final boolean evalAsBoolean() {
      if ((expr1.getResultType() == TYPE_INTEGER) && (expr2.getResultType() == TYPE_INTEGER)) {
         int value1 = expr1.evalAsInt();
         int value2 = expr2.evalAsInt();
         return (value1 == value2);
      } else {
         float value1 = expr1.evalAsFloat();
         float value2 = expr2.evalAsFloat();
         return (value1 == value2);
      }
   }

   @Override
   public Object eval() throws ArithmeticException {
      Object o1 = expr1.eval();
      Object o2 = expr2.eval();
      if ((o1 instanceof Boolean) && (o2 instanceof Boolean)) {
         return (((Boolean) o1).booleanValue()) == (((Boolean) o2).booleanValue());
      } else if ((o1 instanceof Number) && (o2 instanceof Number)) {
         Number n1 = (Number) o1;
         Number n2 = (Number) o2;
         if ((o1 instanceof Float) || (o2 instanceof Float)) {
            return n1.floatValue() == n2.floatValue();
         } else if ((o1 instanceof Double) || (o2 instanceof Double)) {
            return n1.floatValue() == n2.floatValue();
         } else {
            return n1.intValue() == n2.intValue();
         }
      } else if ((o1 instanceof String) && (o2 instanceof String)) {
         return (((String) o1).equals((String) o2));
      } else {
         throw new ArithmeticException("Equality Expression use non numeric elements");
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
