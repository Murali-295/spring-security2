package com.mk.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
//import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
//import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.web.SecurityFilterChain;

import com.mk.service.MyUserDetailsService;

@Configuration
@EnableWebSecurity
public class SecurityConfiguration {

	@Autowired
	private MyUserDetailsService detailsService;

	@Bean
	public SecurityFilterChain filterChain(HttpSecurity security) throws Exception {

		return security.csrf(AbstractHttpConfigurer::disable).authorizeHttpRequests(registry -> {
			registry.requestMatchers("/","/authentication/**").permitAll();
			registry.requestMatchers("/authentication/admin/**").hasRole("ADMIN");
			registry.requestMatchers("/authentication/user/**").hasRole("USER");
			registry.anyRequest().authenticated();
		    }).formLogin(configurer->configurer.loginPage("/login").permitAll()).build();
	}

	/*
	 * @Bean public UserDetailsService detailsService() { UserDetails userDetails =
	 * User.builder().username("murali")
	 * .password("$2a$12$FYkOA01HrjAHGRVFvMAXeuzpux7txxJ424bhVJ5/Y72Apt24XfOWG").
	 * roles("USER").build();
	 * 
	 * UserDetails adminDetails = User.builder().username("kolli")
	 * .password("$2a$12$czD8xnG/GrC.f6Rnjq3TzOs3CfN6TBrj4em4gRc9xG5TfRXefNKiC").
	 * roles("ADMIN", "USER") .build();
	 * 
	 * return new InMemoryUserDetailsManager(userDetails, adminDetails); }
	 */

	@Bean
	public UserDetailsService detailsService() {
		return detailsService;
	}

	@Bean
	public AuthenticationProvider provider() {
		DaoAuthenticationProvider daoProvider = new DaoAuthenticationProvider();
		daoProvider.setUserDetailsService(detailsService);
		daoProvider.setPasswordEncoder(encoder());
		return daoProvider;
	}

	@Bean
	public PasswordEncoder encoder() {
		return new BCryptPasswordEncoder();
	}
}