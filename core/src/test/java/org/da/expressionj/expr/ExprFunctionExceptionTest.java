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

import org.da.expressionj.model.Equation;
import org.da.expressionj.model.Variable;
import java.util.Map;
import java.lang.reflect.Method;
import org.da.expressionj.functions.FunctionsDefinitions;
import org.da.expressionj.expr.parser.ParseException;
import org.da.expressionj.expr.parser.EquationParser;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;

/** test for Expressions in Functions definition.
 *
 * @version 0.5.1
 */
public class ExprFunctionExceptionTest {
    private static FunctionsDefinitions def = null;

    public ExprFunctionExceptionTest() {
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
            Method method = ExprFunctionExceptionTest.class.getMethod("div0Int", Integer.TYPE);
            def.addFunction("div0Int", this, method);
            method = ExprFunctionExceptionTest.class.getMethod("div0Float", Float.TYPE);
            def.addFunction("div0Float", this, method);
        } catch (NoSuchMethodException ex) {
        } catch (SecurityException ex) {
        }
    }

    @After
    public void tearDown() {
        def.reset();
        def = null;
    }

    public float div0Int(int a) {
        return 1 / a;
    }

    public float div0Float(float a) {
        return 1 / a;
    }

    /**
     * Test of creating a function exception.
     */
    @Test
    public void testFunctionException() {
        System.out.println("testFunctionException");
        try {
            Equation condition = EquationParser.parse("div0Int(a)");
            Map<String, Variable> vars = condition.getVariables();
            assertEquals("Must have 1 variable", 1, vars.size());
            condition.getVariable("a").setValue(0);

            boolean fail = false;
            try {
                Object o = condition.eval();
            } catch (Exception e) {
                fail = true;
                assertTrue(e instanceof ArithmeticException);
                assertEquals("Exception message", e.getMessage(), "Exception in function div0Int (/ by zero)");
            }
            assertTrue("Must have 1 ArithmeticException", fail);
        } catch (ParseException ex) {
            System.out.println(ex.getMessage());
            fail("Fail to parse");
        }
    }

    /**
     * Test of creating a function exception.
     */
    @Test
    public void testFunctionException2() {
        System.out.println("testFunctionException2");
        try {
            Equation condition = EquationParser.parse("div0Float(a)");
            Map<String, Variable> vars = condition.getVariables();
            assertEquals("Must have 1 variable", 1, vars.size());
            condition.getVariable("a").setValue(0);

            boolean fail = false;
            try {
                Object o = condition.eval();
            } catch (Exception e) {
                fail = true;
                assertTrue(e instanceof ArithmeticException);
                assertEquals("Exception message", e.getMessage(), "Exception in function div0Float (returned Infinity)");
            }
            assertTrue("Must have 1 ArithmeticException", fail);
        } catch (ParseException ex) {
            System.out.println(ex.getMessage());
            fail("Fail to parse");
        }
    }
}