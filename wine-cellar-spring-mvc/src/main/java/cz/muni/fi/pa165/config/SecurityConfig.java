package cz.muni.fi.pa165.config;

import javax.inject.Inject;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.web.context.AbstractSecurityWebApplicationInitializer;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

/**
 * @author Silvia Borzová
 *         18/12/2016
 */

@Configuration
@EnableWebSecurity
public class SecurityConfig extends WebSecurityConfigurerAdapter{

    @Inject
    private AuthenticationConfig authenticationConfig;

    @Inject
    public void configureGlobal(AuthenticationManagerBuilder auth) throws Exception {
        auth.authenticationProvider(authenticationConfig);
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.authorizeRequests()
                .antMatchers("/marketingevents/**").access("hasAnyRole('ROLE_ADMIN')")
                .and()
                .formLogin()
                .loginPage("/login").loginProcessingUrl("/j_spring_security_check")
                .failureUrl("/login?error=invalidLoginAttempt")
                .usernameParameter("user").passwordParameter("password")
                .and()
                .logout().logoutSuccessUrl("/")
                .logoutRequestMatcher(new AntPathRequestMatcher("/logout"))
                .and()
                .csrf().disable();

    }
}