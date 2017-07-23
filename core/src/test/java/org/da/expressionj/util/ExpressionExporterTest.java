/*
 Copyright (c) 2011, 2012 Herve Girod. All rights reserved.
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
package org.da.expressionj.util;

import org.da.expressionj.expr.parser.EquationParser;
import org.da.expressionj.expr.parser.ParseException;
import org.da.expressionj.functions.FunctionsDefinitions;
import org.da.expressionj.model.Equation;
import org.da.expressionj.model.Variable;
import org.junit.*;

import java.lang.reflect.Method;
import java.util.Map;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.fail;

/**
 * Exporter test.
 *
 * @version 0.10
 */
public class ExpressionExporterTest {
   private static FunctionsDefinitions def = null;

   public ExpressionExporterTest() {
   }

   @BeforeClass
   public static void setUpClass() throws Exception {
   }

   @AfterClass
   public static void tearDownClass() throws Exception {
   }

   @Before
   public void setUp() {
      try {
         def = FunctionsDefinitions.getInstance();
         Method method = ExpressionExporterTest.class.getMethod("myTest2", Float.TYPE, Float.TYPE);
         def.addFunction("test2", this, method);
      } catch (NoSuchMethodException ex) {
      } catch (SecurityException ex) {
      }
   }

   @After
   public void tearDown() {
   }

   public float myTest2(float a, float b) {
      return a + b;
   }

   /**
    * Test of export for Unary Expression.
    */
   @Test
   public void testExportUnary() {
      System.out.println("exportUnary");
      try {
         Equation condition = EquationParser.parse("sin(a)");
         Map<String, Variable> vars = condition.getVariables();
         assertEquals("Must have 1 variable", 1, vars.size());

         ExpressionExporter exporter = new ExpressionExporter();
         String result = exporter.export(condition);
         assertEquals("Must be sin(a)", "sin(a)", result);
      } catch (ParseException ex) {
         System.out.println(ex.getMessage());
         fail("Fail to parse");
      }
   }

   /**
    * Test of export for Unary Expression.
    */
   @Test
   public void testExportUnary2() {
      System.out.println("exportUnary2");
      try {
         Equation condition = EquationParser.parse("! a");
         Map<String, Variable> vars = condition.getVariables();
         assertEquals("Must have 1 variables", 1, vars.size());

         ExpressionExporter exporter = new ExpressionExporter();
         String result = exporter.export(condition);
         assertEquals("Must be ! a", "! a", result);
      } catch (ParseException ex) {
         System.out.println(ex.getMessage());
         fail("Fail to parse");
      }
   }

   /**
    * Test of export for Ary Expression.
    */
   @Test
   public void testExportUnary3() {
      System.out.println("exportUnary3");
      try {
         Equation condition = EquationParser.parse("! (a || b)");
         Map<String, Variable> vars = condition.getVariables();
         assertEquals("Must have 2 variables", 2, vars.size());

         ExpressionExporter exporter = new ExpressionExporter();
         String result = exporter.export(condition);
         assertEquals("Must be ! (a || b)", "! (a || b)", result);
      } catch (ParseException ex) {
         System.out.println(ex.getMessage());
         fail("Fail to parse");
      }
   }

   /**
    * Test of export for Ary Expression.
    */
   @Test
   public void testExportAry() {
      System.out.println("exportAry");
      try {
         Equation condition = EquationParser.parse("a + 1");
         Map<String, Variable> vars = condition.getVariables();
         assertEquals("Must have 1 variable", 1, vars.size());

         ExpressionExporter exporter = new ExpressionExporter();
         String result = exporter.export(condition);
         assertEquals("Must be a + 1", "a + 1", result);
      } catch (ParseException ex) {
         System.out.println(ex.getMessage());
         fail("Fail to parse");
      }
   }

