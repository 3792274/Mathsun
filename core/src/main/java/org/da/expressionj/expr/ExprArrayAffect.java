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
import org.da.expressionj.model.Variable;

/**
 * An affectation expression for an array.
 *
 * @since 0.9.2
 */
public class ExprArrayAffect extends AbstractUnaryExpression {
   protected Variable var = null;
   protected Expression index = null;

   public ExprArrayAffect() {
   }
   
   @Override
   public Object clone() throws CloneNotSupportedException {
      ExprArrayAffect assign = new ExprArrayAffect();
      assign.var = var;
      assign.index = index;
      assign.block = block;
      assign.expr = expr;
      return assign;
   }     

   public Expression getIndex() {
      return index;
   }

   public void setIndex(Expression index) {
      this.index = index;
   }

   public Variable getArray() {
      return var;
   }

   public void setArray(Variable var) {
      this.var = var;
   }

   @Override
   public short getResultType() {
      if (var.getType() == Expression.TYPE_FLOAT) {
         return Expression.TYPE_FLOAT;
      } else if (var.getType() == Expression.TYPE_INTEGER) {
         return Expression.TYPE_INTEGER;
      } else {
         return expr.getResultType();
      }
   }

   @Override
   public short getResultStructure() {
      return Expression.STRUCT_SCALAR;
   }

   @Override
   public Variable getVariable(String varName) {
      return expr.getVariable(varName);
   }

   @Override
   public void setExpression(Expression expr) {
      this.expr = expr;
      if (var.getType() == Expression.TYPE_UNDEF) {
         var.setType(expr.getResultType());
      }
   }

   @Override
   public Expression getExpression() {
      return expr;
   }
   
   @Override
   public float evalAsFloat() {
      int indexEval = index.evalAsInt();
      if (var.getValue() instanceof float[]) {
         float eval = expr.evalAsFloat();
         ((float[])var.getValue())[indexEval] = eval;
         return eval; 
      } else {
         throw new ArithmeticException("Boolean Affectation Variable use non float type");
      }
   }       
   
   @Override
   public int evalAsInt() {
      int indexEval = index.evalAsInt();
      if (var.getValue() instanceof int[]) {
         int eval = expr.evalAsInt();
         ((int[])var.getValue())[indexEval] = eval;
         return eval; 
      } else {
         throw new ArithmeticException("Boolean Affectation Variable use non int type");
      }
   }    
   
   @Override
   public boolean evalAsBoolean() {
      int indexEval = index.evalAsInt();
      if (var.getValue() instanceof boolean[]) {
         boolean eval = expr.evalAsBoolean();
         ((boolean[])var.getValue())[indexEval] = eval;
         return eval; 
      } else {
         throw new ArithmeticException("Boolean Affectation Variable use non boolean type");
      }
   }   

   @Override
   public Object eval() {
      int indexEval = index.evalAsInt();
      if (var.getValue() instanceof int[]) {
         int eval = expr.evalAsInt();
         ((int[])var.getValue())[indexEval] = eval;
         return eval;
      } else if (var.getValue() instanceof float[]) {
         float eval = expr.evalAsFloat();
         ((float[])var.getValue())[indexEval] = eval;
         return eval;
      } else if (var.getValue() instanceof boolean[]) {
         boolean eval = expr.evalAsBoolean();
         ((boolean[])var.getValue())[indexEval] = eval;
         return eval;         
      } else if (var.getValue() instanceof String[]) {
         String eval = (String)expr.eval();
         ((String[])var.getValue())[indexEval] = eval;   
         return eval;
      } else {
         throw new ArithmeticException("Affectation Variable use wrong type");
      }
   }

   @Override
   public String getExpressionName() {
      return "arrayAffect";
   }
}
