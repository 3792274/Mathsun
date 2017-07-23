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

/** UJnit tests for the array affectation.
 *
 * @since 0.9.2
 */
public class ExprArrayAffectTest {
   public ExprArrayAffectTest() {
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
         Equation condition = EquationParser.parse("c[1]=2");
         Map<String, Variable> vars = condition.getVariables();
         assertEquals("Must have 1 variable", 1, vars.size());
         int[] c = new int[2];
         c[0] = 4;
         c[1] = 1;
         condition.getVariable("c").setValue(c);
         Object result = condition.eval();
         assertTrue("Result must be an integer", result instanceof Integer);
         assertEquals("c[1] must be = 2 ", 2, c[1]);
         assertEquals("c[0] must be = 4 ", 4, c[0]);
      } catch (ParseException ex) {
         System.out.println(ex.getMessage());
         fail("Fail to parse");
      }
   }    
   
   /**
    * Test of evaluating an array expression.
    */
   @Test
   public void testEvalArray() {
      System.out.println("evalArrayExpression");
      try {
         Equation condition = EquationParser.parse("int[] a = {1,b*c,3}; a[1] = c; return a[1] + a[0]");
         Map<String, Variable> vars = condition.getVariables();
         assertEquals("Must have 2 variables", 2, vars.size());
         condition.getVariable("b").setValue(10);
         condition.getVariable("c").setValue(3);         
         Object result = condition.eval();
         assertTrue("Result must be Integer", result instanceof Integer);
         int value = ((Integer) result).intValue();
         assertEquals("Result must be 4", 4, value);
      } catch (ParseException ex) {
         System.out.println(ex.getMessage());
         fail("Fail to parse");
      }
   }     
}
