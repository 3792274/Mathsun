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

/**
 * Hold the types used in Expressions.
 *
 * @version 0.10
 */
public interface ExpressionType {
   /** The undefined type.
    */
   public short TYPE_UNDEF = -1;
   
   /** The boolean type.
    */   
   public short TYPE_BOOL = 0;
   
   /** The numeric type. Numeric types are types which can be used for both integer and float values. For example,
    * if the result of an expression is of the numeric type, this expression result can be integer or float.
    */      
   public short TYPE_NUMERIC = 1;
   
   /** The integer type.
    */      
   public short TYPE_INTEGER = 2;
   
   /** The float type.
    */      
   public short TYPE_FLOAT = 3;
   
   /** The String type.
    */      
   public short TYPE_STRING = 4;
   
   /** The dynamic type. Dynamic types are types which can be used for any value type. For example,
    * if the result of an expression is of the dynamic type, this expression result can be integer, float, boolean or 
    * String.
    */      
   public short TYPE_DYNAMIC = 5;
   
   /** The scalar structure.
    */      
   public short STRUCT_SCALAR = 0;
   
   /** The array structure.
    */      
   public short STRUCT_ARRAY = 1;
   
   
   /** The structure structure.
    */      
   public short STRUCT_STRUCTURE = 2;   
}
