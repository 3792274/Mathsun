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
package org.da.expressionj.model;

import java.util.Map;
import org.da.expressionj.expr.CodeBlock;

/**
 * Repredent any expression that can be evaluated. Expressions can be of several different kinds:
 * <ul>
 * <li>{@link org.da.expressionj.model.AryExpression}: an expression with two predicates, as for example AND</li>
 * <li>{@link org.da.expressionj.model.UnaryExpression}: an expression with one predicate, as for example NOT</li>
 * <li>{@link Variable}: a Variable</li>
 * <li>{@link Constant}: a Constant</li>
 * </ul>
 *
 * @version 0.9.2
 */
public interface Expression extends ExpressionType, Cloneable {
   
   public Object clone() throws CloneNotSupportedException;
   
   /** Set the parent expression block.
    */
   public void setParentBlock(CodeBlock block);
   
   /** Return the parent expression block if the expression is one of several expressions separated by commas. For example,
    * in the following equation: <code>b=a+2; return b</code>, the two "b=a+2" and "return b" have "b=a+2; return b" as their
    * parent block.
    */
   public CodeBlock getParentBlock();

   /** Evaluate the expression as an int. The result of the evaluation will be the same as {@link #eval()}, but the evaluation
    * engine will be able to optimize the computation by knwoing that the result is an int (primitive valeus are cached internally).
    */
   public int evalAsInt();

   /** Evaluate the expression as a float. The result of the evaluation will be the same as {@link #eval()}, but the evaluation
    * engine will be able to optimize the computation by knwoing that the result is a float (primitive valeus are cached internally).
    */   
   public float evalAsFloat();

   /** Evaluate the expression as a boolean. The result of the evaluation will be the same as {@link #eval()}, but the evaluation
    * engine will be able to optimize the computation by knwoing that the result is a boolean (primitive valeus are cached internally).
    */      
   public boolean evalAsBoolean();

   /**
    * Evaluate the expression.
    */
   public Object eval();

   /**
    * Return the type of the result for the expression. The type can be:
    * <ul>
    * <li>{@link #TYPE_INTEGER}: integer</li>
    * <li>{@link #TYPE_FLOAT}: float</li>
    * <li>{@link #TYPE_NUMERIC}: integer or float value. The runtime actual type may depend on the expression arguments</li>
    * <li>{@link #TYPE_STRING}: string</li>
    * <li>{@link #TYPE_DYNAMIC}: dynamic type. The runtime actual type may depend on the expression arguments</li>
    * <li>{@link #TYPE_UNDEF}: undefined type. The default type, characteristic of an error if entountered.</li>
    * </ul>
    */
   public short getResultType();

   /**
    * Return the structure of the result for the expression. The structure can be:
    * <ul>
    * <li>{@link #STRUCT_SCALAR}: a scalar value</li>
    * <li>{@link #STRUCT_ARRAY}: an array value</li>
    * </ul>
    */
   public short getResultStructure();

   /**
    * Return the expression name.
    */
   public String getExpressionName();

   /** Return the variables declared by this expression. Note that this methos will not return any internal variables used by
    * the expression. For example, in the following expression: <code>int c=a+b</code>, only "a" and "b" will be returned.
    */
   public Map<String, Variable> getVariables();

   /** Return a variable declared by the expression.
    */   
   public Variable getVariable(String varName);
}
