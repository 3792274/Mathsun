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

import org.da.expressionj.expr.parser.EquationParser;
import org.da.expressionj.expr.parser.ParseException;
import org.da.expressionj.model.Constant;
import org.da.expressionj.model.Equation;
import org.da.expressionj.model.Expression;
import org.junit.*;

import static org.junit.Assert.*;

/**
 * Test of constants parsing.
 *
 * @version 0.8.2
 */
public class ConstantTest {
   public ConstantTest() {
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
    * Test of equation for a Constant String.
    */
   @Test
   public void testEquation() {
      System.out.println("testEquation");
      try {
         Equation condition = EquationParser.parse("\"Test\"");
         Expression expr = condition.getExpression();
         assertTrue(expr instanceof Constant);
      } catch (ParseException ex) {
         System.out.println(ex.getMessage());
         fail("Fail to parse");
      }
   }

   /**
    * Test of eval for a Constant String.
    */
   @Test
   public void testEvalString() {
      System.out.println("testEvalStringConstant");
      try {
         Equation condition = EquationParser.parse("\"Test\"");
         Object o = condition.eval();
         assertTrue(o instanceof String);
         String res = (String) o;
         assertEquals("Must be equals to \"Test\"", "Test", res);
      } catch (ParseException ex) {
         System.out.println(ex.getMessage());
         fail("Fail to parse");
      }
   }

   /**
    * Test of eval for a litteral numeric constant.
    */
   @Test
   public void testEvalNumericConstant() {
      System.out.println("testEvalNumericConstant");
      try {
         Equation condition = EquationParser.parse("1.35");
         Object o = condition.eval();
         assertTrue(o instanceof Float);
         Float res = (Float) o;
         assertEquals("Must be equals to 1.35f", 1.35f, res, 0.00001f);
      } catch (ParseException ex) {
         System.out.println(ex.getMessage());
         fail("Fail to parse");
      }
   }

   /**
    * Test of eval for a litteral boollean constant.
    */
   @Test
   public void testEvalBooleanConstant() {
      System.out.println("testEvalBooleanConstant");
      try {
         Equation condition = EquationParser.parse("true");
         Object o = condition.eval();
         assertTrue(o instanceof Boolean);
         Boolean res = (Boolean) o;
         assertTrue("Must be true", res.booleanValue());

         condition = EquationParser.parse("false");
         o = condition.eval();
         assertTrue(o instanceof Boolean);
         res = (Boolean) o;
         assertFalse("Must be false", res.booleanValue());
      } catch (ParseException ex) {
         System.out.println(ex.getMessage());
         fail("Fail to parse");
      }
   }

   /**
    * Test of eval for a PI constant.
    */
   @Test
   public void testEvalPI() {
      System.out.println("testEvalPI");
      try {
         Equation condition = EquationParser.parse("PI + 1");
         Object o = condition.eval();
         assertTrue(o instanceof Float);
         Float res = (Float) o;
         assertEquals("Must be equals to 4.14...", (Math.PI + 1), res, 0.00001f);
      } catch (ParseException ex) {
         System.out.println(ex.getMessage());
         fail("Fail to parse");
      }
   }

   /**
    * Test of type for a boolean constant.
    */
   @Test
   public void testTypeBooleanConstant() {
      System.out.println("testTypeBooleanConstant");
      Constant c = new Constant(true);
      assertEquals("Type must be boolean", Expression.TYPE_BOOL, c.getType());
      assertEquals("Type must be boolean", Expression.TYPE_BOOL, c.getResultType());      
      assertEquals("Structure must be scalar", Expression.STRUCT_SCALAR, c.getResultStructure());      
   }
   
   /**
    * Test of type for a float constant.
    */
   @Test
   public void testTypeFloatConstant() {
      System.out.println("testTypeFloatConstant");
      Constant c = new Constant(1.2f);
      assertEquals("Type must be float", Expression.TYPE_FLOAT, c.getType());
      assertEquals("Type must be float", Expression.TYPE_FLOAT, c.getResultType());      
      assertEquals("Structure must be scalar", Expression.STRUCT_SCALAR, c.getResultStructure());      
   }   
   
   /**
    * Test of type for a int constant.
    */
   @Test
   public void testTypeIntConstant() {
      System.out.println("testTypeIntConstant");
      Constant c = new Constant(2);
      assertEquals("Type must be int", Expression.TYPE_INTEGER, c.getType());
      assertEquals("Type must be int", Expression.TYPE_INTEGER, c.getResultType());      
      assertEquals("Structure must be scalar", Expression.STRUCT_SCALAR, c.getResultStructure());      
   }      
   
   /**
    * Test of type for a String constant.
    */
   @Test
   public void testTypeStringConstant() {
      System.out.println("testTypeStringConstant");
      Constant c = new Constant("toto");
      assertEquals("Type must be String", Expression.TYPE_STRING, c.getType());
      assertEquals("Type must be String", Expression.TYPE_STRING, c.getResultType());      
      assertEquals("Structure must be scalar", Expression.STRUCT_SCALAR, c.getResultStructure());      
   }         
   
   /**
    * Test of type for a int array constant.
    */
   @Test
   public void testTypeIntArrayConstant() {
      System.out.println("testTypeIntArrayConstant");
      int[] array = new int[2];
      array[0] = 1;
      array[1] = 2;
      Constant c = new Constant(array);
      assertEquals("Type must be int", Expression.TYPE_INTEGER, c.getType());
      assertEquals("Type must be int array", Expression.TYPE_INTEGER, c.getResultType());      
      assertEquals("Structure must be array", Expression.STRUCT_ARRAY, c.getResultStructure());      
   }            
   
   /**
    * Test of type for a boolean array constant.
    */
   @Test
   public void testTypeBooleanArrayConstant() {
      System.out.println("testTypeBooleanArrayConstant");
      boolean[] array = new boolean[2];
      array[0] = true;
      array[1] = false;
      Constant c = new Constant(array);
      assertEquals("Type must be boolean", Expression.TYPE_BOOL, c.getType());
      assertEquals("Type must be boolean array", Expression.TYPE_BOOL, c.getResultType());      
      assertEquals("Structure must be array", Expression.STRUCT_ARRAY, c.getResultStructure());      
   }               
   
   /**
    * Test of type for a float array constant.
    */
   @Test
   public void testTypeFloatArrayConstant() {
      System.out.println("testTypeFloatArrayConstant");
      float[] array = new float[2];
      array[0] = 1f;
      array[1] = 0f;
      Constant c = new Constant(array);
      assertEquals("Type must be float", Expression.TYPE_FLOAT, c.getType());
      assertEquals("Type must be float array", Expression.TYPE_FLOAT, c.getResultType());      
      assertEquals("Structure must be array", Expression.STRUCT_ARRAY, c.getResultStructure());      
   }                  
   
   /**
    * Test of type for a String array constant.
    */
   @Test
   public void testTypeStringArrayConstant() {
      System.out.println("testTypeStringArrayConstant");
      String[] array = new String[2];
      array[0] = "toto";
      array[1] = "toto";
      Constant c = new Constant(array);
      assertEquals("Type must be String", Expression.TYPE_STRING, c.getType());
      assertEquals("Type must be String array", Expression.TYPE_STRING, c.getResultType());      
      assertEquals("Structure must be array", Expression.STRUCT_ARRAY, c.getResultStructure());      
   }                     
}