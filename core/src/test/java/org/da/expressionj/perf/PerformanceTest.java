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
package org.da.expressionj.perf;

import org.da.expressionj.expr.parser.EquationParser;
import org.da.expressionj.expr.parser.ParseException;
import org.da.expressionj.functions.FunctionsDefinitions;
import org.da.expressionj.model.Equation;
import org.da.expressionj.model.Variable;
import org.junit.*;

import java.lang.reflect.Method;
import java.util.Map;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.fail;

/** Test of Performance.
 *
 * @since 0.8
 */
public class PerformanceTest {
   private static FunctionsDefinitions def = null;

    public PerformanceTest() {
    }
    
    public int myTest(int a, int b) {
        return a + b;
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
            Method method = PerformanceTest.class.getMethod("myTest", Integer.TYPE, Integer.TYPE);
            def.addFunction("test", this, method);
            warmer();
        } catch (NoSuchMethodException ex) {
        } catch (SecurityException ex) {
        }          
    }

    @After
    public void tearDown() {
        def.reset();
        def = null;       
    }
    
    /**
     * Test of eval method, of class ExprADD.
     */
    public void warmer() {
        try {
            Equation condition = EquationParser.parse("a + b * c");
            condition.getVariable("a").setValue(4);
            condition.getVariable("b").setValue(2);
            condition.getVariable("c").setValue(2);
            for (int i = 0; i < 100000; i++) {
               condition.eval();
            }
        } catch (ParseException ex) {
            System.out.println(ex.getMessage());
        }
    }    

    /**
     * Test of eval method, of class ExprADD.
     */
    @Test
    public void testPerf1() {
        System.out.println("evalPerfo");
        try {
            Equation condition = EquationParser.parse("a + b*c - cos(a - 4 * b) / c");
            Map<String, Variable> vars = condition.getVariables();
            assertEquals("Must have 3 variables", 3, vars.size());
            condition.getVariable("a").setValue(4);
            condition.getVariable("b").setValue(2);
            condition.getVariable("c").setValue(3);
            long time = System.currentTimeMillis();
            for (int i = 0; i < 1000000; i++) {
               condition.eval();
            }
            time = System.currentTimeMillis() - time;
            System.out.println("evalPerfo time = "+time);
        } catch (ParseException ex) {
            System.out.println(ex.getMessage());
            fail("Fail to parse");
        }
    }
    
    /**
     * Test of eval method, of class ExprADD.
     */
    @Test
    public void testPerf2() {
        System.out.println("evalPerfo2");
        try {
            Equation condition = EquationParser.parse("test(a + b*c - cos(a - 4 * b) / c, c)");
            Map<String, Variable> vars = condition.getVariables();
            assertEquals("Must have 3 variables", 3, vars.size());
            condition.getVariable("a").setValue(4);
            condition.getVariable("b").setValue(2);
            condition.getVariable("c").setValue(3);
            long time = System.currentTimeMillis();
            for (int i = 0; i < 1000000; i++) {
               condition.eval();
            }
            time = System.currentTimeMillis() - time;
            System.out.println("evalPerfo2 time = "+time);            
        } catch (ParseException ex) {
            System.out.println(ex.getMessage());
            fail("Fail to parse");
        }
    }    
}