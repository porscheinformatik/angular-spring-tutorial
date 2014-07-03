package at.porscheinformatik.tutorial.todo;

import antlr.collections.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.context.web.SpringBootServletInitializer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.Environment;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;

import com.mangofactory.swagger.configuration.SpringSwaggerConfig;
import com.mangofactory.swagger.plugin.EnableSwagger;
import com.mangofactory.swagger.plugin.SwaggerSpringMvcPlugin;
import com.wordnik.swagger.model.ApiInfo;

@Configuration
@ComponentScan
@EnableAutoConfiguration
@EnableSwagger
@EnableGlobalMethodSecurity(jsr250Enabled = true)
public class TodoApplication extends SpringBootServletInitializer
{
    @Autowired
    private SpringSwaggerConfig springSwaggerConfig;

    @Autowired
    private Environment env;

    public static void main(String[] args)
    {
        SpringApplication.run(TodoApplication.class, args);
    }

    @Override
    protected SpringApplicationBuilder configure(SpringApplicationBuilder application)
    {
        return application.sources(TodoApplication.class);
    }

    @Bean
    public SwaggerSpringMvcPlugin customImplementation()
    {
        return new SwaggerSpringMvcPlugin(springSwaggerConfig)
            .apiInfo(new ApiInfo("", "", "", "", "", ""))
            .apiVersion(env.getProperty("info.app.version"))
            .directModelSubstitute(Iterable.class, List.class)
            .includePatterns("/api.*");
    }
}
