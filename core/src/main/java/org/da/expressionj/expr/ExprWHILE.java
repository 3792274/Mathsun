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
import org.da.expressionj.model.Expression;
import org.da.expressionj.model.Variable;

/**
 * A while expression.
 *
 * @version 0.10
 */
public class ExprWHILE implements Expression {
   protected CodeBlock block = null;
   private Expression expression = null;
   private Expression condition = null;
   private boolean breakIsSet = false;

   @Override
   public Object clone() throws CloneNotSupportedException {
      ExprWHILE whileExpr = new ExprWHILE();
      whileExpr.block = block;
      whileExpr.condition = condition;
      whileExpr.expression = expression;
      return whileExpr;
   }

   @Override
   public void setParentBlock(CodeBlock block) {
      this.block = block;
      expression.setParentBlock(block);
   }

   @Override
   public CodeBlock getParentBlock() {
      return block;
   }

   public void setCondition(Expression cond) {
      cond.setParentBlock(block);
      this.condition = cond;
   }

   public Expression getCondition() {
      return condition;
   }

   public void setExpression(Expression expr) {
      if (expr instanceof CodeBlock) {
         ((CodeBlock)expr).setExprWHILE(this);
      }
      expr.setParentBlock(block);
      this.expression = expr;
   }

   public Expression getExpression() {
      return expression;
   }

   @Override
   public Map<String, Variable> getVariables() {
      return null;
   }

   @Override
   public String getExpressionName() {
      return "while";
   }

   @Override
   public short getResultType() {
      return expression.getResultType();
   }

   @Override
   public short getResultStructure() {
      return expression.getResultStructure();
   }

   @Override
   public int evalAsInt() {
      int result = 0;
      breakIsSet = false;
      while (condition.evalAsBoolean()) {
         result = expression.evalAsInt();
         if (breakIsSet) {
            break;
         }
      }

      return result;
   }

   @Override
   public float evalAsFloat() {
      float result = 0f;
      breakIsSet = false;
      while (condition.evalAsBoolean()) {
         result = expression.evalAsFloat();
         if (breakIsSet) {
            break;
         }         
      }

      return result;
   }

   @Override
   public boolean evalAsBoolean() {
      boolean result = false;
      breakIsSet = false;
      while (condition.evalAsBoolean()) {
         result = expression.evalAsBoolean();
         if (breakIsSet) {
            break;
         }         
      }

      return result;
   }

   @Override
   public Object eval() {
      Object result = null;
      breakIsSet = false;
      while (condition.evalAsBoolean()) {
         result = expression.eval();
         if (breakIsSet) {
            break;
         }         
      }

      return result;
   }

   @Override
   public Variable getVariable(String varName) {
      return null;
   }

   public void setBreak() {
      breakIsSet = true;
   }
}
