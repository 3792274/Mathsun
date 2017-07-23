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

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import org.da.expressionj.model.Expression;
import org.da.expressionj.model.Variable;

/** An if - then - else if - else expression.
 *
 * @version 0.9.2
 */
public class ExprCHOICE implements Expression {
   protected CodeBlock block = null; 
   private List<Expression> expressions = new ArrayList();
   private List<Expression> conditions = new ArrayList();
   
   @Override
   public Object clone() throws CloneNotSupportedException {
      ExprCHOICE choice = new ExprCHOICE();
      choice.block = block;
      choice.conditions = conditions;
      choice.expressions = expressions;
      return choice;
   }      
   
   @Override
   public void setParentBlock(CodeBlock block) {
      this.block = block;
      // we also need to do that, because in many cases the parent block is defined after 
      // the children expressions has been defined
      Iterator<Expression> it = conditions.iterator();
      while (it.hasNext()) {
         it.next().setParentBlock(block);
      }
      it = expressions.iterator();
      while (it.hasNext()) {
         it.next().setParentBlock(block);
      }      
   }
   
   @Override
   public CodeBlock getParentBlock() {
      return block;
   }  
   
   public void addCondition(Expression cond) {
      cond.setParentBlock(block);
      conditions.add(cond);
   }
   
   public List<Expression> getConditions() {
      return conditions;
   }    
   
   public void addExpression(Expression expr) {
      expr.setParentBlock(block);
      expressions.add(expr);
   }
   
   public List<Expression> getExpressions() {
      return expressions;
   }   
   
   @Override
   public Map<String, Variable> getVariables() {
      return null;
   }
   
   @Override
   public String getExpressionName() {
      return "choice";
   }
   
   @Override
   public short getResultType() {
      return expressions.get(expressions.size() - 1).getResultType();
   }

   @Override
   public short getResultStructure() {
      return expressions.get(expressions.size() - 1).getResultStructure();
   }

   @Override
   public int evalAsInt() {
      int condIndex = 0;
      int result = 0;
      boolean resultFound = false;
      while (condIndex < conditions.size()) {
         Expression expr = conditions.get(condIndex);
         boolean conditionResult = expr.evalAsBoolean();
         if (conditionResult) {
            result = expressions.get(condIndex).evalAsInt();
            resultFound = true;
            break;
         }
         condIndex++;
      }
      if (! resultFound && expressions.size() > condIndex) {
         result = expressions.get(condIndex).evalAsInt();
      }
      
      return result;
   }

   @Override
   public float evalAsFloat() {
      int condIndex = 0;
      float result = 0;
      boolean resultFound = false;
      while (condIndex < conditions.size()) {
         Expression expr = conditions.get(condIndex);
         boolean conditionResult = expr.evalAsBoolean();
         if (conditionResult) {
            result = expressions.get(condIndex).evalAsFloat();
            resultFound = true;
            break;
         }
         condIndex++;
      }
      if (! resultFound) {
         result = expressions.get(condIndex).evalAsFloat();
      }
      
      return result;
   }

   @Override
   public boolean evalAsBoolean() {
      int condIndex = 0;
      boolean result = false;
      boolean resultFound = false;
      while (condIndex < conditions.size()) {
         Expression expr = conditions.get(condIndex);
         boolean conditionResult = expr.evalAsBoolean();
         if (conditionResult) {
            result = expressions.get(condIndex).evalAsBoolean();
            resultFound = true;
            break;
         }
         condIndex++;
      }
      if (! resultFound) {
         result = expressions.get(condIndex).evalAsBoolean();
      }
      
      return result;
   }

   @Override
   public Object eval() {
      int condIndex = 0;
      Object result = null;
      boolean resultFound = false;
      while (condIndex < conditions.size()) {
         Expression expr = conditions.get(condIndex);
         boolean conditionResult = expr.evalAsBoolean();
         if (conditionResult) {
            result = expressions.get(condIndex).eval();
            resultFound = true;
            break;
         }
         condIndex++;
      }
      if ((! resultFound) && (expressions.size() > condIndex)) {
         result = expressions.get(condIndex).eval();
      }
      
      return result;
   }

   @Override
   public Variable getVariable(String varName) {
      return null;
   }
}
