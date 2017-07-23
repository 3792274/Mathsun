package sts;/**
 * Created by admin on 2017/7/8.
 */

import org.junit.Test;

import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * ************************
 *
 * @author tony 3556239829
 */
public class SunMathTest {



    @Test
    public void test03(){
        String s = "(A1 + A2 + (X*90 + Y + 100)*N1)*100+A1=MN";
        List<String> list = Arrays.asList(getEqu(s));
        System.out.println(list);
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


/**********************************************************************************************************************/

    @Test
    public void test02(){
        getVariable("(A1 + A2 + (X*90 + Y + 100)*N1)*100+A1=MN");
        getVariable("a+b-c=(d+C)");
        getVariable("(A1 + A2 + (X*90 + Y + 100)*N1)*100+A1=A1");
        getVariable("(X+Y)/Z");
        getVariable("X1 + X23 + Y1");
        getVariable("X1*6 + Y1");
        getVariable("X1*6 + Y1 + 12");
        getVariable("X1*6 + Y1 + 12 + Y1 + X1*7"); // 含有重复的变量
    }



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
        for (String s : set) {
            System.out.print(s + " ");
        }
        System.out.println();
        return set;
    }

/**********************************************************************************************************************/

/**
 * 匹配分隔符
 */
@Test
public void test01(){
 String args[] = {"a+b*(b-c)=d"};
        if(args.length<1) {
            System.out.println(" please enter the input string containing delimiters that needs to be checked.");
            System.exit(0);
        }
        System.out.println("the input string is: "+args[0]);
        boolean matchFlag=true;
        Stack<Character> theStack=new Stack<Character>();
        for(int j=0; j<args[0].length(); j++) {
            char ch=args[0].charAt(j);
            switch(ch) {
                case '{':
                case '[':
                case '(':
                    theStack.push(ch);
                    break;
                case '}':
                case ']':
                case ')':
                    if(theStack.empty()) {
                        System.out.println("Error : prematurely empty! nothing matches final "+ch+" at  index "+j);
                        matchFlag=false;
                    } else {
                        char chx=theStack.pop();
                        if( (ch=='}' && chx!='{')||
                                (ch==']' && chx!='[')||
                                (ch==')' && chx!='(') ) {
                            System.out.println("Error : "+ch+" doesn't match the previous "+chx+" at index "+j);
                            matchFlag=false;
                        }
                    }
                    break;
                default:
                    break;
            } //end switch
        }//end for  loop
        //在这里,所有的字符都被处理了,如果此时栈中还有字符
        if(!theStack.empty()) {
            matchFlag=false;
            String temp="";
            while(!theStack.empty()) {
                temp+=theStack.pop();
            }
            System.out.println("Error : missing right delimiter "+temp);
        }
        if( matchFlag) {
            System.out.println("Correct! the delimiters of the original string are all matched .");
        }
    }





    /********************************************/

    @Test
    public void test10(){
        String s = "0 < a < 10";
        System.out.println(s.split("<")[0].trim());
        System.out.println(s.split("<")[2].trim());
    }










    }


