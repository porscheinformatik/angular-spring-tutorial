package at.porscheinformatik.tutorial.todo.web;

import java.security.SecureRandom;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.csrf.HttpSessionCsrfTokenRepository;
import org.springframework.security.web.header.writers.StaticHeadersWriter;

@Configuration
public class WebSecurityConfig extends WebSecurityConfigurerAdapter
{
    private static final String CSRF_HEADER_NAME = "X-XSRF-TOKEN";
    private static final String CSRF_COOKIE_NAME = "XSRF-TOKEN";

    @Autowired
    private DataSource dataSource;

    @Override
    protected void configure(HttpSecurity http) throws Exception
    {
        HttpSessionCsrfTokenRepository csrfTokenRepository = new HttpSessionCsrfTokenRepository();
        csrfTokenRepository.setHeaderName(CSRF_HEADER_NAME);
        http.csrf().csrfTokenRepository(csrfTokenRepository);

        http.authorizeRequests()
            .antMatchers("/assets/**", "/webjars/**", "/login/**", "/api-docs/**").permitAll()
            .antMatchers("/jsondoc/**", "/jsondoc-ui.html").permitAll()
            .anyRequest().fullyAuthenticated();

        http.formLogin()
            .loginProcessingUrl("/login")
            .loginPage("/login")
            .failureUrl("/login?error");

        http.httpBasic();

        http.logout().logoutUrl("/logout").logoutSuccessUrl("/login?logout");

        http.headers()
            .defaultsDisabled()
            .contentTypeOptions()
            .and().xssProtection()
            .and().httpStrictTransportSecurity()
            .and().addHeaderWriter(
                new StaticHeadersWriter("Access-Control-Allow-Origin", "http://petstore.swagger.wordnik.com"))
            .addHeaderWriter(new CsrfTokenCookieWriter(csrfTokenRepository, CSRF_COOKIE_NAME));
    }

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception
    {
        BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder(10, new SecureRandom());

        auth.jdbcAuthentication()
            .dataSource(dataSource)
            .passwordEncoder(passwordEncoder);
    }

    @Bean
    @Override
    public AuthenticationManager authenticationManagerBean() throws Exception
    {
        return super.authenticationManagerBean();
    }
}
