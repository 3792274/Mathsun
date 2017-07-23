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
package org.da.expressionj.util;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import org.da.expressionj.expr.ExprADD;
import org.da.expressionj.expr.ExprArray;
import org.da.expressionj.expr.ExprArrayIndex;
import org.da.expressionj.expr.ExprDIV;
import org.da.expressionj.expr.ExprMULT;
import org.da.expressionj.expr.ExprSUB;
import org.da.expressionj.expr.ParsedEquation;
import org.da.expressionj.model.AryExpression;
import org.da.expressionj.model.Constant;
import org.da.expressionj.model.Equation;
import org.da.expressionj.model.Expression;
import org.da.expressionj.model.MultipleAryExpression;
import org.da.expressionj.model.UnaryExpression;
import org.da.expressionj.model.Value;
import org.da.expressionj.model.Variable;

/**
 * This class allows to combine two expressions. This class provide several method:
 * <ul>
 * <li>{@link #replace(Expression, String, Expression)}:
 * Change a value in an Expression. The change consist in replacing a Variable by an Expression, which may contain Variables.</li>
 * <li>{@link #add(Expression, Expression)}: Add two expressions.</li>
 * <li>{@link #subtract(Expression, Expression)}: Substract two expressions.</li>
 * <li>{@link #multiply(Expression, Expression)}: Multiply two expressions.</li>
 * <li>{@link #divide(Expression, Expression)}: Divide two expressions.</li>
 * </ul>
 *
 *  More simple methods also exist to easily add, subtract, multiply, or divide an expression with an int or a float.
 *
 * @version 0.9.2
 */
public class ExpressionCombiner {
   public static final int DONT_COPY = 0;
   public static final int SHALLOW_COPY = 1;
   public static final int DEEP_COPY = 2;
   private int copyBehavior = DONT_COPY;
   
   public ExpressionCombiner() {
   }   
   
   /** Set the copy behavior of this ExpressionCombiner.
    * <ul>
    * <li>{@link #DONT_COPY}: return the expression (no copy)</li>
    * <li>{@link #SHALLOW_COPY}: return a shallow copy of the expression</li>
    * <li>{@link #DEEP_COPY}: return a deep copy of the expression</li>
    * </ul>
    */
   public void setCopyBehavior(int copyBehavior) {
      this.copyBehavior = copyBehavior;
   }
   
   /** Get the copy behavior of this ExpressionCombiner.
   */
   public int getCopyBehavior() {
      return copyBehavior;
   }
   
   /**
    * Add an expression and an integer constant.
    */
   public Expression add(Expression expr, int value) {
      Constant constant = new Constant(value, Expression.TYPE_INTEGER);
      return add(expr, constant);
   }   
   
   /**
    * Add an expression and a float constant.
    */
   public Expression add(Expression expr, float value) {
      Constant constant = new Constant(value, Expression.TYPE_FLOAT);
      return add(expr, constant);
   }      
   
   /**
    * Add an expression and a String constant.
    */
   public Expression add(Expression expr, String value) {
      Constant constant = new Constant(value, Expression.TYPE_STRING);
      return add(expr, constant);
   }        
   
   /**
    * Subtract an expression and an integer constant.
    */
   public Expression subtract(Expression expr, int value) {
      Constant constant = new Constant(value, Expression.TYPE_INTEGER);
      return subtract(expr, constant);
   }   
   
   /**
    * Subtract an expression by a float constant.
    */
   public Expression subtract(Expression expr, float value) {
      Constant constant = new Constant(value, Expression.TYPE_FLOAT);
      return subtract(expr, constant);
   }         
   
   /**
    * Multiply an expression by an integer constant.
    */
   public Expression multiply(Expression expr, int value) {
      Constant constant = new Constant(value, Expression.TYPE_INTEGER);
      return subtract(expr, constant);
   }   
   
