package sts;/**
 * Created by admin on 2017/7/20.
 */

import org.da.expressionj.expr.parser.EquationParser;
import org.da.expressionj.expr.parser.ParseException;
import org.da.expressionj.model.Equation;
import org.da.expressionj.model.Variable;
import org.junit.Test;

import java.util.Map;

/**
 * ************************
 *
 * @author tony 3556239829
 */
public class Test01 {


    @Test
    public void test02() throws ParseException {

        System.out.println(0.94*59);
    }

    @Test
    public void test01() throws ParseException {
        Equation condition = EquationParser.parse("c=a+b");
        Map<String, Variable> vars = condition.getVariables();
        System.out.println(vars);
    }
}
