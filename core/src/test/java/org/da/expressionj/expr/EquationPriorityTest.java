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

import org.da.expressionj.expr.parser.EquationParser;
import org.da.expressionj.expr.parser.ParseException;
import org.da.expressionj.model.Equation;
import org.junit.After;
import org.junit.AfterClass;
import static org.junit.Assert.*;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

/**
 * Test of Equation with parenthesis.
 *
 * @@since 0.8.2
 */
public class EquationPriorityTest {
   public EquationPriorityTest() {
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
   public void testWithoutParenthesis() {
      System.out.println("testWithParenthesis");
      try {
         Equation equation = EquationParser.parse("2 * 3 + 4");
         Object result = equation.eval();
         assertTrue("Result must be int", result instanceof Integer);
         assertEquals("Result", 10, result);
      } catch (ParseException ex) {
         System.out.println(ex.getMessage());
         fail("Fail to parse");
      }
   }
   
   @Test
   public void testWithoutParenthesis2() {
      System.out.println("testWithParenthesis2");
      try {
         Equation equation = EquationParser.parse("2 * 3 + 4 * 2");
         Object result = equation.eval();
         assertTrue("Result must be int", result instanceof Integer);
         assertEquals("Result", 14, result);
      } catch (ParseException ex) {
         System.out.println(ex.getMessage());
         fail("Fail to parse");
      }
   }   
}