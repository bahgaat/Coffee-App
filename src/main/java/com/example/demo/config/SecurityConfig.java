package com.example.demo.config;


import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import javax.crypto.SecretKey;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import com.example.demo.user.Role;
@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true)
public class SecurityConfig extends WebSecurityConfigurerAdapter {
	
	
	private final BCryptPasswordEncoder bCryptPasswordEncoder;
	
	private final UserDetailsService userDetailsService;
    

    @Autowired
    public SecurityConfig(UserDetailsService userDetailsService, BCryptPasswordEncoder bCryptPasswordEncoder) {
        this.userDetailsService = userDetailsService;
        this.bCryptPasswordEncoder = bCryptPasswordEncoder;

    }
    
    @Override
	protected void configure(AuthenticationManagerBuilder auth) throws Exception {
		
		auth.userDetailsService(userDetailsService).passwordEncoder(bCryptPasswordEncoder);
	}

	@Override
    protected void configure(HttpSecurity http) throws Exception {
        http.csrf().disable().sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS)
        .and().authorizeRequests()
                .antMatchers(HttpMethod.POST, "/auth/**", "/login").permitAll()
                .anyRequest().authenticated()
                .and()
                .addFilter(new CustomUsernamePasswordAuthenticationFilter(authenticationManagerBean()))
                .addFilterAfter(new JwtTokenVerifier(),CustomUsernamePasswordAuthenticationFilter.class);
                
        http.formLogin()
        .loginPage("/login").permitAll();
              
    }
    

	/*
	 * @Override protected void configure(HttpSecurity http) throws Exception { http
	 * .csrf().disable() .sessionManagement()
	 * .sessionCreationPolicy(SessionCreationPolicy.STATELESS) .and() .addFilter(new
	 * CustomUsernamePasswordAuthenticationFilter(authenticationManager()))
	 * //.addFilterAfter(new
	 * JwtTokenVerifier(),CustomUsernamePasswordAuthenticationFilter.class)
	 * .authorizeRequests() .antMatchers("/api/allowed/**", "/login").permitAll()
	 * //.antMatchers("/api/**").hasRole(STUDENT.name()) .anyRequest()
	 * .authenticated(); }
	 */

    
    public Collection<? extends GrantedAuthority> getAuthorities(
			  Collection<Role> roles) {
			    List<GrantedAuthority> authorities
			      = new ArrayList<>();
			    for (Role role: roles) {
			        authorities.add(new SimpleGrantedAuthority(role.getName()));
			        
			    }
			    
			    return authorities;
			}
    
			/*
			 * @Bean protected UserDetailsService userDetailsService() { List<Role> roles =
			 * new ArrayList<>(); roles.add(Role.Employer); roles.add(Role.Customer);
			 * Collection<SimpleGrantedAuthority> auths =
			 * (Collection<SimpleGrantedAuthority>) getAuthorities(roles);
			 * 
			 * UserDetails user1 = User.builder().username("user1")
			 * .password(passwordEncoder.encode("password")) .authorities(auths) .build();
			 * 
			 * return new InMemoryUserDetailsManager(user1
			 * 
			 * );
			 * 
			 * }
			 */
	 
    @Bean
    @Override
    public AuthenticationManager authenticationManagerBean() throws Exception {
        return super.authenticationManagerBean();
    }
	 
}