   /**
    * Test of export for Unary Expression.
    */
   @Test
   public void testExportAry2() {
      System.out.println("exportAry2");
      try {
         Equation condition = EquationParser.parse("a+1+b");
         Map<String, Variable> vars = condition.getVariables();
         assertEquals("Must have 2 variables", 2, vars.size());

         ExpressionExporter exporter = new ExpressionExporter();
         String result = exporter.export(condition);
         assertEquals("Must be a + 1 + b", "a + 1 + b", result);
      } catch (ParseException ex) {
         System.out.println(ex.getMessage());
         fail("Fail to parse");
      }
   }

   /**
    * Test of export for Ary Expression.
    */
   @Test
   public void testExportAry3() {
      System.out.println("exportAry3");
      try {
         Equation condition = EquationParser.parse("a+1+b+c");
         Map<String, Variable> vars = condition.getVariables();
         assertEquals("Must have 3 variables", 3, vars.size());

         ExpressionExporter exporter = new ExpressionExporter();
         String result = exporter.export(condition);
         assertEquals("Must be a + 1 + b + c", "a + 1 + b + c", result);
      } catch (ParseException ex) {
         System.out.println(ex.getMessage());
         fail("Fail to parse");
      }
   }

   /**
    * Test of export for Ary Expression.
    */
   @Test
   public void testExportAry4() {
      System.out.println("exportAry4");
      try {
         Equation condition = EquationParser.parse("a+b-c");
         Map<String, Variable> vars = condition.getVariables();
         assertEquals("Must have 3 variables", 3, vars.size());

         ExpressionExporter exporter = new ExpressionExporter();
         String result = exporter.export(condition);
         assertEquals("Must be a + b - c", "a + b - c", result);
      } catch (ParseException ex) {
         System.out.println(ex.getMessage());
         fail("Fail to parse");
      }
   }

   /**
    * Test of export for Ary Expression.
    */
   @Test
   public void testExportAry5() {
      System.out.println("exportAry5");
      try {
         Equation condition = EquationParser.parse("(a+1+b)*c");
         Map<String, Variable> vars = condition.getVariables();
         assertEquals("Must have 3 variables", 3, vars.size());

         ExpressionExporter exporter = new ExpressionExporter();
         String result = exporter.export(condition);
         assertEquals("Must be (a + 1 + b) * c", "(a + 1 + b) * c", result);
      } catch (ParseException ex) {
         System.out.println(ex.getMessage());
         fail("Fail to parse");
      }
   }

   /**
    * Test of export for complex Expression.
    */
   @Test
   public void testExportImbr() {
      System.out.println("exportImbr");
      try {
         Equation condition = EquationParser.parse("test2(a, sin(b))");
         Map<String, Variable> vars = condition.getVariables();
         assertEquals("Must have 2 variables", 2, vars.size());

         ExpressionExporter exporter = new ExpressionExporter();
         String result = exporter.export(condition);
         assertEquals("Must be test2(a, sin(b))", "test2(a,sin(b))", result);
      } catch (ParseException ex) {
         System.out.println(ex.getMessage());
         fail("Fail to parse");
      }
   }

   /**
    * Test of export for substraction Expression.
    */
   @Test
   public void testExportSub() {
      System.out.println("exportSub");
      try {
         Equation condition = EquationParser.parse("a - (b + c)");
         Map<String, Variable> vars = condition.getVariables();
         assertEquals("Must have 3 variables", 3, vars.size());

         ExpressionExporter exporter = new ExpressionExporter();
         String result = exporter.export(condition);
         assertEquals("Must be a - (b + c)", "a - (b + c)", result);
      } catch (ParseException ex) {
         System.out.println(ex.getMessage());
         fail("Fail to parse");
      }
   }

   /**
    * Test of export for addition Expression.
    */
   @Test
   public void testExportAdd() {
      System.out.println("exportAdd");
      try {
         Equation condition = EquationParser.parse("a + (b + c)");
         Map<String, Variable> vars = condition.getVariables();
         assertEquals("Must have 3 variables", 3, vars.size());

         ExpressionExporter exporter = new ExpressionExporter();
         String result = exporter.export(condition);
         assertEquals("Must be a + b + c", "a + b + c", result);
      } catch (ParseException ex) {
         System.out.println(ex.getMessage());
         fail("Fail to parse");
      }
   }

