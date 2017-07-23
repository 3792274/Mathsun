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

import java.util.ArrayList;
import java.util.List;
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

/** Unit tests for the increment and assign (+=) operator.
 *
 * @since 0.9
 */
public class ExprIncrementAssignTest {
   public ExprIncrementAssignTest() {
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
    * Test of eval method, of class ExprAddAssignment.
    */
   @Test
   public void testEvalIncrementAssign() {
      System.out.println("evalIncrementAssign");
      try {
         Equation condition = EquationParser.parse("a += 3");
         Map<String, Variable> vars = condition.getVariables();
         assertEquals("Must have 1 variable", 1, vars.size());
         condition.getVariable("a").setValue(2);
         Object result = condition.eval();
         assertTrue("Result must be Integer", result instanceof Integer);
         int value = ((Integer) result).intValue();
         assertEquals("Result must be 5", 5, value);
         Variable var = condition.getVariable("a");
         assertEquals("a value must be 5", 5, var.getValue());
      } catch (ParseException ex) {
         System.out.println(ex.getMessage());
         fail("Fail to parse");
      }
   }

   /**
    * Test of eval method, of class ExprAddAssignment.
    */
   @Test
   public void testEvalIncrementAssign2() {
      System.out.println("evalIncrementAssign2");
      try {
         Variable a = new Variable("a", 2);
         List<Variable> vars = new ArrayList();
         vars.add(a);
         Equation condition = EquationParser.parse("int a = 5; a += 3", vars);
         condition.getVariable("a").setValue(2);
         Object result = condition.eval();
         assertTrue("Result must be Integer", result instanceof Integer);
         int value = ((Integer) result).intValue();
         assertEquals("Result must be 8", 8, value);
         Variable var = condition.getVariable("a");
         assertEquals("a value must be 2", 2, var.getValue());
      } catch (ParseException ex) {
         System.out.println(ex.getMessage());
         fail("Fail to parse");
      }
   }

}
