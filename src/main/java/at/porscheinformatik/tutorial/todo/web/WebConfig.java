package at.porscheinformatik.tutorial.todo.web;

import java.io.IOException;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.boot.autoconfigure.web.HttpMessageConverters;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.web.filter.OncePerRequestFilter;
import org.springframework.web.servlet.config.annotation.ViewControllerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.util.ISO8601DateFormat;

@Configuration
public class WebConfig extends WebMvcConfigurerAdapter
{
    
    private static final String[] TEMPLATE_VIEW_URLS = {"/index.html", "/login.html", "/error.html",
        "/todolist.html", "/todoedit.html"};
    
    @Override
    public void addViewControllers(ViewControllerRegistry registry)
    {
        
        registry.addRedirectViewController("/", "/index.html");
        
        for (String url : TEMPLATE_VIEW_URLS)
        {
            registry.addViewController(url).setViewName(url.substring(1, url.length() - 5));
        }

        // To work with html5 mode (router.config)
        registry.addViewController("/index").setViewName("index");
        registry.addViewController("/list").setViewName("index");
        registry.addViewController("/login").setViewName("login");
        registry.addViewController("/error").setViewName("index");
    }

    /**
     * This is needed to adapt parsing/sending of data fields in JSON.
     * 
     * @return {@link HttpMessageConverters}
     */
    @Bean
    public HttpMessageConverters customConverters()
    {
        ObjectMapper objectMapper = new ObjectMapper();
        objectMapper.setDateFormat(new ISO8601DateFormat());

        MappingJackson2HttpMessageConverter jackson = new MappingJackson2HttpMessageConverter();
        jackson.setObjectMapper(objectMapper);

        return new HttpMessageConverters(jackson);
    }

    /**
     * This filter is needed for IE to not cache the XHR requests
     * 
     * @return filter adding "Cache-Control: no-cache" HTTP header to all URLs belwow "/api"
     */
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
                if (request.getRequestURI().startsWith("/api"))
                {
                    response.addHeader("Cache-Control", "no-cache");
                }
                filterChain.doFilter(request, response);
            }
        };
    }
}
