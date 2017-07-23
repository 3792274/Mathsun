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
package org.da.expressionj.model;

import java.util.HashMap;
import java.util.Map;

/**
 * Represent a Constant.
 *
 * @version 0.10
 */
public class Constant extends AbstractValue {
   public static final Constant TRUE = new Constant(true);
   public static final Constant FALSE = new Constant(false);
   public static final Constant ZERO_I = new Constant(0);
   public static final Constant ZERO_F = new Constant(0f);
   public static final Constant ONE_I = new Constant(1);
   public static final Constant ONE_F = new Constant(1f);
   public static final Constant EMPTY_STRING = new Constant("");

   public Constant(Object value) {
      this.setValue(value);
      initType();
      checkType();
   }

   public Constant(Object value, short type) {
      this.type = type;
      this.setValue(value);
      checkType();
   }

   public Constant(short type, short struct) {
      this.type = type;
      this.struct = struct;
   }

   public Constant(Object value, short type, short struct) {
      this.type = type;
      this.struct = struct;
      this.setValue(value);
      checkType();
   }
   
   @Override
   public Object clone() throws CloneNotSupportedException {
      Constant constant = new Constant(value, type, struct);
      return constant;
   }   

   @Override
   public String getExpressionName() {
      return name;
   }

   @Override
   public short getResultType() {
      return type;
   }

   @Override
   public short getResultStructure() {
      return struct;
   }

   @Override
   public Map<String, Variable> getVariables() {
      Map<String, Variable> vars = new HashMap();
      return vars;
   }

   @Override
   public Variable getVariable(String varName) {
      return null;
   }

   @Override
   public void setValue(String varName, Object value) {
   }

   @Override
   public boolean equals(Object o) {
      if (!(o instanceof Constant)) {
         return false;
      } else {
         Constant constant = (Constant) o;
         if (constant.value == null) {
            return (value == null);
         } else if (value == null) {
            return (constant.value == null);
         } else {
            return constant.value.getValue().equals(value.getValue());
         }
      }
   }

   @Override
   public int hashCode() {
      int hash = 7;
      hash = 67 * hash + (this.name != null ? this.name.hashCode() : 0);
      return hash;
   }   
}
