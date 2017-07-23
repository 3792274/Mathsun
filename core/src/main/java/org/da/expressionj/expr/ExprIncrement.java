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

import java.util.Map;
import org.da.expressionj.model.AbstractExpression;
import org.da.expressionj.model.Variable;

/**
 * Represent an increment (++) expression.
 *
 * @version 0.9.2
 */
public class ExprIncrement extends AbstractExpression {
   private Variable var = null;

   public ExprIncrement() {
   }
   
   @Override
   public Object clone() throws CloneNotSupportedException {
      ExprIncrement inc = new ExprIncrement();
      inc.var = var;
      inc.block = block;
      return inc;
   }         

   @Override
   public String getExpressionName() {
      return "++";
   }

   public void setVariable(Variable var) {
      this.var = var;
   }

   public Variable getVariable() {
      return var;
   }

   @Override
   public final int evalAsInt() {
      int value = var.evalAsInt();
      value++;
      var.setValueAsInt(value);
      return value;
   }

   @Override
   public final float evalAsFloat() {
      float value = var.evalAsFloat();
      value++;
      var.setValueAsFloat(value);
      return value;
   }

   @Override
   public Object eval() throws ArithmeticException {
      Object o = var.getValue();
      if (o instanceof Float) {
         float value = ((Float)o).floatValue();
         value++;
         var.setValue(value);
         return value;
      } else if (o instanceof Double) {
         float value = ((Double)o).floatValue();
         value++;
         var.setValue(value);         
         return value;
      } else if (o instanceof Integer) {
         int value = ((Integer)o).intValue();
         value++;
         var.setValue(value);         
         return value;
      } else {
         throw new ArithmeticException("ABS Expression use non numeric elements");
      }
   }

   @Override
   public short getResultStructure() {
      return STRUCT_SCALAR;
   }

   @Override
   public short getResultType() {
      return var.getResultType();
   }

   @Override
   public Map<String, Variable> getVariables() {
      return var.getVariables();
   }

   @Override
   public Variable getVariable(String varName) {
      if (varName.equals(var.getName())) {
         return var;
      } else {
         return null;
      }
   }
}
