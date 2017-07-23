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

import org.da.expressionj.expr.parser.EquationParser;
import org.da.expressionj.expr.parser.ParseException;
import org.da.expressionj.model.Equation;
import org.da.expressionj.model.Expression;
import org.da.expressionj.model.Variable;
import org.junit.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import static org.junit.Assert.*;

/**
 * Test of ExprOR.
 *
 * @version 0.10
 */
public class ExprORTest {
   public ExprORTest() {
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
    * Test of basic eval method, of class ExprOR.
    */
   @Test
   public void testEvalORBasic() {
      System.out.println("evalORBasic");   
      try {
         Equation condition = EquationParser.parse("a || b");
         Map<String, Variable> vars = condition.getVariables();
         assertEquals("Must have 2 variables", 2, vars.size());
         condition.getVariable("a").setValue(true);
         condition.getVariable("b").setValue(false);
         boolean b = condition.evalAsBoolean();
         assertTrue("Result must be true", b);
         
         Expression expr = condition.getExpression();
         assertTrue("Expression must be ExprOR", expr instanceof ExprOR);
         ExprOR or = (ExprOR)expr;
         Object result = or.eval();
         assertTrue("Result must be Boolean", result instanceof Boolean);
         boolean value = ((Boolean) result).booleanValue();
         assertTrue("Result must be true", value);  
         
         condition.getVariable("a").setValue(false);
         condition.getVariable("b").setValue(false);
         b = condition.evalAsBoolean();
         assertFalse("Result must be false", b);    
         result = or.eval();
         assertTrue("Result must be Boolean", result instanceof Boolean);
         value = ((Boolean) result).booleanValue();
         assertFalse("Result must be false", value);     
         
         condition.getVariable("a").setValue(true);
         condition.getVariable("b").setValue(true);
         b = condition.evalAsBoolean();
         assertTrue("Result must be true", b);             
         
         condition.getVariable("a").setValue(false);
         condition.getVariable("b").setValue(true);
         b = condition.evalAsBoolean();
         assertTrue("Result must be true", b);                      
      } catch (ParseException ex) {
         ex.printStackTrace();
         fail("Fail to parse");
      }         
   }
   
   /**
    * Test of eval method, of class ExprOR.
    */
   @Test
   public void testEvalOR() {
      System.out.println("evalOR");
      try {
         Equation condition = EquationParser.parse("(COSX != 0) || (COSY != 0)");
         Map<String, Variable> vars = condition.getVariables();
         assertEquals("Must have 2 variables", 2, vars.size());
         condition.getVariable("COSX").setValue(1);
         condition.getVariable("COSY").setValue(1);
         Object result = condition.eval();
         assertTrue("Result must be Boolean", result instanceof Boolean);
         boolean value = ((Boolean) result).booleanValue();
         assertTrue("Result must be true", value);
         
         condition.getVariable("COSX").setValue(0);
         condition.getVariable("COSY").setValue(1);
         result = condition.eval();
         assertTrue("Result must be Boolean", result instanceof Boolean);
         value = ((Boolean) result).booleanValue();
         assertTrue("Result must be true", value);   
         
         condition.getVariable("COSX").setValue(1);
         condition.getVariable("COSY").setValue(0);
         result = condition.eval();
         assertTrue("Result must be Boolean", result instanceof Boolean);
         value = ((Boolean) result).booleanValue();
         assertTrue("Result must be true", value);  
         
         condition.getVariable("COSX").setValue(0);
         condition.getVariable("COSY").setValue(0);
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
    * Test of eval method, of class ExprOR.
    */
   @Test
   public void testEvalORComplex() {
      System.out.println("evalORComplex");
      try {
         Equation condition = EquationParser.parse("((COSX != 0) || (COSY != 0) || (COSZ != 0)) ");
         Map<String, Variable> vars = condition.getVariables();
         assertEquals("Must have 3 variables", 3, vars.size());
         condition.getVariable("COSX").setValue(1);
         condition.getVariable("COSY").setValue(1);
         condition.getVariable("COSZ").setValue(1);
         Object result = condition.eval();
         assertTrue("Result must be Boolean", result instanceof Boolean);
         boolean value = ((Boolean) result).booleanValue();
         assertTrue("Result must be true", value);
         
         condition.getVariable("COSX").setValue(0);
         condition.getVariable("COSY").setValue(1);
         condition.getVariable("COSZ").setValue(1);
         result = condition.eval();
         assertTrue("Result must be Boolean", result instanceof Boolean);
         value = ((Boolean) result).booleanValue();
         assertTrue("Result must be true", value);         
      } catch (ParseException ex) {
         ex.printStackTrace();
         fail("Fail to parse");
      }
   }
   
   /**
    * Test of eval method, of class ExprOR.
    */
   @Test
   public void testEvalORComplexWithFloat() {
      System.out.println("evalOR");
      try {
         List<Variable> vars = new ArrayList();
         vars.add(new Variable("COSX", Expression.TYPE_FLOAT, 0f));
         vars.add(new Variable("COSY", Expression.TYPE_FLOAT, 1f));
         vars.add(new Variable("COSZ", Expression.TYPE_FLOAT, 1f));         
         Equation condition = EquationParser.parse("((COSX != 0) || (COSY != 0) || (COSZ != 0)) ", vars);
         Object result = condition.eval();
         assertTrue("Result must be Boolean", result instanceof Boolean);
         boolean value = ((Boolean) result).booleanValue();
         assertTrue("Result must be true", value);         
      } catch (ParseException ex) {
         ex.printStackTrace();
         fail("Fail to parse");
      }
   }   
   
   /**
    * Test of eval method, of class ExprOR.
    */
   @Test
   public void testEvalORComplexWithFloat2() {
      System.out.println("evalOR2");
      try {
         List<Variable> vars = new ArrayList();
         vars.add(new Variable("COSX", Expression.TYPE_FLOAT, 0.98f));
         vars.add(new Variable("COSY", Expression.TYPE_FLOAT, 0f));
         vars.add(new Variable("COSZ", Expression.TYPE_FLOAT, 0f));         
         Equation condition = EquationParser.parse("((COSX != 0) || (COSY != 0) || (COSZ != 0)) ", vars);
         Object result = condition.eval();
         assertTrue("Result must be Boolean", result instanceof Boolean);
         boolean value = ((Boolean) result).booleanValue();
         assertTrue("Result must be true", value);         
      } catch (ParseException ex) {
         ex.printStackTrace();
         fail("Fail to parse");
      }
   }     
}