   /**
    * Test of export for multiplication Expression.
    */
   @Test
   public void testExportMult() {
      System.out.println("exportMult");
      try {
         Equation condition = EquationParser.parse("a * (b + c)");
         Map<String, Variable> vars = condition.getVariables();
         assertEquals("Must have 3 variables", 3, vars.size());

         ExpressionExporter exporter = new ExpressionExporter();
         String result = exporter.export(condition);
         assertEquals("Must be a * (b + c)", "a * (b + c)", result);
      } catch (ParseException ex) {
         System.out.println(ex.getMessage());
         fail("Fail to parse");
      }
   }

   /**
    * Test of export for division Expression.
    */
   @Test
   public void testExportDiv() {
      System.out.println("exportDiv");
      try {
         Equation condition = EquationParser.parse("a / (b + c)");
         Map<String, Variable> vars = condition.getVariables();
         assertEquals("Must have 3 variables", 3, vars.size());

         ExpressionExporter exporter = new ExpressionExporter();
         String result = exporter.export(condition);
         assertEquals("Must be a / (b + c)", "a / (b + c)", result);
      } catch (ParseException ex) {
         System.out.println(ex.getMessage());
         fail("Fail to parse");
      }
   }

   /**
    * Test of export for Sub expression, if the first expresion is null. The case is "-e".
    */
   @Test
   public void testExportSubWithNull() {
      System.out.println("exportSubWithNull");
      try {
         Equation condition = EquationParser.parse("-c");
         Map<String, Variable> vars = condition.getVariables();
         assertEquals("Must have 1 variable", 1, vars.size());

         ExpressionExporter exporter = new ExpressionExporter();
         String result = exporter.export(condition);
         assertEquals("Must be -c", "-c", result);
      } catch (ParseException ex) {
         System.out.println(ex.getMessage());
         fail("Fail to parse");
      }
   }

   /**
    * Test of export for Add expression, if the first expresion is null. The case is "+e".
    */
   @Test
   public void testExportAddWithNull() {
      System.out.println("exportAddWithNull");
      try {
         Equation condition = EquationParser.parse("+c");
         Map<String, Variable> vars = condition.getVariables();
         assertEquals("Must have 1 variable", 1, vars.size());

         ExpressionExporter exporter = new ExpressionExporter();
         String result = exporter.export(condition);
         assertEquals("Must be c", "c", result);
      } catch (ParseException ex) {
         System.out.println(ex.getMessage());
         fail("Fail to parse");
      }
   }

   /**
    * Test of export for Add expression, if the first expresion is null, and the second one an expression
    * The case is for example"+sin(e)".
    */
   @Test
   public void testExportAddExpressionsWithNull() {
      System.out.println("exportAddExpressionsWithNull");
      try {
         Equation condition = EquationParser.parse("+sin(c)");
         Map<String, Variable> vars = condition.getVariables();
         assertEquals("Must have 1 variable", 1, vars.size());

         ExpressionExporter exporter = new ExpressionExporter();
         String result = exporter.export(condition);
         assertEquals("Must be +sin(c)", "sin(c)", result);
      } catch (ParseException ex) {
         System.out.println(ex.getMessage());
         fail("Fail to parse");
      }
   }

   /**
    * Test of export for Sub expression, if the first expresion is null, and the second one an expression
    * The case is for example"-sin(e)".
    */
   @Test
   public void testExportSubExpressionsWithNull() {
      System.out.println("exportSubExpressionsWithNull");
      try {
         Equation condition = EquationParser.parse("-sin(c)");
         Map<String, Variable> vars = condition.getVariables();
         assertEquals("Must have 1 variable", 1, vars.size());

         ExpressionExporter exporter = new ExpressionExporter();
         String result = exporter.export(condition);
         assertEquals("Must be -sin(c)", "-sin(c)", result);
      } catch (ParseException ex) {
         System.out.println(ex.getMessage());
         fail("Fail to parse");
      }
   }

