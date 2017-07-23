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

import java.util.Iterator;
import java.util.List;
import java.util.Stack;
import org.da.expressionj.expr.CodeBlock;
import org.da.expressionj.expr.ExprADD;
import org.da.expressionj.expr.ExprAND;
import org.da.expressionj.expr.ExprAddAssignment;
import org.da.expressionj.expr.ExprAffectation;
import org.da.expressionj.expr.ExprArray;
import org.da.expressionj.expr.ExprArrayAffect;
import org.da.expressionj.expr.ExprArrayIndex;
import org.da.expressionj.expr.ExprBREAK;
import org.da.expressionj.expr.ExprCHOICE;
import org.da.expressionj.expr.ExprDecrement;
import org.da.expressionj.expr.ExprDivAssignment;
import org.da.expressionj.expr.ExprIncrement;
import org.da.expressionj.expr.ExprMULT;
import org.da.expressionj.expr.ExprMultAssignment;
import org.da.expressionj.expr.ExprNOT;
import org.da.expressionj.expr.ExprOR;
import org.da.expressionj.expr.ExprSUB;
import org.da.expressionj.expr.ExprSubAssignment;
import org.da.expressionj.expr.ExprWHILE;
import org.da.expressionj.expr.ParsedEquation;
import org.da.expressionj.model.AryExpression;
import org.da.expressionj.model.Constant;
import org.da.expressionj.model.Expression;
import org.da.expressionj.model.ExpressionType;
import org.da.expressionj.model.MultipleAryExpression;
import org.da.expressionj.model.UnaryExpression;
import org.da.expressionj.model.ValueWrapper;
import org.da.expressionj.model.Variable;

/**
 * This class allows to export an Expression to a String format. The result of tnhe export, if Parsed again,
 * would return the same equation as the one which has been used for the Export.
 *
 * @version 0.10
 */
public class ExpressionExporter {
   private static boolean newLinesForBlocks = false;
   private static int indentation = 3;
   private static String indentDelta = "   ";
   private boolean keepConstants = false;
   private Stack<String> indentStack = new Stack();
   private static final String EOL = System.getProperty("line.separator");

   public ExpressionExporter() {
   }

   public ExpressionExporter(boolean keepConstants) {
      this.keepConstants = keepConstants;
   }

   public static void setNewLinesForBlocks(boolean newLines) {
      newLinesForBlocks = newLines;
   }

   public static void setIndentation(int indent) {
      indentation = indent;
      if (indentation > 5) {
         indentation = 5;
      }
      indentDelta = "       ".substring(0, indentation);
   }

   /**
    * Export the expression as a readable String.
    * Note that if an exported Expression is parsed again, the same expression
    * will be returned, so that export(EquationParser.parse(expressionString)) = expressionString. For example:
    * <p>
    * export("a + sin(b)") = "a + sin(b)";
    * </p>
    */
   public String export(Expression expr) {
      return export(expr, false, true);
   }
   
   private String export(Expression expr, boolean allowReturn) {
      return export(expr, true, allowReturn);
   }   

   /**
    * Export the expression as a readable String.
    * Note that if an exported Expression is parsed again, the same expression
    * will be returned, so that export(EquationParser.parse(expressionString)) = expressionString. For example:
    * <p>
    * export("a + sin(b)") = "a + sin(b)";
    * </p>
    */
   private String export(Expression expr, boolean inExpression, boolean allowReturn) {
      if (!inExpression) {
         indentStack.clear();
      }
      if (expr == null) {
         return null;
      } else if (expr instanceof ParsedEquation) {
         return export(((ParsedEquation) expr).getExpression(), true, allowReturn);
      } else if (expr instanceof CodeBlock) {
         return export((CodeBlock) expr, allowReturn);
      } else if (expr instanceof ExprCHOICE) {
         return export((ExprCHOICE) expr);
      } else if (expr instanceof ExprWHILE) {
         return export((ExprWHILE) expr);
      } else if (expr instanceof ExprBREAK) {
         return export((ExprBREAK) expr);         
      } else if (expr instanceof ExprArray) {
         return export((ExprArray) expr);   
      } else if (expr instanceof ExprArrayAffect) {
         return export((ExprArrayAffect) expr);           
      } else if (expr instanceof ExprAffectation) {
         return export((ExprAffectation) expr);
      } else if (expr instanceof ExprAddAssignment) {
         return export((ExprAddAssignment) expr);  
      } else if (expr instanceof ExprSubAssignment) {
         return export((ExprSubAssignment) expr);  
      } else if (expr instanceof ExprMultAssignment) {
         return export((ExprMultAssignment) expr);  
      } else if (expr instanceof ExprDivAssignment) {
         return export((ExprDivAssignment) expr);          
      } else if (expr instanceof ExprDecrement) {
         return export((ExprDecrement) expr);          
      } else if (expr instanceof ExprIncrement) {
         return export((ExprIncrement) expr);         
      } else if (expr instanceof AryExpression) {
         return export((AryExpression) expr);
      } else if (expr instanceof UnaryExpression) {
         return export((UnaryExpression) expr);
      } else if (expr instanceof MultipleAryExpression) {
         return export((MultipleAryExpression) expr);
      } else if (expr instanceof Constant) {
         return export((Constant) expr);
      } else if (expr instanceof Variable) {
         return export((Variable) expr);
      } // we should never get there
      else {
         return null;
      }
   }

