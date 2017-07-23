package sts;/**
 * Created by admin on 2017/7/21.
 */

import com.googlecode.aviator.AviatorEvaluator;
import com.googlecode.aviator.Expression;
import org.junit.Test;

import java.util.HashMap;
import java.util.Map;

/**
 * ************************
 *
 * @author tony 3556239829
 */
public class AviatorTest {


    @Test
    public void test03(){
        String expression = "0<1<10";
        // 编译表达式
        Expression compiledExp = AviatorEvaluator.compile(expression);
        Map<String, Object> env = new HashMap<String, Object>();
        env.put("a", 1);
        // 执行表达式
        Boolean result = (Boolean) compiledExp.execute(env);
        System.out.println(result);  // false
    }


    @Test
    public void test02(){
        String expression = "a-(b-c)>=100";
        // 编译表达式
        Expression compiledExp = AviatorEvaluator.compile(expression);
        Map<String, Object> env = new HashMap<String, Object>();
        env.put("a", 100.3);
        env.put("b", 45);
        env.put("c", -199.100);
        // 执行表达式
        Boolean result = (Boolean) compiledExp.execute(env);
        System.out.println(result);  // false
    }

    @Test
    public void test01(){
//        Long result = (Long) AviatorEvaluator.execute("1+2+3");
//        System.out.println(result);

        Object execute = AviatorEvaluator.execute("3>1 && 2!=4 || true ");
        System.out.println(execute);


//        String yourname = "aviator";
//        Map<String, Object> env = new HashMap<String, Object>();
//        env.put("yourname", yourname);
//        String resultStr = (String) AviatorEvaluator.execute(" 'hello ' + yourname ", env);
//        System.out.println(resultStr);
//
//        Object execute1 = AviatorEvaluator.execute("3>0? 'yes':'no'");
//        System.out.println(execute1);
    }
}
