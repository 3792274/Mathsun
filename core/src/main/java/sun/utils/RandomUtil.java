package sun.utils;/**
 * Created by admin on 2017/7/21.
 */

import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.google.common.collect.Sets;
import org.apache.commons.lang3.StringUtils;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.vedantatree.expressionoasis.ExpressionContext;
import org.vedantatree.expressionoasis.ExpressionEngine;

import java.text.NumberFormat;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * ************************
 *
 * @author tony 3556239829
 */
public class RandomUtil {

    public static  Random random = new Random();
    private static final Logger log =  LoggerFactory.getLogger(RandomUtil.class);

    /******************************************************************************************************************/

    /**
     * 整数(包括负数) - [a, b] - rand()%(b-a+1)+a
     */
    public static int getRandomIntBetweenAA(int min,int max) {
        if (min == max) {
            return min;
        }
        if(min < max){
            return   (int)(Math.random()*(max-min+1))+min;  // min 到max 之间的整数
        }
        if(min > max){
            return (int)(Math.random()*(min-max+1))+max;
        }
        return 0;
    }



    /**
     * 整数(包括负数) - (a, b] - rand()%(b-a)+(a+1)
     */
    public static int getRandomIntBetweenBA(int min,int max) {
        if (min == max) {
            return min;
        }
        if(min < max){
            return   (int)(Math.random()*(max-min))+(min+1);  // min 到max 之间的整数
        }
        if(min > max){
            return   (int)(Math.random()*(min-max))+(max+1);
        }
        return 0;
    }


    /**
     * 整数(包括负数) - [a, b) - rand()%(b-a)+a
     */
    public static int getRandomIntBetweenAB(int min,int max) {
        if (min == max) {
            return min;
        }
        if(min < max){
            return   (int)(Math.random()*(max-min))+min;
        }
        if(min > max){
            return   (int)(Math.random()*(min-max))+max;
        }
        return 0;
    }


    /**
     * 整数(包括负数) - (a, b)  - rand()%(b-a-1)+(a+1)
     */
    public static int getRandomIntBetweenBB(int min,int max) {
        if (min == max) {
            return min;
        }
        if(min < max){
            return   (int)(Math.random()*(max-min-1))+(min+1);
        }
        if(min > max){
            return   (int)(Math.random()*(min-max-1))+(max+1);
        }
        return 0;
    }

    /******************************************************************************************************************/

    /**
     * 小数（包括负数） [a,b]
     */
    public static double getRandomDoubleBetween(String minStr,String  maxStr) {
        double min = Double.valueOf(minStr);
        double max = Double.valueOf(maxStr);
        if (min == max) {
            return min;
        }
        if(min < max){
            return  formatDouble(Math.random()*(max-min)+min,minStr,maxStr);
        }
        if(min > max){
            return  formatDouble(Math.random()*(min-max)+max,maxStr,minStr);
        }
        return 0;
    }



/*返回小数位数*/
public static  int pointLength(String strNumber){
    if(StringUtils.isNotBlank(strNumber) && strNumber.contains(".")){
        return  strNumber.length()-strNumber.indexOf(".")-1;
    }
        return 0;
}


    /**
     * 通过小数点位数返回随机位数 0.39 0.399 返回2或者3
     */
    public static int getRandomLengOfBehindPoint(String min,String max){
        int minLength = pointLength(min);
        int maxLength = pointLength(min);
        if(minLength==maxLength)
            return minLength;
        if(minLength<maxLength)
            return getRandomIntBetweenAA(minLength,maxLength);
        if(maxLength<minLength)
            return getRandomIntBetweenAA(maxLength,minLength);
        return 0;
    }


    /*
     * 格式化double 0.3333 --> 0.3|0.33 |0.33
     */
    public static double formatDouble(double d ,String minStr,String  maxStr){
        NumberFormat numberFormat  = NumberFormat.getInstance();
        numberFormat.setMaximumFractionDigits(getRandomLengOfBehindPoint(minStr, maxStr));
        return Double.parseDouble(numberFormat.format(d));
    }

