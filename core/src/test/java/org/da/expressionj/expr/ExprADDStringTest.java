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

/** Test of ExprADD with Strings.
 *
 * @since 0.8.7
 */
public class ExprADDStringTest {

    public ExprADDStringTest() {
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
     * Test of eval method, of class ExprADD, with 2 Strings.
     */
    @Test
    public void testEvalAdd2Strings() {
        System.out.println("evalADD2Strings");
        try {
            Equation condition = EquationParser.parse("\"a\"+\"b\"");
            Object result = condition.eval();
            assertTrue("Result must be String", result instanceof String);
            assertEquals("Result must be ab", "ab", result);
        } catch (ParseException ex) {
            System.out.println(ex.getMessage());
            fail("Fail to parse");
        }
    }
    
    /**
     * Test of eval method, of class ExprADD, with a Strings and a variable.
     */
    @Test
    public void testEvalAddStringAndVariable() {
        System.out.println("evalADDStringAndVariable");
        try {
            Equation condition = EquationParser.parse("\"S\"+a");
            Map<String, Variable> vars = condition.getVariables();
            assertEquals("Must have 1 variable", 1, vars.size());
            condition.getVariable("a").setValue(1);            
            Object result = condition.eval();
            assertTrue("Result must be String", result instanceof String);
            assertEquals("Result must be S1", "S1", result);
        } catch (ParseException ex) {
            System.out.println(ex.getMessage());
            fail("Fail to parse");
        }
    }    
    
    /**
     * Test of eval method, of class ExprADD, with a Strings and a variable.
     */
    @Test
    public void testEvalAddStringAndVariable2() {
        System.out.println("evalADDStringAndVariable2");
        try {
            Equation condition = EquationParser.parse("a + \"S\"");
            Map<String, Variable> vars = condition.getVariables();
            assertEquals("Must have 1 variable", 1, vars.size());
            condition.getVariable("a").setValue(1);            
            Object result = condition.eval();
            assertTrue("Result must be String", result instanceof String);
            assertEquals("Result must be 1S", "1S", result);
        } catch (ParseException ex) {
            System.out.println(ex.getMessage());
            fail("Fail to parse");
        }
    }     
}