   private boolean isCompatible(AryExpression expr1, AryExpression expr2) {
      if ((expr1 instanceof ExprAND) && (expr2 instanceof ExprAND)) {
         return true;
      } else if ((expr1 instanceof ExprOR) && (expr2 instanceof ExprOR)) {
         return true;
      } else if (((expr1 instanceof ExprADD) || (expr1 instanceof ExprSUB))
              && ((expr2 instanceof ExprADD) || (expr2 instanceof ExprSUB))) {
         return true;
      } else if ((expr1 instanceof ExprMULT) && (expr2 instanceof ExprMULT)) {
         return true;
      } else {
         return false;
      }
   }
   
   private String export(ExprBREAK breakExpr) {
      return "break";
   }   

   private String export(ExprWHILE whileExpr) {
      StringBuilder buf = new StringBuilder();
      Expression condition = whileExpr.getCondition();
      appendToBuffer(buf, "while (");
      buf.append(export(condition, true, false)).append(") {");
      if (newLinesForBlocks) {
         buf.append(EOL);
      }
      Expression expr = whileExpr.getExpression();
      if (indentStack.empty()) {
         indentStack.push(indentDelta);
      } else {
         indentStack.push(indentStack.peek() + indentDelta);
      }
      appendToBuffer(buf, export(expr, true, false));
      
      if (!indentStack.empty()) {
         indentStack.pop();
      }
      if (newLinesForBlocks) {
         buf.append(EOL);
      }
      appendToBuffer(buf, "}");

      return buf.toString();
   }
   
   private String export(ExprArray array) {
      StringBuilder buf = new StringBuilder();
      buf.append("{");
      Iterator<Expression> it = array.getExpressions().iterator();
      while (it.hasNext()) {
         Expression expr = it.next();
         buf.append(export(expr, true));
         if (it.hasNext()) {
            buf.append(", ");
         }
      }
      
      buf.append("}");
      return buf.toString();
   }

   private String export(ExprCHOICE choice) {
      StringBuilder buf = new StringBuilder();
      boolean first = true;
      for (int index = 0; index < choice.getConditions().size(); index++) {
         Expression condition = choice.getConditions().get(index);
         if (first) {
            appendToBuffer(buf, "if (");
            buf.append(export(condition, true)).append(") {");
         } else {
            if (newLinesForBlocks) {
               buf.append(EOL);
            }
            appendToBuffer(buf, "} else if (");
            buf.append(export(condition, true)).append(") {");
         }
         if (newLinesForBlocks) {
            buf.append(EOL);
         }
         Expression expr = choice.getExpressions().get(index);
         if (indentStack.empty()) {
            indentStack.push(indentDelta);
         } else {
            indentStack.push(indentStack.peek() + indentDelta);
         }
         if ((expr instanceof ExprCHOICE) || (expr instanceof ExprWHILE) || (expr instanceof CodeBlock)) {
            buf.append(export(expr, true));
         } else {
            appendToBuffer(buf, "return ");
            buf.append(export(expr, true));
         }
         if (!indentStack.empty()) {
            indentStack.pop();
         }
      }
      if (newLinesForBlocks) {
         buf.append(EOL);
      }
      if (choice.getExpressions().size() > choice.getConditions().size()) {
         appendToBuffer(buf, "} else {");
         if (newLinesForBlocks) {
            buf.append(EOL);
         }
         if (indentStack.empty()) {
            indentStack.push(indentDelta);
         } else {
            indentStack.push(indentStack.peek() + indentDelta);
         }
         Expression expr = choice.getExpressions().get(choice.getExpressions().size() - 1);
         if ((expr instanceof ExprCHOICE) || (expr instanceof ExprWHILE)|| (expr instanceof CodeBlock)) {
            buf.append(export(expr, true));
         } else {
            appendToBuffer(buf, "return ");
            buf.append(export(expr, true));
         }
         if (!indentStack.empty()) {
            indentStack.pop();
         }
         if (newLinesForBlocks) {
            buf.append(EOL);
         }         
      }
      appendToBuffer(buf, "}");

      return buf.toString();
   }

