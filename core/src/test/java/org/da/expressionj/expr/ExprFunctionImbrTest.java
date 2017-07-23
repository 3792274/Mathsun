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
public class ExprFunctionImbrTest {
    private static FunctionsDefinitions def = null;

    public ExprFunctionImbrTest() {
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
            Method method = ExprFunctionImbrTest.class.getMethod("myTest", Float.TYPE);
            def.addFunction("test", this, method);
            method = ExprFunctionImbrTest.class.getMethod("myTest2", Float.TYPE, Float.TYPE);
            def.addFunction("test2", this, method);
            method = ExprFunctionImbrTest.class.getMethod("myFunction", Float.TYPE);
            def.addFunction("function", this, method);
        } catch (NoSuchMethodException ex) {
        } catch (SecurityException ex) {
        }
    }

    @After
    public void tearDown() {
        def.reset();
        def = null;
    }

    public float myTest(float a) {
        return a + 1;
    }

    public float myTest2(float a, float b) {
        return a + b;
    }

    public float myFunction(float a) {
        return a * 2;
    }

    /**
     * Test of eval.
     */
    @Test
    public void testEval() {
        System.out.println("testEvalFunctionImbrication");
        try {
            Equation condition = EquationParser.parse("test2(a, sin(b))");
            Map<String, Variable> vars = condition.getVariables();
            assertEquals("Must have 2 variables", 2, vars.size());
            condition.getVariable("a").setValue(2);
            condition.getVariable("b").setValue(1);
            Object o = condition.eval();
            assertTrue(o instanceof Float);
            float res = ((Float)o).floatValue();
            assertEquals("Return value must be 2 + sin(1)", (float)(2f + Math.sin(1f)), res, 0.0001f);
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
        System.out.println("testEvalFunctionImbrication2");
        try {
            Equation condition = EquationParser.parse("test2(a, function(b))");
            Map<String, Variable> vars = condition.getVariables();
            assertEquals("Must have 2 variables", 2, vars.size());
            condition.getVariable("a").setValue(2);
            condition.getVariable("b").setValue(1);
            Object o = condition.eval();
            assertTrue(o instanceof Float);
            float res = ((Float)o).floatValue();
            assertEquals("Return value must be 4", 4f, res, 0.0001f);
        } catch (ParseException ex) {
            System.out.println(ex.getMessage());
            fail("Fail to parse");
        }
    }

    /**
     * Test of eval.
     */
    @Test
    public void testEval3() {
        System.out.println("testEvalFunctionImbrication3");
        try {
            Equation condition = EquationParser.parse("test2(a, function(test(b)))");
            Map<String, Variable> vars = condition.getVariables();
            assertEquals("Must have 2 variables", 2, vars.size());
            condition.getVariable("a").setValue(2);
            condition.getVariable("b").setValue(1);
            Object o = condition.eval();
            assertTrue(o instanceof Float);
            float res = ((Float)o).floatValue();
            assertEquals("Return value must be 6", 6f, res, 0.0001f);
        } catch (ParseException ex) {
            System.out.println(ex.getMessage());
            fail("Fail to parse");
        }
    }

    /**
     * Test of eval.
     */
    @Test
    public void testEval4() {
        System.out.println("testEvalFunctionImbrication4");
        try {
            Equation condition = EquationParser.parse("test2(a, function(test(b))) + 2");
            Map<String, Variable> vars = condition.getVariables();
            assertEquals("Must have 2 variables", 2, vars.size());
            condition.getVariable("a").setValue(2);
            condition.getVariable("b").setValue(1);
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
    public void testEval5() {
        System.out.println("testEvalFunctionImbrication5");
        try {
            Equation condition = EquationParser.parse("test(a) + test2(a, function(test(b))) + 2");
            Map<String, Variable> vars = condition.getVariables();
            assertEquals("Must have 2 variables", 2, vars.size());
            condition.getVariable("a").setValue(2);
            condition.getVariable("b").setValue(1);
            Object o = condition.eval();
            assertTrue(o instanceof Float);
            float res = ((Float)o).floatValue();
            assertEquals("Return value must be 11", 11f, res, 0.0001f);
        } catch (ParseException ex) {
            System.out.println(ex.getMessage());
            fail("Fail to parse");
        }
    }    

}