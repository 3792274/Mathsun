/**	
 *  Copyright (c) 2005-2014 VedantaTree all rights reserved.
 * 
 *  This file is part of ExpressionOasis.
 *
 *  ExpressionOasis is free software. You can redistribute it and/or modify
 *  it under the terms of the GNU Lesser General Public License as published by
 *  the Free Software Foundation, either version 3 of the License, or
 *  (at your option) any later version.
 *
 *  ExpressionOasis is distributed in the hope that it will be useful,
 *  but WITHOUT ANY WARRANTY; without even the implied warranty of
 *  MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. IN NO EVENT SHALL 
 *  THE AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES 
 *  OR OTHER LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, 
 *  ARISING FROM, OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE 
 *  OR OTHER DEALINGS IN THE SOFTWARE.See the GNU Lesser General Public License 
 *  for more details.
 *
 *  You should have received a copy of the GNU Lesser General Public License
 *  along with ExpressionOasis. If not, see <http://www.gnu.org/licenses/>.
 *  
 *  Please consider to contribute any enhancements to upstream codebase. 
 *  It will help the community in getting improved code and features, and 
 *  may help you to get the later releases with your changes.
 */
package org.vedantatree.expressionoasis.expressions;

import org.vedantatree.expressionoasis.ExpressionContext;
import org.vedantatree.expressionoasis.exceptions.ExpressionEngineException;
import org.vedantatree.expressionoasis.types.Type;
import org.vedantatree.expressionoasis.types.ValueObject;


/**
 * This class expression is used to make the string value expression. It gives
 * the string value.
 * 
 * @author Parmod Kamboj
 * @version 1.0
 * 
 *          Modified to support visitor design pattern.
 * 
 * @author Kris Marwood
 * @version 1.1
 * 
 */
public class StringExpression implements Expression
{

	/**
	 * This is the string value for this expression.
	 */
	private ValueObject	stringValue;

	/**
	 * Gets the value object for string value.
	 * 
	 * @see Expression#getValue()
	 */
	public ValueObject getValue() throws ExpressionEngineException
	{
		return stringValue;
	}

	/**
	 * Returns the string type.
	 * 
	 * @see Expression#getReturnType()
	 */
	public Type getReturnType() throws ExpressionEngineException
	{
		return Type.STRING;
	}

	/**
	 * Initializes the string value object.
	 * 
	 * @see Expression#initialize(org.vedantatree.expressionoasis.ExpressionContext,
	 *      Object)
	 */
	public void initialize( ExpressionContext expressionContext, Object parameters, boolean validate )
			throws ExpressionEngineException
	{
		String value = (String) parameters;
		value = value.substring( 1, value.length() - 1 );
		value = value.replaceAll( "[\\\\][']", "'" );
		stringValue = new ValueObject( value, Type.STRING );
	}

	/**
	 * Uninitaizes the expression
	 * 
	 * @see Expression#uninitialize(org.vedantatree.expressionoasis.ExpressionContext)
	 */
	public void uninitialize( ExpressionContext expressionContext )
	{
		stringValue = null;
	}

	/**
	 * @see Object#toString()
	 */
	@Override
	public String toString()
	{
		return stringValue == null ? "String value not set" : (String) stringValue.getValue();
	}

	/**
	 * Allows an expression visitor to visit this expression and it's sub-expressions (implements Visitor design
	 * pattern).
	 * 
	 * @see Expression#accept(org.vedantatree.expressionoasis.ExpressionVisitor)
	 */
	public void accept( ExpressionVisitor visitor )
	{
		visitor.visit( this );
	}
}