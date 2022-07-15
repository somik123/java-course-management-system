package sg.edu.iss.javaca.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import sg.edu.iss.javaca.service.UserDetailsServiceImpl;

@Configuration
@EnableWebSecurity
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {

	@Bean
	public UserDetailsService userDetailsService() {
		return new UserDetailsServiceImpl();
	}

	@Bean
	public BCryptPasswordEncoder bCryptPasswordEncoder() {
		return new BCryptPasswordEncoder();
	}

	@Override
	protected void configure(HttpSecurity http) throws Exception {

		/* Use this one when you want to test role based authentication */

		http.authorizeRequests()
				.antMatchers("/register", "/login/**", "/", "/css/**", "/fonts/**", "/images/**", "/js/**", "/install",
						"/api/**", "/verify", "/forgot_password", "/reset_password").permitAll()
				.antMatchers("/admin/**").hasAnyAuthority("ADMIN").antMatchers("/student/**")
				.hasAnyAuthority("STUDENT").antMatchers("/lecturer/**").hasAnyAuthority("LECTURER").anyRequest()
				.authenticated().and().formLogin().loginPage("/login").defaultSuccessUrl("/login-success").permitAll()
				.and().logout().permitAll().invalidateHttpSession(true).deleteCookies("JSESSIONID");

		http.csrf().disable();

		/* Use this one while you are developing */

		/*
		 * http.authorizeRequests() .antMatchers("/**", "/register", "/login/**", "/",
		 * "/css/**", "/fonts/**", "/images/**", "/js/**")
		 * .permitAll().anyRequest().authenticated().and().formLogin().loginPage(
		 * "/login")
		 * .defaultSuccessUrl("/login-success").permitAll().and().logout().permitAll().
		 * invalidateHttpSession(true) .deleteCookies("JSESSIONID");
		 */

	}

	@Bean
	public DaoAuthenticationProvider authenticationProvider() {
		DaoAuthenticationProvider authProvider = new DaoAuthenticationProvider();
		authProvider.setUserDetailsService(userDetailsService());
		authProvider.setPasswordEncoder(bCryptPasswordEncoder());

		return authProvider;
	}

	@Override
	protected void configure(AuthenticationManagerBuilder auth) throws Exception {
		auth.authenticationProvider(authenticationProvider());
	}

}
