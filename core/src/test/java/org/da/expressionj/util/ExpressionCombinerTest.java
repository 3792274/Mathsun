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
package org.da.expressionj.util;

import org.da.expressionj.expr.parser.EquationParser;
import org.da.expressionj.expr.parser.ParseException;
import org.da.expressionj.model.Equation;
import org.da.expressionj.model.Expression;
import org.da.expressionj.model.Variable;
import org.junit.*;

import java.util.Map;

import static org.junit.Assert.*;

/** Unit tests for Expression Combiner.
 *
 * @version 0.8.2
 */
public class ExpressionCombinerTest {

    public ExpressionCombinerTest() {
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
     * Test of combine for Unary Expression.
     */
    @Test
    public void testCombineUnary() {
        System.out.println("combineUnary");
        try {
            Equation condition = EquationParser.parse("sin(a)");
            Map<String, Variable> vars = condition.getVariables();
            assertEquals("Must have 1 variable", 1, vars.size());

            Equation condition2 = EquationParser.parse("a+b");
            Expression expr = condition2.getExpression();

            ExpressionCombiner combiner = new ExpressionCombiner();
            Expression exprResult = combiner.replace(condition, "a", expr);
            ExpressionExporter exporter = new ExpressionExporter();
            String result = exporter.export(exprResult);
            assertEquals("Must be sin(a + b)", "sin(a + b)", result);

            vars = condition.getVariables();
            assertEquals("Must have 2 variables", 2, vars.size());

            Variable var = vars.get("a");
            assertNotNull("Must have a", var);
            var = vars.get("b");
            assertNotNull("Must have b", var);
        } catch (ParseException ex) {
            System.out.println(ex.getMessage());
            fail("Fail to parse");
        }
    }


    /**
     * Test of combine for Unary Expression.
     */
    @Test
    public void testCombineUnary2() {
        System.out.println("combineUnary2");
        try {
            Equation condition = EquationParser.parse("sin(a)");
            Map<String, Variable> vars = condition.getVariables();
            assertEquals("Must have 1 variable", 1, vars.size());

            Equation condition2 = EquationParser.parse("b+c");
            Expression expr = condition2.getExpression();

            ExpressionCombiner combiner = new ExpressionCombiner();
            Expression exprResult = combiner.replace(condition, "a", expr);
            ExpressionExporter exporter = new ExpressionExporter();
            String result = exporter.export(exprResult);
            assertEquals("Must be sin(b + c)", "sin(b + c)", result);

            vars = condition.getVariables();
            assertEquals("Must have 2 variables", 2, vars.size());

            Variable var = vars.get("b");
            assertNotNull("Must have b", var);
            var = vars.get("c");
            assertNotNull("Must have c", var);
        } catch (ParseException ex) {
            System.out.println(ex.getMessage());
            fail("Fail to parse");
        }
    }

    /**
     * Test of combine for Unary Expression.
     */
    @Test
    public void testCombineSingleVariable() {
        System.out.println("combineSingleVariable");
        try {
            Equation condition = EquationParser.parse("a");
            Map<String, Variable> vars = condition.getVariables();
            assertEquals("Must have 1 variable", 1, vars.size());

            Equation condition2 = EquationParser.parse("2");
            Expression expr = condition2.getExpression();

            ExpressionCombiner combiner = new ExpressionCombiner();
            Expression exprResult = combiner.replace(condition, "a", expr);
            ExpressionExporter exporter = new ExpressionExporter();
            String result = exporter.export(exprResult);
            assertEquals("Must be 2", "2", result);

            vars = exprResult.getVariables();
            assertTrue("Must have 0 variables", vars.isEmpty());
        } catch (ParseException ex) {
            System.out.println(ex.getMessage());
            fail("Fail to parse");
        }
    }

    /**
     * Test of combine for Unary Expression.
     */
    @Test
    public void testCombineSingleVariable2() {
        System.out.println("combineSingleVariable2");
        try {
            Equation condition = EquationParser.parse("a");
            Map<String, Variable> vars = condition.getVariables();
            assertEquals("Must have 1 variable", 1, vars.size());

            Equation condition2 = EquationParser.parse("b + c");
            Expression expr = condition2.getExpression();

            ExpressionCombiner combiner = new ExpressionCombiner();
            Expression exprResult = combiner.replace(condition, "a", expr);
            ExpressionExporter exporter = new ExpressionExporter();
            String result = exporter.export(exprResult);
            assertEquals("Must be b + c", "b + c", result);

            vars = exprResult.getVariables();
            assertEquals("Must have 2 variables", 2, vars.size());
        } catch (ParseException ex) {
            System.out.println(ex.getMessage());
            fail("Fail to parse");
        }
    }
    
    /**
     * Test of combine. Check if StackTraceExceptions are avoided if combining with the same expression.
     */
    @Test
    public void testCombineAvoidStackTraceException() {
        System.out.println("testCombineAvoidStackTraceException");
        try {
            Equation condition = EquationParser.parse("a + 1");
            Map<String, Variable> vars = condition.getVariables();
            assertEquals("Must have 1 variable", 1, vars.size());

            Equation condition2 = EquationParser.parse("a");
            Expression expr = condition2.getExpression();

            ExpressionCombiner combiner = new ExpressionCombiner();
            Expression exprResult = combiner.replace(condition, "a", expr);
            ExpressionExporter exporter = new ExpressionExporter();
            String result = exporter.export(exprResult);
            assertEquals("Must be a + 1", "a + 1", result);

            vars = exprResult.getVariables();
            assertEquals("Must have 1 variable", 1, vars.size());
        } catch (ParseException ex) {
            System.out.println(ex.getMessage());
            fail("Fail to parse");
        }
    } 
    
    /**
     * Test of combine for the Add method.
     */
    @Test
    public void testCombineAdd() {
        System.out.println("combineAdd");
        try {
            Equation condition = EquationParser.parse("a");
            Equation condition2 = EquationParser.parse("b + c");

            ExpressionCombiner combiner = new ExpressionCombiner();
            Expression exprResult = combiner.add(condition, condition2);
            ExpressionExporter exporter = new ExpressionExporter();
            String result = exporter.export(exprResult);
            assertEquals("Must be a + b + c", "a + b + c", result);

            Map<String, Variable> vars = exprResult.getVariables();
            assertEquals("Must have 3 variables", 3, vars.size());
        } catch (ParseException ex) {
            System.out.println(ex.getMessage());
            fail("Fail to parse");
        }
    }    
    
    /**
     * Test of combine for the substract method.
     */
    @Test
    public void testCombineSusbtract() {
        System.out.println("combineSusbtract");
        try {
            Equation condition = EquationParser.parse("a");
            Equation condition2 = EquationParser.parse("b + c");

            ExpressionCombiner combiner = new ExpressionCombiner();
            Expression exprResult = combiner.subtract(condition, condition2);
            ExpressionExporter exporter = new ExpressionExporter();
            String result = exporter.export(exprResult);
            assertEquals("Must be a - (b + c)", "a - (b + c)", result);

            Map<String, Variable> vars = exprResult.getVariables();
            assertEquals("Must have 3 variables", 3, vars.size());
        } catch (ParseException ex) {
            System.out.println(ex.getMessage());
            fail("Fail to parse");
        }
    }
    
    /**
     * Test of combine for the multiply method.
     */
    @Test
    public void testCombineMultiply() {
        System.out.println("combineMultiply");
        try {
            Equation condition = EquationParser.parse("a");
            Equation condition2 = EquationParser.parse("b + c");

            ExpressionCombiner combiner = new ExpressionCombiner();
            Expression exprResult = combiner.multiply(condition, condition2);
            ExpressionExporter exporter = new ExpressionExporter();
            String result = exporter.export(exprResult);
            assertEquals("Must be a * (b + c)", "a * (b + c)", result);

            Map<String, Variable> vars = exprResult.getVariables();
            assertEquals("Must have 3 variables", 3, vars.size());
        } catch (ParseException ex) {
            System.out.println(ex.getMessage());
            fail("Fail to parse");
        }
    }    
    
    /**
     * Test of combine for the divide method.
     */
    @Test
    public void testCombineDivide() {
        System.out.println("combineDivide");
        try {
            Equation condition = EquationParser.parse("a");
            Equation condition2 = EquationParser.parse("b + c");

            ExpressionCombiner combiner = new ExpressionCombiner();
            Expression exprResult = combiner.divide(condition, condition2);
            ExpressionExporter exporter = new ExpressionExporter();
            String result = exporter.export(exprResult);
            assertEquals("Must be a / (b + c)", "a / (b + c)", result);

            Map<String, Variable> vars = exprResult.getVariables();
            assertEquals("Must have 3 variables", 3, vars.size());
        } catch (ParseException ex) {
            System.out.println(ex.getMessage());
            fail("Fail to parse");
        }
    }        

}