   /**
    * Test of export for Sub expression, if the first expresion is null. The case is "-e".
    */
   @Test
   public void testExportUniqueVariable() {
      System.out.println("exportUniqueVariable");
      try {
         Equation condition = EquationParser.parse("+c");
         Map<String, Variable> vars = condition.getVariables();
         assertEquals("Must have 1 variable", 1, vars.size());

         ExpressionExporter exporter = new ExpressionExporter();
         String result = exporter.export(condition);
         assertEquals("Must be c", "c", result);
      } catch (ParseException ex) {
         System.out.println(ex.getMessage());
         fail("Fail to parse");
      }
   }
   
   /**
    * Test of export for an unique variable in a parenthesis.
    */
   @Test
   public void testExportUniqueVariableInParenthesis() {
      System.out.println("exportUniqueVariable");
      try {
         Equation condition = EquationParser.parse("(a)*(b)");
         Map<String, Variable> vars = condition.getVariables();
         assertEquals("Must have 2 variables", 2, vars.size());

         ExpressionExporter exporter = new ExpressionExporter();
         String result = exporter.export(condition);
         assertEquals("Must be a * b", "a * b", result);
      } catch (ParseException ex) {
         System.out.println(ex.getMessage());
         fail("Fail to parse");
      }
   }   
   
   /**
    * Test of export for an unique variable in a parenthesis. The test checks the a + (-b) case, which must be
    * exported as a - b.
    */
   @Test
   public void testExportSeveralAryVariables() {
      System.out.println("exportSeveralAryVariables");
      try {
         Equation condition = EquationParser.parse("a+(-b)");
         Map<String, Variable> vars = condition.getVariables();
         assertEquals("Must have 2 variables", 2, vars.size());

         ExpressionExporter exporter = new ExpressionExporter();
         String result = exporter.export(condition);
         assertEquals("Must be a - b", "a - b", result);
      } catch (ParseException ex) {
         System.out.println(ex.getMessage());
         fail("Fail to parse");
      }
   }     
   
   /**
    * Test of export for an unique variable in a parenthesis. The test checks the a + (-b) case, which must be
    * exported as a - b.
    */
   @Test
   public void testExportModuloExpression() {
      System.out.println("exportModuloExpression");
      try {
         Equation condition = EquationParser.parse("a % b");
         Map<String, Variable> vars = condition.getVariables();
         assertEquals("Must have 2 variables", 2, vars.size());

         ExpressionExporter exporter = new ExpressionExporter();
         String result = exporter.export(condition);
         assertEquals("Must be a % b", "a % b", result);
      } catch (ParseException ex) {
         System.out.println(ex.getMessage());
         fail("Fail to parse");
      }
   } 
   
   /**
    * Test of export for a code block.
    */
   @Test
   public void testExportCodeBlock() {
      System.out.println("exportCodeBlock");
      try {
         Equation condition = EquationParser.parse("c=a+b;d=c*cos(2);return d");
         ExpressionExporter.setNewLinesForBlocks(false);
         ExpressionExporter exporter = new ExpressionExporter();
         String result = exporter.export(condition);
         assertEquals("Must be c = a + b; d = c * cos(2); return d;", "c = a + b; d = c * cos(2); return d;", result);
      } catch (ParseException ex) {
         System.out.println(ex.getMessage());
         fail("Fail to parse");
      }
   }    
   
   /**
    * Test of export for a code block.
    */
   @Test
   public void testExportCodeBlock2() {
      System.out.println("exportCodeBlock2");
      try {
         Equation condition = EquationParser.parse("c=a+b;d=c*cos(2);return d");
         ExpressionExporter.setNewLinesForBlocks(true);
         ExpressionExporter exporter = new ExpressionExporter();
         String eol = System.getProperty("line.separator");
         StringBuilder expectedResult = new StringBuilder();
         expectedResult.append("c = a + b;").append(eol);
         expectedResult.append("d = c * cos(2);").append(eol);
         expectedResult.append("return d;");
         String result = exporter.export(condition);
         assertEquals("Must be c = a + b; d = c * cos(2); return d;", expectedResult.toString(), result);
      } catch (ParseException ex) {
         System.out.println(ex.getMessage());
         fail("Fail to parse");
      }
   }    
   
