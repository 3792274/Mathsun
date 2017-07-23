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

import java.util.HashMap;
import java.util.Map;
import org.da.expressionj.model.AbstractExpression;
import org.da.expressionj.model.AryExpression;
import org.da.expressionj.model.Expression;
import org.da.expressionj.model.Variable;

/** An abstract Ary expression.
 *
 * @version 0.8
 */
public abstract class AbstractAryExpression extends AbstractExpression implements AryExpression {
    protected Expression expr1 = null;
    protected Expression expr2 = null;

    @Override
    public void setExpression1(Expression expr1) {
        this.expr1 = expr1;
    }

    @Override
    public void setExpression2(Expression expr2) {
        this.expr2 = expr2;
    }

    @Override
    public Expression getExpression1() {
        return expr1;
    }

    @Override
    public Expression getExpression2() {
        return expr2;
    }

   @Override
    public Map<String, Variable> getVariables() {
        return new HashMap(1);
    }

   @Override
    public Variable getVariable(String varName) {
        return null;
    } 
}
