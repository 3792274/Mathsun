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

/** Represent a "floor" expression.
 *
 * @version 0.9.2
 */
public class ExprFLOOR extends AbstractUnaryExpression {
   public ExprFLOOR() {
   }
   
   @Override
   public Object clone() throws CloneNotSupportedException {
      ExprFLOOR floor = new ExprFLOOR();
      floor.setExpression(expr);
      floor.block = block;
      return floor;
   }  

   @Override
   public String getExpressionName() {
      return "floor";
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
   public final int evalAsInt() {
      return (int) Math.floor(expr.evalAsInt());
   }

   @Override
   public Object eval() throws ArithmeticException {
      Object o = expr.eval();
      if (o instanceof Float) {
         return ((int) Math.floor(((Float) o).floatValue()));
      } else if (o instanceof Double) {
         return ((int) Math.floor(((Double) o).floatValue()));
      } else if (o instanceof Integer) {
         return ((int) Math.floor(((Integer) o).intValue()));
      } else {
         throw new ArithmeticException("FLOOR Expression use non numeric elements");
      }
   }

   @Override
   public short getResultType() {
      return TYPE_INTEGER;
   }

   @Override
   public short getResultStructure() {
      return STRUCT_SCALAR;
   }
}
