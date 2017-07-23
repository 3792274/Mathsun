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

import java.util.Locale;
import org.da.expressionj.expr.parser.ParseException;
import org.da.expressionj.model.Expression;

/**
 * Represent a "format" expression.
 *
 * @version 0.9.2
 */
public class ExprFORMAT extends AbstractExprFunction {
   public ExprFORMAT() {
   }
   
   @Override
   public Object clone() throws CloneNotSupportedException {
      ExprFORMAT format = new ExprFORMAT();
      format.block = block;
      format.exprs = exprs;
      return format;
   }      

   @Override
   public String getExpressionName() {
      return "format";
   }

   @Override
   public void addExpression(Expression expr) throws Exception {
      if (exprs.size() == 2) {
         throw new ParseException("More than two parameters for FORMAT function");
      }
      exprs.add(expr);
   }

   @Override
   public Object eval() throws ArithmeticException {
      Object o1 = exprs.get(0).eval();
      Object o2 = exprs.get(1).eval();

      if (o2 instanceof String) {
         String format = (String) o2;
         if (o1 instanceof Float) {
            float value = ((Float) o1).floatValue();
            return String.format(Locale.US, format, value);
         } else if (o1 instanceof Double) {
            float value = ((Double) o1).floatValue();
            return String.format(Locale.US, format, value);
         } else if (o1 instanceof Integer) {
            int value = ((Integer) o1).intValue();
            return String.format(Locale.US, format, value);
         } else {
            throw new ArithmeticException("FORMAT Expression Value use non Numeric element");
         }
      } else {
         throw new ArithmeticException("FORMAT Expression Pattern use non String element");
      }
   }

   @Override
   public short getResultType() {
      return TYPE_STRING;
   }

   @Override
   public short getResultStructure() {
      return STRUCT_SCALAR;
   }
}