   /**
    * Test of export for a code block.
    */
   @Test
   public void testExportCodeBlock3() {
      System.out.println("exportCodeBlock3");
      try {
         Equation condition = EquationParser.parse("c=a+b;int d=c+2;return c");
         ExpressionExporter.setNewLinesForBlocks(true);
         ExpressionExporter exporter = new ExpressionExporter();
         String eol = System.getProperty("line.separator");
         StringBuilder expectedResult = new StringBuilder();
         expectedResult.append("c = a + b;").append(eol);
         expectedResult.append("int d = c + 2;").append(eol);
         expectedResult.append("return c;");
         String result = exporter.export(condition);
         assertEquals("Must be c = a + b; int d = c + 2; return c;", expectedResult.toString(), result);
      } catch (ParseException ex) {
         System.out.println(ex.getMessage());
         fail("Fail to parse");
      }
   }     
   
   /**
    * Test of export for a CHOICE.
    */
   @Test
   public void testExportCHOICE() {
      System.out.println("exportCHOICE");
      try {
         Equation condition = EquationParser.parse("if (c) {return 2} else {return 3};");
         ExpressionExporter.setNewLinesForBlocks(false);
         ExpressionExporter exporter = new ExpressionExporter();
         String expectedResult = "if (c) {return 2} else {return 3}";
         String result = exporter.export(condition);
         assertEquals("Must be "+expectedResult, expectedResult.toString(), result);
      } catch (ParseException ex) {
         System.out.println(ex.getMessage());
         fail("Fail to parse");
      }
   }   
   
   /**
    * Test of export for a CHOICE.
    */
   @Test
   public void testExportCHOICE2() {
      System.out.println("exportCHOICE2");
      try {
         Equation condition = EquationParser.parse("if (c) {return 2} else {return 3};");
         ExpressionExporter exporter = new ExpressionExporter();
         ExpressionExporter.setNewLinesForBlocks(true);
         String eol = System.getProperty("line.separator");
         StringBuilder expectedResult = new StringBuilder();
         expectedResult.append("if (c) {").append(eol);
         expectedResult.append("   return 2").append(eol);
         expectedResult.append("} else {").append(eol);
         expectedResult.append("   return 3").append(eol);
         expectedResult.append("}"); 
         String result = exporter.export(condition);
         assertEquals("Must be "+expectedResult, expectedResult.toString(), result);
      } catch (ParseException ex) {
         System.out.println(ex.getMessage());
         fail("Fail to parse");
      }
   }    
   
   /**
    * Test of export for a CHOICE.
    */
   @Test
   public void testExportCHOICE3() {
      System.out.println("exportCHOICE");
      try {
         Equation condition = EquationParser.parse("if (c) {return 2};");
         ExpressionExporter.setNewLinesForBlocks(false);
         ExpressionExporter exporter = new ExpressionExporter();
         String expectedResult = "if (c) {return 2}";
         String result = exporter.export(condition);
         assertEquals("Must be "+expectedResult, expectedResult.toString(), result);
      } catch (ParseException ex) {
         System.out.println(ex.getMessage());
         fail("Fail to parse");
      }
   }    
   
   /**
    * Test of export for a WHILE.
    */
   @Test
   public void testExportWHILE() {
      System.out.println("exportWHILE");
      try {
         Equation condition = EquationParser.parse("int c = 0;while (c < 10) {c = c + 1}; return c;");
         ExpressionExporter.setNewLinesForBlocks(false);
         ExpressionExporter exporter = new ExpressionExporter();
         String expectedResult = "int c = 0; while (c < 10) {c = c + 1}; return c;";
         String result = exporter.export(condition);
         assertEquals("Must be "+expectedResult, expectedResult, result);
      } catch (ParseException ex) {
         System.out.println(ex.getMessage());
         fail("Fail to parse");
      }
   } 
   
