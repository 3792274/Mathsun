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

/** An affectation expression. The affectation may be of one of the following formats:
 * <ul>
 * <li>&lt;variable&gt; = &lt;expression&gt;: the variable will be a global variable visible by all expressions</li>
 * <li>&lt;type&gt &lt;variable&gt; = &lt;expression&gt;: the variable will be an internal variable visible only by this
 * expression. Note that if an internal variable has the same name as a global, it will shadow the global one only in this expression</li>
 * </ul>
 *
 * Available types are:
 * <ul>
 * <li><code>int</code></li>
 * <li><code>float</code></li>
 * <li><code>boolean</code></li>
 * <li><code>string</code></li>
 * <li><code>int[]</code></li>
 * <li><code>float[]</code></li>
 * <li><code>boolean[]</code></li>
 * <li><code>string[]</code></li>
 * <li><code>var</code>: variable has a {@link org.da.expressionj.model.ExpressionType#TYPE_DYNAMIC} type</li>
 * </ul>
 * 
 * <H1>Example</H1>
 * The following expression <code>c=a+b</code> will return the evaluation of "a+b", and will set the value of the variable 
 * c with the result.
 * 
 * @version 0.9.2
 */
public class ExprAffectation extends AbstractAssignment {
   
   public ExprAffectation() {
   }
   
   @Override
   public Object clone() throws CloneNotSupportedException {
      ExprAffectation assign = new ExprAffectation();
      assign.expr = expr;
      assign.isLocal = isLocal;
      assign.resultVariable = resultVariable;
      assign.block = block;
      return assign;
   }          

   @Override
   public int evalAsInt() {
      int eval = expr.evalAsInt();
      resultVariable.setValueAsInt(eval);
      return eval;
   }

   @Override
   public float evalAsFloat() {
      float eval = expr.evalAsFloat();
      resultVariable.setValueAsFloat(eval);
      return eval;
   }

   @Override
   public boolean evalAsBoolean() {
      boolean eval = expr.evalAsBoolean();
      resultVariable.setValueAsBoolean(eval);
      return eval;
   }

   @Override
   public Object eval() {
      Object eval = expr.eval();
      resultVariable.setValue(eval);
      return eval;
   }

   @Override
   public String getExpressionName() {
      return "affect";
   }
}
