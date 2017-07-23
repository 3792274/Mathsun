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

/** Unit Tests for the echo function.
 *
 * @version 0.9.2
 */
public class ExprECHOTest {
   public ExprECHOTest() {
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
     * Test of eval method, of class ExprECHO.
     */
    @Test
    public void testEval() {
        System.out.println("ExprECHO");
        try {
            EquationParser.acceptUndefinedVariables(true);
            Equation condition = EquationParser.parse("echo(\"this is a test \" + a)");
            Map<String, Variable> vars = condition.getVariables();
            assertEquals("Must have 1 variable", 1, vars.size());
            condition.getVariable("a").setValue(3);
            Object result = condition.eval();
            assertTrue("Result must be String", result instanceof String);
            assertEquals("Result must be \"this is a test 3\"", "this is a test 3", result); 
        } catch (ParseException ex) {
            ex.printStackTrace();
            fail("Fail to parse");
        }
    }   
    
    /**
     * Test of eval method, of class ExprECHO.
     */
    @Test
    public void testEval2() {
        System.out.println("ExprECHO2");
        try {
            EquationParser.acceptUndefinedVariables(true);
            Equation condition = EquationParser.parse("echo(\"this is a test=\" + a)");
            Map<String, Variable> vars = condition.getVariables();
            assertEquals("Must have 1 variable", 1, vars.size());
            condition.getVariable("a").setValue(3);
            Object result = condition.eval();
            assertTrue("Result must be String", result instanceof String);
            assertEquals("Result must be \"this is a test=3\"", "this is a test=3", result); 
        } catch (ParseException ex) {
            ex.printStackTrace();
            fail("Fail to parse");
        }
    }     
    
    /**
     * Test of eval method, of class ExprECHO.
     */
    @Test
    public void testEval3() {
        System.out.println("ExprECHO3");
        try {
            EquationParser.acceptUndefinedVariables(true);
            Equation condition = EquationParser.parse("echo(\"in echo=\" + a); a + 3");
            Map<String, Variable> vars = condition.getVariables();
            assertEquals("Must have 1 variable", 1, vars.size());
            condition.getVariable("a").setValue(3);
            Object result = condition.eval();
            assertTrue("Result must be Integer", result instanceof Integer);
            assertEquals("Result must be 6", 6, result); 
        } catch (ParseException ex) {
            ex.printStackTrace();
            fail("Fail to parse");
        }
    }     
}
