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

/** Unit tests or the ExprWHILE expression.
 *
 * @version 0.10
 */
public class ExprWHILETest {
   public ExprWHILETest() {
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
    * Test of eval method, of class ExprWHILE.
    */
   @Test
   public void testEvalWHILESimple() {
      System.out.println("evalWHILESimple");
      try {
         Equation condition = EquationParser.parse("int c = 0;while(c < 10) {c = c + 1}; return c;");
         Map<String, Variable> vars = condition.getVariables();
         assertEquals("Must have 0 variables", 0, vars.size());
         Object result = condition.eval();
         assertTrue("Result must be Integer", result instanceof Integer);
         int value = ((Integer) result).intValue();
         assertEquals("Result must be 10", 10, value);
      } catch (ParseException ex) {
         System.out.println(ex.getMessage());
         fail("Fail to parse");
      }
   }
   
   /**
    * Test of eval method, of class ExprWHILE.
    */
   @Test
   public void testEvalWHILESimple2() {
      System.out.println("evalWHILESimple2");
      try {
         Equation condition = EquationParser.parse("int c = 0;int d = 0;while(c < 10) {d = d + a; c = c + 1}; return d;");
         Map<String, Variable> vars = condition.getVariables();
         assertEquals("Must have 1 variables", 1, vars.size());
         vars.get("a").setValue(2);
         Object result = condition.eval();
         assertTrue("Result must be Integer", result instanceof Integer);
         int value = ((Integer) result).intValue();
         assertEquals("Result must be 20", 20, value);
      } catch (ParseException ex) {
         System.out.println(ex.getMessage());
         fail("Fail to parse");
      }
   }
   
   /**
    * Test of eval method, of class ExprWHILE.
    */
   @Test
   public void testEvalWHILEBreak() {
      System.out.println("evalWHILEWithBreak");
      try {
         Equation condition = EquationParser.parse("int c = 0;while(c < 10) {c = c + 1; if (c == 3) {break} }; return c;");
         Map<String, Variable> vars = condition.getVariables();
         assertEquals("Must have 0 variables", 0, vars.size());
         Object result = condition.eval();
         assertTrue("Result must be Integer", result instanceof Integer);
         int value = ((Integer) result).intValue();
         assertEquals("Result must be 3", 3, value);
      } catch (ParseException ex) {
         System.out.println(ex.getMessage());
         fail("Fail to parse");
      }
   }   
}   
