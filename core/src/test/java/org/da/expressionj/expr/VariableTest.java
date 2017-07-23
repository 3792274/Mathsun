/*
    Copyright (c) 2010, Dassault Aviation. All rights reserved.
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

    Please contact Dassault Aviation, 9 Rond-Point des Champs Elysees, 75008 Paris,
    France if you need additional information.
    Alternatively if you have any questions about this project, you can visit
    the project website at the project page on http://expressionj.sourceforge.net
*/

package org.da.expressionj.expr;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import org.da.expressionj.expr.parser.EquationParser;
import org.da.expressionj.expr.parser.ParseException;
import org.da.expressionj.model.Equation;
import org.da.expressionj.model.Expression;
import org.da.expressionj.model.Variable;
import org.junit.After;
import org.junit.AfterClass;
import static org.junit.Assert.*;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

/** Test for Variables.
 *
 * @version 0.8.8
 */
public class VariableTest {

    public VariableTest() {
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
     * Test of parse for a litteral numeric constant.
     */
    @Test
    public void testParseVariable() {
        System.out.println("testParseVariable");
        try {
            List<Variable> varList = new ArrayList();
            Variable var = new Variable("a", 2);
            varList.add(var);

            Equation condition = EquationParser.parse("a", varList);
            Map<String, Variable> vars = condition.getVariables();
            assertEquals("Must have one variable", 1, vars.size());
            Variable _var = vars.get("a");
            assertNotNull("a must exist", _var);
            assertTrue("a must be the defined variable", var == _var);
        } catch (ParseException ex) {
            System.out.println(ex.getMessage());
            fail("Fail to parse");
        }
    }

    /**
     * Test of eval for a litteral numeric constant.
     */
    @Test
    public void testEvalVariable() {
        System.out.println("testEvalVariable");
        try {
            List<Variable> varList = new ArrayList();
            varList.add(new Variable("a", 2));

            Equation condition = EquationParser.parse("a", varList);
            Object o = condition.eval();
            assertTrue(o instanceof Integer);
            Integer res = (Integer)o;
            assertEquals("Must be equals to 2", 2, res.intValue());
        } catch (ParseException ex) {
            System.out.println(ex.getMessage());
            fail("Fail to parse");
        }
    }

    /**
     * Test of cration for a variable.
     */
    @Test
    public void testCreateVariable() {
        System.out.println("testCreateVariable");
        Variable var = new Variable("a");
        assertEquals("Must be dynamic", Expression.TYPE_DYNAMIC, var.getType());
        var.setValue(1);
        assertEquals("Must be Numeric", Expression.TYPE_INTEGER, var.getResultType());
    }

    /**
     * Test of of cration for a variable.
     */
    @Test
    public void testCreateVariable2() {
        System.out.println("testCreateVariable2");
        Variable var = new Variable("a", 2);
        assertEquals("Must be dynamic", Expression.TYPE_DYNAMIC, var.getType());
        assertEquals("Must be Numeric", Expression.TYPE_INTEGER, var.getResultType());
    }

    /**
     * Test of of cration for a variable.
     */
    @Test
    public void testCreateVariable3() {
        System.out.println("testCreateVariable3");
        Variable var = new Variable("a", Expression.TYPE_NUMERIC);
        assertEquals("Must be Numeric", Expression.TYPE_NUMERIC, var.getType());
    }

    /**
     * Test of eval for a litteral numeric constant.
     */
    @Test
    public void testEvalVariableNumeric() {
        System.out.println("testEvalVariableNumeric");
        try {
            List<Variable> varList = new ArrayList();
            varList.add(new Variable("a", Expression.TYPE_INTEGER));

            Equation condition = EquationParser.parse("a + 1", varList);
            Object o = condition.eval();
            assertTrue(o instanceof Integer);
            Integer res = (Integer)o;
            assertEquals("Must be equals to 1", 1, res.intValue());
        } catch (ParseException ex) {
            System.out.println(ex.getMessage());
            fail("Fail to parse");
         } catch (ArithmeticException ex) {
            System.out.println(ex.getMessage());
            fail("Fail to eval");
        }
    }

    /**
     * Test of eval for a litteral numeric constant.
     */
    @Test
    public void testEvalVariableNull() {
        System.out.println("testEvalVariableNull");
        try {
            List<Variable> varList = new ArrayList();
            varList.add(new Variable("a"));

            Equation condition = EquationParser.parse("a + 1", varList);
            boolean ok = true;
            try {
                Object o = condition.eval();
            } catch (ArithmeticException ex) {
                ok = false;
            }
            assertFalse("Must have an arithmetic Exception", ok);
        } catch (ParseException ex) {
            System.out.println(ex.getMessage());
            fail("Fail to parse");
        }
    }

    /**
     * Test that variables given to the parser are kept as is.
     */
    @Test
    public void testKeepVariable() {
        System.out.println("testKeepVariable");
        try {
            List<Variable> varList = new ArrayList();
            Variable var = new Variable("a", 2);
            varList.add(var);

            Equation condition = EquationParser.parse("sin(a)", varList);
            Map<String, Variable> vars = condition.getVariables();
            assertEquals("Must have one variable", 1, vars.size());
            Variable _var = vars.get("a");
            assertNotNull("a must exist", _var);
            assertTrue("a must be the defined variable", var == _var);
        } catch (ParseException ex) {
            System.out.println(ex.getMessage());
            fail("Fail to parse");
        }
    }

}