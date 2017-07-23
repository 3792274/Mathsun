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

/**
 * Test of Equation.
 *
 * @@version 0.8.4
 */
public class EquationTest {
   public EquationTest() {
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

   @Test
   public void testHelloWorld() {
      System.out.println("testHelloWorld");
      try {
         Equation equation = EquationParser.parse("\"Hello \"+a");
         Map<String, Variable> vars = equation.getVariables();
         vars.get("a").setValue("World!");
         Object result = equation.eval();
         assertTrue("Result must be String", result instanceof String);
         assertEquals("Result", "Hello World!", result);
      } catch (ParseException ex) {
         System.out.println(ex.getMessage());
         fail("Fail to parse");
      }
   }

   /**
    * Test of eval for a dollar variable.
    */
   @Test
   public void testEquation() {
      System.out.println("testEquation");
      try {
         List<Variable> varList = new ArrayList();
         varList.add(new Variable("a", 1));
         varList.add(new Variable("b", 2));

         Equation condition = EquationParser.parse("$1+$2", varList);
         Map<String, Variable> vars = condition.getVariables();
         assertEquals("Must have 2 variables", 2, vars.size());
         Object result = condition.eval();
         assertTrue("Result must be Integer", result instanceof Integer);
         int value = ((Integer) result).intValue();
         assertEquals("Result must be 3", 3, value);
      } catch (ParseException ex) {
         System.out.println(ex.getMessage());
         fail("Fail to parse");
      }
   }

   /**
    * Test of eval for a dollar variable.
    */
   @Test
   public void testEquation2() {
      System.out.println("testEquation2");
      try {
         List<Variable> varList = new ArrayList();
         Variable a = new Variable("a", 1);
         Variable b = new Variable("b", 2);
         varList.add(a);
         varList.add(b);

         Equation condition = EquationParser.parse("$1+$2", varList);
         Map<String, Variable> vars = condition.getVariables();
         assertEquals("Must have 2 variables", 2, vars.size());
         Object result = condition.eval();
         assertTrue("Result must be Integer", result instanceof Integer);
         int value = ((Integer) result).intValue();
         assertEquals("Result must be 3", 3, value);

         b.setValue(4);
         result = condition.eval();
         assertTrue("Result must be Integer", result instanceof Integer);
         value = ((Integer) result).intValue();
         assertEquals("Result must be 5", 5, value);
      } catch (ParseException ex) {
         System.out.println(ex.getMessage());
         fail("Fail to parse");
      }
   }

   /**
    * Test of parsing for a dollar variable.
    */
   @Test
   public void testEquation3() {
      System.out.println("testEquation KO");
      boolean ok = true;
      try {
         List<Variable> varList = new ArrayList();
         varList.add(new Variable("a", 1));
         varList.add(new Variable("b", 2));

         EquationParser.parse("$1+$2+$3", varList);
      } catch (ParseException ex) {
         ok = false;
      } catch (Exception ex) {
         System.out.println(ex.getMessage());
         fail("Fail to parse");
      }
      assertFalse("Parsing must fail", ok);
   }

   /**
    * Test of parsing for a dollar variable.
    */
   @Test
   public void testEquation4() {
      System.out.println("testEquation KO");
      boolean ok = true;
      try {
         EquationParser.parse("$1+$2+$3");
      } catch (ParseException ex) {
         ok = false;
      } catch (Exception ex) {
         System.out.println(ex.getMessage());
         fail("Fail to parse");
      }
      assertFalse("Parsing must fail", ok);
   }

   /**
    * Test of parsing for a variable.
    */
   @Test
   public void testEquation5() {
      System.out.println("testEquation KO 2");
      boolean ok = true;
      try {
         List<Variable> varList = new ArrayList();
         varList.add(new Variable("a", 1));
         varList.add(new Variable("b", 2));

         EquationParser.acceptUndefinedVariables(false);
         EquationParser.parse("a + c", varList);
      } catch (ParseException ex) {
         ok = false;
      } catch (Exception ex) {
         System.out.println(ex.getMessage());
         fail("Fail to parse");
      }
      assertFalse("Parsing must fail", ok);
   }
   
   /**
    * Test of parsing for a uniqueconstant enclosed in a parenthesis.
    */
   @Test
   public void testEquation6() {
      System.out.println("testEquation6");
      try {
         EquationParser.acceptUndefinedVariables(true);
         Equation condition = EquationParser.parse("a*(2)");
         Map<String, Variable> vars = condition.getVariables();
         assertEquals("Must have 1 variables", 1, vars.size());
      } catch (ParseException ex) {
         System.out.println(ex.getMessage());
         fail("Fail to parse");
      }
   }
   
   /**
    * Test of parsing for a uniqueconstant enclosed in a parenthesis.
    */
   @Test
   public void testEquation7() {
      System.out.println("testEquation7");
      try {
         EquationParser.acceptUndefinedVariables(true);
         Equation condition = EquationParser.parse("a*(-2)");
         Map<String, Variable> vars = condition.getVariables();
         assertEquals("Must have 1 variables", 1, vars.size());
      } catch (ParseException ex) {
         System.out.println(ex.getMessage());
         fail("Fail to parse");
      }
   }   
}