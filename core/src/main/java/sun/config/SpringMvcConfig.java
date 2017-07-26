package sun.config;/**
 * Created by admin on 2017/5/30.
 */

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializerProvider;
import org.springframework.boot.web.servlet.ServletListenerRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpStatus;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.web.servlet.config.annotation.PathMatchConfigurer;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.ViewControllerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;
import org.springframework.web.servlet.view.InternalResourceViewResolver;
import org.springframework.web.util.IntrospectorCleanupListener;

import java.io.IOException;
import java.util.List;

/**
 * ************************
 * webMVC配置
 *
 * @author tony 3556239829
 */
@Configuration
public class SpringMvcConfig extends WebMvcConfigurerAdapter {

    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        registry.addResourceHandler("/**").addResourceLocations("classpath:/static/");
    }

    @Bean
    public InternalResourceViewResolver viewResolver() {
        InternalResourceViewResolver resolver = new InternalResourceViewResolver();
        resolver.setPrefix("/static/");
        resolver.setSuffix(".html");
        return resolver;
    }

    @Override
    public void addViewControllers(ViewControllerRegistry registry) {
        registry.addStatusController("/error", HttpStatus.BAD_REQUEST);
        registry.addViewController("/error").setViewName("/error");
    }

    @Bean
    public ServletListenerRegistrationBean<IntrospectorCleanupListener> getDemoListener() {
        ServletListenerRegistrationBean<IntrospectorCleanupListener> registrationBean = new ServletListenerRegistrationBean<>();
        registrationBean.setListener(new IntrospectorCleanupListener());
        //registrationBean.setOrder(1);
        return registrationBean;
    }

    /**
     * 强制覆盖
     *
     * @param converters
     */
    @Override
    public void configureMessageConverters(List<HttpMessageConverter<?>> converters) {
        super.configureMessageConverters(converters);
        converters.add(new MappingJackson2HttpMessageConverter(jsonMapper()));
        // converters.add(new FastJsonHttpMessageConverter4());
    }

    @Bean
    public ObjectMapper jsonMapper() {
        ObjectMapper objectMapper = new ObjectMapper();
        //null输出空字符串
        objectMapper.getSerializerProvider().setNullValueSerializer(new JsonSerializer<Object>() {
            @Override
            public void serialize(Object value, JsonGenerator jgen, SerializerProvider provider) throws IOException {
                jgen.writeString("");
            }
        });

        objectMapper.configure(JsonParser.Feature.ALLOW_UNQUOTED_FIELD_NAMES, true);  // 字段和值都加引号
        objectMapper.configure(JsonGenerator.Feature.WRITE_NUMBERS_AS_STRINGS, true); // 数字也加引号

//      objectMapper.configure(JsonParser.Feature.ALLOW_SINGLE_QUOTES, true); //允许单引号
//
//
//        // 空值处理为空串
//        objectMapper.getSerializerProvider().setNullValueSerializer(new JsonSerializer<Object>()
//        {
//            @Override
//            public void serialize(
//                    Object value,
//                    JsonGenerator jg,
//                    SerializerProvider sp) throws IOException, JsonProcessingException
//            {
//                jg.writeString("");
//            }
//        });

        return objectMapper;
    }

    //不忽略点后面的参数
    @Override
    public void configurePathMatch(PathMatchConfigurer configurer) {
        configurer.setUseSuffixPatternMatch(false);
    }

}
