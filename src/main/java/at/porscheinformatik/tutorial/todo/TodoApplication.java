package at.porscheinformatik.tutorial.todo;

import org.jsondoc.spring.boot.starter.EnableJSONDoc;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.context.web.SpringBootServletInitializer;

@SpringBootApplication
@EnableJSONDoc
public class TodoApplication extends SpringBootServletInitializer
{
    public static void main(String[] args)
    {
        SpringApplication.run(TodoApplication.class, args);
    }

    @Override
    protected SpringApplicationBuilder configure(SpringApplicationBuilder application)
    {
        return application.sources(TodoApplication.class);
    }
}
