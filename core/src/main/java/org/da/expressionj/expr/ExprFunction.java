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
package org.da.expressionj.expr;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import org.da.expressionj.functions.Function;
import org.da.expressionj.functions.FunctionKey;
import org.da.expressionj.functions.FunctionsDefinitions;
import org.da.expressionj.model.Expression;

/** Represent a "Function" expression.
 *
 * @version 0.9.2
 */
public class ExprFunction extends AbstractExprFunction {
   private FunctionKey key = null;
   private Object [] fields = null;
   private boolean varargs = false;

   public ExprFunction() {
   }
   
   @Override
   public Object clone() throws CloneNotSupportedException {
      ExprFunction func = new ExprFunction();
      func.fields = fields;
      func.key = key;
      func.varargs = varargs;      
      func.exprs = exprs;
      func.block = block;
      return func;
   }       

   @Override
   public String getExpressionName() {
      return key.getName();
   }

   public ExprFunction(FunctionKey key) {
      this.key = key;
   }

   public ExprFunction(String name, int paramsSize) {
      if (FunctionsDefinitions.isVarargs(name)) {
         this.key = new FunctionKey(name, 1);
      } else {
         this.key = new FunctionKey(name, paramsSize);
      }
   }

   public void setFunctionKey(FunctionKey key) {
      this.key = key;
   }

   public void setFunctionKey(String name, int paramsSize) {
      this.key = new FunctionKey(name, paramsSize);
   }

   public FunctionKey getFunctionKey() {
      return key;
   }

   private void fillIntField(Object[] fields) {
      int varargIndex = fields.length - 1;
      int[] array = new int[exprs.size() - varargIndex];
      for (int i = 0; i < exprs.size(); i++) {
         Expression expr = exprs.get(i);
         Object result = expr.eval();
         if (i < varargIndex) {
            fields[i] = result;
         } else {
            if (result instanceof Float) {
               array[i - varargIndex] = ((Float) result).intValue();
            } else if (result instanceof Double) {
               array[i - varargIndex] = ((Double) result).intValue();
            } else {
               array[i - varargIndex] = ((Integer) result).intValue();
            }
         }
      }
      fields[varargIndex] = array;
   }

   private void fillFloatField(Object[] fields) {
      int varargIndex = fields.length - 1;
      float[] array = new float[exprs.size() - varargIndex];
      for (int i = 0; i < exprs.size(); i++) {
         Expression expr = exprs.get(i);
         Object result = expr.eval();
         if (i < varargIndex) {
            fields[i] = result;
         } else {
            if (result instanceof Float) {
               array[i - varargIndex] = ((Float) result).floatValue();
            } else if (result instanceof Double) {
               array[i - varargIndex] = ((Double) result).floatValue();
            } else {
               array[i - varargIndex] = ((Integer) result).floatValue();
            }
         }
      }
      fields[varargIndex] = array;
   }

   private void fillObjectField(Object[] fields) {
      int varargIndex = fields.length - 1;
      Object[] array = new Object[exprs.size() - varargIndex];
      for (int i = 0; i < exprs.size(); i++) {
         Expression expr = exprs.get(i);
         Object result = expr.eval();
         if (i < varargIndex) {
            fields[i] = result;
         } else {
            array[i - varargIndex] = result;
         }
      }
      fields[varargIndex] = array;
   }
   
   private Object getField(int result, Class paramType) {
      if (paramType == Integer.TYPE) {
         return result;
      } else if (paramType == Float.TYPE) {
         return (float)result;
      } else return result;
   }
   
   private Object getField(float result, Class paramType) {
      if (paramType == Integer.TYPE) {
         return (int)result;
      } else if (paramType == Float.TYPE) {
         return result;
      } else return result;
   }   
   
   private Object getField(boolean result, Class paramType) {
      if (paramType == Boolean.TYPE) {
         return result;
      } else return result;
   }      

   private Object getField(Object result, Class paramType) {
      if (paramType == Integer.TYPE) {
         if (result instanceof Float) {
            return ((Float) result).intValue();
         } else if (result instanceof Double) {
            return ((Double) result).intValue();
         } else {
            return result;
         }
      } else if (paramType == Float.TYPE) {
         if (result instanceof Float) {
            return result;
         } else if (result instanceof Double) {
            return ((Double) result).floatValue();
         } else if (result instanceof Integer) {
            return ((Integer) result).floatValue();
         } else {
            return result;
         }
      } else {
         return result;
      }
   }