   /**
    * Multiply an expression by a float constant.
    */
   public Expression multiply(Expression expr, float value) {
      Constant constant = new Constant(value, Expression.TYPE_FLOAT);
      return subtract(expr, constant);
   }            
   
   /**
    * Divide an expression by an integer constant.
    */
   public Expression divide(Expression expr, int value) {
      Constant constant = new Constant(value, Expression.TYPE_INTEGER);
      return divide(expr, constant);
   }   
   
   /**
    * Divide an expression by a float constant.
    */
   public Expression divide(Expression expr, float value) {
      Constant constant = new Constant(value, Expression.TYPE_FLOAT);
      return divide(expr, constant);
   }               
   
   /**
    * Create an array expression.
    */   
   public Expression createArray(Expression... exprs) {
      ExprArray array = new ExprArray();
      for (int i = 0; i < exprs.length; i++) {
         array.addExpression(exprs[i]);
      }
      return array;
   }

   /**
    * Add an expression to another.
    *
    * @param expr1 the first expression
    * @param expr2 the expression to add
    * @return expr1 + expr2
    */
   public Expression add(Expression expr1, Expression expr2) {
      if (expr1 == null) {
         return expr2;
      } else if (expr2 == null) {
         return expr1;
      } else {
         ExprADD add = new ExprADD();
         add.setExpression1(expr2);
         add.setExpression2(expr1);
         ParsedEquation equ = new ParsedEquation();
         ExpressionSimplifier simplifier = new ExpressionSimplifier();
         Expression expr = simplifier.simplify(add);
         equ.setExpression(expr);
         equ.getVariables().putAll(expr1.getVariables());
         equ.getVariables().putAll(expr2.getVariables());

         return equ;
      }
   }

   /**
    * Subtract an expression to another.
    *
    * @param expr1 the first expression
    * @param expr2 the expression to substract
    * @return expr1 - expr2
    */
   public Expression subtract(Expression expr1, Expression expr2) {
      if (expr1 == null) {
         ExprMULT mult = new ExprMULT();
         mult.setExpression1(new Constant(-1, Expression.TYPE_INTEGER));
         mult.setExpression2(expr2);
         mult.getVariables().putAll(expr2.getVariables());
         return mult;
      } else if (expr2 == null) {
         return expr1;
      } else {
         ExprSUB sub = new ExprSUB();
         sub.setExpression1(expr2);
         sub.setExpression2(expr1);
         ParsedEquation equ = new ParsedEquation();
         ExpressionSimplifier simplifier = new ExpressionSimplifier();
         Expression expr = simplifier.simplify(sub);
         equ.setExpression(expr);
         equ.getVariables().putAll(expr1.getVariables());
         equ.getVariables().putAll(expr2.getVariables());

         return equ;
      }
   }

   /**
    * Multiply an expression with another.
    *
    * @param expr1 the first expression
    * @param expr2 the expression to multiply
    * @return expr1 * expr2
    */
   public Expression multiply(Expression expr1, Expression expr2) {
      if (expr1 == null) {
         return null;
      } else if (expr2 == null) {
         return null;
      } else {
         ExprMULT mult = new ExprMULT();
         mult.setExpression1(expr2);
         mult.setExpression2(expr1);
         ParsedEquation equ = new ParsedEquation();
         ExpressionSimplifier simplifier = new ExpressionSimplifier();
         Expression expr = simplifier.simplify(mult);
         equ.setExpression(expr);
         equ.getVariables().putAll(expr1.getVariables());
         equ.getVariables().putAll(expr2.getVariables());

         return equ;
      }
   }

   /**
    * Divide an expression by another.
    *
    * @param expr1 the first expression
    * @param expr2 the expression to divide
    * @return expr1 / expr2
    */
   public Expression divide(Expression expr1, Expression expr2) {
      if (expr1 == null) {
         return null;
      } else if (expr2 == null) {
         return null;
      } else {
         ExprDIV div = new ExprDIV();
         div.setExpression1(expr2);
         div.setExpression2(expr1);
         ParsedEquation equ = new ParsedEquation();
         ExpressionSimplifier simplifier = new ExpressionSimplifier();
         Expression expr = simplifier.simplify(div);
         equ.setExpression(expr);
         equ.getVariables().putAll(expr1.getVariables());
         equ.getVariables().putAll(expr2.getVariables());

         return equ;
      }
   }
   
