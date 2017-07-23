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
package org.da.expressionj.expr;

import java.lang.reflect.Method;
import java.util.Map;
import org.da.expressionj.expr.parser.EquationParser;
import org.da.expressionj.expr.parser.ParseException;
import org.da.expressionj.functions.FunctionsDefinitions;
import org.da.expressionj.model.Equation;
import org.da.expressionj.model.Expression;
import org.da.expressionj.model.Variable;
import org.junit.After;
import org.junit.AfterClass;
import static org.junit.Assert.*;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

/** test for Expressions in Functions definition.
 *
 * @version 0.9.2
 */
public class ExprExprInFunctionsTest {
    private static FunctionsDefinitions def = null;

    public ExprExprInFunctionsTest() {
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
            Method method = ExprExprInFunctionsTest.class.getMethod("myTest", Integer.TYPE, Integer.TYPE);
            def.addFunction("test", this, method);
            method = ExprExprInFunctionsTest.class.getMethod("myTest2", Integer.TYPE, Integer.TYPE);
            def.addFunction("test2", this, method);            
            method = ExprExprInFunctionsTest.class.getMethod("myTestf", Float.TYPE, Float.TYPE);
            def.addFunction("testf", this, method);
            method = ExprExprInFunctionsTest.class.getMethod("myTest2f", Float.TYPE, Float.TYPE);
            def.addFunction("test2f", this, method);                        
            method = ExprExprInFunctionsTest.class.getMethod("switchI", Boolean.TYPE, Integer.TYPE, Integer.TYPE);
            def.addFunction("switch", this, method);                                    
        } catch (NoSuchMethodException ex) {
        } catch (SecurityException ex) {
        }
    }

    @After
    public void tearDown() {
        def.reset();
        def = null;
    }

    public int myTest(int a, int b) {
        return a + b;
    }
    
    public int myTest2(int a, int b) {
        return a - b;
    }    
    
    public float myTestf(float a, float b) {
        return a + b;
    }
    
    public float myTest2f(float a, float b) {
        return a - b;
    }     
    
    public int switchI(boolean cond, int a, int b) {
        return cond ? a : b;
    }      

    /**
     * Test of eval.
     */
    @Test
    public void testEval() {
        System.out.println("testEvalExprInFunction");
        try {
            Equation condition = EquationParser.parse("sin(a + 1)");

            Map<String, Variable> vars = condition.getVariables();
            assertEquals("Must have 1 variable", 1, vars.size());
            condition.getVariable("a").setValue(2);
            Object o = condition.eval();
            assertTrue(o instanceof Float);
            float res = ((Float)o).floatValue();
            assertEquals("Return value must be sin(3)", Math.sin(3f), res, 0.0001f);
        } catch (ParseException ex) {
            System.out.println(ex.getMessage());
            fail("Fail to parse");
        }
    }

    /**
     * Test of eval.
     */
    @Test
    public void testEval2() {
        System.out.println("testEvalExprInFunction2");
        try {
            Equation condition = EquationParser.parse("test(a, b + 1)");
            Expression expr = condition.getExpression();
            Map<String, Variable> vars = condition.getVariables();
            assertEquals("Must have 2 variables", 2, vars.size());
            condition.getVariable("a").setValue(2);
            condition.getVariable("b").setValue(5);
            assertTrue(expr instanceof ExprFunction);
            ExprFunction func = (ExprFunction)expr;
            assertEquals("Must have 2 parameters", func.getExpressions().size(), 2);

            Object o = condition.eval();
            assertTrue(o instanceof Integer);
            int res = ((Integer)o).intValue();
            assertEquals("Return value must be 8", 8, res);
        } catch (ParseException ex) {
            System.out.println(ex.getMessage());
            fail("Fail to parse");
        }
    }
    
    /**
     * Test of eval.
     */
    @Test
    public void testEval3() {
        System.out.println("testEvalExprInFunction3");
        try {
            Equation condition = EquationParser.parse("test2(a, c - test(a, b * sin(a)))");
            Expression expr = condition.getExpression();
            Map<String, Variable> vars = condition.getVariables();
            assertEquals("Must have 3 variables", 3, vars.size());
            condition.getVariable("a").setValue(2);
            condition.getVariable("b").setValue(5);
            condition.getVariable("c").setValue(1);            
            assertTrue(expr instanceof ExprFunction);
            ExprFunction func = (ExprFunction)expr;
            assertEquals("Must have 2 parameters", 2, func.getExpressions().size());

            Object o = condition.eval();
            assertTrue(o instanceof Integer);
            int res = ((Integer)o).intValue();
            assertEquals("Return value must be 7.0", 7.0, res, 0.001f);
        } catch (ParseException ex) {
            System.out.println(ex.getMessage());
            fail("Fail to parse");
        }
    }    
    
   /**
    * Test of eval.
    */
   @Test
   public void testEval4() {
      System.out.println("testEvalExprInFunction4");
      try {
         Equation condition = EquationParser.parse("test2f(a, c - testf(a, b * sin(a)))");
         Expression expr = condition.getExpression();
         Map<String, Variable> vars = condition.getVariables();
         assertEquals("Must have 3 variables", 3, vars.size());
         condition.getVariable("a").setValue(2);
         condition.getVariable("b").setValue(5);
         condition.getVariable("c").setValue(1);
         assertTrue(expr instanceof ExprFunction);
         ExprFunction func = (ExprFunction) expr;
         assertEquals("Must have 2 parameters", 2, func.getExpressions().size());

         Object o = condition.eval();
         assertTrue(o instanceof Float);
         float a = 2f;
         float b = 5f;
         float c = 1f;
         float myTest = (float) (a + b * Math.sin(a));
         float expected = a - (c - myTest);

         float res = ((Float) o).floatValue();
         assertEquals("Return value must be 7.546487", expected, res, 0.001f);
      } catch (ParseException ex) {
         System.out.println(ex.getMessage());
         fail("Fail to parse");
      }
   }    
   
    /**
     * Test of eval with echo.
     */
    @Test
    public void testEvalWithEcho() {
        System.out.println("testEvalExprInFunctionWithEcho");
        try {
            Equation condition = EquationParser.parse("switch(a, echo(\"first\"); 2, echo(\"second\"); 3)");

            Map<String, Variable> vars = condition.getVariables();
            assertEquals("Must have 1 variable", 1, vars.size());
            condition.getVariable("a").setValue(false);
            Object o = condition.eval();
            assertTrue(o instanceof Integer);
            int res = ((Integer)o).intValue();
            assertEquals("Return value must be 3", 3, res);
            
            condition.getVariable("a").setValue(true);
            o = condition.eval();
            assertTrue(o instanceof Integer);
            res = ((Integer)o).intValue();
            assertEquals("Return value must be 2", 2, res);            
        } catch (ParseException ex) {
            System.out.println(ex.getMessage());
            fail("Fail to parse");
        }
    }         
    
    /**
     * Test of eval with echo.
     */
    @Test
    public void testEvalWithEcho2() {
        System.out.println("testEvalExprInFunctionWithEcho2");
        try {
            Equation condition = EquationParser.parse("echo(\"outside\"); switch(a, echo(\"first\"); 2, echo(\"second\"); 3)");

            Map<String, Variable> vars = condition.getVariables();
            assertEquals("Must have 1 variable", 1, vars.size());
            condition.getVariable("a").setValue(false);
            Object o = condition.eval();
            assertTrue(o instanceof Integer);
            int res = ((Integer)o).intValue();
            assertEquals("Return value must be 3", 3, res);
            
            condition.getVariable("a").setValue(true);
            o = condition.eval();
            assertTrue(o instanceof Integer);
            res = ((Integer)o).intValue();
            assertEquals("Return value must be 2", 2, res);            
        } catch (ParseException ex) {
            System.out.println(ex.getMessage());
            fail("Fail to parse");
        }
    }         
}