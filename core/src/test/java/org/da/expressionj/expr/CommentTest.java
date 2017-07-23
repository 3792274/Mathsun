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
import org.da.expressionj.model.Constant;
import org.da.expressionj.model.Equation;
import org.da.expressionj.model.Expression;
import org.da.expressionj.model.Variable;
import org.junit.*;

import java.util.Map;

import static org.junit.Assert.*;

/**
 * Test of comments in expressions.
 *
 * @since 0.9.1
 */
public class CommentTest {
   public CommentTest() {
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
    * Test of simple comment.
    */
   @Test
   public void testComment() {
      System.out.println("testComment");
      try {
         Equation condition = EquationParser.parse("/* this is a comment */2");
         Expression expr = condition.getExpression();
         assertTrue(expr instanceof Constant);
         Object result = condition.eval();
         assertTrue("Result must be Integer", result instanceof Integer);
         int value = ((Integer) result).intValue();
         assertEquals("Result must be 2", 2, value);
      } catch (ParseException ex) {
         System.out.println(ex.getMessage());
         fail("Fail to parse");
      }
   }

   /**
    * Test of simple comment.
    */
   @Test
   public void testComment2() {
      System.out.println("testComment2");
      try {
         Equation condition = EquationParser.parse("a++;/* this is a comment */a+2");
         Map<String, Variable> vars = condition.getVariables();
         assertEquals("Must have 1 variable", 1, vars.size());
         condition.getVariable("a").setValue(4);
         Object result = condition.eval();
         assertTrue("Result must be Integer", result instanceof Integer);
         int value = ((Integer) result).intValue();
         assertEquals("Result must be 7", 7, value);
         Object valueO = condition.getVariable("a").getValue();
         assertEquals("a value must be 5", 5, valueO);
      } catch (ParseException ex) {
         System.out.println(ex.getMessage());
         fail("Fail to parse");
      }
   }
   
   /**
    * Test of local variables.
    */
   @Test
   public void testComplexComment() {
      System.out.println("evalComplexComment");
      try {
         String newLine = System.getProperty("line.separator");
         StringBuilder buf = new StringBuilder();
         buf.append("if (!c) {return 2}").append(newLine);
         buf.append("else {").append(newLine);
         buf.append("  int d = 5;").append(newLine);
         buf.append("  int e = 1;").append(newLine);
         buf.append("  /* one comment */").append(newLine);
         buf.append("  while (d < 8) {").append(newLine);
         buf.append("    d++;").append(newLine);    
         buf.append("  /* another comment */").append(newLine);
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