   private Expression intClone(Expression expr) {
      if (copyBehavior == DEEP_COPY) {
         try {
            expr = (Expression)expr.clone();
         } catch (CloneNotSupportedException ex) {            
         }   
         return expr;
      } else {
         return expr;
      }
   }   
   
   private Expression clone(Expression expr) {
      if (copyBehavior != DONT_COPY) {
         try {
            expr = (Expression)expr.clone();
         } catch (CloneNotSupportedException ex) {            
         }   
         return expr;
      } else {
         return expr;
      }
   }

   /**
    * Replace a variable in an expression by another sub-expression. For example, if we have:
    * <pre>
    * </pre
    * expr = sin(a + 2)
    * varName = a
    * replacedExpr = b * c
    * </pre>
    * then the result is
    * <code>sin((b * c) + 2)</code>
    * <p>Note that:
    * <ul>
    * <li>The method will return the first expression if the sub-expression is equal to the first</li>
    * <li>The method will also return the first expression if the variable is not present in the first expression</li>
    * </ul>
    * </p>
    * @param expr the expression
    * @param varName the name of the variable which must be replaced
    * @param replacedExpr the expression which must replace the variable
    */
   public Expression replace(Expression expr, String varName, Expression replacedExpr) {
      if (expr == null) {
         return null;
      } else if (expr == replacedExpr) {
         return expr;
      } else if (expr instanceof ParsedEquation) {
         ExpressionAnalyser analyser = new ExpressionAnalyser();
         Map<String, Variable> replacedVars = analyser.getVariables(replacedExpr);
         finalizeVariableList(varName, (ParsedEquation) expr, replacedVars);

         replacedExpr = getExpression(replacedExpr);
         Expression oldExp = getExpression(expr);
         if (replacedExpr == oldExp) {
            return expr;
         }
         oldExp = clone(oldExp);
         Expression newExpr = replace(oldExp, varName, replacedExpr, replacedVars);
         if (expr instanceof ParsedEquation) {
            ((ParsedEquation) expr).setExpression(newExpr);
         }
         return expr;
      } else if (expr instanceof AryExpression) {
         expr = clone(expr);
         Expression _expr = replace((AryExpression) expr, varName, replacedExpr, null);     
         return _expr;
      } else if (expr instanceof ExprArrayIndex) {
         expr = clone(expr);
         Expression _expr = replace((ExprArrayIndex) expr, varName, replacedExpr, null);              
         return _expr;
      } else if (expr instanceof UnaryExpression) {
         expr = clone(expr);
         Expression _expr = replace((UnaryExpression) expr, varName, replacedExpr, null);         
         return _expr;
      } else if (expr instanceof MultipleAryExpression) {
         expr = clone(expr);
         Expression _expr = replace((MultipleAryExpression) expr, varName, replacedExpr, null);        
         return _expr;
      } else if (expr instanceof Constant) {
         return expr;
      } else if (expr instanceof Variable) {
         return replace((Variable) expr, varName, replacedExpr);
      } // we should never get there
      else {
         return null;
      }
   }

   private Expression getExpression(Expression expr) {
      if (expr instanceof ParsedEquation) {
         return ((ParsedEquation) expr).getExpression();
      } else {
         return expr;
      }
   }

