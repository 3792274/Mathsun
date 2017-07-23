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

/**
 * An abstract value, used for Constants and variables.
 *
 * @version 0.10
 */
public abstract class AbstractValue extends AbstractExpression implements Value {
   protected short type = Expression.TYPE_DYNAMIC;
   protected short struct = Expression.STRUCT_SCALAR;
   protected ValueWrapper value = null;
   protected String name = null;

   public void setName(String name) {
      this.name = name;
   }

   @Override
   public String getName() {
      return name;
   }

   /**
    * Set the type and structure of the value.
    */
   @Override
   public final void setType(short type, short struct) {
      this.type = type;
      this.struct = struct;
      checkType();
   }

   /**
    * Set the type of the value.
    */
   @Override
   public final void setType(short type) {
      this.type = type;
      checkType();
   }

   /**
    * Set the structure of the value.
    */
   @Override
   public final void setStructure(short struct) {
      this.struct = struct;
      checkType();
   }

   /**
    * Set the default value for the type.
    */
   protected void setDefaultValue() {
      if (struct == Expression.STRUCT_SCALAR) {
         if (type == Expression.TYPE_BOOL) {
            value = new ValueWrapper.Bool(false);
         } else if (type == Expression.TYPE_NUMERIC) {
            value = new ValueWrapper.Float(0);
         } else if (type == Expression.TYPE_INTEGER) {
            value = new ValueWrapper.Int(0);
         } else if (type == Expression.TYPE_FLOAT) {
            value = new ValueWrapper.Float(0);
         } else if (type == Expression.TYPE_STRING) {
            value = new ValueWrapper.Obj("");
         }
      } else {
         if (type == Expression.TYPE_BOOL) {
            Boolean[] values = new Boolean[1];
            values[0] = Boolean.FALSE;
            value = new ValueWrapper.Obj(values);
         } else if (type == Expression.TYPE_NUMERIC) {
            Integer[] values = new Integer[1];
            values[0] = 0;
            value = new ValueWrapper.Obj(values);
         } else if (type == Expression.TYPE_INTEGER) {
            Integer[] values = new Integer[1];
            values[0] = 0;
            value = new ValueWrapper.Obj(values);
         } else if (type == Expression.TYPE_FLOAT) {
            Float[] values = new Float[1];
            values[0] = 0f;
            value = new ValueWrapper.Obj(values);
         } else if (type == Expression.TYPE_STRING) {
            value = new ValueWrapper.Obj("");
         }
      }
   }

   private void doInitType(Object oneValue) {
      if (oneValue instanceof String) {
         type = Expression.TYPE_STRING;
      } else if (oneValue instanceof Float) {
         type = Expression.TYPE_FLOAT;
      } else if (oneValue instanceof Double) {
         type = Expression.TYPE_FLOAT;
      } else if (oneValue instanceof Integer) {
         type = Expression.TYPE_INTEGER;
      } else if (oneValue instanceof Boolean) {
         type = Expression.TYPE_BOOL;
      } else if (oneValue instanceof Number) {
         type = Expression.TYPE_NUMERIC;
      } else if (oneValue instanceof ValueWrapper.Float) {
         type = Expression.TYPE_FLOAT;
      } else if (oneValue instanceof ValueWrapper.Int) {
         type = Expression.TYPE_INTEGER;
      } else if (oneValue instanceof ValueWrapper.Bool) {
         type = Expression.TYPE_BOOL;
      } else if (oneValue instanceof ValueWrapper.Obj) {
         ValueWrapper.Obj obj = (ValueWrapper.Obj) oneValue;
         if (obj.getValue() instanceof String) {
            type = Expression.TYPE_STRING;
         } else if (obj.getValue() instanceof Number) {
            type = Expression.TYPE_NUMERIC;
         } else {
            type = Expression.TYPE_UNDEF;
         }
      }
   }

