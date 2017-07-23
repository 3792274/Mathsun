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
import java.util.Iterator;
import java.util.List;
import org.da.expressionj.expr.ExprADD;
import org.da.expressionj.expr.ExprAND;
import org.da.expressionj.expr.ExprDIV;
import org.da.expressionj.expr.ExprMULT;
import org.da.expressionj.expr.ExprNOT;
import org.da.expressionj.expr.ExprOR;
import org.da.expressionj.expr.ExprSUB;
import org.da.expressionj.expr.ParsedEquation;
import org.da.expressionj.model.AryExpression;
import org.da.expressionj.model.Constant;
import org.da.expressionj.model.Expression;
import org.da.expressionj.model.MultipleAryExpression;
import org.da.expressionj.model.UnaryExpression;
import org.da.expressionj.model.Variable;

/**
 * This class allows to simplify an Expression.
 *
 * @version 0.9
 */
public class ExpressionSimplifier {
   public ExpressionSimplifier() {
   }

   public Expression simplify(Expression expr) {
      if (expr == null) {
         return null;
      } else if (expr instanceof ParsedEquation) {
         return simplify(((ParsedEquation) expr).getExpression());
      } else if (expr instanceof AryExpression) {
         return simplify((AryExpression) expr);
      } else if (expr instanceof UnaryExpression) {
         return simplify((UnaryExpression) expr);
      } else if (expr instanceof MultipleAryExpression) {
         return simplify((MultipleAryExpression) expr);
      } else if (expr instanceof Constant) {
         return expr;
      } else if (expr instanceof Variable) {
         return expr;
      } // we should never get there
      else {
         return null;
      }
   }

   private boolean isScalarConstants(Expression expr1, Expression expr2) {
      if ((expr1 instanceof Constant) && (expr2 instanceof Constant)) {
         return (expr1.getResultStructure() == Expression.STRUCT_SCALAR)
             && (expr2.getResultStructure() == Expression.STRUCT_SCALAR);
      } else {
         return false;
      }
   }