   /**
    * Test of export for a WHILE with break.
    */
   @Test
   public void testExportWHILE2() {
      System.out.println("exportWHILE2");
      try {
         Equation condition = EquationParser.parse("int c = 0;while (c < 10) {c = c + 1; if (c == 3) {break}}; return c;");
         ExpressionExporter.setNewLinesForBlocks(false);
         ExpressionExporter exporter = new ExpressionExporter();
         String expectedResult = "int c = 0; while (c < 10) {c = c + 1; if (c == 3) {break};}; return c;";
         String result = exporter.export(condition);
         assertEquals("Must be "+expectedResult, expectedResult, result);
      } catch (ParseException ex) {
         System.out.println(ex.getMessage());
         fail("Fail to parse");
      }
   }    
   
   /**
    * Test of export for a increment.
    */
   @Test
   public void testExportIncrement() {
      System.out.println("exportIncrement");
      try {
         Equation condition = EquationParser.parse("int a = 4; a++");
         ExpressionExporter.setNewLinesForBlocks(false);
         ExpressionExporter exporter = new ExpressionExporter();
         String expectedResult = "int a = 4; return a++;";
         String result = exporter.export(condition);
         assertEquals("Must be "+expectedResult, expectedResult, result);
      } catch (ParseException ex) {
         System.out.println(ex.getMessage());
         fail("Fail to parse");
      }
   }  
   
   /**
    * Test of export for a decrement.
    */
   @Test
   public void testExportDecrement() {
      System.out.println("exportDecrement");
      try {
         Equation condition = EquationParser.parse("int a = 4; a--");
         ExpressionExporter.setNewLinesForBlocks(false);
         ExpressionExporter exporter = new ExpressionExporter();
         String expectedResult = "int a = 4; return a--;";
         String result = exporter.export(condition);
         assertEquals("Must be "+expectedResult, expectedResult, result);
      } catch (ParseException ex) {
         System.out.println(ex.getMessage());
         fail("Fail to parse");
      }
   }   
   
   /**
    * Test of export for an add assignment.
    */
   @Test
   public void testExportAddAssignment() {
      System.out.println("exportAddAssignment");
      try {
         Equation condition = EquationParser.parse("int a = 4; a += 2; return a;");
         ExpressionExporter.setNewLinesForBlocks(false);
         ExpressionExporter exporter = new ExpressionExporter();
         String expectedResult = "int a = 4; a += 2; return a;";
         String result = exporter.export(condition);
         assertEquals("Must be "+expectedResult, expectedResult, result);
      } catch (ParseException ex) {
         System.out.println(ex.getMessage());
         fail("Fail to parse");
      }
   }   
   
   /**
    * Test of export for an subtraction assignment.
    */
   @Test
   public void testExportSubAssignment() {
      System.out.println("exportSubAssignment");
      try {
         Equation condition = EquationParser.parse("int a = 4; a -= 2; return a;");
         ExpressionExporter.setNewLinesForBlocks(false);
         ExpressionExporter exporter = new ExpressionExporter();
         String expectedResult = "int a = 4; a -= 2; return a;";
         String result = exporter.export(condition);
         assertEquals("Must be "+expectedResult, expectedResult, result);
      } catch (ParseException ex) {
         System.out.println(ex.getMessage());
         fail("Fail to parse");
      }
   }    
   
   /**
    * Test of export for an multiplication assignment.
    */
   @Test
   public void testExportMultAssignment() {
      System.out.println("exportMultAssignment");
      try {
         Equation condition = EquationParser.parse("int a = 4; a *= 2; return a;");
         ExpressionExporter.setNewLinesForBlocks(false);
         ExpressionExporter exporter = new ExpressionExporter();
         String expectedResult = "int a = 4; a *= 2; return a;";
         String result = exporter.export(condition);
         assertEquals("Must be "+expectedResult, expectedResult, result);
      } catch (ParseException ex) {
         System.out.println(ex.getMessage());
         fail("Fail to parse");
      }
   }    
   
