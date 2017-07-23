package sts;/**
 * Created by admin on 2017/7/22.
 */

import net.objecthunter.exp4j.Expression;
import net.objecthunter.exp4j.ExpressionBuilder;
import net.objecthunter.exp4j.operator.Operator;
import org.junit.Test;

import static org.junit.Assert.assertTrue;

/**
 * ************************
 *
 * @author tony 3556239829
 */
public class Exp4jTest {

    @Test
    public void testOperators4() throws Exception {
        Operator greaterEq = new Operator(">=", 2, true, 4) {

            @Override
            public double apply(double[] values) {
                if (values[0] >= values[1]) {
                    return 1d;
                } else {
                    return 0d;
                }
            }
        };
        Operator greater = new Operator(">", 2, true, 4) {

            @Override
            public double apply(double[] values) {
                if (values[0] > values[1]) {
                    return 1d;
                } else {
                    return 0d;
                }
            }
        };
        Operator newPlus = new Operator(">=>", 2, true, 4) {

            @Override
            public double apply(double[] values) {
                return values[0] + values[1];
            }
        };

        Expression e = new ExpressionBuilder("1>2").operator(greater).build();
        assertTrue(0d == e.evaluate());

        e = new ExpressionBuilder("2>=2").operator(greaterEq).build();
        assertTrue(1d == e.evaluate());

        e = new ExpressionBuilder("1>=>2").operator(newPlus).build();
        assertTrue(3d == e.evaluate());

        e = new ExpressionBuilder("1>=>2>2").operator(greater).operator(newPlus).build();
        assertTrue(1d == e.evaluate());

        e = new ExpressionBuilder("1>=>2>2>=1").operator(greater).operator(newPlus).operator(greaterEq).build();
        assertTrue(1d == e.evaluate());

        e = new ExpressionBuilder("1 >=> 2 > 2 >= 1").operator(greater).operator(newPlus).operator(greaterEq).build();
        assertTrue(1d == e.evaluate());

        e = new ExpressionBuilder("1 >=> 2 >= 2 > 1").operator(greater).operator(newPlus)
                .operator(greaterEq)
                .build();
        assertTrue(0d == e.evaluate());
        e = new ExpressionBuilder("1 >=> 2 >= 2 > 0").operator(greater).operator(newPlus)
                .operator(greaterEq)
                .build();
        assertTrue(1d == e.evaluate());
        e = new ExpressionBuilder("1 >=> 2 >= 2 >= 1").operator(greater).operator(newPlus)
                .operator(greaterEq)
                .build();
        assertTrue(1d == e.evaluate());
    }
}
