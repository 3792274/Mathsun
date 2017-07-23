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

/** Represent a "Not" expression.
 *
 * @version 0.9.2
 */
public class ExprNOT extends AbstractUnaryExpression {
   public ExprNOT() {
   }
   
   @Override
   public Object clone() throws CloneNotSupportedException {
      ExprNOT not = new ExprNOT();
      not.setExpression(expr);
      not.block = block;
      return not;
   }   

   @Override
   public String getExpressionName() {
      return "!";
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
   public final boolean evalAsBoolean() {
      return (!expr.evalAsBoolean());
   }

   @Override
   public Object eval() throws ArithmeticException {
      Object o = expr.eval();
      if (o instanceof Boolean) {
         return (!((Boolean) o).booleanValue());
      } else {
         throw new ArithmeticException("NOT Expression use non boolean elements");
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
