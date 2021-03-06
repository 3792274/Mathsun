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
package org.vedantatree.expressionoasis.extensions.customfunctions.xml;

import java.io.InputStream;
import java.util.List;

import org.simpleframework.xml.Serializer;
import org.simpleframework.xml.core.Persister;
import org.vedantatree.expressionoasis.extensions.customfunctions.CustomFunctionSourceProvider;


/**
 * Enables method source code for custom expression engine functions to be generated at run-time
 * to stored in an XML config file.
 * 
 * @author Kris Marwood
 * @version 1.0
 */
public class XMLCustomFunctionSourceProvider implements CustomFunctionSourceProvider
{

	/* the file containing the method definitions for our custom functions class */
	private static final String	FILE_PATH	= "customfunctions.xml";

	public List<String> getFunctionSources()
	{
		XMLCustomFunctionsPlaceholder functions = null;
		Serializer serializer = new Persister();
		try
		{
			ClassLoader classLoader = XMLCustomFunctionSourceProvider.class.getClassLoader();
			InputStream stream = classLoader.getResourceAsStream( FILE_PATH );
			functions = serializer.read( XMLCustomFunctionsPlaceholder.class, stream );
		}
		catch( Exception e )
		{
			throw new RuntimeException( e );
		}
		return functions.getFunctionSources();
	}
}
