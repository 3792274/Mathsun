/*
    Copyright (c) 2011, Dassault Aviation. All rights reserved.
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

import org.da.expressionj.expr.parser.EquationParser;
import org.da.expressionj.expr.parser.ParseException;
import org.da.expressionj.model.Equation;
import org.da.expressionj.model.Variable;
import org.junit.*;

import java.lang.reflect.Method;
import java.util.Map;

import static org.junit.Assert.*;

/** Test for Functions Definition.
 *
 * @version 0.9.2
 */
public class FunctionsDefinitionsTest {
    private static FunctionsDefinitions def = null;

    public FunctionsDefinitionsTest() {
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
            Method method = FunctionsDefinitionsTest.class.getMethod("method", Integer.TYPE, Integer.TYPE);
            def.addFunction("method", this, method);
            method = FunctionsDefinitionsTest.class.getMethod("method", Integer.TYPE);
            def.addFunction("method", this, method);
            method = FunctionsDefinitionsTest.class.getMethod("format", Integer.TYPE);
            def.addFunction("format", this, method);
            method = FunctionsDefinitionsTest.class.getMethod("select", Boolean.TYPE, Float.TYPE, Float.TYPE);
            def.addFunction("select", this, method);            
        } catch (NoSuchMethodException ex) {
        } catch (SecurityException ex) {
        }
    }

    @After
    public void tearDown() {
        def.reset();
        def = null;
    }

    public int method(int a, int b) {
        return a + b;
    }

     public int method(int a) {
        return a + 3;
    }

    public int format(int a) {
        return a + 1;
    }
    
    public float select(boolean cond, float b, float c) {
        return cond ? b : c;
    }    

   /**
     * Test of Polymorphism.
     */
    @Test
    public void testEvalPolymorphism() {
        System.out.println("testEvalPolymorphism");
        try {
            Equation condition = EquationParser.parse("method(a, b)");
            Map<String, Variable> vars = condition.getVariables();
            assertEquals("Must have 2 variables", 2, vars.size());
            condition.getVariable("a").setValue(2);
            condition.getVariable("b").setValue(5);
            Object o = condition.eval();
            assertTrue(o instanceof Integer);
            int res = ((Integer)o).intValue();
            assertEquals("Return value must be 7", 7, res);

            condition = EquationParser.parse("method(a)");
            vars = condition.getVariables();
            assertEquals("Must have 1 variable", 1, vars.size());
            condition.getVariable("a").setValue(2);
            o = condition.eval();
            assertTrue(o instanceof Integer);
            res = ((Integer)o).intValue();
            assertEquals("Return value must be 5", 5, res);
        } catch (ParseException ex) {
            System.out.println(ex.getMessage());
            fail("Fail to parse");
        }
    }

   /**
     * Test of Polymorphism.
     */
    @Test
    public void testEvalPolymorphism2() {
        System.out.println("testEvalPolymorphism2");
        try {
            Equation condition = EquationParser.parse("format(a)");
            Map<String, Variable> vars = condition.getVariables();
            assertEquals("Must have 1 variables", 1, vars.size());
            condition.getVariable("a").setValue(2);
            Object o = condition.eval();
            assertTrue(o instanceof Integer);
            int res = ((Integer)o).intValue();
            assertEquals("Return value must be 3", 3, res);

            condition = EquationParser.parse("format(a,\"N %d\")");
            vars = condition.getVariables();
            assertEquals("Must have 1 variables", 1, vars.size());
            condition.getVariable("a").setValue(2);
            o = condition.eval();
            assertTrue(o instanceof String);
            assertEquals("Return value must be N 2", "N 2", o);
        } catch (ParseException ex) {
            System.out.println(ex.getMessage());
            fail("Fail to parse");
        }
    }

   /**
     * Test of function imbrication.
     */
    @Test
    public void testFunctionImbrication() {
        System.out.println("testFunctionImbrication");
        try {
            Equation condition = EquationParser.parse("select(a>max, max, select(a<min, min, a))");
            Map<String, Variable> vars = condition.getVariables();
            assertEquals("Must have 3 variables", 3, vars.size());
            condition.getVariable("a").setValue(2);
            condition.getVariable("min").setValue(1);
            condition.getVariable("max").setValue(4);
            Object o = condition.eval();
            assertTrue(o instanceof Float);
            float res = ((Float)o).floatValue();
            assertEquals("Return value must be 2", 2, res, 0.001f);
            
            condition.getVariable("a").setValue(5);
            o = condition.eval();
            assertTrue(o instanceof Float);
            res = ((Float)o).floatValue();
            assertEquals("Return value must be 4", 4, res, 0.001f);       
            
            condition.getVariable("a").setValue(-2);
            o = condition.eval();
            assertTrue(o instanceof Float);
            res = ((Float)o).floatValue();
            assertEquals("Return value must be 1", 1, res, 0.001f);              
        } catch (ParseException ex) {
            System.out.println(ex.getMessage());
            fail("Fail to parse");
        }
    }    
}