   protected void initType() {
      if (value.getValue() instanceof int[]) {
         struct = Expression.STRUCT_ARRAY;
         doInitType(((int[]) value.getValue())[0]);
      } else if (value.getValue() instanceof float[]) {
         struct = Expression.STRUCT_ARRAY;
         doInitType(((float[]) value.getValue())[0]);
      } else if (value.getValue() instanceof boolean[]) {
         struct = Expression.STRUCT_ARRAY;
         doInitType(((boolean[]) value.getValue())[0]);
      } else if (value.getValue() instanceof String[]) {
         struct = Expression.STRUCT_ARRAY;
         doInitType(((String[]) value.getValue())[0]);
      } else if (value.getValue() instanceof Object[]) {
         struct = Expression.STRUCT_ARRAY;
         doInitType(((Object[]) value.getValue())[0]);
      } else {
         struct = Expression.STRUCT_SCALAR;
         doInitType(value);
      }
   }

   protected void checkType() {
      if ((type != Expression.TYPE_DYNAMIC) && (type != Expression.TYPE_UNDEF)) {
         if (value == null) {
            setDefaultValue();
         } else {
            if (struct == Expression.STRUCT_SCALAR) {
               if ((type == Expression.TYPE_BOOL) && (!(value instanceof ValueWrapper.Bool))) {
                  value = new ValueWrapper.Bool(false);
               } else if ((type == Expression.TYPE_NUMERIC)
                  && (!((value instanceof ValueWrapper.Int) || (value instanceof ValueWrapper.Float)))) {
                  value = new ValueWrapper.Float(0);
               } else if (type == Expression.TYPE_INTEGER) {
                  if (!((value instanceof ValueWrapper.Int) || (value instanceof ValueWrapper.Float))) {
                     value = new ValueWrapper.Int(0);
                  } else if (value instanceof ValueWrapper.Float) {
                     value = new ValueWrapper.Int(value.getIntValue());
                  }
               } else if (type == Expression.TYPE_FLOAT) {
                  if (!((value instanceof ValueWrapper.Int) || (value instanceof ValueWrapper.Float))) {
                     value = new ValueWrapper.Float(0);
                  } else if (value instanceof ValueWrapper.Int) {
                     value = new ValueWrapper.Float(value.getFloatValue());
                  }
               } else if ((type == Expression.TYPE_STRING) && (!(value.getValue() instanceof String))) {
                  value = new ValueWrapper.Obj("");
               }
            } else {
               Object values = value.getValue();
               if ((values instanceof boolean[]) && (type != Expression.TYPE_BOOL)) {
                  value = new ValueWrapper.Obj(new boolean[1]);
                  ((boolean[]) value.getValue())[0] = false;
               } else if ((values instanceof Boolean[]) && (type != Expression.TYPE_BOOL)) {
                  value = new ValueWrapper.Obj(new Boolean[1]);
                  ((Boolean[]) value.getValue())[0] = Boolean.FALSE;
               } else if ((values instanceof int[]) && (type != Expression.TYPE_INTEGER)
                  && (type != Expression.TYPE_NUMERIC)) {
                  value = new ValueWrapper.Obj(new int[1]);
                  ((int[]) value.getValue())[0] = 0;
               } else if ((values instanceof Integer[]) && (type != Expression.TYPE_INTEGER)
                  && (type != Expression.TYPE_NUMERIC)) {
                  value = new ValueWrapper.Obj(new Integer[1]);
                  ((Integer[]) value.getValue())[0] = 0;
               } else if ((values instanceof float[]) && (type != Expression.TYPE_FLOAT)
                  && (type != Expression.TYPE_NUMERIC)) {
                  value = new ValueWrapper.Obj(new float[1]);
                  ((float[]) value.getValue())[0] = 0f;
               } else if ((values instanceof Float[]) && (type != Expression.TYPE_FLOAT)
                  && (type != Expression.TYPE_NUMERIC)) {
                  value = new ValueWrapper.Obj(new float[1]);
                  ((Float[]) value.getValue())[0] = 0f;
               } else if ((values instanceof String[]) && (type != Expression.TYPE_STRING)) {
                  value = new ValueWrapper.Obj(new String[1]);
                  ((String[]) value.getValue())[0] = "";
               }
            }
         }
      }
   }