   /**
    * Test of export for an division assignment.
    */
   @Test
   public void testExportDivAssignment() {
      System.out.println("exportDivAssignment");
      try {
         Equation condition = EquationParser.parse("int a = 4; a /= 2; return a;");
         ExpressionExporter.setNewLinesForBlocks(false);
         ExpressionExporter exporter = new ExpressionExporter();
         String expectedResult = "int a = 4; a /= 2; return a;";
         String result = exporter.export(condition);
         assertEquals("Must be "+expectedResult, expectedResult, result);
      } catch (ParseException ex) {
         System.out.println(ex.getMessage());
         fail("Fail to parse");
      }
   }    
   
   /**
    * Test of export for an expression like a * (-2).
    */
   @Test
   public void testExportWithNegativeMultiplication() {
      System.out.println("exportWithNegativeMultiplication");
      try {
         Equation condition = EquationParser.parse("a * (-2)");
         ExpressionExporter exporter = new ExpressionExporter();
         String result = exporter.export(condition);
         assertEquals("Must be a * (-2)", "a * (-2)", result);
      } catch (ParseException ex) {
         System.out.println(ex.getMessage());
         fail("Fail to parse");
      }
   }  
   
   /**
    * Test of export for an expression like a + (-2).
    */
   @Test
   public void testExportWithNegativeAdd() {
      System.out.println("exportWithNegativeAdd");
      try {
         Equation condition = EquationParser.parse("a + (-2)");
         ExpressionExporter exporter = new ExpressionExporter();
         String result = exporter.export(condition);
         assertEquals("Must be a - 2", "a - 2", result);
      } catch (ParseException ex) {
         System.out.println(ex.getMessage());
         fail("Fail to parse");
      }
   }   
   
   /**
    * Test of export for an expression like a + (-2).
    */
   @Test
   public void testExportArrayIndex() {
      System.out.println("exportArrayIndex");
      try {
         Equation condition = EquationParser.parse("return a[i]");
         ExpressionExporter exporter = new ExpressionExporter();
         String result = exporter.export(condition);
         assertEquals("Must be a[i]", "a[i]", result);
      } catch (ParseException ex) {
         System.out.println(ex.getMessage());
         fail("Fail to parse");
      }
   }  
   
   /**
    * Test of export for Array expression.
    */
   @Test
   public void testExportArrayIndex1() {
      System.out.println("exportIndex1");
      try {
         Equation condition = EquationParser.parse("return a[2]");
         Map<String, Variable> vars = condition.getVariables();
         assertEquals("Must have 1 variable", 1, vars.size());

         ExpressionExporter exporter = new ExpressionExporter();
         String result = exporter.export(condition);
         assertEquals("Must be a[2]", "a[2]", result);
      } catch (ParseException ex) {
         System.out.println(ex.getMessage());
         fail("Fail to parse");
      }
   }   
   
   /**
    * Test of export for an expression like a = {1, 2, 3}.
    */
   @Test
   public void testExportArray() {
      System.out.println("exportArray");
      try {
         Equation condition = EquationParser.parse("a = {1, 2, 3}");
         ExpressionExporter exporter = new ExpressionExporter();
         String result = exporter.export(condition);
         assertEquals("Must be a = {1, 2, 3}", "a = {1, 2, 3}", result);
      } catch (ParseException ex) {
         System.out.println(ex.getMessage());
         fail("Fail to parse");
      }
   }    
   
   /**
    * Test of export for an expression like a = {1, 2, 3}.
    */
   @Test
   public void testExportArrayAffect() {
      System.out.println("exportArrayAffect");
      try {
         Equation condition = EquationParser.parse("c[1]=2");
         ExpressionExporter exporter = new ExpressionExporter();
         String result = exporter.export(condition);
         assertEquals("Must be c[1] = 2", "c[1] = 2", result);
      } catch (ParseException ex) {
         System.out.println(ex.getMessage());
         fail("Fail to parse");
      }
   }    
}