  /******************************************************************************************************************/

    /**
     * 解析变量
     * @param str
     * @return
     */
    private static Set<String> getVariable(String str) {
        Set<String> set = new TreeSet<String>();
        String[] arr = str.split("[^a-zA-Z0-9]");
        for (String s : arr) {
            if (s == null || "".equals(s)) {
                continue;
            }
            try {
                Integer.valueOf(s);
            } catch (Exception e) {
                set.add(s);
            }
        }
        return set;
    }


    /*取等号前表达式*/
    private static String[] getExpRight(String args){
        Matcher m = Pattern.compile("^[^=]*(?==)").matcher(args);
        Set<String> set = new HashSet<String>();
        while(m.find()){
            set.add(m.group());
        }
        return set.toArray(new String[0]);
    }


    /*分割等号前后表达式*/
    private static String[] splitExpByEq(String exp) throws Exception {
         if(exp.contains("=")){
             String[] expSplit = exp.split("=");
             if(expSplit.length==2){
                 return  expSplit;
             }
         }
         throw  new Exception("分割等号表达式出错："+exp);
    }



    /**
     * 转换 0<a<10 ->  0<a && a <10
     */
    public static String convertExp(String exp) throws Exception {
        String[] split = exp.split("&&");
       String result="";
        for (int i = 0; i < split.length; i++) {
            result=result.concat(convertExp00(split[i]));
            if(i!=split.length-1){
              result= result.concat(" && ");
            }
        }
        return result;

    }



    public static String convertExp00(String exp) throws Exception {
        String[] variable = getVariable(exp).toArray(new String[0]);
        if(variable.length==1){ //含有1个变量
            String[] as = exp.split(variable[0]); //变量分割
            if(as.length==1){ //一个操作符  0<=a
                return exp;
            }
            if(as.length==2){ //两个操作符 0<a<10,a<10
                if(StringUtils.isNotBlank(as[0])){
                    return as[0].concat(variable[0]).concat(" && ").concat(variable[0]).concat(as[1]); //0<a<10,
                }else{
                    return exp; //a<10
                }
            }
        }
        log.error("转换表达式出错：包含多个变量: "+exp);
        throw new Exception("转换表达式出错："+exp);
    }




    /**
     * 填充表达式变量: 参数为String，或Map<变量名，变量值>
     */
    public static String wipperVerable(String exp,Object o) throws Exception {
        String[] variables = getVariable(exp).toArray(new String[0]);
        String result = exp;
        if(o instanceof  Map){
            Map<String,String> variableMap = (Map<String, String>) o;
            for (String key : variableMap.keySet()) {
                result = result.replaceAll(key,variableMap.get(key));
            }
            return result;
        }else if(o instanceof String || o instanceof Number){
            if(variables[0].length()==1){
                String variable = variables[0];
                result = result.replaceAll(variable,o.toString());
                return result;
            }
        }
        throw new Exception("填充表达式变量出错:"+exp);
    }



