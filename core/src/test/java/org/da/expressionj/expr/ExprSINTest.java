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
 * Test of ExprSIN.
 *
 * @version 0.9.1
 */
public class ExprSINTest {
   public ExprSINTest() {
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
    * Test of eval method, of class ExprSIN.
    */
   @Test
   public void testEval() {
      System.out.println("evalSIN");
      try {
         Equation condition = EquationParser.parse("sin(a)");
         Map<String, Variable> vars = condition.getVariables();
         assertEquals("Must have 1 variable", 1, vars.size());
         condition.getVariable("a").setValue(2f);
         Object result = condition.eval();
         assertTrue("Result must be Float", result instanceof Float);
         float value = ((Float) result).floatValue();
         assertEquals("Result must be 0.90929742", 0.90929742, value, 0.00001f);
      } catch (ParseException ex) {
         System.out.println(ex.getMessage());
         fail("Fail to parse");
      }
   }

   /**
    * Test of eval method, of class ExprSIN.
    */
   @Test
   public void testEval2() {
      System.out.println("evalSIN2");
      try {
         Equation condition = EquationParser.parse("sin(a+b)");
         Map<String, Variable> vars = condition.getVariables();
         assertEquals("Must have 2 variables", 2, vars.size());
         condition.getVariable("a").setValue(2f);
         condition.getVariable("b").setValue(3f);
         Object result = condition.eval();
         assertTrue("Result must be Float", result instanceof Float);
         float value = ((Float) result).floatValue();
         assertEquals("Result must be -0.958924274", -0.958924274, value, 0.00001f);
      } catch (ParseException ex) {
         System.out.println(ex.getMessage());
         fail("Fail to parse");
      }
   }

   /**
    * Test of eval method, of class ExprSIN.
    */
   @Test
   public void testEval3() {
      System.out.println("evalSIN3");
      try {
         Equation condition = EquationParser.parse("sin a");
         Map<String, Variable> vars = condition.getVariables();
         assertEquals("Must have 1 variable", 1, vars.size());
         condition.getVariable("a").setValue(2f);
         Object result = condition.eval();
         assertTrue("Result must be Float", result instanceof Float);
         float value = ((Float) result).floatValue();
         assertEquals("Result must be 0.90929742", 0.90929742, value, 0.00001f);
      } catch (ParseException ex) {
         System.out.println(ex.getMessage());
         fail("Fail to parse");
      }
   }
}