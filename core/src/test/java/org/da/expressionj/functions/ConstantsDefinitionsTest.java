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
package org.da.expressionj.functions;

import org.da.expressionj.model.Equation;
import org.da.expressionj.expr.parser.EquationParser;
import org.da.expressionj.expr.parser.ParseException;
import org.da.expressionj.model.Expression;
import org.da.expressionj.model.Constant;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;

/** Constants Definition test.
 *
 * @version 0.5
 */
public class ConstantsDefinitionsTest {
    private static ConstantsDefinitions globals = null;

    public ConstantsDefinitionsTest() {
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
        globals.addConstant("DEGREE", new Constant(1, Expression.TYPE_INTEGER));
    }

    @After
    public void tearDown() {
        globals.reset();
        globals = null;
    }

    /**
     * Test of equation for a Constant String.
     */
    @Test
    public void testParseGlobalConstant() {
        System.out.println("testParseGlobalConstant");
        try {
            Equation condition = EquationParser.parse("DEGREE");
            Expression expr = condition.getExpression();
            assertTrue(expr instanceof Constant);
        } catch (ParseException ex) {
            System.out.println(ex.getMessage());
            fail("Fail to parse");
        }
    }

    /**
     * Test of equation for a Constant String.
     */
    @Test
    public void testEvalGlobalConstant() {
        System.out.println("testEvalGlobalConstant");
        try {
            Equation condition = EquationParser.parse("DEGREE + 1");
            Object o = condition.eval();
            assertTrue(o instanceof Integer);
            assertEquals("Must be 2", 2, ((Integer)o).intValue());
        } catch (ParseException ex) {
            System.out.println(ex.getMessage());
            fail("Fail to parse");
        }
    }

}