   private Class getVarargType(Class paramType) {
      if (paramType.equals(int[].class)) {
         return Integer.TYPE;
      } else if (paramType.equals(float[].class)) {
         return Float.TYPE;
      } else {
         return Object.class;
      }
   }
   
   private void initFieldsArray(Function function, Method method) {
      varargs = function.isVarargs();
      if (varargs) {
         fields = new Object[method.getParameterTypes().length];
      } else {
         fields = new Object[exprs.size()];
      }
   }

   @Override
   public final Object eval() throws ArithmeticException {
      FunctionsDefinitions def = FunctionsDefinitions.getInstance();
      Function function = def.getFunction(key);
      if (function == null) {
         throw new ArithmeticException("Function " + key.getName() + "( " + key.getParamsSize() + " parameters) does not exist");
      } else {
         Method method = function.getMethod();
         if (fields == null) {
            initFieldsArray(function, method);
         }
         Object obj = function.getObject();
         if (varargs) {
            Class clazz = method.getParameterTypes()[fields.length - 1];
            Class ctype = getVarargType(clazz);
            if (ctype == Integer.TYPE) {
               fillIntField(fields);
            } else if (ctype == Float.TYPE) {
               fillFloatField(fields);
            } else {
               fillObjectField(fields);
            }
         } else {
            for (int i = 0; i < exprs.size(); i++) {
               Expression expr = exprs.get(i);
               Object _f = null;
               if (expr.getResultType() == TYPE_INTEGER) {
                  _f = getField(expr.evalAsInt(), method.getParameterTypes()[i]);
               } else if (expr.getResultType() == TYPE_FLOAT) {
                  _f = getField(expr.evalAsFloat(), method.getParameterTypes()[i]);
               } else if (expr.getResultType() == TYPE_BOOL) {
                  _f = getField(expr.evalAsBoolean(), method.getParameterTypes()[i]);
               } else {
                  _f = getField(expr.eval(), method.getParameterTypes()[i]);
               }
               fields[i] = _f;
            }
         }
         try {
            Object result = null;
            if (varargs) {
               result = method.invoke(obj, fields);
            } else {
               result = method.invoke(obj, fields);
            }
            if (method.getReturnType() == Integer.TYPE) {
               return result;
            } else if (method.getReturnType() == Short.TYPE) {
               return ((Short) result).intValue();
            } else if (method.getReturnType() == Double.TYPE) {
               return ((Double) result).floatValue();
            } else if (method.getReturnType() == Float.TYPE) {
               Float f = (Float) result;
               if (f.isInfinite()) {
                  throw newArithmeticException(function);
               }
               return f;
            } else if (method.getReturnType() == Boolean.TYPE) {
               return ((Boolean) result).booleanValue();
            } else {
               return result;
            }
         } catch (IllegalAccessException ex) {
            ArithmeticException e = new ArithmeticException("IllegalAccessException for function " + function.getName());
            throw e;
         } catch (IllegalArgumentException ex) {
            ArithmeticException e = new ArithmeticException("Illegal argument for function " + function.getName());
            throw e;
         } catch (InvocationTargetException ex) {
            Throwable _ex = ex.getCause();
            String message = null;
            if (_ex != null) {
               message = "Exception in function " + function.getName() + " (" + _ex.getMessage() + ")";
            } else {
               message = "Exception in function " + function.getName();
            }
            ArithmeticException e = new ArithmeticException(message);
            e.initCause(_ex);
            e.setStackTrace(_ex.getStackTrace());
            throw e;
         }
      }
   }

   private ArithmeticException newArithmeticException(Function function) {
      return new ArithmeticException("Exception in function " + function.getName() + " (returned Infinity)");
   }

   @Override
   public final short getResultType() {
      FunctionsDefinitions def = FunctionsDefinitions.getInstance();
      Function function = def.getFunction(key);
      if (function == null) {
         return Expression.TYPE_UNDEF;
      } else {
         Method method = function.getMethod();
         if (method.getReturnType() == Integer.TYPE) {
            return Expression.TYPE_INTEGER;
         } else if (method.getReturnType() == Short.TYPE) {
            return Expression.TYPE_INTEGER;
         } else if (method.getReturnType() == Double.TYPE) {
            return Expression.TYPE_FLOAT;
         } else if (method.getReturnType() == Float.TYPE) {
            return Expression.TYPE_FLOAT;
         } else if (method.getReturnType() == String.class) {
            return Expression.TYPE_NUMERIC;
         } else {
            return Expression.TYPE_UNDEF;
         }
      }
   }

   @Override
   public short getResultStructure() {
      return STRUCT_SCALAR;
   }
}
