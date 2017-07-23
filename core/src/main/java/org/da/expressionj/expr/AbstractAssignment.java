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

/** An abstract assignment.
 * 
 * @since 0.9
 */
public abstract class AbstractAssignment extends AbstractUnaryExpression {
   protected  Variable resultVariable = null;
   protected boolean isLocal = false;
   
   public AbstractAssignment() {
   }
   
   public boolean isLocal() {
      return isLocal;
   }
   
   public void addLocalVariable(Variable var) {
      if (block != null) {
         isLocal = true;
         block.getInternalVariables().put(var.getName(), var);
      }
   }
   
   public void setResult(Variable resultVariable, boolean local) {
      this.resultVariable = resultVariable;
      if (local && (block != null)) {
         block.getInternalVariables().put(resultVariable.getName(), resultVariable);
      }
   }   
   
   public void setResult(Variable resultVariable) {
      setResult(resultVariable, true);
   }
   
   public Variable getResult() {
      return resultVariable;
   }

   @Override
   public short getResultType() {
      if (resultVariable.getType() == Expression.TYPE_FLOAT) {
         return Expression.TYPE_FLOAT;
      } else if (resultVariable.getType() == Expression.TYPE_INTEGER) {
         return Expression.TYPE_INTEGER;         
      } else {
         return expr.getResultType();
      }
   }

   @Override
   public short getResultStructure() {
      return expr.getResultStructure();
   }
   
   @Override
   public Variable getVariable(String varName) {
      return expr.getVariable(varName);
   }

   @Override
   public void setExpression(Expression expr) {
      this.expr = expr;
      if (resultVariable.getType() == Expression.TYPE_UNDEF) {
         resultVariable.setType(expr.getResultType());
      }
      if (resultVariable.getResultStructure() == Expression.TYPE_UNDEF) { 
         resultVariable.setStructure(expr.getResultStructure());
      }
   }

   @Override
   public Expression getExpression() {
      return expr;
   }
   
}
