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
import org.da.expressionj.model.Constant;
import org.da.expressionj.model.Equation;
import org.da.expressionj.model.Variable;
import org.junit.*;

import java.util.Map;

import static org.junit.Assert.*;

/** Tests of Expression Analyser.
 *
 * @version 0.8
 */
public class ExpressionAnalyserTest {

    public ExpressionAnalyserTest() {
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
     * Test of GetVariables for Unary Expression.
     */
    @Test
    public void testGetVariablesUnary() {
        System.out.println("GetVariables Unary");
        try {
            Equation condition = EquationParser.parse("sin(a)");
            Map<String, Variable> vars = condition.getVariables();
            assertEquals("Must have 1 variable", 1, vars.size());

            ExpressionAnalyser analyser = new ExpressionAnalyser();
            Map<String, Variable> newvars = analyser.getVariables(condition);
            assertEquals("Must have 1 variable", 1, newvars.size());
            Variable var = newvars.get("a");
            assertNotNull("Must have a", var);
        } catch (ParseException ex) {
            System.out.println(ex.getMessage());
            fail("Fail to parse");
        }
    }

    /**
     * Test of GetVariables for Unary Expression.
     */
    @Test
    public void testGetVariablesUnary2() {
        System.out.println("GetVariables Unary2");
        try {
            Equation condition = EquationParser.parse("! (a || b)");
            Map<String, Variable> vars = condition.getVariables();
            assertEquals("Must have 2 variables", 2, vars.size());

            ExpressionAnalyser analyser = new ExpressionAnalyser();
            Map<String, Variable> newvars = analyser.getVariables(condition);
            assertEquals("Must have 2 variables", 2, newvars.size());
            Variable var = newvars.get("a");
            assertNotNull("Must have a", var);
            var = newvars.get("b");
            assertNotNull("Must have b", var);
        } catch (ParseException ex) {
            System.out.println(ex.getMessage());
            fail("Fail to parse");
        }
    }
    
    /**
     * Test of isConstant for Unary Expression.
     */
    @Test
    public void testIsConstant() {
        System.out.println("isConstant");
        try {
            Equation condition = EquationParser.parse("a");
            ExpressionAnalyser analyser = new ExpressionAnalyser();
            assertFalse("Must not be a constant", analyser.isConstant(condition));
            assertNull("Constant must be null", analyser.asConstant(condition));
        } catch (ParseException ex) {
            System.out.println(ex.getMessage());
            fail("Fail to parse");
        }
    }    

    /**
     * Test of isConstant for Unary Expression.
     */
    @Test
    public void testisConstant2() {
        System.out.println("isConstant2");
        try {
            Equation condition = EquationParser.parse("2");
            ExpressionAnalyser analyser = new ExpressionAnalyser();
            assertTrue("Must be a constant", analyser.isConstant(condition));
            Constant constant = analyser.asConstant(condition);
            assertNotNull("Constant must not be null", constant);
            Object value = constant.getValue();
            assertEquals("Constant must be 2", 2, value);
        } catch (ParseException ex) {
            System.out.println(ex.getMessage());
            fail("Fail to parse");
        }
    }    
    
    /**
     * Test of isConstant for Unary Expression.
     */
    @Test
    public void testIsVariable() {
        System.out.println("isVariable");
        try {
            Equation condition = EquationParser.parse("a");
            ExpressionAnalyser analyser = new ExpressionAnalyser();
            assertTrue("Must be a variable", analyser.isVariable(condition));
            Variable variable = analyser.asVariable(condition);
            assertNotNull("Variable must not be null", variable);
            assertEquals("Variable name", "a", variable.getName());
        } catch (ParseException ex) {
            System.out.println(ex.getMessage());
            fail("Fail to parse");
        }
    }      
}