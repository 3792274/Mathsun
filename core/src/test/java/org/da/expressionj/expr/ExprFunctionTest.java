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

import java.lang.reflect.Method;
import java.util.Map;
import org.da.expressionj.expr.parser.EquationParser;
import org.da.expressionj.expr.parser.ParseException;
import org.da.expressionj.functions.FunctionsDefinitions;
import org.da.expressionj.model.Equation;
import org.da.expressionj.model.Expression;
import org.da.expressionj.model.Variable;
import org.junit.After;
import org.junit.AfterClass;
import static org.junit.Assert.*;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

/** test for Functions definition.
 *
 * @version 0.8.7
 */
public class ExprFunctionTest {
    private static FunctionsDefinitions def = null;

    public ExprFunctionTest() {
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
            Method method = ExprFunctionTest.class.getMethod("myTest", Integer.TYPE, Integer.TYPE);
            def.addFunction("test", this, method);
            method = ExprFunctionTest.class.getMethod("intFunction", Integer.TYPE);
            def.addFunction("intFunction", this, method);            
            method = ExprFunctionTest.class.getMethod("myTest2", Float.TYPE, Float.TYPE);
            def.addFunction("test2", this, method);
            method = ExprFunctionTest.class.getMethod("myFunction", Float.TYPE);
            def.addFunction("function", this, method);
            method = ExprFunctionTest.class.getMethod("myVarArgFunction", float[].class);
            def.addFunction("varargFunction", this, method);
            method = ExprFunctionTest.class.getMethod("myVarArg2Function", Integer.TYPE, float[].class);
            def.addFunction("vararg2Function", this, method);
            method = ExprFunctionTest.class.getMethod("toStringFunction", Integer.TYPE);
            def.addFunction("toString", this, method);            
        } catch (NoSuchMethodException ex) {
        } catch (SecurityException ex) {
        }
    }

    @After
    public void tearDown() {
        def.reset();
        def = null;
    }

    public int myTest(int a, int b) {
        return a + b;
    }

    public float myTest2(float a, float b) {
        return a + b;
    }

    public float myVarArgFunction(float... a) {
        float result = 0;
        for (int i = 0; i < a.length; i++) {
            result += a[i];
        }
        return result;
    }

    public float myVarArg2Function(int a, float... b) {
        float result = a * 10;
        for (int i = 0; i < b.length; i++) {
            result += b[i];
        }
        return result;
    }

    public float myFunction(float a) {
        return a * 2;
    }
    
    public int intFunction(int a) {
        return a * 2;
    }    
    
    public String toStringFunction(int a) {
        return Integer.toString(a * 2);
    }    

    /**
     * Test of equation parsing
     */
    @Test
    public void testEquation() {
        System.out.println("testExprFunction");
        try {
            Equation condition = EquationParser.parse("test(1, 2)");
            Expression expr = condition.getExpression();
            assertTrue(expr instanceof ExprFunction);
            ExprFunction func = (ExprFunction)expr;
            assertEquals("Must have 2 parameters", 2, func.getExpressions().size());
        } catch (ParseException ex) {
            System.out.println(ex.getMessage());
            fail("Fail to parse");
        }
    }

    /**
     * Test of eval for a dollar variable.
     */
    @Test
    public void testEval() {
        System.out.println("testEvalFunction");
        try {
            Equation condition = EquationParser.parse("test(1, 2)");
            Expression expr = condition.getExpression();
            assertTrue(expr instanceof ExprFunction);
            ExprFunction func = (ExprFunction)expr;
            assertEquals("Must have 2 parameters", 2, func.getExpressions().size());

            Object o = condition.eval();
            assertTrue(o instanceof Integer);
            int res = ((Integer)o).intValue();
            assertEquals("Return value must be 3", 3, res);
        } catch (ParseException ex) {
            System.out.println(ex.getMessage());
            fail("Fail to parse");
        }
    }

    /**
     * Test of eval.
     */
    @Test
    public void testEval2() {
        System.out.println("testEvalFunction2");
        try {
            Equation condition = EquationParser.parse("test(a, b)");
            Expression expr = condition.getExpression();
            Map<String, Variable> vars = condition.getVariables();
            assertEquals("Must have 2 variables", 2, vars.size());
            condition.getVariable("a").setValue(2);
            condition.getVariable("b").setValue(5);
            assertTrue(expr instanceof ExprFunction);
            ExprFunction func = (ExprFunction)expr;
            assertEquals("Must have 2 parameters", 2, func.getExpressions().size());

            Object o = condition.eval();
            assertTrue(o instanceof Integer);
            int res = ((Integer)o).intValue();
            assertEquals("Return value must be 7", 7, res);
        } catch (ParseException ex) {
            System.out.println(ex.getMessage());
            fail("Fail to parse");
        }
    }

    /**
     * Test of eval.
     */
    @Test
    public void testVararg() {
        System.out.println("testEvalVararg");
        try {
            Equation condition = EquationParser.parse("varargFunction(a, b, c)");
            Expression expr = condition.getExpression();
            Map<String, Variable> vars = condition.getVariables();
            assertEquals("Must have 3 variables", 3, vars.size());
            condition.getVariable("a").setValue(2);
            condition.getVariable("b").setValue(5);
            condition.getVariable("c").setValue(1);
            assertTrue(expr instanceof ExprFunction);

            Object o = condition.eval();
            assertTrue(o instanceof Float);
            float res = ((Float)o).floatValue();
            assertEquals("Return value must be 8", 8f, res, 0.0001f);
        } catch (ParseException ex) {
            System.out.println(ex.getMessage());
            fail("Fail to parse");
        }
    }

    /**
     * Test of eval.
     */
    @Test
    public void testVararg2() {
        System.out.println("testEvalVararg2");
        try {
            Equation condition = EquationParser.parse("vararg2Function(a, b, c)");
            Expression expr = condition.getExpression();
            Map<String, Variable> vars = condition.getVariables();
            assertEquals("Must have 3 variables", 3, vars.size());
            condition.getVariable("a").setValue(2);
            condition.getVariable("b").setValue(5);
            condition.getVariable("c").setValue(1);
            assertTrue(expr instanceof ExprFunction);

            Object o = condition.eval();
            assertTrue(o instanceof Float);
            float res = ((Float)o).floatValue();
            assertEquals("Return value must be 26", 26f, res, 0.0001f);
        } catch (ParseException ex) {
            System.out.println(ex.getMessage());
            fail("Fail to parse");
        }
    }
    
    /**
     * Test of eval.
     */
    @Test
    public void testEvalAddFunctions() {
        System.out.println("testEvalAddFunctions");
        try {
            Equation condition = EquationParser.parse("2 + intFunction(a)");
            Map<String, Variable> vars = condition.getVariables();
            assertEquals("Must have 1 variables", 1, vars.size());
            condition.getVariable("a").setValue(2);

            Object o = condition.eval();
            assertTrue(o instanceof Integer);
            int res = ((Integer)o).intValue();
            assertEquals("Return value must be 6", 6, res);
        } catch (ParseException ex) {
            System.out.println(ex.getMessage());
            fail("Fail to parse");
        }
    }           
    
    /**
     * Test of eval.
     */
    @Test
    public void testEvalMultiplyFunctions() {
        System.out.println("testEvalMultiplyFunctions");
        try {
            Equation condition = EquationParser.parse("2 * intFunction(a)");
            Map<String, Variable> vars = condition.getVariables();
            assertEquals("Must have 1 variables", 1, vars.size());
            condition.getVariable("a").setValue(2);

            Object o = condition.eval();
            assertTrue(o instanceof Integer);
            int res = ((Integer)o).intValue();
            assertEquals("Return value must be 8", 8, res);
        } catch (ParseException ex) {
            System.out.println(ex.getMessage());
            fail("Fail to parse");
        }
    }        
    
    /**
     * Test of eval.
     */
    @Test
    public void testEvalAddFunctions2() {
        System.out.println("testEvalAddFunctions2");
        try {
            Equation condition = EquationParser.parse("2 + test(a, b)");
            Map<String, Variable> vars = condition.getVariables();
            assertEquals("Must have 2 variables", 2, vars.size());
            condition.getVariable("a").setValue(2);
            condition.getVariable("b").setValue(5);

            Object o = condition.eval();
            assertTrue(o instanceof Integer);
            int res = ((Integer)o).intValue();
            assertEquals("Return value must be 9", 9, res);
        } catch (ParseException ex) {
            System.out.println(ex.getMessage());
            fail("Fail to parse");
        }
    }    
    
    /**
     * Test of eval method, of class ExprADD, with a Strings and a variable.
     */
    @Test
    public void testEvalAddStringAndFunction() {
        System.out.println("evalADDStringAndFunction");
        try {
            Equation condition = EquationParser.parse("b + toString(a)");
            Map<String, Variable> vars = condition.getVariables();
            assertEquals("Must have 2 variables", 2, vars.size());
            condition.getVariable("a").setValue(1);
            condition.getVariable("b").setValue("S");  
            Object result = condition.eval();
            assertTrue("Result must be String", result instanceof String);
            assertEquals("Result must be S2", "S2", result);
        } catch (ParseException ex) {
            System.out.println(ex.getMessage());
            fail("Fail to parse");
        }
    }    
    
    /**
     * Test of eval method, of class ExprADD, with a Strings and a variable.
     */
    @Test
    public void testEvalAddConstantStringAndFunction() {
        System.out.println("evalADDConstantStringAndFunction");
        try {
            Equation condition = EquationParser.parse("\"S\" + toString(a)");
            Map<String, Variable> vars = condition.getVariables();
            assertEquals("Must have 1 variable", 1, vars.size());
            condition.getVariable("a").setValue(1);            
            Object result = condition.eval();
            assertTrue("Result must be String", result instanceof String);
            assertEquals("Result must be S2", "S2", result);
        } catch (ParseException ex) {
            System.out.println(ex.getMessage());
            fail("Fail to parse");
        }
    }    
}