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
package org.da.expressionj.expr;

/** An abstract unary numeric expression.
 *
 * @since 0.8
 */
public abstract class AbstractUnaryNumericExpression extends AbstractUnaryExpression {
   protected short type = TYPE_UNDEF;
   
   @Override
   public short getResultType() {
      if (expr != null) {
         type = expr.getResultType();
      }
      if (type == TYPE_UNDEF) {
         type = TYPE_NUMERIC;
      }
      return type;
   }   
   
   @Override
   public short getResultStructure() {
      return STRUCT_SCALAR;
   }   
}
