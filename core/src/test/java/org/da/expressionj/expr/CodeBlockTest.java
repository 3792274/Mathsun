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
import org.da.expressionj.model.Expression;
import org.da.expressionj.model.Variable;
import org.junit.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import static org.junit.Assert.*;

/**
 * test for code blocks.
 *
 * @since 0.9
 */
public class CodeBlockTest {
   public CodeBlockTest() {
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
     * Test of eval method, of class CodeBlock.
     */
    @Test
    public void testEvalAffect() {
        System.out.println("evalAffect");
        try {
            Equation condition = EquationParser.parse("c=a+b;");
            Map<String, Variable> vars = condition.getVariables();
            assertEquals("Must have 3 variables", 3, vars.size());
            condition.getVariable("a").setValue(2);
            condition.getVariable("b").setValue(1);
            Object result = condition.eval();
            assertTrue("Result must be Integer", result instanceof Integer);
            int value = ((Integer)result).intValue();
            assertEquals("Result must be 3", 3, value);
        } catch (ParseException ex) {
            System.out.println(ex.getMessage());
            fail("Fail to parse");
        }
    }

   /**
    * Test of eval method, of class CodeBlock.
    */
   @Test
   public void testEvalOneAffectation() {
      System.out.println("evalOneAffectation");
      try {
         Equation condition = EquationParser.parse("c=a+b;return c;");
         Map<String, Variable> vars = condition.getVariables();
         assertEquals("Must have 3 variables", 3, vars.size());
         condition.getVariable("a").setValue(2);
         condition.getVariable("b").setValue(1);
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
    * Test of eval method, of class CodeBlock.
    */
   @Test
   public void testEvalTwoAffectations() {
      System.out.println("evalTwoAffectations");
      try {
         Equation condition = EquationParser.parse("c=a+b;d=c*2;return d;");
         Map<String, Variable> vars = condition.getVariables();
         assertEquals("Must have 4 variables", 4, vars.size());
         condition.getVariable("a").setValue(2);
         condition.getVariable("b").setValue(1);
         Object result = condition.eval();
         assertTrue("Result must be Integer", result instanceof Integer);
         int value = ((Integer) result).intValue();
         assertEquals("Result must be 6", 6, value);
      } catch (ParseException ex) {
         System.out.println(ex.getMessage());
         fail("Fail to parse");
      }
   }   
   
   /**
    * Test of eval method, of class CodeBlock.
    */
   @Test
   public void testEvalTwoAffectationsA() {
      System.out.println("evalTwoAffectationsA");
      try {
         Equation condition = EquationParser.parse("c=a+b;d=c*2;");
         Map<String, Variable> vars = condition.getVariables();
         assertEquals("Must have 4 variables", 4, vars.size());
         condition.getVariable("a").setValue(2);
         condition.getVariable("b").setValue(1);
         Object result = condition.eval();
         assertTrue("Result must be Integer", result instanceof Integer);
         int value = ((Integer) result).intValue();
         assertEquals("Result must be 6", 6, value);
         Variable var = condition.getVariable("c");
         assertEquals("c must be 3", 3, var.getValue());
         var = condition.getVariable("d");
         assertEquals("d must be 6", 6, var.getValue());
      } catch (ParseException ex) {
         System.out.println(ex.getMessage());
         fail("Fail to parse");
      }
   }      
   
   /**
    * Test of eval method, of class CodeBlock.
    */
   @Test
   public void testEvalTwoAffectations2() {
      System.out.println("evalTwoAffectations2");
      try {
         Equation condition = EquationParser.parse("c=a+b;d=c*2;return c;");
         Map<String, Variable> vars = condition.getVariables();
         assertEquals("Must have 4 variables", 4, vars.size());
         condition.getVariable("a").setValue(2);
         condition.getVariable("b").setValue(1);
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
    * Test of eval method, of class CodeBlock.
    */
   @Test
   public void testEvalTwoAffectations3() {
      System.out.println("evalTwoAffectations3");
      try {
         Equation condition = EquationParser.parse("c=a+b;d=c*cos(2);return d");
         Map<String, Variable> vars = condition.getVariables();
         assertEquals("Must have 4 variables", 4, vars.size());
         condition.getVariable("a").setValue(2);
         condition.getVariable("b").setValue(1);
         Object result = condition.eval();
         assertTrue("Result must be Float", result instanceof Float);
         float value = ((Float) result).floatValue();
         assertEquals("Result must be -1.2484", -1.2484, value, 0.001f);
      } catch (ParseException ex) {
         System.out.println(ex.getMessage());
         fail("Fail to parse");
      }
   } 
   
   /**
    * Test of eval method, of class CodeBlock.
    */
   @Test
   public void testEvalTwoAffectations4() {
      System.out.println("evalTwoAffectations4");
      try {
         Equation condition = EquationParser.parse("c=a+b;d=c*cos(2);return c;");
         Map<String, Variable> vars = condition.getVariables();
         assertEquals("Must have 4 variables", 4, vars.size());
         condition.getVariable("a").setValue(2);
         condition.getVariable("b").setValue(1);
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
    * Test of eval method, of class CodeBlock.
    */
   @Test
   public void testEvalTwoAffectations5() {
      System.out.println("evalTwoAffectations5");
      try {
         Equation condition = EquationParser.parse("c=a+b;d=c+\"A\";return d");
         Map<String, Variable> vars = condition.getVariables();
         assertEquals("Must have 4 variables", 4, vars.size());
         condition.getVariable("a").setValue(2);
         condition.getVariable("b").setValue(1);
         Object result = condition.eval();
         assertTrue("Result must be String", result instanceof String);
         String value = (String)result;
         assertEquals("Result must be 3A", "3A", value);
      } catch (ParseException ex) {
         System.out.println(ex.getMessage());
         fail("Fail to parse");
      }
   }  
   
   /**
    * Test of eval method, of class CodeBlock.
    */
   @Test
   public void testParseLocals() {
      System.out.println("testParseLocals");
      try {
         Equation condition = EquationParser.parse("c=a+b;int d=c+2;return d");
         Map<String, Variable> vars = condition.getVariables();
         assertEquals("Must have 3 variables", 3, vars.size());
         condition.getVariable("a").setValue(2);
         condition.getVariable("b").setValue(1);
         Object result = condition.eval();
         assertTrue("Result must be Integer", result instanceof Integer);
         int value = ((Integer) result).intValue();
         assertEquals("Result must be 5", 5, value);         
      } catch (ParseException ex) {
         System.out.println(ex.getMessage());
         fail("Fail to parse");
      }
   }   
   
   /**
    * Test of eval method, of class CodeBlock.
    */
   @Test
   public void testParseLocals2() {
      System.out.println("testParseLocals2");
      try {
         Equation condition = EquationParser.parse("int c=a+b;return c");
         Map<String, Variable> vars = condition.getVariables();
         assertEquals("Must have 2 variables", 2, vars.size());
      } catch (ParseException ex) {
         System.out.println(ex.getMessage());
         fail("Fail to parse");
      }
   }   
   
   /**
    * Test of eval method, of class CodeBlock.
    */
   @Test
   public void testParseLocals3() {
      System.out.println("testParseLocals3");
      try {
         Equation condition = EquationParser.parse("c=a+b;int d=c+2;return c");
         Map<String, Variable> vars = condition.getVariables();
         assertEquals("Must have 3 variables", 3, vars.size());
         condition.getVariable("a").setValue(2);
         condition.getVariable("b").setValue(1);
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
    * Test of eval method, of class CodeBlock.
    */
   @Test
   public void testParseLocals4() {
      System.out.println("testParseLocals4");
      try {
         Equation condition = EquationParser.parse("int c=a+b;return c");
         Map<String, Variable> vars = condition.getVariables();
         assertEquals("Must have 2 variables", 2, vars.size());
         condition.getVariable("a").setValue(2);
         condition.getVariable("b").setValue(1);
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
    * Test of eval method, of class CodeBlock. Check that a local variable shadows a global variable with the same name in its block.
    */
   @Test
   public void testParseLocals5() {
      System.out.println("testParseLocals5");
      try {
         List<Variable> vars = new ArrayList();
         Variable varA = new Variable("a", Expression.TYPE_INTEGER);
         Variable varB = new Variable("b", Expression.TYPE_INTEGER);
         Variable varC = new Variable("c", Expression.TYPE_INTEGER);
         vars.add(varA);
         vars.add(varB);
         vars.add(varC);
         varA.setValueAsInt(2);
         varB.setValueAsInt(1);
         varC.setValueAsInt(10);
         Equation condition = EquationParser.parse("int c=a+b;return c", vars);
         Object result = condition.eval();
         assertTrue("Result must be Integer", result instanceof Integer);
         int value = ((Integer) result).intValue();
         assertEquals("Result must be 3", 3, value); 
         assertEquals("c value must still be 10", 10, varC.getValue());    
      } catch (ParseException ex) {
         System.out.println(ex.getMessage());
         fail("Fail to parse");
      }
   }   
   
   /**
    * Test of eval method, of class CodeBlock.
    */
   @Test
   public void testEvalComplexReturn() {
      System.out.println("evalComplexReturn");
      try {
         Equation condition = EquationParser.parse("c=a+b; return c*2;");
         Map<String, Variable> vars = condition.getVariables();
         assertEquals("Must have 3 variables", 3, vars.size());
         condition.getVariable("a").setValue(2);
         condition.getVariable("b").setValue(1);
         Object result = condition.eval();
         assertTrue("Result must be Integer", result instanceof Integer);
         int value = ((Integer) result).intValue();
         assertEquals("Result must be 6", 6, value);
         Variable var = condition.getVariable("c");
         assertEquals("c must be 3", 3, var.getValue());
      } catch (ParseException ex) {
         System.out.println(ex.getMessage());
         fail("Fail to parse");
      }
   }      
}
