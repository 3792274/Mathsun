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

import java.util.Map;
import org.da.expressionj.model.Expression;
import org.da.expressionj.model.ExpressionType;
import org.da.expressionj.model.Variable;

/** The break expression.
 * 
 * @since 0.10
 */
public class ExprBREAK implements Expression {
   protected CodeBlock block = null;

   public ExprBREAK() {
   }

   @Override
   public Object clone() throws CloneNotSupportedException {
      ExprBREAK _break = new ExprBREAK();
      _break.block = block;
      return _break;
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
      return null;
   }

   private void checkForExprWHILE() {
      CodeBlock _block = block;
      while (_block != null) {
         if (_block.getExprWHILE() != null) {
            _block.getExprWHILE().setBreak();
         }
         _block = _block.getParentBlock();
      }
   }

   @Override
   public Object eval() {
      checkForExprWHILE();
      return null;
   }

   @Override
   public Map<String, Variable> getVariables() {
      return null;
   }

   @Override
   public String getExpressionName() {
      return "break";
   }

   @Override
   public Variable getVariable(String varName) {
      return null;
   }

   @Override
   public int evalAsInt() {
      return 0;
   }

   @Override
   public float evalAsFloat() {
      checkForExprWHILE();
      return 0;
   }

   @Override
   public boolean evalAsBoolean() {
      checkForExprWHILE();
      return false;
   }

   @Override
   public short getResultType() {
      return ExpressionType.TYPE_UNDEF;
   }

   @Override
   public short getResultStructure() {
      return ExpressionType.TYPE_UNDEF;
   }
}