   @Override
   public Expression getExpression() {
      return this;
   }

   @Override
   public Object getValue() {
      if (value != null) {
         return value.getValue();
      } else {
         return null;
      }
   }

   @Override
   public int getValueAsInt() {
      if (value != null) {
         return value.getIntValue();
      } else {
         return 0;
      }
   }

   @Override
   public float getValueAsFloat() {
      if (value != null) {
         return value.getFloatValue();
      } else {
         return 0f;
      }
   }

   @Override
   public boolean getValueAsBoolean() {
      if (value != null) {
         return value.getBooleanValue();
      } else {
         return false;
      }
   }

   @Override
   public void setValue(Object value) {
      checkType();
      if (value instanceof Integer) {
         setValueAsInt((Integer) value);
      } else if (value instanceof Float) {
         setValueAsFloat((Float) value);
      } else if (value instanceof Double) {
         setValueAsFloat(((Double) value).floatValue());
      } else if (value instanceof Boolean) {
         setValueAsBoolean((Boolean) value);
      } else if (value instanceof ValueWrapper.Int) {
         setValueAsInt(((ValueWrapper) value).getIntValue());
      } else if (value instanceof ValueWrapper.Float) {
         setValueAsFloat(((ValueWrapper) value).getFloatValue());
      } else if (value instanceof ValueWrapper.Bool) {
         setValueAsBoolean(((ValueWrapper) value).getBooleanValue());
      } else if (value instanceof ValueWrapper.Obj) {
         setValue(((ValueWrapper) value).getValue());
      } else {
         if (this.value == null) {
            this.value = new ValueWrapper.Obj(value);
         } else {
            this.value.setValue(value);
         }
      }
   }

   @Override
   public void setValueAsInt(int value) {
      checkType();
      if (this.value == null) {
         this.value = new ValueWrapper.Int(value);
      } else if (((type == Expression.TYPE_DYNAMIC) || (type == Expression.TYPE_NUMERIC))
         && (this.value instanceof ValueWrapper.Float)) {
         this.value = new ValueWrapper.Int(value);
      } else {
         this.value.setIntValue(value);
      }
   }

   @Override
   public void setValueAsFloat(float value) {
      checkType();
      if (this.value == null) {
         this.value = new ValueWrapper.Float(value);
      } else if (((type == Expression.TYPE_DYNAMIC) || (type == Expression.TYPE_NUMERIC))
         && (this.value instanceof ValueWrapper.Int)) {
         this.value = new ValueWrapper.Float(value);
      } else {
         this.value.setFloatValue(value);
      }
   }

   @Override
   public void setValueAsBoolean(boolean value) {
      checkType();
      if (this.value == null) {
         this.value = new ValueWrapper.Bool(value);
      } else {
         this.value.setBooleanValue(value);
      }
   }

   @Override
   public final int evalAsInt() {
      return value.getIntValue();
   }

   @Override
   public final float evalAsFloat() {
      return value.getFloatValue();
   }

   @Override
   public final boolean evalAsBoolean() {
      return value.getBooleanValue();
   }

   @Override
   public Object eval() throws ArithmeticException {
      if (value == null) {
         throw new ArithmeticException("Variable " + name + " value is null");
      }
      return value.getValue();
   }

   public short getType() {
      return type;
   }

   @Override
   public boolean equals(Object o) {
      if (!(o instanceof AbstractValue)) {
         return false;
      } else {
         return ((AbstractValue) o).name.equals(name);
      }
   }

   @Override
   public int hashCode() {
      int hash = 7;
      hash = 67 * hash + (this.name != null ? this.name.hashCode() : 0);
      return hash;
   }
}
