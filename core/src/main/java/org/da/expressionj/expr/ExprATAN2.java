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

import org.da.expressionj.expr.parser.ParseException;
import org.da.expressionj.model.Expression;

/**
 * Represent a "atn2" expression.
 *
 * @version 0.9.2
 */
public class ExprATAN2 extends AbstractExprFunction {
   public ExprATAN2() {
   }
   
   @Override
   public Object clone() throws CloneNotSupportedException {
      ExprATAN2 atn2 = new ExprATAN2();
      atn2.block = block;
      atn2.exprs = exprs;
      return atn2;
   }   

   @Override
   public String getExpressionName() {
      return "atn2";
   }

   @Override
   public void addExpression(Expression expr) throws Exception {
      if (exprs.size() == 2) {
         throw new ParseException("More than two parameters for ATN2 function");
      }
      exprs.add(expr);
   }

   @Override
   public Object eval() throws ArithmeticException {
      float o1 = exprs.get(0).evalAsFloat();
      float o2 = exprs.get(1).evalAsFloat();

      return Math.atan2(o1, o2);
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
