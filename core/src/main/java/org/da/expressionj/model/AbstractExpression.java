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
package org.da.expressionj.model;

import org.da.expressionj.expr.CodeBlock;

/**  An abstract expression contains <code>eval()</code> methods which cefault to {@link #eval()}.
 *
 * @version 0.9
 */
public abstract class AbstractExpression implements Expression {
   protected CodeBlock block = null;
   
   @Override
   public abstract Object clone() throws CloneNotSupportedException;   
   
   @Override
   public void setParentBlock(CodeBlock block) {
      this.block = block;
   }
   
   @Override
   public CodeBlock getParentBlock() {
      return block;
   }
   
   @Override
    public int evalAsInt() {
      Object value = eval();
      if (value instanceof Integer) {
         return (Integer)value;
      } else if (value instanceof Number) {
         return ((Number)value).intValue();
      } else throw new ArithmeticException("Numeric Expression use non numeric elements");
    }
    
   @Override
    public float evalAsFloat() {
      Object value = eval();
      if (value instanceof Float) {
         return (Float)value;
      } else if (value instanceof Number) {
         return ((Number)value).floatValue();
      } else throw new ArithmeticException("Numeric Expression use non numeric elements");       
    }
    
   @Override
    public boolean evalAsBoolean() {
       Object value = eval();
       if (value instanceof Boolean) {
          return (Boolean)value;
       } else throw new ArithmeticException("Boolean Expression use non boolean elements"); 
    } 
   
}
