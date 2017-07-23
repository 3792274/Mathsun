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
import java.util.List;
import java.util.Map;
import org.da.expressionj.model.AbstractExpression;
import org.da.expressionj.model.Expression;
import org.da.expressionj.model.MultipleAryExpression;
import org.da.expressionj.model.Variable;

/**
 * Represent an array creation expression.
 *
 * @since 0.9.2
 */
public class ExprArray extends AbstractExpression implements MultipleAryExpression {
   protected List<Expression> exprs = new ArrayList();

   public ExprArray() {
   }
   
   @Override
   public Object clone() throws CloneNotSupportedException {
      ExprArray assign = new ExprArray();
      assign.exprs = exprs;
      assign.block = block;
      return assign;
   }      

   @Override
   public String getExpressionName() {
      return "array";
   }
   
    @Override
    public void addExpression(Expression expr) {
        exprs.add(expr);
    }

    @Override
    public void setExpressions(List<Expression> exprs) {
        this.exprs = exprs;
    }   

   @Override
   public final Object eval() throws ArithmeticException {
      if (exprs.get(0).getResultType() == Expression.TYPE_BOOL) {
         boolean[] array = new boolean[exprs.size()];
         for (int i = 0; i < exprs.size(); i++) {
            array[i] = exprs.get(i).evalAsBoolean();
         }
         return array;
      } else if (exprs.get(0).getResultType() == Expression.TYPE_INTEGER) {
         int[] array = new int[exprs.size()];
         for (int i = 0; i < exprs.size(); i++) {
            array[i] = exprs.get(i).evalAsInt();
         }
         return array;         
      } else if (exprs.get(0).getResultType() == Expression.TYPE_FLOAT) {
         float[] array = new float[exprs.size()];
         for (int i = 0; i < exprs.size(); i++) {
            array[i] = exprs.get(i).evalAsFloat();
         }
         return array;                  
      } else if (exprs.get(0).getResultType() == Expression.TYPE_STRING) {
         String[] array = new String[exprs.size()];
         for (int i = 0; i < exprs.size(); i++) {
            array[i] = exprs.get(i).eval().toString();
         }
         return array;                           
      } else {
         throw new ArithmeticException("Impossible to evaluate expression");
      }
   }
   
   @Override
   public short getResultType() {
      if (exprs.isEmpty()) {
         return Expression.TYPE_UNDEF;
      } else {
         return exprs.get(0).getResultType();
      }
   }   

   @Override
   public short getResultStructure() {
      return STRUCT_ARRAY;
   }

   @Override
   public List<Expression> getExpressions() {
      return exprs;
   }

   @Override
   public Map<String, Variable> getVariables() {
      return new HashMap(1);
   }

   @Override
   public Variable getVariable(String varName) {
      return null;
   }
}
