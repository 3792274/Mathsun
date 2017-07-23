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

/**
 * Represent a "Divide" expression.
 *
 * @version 0.9.2
 */
public class ExprDIV extends AbstractAryExpression {
   public ExprDIV() {
   }
   
   @Override
   public Object clone() throws CloneNotSupportedException {
      ExprDIV div = new ExprDIV();
      div.setExpression1(expr1);
      div.setExpression2(expr2);
      div.block = block;
      return div;
   }      

   @Override
   public String getExpressionName() {
      return "/";
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
      return value2 / value1;
   }

   @Override
   public Object eval() throws ArithmeticException {
      Object o1 = expr2.eval();
      Object o2 = expr1.eval();
      if ((o1 instanceof Number) && (o2 instanceof Number)) {
         Number n1 = (Number) o1;
         Number n2 = (Number) o2;
         return n1.floatValue() / n2.floatValue();
      } else {
         throw new ArithmeticException("Arithmetic DIVIDE Expression use non numeric elements");
      }
   }

   @Override
   public short getResultType() {
      return TYPE_FLOAT;
   }

   @Override
   public short getResultStructure() {
      return STRUCT_SCALAR;
   }
}
