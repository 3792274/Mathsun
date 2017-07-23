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

import org.da.expressionj.model.Expression;

/** Represent a "XOr" expression.
 *
 * @version 0.9.2
 */
public class ExprXOR extends AbstractAryExpression {
   public ExprXOR() {
   }
   
   @Override
   public Object clone() throws CloneNotSupportedException {
      ExprXOR or = new ExprXOR();
      or.setExpression1(expr1);
      or.setExpression2(expr2);
      or.block = block;
      return or;
   }        

   @Override
   public String getExpressionName() {
      return "^";
   }

   @Override
   public final boolean evalAsBoolean() {
      boolean value2 = false;
      boolean value1 = false;
      if (expr2 != null) {
         value2 = expr2.evalAsBoolean();
      }
      if (expr1 != null) {
         value1 = expr1.evalAsBoolean();
      }
      return ((value1 && (! value2) || ((! value1) && value2)));
   }

   @Override
   public Object eval() throws ArithmeticException {
      Object o1 = expr1.eval();
      Object o2 = expr2.eval();
      if ((o1 instanceof Boolean) && (o2 instanceof Boolean)) {
         boolean value1 = ((Boolean) o1).booleanValue();
         boolean value2 = ((Boolean) o2).booleanValue();
         return (value1 && (! value2) || ((! value1) && value2));
      } else {
         throw new ArithmeticException("Boolean Expression use non boolean elements");
      }
   }

   @Override
   public short getResultType() {
      return Expression.TYPE_BOOL;
   }

   @Override
   public short getResultStructure() {
      return STRUCT_SCALAR;
   }
}
