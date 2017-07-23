package sts;/**
 * Created by admin on 2017/7/21.
 */

import com.greenpineyu.fel.FelEngine;
import org.junit.Test;

/**
 * ************************
 *
 * @author tony 3556239829
 */
public class FelTest {
    @Test
    public void test01(){
            // FelEngine fel = new FelEngineImpl();
            Object result = FelEngine.instance.eval("5000*12+7500");
            System.out.println(result);
    }
}
