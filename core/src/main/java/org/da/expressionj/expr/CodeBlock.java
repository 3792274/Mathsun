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
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import org.da.expressionj.model.Expression;
import org.da.expressionj.model.Variable;

/**
 * A code block containing several "&lt;do something&gt;;", each being one {@link org.da.expressionj.model.Expression}.
 * 
 * <H1>Examples</H1>
 * The following expression <code>c=a+b;d=c*2;return d;</code> will perform the following computations:
 * <ul>
 * <li>c is the result of a+b</li>
 * <li>d is c * 2</li>
 * <li>return d as the result of the evaluation</li>
 * </ul>
 * The equation will also have two side-effects in this case: set the value for c and d.
 * 
 * <p>The following expression <code>c=a+b;d=c*2;</code> will have exactly the same result.
 *
 * @version 0.10
 */
public class CodeBlock implements Expression {
   private List<Expression> expressions = new ArrayList();
   private Map<String, Variable> vars = new HashMap();
   private Map<String, Variable> internalVars = new HashMap();
   protected CodeBlock block = null;
   private ExprWHILE exprWHILE = null;
   
   public CodeBlock() {
   }
   
   @Override
   public Object clone() throws CloneNotSupportedException {
      CodeBlock _block = new CodeBlock();
      _block.internalVars = internalVars;
      _block.expressions = expressions;   
      _block.vars = vars; 
      _block.block = block;
      return _block;
   }     
   
   public void setExprWHILE(ExprWHILE exprWHILE) {
      this.exprWHILE = exprWHILE;
   }
   
   public ExprWHILE getExprWHILE() {
      return exprWHILE;
   }
   
   @Override
   public void setParentBlock(CodeBlock block) {
      this.block = block;
   }
   
   @Override
   public CodeBlock getParentBlock() {
      return block;
   }   
   
   public Expression lastExpression() {
      if (expressions.isEmpty()) {
         return null;
      } else {
         return expressions.get(expressions.size() - 1);
      }
   }
   
   public int countExpressions() {
      return expressions.size();
   }
   
   public void addExpression(Expression expr) {
      expr.setParentBlock(this);
      expressions.add(expr);
   }
   
   public List<Expression> getExpressions() {
      return expressions;
   }

   @Override
   public Object eval() {
      Object result = null;
      Iterator<Expression> it = expressions.iterator();
      while (it.hasNext()) {
         Expression expr = it.next();
         if (it.hasNext()) {
            expr.eval();
         } else {
            result = expr.eval();
         }
      }
      return result;
   }

   @Override
   public Map<String, Variable> getVariables() {
      return vars;
   }
   
   public Variable getInternalVariable(String varName) {
      if (internalVars.containsKey(varName)) {
         return internalVars.get(varName);
      } else if (block != null) {
         return block.getInternalVariable(varName);
      } else {
         return null;
      }
   }   
   
   public Map<String, Variable> getInternalVariables() {
      return internalVars;
   }
   
   @Override
   public String getExpressionName() {
      return "affect";
   }

   @Override
   public Variable getVariable(String varName) {
      return vars.get(varName);
   }  

   @Override
   public int evalAsInt() {
      int result = 0;
      Iterator<Expression> it = expressions.iterator();
      while (it.hasNext()) {
         Expression expr = it.next();
         if (it.hasNext()) {
            expr.eval();
         } else {
            result = expr.evalAsInt();
         }
      }
      return result;
   }

   @Override
   public float evalAsFloat() {
      float result = 0f;
      Iterator<Expression> it = expressions.iterator();
      while (it.hasNext()) {
         Expression expr = it.next();
         if (it.hasNext()) {
            expr.eval();
         } else {
            result = expr.evalAsFloat();
         }
      }
      return result;
   }

   @Override
   public boolean evalAsBoolean() {
      boolean result = false;
      Iterator<Expression> it = expressions.iterator();
      while (it.hasNext()) {
         Expression expr = it.next();
         if (it.hasNext()) {
            expr.eval();
         } else {
            result = expr.evalAsBoolean();
         }
      }
      return result;
   }

   @Override
   public short getResultType() {
      return expressions.get(expressions.size() - 1).getResultType();
   }

   @Override
   public short getResultStructure() {
      return expressions.get(expressions.size() - 1).getResultStructure();
   }
}
