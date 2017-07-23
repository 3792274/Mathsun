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

import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import org.da.expressionj.expr.parser.ParseException;
import org.da.expressionj.model.AbstractExpression;
import org.da.expressionj.model.Constant;
import org.da.expressionj.model.Equation;
import org.da.expressionj.model.Expression;
import org.da.expressionj.model.Variable;

/**
 * This class represent a parsed condition.
 *
 * @version 0.9.2
 */
public class ParsedEquation extends AbstractExpression implements Equation {
   private Expression expr = null;
   private List<Variable> varsList = null;
   private Map<String, Variable> vars = new HashMap();
   private boolean acceptUndefVars = true;
   private static final Pattern patInt = Pattern.compile("[+-]?\\d+");
   private static final Pattern patFloat = Pattern.compile("[+-]?\\d+\\x2E\\d+");
   private static final Pattern patString = Pattern.compile("\\x22(.*)\\x22");
   private static final Pattern dollarString = Pattern.compile("\\x24(\\d+)");

   public ParsedEquation() {
   }

   @Override
   public Object clone() throws CloneNotSupportedException {
      ParsedEquation equ = new ParsedEquation();
      Expression _expr = (Expression) expr.clone();
      equ.setExpression(_expr);
      equ.vars = vars;
      equ.varsList = varsList;
      return equ;
   }

   @Override
   public String getExpressionName() {
      return expr.getExpressionName();
   }

   public void acceptUndefinedVariables(boolean acceptUndefVars) {
      this.acceptUndefVars = acceptUndefVars;
   }

   public void endParsing() {
      if (varsList != null) {
         varsList = null;
      }
   }

   public void setExpression(Expression expr) {
      this.expr = expr;
   }

   @Override
   public Map<String, Variable> getVariables() {
      return vars;
   }

   @Override
   public Expression getExpression() {
      return expr;
   }

   @Override
   public short getResultType() {
      if (expr == null) {
         return Expression.TYPE_UNDEF;
      } else {
         return expr.getResultType();
      }
   }

   @Override
   public short getResultStructure() {
      if (expr == null) {
         return Expression.TYPE_UNDEF;
      } else {
         return expr.getResultStructure();
      }
   }

   public void addVariable(Variable var) {
      vars.put(var.getName(), var);
   }

   public void setVariables(List<Variable> varsList) {
      vars.clear();
      this.varsList = varsList;
      Iterator<Variable> it = varsList.iterator();
      while (it.hasNext()) {
         Variable var = it.next();
         vars.put(var.getName(), var);
      }
   }

   public Variable getDollarVariable(String dollarName) throws ParseException {
      if (varsList == null) {
         throw new ParseException("Variable of position " + dollarName + " undefined (no Variables List)");
      }

      Matcher m = dollarString.matcher(dollarName);
      if (m.matches()) {
         String positionS = m.group(1);
         int pos = Integer.parseInt(positionS) - 1;
         if (varsList.size() <= pos) {
            throw new ParseException("Variable of position " + dollarName + " undefined");
         } else {
            return varsList.get(pos);
         }
      } else {
         throw new ParseException("Variable of position " + dollarName + " undefined");
      }
   }

   @Override
   public Variable getVariable(String varName) {
      if (!vars.containsKey(varName)) {
         if (acceptUndefVars) {
            Variable var = new Variable(varName);
            vars.put(varName, var);
            return var;
         } else {
            return null;
         }
      } else {
         return vars.get(varName);
      }
   }

   public Constant createPIConstant(double value) {
      return new Constant(Math.PI, Expression.TYPE_FLOAT);
   }

   public Constant createBooleanConstant(boolean value) {
      return new Constant(value, Expression.TYPE_BOOL);
   }

   public Constant createConstant(String expr) {
      if (expr.equals("true")) {
         return new Constant(true, Expression.TYPE_BOOL);
      } else if (expr.equals("false")) {
         return new Constant(false, Expression.TYPE_BOOL);
      } else {
         Matcher m = patInt.matcher(expr);
         if (m.matches()) {
            return new Constant(Integer.parseInt(expr), Expression.TYPE_INTEGER);
         }
         m = patFloat.matcher(expr);
         if (m.matches()) {
            return new Constant(Float.parseFloat(expr), Expression.TYPE_FLOAT);
         }
         m = patString.matcher(expr);
         if (m.matches()) {
            String s = m.group(1);
            return new Constant(s, Expression.TYPE_STRING);
         }
         return null;
      }
   }

   @Override
   public void setValue(String varName, Object value) {
      if (vars.containsKey(varName)) {
         vars.get(varName).setValue(value);
      }
   }

   @Override
   public int evalAsInt() throws ArithmeticException {
      return expr.evalAsInt();
   }

   @Override
   public float evalAsFloat() throws ArithmeticException {
      return expr.evalAsFloat();
   }

   @Override
   public boolean evalAsBoolean() throws ArithmeticException {
      return expr.evalAsBoolean();
   }

   @Override
   public Object eval() throws ArithmeticException {
      if (getResultType() == TYPE_INTEGER) {
         return evalAsInt();
      } else if (getResultType() == TYPE_FLOAT) {
         return evalAsFloat();
      } else if (getResultType() == TYPE_BOOL) {
         return evalAsBoolean();
      }
      return expr.eval();
   }
}
