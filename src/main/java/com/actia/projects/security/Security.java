package com.actia.projects.security;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import com.actia.projects.services.UserService;




@Configuration
@EnableWebSecurity
public class Security extends WebSecurityConfigurerAdapter  {

	private final UserService userDetailsService;
	private final BCryptPasswordEncoder bCryptPasswordEncoder;

	public Security(UserService userDetailsService, BCryptPasswordEncoder bCryptPasswordEncoder) {
		this.userDetailsService = userDetailsService;
		this.bCryptPasswordEncoder = bCryptPasswordEncoder;
	}
	
	//the routes that will be allowed without any authorization
	   @Override
	    public void configure(WebSecurity web)  {
	        web.ignoring().antMatchers("/user/resetPassword",
	        		"/user/add",
	        		"/user/register",
	        		"/requestfile/download/*",
	        		"/user/tl",
	                "/user/sendEmail",
	        		"/tool/image/**",
	        		"/notify",
	        		"/socket/**");
	    }
	
	   //Enable CORS and disable CSRF
	   //Set session management to stateless
	   //Set permissions on endpoints
	   //Add authentication and authorization filter
	@Override
	protected void configure(HttpSecurity http) throws Exception {


		http
				.cors().and()
				.csrf().disable()
				.authorizeRequests()
				.antMatchers("/user/files/*").permitAll()
				.antMatchers("/user/add").permitAll()
				.anyRequest().authenticated()
				.and()
				.addFilter(getAuthenticationFilter())
				.addFilter(new AuthorizationFilter(authenticationManager()))
				.sessionManagement()
				.sessionCreationPolicy(SessionCreationPolicy.STATELESS);


	}



	
	public AuthenticationFilter getAuthenticationFilter() throws Exception {
		final AuthenticationFilter filter = new AuthenticationFilter(authenticationManager());
		filter.setFilterProcessesUrl("/users/login");
		return filter;
	}


	@Override
	protected void configure(AuthenticationManagerBuilder auth) throws Exception {
		auth.userDetailsService(userDetailsService).passwordEncoder(bCryptPasswordEncoder);
	}
	
	
}