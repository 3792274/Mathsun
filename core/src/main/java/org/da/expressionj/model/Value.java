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

/** A value element  interface, which can be a Constant or a Variable.
 *
 * @version 0.8.1
 */
public interface Value extends Equation {
    public String getName();

    /** Return the value. Note that this method will return null if the value is null.
     */
    public Object getValue();
    
    /** Return the value as an int. Note that this method will return 0 if the value if null.
     */    
    public int getValueAsInt();
    
    /** Return the value as an int. Note that this method will return 0f if the value if null.
     */        
    public float getValueAsFloat();
    
    /** Return the value as an int. Note that this method will return false if the value if null.
     */       
    public boolean getValueAsBoolean();

    public void setValue(Object value);
    
    public void setValueAsInt(int value);
    
    public void setValueAsFloat(float value);
    
    public void setValueAsBoolean(boolean value);

    /** Set the type and structure of the value.
     */
    public void setType(short type, short struct);

    /** Set the type of the value.
     */
    public void setType(short type);

    /** Set the structure of the value.
     */
    public void setStructure(short struct);
}
