/*
 Copyright (c) 2011, 2012 Herve Girod. All rights reserved.
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
import org.da.expressionj.model.Equation;
import org.da.expressionj.model.Expression;
import org.junit.After;
import org.junit.AfterClass;
import static org.junit.Assert.*;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

/**
 * Unit tests for expression simplification.
 *
 * @version 0.9
 */
public class ExpressionSimplifierTest {
   public ExpressionSimplifierTest() {
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
    * Test of simplify method, of class ExpressionSimplifier.
    */
   @Test
   public void testSimplifyADD() {
      System.out.println("simplify ADD");
      try {
         Equation condition = EquationParser.parse("a + 0");

         ExpressionSimplifier simpl = new ExpressionSimplifier();
         Expression expr = simpl.simplify(condition);
         ExpressionExporter exporter = new ExpressionExporter();
         String result = exporter.export(expr);
         assertEquals("Must be a", "a", result);
      } catch (ParseException ex) {
         System.out.println(ex.getMessage());
         fail("Fail to parse");
      }
   }

   /**
    * Test of simplify method, of class ExpressionSimplifier.
    */
   @Test
   public void testSimplifySUB() {
      System.out.println("simplify SUB");
      try {
         Equation condition = EquationParser.parse("a - 0");

         ExpressionSimplifier simpl = new ExpressionSimplifier();
         Expression expr = simpl.simplify(condition);
         ExpressionExporter exporter = new ExpressionExporter();
         String result = exporter.export(expr);
         assertEquals("Must be a", "a", result);
      } catch (ParseException ex) {
         System.out.println(ex.getMessage());
         fail("Fail to parse");
      }
   }

   /**
    * Test of simplify method, of class ExpressionSimplifier.
    */
   @Test
   public void testSimplifyMULT() {
      System.out.println("simplify MULT");
      try {
         Equation condition = EquationParser.parse("a * 0");

         ExpressionSimplifier simpl = new ExpressionSimplifier();
         Expression expr = simpl.simplify(condition);
         ExpressionExporter exporter = new ExpressionExporter();
         String result = exporter.export(expr);
         assertEquals("Must be 0", "0", result);

         condition = EquationParser.parse("a * 1");
         expr = simpl.simplify(condition);
         result = exporter.export(expr);
         assertEquals("Must be a", "a", result);
      } catch (ParseException ex) {
         System.out.println(ex.getMessage());
         fail("Fail to parse");
      }
   }

   /**
    * Test of simplify method, of class ExpressionSimplifier.
    */
   @Test
   public void testSimplifyDIV() {
      System.out.println("simplify DIV");
      try {
         Equation condition = EquationParser.parse("0 / a");

         ExpressionSimplifier simpl = new ExpressionSimplifier();
         Expression expr = simpl.simplify(condition);
         ExpressionExporter exporter = new ExpressionExporter();
         String result = exporter.export(expr);
         assertEquals("Must be 0", "0", result);

         condition = EquationParser.parse("a / 1");
         expr = simpl.simplify(condition);
         result = exporter.export(expr);
         assertEquals("Must be a", "a", result);
      } catch (ParseException ex) {
         System.out.println(ex.getMessage());
         fail("Fail to parse");
      }
   }

   /**
    * Test of simplify method, of class ExpressionSimplifier.
    */
   @Test
   public void testSimplifyAND() {
      System.out.println("simplify AND");
      try {
         Equation condition = EquationParser.parse("a && true");

         ExpressionSimplifier simpl = new ExpressionSimplifier();
         Expression expr = simpl.simplify(condition);
         ExpressionExporter exporter = new ExpressionExporter();
         String result = exporter.export(expr);
         assertEquals("Must be a", "a", result);

         condition = EquationParser.parse("a && false");

         expr = simpl.simplify(condition);
         result = exporter.export(expr);
         assertEquals("Must be false", "false", result);
      } catch (ParseException ex) {
         System.out.println(ex.getMessage());
         fail("Fail to parse");
      }
   }

   /**
    * Test of simplify method, of class ExpressionSimplifier.
    */
   @Test
   public void testSimplifyOR() {
      System.out.println("simplify OR");
      try {
         Equation condition = EquationParser.parse("a || true");

         ExpressionSimplifier simpl = new ExpressionSimplifier();
         Expression expr = simpl.simplify(condition);
         ExpressionExporter exporter = new ExpressionExporter();
         String result = exporter.export(expr);
         assertEquals("Must be true", "true", result);

         condition = EquationParser.parse("a && false");

         expr = simpl.simplify(condition);
         result = exporter.export(expr);
         assertEquals("Must be false", "false", result);

         condition = EquationParser.parse("true && false");

         expr = simpl.simplify(condition);
         result = exporter.export(expr);
         assertEquals("Must be false", "false", result);
      } catch (ParseException ex) {
         System.out.println(ex.getMessage());
         fail("Fail to parse");
      }
   }

   /**
    * Test of simplify method, of class ExpressionSimplifier.
    */
   @Test
   public void testSimplifyNOT() {
      System.out.println("simplify NOT");
      try {
         Equation condition = EquationParser.parse("! true");

         ExpressionSimplifier simpl = new ExpressionSimplifier();
         Expression expr = simpl.simplify(condition);
         ExpressionExporter exporter = new ExpressionExporter();
         String result = exporter.export(expr);
         assertEquals("Must be false", "false", result);

         condition = EquationParser.parse("! false");

         expr = simpl.simplify(condition);
         result = exporter.export(expr);
         assertEquals("Must be true", "true", result);
      } catch (ParseException ex) {
         System.out.println(ex.getMessage());
         fail("Fail to parse");
      }
   }

   /**
    * Test of simplify method, of class ExpressionSimplifier.
    */
   @Test
   public void testSimplifyADDConstants() {
      System.out.println("simplify ADD Constants");
      try {
         Equation condition = EquationParser.parse("2 + 3");

         ExpressionSimplifier simpl = new ExpressionSimplifier();
         Expression expr = simpl.simplify(condition);
         ExpressionExporter exporter = new ExpressionExporter();
         String result = exporter.export(expr);
         assertEquals("Must be 5", "5", result);
      } catch (ParseException ex) {
         System.out.println(ex.getMessage());
         fail("Fail to parse");
      }
   }

   /**
    * Test of simplify method, of class ExpressionSimplifier.
    */
   @Test
   public void testSimplifyADDConstants2() {
      System.out.println("simplify ADD Constants 2");
      try {
         Equation condition = EquationParser.parse("2 + 3.4");

         ExpressionSimplifier simpl = new ExpressionSimplifier();
         Expression expr = simpl.simplify(condition);
         ExpressionExporter exporter = new ExpressionExporter();
         String result = exporter.export(expr);
         assertEquals("Must be 5.4", "5.4", result);
      } catch (ParseException ex) {
         System.out.println(ex.getMessage());
         fail("Fail to parse");
      }
   }

   /**
    * Test of simplify method, of class ExpressionSimplifier.
    */
   @Test
   public void testSimplifySUBConstants() {
      System.out.println("simplify SUB Constants");
      try {
         Equation condition = EquationParser.parse("2 - 3");

         ExpressionSimplifier simpl = new ExpressionSimplifier();
         Expression expr = simpl.simplify(condition);
         ExpressionExporter exporter = new ExpressionExporter();
         String result = exporter.export(expr);
         assertEquals("Must be -1", "-1", result);
      } catch (ParseException ex) {
         System.out.println(ex.getMessage());
         fail("Fail to parse");
      }
   }

   /**
    * Test of simplify method, of class ExpressionSimplifier.
    */
   @Test
   public void testSimplifyMULTConstants() {
      System.out.println("simplify MULT Constants");
      try {
         Equation condition = EquationParser.parse("2 * 3");

         ExpressionSimplifier simpl = new ExpressionSimplifier();
         Expression expr = simpl.simplify(condition);
         ExpressionExporter exporter = new ExpressionExporter();
         String result = exporter.export(expr);
         assertEquals("Must be 6", "6", result);
      } catch (ParseException ex) {
         System.out.println(ex.getMessage());
         fail("Fail to parse");
      }
   }

   /**
    * Test of simplify method, of class ExpressionSimplifier.
    */
   @Test
   public void testSimplifyDIVConstants() {
      System.out.println("simplify DIV Constants");
      try {
         Equation condition = EquationParser.parse("2 / 4");

         ExpressionSimplifier simpl = new ExpressionSimplifier();
         Expression expr = simpl.simplify(condition);
         ExpressionExporter exporter = new ExpressionExporter();
         String result = exporter.export(expr);
         assertEquals("Must be 0.5", "0.5", result);
      } catch (ParseException ex) {
         System.out.println(ex.getMessage());
         fail("Fail to parse");
      }
   }
   
   /**
    * Test of simplify method, of class ExpressionSimplifier.
    */
   @Test
   public void testSimplifyMultiplyConstants() {
      System.out.println("simplify DIV Constants");
      try {
         Equation condition = EquationParser.parse("a * (2.0 * 5.0)");

         ExpressionSimplifier simpl = new ExpressionSimplifier();
         Expression expr = simpl.simplify(condition);
         ExpressionExporter exporter = new ExpressionExporter();
         String result = exporter.export(expr);
         assertEquals("Must be a * 10.0", "a * 10.0", result);
      } catch (ParseException ex) {
         System.out.println(ex.getMessage());
         fail("Fail to parse");
      }
   }   
}