    /******************************************************************************************************************/
    /*从表达式获取随机值*/
    private static String getNumFromExp(String exp){
        String expReplace = exp.replaceAll("<=","<").replaceAll(">=",">"); //替换
        Object result = null;
        String[] split = expReplace.split("&&");
        if(StringUtils.isNotBlank(exp) && null!=split){
            for (int i = 0; i < split.length; i++) {
                String s = split[i].trim();
                if(StringUtils.isBlank(s) || s.contains("||") || getVariable(s).size()!=1)
                    continue;
                else{
                    if(s.split("<").length==3 || s.split(">").length==3){  //0 < a < 10
                        String n1="0";
                        String n2="0";
                        if(s.contains("<")){
                            n1 = s.split("<")[0].trim();
                            n2 = s.split("<")[2].trim();
                        } else if(s.contains(">")){
                            n1 = s.split(">")[0].trim();
                            n2 = s.split(">")[2].trim();
                        }
                        if(n1.contains(".") || n2.contains(".")){
                            result = getRandomDoubleBetween(n1,n2);
                        }else{
                            result = getRandomIntBetweenAA(Integer.parseInt(n1),Integer.parseInt(n2));
                        }

                    }else if(s.split("<").length==2 || s.split(">").length==2){
                        String n1="0";

                        if(s.contains("<")){
                            String[] split1 = s.split("<");
                            try{
                                Double n2um = Double.parseDouble(split1[0].trim());
                                n1 = split1[0].trim();
                            }catch (Exception e){
                                Double n2um = Double.parseDouble(split1[1].trim());
                                n1 = split1[1].trim();
                            }
                            result = getRandomIntBetweenAA(Integer.parseInt(n1),-9999);
                        }else if(s.contains(">")){
                            String[] split1 = s.split(">");
                            try{
                                Double n2um = Double.parseDouble(split1[0].trim());
                                n1 = split1[0].trim();
                            }catch (Exception e){
                                Double n2um = Double.parseDouble(split1[1].trim());
                                n1 = split1[1].trim();
                            }

                            if(n1.contains(".")){
                                result = getRandomDoubleBetween(n1,"9999");
                            }else{
                                result = getRandomIntBetweenAA(Integer.parseInt(n1),9999);
                            }

                        }
                    } if(s.split("=").length==2){
                        String[] split1 = s.split("=");
                        try{
                            Double n2um = Double.parseDouble(split1[0].trim());
                            result = split1[0].trim();
                        }catch (Exception e){
                            Double n2um = Double.parseDouble(split1[1].trim());
                            result = split1[1].trim();
                        }
                    }
                        break;
                }
            }

        }else{
            result =getRandomIntBetweenAA(0,9999);
        }
        return String.valueOf(result);
    }




    /*生成一对符合参数规则的变量和值*/
    private  Map<String,String>  getRandomParmAndValue(Map<String, String> paramMap) throws Exception {
        Map<String,String> parmAndValue = Maps.newHashMap();
        ExpressionContext expressionContext = new ExpressionContext();
        String exp = paramMap.get("exp");
        String otherExp = paramMap.get("otherExp");
        String  expLeftVarablue =   getVariable(splitExpByEq(exp)[1]).toArray(new String[0])[0];//等号右边变量名

        for (String key : paramMap.keySet()) {
            if(key.equalsIgnoreCase("exp") ||key.equalsIgnoreCase(expLeftVarablue) ||key.equalsIgnoreCase("otherExp"))
                continue;
            String keyVarStr = key;                  //变量名称
            String valueVarStr = paramMap.get(key);  //取值范围

            boolean fondVar=true;
            while(fondVar){
                String randomNum = getNumFromExp(valueVarStr);
                String convertExp = convertExp(valueVarStr);
                convertExp= wipperVerable(convertExp,randomNum);
                Boolean result = (Boolean) ExpressionEngine.evaluate(convertExp, expressionContext );
                if(result){
                    parmAndValue.put(keyVarStr,randomNum);
                    fondVar=false;
                }
            }
        }


        //其他验证
        if(StringUtils.isNotBlank(otherExp)) {
            String otherExpStr = wipperVerable(otherExp, parmAndValue);
            Boolean otherExpStrResult = (Boolean) ExpressionEngine.evaluate(otherExpStr, expressionContext);
            if (otherExpStrResult){
                return parmAndValue;
            }
             return getRandomParmAndValue(paramMap);
        }


        return parmAndValue;
    }

  /******************************************************************************************************************/

      /*当前时间纳秒*/
      private long getNowMilliSecond() {
          return System.nanoTime()/1000000L;
      }


    /******************************************************************************************************************/


