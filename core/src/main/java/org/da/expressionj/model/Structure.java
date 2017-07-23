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
package org.da.expressionj.model;

import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;

/**
 * Represent a Structure.
 *
 * @since 0.10
 */
public class Structure extends Variable {
   protected Set<Field> fields = new HashSet();

   public Structure(String name) {
      super(name);
      this.setStructure(ExpressionType.STRUCT_STRUCTURE);
   }

   public Structure(String name, short type) {
      super(name);
      this.type = type;
      this.setStructure(ExpressionType.STRUCT_STRUCTURE);
   }

   @Override
   public Object clone() throws CloneNotSupportedException {
      Structure var = new Structure(name, type);
      Iterator<Field> it = fields.iterator();
      while (it.hasNext()) {
         fields.add(it.next());
      }
      return var;
   }

   public void addField(Field field) {
      fields.add(field);      
   }
   
   public Set<Field> getFields() {
      return fields;
   }

   @Override
   public String getExpressionName() {
      return name;
   }

   @Override
   public short getResultType() {
      return ExpressionType.TYPE_UNDEF;
   }

   @Override
   public short getResultStructure() {
      return ExpressionType.STRUCT_STRUCTURE;
   }

   @Override
   public void setValue(String varName, Object value) {
   }

   /** Represent a structure field.
    * @since 0.10
    */
   public static class Field extends Variable {
      public Field(String name) {
         super(name);
      }

      public Field(String name, short type) {
         super(name, type);
      }

      public Field(String name, short type, short struct) {
         super(name, type, struct);
      }

      public Field(String name, short type, Object value) {
         super(name, type, value);
      }

      public Field(String name, short type, Object value, short struct) {
         super(name, type, value, struct);
      }

      public Field(String name, Object value) {
         super(name, value);
      }
      
      @Override
      public Object clone() throws CloneNotSupportedException {
         Field field = new Field(name, type, value, struct);
         return field;
      }      

      @Override
      public boolean equals(Object o) {
         if (!(o instanceof Field)) {
            return false;
         } else {
            return ((Field) o).name.equals(name);
         }
      }

      @Override
      public int hashCode() {
         int hash = 7;
         hash = 67 * hash + (this.name != null ? this.name.hashCode() : 0);
         return hash;
      }
   }
}
