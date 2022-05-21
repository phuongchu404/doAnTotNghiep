package com.bountysneaker.conf;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

@Configuration
@EnableWebSecurity
public class SecureConf extends WebSecurityConfigurerAdapter {

	@Autowired
	private UserDetailsService userDetailsService;

	@Override
	protected void configure(HttpSecurity http) throws Exception {
		http.csrf().disable().authorizeRequests() // bat dau cau hinh security

				// cho phép các request static không bị ràng buộc

				.antMatchers("/user/**", "/manager/**", "/upload/**").permitAll()

				// các request kiểu: "/admin/" phải đăng nhập

				.antMatchers("/bounty-sneaker/admin/**").hasAnyAuthority("ADMIN_S")
				.antMatchers("/bounty-sneaker/my-account.html").hasAnyAuthority("ADMIN_S", "GUEST")
				/* .antMatchers("/admin/**").permitAll() */
				.and()

				// cấu hình trang đăng nhập
				.formLogin().loginPage("/bounty-sneaker/login.html").loginProcessingUrl("/perform_login")
				.defaultSuccessUrl("/bounty-sneaker/login-success", true)
				.failureUrl("/bounty-sneaker/login.html?login_error=true").permitAll()

				.and()

				// cấu hình cho phần logout
				.logout().logoutUrl("/bounty-sneaker/logout.html").logoutSuccessUrl("/bounty-sneaker/login.html")
				.invalidateHttpSession(true).deleteCookies("JSESSIONID").permitAll();
	}

	@Autowired
	public void configureGlobal(AuthenticationManagerBuilder auth) throws Exception {
		auth.userDetailsService(userDetailsService).passwordEncoder(new BCryptPasswordEncoder(4));
	}

}