    @Test
    public void test04() throws Exception {
//        Map<String, String> paramMap = new HashMap<String, String>() {
//            {
//                put("exp", "a/b=c");  //公式,表达式右边只有1个变量
//                put("a", "1.0<a<30"); //取值范围 put("a", " && 0 <= a <= 10 && a>3 && a<9 && a=4 && a>b || a!=(6-b)/2");
//                put("b", "1.00<b<30");        //取值范围 // put("b", "b<=a || b<10");
//                put("c", "0<=c<=30 && c%1!=0");     //结果范围
//            }
//        };

        Map<String, String> paramMap = new HashMap<String, String>() {
            {
                put("exp", "a*c+b*c=d");  //公式,表达式右边只有1个变量
                put("a", "0<a<100");    //取值范围 put("a", " && 0 <= a <= 10 && a>3 && a<9 && a=4 && a>b || a!=(6-b)/2");
                put("b", "0<b<100");    //取值范围 // put("b", "b<=a || b<10");
                put("c", "1<c<99");     //结果范围
                put("d", "0<d<500");     //结果范围
                //put("otherExp", "a+b==100 || a+b==10");     //其他约束

            }
        };


        // getVariable(str1);

        String exp = paramMap.get("exp");
        String otherExp = paramMap.get("otherExp");
        HashSet<String> resultSet = Sets.newHashSet();

        int sum=50; //总题数
        int timeout=50; //超时时间秒

        long startMilliSecond = getNowMilliSecond();
        ExpressionContext expressionContext = new ExpressionContext();

        while (true){
                 Map<String,String> parmAndValue = getRandomParmAndValue(paramMap); //符合条件的随机变量值

                String expRight = wipperVerable(splitExpByEq(exp)[0],parmAndValue);
                Number expRightResult = (Number) ExpressionEngine.evaluate( expRight, expressionContext ); //等号左边值

                String  expLeftVarablue =   getVariable(splitExpByEq(exp)[1]).toArray(new String[0])[0];//等号右边变量名
                String expLeftVarablueExp  = paramMap.get(expLeftVarablue);//等号右边变量名，范围

                String finalExpResult = wipperVerable(convertExp(expLeftVarablueExp), expRightResult);//等号左边的计算结果，替换等号右边的表达式值
                Boolean expLeftResult = (Boolean) ExpressionEngine.evaluate( finalExpResult , expressionContext );//等号左边的计算结果是否在给定范围



                //找到结果后退出
                if(expLeftResult){
                    resultSet.add("".concat(expRight+"="+expRightResult).replaceAll("/","÷")); //去掉重复
                    if(resultSet.size()==sum){
                        break;
                    }
                }

                //超时后退出
                if(getNowMilliSecond()-startMilliSecond>=timeout*1000L){
                    log.error("超时："+timeout+" 秒。");
                    break;
                }

            }



        System.out.println(resultSet);


    }




    /******************************************************************************************************************/



    @Test
    public void testA(){
        for(int i=0;i<10;i++){
            //System.out.println(getNumFromExp("0 <= a <= 10"));
            //System.out.println(getNumFromExp("0<a<10"));
            //System.out.println(getNumFromExp("0>=a>10"));
            System.out.println(getNumFromExp("200>a>0"));
        }

//
//        for(int i=0;i<50;i++){
//            System.out.println(getRandomIntBetweenAA(3,6));
//        }

    }


    @Test
    public void test05() throws Exception {
        String r = "a<=10";
        System.out.println(convertExp(r));

    }

    /******************************************************************************************************************/

@Test
public void test44() throws Exception {
    String str = "0<a && a<10 && b<20";
    //System.out.println(wipperVerable(str,"20"));

    HashMap<String,String> variableMap = Maps.newHashMap();
    variableMap.put("a","20");
    variableMap.put("b","31");
    System.out.println(wipperVerable(str,variableMap));
}






    /******************************************************************************************************************/


    /**
     * 生成byte随机数数组，范围[-128,127]
     */
    public static byte[] getRandomByte(int size){
        Random random = new Random();
        byte[] bytes = new byte[size];
        // 存入随机数组
        random.nextBytes(bytes);
        return bytes;
    }





