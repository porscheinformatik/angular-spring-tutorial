package at.porscheinformatik.tutorial.todo.web;

import java.io.IOException;
import java.util.List;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.web.filter.OncePerRequestFilter;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.util.ISO8601DateFormat;

@Configuration
public class WebConfig
{
    /**
     * This is needed to adapt parsing/sending of data fields in JSON.
     * 
     * @return {@link WebMvcConfigurer}
     */
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

    @Bean
    public OncePerRequestFilter nocacheFilter()
    {
        return new OncePerRequestFilter()
        {
            @Override
            protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response,
                FilterChain filterChain)
                throws ServletException, IOException
            {
                if (request.getRequestURI().startsWith("/data"))
                {
                    response.addHeader("Cache-Control", "no-cache");
                }
                filterChain.doFilter(request, response);
            }
        };
    }
}
