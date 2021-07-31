package com.lahiru.demo.configuration.security;

import com.lahiru.demo.enums.Permission;
import com.lahiru.demo.enums.Role;
import com.lahiru.demo.filter.CsrfHeaderFilter;
import com.lahiru.demo.filter.JwtFilter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.security.web.csrf.CookieCsrfTokenRepository;
import org.springframework.security.web.csrf.CsrfFilter;

@Configuration
@EnableWebSecurity
public class WebSecurityConfiguration extends WebSecurityConfigurerAdapter {

    @Autowired
    private UserDetailsService userDetailsService;

    @Autowired
    private JwtFilter jwtFilter;

    @Autowired
    private CsrfHeaderFilter csrfHeaderFilter;

    @Autowired
    private CsrfSecurityRequestMatcher csrfSecurityRequestMatcher;

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {

        auth.authenticationProvider(authenticationProvider());
    }

    @Override
    @Bean
    public AuthenticationManager authenticationManagerBean() throws Exception {
        return super.authenticationManagerBean();
    }

    @Bean
    AuthenticationProvider authenticationProvider() {
        DaoAuthenticationProvider provider
                = new DaoAuthenticationProvider();
        provider.setUserDetailsService(userDetailsService);
        provider.setPasswordEncoder(new BCryptPasswordEncoder());
        return  provider;
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http
                .csrf().csrfTokenRepository(CookieCsrfTokenRepository.withHttpOnlyFalse())
                .requireCsrfProtectionMatcher(csrfSecurityRequestMatcher)
                .and()
                .authorizeRequests()
                .antMatchers("/api/v1/authenticate")
                .permitAll()

                .antMatchers("/api/v1/jwt")
                .hasAnyAuthority(Role.ADMIN.name(), Role.USER.name())

                .antMatchers("/api/v1/student")
                .hasAnyAuthority(Role.ADMIN.name(), Role.USER.name())

                .antMatchers(HttpMethod.GET, "/api/v1/teacher")
                .hasAnyAuthority(Role.ADMIN.name(), Role.USER.name())
                .antMatchers(HttpMethod.POST, "/api/v1/teacher")
                .hasAuthority(Permission.TEACHER_POST.name())
                .antMatchers(HttpMethod.PUT, "/api/v1/teacher")
                .hasAuthority(Permission.TEACHER_PUT.name())
                .antMatchers(HttpMethod.DELETE, "/api/v1/teacher")
                .hasAuthority(Permission.TEACHER_DELETE.name())

                .anyRequest()
                .authenticated()
                .and()
                .sessionManagement()
                .sessionCreationPolicy(SessionCreationPolicy.STATELESS);

        http.addFilterBefore(jwtFilter, UsernamePasswordAuthenticationFilter.class);
        //http.addFilterAfter(csrfHeaderFilter, CsrfFilter.class);
        // csrfHeaderFilter not required currently, because job done by csrfHeaderFilter
        // is already handle by CookieCsrfTokenRepository
    }
}
