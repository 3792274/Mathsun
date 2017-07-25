package sun.controller;/**
 * Created by admin on 2017/7/25.
 */

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import sun.utils.RandomUtil;

import java.util.HashSet;
import java.util.Map;

/**
 * ************************
 *
 * @author tony 3556239829
 */

@RestController
@RequestMapping("/genRex")
public class GeneratorRexContorller {
    private static final Logger log =  LoggerFactory.getLogger(GeneratorRexContorller.class);

    @RequestMapping(value = {"","/"},method = {RequestMethod.POST, RequestMethod.GET})  //,consumes={"application/json"}
    public HashSet<String>   testGetReqPayInfoById(@RequestBody(required = false) Map<String,String> reqMap) {
        int sumRow=10; //总题数行
        int sumCol=5; //总题数列
        int timeout=25; //超时时间秒

        if(reqMap.containsKey("sumRow")){
            sumRow = Integer.parseInt(reqMap.get("sumRow"));
            reqMap.remove("sumRow");
        }

         if(reqMap.containsKey("sumCol")){
             sumCol =  Integer.parseInt(reqMap.get("sumCol"));
             reqMap.remove("sumCol");
         }

        try {
              HashSet<String> resultSet = RandomUtil.generatorRex(sumRow*sumCol,timeout,reqMap);
              return resultSet;
        }catch (Exception e){
            log.error(e.getMessage());
        }
       return null;
    }


}
