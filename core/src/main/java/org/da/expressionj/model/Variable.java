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
package org.da.expressionj.model;

import java.util.HashMap;
import java.util.Map;

/**
 * Represent a Variable.
 *
 * @version 0.9.2
 */
public class Variable extends AbstractValue {
   public Variable(String name) {
      this.name = name;
   }

   public Variable(String name, short type) {
      this.name = name;
      this.type = type;
      setDefaultValue();
   }

   public Variable(String name, short type, short struct) {
      this.name = name;
      this.type = type;
      this.struct = struct;
      setDefaultValue();
   }

   public Variable(String name, short type, Object value) {
      this.name = name;
      this.setValue(value);
      setType(type, struct);
   }

   public Variable(String name, short type, Object value, short struct) {
      this.name = name;
      this.setValue(value);
      this.struct = struct;
      setType(type, struct);
   }

   public Variable(String name, Object value) {
      this.name = name;
      this.setValue(value);
   }
   
   @Override
   public Object clone() throws CloneNotSupportedException {
      Variable var = new Variable(name, type, value, struct);
      return var;
   }      

   @Override
   public String getExpressionName() {
      return name;
   }

   @Override
   public short getResultType() {
      if (type == Expression.TYPE_DYNAMIC) {
         if (value != null) {
            if (value instanceof ValueWrapper.Int) {
               return TYPE_INTEGER;
            } else if (value instanceof ValueWrapper.Float) {
               return TYPE_FLOAT;
            } else if (value instanceof ValueWrapper.Bool) {
               return TYPE_BOOL;
            } else if (value.getValue() instanceof Integer) {
               return TYPE_INTEGER;
            } else if (value.getValue() instanceof Number) {
               return TYPE_FLOAT;
            } else if (value.getValue() instanceof Boolean) {
               return TYPE_BOOL;
            } else if (value.getValue() instanceof String) {
               return TYPE_STRING;
            } else {
               return TYPE_UNDEF;
            }
         } else {
            return TYPE_UNDEF;
         }
      } else {
         return type;
      }
   }

   @Override
   public short getResultStructure() {
      if (type == Expression.TYPE_DYNAMIC) {
         if (value != null) {
            if (value.getValue() instanceof Object[]) {
               return STRUCT_ARRAY;
            } else if (value.getValue() instanceof int[]) {
               return STRUCT_ARRAY;
            } else if (value.getValue() instanceof float[]) {
               return STRUCT_ARRAY;
            } else {
               return STRUCT_SCALAR;
            }
         } else {
            return TYPE_UNDEF;
         }
      } else {
         return struct;
      }
   }

   @Override
   public Map<String, Variable> getVariables() {
      Map<String, Variable> vars = new HashMap(1);
      vars.put(name, this);
      return vars;
   }

   @Override
   public Variable getVariable(String varName) {
      if (varName.equals(name)) {
         return this;
      } else {
         return null;
      }
   }

   @Override
   public void setValue(String varName, Object value) {
      if (varName.equals(name)) {
         this.setValue(value);
      }
   }
   
   @Override
   public boolean equals(Object o) {
      if (!(o instanceof Variable)) {
         return false;
      } else {
         return ((Variable) o).name.equals(name);
      }
   }

   @Override
   public int hashCode() {
      int hash = 7;
      hash = 67 * hash + (this.name != null ? this.name.hashCode() : 0);
      return hash;
   }
}
