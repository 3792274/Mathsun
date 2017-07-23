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
package org.da.expressionj.functions;

import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

/**
 * This singlton class allows to bind function names in the mathematical expressions to regular Java methods.
 *
 * @version 0.9.1
 */
public class FunctionsDefinitions {
   private static FunctionsDefinitions def = null;
   private Map<FunctionKey, Function> functions = new HashMap();
   private Set<String> varargsFunctions = new HashSet();

   private FunctionsDefinitions() {
   }

   public static FunctionsDefinitions getInstance() {
      if (def == null) {
         def = new FunctionsDefinitions();
      }
      return def;
   }

   /**
    * Reset all the bindings.
    */
   public void reset() {
      functions.clear();
   }

   public Map<FunctionKey, Function> getFunctions() {
      return functions;
   }

   public static boolean isVarargs(String functionName) {
      return def.varargsFunctions.contains(functionName);
   }

   /**
    * Return the function of a given name.
    */
   public Function getFunction(FunctionKey key) {
      return functions.get(key);
   }

   /**
    * Add a new binding. For example, to add the new "test" function, this code is valid:
    * <pre>
    *   FunctionsDefinitions def = FunctionsDefinitions.getInstance();
    *   Method method = ExprFunctionTest.class.getMethod("myTest", Integer.TYPE, Integer.TYPE);
    *   def.addFunction("test", this, method);
    * </pre>
    *
    * For the function with the following signature:
    * <pre>
    *  public int myTest(int a, int b) {
    *     return a + b;
    *  }
    * </pre>
    *
    * The function can then be called as for example:
    * <pre>
    * test(1, 2)
    * </pre>
    *
    * @param name the name of the function (to be used in the expressions)
    * @param obj the object holding the associated Java method
    * @param method the Java method
    */
   public void addFunction(String name, Object obj, Method method) {
      Function function = new Function(name);
      function.setObject(obj);
      function.setMethod(method);
      FunctionKey key = null;
      if (method.isVarArgs()) {
         key = new FunctionKey(name, 1);
         varargsFunctions.add(name);
      } else {
         key = new FunctionKey(name, function.getParamsSize());
      }
      functions.put(key, function);
   }
}
