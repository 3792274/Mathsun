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
 * Represent a "ADD" expression.
 *
 * @version 0.9.2
 */
public class ExprADD extends AbstractAryExpression {
   protected short type = TYPE_UNDEF;

   public ExprADD() {
   }
   
   @Override
   public Object clone() throws CloneNotSupportedException {
      ExprADD add = new ExprADD();
      add.setExpression1(expr1);
      add.setExpression2(expr2);
      add.block = block;
      return add;
   }   

   @Override
   public String getExpressionName() {
      return "+";
   }

   @Override
   public final int evalAsInt() {
      int value2 = 0;
      int value1 = 0;
      if (expr2 != null) {
         value2 = expr2.evalAsInt();
      }
      if (expr1 != null) {
         value1 = expr1.evalAsInt();
      }
      return (value1 + value2);
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
      return (value1 + value2);
   }

   @Override
   public Object eval() throws ArithmeticException {
      Object o1 = null;
      if (expr2 != null) {
         o1 = expr2.eval();
      }
      Object o2 = expr1.eval();
      if (o2 instanceof Number) {
         if (o1 == null) {
            return o2;
         } else if (o1 instanceof Number) {
            Number n1 = (Number) o1;
            Number n2 = (Number) o2;
            if ((n1 instanceof Float) || (n2 instanceof Float)) {
               return n1.floatValue() + n2.floatValue();
            } else if ((n1 instanceof Double) || (n2 instanceof Double)) {
               return n1.floatValue() + n2.floatValue();
            } else {
               return n1.intValue() + n2.intValue();
            }
         } else if (o1 instanceof String) {
            String s1 = (String) o1;
            if (o2 instanceof Number) {
               Number n2 = (Number) o2;
               if (n2 instanceof Float) {
                  return s1 + n2.floatValue();
               } else if (n2 instanceof Double) {
                  return s1 + n2.floatValue();
               } else {
                  return s1 + n2.intValue();
               }
            } else {
               throw new ArithmeticException("Arithmetic ADD Expression use non Numeric or String elements");
            }
         } else {
            throw new ArithmeticException("Arithmetic ADD Expression use non Numeric or String elements");
         }
      } else if (o2 instanceof String) {
         String s2 = (String) o2;
         if (o1 instanceof Number) {
            Number n1 = (Number) o1;
            if (n1 instanceof Float) {
               return n1.floatValue() + s2;
            } else if (n1 instanceof Double) {
               return n1.floatValue() + s2;
            } else {
               return n1.intValue() + s2;
            }
         } else if (o1 instanceof String) {
            String s1 = (String) o1;
            return s1 + s2;
         } else {
            throw new ArithmeticException("Arithmetic ADD Expression use non Numeric or String elements");
         }
      } else {
         throw new ArithmeticException("Arithmetic ADD Expression use non Numeric or String elements");
      }
   }

   @Override
   public short getResultType() {
      if (expr2 != null) {
         type = expr2.getResultType();
      }
      if ((type != TYPE_STRING) && (expr1 != null)) {
         short type2 = expr1.getResultType();
         if ((type2 == TYPE_FLOAT) || (type == TYPE_FLOAT)) {
            type = TYPE_FLOAT;
         } else if (type2 == TYPE_STRING) {
            type = TYPE_STRING;
         }
      }
      if (type == TYPE_UNDEF) {
         type = TYPE_DYNAMIC;
      }
      return type;
   }

   @Override
   public short getResultStructure() {
      return STRUCT_SCALAR;
   }
}
