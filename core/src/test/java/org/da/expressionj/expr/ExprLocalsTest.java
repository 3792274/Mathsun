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
 * Unit tests for the locals variabels in expressions.
 *
 * @since 0.9
 */
public class ExprLocalsTest {
   public ExprLocalsTest() {
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
    * Test of local variables.
    */
   @Test
   public void testEvalLocalsInBlocks() {
      System.out.println("evalLocalsInBlocks");
      try {
         String newLine = System.getProperty("line.separator");
         StringBuilder buf = new StringBuilder();
         buf.append("if (!c) {return 2}").append(newLine);
         buf.append("else if (b == 2) {").append(newLine);
         buf.append(" int d = 1;").append(newLine);
         buf.append(" if (a == 3) {d = d *10; return d} else {return d}").append(newLine);
         buf.append("} else {return 3};");
         Equation condition = EquationParser.parse(buf.toString());
         Map<String, Variable> vars = condition.getVariables();
         assertEquals("Must have 3 variables", 3, vars.size());
         condition.getVariable("c").setValue(false);
         condition.getVariable("b").setValue(1);
         condition.getVariable("d").setValue(4);
         condition.getVariable("a").setValue(4);
         Object result = condition.eval();
         assertTrue("Result must be Integer", result instanceof Integer);
         int value = ((Integer) result).intValue();
         assertEquals("Result must be 2", 2, value);

         condition.getVariable("c").setValue(true);
         result = condition.eval();
         assertTrue("Result must be Integer", result instanceof Integer);
         value = ((Integer) result).intValue();
         assertEquals("Result must be 3", 3, value);

         condition.getVariable("b").setValue(2);
         result = condition.eval();
         assertTrue("Result must be Integer", result instanceof Integer);
         value = ((Integer) result).intValue();
         assertEquals("Result must be 1", 1, value);

         condition.getVariable("a").setValue(3);
         result = condition.eval();
         assertTrue("Result must be Integer", result instanceof Integer);
         value = ((Integer) result).intValue();
         assertEquals("Result must be 10", 10, value);
      } catch (ParseException ex) {
         System.out.println(ex.getMessage());
         fail("Fail to parse");
      }
   }

   /**
    * Test of local variables.
    */
   @Test
   public void testEvalLocalsInBlocks2() {
      System.out.println("evalLocalsInBlocks2");
      try {
         String newLine = System.getProperty("line.separator");
         StringBuilder buf = new StringBuilder();
         buf.append("if (!c) {return 2}").append(newLine);
         buf.append("else {").append(newLine);
         buf.append("  int d = 5;").append(newLine);
         buf.append("  int e = 1;").append(newLine);
         buf.append("  while (d < 8) {").append(newLine);
         buf.append("    d++;").append(newLine);      
         buf.append("    if (d < 7) {").append(newLine);     
         buf.append("      e++").append(newLine);                    
         buf.append("    }");
         buf.append("  };");           
         buf.append("  return e");           
         buf.append("}");
         Equation condition = EquationParser.parse(buf.toString());
         Map<String, Variable> vars = condition.getVariables();
         assertEquals("Must have 1 variable", 1, vars.size());
         condition.getVariable("c").setValue(true);
         
         Object result = condition.eval();
         assertTrue("Result must be Integer", result instanceof Integer);
         int value = ((Integer) result).intValue();
         assertEquals("Result must be 2", 2, value);
      } catch (ParseException ex) {
         System.out.println(ex.getMessage());
         fail("Fail to parse");
      }
   }
}
