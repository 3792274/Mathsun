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
package org.da.expressionj.functions;

import java.io.PrintStream;

/**
 * This class allows to log the result of the "echo" keyword in an output stream.
 *
 * @since 0.12
 */
public class EchoLogger {
   private static EchoLogger logger = null;
   private PrintStream stream = null;

   public static EchoLogger getInstance() {
      if (logger == null) {
         logger = new EchoLogger();
      }
      return logger;
   }
   
   /** Set the printStream to use for the echo keyword. If the PrintStream to use is null, the standard output stream will be used instead.
    * Note that there is a very simple way to redirect the result of a PrintStream to anything you want in Java. For example, 
    * the following class redirect the result of the stream to the standard error stream in Java.
    * <pre>
    * public class ErrorStream extends ByteArrayOutputStream {
    *    public ErrorStream() {
    *       super();
    *    }
    * 
    *   public void flush() throws IOException {
    *      synchronized (this) {
    *         super.flush();
    *         String result = this.toString();
    *         System.out.err(result);
    *         super.reset();
    *       } 
    *    }
    * }
    * </pre>
    * A simple way to wrap an OutputStream like this one to a PrintStream is performed just by: 
    * <pre>
    * ErrorStream errorStream = new ErrorStream();
    * PrintStream stream = new PrintStream(errorStream);
    * </pre>
    */
   public void setPrintStream(PrintStream stream) {
      this.stream = stream;
   }
   
   /** Log one Object to the underlying PrintStream or the standard output Stream. This method will be invoked by the
    * expression echo.
    * @see org.da.expressionj.expr.ExprECHO 
    */
   public void log(Object o) {
      if (o != null) {
         if (stream == null) {
            System.out.println(o);
         } else {
            stream.println(o);
            stream.flush();
         }
      }      
   }
}
