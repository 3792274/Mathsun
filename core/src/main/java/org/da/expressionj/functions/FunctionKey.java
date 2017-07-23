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

/**
 * A Function Key.
 *
 * @version 0.9.1
 */
public class FunctionKey {
   private String name = null;
   private int paramsSize = 0;

   public FunctionKey(String name, int paramsSize) {
      this.name = name;
      this.paramsSize = paramsSize;
   }

   public int getParamsSize() {
      return paramsSize;
   }

   public String getName() {
      return name;
   }

   @Override
   public boolean equals(Object o) {
      if (!(o instanceof FunctionKey)) {
         return false;
      } else {
         FunctionKey f = (FunctionKey) o;
         return ((f.paramsSize == paramsSize) && (f.name.equals(name)));
      }
   }

   @Override
   public int hashCode() {
      int hash = 5;
      hash = 67 * hash + (this.name != null ? this.name.hashCode() : 0);
      hash = 67 * hash + this.paramsSize;
      return hash;
   }
}
