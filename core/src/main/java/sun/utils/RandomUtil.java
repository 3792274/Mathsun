package sun.utils;/**
 * Created by admin on 2017/7/21.
 */

import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import net.objecthunter.exp4j.ExpressionBuilder;
import net.objecthunter.exp4j.operator.Operator;
import org.apache.commons.lang3.StringUtils;
import org.junit.Test;

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


    /**
     * 通过小数点位数返回随机位数 0.39 0.399 返回2或者3
     */
    public static int getRandomLengOfBehindPoint(String min,String max){
        int minLength = min.length()-min.indexOf(".")-1;
        int maxLength = max.length()-max.indexOf(".")-1;
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


    /*取等号前变量*/
    private static String[] getEqu(String args){
        Matcher m = Pattern.compile("^[^=]*(?==)").matcher(args);
        Set<String> set = new HashSet<String>();
        while(m.find()){
            set.add(m.group());
        }
        return set.toArray(new String[0]);
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
                        result = getRandomIntBetweenAA(Integer.parseInt(n1),Integer.parseInt(n2));

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
                            result = getRandomIntBetweenAA(Integer.parseInt(n1),9999);
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
    private  Map<String,Double>  getRandomParmAndValue(Map<String, String> paramMap) {
        Map<String,Double> parmAndValue = Maps.newHashMap();
        for (String key : paramMap.keySet()) {
            if(key.equalsIgnoreCase("exp"))
                continue;
            String keyVarStr = key;                  //变量名称
            String valueVarStr = paramMap.get(key);  //取值范围

            ExpressionBuilder expressionBuilder = new ExpressionBuilder(valueVarStr).operator(greater).operator(greaterEq).operator(smaller).operator(smallerEq).operator(eq).operator(notEq);
            net.objecthunter.exp4j.Expression expression = expressionBuilder.variables(keyVarStr).build();

            boolean fondVar=true;
            while(fondVar){
                double randomNum = Double.parseDouble(getNumFromExp(valueVarStr));
                expression.setVariable(keyVarStr,randomNum);
                if(1d == expression.evaluate()){
                    parmAndValue.put(keyVarStr,randomNum);
                    fondVar=false;
                }
            }
        }
        return parmAndValue;
    }

  /******************************************************************************************************************/
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

    Operator smallerEq = new Operator("<=", 2, true, 4) {

            @Override
            public double apply(double[] values) {
                if (values[0] <= values[1]) {
                    return 1d;
                } else {
                    return 0d;
                }
            }
  };

    Operator smaller = new Operator("<", 2, true, 4) {
        @Override
        public double apply(double[] values) {
            if (values[0] < values[1]) {
                return 1d;
            } else {
                return 0d;
            }
        }
    };


    Operator eq = new Operator("=", 2, true, 4) {
        @Override
        public double apply(double[] values) {
            if (values[0] == values[1]) {
                return 1d;
            } else {
                return 0d;
            }
        }
    };



    Operator notEq = new Operator("!=", 2, true, 4) {
        @Override
        public double apply(double[] values) {
            if (values[0] != values[1]) {
                return 1d;
            } else {
                return 0d;
            }
        }
    };



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
    public void test04(){
        Map<String, String> paramMap = new HashMap<String, String>() {
            {
                put("exp", "a+b=c");  //公式
                put("a", "0<a<10"); //取值范围 put("a", " && 0 <= a <= 10 && a>3 && a<9 && a=4 && a>b || a!=(6-b)/2");
                put("b", "0<b<10");        //取值范围 // put("b", "b<=a || b<10");
                put("c", "c<=10");     //结果范围
            }
        };
        // getVariable(str1);



        String exp = paramMap.get("exp");

        for (int i = 0; i <10; i++) {
            boolean b = true;
            while (b){
                Map<String,Double> parmAndValue = getRandomParmAndValue(paramMap);

                ExpressionBuilder expressionBuilder = new ExpressionBuilder(exp).operator(greater).operator(greaterEq).operator(smaller).operator(smallerEq).operator(eq).operator(notEq);
                net.objecthunter.exp4j.Expression expression = expressionBuilder.variables(getVariable(exp)).build();
                for (String key : parmAndValue.keySet()) {
                    String keyVarStr = key;                      //变量名称
                    Double valueVarStr = parmAndValue.get(key);  //取值范围
                    expression.setVariable(keyVarStr,valueVarStr);
                }

                if(1d == expression.evaluate()){
                    b=false;
                    String result = exp;
                    for (String key : parmAndValue.keySet()) {
                        Double value = parmAndValue.get(key);
                        NumberFormat Nformat= NumberFormat.getInstance();
                        Nformat.setMaximumFractionDigits(0);
                        String string = Nformat.format(value);
                        result = result.replaceFirst(key,string);
                    }
                    System.out.println(result);
                }


            }
        }



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
