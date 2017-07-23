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

/** Test of ExprNOT.
 *
 * @@version 0.8.8
 */
public class ExprNOTTest {

    public ExprNOTTest() {
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
     * Test of eval method, of class ExprNOT.
     */
    @Test
    public void testEval() {
        System.out.println("evalNOT");
        try {
            Equation condition = EquationParser.parse("! a");
            Map<String, Variable> vars = condition.getVariables();
            assertEquals("Must have 1 variable", 1, vars.size());
            condition.getVariable("a").setValue(false);
            Object result = condition.eval();
            assertTrue("Result must be Boolean", result instanceof Boolean);
            boolean value = ((Boolean)result).booleanValue();
            assertTrue("Result must be true", value);
        } catch (ParseException ex) {
            ex.printStackTrace();
            fail("Fail to parse");
        }
    }

    /**
     * Test of eval method, of class ExprNOT.
     */
    @Test
    public void testEval2() {
        System.out.println("evalNOT2");
        try {
            Equation condition = EquationParser.parse("! (a == 2)");
            Map<String, Variable> vars = condition.getVariables();
            assertEquals("Must have 1 variable", 1, vars.size());
            condition.getVariable("a").setValue(3);
            Object result = condition.eval();
            assertTrue("Result must be Boolean", result instanceof Boolean);
            boolean value = ((Boolean)result).booleanValue();
            assertTrue("Result must be true", value);
            condition.getVariable("a").setValue(2);
            result = condition.eval();
            assertTrue("Result must be Boolean", result instanceof Boolean);
            value = ((Boolean)result).booleanValue();
            assertFalse("Result must be false", value);
        } catch (ParseException ex) {
            System.out.println(ex.getMessage());
            fail("Fail to parse");
        }
    }

    /**
     * Test of eval method, of class ExprNOT.
     */
    @Test
    public void testEval3() {
        System.out.println("evalNOT3");
        try {
            Equation condition = EquationParser.parse("! (a == (b + c * 2))");
            Map<String, Variable> vars = condition.getVariables();
            assertEquals("Must have 3 variables", 3, vars.size());
            condition.getVariable("a").setValue(7);
            condition.getVariable("b").setValue(3);
            condition.getVariable("c").setValue(2);
            Object result = condition.eval();
            assertTrue("Result must be Boolean", result instanceof Boolean);
            boolean value = ((Boolean)result).booleanValue();
            assertTrue("Result must be false", value);
            condition.getVariable("a").setValue(2);
            result = condition.eval();
            assertTrue("Result must be Boolean", result instanceof Boolean);
            value = ((Boolean)result).booleanValue();
            assertTrue("Result must be true", value);
        } catch (ParseException ex) {
            System.out.println(ex.getMessage());
            fail("Fail to parse");
        }
    }
    
    /**
     * Test of eval method, of class ExprNOT.
     */
    @Test
    public void testEval4() {
        System.out.println("evalNOT4");
        try {
            Equation condition = EquationParser.parse("! (a==(b+c*2))");
            Map<String, Variable> vars = condition.getVariables();
            assertEquals("Must have 3 variables", 3, vars.size());
            condition.getVariable("a").setValue(7);
            condition.getVariable("b").setValue(3);
            condition.getVariable("c").setValue(2);
            Object result = condition.eval();
            assertTrue("Result must be Boolean", result instanceof Boolean);
            boolean value = ((Boolean)result).booleanValue();
            assertTrue("Result must be false", value);
            condition.getVariable("a").setValue(2);
            result = condition.eval();
            assertTrue("Result must be Boolean", result instanceof Boolean);
            value = ((Boolean)result).booleanValue();
            assertTrue("Result must be true", value);
        } catch (ParseException ex) {
            System.out.println(ex.getMessage());
            fail("Fail to parse");
        }
    }
}