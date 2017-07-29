import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;

@Controller
@EnableCaching
@EnableWebMvc
@ComponentScan(basePackages = {"sun"})
@SpringBootApplication
public class Main {
    private static final Logger log =  LoggerFactory.getLogger(Main.class);
    public static void main(String[] args) {
         // new SpringApplicationBuilder() .environment(new EncryptableEnvironment(new StandardServletEnvironment())).sources(Main.class).run(args);
         ConfigurableApplicationContext run = SpringApplication.run(Main.class, args);
         log.info("-================================[Start Finish]================================-");
    }


    @RequestMapping("/")
    public String index(Model model) {
//        Person single = new Person("aa", 11);
//        List<Person> people = new ArrayList<>();
//        Person p1 = new Person("zhangsan", 11);
//        Person p2 = new Person("lisi", 22);
//        Person p3 = new Person("wangwu", 33);
//        people.add(p1);
//        people.add(p2);
//        people.add(p3);
//        model.addAttribute("singlePerson", single);
//        model.addAttribute("people", people);
//        return "index";
        return "redirect:dist/index.html";
    }

}