   private Expression simplify(AryExpression ary) {
      Expression expr1 = ary.getExpression1();
      expr1 = simplify(expr1);
      Expression expr2 = ary.getExpression2();
      expr2 = simplify(expr2);
      if (ary instanceof ExprADD) {
         if (isScalarConstants(expr1, expr2)) {
            if ((expr2.getResultType() == Expression.TYPE_INTEGER) && (expr1.getResultType() == Expression.TYPE_INTEGER)) {
               Constant constant = new Constant(Expression.TYPE_INTEGER, Expression.STRUCT_SCALAR);
               constant.setValue(expr1.evalAsInt() + expr2.evalAsInt());
               return constant;
            } else if ((expr2.getResultType() == Expression.TYPE_FLOAT)
                || (expr1.getResultType() == Expression.TYPE_FLOAT)) {
               Constant constant = new Constant(Expression.TYPE_FLOAT, Expression.STRUCT_SCALAR);
               constant.setValue(expr1.evalAsFloat() + expr2.evalAsFloat());
               return constant;
            } else if ((expr2.getResultType() == Expression.TYPE_NUMERIC)
                || (expr1.getResultType() == Expression.TYPE_NUMERIC)) {
               Constant constant = new Constant(Expression.TYPE_NUMERIC, Expression.STRUCT_SCALAR);
               constant.setValue(expr1.evalAsFloat() + expr2.evalAsFloat());
               return constant;
            } else {
               return ary;
            }
         } else if ((expr1 instanceof Constant) || (expr2 instanceof Constant)) {
            if (expr1.equals(Constant.ZERO_F)) {
               return expr2;
            } else if (expr1.equals(Constant.ZERO_I)) {
               return expr2;
            } else if (expr1.equals(Constant.EMPTY_STRING)) {
               return expr2;
            } else if (expr2.equals(Constant.ZERO_F)) {
               return expr1;
            } else if (expr2.equals(Constant.ZERO_I)) {
               return expr1;
            } else if (expr2.equals(Constant.EMPTY_STRING)) {
               return expr1;
            }
         }
      } else if (ary instanceof ExprSUB) {
         if (isScalarConstants(expr1, expr2)) {
            if ((expr2.getResultType() == Expression.TYPE_INTEGER) && (expr1.getResultType() == Expression.TYPE_INTEGER)) {
               Constant constant = new Constant(Expression.TYPE_INTEGER, Expression.STRUCT_SCALAR);
               constant.setValue(expr2.evalAsInt() - expr1.evalAsInt());
               return constant;
            } else if ((expr2.getResultType() == Expression.TYPE_FLOAT)
                || (expr1.getResultType() == Expression.TYPE_FLOAT)) {
               Constant constant = new Constant(Expression.TYPE_FLOAT, Expression.STRUCT_SCALAR);
               constant.setValue(expr2.evalAsFloat() - expr1.evalAsFloat());
               return constant;
            } else if ((expr2.getResultType() == Expression.TYPE_NUMERIC)
                || (expr1.getResultType() == Expression.TYPE_NUMERIC)) {
               Constant constant = new Constant(Expression.TYPE_NUMERIC, Expression.STRUCT_SCALAR);
               constant.setValue(expr2.evalAsFloat() - expr1.evalAsFloat());
               return constant;
            } else {
               return ary;
            }
         } else if (expr1 instanceof Constant) {
            if (expr1.equals(Constant.ZERO_F)) {
               return expr2;
            } else if (expr1.equals(Constant.ZERO_I)) {
               return expr2;
            }
         }
      } else if (ary instanceof ExprMULT) {
         if (isScalarConstants(expr1, expr2)) {
            if ((expr2.getResultType() == Expression.TYPE_INTEGER) && (expr1.getResultType() == Expression.TYPE_INTEGER)) {
               Constant constant = new Constant(Expression.TYPE_INTEGER, Expression.STRUCT_SCALAR);
               constant.setValue(expr2.evalAsInt() * expr1.evalAsInt());
               return constant;
            } else if ((expr2.getResultType() == Expression.TYPE_FLOAT) || (expr1.getResultType() == Expression.TYPE_FLOAT)) {
               Constant constant = new Constant(Expression.TYPE_FLOAT, Expression.STRUCT_SCALAR);
               constant.setValue(expr2.evalAsFloat() * expr1.evalAsFloat());
               return constant;
            } else if ((expr2.getResultType() == Expression.TYPE_NUMERIC) || (expr1.getResultType() == Expression.TYPE_NUMERIC)) {
               Constant constant = new Constant(Expression.TYPE_NUMERIC, Expression.STRUCT_SCALAR);
               constant.setValue(expr2.evalAsFloat() * expr1.evalAsFloat());
               return constant;
            } else {
               return ary;
            }
         } else if ((expr1 instanceof Constant) || (expr2 instanceof Constant)) {
            if (expr1.equals(Constant.ZERO_F)) {
               return Constant.ZERO_F;
            } else if (expr1.equals(Constant.ZERO_I)) {
               return Constant.ZERO_I;
            } else if (expr2.equals(Constant.ZERO_F)) {
               return Constant.ZERO_F;
            } else if (expr2.equals(Constant.ZERO_I)) {
               return Constant.ZERO_I;
            } else if (expr1.equals(Constant.ONE_F)) {
               return expr2;
            } else if (expr1.equals(Constant.ONE_I)) {
               return expr2;
            } else if (expr2.equals(Constant.ONE_F)) {
               return expr1;
            } else if (expr2.equals(Constant.ONE_I)) {
               return expr1;
            }
         }
      } else if (ary instanceof ExprDIV) {
         if (isScalarConstants(expr1, expr2)) {
            if ((expr2.getResultType() == Expression.TYPE_INTEGER) && (expr1.getResultType() == Expression.TYPE_INTEGER)) {
               Constant constant = new Constant(Expression.TYPE_FLOAT, Expression.STRUCT_SCALAR);
               constant.setValue((float) expr2.evalAsInt() / (float) expr1.evalAsInt());
               return constant;
            } else if ((expr2.getResultType() == Expression.TYPE_FLOAT) || (expr1.getResultType() == Expression.TYPE_FLOAT)) {
               Constant constant = new Constant(Expression.TYPE_FLOAT, Expression.STRUCT_SCALAR);
               constant.setValue(expr2.evalAsFloat() / expr1.evalAsFloat());
               return constant;
            } else if ((expr2.getResultType() == Expression.TYPE_NUMERIC) || (expr1.getResultType() == Expression.TYPE_NUMERIC)) {
               Constant constant = new Constant(Expression.TYPE_NUMERIC, Expression.STRUCT_SCALAR);
               constant.setValue(expr2.evalAsFloat() / expr1.evalAsFloat());
               return constant;
            } else {
               return ary;
            }
         } else if ((expr1 instanceof Constant) || (expr2 instanceof Constant)) {
            if (expr1.equals(Constant.ONE_F)) {
               return expr2;
            } else if (expr1.equals(Constant.ONE_I)) {
               return expr2;
            } else if (expr2.equals(Constant.ZERO_F)) {
               return Constant.ZERO_F;
            } else if (expr2.equals(Constant.ZERO_I)) {
               return Constant.ZERO_I;
            }
         }
      } else if (ary instanceof ExprAND) {
         if ((expr1 instanceof Constant) && (expr2 instanceof Constant)) {
            ((Constant) expr1).setValue(expr2.evalAsBoolean() && expr1.evalAsBoolean());
            return expr1;
         } else if ((expr1 instanceof Constant) || (expr2 instanceof Constant)) {
            if (expr1.equals(Constant.TRUE)) {
               return expr2;
            } else if (expr1.equals(Constant.FALSE)) {
               return Constant.FALSE;
            } else if (expr2.equals(Constant.TRUE)) {
               return expr1;
            } else if (expr2.equals(Constant.FALSE)) {
               return Constant.FALSE;
            }
         }
      } else if (ary instanceof ExprOR) {
         if (isScalarConstants(expr1, expr2)) {
            Constant constant = new Constant(Expression.TYPE_BOOL, Expression.STRUCT_SCALAR);
            constant.setValue(expr2.evalAsBoolean() || expr1.evalAsBoolean());
            return constant;
         } else if ((expr1 instanceof Constant) || (expr2 instanceof Constant)) {
            if (expr1.equals(Constant.TRUE)) {
               return Constant.TRUE;
            } else if (expr1.equals(Constant.FALSE)) {
               return Constant.FALSE;
            } else if (expr2.equals(Constant.TRUE)) {
               return Constant.TRUE;
            } else if (expr2.equals(Constant.FALSE)) {
               return Constant.FALSE;
            }
         }
      }
      ary.setExpression1(expr1);
      ary.setExpression2(expr2);
      return ary;
   }

   private Expression simplify(UnaryExpression unary) {
      Expression expr = unary.getExpression();
      expr = simplify(expr);
      if (unary instanceof ExprNOT) {
         if (expr instanceof Constant) {
            if (expr.equals(Constant.TRUE)) {
               return Constant.FALSE;
            } else if (expr.equals(Constant.FALSE)) {
               return Constant.TRUE;
            }
         }
      }
      unary.setExpression(expr);
      return unary;
   }

   private Expression simplify(MultipleAryExpression multipleAry) {
      List<Expression> expressions = multipleAry.getExpressions();
      List<Expression> newExpr = new ArrayList(expressions.size());
      Iterator<Expression> it = expressions.iterator();
      while (it.hasNext()) {
         Expression expr = it.next();
         expr = simplify(expr);
         newExpr.add(expr);
      }
      multipleAry.setExpressions(newExpr);
      return multipleAry;
   }
}
