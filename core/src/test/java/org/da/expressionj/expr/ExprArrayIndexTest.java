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
import org.da.expressionj.functions.ConstantsDefinitions;
import org.da.expressionj.model.Constant;
import org.da.expressionj.model.Equation;
import org.da.expressionj.model.Variable;
import org.junit.After;
import org.junit.AfterClass;
import static org.junit.Assert.*;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

/** test of arrays expressions.
 *
 * @version 0.9.2
 */
public class ExprArrayIndexTest {
    private static ConstantsDefinitions globals = null;

    public ExprArrayIndexTest() {
    }

    @BeforeClass
    public static void setUpClass() throws Exception {
    }

    @AfterClass
    public static void tearDownClass() throws Exception {
    }

    @Before
    public void setUp() {
        globals = ConstantsDefinitions.getInstance();
        String[] weather = new String[3];
        weather[0] = "SUNNY";
        weather[1] = "RAINY";
        weather[2] = "SNOWY";
        globals.addConstant("WEATHER", new Constant(weather));
    }

    @After
    public void tearDown() {
        globals.reset();
        globals = null;
    }

    /**
     * Test of evaluating an array expression with a constant index.
     */
    @Test
    public void testEval() {
        System.out.println("evalArrayExpression");
        try {
            Equation condition = EquationParser.parse("a[2]");
            Map<String, Variable> vars = condition.getVariables();
            assertEquals("Must have 1 variable", 1, vars.size());
            int[] array = new int[3];
            array[0] = 1;
            array[1] = 2;
            array[2] = 3;
            condition.getVariable("a").setValue(array);
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
     * Test of evaluating an array expression with a variable index.
     */
    @Test
    public void testEval2() {
        System.out.println("evalArrayExpression2");
        try {
            Equation condition = EquationParser.parse("a[b]");
            Map<String, Variable> vars = condition.getVariables();
            assertEquals("Must have 2 variable", 2, vars.size());
            int[] array = new int[3];
            array[0] = 1;
            array[1] = 2;
            array[2] = 3;
            condition.getVariable("b").setValue(1);
            condition.getVariable("a").setValue(array);
            Object result = condition.eval();
            assertTrue("Result must be Integer", result instanceof Integer);
            int value = ((Integer)result).intValue();
            assertEquals("Result must be 2", 2, value);
        } catch (ParseException ex) {
            System.out.println(ex.getMessage());
            fail("Fail to parse");
        }
    }

    /**
     * Test of evaluating an array expression with a variable index.
     */
    @Test
    public void testEval3() {
        System.out.println("evalArrayExpression2");
        try {
            Equation condition = EquationParser.parse("a[b]");
            Map<String, Variable> vars = condition.getVariables();
            assertEquals("Must have 2 variable", 2, vars.size());
            String[] array = new String[3];
            array[0] = "HELLO";
            array[1] = "BRIGHT";
            array[2] = "WORLD";
            condition.getVariable("b").setValue(1);
            condition.getVariable("a").setValue(array);
            Object result = condition.eval();
            assertTrue("Result must be Integer", result instanceof String);
            assertEquals("Result must be BRIGHT", "BRIGHT", result);
        } catch (ParseException ex) {
            System.out.println(ex.getMessage());
            fail("Fail to parse");
        }
    }

    /**
     * Test of evaluating an array constant.
     */
    @Test
    public void testEvalConstant() {
        System.out.println("evalArrayConstant");
        try {
            Equation condition = EquationParser.parse("WEATHER[b]");
            Map<String, Variable> vars = condition.getVariables();
            assertEquals("Must have 1 variable", 1, vars.size());
            condition.getVariable("b").setValue(1);
            Object result = condition.eval();
            assertTrue("Result must be Integer", result instanceof String);
            assertEquals("Result must be RAINY", "RAINY", result);
        } catch (ParseException ex) {
            System.out.println(ex.getMessage());
            fail("Fail to parse");
        }
    }
    
    /**
     * Test of parsing an array with an expression as index.
     */
    @Test
    public void testParseEquationIndex() {
        System.out.println("parseEquationIndex");
        try {
            Equation condition = EquationParser.parse("WEATHER[b + 1]");
            Map<String, Variable> vars = condition.getVariables();
            assertEquals("Must have 1 variable", 1, vars.size());
        } catch (ParseException ex) {
            System.out.println(ex.getMessage());
            fail("Fail to parse");
        }
    }    
    
    /**
     * Test of evaluating an array with an expression as index.
     */
    @Test
    public void testEvalEquationIndex() {
        System.out.println("evalEquationIndex");
        try {
            Equation condition = EquationParser.parse("WEATHER[b + 1]");
            Map<String, Variable> vars = condition.getVariables();
            assertEquals("Must have 1 variable", 1, vars.size());
            condition.getVariable("b").setValue(1);
            Object result = condition.eval();
            assertTrue("Result must be Integer", result instanceof String);
            assertEquals("Result must be SNOWY", "SNOWY", result);            
        } catch (ParseException ex) {
            System.out.println(ex.getMessage());
            fail("Fail to parse");
        }
    }
}