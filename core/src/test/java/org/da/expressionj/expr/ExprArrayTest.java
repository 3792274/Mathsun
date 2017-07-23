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

/**
 * tests array creation.
 *
 * @since 0.9.2
 */
public class ExprArrayTest {
   public ExprArrayTest() {
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
    * Test of evaluating an array expression.
    */
   @Test
   public void testEval() {
      System.out.println("evalArrayExpression");
      try {
         Equation condition = EquationParser.parse("a = {1,2,3}; return a[1]");
         Map<String, Variable> vars = condition.getVariables();
         assertEquals("Must have 1 variable", 1, vars.size());
         Object result = condition.eval();
         assertTrue("Result must be Integer", result instanceof Integer);
         int value = ((Integer) result).intValue();
         assertEquals("Result must be 2", 2, value);
         Variable a = vars.get("a");
         Object array = a.getValue();
         assertTrue("a must be an array of int", array instanceof int[]);
         int[] arrayI = (int[])array;
         assertEquals("arrayI must have 3 elements", 3, arrayI.length);
         assertEquals("a[0] must be 1", 1, arrayI[0]);
         assertEquals("a[1] must be 2", 2, arrayI[1]);
         assertEquals("a[2] must be 3", 3, arrayI[2]);         
      } catch (ParseException ex) {
         System.out.println(ex.getMessage());
         fail("Fail to parse");
      }
   }
   
   /**
    * Test of evaluating an array expression.
    */
   @Test
   public void testEval2() {
      System.out.println("evalArrayExpression2");
      try {
         Equation condition = EquationParser.parse("a = {1,2,3}; return a[0]");
         Map<String, Variable> vars = condition.getVariables();
         assertEquals("Must have 1 variable", 1, vars.size());
         Object result = condition.eval();
         assertTrue("Result must be Integer", result instanceof Integer);
         int value = ((Integer) result).intValue();
         assertEquals("Result must be 1", 1, value);
      } catch (ParseException ex) {
         System.out.println(ex.getMessage());
         fail("Fail to parse");
      }
   }   
   
   /**
    * Test of evaluating an array expression.
    */
   @Test
   public void testEval3() {
      System.out.println("evalArrayExpression3");
      try {
         Equation condition = EquationParser.parse("int[] a = {1,b*c,3}; return a[1] + a[0]");
         Map<String, Variable> vars = condition.getVariables();
         assertEquals("Must have 2 variables", 2, vars.size());
         condition.getVariable("b").setValue(10);
         condition.getVariable("c").setValue(3);         
         Object result = condition.eval();
         assertTrue("Result must be Integer", result instanceof Integer);
         int value = ((Integer) result).intValue();
         assertEquals("Result must be 31", 31, value);
      } catch (ParseException ex) {
         System.out.println(ex.getMessage());
         fail("Fail to parse");
      }
   }      
}
