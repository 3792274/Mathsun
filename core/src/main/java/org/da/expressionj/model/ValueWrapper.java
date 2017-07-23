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

/** This class cache a value.
 *
 * @version 0.8.2
 */
public interface ValueWrapper {
   public Object getValue();

   public int getIntValue();

   public float getFloatValue();

   public boolean getBooleanValue();

   public void setIntValue(int value);

   public void setFloatValue(float value);

   public void setBooleanValue(boolean value);

   public void setValue(Object value);

   public static class Obj implements ValueWrapper {
      private Object value = null;

      public Obj(Object value) {
         this.value = value;
      }

      @Override
      public Object getValue() {
         return value;
      }

      @Override
      public int getIntValue() {
         return (Integer) value;
      }

      @Override
      public float getFloatValue() {
         if (value instanceof java.lang.Float) {
            return (java.lang.Float) value;
         } else {
            return (Integer) value;
         }
      }

      @Override
      public boolean getBooleanValue() {
         return (Boolean) value;
      }

      @Override
      public void setIntValue(int value) {
         this.value = value;
      }

      @Override
      public void setFloatValue(float value) {
         this.value = value;
      }

      @Override
      public void setBooleanValue(boolean value) {
         this.value = value;
      }

      @Override
      public void setValue(Object value) {
         this.value = value;
      }
   }

   public static class Bool implements ValueWrapper {
      private boolean value = false;

      public Bool(boolean value) {
         this.value = value;
      }

      @Override
      public final boolean getBooleanValue() {
         return value;
      }

      @Override
      public final float getFloatValue() {
         return 0f;
      }

      @Override
      public final int getIntValue() {
         return 0;
      }

      @Override
      public Object getValue() {
         return value;
      }

      @Override
      public void setIntValue(int value) {
      }

      @Override
      public void setFloatValue(float value) {
      }

      @Override
      public final void setBooleanValue(boolean value) {
         this.value = value;
      }

      @Override
      public void setValue(Object value) {
         if (value instanceof Boolean) {
            this.value = (Boolean) value;
         }
      }
   }

   public static class Float implements ValueWrapper {
      private float value = 0f;

      public Float(float value) {
         this.value = value;
      }

      @Override
      public final boolean getBooleanValue() {
         return false;
      }

      @Override
      public final float getFloatValue() {
         return value;
      }

      @Override
      public final int getIntValue() {
         return (int) value;
      }

      @Override
      public Object getValue() {
         return value;
      }

      @Override
      public final void setIntValue(int value) {
         this.value = value;
      }

      @Override
      public final void setFloatValue(float value) {
         this.value = value;
      }

      @Override
      public void setBooleanValue(boolean value) {
      }

      @Override
      public void setValue(Object value) {
         if (value instanceof java.lang.Float) {
            this.value = ((java.lang.Float) value);
         } else if (value instanceof Integer) {
            this.value = ((Integer) value);
         }
      }
   }

   public static class Int implements ValueWrapper {
      private int value = 0;

      public Int(int value) {
         this.value = value;
      }

      @Override
      public final boolean getBooleanValue() {
         return false;
      }

      @Override
      public final float getFloatValue() {
         return value;
      }

      @Override
      public final int getIntValue() {
         return value;
      }

      @Override
      public Object getValue() {
         return value;
      }

      @Override
      public final void setIntValue(int value) {
         this.value = value;
      }

      @Override
      public final void setFloatValue(float value) {
         this.value = (int) value;
      }

      @Override
      public void setBooleanValue(boolean value) {
      }

      @Override
      public void setValue(Object value) {
         if (value instanceof java.lang.Float) {
            this.value = ((java.lang.Float) value).intValue();
         } else if (value instanceof Integer) {
            this.value = ((Integer) value);
         }
      }
   }
}
