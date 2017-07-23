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

/** Test for ExprNEQ.
 *
 * @since 0.8.8
 */
public class ExprNEQTest {
   public ExprNEQTest() {
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
    * Test of eval method, of class ExprNEQ.
    */
   @Test
   public void testEvalNEQInt() {
      System.out.println("evalNEQInt");
      try {
         Equation condition = EquationParser.parse("COSX != 0");
         Map<String, Variable> vars = condition.getVariables();
         assertEquals("Must have 1 variable", 1, vars.size());
         condition.getVariable("COSX").setValue(1);
         Object result = condition.eval();
         assertTrue("Result must be Boolean", result instanceof Boolean);
         boolean value = ((Boolean) result).booleanValue();
         assertTrue("Result must be true", value);
         
         condition.getVariable("COSX").setValue(0);
         result = condition.eval();
         assertTrue("Result must be Boolean", result instanceof Boolean);
         value = ((Boolean) result).booleanValue();
         assertFalse("Result must be false", value);         
      } catch (ParseException ex) {
         ex.printStackTrace();
         fail("Fail to parse");
      }
   }   

   /**
    * Test of eval method, of class ExprNEQ.
    */
   @Test
   public void testEvalNEQFloat() {
      System.out.println("evalNEQFloat");
      try {
         Equation condition = EquationParser.parse("COSX != 0");
         Map<String, Variable> vars = condition.getVariables();
         assertEquals("Must have 1 variable", 1, vars.size());
         condition.getVariable("COSX").setValue(1f);
         Object result = condition.eval();
         assertTrue("Result must be Boolean", result instanceof Boolean);
         boolean value = ((Boolean) result).booleanValue();
         assertTrue("Result must be true", value);
         
         condition.getVariable("COSX").setValue(0f);
         result = condition.eval();
         assertTrue("Result must be Boolean", result instanceof Boolean);
         value = ((Boolean) result).booleanValue();
         assertFalse("Result must be false", value);   
         
         condition.getVariable("COSX").setValue(0.9f);
         result = condition.eval();
         assertTrue("Result must be Boolean", result instanceof Boolean);
         value = ((Boolean) result).booleanValue();
         assertTrue("Result must be true", value);    
         
         condition.getVariable("COSX").setValue(0.99f);
         result = condition.eval();
         assertTrue("Result must be Boolean", result instanceof Boolean);
         value = ((Boolean) result).booleanValue();
         assertTrue("Result must be true", value);            
      } catch (ParseException ex) {
         ex.printStackTrace();
         fail("Fail to parse");
      }
   }     
}
