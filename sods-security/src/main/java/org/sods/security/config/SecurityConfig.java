package org.sods.security.config;


import org.sods.security.filter.JwtAuthenticationTokenFilter;
import org.sods.security.handler.AccessDeniedHandlerImpl;
import org.sods.security.handler.AuthenticationEntryPointImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;


@Configuration
@EnableGlobalMethodSecurity(prePostEnabled = true)
public class SecurityConfig extends WebSecurityConfigurerAdapter {
    @Bean
    public PasswordEncoder passwordEncoder(){
        return new BCryptPasswordEncoder();
    }

    @Autowired
    private JwtAuthenticationTokenFilter jwtAuthenticationTokenFilter;

    @Autowired
    private AuthenticationEntryPointImpl authenticationEntryPoint;

    @Autowired
    private AccessDeniedHandlerImpl accessDeniedHandler;

    @Bean
    @Override
    public AuthenticationManager authenticationManagerBean() throws Exception {
        return super.authenticationManagerBean();
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http
                //close csrf
                .csrf().disable()
                //Not get session through SessionSecurityContext
                .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                .and()
                .authorizeRequests()
                // Allow Anonymous Visit
                .antMatchers("/user/login").anonymous()
                .antMatchers("/user/register").anonymous()
                .antMatchers("/public/*").anonymous()

                //Template Add new page
//                .antMatchers("/testCors").hasAuthority("system:dept:list222")
                // Other need to perform authentication
                .anyRequest().authenticated();

        //add filter
        http.addFilterBefore(jwtAuthenticationTokenFilter,UsernamePasswordAuthenticationFilter.class);

        //config error handler
        http.exceptionHandling()
                //Login fail
                .authenticationEntryPoint(authenticationEntryPoint)
                //Not enough permission
                .accessDeniedHandler(accessDeniedHandler);

        //allow cross original
        http.cors();
    }


}
