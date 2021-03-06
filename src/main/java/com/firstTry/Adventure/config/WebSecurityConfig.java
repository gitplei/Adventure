package com.firstTry.Adventure.config;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.Order;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.boot.autoconfigure.security.SecurityProperties;  
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import com.firstTry.Adventure.service.UserService;
/**
 *  权限控制拦截
 * @author Roger
 *
 */
@Configuration
@EnableWebSecurity
@Order(SecurityProperties.ACCESS_OVERRIDE_ORDER)  
@EnableGlobalMethodSecurity(prePostEnabled = true)
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {
	
	@Autowired
	private UserService userDetailsService; 	
	
	@Autowired
	private BCryptPasswordEncoder bCryptPasswordEncoder;  
	/**
	 * 跳过校验token验证请求的链接
	 * 静态文件也要设置
	 */
	private static final String[] PASS_URL = { 
		//控制层拦截
		"/",
		"/index",
		"/sys/generator/*",
		"/adventure/application/*",
		//访问静态资源文件去除拦截
		"/js/**",
		"/css/**",
		"/img/**",
		"/fonts/**",
		"/favicon.ico",
		"/libs/**",
		"/plugins/**",
		"*/swagger-ui.html",
		"/views/**",
		"/**/**.html",
		"/xinxinniannian",
		"/narcotics",
		"/preface",
		"/**.png",
		"/webjars/**",
		"/swagger-resources/**",
		"/v2/**"
	};

    public WebSecurityConfig(UserService userDetailsService, BCryptPasswordEncoder bCryptPasswordEncoder) {  
        this.userDetailsService = userDetailsService;  
        this.bCryptPasswordEncoder = bCryptPasswordEncoder;  
    }  
    

    @Override
    protected void configure(HttpSecurity http) throws Exception {
    	//禁用 csrf
        http.cors().and().csrf().disable().authorizeRequests()
                //允许以下请求
                .antMatchers(PASS_URL).permitAll()
                // 所有请求需要身份认证
                .anyRequest().authenticated()
                .and()
                //验证登陆
                .addFilter(new JWTLoginFilter(authenticationManager()))
                //验证token
                .addFilter(new JWTAuthenticationFilter(authenticationManager()))
                .formLogin()
                .loginPage("/login")
                .permitAll()
                .and()
	            .logout()
                .permitAll();
    }
    
    @Override  
    public void configure(AuthenticationManagerBuilder auth) throws Exception {  
        auth.userDetailsService(userDetailsService).passwordEncoder(bCryptPasswordEncoder);  
    } 
    
/*    public void configureGlobal(AuthenticationManagerBuilder auth) throws Exception {
    	System.err.println("自定义校验---------------进来了");

        // auth.userDetailsService(userDetailsService).passwordEncoder(bCryptPasswordEncoder);
         // 使用自定义身份验证组件
         auth.authenticationProvider(new CustomAuthenticationProvider(userDetailsService,bCryptPasswordEncoder));
    }*/
}