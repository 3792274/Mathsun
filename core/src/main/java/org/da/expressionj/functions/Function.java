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

/**
 * The definition of a Function.
 *
 * @version 0.9.1
 */
public class Function {
   private String name = null;
   private Method method = null;
   private Object obj = null;
   private int paramsSize = 0;

   public Function(String name) {
      this.name = name;
   }

   /**
    * Return the function name.
    */
   public String getName() {
      return name;
   }

   public boolean isVarargs() {
      return method.isVarArgs();
   }

   public int getParamsSize() {
      return paramsSize;
   }

   void setObject(Object obj) {
      this.obj = obj;
   }

   void setMethod(Method method) {
      this.method = method;
      Class[] clazz = method.getParameterTypes();
      paramsSize = clazz.length;
   }

   /**
    * Return the Java Object which hold the function.
    */
   public Object getObject() {
      return obj;
   }

   /**
    * Return the Java Method associated with the function.
    */
   public Method getMethod() {
      return method;
   }
}
