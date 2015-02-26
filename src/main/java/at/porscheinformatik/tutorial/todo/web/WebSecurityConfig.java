package at.porscheinformatik.tutorial.todo.web;

import java.security.SecureRandom;

import javax.persistence.EntityManagerFactory;
import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

@Configuration
public class WebSecurityConfig extends WebSecurityConfigurerAdapter
{
    private static final String CSRF_HEADER_NAME = "X-XSRF-TOKEN";
    private static final String CSRF_COOKIE_NAME = "XSRF-TOKEN";

    @Autowired
    private DataSource dataSource;

    // injected to so that schem update occurs before users initialization below
    @Autowired
    private EntityManagerFactory emf;

    @Override
    protected void configure(HttpSecurity http) throws Exception
    {
        StatelessCsrfTokenRepository csrfTokenRepository = new StatelessCsrfTokenRepository();
        csrfTokenRepository.setHeaderName(CSRF_HEADER_NAME);
        csrfTokenRepository.setCookieName(CSRF_COOKIE_NAME);
        http.csrf().csrfTokenRepository(csrfTokenRepository);

        http.authorizeRequests()
            .antMatchers("/assets/**", "/webjars/**", "/login/**", "/api-docs/**").permitAll()
            .anyRequest().fullyAuthenticated();

        http.formLogin()
            .loginProcessingUrl("/login")
            .loginPage("/login")
            .failureUrl("/login?error");

        http.logout().logoutUrl("/logout").logoutSuccessUrl("/login?logout");

        http.headers()
            .contentTypeOptions()
            .xssProtection()
            .httpStrictTransportSecurity();
    }

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception
    {
        BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder(10, new SecureRandom());

        auth.jdbcAuthentication()
            .dataSource(dataSource)
            .passwordEncoder(passwordEncoder)
            .withUser("user").password(passwordEncoder.encode("user")).authorities("USER").and()
            .withUser("admin").password(passwordEncoder.encode("admin")).authorities("ADMIN");
    }

    @Bean
    @Override
    public AuthenticationManager authenticationManagerBean() throws Exception
    {
        return super.authenticationManagerBean();
    }
}
