package at.porscheinformatik.tutorial.todo.spring;

import java.util.List;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.util.ISO8601DateFormat;

@Configuration
@ComponentScan("at.porscheinformatik.tutorial.todo")
@EnableWebMvc
public class AppConfig
{
    @Bean
    WebMvcConfigurer mvcConfigurer()
    {
        return new WebMvcConfigurerAdapter()
        {
            @Override
            public void configureMessageConverters(List<HttpMessageConverter<?>> converters)
            {
                ObjectMapper objectMapper = new ObjectMapper();
                objectMapper.setDateFormat(new ISO8601DateFormat());

                MappingJackson2HttpMessageConverter jackson = new MappingJackson2HttpMessageConverter();
                jackson.setObjectMapper(objectMapper);
                converters.add(jackson);
            }
        };
    }
}
