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
package org.vedantatree.expressionoasis.expressions.arithmatic;

import org.vedantatree.expressionoasis.exceptions.ExpressionEngineException;
import org.vedantatree.expressionoasis.expressions.BinaryOperatorExpression;
import org.vedantatree.expressionoasis.types.Type;
import org.vedantatree.expressionoasis.types.ValueObject;

import java.math.BigDecimal;

/**
 * This class expression performs the multiplication ioperations.
 * 
 * @author Parmod Kamboj
 * @version 1.0
 * 
 *          Added support for nulls
 * 
 * @author Kris Marwood
 * @version 1.1
 */
public class MultiplyExpression extends BinaryOperatorExpression
{

	static
	{
		addTypePair( MultiplyExpression.class, Type.LONG, Type.LONG, Type.LONG );
		addTypePair( MultiplyExpression.class, Type.DOUBLE, Type.DOUBLE, Type.DOUBLE );
		addTypePair( MultiplyExpression.class, Type.LONG, Type.DOUBLE, Type.DOUBLE );
		addTypePair( MultiplyExpression.class, Type.DOUBLE, Type.LONG, Type.DOUBLE );

		// nullable type support
		addTypePair( MultiplyExpression.class, Type.OBJECT, Type.OBJECT, Type.OBJECT );
		addTypePair( MultiplyExpression.class, Type.DOUBLE, Type.OBJECT, Type.DOUBLE );
		addTypePair( MultiplyExpression.class, Type.OBJECT, Type.DOUBLE, Type.DOUBLE );
		addTypePair( MultiplyExpression.class, Type.LONG, Type.OBJECT, Type.LONG );
		addTypePair( MultiplyExpression.class, Type.OBJECT, Type.LONG, Type.LONG );
	}

	/**
	 * Performs the multiplications.
	 * 
	 * @see org.vedantatree.expressionoasis.expressions.Expression#getValue()
	 */
	public ValueObject getValue() throws ExpressionEngineException
	{
		Object leftValue = leftOperandExpression.getValue().getValue();
		Object rightValue = rightOperandExpression.getValue().getValue();
		Object result = null;
		Type returnType = getReturnType();

		if( leftValue != null && rightValue != null )
		{
			if( returnType == Type.LONG )
			{
				result = new Long( ( (Number) leftValue ).longValue() * ( (Number) rightValue ).longValue() );
			}
			else if( returnType == Type.DOUBLE )
			{
				result =  new BigDecimal(leftValue.toString()).multiply(new BigDecimal(rightValue.toString())).doubleValue();
			}
		}

		return new ValueObject( result, returnType );
	}
}