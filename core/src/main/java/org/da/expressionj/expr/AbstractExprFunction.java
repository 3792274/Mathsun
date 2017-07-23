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

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.da.expressionj.model.AbstractExpression;
import org.da.expressionj.model.Expression;
import org.da.expressionj.model.MultipleAryExpression;
import org.da.expressionj.model.Variable;

/**
 * Abstract function expression.
 *
 * @version 0.8.5
 */
public abstract class AbstractExprFunction extends AbstractExpression implements MultipleAryExpression {
   protected List<Expression> exprs = new ArrayList();

   @Override
   public void addExpression(Expression expr) throws Exception {
      exprs.add(expr);
   }

   @Override
   public void setExpressions(List<Expression> exprs) {
      this.exprs = exprs;
   }

   @Override
   public List<Expression> getExpressions() {
      return exprs;
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