   private Expression replace(Expression expr, String varName, Expression replacedExpr, Map<String, Variable> replacedVars) {
      replacedExpr = getExpression(replacedExpr);
      expr = getExpression(expr);

      if (expr == replacedExpr) {
         return expr;
      } else if (expr instanceof AryExpression) {
         expr = intClone(expr);
         return replace((AryExpression) expr, varName, replacedExpr, replacedVars);
      } else if (expr instanceof ExprArrayIndex) {
         expr = intClone(expr);
         return replace((ExprArrayIndex) expr, varName, replacedExpr, replacedVars);         
      } else if (expr instanceof UnaryExpression) {
         expr = intClone(expr);
         return replace((UnaryExpression) expr, varName, replacedExpr, replacedVars);
      } else if (expr instanceof MultipleAryExpression) {
         expr = intClone(expr);
         return replace((MultipleAryExpression) expr, varName, replacedExpr, replacedVars);
      } else if (expr instanceof Constant) {
         return expr;
      } else if (expr instanceof Variable) {
         return replace((Variable) expr, varName, replacedExpr);
      } // we should never get there
      else {
         return null;
      }
   }

   private void finalizeVariableList(String varName, ParsedEquation expr, Map<String, Variable> replacedVars) {
      Map<String, Variable> vars = expr.getVariables();
      if (vars.containsKey(varName) && (!replacedVars.containsKey(varName))) {
         vars.remove(varName);
      }
      Iterator<Variable> it = replacedVars.values().iterator();
      while (it.hasNext()) {
         Variable var = it.next();
         if (!vars.containsKey(var.getName())) {
            vars.put(var.getName(), var);
         }
      }
   }

   private Expression replace(AryExpression ary, String varName, Expression replacedExpr, Map<String, Variable> replacedVars) {
      replacedExpr = getExpression(replacedExpr);

      Expression expr1 = ary.getExpression1();
      expr1 = replace(expr1, varName, replacedExpr, replacedVars);
      Expression expr2 = ary.getExpression2();
      expr2 = replace(expr2, varName, replacedExpr, replacedVars);
      ary.setExpression1(expr1);
      ary.setExpression2(expr2);
      return ary;
   }

   private Expression replace(UnaryExpression unary, String varName, Expression replacedExpr, Map<String, Variable> replacedVars) {
      replacedExpr = getExpression(replacedExpr);

      Expression expr = unary.getExpression();
      expr = replace(expr, varName, replacedExpr, replacedVars);
      unary.setExpression(expr);
      return unary;
   }

   private Expression replace(ExprArrayIndex array, String varName, Expression replacedExpr, Map<String, Variable> replacedVars) {
      replacedExpr = getExpression(replacedExpr);

      Expression expr = array.getExpression();
      expr = replace(expr, varName, replacedExpr, replacedVars);
      array.setExpression(expr);

      Value value = array.getValue();
      if (value instanceof Variable) {
         Variable variable = (Variable) value;
         if (variable.getName().equals(varName)) {
            if (replacedExpr instanceof Variable) {
               array.setValue((Variable)replacedExpr);
            } else if (replacedExpr instanceof Constant) {
               array.setValue((Constant)replacedExpr);               
            }
         }
      }
      return array;
   }

   private Expression replace(Variable variable, String varName, Expression replacedExpr) {
      replacedExpr = getExpression(replacedExpr);

      if (variable.getName().equals(varName)) {
         if (replacedExpr instanceof Equation) {
            return replacedExpr;
         } else {
            ParsedEquation equ = new ParsedEquation();
            equ.setExpression(replacedExpr);
            return equ;
         }
      } else {
         return variable;
      }
   }

   private Expression replace(MultipleAryExpression multipleAry, String varName, Expression replacedExpr,
       Map<String, Variable> replacedVars) {
      replacedExpr = getExpression(replacedExpr);

      List<Expression> expressions = multipleAry.getExpressions();
      List<Expression> newExpr = new ArrayList(expressions.size());
      Iterator<Expression> it = expressions.iterator();
      while (it.hasNext()) {
         Expression expr = it.next();
         expr = replace(expr, varName, replacedExpr, replacedVars);
         newExpr.add(expr);
      }
      multipleAry.setExpressions(newExpr);
      return multipleAry;
   }
}
