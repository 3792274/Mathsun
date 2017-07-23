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
package org.da.expressionj;

import java.io.IOException;
import java.net.URL;
import java.util.PropertyResourceBundle;

/** Main class, only used to give some informations about the version of the
 * library on the command line.
 * 
 *  @since 0.1
 */
public class Main {
    public static void main(String[] args) {
        URL url = Thread.currentThread().getContextClassLoader()
            .getResource("org/da/expressionj/resources/expressionj.properties");
        try {
            PropertyResourceBundle prb = new PropertyResourceBundle(url.openStream());
            String version = prb.getString("version");
            String date = prb.getString("date");
            System.out.println("expresssionJ version " + version + " build on " + date);
            System.out.println("Distributed under LGPL 2.1 license");
        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }
}
