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
package org.da.expressionj.util;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import org.da.expressionj.expr.ParsedEquation;
import org.da.expressionj.model.AryExpression;
import org.da.expressionj.model.Constant;
import org.da.expressionj.model.Expression;
import org.da.expressionj.model.MultipleAryExpression;
import org.da.expressionj.model.UnaryExpression;
import org.da.expressionj.model.Variable;

/** This utility class allows to analyse an Expression.
 *
 * @since 0.8
 */
public class ExpressionAnalyser {
   public ExpressionAnalyser() {
   }

   /** Return true if the Expression is a constant.
    */
   public boolean isConstant(Expression expr) {
      if (expr instanceof ParsedEquation) {
         return isConstant(((ParsedEquation) expr).getExpression());
      } else {
         return (expr instanceof Constant);
      }
   }
   
   /** Return true if the Expression is a Variable.
    */   
   public boolean isVariable(Expression expr) {
      if (expr instanceof ParsedEquation) {
         return isVariable(((ParsedEquation) expr).getExpression());
      } else {
         return (expr instanceof Variable);
      }
   } 
   
   /** Return the Expression as a Constant or null if this is not a Constant.
    */   
   public Constant asConstant(Expression expr) {
      if (expr instanceof ParsedEquation) {
         return asConstant(((ParsedEquation) expr).getExpression());
      } else if (expr instanceof Constant) {
         return (Constant)expr;
      } else {
         return null;
      }
   }   
   
   /** Return the Expression as a Variable or null if this is not a Variable.
    */   
   public Variable asVariable(Expression expr) {
      if (expr instanceof ParsedEquation) {
         return asVariable(((ParsedEquation) expr).getExpression());
      } else if (expr instanceof Variable) {
         return (Variable)expr;
      } else {
         return null;
      }
   }

   /* Analyse an Expression to get the list of Variables which are defined in this expression. The
    * class will always return a not null list, which can be empty
    */
   public List<Variable> getVariablesList(Expression expr) {
      Map<String, Variable> vars = getVariables(expr);
      List<Variable> list = new ArrayList(vars.size());
      Iterator<Variable> it = vars.values().iterator();
      while (it.hasNext()) {
         list.add(it.next());
      }
      return list;
   }

   /* Analyse an Expression to get the Map of Variables which are defined in this expression. The
    * class will always return a not null Map, which can be empty
    */   
   public Map<String, Variable> getVariables(Expression expr) {
      Map<String, Variable> vars = new HashMap();
      if (expr instanceof ParsedEquation) {
         vars = ((ParsedEquation) expr).getVariables();
      } else if (expr instanceof AryExpression) {
         vars = getAryVariables((AryExpression) expr);
      } else if (expr instanceof UnaryExpression) {
         vars = getUnaryVariables((UnaryExpression) expr);
      } else if (expr instanceof MultipleAryExpression) {
         vars = getMultipleAryVariables((MultipleAryExpression) expr);
      } else if (expr instanceof Constant) {
         vars = new HashMap(1);
      } else if (expr instanceof Variable) {
         vars.put(((Variable) expr).getName(), (Variable) expr);
      }
      return vars;
   }

   private Map<String, Variable> getAryVariables(AryExpression ary) {
      Map<String, Variable> vars1 = getVariables(ary.getExpression1());
      Map<String, Variable> vars2 = getVariables(ary.getExpression2());
      vars1.putAll(vars2);
      return vars1;
   }

   private Map<String, Variable> getUnaryVariables(UnaryExpression ary) {
      Map<String, Variable> vars = getVariables(ary.getExpression());
      return vars;
   }

   private Map<String, Variable> getMultipleAryVariables(MultipleAryExpression ary) {
      Map<String, Variable> vars = new HashMap();
      Iterator<Expression> it = ary.getExpressions().iterator();
      while (it.hasNext()) {
         Expression expr = it.next();
         Map<String, Variable> exprVars = getVariables(expr);
         vars.putAll(exprVars);
      }
      return vars;
   }
}
