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

import org.da.expressionj.expr.parser.ParseException;
import org.da.expressionj.model.Expression;

/**
 * Represent a "pow" expression.
 *
 * @version 0.9.2
 */
public class ExprPOW extends AbstractExprFunction {
   protected short type = TYPE_UNDEF;

   public ExprPOW() {
   }

   @Override
   public Object clone() throws CloneNotSupportedException {
      ExprPOW pow = new ExprPOW();
      pow.block = block;
      pow.exprs = exprs;
      pow.type = type;
      return pow;
   }

   @Override
   public String getExpressionName() {
      return "pow";
   }

   @Override
   public void addExpression(Expression expr) throws Exception {
      if (exprs.size() == 2) {
         throw new ParseException("More than two parameters for POW function");
      }
      exprs.add(expr);
   }

   @Override
   public int evalAsInt() {
      Expression expr1 = exprs.get(0);
      Expression expr2 = exprs.get(1);
      int pow = 0;
      int value = 0;
      if (expr2 != null) {
         pow = expr2.evalAsInt();
      }
      if (expr1 != null) {
         value = expr1.evalAsInt();
      }
      return (int) Math.pow(value, pow);
   }

   @Override
   public float evalAsFloat() {
      Expression expr1 = exprs.get(0);
      Expression expr2 = exprs.get(1);
      float pow = 0;
      float value = 0;
      if (expr2 != null) {
         pow = expr2.evalAsFloat();
      }
      if (expr1 != null) {
         value = expr1.evalAsFloat();
      }
      return (float) Math.pow(value, pow);
   }

   @Override
   public Object eval() throws ArithmeticException {
      Object o1 = exprs.get(0).eval();
      Object o2 = exprs.get(1).eval();

      if ((o1 instanceof Number) && (o2 instanceof Number)) {
         if ((o1 instanceof Integer) && (o2 instanceof Integer)) {
            int value = ((Integer) o1).intValue();
            int pow = ((Integer) o2).intValue();
            return (int) Math.pow(value, pow);
         } else {
            double value = ((Number) o1).doubleValue();
            double pow = ((Number) o2).doubleValue();
            return (float) Math.pow(value, pow);
         }
      } else {
         throw new ArithmeticException("FORMAT Expression Pattern use non String element");
      }
   }

   @Override
   public short getResultType() {
      Expression expr1 = exprs.get(0);
      Expression expr2 = exprs.get(1);
      if (expr2 != null) {
         type = expr2.getResultType();
      }
      if (expr1 != null) {
         short type2 = expr1.getResultType();
         if (type2 == TYPE_FLOAT) {
            type = TYPE_FLOAT;
         } else if (type2 == TYPE_NUMERIC) {
            type = TYPE_FLOAT;
         }
      }
      if (type == TYPE_UNDEF) {
         type = TYPE_NUMERIC;
      }
      return type;
   }

   @Override
   public short getResultStructure() {
      return STRUCT_SCALAR;
   }
}
