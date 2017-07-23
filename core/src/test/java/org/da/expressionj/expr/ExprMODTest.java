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

/** Test of ExprMOD.
 *
 * @since 0.8.6
 */
public class ExprMODTest {

    public ExprMODTest() {
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
     * Test of eval method, of class ExprMOD.
     */
    @Test
    public void testEval() {
        System.out.println("evalMOD");
        try {
            EquationParser.acceptUndefinedVariables(true);
            Equation condition = EquationParser.parse("a % 2");
            Map<String, Variable> vars = condition.getVariables();
            assertEquals("Must have 1 variable", 1, vars.size());
            condition.getVariable("a").setValue(3);
            Object result = condition.eval();
            assertTrue("Result must be int", result instanceof Integer);
            int expected = 3 % 2;
            assertEquals("Result must be " + expected, expected, ((Integer)result).intValue());
        } catch (ParseException ex) {
            ex.printStackTrace();
            fail("Fail to parse");
        }
    }
    
    /**
     * Test of eval method, of class ExprMOD.
     */
    @Test
    public void testEvalFloat() {
        System.out.println("evalMODFloat");
        try {
            EquationParser.acceptUndefinedVariables(true);
            Equation condition = EquationParser.parse("a % 2");
            Map<String, Variable> vars = condition.getVariables();
            assertEquals("Must have 1 variable", 1, vars.size());
            condition.getVariable("a").setValue(3f);
            Object result = condition.eval();
            assertTrue("Result must be int", result instanceof Float);
            float expected = 3f % 2;
            assertEquals("Result must be " + expected, expected, ((Float)result).floatValue(), 0.001f);
        } catch (ParseException ex) {
            ex.printStackTrace();
            fail("Fail to parse");
        }
    }    
}