   private void appendToBuffer(StringBuilder buf, String content) {
      if (newLinesForBlocks) {
         if (!indentStack.empty()) {
            buf.append(indentStack.peek()).append(content);
         } else {
            buf.append(content);
         }
      } else {
         buf.append(content);
      }
   }

   private String export(CodeBlock block, boolean allowReturn) {
      if (block.countExpressions() == 1) {
         Expression last = block.lastExpression();
         if (!(last instanceof ExprCHOICE) && ! (last instanceof ExprWHILE) && ! (last instanceof ExprBREAK) && allowReturn) {
            StringBuilder buf = new StringBuilder();
            appendToBuffer(buf, "return ");
            buf.append(export(block.getExpressions().get(0), false));
            return buf.toString();
         } else {
            return export(block.getExpressions().get(0), allowReturn);
         }
      }

      StringBuilder buf = new StringBuilder();
      Iterator<Expression> it = block.getExpressions().iterator();
      boolean first = true;
      while (it.hasNext()) {
         Expression expr = it.next();
         if ((!first) && (!newLinesForBlocks)) {
            buf.append(" ");
         } else {
            first = false;
         }
         if (it.hasNext() || (expr instanceof ExprCHOICE)) {
            appendToBuffer(buf, export(expr, true));
            buf.append(";");
         } else {
            appendToBuffer(buf, "return ");
            buf.append(export(expr, true)).append(";");
         }
         if (newLinesForBlocks && it.hasNext()) {
            buf.append(EOL);
         }
      }

      return buf.toString();
   }

   private String getVariableType(Variable var) {
      if (var.getType() == Expression.TYPE_INTEGER) {
         if (var.getResultStructure() == Expression.STRUCT_SCALAR) {
            return "int";
         } else {
            return "int[]";
         }
      } else if (var.getType() == Expression.TYPE_FLOAT) {
         if (var.getResultStructure() == Expression.STRUCT_SCALAR) {
            return "float";
         } else {
            return "float[]";
         }
      } else if (var.getType() == Expression.TYPE_BOOL) {
         if (var.getResultStructure() == Expression.STRUCT_SCALAR) {
            return "bool";
         } else {
            return "bool[]";
         }
      } else if (var.getType() == Expression.TYPE_STRING) {
         if (var.getResultStructure() == Expression.STRUCT_SCALAR) {
            return "string";
         } else {
            return "string[]";
         }
      } else {
         return "var";
      }
   }
   
   private String export(ExprDecrement decrement) {
      Variable var = decrement.getVariable();
      return var.getName() + "--";
   }      
   
   private String export(ExprIncrement increment) {
      Variable var = increment.getVariable();
      return var.getName() + "++";
   }   
   
   private String export(ExprAddAssignment affect) {
      Variable var = affect.getResult();
      Expression expr = affect.getExpression();
      return var.getName() + " += " + export(expr, true);
   }   
   
   private String export(ExprSubAssignment affect) {
      Variable var = affect.getResult();
      Expression expr = affect.getExpression();
      return var.getName() + " -= " + export(expr, true);
   }     
   
   private String export(ExprMultAssignment affect) {
      Variable var = affect.getResult();
      Expression expr = affect.getExpression();
      return var.getName() + " *= " + export(expr, true);
   }     
   
   private String export(ExprDivAssignment affect) {
      Variable var = affect.getResult();
      Expression expr = affect.getExpression();
      return var.getName() + " /= " + export(expr, true);
   } 
   
   private String export(ExprArrayAffect affect) {
      Variable var = affect.getArray();
      Expression expr = affect.getExpression();
      Expression index = affect.getIndex();
      return var.getName() + "[" + export(index, true) + "] = " + export(expr, true);
   }   

   private String export(ExprAffectation affect) {
      Variable var = affect.getResult();
      Expression expr = affect.getExpression();
      if (affect.getParentBlock() != null) {
         CodeBlock block = affect.getParentBlock();
         if (block.getInternalVariables().containsKey(var.getName())) {
            return getVariableType(var) + " " + var.getName() + " = " + export(expr, true);
         } else {
            return var.getName() + " = " + export(expr, true);
         }
      } else {
         return var.getName() + " = " + export(expr, true);
      }
   }