    @Test
    public void test03(){
        // 获得随机数
        double random = Math.random(); //大于等于 0.0 且小于 1.0的double类型小数值

        // 输出信息1
        System.out.printf("%.2f", random);
        System.out.println();
        System.out.println("This is a random for double:"+random);

        //输出信息2
        NumberFormat Nformat= NumberFormat.getInstance();
        Nformat.setMaximumFractionDigits(2);
        String string = Nformat.format(random);
        System.out.println("NumberFormat: "+string);



    }




   @Test
   public void test02(){
       byte[] randomByte = getRandomByte(20);
       System.out.println(randomByte);
       // 遍历输出
       for (byte b : randomByte) {
           System.out.println("byte:" + b);
       }

   }



    @Test
    public void test01(){
        boolean b=true;
        int j=0;
        while (b){
            j++;
            int i = getRandomIntBetweenAA(1,3);
            System.out.println(i);
            if(i==-3){
                System.out.println(i);
                System.out.println(j);
                b=false;
            }
            if(j==100)
                b=false;
        }
        System.out.println();
    }




    @Test
    public void test00(){
        Random random = new Random();
        double random1 = Math.random();                   //[0,1.0)区间的随机小数。
        double v  = random.nextDouble();                 //[0,1.0)
        double d2 = random.nextDouble() * 5;            //[0,5.0)  ，将该区间扩大5倍即是要求的区间
        double d3 = random.nextDouble() * 1.5 + 1;      //[1,2.5)
        //生成任意非从0开始的小数区间[a,b)范围的随机数字(其中a不等于0)，则只需要首先生成[0,b-a)区间的随机数字，然后将生成的随机数字区间加上a即可



        int i = random.nextInt();                 //-231到231-1之间
        int n2 = random.nextInt(10);       //[0,10)  ,数字是均匀的
        n2 = Math.abs(random.nextInt() % 10);    //该数字和10取余以后生成的数字区间为(-10,10)，余数的绝对值小于除数,总结果[0,10)，


        int n3 = random.nextInt(8);        //[0,n)
        n3 = Math.abs(random.nextInt() % 8);


        int n4 = random.nextInt(11);     //生成[0,10]区间的整数
        n4 = Math.abs(random.nextInt() % 11);



        int n5 = random.nextInt(18) - 3;         //生成[-3,15)区间的整数 ,生成非从0开始区间的随机整数，可以参看上面非从0开始的小数区间实现原理的说明。
        n5 = Math.abs(random.nextInt() % 18) - 3;




        //几率问题,随机生成一个整数，该整数以55%的几率生成1，以40%的几率生成2，以5%的几率生成3
        int n6 = random.nextInt(100);
        int m; //结果数字
        if(n6 < 55){ //55个数字的区间，55%的几率
            m = 1;
        }else if(n6 < 95){//[55,95)，40个数字的区间，40%的几率
            m = 2;
        }else{
            m = 3;
        }


        //几率问题，优化，因为几率都是5%的倍数，所以只要以5%为基础来控制几率即可，
        int n7 = random.nextInt(20);
        int m1;
        if(n7 < 11){
            m1 = 1;
        }else if(n7 < 19){
            m1= 2;
        }else{
            m1 = 3;
        }

    }

    
    @Test    
    public void testABC(){
        for (int i=0;i<100;i++){
            System.out.println(testInt(-1,3));
        }
    }



    public static int testInt(int a,int b){
        return   (int)(Math.random()*(b-a+1))+a;  // a 到b 之间的整数
    }


    @Test
    public void test011(){
        boolean finsh=true;
        ArrayList<String> lists = Lists.newArrayList();
        while (finsh){
            int a = testInt(0,10);
            int b = testInt(0,10);
            int c = testInt(0,10);

            if(a+b+c==10){
                System.out.println(a+"+"+b+"+"+c);
                lists.add(a+""+b+""+c);
                if(lists.size()==10)
                    finsh=false;
            }
        }

        System.out.println(lists);
    }


}
