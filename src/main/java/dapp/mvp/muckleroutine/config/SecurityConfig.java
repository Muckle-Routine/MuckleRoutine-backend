package dapp.mvp.muckleroutine.config;

import java.util.HashMap;

import dapp.mvp.muckleroutine.security.ApiCheckFilter;
import dapp.mvp.muckleroutine.security.ApiLoginFilter;
import dapp.mvp.muckleroutine.security.handler.ApiLoginFailHandler;
import dapp.mvp.muckleroutine.security.util.JWTUtil;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import lombok.extern.log4j.Log4j2;

//@EnableGlobalMethodSecurity(prePostEnabled = true, securedEnabled = true)

@Configuration
@Log4j2
public class SecurityConfig extends WebSecurityConfigurerAdapter{

	@Bean
	PasswordEncoder passwordEncoder() {
		return new BCryptPasswordEncoder();
	}
	
	@Override
	protected void configure(HttpSecurity http) throws Exception{
		http.authorizeRequests()
		.antMatchers("/login").permitAll()
		.antMatchers("/swagger-ui/*").permitAll()
		.antMatchers("/**/*").permitAll(); //TODO 리팩토링
		
		http.sessionManagement()
        .sessionCreationPolicy(SessionCreationPolicy.STATELESS); // jwt token으로 인증할것이므로 세션필요없으므로 생성안함.

		http.formLogin();
		http.csrf().disable();

		http.addFilterBefore(apiCheckFilter(), UsernamePasswordAuthenticationFilter.class); //패스워드를 기반으로 동작하는 Username Filter 이전에 apiCheckFilter를 먼저 동작하도록
		http.addFilterBefore(apiLoginFilter(), UsernamePasswordAuthenticationFilter.class);
	}
	
	@Bean
	public ApiLoginFilter apiLoginFilter() throws Exception{

		ApiLoginFilter apiLoginFilter = new ApiLoginFilter("/login", jwtUtil()); //로그인 경로 지정
		apiLoginFilter.setAuthenticationManager(authenticationManager());
		apiLoginFilter.setAuthenticationFailureHandler(new ApiLoginFailHandler());

		return apiLoginFilter;
	}

	@Bean
	public JWTUtil jwtUtil() { //JWTUtil을 생성자에서 사용할 수 있도록
		return new JWTUtil();
	}
	
	@Bean
	public ApiCheckFilter apiCheckFilter() {
		HashMap<String, String> patterns = new HashMap<>();
		//pattern이 겹치지않도록 설정.
//		patterns.put("/**/*", "ROLE_USER");

		return new ApiCheckFilter(patterns, jwtUtil()); //해당 경우에만 checkFilter가 작동하도록
	}
}