   private String export(AryExpression ary) {
      String expr1S;
      String expr2S;
      Expression _expr1 = getInternalExpression(ary.getExpression1());
      if (_expr1 instanceof AryExpression) {
         AryExpression _expr1a = (AryExpression) _expr1;
         if (isCompatible(ary, _expr1a)) {
            expr1S = export(_expr1a, true);
         } else if ((_expr1a instanceof ExprADD)
                 && ((_expr1a.getExpression1() == null) || (_expr1a.getExpression2() == null))) {
            expr1S = export(_expr1a, true);
         } else {
            expr1S = "(" + export(_expr1a, true) + ")";
         }
      } else {
         expr1S = export(_expr1, true);
      }

      Expression _expr2 = getInternalExpression(ary.getExpression2());
      if (_expr2 instanceof AryExpression) {
         AryExpression expr2 = (AryExpression) _expr2;
         if (isCompatible(ary, expr2)) {
            expr2S = export(expr2, true);
         } else if ((expr2 instanceof ExprADD)
            && ((expr2.getExpression1() == null) || (expr2.getExpression2() == null))) {
            expr2S = export(expr2, true);
         } else {
            expr2S = "(" + export(expr2, true) + ")";
         }
      } else {
         expr2S = export(_expr2, true);
      }
      if (_expr1 instanceof AryExpression) {
         if (ary instanceof ExprADD) {
            if (expr2S == null) {
               return ary.getExpressionName() + expr1S;
            } else {
               if (_expr1 instanceof ExprSUB) {
                  ExprSUB exprSUB = (ExprSUB) _expr1;
                  if (exprSUB.getExpression2() == null) {
                     return expr2S + " - " + expr1S.substring(1);
                  } else {
                     return expr2S + " " + ary.getExpressionName() + " " + expr1S;
                  }
               } else {
                  return expr2S + " " + ary.getExpressionName() + " " + expr1S;
               }
            }
         } else if (ary instanceof ExprSUB) {
            if (expr2S == null) {
               return ary.getExpressionName() + expr1S;
            } else {
               return expr2S + " " + ary.getExpressionName() + " (" + expr1S + ")";
            }
         } else {
            return expr2S + " " + ary.getExpressionName() + " " + expr1S;
         }
      } else {
         if (ary instanceof ExprADD) {
            if (expr2S == null) {
               return expr1S;
            } else {
               return expr2S + " " + ary.getExpressionName() + " " + expr1S;
            }
         } else if (ary instanceof ExprSUB) {
            if (expr2S == null) {
               return ary.getExpressionName() + expr1S;
            } else {
               return expr2S + " " + ary.getExpressionName() + " " + expr1S;
            }
         } else {
            return expr2S + " " + ary.getExpressionName() + " " + expr1S;
         }
      }
   }

   private Expression getInternalExpression(Expression expr) {
      Expression myExpr = expr;
      while (true) {
         if (myExpr instanceof ParsedEquation) {
            myExpr = ((ParsedEquation) expr).getExpression();
         } else {
            break;
         }
      }
      return myExpr;
   }

   private String export(UnaryExpression unary) {
      if (unary instanceof ExprArrayIndex) {
         ExprArrayIndex exprArray = (ExprArrayIndex) unary;
         return exprArray.getValue().getName() + "[" + export(exprArray.getExpression(), true) + "]";
      } else if (unary instanceof ExprNOT) {
         String exprS;
         Expression expr = getInternalExpression(unary.getExpression());
         if (expr instanceof AryExpression) {
            exprS = "(" + export(expr, true) + ")";
         } else {
            exprS = export(expr, true);
         }
         return unary.getExpressionName() + " " + exprS;
      } else {
         return unary.getExpressionName() + "(" + export(unary.getExpression(), true) + ")";
      }
   }

   private String export(Constant constant) {
      if (keepConstants && (constant.getName() != null)) {
         return constant.getName();
      } else {
         Object value = constant.getValue();
         if (value instanceof Float) {
            if (((Float) value).floatValue() == 0) {
               return "0";
            } else if (constant.getType() == Expression.TYPE_INTEGER) {
               return Integer.toString(((Float) value).intValue()).toString();
            } else {
               return value.toString();
            }
         } else if (value instanceof ValueWrapper.Float) {
            if (((ValueWrapper.Float) value).getFloatValue() == 0) {
               return "0";
            } else if (constant.getType() == Expression.TYPE_INTEGER) {
               return Integer.toString(((ValueWrapper) value).getIntValue());
            } else {
               return value.toString();
            }         
         } else {
            String str = value.toString();
            if (constant.getResultType() == ExpressionType.TYPE_STRING) {
               return "\"" + str + "\"";
            } else {
               return value.toString();
            }
         }
      }
   }

   private String export(Variable variable) {
      return variable.getName();
   }

   private String export(MultipleAryExpression multipleAry) {
      StringBuilder builder = new StringBuilder();
      String name = multipleAry.getExpressionName();
      builder.append(name).append("(");
      List<Expression> expressions = multipleAry.getExpressions();
      Iterator<Expression> it = expressions.iterator();
      while (it.hasNext()) {
         Expression _expr = it.next();
         builder.append(export(_expr, true));
         if (it.hasNext()) {
            builder.append(",");
         } else {
            builder.append(")");
         }
      }
      return builder.toString();
   }
}
