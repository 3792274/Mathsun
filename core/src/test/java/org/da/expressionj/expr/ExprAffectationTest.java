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
import org.da.expressionj.expr.parser.EquationParser;
import org.da.expressionj.expr.parser.ParseException;
import org.da.expressionj.model.Equation;
import org.da.expressionj.model.Variable;
import org.junit.After;
import org.junit.AfterClass;
import static org.junit.Assert.*;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

/** Unit tests for the ExprAffectation class.
 *
 * @since 0.99
 */
public class ExprAffectationTest {
   public ExprAffectationTest() {
   }

   @BeforeClass
   public static void setUpClass() throws Exception {
   }

   @AfterClass
   public static void tearDownClass() throws Exception {
   }
   
   @Before
   public void setUp() {
   }
   
   @After
   public void tearDown() {
   }
   
   /**
    * Test of eval method, of class ExprAffectation.
    */
   @Test
   public void testEvalAffectation() {
      System.out.println("evalAffectation");
      try {
         Equation condition = EquationParser.parse("c=a+b");
         Map<String, Variable> vars = condition.getVariables();
         assertEquals("Must have 3 variables", 3, vars.size());
         condition.getVariable("a").setValue(2);
         condition.getVariable("b").setValue(1);
         Object result = condition.eval();
         assertTrue("Result must be Integer", result instanceof Integer);
         int value = ((Integer) result).intValue();
         assertEquals("Result must be 3", 3, value);
         Variable var = condition.getVariable("c");
         assertEquals("c value must be 3", 3, var.getValue());
      } catch (ParseException ex) {
         System.out.println(ex.getMessage());
         fail("Fail to parse");
      }
   } 
}
