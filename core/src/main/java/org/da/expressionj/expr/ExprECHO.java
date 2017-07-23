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

import org.da.expressionj.functions.EchoLogger;
import org.da.expressionj.model.Expression;

/**
 * Represent a "echo" expression. The echo expression will echo its expression to the PrintStream defined by the
 * {@link org.da.expressionj.functions.EchoLogger} class, or to the standard Output Stream if the PrintStream is undefined.
 * 
 * Note that the result of this expression is the content in the echo expression, so that for example: <code>echo(2)</code> will
 * both echo the value 2 on the PrintStream and return 2.
 * 
 * @see org.da.expressionj.functions.EchoLogger
 *
 * @version 0.9.2
 */
public class ExprECHO extends AbstractUnaryNumericExpression {
   public ExprECHO() {
   }
   
   @Override
   public Object clone() throws CloneNotSupportedException {
      ExprECHO echo = new ExprECHO();
      echo.setExpression(expr);
      return echo;
   }     

   @Override
   public String getExpressionName() {
      return "echo";
   }

   @Override
   public void setExpression(Expression expr) {
      this.expr = expr;
   }

   @Override
   public Expression getExpression() {
      return expr;
   }

   @Override
   public Object eval() throws ArithmeticException {
      Object o = expr.eval();
      EchoLogger.getInstance().log(o);
      
      return o;
   }

   @Override
   public short getResultStructure() {
      return STRUCT_SCALAR;
   }
}
