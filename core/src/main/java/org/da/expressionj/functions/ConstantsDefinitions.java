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

import java.util.HashMap;
import java.util.Map;
import org.da.expressionj.model.Constant;

/**
 * This singleton class allows to bind names in the mathematical expressions to global constants.
 *
 * @version 0.9.1
 */
public class ConstantsDefinitions {
   private static ConstantsDefinitions global = null;
   private Map<String, Constant> constants = new HashMap();

   private ConstantsDefinitions() {
   }

   public static ConstantsDefinitions getInstance() {
      if (global == null) {
         global = new ConstantsDefinitions();
      }
      return global;
   }

   /**
    * Reset all the bindings.
    */
   public void reset() {
      constants.clear();
   }

   public Map<String, Constant> getConstants() {
      return constants;
   }

   public boolean hasConstant(String name) {
      return constants.containsKey(name);
   }

   /**
    * Return the function of a given name.
    */
   public Constant getConstant(String name) {
      return constants.get(name);
   }

   /**
    * Add a new global constant binding.
    */
   public void addConstant(String name, Constant constant) {
      constant.setName(name);
      constants.put(name, constant);
   }
}
