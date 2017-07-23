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

/**  The -= operator.
 *
 * @version 0.9.2
 */
public class ExprSubAssignment extends AbstractAssignment {
   
   @Override
   public Object clone() throws CloneNotSupportedException {
      ExprSubAssignment assign = new ExprSubAssignment();
      assign.expr = expr;
      assign.isLocal = isLocal;
      assign.resultVariable = resultVariable;
      assign.block = block;
      return assign;
   }      
   
   @Override
   public int evalAsInt() {
      int eval = expr.evalAsInt();
      resultVariable.setValueAsInt(resultVariable.getValueAsInt() - eval);
      return resultVariable.getValueAsInt();
   }

   @Override
   public float evalAsFloat() {
      float eval = expr.evalAsFloat();
      resultVariable.setValueAsFloat(resultVariable.getValueAsFloat() - eval);
      return resultVariable.getValueAsFloat();
   }
   
   @Override
   public boolean evalAsBoolean() {
      throw new UnsupportedOperationException("-= Not supported on booleans");
   }   

   @Override
   public Object eval() {
      Object eval = expr.eval();
      Object result = null;
      if (eval instanceof Integer) {
         int evalI = ((Integer)eval).intValue();
         Object value = resultVariable.getValue();
         if (value instanceof Integer) {
            evalI =  ((Integer)value).intValue() - evalI;
            resultVariable.setValue(evalI);
            result = evalI;
         } else if (value instanceof Number) {
            float evalf = ((Float)value).floatValue() - evalI;
            resultVariable.setValue(evalf);
            result = evalf;            
         }
      } else if (eval instanceof Number) {
         float evalf = ((Float)eval).floatValue();
         Object value = resultVariable.getValue();
         if (value instanceof Number) {
            evalf =  ((Float)value).floatValue() - evalf;
            resultVariable.setValue(evalf);
            result = evalf;                      
         }
      }
      
      if (result == null) {
         throw new UnsupportedOperationException("-= Not supported on non numeric values");
      }
      return result;
   }

   @Override
   public String getExpressionName() {
     return "+="; 
   }
}
